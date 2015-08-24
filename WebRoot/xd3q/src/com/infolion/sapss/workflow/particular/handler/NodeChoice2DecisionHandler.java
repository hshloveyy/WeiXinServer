/*
 * @(#)NodeChoice4DecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.handler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

public class NodeChoice2DecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service = (ParticularWorkflowService)EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		String nodeName = service.getNearestNoPassNodeName(particularId);
		if(nodeName!=null && !"".equals(nodeName)){
			if(nodeName.indexOf("上海贸易公司常务副总审核")!=-1)
				return "由上海副总退回，提交审单员";
			else if(nodeName.indexOf("股份公司审单员")!=-1)
				return "审单员退回,提交贸管经理";
			else if(nodeName.indexOf("股份公司贸管经理")!=-1)
				return "由贸管经理退回，提交股份公司副总";
			else if(nodeName.indexOf("股份公司副总")!=-1)
				return "由副总退回，提交股份公司常务副总";
			else if(nodeName.indexOf("股份公司常务副总")!=-1)
				return "由股份公司常务副总退回，提交股份总经理";
			else if(nodeName.indexOf("总经理")!=-1)
				return "由总经理退回，提交董事长";
		}
		return null;
	}
}
