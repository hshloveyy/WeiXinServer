/*
 * @(#)JbpmTask.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.domain;

import com.infolion.platform.core.domain.BaseObject;

public class JbpmTask extends BaseObject {
	private String id;
	private String name;
	private String description;
	private String processdefitionId;
	private String processdefitionName;
	private int childcount;
	private String showText;
	
	public JbpmTask() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessdefitionName() {
		return processdefitionName;
	}

	public void setProcessdefitionName(String processdefitionName) {
		this.processdefitionName = processdefitionName;
	}

	public String getProcessdefitionId() {
		return processdefitionId;
	}

	public void setProcessdefitionId(String processdefitionId) {
		this.processdefitionId = processdefitionId;
	}

	public int getChildcount() {
		return childcount;
	}

	public void setChildcount(int childcount) {
		this.childcount = childcount;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}
	
}
