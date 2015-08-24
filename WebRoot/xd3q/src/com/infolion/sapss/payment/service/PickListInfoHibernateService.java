/*
 * @(#)PickListHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.payment.dao.PaymentImportsMaterialHibernateDao;
import com.infolion.sapss.payment.dao.PickListInfoHibernateDao;
import com.infolion.sapss.payment.dao.PickListInfoJdbcDao;
import com.infolion.sapss.payment.domain.TPaymentImportsMaterial;
import com.infolion.sapss.payment.domain.TPickListInfo;

@Service
public class PickListInfoHibernateService extends BaseHibernateService
{
	@Autowired
	PickListInfoHibernateDao pickListInfoHibernateDao;
	@Autowired
	PickListInfoJdbcDao pickListInfoJdbcDao;
	@Autowired
	ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;
	@Autowired
	ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao;
	@Autowired
	PaymentImportsMaterialHibernateDao paymentImportsMaterialHibernateDao;
	
	@Autowired
	private SysDeptJdbcDao tSysDeptJdbcDao;
	public void setTSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		tSysDeptJdbcDao = sysDeptJdbcDao;
	}
	

	public PickListInfoHibernateDao getPickListInfoHibernateDao()
	{
		return pickListInfoHibernateDao;
	}

	public void setPickListInfoHibernateDao(
			PickListInfoHibernateDao pickListInfoHibernateDao)
	{
		this.pickListInfoHibernateDao = pickListInfoHibernateDao;
	}

	public PickListInfoJdbcDao getPickListInfoJdbcDao()
	{
		return pickListInfoJdbcDao;
	}

	public void setPickListInfoJdbcDao(PickListInfoJdbcDao pickListInfoJdbcDao)
	{
		this.pickListInfoJdbcDao = pickListInfoJdbcDao;
	}

	public void setContractPurchaseInfoHibernateDao(
			ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao)
	{
		this.contractPurchaseInfoHibernateDao = contractPurchaseInfoHibernateDao;
	}

	public void setContractPurchaseMaterielHibernateDao(
			ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao)
	{
		this.contractPurchaseMaterielHibernateDao = contractPurchaseMaterielHibernateDao;
	}

	public void setPaymentImportsMaterialHibernateDao(
			PaymentImportsMaterialHibernateDao paymentImportsMaterialHibernateDao)
	{
		this.paymentImportsMaterialHibernateDao = paymentImportsMaterialHibernateDao;
	}

	/**
	 * 保存到单信息
	 * 
	 * @param tPickListInfo
	 */
	public void savePickListInfo(TPickListInfo tPickListInfo)
	{
		this.pickListInfoHibernateDao.saveOrUpdate(tPickListInfo);
	}

	/**
	 * 通过ID获取到单信息
	 * 
	 * @param pickListId
	 * @return
	 */
	public TPickListInfo getPickListInfo(String pickListId)
	{
		return this.pickListInfoHibernateDao.get(pickListId);
	}

	/**
	 * 新增到单
	 * 
	 * @param pickListType
	 * @return
	 */
	public TPickListInfo addPickListInfo(String pickListType)
	{
		TPickListInfo tPickListInfo = new TPickListInfo();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tPickListInfo.setIsAvailable("1");
		tPickListInfo.setCreator(userContext.getSysUser().getUserId());
		tPickListInfo.setCreatorTime(DateUtils
				.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		tPickListInfo.setPickListId(CodeGenerator.getUUID());
		tPickListInfo.setTradeType(pickListType);
		tPickListInfo.setExamineState("1");
		if ("tt".equals(pickListType))
		{
			tPickListInfo.setPickListType("5");
			//tPickListInfo.setDeptId(userContext.getSysDept().getDeptid());
		}
		this.pickListInfoHibernateDao.saveOrUpdate(tPickListInfo);
		return tPickListInfo;
	}

	/**
	 * 删除到单
	 * 
	 * @param pickListId
	 */
	public void delPickListInfo(String pickListId)
	{
		TPickListInfo tPickListInfo = this.pickListInfoHibernateDao
				.get(pickListId);
		tPickListInfo.setIsAvailable("0");
		this.pickListInfoHibernateDao.saveOrUpdate(tPickListInfo);
	}

	/**
	 * 初始化到单,物料信息
	 * 
	 * @param pickListId
	 * @param contractPurchaseId
	 * @param lcNo
	 */
	public void initPickListInfoMt(String pickListId,
			String contractPurchaseId, String lcNo)
	{
		TPickListInfo tPickListInfo = this.pickListInfoHibernateDao
				.get(pickListId);
		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		tContractPurchaseInfo = contractPurchaseInfoHibernateDao
				.get(contractPurchaseId);
		tPickListInfo.setContractPurchaseId(contractPurchaseId);
		tPickListInfo.setContractNo(tContractPurchaseInfo.getContractNo());
		tPickListInfo.setSapOrderNo(tContractPurchaseInfo.getSapOrderNo());
		tPickListInfo.setLcNo(lcNo);
		tPickListInfo.setCurrencyId(tContractPurchaseInfo.getEkkoWaers());
		pickListInfoHibernateDao.save(tPickListInfo);
		// 先删除存在的物料信息
		List<TPaymentImportsMaterial> tPaymentImportsMaterialList = new ArrayList();
		tPaymentImportsMaterialList = paymentImportsMaterialHibernateDao
				.find("from TPaymentImportsMaterial a where a.pickListId ='"
						+ pickListId + "'");
		for (int i = 0; i < tPaymentImportsMaterialList.size(); i++)
		{
			TPaymentImportsMaterial tPaymentImportsMaterial = (TPaymentImportsMaterial) tPaymentImportsMaterialList
					.get(i);
			paymentImportsMaterialHibernateDao.remove(tPaymentImportsMaterial);
		}

		List<TContractPurchaseMateriel> tContractPurchaseMaterielList = new ArrayList();
		tContractPurchaseMaterielList = this.contractPurchaseMaterielHibernateDao
				.find("from TContractPurchaseMateriel a where a.contractPurchaseId ='"
						+ contractPurchaseId + "'");

		for (int i = 0; i < tContractPurchaseMaterielList.size(); i++)
		{
			TContractPurchaseMateriel tContractPurchaseMateriel = (TContractPurchaseMateriel) tContractPurchaseMaterielList
					.get(i);
			this.addMtByPurchaseMt(pickListId, tContractPurchaseMateriel);
		}

	}

	/**
	 * 增加物料信息
	 * 
	 * @param pickListId
	 * @param purchaseRowsId
	 * @return
	 */
	public TPaymentImportsMaterial addMtByCon(String pickListId,
			String purchaseRowsId)
	{
		TPaymentImportsMaterial tPaymentImportsMaterial = new TPaymentImportsMaterial();
		TContractPurchaseMateriel tContractPurchaseMateriel = this.contractPurchaseMaterielHibernateDao
				.get(purchaseRowsId);
		tPaymentImportsMaterial = this.addMtByPurchaseMt(pickListId,
				tContractPurchaseMateriel);
		return tPaymentImportsMaterial;
	}

	/**
	 * 通过采购物料增加付款物料
	 * 
	 * @param pickListId
	 * @param tContractPurchaseMateriel
	 * @return
	 */
	public TPaymentImportsMaterial addMtByPurchaseMt(String pickListId,
			TContractPurchaseMateriel tContractPurchaseMateriel)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TPaymentImportsMaterial tPaymentImportsMaterial = new TPaymentImportsMaterial();
		tPaymentImportsMaterial.setPurchaseRowsId(CodeGenerator.getUUID());
		tPaymentImportsMaterial.setPickListId(pickListId);
		tPaymentImportsMaterial.setEkpoMatnr(tContractPurchaseMateriel
				.getEkpoMatnr());
		tPaymentImportsMaterial.setEkpoTxz01(tContractPurchaseMateriel
				.getEkpoTxz01());
		tPaymentImportsMaterial.setEkpoMeins(tContractPurchaseMateriel
				.getEkpoMeins());
		// 数量
		tPaymentImportsMaterial.setEkpoMenge(Double.parseDouble("0"));
		tPaymentImportsMaterial.setEkpoNetpr(tContractPurchaseMateriel
				.getEkpoNetpr());
		tPaymentImportsMaterial.setEkpoBprme(tContractPurchaseMateriel
				.getEkpoBprme());
		tPaymentImportsMaterial.setEkpoPeinh(tContractPurchaseMateriel
				.getEkpoPeinh());
		tPaymentImportsMaterial.setEkpoWerks(tContractPurchaseMateriel
				.getEkpoWerks());
		tPaymentImportsMaterial.setEkpoWerksName(tContractPurchaseMateriel
				.getEkpoWerksName());
		tPaymentImportsMaterial.setEketEindt(tContractPurchaseMateriel
				.getEketEindt());
		tPaymentImportsMaterial.setEkpoWebre(tContractPurchaseMateriel
				.getEkpoWebre());
		tPaymentImportsMaterial.setEkpoMwskz(tContractPurchaseMateriel
				.getEkpoMwskz());
		tPaymentImportsMaterial.setEkpoMwskzName(tContractPurchaseMateriel
				.getEkpoMwskzName());
		tPaymentImportsMaterial.setFactoryLocal(tContractPurchaseMateriel
				.getFactoryLocal());
		tPaymentImportsMaterial
				.setFlowNo(tContractPurchaseMateriel.getFlowNo());
		// 金额
		tPaymentImportsMaterial.setTotalValue("0");
		tPaymentImportsMaterial.setPrice(tContractPurchaseMateriel.getPrice());
		tPaymentImportsMaterial.setTaxes(tContractPurchaseMateriel.getTaxes());
		tPaymentImportsMaterial.setTotalTaxes(tContractPurchaseMateriel
				.getTotalTaxes());
		tPaymentImportsMaterial.setMaterielUnit(tContractPurchaseMateriel
				.getMaterielUnit());
		tPaymentImportsMaterial
				.setCreator(userContext.getSysUser().getUserId());
		tPaymentImportsMaterial.setCreateTime(DateUtils
				.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		tPaymentImportsMaterial.setPurRowId(tContractPurchaseMateriel.getPurchaseRowsId());
		paymentImportsMaterialHibernateDao.save(tPaymentImportsMaterial);
		return tPaymentImportsMaterial;
	}

	/**
	 * 删除选择的物料
	 * 
	 * @param purchaseRowsId
	 */
	public void deleteMaterial(String purchaseRowsId)
	{
		paymentImportsMaterialHibernateDao
				.remove(paymentImportsMaterialHibernateDao.get(purchaseRowsId));
	}

	public String getPickListTag(String pickListType)
	{
		String tag = "";
		if (pickListType.equals("tt") || pickListType.equals("dpda")
				|| pickListType.equals("lc")|| pickListType.equals("home"))
		{
			tag = pickListType;
		}
		else if (pickListType.length() == 1)
		{
			int iPickListType = 0;
			try
			{
				iPickListType = Integer.parseInt(pickListType);
			}
			catch (Exception e)
			{

			}
			switch (iPickListType)
			{
			case 1:
			case 2:
				tag = "lc";
				break;
			case 3:
			case 4:
				tag = "dpda";
			case 5:
				tag = "tt";
			}
		}
		return tag.toUpperCase();
	}
	
	/**
	 * 保存并提交到单
	 * @param taskId
	 * @param tPickListInfo
	 */
	public void saveAndSubmitPickListInfo(String taskId,
			TPickListInfo tPickListInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tPickListInfo.setExamineState("2");
		tPickListInfo.setApplyTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "到单号："
				+ tPickListInfo.getPickListNo();
		tPickListInfo.setWorkflowBusinessNote(workflowBusinessNote);
		this.savePickListInfo(tPickListInfo);
		this.submitPickListInfo(taskId, tPickListInfo);
	}
	
	/**
	 * 提交到单
	 * @param taskId
	 * @param tPickListInfo
	 */
	public void submitPickListInfo(String taskId, TPickListInfo tPickListInfo)
	{
		Map parameters = new HashMap();
		tPickListInfo.setWorkflowUserDefineTaskVariable(parameters);
		if (null == taskId || "".equals(taskId))
		{	
			
			SysDept sysDept = tSysDeptJdbcDao.queryDeptById(tPickListInfo
					.getDeptId());
			//如果不是tt到单
			//if(!"tt".equals(tPickListInfo.getTradeType())){
				tPickListInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("import_bill_arrive_LC_DP_DA_v1",sysDept.getDeptcode()));
				WorkflowService.createAndSignalProcessInstance(tPickListInfo,tPickListInfo.getPickListId(),tPickListInfo.getDeptId());
			//}else{
				//tPickListInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("import_bill_arrive_TT_v1"));
				//WorkflowService.createAndSignalProcessInstance(tPickListInfo,tPickListInfo.getPickListId());
			//}
		}
		else
		{
			WorkflowService.signalProcessInstance(tPickListInfo, tPickListInfo.getPickListId(), null);
		}
	}
	
	public void updatePickListInfoState(String pickListId, String examineState){
		TPickListInfo tPickListInfo = this.pickListInfoHibernateDao
		.get(pickListId);		
		tPickListInfo.setExamineState(examineState);
		tPickListInfo.setApprovedTime(DateUtils
				.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));	
		this.savePickListInfo(tPickListInfo);
	}

}
