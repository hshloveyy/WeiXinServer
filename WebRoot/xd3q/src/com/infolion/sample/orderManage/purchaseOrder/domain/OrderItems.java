/*
 * @(#)OrderItems.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月31日 15点00分58秒
 *  描　述：创建
 */
package com.infolion.sample.orderManage.purchaseOrder.domain;

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
import com.infolion.sample.orderManage.purchaseOrder.domain.PurchaseOrder;

/**
 * <pre>
 * 采购行项目(OrderItems)实体类
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
@Table(name = "YORDERITEMS")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class OrderItems extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 采购订单明细ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="ORDERITEMSID")
      
    private String orderItemsId;   
    
	/*
	 * 采购凭证的项目编号
	 */
    @Column(name="ITEMNO")
    @ValidateRule(dataType=0,label="采购凭证的项目编号",required=false)  
    private String itemNo;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATERIELNO")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=true)  
    private String materielNo;   
    
	/*
	 * 采购订单数量
	 */
    @Column(name="QUANTITY")
    @ValidateRule(dataType=9,label="采购订单数量",maxLength= 17,required=true)  
    private String quantity;   
    
	/*
	 * 交货日期
	 */
    @Column(name="DELIVERYDATE")
    @ValidateRule(dataType=8,label="交货日期",required=false)  
    private String deliveryDate;   
    
	/*
	 * 度量单位
	 */
    @Column(name="MEASUREUNIT")
    @ValidateRule(dataType=9,label="度量单位",maxLength= 36,required=false)  
    private String measureUnit;   
    
	/*
	 * 地址
	 */
    @Column(name="ADDRESS")
    @ValidateRule(dataType=9,label="地址",maxLength= 150,required=false)  
    private String address;   
    
	/*
	 * 采购订单
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PURCHASEORDERID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private PurchaseOrder purchaseOrder;   
    

    /**
     * 功能描述:
     *    获得客户端
     * @return 客户端 : String
     */
    public String getClient()
    {
		return this.client;
    }

    /**
     * 功能描述:
     *    设置客户端
     * @param client : String
     */
    public void setClient(String client)
    {
	    this.client = client;
    }
    

    /**
     * 功能描述:
     *    获得采购订单明细ID
     * @return 采购订单明细ID : String
     */
    public String getOrderItemsId()
    {
		return this.orderItemsId;
    }

    /**
     * 功能描述:
     *    设置采购订单明细ID
     * @param orderItemsId : String
     */
    public void setOrderItemsId(String orderItemsId)
    {
	    this.orderItemsId = orderItemsId;
    }
    

    /**
     * 功能描述:
     *    获得采购凭证的项目编号
     * @return 采购凭证的项目编号 : String
     */
    public String getItemNo()
    {
		return this.itemNo;
    }

    /**
     * 功能描述:
     *    设置采购凭证的项目编号
     * @param itemNo : String
     */
    public void setItemNo(String itemNo)
    {
	    this.itemNo = itemNo;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMaterielNo()
    {
		return this.materielNo;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param materielNo : String
     */
    public void setMaterielNo(String materielNo)
    {
	    this.materielNo = materielNo;
    }
    

    /**
     * 功能描述:
     *    获得采购订单数量
     * @return 采购订单数量 : String
     */
    public String getQuantity()
    {
		return this.quantity;
    }

    /**
     * 功能描述:
     *    设置采购订单数量
     * @param quantity : String
     */
    public void setQuantity(String quantity)
    {
	    this.quantity = quantity;
    }
    

    /**
     * 功能描述:
     *    获得交货日期
     * @return 交货日期 : String
     */
    public String getDeliveryDate()
    {
    	return DateUtils.toDisplayStr(this.deliveryDate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置交货日期
     * @param deliveryDate : String
     */
    public void setDeliveryDate(String deliveryDate)
    {
    	deliveryDate = DateUtils.toStoreStr(deliveryDate);
	    this.deliveryDate = deliveryDate;
    }
    

    /**
     * 功能描述:
     *    获得度量单位
     * @return 度量单位 : String
     */
    public String getMeasureUnit()
    {
		return this.measureUnit;
    }

    /**
     * 功能描述:
     *    设置度量单位
     * @param measureUnit : String
     */
    public void setMeasureUnit(String measureUnit)
    {
	    this.measureUnit = measureUnit;
    }
    

    /**
     * 功能描述:
     *    获得地址
     * @return 地址 : String
     */
    public String getAddress()
    {
		return this.address;
    }

    /**
     * 功能描述:
     *    设置地址
     * @param address : String
     */
    public void setAddress(String address)
    {
	    this.address = address;
    }
    

    /**
     * 功能描述:
     *    获得采购订单
     * @return 采购订单 : PurchaseOrder
     */
    public PurchaseOrder getPurchaseOrder()
    {
		return this.purchaseOrder;
    }

    /**
     * 功能描述:
     *    设置采购订单
     * @param purchaseOrder : PurchaseOrder
     */
    public void setPurchaseOrder(PurchaseOrder purchaseOrder)
    {
	    this.purchaseOrder = purchaseOrder;
    }
    
    
	/**
	 *  默认构造器
	 */
    public OrderItems()
    {
    	super();
    }
}
