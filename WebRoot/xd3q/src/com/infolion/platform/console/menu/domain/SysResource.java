/*
 * @(#)TSysResourceObject.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-19
 *  描　述：创建
 */

package com.infolion.platform.console.menu.domain;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SysResource extends BaseObject {
	private String resourceid;
	
	@ValidateRule(dataType = DataType.STRING, label = "资源类型", maxLength = 1,required = true)
	private String typeid;
	
	@ValidateRule(dataType = DataType.STRING, label = "资源名称", maxLength = 100,required = true)
	private String resourcename;
	
	@ValidateRule(dataType = DataType.STRING, label = "资源标题", maxLength = 100,required = true)
	private String resourcetitle;
	
	@ValidateRule(dataType = DataType.STRING, label = "资源地址", maxLength = 300)
	private String resourceurl;
	
	@ValidateRule(dataType = DataType.STRING, label = "资源图标", maxLength = 100)
	private String resourceicon;
	private String displayno;
	
	private String parentid;
	
	private String cmd;
	private String creator;
	private String create_time;
	private String deleted;

	private String parenttitle;
	private String typename;
	private int childcount;
	private int ischeck;
	
	public SysResource(String id, String typeid, String resourcename,
			String resourcetitle, String resourceurl, String resourceicon,
			String parentid, String cmd) {
		super();
		this.resourceid = id;
		this.typeid = typeid;
		this.resourcename = resourcename;
		this.resourcetitle = resourcetitle;
		this.resourceurl = resourceurl;
		this.resourceicon = resourceicon;
		this.parentid = parentid;
		this.cmd = cmd;
	}

	public SysResource() {
		super();
	}
	
	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}


	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getResourcetitle() {
		return resourcetitle;
	}

	public void setResourcetitle(String resourcetitle) {
		this.resourcetitle = resourcetitle;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	public String getResourceicon() {
		return resourceicon;
	}

	public void setResourceicon(String resourceicon) {
		this.resourceicon = resourceicon;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getParenttitle() {
		return parenttitle;
	}

	public void setParenttitle(String parenttitle) {
		this.parenttitle = parenttitle;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getChildcount() {
		return childcount;
	}

	public void setChildcount(int childcount) {
		this.childcount = childcount;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public String getDisplayno() {
		return displayno;
	}

	public void setDisplayno(String displayno) {
		this.displayno = displayno;
	}
}
