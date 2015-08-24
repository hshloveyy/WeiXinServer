package com.infolion.sapss.workflow.mainData;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.mainData.domain.TSuppliers;
import com.infolion.sapss.mainData.service.SupplyHibernateService;

public class SupplierYearCheckDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext arg0) throws Exception {
		//外贸片公司，且是修改客户
		//新增或非外贸公司修改
		//
		String SUPPLIER_ID =(String)arg0.getVariable("SUPPLIER_ID");
		SupplyHibernateService service =(SupplyHibernateService)EasyApplicationContextUtils.getBeanByType(SupplyHibernateService.class);
		TSuppliers g =  service.find(SUPPLIER_ID);
		if("update2".equals(g.getAddType())){
			return "是";
		}
		return "否";
	}


}
