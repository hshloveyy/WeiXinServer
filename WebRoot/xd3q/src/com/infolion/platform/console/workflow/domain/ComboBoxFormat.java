/*
 * @(#)ComboBoxFormat.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.domain;

import com.infolion.platform.core.domain.BaseObject;

public class ComboBoxFormat extends BaseObject {
	private String Id;
	private String Text;
	private String Nick;
	
	public ComboBoxFormat() {
		super();
	}

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public String getNick() {
		return Nick;
	}
	public void setNick(String nick) {
		Nick = nick;
	}
}
