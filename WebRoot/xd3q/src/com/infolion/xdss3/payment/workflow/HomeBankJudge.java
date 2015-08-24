/*
 * @(#)HomeBankJudge.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-8-13
 *  描　述：创建
 */

package com.infolion.xdss3.payment.workflow;

import java.util.Iterator;
import java.util.Set;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.service.HomePaymentService;

public class HomeBankJudge implements DecisionHandler
{

	public String decide(ExecutionContext context) throws Exception
	{
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		HomePaymentService homePaymentService = (HomePaymentService)EasyApplicationContextUtils.getBeanByName("homePaymentService");
		HomePayment homePayment = homePaymentService._get(businessId);

		String strRet = "普通帐号";
		
		Set<HomePayBankItem> homePayBankItems = homePayment.getHomePayBankItem();
		if(null != homePayBankItems){
			Iterator<HomePayBankItem> iterator = homePayBankItems.iterator();
			while(iterator.hasNext()){
				HomePayBankItem homePayBankItem = iterator.next();
				if(homePayBankItem.getPaybankaccount().equals("310066399018010033466")){
					strRet = "上海信达诺帐号";
					break;
				}
			}
		}
		return strRet;
	}

}
