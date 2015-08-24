/*
 * @(#)SyncMasterLog.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 主数据同步日志(SyncMasterLog)实体类
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
@Entity
@Table(name = "YSYNCMASTERLOG")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SyncMasterLog extends BaseObject
{
	// fields
	/*
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "SYNCLOGID")
	private String synclogid;

	/*
	 * 表名称
	 */
	@Column(name = "SYNCTABLENAME")
	@ValidateRule(dataType = 9, label = "表名称", maxLength = 100, required = false)
	private String synctablename;

	/*
	 * 同步日期
	 */
	@Column(name = "SYNCDATE")
	@ValidateRule(dataType = 9, label = "同步日期", maxLength = 14, required = false)
	private String syncdate;

	/*
	 * 是否成功
	 */
	@Column(name = "ISSUCCEED")
	@ValidateRule(dataType = 9, label = "是否成功", maxLength = 1, required = false)
	private String issucceed;

	/*
	 * 创建时间
	 */
	@Column(name = "CREATETIME")
	@ValidateRule(dataType = 9, label = "创建时间", maxLength = 14, required = false)
	private String createtime;

	/*
	 * 错误信息
	 */
	@Column(name = "ERRMESSAGE")
	@ValidateRule(dataType = 9, label = "错误信息", required = false)
	private String errMessage;

	/**
	 * @return the errMessage
	 */
	public String getErrMessage()
	{
		return errMessage;
	}

	/**
	 * @param errMessage
	 *            the errMessage to set
	 */
	public void setErrMessage(String errMessage)
	{
		this.errMessage = errMessage;
	}

	/**
	 * 功能描述: 获得ID
	 * 
	 * @return ID : String
	 */
	public String getSynclogid()
	{
		return this.synclogid;
	}

	/**
	 * 功能描述: 设置ID
	 * 
	 * @param synclogid
	 *            : String
	 */
	public void setSynclogid(String synclogid)
	{
		this.synclogid = synclogid;
	}

	/**
	 * 功能描述: 获得表名称
	 * 
	 * @return 表名称 : String
	 */
	public String getSynctablename()
	{
		return this.synctablename;
	}

	/**
	 * 功能描述: 设置表名称
	 * 
	 * @param synctablename
	 *            : String
	 */
	public void setSynctablename(String synctablename)
	{
		this.synctablename = synctablename;
	}

	/**
	 * 功能描述: 获得同步日期
	 * 
	 * @return 同步日期 : String
	 */
	public String getSyncdate()
	{
		return this.syncdate;
	}

	/**
	 * 功能描述: 设置同步日期
	 * 
	 * @param syncdate
	 *            : String
	 */
	public void setSyncdate(String syncdate)
	{
		this.syncdate = syncdate;
	}

	/**
	 * 功能描述: 获得是否成功
	 * 
	 * @return 是否成功 : String
	 */
	public String getIssucceed()
	{
		return this.issucceed;
	}

	/**
	 * 功能描述: 设置是否成功
	 * 
	 * @param issucceed
	 *            : String
	 */
	public void setIssucceed(String issucceed)
	{
		this.issucceed = issucceed;
	}

	/**
	 * 功能描述: 获得创建时间
	 * 
	 * @return 创建时间 : String
	 */
	public String getCreatetime()
	{
		return this.createtime;
	}

	/**
	 * 功能描述: 设置创建时间
	 * 
	 * @param createtime
	 *            : String
	 */
	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}

	/**
	 * 默认构造器
	 */
	public SyncMasterLog()
	{
		super();
	}
}
