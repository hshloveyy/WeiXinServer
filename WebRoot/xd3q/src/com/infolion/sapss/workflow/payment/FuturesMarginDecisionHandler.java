/*
 * @(#)BAJudgeDecisionHandler.java
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
 * 是否银行承兑汇票
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
public class FuturesMarginDecisionHandler implements DecisionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713447794416790203L;

	public String decide(ExecutionContext context) throws Exception {
		Object obj = context.getVariable("paymentInnerInfo");
		if(obj!=null&&((String)obj).length()>0){
			String payMethod = (String)context.getVariable("payMethod");
			String payType = (String)context.getVariable("payType");
			if("2".equals(payMethod)&&"2".equals(payType)){
				return "是";
			}
		}
		return "否";
	}

}
