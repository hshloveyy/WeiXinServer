package com.infolion.sapss.receiptLog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TReceiptGoodsInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RECEIPT_GOODS_INFO")
public class TReceiptGoodsInfo implements java.io.Serializable {

	// Fields

	private String infoId;
	private String receiptNo;
	private String receiptId;
	private String importDate;
	private String customeNo;
	private String preWrCd;
	private String customePrice;
	private String customeCash;
	private String customePort;
	private String importCountry;
	private String customeTotal;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String deptId;
	private String remark;
	private String tradeType;
	private String writeNo;
	
	/**后新增
	 *  海关商编、成交币别、报关数量、报关单位、成交方式、核销金额、核销日期
	 *  退汇金额、退汇日期、核销进度（已核销、部分核销）
    **/
	private String hgsb;//海关商编
	private String cjbb;//成交币别
	private String bgsl;//报关数量
	private String bgdw;//报关单位
	private String cjfs;//成交方式
	private String hxje;//核销金额
	private String hxrq;//核销日期
	private String thje;//退汇金额
	private String thrq;//退汇日期
	private String hxjd;//核销进度（已核销、部分核销）
	private String dj;//单价

	// Constructors

	/** default constructor */
	public TReceiptGoodsInfo() {
	}

	/** minimal constructor */
	public TReceiptGoodsInfo(String infoId) {
		this.infoId = infoId;
	}

	/** full constructor */
	public TReceiptGoodsInfo(String infoId, String receiptNo, String receiptId,
			String importDate, String customeNo, String preWrCd,
			String customePrice, String customeCash, String customePort,
			String importCountry, String customeTotal, String isAvailable,
			String creatorTime, String creator, String deptId) {
		this.infoId = infoId;
		this.receiptNo = receiptNo;
		this.receiptId = receiptId;
		this.importDate = importDate;
		this.customeNo = customeNo;
		this.preWrCd = preWrCd;
		this.customePrice = customePrice;
		this.customeCash = customeCash;
		this.customePort = customePort;
		this.importCountry = importCountry;
		this.customeTotal = customeTotal;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "INFO_ID", unique = true, nullable = false, length = 36)
	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "RECEIPT_NO", length = 50)
	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name = "RECEIPT_ID", length = 36)
	public String getReceiptId() {
		return this.receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	@Column(name = "IMPORT_DATE", length = 20)
	public String getImportDate() {
		return this.importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	@Column(name = "CUSTOME_NO", length = 50)
	public String getCustomeNo() {
		return this.customeNo;
	}

	public void setCustomeNo(String customeNo) {
		this.customeNo = customeNo;
	}

	@Column(name = "PRE_WR_CD", length = 50)
	public String getPreWrCd() {
		return this.preWrCd;
	}

	public void setPreWrCd(String preWrCd) {
		this.preWrCd = preWrCd;
	}

	@Column(name = "CUSTOME_PRICE", length = 50)
	public String getCustomePrice() {
		return this.customePrice;
	}

	public void setCustomePrice(String customePrice) {
		this.customePrice = customePrice;
	}

	@Column(name = "CUSTOME_CASH", length = 50)
	public String getCustomeCash() {
		return this.customeCash;
	}

	public void setCustomeCash(String customeCash) {
		this.customeCash = customeCash;
	}

	@Column(name = "CUSTOME_PORT", length = 50)
	public String getCustomePort() {
		return this.customePort;
	}

	public void setCustomePort(String customePort) {
		this.customePort = customePort;
	}

	@Column(name = "IMPORT_COUNTRY", length = 50)
	public String getImportCountry() {
		return this.importCountry;
	}

	public void setImportCountry(String importCountry) {
		this.importCountry = importCountry;
	}

	@Column(name = "CUSTOME_TOTAL", length = 50)
	public String getCustomeTotal() {
		return this.customeTotal;
	}

	public void setCustomeTotal(String customeTotal) {
		this.customeTotal = customeTotal;
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

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "REMARK", length =1000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "TRADE_TYPE", length =2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	@Column(name = "WRITE_NO", length =50)
	public String getWriteNo() {
		return writeNo;
	}

	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}
	@Column(name = "hgsb", length =50)
	public String getHgsb() {
		return hgsb;
	}

	public void setHgsb(String hgsb) {
		this.hgsb = hgsb;
	}
	@Column(name = "cjbb", length =50)
	public String getCjbb() {
		return cjbb;
	}

	public void setCjbb(String cjbb) {
		this.cjbb = cjbb;
	}
	@Column(name = "bgsl", length =50)
	public String getBgsl() {
		return bgsl;
	}

	public void setBgsl(String bgsl) {
		this.bgsl = bgsl;
	}
	@Column(name = "bgdw", length =50)
	public String getBgdw() {
		return bgdw;
	}

	public void setBgdw(String bgdw) {
		this.bgdw = bgdw;
	}
	@Column(name = "cjfs", length =50)
	public String getCjfs() {
		return cjfs;
	}

	public void setCjfs(String cjfs) {
		this.cjfs = cjfs;
	}
	@Column(name = "hxje", length =50)
	public String getHxje() {
		return hxje;
	}

	public void setHxje(String hxje) {
		this.hxje = hxje;
	}
	@Column(name = "hxrq", length =50)
	public String getHxrq() {
		return hxrq;
	}

	public void setHxrq(String hxrq) {
		this.hxrq = hxrq;
	}
	@Column(name = "thje", length =50)
	public String getThje() {
		return thje;
	}

	public void setThje(String thje) {
		this.thje = thje;
	}
	@Column(name = "thrq", length =50)
	public String getThrq() {
		return thrq;
	}

	public void setThrq(String thrq) {
		this.thrq = thrq;
	}
	@Column(name = "hxjd", length =50)
	public String getHxjd() {
		return hxjd;
	}

	public void setHxjd(String hxjd) {
		this.hxjd = hxjd;
	}
	@Column(name = "dj", length =50)
	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

}