package com.infolion.xdss3.payment.workflow;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.dao.PaymentJdbcDao;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.service.ImportPaymentService;

/**
 * 判断是否还款完成
 * @author zzh
 */
public class ImportBillpurchasedApprovalJudge implements DecisionHandler {
    
    public String decide(ExecutionContext context) throws Exception {
	    String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
	    ImportPaymentService importPaymentService = (ImportPaymentService) EasyApplicationContextUtils.getBeanByName("importPaymentService");
	    ImportPayment importPayment = importPaymentService.getImportPaymentById(businessId);
	    
	    BigDecimal docryAmount = new BigDecimal(0);
	    for ( ImportDocuBankItem bankItem : importPayment.getImportDocuBankItem()){
	        docryAmount = docryAmount.add(bankItem.getDocuarypayamount()); //实际押汇金额
	    }
	    
	    BigDecimal reDocryAmount = new BigDecimal(0);
	    for ( BillPayReBankItem reBankItem : importPayment.getBillPayReBankItem()) {
	        reDocryAmount = reDocryAmount.add(reBankItem.getApplyamount()); //实际还押汇金额
	    }
	    
	    
	    PaymentJdbcDao jdbcdao = (PaymentJdbcDao)EasyApplicationContextUtils.getBeanByType(PaymentJdbcDao.class);
	    jdbcdao.updateYbillPurRebank(importPayment.getPaymentid());
	   
	    //财务部还押汇结束流程是否结束为：还押汇金额大于等于实际押汇金额， 未结束则从业务申请还押汇节点开始循环。
	    if (reDocryAmount.compareTo(docryAmount)>-1){
	        return "全额还";
	    } else {  
//	    	importPayment.setRedocaryamount2(importPayment.getRedocaryamount2().add(importPayment.getRedocaryamount()));
//	        importPayment.setAccountdate(" ");
//	        importPayment.setVoucherdate(" ");
//	        importPayment.setText(" ");
//	        importPayment.setRedocaryamount(new BigDecimal("0"));
//	        importPayment.setRedocaryrate(new BigDecimal("0"));
//	        importPayment.setImportSettSubj(null);
//	        importPayment.setImportFundFlow(null);	        
//	        ImportPaymentHibernateDao importPaymentHibernateDao = (ImportPaymentHibernateDao)EasyApplicationContextUtils.getBeanByName("importPaymentHibernateDao");
//	     //   importPaymentHibernateDao.saveOrUpdate(importPayment);
//	        importPaymentHibernateDao.update(importPayment);
//	        importPaymentHibernateDao.flush();
	    	jdbcdao.updatePurRebank(importPayment.getPaymentid(), importPayment.getRedocaryamount2().add(importPayment.getRedocaryamount()));
	    	jdbcdao.deleteSettleFund(importPayment.getPaymentid());
	        
	        return "部分还";
	    }
	}
}
