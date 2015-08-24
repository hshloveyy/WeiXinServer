/*
 * @(#)ContractService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-2-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractJdbcDao;
import com.infolion.sapss.contract.dao.SysDeptHibernateDao;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.project.dao.ContractAccountingJdbcDao;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.web.ProjectDetailVO;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class ContractService extends BaseJdbcService implements ProcessCallBack {
	@Autowired
	private ContractJdbcDao contractJdbcDao;
	@Autowired
	private SysDeptJdbcDao tSysDeptJdbcDao;
	@Autowired
	SysDeptHibernateDao sysDeptHibernateDao;
	
	@Autowired
	ContractAccountingJdbcDao contractAccountingJdbcDao;
	
	
	/**
	 * @param contractAccountingJdbcDao the contractAccountingJdbcDao to set
	 */
	public void setContractAccountingJdbcDao(
			ContractAccountingJdbcDao contractAccountingJdbcDao) {
		this.contractAccountingJdbcDao = contractAccountingJdbcDao;
	}

	public void setContractJdbcDao(ContractJdbcDao contractJdbcDao) {
		this.contractJdbcDao = contractJdbcDao;
	}

	public void setTSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		tSysDeptJdbcDao = sysDeptJdbcDao;
	}

	public void setSysDeptHibernateDao(SysDeptHibernateDao sysDeptHibernateDao) {
		this.sysDeptHibernateDao = sysDeptHibernateDao;
	}

	public void addContractGroup(TContractGroup tContractGroup) {
		contractJdbcDao.addContractGroup(tContractGroup);
	}

	public int deleteContractGroup(String strContractGroupId) {
		return contractJdbcDao.deleteContractGroup(strContractGroupId);
	}

	public String getContractGroupCount(String strProjectId) {
		return contractJdbcDao.getContractGroupCount(strProjectId);
	}

	public String queryContractGroupInfoByPurchaseId(String strGroupId) {
		return contractJdbcDao.queryContractGroupInfoByPurchaseId(strGroupId);
	}
	
	public TContractGroup queryContractGroupByPurchaseId(String strGroupId) {
		return contractJdbcDao.queryContractGroupByPurchaseId(strGroupId);
	}

	public String getContractNo(String strContractGroupId,
			String strContractGroupNo, String strContractType,
			String strActionType, String strType) {
		return contractJdbcDao.getContractNo(strContractGroupId,
				strContractGroupNo, strContractType, strActionType,
				strType);
	}

	public int updatePurchaseContract(
			TContractPurchaseInfo tContractPurchaseInfo) {
		return contractJdbcDao.updatePurchaseContract(tContractPurchaseInfo);
		// contractJdbcDao.updatePurchaserMaterielPeinh(tContractPurchaseInfo.getContractPurchaseId(),
		// tContractPurchaseInfo.getEkkoWaers());
	}

	public int deletePurchaseContract(String strContractPurchaseId) {
		return contractJdbcDao.deletePurchaseContract(strContractPurchaseId);
	}

	public void deletePurchaseMaterielInfo(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			contractJdbcDao.deletePurchaseMaterielInfo(toIdList[i]);
		}
	}

	public void deletePurchaseMaterielCase(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			contractJdbcDao.deletePurchaseMaterielCase(toIdList[i]);
		}
	}

	public void deletePurchaseBom(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			contractJdbcDao.deletePurchaseBom(toIdList[i]);
		}
	}

	public TProjectInfo queryProjectInfo(String strProjectId) {
		return contractJdbcDao.queryProjectInfo(strProjectId);
	}

	public void updateMaterielCase(String strMaterielCaseId, String strColName,
			String strColValue) {
		contractJdbcDao.updateMaterielCase(strMaterielCaseId, strColName,
				strColValue);
	}

	public void updateBomInfo(String strBomId, String strColName,
			String strColValue) {
		contractJdbcDao.updateBomInfo(strBomId, strColName, strColValue);
	}

	public void updatePurchaseMaterielInfo(String strPurchaseRowsId,
			String strColName, String strColValue) {
		contractJdbcDao.updatePurchaseMaterielInfo(strPurchaseRowsId,
				strColName, strColValue);
	}

	public void updateSalesMaterielCaseInfo(String strMaterielCaseId,
			String strColName, String strColValue) {
		contractJdbcDao.updateSalesMaterielCaseInfo(strMaterielCaseId,
				strColName, strColValue);
	}

	public void updateAgentMtCaseInfo(String strMaterielCaseId,
			String strColName, String strColValue) {
		contractJdbcDao.updateAgentMtCaseInfo(strMaterielCaseId, strColName,
				strColValue);
	}

	public void deleteSalesMaterielCase(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			contractJdbcDao.deleteSalesMaterielCase(toIdList[i]);
		}
	}

	public void deleteSalesMateriel(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			contractJdbcDao.deleteSalesMateriel(toIdList[i]);
		}
	}

	public void updateSalesMateriel(String strSalesRowsId, String strColName,
			String strColValue) {
		contractJdbcDao.updateSalesMateriel(strSalesRowsId, strColName,
				strColValue);
	}

	public void updateAgentMateriel(String strSalesRowsId, String strColName,
			String strColValue) {
		contractJdbcDao.updateAgentMateriel(strSalesRowsId, strColName,
				strColValue);
	}

	public void deleteSalesContract(String strContractSalesId) {
		contractJdbcDao.deleteSalesContract(strContractSalesId);
	}

	// public void updateContractSalesInfo(TContractSalesInfo
	// tContractSalesInfo){
	// contractJdbcDao.updateContractSalesInfo(tContractSalesInfo);
	// }

	public String getSapRowNo(String strContractType,
			String strContractPurchaseId) {
		return contractJdbcDao.getSapRowNo(strContractType,
				strContractPurchaseId);
	}

	public void updatePurchaserOrderState(String strContractPrichaserId,
			String strOrderState) {
		contractJdbcDao.updatePurchaserOrderState(strContractPrichaserId,
				strOrderState);
	}

	public void updateSaleOrderState(String strContractPrichaserId,
			String strOrderState) {
		contractJdbcDao.updateSalesOrderState(strContractPrichaserId,
				strOrderState);
	}

	public String getDeptCode(String deptId) {
		SysDept sysDept = tSysDeptJdbcDao.queryDeptById(deptId);
		return sysDept.getDeptcode();
	}

	public void submitPurchaseInfo(String taskId, TContractPurchaseInfo info) {
		Map parameters = new HashMap();
		String total = info.getTotal();
		if(total==null || "".equals(total)){
			total="0";
		}
		BigDecimal bd = new BigDecimal(Double.valueOf(total));
		//币别
		parameters.put("_workflow_currency",info.getEkkoWaers());//ekkoWaers getEkkoWkurs
		/*if(!"USD".equals(info.getEkkoWaers()) && !"CNY".equals(info.getEkkoWaers())){
			BigDecimal rate = new BigDecimal(info.getEkkoWkurs());
			if(rate==null){
				rate = new BigDecimal(1);
			}
			bd = bd.multiply(rate);
		}*/
		parameters.put("_workflow_total_value", bd.divide(new BigDecimal(10000),2,RoundingMode.UP).toString());
		parameters.put("_trade_type", info.getTradeType());
		parameters.put("unit_no", info.getEkkoLifnr());
		// 由于页面存在错误，未得到dept_id，关闭此传参功能 by zhangchzh
		// String deptCode = contractJdbcDao.getSysDeptCode(info.getDeptId());
		// parameters.put("_workflow_dept_code",deptCode );
		info.setWorkflowUserDefineTaskVariable(parameters);
		// info.setWorkflowBusinessNote("合同编号："+info.getContractNo()+"|合同名称:"+info.getContractName());
		info.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("contract_purcharse_v1"));
		
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(info, info
					.getContractPurchaseId());
		} else {
			WorkflowService.signalProcessInstance(info, info
					.getContractPurchaseId(), null);
		}
		contractJdbcDao.updatePurchaseApplyTime(info.getContractPurchaseId());
	}

	public void submitSalesInfo(String taskId, TContractSalesInfo info) {
		info.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("contract_sales_v1"));
		Map parameters = new HashMap();
		BigDecimal bd = new BigDecimal(Double.valueOf(info.getOrderTotal()));
		parameters.put("_workflow_currency",info.getVbakWaerk());
		//非美元,人民币,要*汇率
		/*if(!"USD".equals(info.getVbakWaerk()) && !"CNY".equals(info.getVbakWaerk())){
			BigDecimal rate = info.getVbkdKurrf();
			if(rate==null){
				rate = new BigDecimal(1);
			}
			bd = bd.multiply(rate);
		}*/
		parameters.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		parameters.put("unit_no", info.getKuagvKunnr());
		parameters.put("_trade_type", info.getTradeType());

		// 由于页面存在错误，未得到dept_id，关闭此传参功能 by zhangchzh
		// String deptCode = contractJdbcDao.getSysDeptCode(info.getDeptId());
		// parameters.put("_workflow_dept_code",deptCode );
		info.setWorkflowUserDefineTaskVariable(parameters);
		// info.setWorkflowBusinessNote("合同编号："+info.getContractNo()+",合同名称:"+info.getContractName());
		
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(info, info
					.getContractSalesId());
		} else {
			WorkflowService.signalProcessInstance(info, info
					.getContractSalesId(), null);
		}
	}

	/**
	 * 判断是否允许直接写入 SAP 订单
	 * 
	 * @param taskName
	 * @param deptCode
	 * @param businessType
	 *            TODO
	 * @param leaveName
	 * @param groupName
	 * @return
	 */
	public boolean checkAllowSubmit(String taskName, String transitionName,
			String group, String deptCode, String businessType,String contractType) {
		if(!"写入SAP订单".equals(transitionName.trim()))
			return true;
		if (!checkAllowBusinessType(taskName, transitionName, group, deptCode,
				businessType,contractType)) {
			if (("部门经理确认".equals(taskName.trim())||"芜湖信达贸易总经理审核".equals(taskName.trim()))
					&& "写入SAP订单".equals(transitionName.trim())
					&& "2105".equals(deptCode)
					&& ("W22".equals(group) || "W24".equals(group))
					&&("7".equals(businessType)||"10".equals(businessType))) {
				return true;
			}
			else
				return false;
		} 
		else
			return true;
	}
	public boolean checkAllowPurcharseSubmit(String taskName, String transitionName,
			String group, String deptCode, String businessType,String contractType) {
		if(!"写入SAP订单".equals(transitionName.trim()))
			return true;
		if ("部门经理确认".equals(taskName.trim())
					&& "写入SAP订单".equals(transitionName.trim())
					&& "2105".equals(deptCode)
					&& ("W22".equals(group) || "W24".equals(group))
					&&("7".equals(businessType)||"10".equals(businessType))) {
				return true;
		}
		else return false;
	}

	public boolean checkAllowBusinessType(String taskName,
			String transitionName, String group, String deptCode,
			String businessType,String contractType) {
		// 如果是代理进出口、自营出口*+部门经理确认+写入SAP订单
		if ("salesContract".equals(contractType)&&("2".equals(businessType)||"5".equals(businessType) || "6".equals(businessType)||"12".equals(businessType))
				&& (("部门经理确认".equals(taskName.trim())||"芜湖信达贸易总经理审核".equals(taskName.trim())) && "写入SAP订单"
						.equals(transitionName.trim()))) {
			return true;
		}
		return false;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		contractJdbcDao.updatePurchaseWorkflowResult(businessRecordId,
				examineResult, sapOrderNo);
	}
	/**
	 * 计算采购合同总金额
	 * @param strContractPurchaserId
	 * @param lWhurs
	 * @return
	 */
	public String sumTotal(String strContractPurchaserId, Double lWhurs) {
		return contractJdbcDao.sumTotal(strContractPurchaserId, lWhurs);
	}

	public boolean isCompleteMaterielPurchase(String strContractPurchaseId) {
		return contractJdbcDao
				.isCompleteMaterielPurchase(strContractPurchaseId);
	}
	/**
	 * 取得合同组名称
	 * @param contractGroupId
	 * @return
	 */
	public TContractGroup getContractGroupName(String contractGroupId) {
		return contractJdbcDao.getContractGroupName(contractGroupId);
	}
	
	public Double getCurrencyRate(String currency){
		return contractJdbcDao.getCurrencyRate(currency);
	}
	
	public Double getPurcharseContractSum(String projectID,String orderStateStr){
		return contractJdbcDao.getPurcharseContractSum(projectID, orderStateStr);
	}
	
	public Double getSalesContractSum(String projectID,String orderStateStr){
		return contractJdbcDao.getSalesContractSum(projectID,orderStateStr);
	}
	
	public boolean getIsOver30WUSD(String orderTotal , String currency){
		return contractJdbcDao.getIsOver30WUSD(orderTotal,currency);
	}
	
	public Double getProjectSum(String projectID){
		return contractJdbcDao.getProjectSum(projectID);
	}
	
	public String getContractOrderState(String contractId,String orderTypeStr){
		return contractJdbcDao.getContractOrderState( contractId, orderTypeStr);
	}
	
	public boolean checkSalesBalance(TContractSalesInfo sales,Double currrencyContractSum){
		return contractJdbcDao.checkSalesBalance(sales, currrencyContractSum);
	}
	
	public boolean checkPurcharseBalance(TContractPurchaseInfo purchar,Double currrencyContractSum){
		return contractJdbcDao.checkPurcharseBalance(purchar, currrencyContractSum);
	}
	public Map<String,Object> updateContractSalesRate(String salesId,String sapOrderNo,String rate){
		return contractJdbcDao.updateContractSalesRate(salesId,sapOrderNo,rate);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-25
	 * 根据合同编号反回合同ID（用于“在收付款界面的金额分配行项双击"合同号"时，弹出合同查看窗口”的功能）
	 */
	public String getContractIdByNo(String contractNo, int type){
		
		List<Map<String,String>> list = this.contractJdbcDao.getContractIdByNo(contractNo, type);
		if(type==1){
			return list.get(0).get("CONTRACT_SALES_ID");
		}else{
			return list.get(0).get("CONTRACT_PURCHASE_ID");
		}
	}
	
	public String getSalePurGroup(String ymgroup,String orderTypeStr){
		return contractJdbcDao.getSalePurGroup(ymgroup,orderTypeStr);
	}
	
	public boolean isExistCusSupp(String deptId,String mack,String no){
		return contractJdbcDao.isExistCusSupp(deptId,mack,no);
	}

	
	
	
	
	
	/**
	 * 保存核算信息
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = true)
	public int saveOrUpdateContractAccounting(List<TProjectAccounting> list){
		return this.contractAccountingJdbcDao.saveOrUpdateContractAccounting(list);
	}
	/**
	 * 获取核算信息
	 * @param list
	 * @return
	 */
	public Map<String,TProjectAccounting> getContractAccounting(String projectId){
		return this.contractAccountingJdbcDao.getContractAccounting(projectId);
	}
	

	
	public void fileContract(String IdList,String type) {		
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			if("sale".equals(type)) contractJdbcDao.fileSales(toIdList[i]);
			if("purchase".equals(type)) contractJdbcDao.filePurchase(toIdList[i]);
		}
	}
	/**
	public List fuckDealQuery(){
		return contractJdbcDao.fuckDealQuery();
	}
	public void fuckDeal(String busid,String oldorderNO,String type,String newOrderNo){
		contractJdbcDao.fuckDeal(busid,oldorderNO,type,newOrderNo);
	}
	**/

}
