/*
 * @(#)CIJudgeDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * 是否即期付款
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CIJudgeDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext ec) throws Exception {
		//dp,即期信用证 =即期付款
		Object obj = ec.getVariable("_current_issue");
		if(obj!=null){
			String str = (String)obj;
			str = str.toLowerCase();
			if("dp".equals(str) || "sight_lc".equals(str)){
				return "是";
			}
		}
		return "否";
	}

}
