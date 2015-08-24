package com.infolion.sapss.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TPaymentInnerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PAYMENT_INNER_INFO")
public class TPaymentInnerInfo extends ProcessObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -56917409592074318L;
	private String paymentId;
	private String financeNo;
	private String column23;
	private String deptName;
	private String creatorName;
	@ValidateRule(dataType = DataType.STRING, label = "收款单位", required = true)
	private String recBank;
	@ValidateRule(dataType = DataType.STRING, label = "收款单位开户银行", required = true)
	private String openAccountBank;
	@ValidateRule(dataType = DataType.STRING, label = "收款单位账户", required = true)
	private String openAccountNo;
	@ValidateRule(dataType = DataType.NUMBER, label = "申请付款金额", required = true)
	private Double payValue;
	private String tradeType;
	private String payType;
	@ValidateRule(dataType = DataType.NUMBER, label = "付款类型", required = true)
	private String payMethod;
	private String currency;
	private Double exchangeRate;
	private String payUse;
	private String payBank;
	private String payAccount;
	private String payTime;
	private String maturityDate;
	private String payRealTime;
	private String payer;
	private String deptId;
	private String applyTime;
	private String approvedTime;
	private String cmd;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String examineState;
	private String createBank;
	private String preSecurity;
	private Double deposit;
	//@ValidateRule(dataType = DataType.STRING, label = "立项号", required = true)
	private String projectId;
	private String projectNo;
	private String goodsName;
	private String quantity;
	private String replaceDate;
	private String isNetPay;//是否网上支付税款
	private String isPayForAnother;//是否代收代付
	private String unitNo;
	private String unitType;
	
	private String protocolNo;
	
	@Column(name = "protocol_NO", length = 50)
	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	@Column(name = "protocol_id", length = 50)
	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	private String protocolId;
	// Constructors

	/** default constructor */
	public TPaymentInnerInfo() {
	}

	/** minimal constructor */
	public TPaymentInnerInfo(String paymentId) {
		this.paymentId = paymentId;
	}

	/** full constructor */
	public TPaymentInnerInfo(String paymentId, String financeNo,
			String column23, String deptName, String creatorName,
			String recBank, String openAccountBank, String openAccountNo,
			Double payValue, String tradeType, String payType,
			String payMethod, String currency, Double exchangeRate, String payUse, String payBank,
			String payAccount, String payTime, String payRealTime,
			String payer, String deptId, String applyTime, String approvedTime,
			String cmd, String isAvailable, String creatorTime, String creator,
			String examineState,String createBank,String preSecurity) {
		this.paymentId = paymentId;
		this.financeNo = financeNo;
		this.column23 = column23;
		this.deptName = deptName;
		this.creatorName = creatorName;
		this.recBank = recBank;
		this.openAccountBank = openAccountBank;
		this.openAccountNo = openAccountNo;
		this.payValue = payValue;
		this.tradeType = tradeType;
		this.payType = payType;
		this.payMethod = payMethod;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.payUse = payUse;
		this.payBank = payBank;
		this.payAccount = payAccount;
		this.payTime = payTime;
		this.payRealTime = payRealTime;
		this.payer = payer;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.cmd = cmd;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.examineState = examineState;
		this.createBank = createBank;
		this.preSecurity = preSecurity;	
	}

	// Property accessors
	@Id
	@Column(name = "PAYMENT_ID", unique = true, nullable = false, length = 36)
	public String getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Column(name = "FINANCE_NO", length = 50)
	public String getFinanceNo() {
		return this.financeNo;
	}

	public void setFinanceNo(String financeNo) {
		this.financeNo = financeNo;
	}

	@Column(name = "COLUMN_23", length = 50)
	public String getColumn23() {
		return this.column23;
	}

	public void setColumn23(String column23) {
		this.column23 = column23;
	}

	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "CREATOR_NAME", length = 30)
	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "REC_BANK", length = 100)
	public String getRecBank() {
		return this.recBank;
	}

	public void setRecBank(String recBank) {
		this.recBank = recBank;
	}

	@Column(name = "OPEN_ACCOUNT_BANK", length = 100)
	public String getOpenAccountBank() {
		return this.openAccountBank;
	}

	public void setOpenAccountBank(String openAccountBank) {
		this.openAccountBank = openAccountBank;
	}

	@Column(name = "OPEN_ACCOUNT_NO", length = 100)
	public String getOpenAccountNo() {
		return this.openAccountNo;
	}

	public void setOpenAccountNo(String openAccountNo) {
		this.openAccountNo = openAccountNo;
	}

	@Column(name = "PAY_VALUE", precision = 9, scale = 3)
	public Double getPayValue() {
		return this.payValue;
	}

	public void setPayValue(Double payValue) {
		this.payValue = payValue;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "PAY_TYPE", length = 2)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "PAY_METHOD", length = 2)
	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name = "CURRENCY", length = 3)
	public String getCurrency() {
		return this.currency;
	}

	@Column(name = "EXCHANGE_RATE", precision = 9, scale = 5)
	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "PAY_USE", length = 200)
	public String getPayUse() {
		return this.payUse;
	}

	public void setPayUse(String payUse) {
		this.payUse = payUse;
	}

	@Column(name = "PAY_BANK", length = 100)
	public String getPayBank() {
		return this.payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	@Column(name = "PAY_ACCOUNT", length = 100)
	public String getPayAccount() {
		return this.payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	@Column(name = "PAY_TIME", length = 20)
	public String getPayTime() {
		return this.payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	@Column(name = "MATURITY_DATE", length = 20)
	public String getMaturityDate() {
		return this.maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	@Column(name = "PAY_REAL_TIME", length = 20)
	public String getPayRealTime() {
		return this.payRealTime;
	}

	public void setPayRealTime(String payRealTime) {
		this.payRealTime = payRealTime;
	}

	@Column(name = "PAYER", length = 50)
	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime() {
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState() {
		return this.examineState;
	}

	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}

	@Column(name = "CREATE_BANK", length = 36)
	public String getCreateBank() {
		return this.createBank;
	}

	public void setCreateBank(String createBank) {
		this.createBank = createBank;
	}

	@Column(name = "PRE_SECURITY", length = 2)
	public String getPreSecurity() {
		return this.preSecurity;
	}

	public void setPreSecurity(String preSecurity) {
		this.preSecurity = preSecurity;
	}
	
	@Column(name = "DEPOSIT", precision = 20, scale = 3)
	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	
	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="PAYMENT_INNER";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="国内付款申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName="home_credit_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="innerTradePayController.spr?action=paymentInnerInfoExamine";
	}
	@Column(name = "project_id", length = 36)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(name = "project_no", length = 20)
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	@Column(name = "goods_name")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name = "quantity")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "replace_date", length = 20)
	public String getReplaceDate() {
		return replaceDate;
	}

	public void setReplaceDate(String replaceDate) {
		this.replaceDate = replaceDate;
	}
	@Column(name = "is_net_pay", length = 4)
	public String getIsNetPay() {
		return isNetPay;
	}

	public void setIsNetPay(String isNetPay) {
		this.isNetPay = isNetPay;
	}
	@Column(name = "is_payforanother", length = 4)
	public String getIsPayForAnother() {
		return isPayForAnother;
	}

	public void setIsPayForAnother(String isPayForAnother) {
		this.isPayForAnother = isPayForAnother;
	}
	@Column(name = "unitNo", length = 4)
	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	@Column(name = "unitType", length = 4)
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	
}