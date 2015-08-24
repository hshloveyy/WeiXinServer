/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.ship.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractAgentMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.ship.dao.ExportApplyHibernateDao;
import com.infolion.sapss.ship.dao.ExportApplyJdbcDao;
import com.infolion.sapss.ship.dao.ExportApplyMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TExportApply;
import com.infolion.sapss.ship.domain.TExportApplyMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ShipNotifyHibernateService extends BaseHibernateService implements
		ProcessCallBack
{
	@Autowired
	ExportApplyHibernateDao exportApplyHibernateDao;
	@Autowired
	ExportApplyMaterialHibernateDao exportApplyMaterialHibernateDao;
	@Autowired
	ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao;
	@Autowired
	ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	@Autowired
	ExportApplyJdbcDao exportApplyJdbcDao;
	@Autowired
	ContractGroupHibernateDao contractGroupHibernateDao;
	@Autowired
	ProjectInfoHibernate projectInfoHibernate;
	@Autowired
	ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao;
	@Autowired
	ShipNotifyJdbcService shipNotifyJdbcService;

	public void setShipNotifyJdbcService(ShipNotifyJdbcService shipNotifyJdbcService) {
		this.shipNotifyJdbcService = shipNotifyJdbcService;
	}

	public void setExportApplyHibernateDao(
			ExportApplyHibernateDao exportApplyHibernateDao)
	{
		this.exportApplyHibernateDao = exportApplyHibernateDao;
	}

	public void setExportApplyMaterialHibernateDao(
			ExportApplyMaterialHibernateDao exportApplyMaterialHibernateDao)
	{
		this.exportApplyMaterialHibernateDao = exportApplyMaterialHibernateDao;
	}

	public void setContractSalesMaterielHibernateDao(
			ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao)
	{
		this.contractSalesMaterielHibernateDao = contractSalesMaterielHibernateDao;
	}

	public void setContractSalesInfoHibernateDao(
			ContractSalesInfoHibernateDao contractSalesInfoHibernateDao)
	{
		this.contractSalesInfoHibernateDao = contractSalesInfoHibernateDao;
	}

	public void setExportApplyJdbcDao(ExportApplyJdbcDao exportApplyJdbcDao)
	{
		this.exportApplyJdbcDao = exportApplyJdbcDao;
	}

	public void setContractGroupHibernateDao(
			ContractGroupHibernateDao contractGroupHibernateDao)
	{
		this.contractGroupHibernateDao = contractGroupHibernateDao;
	}

	public void setProjectInfoHibernate(
			ProjectInfoHibernate projectInfoHibernate)
	{
		this.projectInfoHibernate = projectInfoHibernate;
	}

	public void setContractAgentMaterielHibernateDao(
			ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao)
	{
		this.contractAgentMaterielHibernateDao = contractAgentMaterielHibernateDao;
	}

	public TExportApply getExportApply(String exportApplyId)
	{
		return this.exportApplyHibernateDao.get(exportApplyId);
	}

	public TExportApplyMaterial getExportApplyMaterial(String exportMateId)
	{
		return this.exportApplyMaterialHibernateDao.get(exportMateId);

	}

	public void saveExportApply(TExportApply tExportApply)
	{
		exportApplyHibernateDao.saveOrUpdate(tExportApply);
	}

	public void deleteMaterial(String exportMateId)
	{
		TExportApplyMaterial tExportApplyMaterial = exportApplyMaterialHibernateDao
				.get(exportMateId);
		exportApplyMaterialHibernateDao.remove(tExportApplyMaterial);
	}
	
	public void saveExportApplyMaterial(
			TExportApplyMaterial tExportApplyMaterial)
	{
		exportApplyMaterialHibernateDao.saveOrUpdate(tExportApplyMaterial);
	}

	/**
	 * 新增出口货物通知单
	 * 
	 * @param tradeType
	 * @return
	 */
	public TExportApply addExportApply(String tradeType)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TExportApply tExportApply = new TExportApply();
		tExportApply.setTradeType(tradeType);
		tExportApply.setIsAvaiLable("1");
		tExportApply.setExamineState("1");
		tExportApply.setCreator(userContext.getSysUser().getUserId());
		tExportApply.setCreatorTime(DateUtils
				.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		tExportApply.setExportApplyId(CodeGenerator.getUUID());
		tExportApply.setNoticeNo(exportApplyJdbcDao.getNoticeNo());
		tExportApply.setDeptId(userContext.getSysDept().getDeptid());
		this.saveExportApply(tExportApply);
		return tExportApply;
	}

	/**
	 * 删除出口货物通知单
	 * 
	 * @param exportApplyId
	 */
	public boolean delExportApply(String exportApplyId)
	{
		TExportApply tExportApply = this.exportApplyHibernateDao
				.get(exportApplyId);
		if(exportApplyJdbcDao.checkIsExistExportBillExam(exportApplyId)){
			return false;
		}
		tExportApply.setIsAvaiLable("0");
		this.exportApplyHibernateDao.saveOrUpdate(tExportApply);
		this.exportApplyJdbcDao.deleteMaterielByApply(exportApplyId);
		return true;
	}

	/**
	 * 查询合同物料实体数据
	 * 
	 * @param salesRowsId
	 * @return
	 */
	public TContractSalesMateriel getTContractSalesMateriel(String salesRowsId)
	{
		return this.contractSalesMaterielHibernateDao.get(salesRowsId);
	}

	/**
	 * 初始化出口货物通知单
	 * @param exportApplyId
	 * @param contractSalesId
	 */
	public void initExportApply(String exportApplyId, String contractSalesId)
	{

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 取的申请单
		TExportApply tExportApply = exportApplyHibernateDao.get(exportApplyId);
		// 取的合同
		TContractSalesInfo tContractSalesInfo = contractSalesInfoHibernateDao
				.get(contractSalesId);
		// 通过合同赋值
		tExportApply.setSalesNo(tContractSalesInfo.getContractNo());
		tExportApply.setSapOrderNo(tContractSalesInfo.getSapOrderNo());
		tExportApply
				.setContractSalesId(tContractSalesInfo.getContractSalesId());
		tExportApply.setTradeTerms(tContractSalesInfo.getVbkdInco1()); // 国际贸易条件
		tExportApply.setTradeTerms2(tContractSalesInfo.getVbkdInco2()); //
		// 获的合同组
		TContractGroup tContractGroup = contractGroupHibernateDao
				.get(tContractSalesInfo.getContractGroupId());
		tExportApply.setContractGroupNo(tContractGroup.getContractGroupNo());
		// 获得立项
		TProjectInfo tProjectInfo = projectInfoHibernate.get(tContractSalesInfo
				.getProjectId());
		tExportApply.setProjectNo(tProjectInfo.getProjectNo());
		tExportApply.setProjectName(tProjectInfo.getProjectName());
		exportApplyHibernateDao.saveOrUpdate(tExportApply);

		// 先删除存在的出货通知单物料信息
		List<TExportApplyMaterial> tExportApplyMaterialList = new ArrayList();
		tExportApplyMaterialList = exportApplyMaterialHibernateDao
				.find("from TExportApplyMaterial a where a.exportApplyId ='"
						+ exportApplyId + "'");
		for (int i = 0; i < tExportApplyMaterialList.size(); i++)
		{
			TExportApplyMaterial tExportApplyMaterial = (TExportApplyMaterial) tExportApplyMaterialList
					.get(i);
			exportApplyMaterialHibernateDao.remove(tExportApplyMaterial);
		}
		// 取贸易类型
		String tradeType = tExportApply.getTradeType();
		// 如果是代理出口的取合同代理物料信息
		if ("5".equals(tradeType))
		{
			List<TContractAgentMateriel> tContractAgentMaterielList = new ArrayList();
			tContractAgentMaterielList = contractAgentMaterielHibernateDao
					.find("from TContractAgentMateriel a where a.contractSalesId ='"
							+ contractSalesId + "'");
			for (int i = 0; i < tContractAgentMaterielList.size(); i++)
			{
				TContractAgentMateriel tContractAgentMateriel = tContractAgentMaterielList
						.get(i);
				BigDecimal hasQuantity = exportApplyJdbcDao
						.getAgentHasQuantity(tContractAgentMateriel
								.getSalesRowsId());
				if (hasQuantity.signum() == 1)
				{
					this.addMtByAgentMt(exportApplyId, tContractSalesInfo,
							tContractAgentMateriel, hasQuantity);
				}
			}
		}
		else
		{
			List<TContractSalesMateriel> tContractSalesMaterielList = new ArrayList();
			tContractSalesMaterielList = contractSalesMaterielHibernateDao
					.find("from TContractSalesMateriel a where a.contractSalesId ='"
							+ contractSalesId + "'");
			for (int i = 0; i < tContractSalesMaterielList.size(); i++)
			{
				TContractSalesMateriel tContractSalesMateriel = tContractSalesMaterielList
						.get(i);
				BigDecimal hasQuantity = exportApplyJdbcDao
						.getSalesHasQuantity(tContractSalesMateriel
								.getSalesRowsId());
				if (hasQuantity.signum() == 1)
				{
					this.addMtBySalesMt(exportApplyId, tContractSalesInfo,
							tContractSalesMateriel, hasQuantity);
				}
			}
		}

	}

	/**
	 * 通过销售物料新增出货通知单物料信息
	 * 
	 * @param exportApplyId
	 * @param tContractSalesInfo
	 * @param tContractSalesMateriel
	 * @return
	 */
	public TExportApplyMaterial addMtBySalesMt(String exportApplyId,
			TContractSalesInfo tContractSalesInfo,
			TContractSalesMateriel tContractSalesMateriel,
			BigDecimal hasQuantity)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TExportApplyMaterial tExportApplyMaterial = new TExportApplyMaterial();
		tExportApplyMaterial.setExportApplyId(exportApplyId);
		tExportApplyMaterial.setSalesRowsId(tContractSalesMateriel
				.getSalesRowsId());
		tExportApplyMaterial.setExportMateId(CodeGenerator.getUUID());
		tExportApplyMaterial.setMaterialCode(tContractSalesMateriel
				.getVbapMatnr()); // 物料号
		tExportApplyMaterial.setMaterialName(tContractSalesMateriel
				.getVbapArktx()); // 物料名称
		tExportApplyMaterial.setMaterialUnit(tContractSalesMateriel
				.getVbapVrkme()); // 单位VBAP_VRKME
		// tExportApplyMaterial.setQuantity(tContractSalesMateriel.getVbapZmeng());
		// // 数量
		tExportApplyMaterial.setQuantity(hasQuantity);
		tExportApplyMaterial.setPeinh(tContractSalesMateriel.getVbapKpein()); //每价格单位
		String rate = tContractSalesMateriel.getRowTaxesRale();
		if (StringUtils.isBlank(rate))
		{
			rate = "0";
		}
		tExportApplyMaterial.setRate(Double.valueOf(rate)); // 税率ROW_TAXES_RALE
		tExportApplyMaterial.setPrice(tContractSalesMateriel.getKonvKbetr()); // 单价
		tExportApplyMaterial.setCurrency(tContractSalesInfo.getVbakWaerk()); // 货币VBAK_WAERK
		tExportApplyMaterial.setIsAvailabel("1");
		tExportApplyMaterial.setCreatorTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		tExportApplyMaterial.setTotal(tExportApplyMaterial.getQuantity().
				multiply(new BigDecimal(tExportApplyMaterial.getPrice())).
				divide(new BigDecimal(tExportApplyMaterial.getPeinh())));  //add by zxj 20090508
		tExportApplyMaterial.setCreator(userContext.getSysUser().getUserId());
		tExportApplyMaterial.setSapRowNo(tContractSalesMateriel.getSapRowNo());
		exportApplyMaterialHibernateDao.save(tExportApplyMaterial);
		return tExportApplyMaterial;

	}

	/**
	 * 通过代理物料新增出货通知单物料信息
	 * 
	 * @param exportApplyId
	 * @param tContractSalesInfo
	 * @param tContractAgentMateriel
	 * @return
	 */
	public TExportApplyMaterial addMtByAgentMt(String exportApplyId,
			TContractSalesInfo tContractSalesInfo,
			TContractAgentMateriel tContractAgentMateriel,
			BigDecimal hasQuantity)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TExportApplyMaterial tExportApplyMaterial = new TExportApplyMaterial();
		tExportApplyMaterial.setExportApplyId(exportApplyId);
		tExportApplyMaterial.setAgentMaterialId(tContractAgentMateriel
				.getSalesRowsId()); // 代理物料行ID
		tExportApplyMaterial.setExportMateId(CodeGenerator.getUUID());
		tExportApplyMaterial.setMaterialCode(tContractAgentMateriel // 物料号
				.getVbapMatnr());
		tExportApplyMaterial.setMaterialName(tContractAgentMateriel // 物料名称
				.getVbapArktx());
		tExportApplyMaterial.setMaterialUnit(tContractAgentMateriel // 单位
				.getVbapVrkme());
		// tExportApplyMaterial.setQuantity(tContractAgentMateriel // 数量
		// .getVbapZmeng());
		tExportApplyMaterial.setQuantity(hasQuantity);
		tExportApplyMaterial.setPeinh(tContractAgentMateriel.getVbapKpein());  //每价格单位
//		tExportApplyMaterial.setTotal(tExportApplyMaterial.getPrice() * hasQuantity / tExportApplyMaterial.getPeinh());//add by zxj 20090508
		String rate = tContractAgentMateriel.getRowTaxesRale();
		if (StringUtils.isBlank(rate))
		{
			rate = "0";
		}
		tExportApplyMaterial.setRate(Double.valueOf(rate)); // 税率
		tExportApplyMaterial.setPrice(tContractAgentMateriel.getKonvKbetr());
		tExportApplyMaterial.setCurrency(tContractSalesInfo // 货币
				.getVbakWaerk());
		tExportApplyMaterial.setIsAvailabel("1");
		tExportApplyMaterial.setCreatorTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));

		tExportApplyMaterial.setCreator(userContext.getSysUser().getUserId());
		exportApplyMaterialHibernateDao.save(tExportApplyMaterial);
		return tExportApplyMaterial;
	}

	/**
	 * 通过销售物料明细ID新增出货通知单物料信息
	 * 
	 * @param exportApplyId
	 * @param tradeType
	 * @param salesRowsId
	 * @return
	 */
	public TExportApplyMaterial addMtByCon(String exportApplyId,
			String tradeType, String salesRowsId, String hasQuantity)
	{
		TExportApplyMaterial tExportApplyMaterial = new TExportApplyMaterial();
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		TContractSalesMateriel tContractSalesMateriel = new TContractSalesMateriel();
		TContractAgentMateriel tContractAgentMateriel = new TContractAgentMateriel();
		BigDecimal quantity = BigDecimal.valueOf(Double
				.parseDouble(hasQuantity));
		if ("5".equals(tradeType))
		{
			tContractAgentMateriel = contractAgentMaterielHibernateDao
					.get(salesRowsId);
			tContractSalesInfo = contractSalesInfoHibernateDao
					.get(tContractAgentMateriel.getContractSalesId());
			tExportApplyMaterial = this.addMtByAgentMt(exportApplyId,
					tContractSalesInfo, tContractAgentMateriel, quantity);
		}
		else
		{
			tContractSalesMateriel = contractSalesMaterielHibernateDao
					.get(salesRowsId);
			tContractSalesInfo = contractSalesInfoHibernateDao
					.get(tContractSalesMateriel.getContractSalesId());
			tExportApplyMaterial = this.addMtBySalesMt(exportApplyId,
					tContractSalesInfo, tContractSalesMateriel, quantity);
		}
		return tExportApplyMaterial;
	}

	/**
	 * 保存并提交出货通知单
	 * 
	 * @param taskId
	 * @param tExportApply
	 */
	public void submitAndSaveExportApply(String taskId,
			TExportApply tExportApply)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tExportApply.setExamineState("2");
		tExportApply.setApplyTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveExportApply(tExportApply);

		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "出货通知单号："
				+ tExportApply.getNoticeNo();
		tExportApply.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitExportApply(taskId, tExportApply);
	}

	/**
	 * 提交出口货物通知单
	 * 
	 * @param taskId
	 * @param tExportApply
	 */
	public void submitExportApply(String taskId, TExportApply tExportApply)
	{
		Map parameters = new HashMap();
		
		tExportApply.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("export_v1"));

		String writeTo = tExportApply.getWriteTo();
		parameters.put("_workflow_writeTo_value", writeTo);
		parameters.put("_workflow_ispay", tExportApply.getIsPay());
		BigDecimal bd = exportApplyJdbcDao.getExportApplyTotal(tExportApply.getExportApplyId());
		parameters.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		TExportApply temp = getExportApply(tExportApply.getExportApplyId());
		temp.setCustomer(tExportApply.getCustomer());
		saveExportApply(temp);
		tExportApply.setWorkflowUserDefineTaskVariable(parameters);
		
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tExportApply,
					tExportApply.getExportApplyId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tExportApply, tExportApply
					.getExportApplyId(), null);
		}
	}
	
	/**
	 * 提交出口货物通知单条件检查
	 * @param taskId
	 * @param taskName
	 * @param tExportApply
	 * @throws BusinessException
	 */
	public void checkSubmit(String taskId,String taskName,TExportApply tExportApply) throws BusinessException {
		if (taskName.equals("填写核销单号")){
			if (StringUtils.isBlank(tExportApply.getWriteTo())){
				throw new BusinessException("核销单号没有填写");
			}
		}
		
	}
	
	/**
	 * 在审批失败的时候更新单据所关联的物料信息，将被使用的物料数量返回
	 * @param exportApplyIds
	 */
	public void updateFailExportApplyMateriel(final String[] exportApplyIds){
		final String hql = "from TExportApplyMaterial where exportApplyId in (:ids)";
		List<?> exportApplyMaterials = (List<?>) this.exportApplyHibernateDao
		.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(hql);
				q.setParameterList("ids", exportApplyIds);
				return q.list();
			}
		});
		TExportApplyMaterial exportApplyMaterial;
		Iterator<?> iExportApplyMaterial;
		if(exportApplyMaterials!=null&&exportApplyMaterials.size()>0){
			iExportApplyMaterial = exportApplyMaterials.iterator();
			for(int i=0;iExportApplyMaterial.hasNext();i++){
				exportApplyMaterial = (TExportApplyMaterial)iExportApplyMaterial.next();
				this.shipNotifyJdbcService.updateExportApplyMateriel(
						exportApplyMaterial.getExportMateId(), "IS_AVAILABLE" ,"0");
			}
		}
	}

	public void updateStateAndWriteTo(String businessRecordId,
			String examineResult, String writeTo){
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(examineResult))
		{
			examineState = "5";
		}
		else
		{
			examineState = "4";
		}
		TExportApply tExportApply = this.getExportApply(businessRecordId);
		tExportApply.setExamineState(examineState);
		tExportApply.setWriteTo(writeTo);
		tExportApply.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveExportApply(tExportApply);		
	}
	/**
	 * 流程回调更新出货通知单状态
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(examineResult))
		{
			examineState = "5";
		}
		else
		{
			examineState = "4";
		}
		TExportApply tExportApply = this.getExportApply(businessRecordId);
		tExportApply.setExamineState(examineState);
		tExportApply.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveExportApply(tExportApply);
	}
	
	/**查询库存数量
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public double queryInventoryNum(String materialCode,String warehouse,String batchNo)
	{
		return exportApplyJdbcDao.queryInventoryNum(materialCode,warehouse,batchNo);
	}
}
