package com.infolion.xdss3.billpurchased.workflow;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billpurchased.dao.BillPurchasedHibernateDao;
import com.infolion.xdss3.billpurchased.dao.BillPurchasedJdbcDao;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.billpurchased.service.BillPurchasedService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;

/**
 * 判断是否还款完成
 * @author HONG
 */
public class BillpurchasedApprovalJudge implements DecisionHandler {
    
    public String decide(ExecutionContext context) throws Exception {
	    String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
	    BillPurchasedService billPurchasedService = (BillPurchasedService)EasyApplicationContextUtils.getBeanByName("billPurchasedService");
	    BillPurchased billPurchased = billPurchasedService._get(businessId);
	    
	    BigDecimal docryAmount = new BigDecimal(0);
	    for ( BillPurBankItem bankItem : billPurchased.getBillPurBankItem()) {
	        docryAmount = docryAmount.add(bankItem.getApplyamount()); //实际押汇金额
	    }
	    
	    BigDecimal reDocryAmount = new BigDecimal(0);
	    for ( BillPurReBankItem reBankItem : billPurchased.getBillPurReBankItem()) {
	        reDocryAmount = reDocryAmount.add(reBankItem.getApplyamount()); //实际还押汇金额
	    }
	    
	    
	    BillPurchasedJdbcDao jdbcdao = (BillPurchasedJdbcDao)EasyApplicationContextUtils.getBeanByType(BillPurchasedJdbcDao.class);
	    jdbcdao.updateYbillPurRebank(billPurchased.getBillpurid());
	    
	    //财务部还押汇结束流程是否结束为：还押汇金额大于等于实际押汇金额， 未结束则从业务申请还押汇节点开始循环。
	    if (reDocryAmount.compareTo(docryAmount)>-1){
	        return "全额还押汇";
	    } else {
	        billPurchased.setReaccountdate(" ");
	        billPurchased.setRevoucherdate(" ");
	        billPurchased.setRetext(" ");
	        
//	        SettleSubject  settleSubject = billPurchased.getSettleSubject();
//	        if (settleSubject!=null) {
//    	        BeanUtils.copyProperties(settleSubject, new SettleSubject());
//    	        billPurchased.setSettleSubject(new SettleSubject());
//	        }
//	        FundFlow fundFlow = billPurchased.getFundFlow();
//	        if (fundFlow!=null) {
//    	        BeanUtils.copyProperties(fundFlow, new FundFlow());
//    	        billPurchased.setFundFlow(fundFlow);
//	        }
	        BillPurchasedHibernateDao billPurchasedHibernateDao = (BillPurchasedHibernateDao)EasyApplicationContextUtils.getBeanByName("billPurchasedHibernateDao");
	        billPurchasedHibernateDao.saveOrUpdate(billPurchased);
	        return "部分还押汇";
	    }
	}
}
