/*
 * @(#)Judge2TimesNoPassDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-16
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.handler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

/**
 * 工作流处理器:判断不同意的提交审批次数是否超过2次
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class Judge2TimesNoPassDecisionHandler implements DecisionHandler{
	private static final long serialVersionUID = 8112006611035447343L;

	public String decide(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service = (ParticularWorkflowService)EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		int times = service.getNoPassTimes(particularId);
		if(times>=2)
			return "累计不通过已超过2次";
		return "累计不通过未超过2次";
	}

}
