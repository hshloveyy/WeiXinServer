package com.infolion.sapss.account.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.validation.DataType;

@Entity
@Table(name = "T_INNER_TRANSFER")
public class TInnerTransferInfo extends ProcessObject {
	private static final long serialVersionUID = -7263283250930399670L;
	private String transferID;// 主键
	private String orgID;// 申请单位ID
	private String orgName;// 申请单位
	private String creatorID; // 创建人ID
	private String creatorName; // 创建人
	private String createTime; // 创建时间
	private String isAvailable;// 0无效,1有效
	private String approveState;// 审批状态
	private String applyTime;// 申报时间
	private String approvedTime;// 审批通过时间
	@ValidateRule(dataType = DataType.STRING, label = "付款形式", maxLength = 10, required = true)
	private String payType;// 付款形式
	@ValidateRule(dataType = DataType.STRING, label = "付款人", maxLength = 50, required = true)
	private String payer;// 付款人
	@ValidateRule(dataType = DataType.STRING, label = "收款人", maxLength = 50, required = true)
	private String receiver;// 收款人
	// 关联的多条付款信息
	@OneToMany(mappedBy = "innerTransferInfo", cascade = CascadeType.ALL)
	private List<TInnerTransferDetail> innerTransferDetail = new ArrayList<TInnerTransferDetail>();

	@Transient
	public List<TInnerTransferDetail> getInnerTransferDetail() {
		return innerTransferDetail;
	}

	public void setInnerTransferDetail(
			List<TInnerTransferDetail> innerTransferDetail) {
		this.innerTransferDetail = innerTransferDetail;
	}

	@Id
	@Column(name = "TRANSFER_ID", unique = true, nullable = false, length = 36)
	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IS_AVAILABLE", length = 1)
	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Column(name = "APPROVE_STATE", length = 10)
	public String getApproveState() {
		return approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	@Column(name = "APPLY_TIME", length = 20)
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPROVED_TIME", length = 20)
	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "ORG_NAME", length = 200)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "ORG_ID", length = 36)
	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	@Column(name = "CREATOR_ID", length = 36)
	public String getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	@Column(name = "CREATOR_NAME", length = 20)
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "PAY_TYPE", length = 10)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "PAYER", length = 50)
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	@Column(name = "RECEIVER", length = 50)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setWorkflowProcessUrl() {
		this.workflowProcessUrl = "innerTransferController.spr?action=transferExamine";
	}

	@Override
	public void setWorkflowModelId() {
		this.workflowModelId = "1034";
	}

	@Override
	public void setWorkflowModelName() {
		this.workflowModelName = "内部划拨申请";
	}

	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName = "inner_transfer_approve_v1";
	}
}
