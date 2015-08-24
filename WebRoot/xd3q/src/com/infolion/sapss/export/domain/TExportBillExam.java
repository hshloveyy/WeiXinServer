package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.validation.DataType;

/**
 * TExportBillExam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_EXPORT_BILL_EXAM")
public class TExportBillExam implements java.io.Serializable {

	// Fields

	private String exportBillExamId;
	private String exportApplyId;
	private String exportApplyNo;
	private String invNo;
	private String billType;
	@ValidateRule(dataType = DataType.STRING, label = "单据所属部门", maxLength = 100,required = true)
	private String deptId;
	private String billDate;
	private String contractNo;
	private String lcdpdaNo;
	private Double total;
	private String currency;
	private String writeNo;
	private String examRecord;
	private String examDate;
	private String bank;
	private String isNegotiat;
	private String billShipDate;
	private String shouldIncomeDate;
	private String mark;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String creatorDept;
	private String tradeType;
	private String maturity;//承兑到期日
	private String goods;//货物品名
	@ValidateRule(dataType = DataType.STRING, label = "期限",required = true)
	private String timeLimit;//托收期限/TT期限
	private String isClear;	//是否已清
	private String negotiatDate; 	//押汇到期日
	private String deliveryDate;	//交单日期
	private String ymatGroup; //物料组
	private String collectNo;//收款单号
	private String collectId;//收款单id
	// Constructors

	/** default constructor */
	public TExportBillExam() {
	}

	/** minimal constructor */
	public TExportBillExam(String exportBillExamId) {
		this.exportBillExamId = exportBillExamId;
	}

	/** full constructor */
	public TExportBillExam(String exportBillExamId, String exportApplyId,
			String exportApplyNo, String invNo, String billType, String deptId,
			String billDate, String contractNo, String lcdpdaNo, Double total,
			String currency, String writeNo, String examRecord,
			String examDate, String bank, String isNegotiat,
			String billShipDate, String shouldIncomeDate, String mark,
			String isAvailable, String creatorTime, String creator,
			String creatorDept,String tradeType,String ymatGroup,String collectNo,String collectId) {
		this.exportBillExamId = exportBillExamId;
		this.exportApplyId = exportApplyId;
		this.exportApplyNo = exportApplyNo;
		this.invNo = invNo;
		this.billType = billType;
		this.deptId = deptId;
		this.billDate = billDate;
		this.contractNo = contractNo;
		this.lcdpdaNo = lcdpdaNo;
		this.total = total;
		this.currency = currency;
		this.writeNo = writeNo;
		this.examRecord = examRecord;
		this.examDate = examDate;
		this.bank = bank;
		this.isNegotiat = isNegotiat;
		this.billShipDate = billShipDate;
		this.shouldIncomeDate = shouldIncomeDate;
		this.mark = mark;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.creatorDept = creatorDept;
		this.tradeType = tradeType;
		this.ymatGroup = ymatGroup;
		this.collectNo = collectNo;
		this.collectId = collectId;
	}

	// Property accessors
	@Id
	@Column(name = "EXPORT_BILL_EXAM_ID", unique = true, nullable = false, length = 36)
	public String getExportBillExamId() {
		return this.exportBillExamId;
	}

	public void setExportBillExamId(String exportBillExamId) {
		this.exportBillExamId = exportBillExamId;
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

	@Column(name = "INV_NO", length = 100)
	public String getInvNo() {
		return this.invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	@Column(name = "BILL_TYPE", length = 2)
	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "BILL_DATE", length = 20)
	public String getBillDate() {
		return this.billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Column(name = "CONTRACT_NO", length = 30)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "LCDPDA_NO", length = 100)
	public String getLcdpdaNo() {
		return this.lcdpdaNo;
	}

	public void setLcdpdaNo(String lcdpdaNo) {
		this.lcdpdaNo = lcdpdaNo;
	}

	@Column(name = "TOTAL", precision = 20, scale = 3)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "CURRENCY", length = 10)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "WRITE_NO", length = 50)
	public String getWriteNo() {
		return this.writeNo;
	}

	public void setWriteNo(String writeNo) {
		this.writeNo = writeNo;
	}

	@Column(name = "EXAM_RECORD", length = 4000)
	public String getExamRecord() {
		return this.examRecord;
	}

	public void setExamRecord(String examRecord) {
		this.examRecord = examRecord;
	}

	@Column(name = "EXAM_DATE", length = 20)
	public String getExamDate() {
		return this.examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	@Column(name = "BANK", length = 200)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "IS_NEGOTIAT", length = 1)
	public String getIsNegotiat() {
		return this.isNegotiat;
	}

	public void setIsNegotiat(String isNegotiat) {
		this.isNegotiat = isNegotiat;
	}

	@Column(name = "BILL_SHIP_DATE", length = 20)
	public String getBillShipDate() {
		return this.billShipDate;
	}

	public void setBillShipDate(String billShipDate) {
		this.billShipDate = billShipDate;
	}

	@Column(name = "SHOULD_INCOME_DATE", length = 20)
	public String getShouldIncomeDate() {
		return this.shouldIncomeDate;
	}

	public void setShouldIncomeDate(String shouldIncomeDate) {
		this.shouldIncomeDate = shouldIncomeDate;
	}

	@Column(name = "MARK", length = 4000)
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
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

	@Column(name = "CREATOR_DEPT", length = 36)
	public String getCreatorDept() {
		return this.creatorDept;
	}

	public void setCreatorDept(String creatorDept) {
		this.creatorDept = creatorDept;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	@Column(name = "maturity", length = 50)
	public String getMaturity() {
		return maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}
	@Column(name = "goods", length = 200)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	
	@Column(name = "timeLimit", length = 50)
	public String getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@Column(name = "isClear", length = 1)
	public String getIsClear() {
		return isClear;
	}
	
	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}
	
	@Column(name = "NEGOTIAT_DATE", length = 20)
	public String getNegotiatDate() {
		return negotiatDate;
	}
	
	public void setNegotiatDate(String negotiatDate) {
		this.negotiatDate = negotiatDate;
	}
	
	@Column(name = "DELIVERY_DATE", length = 20)
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Column(name = "YMAT_GROUP", length = 36)
	public String getYmatGroup() {
		return ymatGroup;
	}

	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}
	
	@Column(name = "COLLECT_NO", length = 36)
	public String getCollectNo() {
		return collectNo;
	}

	public void setCollectNo(String collectNo) {
		this.collectNo = collectNo;
	}
	@Column(name = "COLLECT_ID", length = 36)
	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	
	
}