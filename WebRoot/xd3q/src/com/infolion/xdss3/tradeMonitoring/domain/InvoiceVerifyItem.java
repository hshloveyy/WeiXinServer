/*
 * @(#)InvoiceVerifyItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月06日 09点42分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoring.domain;

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
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerify;

/**
 * <pre>
 * 发票校验行项目(InvoiceVerifyItem)实体类
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
@Table(name = "YGETITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class InvoiceVerifyItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 唯一标识
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CODE")
      
    private String code;   
    
	/*
	 * 行项目号
	 */
    @Column(name="BUZEI")
    @ValidateRule(dataType=0,label="行项目号",required=false)  
    private String buzei;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATNR")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false)  
    private String matnr;   
    
	/*
	 * 数量
	 */
    @Column(name="MENGE")
    @ValidateRule(dataType=0,label="数量",required=false)  
    private BigDecimal menge;   
    
	/*
	 * 单位
	 */
    @Column(name="BSTME")
    @ValidateRule(dataType=9,label="单位",maxLength= 3,required=false)  
    private String bstme;   
    
	/*
	 * 税率
	 */
    @Column(name="SHUILV")
    @ValidateRule(dataType=9,label="税率",maxLength= 10,required=false)  
    private String shuilv;   
    
	/*
	 * 含税金额
	 */
    @Column(name="TAXIN")
    @ValidateRule(dataType=0,label="含税金额",required=false)  
    private BigDecimal taxin;   
    
	/*
	 * 税额
	 */
    @Column(name="TAX")
    @ValidateRule(dataType=0,label="税额",required=false)  
    private BigDecimal tax;   
    
	/*
	 * 不含税金额
	 */
    @Column(name="TAXOUT")
    @ValidateRule(dataType=0,label="不含税金额",required=false)  
    private BigDecimal taxout;   
    
	/*
	 * 供应商
	 */
    @Column(name="LIFNR")
    @ValidateRule(dataType=9,label="供应商",maxLength= 10,required=false)  
    private String lifnr;   
    
	/*
	 * 应付款日
	 */
    @Column(name="YFKR")
    @ValidateRule(dataType=9,label="应付款日",maxLength= 14,required=false)  
    private String yfkr;   
    
	/*
	 * 应到货日
	 */
    @Column(name="YDHR")
    @ValidateRule(dataType=9,label="应到货日",maxLength= 14,required=false)  
    private String ydhr;   
    
	/*
	 * 实收货日
	 */
    @Column(name="REALRECIVDAY")
    @ValidateRule(dataType=9,label="实收货日",maxLength= 14,required=false)  
    private String realrecivday;   
    
	/*
	 * 实付款日
	 */
    @Column(name="REALPAYDAY")
    @ValidateRule(dataType=9,label="实付款日",maxLength= 14,required=false)  
    private String realpayday;   
    
	/*
	 * 发票校验
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="MAINCODE")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private InvoiceVerify invoiceVerify;   
    

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
     *    获得唯一标识
     * @return 唯一标识 : String
     */
    public String getCode()
    {
		return this.code;
    }

    /**
     * 功能描述:
     *    设置唯一标识
     * @param code : String
     */
    public void setCode(String code)
    {
	    this.code = code;
    }
    

    /**
     * 功能描述:
     *    获得行项目号
     * @return 行项目号 : String
     */
    public String getBuzei()
    {
		return this.buzei;
    }

    /**
     * 功能描述:
     *    设置行项目号
     * @param buzei : String
     */
    public void setBuzei(String buzei)
    {
	    this.buzei = buzei;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMatnr()
    {
		return this.matnr;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param matnr : String
     */
    public void setMatnr(String matnr)
    {
	    this.matnr = matnr;
    }
    

    /**
     * 功能描述:
     *    获得数量
     * @return 数量 : BigDecimal
     */
    public BigDecimal getMenge()
    {
		return this.menge;
    }

    /**
     * 功能描述:
     *    设置数量
     * @param menge : BigDecimal
     */
    public void setMenge(BigDecimal menge)
    {
	    this.menge = menge;
    }
    

    /**
     * 功能描述:
     *    获得单位
     * @return 单位 : String
     */
    public String getBstme()
    {
		return this.bstme;
    }

    /**
     * 功能描述:
     *    设置单位
     * @param bstme : String
     */
    public void setBstme(String bstme)
    {
	    this.bstme = bstme;
    }
    

    /**
     * 功能描述:
     *    获得税率
     * @return 税率 : String
     */
    public String getShuilv()
    {
		return this.shuilv;
    }

    /**
     * 功能描述:
     *    设置税率
     * @param shuilv : String
     */
    public void setShuilv(String shuilv)
    {
	    this.shuilv = shuilv;
    }
    

    /**
     * 功能描述:
     *    获得含税金额
     * @return 含税金额 : BigDecimal
     */
    public BigDecimal getTaxin()
    {
		return this.taxin;
    }

    /**
     * 功能描述:
     *    设置含税金额
     * @param taxin : BigDecimal
     */
    public void setTaxin(BigDecimal taxin)
    {
	    this.taxin = taxin;
    }
    

    /**
     * 功能描述:
     *    获得税额
     * @return 税额 : BigDecimal
     */
    public BigDecimal getTax()
    {
		return this.tax;
    }

    /**
     * 功能描述:
     *    设置税额
     * @param tax : BigDecimal
     */
    public void setTax(BigDecimal tax)
    {
	    this.tax = tax;
    }
    

    /**
     * 功能描述:
     *    获得不含税金额
     * @return 不含税金额 : BigDecimal
     */
    public BigDecimal getTaxout()
    {
		return this.taxout;
    }

    /**
     * 功能描述:
     *    设置不含税金额
     * @param taxout : BigDecimal
     */
    public void setTaxout(BigDecimal taxout)
    {
	    this.taxout = taxout;
    }
    

    /**
     * 功能描述:
     *    获得供应商
     * @return 供应商 : String
     */
    public String getLifnr()
    {
		return this.lifnr;
    }

    /**
     * 功能描述:
     *    设置供应商
     * @param lifnr : String
     */
    public void setLifnr(String lifnr)
    {
	    this.lifnr = lifnr;
    }
    

    /**
     * 功能描述:
     *    获得应付款日
     * @return 应付款日 : String
     */
    public String getYfkr()
    {
		return this.yfkr;
    }

    /**
     * 功能描述:
     *    设置应付款日
     * @param yfkr : String
     */
    public void setYfkr(String yfkr)
    {
	    this.yfkr = yfkr;
    }
    

    /**
     * 功能描述:
     *    获得应到货日
     * @return 应到货日 : String
     */
    public String getYdhr()
    {
		return this.ydhr;
    }

    /**
     * 功能描述:
     *    设置应到货日
     * @param ydhr : String
     */
    public void setYdhr(String ydhr)
    {
	    this.ydhr = ydhr;
    }
    

    /**
     * 功能描述:
     *    获得实收货日
     * @return 实收货日 : String
     */
    public String getRealrecivday()
    {
		return this.realrecivday;
    }

    /**
     * 功能描述:
     *    设置实收货日
     * @param realrecivday : String
     */
    public void setRealrecivday(String realrecivday)
    {
	    this.realrecivday = realrecivday;
    }
    

    /**
     * 功能描述:
     *    获得实付款日
     * @return 实付款日 : String
     */
    public String getRealpayday()
    {
		return this.realpayday;
    }

    /**
     * 功能描述:
     *    设置实付款日
     * @param realpayday : String
     */
    public void setRealpayday(String realpayday)
    {
	    this.realpayday = realpayday;
    }
    

    /**
     * 功能描述:
     *    获得发票校验
     * @return 发票校验 : InvoiceVerify
     */
    public InvoiceVerify getInvoiceVerify()
    {
		return this.invoiceVerify;
    }

    /**
     * 功能描述:
     *    设置发票校验
     * @param invoiceVerify : InvoiceVerify
     */
    public void setInvoiceVerify(InvoiceVerify invoiceVerify)
    {
	    this.invoiceVerify = invoiceVerify;
    }
    
    
	/**
	 *  默认构造器
	 */
    public InvoiceVerifyItem()
    {
    	super();
    }
}
