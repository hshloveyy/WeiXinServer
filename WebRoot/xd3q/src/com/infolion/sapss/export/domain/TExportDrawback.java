package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TExportDrawback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_DRAWBACK")
public class TExportDrawback implements java.io.Serializable {

	// Fields

	private String exportDrawbackId;
	private String exportApplyId;
	private String exportApplyNo;
	private String writeDate;
	private String drawbackDate;
	private Double drawbackRate;
	private Double drawbackValue;
	private Double drawbackReal;
	private Double shipingValue;
	private Double rate;
	private String mark;
	private Double imposeRate;
	private String creatorTime;
	private String creator;
	private String creatorDept;
	private String isAvailable;
	private String tradeType;
	private String deptId;

	private String skdzr;//税款到帐日
	private String writeNo;//核销单号
	private String bgdh;//报关单号
	private String ylrh;//预录入号
	private String bgje;//报关金额
	private String cjbb;//成交币别
	private String bgsl;//报关数量
	private String bgdw;//报关单位
	private String ckrq;//出口日期 
	private String hdrq;//回单日期-->后改成合同号
	private String hxje;//核销金额
	private String zzsfpje;//增值税发票金额
	private String hhb;//换汇比
	private String hgsb;//海关商编-->后新增
	private String ckka;//出口口岸-->后新增
	private String mdg;//目的港-->后新增

	// Constructors

	/** default constructor */
	public TExportDrawback() {
	}

	/** minimal constructor */
	public TExportDrawback(String exportDrawbackId) {
		this.exportDrawbackId = exportDrawbackId;
	}

	/** full constructor */
	public TExportDrawback(String exportDrawbackId, String exportApplyId,
			String exportApplyNo, String writeDate, String drawbackDate,
			Double drawbackRate, Double drawbackValue, Double drawbackReal,
			Double shipingValue, Double rate, String mark, Double imposeRate,
			String creatorTime, String creator, String creatorDept) {
		this.exportDrawbackId = exportDrawbackId;
		this.exportApplyId = exportApplyId;
		this.exportApplyNo = exportApplyNo;
		this.writeDate = writeDate;
		this.drawbackDate = drawbackDate;
		this.drawbackRate = drawbackRate;
		this.drawbackValue = drawbackValue;
		this.drawbackReal = drawbackReal;
		this.shipingValue = shipingValue;
		this.rate = rate;
		this.mark = mark;
		this.imposeRate = imposeRate;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.creatorDept = creatorDept;
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_DRAWBACK_ID", unique = true, nullable = false, length = 36)
	public String getExportDrawbackId() {
		return this.exportDrawbackId;
	}

	public void setExportDrawbackId(String exportDrawbackId) {
		this.exportDrawbackId = exportDrawbackId;
	}

	@Column(name = "EXPORT_APPLY_ID", length = 36)
	public String getExportApplyId() {
		return this.exportApplyId;
	}

	public void setExportApplyId(String exportApplyId) {
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "EXPORT_APPLY_NO", length = 50)
	public String getExportApplyNo() {
		return this.exportApplyNo;
	}

	public void setExportApplyNo(String exportApplyNo) {
		this.exportApplyNo = exportApplyNo;
	}

	@Column(name = "WRITE_DATE", length = 20)
	public String getWriteDate() {
		return this.writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	@Column(name = "DRAWBACK_DATE", length = 20)
	public String getDrawbackDate() {
		return this.drawbackDate;
	}

	public void setDrawbackDate(String drawbackDate) {
		this.drawbackDate = drawbackDate;
	}

	@Column(name = "DRAWBACK_RATE", precision = 10, scale = 4)
	public Double getDrawbackRate() {
		return this.drawbackRate;
	}

	public void setDrawbackRate(Double drawbackRate) {
		this.drawbackRate = drawbackRate;
	}

	@Column(name = "DRAWBACK_VALUE", precision = 12, scale = 3)
	public Double getDrawbackValue() {
		return this.drawbackValue;
	}

	public void setDrawbackValue(Double drawbackValue) {
		this.drawbackValue = drawbackValue;
	}

	@Column(name = "DRAWBACK_REAL", precision = 12, scale = 3)
	public Double getDrawbackReal() {
		return this.drawbackReal;
	}

	public void setDrawbackReal(Double drawbackReal) {
		this.drawbackReal = drawbackReal;
	}

	@Column(name = "SHIPING_VALUE", precision = 12, scale = 3)
	public Double getShipingValue() {
		return this.shipingValue;
	}

	public void setShipingValue(Double shipingValue) {
		this.shipingValue = shipingValue;
	}

	@Column(name = "RATE", precision = 10, scale = 4)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "MARK", length = 4000)
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Column(name = "IMPOSE_RATE", precision = 10, scale = 4)
	public Double getImposeRate() {
		return this.imposeRate;
	}

	public void setImposeRate(Double imposeRate) {
		this.imposeRate = imposeRate;
	}

	@Column(name = "CREATOR_TIME", length = 36)
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

	@Column(name = "CREATOR_DEPT", length = 36)
	public String getCreatorDept() {
		return this.creatorDept;
	}

	public void setCreatorDept(String creatorDept) {
		this.creatorDept = creatorDept;
	}
    @Column(name = "IS_AVAILABLE",length=1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "TRADE_TYPE",length=2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "skdzr", length = 50)
	public String getSkdzr() {
		return skdzr;
	}

	public void setSkdzr(String skdzr) {
		this.skdzr = skdzr;
	}
	@Column(name = "write_No", length = 50)
	public String getWriteNo() {
		return writeNo;
	}

	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}
	@Column(name = "bgdh", length = 50)
	public String getBgdh() {
		return bgdh;
	}

	public void setBgdh(String bgdh) {
		this.bgdh = bgdh;
	}
	@Column(name = "ylrh", length = 50)
	public String getYlrh() {
		return ylrh;
	}

	public void setYlrh(String ylrh) {
		this.ylrh = ylrh;
	}
	@Column(name = "bgje", length = 50)
	public String getBgje() {
		return bgje;
	}

	public void setBgje(String bgje) {
		this.bgje = bgje;
	}
	@Column(name = "cjbb", length = 50)
	public String getCjbb() {
		return cjbb;
	}

	public void setCjbb(String cjbb) {
		this.cjbb = cjbb;
	}
	@Column(name = "bgsl", length = 50)
	public String getBgsl() {
		return bgsl;
	}

	public void setBgsl(String bgsl) {
		this.bgsl = bgsl;
	}
	@Column(name = "bgdw", length = 50)
	public String getBgdw() {
		return bgdw;
	}

	public void setBgdw(String bgdw) {
		this.bgdw = bgdw;
	}
	@Column(name = "ckrq", length = 50)
	public String getCkrq() {
		return ckrq;
	}

	public void setCkrq(String ckrq) {
		this.ckrq = ckrq;
	}
	@Column(name = "hdrq", length = 50)
	public String getHdrq() {
		return hdrq;
	}

	public void setHdrq(String hdrq) {
		this.hdrq = hdrq;
	}
	@Column(name = "hxje", length = 50)
	public String getHxje() {
		return hxje;
	}

	public void setHxje(String hxje) {
		this.hxje = hxje;
	}
	@Column(name = "zzsfpje", length = 50)
	public String getZzsfpje() {
		return zzsfpje;
	}

	public void setZzsfpje(String zzsfpje) {
		this.zzsfpje = zzsfpje;
	}
	@Column(name = "hhb", length = 50)
	public String getHhb() {
		return hhb;
	}

	public void setHhb(String hhb) {
		this.hhb = hhb;
	}
	@Column(name = "hgsb", length = 50)
	public String getHgsb() {
		return hgsb;
	}

	public void setHgsb(String hgsb) {
		this.hgsb = hgsb;
	}
	@Column(name = "ckka", length = 50)
	public String getCkka() {
		return ckka;
	}

	public void setCkka(String ckka) {
		this.ckka = ckka;
	}
	@Column(name = "mdg", length = 50)
	public String getMdg() {
		return mdg;
	}

	public void setMdg(String mdg) {
		this.mdg = mdg;
	}
	
	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}
}