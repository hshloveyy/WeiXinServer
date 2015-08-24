/*
 * @(#)NodeChoiceDecisionHandler.java
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
 * 工作处理器:越级审批
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
public class NodeChoiceDecisionHandler implements DecisionHandler{
	private static final long serialVersionUID = -3903979789097678893L;

	public String decide(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service = (ParticularWorkflowService)EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		String nodeName = service.getNearestNoPassNodeName(particularId);
		if(nodeName!=null && !"".equals(nodeName)){
//			if(nodeName.indexOf("财务经理")!=-1)
//				return "由财务退回,提交贸管";
//			else if(nodeName.indexOf("贸管经理")!=-1)
//				return "由贸管退回，提交副总";
//			else
			if(nodeName.indexOf("股份公司副总")!=-1)
				return "由副总退回，提交股份公司常务副总";
			else if(nodeName.indexOf("股份公司常务副总")!=-1)
				return "由股份公司常务副总退回，提交股份总经理";
			else if(nodeName.indexOf("总经理")!=-1)
				return "由总经理退回，提交董事长";
		}
		return null;
	}

}
