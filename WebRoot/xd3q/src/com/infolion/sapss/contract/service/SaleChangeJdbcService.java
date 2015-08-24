/*
 * @(#)SaleChangeJdbcService.java
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
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractJdbcDao;
import com.infolion.sapss.contract.dao.SaleChangeJdbcDao;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TSalesChange;
import com.infolion.sapss.project.dao.ProjectInfoJdbcDao;
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
public class SaleChangeJdbcService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private SaleChangeJdbcDao saleChangeJdbcDao;
	public void setSaleChangeJdbcDao(SaleChangeJdbcDao saleChangeJdbcDao) {
		this.saleChangeJdbcDao = saleChangeJdbcDao;
	}
	@Autowired
	private ProjectInfoJdbcDao projectInfoJdbcDao;
	
	public void setProjectInfoJdbcDao(ProjectInfoJdbcDao projectInfoJdbcDao) {
		this.projectInfoJdbcDao = projectInfoJdbcDao;
	}
	@Autowired
	private ContractJdbcDao contractJdbcDao;
	public void setContractJdbcDao(ContractJdbcDao contractJdbcDao) {
		this.contractJdbcDao = contractJdbcDao;
	}
	
	public void copyFile(String businessRecordId){
		String projectId = this.saleChangeJdbcDao.getContractId(businessRecordId);
		contractJdbcDao.copyFile(projectId, businessRecordId);
	}
	/**
	 * 更新业务记录状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String sapOrderNo) {
		String projectId = this.saleChangeJdbcDao.getContractId(businessRecordId);
		int run = this.saleChangeJdbcDao.workflowUpdateState(projectId,examineResult);
		int run1 = this.saleChangeJdbcDao.workflowChangeUpdateState(businessRecordId, examineResult);
		if(run+run1<2){
			throw new BusinessException("更新失败!");
		}
	}
	/**
	 * 删除变更信息
	 * @param changeId
	 * @return
	 */
	public int deleteChange(String changeId) {
		return this.saleChangeJdbcDao.deleteChange(changeId);
	}
	/**
	 * 更新变更信息
	 * @param tc
	 */
	public int updateChange(TSalesChange tc) {
		
		return this.saleChangeJdbcDao.updateChange(tc);
	}
	/**
	 * 提交工作流
	 * @param taskId
	 * @param change
	 */
	public void submitWorkflow(String taskId, TSalesChange change,TContractSalesInfo info,boolean isDirectWriteToSAP) {
		Map map = new HashMap();
		BigDecimal bd = new BigDecimal(Double.valueOf(info.getOrderTotal()));
		
		if(StringUtils.isBlank(change.getCurrency()) || StringUtils.isBlank(change.getAmount())||"null".equals(change.getCurrency())){
			map.put("_workflow_currency",info.getVbakWaerk());//ekkoWaers getEkkoWkurs
			/*if(!"USD".equals(info.getVbakWaerk()) && !"CNY".equals(info.getVbakWaerk())){
				BigDecimal rate = info.getVbkdKurrf();
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}*/
			map.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		}else if(StringUtils.isNotBlank(change.getCurrency()) && StringUtils.isNotBlank(change.getAmount())&&!"null".equals(change.getCurrency())){
			bd = new BigDecimal(Double.valueOf(change.getAmount()));
			map.put("_workflow_currency",change.getCurrency());//ekkoWaers getEkkoWkurs
			/*if(!"USD".equals(change.getCurrency()) && !"CNY".equals(change.getCurrency())){
				BigDecimal rate = info.getVbkdKurrf();
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}*/
			map.put("_workflow_total_value",bd.divide(new BigDecimal(10000),2,RoundingMode.UP));
		}
		
		map.put("unit_no", info.getKuagvKunnr());
		map.put("_workflow_dept_code",info.getDeptId());
		change.setWorkflowUserDefineTaskVariable(map);
		change.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("contract_sales_modify_v1"));
			if(null==taskId||"".equals(taskId)){
				WorkflowService.createAndSignalProcessInstance(change, change.getChangeId());
				String changeState = "2";
				String contractState = "6";
				//未经审批写入SAP
				if(isDirectWriteToSAP){
					contractState = "8";//未经审批变更
				}
				//合同变更状态为:变更审批中
				this.saleChangeJdbcDao.submitUpdateState(change.getChangeId(),changeState);
				//合同状态为:变更
				this.contractJdbcDao.updateSalesOrderState(info.getContractSalesId(),contractState);
			}else{
				WorkflowService.signalProcessInstance(change, change.getChangeId(), null);
			}
	}
	/**
	 * 判断是否是关联单位
	 * @param id
	 * @return
	 */
	private boolean isRelateCompany(String id){
		boolean yes_no = false;
		if(id!=null&& !"".equals(id.trim())){
			for(int pos=0;pos<id.length();pos++){
				if(id.charAt(pos)=='0'){
					continue;
				}else if(id.charAt(pos)=='2' || id.charAt(pos)=='1'){
					yes_no = true;
					return yes_no;
				}else
					break;
			}	
		}
		return yes_no;
	}
	/**
	 * 保存信息中心执行信息
	 * @param tpc
	 * @return
	 */
	public int saveChanged(TSalesChange tpc) {
		return this.saleChangeJdbcDao.saveChanged(tpc);
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String projectId,String changeNo) {
		return this.saleChangeJdbcDao.getChangeNo(projectId,changeNo);
	}

}
