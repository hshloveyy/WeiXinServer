/*
 * @(#)PaymentImportsInfoQueryVO.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-4-8
 *  描　述：创建
 */

package com.infolion.sapss.payment.web;

public class PaymentImportsInfoQueryVO {
	
	private String tradeType;
	private String dict_dept;
	//单号--T_PICK_LIST_INFO
	private String pickLiskNo;
	//合同号--T_PICK_LIST_INFO
	private String contractNo;
	//单到日--T_PICK_LIST_INFO
	private String pickListRecDate;
	//物料描述--T_PAYMENT_IMPORTS_MATERIAL
	private String ekpoTxz01;
	//核销单号(L/C项下到单)--T_PICK_LIST_INFO
	private String writeListNo;
	
	private String applyTime;
	private String approvedTime;
	private String approvedTime1;
	private String payTime;
	private String payTime1;
	private String payRealTime;
	private String payType;
	
	private String dpDaNo;
	private String pickListType;
	private String collectionBank;
	private String creditNO;
	private String issuingDate;
	private String issuingBank;
    private String goodsName;
	public String getDict_dept() {
		return dict_dept;
	}
	public void setDict_dept(String dict_dept) {
		this.dict_dept = dict_dept;
	}
	public String getPickLiskNo() {
		return pickLiskNo;
	}
	public void setPickLiskNo(String pickLiskNo) {
		this.pickLiskNo = pickLiskNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPickListRecDate() {
		return pickListRecDate;
	}
	public void setPickListRecDate(String pickListRecDate) {
		this.pickListRecDate = pickListRecDate;
	}
	public String getEkpoTxz01() {
		return ekpoTxz01;
	}
	public void setEkpoTxz01(String ekpoTxz01) {
		this.ekpoTxz01 = ekpoTxz01;
	}
	public String getWriteListNo() {
		return writeListNo;
	}
	public void setWriteListNo(String writeListNo) {
		this.writeListNo = writeListNo;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayRealTime() {
		return payRealTime;
	}
	public void setPayRealTime(String payRealTime) {
		this.payRealTime = payRealTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getDpDaNo() {
		return dpDaNo;
	}
	public void setDpDaNo(String dpDaNo) {
		this.dpDaNo = dpDaNo;
	}
	public String getPickListType() {
		return pickListType;
	}
	public void setPickListType(String pickListType) {
		this.pickListType = pickListType;
	}
	public String getCollectionBank() {
		return collectionBank;
	}
	public void setCollectionBank(String collectionBank) {
		this.collectionBank = collectionBank;
	}
	public String getCreditNO() {
		return creditNO;
	}
	public void setCreditNO(String creditNO) {
		this.creditNO = creditNO;
	}
	public String getIssuingDate() {
		return issuingDate;
	}
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}
	public String getIssuingBank() {
		return issuingBank;
	}
	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}
	public String getPayTime1() {
		return payTime1;
	}
	public void setPayTime1(String payTime1) {
		this.payTime1 = payTime1;
	}
	public String getApprovedTime1() {
		return approvedTime1;
	}
	public void setApprovedTime1(String approvedTime1) {
		this.approvedTime1 = approvedTime1;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

}
