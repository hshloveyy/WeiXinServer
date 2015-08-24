/*
 * @(#)ProcessFaileActionHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.mainData;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.mainData.service.MaterialHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.workflow.export.ExportProcessService;

/**
 * 
 * <pre>
 * 物料主数据审批通过动作
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张小军
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SupplySuccessActionHandler implements ActionHandler
{
	public void execute(ExecutionContext context) throws Exception
	{
		String businessRecordId = (String) context.getContextInstance()
		.getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		String type = (String) context.getVariable("type");
		long processId = context.getProcessInstance().getId();
		SupplyProcessService supplyProcessService = (SupplyProcessService) EasyApplicationContextUtils
				.getBeanByName("supplyProcessService");
		
		supplyProcessService.processBaleLoanSucessful(businessRecordId,
				processId);
	

		context.getProcessInstance().signal();
	}

}
