package com.infolion.sapss.workflow.mainData;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.service.GuestHibernateService;

public class GuestYearCheckDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext arg0) throws Exception {
		//外贸片公司，且是修改客户
		//新增或非外贸公司修改
		//
		String GUEST_ID =(String)arg0.getVariable("GUEST_ID");
		GuestHibernateService service =(GuestHibernateService)EasyApplicationContextUtils.getBeanByType(GuestHibernateService.class);
		TGuest g =  service.find(GUEST_ID);

		if("update2".equals(g.getAddType())){
			return "是";
		}
		return "否";
	}

}
