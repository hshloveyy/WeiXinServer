/*
 * @(#)HomePayTypeJudge.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-8-13
 *  描　述：创建
 */

package com.infolion.xdss3.payment.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.service.HomePaymentService;

public class HomePayTypeJudge implements DecisionHandler
{

	public String decide(ExecutionContext context) throws Exception
	{
		// TODO Auto-generated method stub
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		HomePaymentService homePaymentService = (HomePaymentService)EasyApplicationContextUtils.getBeanByName("homePaymentService");
		HomePayment homePayment = homePaymentService._get(businessId);
		
		String strRet = "";	// 流程路径名称
		
		if(homePayment.getPaymenttype().equals("06") || homePayment.getPaymenttype().equals("07")){
			strRet = "银行承兑汇票或国内信用证";
		/*}else if(homePayment.getPaymenttype().equals("09")){
			strRet = "背书";//背书取消*/
		}else{
			strRet = "普通付款";
		}

		return strRet;
	}

}
