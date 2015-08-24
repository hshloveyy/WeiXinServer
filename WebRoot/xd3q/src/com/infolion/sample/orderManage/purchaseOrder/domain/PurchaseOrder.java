/*
 * @(#)PurchaseOrder.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分57秒
 *  描　述：创建
 */
package com.infolion.sample.orderManage.purchaseOrder.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;

/**
 * <pre>
 * 采购订单(PurchaseOrder)实体类
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
@Table(name = "YPURCHASEORDER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class PurchaseOrder extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 供应商或债权人的帐号
	 */
	@Column(name = "SUPPLIERNO")
	@ValidateRule(dataType = 9, label = "供应商或债权人的帐号", maxLength = 10, required = true)
	private String supplierNo;

	/*
	 * 备注
	 */
	@Column(name = "MEMO")
	@ValidateRule(dataType = 9, label = "备注", maxLength = 128, required = true)
	private String memo;

	/*
	 * 销售员
	 */
	@Column(name = "SALESPEOPLE")
	@ValidateRule(dataType = 9, label = "销售员", maxLength = 36, required = false)
	private String salespeople;

	/*
	 * 订单业务ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "PURCHASEORDERID")
	private String purchaseOrderId;

	/*
	 * 订单号
	 */
	@Column(name = "PURCHASEORDERNO")
	@ValidateRule(dataType = 9, label = "订单号", maxLength = 36, required = false)
	private String purchaseOrderNo;

	/*
	 * 订单类型（采购）
	 */
	@Column(name = "CERTIFICATETYPE")
	@ValidateRule(dataType = 9, label = "订单类型（采购）", maxLength = 4, required = true)
	private String certificateType;

	/*
	 * 凭证日期
	 */
	@Column(name = "CERTIFICATEDATE")
	@ValidateRule(dataType = 8, label = "凭证日期", required = true)
	private String certificateDate;

	/*
	 * 地址
	 */
	@Column(name = "ADDRESS")
	@ValidateRule(dataType = 9, label = "地址", maxLength = 150, required = false)
	private String address;

	/*
	 * 创建对象的人员名称
	 */
	@Column(name = "CREATOR")
	@ValidateRule(dataType = 9, label = "创建对象的人员名称", maxLength = 36, required = false)
	private String creator;

	/*
	 * 记录的创建日期
	 */
	@Column(name = "CREATETIME")
	@ValidateRule(dataType = 8, label = "记录的创建日期", required = false)
	private String createTime;

	/*
	 * 对象更改人员的名称
	 */
	@Column(name = "LASTMODIFYOR")
	@ValidateRule(dataType = 9, label = "对象更改人员的名称", maxLength = 36, required = false)
	private String lastModifyor;

	/*
	 * 对象最后更改日期
	 */
	@Column(name = "LASTMODIFYTIME")
	@ValidateRule(dataType = 8, label = "对象最后更改日期", required = false)
	private String lastModifyTime;

	/*
	 * 流程状态
	 */
	@Column(name = "PROCESSSTATE")
	@ValidateRule(dataType = 9, label = "流程状态", maxLength = 50, required = false)
	private String processState;

	/*
	 * 采购行项目
	 */
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<OrderItems> orderItems;

	/**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient()
	{
		return this.client;
	}

	/**
	 * 功能描述: 设置客户端
	 * 
	 * @param client
	 *            : String
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * 功能描述: 获得供应商或债权人的帐号
	 * 
	 * @return 供应商或债权人的帐号 : String
	 */
	public String getSupplierNo()
	{
		return this.supplierNo;
	}

	/**
	 * 功能描述: 设置供应商或债权人的帐号
	 * 
	 * @param supplierNo
	 *            : String
	 */
	public void setSupplierNo(String supplierNo)
	{
		this.supplierNo = supplierNo;
	}

	/**
	 * 功能描述: 获得备注
	 * 
	 * @return 备注 : String
	 */
	public String getMemo()
	{
		return this.memo;
	}

	/**
	 * 功能描述: 设置备注
	 * 
	 * @param memo
	 *            : String
	 */
	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	/**
	 * 功能描述: 获得销售员
	 * 
	 * @return 销售员 : String
	 */
	public String getSalespeople()
	{
		return this.salespeople;
	}

	/**
	 * 功能描述: 设置销售员
	 * 
	 * @param salespeople
	 *            : String
	 */
	public void setSalespeople(String salespeople)
	{
		this.salespeople = salespeople;
	}

	/**
	 * 功能描述: 获得订单业务ID
	 * 
	 * @return 订单业务ID : String
	 */
	public String getPurchaseOrderId()
	{
		return this.purchaseOrderId;
	}

	/**
	 * 功能描述: 设置订单业务ID
	 * 
	 * @param purchaseOrderId
	 *            : String
	 */
	public void setPurchaseOrderId(String purchaseOrderId)
	{
		this.purchaseOrderId = purchaseOrderId;
	}

	/**
	 * 功能描述: 获得订单号
	 * 
	 * @return 订单号 : String
	 */
	public String getPurchaseOrderNo()
	{
		return this.purchaseOrderNo;
	}

	/**
	 * 功能描述: 设置订单号
	 * 
	 * @param purchaseOrderNo
	 *            : String
	 */
	public void setPurchaseOrderNo(String purchaseOrderNo)
	{
		this.purchaseOrderNo = purchaseOrderNo;
	}

	/**
	 * 功能描述: 获得订单类型（采购）
	 * 
	 * @return 订单类型（采购） : String
	 */
	public String getCertificateType()
	{
		return this.certificateType;
	}

	/**
	 * 功能描述: 设置订单类型（采购）
	 * 
	 * @param certificateType
	 *            : String
	 */
	public void setCertificateType(String certificateType)
	{
		this.certificateType = certificateType;
	}

	/**
	 * 功能描述: 获得凭证日期
	 * 
	 * @return 凭证日期 : String
	 */
	public String getCertificateDate()
	{
		return DateUtils.toDisplayStr(this.certificateDate, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置凭证日期
	 * 
	 * @param certificateDate
	 *            : String
	 */
	public void setCertificateDate(String certificateDate)
	{
		certificateDate = DateUtils.toStoreStr(certificateDate);
		this.certificateDate = certificateDate;
	}

	/**
	 * 功能描述: 获得地址
	 * 
	 * @return 地址 : String
	 */
	public String getAddress()
	{
		return this.address;
	}

	/**
	 * 功能描述: 设置地址
	 * 
	 * @param address
	 *            : String
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * 功能描述: 获得创建对象的人员名称
	 * 
	 * @return 创建对象的人员名称 : String
	 */
	public String getCreator()
	{
		return this.creator;
	}

	/**
	 * 功能描述: 设置创建对象的人员名称
	 * 
	 * @param creator
	 *            : String
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	/**
	 * 功能描述: 获得记录的创建日期
	 * 
	 * @return 记录的创建日期 : String
	 */
	public String getCreateTime()
	{
		return DateUtils.toDisplayStr(this.createTime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置记录的创建日期
	 * 
	 * @param createTime
	 *            : String
	 */
	public void setCreateTime(String createTime)
	{
		createTime = DateUtils.toStoreStr(createTime);
		this.createTime = createTime;
	}

	/**
	 * 功能描述: 获得对象更改人员的名称
	 * 
	 * @return 对象更改人员的名称 : String
	 */
	public String getLastModifyor()
	{
		return this.lastModifyor;
	}

	/**
	 * 功能描述: 设置对象更改人员的名称
	 * 
	 * @param lastModifyor
	 *            : String
	 */
	public void setLastModifyor(String lastModifyor)
	{
		this.lastModifyor = lastModifyor;
	}

	/**
	 * 功能描述: 获得对象最后更改日期
	 * 
	 * @return 对象最后更改日期 : String
	 */
	public String getLastModifyTime()
	{
		return DateUtils.toDisplayStr(this.lastModifyTime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置对象最后更改日期
	 * 
	 * @param lastModifyTime
	 *            : String
	 */
	public void setLastModifyTime(String lastModifyTime)
	{
		lastModifyTime = DateUtils.toStoreStr(lastModifyTime);
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * 功能描述: 获得流程状态
	 * 
	 * @return 流程状态 : String
	 */
	public String getProcessState()
	{
		return this.processState;
	}

	/**
	 * 功能描述: 设置流程状态
	 * 
	 * @param processState
	 *            : String
	 */
	public void setProcessState(String processState)
	{
		this.processState = processState;
	}

	/**
	 * 功能描述: 获得采购行项目
	 * 
	 * @return 采购行项目 : Set<OrderItems>
	 */
	public Set<OrderItems> getOrderItems()
	{
		return this.orderItems;
	}

	/**
	 * 功能描述: 设置采购行项目
	 * 
	 * @param orderItems
	 *            : Set<OrderItems>
	 */
	public void setOrderItems(Set<OrderItems> orderItems)
	{
		this.orderItems = orderItems;
	}

	/**
	 * 默认构造器
	 */
	public PurchaseOrder()
	{
		super();
	}
}
