/*
 * @(#)HomeCreditRebank.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分50秒
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

/**
 * <pre>
 * 国内证还押汇银行(HomeCreditRebank)实体类
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
@Table(name = "YBILLPAYMENTBANK")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomeCreditRebank extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 押汇银行ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BANKITEMID")
      
    private String bankitemid;   
    
	 
    
	/*
	 * 还押汇银行帐号
	 */
    @Column(name="BANKACC")
    @ValidateRule(dataType=9,label="还押汇银行帐号",maxLength= 100,required=false)  
    private String bankacc;   
    
	/*
	 * 银行名称
	 */
    @Column(name="BANKNAME")
    @ValidateRule(dataType=9,label="银行名称",maxLength= 60,required=false)  
    private String bankname;   
    
	/*
	 * 还押汇币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="还押汇币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 实际还款金额
	 */
    @Column(name="REALMONEY")
    @ValidateRule(dataType=0,label="实际还款金额",required=false)  
    private BigDecimal realmoney;   
    
	/*
	 * 实际还款金额(本位币)
	 */
    @Column(name="REALMONEY2")
    @ValidateRule(dataType=0,label="实际还款金额(本位币)",required=false)  
    private BigDecimal realmoney2;   
    
	/*
	 * 扣含税利润金额（清供应商额度）（本位币）
	 */
    @Column(name="SUPPLIERAMOUNT")
    @ValidateRule(dataType=0,label="扣含税利润金额（清供应商额度）（本位币）",required=false)  
    private BigDecimal supplieramount;   
    
	/*
	 * 还押汇汇率
	 */
    @Column(name="REBILLPURRATE")
    @ValidateRule(dataType=9,label="还押汇汇率",maxLength= 10,required=false)  
    private String rebillpurrate;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 还押汇日
	 */
    @Column(name="REMATURITY")
    @ValidateRule(dataType=9,label="还押汇日",maxLength= 20,required=false)  
    private String rematurity;   
    
	/*
	 * 是否做账
	 */
    @Column(name="BUSINESSTYPE")
    @ValidateRule(dataType=9,label="是否做账",maxLength= 20,required=false)  
    private String businesstype;   
    
	/*
	 * 还押汇金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
    private BigDecimal applyamount;   
    
	/*
	 * 还押汇金额(本位币)
	 */
    @Column(name="APPLYAMOUNT2")
    @ValidateRule(dataType=0,label="还押汇金额(本位币)",required=false)  
    private BigDecimal applyamount2;   
    
	/*
	 * 还押汇银行帐号2
	 */
    @Column(name="BANKACC3")
    @ValidateRule(dataType=9,label="还押汇银行帐号2",maxLength= 100,required=false)  
    private String bankacc3;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit;   
    
	/*
	 * 还押汇币别
	 */
    @Column(name="CURRENCY3")
    @ValidateRule(dataType=9,label="还押汇币别",maxLength= 10,required=false)  
    private String currency3;   
    
//	/*
//	 * 还押汇汇率2
//	 */
//    @Column(name="REBILLPURRATE2")
//    @ValidateRule(dataType=9,label="还押汇汇率2",maxLength= 10,required=false)  
//    private String rebillpurrate2;   
//    
//	/*
//	 * 还押汇金额
//	 */
//    @Column(name="APPLYAMOUNT3")
//    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
//    private BigDecimal applyamount3;   
//    
//	/*
//	 * 还押汇金额(本位币)
//	 */
//    @Column(name="APPLYAMOUNT4")
//    @ValidateRule(dataType=0,label="还押汇金额(本位币)",required=false)  
//    private BigDecimal applyamount4;   
//    
//	/*
//	 * 现金流项目
//	 */
//    @Column(name="CASHFLOWITEM3")
//    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
//    private String cashflowitem3;   
    
    
    /*
	 * 国内证付款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PAYMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
    
    private HomeCreditPayment homeCreditPayment;

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
     *    获得押汇银行ID
     * @return 押汇银行ID : String
     */
    public String getBankitemid()
    {
		return this.bankitemid;
    }

    /**
     * 功能描述:
     *    设置押汇银行ID
     * @param bankitemid : String
     */
    public void setBankitemid(String bankitemid)
    {
	    this.bankitemid = bankitemid;
    }
    

   
    

    /**
	 * @return the homeCreditPayment
	 */
	public HomeCreditPayment getHomeCreditPayment() {
		return homeCreditPayment;
	}

	/**
	 * @param homeCreditPayment the homeCreditPayment to set
	 */
	public void setHomeCreditPayment(HomeCreditPayment homeCreditPayment) {
		this.homeCreditPayment = homeCreditPayment;
	}

	/**
     * 功能描述:
     *    获得还押汇银行帐号
     * @return 还押汇银行帐号 : String
     */
    public String getBankacc()
    {
		return this.bankacc;
    }

    /**
     * 功能描述:
     *    设置还押汇银行帐号
     * @param bankacc : String
     */
    public void setBankacc(String bankacc)
    {
	    this.bankacc = bankacc;
    }
    

    /**
     * 功能描述:
     *    获得银行名称
     * @return 银行名称 : String
     */
    public String getBankname()
    {
		return this.bankname;
    }

    /**
     * 功能描述:
     *    设置银行名称
     * @param bankname : String
     */
    public void setBankname(String bankname)
    {
	    this.bankname = bankname;
    }
    

    /**
     * 功能描述:
     *    获得还押汇币别
     * @return 还押汇币别 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置还押汇币别
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得实际还款金额
     * @return 实际还款金额 : BigDecimal
     */
    public BigDecimal getRealmoney()
    {
		return this.realmoney;
    }

    /**
     * 功能描述:
     *    设置实际还款金额
     * @param realmoney : BigDecimal
     */
    public void setRealmoney(BigDecimal realmoney)
    {
	    this.realmoney = realmoney;
    }
    

    /**
     * 功能描述:
     *    获得实际还款金额(本位币)
     * @return 实际还款金额(本位币) : BigDecimal
     */
    public BigDecimal getRealmoney2()
    {
		return this.realmoney2;
    }

    /**
     * 功能描述:
     *    设置实际还款金额(本位币)
     * @param realmoney2 : BigDecimal
     */
    public void setRealmoney2(BigDecimal realmoney2)
    {
	    this.realmoney2 = realmoney2;
    }
    

    /**
     * 功能描述:
     *    获得扣含税利润金额（清供应商额度）（本位币）
     * @return 扣含税利润金额（清供应商额度）（本位币） : BigDecimal
     */
    public BigDecimal getSupplieramount()
    {
		return this.supplieramount;
    }

    /**
     * 功能描述:
     *    设置扣含税利润金额（清供应商额度）（本位币）
     * @param supplieramount : BigDecimal
     */
    public void setSupplieramount(BigDecimal supplieramount)
    {
	    this.supplieramount = supplieramount;
    }
    

    /**
     * 功能描述:
     *    获得还押汇汇率
     * @return 还押汇汇率 : String
     */
    public String getRebillpurrate()
    {
		return this.rebillpurrate;
    }

    /**
     * 功能描述:
     *    设置还押汇汇率
     * @param rebillpurrate : String
     */
    public void setRebillpurrate(String rebillpurrate)
    {
	    this.rebillpurrate = rebillpurrate;
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
     *    获得还押汇日
     * @return 还押汇日 : String
     */
    public String getRematurity()
    {
		return this.rematurity;
    }

    /**
     * 功能描述:
     *    设置还押汇日
     * @param rematurity : String
     */
    public void setRematurity(String rematurity)
    {
	    this.rematurity = rematurity;
    }
    

    /**
     * 功能描述:
     *    获得是否做账
     * @return 是否做账 : String
     */
    public String getBusinesstype()
    {
		return this.businesstype;
    }

    /**
     * 功能描述:
     *    设置是否做账
     * @param businesstype : String
     */
    public void setBusinesstype(String businesstype)
    {
	    this.businesstype = businesstype;
    }
    

    /**
     * 功能描述:
     *    获得还押汇金额
     * @return 还押汇金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
		return this.applyamount;
    }

    /**
     * 功能描述:
     *    设置还押汇金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
    }
    

    /**
     * 功能描述:
     *    获得还押汇金额(本位币)
     * @return 还押汇金额(本位币) : BigDecimal
     */
    public BigDecimal getApplyamount2()
    {
		return this.applyamount2;
    }

    /**
     * 功能描述:
     *    设置还押汇金额(本位币)
     * @param applyamount2 : BigDecimal
     */
    public void setApplyamount2(BigDecimal applyamount2)
    {
	    this.applyamount2 = applyamount2;
    }
    

    /**
     * 功能描述:
     *    获得还押汇银行帐号2
     * @return 还押汇银行帐号2 : String
     */
    public String getBankacc3()
    {
		return this.bankacc3;
    }

    /**
     * 功能描述:
     *    设置还押汇银行帐号2
     * @param bankacc3 : String
     */
    public void setBankacc3(String bankacc3)
    {
	    this.bankacc3 = bankacc3;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit()
    {
		return this.debtcredit;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit : String
     */
    public void setDebtcredit(String debtcredit)
    {
	    this.debtcredit = debtcredit;
    }
    

    /**
     * 功能描述:
     *    获得还押汇币别
     * @return 还押汇币别 : String
     */
    public String getCurrency3()
    {
		return this.currency3;
    }

    /**
     * 功能描述:
     *    设置还押汇币别
     * @param currency3 : String
     */
    public void setCurrency3(String currency3)
    {
	    this.currency3 = currency3;
    }
    

//    /**
//     * 功能描述:
//     *    获得还押汇汇率2
//     * @return 还押汇汇率2 : String
//     */
//    public String getRebillpurrate2()
//    {
//		return this.rebillpurrate2;
//    }
//
//    /**
//     * 功能描述:
//     *    设置还押汇汇率2
//     * @param rebillpurrate2 : String
//     */
//    public void setRebillpurrate2(String rebillpurrate2)
//    {
//	    this.rebillpurrate2 = rebillpurrate2;
//    }
//    
//
//    /**
//     * 功能描述:
//     *    获得还押汇金额
//     * @return 还押汇金额 : BigDecimal
//     */
//    public BigDecimal getApplyamount3()
//    {
//		return this.applyamount3;
//    }
//
//    /**
//     * 功能描述:
//     *    设置还押汇金额
//     * @param applyamount3 : BigDecimal
//     */
//    public void setApplyamount3(BigDecimal applyamount3)
//    {
//	    this.applyamount3 = applyamount3;
//    }
//    
//
//    /**
//     * 功能描述:
//     *    获得还押汇金额(本位币)
//     * @return 还押汇金额(本位币) : BigDecimal
//     */
//    public BigDecimal getApplyamount4()
//    {
//		return this.applyamount4;
//    }
//
//    /**
//     * 功能描述:
//     *    设置还押汇金额(本位币)
//     * @param applyamount4 : BigDecimal
//     */
//    public void setApplyamount4(BigDecimal applyamount4)
//    {
//	    this.applyamount4 = applyamount4;
//    }
//    
//
//    /**
//     * 功能描述:
//     *    获得现金流项目
//     * @return 现金流项目 : String
//     */
//    public String getCashflowitem3()
//    {
//		return this.cashflowitem3;
//    }
//
//    /**
//     * 功能描述:
//     *    设置现金流项目
//     * @param cashflowitem3 : String
//     */
//    public void setCashflowitem3(String cashflowitem3)
//    {
//	    this.cashflowitem3 = cashflowitem3;
//    }
//    
    
	/**
	 *  默认构造器
	 */
    public HomeCreditRebank()
    {
    	super();
    }
}
