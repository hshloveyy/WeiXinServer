/*
 * @(#)SpecialAccountDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-22
 *  描　述：创建
 */

package com.infolion.sapss.workflow.payment;


import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.sys.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.payment.service.PaymentImportsInfoHibernateService;

public class SpecialAccountDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 4217025300799034115L;

	public String decide(ExecutionContext txt) throws Exception {
		Object payAccount = txt.getVariable("payAccount");
		String acc="";
		//进口付款
		if(payAccount==null){
			return "正常帐号";
		}else{
			acc = (String)payAccount;

			if(PaymentContants.BANK_DEPT_ID_METAL.equals(acc.replaceAll(" ", "").replaceAll(",", ""))){
				return "上海信达诺帐号";
			}else
				return "正常帐号";
		}
	}
}
