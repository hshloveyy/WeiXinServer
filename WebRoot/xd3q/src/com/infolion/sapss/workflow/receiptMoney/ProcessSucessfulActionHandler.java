/*
 * @(#)ProcessSucessfulActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-24
 *  描　述：创建
 */

package com.infolion.sapss.workflow.receiptMoney;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 进仓申请通过动作(对应 更新出仓单状态 节点)
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
public class ProcessSucessfulActionHandler implements ActionHandler {
	public void execute(ExecutionContext context) throws Exception {
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		long processId = context.getProcessInstance().getId();
		
		//
		ReceiptMoneyProcessService ReceiptMoneyProcessService = (ReceiptMoneyProcessService) EasyApplicationContextUtils
			.getBeanByName("receiptMoneyProcessService");
		ReceiptMoneyProcessService.processSucessful(businessRecordId, processId);
		
		context.getProcessInstance().signal();
	}

}
