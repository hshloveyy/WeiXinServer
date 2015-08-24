package com.infolion.sapss.contract.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.core.domain.BaseObject;
import com.infolion.sapss.Constants;

/**
 * TContractSalesMateriel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_SALES_MATERIEL")
public class TContractSalesMateriel extends BaseObject {

	// Fields

	private String salesRowsId;
	private String contractSalesId;
	private String vbapPstyv;
	private String vbapMatnr;
	private String vbapArktx;
	private BigDecimal vbapZmeng;
	private String vbapVrkme;
	private String rv45aEtdat;
	private String vbapWerks;
	private String vbapUebto;
	private String vbapUntto;
	private BigDecimal vbapKwmeng;
	private Double konvKbetr;
	private String vbkdZterm;
	private String vbkdZtermName;
	private String vbapKpein;
	private String vbapKmein;
	private String rowTotal;
	private String rowNet;
	private String rowTaxes;
	private String rowTaxesRale;
	private String createTime;
	private String creator;
	private String sapRowNo;	
	private String payer;
	private String payerName;
	private String billToParty;
	private String billToPartyName;
	
	//销售合同物料信息关联的其他费用信息
	@OneToMany(mappedBy = "contractSalesMateriel", cascade = CascadeType.ALL)
	private List<TContractSeMaterielCase> contractSeMaterielCases= new ArrayList<TContractSeMaterielCase>();


	//销售合同物料信息对应的销售合同
	@ManyToOne
    @JoinColumn(name = "CONTRACT_SALES_ID", nullable = false)
	private TContractSalesInfo contractSalesInfo;
	// Constructors

	/** default constructor */
	public TContractSalesMateriel() {
	}

	/** minimal constructor */
	public TContractSalesMateriel(String salesRowsId, String vbapPstyv,
			String vbapMatnr, String vbapArktx, BigDecimal vbapZmeng,
			String vbapVrkme, String rv45aEtdat, String vbapWerks,
			String vbapUebto, String vbapUntto, BigDecimal vbapKwmeng,
			Double konvKbetr, String vbkdZterm, String vbkdZtermName,
			String vbapKpein, String vbapKmein) {
		this.salesRowsId = salesRowsId;
		this.vbapPstyv = vbapPstyv;
		this.vbapMatnr = vbapMatnr;
		this.vbapArktx = vbapArktx;
		this.vbapZmeng = vbapZmeng;
		this.vbapVrkme = vbapVrkme;
		this.rv45aEtdat = rv45aEtdat;
		this.vbapWerks = vbapWerks;
		this.vbapUebto = vbapUebto;
		this.vbapUntto = vbapUntto;
		this.vbapKwmeng = vbapKwmeng;
		this.konvKbetr = konvKbetr;
		this.vbkdZterm = vbkdZterm;
		this.vbkdZtermName = vbkdZtermName;
		this.vbapKpein = vbapKpein;
		this.vbapKmein = vbapKmein;
	}

	/** full constructor */
	public TContractSalesMateriel(String salesRowsId, String contractSalesId,
			String vbapPstyv, String vbapMatnr, String vbapArktx,
			BigDecimal vbapZmeng, String vbapVrkme, String rv45aEtdat,
			String vbapWerks, String vbapUebto, String vbapUntto,
			BigDecimal vbapKwmeng, Double konvKbetr, String vbkdZterm,
			String vbkdZtermName, String vbapKpein, String vbapKmein,
			String rowTotal, String rowNet, String rowTaxes,
			String rowTaxesRale, String createTime, String creator,
			String payer, String payerName, String billToParty, String billToPartyName ) {
		this.salesRowsId = salesRowsId;
		this.contractSalesId = contractSalesId;
		this.vbapPstyv = vbapPstyv;
		this.vbapMatnr = vbapMatnr;
		this.vbapArktx = vbapArktx;
		this.vbapZmeng = vbapZmeng;
		this.vbapVrkme = vbapVrkme;
		this.rv45aEtdat = rv45aEtdat;
		this.vbapWerks = vbapWerks;
		this.vbapUebto = vbapUebto;
		this.vbapUntto = vbapUntto;
		this.vbapKwmeng = vbapKwmeng;
		this.konvKbetr = konvKbetr;
		this.vbkdZterm = vbkdZterm;
		this.vbkdZtermName = vbkdZtermName;
		this.vbapKpein = vbapKpein;
		this.vbapKmein = vbapKmein;
		this.rowTotal = rowTotal;
		this.rowNet = rowNet;
		this.rowTaxes = rowTaxes;
		this.rowTaxesRale = rowTaxesRale;
		this.createTime = createTime;
		this.creator = creator;
		this.payer = payer;
		this.payerName = payerName;
		this.billToParty = billToParty;
		this.billToPartyName = billToPartyName;
	}

	// Property accessors
	@Id
	@Column(name = "SALES_ROWS_ID", unique = true,  length = 36)
	public String getSalesRowsId() {
		return this.salesRowsId;
	}

	public void setSalesRowsId(String salesRowsId) {
		this.salesRowsId = salesRowsId;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId() {
		return this.contractSalesId;
	}

	public void setContractSalesId(String contractSalesId) {
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "VBAP_PSTYV",  length = 4)
	public String getVbapPstyv() {
		return this.vbapPstyv;
	}

	public void setVbapPstyv(String vbapPstyv) {
		this.vbapPstyv = vbapPstyv;
	}

	@Column(name = "VBAP_MATNR",  length = 18)
	public String getVbapMatnr() {
		return this.vbapMatnr;
	}

	public void setVbapMatnr(String vbapMatnr) {
		this.vbapMatnr = vbapMatnr;
	}

	@Column(name = "VBAP_ARKTX",  length = 40)
	public String getVbapArktx() {
		return this.vbapArktx;
	}

	public void setVbapArktx(String vbapArktx) {
		this.vbapArktx = vbapArktx;
	}

	@Column(name = "VBAP_ZMENG",  precision = 22, scale = 0)
	public BigDecimal getVbapZmeng() {
		return this.vbapZmeng;
	}

	public void setVbapZmeng(BigDecimal vbapZmeng) {
		this.vbapZmeng = vbapZmeng;
	}

	@Column(name = "VBAP_VRKME",  length = 3)
	public String getVbapVrkme() {
		return this.vbapVrkme;
	}

	public void setVbapVrkme(String vbapVrkme) {
		this.vbapVrkme = vbapVrkme;
	}

	@Column(name = "RV45A_ETDAT",  length = 8)
	public String getRv45aEtdat() {
		return this.rv45aEtdat;
	}

	public void setRv45aEtdat(String rv45aEtdat) {
		this.rv45aEtdat = rv45aEtdat;
	}

	@Column(name = "VBAP_WERKS",  length = 4)
	public String getVbapWerks() {
		return this.vbapWerks;
	}

	public void setVbapWerks(String vbapWerks) {
		this.vbapWerks = vbapWerks;
	}

	@Column(name = "VBAP_UEBTO",  length = 4)
	public String getVbapUebto() {
		return this.vbapUebto;
	}

	public void setVbapUebto(String vbapUebto) {
		this.vbapUebto = vbapUebto;
	}

	@Column(name = "VBAP_UNTTO",  length = 4)
	public String getVbapUntto() {
		return this.vbapUntto;
	}

	public void setVbapUntto(String vbapUntto) {
		this.vbapUntto = vbapUntto;
	}

	@Column(name = "VBAP_KWMENG",  precision = 22, scale = 0)
	public BigDecimal getVbapKwmeng() {
		return this.vbapKwmeng;
	}

	public void setVbapKwmeng(BigDecimal vbapKwmeng) {
		this.vbapKwmeng = vbapKwmeng;
	}

	@Column(name = "KONV_KBETR",  precision = 126, scale = 0)
	public Double getKonvKbetr() {
		return this.konvKbetr;
	}

	public void setKonvKbetr(Double konvKbetr) {
		this.konvKbetr = konvKbetr;
	}

	@Column(name = "VBKD_ZTERM",  length = 4)
	public String getVbkdZterm() {
		return this.vbkdZterm;
	}

	public void setVbkdZterm(String vbkdZterm) {
		this.vbkdZterm = vbkdZterm;
	}

	@Column(name = "VBKD_ZTERM_NAME",  length = 100)
	public String getVbkdZtermName() {
		return this.vbkdZtermName;
	}

	public void setVbkdZtermName(String vbkdZtermName) {
		this.vbkdZtermName = vbkdZtermName;
	}

	@Column(name = "VBAP_KPEIN",  length = 5)
	public String getVbapKpein() {
		return this.vbapKpein;
	}

	public void setVbapKpein(String vbapKpein) {
		this.vbapKpein = vbapKpein;
	}

	@Column(name = "VBAP_KMEIN",  length = 3)
	public String getVbapKmein() {
		return this.vbapKmein;
	}

	public void setVbapKmein(String vbapKmein) {
		this.vbapKmein = vbapKmein;
	}

	@Column(name = "ROW_TOTAL", length = 30)
	public String getRowTotal() {
		return this.rowTotal;
	}

	public void setRowTotal(String rowTotal) {
		this.rowTotal = rowTotal;
	}

	@Column(name = "ROW_NET", length = 30)
	public String getRowNet() {
		return this.rowNet;
	}

	public void setRowNet(String rowNet) {
		this.rowNet = rowNet;
	}

	@Column(name = "ROW_TAXES", length = 30)
	public String getRowTaxes() {
		return this.rowTaxes;
	}

	public void setRowTaxes(String rowTaxes) {
		this.rowTaxes = rowTaxes;
	}

	@Column(name = "ROW_TAXES_RALE", length = 30)
	public String getRowTaxesRale() {
		return this.rowTaxesRale;
	}

	public void setRowTaxesRale(String rowTaxesRale) {
		this.rowTaxesRale = rowTaxesRale;
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
	
	@Column(name = "SAP_ROW_NO", length = 6)
	public String getSapRowNo() {
		return sapRowNo;
	}
	
	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
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
	public String getBillToParty() {
		return billToParty;
	}

	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}

	@Column(name = "BILL_TO_PARTY_NAME", length = 100)		
	public String getBillToPartyName() {
		return billToPartyName;
	}

	public void setBillToPartyName(String billToPartyName) {
		this.billToPartyName = billToPartyName;
	}
	
	@Transient
	public TContractSalesInfo getContractSalesInfo() {
		return contractSalesInfo;
	}

	public void setContractSalesInfo(TContractSalesInfo contractSalesInfo) {
		this.contractSalesInfo = contractSalesInfo;
	}
	@Transient
	public List<TContractSeMaterielCase> getContractSeMaterielCases() {
		return contractSeMaterielCases;
	}

	public void setContractSeMaterielCases(
			List<TContractSeMaterielCase> contractSeMaterielCases) {
		this.contractSeMaterielCases = contractSeMaterielCases;
	}


}