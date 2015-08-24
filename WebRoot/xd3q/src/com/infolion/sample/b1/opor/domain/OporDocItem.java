/*
 * @(#)OporDocItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月26日 10点30分25秒
 *  描　述：创建
 */
package com.infolion.sample.b1.opor.domain;

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
import com.infolion.sample.b1.opor.domain.OporDoc;

/**
 * <pre>
 * 采购订单明细(OporDocItem)实体类
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
@Table(name = "YOPORDOCITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class OporDocItem extends BaseObject
{
	//fields
	/*
	 * 采购行项目ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="OPORDOCITEMID")
      
    private String oporDocItemId;   
    
	/*
	 * 物料编号
	 */
    @Column(name="ITEMCODE")
    @ValidateRule(dataType=9,label="物料编号",maxLength= 36,required=false)  
    private String itemCode;   
    
	/*
	 * 物料名称
	 */
    @Column(name="ITEMNAME")
    @ValidateRule(dataType=9,label="物料名称",maxLength= 100,required=false)  
    private String itemName;   
    
	/*
	 * 仓库
	 */
    @Column(name="WHSCODE")
    @ValidateRule(dataType=9,label="仓库",maxLength= 8,required=false)  
    private String whsCode;   
    
	/*
	 * 税码
	 */
    @Column(name="VATGROUP")
    @ValidateRule(dataType=9,label="税码",maxLength= 8,required=false)  
    private String vatGroup;   
    
	/*
	 * 单价
	 */
    @Column(name="UNITPRICE")
    @ValidateRule(dataType=0,label="单价",required=false)  
    private BigDecimal unitPrice;   
    
	/*
	 * 数量
	 */
    @Column(name="QUANTITY")
    @ValidateRule(dataType=0,label="数量",required=false)  
    private BigDecimal quantity;   
    
	/*
	 * 行总计
	 */
    @Column(name="LINETOTAL")
    @ValidateRule(dataType=0,label="行总计",required=false)  
    private BigDecimal lineTotal;   
    
	/*
	 * 合格数量
	 */
    @Column(name="U_QC")
    @ValidateRule(dataType=0,label="合格数量",required=false)  
    private BigDecimal u_qc;   
    
	/*
	 * 采购订单
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="OPORDOCID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private OporDoc oporDoc;   
    

    /**
     * 功能描述:
     *    获得采购行项目ID
     * @return 采购行项目ID : String
     */
    public String getOporDocItemId()
    {
		return this.oporDocItemId;
    }

    /**
     * 功能描述:
     *    设置采购行项目ID
     * @param oporDocItemId : String
     */
    public void setOporDocItemId(String oporDocItemId)
    {
	    this.oporDocItemId = oporDocItemId;
    }
    

    /**
     * 功能描述:
     *    获得物料编号
     * @return 物料编号 : String
     */
    public String getItemCode()
    {
		return this.itemCode;
    }

    /**
     * 功能描述:
     *    设置物料编号
     * @param itemCode : String
     */
    public void setItemCode(String itemCode)
    {
	    this.itemCode = itemCode;
    }
    

    /**
     * 功能描述:
     *    获得物料名称
     * @return 物料名称 : String
     */
    public String getItemName()
    {
		return this.itemName;
    }

    /**
     * 功能描述:
     *    设置物料名称
     * @param itemName : String
     */
    public void setItemName(String itemName)
    {
	    this.itemName = itemName;
    }
    

    /**
     * 功能描述:
     *    获得仓库
     * @return 仓库 : String
     */
    public String getWhsCode()
    {
		return this.whsCode;
    }

    /**
     * 功能描述:
     *    设置仓库
     * @param whsCode : String
     */
    public void setWhsCode(String whsCode)
    {
	    this.whsCode = whsCode;
    }
    

    /**
     * 功能描述:
     *    获得税码
     * @return 税码 : String
     */
    public String getVatGroup()
    {
		return this.vatGroup;
    }

    /**
     * 功能描述:
     *    设置税码
     * @param vatGroup : String
     */
    public void setVatGroup(String vatGroup)
    {
	    this.vatGroup = vatGroup;
    }
    

    /**
     * 功能描述:
     *    获得单价
     * @return 单价 : BigDecimal
     */
    public BigDecimal getUnitPrice()
    {
		return this.unitPrice;
    }

    /**
     * 功能描述:
     *    设置单价
     * @param unitPrice : BigDecimal
     */
    public void setUnitPrice(BigDecimal unitPrice)
    {
	    this.unitPrice = unitPrice;
    }
    

    /**
     * 功能描述:
     *    获得数量
     * @return 数量 : BigDecimal
     */
    public BigDecimal getQuantity()
    {
		return this.quantity;
    }

    /**
     * 功能描述:
     *    设置数量
     * @param quantity : BigDecimal
     */
    public void setQuantity(BigDecimal quantity)
    {
	    this.quantity = quantity;
    }
    

    /**
     * 功能描述:
     *    获得行总计
     * @return 行总计 : BigDecimal
     */
    public BigDecimal getLineTotal()
    {
		return this.lineTotal;
    }

    /**
     * 功能描述:
     *    设置行总计
     * @param lineTotal : BigDecimal
     */
    public void setLineTotal(BigDecimal lineTotal)
    {
	    this.lineTotal = lineTotal;
    }
    

    /**
     * 功能描述:
     *    获得合格数量
     * @return 合格数量 : BigDecimal
     */
    public BigDecimal getU_qc()
    {
		return this.u_qc;
    }

    /**
     * 功能描述:
     *    设置合格数量
     * @param u_qc : BigDecimal
     */
    public void setU_qc(BigDecimal u_qc)
    {
	    this.u_qc = u_qc;
    }
    

    /**
     * 功能描述:
     *    获得采购订单
     * @return 采购订单 : OporDoc
     */
    public OporDoc getOporDoc()
    {
		return this.oporDoc;
    }

    /**
     * 功能描述:
     *    设置采购订单
     * @param oporDoc : OporDoc
     */
    public void setOporDoc(OporDoc oporDoc)
    {
	    this.oporDoc = oporDoc;
    }
    
    
	/**
	 *  默认构造器
	 */
    public OporDocItem()
    {
    	super();
    }
}
