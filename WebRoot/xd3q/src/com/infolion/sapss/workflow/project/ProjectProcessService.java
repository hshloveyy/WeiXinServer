/*
 * @(#)ProjectProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.project;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.project.service.ProjectJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;
/**
 * 
 * <pre>立项审批流程推进回调服务</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ProjectProcessService extends BaseHibernateService {
	/**
	 * 流程通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"立项申请通过");
		ProjectJdbcService project = (ProjectJdbcService) EasyApplicationContextUtils
				.getBeanByName("projectJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) project;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}
	/**
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"立项申请未通过");
		ProjectJdbcService project = (ProjectJdbcService) EasyApplicationContextUtils
				.getBeanByName("projectJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) project;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
	/**
	 * 立项变更流程通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processChangeSucessful(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"立项变更申请通过");
		ChangeProjectJdbcService project = (ChangeProjectJdbcService) EasyApplicationContextUtils
				.getBeanByName("changeProjectJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) project;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}
	/**
	 * 立项变更流程不通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processChangeFailure(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"立项变更申请未通过");
		ChangeProjectJdbcService project = (ChangeProjectJdbcService) EasyApplicationContextUtils
				.getBeanByName("changeProjectJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) project;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
