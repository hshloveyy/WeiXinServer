/*
 * @(#)AccountDecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.handler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.payment.domain.TPaymentInnerInfo;
import com.infolion.sapss.payment.service.PaymentImportsInfoHibernateService;
import com.infolion.sapss.payment.service.PaymentInnerInfoHibernateService;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

public class AccountDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service = (ParticularWorkflowService)EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		TParticularWorkflow flow = service.load(particularId);
		double account=0;
		double rate =0;
		String currency="";
		String module = flow.getModuleName();
		if(module.indexOf("进口货物付款")!=-1){
			PaymentImportsInfoHibernateService p = (PaymentImportsInfoHibernateService)EasyApplicationContextUtils.getBeanByType(PaymentImportsInfoHibernateService.class);
			TPaymentImportsInfo info = p.findPaymentImportInfo(flow.getOriginalBizId());
			account = Double.valueOf(info.getPayValue()).doubleValue();
			PaymentImportsInfoJdbcDao dao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.getBeanByType(PaymentImportsInfoJdbcDao.class);
			rate = Double.valueOf(dao.getCurrentRate(info.getCurrency())).doubleValue();
			currency = info.getCurrency();
		}else if(module.indexOf("内贸付款")!=-1){
			PaymentInnerInfoHibernateService p = (PaymentInnerInfoHibernateService)EasyApplicationContextUtils.getBeanByType(PaymentInnerInfoHibernateService.class);
			TPaymentInnerInfo info = p.getPaymentInnerInfo(flow.getOriginalBizId());
			account = Double.valueOf(info.getPayValue()).doubleValue();
			PaymentImportsInfoJdbcDao dao = (PaymentImportsInfoJdbcDao)EasyApplicationContextUtils.getBeanByType(PaymentImportsInfoJdbcDao.class);
			rate = Double.valueOf(dao.getCurrentRate(info.getCurrency())).doubleValue();
			currency = info.getCurrency();
		}
		if("CNY".equals(currency) && (account/10000)>700)
			return "金额大于100万美金或700万人民币";
		else if("USD".equals(currency) && (account/10000)>100)
			return "金额大于100万美金或700万人民币";
		else if(account * rate/10000 >700){
					return "金额大于100万美金或700万人民币";
		}
		return "金额小于100万美金或700万人民币";
	}
}
