/*
 * @(#)FundFlow.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月08日 14点42分57秒
 *  描　述：创建
 */
package com.infolion.xdss3.financeSquare.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;

/**
 * <pre>
 * 纯资金往来(FundFlow)实体类
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
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class FundFlow extends BaseObject implements IFundFlow
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 纯资金往来ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "FUNDFLOWID")
	private String fundflowid;

	/*
	 * 借贷1
	 */
	@Column(name = "DEBTCREDIT1")
	@ValidateRule(dataType = 9, label = "借贷1", maxLength = 2, required = false)
	private String debtcredit1;

	/*
	 * 借贷2
	 */
	@Column(name = "DEBTCREDIT2")
	@ValidateRule(dataType = 9, label = "借贷2", maxLength = 2, required = false)
	private String debtcredit2;

	/*
	 * 借贷3
	 */
	@Column(name = "DEBTCREDIT3")
	@ValidateRule(dataType = 9, label = "借贷3", maxLength = 2, required = false)
	private String debtcredit3;

	/*
	 * 金额1
	 */
	@Column(name = "AMOUNT1")
	@ValidateRule(dataType = 0, label = "金额1", required = false)
	private BigDecimal amount1;

	/*
	 * 金额2
	 */
	@Column(name = "AMOUNT2")
	@ValidateRule(dataType = 0, label = "金额2", required = false)
	private BigDecimal amount2;

	/*
	 * 金额3
	 */
	@Column(name = "AMOUNT3")
	@ValidateRule(dataType = 0, label = "金额3", required = false)
	private BigDecimal amount3;

	/*
	 * 本位币金额1
	 */
	@Column(name = "STANDARDAMOUNT1")
	@ValidateRule(dataType = 0, label = "本位币金额1", required = false)
	private BigDecimal standardamount1;

	/*
	 * 本位币金额2
	 */
	@Column(name = "STANDARDAMOUNT2")
	@ValidateRule(dataType = 0, label = "本位币金额2", required = false)
	private BigDecimal standardamount2;

	/*
	 * 本位币金额3
	 */
	@Column(name = "STANDARDAMOUNT3")
	@ValidateRule(dataType = 0, label = "本位币金额3", required = false)
	private BigDecimal standardamount3;

	/*
	 * 特殊总账1
	 */
	@Column(name = "SPECIALACCOUNT1")
	@ValidateRule(dataType = 9, label = "特殊总账1", maxLength = 50, required = false)
	private String specialaccount1;

	/*
	 * 特殊总账2
	 */
	@Column(name = "SPECIALACCOUNT2")
	@ValidateRule(dataType = 9, label = "特殊总账2", maxLength = 50, required = false)
	private String specialaccount2;

	/*
	 * 特殊总账3
	 */
	@Column(name = "SPECIALACCOUNT3")
	@ValidateRule(dataType = 9, label = "特殊总账3", maxLength = 50, required = false)
	private String specialaccount3;

	/*
	 * 客户1
	 */
	@Column(name = "CUSTOMER1")
	@ValidateRule(dataType = 9, label = "客户1", maxLength = 36, required = false)
	private String customer1;

	/*
	 * 客户2
	 */
	@Column(name = "CUSTOMER2")
	@ValidateRule(dataType = 9, label = "客户2", maxLength = 36, required = false)
	private String customer2;

	/*
	 * 客户3
	 */
	@Column(name = "CUSTOMER3")
	@ValidateRule(dataType = 9, label = "客户3", maxLength = 36, required = false)
	private String customer3;

	/*
	 * 业务范围1
	 */
	@Column(name = "DEPID1")
	@ValidateRule(dataType = 9, label = "业务范围1", maxLength = 4, required = false)
	private String depid1;

	/*
	 * 业务范围2
	 */
	@Column(name = "DEPID2")
	@ValidateRule(dataType = 9, label = "业务范围2", maxLength = 4, required = false)
	private String depid2;

	/*
	 * 业务范围3
	 */
	@Column(name = "DEPID3")
	@ValidateRule(dataType = 9, label = "业务范围3", maxLength = 4, required = false)
	private String depid3;

	/*
	 * 反记账1
	 */
	@Column(name = "ANTIACCOUNT1")
	@ValidateRule(dataType = 9, label = "反记账1", maxLength = 1, required = false)
	private String antiaccount1;

	/*
	 * 反记账2
	 */
	@Column(name = "ANTIACCOUNT2")
	@ValidateRule(dataType = 9, label = "反记账2", maxLength = 1, required = false)
	private String antiaccount2;

	/*
	 * 反记账3
	 */
	@Column(name = "ANTIACCOUNT3")
	@ValidateRule(dataType = 9, label = "反记账3", maxLength = 1, required = false)
	private String antiaccount3;

    /*
     * 供应商
     */
    @Column(name="SUPPLIER1")
    @ValidateRule(dataType=9,label="供应商",maxLength= 36,required=false)  
    private String supplier1;   
    
    /*
     * 供应商
     */
    @Column(name="SUPPLIER2")
    @ValidateRule(dataType=9,label="供应商",maxLength= 36,required=false)  
    private String supplier2;   
    
    /*
     * 供应商
     */
    @Column(name="SUPPLIER3")
    @ValidateRule(dataType=9,label="供应商",maxLength= 36,required=false)  
    private String supplier3;   
    
	
	
	/*
     * 数据标识
     */
    @Column(name = "FLAG")
    @ValidateRule(dataType = 9, label = "数据标识", required = false)
    private String flag;

	/*
	 * 收款
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "COLLECTID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Collect collect;
	
	/*
	 * 供应商单清帐
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "SUPPLIERSCLEARID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private SupplierSinglClear supplierSinglClear;

	/*
	 * 票清付款
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "BILLCLEARID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private BillClearPayment billClearPayment;

	/*
	 * 客户单清帐
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "CUSTOMSCLEARID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private CustomSingleClear customSingleClear;
	   
    /*
     * 供应商退款抬头
     */
    @OneToOne(optional = true)
    @JoinColumn(name = "REFUNDMENTID", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private SupplierRefundment supplierRefundment;
    
    /*
     * 客户退款抬头
     */
    @OneToOne(optional = true)
    @JoinColumn(name = "CUSTREFUNDMENTID", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private CustomerRefundment customerRefundment;
    
    /*
     * 出口押汇
     */
    @OneToOne(optional=true)
    @JoinColumn(name = "BILLPURID",nullable = true)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillPurchased billPurchased;   
    
    
    /*
     * 打包贷款
     */
    @OneToOne(optional=true)
    @JoinColumn(name = "PACKINGID",nullable = true)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private PackingLoan packingLoan;   
    
    
    /*
	 * 国内证
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "PAYMENTID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private HomeCreditPayment homeCreditPayment;
	
	
	/**
	 * @return the customSingleClear
	 */
	public CustomSingleClear getCustomSingleClear()
	{
		return customSingleClear;
	}

	/**
	 * @param customSingleClear
	 *            the customSingleClear to set
	 */
	public void setCustomSingleClear(CustomSingleClear customSingleClear)
	{
		this.customSingleClear = customSingleClear;
	}

    /**
     * 获取供应商抬头
     * @return
     */
    public SupplierRefundment getSupplierRefundment() {
        return supplierRefundment;
    }

    public void setSupplierRefundment(SupplierRefundment supplierRefundment) {
        this.supplierRefundment = supplierRefundment;
    }
    
    /**
     * 获取客户退款抬头
     * @return
     */
    public CustomerRefundment getCustomerRefundment() {
        return customerRefundment;
    }
    
    public void setCustomerRefundment(CustomerRefundment customerRefundment) {
        this.customerRefundment = customerRefundment;
    }
    
	/**
	 * 出口押汇
     * @return the billPurchased
     */
    public BillPurchased getBillPurchased() {
        return billPurchased;
    }

    /**
     * @param billPurchased the billPurchased to set
     */
    public void setBillPurchased(BillPurchased billPurchased) {
        this.billPurchased = billPurchased;
    }

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
	 * 功能描述: 获得 纯资金往来ID
	 * 
	 * @return 纯资金往来ID : String
	 */
	public String getFundflowid()
	{
		return this.fundflowid;
	}

	/**
	 * 功能描述: 设置 纯资金往来ID
	 * 
	 * @param fundflowid
	 *            : String
	 */
	public void setFundflowid(String fundflowid)
	{
		this.fundflowid = fundflowid;
	}

	/**
	 * 功能描述: 获得借贷1
	 * 
	 * @return 借贷1 : String
	 */
	public String getDebtcredit1()
	{
		return this.debtcredit1;
	}

	/**
	 * 功能描述: 设置借贷1
	 * 
	 * @param debtcredit1
	 *            : String
	 */
	public void setDebtcredit1(String debtcredit1)
	{
		this.debtcredit1 = debtcredit1;
	}

	/**
	 * 功能描述: 获得借贷2
	 * 
	 * @return 借贷2 : String
	 */
	public String getDebtcredit2()
	{
		return this.debtcredit2;
	}

	/**
	 * 功能描述: 设置借贷2
	 * 
	 * @param debtcredit2
	 *            : String
	 */
	public void setDebtcredit2(String debtcredit2)
	{
		this.debtcredit2 = debtcredit2;
	}

	/**
	 * 功能描述: 获得借贷3
	 * 
	 * @return 借贷3 : String
	 */
	public String getDebtcredit3()
	{
		return this.debtcredit3;
	}

	/**
	 * 功能描述: 设置借贷3
	 * 
	 * @param debtcredit3
	 *            : String
	 */
	public void setDebtcredit3(String debtcredit3)
	{
		this.debtcredit3 = debtcredit3;
	}

	/**
	 * 功能描述: 获得金额1
	 * 
	 * @return 金额1 : BigDecimal
	 */
	public BigDecimal getAmount1()
	{
		return this.amount1;
	}

	/**
	 * 功能描述: 设置金额1
	 * 
	 * @param amount1
	 *            : BigDecimal
	 */
	public void setAmount1(BigDecimal amount1)
	{
		this.amount1 = amount1;
	}

	/**
	 * 功能描述: 获得金额2
	 * 
	 * @return 金额2 : BigDecimal
	 */
	public BigDecimal getAmount2()
	{
		return this.amount2;
	}

	/**
	 * 功能描述: 设置金额2
	 * 
	 * @param amount2
	 *            : BigDecimal
	 */
	public void setAmount2(BigDecimal amount2)
	{
		this.amount2 = amount2;
	}

	/**
	 * 功能描述: 获得金额3
	 * 
	 * @return 金额3 : BigDecimal
	 */
	public BigDecimal getAmount3()
	{
		return this.amount3;
	}

	/**
	 * 功能描述: 设置金额3
	 * 
	 * @param amount3
	 *            : BigDecimal
	 */
	public void setAmount3(BigDecimal amount3)
	{
		this.amount3 = amount3;
	}

	/**
	 * 功能描述: 获得本位币金额1
	 * 
	 * @return 本位币金额1 : BigDecimal
	 */
	public BigDecimal getStandardamount1()
	{
		return this.standardamount1;
	}

	/**
	 * 功能描述: 设置本位币金额1
	 * 
	 * @param standardamount1
	 *            : BigDecimal
	 */
	public void setStandardamount1(BigDecimal standardamount1)
	{
		this.standardamount1 = standardamount1;
	}

	/**
	 * 功能描述: 获得本位币金额2
	 * 
	 * @return 本位币金额2 : BigDecimal
	 */
	public BigDecimal getStandardamount2()
	{
		return this.standardamount2;
	}

	/**
	 * 功能描述: 设置本位币金额2
	 * 
	 * @param standardamount2
	 *            : BigDecimal
	 */
	public void setStandardamount2(BigDecimal standardamount2)
	{
		this.standardamount2 = standardamount2;
	}

	/**
	 * 功能描述: 获得本位币金额3
	 * 
	 * @return 本位币金额3 : BigDecimal
	 */
	public BigDecimal getStandardamount3()
	{
		return this.standardamount3;
	}

	/**
	 * 功能描述: 设置本位币金额3
	 * 
	 * @param standardamount3
	 *            : BigDecimal
	 */
	public void setStandardamount3(BigDecimal standardamount3)
	{
		this.standardamount3 = standardamount3;
	}

	/**
	 * 功能描述: 获得特殊总账1
	 * 
	 * @return 特殊总账1 : String
	 */
	public String getSpecialaccount1()
	{
		return this.specialaccount1;
	}

	/**
	 * 功能描述: 设置特殊总账1
	 * 
	 * @param specialaccount1
	 *            : String
	 */
	public void setSpecialaccount1(String specialaccount1)
	{
		this.specialaccount1 = specialaccount1;
	}

	/**
	 * 功能描述: 获得特殊总账2
	 * 
	 * @return 特殊总账2 : String
	 */
	public String getSpecialaccount2()
	{
		return this.specialaccount2;
	}

	/**
	 * 功能描述: 设置特殊总账2
	 * 
	 * @param specialaccount2
	 *            : String
	 */
	public void setSpecialaccount2(String specialaccount2)
	{
		this.specialaccount2 = specialaccount2;
	}

	/**
	 * 功能描述: 获得特殊总账3
	 * 
	 * @return 特殊总账3 : String
	 */
	public String getSpecialaccount3()
	{
		return this.specialaccount3;
	}

	/**
	 * 功能描述: 设置特殊总账3
	 * 
	 * @param specialaccount3
	 *            : String
	 */
	public void setSpecialaccount3(String specialaccount3)
	{
		this.specialaccount3 = specialaccount3;
	}

	/**
	 * 功能描述: 获得客户1
	 * 
	 * @return 客户1 : String
	 */
	public String getCustomer1()
	{
		return this.customer1;
	}

	/**
	 * 功能描述: 设置客户1
	 * 
	 * @param customer1
	 *            : String
	 */
	public void setCustomer1(String customer1)
	{
		this.customer1 = customer1;
	}

	/**
	 * 功能描述: 获得客户2
	 * 
	 * @return 客户2 : String
	 */
	public String getCustomer2()
	{
		return this.customer2;
	}

	/**
	 * 功能描述: 设置客户2
	 * 
	 * @param customer2
	 *            : String
	 */
	public void setCustomer2(String customer2)
	{
		this.customer2 = customer2;
	}

	/**
	 * 功能描述: 获得客户3
	 * 
	 * @return 客户3 : String
	 */
	public String getCustomer3()
	{
		return this.customer3;
	}

	/**
	 * 功能描述: 设置客户3
	 * 
	 * @param customer3
	 *            : String
	 */
	public void setCustomer3(String customer3)
	{
		this.customer3 = customer3;
	}

	/**
	 * 功能描述: 获得业务范围1
	 * 
	 * @return 业务范围1 : String
	 */
	public String getDepid1()
	{
		return this.depid1;
	}

	/**
	 * 功能描述: 设置业务范围1
	 * 
	 * @param depid1
	 *            : String
	 */
	public void setDepid1(String depid1)
	{
		this.depid1 = depid1;
	}

	/**
	 * 功能描述: 获得业务范围2
	 * 
	 * @return 业务范围2 : String
	 */
	public String getDepid2()
	{
		return this.depid2;
	}

	/**
	 * 功能描述: 设置业务范围2
	 * 
	 * @param depid2
	 *            : String
	 */
	public void setDepid2(String depid2)
	{
		this.depid2 = depid2;
	}

	/**
	 * 功能描述: 获得业务范围3
	 * 
	 * @return 业务范围3 : String
	 */
	public String getDepid3()
	{
		return this.depid3;
	}

	/**
	 * 功能描述: 设置业务范围3
	 * 
	 * @param depid3
	 *            : String
	 */
	public void setDepid3(String depid3)
	{
		this.depid3 = depid3;
	}

	/**
	 * 功能描述: 获得反记账1
	 * 
	 * @return 反记账1 : String
	 */
	public String getAntiaccount1()
	{
		return this.antiaccount1;
	}

	/**
	 * 功能描述: 设置反记账1
	 * 
	 * @param antiaccount1
	 *            : String
	 */
	public void setAntiaccount1(String antiaccount1)
	{
		this.antiaccount1 = antiaccount1;
	}

	/**
	 * 功能描述: 获得反记账2
	 * 
	 * @return 反记账2 : String
	 */
	public String getAntiaccount2()
	{
		return this.antiaccount2;
	}

	/**
	 * 功能描述: 设置反记账2
	 * 
	 * @param antiaccount2
	 *            : String
	 */
	public void setAntiaccount2(String antiaccount2)
	{
		this.antiaccount2 = antiaccount2;
	}

	/**
	 * 功能描述: 获得反记账3
	 * 
	 * @return 反记账3 : String
	 */
	public String getAntiaccount3()
	{
		return this.antiaccount3;
	}

	/**
	 * 功能描述: 设置反记账3
	 * 
	 * @param antiaccount3
	 *            : String
	 */
	public void setAntiaccount3(String antiaccount3)
	{
		this.antiaccount3 = antiaccount3;
	}

	
	/**
	 * 功能描述: 获得供应商单清帐
	 * 
	 * @return 供应商单清帐 : SupplierSinglClear
	 */
	public SupplierSinglClear getSupplierSinglClear()
	{
		return this.supplierSinglClear;
	}

	/**
	 * 功能描述: 设置供应商单清帐
	 * 
	 * @param supplierSinglClear
	 *            : SupplierSinglClear
	 */
	public void setSupplierSinglClear(SupplierSinglClear supplierSinglClear)
	{
		this.supplierSinglClear = supplierSinglClear;
	}

	/**
	 * 功能描述: 获得票清付款
	 * 
	 * @return 票清付款 : BillClearPayment
	 */
	public BillClearPayment getBillClearPayment()
	{
		return this.billClearPayment;
	}

	/**
	 * 功能描述: 设置票清付款
	 * 
	 * @param billClearPayment
	 *            : BillClearPayment
	 */
	public void setBillClearPayment(BillClearPayment billClearPayment)
	{
		this.billClearPayment = billClearPayment;
	}

	/**
	 * 默认构造器
	 */
	public FundFlow()
	{
		super();
	}

	/*
	 * 客户1
	 */
	@Transient
	private String customer1_htext;

	/*
	 * 客户2
	 */
	@Transient
	private String customer2_htext;

	/*
	 * 客户3
	 */
	@Transient
	private String customer3_htext;

	/*
	 * 业务范围1
	 */
	@Transient
	private String businessscope1_htext;

	/*
	 * 业务范围2
	 */
	@Transient
	private String businessscope2_htext;

	/*
	 * 业务范围3
	 */
	@Transient
	private String businessscope3_htext;

	/**
	 * @return the customer1_htext
	 */
	public String getCustomer1_htext()
	{
		return customer1_htext;
	}

	/**
	 * @param customer1_htext
	 *            the customer1_htext to set
	 */
	public void setCustomer1_htext(String customer1_htext)
	{
		this.customer1_htext = customer1_htext;
	}

	/**
	 * @return the customer2_htext
	 */
	public String getCustomer2_htext()
	{
		return customer2_htext;
	}

	/**
	 * @param customer2_htext
	 *            the customer2_htext to set
	 */
	public void setCustomer2_htext(String customer2_htext)
	{
		this.customer2_htext = customer2_htext;
	}

	/**
	 * @return the customer3_htext
	 */
	public String getCustomer3_htext()
	{
		return customer3_htext;
	}

	/**
	 * @param customer3_htext
	 *            the customer3_htext to set
	 */
	public void setCustomer3_htext(String customer3_htext)
	{
		this.customer3_htext = customer3_htext;
	}

	/**
	 * @return the businessscope1_htext
	 */
	public String getBusinessscope1_htext()
	{
		return businessscope1_htext;
	}

	/**
	 * @param businessscope1_htext
	 *            the businessscope1_htext to set
	 */
	public void setBusinessscope1_htext(String businessscope1_htext)
	{
		this.businessscope1_htext = businessscope1_htext;
	}

	/**
	 * @return the businessscope2_htext
	 */
	public String getBusinessscope2_htext()
	{
		return businessscope2_htext;
	}

	/**
	 * @param businessscope2_htext
	 *            the businessscope2_htext to set
	 */
	public void setBusinessscope2_htext(String businessscope2_htext)
	{
		this.businessscope2_htext = businessscope2_htext;
	}

	/**
	 * @return the businessscope3_htext
	 */
	public String getBusinessscope3_htext()
	{
		return businessscope3_htext;
	}

	/**
	 * @param businessscope3_htext
	 *            the businessscope3_htext to set
	 */
	public void setBusinessscope3_htext(String businessscope3_htext)
	{
		this.businessscope3_htext = businessscope3_htext;
	}

	public Collect getCollect() {
		return collect;
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
	}

    public String getFlag(){
        return flag;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public PackingLoan getPackingLoan() {
        return packingLoan;
    }

    public void setPackingLoan(PackingLoan packingLoan) {
        this.packingLoan = packingLoan;
    }

    public String getSupplier1() {
        return supplier1;
    }

    public void setSupplier1(String supplier1) {
        this.supplier1 = supplier1;
    }

    public String getSupplier2() {
        return supplier2;
    }

    public void setSupplier2(String supplier2) {
        this.supplier2 = supplier2;
    }

    public String getSupplier3() {
        return supplier3;
    }

    public void setSupplier3(String supplier3) {
        this.supplier3 = supplier3;
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

	
	
}
