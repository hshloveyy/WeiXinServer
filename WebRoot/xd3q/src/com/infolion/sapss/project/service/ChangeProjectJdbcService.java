/*
 * @(#)ChangeProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.project.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.project.dao.ChangeProjectJdbcDao;
import com.infolion.sapss.project.dao.ProjectInfoJdbcDao;
import com.infolion.sapss.project.domain.TProjectChange;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class ChangeProjectJdbcService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private ChangeProjectJdbcDao changeProjectJdbcDao;
	@Autowired
	private ProjectInfoJdbcDao projectInfoJdbcDao;
	
	public void setProjectInfoJdbcDao(ProjectInfoJdbcDao projectInfoJdbcDao) {
		this.projectInfoJdbcDao = projectInfoJdbcDao;
	}

	public void setChangeProjectJdbcDao(ChangeProjectJdbcDao changeProjectJdbcDao) {
		this.changeProjectJdbcDao = changeProjectJdbcDao;
	}
	/**
	 * 更新业务记录状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String sapOrderNo) {
		String projectId = this.changeProjectJdbcDao.getProjectId(businessRecordId);
		this.changeProjectJdbcDao.workflowUpdateState(businessRecordId,examineResult);
		this.projectInfoJdbcDao.workflowChangeUpdateState(projectId, examineResult);
		if(ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)){
			changeProjectJdbcDao.copyFile(projectId, businessRecordId);
		}
	}
	/**
	 * 删除变更信息
	 * @param changeId
	 * @return
	 */
	public int deleteChange(String changeId) {
		return this.changeProjectJdbcDao.deleteChange(changeId);
	}
	/**
	 * 更新变更信息
	 * @param tc
	 */
	public int updateChange(TProjectChange tc) {
		
		return this.changeProjectJdbcDao.updateChange(tc);
	}
	/**
	 * 提交工作流
	 * @param taskId
	 * @param change
	 */
	public void submitWorkflow(String taskId, TProjectChange change,TProjectInfo info) {
		Map map = new HashMap();
		BigDecimal bd = new BigDecimal(info.getSum());
		if(StringUtils.isBlank(change.getAmount())||StringUtils.isBlank(change.getCurrency()) || "null".equals(change.getCurrency())){
			map.put("_workflow_currency",info.getCurrency());
			if(!"USD".equals(info.getCurrency()) && !"CNY".equals(info.getCurrency())){
				BigDecimal rate = info.getExchangeRate();
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}
			map.put("_workflow_total_value",bd.toString());
		}else if(StringUtils.isNotBlank(change.getAmount())&& StringUtils.isNotBlank(change.getCurrency())&& !"null".equals(change.getCurrency())){
			bd = new BigDecimal(Double.valueOf(change.getAmount()));
			map.put("_workflow_currency",change.getCurrency());
			if(!"USD".equals(change.getCurrency()) && !"CNY".equals(change.getCurrency())){
				BigDecimal rate = info.getExchangeRate();
				if(rate==null){
					rate = new BigDecimal(1);
				}
				bd = bd.multiply(rate);
			}
			map.put("_workflow_total_value",bd.toString());
			
		}
		
		//判断关联单位:
		boolean customer = isRelateCompany(info.getCustomerId());
		boolean provider = isRelateCompany(info.getProviderId());
		String yes_no="no";
		if(customer || provider)
			yes_no="yes";
		map.put("_workflow_is_unit",yes_no);
		map.put("_workflow_dept_code",info.getDeptId());
		change.setWorkflowUserDefineTaskVariable(map);
		change.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("project_modify_v1"));
			if(null==taskId||"".equals(taskId)){
				WorkflowService.createAndSignalProcessInstance(change, change.getChangeId());
				this.changeProjectJdbcDao.submitUpdateState(change.getChangeId(),"2");
				this.projectInfoJdbcDao.submitUpdateState(info.getProjectId(),"6");
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
				}else if(id.charAt(pos)=='2'){// || id.charAt(pos)=='1'内部交易不经过证券部
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
	public int saveChanged(TProjectChange tpc) {
		return this.changeProjectJdbcDao.saveChanged(tpc);
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String projectId,String changeNo) {
		return this.changeProjectJdbcDao.getChangeNo(projectId,changeNo);
	}

}
