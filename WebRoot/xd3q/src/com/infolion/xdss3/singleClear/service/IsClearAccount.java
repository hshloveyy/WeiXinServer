package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.ParameterObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

public class IsClearAccount extends ClearAccount implements IIsClearAccount{
	
	/**
	 * 取得原币差额((差额)结算科目金额总计+纯资金往来金额总计的和)
	 * @param voucherItemList 凭证行项目(结算科目+纯资金往来)
	 * @return
	 */
	public BigDecimal getMargin(List<VoucherItem> voucherItemList){
//		BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
//		BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和
		
		BigDecimal sumAmount3_s = new BigDecimal(0); // 所有财务结算借方金额的和
		BigDecimal sumAmount3_h = new BigDecimal(0); // 所有财务结算贷方金额的和
		for(int n=0;n<voucherItemList.size();n++){
			//第一条为调整金，不统计
			VoucherItem voucherItem0 = (VoucherItem)voucherItemList.get(n);
			if("001".equals(voucherItem0.getRownumber()) || "1".equals(voucherItem0.getRownumber()))continue;
			if ("S".equalsIgnoreCase(voucherItem0.getDebitcredit())){
//				sumAmount2_s = sumAmount2_s.add(voucherItem0.getAmount2());
				sumAmount3_s = sumAmount3_s.add(voucherItem0.getAmount());
			}
			if ("H".equalsIgnoreCase(voucherItem0.getDebitcredit())){
//				sumAmount2_h = sumAmount2_h.add(voucherItem0.getAmount2());
				sumAmount3_h = sumAmount3_h.add(voucherItem0.getAmount());
			}			
		}
		//金额借-贷相减
		BigDecimal sumAmount5 = sumAmount3_s.subtract(sumAmount3_h);		
		return sumAmount5;
	}
	/**
	 * 取得原币差额((差额)结算科目金额总计+纯资金往来金额总计的和)
	 * @param voucherItemList 凭证行项目(结算科目+纯资金往来)
	 * @return
	 */
	public BigDecimal getMargin(Set<VoucherItem> voucherItemSet){
		List<VoucherItem> list = new ArrayList<VoucherItem>(voucherItemSet);
		return this.getMargin(list);
	}
	/**
	 * 取得本位币差额((差额)结算科目金额总计+纯资金往来金额总计的和)
	 * @param voucherItemList 凭证行项目(结算科目+纯资金往来)
	 * @return
	 */
	public BigDecimal getMarginByBwb(Set<VoucherItem> voucherItemSet){
		List<VoucherItem> list = new ArrayList<VoucherItem>(voucherItemSet);
		BigDecimal sumAmount2_s = new BigDecimal(0); // 所有财务结算借方本位币金额的和
		BigDecimal sumAmount2_h = new BigDecimal(0); // 所有财务结算贷方本位币金额的和
		
		for(int n=0;n<list.size();n++){
			//第一条为调整金，不统计
			VoucherItem voucherItem0 = (VoucherItem)list.get(n);
			if("001".equals(voucherItem0.getRownumber()) || "1".equals(voucherItem0.getRownumber()))continue;
			if ("S".equalsIgnoreCase(voucherItem0.getDebitcredit())){
				sumAmount2_s = sumAmount2_s.add(voucherItem0.getAmount2());
			
			}
			if ("H".equalsIgnoreCase(voucherItem0.getDebitcredit())){
				sumAmount2_h = sumAmount2_h.add(voucherItem0.getAmount2());				
			}			
		}
		//金额借-贷相减
		BigDecimal sumAmount5 = sumAmount2_s.subtract(sumAmount2_h);		
		return sumAmount5;
	}
	/**
	 * 是否出汇损
	 * @param currency 币别
	 * @param bukrs	公司代码
	 * @return
	 */
	public boolean isProfitAndLoss(String currency,String bukrs){
//		2600公司本位币是港元
		if("2600".equals(bukrs)){
			if(!"HKD".equals(currency)){
				return true;
			}else{
				return false;
			}
		}else{
//			其他的都是人民币是本位币
			if(!"CNY".equals(currency)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	

	/**
	 * 取得汇损差额
	 * 
	 * @param clearF 款清
	 * @param billF 票清
	 * @param sumUnreceivableamouBwb 未收款本位币金额合计
	 * @param sumUnoffsetamountBwb  款未抵消本位币金额合计
	 * @param sumoffsetAmountBwb 次抵消本位币金额合计
	 * @param sumclearAmountBwb 清帐金额本位币合计
	 * @return
	 */
	public BigDecimal getMarginByprofitAndLoss(boolean clearF, boolean billF,
			BigDecimal sumUnreceivableamouBwb, BigDecimal sumUnoffsetamountBwb,
			BigDecimal sumoffsetAmountBwb, BigDecimal sumclearAmountBwb) {
		
		BigDecimal subtractVlaue2 = new BigDecimal(0);	
		if(!clearF && !billF){
			
			//刚好两全清，款的本位币合计 -票的本位币合计
			
			subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumUnoffsetamountBwb);
		}else{
							
			//票的一边全清
			if(!billF){
				//算款，的本位币=款的本位币合计/款的金额的合计*本次已抵消收款合计划-票的本位币合计
//				subtractVlaue2 = collectAmount.divide(sumcollectAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
				//修改成 算款，的本位币=未抵消收款的本位币合计/未抵消收款的金额的合计*本次已抵消收款合计划-票的本位币合计
//				subtractVlaue2 = collectAmount.divide(sumUnoffsetamount,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(billAmount);
				//修改成各个行项目算汇率，再算清账本位币再相加						
				subtractVlaue2 = sumoffsetAmountBwb.subtract(sumUnoffsetamountBwb);
			}
			//款的一边全清
			if(!clearF){
				//算票，的本位币=款的本位币合计-(票的本位币合计/票的金额的合计*清帐金额合计)
//				subtractVlaue2 = billAmount.divide(sumbillAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
				//修改成 算票，的本位币=款的本位币合计-(未收款的本位币合计/未收款的金额的合计*清帐金额合计)
//				subtractVlaue2 = billAmount.divide(sumUnreceivableamou,5,BigDecimal.ROUND_HALF_EVEN).multiply(sumclearAmount);
				//修改成各个行项目算汇率，再算清账本位币再相加
				subtractVlaue2 = sumUnreceivableamouBwb.subtract(sumclearAmountBwb);
			}			
		}
		return subtractVlaue2;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.singleClear.service.IIsClearAccount#getTaxCode(java.util.List, java.lang.String)
	 */
	public String getTaxCode(List<String> contractNoList, String projectNo) {
		String taxCode = " ";
		String contractNo = " ";
		boolean taxCodeFlag=false;
		for(int i=0;i<contractNoList.size()-1;i++){
			String cn = contractNoList.get(i);
			String cn2 = contractNoList.get(i+1);
			if(null == cn || null == cn2){
				taxCodeFlag=true;
				break;
			}
			//是否全部合同号都一样
			if(!cn.equals(cn2))taxCodeFlag=true;
			contractNo=cn;
		}
		//如果合同都一样，taxcode取合同号，否则取能全清一边的立项号。
		if(taxCodeFlag && !StringUtils.isNullBlank(projectNo)){
			taxCode = projectNo;
		}else{
			if(StringUtils.isNullBlank(contractNo)){
				taxCode = projectNo;
			}else{
				if(contractNoList.size()==1){
					taxCode = contractNoList.get(0);
				}else{
					taxCode = contractNo;
				}
			}
		}
		return taxCode;
	}
}
