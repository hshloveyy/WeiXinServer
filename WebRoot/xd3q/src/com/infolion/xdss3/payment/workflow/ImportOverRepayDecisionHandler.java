package com.infolion.xdss3.payment.workflow;

import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.domain.Voucher;

public class ImportOverRepayDecisionHandler implements DecisionHandler{

	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		VoucherHibernateDao voucherHibernateDao = (VoucherHibernateDao) EasyApplicationContextUtils.getBeanByType(VoucherHibernateDao.class);
		ImportPaymentHibernateDao importPaymentHibernateDao = (ImportPaymentHibernateDao)EasyApplicationContextUtils.getBeanByType(ImportPaymentHibernateDao.class);
		List<Voucher> voucherLst = voucherHibernateDao.find("from Voucher v where v.businessid = '" + businessId + "'");
		ImportPayment imPayment = importPaymentHibernateDao.get(businessId);
		
		if(imPayment != null){
//			如果是内贸走押汇
			if("01".equals(imPayment.getTrade_type()))return "否"; 
			//String overRepay = imPayment.getIsoverrepay();海外代付走正常流程了。不跳过出纳审核和财务做帐节点 20150427
			if((voucherLst != null && voucherLst.size() > 0)){
			    for(int i=0; i<voucherLst.size(); i++){
			        // 若凭证列表当中有某个凭证的凭证号为空，就直接返回"否"
			        if(StringUtils.isNullBlank(voucherLst.get(i).getVoucherno())){
			            return "否"; 
			        }
			    }
			    return "是或二次押汇";
			}else{
			    return "否";
			}
		}else{
		    return "否";
		}
		
		
	}

}
