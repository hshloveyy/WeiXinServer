package com.infolion.sapss.storageProtocol.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

@Entity
@Table(name = "T_STORAGEPROTOCOL_CHANGE")
public class TStorageProtocolChange extends ProcessObject{

	@Id
	@Column(name = "CHANGE_ID", unique = true, nullable = false, length = 36)
	private String changeId;
	@Column(name="INFO_ID")
	private String infoId;
	@Column(name="CHANGE_DESC")
	private String changeDesc;
	@Column(name="CHANGE_TIME")
	private String changeTime;
	@Column(name="CHANGER")
	private String changer;
	@Column(name="CREATE_TIME")
	private String createTime;
	@Column(name="CREATOR")
	private String creator;
	@Column(name="MASK")
	private String mask;
	@Column(name="CHANGE_NO")
	private String changeNo;
	@Column(name="APPLY_TIME")
	private String applyTime;
	@Column(name="APPROVED_TIME")
	private String approvedTime;
	@Column(name="IS_ALLOW")
	private String isAllow;
	@Column(name="IS_CHANGED")
	private String isChanged;
	@Column(name="CHANGE_OPERATOR")
	private String changeOperator;
	@Column(name="CHANGE_NOTE")
	private String changeNote;
	@Column(name="OPERATE_TIME")
	private String operateTime;
	@Column(name="CHANGE_STATE")
	private String changeState;
	@Column(name="IS_AVAILABLE")
	private String isAvailable;
	
	
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getChangeDesc() {
		return changeDesc;
	}
	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getChanger() {
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getChangeNo() {
		return changeNo;
	}
	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	public String getIsAllow() {
		return isAllow;
	}
	public void setIsAllow(String isAllow) {
		this.isAllow = isAllow;
	}
	public String getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}
	public String getChangeOperator() {
		return changeOperator;
	}
	public void setChangeOperator(String changeOperator) {
		this.changeOperator = changeOperator;
	}
	public String getChangeNote() {
		return changeNote;
	}
	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getChangeState() {
		return changeState;
	}
	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="1002";
		
	}
	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="仓储协议变更申请";
		
	}
	@Override
	public void setWorkflowProcessName() {
//		this.workflowProcessName="project_v1";
//		this.workflowProcessName="project_modify_v1";
		
	}
	@Override
	public void setWorkflowProcessUrl() {
		//this.workflowProcessUrl="projectController.spr?action=projectExamine";
		this.workflowProcessUrl="storageProtocolController.spr?action=changeExamine";
	}

}
