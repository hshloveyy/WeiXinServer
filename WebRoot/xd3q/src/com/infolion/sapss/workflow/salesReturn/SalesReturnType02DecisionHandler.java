/*
 * @(#)ScrappedAmount100DecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.salesReturn;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.scrapped.dao.ScrappedJdbcDao;
import com.infolion.sapss.scrapped.domain.TScrapped;

public class SalesReturnType02DecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext txt) throws Exception {
		Object obj = txt.getVariable("returnType");
		String returnType ="0";
		if(obj!=null){
			returnType= (String)obj;
			if ("0".equals(returnType)){
				return "否";
			} else {
				return "是";
			}
		}
		return null;
	}

}
