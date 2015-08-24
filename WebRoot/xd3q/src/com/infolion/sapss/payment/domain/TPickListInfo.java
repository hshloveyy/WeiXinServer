package com.infolion.sapss.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

/**
 * TPickListInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PICK_LIST_INFO")
public class TPickListInfo extends ProcessObject {

	// Fields

	private String pickListId;
	private String contractPurchaseId;
	private String tradeType;
	private String pickListType;
	private String pickListNo;
	private String issuingBank;
	private String lcNo;
	private String collectionBank;
	private String contractNo;
	private String sapOrderNo;
	private String dpDaNo;
	private String issuingDate;
	private String shipmentPort;
	private String destinationPort;
	private String arrivalDate;
	private String pickListRecDate;
	private String securityPickDate;
	private String acceptanceDate;
	private String payDate;
	private String hasRecWrite;
	private String writeListNo;
	private String examineAdvice;
	private String deptId;
	private String applyTime;
	private String approvedTime;
	private String cmd;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String examineState;
	private String currencyId;
	private String provider;
	private String goods;//货物品名
	private String collectionBankNo;//代收行号
	private String timeLimit;//代收期限/TT期限//LC期限
	private String ymatGroup;
	private String isCustomPayTax;//是否客户自缴关税增值税
	private String totalValue;//总金额
	private String maoguanAdvice;//贸管部意见
	// Constructors




	/** default constructor */
	public TPickListInfo() {
	}

	/** minimal constructor */
	public TPickListInfo(String pickListId) {
		this.pickListId = pickListId;
	}

	/** full constructor */
	public TPickListInfo(String pickListId, String contractPurchaseId,
			String tradeType, String pickListType, String pickListNo,
			String issuingBank, String lcNo, String collectionBank,
			String contractNo, String sapOrderNo, String dpDaNo,
			String issuingDate, String shipmentPort, String destinationPort,
			String arrivalDate, String pickListRecDate,
			String securityPickDate, String acceptanceDate, String payDate,
			String hasRecWrite, String writeListNo, String examineAdvice,
			String deptId, String applyTime, String approvedTime, String cmd,
			String isAvailable, String creatorTime, String creator,String ymatGroup) {
		this.pickListId = pickListId;
		this.contractPurchaseId = contractPurchaseId;
		this.tradeType = tradeType;
		this.pickListType = pickListType;
		this.pickListNo = pickListNo;
		this.issuingBank = issuingBank;
		this.lcNo = lcNo;
		this.collectionBank = collectionBank;
		this.contractNo = contractNo;
		this.sapOrderNo = sapOrderNo;
		this.dpDaNo = dpDaNo;
		this.issuingDate = issuingDate;
		this.shipmentPort = shipmentPort;
		this.destinationPort = destinationPort;
		this.arrivalDate = arrivalDate;
		this.pickListRecDate = pickListRecDate;
		this.securityPickDate = securityPickDate;
		this.acceptanceDate = acceptanceDate;
		this.payDate = payDate;
		this.hasRecWrite = hasRecWrite;
		this.writeListNo = writeListNo;
		this.examineAdvice = examineAdvice;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.cmd = cmd;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.ymatGroup= ymatGroup;
	}

	// Property accessors
	@Id
	@Column(name = "PICK_LIST_ID", unique = true, nullable = false, length = 36)
	public String getPickListId() {
		return this.pickListId;
	}

	public void setPickListId(String pickListId) {
		this.pickListId = pickListId;
	}

	@Column(name = "CONTRACT_PURCHASE_ID", length = 36)
	public String getContractPurchaseId() {
		return this.contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "PICK_LIST_TYPE", length = 2)
	public String getPickListType() {
		return this.pickListType;
	}

	public void setPickListType(String pickListType) {
		this.pickListType = pickListType;
	}

	@Column(name = "PICK_LIST_NO", length = 100)
	public String getPickListNo() {
		return this.pickListNo;
	}

	public void setPickListNo(String pickListNo) {
		this.pickListNo = pickListNo;
	}

	@Column(name = "ISSUING_BANK", length = 200)
	public String getIssuingBank() {
		return this.issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	@Column(name = "LC_NO", length = 50)
	public String getLcNo() {
		return this.lcNo;
	}

	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	@Column(name = "COLLECTION_BANK", length = 200)
	public String getCollectionBank() {
		return this.collectionBank;
	}

	public void setCollectionBank(String collectionBank) {
		this.collectionBank = collectionBank;
	}

	@Column(name = "CONTRACT_NO", length = 100)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 30)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "DP_DA_NO", length = 100)
	public String getDpDaNo() {
		return this.dpDaNo;
	}

	public void setDpDaNo(String dpDaNo) {
		this.dpDaNo = dpDaNo;
	}

	@Column(name = "ISSUING_DATE", length = 20)
	public String getIssuingDate() {
		return this.issuingDate;
	}

	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	@Column(name = "SHIPMENT_PORT", length = 200)
	public String getShipmentPort() {
		return this.shipmentPort;
	}

	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}

	@Column(name = "DESTINATION_PORT", length = 200)
	public String getDestinationPort() {
		return this.destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column(name = "ARRIVAL_DATE", length = 20)
	public String getArrivalDate() {
		return this.arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Column(name = "PICK_LIST_REC_DATE", length = 20)
	public String getPickListRecDate() {
		return this.pickListRecDate;
	}

	public void setPickListRecDate(String pickListRecDate) {
		this.pickListRecDate = pickListRecDate;
	}

	@Column(name = "SECURITY_PICK_DATE", length = 20)
	public String getSecurityPickDate() {
		return this.securityPickDate;
	}

	public void setSecurityPickDate(String securityPickDate) {
		this.securityPickDate = securityPickDate;
	}

	@Column(name = "ACCEPTANCE_DATE", length = 20)
	public String getAcceptanceDate() {
		return this.acceptanceDate;
	}

	public void setAcceptanceDate(String acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	@Column(name = "PAY_DATE", length = 20)
	public String getPayDate() {
		return this.payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	@Column(name = "HAS_REC_WRITE", length = 1)
	public String getHasRecWrite() {
		return this.hasRecWrite;
	}

	public void setHasRecWrite(String hasRecWrite) {
		this.hasRecWrite = hasRecWrite;
	}

	@Column(name = "WRITE_LIST_NO", length = 100)
	public String getWriteListNo() {
		return this.writeListNo;
	}

	public void setWriteListNo(String writeListNo) {
		this.writeListNo = writeListNo;
	}

	@Column(name = "EXAMINE_ADVICE", length = 2000)
	public String getExamineAdvice() {
		return this.examineAdvice;
	}

	public void setExamineAdvice(String examineAdvice) {
		this.examineAdvice = examineAdvice;
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
	public String getExamineState()
	{
		return examineState;
	}

	public void setExamineState(String examineState)
	{
		this.examineState = examineState;
	}
	
	@Column(name = "CURRENCY_ID",  length = 5)	
	public String getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(String currencyId)
	{
		this.currencyId = currencyId;
	}
	@Column(name = "PROVIDER",  length =100)	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	
	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="111";
	}
	
	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="到单申请";
	}
	
	@Override
	public void setWorkflowProcessName() {
		//this.workflowProcessName="import_bill_arrive_v1";		
	}
	
	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="pickListInfoController.spr?action=pickListInfoExamine";	
	}
	@Column(name = "goods", length = 200)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	@Column(name = "collectionBankNo",  length = 50)	
	public String getCollectionBankNo() {
		return collectionBankNo;
	}

	public void setCollectionBankNo(String collectionBankNo) {
		this.collectionBankNo = collectionBankNo;
	}
	@Column(name = "timeLimit",  length = 50)	
	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@Column(name = "YMAT_GROUP", length = 36)
	public String getYmatGroup() {
		return ymatGroup;
	}

	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}
	@Column(name = "isCustomPayTax", length = 20)
	public String getIsCustomPayTax() {
		return isCustomPayTax;
	}

	public void setIsCustomPayTax(String isCustomPayTax) {
		this.isCustomPayTax = isCustomPayTax;
	}
	@Column(name = "totalValue", length = 20)
	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	@Column(name = "maoguanAdvice", length = 2000)
	public String getMaoguanAdvice() {
		return maoguanAdvice;
	}

	public void setMaoguanAdvice(String maoguanAdvice) {
		this.maoguanAdvice = maoguanAdvice;
	}
	
}