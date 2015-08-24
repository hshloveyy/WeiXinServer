/**
 * @(#) Materiel.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：Jun 4, 2009 2:39:49 PM
 *    描　述：创建
 */

package com.infolion.sample.orderManage.purchaseOrder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * 对应sap MARA 表
 */
@Entity
@Table(name = "MARA")
public class Materiel extends BaseObject
{
	/**
	 * MATNR
	 */
	@Id
	@Column(name = "MATNR")
	private String materielNo;
	
	@Column(name = "MANDT")
	private String client;

	@OneToOne(mappedBy = "materiel")
	private MaterielText materielText;

	/**
	 * 基本单位MEINS
	 */
	@Column(name = "MEINS")
	private String unit;

	/**
	 * 销售单位不可变
	 */
	@Column(name = "KZEFF")
	private String kzeff;

	/**
	 * 物料描述
	 */
	@Transient
	private String desc;
	/**
	 * 有效日期起DATAB
	 */
	private String validDateStart;

	public String getClient()
	{
		return client;
	}

	public void setClient(String client)
	{
		this.client = client;
	}


	/**
	 * @return the materielNo
	 */
	public String getMaterielNo()
	{
		return materielNo;
	}

	/**
	 * @param materielNo
	 *            the materielNo to set
	 */
	public void setMaterielNo(String materielNo)
	{
		this.materielNo = materielNo;
	}

	/**
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public MaterielText getMaterielText()
	{
		return materielText;
	}

	public void setMaterielText(MaterielText materielText)
	{
		this.materielText = materielText;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getKzeff()
	{
		return kzeff;
	}

	public void setKzeff(String kzeff)
	{
		this.kzeff = kzeff;
	}

	public String getValidDateStart()
	{
		return validDateStart;
	}

	public void setValidDateStart(String validDateStart)
	{
		this.validDateStart = validDateStart;
	}

}
