/*
 * @(#)PurchasingDoc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 08点56分13秒
 *  描　述：创建
 */
package com.infolion.sample.purchase.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * <pre>
 * SAP采购凭证(PurchasingDoc)实体类,复合主键类。
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
@Embeddable 
public class PurchasingDocItemKey implements java.io.Serializable 
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 采购凭证号
	 */
	@Column(name = "EBELP")
	private String ebelp;

	@Column(name = "EBELN")
	private String ebeln;

	/**
	 * @return the client
	 */
	public String getClient()
	{
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * @return the ebelp
	 */
	public String getEbelp()
	{
		return ebelp;
	}

	/**
	 * @param ebelp the ebelp to set
	 */
	public void setEbelp(String ebelp)
	{
		this.ebelp = ebelp;
	}

	/**
	 * @return the ebeln
	 */
	public String getEbeln()
	{
		return ebeln;
	}

	/**
	 * @param ebeln the ebeln to set
	 */
	public void setEbeln(String ebeln)
	{
		this.ebeln = ebeln;
	}

	/**
	 * @param client
	 * @param ebelp
	 * @param ebeln
	 */
	public PurchasingDocItemKey(String client, String ebelp, String ebeln)
	{
		super();
		this.client = client;
		this.ebelp = ebelp;
		this.ebeln = ebeln;
	}

	/**
	 * 
	 */
	public PurchasingDocItemKey()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((ebeln == null) ? 0 : ebeln.hashCode());
		result = prime * result + ((ebelp == null) ? 0 : ebelp.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchasingDocItemKey other = (PurchasingDocItemKey) obj;
		if (client == null)
		{
			if (other.client != null)
				return false;
		}
		else if (!client.equals(other.client))
			return false;
		if (ebeln == null)
		{
			if (other.ebeln != null)
				return false;
		}
		else if (!ebeln.equals(other.ebeln))
			return false;
		if (ebelp == null)
		{
			if (other.ebelp != null)
				return false;
		}
		else if (!ebelp.equals(other.ebelp))
			return false;
		return true;
	}

}
