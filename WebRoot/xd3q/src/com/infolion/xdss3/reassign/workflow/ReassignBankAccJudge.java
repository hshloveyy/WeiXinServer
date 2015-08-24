package com.infolion.xdss3.reassign.workflow;

import java.util.Iterator;
import java.util.Set;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;
import com.infolion.xdss3.payment.dao.PaymentJdbcDao;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.service.HomePaymentService;
import com.infolion.xdss3.payment.service.ImportPaymentService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundmentService;

public class ReassignBankAccJudge implements DecisionHandler {

	public String decide(ExecutionContext context) throws Exception {
		String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		ReassignService reassignService = (ReassignService) EasyApplicationContextUtils.getBeanByName("reassignService");
		Reassign reassign = reassignService.getReassignById(businessId);
		String strRet = "路径4";	
		String reassignMethod = reassign.getReassigntmethod();
		
		/**
		 * 重分配方式为"重置并冲销（到业务部门重新分配，过资金部）" 或 "冲销（财务部冲销并作废）"
		 */
		if (reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR_TO_CASH) || reassignMethod.equals(ReassignConstant.FI_CLEAR)) {
			if (reassign.getReassigntype().equals(ReassignConstant.COLLECT)) {				// 【收款】
				CollectService collectService = (CollectService) EasyApplicationContextUtils.getBeanByName("collectService");
				Collect collect = collectService._get(reassign.getReassignboid());
				
				//添加保存收款状态为2
//				CollectJdbcDao jdbcdao = (CollectJdbcDao)EasyApplicationContextUtils.getBeanByType(CollectJdbcDao.class);
//		        jdbcdao.updateOldCollect(collect.getCollectid(), "2");
				
				Set<CollectBankItem> collectbankitems = collect.getCollectbankitem();
				if (null != collectbankitems) {
					Iterator<CollectBankItem> itCollectBankItem = collectbankitems.iterator();
					String currency = collect.getCurrency();
					while (itCollectBankItem.hasNext()) {
						CollectBankItem collectItem = (CollectBankItem) itCollectBankItem.next();
						if (collectItem.getCollectbankacc().equals("310066399018010033466")) {
							return "路径3";		// 上海信达诺出纳确认
						} else if (currency.equals("CNY")) {
							return "路径1";		// 出纳确认收款（本币）
						} else {
							return "路径2";		// 出纳确认收款（外币）
						}
					}
				}
			}else if (reassign.getReassigntype().equals(ReassignConstant.PAYMENT)) {		// 【付款】
				String tradeType = reassignService.getPaymentTradeType(reassign.getReassignboid());
				if (tradeType.equals("01")) {	// 〖内贸付款〗
					HomePaymentService homePaymentService = (HomePaymentService) EasyApplicationContextUtils.getBeanByName("homePaymentService");
					HomePayment homePayment = homePaymentService._get(reassign.getReassignboid());
					
		               //添加保存状态为2
//					homePayment.setBusinessstate("2");
//	                homePaymentService._save(homePayment);
//	                PaymentJdbcDao jdbcdao = (PaymentJdbcDao)EasyApplicationContextUtils.getBeanByType(PaymentJdbcDao.class);
//	                jdbcdao.updatePayment(homePayment.getPaymentid(), "2");
	                
					Set<HomePayBankItem> homePayBankItems = homePayment.getHomePayBankItem();
					if (null != homePayBankItems) {
						Iterator<HomePayBankItem> iterator = homePayBankItems.iterator();
						while (iterator.hasNext()) {
							HomePayBankItem homePayBankItem = iterator.next();
							if (homePayBankItem.getPaybankaccount().equals("310066399018010033466")) {
								return "路径3";	// 上海信达诺出纳确认
							} else if (homePayment.getCurrency().equals("CNY")) {
								return "路径5";	// 出纳确认付款（本币）
							} else {
								return "路径6";	// 出纳确认付 款（外币）
							}
						}
					}
				}else {							// 〖进口付款〗
					ImportPaymentService importPaymentService = (ImportPaymentService) EasyApplicationContextUtils.getBeanByName("importPaymentService");
					ImportPayment importPayment = importPaymentService._get(reassign.getReassignboid());
					
                    //添加保存状态为2
//					importPayment.setBusinessstate("2");
//					importPaymentService._save(importPayment);
//                    PaymentJdbcDao jdbcdao = (PaymentJdbcDao)EasyApplicationContextUtils.getBeanByType(PaymentJdbcDao.class);
//                    jdbcdao.updatePayment(importPayment.getPaymentid(), "2");
                    
					Set<ImportPayBankItem> importPayBankItems = importPayment.getImportPayBankItem();
					if (null != importPayBankItems) {
						Iterator<ImportPayBankItem> iterator = importPayBankItems.iterator();
						while (iterator.hasNext()) {
							ImportPayBankItem importPayBankItem = iterator.next();
							if (importPayBankItem.getPaybankaccount().equals("310066399018010033466")) {
								return "路径3";	// 上海信达诺出纳确认
							} else if(importPayment.getCurrency().equals("CNY")) {
								return "路径5";	// 出纳确认付款（本币）
							} else {
								return "路径6";	// 出纳确认付 款（外币）
							}
						}
					}
				}
			}else if (reassign.getReassigntype().equals(ReassignConstant.CUSTOMERREFUNDMENT)) {		// 【客户退款】
				CustomerRefundmentService customerRefundmentService = (CustomerRefundmentService) EasyApplicationContextUtils.getBeanByName("customerRefundmentService");
				CustomerRefundment customerRefundment = customerRefundmentService._get(reassign.getReassignboid());
                
                //添加保存状态为2
//				customerRefundment.setBusinessstate("2");
//				customerRefundmentService._save(customerRefundment);
//				CustomerRefundItemJdbcDao jdbcdao = (CustomerRefundItemJdbcDao)EasyApplicationContextUtils.getBeanByType(CustomerRefundItemJdbcDao.class);
//                jdbcdao.updateRefundBusinessState(customerRefundment.getRefundmentid(), "2");
                
				CustomerRefundItem customerRefundItem = new CustomerRefundItem();
				CustomerDbBankItem customerDbBankItem = new CustomerDbBankItem();
				// 获取第一条"客户退款行项目"
				if (customerRefundment.getCustomerRefundItem().iterator().hasNext()) {
				    customerRefundItem = customerRefundment.getCustomerRefundItem().iterator().next();
				}
				// 获取第一条"客户退款银行"
				if (customerRefundment.getCustomerDbBankItem().iterator().hasNext()) {
				    customerDbBankItem = customerRefundment.getCustomerDbBankItem().iterator().next();
				}
				if (null != customerDbBankItem) {
				    if ("310066399018010033466".equals(customerDbBankItem.getRefundbankacount())) {
				        return "路径3";
				    } else if(customerRefundItem.getCurrency().equals("CNY")) {
				        return "路径5";
				    } else {
				        return "路径6";
				    }
				}
			}else if(reassign.getReassigntype().equals(ReassignConstant.SUPPLIERREFUNDMENT)){    // 【供应商退款】
			    SupplierRefundmentService supplierRefundmentService = (SupplierRefundmentService) EasyApplicationContextUtils.getBeanByName("supplierRefundmentService");
                SupplierRefundment supplierRefundment = supplierRefundmentService._get(reassign.getReassignboid());
                
                //添加保存状态为2
//                supplierRefundment.setBusinessstate("2");
//                supplierRefundmentService._save(supplierRefundment);
//                CustomerRefundItemJdbcDao jdbcdao = (CustomerRefundItemJdbcDao)EasyApplicationContextUtils.getBeanByType(CustomerRefundItemJdbcDao.class);
//                jdbcdao.updateRefundBusinessState(supplierRefundment.getRefundmentid(), "2");
                
                SupplierRefundItem supplierRefundItem = new SupplierRefundItem();
                SupplierDbBankItem supplierDbBankItem = new SupplierDbBankItem();
                // 获取第一条"客户退款行项目"
                if (supplierRefundment.getSupplierRefundItem().iterator().hasNext()) {
                    supplierRefundItem = supplierRefundment.getSupplierRefundItem().iterator().next();
                }
                // 获取第一条"客户退款银行"
                if (supplierRefundment.getSupplierDbBankItem().iterator().hasNext()) {
                    supplierDbBankItem = supplierRefundment.getSupplierDbBankItem().iterator().next();
                }
                if (null != supplierDbBankItem) {
                    if ("310066399018010033466".equals(supplierDbBankItem.getRefundbankacount())) {
                        strRet = "路径3";
                    } else if (supplierRefundItem.getCurrency().equals("CNY")) {
                        strRet = "路径1";
                    } else {
                        strRet = "路径2";
                    }
                }
			}
		}
		return strRet;
	}
}
