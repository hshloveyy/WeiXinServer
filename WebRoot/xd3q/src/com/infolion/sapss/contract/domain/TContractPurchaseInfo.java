package com.infolion.sapss.contract.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;
import com.infolion.sapss.Constants;

/**
 * TContractPurchaseInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_PURCHASE_INFO")
public class TContractPurchaseInfo extends ProcessObject {

	// Fields

	private String contractPurchaseId;
	private String contractGroupId;
	private String projectId;
	private String projectName;
	private String contractNo;
	@ValidateRule(dataType = DataType.STRING, label = "合同名称", maxLength = 100,required = true)
	private String contractName;
	private String invoicingParty;
	private String invoicingPartyName;
	private String payer;
	private String payerName;
	private String ekkoBstyp;
	private String ekkoBsart;
	@ValidateRule(dataType = DataType.STRING, label = "供应商账户号", maxLength = 10,required = true)
	private String ekkoLifnr;
	private String ekkoLifnrName;
	@ValidateRule(dataType = DataType.STRING, label = "采购凭证日期", maxLength = 8,required = true)
	private String ekkoBedat;
	@ValidateRule(dataType = DataType.STRING, label = "采购组织", maxLength = 4,required = true)
	private String ekkoEkorg;
	@ValidateRule(dataType = DataType.STRING, label = "采购组", maxLength = 3)
	private String ekkoEkgrp;
	@ValidateRule(dataType = DataType.STRING, label = "付款条件代码", maxLength = 4,required = true)
	private String ekkoZterm;
	private String ekkoZtermName;
	@ValidateRule(dataType = DataType.STRING, label = "国际贸易条款1", maxLength = 3)
	private String ekkoInco1;
	private String ekkoInco1Name;
	@ValidateRule(dataType = DataType.STRING, label = "国际贸易条款2", maxLength = 28)
	private String ekkoInco2;
	@ValidateRule(dataType = DataType.STRING, label = "货币码", maxLength = 5,required = true)
	private String ekkoWaers;
	private String ekkoWaersName;
	@ValidateRule(dataType = DataType.STRING, label = "汇率", maxLength = 9)
	private Double ekkoWkurs;
	private String ekkoIhrez;
	@ValidateRule(dataType = DataType.STRING, label = "外部纸质合同号", maxLength = 30,required = true)
	private String ekkoUnsez;
	@ValidateRule(dataType = DataType.STRING, label = "手册号", maxLength = 16)
	private String ekkoTelf1;
	private String totalAmount;
	private String totalTaxes;
	private String totalAmountTaxes;
	private String totalFreight;
	private String totalTariff;
	private String totalCt;
	private String totalIt;
	private String total;
	private String sapOrderNo;
	private String applyTime;
	private String approvedTime;
	private String deptId;
	private String isAvailable;
	private String createTime;
	private String creator;
	private String shipmentPort;
	private String destinationPort;
	@ValidateRule(dataType = DataType.STRING, label = "装运期",required = true)
	private String shipmentDate;
	private String orderState;
	private String tradeType;
	private String oldContractNo;
	private String mask;
	private String payType;//付款方式
	private String collectionDate;		//合同规定收款日
	private String laterDate;			//最迟开证日
	private String ymatGroup; //物类组
	private String totalQuality;
	//采购合同关联的采购合同物料信息
	@Transient
	private List<TContractPurchaseMateriel> contractPurchaseMateriels= new ArrayList();

	// Constructors

	/** default constructor */
	public TContractPurchaseInfo() {
	}
	@Transient
	public List<TContractPurchaseMateriel> getContractPurchaseMateriels() {
		return contractPurchaseMateriels;
	}
	@Transient
	public void setContractPurchaseMateriels(
			List<TContractPurchaseMateriel> contractPurchaseMateriels) {
		this.contractPurchaseMateriels = contractPurchaseMateriels;
	}

	/** minimal constructor */
	public TContractPurchaseInfo(String contractPurchaseId, String projectId,
			String contractNo, String contractName, String ekkoBstyp,
			String ekkoLifnr, String ekkoBedat, String ekkoEkorg,
			String ekkoEkgrp, String ekkoZterm, String ekkoInco1,
			String ekkoInco2, String ekkoWaers, Double ekkoWkurs,
			String ekkoIhrez, String ekkoUnsez, String ekkoTelf1) {
		this.contractPurchaseId = contractPurchaseId;
		this.projectId = projectId;
		this.contractNo = contractNo;
		this.contractName = contractName;
		this.ekkoBstyp = ekkoBstyp;
		this.ekkoLifnr = ekkoLifnr;
		this.ekkoBedat = ekkoBedat;
		this.ekkoEkorg = ekkoEkorg;
		this.ekkoEkgrp = ekkoEkgrp;
		this.ekkoZterm = ekkoZterm;
		this.ekkoInco1 = ekkoInco1;
		this.ekkoInco2 = ekkoInco2;
		this.ekkoWaers = ekkoWaers;
		this.ekkoWkurs = ekkoWkurs;
		this.ekkoIhrez = ekkoIhrez;
		this.ekkoUnsez = ekkoUnsez;
		this.ekkoTelf1 = ekkoTelf1;
		
	}

	/** full constructor */
	public TContractPurchaseInfo(String contractPurchaseId,
			String contractGroupId, String projectId, String projectName,
			String contractNo, String contractName, String invoicingParty,
			String invoicingPartyName, String payer, String payerName,
			String ekkoBstyp, String ekkoLifnr, String ekkoLifnrName,
			String ekkoBedat, String ekkoEkorg, String ekkoEkgrp,
			String ekkoZterm, String ekkoZtermName, String ekkoInco1,
			String ekkoInco1Name, String ekkoInco2,
			String ekkoWaers, String ekkoWaersName, Double ekkoWkurs,
			String ekkoIhrez, String ekkoUnsez, String ekkoTelf1,
			String totalAmount, String totalTaxes, String totalAmountTaxes,
			String totalFreight, String totalTariff, String totalCt,
			String totalIt, String total, String sapOrderNo, String applyTime,
			String approvedTime, String deptId, String isAvailable,
			String createTime, String creator, String shipmentPort,
			String destinationPort, String shipmentDate,String ymatGroup) {
		this.contractPurchaseId = contractPurchaseId;
		this.contractGroupId = contractGroupId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.contractNo = contractNo;
		this.contractName = contractName;
		this.invoicingParty = invoicingParty;
		this.invoicingPartyName = invoicingPartyName;
		this.payer = payer;
		this.payerName = payerName;
		this.ekkoBstyp = ekkoBstyp;
		this.ekkoLifnr = ekkoLifnr;
		this.ekkoLifnrName = ekkoLifnrName;
		this.ekkoBedat = ekkoBedat;
		this.ekkoEkorg = ekkoEkorg;
		this.ekkoEkgrp = ekkoEkgrp;
		this.ekkoZterm = ekkoZterm;
		this.ekkoZtermName = ekkoZtermName;
		this.ekkoInco1 = ekkoInco1;
		this.ekkoInco1Name = ekkoInco1Name;
		this.ekkoInco2 = ekkoInco2;
		this.ekkoWaers = ekkoWaers;
		this.ekkoWaersName = ekkoWaersName;
		this.ekkoWkurs = ekkoWkurs;
		this.ekkoIhrez = ekkoIhrez;
		this.ekkoUnsez = ekkoUnsez;
		this.ekkoTelf1 = ekkoTelf1;
		this.totalAmount = totalAmount;
		this.totalTaxes = totalTaxes;
		this.totalAmountTaxes = totalAmountTaxes;
		this.totalFreight = totalFreight;
		this.totalTariff = totalTariff;
		this.totalCt = totalCt;
		this.totalIt = totalIt;
		this.total = total;
		this.sapOrderNo = sapOrderNo;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.deptId = deptId;
		this.isAvailable = isAvailable;
		this.createTime = createTime;
		this.creator = creator;
		this.shipmentPort = shipmentPort;
		this.destinationPort = destinationPort;
		this.shipmentDate = shipmentDate;
		this.ymatGroup = ymatGroup;
	}

	// Property accessors
	@Id
	@Column(name = "CONTRACT_PURCHASE_ID", unique = true,  length = 36)
	public String getContractPurchaseId() {
		return this.contractPurchaseId;
	}

	public void setContractPurchaseId(String contractPurchaseId) {
		this.contractPurchaseId = contractPurchaseId;
	}

	@Column(name = "MASK", length =1000)
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

	@Column(name = "CONTRACT_GROUP_ID", length = 36)
	public String getContractGroupId() {
		return this.contractGroupId;
	}

	public void setContractGroupId(String contractGroupId) {
		this.contractGroupId = contractGroupId;
	}

	@Column(name = "PROJECT_ID",  length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_NO",  length = 30)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_NAME",  length = 100)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "INVOICING_PARTY", length = 30)
	public String getInvoicingParty() {
		return this.invoicingParty;
	}

	public void setInvoicingParty(String invoicingParty) {
		this.invoicingParty = invoicingParty;
	}

	@Column(name = "INVOICING_PARTY_NAME", length = 100)
	public String getInvoicingPartyName() {
		return this.invoicingPartyName;
	}

	public void setInvoicingPartyName(String invoicingPartyName) {
		this.invoicingPartyName = invoicingPartyName;
	}

	@Column(name = "PAYER", length = 30)
	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "PAYER_NAME", length = 100)
	public String getPayerName() {
		return this.payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	@Column(name = "EKKO_BSTYP",  length = 1)
	public String getEkkoBstyp() {
		return this.ekkoBstyp;
	}

	public void setEkkoBstyp(String ekkoBstyp) {
		this.ekkoBstyp = ekkoBstyp;
	}

	@Column(name = "EKKO_LIFNR",  length = 10)
	public String getEkkoLifnr() {
		return this.ekkoLifnr;
	}

	public void setEkkoLifnr(String ekkoLifnr) {
		this.ekkoLifnr = ekkoLifnr;
	}

	@Column(name = "EKKO_LIFNR_NAME", length = 100)
	public String getEkkoLifnrName() {
		return this.ekkoLifnrName;
	}

	public void setEkkoLifnrName(String ekkoLifnrName) {
		this.ekkoLifnrName = ekkoLifnrName;
	}

	@Column(name = "EKKO_BEDAT",  length = 8)
	public String getEkkoBedat() {
		return this.ekkoBedat;
	}

	public void setEkkoBedat(String ekkoBedat) {
		this.ekkoBedat = ekkoBedat;
	}

	@Column(name = "EKKO_EKORG",  length = 4)
	public String getEkkoEkorg() {
		return this.ekkoEkorg;
	}

	public void setEkkoEkorg(String ekkoEkorg) {
		this.ekkoEkorg = ekkoEkorg;
	}

	@Column(name = "EKKO_EKGRP",  length = 3)
	public String getEkkoEkgrp() {
		return this.ekkoEkgrp;
	}

	public void setEkkoEkgrp(String ekkoEkgrp) {
		this.ekkoEkgrp = ekkoEkgrp;
	}

	@Column(name = "EKKO_ZTERM",  length = 4)
	public String getEkkoZterm() {
		return this.ekkoZterm;
	}

	public void setEkkoZterm(String ekkoZterm) {
		this.ekkoZterm = ekkoZterm;
	}

	@Column(name = "EKKO_ZTERM_NAME", length = 100)
	public String getEkkoZtermName() {
		return this.ekkoZtermName;
	}

	public void setEkkoZtermName(String ekkoZtermName) {
		this.ekkoZtermName = ekkoZtermName;
	}

	@Column(name = "EKKO_INCO1",  length = 3)
	public String getEkkoInco1() {
		return this.ekkoInco1;
	}

	public void setEkkoInco1(String ekkoInco1) {
		this.ekkoInco1 = ekkoInco1;
	}

	@Column(name = "EKKO_INCO1_NAME", length = 100)
	public String getEkkoInco1Name() {
		return this.ekkoInco1Name;
	}

	public void setEkkoInco1Name(String ekkoInco1Name) {
		this.ekkoInco1Name = ekkoInco1Name;
	}

	@Column(name = "EKKO_INCO2",  length = 28)
	public String getEkkoInco2() {
		return this.ekkoInco2;
	}

	public void setEkkoInco2(String ekkoInco2) {
		this.ekkoInco2 = ekkoInco2;
	}

	@Column(name = "EKKO_WAERS",  length = 5)
	public String getEkkoWaers() {
		return this.ekkoWaers;
	}

	public void setEkkoWaers(String ekkoWaers) {
		this.ekkoWaers = ekkoWaers;
	}

	@Column(name = "EKKO_WAERS_NAME", length = 100)
	public String getEkkoWaersName() {
		return this.ekkoWaersName;
	}

	public void setEkkoWaersName(String ekkoWaersName) {
		this.ekkoWaersName = ekkoWaersName;
	}

	@Column(name = "EKKO_WKURS",  precision = 9, scale = 0)
	public Double getEkkoWkurs() {
		return this.ekkoWkurs;
	}

	public void setEkkoWkurs(Double ekkoWkurs) {
		this.ekkoWkurs = ekkoWkurs;
	}

	@Column(name = "EKKO_IHREZ",  length = 12)
	public String getEkkoIhrez() {
		return this.ekkoIhrez;
	}

	public void setEkkoIhrez(String ekkoIhrez) {
		this.ekkoIhrez = ekkoIhrez;
	}

	@Column(name = "EKKO_UNSEZ",  length = 30)
	public String getEkkoUnsez() {
		return this.ekkoUnsez;
	}

	public void setEkkoUnsez(String ekkoUnsez) {
		this.ekkoUnsez = ekkoUnsez;
	}

	@Column(name = "EKKO_TELF1",  length = 16)
	public String getEkkoTelf1() {
		return this.ekkoTelf1;
	}

	public void setEkkoTelf1(String ekkoTelf1) {
		this.ekkoTelf1 = ekkoTelf1;
	}

	@Column(name = "TOTAL_AMOUNT", length = 20)
	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "TOTAL_TAXES", length = 20)
	public String getTotalTaxes() {
		return this.totalTaxes;
	}

	public void setTotalTaxes(String totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	@Column(name = "TOTAL_AMOUNT_TAXES", length = 20)
	public String getTotalAmountTaxes() {
		return this.totalAmountTaxes;
	}

	public void setTotalAmountTaxes(String totalAmountTaxes) {
		this.totalAmountTaxes = totalAmountTaxes;
	}

	@Column(name = "TOTAL_FREIGHT", length = 20)
	public String getTotalFreight() {
		return this.totalFreight;
	}

	public void setTotalFreight(String totalFreight) {
		this.totalFreight = totalFreight;
	}

	@Column(name = "TOTAL_TARIFF", length = 20)
	public String getTotalTariff() {
		return this.totalTariff;
	}

	public void setTotalTariff(String totalTariff) {
		this.totalTariff = totalTariff;
	}

	@Column(name = "TOTAL_CT", length = 20)
	public String getTotalCt() {
		return this.totalCt;
	}

	public void setTotalCt(String totalCt) {
		this.totalCt = totalCt;
	}

	@Column(name = "TOTAL_IT", length = 20)
	public String getTotalIt() {
		return this.totalIt;
	}

	public void setTotalIt(String totalIt) {
		this.totalIt = totalIt;
	}

	@Column(name = "TOTAL", length = 20)
	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Column(name = "SAP_ORDER_NO", length = 30)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "APPLY_TIME", length = 14)
	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 14)
	public String getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	@Column(name = "SHIPMENT_DATE", length = 20)
	public String getShipmentDate() {
		return this.shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	
	@Column(name = "ORDER_STATE", length = 1)
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	
	@Column(name = "EKKO_BSART", length = 1)
	public String getEkkoBsart() {
		return ekkoBsart;
	}
	public void setEkkoBsart(String ekkoBsart) {
		this.ekkoBsart = ekkoBsart;
	}

	@Column(name = "TRADE_TYPE", length = 2)	
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "OLD_CONTRACT_NO", length = 100)		
	public String getOldContractNo() {
		return oldContractNo;
	}

	public void setOldContractNo(String oldContractNo) {
		this.oldContractNo = oldContractNo;
	}
		
	@Override
	public void setWorkflowModelId() {
		// TODO Auto-generated method stub
		this.workflowModelId="1003";
	}

	@Override
	public void setWorkflowModelName() {
		// TODO Auto-generated method stub
		this.workflowModelName="采购合同申请";
	}

	@Override
	public void setWorkflowProcessName() {
		// TODO Auto-generated method stub
//		this.workflowProcessName="contract_purcharse_v1";
	}

	@Override
	public void setWorkflowProcessUrl() {
		// TODO Auto-generated method stub
		this.workflowProcessUrl="contractController.spr?action=purchaseExamine";
	}

	@Column(name = "pay_type", length = 20)		
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "COLLECTION_DATE", length = 20)		
	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	@Column(name = "LATER_DATE", length = 20)		
	public String getLaterDate() {
		return laterDate;
	}

	public void setLaterDate(String laterDate) {
		this.laterDate = laterDate;
	}
	
	@Column(name = "YMAT_GROUP", length = 36)	
	public String getYmatGroup() {
		return ymatGroup;
	}
	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}
	
	@Column(name = "total_Quality", length = 50)
	public String getTotalQuality() {
		return totalQuality;
	}
	public void setTotalQuality(String totalQuality) {
		this.totalQuality = totalQuality;
	}
	
	
	
	
	
//	public List getContractPurchaseMateriels() {
//		return contractPurchaseMateriels;
//	}
//
//	public void setContractPurchaseMateriels(List contractPurchaseMateriels) {
//		this.contractPurchaseMateriels = contractPurchaseMateriels;
//	}

	
}