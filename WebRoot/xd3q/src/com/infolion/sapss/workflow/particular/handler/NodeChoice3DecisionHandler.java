/*
 * @(#)NodeChoice3DecisionHandler.java
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

public class NodeChoice3DecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service = (ParticularWorkflowService)EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		String nodeName = service.getNearestNoPassNodeName(particularId);
		if(nodeName!=null && !"".equals(nodeName)){
			if(nodeName.indexOf("信达安副总")!=-1)
				return "由信达安副总退回，提交副总";
			else if(nodeName.indexOf("股份公司副总")!=-1)
				return "由副总退回，提交股份公司常务副总";
			else if(nodeName.indexOf("信达安总经理")!=-1)
				return "由股份公司常务副总退回，提交股份总经理";
			else if(nodeName.indexOf("信达安副董事长")!=-1)
				return "由总经理退回，提交董事长";
		}
		return null;
	}
}
