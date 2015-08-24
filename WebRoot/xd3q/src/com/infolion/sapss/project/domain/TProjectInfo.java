package com.infolion.sapss.project.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;
import com.infolion.sapss.common.WorkflowUtils;

/**
 * TProjectInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PROJECT_INFO")
public class TProjectInfo extends ProcessObject{

	// Fields

	private String projectId;
	private String orgId;
	private String orgName;
	private String underTakerId;
	private String nuderTaker;
	private String projectNo;
	@ValidateRule(dataType = DataType.STRING, label = "协议号", maxLength = 200, required = true)
	private String oldProjectNo;
	private String applyTime;
	private String approvedTime;
	@ValidateRule(dataType = DataType.STRING, label = "项目名称", maxLength = 200, required = true)
	private String projectName;
	private Integer tradeType;
	private String availableDataStart;
	@ValidateRule(dataType = DataType.STRING, label = "终止时间", maxLength = 20, required = true)
	private String availableDataEnd;
	private String providerId;
	private String providerPayType;
	private String providerBalanceType;
	private String providerLinkMan;
	private String providerAddress;
	private String providerTel;
	private String proiderCreditGrade;
	private String providerExplain;
	private String customerId;
	private String customerPayType;
	private String customerBalanceType;
	private String customerLinkMan;
	private String customerAddress;
	private String customerTel;
	private String customerCreditGrade;
	private String customerExplain;
	private String className;
	private String spec;
	private Double no;
	private String sum;
	private String shipmentPort;
	private String destinationPort;
	private String shipmentDate;
	private String projectState;
	private String deptId;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String currency;
	private BigDecimal exchangeRate;
	private String mask;
	private String isCredit;
	private String creditDescription;
	private String ymatGroup;
	private String placeOfProduction;//产地
	private String interestRate;//利息税点
	private String cmd;//是否寄售业务
	
	// Property accessors
	@Id
	@Column(name = "PROJECT_ID", unique = true, nullable = false, length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "ORG_ID", length = 36)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 200)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "UNDER_TAKER_ID", length = 36)
	public String getUnderTakerId() {
		return this.underTakerId;
	}

	public void setUnderTakerId(String underTakerId) {
		this.underTakerId = underTakerId;
	}

	@Column(name = "NUDER_TAKER", length = 200)
	public String getNuderTaker() {
		return this.nuderTaker;
	}

	public void setNuderTaker(String nuderTaker) {
		this.nuderTaker = nuderTaker;
	}

	@Column(name = "PROJECT_NO", length = 200)
	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	@Column(name = "OLD_PROJECT_NO", length = 200)
	public String getOldProjectNo() {
		return this.oldProjectNo;
	}

	public void setOldProjectNo(String oldProjectNo) {
		this.oldProjectNo = oldProjectNo;
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

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "TRADE_TYPE", precision = 22, scale = 0)
	public Integer getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	@Column(name = "AVAILABLE_DATA_START", length = 8)
	public String getAvailableDataStart() {
		return this.availableDataStart;
	}

	public void setAvailableDataStart(String availableDataStart) {
		this.availableDataStart = availableDataStart;
	}

	@Column(name = "AVAILABLE_DATA_END", length = 8)
	public String getAvailableDataEnd() {
		return this.availableDataEnd;
	}

	public void setAvailableDataEnd(String availableDataEnd) {
		this.availableDataEnd = availableDataEnd;
	}

	@Column(name = "PROVIDER_ID", length = 36)
	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Column(name = "PROVIDER_PAY_TYPE", length = 100)
	public String getProviderPayType() {
		return this.providerPayType;
	}

	public void setProviderPayType(String providerPayType) {
		this.providerPayType = providerPayType;
	}

	@Column(name = "PROVIDER_BALANCE_TYPE", length = 100)
	public String getProviderBalanceType() {
		return this.providerBalanceType;
	}

	public void setProviderBalanceType(String providerBalanceType) {
		this.providerBalanceType = providerBalanceType;
	}

	@Column(name = "PROVIDER_LINK_MAN", length = 50)
	public String getProviderLinkMan() {
		return this.providerLinkMan;
	}

	public void setProviderLinkMan(String providerLinkMan) {
		this.providerLinkMan = providerLinkMan;
	}

	@Column(name = "PROVIDER_ADDRESS", length = 300)
	public String getProviderAddress() {
		return this.providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	@Column(name = "PROVIDER_TEL", length = 50)
	public String getProviderTel() {
		return this.providerTel;
	}

	public void setProviderTel(String providerTel) {
		this.providerTel = providerTel;
	}

	@Column(name = "PROIDER_CREDIT_GRADE", length = 50)
	public String getProiderCreditGrade() {
		return this.proiderCreditGrade;
	}

	public void setProiderCreditGrade(String proiderCreditGrade) {
		this.proiderCreditGrade = proiderCreditGrade;
	}

	@Column(name = "PROVIDER_EXPLAIN", length = 1000)
	public String getProviderExplain() {
		return this.providerExplain;
	}

	public void setProviderExplain(String providerExplain) {
		this.providerExplain = providerExplain;
	}

	@Column(name = "CUSTOMER_ID", length = 36)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_PAY_TYPE", length = 100)
	public String getCustomerPayType() {
		return this.customerPayType;
	}

	public void setCustomerPayType(String customerPayType) {
		this.customerPayType = customerPayType;
	}

	@Column(name = "CUSTOMER_BALANCE_TYPE", length = 100)
	public String getCustomerBalanceType() {
		return this.customerBalanceType;
	}

	public void setCustomerBalanceType(String customerBalanceType) {
		this.customerBalanceType = customerBalanceType;
	}

	@Column(name = "CUSTOMER_LINK_MAN", length = 50)
	public String getCustomerLinkMan() {
		return this.customerLinkMan;
	}

	public void setCustomerLinkMan(String customerLinkMan) {
		this.customerLinkMan = customerLinkMan;
	}

	@Column(name = "CUSTOMER_ADDRESS", length = 300)
	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Column(name = "CUSTOMER_TEL", length = 50)
	public String getCustomerTel() {
		return this.customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	@Column(name = "CUSTOMER_CREDIT_GRADE", length = 50)
	public String getCustomerCreditGrade() {
		return this.customerCreditGrade;
	}

	public void setCustomerCreditGrade(String customerCreditGrade) {
		this.customerCreditGrade = customerCreditGrade;
	}

	@Column(name = "CUSTOMER_EXPLAIN", length = 1000)
	public String getCustomerExplain() {
		return this.customerExplain;
	}

	public void setCustomerExplain(String customerExplain) {
		this.customerExplain = customerExplain;
	}

	@Column(name = "CLASS_NAME", length = 200)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "SPEC", length = 200)
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "NO", precision = 126, scale = 0)
	public Double getNo() {
		return this.no;
	}

	public void setNo(Double no) {
		this.no = no;
	}

	@Column(name = "SUM",length = 20)
	public String getSum() {
		return this.sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
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

	@Column(name = "SHIPMENT_DATE", length = 8)
	public String getShipmentDate() {
		return this.shipmentDate;
	}

	public void setShipmentDate(String shipmentData) {
		this.shipmentDate = shipmentData;
	}

	@Column(name = "PROJECT_STATE", length = 10)
	public String getProjectState() {
		return this.projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
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

	@Column(name = "CREATOR_TIME", length = 14)
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

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="1002";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="项目立项申请";
		
	}

	@Override
	public void setWorkflowProcessName() {
		//String name = WorkflowUtils.chooseWorkflowName("project");
//		this.workflowProcessName="project_v3";
		
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl="projectController.spr?action=projectExamine";
		
	}
	@Column(name = "CURRENCY", length = 5)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column(name = "EXCHANGE_RATE",precision=9,scale=5)
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	@Column(name = "MASK")
	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
	
	@Column(name = "IS_CREDIT", length = 1)
	public String getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(String isCredit) {
		this.isCredit = isCredit;
	}
	
	@Column(name = "CREDIT_DESCRIPTION", length = 1000)
	public String getCreditDescription() {
		return creditDescription;
	}

	public void setCreditDescription(String creditDescription) {
		this.creditDescription = creditDescription;
	}
	
	@Column(name = "YMAT_GROUP", length = 36)
	public String getYmatGroup() {
		return ymatGroup;
	}

	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}
	@Column(name = "place_of_production", length = 50)
	public String getPlaceOfProduction() {
		return placeOfProduction;
	}

	public void setPlaceOfProduction(String placeOfProduction) {
		this.placeOfProduction = placeOfProduction;
	}
	
	@Column(name = "interestRate", length = 50)
	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	@Column(name = "cmd", length = 50)
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
}