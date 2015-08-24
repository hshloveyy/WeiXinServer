/*
 * @(#)BOneTestItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年09月17日 11点44分08秒
 *  描　述：创建
 */
package test.infolion.platform.dpframework.outsideinteface.domain;

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
 * B1测试2(BOneTestItem)实体类
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
@Table(name = "YTESTB1INTER2")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BOneTestItem extends BaseObject
{
	//fields
	/*
	 * ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="ID")
     
    private String id;   
    
	/*
	 * 物料号
	 */
    @Column(name="ITEMCODE")
    @ValidateRule(dataType=9,label="物料号",maxLength= 10,required=false) 
    private String itemcode;   
    
	/*
	 * 折扣
	 */
    @Column(name="DISCOUNTPERCENT")
    @ValidateRule(dataType=0,label="折扣",required=false) 
    private Double discountpercent;   
    
	/*
	 * 数量
	 */
    @Column(name="QUANTITY")
    @ValidateRule(dataType=0,label="数量",required=false) 
    private Double quantity;   
    
	/*
	 * 单价
	 */
    @Column(name="UNITPRICE")
    @ValidateRule(dataType=0,label="单价",required=false) 
    private Double unitprice;   
    
	/*
	 * 税码
	 */
    @Column(name="VATGROUP")
    @ValidateRule(dataType=9,label="税码",maxLength= 16,required=false) 
    private String vatgroup;   
    
	/*
	 * 仓库
	 */
    @Column(name="WAREHOUSECODE")
    @ValidateRule(dataType=9,label="仓库",maxLength= 10,required=false) 
    private String warehousecode;   
    
	/*
	 * 含税价
	 */
    @Column(name="PRICEAFTERVAT")
    @ValidateRule(dataType=0,label="含税价",required=false) 
    private Double priceaftervat;   
    

    /**
     * 功能描述:
     *    获得ID
     * @return ID : String
     */
    public String getId()
    {
		return this.id;
    }

    /**
     * 功能描述:
     *    设置ID
     * @param id : String
     */
    public void setId(String id)
    {
	    this.id = id;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getItemcode()
    {
		return this.itemcode;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param itemcode : String
     */
    public void setItemcode(String itemcode)
    {
	    this.itemcode = itemcode;
    }
    

    /**
     * 功能描述:
     *    获得折扣
     * @return 折扣 : BigDecimal
     */
    public Double getDiscountpercent()
    {
		return this.discountpercent;
    }

    /**
     * 功能描述:
     *    设置折扣
     * @param discountpercent : BigDecimal
     */
    public void setDiscountpercent(Double discountpercent)
    {
	    this.discountpercent = discountpercent;
    }
    

    /**
     * 功能描述:
     *    获得数量
     * @return 数量 : BigDecimal
     */
    public Double getQuantity()
    {
		return this.quantity;
    }

    /**
     * 功能描述:
     *    设置数量
     * @param quantity : BigDecimal
     */
    public void setQuantity(Double quantity)
    {
	    this.quantity = quantity;
    }
    

    /**
     * 功能描述:
     *    获得单价
     * @return 单价 : BigDecimal
     */
    public Double getUnitprice()
    {
		return this.unitprice;
    }

    /**
     * 功能描述:
     *    设置单价
     * @param unitprice : BigDecimal
     */
    public void setUnitprice(Double unitprice)
    {
	    this.unitprice = unitprice;
    }
    

    /**
     * 功能描述:
     *    获得税码
     * @return 税码 : String
     */
    public String getVatgroup()
    {
		return this.vatgroup;
    }

    /**
     * 功能描述:
     *    设置税码
     * @param vatgroup : String
     */
    public void setVatgroup(String vatgroup)
    {
	    this.vatgroup = vatgroup;
    }
    

    /**
     * 功能描述:
     *    获得仓库
     * @return 仓库 : String
     */
    public String getWarehousecode()
    {
		return this.warehousecode;
    }

    /**
     * 功能描述:
     *    设置仓库
     * @param warehousecode : String
     */
    public void setWarehousecode(String warehousecode)
    {
	    this.warehousecode = warehousecode;
    }
    

    /**
     * 功能描述:
     *    获得含税价
     * @return 含税价 : BigDecimal
     */
    public Double getPriceaftervat()
    {
		return this.priceaftervat;
    }

    /**
     * 功能描述:
     *    设置含税价
     * @param priceaftervat : BigDecimal
     */
    public void setPriceaftervat(Double priceaftervat)
    {
	    this.priceaftervat = priceaftervat;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BOneTestItem()
    {
    	super();
    }
}
