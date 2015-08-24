/*
 * @(#)PurchasingDoc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 08点56分13秒
 *  描　述：创建
 */
package com.infolion.sample.sales.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * <pre>
 * SAP销售凭证(SalesDoc)实体类,复合主键类。
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
public class SalesDocKey implements java.io.Serializable 
{
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 销售凭证号
	 */
    @Column(name="VBELN")     
    private String vbeln;  

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
	 * @return the vbeln
	 */
	public String getVbeln()
	{
		return vbeln;
	}

	/**
	 * @param vbeln the vbeln to set
	 */
	public void setVbeln(String vbeln)
	{
		this.vbeln = vbeln;
	}

	/**
	 * @param client
	 * @param vbeln
	 */
	public SalesDocKey(String client, String vbeln)
	{
		super();
		this.client = client;
		this.vbeln = vbeln;
	}

	/**
	 * 
	 */
	public SalesDocKey()
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
		result = prime * result + ((vbeln == null) ? 0 : vbeln.hashCode());
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
		SalesDocKey other = (SalesDocKey) obj;
		if (client == null)
		{
			if (other.client != null)
				return false;
		}
		else if (!client.equals(other.client))
			return false;
		if (vbeln == null)
		{
			if (other.vbeln != null)
				return false;
		}
		else if (!vbeln.equals(other.vbeln))
			return false;
		return true;
	}
}
