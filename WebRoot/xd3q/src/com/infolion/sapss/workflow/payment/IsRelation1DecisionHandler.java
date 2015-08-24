package com.infolion.sapss.workflow.payment;

import java.util.Collection;
import java.util.Map;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.sys.cache.SysCachePoolUtils;

public class IsRelation1DecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext txt) throws Exception {
		Map<String, String> keys = SysCachePoolUtils.getDictDataMap("BM_RELATION_BUSINESS_DIC");
		//System.out.println(keys);
		Object value = txt.getVariable("unit_name");
		if(value!=null){
			for(String key:keys.values()){
				if(value.toString().indexOf(key)>-1) return "关联交易";
			}
		}
		return "非关联交易";
	}


}