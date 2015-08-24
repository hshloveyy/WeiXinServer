/**
 * 
 */
package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(InfoObject),信息接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface IInfoVoucher extends IInfo{
	
	/****
	 * 票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getBillIds() ;
	
	/****
	 * 款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getCpIds();
	
	/**
	 * 凭证
	 * @return the voucher
	 */
	public Voucher getVoucher() ;
	
	/**
	 * 是否能全清
	 * @return
	 */
	public boolean isClear() ;	
	
	/**
	 * 单据的ID
	 */
	public List<String> getIds();
	
	/****
	 * 部分清票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getBillIdsByPart() ;
	
	/****
	 *  部分清款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getCpIdsByPart();
	
	/**用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	 * @return the taxCode
	 */
	public String getTaxCode();
	
	/**本位币相差的金额(汇损，调整金)
	 * @return the sumAdjustamount
	 */
	public BigDecimal getSumAdjustamount();
	
	/**本位币相差的金额
	 * @return the subtractVlaue
	 */
	public BigDecimal getSubtractVlaue();
	/****
	 * 本次全清票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getBillIdsByAllClear();
	
	/****
	 *  本次全清款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return
	 */
	public List<IKeyValue> getCpIdsByAllClear();
}
