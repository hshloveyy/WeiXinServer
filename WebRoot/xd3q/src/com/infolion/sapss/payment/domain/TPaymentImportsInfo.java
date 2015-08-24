package com.infolion.sapss.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TPaymentImportsInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PAYMENT_IMPORTS_INFO")
public class TPaymentImportsInfo extends ProcessObject 
{
//@ValidateRule(dataType = DataType.STRING, label = "项目名称", maxLength = 200, required = true)
	// Fields

	private String paymentId;
	private String pickListId;
	@ValidateRule(dataType = DataType.NUMBER, label = "申请付款金额", required = true)
	private String payValue;
	private String tradeType;
	private String payMethod;
	@ValidateRule(dataType = DataType.NUMBER, label = "付款类型", required = true)
	private String payType;
	private String documentaryLimit;
	private String documentaryRealLimit;
	private String documentaryDate;
	private String payDate;
	private String documentaryCurrency;
	@ValidateRule(dataType = DataType.NUMBER, label = "币别", required = true)
	private String currency;
	private String payUse;
	//@ValidateRule(dataType = DataType.NUMBER, label = "付款银行", required = true)
	private String payBank;
	//@ValidateRule(dataType = DataType.NUMBER, label = "付款账号", required = true)
	private String payAccount;
	private String payTime;
	private String payer;
	private String payRealValue;
	private String payRealTime;
	private String deptId;
	private String applyTime;
	private String approvedTime;
	private String cmd;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String examineState;
	//@ValidateRule(dataType = DataType.NUMBER, label = "汇率", required = true)
	private String exchangeRate;
	@ValidateRule(dataType = DataType.NUMBER, label = "单号(发票号)", required = true)
	private String pickListNo;
	private String writeListNo;
	private String documentaryRealRate;
	private String documentaryInterest;
	private String payUnit;
	
	// Constructors

	/** default constructor */
	public TPaymentImportsInfo()
	{
	}

	/** minimal constructor */
	public TPaymentImportsInfo(String paymentId)
	{
		this.paymentId = paymentId;
	}

	/** full constructor */
	public TPaymentImportsInfo(String paymentId, String pickListId,
			String payValue, String tradeType, String payMethod,
			String payType, String documentaryLimit,
			String documentaryRealLimit, String documentaryDate,
			String payDate, String documentaryCurrency, String currency,
			String payUse, String payBank, String payAccount, String payTime,
			String payer, String payRealValue, String payRealTime,
			String deptId, String applyTime, String approvedTime, String cmd,
			String isAvailable, String creatorTime, String creator)
	{
		this.paymentId = paymentId;
		this.pickListId = pickListId;
		this.payValue = payValue;
		this.tradeType = tradeType;
		this.payMethod = payMethod;
		this.payType = payType;
		this.documentaryLimit = documentaryLimit;
		this.documentaryRealLimit = documentaryRealLimit;
		this.documentaryDate = documentaryDate;
		this.payDate = payDate;
		this.documentaryCurrency = documentaryCurrency;
		this.currency = currency;
		this.payUse = payUse;
		this.payBank = payBank;
		this.payAccount = payAccount;
		this.payTime = payTime;
		this.payer = payer;
		this.payRealValue = payRealValue;
		this.payRealTime = payRealTime;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.cmd = cmd;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "PAYMENT_ID", unique = true, nullable = false, length = 36)
	public String getPaymentId()
	{
		return this.paymentId;
	}

	public void setPaymentId(String paymentId)
	{
		this.paymentId = paymentId;
	}

	@Column(name = "PICK_LIST_ID", length = 36)
	public String getPickListId()
	{
		return this.pickListId;
	}

	public void setPickListId(String pickListId)
	{
		this.pickListId = pickListId;
	}

	@Column(name = "PAY_VALUE", precision = 9, scale = 3)
	public String getPayValue()
	{
		return this.payValue;
	}

	public void setPayValue(String payValue)
	{
		this.payValue = payValue;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType()
	{
		return this.tradeType;
	}

	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}

	@Column(name = "PAY_METHOD", length = 4)
	public String getPayMethod()
	{
		return this.payMethod;
	}

	public void setPayMethod(String payMethod)
	{
		this.payMethod = payMethod;
	}

	@Column(name = "PAY_TYPE", length = 2)
	public String getPayType()
	{
		return this.payType;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	@Column(name = "DOCUMENTARY_LIMIT", length = 20)
	public String getDocumentaryLimit()
	{
		return this.documentaryLimit;
	}

	public void setDocumentaryLimit(String documentaryLimit)
	{
		this.documentaryLimit = documentaryLimit;
	}

	@Column(name = "DOCUMENTARY_REAL_LIMIT", length = 20)
	public String getDocumentaryRealLimit()
	{
		return this.documentaryRealLimit;
	}

	public void setDocumentaryRealLimit(String documentaryRealLimit)
	{
		this.documentaryRealLimit = documentaryRealLimit;
	}

	@Column(name = "DOCUMENTARY_DATE", length = 20)
	public String getDocumentaryDate()
	{
		return this.documentaryDate;
	}

	public void setDocumentaryDate(String documentaryDate)
	{
		this.documentaryDate = documentaryDate;
	}

	@Column(name = "PAY_DATE", length = 20)
	public String getPayDate()
	{
		return this.payDate;
	}

	public void setPayDate(String payDate)
	{
		this.payDate = payDate;
	}

	@Column(name = "DOCUMENTARY_CURRENCY", length = 2)
	public String getDocumentaryCurrency()
	{
		return this.documentaryCurrency;
	}

	public void setDocumentaryCurrency(String documentaryCurrency)
	{
		this.documentaryCurrency = documentaryCurrency;
	}

	@Column(name = "CURRENCY", length = 2)
	public String getCurrency()
	{
		return this.currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Column(name = "PAY_USE", length = 200)
	public String getPayUse()
	{
		return this.payUse;
	}

	public void setPayUse(String payUse)
	{
		this.payUse = payUse;
	}

	@Column(name = "PAY_BANK", length = 100)
	public String getPayBank()
	{
		return this.payBank;
	}

	public void setPayBank(String payBank)
	{
		this.payBank = payBank;
	}

	@Column(name = "PAY_ACCOUNT", length = 100)
	public String getPayAccount()
	{
		return this.payAccount;
	}

	public void setPayAccount(String payAccount)
	{
		this.payAccount = payAccount;
	}

	@Column(name = "PAY_TIME", length = 20)
	public String getPayTime()
	{
		return this.payTime;
	}

	public void setPayTime(String payTime)
	{
		this.payTime = payTime;
	}

	@Column(name = "PAYER", length = 50)
	public String getPayer()
	{
		return this.payer;
	}

	public void setPayer(String payer)
	{
		this.payer = payer;
	}

	@Column(name = "PAY_REAL_VALUE", precision = 20, scale = 3)
	public String getPayRealValue()
	{
		return this.payRealValue;
	}

	public void setPayRealValue(String payRealValue)
	{
		this.payRealValue = payRealValue;
	}

	@Column(name = "PAY_REAL_TIME", length = 20)
	public String getPayRealTime()
	{
		return this.payRealTime;
	}

	public void setPayRealTime(String payRealTime)
	{
		this.payRealTime = payRealTime;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId()
	{
		return this.deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime()
	{
		return this.applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime()
	{
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime)
	{
		this.approvedTime = approvedTime;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd()
	{
		return this.cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable()
	{
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime()
	{
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator()
	{
		return this.creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState()
	{
		return examineState;
	}

	public void setExamineState(String examineState)
	{
		this.examineState = examineState;
	}

	@Column(name = "EXCHANGE_RATE", length = 9)	
	public String getExchangeRate()
	{
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="1002";
		
	}

	@Override
	public void setWorkflowModelName() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkflowProcessName() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="paymentImportsInfoController.spr?action=workflowSubmit";
	}
	@Column(name = "PICK_LIST_NO")	
	public String getPickListNo() {
		return pickListNo;
	}

	public void setPickListNo(String pickListNo) {
		this.pickListNo = pickListNo;
	}
	@Column(name = "WRITE_LIST_NO")	
	public String getWriteListNo() {
		return writeListNo;
	}

	public void setWriteListNo(String writeListNo) {
		this.writeListNo = writeListNo;
	}
	@Column(name = "DOCUMENTARY_REAL_RATE")	
	public String getDocumentaryRealRate() {
		return documentaryRealRate;
	}

	public void setDocumentaryRealRate(String documentaryRealRate) {
		this.documentaryRealRate = documentaryRealRate;
	}
	@Column(name = "documentary_Interest")	
	public String getDocumentaryInterest() {
		return documentaryInterest;
	}

	public void setDocumentaryInterest(String documentaryInterest) {
		this.documentaryInterest = documentaryInterest;
	}
	@Column(name = "PAY_UNIT", length = 100)
	public String getPayUnit() {
		return payUnit;
	}

	public void setPayUnit(String payUnit) {
		this.payUnit = payUnit;
	}

}