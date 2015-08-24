/**
 * @(#) Attachement.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：May 14, 2009 1:04:05 PM
 *    描　述：创建
 */

package com.infolion.platform.dpframework.uicomponent.attachement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * 
 * <pre>
 * 业务附件明细表
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Table(name = "YATTACHEMENT")
public class Attachement  extends BaseObject
{
	/**
	 * 附件ID
	 */
	@Id
	@Column(name = "ATTACHEMENTID" , unique = true, nullable = false ,length = 36)
	private String attachementId;

	/**
	 * 业务编号
	 */
	@Column(name="BUSINESSID",length = 36)
	private String businessId;

	/**
	 * 业务对象名称
	 */
	@Column(name="BONAME")
	private String boName;
	/**
	 * 业务对象属性名称
	 */
	@Column(name = "BOPROPERTY")
	private String boProperty;
	/**
	 * 文件名
	 */
	@Column(name = "FILENAME")
	private String fileName;

	/**
	 * 附件描述
	 */
	@Column(name = "DESCRIPTION",nullable = true)
	private String description;

	/**
	 * 文件流 columnDefinition = "BLOB"
	 */
	@Column(name = "FILESTORE",columnDefinition = "BLOB")
	@Type(type = "org.springframework.orm.hibernate3.support.BlobByteArrayType")
	private byte[] fileStore;

	/**
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/**
	 * 建立者
	 */
	@Column(name = "CREATOR")
	private String creator;

	/**
	 * 建立时间
	 */
	@Column(name = "CREATETIME")
	private String createTime;

	/**
	 * 最后修改者
	 */
	@Column(name = "LASTMODIFYOR")
	private String lastModifyer;

	/**
	 * 最后修改时间
	 */
	@Column(name = "LASTMODIFYTIME")
	private String lastModifyTime;
	

	public Attachement()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造器
	 * 
	 * @param attachementId
	 * @param boProperty
	 *            TODO
	 * @param fileName
	 * @param description
	 * @param fileStore
	 * @param creator
	 * @param createTime
	 * @param lastModifyer
	 * @param lastModifyTime
	 * @param attachementBusiness
	 */
	public Attachement(String attachementId, String businessId, String boName,
			String boProperty, String fileName, String description,
			byte[] fileStore, String client, String creator,
			String createTime, String lastModifyer, String lastModifyTime)
	{
		super();
		this.attachementId = attachementId;
		this.businessId = businessId;
		this.boName = boName;
		this.boProperty = boProperty;
		this.fileName = fileName;
		this.description = description;
		this.fileStore = fileStore;
		this.client = client;
		this.creator = creator;
		this.createTime = createTime;
		this.lastModifyer = lastModifyer;
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return the boProperty
	 */
	public String getBoProperty()
	{
		return boProperty;
	}

	/**
	 * @param boProperty
	 *            the boProperty to set
	 */
	public void setBoProperty(String boProperty)
	{
		this.boProperty = boProperty;
	}

	public String getBusinessId()
	{
		return businessId;
	}

	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}

	public String getBoName()
	{
		return boName;
	}

	public void setBoName(String boName)
	{
		this.boName = boName;
	}

	public String getAttachementId()
	{
		return attachementId;
	}

	public void setAttachementId(String attachementId)
	{
		this.attachementId = attachementId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public byte[] getFileStore()
	{
		return fileStore;
	}

	public void setFileStore(byte[] fileStore)
	{
		this.fileStore = fileStore;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getLastModifyer()
	{
		return lastModifyer;
	}

	public void setLastModifyer(String lastModifyer)
	{
		this.lastModifyer = lastModifyer;
	}

	public String getLastModifyTime()
	{
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}

	public void setClient(String client)
	{
		this.client = client;
	}

	public String getClient()
	{
		return client;
	}
	
	
}
