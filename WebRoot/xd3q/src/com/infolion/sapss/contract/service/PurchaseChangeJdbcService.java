/*
 * @(#)PurchaseChangeJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractJdbcDao;
import com.infolion.sapss.contract.dao.PurchaseChangeJdbcDao;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TPurchaseChange;
import com.infolion.sapss.workflow.ProcessCallBack;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class PurchaseChangeJdbcService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private PurchaseChangeJdbcDao purchaseChangeJdbcDao;
	@Autowired
	private ContractJdbcDao contractJdbcDao;
	public void setContractJdbcDao(ContractJdbcDao contractJdbcDao) {
		this.contractJdbcDao = contractJdbcDao;
	}
	
	public void copyFile(String businessRecordId){
		String purchaseId = this.purchaseChangeJdbcDao.getContractId(businessRecordId);
		this.contractJdbcDao.copyFile(purchaseId,businessRecordId);
	}
	/**
	 * 更新业务记录状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String sapOrderNo) {
		String purchaseId = this.purchaseChangeJdbcDao.getContractId(businessRecordId);
		this.purchaseChangeJdbcDao.workflowChangeUpdateState(businessRecordId,examineResult);
		this.purchaseChangeJdbcDao.workflowUpdateState(purchaseId, examineResult);
	}
	/**
	 * 删除变更信息
	 * @param changeId
	 * @return
	 */
	public int deleteChange(String changeId) {
		return this.purchaseChangeJdbcDao.deleteChange(changeId);
	}
	/**
	 * 更新变更信息
	 * @param tc
	 */
	public int updateChange(TPurchaseChange tc) {
		return this.purchaseChangeJdbcDao.updateChange(tc);
	}
	/**
	 * 提交工作流
	 * @param taskId
	 * @param change
	 */
	public void submitWorkflow(String taskId, TPurchaseChange change,TContractPurchaseInfo info,boolean isDirectWriteToSAP) {
		Map map = new HashMap();
		BigDecimal bd = new BigDecimal(Double.valueOf(info.getTotal()));
		if(StringUtils.isBlank(change.getCurrency()) || StringUtils.isBlank(change.getAmount()) ||"null".equals(change.getCurrency())){
			map.put("_workflow_currency",info.getEkkoWaers());//ekkoWaers getEkkoWkurs
			/*if(!"USD".equals(info.getEkkoWaers()) && !"CNY".equals(info.getEkkoWaers())){
				BigDecimal rate = new BigDecimal(info.getEkkoWkurs());
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}*/
			map.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		}else if(StringUtils.isNotBlank(change.getCurrency()) && StringUtils.isNotBlank(change.getAmount()) && !"null".equals(change.getCurrency())){
			bd = new BigDecimal(Double.valueOf(change.getAmount()));
			map.put("_workflow_currency",change.getCurrency());//ekkoWaers getEkkoWkurs
			/*if(!"USD".equals(change.getCurrency()) && !"CNY".equals(change.getCurrency())){
				BigDecimal rate = new BigDecimal(info.getEkkoWkurs());
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}*/
			map.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		}
		map.put("_workflow_dept_code",info.getDeptId());
		map.put("unit_no", info.getEkkoLifnr());
		map.put("_trade_type", info.getTradeType());
		change.setWorkflowUserDefineTaskVariable(map);
		change.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("contract_purcharse_modify_v1"));
			if(null==taskId||"".equals(taskId)){
				WorkflowService.createAndSignalProcessInstance(change, change.getChangeId());
				String changeState = "2";
				String contractState = "6";
				//未经审批写入SAP
				if(isDirectWriteToSAP){
					contractState = "8";//未经审批变更
				}
				
				//合同变更状态为:变更审批中
				this.purchaseChangeJdbcDao.submitUpdateState(change.getChangeId(),changeState);
				//合同状态为:变更
				this.contractJdbcDao.updatePurchaserOrderState(info.getContractPurchaseId(),contractState);
			}else{
				WorkflowService.signalProcessInstance(change, change.getChangeId(), null);
			}
	}
	/**
	 * 保存信息中心执行信息
	 * @param tpc
	 * @return
	 */
	public int saveChanged(TPurchaseChange tpc) {
		return this.purchaseChangeJdbcDao.saveChanged(tpc);
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String projectId,String changeNo) {
		return this.purchaseChangeJdbcDao.getChangeNo(projectId,changeNo);
	}

}
