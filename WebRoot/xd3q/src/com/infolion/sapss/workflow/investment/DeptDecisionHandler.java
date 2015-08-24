/*
 * @(#)DeptDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-7-21
 *  描　述：创建
 */

package com.infolion.sapss.workflow.investment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;

public class DeptDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext arg0) throws Exception {
		String id = (String)arg0.getVariable("deptId");
		SysDeptService service =(SysDeptService)EasyApplicationContextUtils.getBeanByType(SysDeptService.class);
		SysDept dept =  service.queryDeptById(id);//投资部
		if(dept!=null && dept.getDeptname().equals("投资部")){
			dept = service.queryDeptById(dept.getPdeptid());//股份公司
			if(dept!=null && dept.getDeptname().equals("股份公司")){
				return "投资部";
			}
		}
		return "非投资部";
		//非投资部
		//投资部
	}

}
