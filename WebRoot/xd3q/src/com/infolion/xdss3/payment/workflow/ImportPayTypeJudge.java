/*
 * @(#)ImportPayTypeJudge.java
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
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.service.ImportPaymentService;

public class ImportPayTypeJudge implements DecisionHandler
{

	public String decide(ExecutionContext context) throws Exception
	{
		// TODO Auto-generated method stub
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		ImportPaymentService importPaymentService = (ImportPaymentService)EasyApplicationContextUtils.getBeanByName("importPaymentService");
		ImportPayment importPayment = importPaymentService._get(businessId);
		
		String strRet = "即期或TT";	// 流程路径名称

		if (importPayment.getPay_type().equals("1")){
			strRet = "承兑";
		}else if(importPayment.getPay_type().equals("2")){
			strRet = "押汇";
		}else if (importPayment.getPay_type().equals("3")){
			strRet = "即期或TT";
		}

		return strRet;
	}

}
