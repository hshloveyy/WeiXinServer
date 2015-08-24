/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.receipts.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.bapi.ShipReceiptUtils;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.mainData.domain.TMaterial;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.dao.ReceiptsJdbcDao;
import com.infolion.sapss.receipts.dao.ReceiptsHibernateDao;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
import com.infolion.sapss.receipts.dao.ReceiptsMaterialHibernateDao;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service     
public class ReceiptsHibernateService extends BaseHibernateService
	implements ProcessCallBack
{
	@Autowired
	ReceiptsHibernateDao receiptsHibernateDao;
	@Autowired
	ReceiptsMaterialHibernateDao receiptsMaterialHibernateDao;	
	@Autowired
	ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao;
	@Autowired
	ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;
	@Autowired
	ReceiptsJdbcDao receiptsJdbcDao;
	@Autowired
	ProjectInfoHibernate projectInfoHibernate;
	@Autowired	
	ContractGroupHibernateDao contractGroupHibernateDao;

	public void setReceiptsHibernate(ReceiptsHibernateDao receiptsHibernateDao) {
		this.receiptsHibernateDao = receiptsHibernateDao;
	}

	/**
	 * 保存进仓单主信息
	 * @param receiptInfo
	 * @return
	 */
	public String newReceiptInfo(TReceiptInfo receiptInfo) {
		String receiptId = CodeGenerator.getUUID();
		receiptInfo.setReceiptId(receiptId);
		
		this.receiptsHibernateDao.save(receiptInfo);
		return receiptId;
	}
	
	/**
	 * 保存收货主信息
	 * @param projectInfo
	 * @return
	 */
	public void updateReceiptInfo(TReceiptInfo receiptInfo) {
		this.receiptsHibernateDao.update(receiptInfo);
	}
	
	/**
	 * 根据ID取得收货信息
	 * @param id
	 * @return
	 */
	public TReceiptInfo getTReceiptInfo(String id) {
		return this.receiptsHibernateDao.get(id);
	}
	
	public void saveReceipt(TReceiptInfo tReceiptInfo)
	{
		receiptsHibernateDao.saveOrUpdate(tReceiptInfo);
	}
	
	public void saveReceiptUpdateDate(TReceiptInfo tReceiptInfo,TReceiptInfo OldtReceiptInfo){
		//进料加工成品检查成品单价必须填写
		TContractPurchaseInfo p = contractPurchaseInfoHibernateDao.get(tReceiptInfo.getContractPurchaseId());
		if(p.getTradeType().equals("8")&&p.getEkkoBsart().equals("Z006")){
			List<TReceiptMaterial> tReceiptMaterialList =  receiptsMaterialHibernateDao
					.find("from TReceiptMaterial a where a.receiptId ='"
							+ tReceiptInfo.getReceiptId() + "'");
			for (int i = 0; i < tReceiptMaterialList.size(); i++)
			{
				TReceiptMaterial tReceiptMaterial = (TReceiptMaterial) tReceiptMaterialList
						.get(i);
				if(null==tReceiptMaterial.getCostPrice()||tReceiptMaterial.getCostPrice().doubleValue()<=0)
					throw new BusinessException("请填写物料行项目成品单价！");
			}
		}
		receiptsHibernateDao.saveOrUpdate(tReceiptInfo);
		this.updateDate(OldtReceiptInfo, tReceiptInfo);
	}
	
	public TReceiptInfo getReceiptInfo(String receiptId)
	{
		return this.receiptsHibernateDao.get(receiptId);
	}
	
	//新增进仓单明细资料
	public void addDetail(String receiptId, String purchaseRowsId,int Lines)
	{
		double hasQuantity =
			receiptsJdbcDao.getPurchaseHasQuantity(purchaseRowsId,receiptId,"").doubleValue();
		// 先删除存在的物料信息
		List<TReceiptMaterial> tReceiptMaterialList = new ArrayList();
		tReceiptMaterialList = receiptsMaterialHibernateDao
				.find("from TReceiptMaterial a where a.receiptId ='"
						+ receiptId + "' and a.purchaseRowsId='"+purchaseRowsId+"'");
		for (int i = 0; i < tReceiptMaterialList.size(); i++)
		{
			TReceiptMaterial tReceiptMaterial = (TReceiptMaterial) tReceiptMaterialList
					.get(i);
			//hasQuantity += tReceiptMaterial.getQuantity();
			receiptsMaterialHibernateDao.remove(tReceiptMaterial);
		}
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TContractPurchaseMateriel tContractPurchaseMateriel = this
				.getTContractPurchaseMateriel(purchaseRowsId);		
		TContractPurchaseInfo tContractPurchaseInfo = contractPurchaseInfoHibernateDao
				.get(tContractPurchaseMateriel.getContractPurchaseId());
		
		for (int i=0;i<Lines;i++)
		{
			TReceiptMaterial tReceiptMaterial = new TReceiptMaterial();
			// 赋值
			tReceiptMaterial.setReceiptId(receiptId);
			tReceiptMaterial.setReceiptDetailId(CodeGenerator.getUUID());
			tReceiptMaterial.setPurchaseRowsId(purchaseRowsId);
			tReceiptMaterial.setMaterialCode(tContractPurchaseMateriel
					.getEkpoMatnr()); // 物料号
			tReceiptMaterial.setMaterialName(tContractPurchaseMateriel
					.getEkpoTxz01()); // 物料名称
			tReceiptMaterial.setMaterialUnit(tContractPurchaseMateriel
					.getEkpoMeins()); // 单位EKPO_MEINS
			tReceiptMaterial.setEkpoPeinh(tContractPurchaseMateriel.getEkpoPeinh());
		
			tReceiptMaterial.setQuantity(hasQuantity / Lines); // 数量EKPO_MENGE
		//		tReceiptMaterial.setRate(Double.valueOf(tContractPurchaseMateriel
		//				.getTaxes())); // 税金TAXES
			tReceiptMaterial
				.setPrice(tContractPurchaseMateriel.getEkpoNetpr()); 	// 单价
			
			tReceiptMaterial.setTotal(Double.parseDouble(NumberUtils.round(hasQuantity/Lines*tContractPurchaseMateriel.getEkpoNetpr()/Double.parseDouble(tContractPurchaseMateriel.getEkpoPeinh()),2)));	//总计
			
			tReceiptMaterial.setCurrency(tContractPurchaseInfo.getEkkoWaers()); // 货币VBAK_WAERK
			tReceiptMaterial.setIsAvailabel("1");
			tReceiptMaterial.setCreatorTime(DateUtils
					.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		
			tReceiptMaterial.setCreator(userContext.getSysUser().getUserId());
			tReceiptMaterial.setSapRowNo(tContractPurchaseMateriel.getSapRowNo());
			receiptsMaterialHibernateDao.save(tReceiptMaterial);
		}
	}
	
	//初始化进仓单物料信息
	public void initReceiptMt(String receiptId, String contractPurchaseId,
			String receiptTime,String payAbleDate,String receiveBillDate,String sendGoodsDate)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
		
		//取得进仓单
		TReceiptInfo tReceiptInfo = receiptsHibernateDao
				.get(receiptId);

		// 取得采购合同
		TContractPurchaseInfo tContractPurchaseInfo = contractPurchaseInfoHibernateDao
				.get(contractPurchaseId);
		// 通过合同赋值
		tReceiptInfo.setContractNo(tContractPurchaseInfo.getContractNo());
		tReceiptInfo.setSapOrderNo(tContractPurchaseInfo.getSapOrderNo());

		// 获得立项号
		TProjectInfo tProjectInfo = projectInfoHibernate.get(tContractPurchaseInfo
				.getProjectId());
		tReceiptInfo.setProjectNo(tProjectInfo.getProjectNo());
		tReceiptInfo.setProjectName(tContractPurchaseInfo.getProjectName());

		// 获得合同组
		TContractGroup tContractGroup = contractGroupHibernateDao
		.get(tContractPurchaseInfo.getContractGroupId());
		tReceiptInfo.setContractGroupNo(tContractGroup.getContractGroupNo());
		
		tReceiptInfo
				.setContractPurchaseId(tContractPurchaseInfo.getContractPurchaseId());
//		tReceiptInfo.setTradeTerms(tContractPurchaseInfo.getEkkoInco1()); // 国际贸易条件

		receiptsHibernateDao.saveOrUpdate(tReceiptInfo);

		// 先删除存在的进仓单物料信息
		List<TReceiptMaterial> tReceiptMaterialList = new ArrayList();
		tReceiptMaterialList = receiptsMaterialHibernateDao
				.find("from TReceiptMaterial a where a.receiptId ='"
						+ receiptId + "'");
		for (int i = 0; i < tReceiptMaterialList.size(); i++)
		{
			TReceiptMaterial tReceiptMaterial = (TReceiptMaterial) tReceiptMaterialList
					.get(i);
			receiptsMaterialHibernateDao.remove(tReceiptMaterial);
		}
		
		// 从采购合同物料取物料信息
		List<TContractPurchaseMateriel> tContractPurchaseMaterielList = new ArrayList();
		tContractPurchaseMaterielList = contractPurchaseMaterielHibernateDao
				.find("from TContractPurchaseMateriel a where a.contractPurchaseId ='"
						+ contractPurchaseId + "' order by createTime desc");

		for (int i = 0; i < tContractPurchaseMaterielList.size(); i++)
		{
			TContractPurchaseMateriel tContractPurchaseMateriel = tContractPurchaseMaterielList
					.get(i);
			
			String purchaseRowsId=tContractPurchaseMateriel.getPurchaseRowsId();
			BigDecimal hasQuantity = receiptsJdbcDao
				.getPurchaseHasQuantity(purchaseRowsId,receiptId,"");
			
			if (hasQuantity.signum() == 1)
			{
				TReceiptMaterial tReceiptMaterial = new TReceiptMaterial();
				tReceiptMaterial.setReceiptId(receiptId);
				tReceiptMaterial.setReceiptDetailId(CodeGenerator.getUUID());
				tReceiptMaterial.setPurchaseRowsId(purchaseRowsId);
				tReceiptMaterial.setMaterialCode(tContractPurchaseMateriel
						.getEkpoMatnr()); // 物料号
				tReceiptMaterial.setMaterialName(tContractPurchaseMateriel
						.getEkpoTxz01()); // 物料名称
				tReceiptMaterial.setMaterialUnit(tContractPurchaseMateriel
						.getEkpoMeins()); // 单位EKPO_MEINS			
				tReceiptMaterial.setQuantity(hasQuantity.doubleValue()); // 数量EKPO_MENGE
	//			tReceiptMaterial.setRate(Double.valueOf(tContractPurchaseMateriel
	//					.getTaxes())); // 税金TAXES
				tReceiptMaterial
						.setPrice(tContractPurchaseMateriel.getEkpoNetpr()); // 单价
				tReceiptMaterial.setEkpoPeinh(tContractPurchaseMateriel.getEkpoPeinh());//条件定价单位
				tReceiptMaterial
					.setTotal((hasQuantity.doubleValue() *
							tContractPurchaseMateriel.getEkpoNetpr())/Double.parseDouble(tContractPurchaseMateriel.getEkpoPeinh())); // 数量 * 单价
		
				tReceiptMaterial.setCurrency(tContractPurchaseInfo.getEkkoWaers()); // 货币VBAK_WAERK
				tReceiptMaterial.setIsAvailabel("1");
				tReceiptMaterial.setCreatorTime(DateUtils
						.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
	
				tReceiptMaterial.setCreator(userContext.getSysUser()
						.getUserId());
				tReceiptMaterial.setSapRowNo(tContractPurchaseMateriel.getSapRowNo());

				tReceiptMaterial.setReceiptTime(receiptTime);
				tReceiptMaterial.setPayAbleDate(payAbleDate);
				tReceiptMaterial.setReceiveBillDate(receiveBillDate);
				tReceiptMaterial.setSendGoodsDate(sendGoodsDate);
				
				String saleTaxCd = SysCachePoolUtils.getDictDataValue("BM_SALES_TAX", tContractPurchaseMateriel.getEkpoMwskz());
				Double salesPrice = tReceiptMaterial.getTotal() * ((Double.valueOf(parseInt(saleTaxCd))/100) + 1);
				tReceiptMaterial.setSalesPrice(salesPrice.toString());
				tReceiptMaterial.setCurrentClearCharge("");
				
				receiptsMaterialHibernateDao.save(tReceiptMaterial);
				//System.out.println(tReceiptMaterial.getMaterialCode());
			}
			else
			{
				//System.out.println(tContractPurchaseMateriel
					//	.getEkpoMatnr() + " - 0");
			}
		}
		
	}
	
	/**
	 * 得到税率
	 * @param str
	 * @return
	 */
	private String parseInt(String str){
		if(str!=null && !"".equals(str)){
			char[] chs = str.toCharArray();
			int m=1;
			int result = 0;
			for (int i = chs.length-1; i>-1; i--) {
				if(String.valueOf(chs[i]).matches("\\d")){
					result = result+ Integer.parseInt(String.valueOf(chs[i]))*m;
					m=m*10;
				}
			}
			return result+"";
		}else
			return "0";
		
	}
	
	private void saveMaterialInventoryTable(TReceiptMaterial tReceiptMaterial){
//		this.receiptsJdbcDao
	}
	/**
	 * 查询合同物料实体数据
	 * @param purchaseRowsId
	 * @return
	 */
	public TContractPurchaseMateriel getTContractPurchaseMateriel(String purchaseRowsId){
		return  this.contractPurchaseMaterielHibernateDao.get(purchaseRowsId);
	}
	
	public void saveReceiptMaterial(
			TReceiptMaterial tReceiptMaterial)
	{
		receiptsMaterialHibernateDao.saveOrUpdate(tReceiptMaterial);
	}	
	
	/**
	 * 删除进仓单物料实体数据
	 * @param receiptDetailId
	 * @return
	 */
	public void deleteMaterial(String receiptDetailIds)
	{
		String[] ids = receiptDetailIds.split(",");
		for(String id:ids){
			if(id==null) continue;
			TReceiptMaterial tReceiptMaterial = receiptsMaterialHibernateDao.get(id);
			receiptsMaterialHibernateDao.remove(tReceiptMaterial);
		}
	}
	
	public TReceiptMaterial getReceiptMaterial(String receiptDetailId)
	{
		return this.receiptsMaterialHibernateDao.get(receiptDetailId);
	}

	/**
	 * 保存并提交进货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitAndSaveReceiptInfo(String taskId,
			TReceiptInfo tReceiptInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tReceiptInfo.setExamineState("2");
		tReceiptInfo.setApplyTime(DateUtils.getCurrDate(2));
		this.saveReceipt(tReceiptInfo);

		String workflowBusinessNote = ((StringUtils.isNullBlank(tReceiptInfo.getOldReceiptId()))?"":"进仓冲销|")+userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "序号："+ tReceiptInfo.getSerialNo()+
		                       "|外合同号："+tReceiptInfo.getContractPaperNo();
		tReceiptInfo.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitReceiptInfo(taskId, tReceiptInfo);
	}	

	/**
	 * 提交进货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitReceiptInfo(String taskId, TReceiptInfo tReceiptInfo)
	{
		tReceiptInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("receipt_v1"));
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tReceiptInfo,
					tReceiptInfo.getReceiptId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tReceiptInfo, tReceiptInfo
					.getReceiptId(), null);
		}
	}
	
	/**
	 * 流程回调更新进仓单状态
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		TReceiptInfo tReceiptInfo = this.getReceiptInfo(businessRecordId);
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
			if(StringUtils.isNullBlank(tReceiptInfo.getReceiptNo())){
			    tReceiptInfo.setReceiptNo(receiptsJdbcDao.getReceiptNo(receiptsJdbcDao.queryDeptCode(businessRecordId).substring(0, 2)));
			}
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(examineResult))
		{
			examineState = "5";
		}
		else
		{
			examineState = "4";
		}
		
		tReceiptInfo.setExamineState(examineState);
		tReceiptInfo.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		if("3".equals(examineState)&&!StringUtils.isNullBlank(tReceiptInfo.getOldReceiptId())){
			tReceiptInfo.setBillState("1");
			TReceiptInfo oldReceiptInfo = this.getReceiptInfo(tReceiptInfo.getOldReceiptId());
			oldReceiptInfo.setBillState("1");
			this.saveReceipt(oldReceiptInfo);
		}
		this.saveReceipt(tReceiptInfo);
		
		//System.out.println("examineState" + examineState);
	}
	
	/**查询库存数量
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public double queryInventoryNum(String materialCode,String warehouse,String batchNo){
		//存代理批次号不允许重复
		return receiptsJdbcDao.queryInventoryNum(materialCode,warehouse,batchNo);
	}
	public boolean isRightBatchNo(String materialCode,String warehouse,String batchNo,String tradeType,String deptId){
		return receiptsJdbcDao.isRightBatchNo(materialCode,warehouse,batchNo,tradeType,deptId);
	}
	
	public Map<String,String> dealPost(String receiptId,String postDate,String deptCode){
		TReceiptInfo info = receiptsHibernateDao.get(receiptId);
		//不是500打头的允许过账
		if(info.getSapReturnNo()==null||"".equals(info.getSapReturnNo().trim())||info.getSapReturnNo().trim().indexOf("500")==-1){

			info.setPostDate(postDate);
			List<TReceiptMaterial> materials = receiptsMaterialHibernateDao
			.find("from TReceiptMaterial a where a.receiptId ='"+ receiptId + "'");
			Map<String,String> map = ShipReceiptUtils.receiptBapi(info, materials, deptCode);
			if(map.get("SAP_RETURN_NO")!=null&&map.get("SAP_RETURN_NO").length()>1)
				info.setSapReturnNo(map.get("SAP_RETURN_NO"));
			receiptsHibernateDao.saveOrUpdate(info);
			return map;
		}
		else {
			Map<String, String> map = new HashMap<String,String>();
			map.put("SAP_RETURN_NO","");
			map.put("SAP_RETURN_MSG","过账失败!已过账,请检查");
			return map;
		}
	}
	
	/**
	 * 更新进仓物料表中的日期信息
	 * @param OldtReceiptInfo
	 * @param NewtReceiptInfo
	 */
	public void updateDate(TReceiptInfo OldtReceiptInfo,TReceiptInfo NewtReceiptInfo){
		if (NewtReceiptInfo.getReceiptTime() != null && !NewtReceiptInfo.getReceiptTime().equals(OldtReceiptInfo.getReceiptTime())){
			this.receiptsJdbcDao.updateDate("RECEIPTTIME", 
					NewtReceiptInfo.getReceiptTime(), 
					NewtReceiptInfo.getReceiptId());
		}
		
		if (NewtReceiptInfo.getPayAbleDate() != null && !NewtReceiptInfo.getPayAbleDate().equals(OldtReceiptInfo.getPayAbleDate())){
			this.receiptsJdbcDao.updateDate("PAYABLEDATE", 
					NewtReceiptInfo.getPayAbleDate(), 
					NewtReceiptInfo.getReceiptId());
		}
		
		if (NewtReceiptInfo.getReceiveBillDate() !=null && !NewtReceiptInfo.getReceiveBillDate().equals(OldtReceiptInfo.getReceiveBillDate())){
			this.receiptsJdbcDao.updateDate("RECEIVEBILLDATE", 
					NewtReceiptInfo.getReceiveBillDate(), 
					NewtReceiptInfo.getReceiptId());
		}
		
		if (NewtReceiptInfo.getSendGoodsDate() != null && !NewtReceiptInfo.getSendGoodsDate().equals(OldtReceiptInfo.getSendGoodsDate())){
			this.receiptsJdbcDao.updateDate("SENDGOODSDATE", 
					NewtReceiptInfo.getSendGoodsDate(), 
					NewtReceiptInfo.getReceiptId());
		}
	}
	
	
	public String writeOffReceiptInfo(TReceiptInfo info){
		String receiptId = CodeGenerator.getUUID();
		info.setReceiptId(receiptId);
		this.receiptsHibernateDao.save(info);
		List<TReceiptMaterial> list = receiptsMaterialHibernateDao.find("from TReceiptMaterial a where a.receiptId ='"+info.getOldReceiptId()+"'");
		for(TReceiptMaterial m :list){
			TReceiptMaterial n = new TReceiptMaterial();
			BeanUtils.copyProperties(m, n);
			n.setReceiptDetailId(CodeGenerator.getUUID());
			n.setReceiptId(receiptId);
			n.setQuantity(-m.getQuantity());
			n.setTotal(-m.getTotal());
			n.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			n.setCreator(info.getCreator());
			receiptsMaterialHibernateDao.save(n);
		}
		return receiptId;
	
	}
	public boolean isAreadyWriteOff(String oldReceiptId){
		List<TReceiptInfo> list = receiptsHibernateDao
		.find("from TReceiptInfo a where a.oldReceiptId ='"+oldReceiptId+"' and a.isAvailable=1 and a.examineState!=4");
		if(list.isEmpty()) return false;
		return true;
	}
	/**
	public Map<String,String> dealPostFuck(String receiptId,String postDate,String deptCode){
		TReceiptInfo info = receiptsHibernateDao.get(receiptId);
		info.setPostDate(postDate);
		String oldSapReturnNo = info.getSapReturnNo();
		List<TReceiptMaterial> materials = receiptsMaterialHibernateDao
				.find("from TReceiptMaterial a where a.receiptId ='"+ receiptId + "'");
		Map<String,String> map = ShipReceiptUtils.receiptBapi(info, materials, deptCode);
		String newReturnNo = map.get("SAP_RETURN_NO");
		String returnMsg = map.get("SAP_RETURN_MSG");
		if(map.get("SAP_RETURN_NO")!=null&&map.get("SAP_RETURN_NO").length()>1)
			info.setSapReturnNo(map.get("SAP_RETURN_NO"));
		else map.put("SAP_RETURN_NO", "false");
		receiptsHibernateDao.saveOrUpdate(info);
		receiptsJdbcDao.fuckDeal(receiptId, oldSapReturnNo, newReturnNo, returnMsg);
		return map;

	}**/
}
