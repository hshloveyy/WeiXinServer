package com.infolion.sapss.workflow.ship;

import org.apache.commons.lang.StringUtils;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class CommonJudge2DecisionHandler implements DecisionHandler{
	public String decide(ExecutionContext executionContext) throws Exception {
		Object isHasInv = executionContext.getVariable("isHasInv");
		Object oldShipId = executionContext.getVariable("oldShipId");

		if("1".equals(isHasInv)&&(null==oldShipId||StringUtils.isBlank((String)oldShipId))){
			return "出仓单已开票";
		}
		return "出仓单未开票";
	}
}
