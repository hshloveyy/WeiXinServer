/*
 * @(#)ExportNoteSucessfulActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-3-24
 *  描　述：创建
 */

package com.infolion.sapss.workflow.ship;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.contract.service.ContractSaleService;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.workflow.sales.SalesProcessService;

/**
 * 
 * <pre>
 * 更新核销单号，修改通知单状态
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
public class ExportNoteSucessfulActionHandler implements ActionHandler
{

	public void execute(ExecutionContext context) throws Exception
	{
		String businessRecordId = (String) context.getContextInstance()
				.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String writeTo = (String) context.getVariable("_workflow_writeTo_value");
		long processId = context.getProcessInstance().getId();
		ShipNotifyProcessService shipNotifyProcessService = (ShipNotifyProcessService) EasyApplicationContextUtils
				.getBeanByName("shipNotifyProcessService");
		shipNotifyProcessService.processSucessful(businessRecordId, processId,writeTo);
		context.getProcessInstance().signal();

	}

}
