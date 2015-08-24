/*
 * @(#)ProjectProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.credit;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.credit.service.CreditEntryHisJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 信用证开证改证审批流程推进回调服务
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class CreditEntryHisProcessService extends BaseHibernateService
{
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "信用证开证改证申请通过");
		CreditEntryHisJdbcService creditEntry = (CreditEntryHisJdbcService) EasyApplicationContextUtils.getBeanByName("creditEntryHisJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) creditEntry;
		callBack.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 流程不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "信用证开证改证申请未通过");
		CreditEntryHisJdbcService creditEntry = (CreditEntryHisJdbcService) EasyApplicationContextUtils.getBeanByName("creditEntryHisJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) creditEntry;
		callBack.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_FAILE, null);
	}
}
