package com.infolion.sapss.contract.domain;

import java.math.BigDecimal;
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
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;
import com.infolion.sapss.Constants;

/**
 * TContractSalesInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_SALES_INFO")
public class TContractSalesInfo extends ProcessObject {

	// Fields

	private String contractSalesId;
	private String contractGroupId;
	private String projectId;
	private String projectName;
	private String ymatGroup;
	@ValidateRule(dataType = DataType.STRING, label = "合同名称", maxLength = 100,required = true)
	private String contractName;
	private String contractNo;
	@ValidateRule(dataType = DataType.STRING, label = "凭证类型", maxLength = 4,required = true)	
	private String vbakAuart;
	private String vbakAuartName;
	@ValidateRule(dataType = DataType.STRING, label = "销售组织", maxLength = 4,required = true)		
	private String vbakVkorg;
	@ValidateRule(dataType = DataType.STRING, label = "分销渠道", maxLength = 4,required = true)		
	private String vbakVtweg;
	private String vbakVtwegName;
	@ValidateRule(dataType = DataType.STRING, label = "产品组", maxLength = 2,required = true)		
	private String vbakSpart;
	@ValidateRule(dataType = DataType.STRING, label = "销售部门", maxLength = 4,required = true)		
	private String vbakVkbur;
	@ValidateRule(dataType = DataType.STRING, label = "销售组", maxLength = 4)		
	private String vbakVkgrp;
	@ValidateRule(dataType = DataType.STRING, label = "售达方", maxLength = 10,required = true)		
	private String kuagvKunnr;
	private String kuagvKunnrName;
	@ValidateRule(dataType = DataType.STRING, label = "送达方", maxLength = 10,required = true)			
	private String kuwevKunnr;
	private String kuwevKunnrName;
	@ValidateRule(dataType = DataType.STRING, label = "付款方", maxLength = 10,required = true)		
	private String payer;
	private String payerName;
	@ValidateRule(dataType = DataType.STRING, label = "收票方", maxLength = 10,required = true)			
	private String bllToParty;
	private String bllToPartyName;
	@ValidateRule(dataType = DataType.STRING, label = "单据日期", maxLength = 8,required = true)			
	private String vbakAudat;
	@ValidateRule(dataType = DataType.STRING, label = "货币码", maxLength = 5,required = true)		
	private String vbakWaerk;
	private String vbakWaerkName;
	@ValidateRule(dataType = DataType.STRING, label = "国际贸易条件1", maxLength = 3)			
	private String vbkdInco1;
	private String vbkdInco1Name;
	@ValidateRule(dataType = DataType.STRING, label = "国际贸易条件2", maxLength = 28)		
	private String vbkdInco2;
	@ValidateRule(dataType = DataType.STRING, label = "付款条件", maxLength = 4,required = true)		
	private String vbkdZterm;
	private String vbkdZtermName;
	@ValidateRule(dataType = DataType.STRING, label = "付款方式", maxLength = 1,required = true)		
	private String vbkdZlsch;
	@ValidateRule(dataType = DataType.STRING, label = "会计汇率", maxLength = 9)		
	private BigDecimal vbkdKurrf;
	private String vbkdZlschName;
	private String vbakBname;
	@ValidateRule(dataType = DataType.STRING, label = "外部纸质合同号", maxLength = 35,required = true)			
	private String vbkdIhrez;
	@ValidateRule(dataType = DataType.STRING, label = "手册号", maxLength = 35)			
	private String vbkdBstkdE;
	@ValidateRule(dataType = DataType.STRING, label = "销售地区", maxLength = 6,required=true)			
	private String vbkdBzirk;
	private String shipmentPort;
	private String destinationPort;
	private String shipmentDate;
	private String orderTotal;
	private String orderNet;
	private String vbkdBzirkName;
	private String orderTaxes;
	private String sapOrderNo;
	private String applyTime;
	private String approvedTime;
	private String deptId;
	private String isAvailable;
	private String createTime;
	private String creator;
	private String orderState;
	private String tradeType;
	private String oldContractNo;
	private String mask;
	private String collectionDate;		//合同规定收款日
	private String laterDate;			//最迟开证日
	private BigDecimal marginRatio;		//保证金比例%
	private String totalQuality;//数量
	private String isPromise;//是否约定跌价保证金
	//采购合同关联的采购合同物料信息
	@OneToMany(mappedBy = "contractSalesInfo", cascade = CascadeType.ALL)
	private List<TContractSalesMateriel> contractSalesMateriels= new ArrayList<TContractSalesMateriel>();

	// Constructors

	/** default constructor */
	public TContractSalesInfo() {
	}
	@Transient
	public List<TContractSalesMateriel> getContractSalesMateriels() {
		return contractSalesMateriels;
	}

	public void setContractSalesMateriels(
			List<TContractSalesMateriel> contractSalesMateriels) {
		this.contractSalesMateriels = contractSalesMateriels;
	}

	/** minimal constructor */
	public TContractSalesInfo(String contractSalesId, String projectId,
			String vbkdBstkd, String vbakAuart, String vbakVkorg,
			String vbakVtweg, String vbakSpart, String vbakVkbur,
			String vbakVkgrp, String kuagvKunnr, String kuwevKunnr,
			String payer, String payerName, String bllToParty,
			String bllToPartyName, String vbakAudat, String vbakWaerk,
			String vbkdInco1, String vbkdInco2, String vbkdZterm,
			String vbkdZlsch, BigDecimal vbkdKurrf, String vbakBname,
			String vbkdIhrez, String vbkdBstkdE, String vbkdBzirk,
			String sapOrderNo,String ymatGroup) {
		this.contractSalesId = contractSalesId;
		this.projectId = projectId;
		this.vbakVkorg = vbakVkorg;
		this.vbakVtweg = vbakVtweg;
		this.vbakSpart = vbakSpart;
		this.vbakVkbur = vbakVkbur;
		this.vbakVkgrp = vbakVkgrp;
		this.kuagvKunnr = kuagvKunnr;
		this.kuwevKunnr = kuwevKunnr;
		this.payer = payer;
		this.payerName = payerName;
		this.bllToParty = bllToParty;
		this.bllToPartyName = bllToPartyName;
		this.vbakAudat = vbakAudat;
		this.vbakWaerk = vbakWaerk;
		this.vbkdInco1 = vbkdInco1;
		this.vbkdInco2 = vbkdInco2;
		this.vbkdZterm = vbkdZterm;
		this.vbkdZlsch = vbkdZlsch;
		this.vbkdKurrf = vbkdKurrf;
		this.vbakBname = vbakBname;
		this.vbkdIhrez = vbkdIhrez;
		this.vbkdBstkdE = vbkdBstkdE;
		this.vbkdBzirk = vbkdBzirk;
		this.sapOrderNo = sapOrderNo;
		this.ymatGroup =ymatGroup;
	}

	/** full constructor */
	public TContractSalesInfo(String contractSalesId, String contractGroupId,
			String projectId, String projectName, String contractName,
			String contractNo, String vbkdBstkd, String vbakAuart,
			String vbakAuartName, String vbakVkorg, String vbakVtweg,
			String vbakVtwegName, String vbakSpart, String vbakVkbur,
			String vbakVkgrp, String kuagvKunnr, String kuagvKunnrName,
			String kuwevKunnr, String kuwevKunnrName, String payer,
			String payerName, String bllToParty, String bllToPartyName,
			String vbakAudat, String vbakWaerk, String vbakWaerkName,
			String vbkdInco1, String vbkdInco1Name, String vbkdInco2,
			String vbkdZterm, String vbkdZtermName,
			String vbkdZlsch, BigDecimal vbkdKurrf, String vbkdZlschName,
			String vbakBname, String vbkdIhrez, String vbkdBstkdE,
			String vbkdBzirk, String shipmentPort, String destinationPort,
			String shipmentDate, String orderTotal, String orderNet,
			String vbkdBzirkName, String orderTaxes, String sapOrderNo,
			String applyTime, String approvedTime, String deptId,
			String isAvailable, String createTime, String creator,String ymatGroup) {
		this.contractSalesId = contractSalesId;
		this.contractGroupId = contractGroupId;
		this.projectId = projectId;
		this.projectName = projectName;
		this.contractName = contractName;
		this.contractNo = contractNo;
		this.vbakAuart = vbakAuart;
		this.vbakAuartName = vbakAuartName;
		this.vbakVkorg = vbakVkorg;
		this.vbakVtweg = vbakVtweg;
		this.vbakVtwegName = vbakVtwegName;
		this.vbakSpart = vbakSpart;
		this.vbakVkbur = vbakVkbur;
		this.vbakVkgrp = vbakVkgrp;
		this.kuagvKunnr = kuagvKunnr;
		this.kuagvKunnrName = kuagvKunnrName;
		this.kuwevKunnr = kuwevKunnr;
		this.kuwevKunnrName = kuwevKunnrName;
		this.payer = payer;
		this.payerName = payerName;
		this.bllToParty = bllToParty;
		this.bllToPartyName = bllToPartyName;
		this.vbakAudat = vbakAudat;
		this.vbakWaerk = vbakWaerk;
		this.vbakWaerkName = vbakWaerkName;
		this.vbkdInco1 = vbkdInco1;
		this.vbkdInco1Name = vbkdInco1Name;
		this.vbkdInco2 = vbkdInco2;
		this.vbkdZterm = vbkdZterm;
		this.vbkdZtermName = vbkdZtermName;
		this.vbkdZlsch = vbkdZlsch;
		this.vbkdKurrf = vbkdKurrf;
		this.vbkdZlschName = vbkdZlschName;
		this.vbakBname = vbakBname;
		this.vbkdIhrez = vbkdIhrez;
		this.vbkdBstkdE = vbkdBstkdE;
		this.vbkdBzirk = vbkdBzirk;
		this.shipmentPort = shipmentPort;
		this.destinationPort = destinationPort;
		this.shipmentDate = shipmentDate;
		this.orderTotal = orderTotal;
		this.orderNet = orderNet;
		this.vbkdBzirkName = vbkdBzirkName;
		this.orderTaxes = orderTaxes;
		this.sapOrderNo = sapOrderNo;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.deptId = deptId;
		this.isAvailable = isAvailable;
		this.createTime = createTime;
		this.creator = creator;
		this.ymatGroup =ymatGroup;
	}

	// Property accessors
	@Id
	@Column(name = "CONTRACT_SALES_ID", unique = true,  length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "MASK", length =1000)
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	
	@Column(name = "YMAT_GROUP", length = 36)
	public String getYmatGroup() {
		return ymatGroup;
	}
	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
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

	@Column(name = "CONTRACT_NAME", length = 100)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "CONTRACT_NO", length = 30)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "VBAK_AUART", length = 4)	
	public String getVbakAuart() {
		return vbakAuart;
	}
	public void setVbakAuart(String vbakAuart) {
		this.vbakAuart = vbakAuart;
	}

	@Column(name = "VBAK_AUART_NAME", length = 100)
	public String getVbakAuartName() {
		return this.vbakAuartName;
	}

	public void setVbakAuartName(String vbakAuartName) {
		this.vbakAuartName = vbakAuartName;
	}

	@Column(name = "VBAK_VKORG",  length = 4)
	public String getVbakVkorg() {
		return this.vbakVkorg;
	}

	public void setVbakVkorg(String vbakVkorg) {
		this.vbakVkorg = vbakVkorg;
	}

	@Column(name = "VBAK_VTWEG",  length = 2)
	public String getVbakVtweg() {
		return this.vbakVtweg;
	}

	public void setVbakVtweg(String vbakVtweg) {
		this.vbakVtweg = vbakVtweg;
	}

	@Column(name = "VBAK_VTWEG_NAME", length = 100)
	public String getVbakVtwegName() {
		return this.vbakVtwegName;
	}

	public void setVbakVtwegName(String vbakVtwegName) {
		this.vbakVtwegName = vbakVtwegName;
	}

	@Column(name = "VBAK_SPART",  length = 2)
	public String getVbakSpart() {
		return this.vbakSpart;
	}

	public void setVbakSpart(String vbakSpart) {
		this.vbakSpart = vbakSpart;
	}

	@Column(name = "VBAK_VKBUR",  length = 4)
	public String getVbakVkbur() {
		return this.vbakVkbur;
	}

	public void setVbakVkbur(String vbakVkbur) {
		this.vbakVkbur = vbakVkbur;
	}

	@Column(name = "VBAK_VKGRP",  length = 3)
	public String getVbakVkgrp() {
		return this.vbakVkgrp;
	}

	public void setVbakVkgrp(String vbakVkgrp) {
		this.vbakVkgrp = vbakVkgrp;
	}

	@Column(name = "KUAGV_KUNNR",  length = 10)
	public String getKuagvKunnr() {
		return this.kuagvKunnr;
	}

	public void setKuagvKunnr(String kuagvKunnr) {
		this.kuagvKunnr = kuagvKunnr;
	}

	@Column(name = "KUAGV_KUNNR_NAME", length = 100)
	public String getKuagvKunnrName() {
		return this.kuagvKunnrName;
	}

	public void setKuagvKunnrName(String kuagvKunnrName) {
		this.kuagvKunnrName = kuagvKunnrName;
	}

	@Column(name = "KUWEV_KUNNR",  length = 10)
	public String getKuwevKunnr() {
		return this.kuwevKunnr;
	}

	public void setKuwevKunnr(String kuwevKunnr) {
		this.kuwevKunnr = kuwevKunnr;
	}

	@Column(name = "KUWEV_KUNNR_NAME", length = 100)
	public String getKuwevKunnrName() {
		return this.kuwevKunnrName;
	}

	public void setKuwevKunnrName(String kuwevKunnrName) {
		this.kuwevKunnrName = kuwevKunnrName;
	}


	@Column(name = "VBAK_AUDAT",  length = 8)
	public String getVbakAudat() {
		return this.vbakAudat;
	}

	public void setVbakAudat(String vbakAudat) {
		this.vbakAudat = vbakAudat;
	}

	@Column(name = "VBAK_WAERK",  length = 5)
	public String getVbakWaerk() {
		return this.vbakWaerk;
	}

	public void setVbakWaerk(String vbakWaerk) {
		this.vbakWaerk = vbakWaerk;
	}

	@Column(name = "VBAK_WAERK_NAME", length = 100)
	public String getVbakWaerkName() {
		return this.vbakWaerkName;
	}

	public void setVbakWaerkName(String vbakWaerkName) {
		this.vbakWaerkName = vbakWaerkName;
	}

	@Column(name = "VBKD_INCO1",  length = 3)
	public String getVbkdInco1() {
		return this.vbkdInco1;
	}

	public void setVbkdInco1(String vbkdInco1) {
		this.vbkdInco1 = vbkdInco1;
	}

	@Column(name = "VBKD_INCO1_NAME", length = 100)
	public String getVbkdInco1Name() {
		return this.vbkdInco1Name;
	}

	public void setVbkdInco1Name(String vbkdInco1Name) {
		this.vbkdInco1Name = vbkdInco1Name;
	}

	@Column(name = "VBKD_INCO2",  length = 28)
	public String getVbkdInco2() {
		return this.vbkdInco2;
	}

	public void setVbkdInco2(String vbkdInco2) {
		this.vbkdInco2 = vbkdInco2;
	}

	@Column(name = "VBKD_ZTERM",  length = 4)
	public String getVbkdZterm() {
		return this.vbkdZterm;
	}

	public void setVbkdZterm(String vbkdZterm) {
		this.vbkdZterm = vbkdZterm;
	}

	@Column(name = "VBKD_ZTERM_NAME", length = 100)
	public String getVbkdZtermName() {
		return this.vbkdZtermName;
	}

	public void setVbkdZtermName(String vbkdZtermName) {
		this.vbkdZtermName = vbkdZtermName;
	}

	@Column(name = "VBKD_ZLSCH",  length = 1)
	public String getVbkdZlsch() {
		return this.vbkdZlsch;
	}

	public void setVbkdZlsch(String vbkdZlsch) {
		this.vbkdZlsch = vbkdZlsch;
	}

	@Column(name = "VBKD_KURRF", precision = 9, scale = 0)
	public BigDecimal getVbkdKurrf() {
		return this.vbkdKurrf;
	}

	public void setVbkdKurrf(BigDecimal vbkdKurrf) {
		this.vbkdKurrf = vbkdKurrf;
	}

	@Column(name = "VBKD_ZLSCH_NAME", length = 100)
	public String getVbkdZlschName() {
		return this.vbkdZlschName;
	}

	public void setVbkdZlschName(String vbkdZlschName) {
		this.vbkdZlschName = vbkdZlschName;
	}

	@Column(name = "VBAK_BNAME",  length = 35)
	public String getVbakBname() {
		return this.vbakBname;
	}

	public void setVbakBname(String vbakBname) {
		this.vbakBname = vbakBname;
	}

	@Column(name = "VBKD_IHREZ",  length = 12)
	public String getVbkdIhrez() {
		return this.vbkdIhrez;
	}

	public void setVbkdIhrez(String vbkdIhrez) {
		this.vbkdIhrez = vbkdIhrez;
	}

	@Column(name = "VBKD_BSTKD_E",  length = 35)
	public String getVbkdBstkdE() {
		return this.vbkdBstkdE;
	}

	public void setVbkdBstkdE(String vbkdBstkdE) {
		this.vbkdBstkdE = vbkdBstkdE;
	}

	@Column(name = "VBKD_BZIRK",  length = 6)
	public String getVbkdBzirk() {
		return this.vbkdBzirk;
	}

	public void setVbkdBzirk(String vbkdBzirk) {
		this.vbkdBzirk = vbkdBzirk;
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

	@Column(name = "ORDER_TOTAL", length = 50)
	public String getOrderTotal() {
		return this.orderTotal;
	}

	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}

	@Column(name = "ORDER_NET", length = 50)
	public String getOrderNet() {
		return this.orderNet;
	}

	public void setOrderNet(String orderNet) {
		this.orderNet = orderNet;
	}

	@Column(name = "VBKD_BZIRK_NAME", length = 100)
	public String getVbkdBzirkName() {
		return this.vbkdBzirkName;
	}

	public void setVbkdBzirkName(String vbkdBzirkName) {
		this.vbkdBzirkName = vbkdBzirkName;
	}

	@Column(name = "ORDER_TAXES", length = 50)
	public String getOrderTaxes() {
		return this.orderTaxes;
	}

	public void setOrderTaxes(String orderTaxes) {
		this.orderTaxes = orderTaxes;
	}

	@Column(name = "SAP_ORDER_NO",  length = 50)
	public String getSapOrderNo() {
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "APPLY_TIME", length = 20)
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
	
	@Column(name = "PAYER", length = 10)	
	public String getPayer() {
		return payer;
	}
	
	public void setPayer(String payer) {
		this.payer = payer;
	}
	
	@Column(name = "PAYER_NAME", length = 100)		
	public String getPayerName() {
		return payerName;
	}
	
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	
	@Column(name = "BILL_TO_PARTY", length = 10)		
	public String getBllToParty() {
		return bllToParty;
	}
	
	public void setBllToParty(String bllToParty) {
		this.bllToParty = bllToParty;
	}
	
	@Column(name = "BILL_TO_PARTY_NAME", length = 100)		
	public String getBllToPartyName() {
		return bllToPartyName;
	}
	
	public void setBllToPartyName(String bllToPartyName) {
		this.bllToPartyName = bllToPartyName;
	}
	
	@Column(name = "ORDER_STATE", length = 1)		
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
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
	
	@Column(name = "MARGIN_RATIO", precision = 16, scale = 2)
	public BigDecimal getMarginRatio() {
		return this.marginRatio;
	}

	public void setMarginRatio(BigDecimal marginRatio) {
		this.marginRatio = marginRatio;
	}
	
	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="111";
		
	}
	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="销售合同申请";
		
	}
	@Override
	public void setWorkflowProcessName() {
//		this.workflowProcessName="contract_sales_v1";
		
	}
	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="contractController.spr?action=salesExamine";
		
	}
	@Column(name = "total_Quality", length = 50)
	public String getTotalQuality() {
		return totalQuality;
	}
	public void setTotalQuality(String totalQuality) {
		this.totalQuality = totalQuality;
	}
	@Column(name = "isPromise", length = 10)
	public String getIsPromise() {
		return isPromise;
	}
	public void setIsPromise(String isPromise) {
		this.isPromise = isPromise;
	}



}