package com.infolion.sapss.export.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

/**
 * TBaleLoan entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BALE_LOAN")
public class TBaleLoan extends ProcessObject
{

	// Fields

	private String baleLoanId;
	@ValidateRule(dataType = DataType.STRING, label = "信用证号码", maxLength = 36,required = true)	
	private String creditId;
	@ValidateRule(dataType = DataType.STRING, label = "币种", required = true)		
	private String currency;
	@ValidateRule(dataType = DataType.NUMBER, label = "申请金额", required = true)	
	private Double applyMoney;
	private Double realMoney;
	@ValidateRule(dataType = DataType.STRING, label = "银行", required = true)			
	private String bank;
	private String cmd;
	private String deptId;
	private String examineState;
	private String applyTime;
	private String approvedTime;
	private String isAvailable;
	private String creatorTime;
	private String creator;
	private String baleLoanNo;

	// Constructors

	/** default constructor */
	public TBaleLoan()
	{
	}

	/** minimal constructor */
	public TBaleLoan(String baleLoanId)
	{
		this.baleLoanId = baleLoanId;
	}

	/** full constructor */
	public TBaleLoan(String baleLoanId, String creditId, String currency,
			Double applyMoney, Double realMoney, String bank, String cmd,
			String deptId, String examineState, String applyTime,
			String approvedTime, String isAvailable, String creatorTime,
			String creator)
	{
		this.baleLoanId = baleLoanId;
		this.creditId = creditId;
		this.currency = currency;
		this.applyMoney = applyMoney;
		this.realMoney = realMoney;
		this.bank = bank;
		this.cmd = cmd;
		this.deptId = deptId;
		this.examineState = examineState;
		this.applyTime = applyTime;
		this.approvedTime = approvedTime;
		this.isAvailable = isAvailable;
		this.creatorTime = creatorTime;
		this.creator = creator;
	}

	// Property accessors
	@Id
	@Column(name = "BALE_LOAN_ID", unique = true, nullable = false, length = 36)
	public String getBaleLoanId()
	{
		return this.baleLoanId;
	}

	public void setBaleLoanId(String baleLoanId)
	{
		this.baleLoanId = baleLoanId;
	}

	@Column(name = "CREDIT_ID", length = 36)
	public String getCreditId()
	{
		return this.creditId;
	}

	public void setCreditId(String creditId)
	{
		this.creditId = creditId;
	}

	@Column(name = "CURRENCY", length = 4)
	public String getCurrency()
	{
		return this.currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Column(name = "APPLY_MONEY", precision = 16)
	public Double getApplyMoney()
	{
		return this.applyMoney;
	}

	public void setApplyMoney(Double applyMoney)
	{
		this.applyMoney = applyMoney;
	}

	@Column(name = "REAL_MONEY", precision = 16)
	public Double getRealMoney()
	{
		return this.realMoney;
	}

	public void setRealMoney(Double realMoney)
	{
		this.realMoney = realMoney;
	}

	@Column(name = "BANK", length = 2000)
	public String getBank()
	{
		return this.bank;
	}

	public void setBank(String bank)
	{
		this.bank = bank;
	}

	@Column(name = "CMD", length = 2000)
	public String getCmd()
	{
		return this.cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
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

	@Column(name = "EXAMINE_STATE", length = 2)
	public String getExamineState()
	{
		return this.examineState;
	}

	public void setExamineState(String examineState)
	{
		this.examineState = examineState;
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

	@Column(name = "BALE_LOAN_NO", length = 50)	
	public String getBaleLoanNo()
	{
		return baleLoanNo;
	}

	public void setBaleLoanNo(String baleLoanNo)
	{
		this.baleLoanNo = baleLoanNo;
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

	@Override
	public void setWorkflowModelId()
	{
		this.workflowModelId = "1002";
	}

	@Override
	public void setWorkflowModelName()
	{
		this.workflowModelName = "打包贷款";
	}

	@Override
	public void setWorkflowProcessName()
	{
		//this.workflowProcessName = "export_lend_v1";
	}

	@Override
	public void setWorkflowProcessUrl()
	{
		this.workflowProcessUrl = "baleLoanController.spr?action=examine";
	}

}