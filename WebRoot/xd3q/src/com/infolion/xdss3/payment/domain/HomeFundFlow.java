/*
 * @(#)HomeFundFlow.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月27日 08点19分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.domain;

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

import com.infolion.xdss3.financeSquare.domain.IFundFlow;
import com.infolion.xdss3.payment.domain.HomePayment;

/**
 * <pre>
 * 内贸纯资金往来(HomeFundFlow)实体类
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
@Table(name = "YFUNDFLOW")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomeFundFlow extends BaseObject implements IFundFlow
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 *  纯资金往来ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="FUNDFLOWID")
      
    private String fundflowid;   
    
	/*
	 * 票清款ID
	 */
    @Column(name="BILLCLEARID")
    @ValidateRule(dataType=9,label="票清款ID",maxLength= 36,required=false)  
    private String billclearid;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT1")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit1;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT2")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit2;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT3")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit3;   
    
	/*
	 * 金额1
	 */
    @Column(name="AMOUNT1")
    @ValidateRule(dataType=0,label="金额1",required=false)  
    private BigDecimal amount1;   
    
	/*
	 * 金额2
	 */
    @Column(name="AMOUNT2")
    @ValidateRule(dataType=0,label="金额2",required=false)  
    private BigDecimal amount2;   
    
	/*
	 * 金额3
	 */
    @Column(name="AMOUNT3")
    @ValidateRule(dataType=0,label="金额3",required=false)  
    private BigDecimal amount3;   
    
	/*
	 * 本位币金额1
	 */
    @Column(name="STANDARDAMOUNT1")
    @ValidateRule(dataType=0,label="本位币金额1",required=false)  
    private BigDecimal standardamount1;   
    
	/*
	 * 本位币金额2
	 */
    @Column(name="STANDARDAMOUNT2")
    @ValidateRule(dataType=0,label="本位币金额2",required=false)  
    private BigDecimal standardamount2;   
    
	/*
	 * 本位币金额3
	 */
    @Column(name="STANDARDAMOUNT3")
    @ValidateRule(dataType=0,label="本位币金额3",required=false)  
    private BigDecimal standardamount3;   
    
	/*
	 * 特殊总账
	 */
    @Column(name="SPECIALACCOUNT1")
    @ValidateRule(dataType=9,label="特殊总账",maxLength= 50,required=false)  
    private String specialaccount1;   
    
	/*
	 * 特殊总账
	 */
    @Column(name="SPECIALACCOUNT2")
    @ValidateRule(dataType=9,label="特殊总账",maxLength= 50,required=false)  
    private String specialaccount2;   
    
	/*
	 * 特殊总账
	 */
    @Column(name="SPECIALACCOUNT3")
    @ValidateRule(dataType=9,label="特殊总账",maxLength= 50,required=false)  
    private String specialaccount3;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMER1")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customer1;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMER2")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customer2;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMER3")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customer3;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID1")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid1;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID2")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid2;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID3")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid3;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT1")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount1;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT2")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount2;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT3")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount3;   
    
	/*
	 * 收款ID
	 */
    @Column(name="COLLECTID")
    @ValidateRule(dataType=9,label="收款ID",maxLength= 36,required=false)  
    private String collectid;   
    
	/*
	 * 供应商单清帐ID
	 */
    @Column(name="SUPPLIERSCLEARID")
    @ValidateRule(dataType=9,label="供应商单清帐ID",maxLength= 36,required=false)  
    private String suppliersclearid;   
    
	/*
	 * 客户单清帐ID
	 */
    @Column(name="CUSTOMSCLEARID")
    @ValidateRule(dataType=9,label="客户单清帐ID",maxLength= 36,required=false)  
    private String customsclearid;   
    
	/*
	 * 内贸付款
	 */
    @OneToOne(optional=true)
    @JoinColumn(name = "PAYMENTID",nullable = true)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private HomePayment homePayment;   
    

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
     *    获得 纯资金往来ID
     * @return  纯资金往来ID : String
     */
    public String getFundflowid()
    {
		return this.fundflowid;
    }

    /**
     * 功能描述:
     *    设置 纯资金往来ID
     * @param fundflowid : String
     */
    public void setFundflowid(String fundflowid)
    {
	    this.fundflowid = fundflowid;
    }
    

    /**
     * 功能描述:
     *    获得票清款ID
     * @return 票清款ID : String
     */
    public String getBillclearid()
    {
		return this.billclearid;
    }

    /**
     * 功能描述:
     *    设置票清款ID
     * @param billclearid : String
     */
    public void setBillclearid(String billclearid)
    {
	    this.billclearid = billclearid;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit1()
    {
		return this.debtcredit1;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit1 : String
     */
    public void setDebtcredit1(String debtcredit1)
    {
	    this.debtcredit1 = debtcredit1;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit2()
    {
		return this.debtcredit2;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit2 : String
     */
    public void setDebtcredit2(String debtcredit2)
    {
	    this.debtcredit2 = debtcredit2;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit3()
    {
		return this.debtcredit3;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit3 : String
     */
    public void setDebtcredit3(String debtcredit3)
    {
	    this.debtcredit3 = debtcredit3;
    }
    

    /**
     * 功能描述:
     *    获得金额1
     * @return 金额1 : BigDecimal
     */
    public BigDecimal getAmount1()
    {
		return this.amount1;
    }

    /**
     * 功能描述:
     *    设置金额1
     * @param amount1 : BigDecimal
     */
    public void setAmount1(BigDecimal amount1)
    {
	    this.amount1 = amount1;
    }
    

    /**
     * 功能描述:
     *    获得金额2
     * @return 金额2 : BigDecimal
     */
    public BigDecimal getAmount2()
    {
		return this.amount2;
    }

    /**
     * 功能描述:
     *    设置金额2
     * @param amount2 : BigDecimal
     */
    public void setAmount2(BigDecimal amount2)
    {
	    this.amount2 = amount2;
    }
    

    /**
     * 功能描述:
     *    获得金额3
     * @return 金额3 : BigDecimal
     */
    public BigDecimal getAmount3()
    {
		return this.amount3;
    }

    /**
     * 功能描述:
     *    设置金额3
     * @param amount3 : BigDecimal
     */
    public void setAmount3(BigDecimal amount3)
    {
	    this.amount3 = amount3;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额1
     * @return 本位币金额1 : BigDecimal
     */
    public BigDecimal getStandardamount1()
    {
		return this.standardamount1;
    }

    /**
     * 功能描述:
     *    设置本位币金额1
     * @param standardamount1 : BigDecimal
     */
    public void setStandardamount1(BigDecimal standardamount1)
    {
	    this.standardamount1 = standardamount1;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额2
     * @return 本位币金额2 : BigDecimal
     */
    public BigDecimal getStandardamount2()
    {
		return this.standardamount2;
    }

    /**
     * 功能描述:
     *    设置本位币金额2
     * @param standardamount2 : BigDecimal
     */
    public void setStandardamount2(BigDecimal standardamount2)
    {
	    this.standardamount2 = standardamount2;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额3
     * @return 本位币金额3 : BigDecimal
     */
    public BigDecimal getStandardamount3()
    {
		return this.standardamount3;
    }

    /**
     * 功能描述:
     *    设置本位币金额3
     * @param standardamount3 : BigDecimal
     */
    public void setStandardamount3(BigDecimal standardamount3)
    {
	    this.standardamount3 = standardamount3;
    }
    

    /**
     * 功能描述:
     *    获得特殊总账
     * @return 特殊总账 : String
     */
    public String getSpecialaccount1()
    {
		return this.specialaccount1;
    }

    /**
     * 功能描述:
     *    设置特殊总账
     * @param specialaccount1 : String
     */
    public void setSpecialaccount1(String specialaccount1)
    {
	    this.specialaccount1 = specialaccount1;
    }
    

    /**
     * 功能描述:
     *    获得特殊总账
     * @return 特殊总账 : String
     */
    public String getSpecialaccount2()
    {
		return this.specialaccount2;
    }

    /**
     * 功能描述:
     *    设置特殊总账
     * @param specialaccount2 : String
     */
    public void setSpecialaccount2(String specialaccount2)
    {
	    this.specialaccount2 = specialaccount2;
    }
    

    /**
     * 功能描述:
     *    获得特殊总账
     * @return 特殊总账 : String
     */
    public String getSpecialaccount3()
    {
		return this.specialaccount3;
    }

    /**
     * 功能描述:
     *    设置特殊总账
     * @param specialaccount3 : String
     */
    public void setSpecialaccount3(String specialaccount3)
    {
	    this.specialaccount3 = specialaccount3;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomer1()
    {
		return this.customer1;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customer1 : String
     */
    public void setCustomer1(String customer1)
    {
	    this.customer1 = customer1;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomer2()
    {
		return this.customer2;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customer2 : String
     */
    public void setCustomer2(String customer2)
    {
	    this.customer2 = customer2;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomer3()
    {
		return this.customer3;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customer3 : String
     */
    public void setCustomer3(String customer3)
    {
	    this.customer3 = customer3;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid1()
    {
		return this.depid1;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid1 : String
     */
    public void setDepid1(String depid1)
    {
	    this.depid1 = depid1;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid2()
    {
		return this.depid2;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid2 : String
     */
    public void setDepid2(String depid2)
    {
	    this.depid2 = depid2;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid3()
    {
		return this.depid3;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid3 : String
     */
    public void setDepid3(String depid3)
    {
	    this.depid3 = depid3;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount1()
    {
		return this.antiaccount1;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount1 : String
     */
    public void setAntiaccount1(String antiaccount1)
    {
	    this.antiaccount1 = antiaccount1;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount2()
    {
		return this.antiaccount2;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount2 : String
     */
    public void setAntiaccount2(String antiaccount2)
    {
	    this.antiaccount2 = antiaccount2;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount3()
    {
		return this.antiaccount3;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount3 : String
     */
    public void setAntiaccount3(String antiaccount3)
    {
	    this.antiaccount3 = antiaccount3;
    }
    

    /**
     * 功能描述:
     *    获得收款ID
     * @return 收款ID : String
     */
    public String getCollectid()
    {
		return this.collectid;
    }

    /**
     * 功能描述:
     *    设置收款ID
     * @param collectid : String
     */
    public void setCollectid(String collectid)
    {
	    this.collectid = collectid;
    }
    

    /**
     * 功能描述:
     *    获得供应商单清帐ID
     * @return 供应商单清帐ID : String
     */
    public String getSuppliersclearid()
    {
		return this.suppliersclearid;
    }

    /**
     * 功能描述:
     *    设置供应商单清帐ID
     * @param suppliersclearid : String
     */
    public void setSuppliersclearid(String suppliersclearid)
    {
	    this.suppliersclearid = suppliersclearid;
    }
    

    /**
     * 功能描述:
     *    获得客户单清帐ID
     * @return 客户单清帐ID : String
     */
    public String getCustomsclearid()
    {
		return this.customsclearid;
    }

    /**
     * 功能描述:
     *    设置客户单清帐ID
     * @param customsclearid : String
     */
    public void setCustomsclearid(String customsclearid)
    {
	    this.customsclearid = customsclearid;
    }
    

    /**
     * 功能描述:
     *    获得内贸付款
     * @return 内贸付款 : HomePayment
     */
    public HomePayment getHomePayment()
    {
		return this.homePayment;
    }

    /**
     * 功能描述:
     *    设置内贸付款
     * @param homePayment : HomePayment
     */
    public void setHomePayment(HomePayment homePayment)
    {
	    this.homePayment = homePayment;
    }
    
    
	/**
	 *  默认构造器
	 */
    public HomeFundFlow()
    {
    	super();
    }
}
