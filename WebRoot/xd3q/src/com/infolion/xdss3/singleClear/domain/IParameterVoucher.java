package com.infolion.xdss3.singleClear.domain;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.IFundFlow;
import com.infolion.xdss3.financeSquare.domain.ISettleSubject;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(IParameterVoucher),参数接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IParameterVoucher extends IParameter{
	/***
	 * 凭证抬头（行项目要用）
	 * @return
	 */
	public Voucher getVoucher() ;
	/**
	 * 纯资金
	 * @return
	 */
	public IFundFlow getFundFlow() ;
	
	/**
	 * 结算科目
	 * @return
	 */
	public ISettleSubject getSettleSubject() ;
}
