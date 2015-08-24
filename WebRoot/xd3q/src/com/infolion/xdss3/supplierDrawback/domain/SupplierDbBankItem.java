/*
 * @(#)SupplierDbBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 17点13分52秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawback.domain;

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
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;

/**
 * <pre>
 * 供应商退款银行(SupplierDbBankItem)实体类
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
@Table(name = "YREFUNDBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class SupplierDbBankItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 退款银行项目表ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="REFUNDBANKITEMID")
      
    private String refundbankitemid;   
    
	/*
	 * 退款银行ID
	 */
    @Column(name="REFUNDBANKID")
    @ValidateRule(dataType=9,label="退款银行ID",maxLength= 36,required=false)  
    private String refundbankid;   
    
	/*
	 * 退款银行
	 */
    @Column(name="REFUNDBANKNAME")
    @ValidateRule(dataType=9,label="退款银行",maxLength= 255,required=false)  
    private String refundbankname;   
    
	/*
	 * 退款账号
	 */
    @Column(name="REFUNDBANKACOUNT")
    @ValidateRule(dataType=9,label="退款账号",maxLength= 50,required=false)  
    private String refundbankacount;   
    
	/*
	 * 退款金额
	 */
    @Column(name="REFUNDAMOUNT")
    @ValidateRule(dataType=0,label="退款金额",required=false)  
    private BigDecimal refundamount;   
    
	/*
	 * 退款金额（本位币）
	 */
    @Column(name="REFUNDAMOUNT2")
    @ValidateRule(dataType=0,label="退款金额（本位币）",required=false)  
    private BigDecimal refundamount2;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 银行科目
	 */
    @Column(name="BANKSUBJECT")
    @ValidateRule(dataType=9,label="银行科目",maxLength= 20,required=false)  
    private String banksubject;   
    
	/*
	 * 退款类型
	 */
    @Column(name="REFUNDTYPE")
    @ValidateRule(dataType=9,label="退款类型",maxLength= 1,required=false)  
    private String refundtype;   
    
	/*
	 * 供应商退款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="REFUNDMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private SupplierRefundment supplierRefundment;   
    

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
     *    获得退款银行项目表ID
     * @return 退款银行项目表ID : String
     */
    public String getRefundbankitemid()
    {
		return this.refundbankitemid;
    }

    /**
     * 功能描述:
     *    设置退款银行项目表ID
     * @param refundbankitemid : String
     */
    public void setRefundbankitemid(String refundbankitemid)
    {
	    this.refundbankitemid = refundbankitemid;
    }
    

    /**
     * 功能描述:
     *    获得退款银行ID
     * @return 退款银行ID : String
     */
    public String getRefundbankid()
    {
		return this.refundbankid;
    }

    /**
     * 功能描述:
     *    设置退款银行ID
     * @param refundbankid : String
     */
    public void setRefundbankid(String refundbankid)
    {
	    this.refundbankid = refundbankid;
    }
    

    /**
     * 功能描述:
     *    获得退款银行
     * @return 退款银行 : String
     */
    public String getRefundbankname()
    {
		return this.refundbankname;
    }

    /**
     * 功能描述:
     *    设置退款银行
     * @param refundbankname : String
     */
    public void setRefundbankname(String refundbankname)
    {
	    this.refundbankname = refundbankname;
    }
    

    /**
     * 功能描述:
     *    获得退款账号
     * @return 退款账号 : String
     */
    public String getRefundbankacount()
    {
		return this.refundbankacount;
    }

    /**
     * 功能描述:
     *    设置退款账号
     * @param refundbankacount : String
     */
    public void setRefundbankacount(String refundbankacount)
    {
	    this.refundbankacount = refundbankacount;
    }
    

    /**
     * 功能描述:
     *    获得退款金额
     * @return 退款金额 : BigDecimal
     */
    public BigDecimal getRefundamount()
    {
		return this.refundamount;
    }

    /**
     * 功能描述:
     *    设置退款金额
     * @param refundamount : BigDecimal
     */
    public void setRefundamount(BigDecimal refundamount)
    {
	    this.refundamount = refundamount;
    }
    

    /**
     * 功能描述:
     *    获得退款金额（本位币）
     * @return 退款金额（本位币） : BigDecimal
     */
    public BigDecimal getRefundamount2()
    {
		return this.refundamount2;
    }

    /**
     * 功能描述:
     *    设置退款金额（本位币）
     * @param refundamount2 : BigDecimal
     */
    public void setRefundamount2(BigDecimal refundamount2)
    {
	    this.refundamount2 = refundamount2;
    }
    

    /**
     * 功能描述:
     *    获得现金流项目
     * @return 现金流项目 : String
     */
    public String getCashflowitem()
    {
		return this.cashflowitem;
    }

    /**
     * 功能描述:
     *    设置现金流项目
     * @param cashflowitem : String
     */
    public void setCashflowitem(String cashflowitem)
    {
	    this.cashflowitem = cashflowitem;
    }
    

    /**
     * 功能描述:
     *    获得银行科目
     * @return 银行科目 : String
     */
    public String getBanksubject()
    {
		return this.banksubject;
    }

    /**
     * 功能描述:
     *    设置银行科目
     * @param banksubject : String
     */
    public void setBanksubject(String banksubject)
    {
	    this.banksubject = banksubject;
    }
    

    /**
     * 功能描述:
     *    获得退款类型
     * @return 退款类型 : String
     */
    public String getRefundtype()
    {
		return this.refundtype;
    }

    /**
     * 功能描述:
     *    设置退款类型
     * @param refundtype : String
     */
    public void setRefundtype(String refundtype)
    {
	    this.refundtype = refundtype;
    }
    

    /**
     * 功能描述:
     *    获得供应商退款
     * @return 供应商退款 : SupplierRefundment
     */
    public SupplierRefundment getSupplierRefundment()
    {
		return this.supplierRefundment;
    }

    /**
     * 功能描述:
     *    设置供应商退款
     * @param supplierRefundment : SupplierRefundment
     */
    public void setSupplierRefundment(SupplierRefundment supplierRefundment)
    {
	    this.supplierRefundment = supplierRefundment;
    }
    
    
	/**
	 *  默认构造器
	 */
    public SupplierDbBankItem()
    {
    	super();
    }
}
