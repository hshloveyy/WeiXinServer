package com.infolion.sapss.storageProtocol.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_STORAGE_PROTOCOL_C")
public class TStorageProtocolC {
	
	private String storageProtocolCId;
	private String storageProtocolId;
	private String contractId;
	private String contractNo;
	private String sapOrderNo;
	private String cmd;
	private String creatorTime;
	private String creator;
	private String paperNo;
	private String contractType;
	
	@Id
	@Column(name = "storage_Protocol_C_Id", unique = true, nullable = false, length = 36)
	public String getStorageProtocolCId() {
		return storageProtocolCId;
	}
	public void setStorageProtocolCId(String storageProtocolCId) {
		this.storageProtocolCId = storageProtocolCId;
	}
	@Column(name = "contract_Id", length = 36)
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	@Column(name = "contract_NO", length = 36)
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(name = "sap_Order_No", length = 36)
	public String getSapOrderNo() {
		return sapOrderNo;
	}
	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}
	@Column(name = "cmd", length = 500)
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	@Column(name = "creator_Time", length = 20)
	public String getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}
	@Column(name = "creator", length = 36)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "paper_No", length = 36)
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	@Column(name = "CONTRACT_TYPE", length = 36)
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	@Column(name = "storage_Protocol_Id", length = 36)
	public String getStorageProtocolId() {
		return storageProtocolId;
	}
	public void setStorageProtocolId(String storageProtocolId) {
		this.storageProtocolId = storageProtocolId;
	}


}
