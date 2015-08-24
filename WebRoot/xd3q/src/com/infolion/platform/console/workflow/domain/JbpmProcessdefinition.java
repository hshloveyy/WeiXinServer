/*
 * @(#)jbpmProcessdefinition.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-8
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.domain;

import com.infolion.platform.core.domain.BaseObject;

public class JbpmProcessdefinition extends BaseObject {
	private String id_;
	private String class_;
	private String name_;
	private String description_;
	private String version_;
	private String isterminationimplicit_;
	private String startstate_;
	
	public JbpmProcessdefinition() {
		super();
	}
	
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public String getClass_() {
		return class_;
	}
	public void setClass_(String class_) {
		this.class_ = class_;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getDescription_() {
		return description_;
	}
	public void setDescription_(String description_) {
		this.description_ = description_;
	}
	public String getVersion_() {
		return version_;
	}
	public void setVersion_(String version_) {
		this.version_ = version_;
	}
	public String getIsterminationimplicit_() {
		return isterminationimplicit_;
	}
	public void setIsterminationimplicit_(String isterminationimplicit_) {
		this.isterminationimplicit_ = isterminationimplicit_;
	}
	public String getStartstate_() {
		return startstate_;
	}
	public void setStartstate_(String startstate_) {
		this.startstate_ = startstate_;
	}
}
