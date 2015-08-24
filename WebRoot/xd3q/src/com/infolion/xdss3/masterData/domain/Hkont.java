/*
 * @(#)Supplier.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 会计科目(Hkont)实体类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class Hkont extends BaseObject
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;
	
	/*
	 * 总帐科目编号
	 */
	@Column(name = "SAKNR")
	private String saknr;

	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	
	/**
	 * 账目表
	 */
	@Column(name = "KTOPL")
	@ValidateRule(dataType = 9, label = "账目表", maxLength = 4, required = false)
	private String ktopl;
	
	
	/*
	 * 文本
	 */
	@Column(name = "TXT20")
	@ValidateRule(dataType = 9, label = "文本", maxLength = 20, required = false)
	private String txt20;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSaknr() {
		return saknr;
	}

	public void setSaknr(String saknr) {
		this.saknr = saknr;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getTxt20() {
		return txt20;
	}

	public void setTxt20(String txt20) {
		this.txt20 = txt20;
	}

	/**
	 * @return the ktopl
	 */
	public String getKtopl() {
		return ktopl;
	}

	/**
	 * @param ktopl the ktopl to set
	 */
	public void setKtopl(String ktopl) {
		this.ktopl = ktopl;
	}
	
	
}
