package com.infolion.sapss.contract.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infolion.platform.core.domain.BaseObject;
import com.infolion.sapss.Constants;

/**
 * TContractPuMaterielCase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_PU_MATERIEL_CASE")
public class TContractPuMaterielCase extends BaseObject {

	// Fields

	private String materielCaseId;
	private String purchaseRowsId;
	private String konvKschl;
	private String konvWears;
	private String konvWearsName;
	private Double konvKbetr;
	private String konvLifnr;
	private String konvLifnrName;
	private String cmd;
	private String createTime;
	private String creator;
	private String sapRowNo;
	//其他费用对应的采购合同物料信息
//	private TContractPurchaseMateriel contractPurchaseMateriel;
	// Constructors

	/** default constructor */
	public TContractPuMaterielCase() {
	}

	/** minimal constructor */
	public TContractPuMaterielCase(String materielCaseId, String konvKschl,
			String konvWears, Double konvKbetr, String konvLifnr) {
		this.materielCaseId = materielCaseId;
		this.konvKschl = konvKschl;
		this.konvWears = konvWears;
		this.konvKbetr = konvKbetr;
		this.konvLifnr = konvLifnr;
	}

	/** full constructor */
	public TContractPuMaterielCase(String materielCaseId,
			String purchaseRowsId, String konvKschl, String konvWears,
			String konvWearsName, Double konvKbetr, String konvLifnr,
			String konvLifnrName, String cmd, String createTime, String creator) {
		this.materielCaseId = materielCaseId;
		this.purchaseRowsId = purchaseRowsId;
		this.konvKschl = konvKschl;
		this.konvWears = konvWears;
		this.konvWearsName = konvWearsName;
		this.konvKbetr = konvKbetr;
		this.konvLifnr = konvLifnr;
		this.konvLifnrName = konvLifnrName;
		this.cmd = cmd;
		this.createTime = createTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "MATERIEL_CASE_ID", unique = true,  length = 36)
	public String getMaterielCaseId() {
		return this.materielCaseId;
	}

	public void setMaterielCaseId(String materielCaseId) {
		this.materielCaseId = materielCaseId;
	}

	@Column(name = "PURCHASE_ROWS_ID", length = 36)
	public String getPurchaseRowsId() {
		return this.purchaseRowsId;
	}

	public void setPurchaseRowsId(String purchaseRowsId) {
		this.purchaseRowsId = purchaseRowsId;
	}

	@Column(name = "KONV_KSCHL",  length = 4)
	public String getKonvKschl() {
		return this.konvKschl;
	}

	public void setKonvKschl(String konvKschl) {
		this.konvKschl = konvKschl;
	}

	@Column(name = "KONV_WEARS",  length = 5)
	public String getKonvWears() {
		return this.konvWears;
	}

	public void setKonvWears(String konvWears) {
		this.konvWears = konvWears;
	}

	@Column(name = "KONV_WEARS_NAME", length = 100)
	public String getKonvWearsName() {
		return this.konvWearsName;
	}

	public void setKonvWearsName(String konvWearsName) {
		this.konvWearsName = konvWearsName;
	}

	@Column(name = "KONV_KBETR",  precision = 11)
	public Double getKonvKbetr() {
		return this.konvKbetr;
	}

	public void setKonvKbetr(Double konvKbetr) {
		this.konvKbetr = konvKbetr;
	}

	@Column(name = "KONV_LIFNR",  length = 10)
	public String getKonvLifnr() {
		return this.konvLifnr;
	}

	public void setKonvLifnr(String konvLifnr) {
		this.konvLifnr = konvLifnr;
	}

	@Column(name = "KONV_LIFNR_NAME", length = 200)
	public String getKonvLifnrName() {
		return konvLifnrName;
	}

	public void setKonvLifnrName(String konvLifnrName) {
		this.konvLifnrName = konvLifnrName;
	}

	@Column(name = "CMD", length = 200)
	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

//	public TContractPurchaseMateriel getContractPurchaseMateriel() {
//		return contractPurchaseMateriel;
//	}
//
//	public void setContractPurchaseMateriel(
//			TContractPurchaseMateriel contractPurchaseMateriel) {
//		this.contractPurchaseMateriel = contractPurchaseMateriel;
//	}

}