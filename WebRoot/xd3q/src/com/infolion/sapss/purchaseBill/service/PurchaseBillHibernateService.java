/*
 * @(#)PurchaseBillHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.purchaseBill.service;

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
import com.infolion.sapss.purchaseBill.dao.PurchaseBillHibernateDao;
import com.infolion.sapss.purchaseBill.dao.PurchaseBillJdbcDao;
import com.infolion.sapss.purchaseBill.dao.PurchaseBillMaterialHibernateDao;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBill;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBillMaterial;
import com.infolion.sapss.bill.domain.TBillApplyMaterial;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
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
public class PurchaseBillHibernateService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	PurchaseBillHibernateDao purchaseBillHibernateDao;
	@Autowired
	PurchaseBillMaterialHibernateDao purchaseBillMaterialHibernateDao;
	@Autowired
	PurchaseBillJdbcDao purchaseBillJdbcDao;

	@Autowired
	ContractHibernateService contractHibernateService;
	@Autowired
	ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao;

	public void setPurchaseBillHibernate(
			PurchaseBillHibernateDao purchaseBillHibernateDao) {
		this.purchaseBillHibernateDao = purchaseBillHibernateDao;
	}



	/**
	 * 保存开票申请单主信息
	 * 
	 * @param PurchaseBill
	 * @return
	 */
	public String newPurchaseBill(TPurchaseBill PurchaseBill) {
		String purchaseBillId = CodeGenerator.getUUID();
		PurchaseBill.setPurchaseBillId(purchaseBillId);

		this.purchaseBillHibernateDao.save(PurchaseBill);
		return purchaseBillId;
	}

	/**
	 * 保存收货主信息
	 * 
	 * @param projectInfo
	 * @return
	 */
	public void updatePurchaseBill(TPurchaseBill PurchaseBill) {
		this.purchaseBillHibernateDao.update(PurchaseBill);
	}

	/**
	 * 根据ID取得收货信息
	 * 
	 * @param id
	 * @return
	 */
	public TPurchaseBill getTPurchaseBill(String id) {
		return this.purchaseBillHibernateDao.get(id);
	}

	public void savePurchaseBill(TPurchaseBill tPurchaseBill) {
		purchaseBillHibernateDao.saveOrUpdate(tPurchaseBill);
	}

	public TPurchaseBill getPurchaseBill(String purchaseBillId) {
		return this.purchaseBillHibernateDao.get(purchaseBillId);
	}

	// 初始化开票申请单物料信息
	public void initPurchaseBillMt(String purchaseBillId, String contractPurchaseId) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		// 先删除存在的开票申请单物料信息
		List<TPurchaseBillMaterial> tPurchaseBillMaterialList = new ArrayList();
		tPurchaseBillMaterialList = purchaseBillMaterialHibernateDao
				.find("from TPurchaseBillMaterial a where a.purchaseBillId ='"
						+ purchaseBillId + "'");
		for (int i = 0; i < tPurchaseBillMaterialList.size(); i++) {
			TPurchaseBillMaterial tPurchaseBillMaterial = (TPurchaseBillMaterial) tPurchaseBillMaterialList
					.get(i);
			purchaseBillMaterialHibernateDao.remove(tPurchaseBillMaterial);
		}
		
		String sql = "from TContractPurchaseMateriel a where a.contractPurchaseId='"+contractPurchaseId+"'";
		
		TContractPurchaseInfo purchase = contractHibernateService.getContractPurchaseInfo(contractPurchaseId);

		// 取物料列表
		List<TContractPurchaseMateriel> tList = contractPurchaseMaterielHibernateDao.find(sql);

		double sumTotal = 0, sumQuantity = 0;

		for(TContractPurchaseMateriel r:tList){
			TPurchaseBillMaterial material = new TPurchaseBillMaterial();
			material.setPurchaseRowsId(r.getPurchaseRowsId());
			material.setPurchaseBillMateId(CodeGenerator.getUUID());
			material.setContractNo(purchase.getEkkoUnsez());
			material.setCurrency("CNY");
			material.setMaterialCode(r.getEkpoMatnr());
			material.setMaterialUnit(r.getEkpoMeins());
			material.setMaterialName(r.getEkpoTxz01());
			material.setPrice(r.getEkpoNetpr()/Double.parseDouble(r.getEkpoPeinh()));
			material.setPurchaseBillId(purchaseBillId);
			material.setQuantity(r.getEkpoMenge());
			sumQuantity+=r.getEkpoMenge();
			material.setTotalMoney(r.getEkpoMenge()*r.getEkpoNetpr()/Double.parseDouble(r.getEkpoPeinh()));
			sumTotal += material.getTotalMoney();
			purchaseBillMaterialHibernateDao.save(material);

		}

		
		// 取得开票申请单
		TPurchaseBill tPurchaseBill = purchaseBillHibernateDao.get(purchaseBillId);

		tPurchaseBill.setBillToParty(purchase.getEkkoLifnr());
		tPurchaseBill.setBillToPartyName(purchase.getEkkoLifnrName());
		tPurchaseBill.setSapOrderNo(purchase.getSapOrderNo());
		tPurchaseBill.setPriceTotal(sumTotal);
		tPurchaseBill.setQuantityTotal(sumQuantity);
		tPurchaseBill.setPaperContractNo(purchase.getEkkoUnsez());
		purchaseBillHibernateDao.saveOrUpdate(tPurchaseBill);
	}

	public static double round(double v, int scale) {
		if (scale < 0)
			return v;
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}



	public void savePurchaseBillMaterial(TPurchaseBillMaterial tPurchaseBillMaterial) {
		purchaseBillMaterialHibernateDao.saveOrUpdate(tPurchaseBillMaterial);
	}

	/**
	 * 删除开票申请单物料实体数据
	 * 
	 * @param exportMateId
	 * @return
	 */
	public void deleteMaterial(String exportMateId) {
		TPurchaseBillMaterial tPurchaseBillMaterial = purchaseBillMaterialHibernateDao
				.get(exportMateId);
		purchaseBillMaterialHibernateDao.remove(tPurchaseBillMaterial);
	}

	// 新增进仓单明细资料
	public void addDetail(String purchaseBillId, String purchaseRowsId) {


		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TContractPurchaseMateriel purchaseMateriel = this
				.contractPurchaseMaterielHibernateDao.get(purchaseRowsId);
		TContractPurchaseInfo purchase = contractHibernateService.getContractPurchaseInfo(purchaseMateriel.getContractPurchaseId());

		TPurchaseBillMaterial material = new TPurchaseBillMaterial();

		material.setPurchaseRowsId(purchaseMateriel.getPurchaseRowsId());
		material.setPurchaseBillMateId(CodeGenerator.getUUID());
		material.setContractNo(purchase.getEkkoUnsez());
		material.setCurrency("CNY");
		material.setMaterialCode(purchaseMateriel.getEkpoMatnr());
		material.setMaterialUnit(purchaseMateriel.getEkpoMeins());
		material.setMaterialName(purchaseMateriel.getEkpoTxz01());
		material.setPrice(purchaseMateriel.getEkpoNetpr()/Double.parseDouble(purchaseMateriel.getEkpoPeinh()));
		material.setPurchaseBillId(purchaseBillId);
		material.setQuantity(purchaseMateriel.getEkpoMenge());
		material.setTotalMoney(purchaseMateriel.getEkpoMenge()*purchaseMateriel.getEkpoNetpr()/Double.parseDouble(purchaseMateriel.getEkpoPeinh()));

		purchaseBillMaterialHibernateDao.save(material);
	}
	
	public void copyMaterial(String exportMateId) {
		TPurchaseBillMaterial tPurchaseBillMaterial = purchaseBillMaterialHibernateDao
				.get(exportMateId);
		TPurchaseBillMaterial tPurchaseBillMaterialNew = new TPurchaseBillMaterial();
		tPurchaseBillMaterialNew.setPurchaseBillId(tPurchaseBillMaterial.getPurchaseBillId());
		tPurchaseBillMaterialNew.setPurchaseBillMateId(CodeGenerator.getUUID());
		tPurchaseBillMaterialNew.setCmd(tPurchaseBillMaterial.getCmd());
		tPurchaseBillMaterialNew.setCurrency(tPurchaseBillMaterial.getCurrency());
		tPurchaseBillMaterialNew.setMaterialCode(tPurchaseBillMaterial.getMaterialCode());
		tPurchaseBillMaterialNew.setMaterialName(tPurchaseBillMaterial.getMaterialName());
		tPurchaseBillMaterialNew.setMaterialUnit(tPurchaseBillMaterial.getMaterialUnit());
		tPurchaseBillMaterialNew.setPrice(tPurchaseBillMaterial.getPrice());
		tPurchaseBillMaterialNew.setQuantity(tPurchaseBillMaterial.getQuantity());
		tPurchaseBillMaterialNew.setTotalMoney(tPurchaseBillMaterial.getTotalMoney());
		purchaseBillMaterialHibernateDao.save(tPurchaseBillMaterialNew);
	}

	public TPurchaseBillMaterial getPurchaseBillMaterial(String purchaseBillDetailId) {
		return this.purchaseBillMaterialHibernateDao.get(purchaseBillDetailId);
	}

	/**
	 * 保存并提交进货单
	 * 
	 * @param taskId
	 * @param tPurchaseBill
	 */
	public void submitAndSavePurchaseBill(String taskId, TPurchaseBill tPurchaseBill) {
		tPurchaseBill.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("purchase_invoice_v1"));

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tPurchaseBill.setExamineState("2");
		tPurchaseBill.setApplyTime(DateUtils.getCurrDate(2));
		this.savePurchaseBill(tPurchaseBill);

		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "采购开票申请单号："
				+ tPurchaseBill.getPurchaseBillNo();

		tPurchaseBill.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitPurchaseBill(taskId, tPurchaseBill);
	}

	/**
	 * 提交进货单
	 * 
	 * @param taskId
	 * @param tPurchaseBill
	 */
	public void submitPurchaseBill(String taskId, TPurchaseBill tPurchaseBill) {
		if (StringUtils.isBlank(taskId)) {
			WorkflowService.createAndSignalProcessInstance(tPurchaseBill,
					tPurchaseBill.getPurchaseBillId());
		} else {
			WorkflowService.signalProcessInstance(tPurchaseBill, tPurchaseBill
					.getPurchaseBillId(), null);
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
		TPurchaseBill tPurchaseBill = this.getPurchaseBill(businessRecordId);
		tPurchaseBill.setExamineState(examineState);
		tPurchaseBill.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.savePurchaseBill(tPurchaseBill);
	}

	public String verifyFilds(String taskId, TPurchaseBill tPurchaseBill) {
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId)) {
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if (taskName.indexOf("开票，并录入票号")>0) {
			String taxNo = tPurchaseBill.getTaxNo();
			if (StringUtils.isBlank(taxNo)) {
				rtn = "请录入票号！";
				return rtn;
			}
			purchaseBillHibernateDao.saveOrUpdate(tPurchaseBill);
		}
		return rtn;
	}

	/**
	 * 取的物料的汇总金额
	 * 
	 * @param purchaseBillId
	 * @return
	 */
	public List<BigDecimal> getPurchaseBillMtMoney(String purchaseBillId) {
		return this.purchaseBillJdbcDao.getPurchaseBillMtMoney(purchaseBillId);
	}
}
