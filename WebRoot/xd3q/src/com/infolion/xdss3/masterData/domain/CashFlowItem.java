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
 * 现金流量项(CashFlowItem)实体类
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
public class CashFlowItem extends BaseObject
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;
	
	/**
	 * 现金流量项代码
	 */
	@Column(name = "RSTGR")
	private String rstgr;

	/**
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	
	/**
	 * 语言代码
	 */
	@Column(name = "SPRAS")
	@ValidateRule(dataType = 9, label = "语言代码", maxLength = 1, required = false)
	private String spras;
	
	
	/**
	 * 短文本
	 */
	@Column(name = "TXT20")
	@ValidateRule(dataType = 9, label = "文本", maxLength = 20, required = false)
	private String txt20;
	
	/**
	 * 长文本
	 */
	@Column(name = "TXT40")
	@ValidateRule(dataType = 9, label = "文本", maxLength = 40, required = false)
	private String txt40;
	
	

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

	public String getTxt20() {
		return txt20;
	}

	public void setTxt20(String txt20) {
		
		if(txt20 == null || txt20.equals(""))
		{
			this.txt20 = " ";
		}
		else
		{
			this.txt20 = txt20;
		}	
	}

	/**
	 * @return the rstgr
	 */
	public String getRstgr() {
		return rstgr;
	}

	/**
	 * @param rstgr the rstgr to set
	 */
	public void setRstgr(String rstgr) {
		
		if(rstgr == null || rstgr.equals(""))
		{
			this.rstgr = " ";
		}
		else
		{
			this.rstgr = rstgr;
		}		
	}

	/**
	 * @return the spras
	 */
	public String getSpras() {
		return spras;
	}

	/**
	 * @param spras the spras to set
	 */
	public void setSpras(String spras) {
		if(spras == null || spras.equals(""))
		{
			this.spras = " ";
		}
		else
		{
			this.spras = spras;
		}		
	}

	/**
	 * @return the txt40
	 */
	public String getTxt40() {
		return txt40;
	}

	/**
	 * @param txt40 the txt40 to set
	 */
	public void setTxt40(String txt40) {
		if(txt40 == null || txt40.equals(""))
		{
			this.txt40 = " ";
		}
		else
		{
			this.txt40 = txt40;
		}		
	}
	
	
}
