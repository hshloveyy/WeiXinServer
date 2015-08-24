package com.infolion.xdss3.packingloan.workflow;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.packingloan.dao.PackingLoanHibernateDao;
import com.infolion.xdss3.packingloan.dao.PackingLoanJdbcDao;
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.service.PackingLoanService;

/**
 * 判断是否还款完成
 * @author HONG
 */
public class PackingLoanApprovalJudge implements DecisionHandler {
    
    public String decide(ExecutionContext context) throws Exception {
	    String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
	    PackingLoanService packingLoanService = (PackingLoanService)EasyApplicationContextUtils.getBeanByName("packingLoanService");
	    PackingLoan packingLoan = packingLoanService._get(businessId);
	    
	    BigDecimal docryAmount = new BigDecimal(0);
	    for ( PackingBankItem bankItem : packingLoan.getPackingBankItem()) {
	        docryAmount = docryAmount.add(bankItem.getApplyamount()); //实际押汇金额
	    }
	    
	    BigDecimal reDocryAmount = new BigDecimal(0);
	    for ( PackingReBankItem reBankItem : packingLoan.getPackingReBankItem()) {
	        reDocryAmount = reDocryAmount.add(reBankItem.getApplyamount()); //实际还押汇金额
	    }
	    
	    
	    PackingLoanJdbcDao jdbcdao = (PackingLoanJdbcDao)EasyApplicationContextUtils.getBeanByType(PackingLoanJdbcDao.class);
	    jdbcdao.updatePackingRebank(packingLoan.getPackingid());
	    
	    //财务部还押汇结束流程是否结束为：还押汇金额大于等于实际押汇金额， 未结束则从业务申请还押汇节点开始循环。
	    if (reDocryAmount.compareTo(docryAmount)>-1){
	        return "全额还打包贷款";
	    } else {
	        packingLoan.setReaccountdate(" ");
	        packingLoan.setRevoucherdate(" ");
	        packingLoan.setRetext(" ");
	        
//	        SettleSubject  settleSubject = packingLoan.getSettleSubject();
//	        if (settleSubject!=null) {
//    	        BeanUtils.copyProperties(settleSubject, new SettleSubject());
//    	        packingLoan.setSettleSubject(settleSubject);
//	        }
//	        FundFlow fundFlow = packingLoan.getFundFlow();
//	        if (fundFlow!=null) {
//    	        BeanUtils.copyProperties(fundFlow, new FundFlow());
//    	        packingLoan.setFundFlow(fundFlow);
//	        }
	        PackingLoanHibernateDao packingLoanHibernateDao = (PackingLoanHibernateDao)EasyApplicationContextUtils.getBeanByName("packingLoanHibernateDao");
	        packingLoanHibernateDao.saveOrUpdate(packingLoan);
	        return "部分还打包贷款";
	    }
	}
}
