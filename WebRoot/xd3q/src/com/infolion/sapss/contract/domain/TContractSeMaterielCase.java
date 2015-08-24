package com.infolion.sapss.contract.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.sapss.Constants;

/**
 * TContractSeMaterielCase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTRACT_SE_MATERIEL_CASE")
public class TContractSeMaterielCase implements java.io.Serializable {

	// Fields

	private String materielCaseId;
	private String salesRowsId;
	private String konvKschl;
	private String konvKschlName;
	private Long konvKbetr;
	private String konvWears;
	private String konvWearsName;
	private String cmd;
	private String createTime;
	private String creator;
	private String sapRowNo;
	//其他费用对应的销售合同物料信息
	@ManyToOne
    @JoinColumn(name = "SALES_ROWS_ID", nullable = false)
	private TContractSalesMateriel contractSalesMateriel;
	// Constructors

	/** default constructor */
	public TContractSeMaterielCase() {
	}

	/** minimal constructor */
	public TContractSeMaterielCase(String materielCaseId, String konvKschl,
			Long konvKbetr, String konvWears) {
		this.materielCaseId = materielCaseId;
		this.konvKschl = konvKschl;
		this.konvKbetr = konvKbetr;
		this.konvWears = konvWears;
	}

	/** full constructor */
	public TContractSeMaterielCase(String materielCaseId, String salesRowsId,
			String konvKschl, String konvKschlName, Long konvKbetr,
			String konvWears, String konvWearsName, String cmd,
			String createTime, String creator) {
		this.materielCaseId = materielCaseId;
		this.salesRowsId = salesRowsId;
		this.konvKschl = konvKschl;
		this.konvKschlName = konvKschlName;
		this.konvKbetr = konvKbetr;
		this.konvWears = konvWears;
		this.konvWearsName = konvWearsName;
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

	@Column(name = "SALES_ROWS_ID", length = 36)
	public String getSalesRowsId() {
		return this.salesRowsId;
	}

	public void setSalesRowsId(String salesRowsId) {
		this.salesRowsId = salesRowsId;
	}

	@Column(name = "KONV_KSCHL",  length = 4)
	public String getKonvKschl() {
		return this.konvKschl;
	}

	public void setKonvKschl(String konvKschl) {
		this.konvKschl = konvKschl;
	}

	@Column(name = "KONV_KSCHL_NAME", length = 100)
	public String getKonvKschlName() {
		return this.konvKschlName;
	}

	public void setKonvKschlName(String konvKschlName) {
		this.konvKschlName = konvKschlName;
	}

	@Column(name = "KONV_KBETR",  precision = 13, scale = 0)
	public Long getKonvKbetr() {
		return this.konvKbetr;
	}

	public void setKonvKbetr(Long konvKbetr) {
		this.konvKbetr = konvKbetr;
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

	@Transient	
	public TContractSalesMateriel getContractSalesMateriel() {
		return contractSalesMateriel;
	}

	public void setContractSalesMateriel(
			TContractSalesMateriel contractSalesMateriel) {
		this.contractSalesMateriel = contractSalesMateriel;
	}

	@Column(name = "SAP_ROW_NO", length = 6)	
	public String getSapRowNo() {
		return sapRowNo;
	}

	public void setSapRowNo(String sapRowNo) {
		this.sapRowNo = sapRowNo;
	}

}