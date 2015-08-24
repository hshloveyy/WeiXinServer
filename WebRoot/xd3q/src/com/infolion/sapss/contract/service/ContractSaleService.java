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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.contract.dao.ContractJdbcDao;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class ContractSaleService extends BaseJdbcService implements ProcessCallBack {
	@Autowired
	private ContractJdbcDao contractJdbcDao;
	@Autowired
	private SysDeptJdbcDao tSysDeptJdbcDao;
	
	
	public void setContractJdbcDao(ContractJdbcDao contractJdbcDao) {
		this.contractJdbcDao = contractJdbcDao;
	}

	public void setTSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		tSysDeptJdbcDao = sysDeptJdbcDao;
	}


	public void updateSalesMaterielCaseInfo(String strMaterielCaseId,
			String strColName,
			String strColValue){
		contractJdbcDao.updateSalesMaterielCaseInfo(strMaterielCaseId, strColName, strColValue);
	}
	
	public void updateAgentMtCaseInfo(String strMaterielCaseId,
			String strColName,
			String strColValue){
		contractJdbcDao.updateAgentMtCaseInfo(strMaterielCaseId, strColName, strColValue);
	}	
	
	public void deleteSalesMaterielCase(String IdList){
		String[] toIdList = IdList.split("\\|");
		for (int i=0;i<toIdList.length;i++){
			contractJdbcDao.deleteSalesMaterielCase(toIdList[i]);
		}
	}
	
	public void deleteSalesMateriel(String IdList){
		String[] toIdList = IdList.split("\\|");
		for (int i=0;i<toIdList.length;i++){
			contractJdbcDao.deleteSalesMateriel(toIdList[i]);
		}
	}
	
	public void updateSalesMateriel(String strSalesRowsId,
			String strColName,
			String strColValue){
		contractJdbcDao.updateSalesMateriel(strSalesRowsId, strColName, strColValue);
	}
	
	public void updateAgentMateriel(String strSalesRowsId,
			String strColName,
			String strColValue){
		contractJdbcDao.updateAgentMateriel(strSalesRowsId, strColName, strColValue);
	}	
	
	public void deleteSalesContract(String strContractSalesId){
		contractJdbcDao.deleteSalesContract(strContractSalesId);
	}
	
	public String getSapRowNo(String strContractType,String strContractPurchaseId){
		return contractJdbcDao.getSapRowNo(strContractType, strContractPurchaseId);
	}
	
	public void submitSalesInfo(String taskId,
			TContractSalesInfo info){
		Map parameters = new HashMap();
		parameters.put("_workflow_total_value", Double.valueOf(info.getOrderTotal()));
		String deptId = info.getDeptId();
		SysDept sysDept = tSysDeptJdbcDao.queryDeptById(deptId);
		parameters.put("_workflow_dept_code", sysDept.getDeptcode());
		info.setWorkflowUserDefineTaskVariable(parameters);
		info.setWorkflowBusinessNote("合同编号："+info.getContractNo()+",合同名称:"+info.getContractName());		
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(info, info.getContractSalesId());
		}else{
			WorkflowService.signalProcessInstance(info,info.getContractSalesId(), null);
		}
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		contractJdbcDao.updateSalesWorkflowResult(businessRecordId,examineResult,sapOrderNo);	
	}	
	
}
