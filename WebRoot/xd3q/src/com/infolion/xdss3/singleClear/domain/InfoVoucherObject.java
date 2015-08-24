package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * * <pre>
 * (客户全面清帐(InfoVoucherObject),凭证信息类
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class InfoVoucherObject extends InfoObject implements IInfoVoucher{
//	票方的ID
	private List<IKeyValue> billIds;
//	款方的ID
	private List<IKeyValue> cpIds;
//	凭证
	private Voucher voucher;	
	
	private boolean isClear;
	
	private List<String> ids;
	
	private List<IKeyValue> BillIdsByPart;
	private List<IKeyValue> cpIdsByPart;	
	private BigDecimal sumAdjustamount;//本位币相差的金额(调整金)	
	private String taxCode; //用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	private BigDecimal subtractVlaue;//本位币相差的金额,汇损
	
	private List<IKeyValue> BillIdsByAllClear;
	private List<IKeyValue> cpIdsByAllClear;

	/***票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the billIdsByPart
	 */
	public List<IKeyValue> getBillIdsByPart() {
		return BillIdsByPart;
	}
	/**
	 * 款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the cpIdsByPart
	 */
	public List<IKeyValue> getCpIdsByPart() {
		return cpIdsByPart;
	}
	/**
	 * 凭证
	 * @return the voucher
	 */
	public Voucher getVoucher() {
		return voucher;
	}
	/**
	 * 凭证
	 * @param voucher the voucher to set
	 */
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	/**是否全清
	 * @return the isClear
	 */
	public boolean isClear() {
		return isClear;
	}
	/**是否全清
	 * @param isClear the isClear to set
	 */
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
	/**单据的ID
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}
	/**单据的ID
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	
	/**
	 * 票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the billIds
	 */
	public List<IKeyValue> getBillIds() {
		return billIds;
	}
	/**
	 * *票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param billIds the billIds to set
	 */
	public void setBillIds(List<IKeyValue> billIds) {
		this.billIds = billIds;
	}
	/**
	 * 款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the cpIds
	 */
	public List<IKeyValue> getCpIds() {
		return cpIds;
	}
	/**
	 * 款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param cpIds the cpIds to set
	 */
	public void setCpIds(List<IKeyValue> cpIds) {
		this.cpIds = cpIds;
	}
	/**
	 * *票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param billIdsByPart the billIdsByPart to set
	 */
	public void setBillIdsByPart(List<IKeyValue> billIdsByPart) {
		BillIdsByPart = billIdsByPart;
	}
	/**
	 * 款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param cpIdsByPart the cpIdsByPart to set
	 */
	public void setCpIdsByPart(List<IKeyValue> cpIdsByPart) {
		this.cpIdsByPart = cpIdsByPart;
	}
	/**本位币相差的金额(汇损，调整金)	
	 * @return the sumAdjustamount
	 */
	public BigDecimal getSumAdjustamount() {
		return sumAdjustamount;
	}
	/**本位币相差的金额(汇损，调整金)	
	 * @param sumAdjustamount the sumAdjustamount to set
	 */
	public void setSumAdjustamount(BigDecimal sumAdjustamount) {
		this.sumAdjustamount = sumAdjustamount;
	}
	/**
	 * 用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	 * @return the taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}
	/**
	 * 用来区别汇损（调整）和 其他凭证(存立项号或合同号)
	 * @param taxCode the taxCode to set
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	/**
	 * 本位币相差的金额,汇损
	 * @return the subtractVlaue
	 */
	public BigDecimal getSubtractVlaue() {
		return subtractVlaue;
	}
	/**
	 * 本位币相差的金额,汇损
	 * @param subtractVlaue the subtractVlaue to set
	 */
	public void setSubtractVlaue(BigDecimal subtractVlaue) {
		this.subtractVlaue = subtractVlaue;
	}
	/**
	 * 本次全清票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the billIdsByAllClear
	 */
	public List<IKeyValue> getBillIdsByAllClear() {
		return BillIdsByAllClear;
	}
	/**
	 * 本次全清票方的ID,map key 为billno ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param billIdsByAllClear the billIdsByAllClear to set
	 */
	public void setBillIdsByAllClear(List<IKeyValue> billIdsByAllClear) {
		BillIdsByAllClear = billIdsByAllClear;
	}
	/**
	 * 本次全清款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @return the cpIdsByAllClear
	 */
	public List<IKeyValue> getCpIdsByAllClear() {
		return cpIdsByAllClear;
	}
	/**
	 * 本次全清款方的ID,map key 为collectitemid,paymentitemid ,vlaue 为未清id(ycusomertitleid,yvendortitleid)
	 * @param cpIdsByAllClear the cpIdsByAllClear to set
	 */
	public void setCpIdsByAllClear(List<IKeyValue> cpIdsByAllClear) {
		this.cpIdsByAllClear = cpIdsByAllClear;
	}
	
	
	
}
