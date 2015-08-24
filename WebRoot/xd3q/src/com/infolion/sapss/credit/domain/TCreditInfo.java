package com.infolion.sapss.credit.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TCreditInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CREDIT_INFO")
public class TCreditInfo extends ProcessObject
{

	// Fields

	private String creditId;
	private String tradeType;
	private String creditState;
	private String creditNo;
	private String projectNo;
	private String projectName;
	private String sapOrderNo;
	@ValidateRule(dataType = DataType.STRING, label = "关联合同号", maxLength = 300)
	private String contractNo;
	private String createOrRec;
	private String customCreateDate;
	private String creditRecDate;
	private String createBank;
	private String createDate;
	private String country;
	private String request;
	private String benefit;
	private String benefitCertification;
	private String paymentType;
	@ValidateRule(dataType = DataType.NUMBER, label = "金额", required = false)
	private String amount;
	private String goods;
	private String specification;
	private String mark;
	private String invoice;
	private String billOfLading;
	private String billOfInsurance;
	private String billOfQuality;
	private String certificateOfOrigin;
	private String packingSlip;
	private String electricShip;
	private String dispatchElectric;
	private String otherDocuments;
	private String loadingPeriod;
	private String period;
	private String place;
	private String canBatches;
	private String transShipment;
	private String portOfShipment;
	private String portOfDestination;
	private String paymentDate;
	private String preSecurity;
	private String writeOffSingleNo;
	private String specialConditions;
	private String mattersShouldBeAmended;
	private String deptId;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String billConditions;
	private String currency;
	@ValidateRule(dataType = DataType.NUMBER, label = "汇率", required = false)
	private BigDecimal rate;
	private String validDate;
	private String creditInfo;
	private String applyer;
	private String bailDate;
	private String contractId;
	private String availDate;
	private String ymatGroup;
	/** default constructor */
	public TCreditInfo()
	{
	}

	/** minimal constructor */
	public TCreditInfo(String creditId, String createOrRec)
	{
		this.creditId = creditId;
		this.createOrRec = createOrRec;
	}

	/** full constructor */
	public TCreditInfo(String creditId, String tradeType, String creditState,
			String creditNo, String projectNo, String projectName,
			String sapOrderNo, String contractNo, String createOrRec,
			String customCreateDate, String creditRecDate, String createBank,
			String createDate, String country, String request, String benefit,
			String benefitCertification, String paymentType, String amount,
			String goods, String specification, String mark, String invoice,
			String billOfLading, String billOfInsurance, String billOfQuality,
			String certificateOfOrigin, String packingSlip,
			String electricShip, String dispatchElectric,
			String otherDocuments, String loadingPeriod, String period,
			String place, String canBatches, String transShipment,
			String portOfShipment, String portOfDestination,
			String paymentDate, String preSecurity, String writeOffSingleNo,
			String specialConditions, String mattersShouldBeAmended,
			String deptId, String applyTime, String approvedTime,
			String isAvailable, String creatorTime, String creator,
			String billConditions, String currency, BigDecimal rate,
			String creditInfo, String applyer, String bailDate,String contractId,
			String ymatGroup)
	{
		this.creditId = creditId;
		this.tradeType = tradeType;
		this.creditState = creditState;
		this.creditNo = creditNo;
		this.projectNo = projectNo;
		this.projectName = projectName;
		this.sapOrderNo = sapOrderNo;
		this.contractNo = contractNo;
		this.createOrRec = createOrRec;
		this.customCreateDate = customCreateDate;
		this.creditRecDate = creditRecDate;
		this.createBank = createBank;
		this.createDate = createDate;
		this.country = country;
		this.request = request;
		this.benefit = benefit;
		this.benefitCertification = benefitCertification;
		this.paymentType = paymentType;
		this.amount = amount;
		this.goods = goods;
		this.specification = specification;
		this.mark = mark;
		this.invoice = invoice;
		this.billOfLading = billOfLading;
		this.billOfInsurance = billOfInsurance;
		this.billOfQuality = billOfQuality;
		this.certificateOfOrigin = certificateOfOrigin;
		this.packingSlip = packingSlip;
		this.electricShip = electricShip;
		this.dispatchElectric = dispatchElectric;
		this.otherDocuments = otherDocuments;
		this.loadingPeriod = loadingPeriod;
		this.period = period;
		this.place = place;
		this.canBatches = canBatches;
		this.transShipment = transShipment;
		this.portOfShipment = portOfShipment;
		this.portOfDestination = portOfDestination;
		this.paymentDate = paymentDate;
		this.preSecurity = preSecurity;
		this.writeOffSingleNo = writeOffSingleNo;
		this.specialConditions = specialConditions;
		this.mattersShouldBeAmended = mattersShouldBeAmended;
		this.deptId = deptId;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
		this.billConditions = billConditions;
		this.rate = rate;
		this.currency = currency;
		this.creditInfo = creditInfo;
		this.applyer = applyer;
		this.bailDate = bailDate;
		this.contractId = contractId;
		this.ymatGroup = ymatGroup;
	}

	@Column(name = "CONTRACT_ID", length = 3000)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * 保证金日期
	 * 
	 * @return the bailDate
	 */
	@Column(name = "BAIL_DATE", length = 20)
	public String getBailDate()
	{
		return bailDate;
	}

	/**
	 * @param bailDate
	 *            the bailDate to set
	 */
	public void setBailDate(String bailDate)
	{
		this.bailDate = bailDate;
	}

	/**
	 * @return the creditInfo
	 */
	@Column(name = "CREDIT_INFO", length = 200)
	public String getCreditInfo()
	{
		return creditInfo;
	}

	/**
	 * @param creditInfo
	 *            the creditInfo to set
	 */
	public void setCreditInfo(String creditInfo)
	{
		this.creditInfo = creditInfo;
	}

	/**
	 * @return the applyer
	 */
	@Column(name = "APPLYER", length = 36)
	public String getApplyer()
	{
		return applyer;
	}

	/**
	 * @param applyer
	 *            the applyer to set
	 */
	public void setApplyer(String applyer)
	{
		this.applyer = applyer;
	}

	// Property accessors
	@Id
	@Column(name = "CREDIT_ID", unique = true, nullable = false, length = 36)
	public String getCreditId()
	{
		return this.creditId;
	}

	public void setCreditId(String creditId)
	{
		this.creditId = creditId;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType()
	{
		return this.tradeType;
	}

	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}

	@Column(name = "CREDIT_STATE", length = 2)
	public String getCreditState()
	{
		return this.creditState;
	}

	public void setCreditState(String creditState)
	{
		this.creditState = creditState;
	}

	@Column(name = "CREDIT_NO", length = 50)
	public String getCreditNo()
	{
		return this.creditNo;
	}

	public void setCreditNo(String creditNo)
	{
		this.creditNo = creditNo;
	}

	@Column(name = "PROJECT_NO", length = 300)
	public String getProjectNo()
	{
		return this.projectNo;
	}

	public void setProjectNo(String projectNo)
	{
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_NAME", length = 300)
	public String getProjectName()
	{
		return this.projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	@Column(name = "SAP_ORDER_NO", length = 300)
	public String getSapOrderNo()
	{
		return this.sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo)
	{
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "CONTRACT_NO", length = 300)
	public String getContractNo()
	{
		return this.contractNo;
	}

	public void setContractNo(String contractNo)
	{
		this.contractNo = contractNo;
	}

	@Column(name = "CREATE_OR_REC", nullable = false, length = 1)
	public String getCreateOrRec()
	{
		return this.createOrRec;
	}

	public void setCreateOrRec(String createOrRec)
	{
		this.createOrRec = createOrRec;
	}

	@Column(name = "CUSTOM_CREATE_DATE", length = 20)
	public String getCustomCreateDate()
	{
		return this.customCreateDate;
	}

	public void setCustomCreateDate(String customCreateDate)
	{
		this.customCreateDate = customCreateDate;
	}

	@Column(name = "CREDIT_REC_DATE", length = 20)
	public String getCreditRecDate()
	{
		return this.creditRecDate;
	}

	public void setCreditRecDate(String creditRecDate)
	{
		this.creditRecDate = creditRecDate;
	}

	@Column(name = "CREATE_BANK", length = 100)
	public String getCreateBank()
	{
		return this.createBank;
	}

	public void setCreateBank(String createBank)
	{
		this.createBank = createBank;
	}

	@Column(name = "CREATE_DATE", length = 20)
	public String getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	@Column(name = "COUNTRY", length = 50)
	public String getCountry()
	{
		return this.country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	@Column(name = "REQUEST", length = 50)
	public String getRequest()
	{
		return this.request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

	@Column(name = "BENEFIT", length = 50)
	public String getBenefit()
	{
		return this.benefit;
	}

	public void setBenefit(String benefit)
	{
		this.benefit = benefit;
	}

	@Column(name = "BENEFIT_CERTIFICATION", length = 50)
	public String getBenefitCertification()
	{
		return this.benefitCertification;
	}

	public void setBenefitCertification(String benefitCertification)
	{
		this.benefitCertification = benefitCertification;
	}

	@Column(name = "PAYMENT_TYPE", length = 50)
	public String getPaymentType()
	{
		return this.paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	@Column(name = "AMOUNT", length = 50)
	public String getAmount()
	{
		return this.amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	@Column(name = "GOODS", length = 50)
	public String getGoods()
	{
		return this.goods;
	}

	public void setGoods(String goods)
	{
		this.goods = goods;
	}

	@Column(name = "SPECIFICATION", length = 50)
	public String getSpecification()
	{
		return this.specification;
	}

	public void setSpecification(String specification)
	{
		this.specification = specification;
	}

	@Column(name = "MARK", length = 50)
	public String getMark()
	{
		return this.mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}

	@Column(name = "INVOICE", length = 50)
	public String getInvoice()
	{
		return this.invoice;
	}

	public void setInvoice(String invoice)
	{
		this.invoice = invoice;
	}

	@Column(name = "BILL_OF_LADING", length = 50)
	public String getBillOfLading()
	{
		return this.billOfLading;
	}

	public void setBillOfLading(String billOfLading)
	{
		this.billOfLading = billOfLading;
	}

	@Column(name = "BILL_OF_INSURANCE", length = 50)
	public String getBillOfInsurance()
	{
		return this.billOfInsurance;
	}

	public void setBillOfInsurance(String billOfInsurance)
	{
		this.billOfInsurance = billOfInsurance;
	}

	@Column(name = "BILL_OF_QUALITY", length = 50)
	public String getBillOfQuality()
	{
		return this.billOfQuality;
	}

	public void setBillOfQuality(String billOfQuality)
	{
		this.billOfQuality = billOfQuality;
	}

	@Column(name = "CERTIFICATE_OF_ORIGIN", length = 50)
	public String getCertificateOfOrigin()
	{
		return this.certificateOfOrigin;
	}

	public void setCertificateOfOrigin(String certificateOfOrigin)
	{
		this.certificateOfOrigin = certificateOfOrigin;
	}

	@Column(name = "PACKING_SLIP", length = 50)
	public String getPackingSlip()
	{
		return this.packingSlip;
	}

	public void setPackingSlip(String packingSlip)
	{
		this.packingSlip = packingSlip;
	}

	@Column(name = "ELECTRIC_SHIP", length = 50)
	public String getElectricShip()
	{
		return this.electricShip;
	}

	public void setElectricShip(String electricShip)
	{
		this.electricShip = electricShip;
	}

	@Column(name = "DISPATCH_ELECTRIC", length = 50)
	public String getDispatchElectric()
	{
		return this.dispatchElectric;
	}

	public void setDispatchElectric(String dispatchElectric)
	{
		this.dispatchElectric = dispatchElectric;
	}

	@Column(name = "OTHER_DOCUMENTS", length = 50)
	public String getOtherDocuments()
	{
		return this.otherDocuments;
	}

	public void setOtherDocuments(String otherDocuments)
	{
		this.otherDocuments = otherDocuments;
	}

	@Column(name = "LOADING_PERIOD", length = 50)
	public String getLoadingPeriod()
	{
		return this.loadingPeriod;
	}

	public void setLoadingPeriod(String loadingPeriod)
	{
		this.loadingPeriod = loadingPeriod;
	}

	@Column(name = "PERIOD", length = 50)
	public String getPeriod()
	{
		return this.period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	@Column(name = "PLACE", length = 50)
	public String getPlace()
	{
		return this.place;
	}

	public void setPlace(String place)
	{
		this.place = place;
	}

	@Column(name = "CAN_BATCHES", length = 1)
	public String getCanBatches()
	{
		return this.canBatches;
	}

	public void setCanBatches(String canBatches)
	{
		this.canBatches = canBatches;
	}

	@Column(name = "TRANS_SHIPMENT", length = 1)
	public String getTransShipment()
	{
		return this.transShipment;
	}

	public void setTransShipment(String transShipment)
	{
		this.transShipment = transShipment;
	}

	@Column(name = "PORT_OF_SHIPMENT", length = 50)
	public String getPortOfShipment()
	{
		return this.portOfShipment;
	}

	public void setPortOfShipment(String portOfShipment)
	{
		this.portOfShipment = portOfShipment;
	}

	@Column(name = "PORT_OF_DESTINATION", length = 50)
	public String getPortOfDestination()
	{
		return this.portOfDestination;
	}

	public void setPortOfDestination(String portOfDestination)
	{
		this.portOfDestination = portOfDestination;
	}

	@Column(name = "PAYMENT_DATE", length = 20)
	public String getPaymentDate()
	{
		return this.paymentDate;
	}

	public void setPaymentDate(String paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	@Column(name = "PRE_SECURITY", length = 1)
	public String getPreSecurity()
	{
		return this.preSecurity;
	}

	public void setPreSecurity(String preSecurity)
	{
		this.preSecurity = preSecurity;
	}

	@Column(name = "WRITE_OFF_SINGLE_NO", length = 50)
	public String getWriteOffSingleNo()
	{
		return this.writeOffSingleNo;
	}

	public void setWriteOffSingleNo(String writeOffSingleNo)
	{
		this.writeOffSingleNo = writeOffSingleNo;
	}

	@Column(name = "SPECIAL_CONDITIONS", length = 500)
	public String getSpecialConditions()
	{
		return this.specialConditions;
	}

	public void setSpecialConditions(String specialConditions)
	{
		this.specialConditions = specialConditions;
	}

	@Column(name = "MATTERS_SHOULD_BE_AMENDED", length = 500)
	public String getMattersShouldBeAmended()
	{
		return this.mattersShouldBeAmended;
	}

	public void setMattersShouldBeAmended(String mattersShouldBeAmended)
	{
		this.mattersShouldBeAmended = mattersShouldBeAmended;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId()
	{
		return this.deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime()
	{
		return this.applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime()
	{
		return this.approvedTime;
	}

	public void setApprovedTime(String approvedTime)
	{
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable()
	{
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime()
	{
		return this.creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator()
	{
		return this.creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	@Column(name = "BILL_CONDITIONS", length = 2000)
	public String getBillConditions()
	{
		return billConditions;
	}

	public void setBillConditions(String billConditions)
	{
		this.billConditions = billConditions;
	}

	@Column(name = "CURRENCY", length = 10)
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Column(name = "RATE")
	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	@Column(name = "VALID_DATE", length = 10)
	public String getValidDate()
	{
		return validDate;
	}

	public void setValidDate(String validDate)
	{
		this.validDate = validDate;
	}

	@Override
	public void setWorkflowModelId()
	{
		// TODO Auto-generated method stub
		this.workflowModelId = "CREDITENTRY";
	}

	@Override
	public void setWorkflowModelName()
	{
		//this.workflowModelName = "信用证开证申请";

	}

	@Override
	public void setWorkflowProcessName()
	{
//		this.workflowProcessName = "credit_application_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "creditEntryController.spr?action=creditEntryExamine";

	}
	@Column(name = "avail_Date", length = 10)
	public String getAvailDate() {
		return availDate;
	}

	public void setAvailDate(String availDate) {
		this.availDate = availDate;
	}
	@Column(name = "YMAT_GROUP", length = 36)
	public String getYmatGroup() {
		return ymatGroup;
	}

	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}

}