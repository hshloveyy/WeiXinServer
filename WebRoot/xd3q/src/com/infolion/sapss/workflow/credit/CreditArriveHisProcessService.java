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
import com.infolion.sapss.credit.service.CreditArriveHisJdbcService;
import com.infolion.sapss.credit.service.CreditArriveJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 信用证开证审批流程推进回调服务
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
public class CreditArriveHisProcessService extends BaseHibernateService
{
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId,String creditState)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "信用证到证改证申请通过");
		CreditArriveHisJdbcService creditArrive = (CreditArriveHisJdbcService) EasyApplicationContextUtils.getBeanByName("creditArriveHisJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) creditArrive;
		callBack.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, creditState);
	}

	/**
	 * 流程不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "信用证到证改证申请未通过");
		CreditArriveHisJdbcService creditArrive = (CreditArriveHisJdbcService) EasyApplicationContextUtils.getBeanByName("creditArriveHisJdbcService");
		ProcessCallBack callBack = (ProcessCallBack) creditArrive;
		callBack.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_FAILE, null);
	}
}
