/*
 * @(#)TParticularWorkflow.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infolion.platform.core.domain.ProcessObject;

@Entity
@Table(name = "T_PARTICULAR_WORKFLOW")
public class TParticularWorkflow extends ProcessObject implements java.io.Serializable {
	private String particularId;
	private String originalBizId;
	private String moduleName;
	private String linkUrl;
	private String createdTime;
	private String creator;

	@Id
	@Column(name="particular_id")
	public String getParticularId() {
		return particularId;
	}
	public void setParticularId(String particularId) {
		this.particularId = particularId;
	}
	@Column(name="original_biz_id")
	public String getOriginalBizId() {
		return originalBizId;
	}
	public void setOriginalBizId(String originalBizId) {
		this.originalBizId = originalBizId;
	}
	@Override
	public void setWorkflowModelId() {
		this.workflowModelId="0000";
		
	}
	@Override
	public void setWorkflowModelName() {
		this.workflowModelName="特批流程";
		
	}
	@Override
	public void setWorkflowProcessName() {
		this.workflowProcessName="";
		
	}
	@Override
	public void setWorkflowProcessUrl() {
		// TODO Auto-generated method stub
		
	}
	@Column(name = "CREATOR", length = 36)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "link_url")
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	@Column(name = "created_time")
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	@Column(name = "module_name")
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
