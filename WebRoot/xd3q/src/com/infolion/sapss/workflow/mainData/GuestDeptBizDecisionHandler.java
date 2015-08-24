/*
 * @(#)GusetDeptBizDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-7-14
 *  描　述：创建
 */

package com.infolion.sapss.workflow.mainData;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.service.GuestHibernateService;

public class GuestDeptBizDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext arg0) throws Exception {
		//外贸片公司，且是修改客户
		//新增或非外贸公司修改
		//
		String GUEST_ID =(String)arg0.getVariable("GUEST_ID");
		GuestHibernateService service =(GuestHibernateService)EasyApplicationContextUtils.getBeanByType(GuestHibernateService.class);
		TGuest g =  service.find(GUEST_ID);
		SysDeptService ds = (SysDeptService)EasyApplicationContextUtils.getBeanByType(SysDeptService.class);
		SysDept dept = ds.queryDeptById(g.getDeptId());
//		if("add".equals(g.getAddType()) || (!WorkflowUtils.isForeignTradeDept(dept.getDeptcode())&&"update".equals(g.getAddType())) ){
//			return "新增或非外贸公司修改";
//		}
//		if(WorkflowUtils.isForeignTradeDept(dept.getDeptcode()) && "update".equals(g.getAddType())){
//			return "外贸片公司,且是修改数据";
//		}
//		if((WorkflowUtils.isForeignTradeDept(dept.getDeptcode())&&"add".equals(g.getAddType())) ||
//				dept.getDeptname().indexOf("财务")!=-1 ){
//			return "财务部门申请或外贸片公司且是新增";
//		}
//		
//		return "新增或非外贸公司修改";
		if("业务".equals(g.getType())){
			return "业务类型客户";
		}
		if("物流".equals(g.getType())){
			return "物流类型客户";
		}
		return "业务类型客户";
	}

}
