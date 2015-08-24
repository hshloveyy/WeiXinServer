/*
 * @(#)PickListInfoProcessService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 10, 2009
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.service.PickListInfoHibernateService;
import com.infolion.sapss.project.service.ProjectJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class PickListInfoProcessService extends BaseHibernateService
{

	private PickListInfoHibernateService pickListInfoHibernateService;

	public void processSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "更新到单状态为有效");
		PickListInfoHibernateService pickListInfoHibernateService = (PickListInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("pickListInfoHibernateService");
		pickListInfoHibernateService.updatePickListInfoState(businessRecordId,
				"3");
	}

	/**
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "更新到单状态为作废");
		PickListInfoHibernateService pickListInfoHibernateService = (PickListInfoHibernateService) EasyApplicationContextUtils
				.getBeanByName("pickListInfoHibernateService");
		pickListInfoHibernateService.updatePickListInfoState(businessRecordId,
				"4");
	}

}
