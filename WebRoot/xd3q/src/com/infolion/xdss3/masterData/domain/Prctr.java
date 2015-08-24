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
 * 利润中心(Prctr)实体类
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
public class Prctr extends BaseObject
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 利润中心ID
	 */
	@Id
	@Column(name = "PRCTRID")
	private String prctrid;
	
	/*
	 * 控制范围
	 */
	@Column(name = "KOKRS")
	private String kokrs;
	
	/**
	 * @return the prctrid
	 */
	public String getPrctrid() {
		return prctrid;
	}

	/**
	 * @param prctrid the prctrid to set
	 */
	public void setPrctrid(String prctrid) {
		this.prctrid = prctrid;
	}

	/**
	 * @return the kokrs
	 */
	public String getKokrs() {
		return kokrs;
	}

	/**
	 * @param kokrs the kokrs to set
	 */
	public void setKokrs(String kokrs) {
		this.kokrs = kokrs;
	}

	/**
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	
	/**
	 * 有效截至日期
	 */
	@Column(name = "DATBI")
	@ValidateRule(dataType = 9, label = "有效截至日期", maxLength = 8, required = false)
	private String datbi;
	
	
	/**
	 * 开始生效日期
	 */
	@Column(name = "DATAB")
	@ValidateRule(dataType = 9, label = "开始生效日期", maxLength = 8, required = false)
	private String datab;
	
	
	/**
	 * 利润中心
	 */
	@Column(name = "PRCTR")
	@ValidateRule(dataType = 9, label = "利润中心", maxLength = 10, required = false)
	private String prctr;
	
	
	/**
	 * 一般姓名
	 */
	@Column(name = "KTEXT")
	@ValidateRule(dataType = 9, label = "一般姓名", maxLength = 20, required = false)
	private String ktext;
	
	/**
	 * 利润中心组
	 */
	@Column(name = "KHINR")
	@ValidateRule(dataType = 9, label = "利润中心组", maxLength = 12, required = false)
	private String khinr;
	

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {		
		if(bukrs == null || bukrs.equals(""))
		{
			this.bukrs = " ";
		}
		else
		{
			this.bukrs = bukrs;
		}	
	}


	/**
	 * @return the datbi
	 */
	public String getDatbi() {
		return datbi;
	}

	/**
	 * @param datbi the datbi to set
	 */
	public void setDatbi(String datbi) {
		
		if(datbi == null || datbi.equals(""))
		{
			this.datbi = " ";
		}
		else
		{
			this.datbi = datbi;
		}	
	}

	/**
	 * @return the datab
	 */
	public String getDatab() {
		return datab;
	}

	/**
	 * @param datab the datab to set
	 */
	public void setDatab(String datab) {
		if(datab == null || datab.equals(""))
		{
			this.datab = " ";
		}
		else
		{
			this.datab = datab;
		}	
	}

	/**
	 * @return the prctr
	 */
	public String getPrctr() {
		return prctr;
	}

	/**
	 * @param prctr the prctr to set
	 */
	public void setPrctr(String prctr) {
		if(prctr == null || prctr.equals(""))
		{
			this.prctr = " ";
		}
		else
		{
			this.prctr = prctr;
		}	
	}

	/**
	 * @return the ktext
	 */
	public String getKtext() {
		return ktext;
	}

	/**
	 * @param ktext the ktext to set
	 */
	public void setKtext(String ktext) {
		if(ktext == null || ktext.equals(""))
		{
			this.ktext = " ";
		}
		else
		{
			this.ktext = ktext;
		}	
	}

	/**
	 * @return the khinr
	 */
	public String getKhinr() {
		return khinr;
	}

	/**
	 * @param khinr the khinr to set
	 */
	public void setKhinr(String khinr) {
		if(khinr == null || khinr.equals(""))
		{
			this.khinr = " ";
		}
		else
		{
			this.khinr = khinr;
		}	
	}
	
	
}
