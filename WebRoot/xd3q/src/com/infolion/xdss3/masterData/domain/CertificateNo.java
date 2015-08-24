/*
 * @(#)VendorTitle.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月07日 10点18分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;
import java.math.BigDecimal;

/**
 * <pre>
 * 凭证号(CertificateNo)实体类
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
public class CertificateNo extends BaseObject
{   
	/*
	 * 公司代码
	 */
	private String bukrs;   
    
	/*
	 * 会计凭证号码
	 */
    private String belnr;   
    
	/*
	 * 会计年度
	 */
    private String gjahr;

	/**
	 * @return the bukrs
	 */
	public String getBukrs() {
		return bukrs;
	}

	/**
	 * @param bukrs the bukrs to set
	 */
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	/**
	 * @return the belnr
	 */
	public String getBelnr() {
		return belnr;
	}

	/**
	 * @param belnr the belnr to set
	 */
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}

	/**
	 * @return the gjahr
	 */
	public String getGjahr() {
		return gjahr;
	}

	/**
	 * @param gjahr the gjahr to set
	 */
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}   
 
    
}
