/*
 * @(#)HomeCreditBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 11点36分30秒
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
 * 付款银行(HomeCreditBankItem)实体类
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
@Table(name = "YPAYBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomeCreditBankItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 付款银行ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PAYBANKITEMID")
      
    private String paybankitemid;   
    
	
    
	/*
	 * 付款金额
	 */
    @Column(name="PAYAMOUNT")
    @ValidateRule(dataType=0,label="付款金额",required=false)  
    private BigDecimal payamount;   
    
	/*
	 * 付款金额(本位币)
	 */
    @Column(name="PAYAMOUNT2")
    @ValidateRule(dataType=0,label="付款金额(本位币)",required=false)  
    private BigDecimal payamount2;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 付款银行科目
	 */
    @Column(name="PAYBANKSUBJECT")
    @ValidateRule(dataType=9,label="付款银行科目",maxLength= 20,required=false)  
    private String paybanksubject;   
    
	/*
	 * 银行账号ID
	 */
    @Column(name="PAYBANKID")
    @ValidateRule(dataType=9,label="银行账号ID",maxLength= 36,required=false)  
    private String paybankid;   
    
	/*
	 * 付款银行名称
	 */
    @Column(name="PAYBANKNAME")
    @ValidateRule(dataType=9,label="付款银行名称",maxLength= 255,required=false)  
    private String paybankname;   
    
	/*
	 * 银行账号
	 */
    @Column(name="PAYBANKACCOUNT")
    @ValidateRule(dataType=9,label="银行账号",maxLength= 100,required=false)  
    private String paybankaccount;   
    
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
     *    获得付款银行ID
     * @return 付款银行ID : String
     */
    public String getPaybankitemid()
    {
		return this.paybankitemid;
    }

    /**
     * 功能描述:
     *    设置付款银行ID
     * @param paybankitemid : String
     */
    public void setPaybankitemid(String paybankitemid)
    {
	    this.paybankitemid = paybankitemid;
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
     *    获得付款金额
     * @return 付款金额 : BigDecimal
     */
    public BigDecimal getPayamount()
    {
		return this.payamount;
    }

    /**
     * 功能描述:
     *    设置付款金额
     * @param payamount : BigDecimal
     */
    public void setPayamount(BigDecimal payamount)
    {
	    this.payamount = payamount;
    }
    

    /**
     * 功能描述:
     *    获得付款金额(本位币)
     * @return 付款金额(本位币) : BigDecimal
     */
    public BigDecimal getPayamount2()
    {
		return this.payamount2;
    }

    /**
     * 功能描述:
     *    设置付款金额(本位币)
     * @param payamount2 : BigDecimal
     */
    public void setPayamount2(BigDecimal payamount2)
    {
	    this.payamount2 = payamount2;
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
     *    获得付款银行科目
     * @return 付款银行科目 : String
     */
    public String getPaybanksubject()
    {
		return this.paybanksubject;
    }

    /**
     * 功能描述:
     *    设置付款银行科目
     * @param paybanksubject : String
     */
    public void setPaybanksubject(String paybanksubject)
    {
	    this.paybanksubject = paybanksubject;
    }
    

    /**
     * 功能描述:
     *    获得银行账号ID
     * @return 银行账号ID : String
     */
    public String getPaybankid()
    {
		return this.paybankid;
    }

    /**
     * 功能描述:
     *    设置银行账号ID
     * @param paybankid : String
     */
    public void setPaybankid(String paybankid)
    {
	    this.paybankid = paybankid;
    }
    

    /**
     * 功能描述:
     *    获得付款银行名称
     * @return 付款银行名称 : String
     */
    public String getPaybankname()
    {
		return this.paybankname;
    }

    /**
     * 功能描述:
     *    设置付款银行名称
     * @param paybankname : String
     */
    public void setPaybankname(String paybankname)
    {
	    this.paybankname = paybankname;
    }
    

    /**
     * 功能描述:
     *    获得银行账号
     * @return 银行账号 : String
     */
    public String getPaybankaccount()
    {
		return this.paybankaccount;
    }

    /**
     * 功能描述:
     *    设置银行账号
     * @param paybankaccount : String
     */
    public void setPaybankaccount(String paybankaccount)
    {
	    this.paybankaccount = paybankaccount;
    }
    
    
	/**
	 *  默认构造器
	 */
    public HomeCreditBankItem()
    {
    	super();
    }
}
