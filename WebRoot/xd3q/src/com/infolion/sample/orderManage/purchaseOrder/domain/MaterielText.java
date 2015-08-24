/**
 * @(#) MaterielText.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：Jun 4, 2009 2:39:49 PM
 *    描　述：创建
 */

package com.infolion.sample.orderManage.purchaseOrder.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * sap MAKT物料描述表
 */
@Entity
@Table(name = "MAKT")
public class MaterielText
{
	@Column(name = "MANDT")
	private String client;

	/**
	 * 物料号
	 */
	@Id
	@Column(name = "MATNR")
	private Integer materielNo;

	/**
	 * 语言
	 */
	@Column(name = "SPRAS")
	private String lang;

	/**
	 * 描述
	 */
	@Column(name = "MAKTX")
	private String desc;
	
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "MATNR", nullable = true)
	private Materiel materiel;

	public Materiel getMateriel()
	{
		return materiel;
	}

	public void setMateriel(Materiel materiel)
	{
		this.materiel = materiel;
	}

	public String getClient()
	{
		return client;
	}

	public void setClient(String client)
	{
		this.client = client;
	}

	public Integer getMaterielNo()
	{
		return materielNo;
	}

	public void setMaterielNo(Integer materielNo)
	{
		this.materielNo = materielNo;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

}
