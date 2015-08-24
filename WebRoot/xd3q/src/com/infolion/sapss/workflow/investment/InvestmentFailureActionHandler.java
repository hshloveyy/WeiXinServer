/*
 * @(#)InvestmentFailureActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-7-21
 *  描　述：创建
 */

package com.infolion.sapss.workflow.investment;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.investment.domain.TInvestmentInfo;
import com.infolion.sapss.investment.service.InvestmentService;

public class InvestmentFailureActionHandler implements ActionHandler {

	public void execute(ExecutionContext arg0) throws Exception {
		String infoId = (String)arg0.getVariable("infoId");
		InvestmentService service = (InvestmentService)EasyApplicationContextUtils.getBeanByType(InvestmentService.class);
		TInvestmentInfo info = new TInvestmentInfo();
		info.setInfoId(infoId);
		service.updateState(info, "4", true);
		arg0.getProcessInstance().signal();
	}

}
