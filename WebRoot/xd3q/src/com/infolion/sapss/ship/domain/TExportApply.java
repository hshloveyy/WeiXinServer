/*
 * @(#)TExportApply.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 9, 2009
 *  描　述：创建
 */

package com.infolion.sapss.ship.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.4
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@Table(name = "T_EXPORT_APPLY")
public class TExportApply extends ProcessObject
{
	private String exportApplyId;
	private String contractSalesId;
	private String tradeType;
	private String projectNo;
	private String projectName;
	private String contractGroupNo;
	private String salesNo;
	private String sapOrderNo;
	private String billState;
	private String noticeNo;
	private String exchangeType;
	private String origin;
	private String shipmentDate;
	private String lcno;
	private String commitment;
	private String exportPort;
	@ValidateRule(dataType = DataType.STRING, label = "核销单号", maxLength = 30,required = false)
	private String writeTo;
	private String supplier;
	private String destinations;
	private String tradeTerms;
	private String tradeTerms2;
	private String deptId;
	private String applyTime;
	private String approvedTime;
	private String isAvaiLable;
	private String creatorTime;
	private String creator;
	private String examineState;
	private String cmd;
	private String isPay;
	private String getSheetTime;
	@ValidateRule(dataType = DataType.STRING, label = "客户名称", maxLength = 100,required = true)
	private String customer;
	private String contractPaperNo;//外部纸质合同号
	private String referSheetTime;//交单时间

	@Id
	@Column(name = "EXPORT_APPLY_ID", unique = true, length = 36)
	public String getExportApplyId()
	{
		return exportApplyId;
	}

	public void setExportApplyId(String exportApplyId)
	{
		this.exportApplyId = exportApplyId;
	}

	@Column(name = "CONTRACT_SALES_ID", length = 36)
	public String getContractSalesId()
	{
		return contractSalesId;
	}

	public void setContractSalesId(String contractSalesId)
	{
		this.contractSalesId = contractSalesId;
	}

	@Column(name = "TRADE_TYPE", length = 2)
	public String getTradeType()
	{
		return tradeType;
	}

	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}

	@Column(name = "PROJECT_NO", length = 50)
	public String getProjectNo()
	{
		return projectNo;
	}

	public void setProjectNo(String projectNo)
	{
		this.projectNo = projectNo;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_GROUP_NO", length = 30)
	public String getContractGroupNo()
	{
		return contractGroupNo;
	}

	public void setContractGroupNo(String contractGroupNo)
	{
		this.contractGroupNo = contractGroupNo;
	}

	@Column(name = "SALES_NO", length = 50)
	public String getSalesNo()
	{
		return salesNo;
	}

	public void setSalesNo(String salesNo)
	{
		this.salesNo = salesNo;
	}

	@Column(name = "SAP_ORDER_NO", length = 50)
	public String getSapOrderNo()
	{
		return sapOrderNo;
	}

	public void setSapOrderNo(String sapOrderNo)
	{
		this.sapOrderNo = sapOrderNo;
	}

	@Column(name = "BILL_STATE", length = 1)
	public String getBillState()
	{
		return billState;
	}

	public void setBillState(String billState)
	{
		this.billState = billState;
	}

	@Column(name = "NOTICE_NO", length = 1)
	public String getNoticeNo()
	{
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo)
	{
		this.noticeNo = noticeNo;
	}

	@Column(name = "EXCHANGE_TYPE", length = 50)
	public String getExchangeType()
	{
		return exchangeType;
	}

	public void setExchangeType(String exchangeType)
	{
		this.exchangeType = exchangeType;
	}

	@Column(name = "ORIGIN", length = 50)
	public String getOrigin()
	{
		return origin;
	}

	public void setOrigin(String origin)
	{
		this.origin = origin;
	}

	@Column(name = "SHIPMENT_DATE", length = 50)
	public String getShipmentDate()
	{
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate)
	{
		this.shipmentDate = shipmentDate;
	}

	@Column(name = "LCNO", length = 50)
	public String getLcno()
	{
		return lcno;
	}

	public void setLcno(String lcno)
	{
		this.lcno = lcno;
	}

	@Column(name = "COMMITMENT", length = 50)
	public String getCommitment()
	{
		return commitment;
	}

	public void setCommitment(String commitment)
	{
		this.commitment = commitment;
	}

	@Column(name = "EXPORT_PORT", length = 50)
	public String getExportPort()
	{
		return exportPort;
	}

	public void setExportPort(String exportPort)
	{
		this.exportPort = exportPort;
	}

	@Column(name = "WRITE_NO", length = 50)
	public String getWriteTo()
	{
		return writeTo;
	}

	public void setWriteTo(String writeTo)
	{
		this.writeTo = writeTo;
	}

	@Column(name = "SUPPLIER", length = 50)
	public String getSupplier()
	{
		return supplier;
	}

	public void setSupplier(String supplier)
	{
		this.supplier = supplier;
	}

	@Column(name = "DESTINATIONS", length = 50)
	public String getDestinations()
	{
		return destinations;
	}

	public void setDestinations(String destinations)
	{
		this.destinations = destinations;
	}

	@Column(name = "TRADE_TERMS", length = 50)
	public String getTradeTerms()
	{
		return tradeTerms;
	}

	public void setTradeTerms(String tradeTerms)
	{
		this.tradeTerms = tradeTerms;
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

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime()
	{
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime)
	{
		this.approvedTime = approvedTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvaiLable()
	{
		return isAvaiLable;
	}

	public void setIsAvaiLable(String isAvaiLable)
	{
		this.isAvaiLable = isAvaiLable;
	}

	@Column(name = "CREATOR_TIME", length = 20)
	public String getCreatorTime()
	{
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime)
	{
		this.creatorTime = creatorTime;
	}

	@Column(name = "CREATOR", length = 36)
	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState()
	{
		return examineState;
	}

	public void setExamineState(String examineState)
	{
		this.examineState = examineState;
	}

	@Column(name = "TRADE_TERMS2", length = 50)
	public String getTradeTerms2()
	{
		return tradeTerms2;
	}

	public void setTradeTerms2(String tradeTerms2)
	{
		this.tradeTerms2 = tradeTerms2;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}
	
	@Column(name = "ISPAY", length = 1)
	public String getIsPay()
	{
		return isPay;
	}

	public void setIsPay(String isPay)
	{
		this.isPay = isPay;
	}
	
	@Column(name = "GET_SHEET_TIME", length = 36)
	public String getGetSheetTime()
	{
		return getSheetTime;
	}

	public void setGetSheetTime(String getSheetTime)
	{
		this.getSheetTime = getSheetTime;
	}
	
	@Override
	public void setWorkflowModelId()
	{
		this.workflowModelId = "1002";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "出口货物通知单申请";
	}

	@Override
	public void setWorkflowProcessName()
	{
		//this.workflowProcessName = "export_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "shipNotifyController.spr?action=shipNotifyExamine";
	}
	@Column(name = "customer", length = 100)
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Column(name = "contract_Paper_No", length = 50)
	public String getContractPaperNo() {
		return contractPaperNo;
	}

	public void setContractPaperNo(String contractPaperNo) {
		this.contractPaperNo = contractPaperNo;
	}
	
	@Column(name = "REFER_SHEET_TIME", length = 20)
	public String getReferSheetTime()
	{
		return referSheetTime;
	}

	public void setReferSheetTime(String referSheetTime)
	{
		this.referSheetTime = referSheetTime;
	}

}
