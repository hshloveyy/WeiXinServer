package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.IFundFlow;
import com.infolion.xdss3.financeSquare.domain.ISettleSubject;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (全面清帐(ParameterVoucherObject),参数类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ParameterVoucherObject extends ParameterObject implements IParameterVoucher{	
	
	private IFundFlow fundFlow; //纯资金
	private ISettleSubject settleSubject; //结算科目	
	private Voucher voucher; //凭证（行项目要用）
	
	
	
	/**纯资金
	 * @return the fundFlow
	 */
	public IFundFlow getFundFlow() {
		return fundFlow;
	}
	/**纯资金
	 * @param fundFlow the fundFlow to set
	 */
	public void setFundFlow(IFundFlow fundFlow) {
		this.fundFlow = fundFlow;
	}
	/**结算科目
	 * @return the settleSubject
	 */
	public ISettleSubject getSettleSubject() {
		return settleSubject;
	}
	/**结算科目
	 * @param settleSubject the settleSubject to set
	 */
	public void setSettleSubject(ISettleSubject settleSubject) {
		this.settleSubject = settleSubject;
	}
	/***
	 * 凭证抬头（行项目要用）
	 * @return
	 */
	public Voucher getVoucher() {
		return voucher;
	}
	/***
	 * 凭证抬头（行项目要用）
	 * @param voucher
	 */
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}	
	
}
