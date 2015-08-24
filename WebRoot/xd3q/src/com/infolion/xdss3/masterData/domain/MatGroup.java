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
 * 物料组(MatGroup)实体类
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
public class MatGroup extends BaseObject
{	
	/**
	 * 物料组
	 */
	private String matkl;

	/**
	 * 物料组描述
	 */
	private String wgbez;

	/**
	 * @return the matkl
	 */
	public String getMatkl() {
		return matkl;
	}

	/**
	 * @param matkl the matkl to set
	 */
	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}

	/**
	 * @return the wgbez
	 */
	public String getWgbez() {
		return wgbez;
	}

	/**
	 * @param wgbez the wgbez to set
	 */
	public void setWgbez(String wgbez) {
		this.wgbez = wgbez;
	}

	
	
}
