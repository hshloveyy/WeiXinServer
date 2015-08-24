/*
 * @(#)ProjectProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.bill;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;

import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.bill.service.BillApplyHibernateService;

/**
 * 
 * <pre>
 * 开票申请流程推进回调服务
 * </pre>
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
public class BillProcessService extends BaseHibernateService
{
	/**
	 * 开票申请流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBillApplySucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		BillApplyHibernateService billApplyHibernateService = (BillApplyHibernateService) EasyApplicationContextUtils
			.getBeanByName("billApplyHibernateService");
		billApplyHibernateService.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 开票申请出口押汇流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBillApplyFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		BillApplyHibernateService billApplyHibernateService = (BillApplyHibernateService) EasyApplicationContextUtils
				.getBeanByName("billApplyHibernateService");
		billApplyHibernateService.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
