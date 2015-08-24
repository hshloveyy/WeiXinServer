/*
 * @(#)ImportPaymentSquareAccountsActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2010-5-28
 *  描　述：创建
 */

package com.infolion.xdss3.collect.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.collect.service.CollectService;

/**
 * 
 * <pre>
 * 收款审批通过结清
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄虎
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CollectCompleteActionHandler implements ActionHandler
{

	public void execute(ExecutionContext context) throws Exception
	{
		String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CollectService collectService = (CollectService) EasyApplicationContextUtils.getBeanByName("collectService");
		collectService.settleCollect(businessId);
		context.getProcessInstance().signal();
	}

}
