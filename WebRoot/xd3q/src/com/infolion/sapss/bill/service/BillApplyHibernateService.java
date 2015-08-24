/*
 * @(#)BillApplyHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.bill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bill.dao.BillApplyExportHibernateDao;
import com.infolion.sapss.bill.dao.BillApplyHibernateDao;
import com.infolion.sapss.bill.dao.BillApplyJdbcDao;
import com.infolion.sapss.bill.dao.BillApplyMaterialHibernateDao;
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.bill.domain.TBillApplyExport;
import com.infolion.sapss.bill.domain.TBillApplyMaterial;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.ship.dao.ShipInfoHibernateDao;
import com.infolion.sapss.ship.dao.ShipMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
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
public class BillApplyHibernateService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	BillApplyHibernateDao billApplyHibernateDao;
	@Autowired
	BillApplyExportHibernateDao billApplyExportHibernateDao;
	@Autowired
	BillApplyMaterialHibernateDao billApplyMaterialHibernateDao;
	@Autowired
	ShipMaterialHibernateDao shipMaterialHibernateDao;
	@Autowired
	ShipInfoHibernateDao shipInfoHibernateDao;
	@Autowired
	BillApplyJdbcDao billApplyJdbcDao;

	@Autowired
	ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	@Autowired
	ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao;
	@Autowired
	ContractHibernateService contractHibernateService;

	public void setBillApplyHibernate(
			BillApplyHibernateDao billApplyHibernateDao) {
		this.billApplyHibernateDao = billApplyHibernateDao;
	}

	// 新增进仓单明细资料
	public void addDetail(String billApplyId, String salesRowsId) {
		// // 先删除存在的物料信息
		// List<TBillApplyMaterial> tBillApplyMaterialList = new ArrayList();
		// tBillApplyMaterialList = billApplyMaterialHibernateDao
		// .find("from TBillApplyMaterial a where a.billApplyId ='"
		// + billApplyId + "'");
		// for (int i = 0; i < tBillApplyMaterialList.size(); i++) {
		// TBillApplyMaterial tBillApplyMaterial = (TBillApplyMaterial)
		// tBillApplyMaterialList
		// .get(i);
		// // hasQuantity += tReceiptMaterial.getQuantity();
		// billApplyMaterialHibernateDao.remove(tBillApplyMaterial);
		// }

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TContractSalesMateriel tContractSalesMateriel = this
				.getTContractSalesMateriel(salesRowsId);
		TContractSalesInfo tContractSalesInfo = contractSalesInfoHibernateDao
				.get(tContractSalesMateriel.getContractSalesId());

		TBillApplyMaterial tBillApplyMaterial = new TBillApplyMaterial();

		tBillApplyMaterial.setExportMateId(CodeGenerator.getUUID());
		tBillApplyMaterial.setBillApplyId(billApplyId);

		tBillApplyMaterial.setMaterialCode(tContractSalesMateriel
				.getVbapMatnr());
		tBillApplyMaterial.setMaterialName(tContractSalesMateriel
				.getVbapArktx());
		tBillApplyMaterial.setContractNo(tContractSalesInfo.getContractNo());
		tBillApplyMaterial.setContractSalesId(tContractSalesInfo.getContractSalesId());

		if (StringUtils.isNotBlank(tContractSalesMateriel.getRowTotal()))
			tBillApplyMaterial.setTotalMoney(Double
					.valueOf(tContractSalesMateriel.getRowTotal()));

		if (StringUtils.isNotBlank(tContractSalesMateriel.getRowTaxesRale()))
			tBillApplyMaterial.setRate(Double.valueOf(tContractSalesMateriel
					.getRowTaxesRale()));

		if (StringUtils.isNotBlank(tContractSalesMateriel.getRowTaxes()))
			tBillApplyMaterial.setTax(Double.valueOf(tContractSalesMateriel
					.getRowTaxes()));

		if (StringUtils.isNotBlank(tContractSalesMateriel.getVbapVrkme()))
			tBillApplyMaterial.setPrice(tContractSalesMateriel.getKonvKbetr());

		tBillApplyMaterial.setMaterialUnit(tContractSalesMateriel
				.getVbapVrkme());

		tBillApplyMaterial.setQuantity(tContractSalesMateriel.getVbapZmeng()
				.doubleValue());

		billApplyMaterialHibernateDao.save(tBillApplyMaterial);
	}

	/**
	 * 查询合同物料实体数据
	 * 
	 * @param purchaseRowsId
	 * @return
	 */
	public TContractSalesMateriel getTContractSalesMateriel(String salesRowsId) {
		return this.contractSalesMaterielHibernateDao.get(salesRowsId);
	}

	/**
	 * 保存开票申请单主信息
	 * 
	 * @param BillApply
	 * @return
	 */
	public String newBillApply(TBillApply BillApply) {
		String billApplyId = CodeGenerator.getUUID();
		BillApply.setBillApplyId(billApplyId);

		this.billApplyHibernateDao.save(BillApply);
		return billApplyId;
	}

	/**
	 * 保存收货主信息
	 * 
	 * @param projectInfo
	 * @return
	 */
	public void updateBillApply(TBillApply BillApply) {
		this.billApplyHibernateDao.update(BillApply);
	}

	/**
	 * 根据ID取得收货信息
	 * 
	 * @param id
	 * @return
	 */
	public TBillApply getTBillApply(String id) {
		return this.billApplyHibernateDao.get(id);
	}

	public void saveBillApply(TBillApply tBillApply) {
		billApplyHibernateDao.saveOrUpdate(tBillApply);
	}

	public TBillApply getBillApply(String billApplyId) {
		return this.billApplyHibernateDao.get(billApplyId);
	}

	// 初始化开票申请单物料信息
	public void initBillApplyMt(String billApplyId, String contractNo,
			String shipNo,String receiptTime) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		String sql = "from TShipInfo a where a.isAvailable='1' ";
		if (StringUtils.isNotBlank(shipNo)
				&& shipNo.replace(",", "").length() > 1)
			sql += " and a.shipNo In ('" + shipNo.replaceAll(",", "','") + "')";
		if (StringUtils.isNotBlank(contractNo)
				&& contractNo.replace(",", "").length() > 1)
			sql += " and a.salesNo In ('" + contractNo.replaceAll(",", "','")
					+ "')";

		// 先删除存在的开票申请单物料信息
		List<TBillApplyMaterial> tBillApplyMaterialList = new ArrayList();
		tBillApplyMaterialList = billApplyMaterialHibernateDao
				.find("from TBillApplyMaterial a where a.billApplyId ='"
						+ billApplyId + "'");
		for (int i = 0; i < tBillApplyMaterialList.size(); i++) {
			TBillApplyMaterial tBillApplyMaterial = (TBillApplyMaterial) tBillApplyMaterialList
					.get(i);
			billApplyMaterialHibernateDao.remove(tBillApplyMaterial);
		}

		// 先删除存在的开票申请单明细信息
		List<TBillApplyExport> tBillApplyExportList = new ArrayList();
		tBillApplyExportList = billApplyExportHibernateDao
				.find("from TBillApplyExport a where a.billApplyId ='"
						+ billApplyId + "'");
		for (int i = 0; i < tBillApplyExportList.size(); i++) {
			TBillApplyExport tBillApplyExport = (TBillApplyExport) tBillApplyExportList
					.get(i);
			billApplyExportHibernateDao.remove(tBillApplyExport);
		}

		// 取得出仓单列表
		List<TShipInfo> tShipInfoList = new ArrayList();
		tShipInfoList = shipInfoHibernateDao.find(sql);
		TShipInfo tShipInfo = null;
		String contractSalesId = "";
		if (tShipInfoList != null && tShipInfoList.size() > 0) {
			tShipInfo = (TShipInfo) tShipInfoList.get(0);
			contractSalesId = tShipInfo.getContractSalesId();
		}

		double sumTax = 0, sumTotal = 0, sumQuantity = 0, sumLoanMoney = 0;
		String strSapOrderNo = ",";
		String strSapReturnNo = ",";
		String strShipNo = ",";
		String shipIds="";
		if (tShipInfoList != null && tShipInfoList.size() > 0)
			for (int j = 0; j < tShipInfoList.size(); j++) {
				tShipInfo = (TShipInfo) tShipInfoList.get(j);
				String shipId = tShipInfo.getShipId();
				shipIds +="'"+shipId+"',";
				if (StringUtils.isNotBlank(tShipInfo.getShipNo())
						&& ("," + strShipNo + ",").indexOf(","
								+ tShipInfo.getShipNo() + ",") < 0)
					strShipNo += "," + tShipInfo.getShipNo();

				if (StringUtils.isNotBlank(tShipInfo.getSapOrderNo())
						&& ("," + strSapOrderNo + ",").indexOf(","
								+ tShipInfo.getSapOrderNo() + ",") < 0)
					strSapOrderNo += "," + tShipInfo.getSapOrderNo();

				if (StringUtils.isNotBlank(tShipInfo.getSapReturnNo())
						&& ("," + strSapReturnNo + ",").indexOf(","
								+ tShipInfo.getSapReturnNo() + ",") < 0)
					strSapReturnNo += "," + tShipInfo.getSapReturnNo();

				TBillApplyExport tBillApplyExport = new TBillApplyExport();
				tBillApplyExport.setBillApplyId(billApplyId);
				tBillApplyExport.setApplyExportId(CodeGenerator.getUUID());
				tBillApplyExport.setExportApplyId(tShipInfo.getShipId());
				tBillApplyExport.setContractSalesId(tShipInfo
						.getContractSalesId());
				tBillApplyExport.setNoticeNo(tShipInfo.getShipNo());
				tBillApplyExport.setSapOrderNo(tShipInfo.getSapOrderNo());
				billApplyExportHibernateDao.save(tBillApplyExport);

				// 从出仓单取物料信息
//				List<TShipMaterial> tShipMaterialList = new ArrayList<TShipMaterial>();
//				tShipMaterialList = shipMaterialHibernateDao
//						.find("from TShipMaterial a where a.shipId ='" + shipId	+ "' and a.isAvailabel='1'");
				
				/*
				for (int i = 0; i < tShipMaterialList.size(); i++) {
					TShipMaterial tShipMaterial = tShipMaterialList.get(i);

					TBillApplyMaterial tBillApplyMaterial = new TBillApplyMaterial();
					tBillApplyMaterial.setExportMateId(CodeGenerator.getUUID());

					//不能用物料号查找出单中的物料信息,
//					tBillApplyMaterialList = billApplyExportHibernateDao
//							.find("from TBillApplyMaterial a where a.ship_id='"
//									+ tShipMaterial.getShipId() + "'");
//					if (tBillApplyMaterialList.size() > 0)
//						tBillApplyMaterial = (TBillApplyMaterial) tBillApplyMaterialList.get(0);
//					else {
//						tBillApplyMaterial = new TBillApplyMaterial();
//						tBillApplyMaterial.setExportMateId(CodeGenerator.getUUID());
//					}

					tBillApplyMaterial.setBillApplyId(billApplyId);
					String saleRowsId = tShipMaterial.getSalesRowsId();
					TContractSalesMateriel saleMaterial = new TContractSalesMateriel();
					TContractSalesInfo salesInfo = new TContractSalesInfo();
					if(saleRowsId!=null){
						saleMaterial = this.getTContractSalesMateriel(saleRowsId);
						salesInfo = this.contractSalesInfoHibernateDao.get(saleMaterial.getContractSalesId());
					}
					tBillApplyMaterial.setMaterialCode(tShipMaterial.getMaterialCode());
					tBillApplyMaterial.setMaterialName(tShipMaterial.getMaterialName());
					tBillApplyMaterial.setMaterialUnit(tShipMaterial.getMaterialUnit());
//					tBillApplyMaterial.setCurrency(salesInfo.getVbakWaerk());
					tBillApplyMaterial.setCurrency(tShipMaterial.getCurrency());

//					Double total = this.round(tShipMaterial.getTotal()
//							.doubleValue(), 2);


//					double quantity =0;
					double total = 0;
					//????????可能有问题
					
/*					此出单物料为已申请的同一个物料号的信息,不能用来累加
 * 					if (tBillApplyMaterial.getQuantity() != null)
						quantity += tBillApplyMaterial.getQuantity()
								.doubleValue();
					if (tBillApplyMaterial.getTotalMoney() != null)
						total += tBillApplyMaterial.getTotalMoney()
								.doubleValue();
*/					///????????
//					tBillApplyMaterial.setQuantity(quantity + tShipMaterial.getQuantity().doubleValue());
					/*
					double rate = Double.valueOf(saleMaterial.getRowTaxesRale()==null?"1":saleMaterial.getRowTaxesRale());
					double price = saleMaterial.getKonvKbetr()/Double.valueOf(saleMaterial.getVbapKpein());
					total =total + tShipMaterial.getQuantity().doubleValue() * price;
					Double tax = total * rate /(100+rate);
					tBillApplyMaterial.setTotalMoney(total);
					tBillApplyMaterial.setRate(rate);
					tBillApplyMaterial.setTaxRate(rate+"");
					tBillApplyMaterial.setTax(tax);
					tBillApplyMaterial.setPrice(price);
//					tBillApplyMaterial.setPrice(total / quantity);
					tBillApplyMaterial.setLoanMoney(total - tax);
					tBillApplyMaterial.setQuantity(tShipMaterial.getQuantity().doubleValue());

					billApplyMaterialHibernateDao.save(tBillApplyMaterial);

					sumTax += tBillApplyMaterial.getTax();
					sumTotal += tBillApplyMaterial.getTotalMoney();
					sumQuantity += tBillApplyMaterial.getQuantity();
					sumLoanMoney += tBillApplyMaterial.getLoanMoney();
				}
			*/
			}
		if(!"".equals(shipIds))
			shipIds = shipIds.substring(0, shipIds.length()-1);
		List<TBillApplyMaterial> list = this.mergeShipMaterialSameMaterial(billApplyJdbcDao.magerBillApplyMaterial(shipIds),billApplyId,receiptTime);
		for(TBillApplyMaterial r:list){
			billApplyMaterialHibernateDao.save(r);
			sumTax += r.getTax();
			sumTotal += r.getTotalMoney();
			sumQuantity += r.getQuantity();
			sumLoanMoney += r.getLoanMoney();
		}

		
		// 取得开票申请单
		TBillApply tBillApply = billApplyHibernateDao.get(billApplyId);

		// 取得销售订单
		if (StringUtils.isNotBlank(contractSalesId)) {
			tBillApply.setContractSalesId(contractSalesId);
			TContractSalesInfo tContractSalesInfo = contractHibernateService
					.getContractSalesInfo(contractSalesId);
			if (tContractSalesInfo != null) {
				tBillApply.setBillToParty(tContractSalesInfo.getPayer());
				tBillApply
						.setBillToPartyName(tContractSalesInfo.getPayerName());
			}
		}

		tBillApply.setSapOrderNo(strSapOrderNo.replace(",,", ""));
		tBillApply.setSapReturnNo(strSapReturnNo.replace(",,", ""));
		tBillApply.setExportApplyNo(strShipNo.replace(",,", ""));

		tBillApply.setTaxTotal(sumTax);
		tBillApply.setPriceTotal(sumTotal);
		tBillApply.setQuantityTotal(sumQuantity);
		tBillApply.setLoanTotal(sumLoanMoney);

		billApplyHibernateDao.saveOrUpdate(tBillApply);
	}
	/**
	 * 
	 * @param shipMaterialList
	 * @return
	 */
	private List mergeShipMaterialSameMaterial(List<TBillApplyMaterial> materialList,String billApplyId,String receiptTime){
		List<TBillApplyMaterial> mlist = new ArrayList();
		for(TBillApplyMaterial material:materialList){
			material.setExportMateId(CodeGenerator.getUUID());
			double total = material.getPrice()*material.getQuantity();
			double tax = total * Double.valueOf(material.getTaxRate())/(100+Double.valueOf(material.getTaxRate()));
			material.setTax(tax);
			material.setTotalMoney(total);
			material.setLoanMoney(total-tax);
			material.setBillApplyId(billApplyId);
			material.setReceiptTime(receiptTime);
			mlist.add(material);
		}
		return mlist;
	}
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0)
			return v;
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 查询合同物料实体数据
	 * 
	 * @param SalesRowsId
	 * @return
	 */
	public TShipMaterial getTShipMaterial(String shipId) {
		return this.shipMaterialHibernateDao.get(shipId);
	}

	public void saveBillApplyMaterial(TBillApplyMaterial tBillApplyMaterial) {
		billApplyMaterialHibernateDao.saveOrUpdate(tBillApplyMaterial);
	}

	/**
	 * 删除开票申请单物料实体数据
	 * 
	 * @param exportMateId
	 * @return
	 */
	public void deleteMaterial(String exportMateId) {
		TBillApplyMaterial tBillApplyMaterial = billApplyMaterialHibernateDao
				.get(exportMateId);
		billApplyMaterialHibernateDao.remove(tBillApplyMaterial);
	}

	/**
	 * 删除开票申请单物料实体数据
	 * 
	 * @param exportMateId
	 * @return
	 */
	public void copyMaterial(String exportMateId) {
		TBillApplyMaterial tBillApplyMaterial = billApplyMaterialHibernateDao
				.get(exportMateId);
		TBillApplyMaterial tBillApplyMaterialNew = new TBillApplyMaterial();
		tBillApplyMaterialNew.setBillApplyId(tBillApplyMaterial.getBillApplyId());
		tBillApplyMaterialNew.setExportMateId(CodeGenerator.getUUID());
		tBillApplyMaterialNew.setCmd(tBillApplyMaterial.getCmd());
		tBillApplyMaterialNew.setCurrency(tBillApplyMaterial.getCurrency());
		tBillApplyMaterialNew.setLoanMoney(tBillApplyMaterial.getLoanMoney());
		tBillApplyMaterialNew.setMaterialCode(tBillApplyMaterial.getMaterialCode());
		tBillApplyMaterialNew.setMaterialName(tBillApplyMaterial.getMaterialName());
		tBillApplyMaterialNew.setMaterialUnit(tBillApplyMaterial.getMaterialUnit());
		tBillApplyMaterialNew.setPrice(tBillApplyMaterial.getPrice());
		tBillApplyMaterialNew.setQuantity(tBillApplyMaterial.getQuantity());
		tBillApplyMaterialNew.setRate(tBillApplyMaterial.getRate());
		tBillApplyMaterialNew.setTax(tBillApplyMaterial.getTax());
		tBillApplyMaterialNew.setTaxRate(tBillApplyMaterial.getTaxRate());
		tBillApplyMaterialNew.setTotalMoney(tBillApplyMaterial.getTotalMoney());
		billApplyMaterialHibernateDao.save(tBillApplyMaterialNew);
	}

	public TBillApplyMaterial getBillApplyMaterial(String billApplyDetailId) {
		return this.billApplyMaterialHibernateDao.get(billApplyDetailId);
	}

	/**
	 * 保存并提交进货单
	 * 
	 * @param taskId
	 * @param tBillApply
	 */
	public void submitAndSaveBillApply(String taskId, TBillApply tBillApply) {
		tBillApply.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("import_invoice_v1"));

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tBillApply.setExamineState("2");
		tBillApply.setApplyTime(DateUtils.getCurrDate(2));
		this.saveBillApply(tBillApply);

		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "开票申请单号："
				+ tBillApply.getBillApplyNo();

		tBillApply.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitBillApply(taskId, tBillApply);
	}

	/**
	 * 提交进货单
	 * 
	 * @param taskId
	 * @param tBillApply
	 */
	public void submitBillApply(String taskId, TBillApply tBillApply) {
		Map parameters = new HashMap();
		if (tBillApply.getBillType() == "2")
			parameters.put("_agent", "代理");
		parameters.put("_billType", tBillApply.getBillType());
		tBillApply.setWorkflowUserDefineTaskVariable(parameters);

		if (StringUtils.isBlank(taskId)) {
			WorkflowService.createAndSignalProcessInstance(tBillApply,
					tBillApply.getBillApplyId());
		} else {
			WorkflowService.signalProcessInstance(tBillApply, tBillApply
					.getBillApplyId(), null);
		}
	}

	/**
	 * 流程回调更新开票申请单状态
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)) {
			examineState = "3";
		} else if (ProcessCallBack.NO_EXAMINE.equals(examineResult)) {
			examineState = "5";
		} else {
			examineState = "4";
		}
		TBillApply tBillApply = this.getBillApply(businessRecordId);
		tBillApply.setExamineState(examineState);
		tBillApply.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveBillApply(tBillApply);

		System.out.println("examineState" + examineState);
	}

	public String verifyFilds(String taskId, TBillApply tBillApply) {
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId)) {
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if (taskName.indexOf("开票，并录入票号")>0) {
			String strSapBillNo = tBillApply.getSapBillNo();
			if (StringUtils.isBlank(strSapBillNo)) {
				rtn = "请录入SAP开票凭证号！";
				return rtn;
			}
			billApplyHibernateDao.saveOrUpdate(tBillApply);
		}
		return rtn;
	}

	/**
	 * 取的物料的汇总金额
	 * 
	 * @param billApplyId
	 * @return
	 */
	public List<BigDecimal> getBillApplyMtMoney(String billApplyId) {
		return this.billApplyJdbcDao.getBillApplyMtMoney(billApplyId);
	}
}
