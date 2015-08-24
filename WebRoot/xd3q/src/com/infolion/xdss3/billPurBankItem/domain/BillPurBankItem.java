/*
 * @(#)BillPurBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurBankItem.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;

/**
 * <pre>
 * 押汇银行(BillPurBankItem)实体类
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
@Table(name = "YBILLPURBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillPurBankItem extends BaseObject
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
	 * 押汇银行帐号
	 */
    @Column(name="BANKACC")
    @ValidateRule(dataType=9,label="押汇银行帐号",maxLength= 100,required=false)  
    private String bankacc;   
    
	/*
	 * 银行名称
	 */
    @Column(name="BANKNAME")
    @ValidateRule(dataType=9,label="银行名称",maxLength= 60,required=false)  
    private String bankname;   
    
	/*
	 * 押汇币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="押汇币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 实际收款金额
	 * 用于计算是否完全还款
	 */
    @Column(name="REALMONEY")
    @ValidateRule(dataType=0,label="实际收款金额",required=false)  
    private BigDecimal realmoney;   
    
	/*
	 * 实际收款金额(本位币)
	 */
    @Column(name="REALMONEY1")
    @ValidateRule(dataType=0,label="实际收款金额(本位币)",required=false)  
    private BigDecimal realmoney1;   
    
	/*
	 * 扣含税利润金额（清供应商额度）（本位币）
	 */
    @Column(name="SUPPLIERAMOUNT")
    @ValidateRule(dataType=0,label="扣含税利润金额（清供应商额度）（本位币）",required=false)  
    private BigDecimal supplieramount;  
    
    /*
     * 现金日记帐状态
     */
    @Column(name="BUSINESSTYPE")
    @ValidateRule(dataType=9,label="现金日记帐状态",maxLength= 30,required=false)  
    private String businesstype;   
    
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 押汇银行科目
	 */
    @Column(name="BANKSUBJ")
    @ValidateRule(dataType=9,label="押汇银行科目",maxLength= 10,required=false)  
    private String banksubj;   
    
	/*
	 * 出口押汇
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BILLPURID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillPurchased billPurchased;   
    
    /*
     * 实际押汇金额
     */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="实际押汇金额",required=false)  
    private BigDecimal applyamount;   
    
    /*
     * 实际押汇金额(本位币)
     */
    @Column(name="APPLYAMOUNT2")
    @ValidateRule(dataType=0,label="实际押汇金额(本位币)",required=false)  
    private BigDecimal applyamount2;   
    
    /*
     * 实际押汇汇率
     */
    @Column(name="BANKRATE")
    @ValidateRule(dataType=0,label="实际押汇汇率",required=false)  
    private BigDecimal bankrate;   
    
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
     * 功能描述:
     *    获得押汇银行帐号
     * @return 押汇银行帐号 : String
     */
    public String getBankacc()
    {
		return this.bankacc;
    }

    /**
     * 功能描述:
     *    设置押汇银行帐号
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
     *    获得押汇币别
     * @return 押汇币别 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置押汇币别
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得实际收款金额
     *    用于计算是否完全还款
     * @return 实际收款金额 : BigDecimal
     */
    public BigDecimal getRealmoney()
    {
		return this.realmoney;
    }

    /**
     * 功能描述:
     *    设置实际收款金额
     * @param realmoney : BigDecimal
     */
    public void setRealmoney(BigDecimal realmoney)
    {
	    this.realmoney = realmoney;
    }
    

    /**
     * 功能描述:
     *    获得实际收款金额(本位币)
     * @return 实际收款金额(本位币) : BigDecimal
     */
    public BigDecimal getRealmoney1()
    {
		return this.realmoney1;
    }

    /**
     * 功能描述:
     *    设置实际收款金额(本位币)
     * @param realmoney1 : BigDecimal
     */
    public void setRealmoney1(BigDecimal realmoney1)
    {
	    this.realmoney1 = realmoney1;
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
     *    获得押汇银行科目
     * @return 押汇银行科目 : String
     */
    public String getBanksubj()
    {
		return this.banksubj;
    }

    /**
     * 功能描述:
     *    设置押汇银行科目
     * @param banksubj : String
     */
    public void setBanksubj(String banksubj)
    {
	    this.banksubj = banksubj;
    }
    

    /**
     * 功能描述:
     *    获得出口押汇
     * @return 出口押汇 : BillPurchased
     */
    public BillPurchased getBillPurchased()
    {
		return this.billPurchased;
    }

    /**
     * 功能描述:
     *    设置出口押汇
     * @param billPurchased : BillPurchased
     */
    public void setBillPurchased(BillPurchased billPurchased)
    {
	    this.billPurchased = billPurchased;
    }
    
    
	/**
     * @return the businesstype
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * @param businesstype the businesstype to set
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    /**
     * @return the applyamount
     */
    public BigDecimal getApplyamount() {
        return applyamount;
    }

    /**
     * @param applyamount the applyamount to set
     */
    public void setApplyamount(BigDecimal applyamount) {
        this.applyamount = applyamount;
    }

    /**
     * @return the applyamount2
     */
    public BigDecimal getApplyamount2() {
        return applyamount2;
    }

    /**
     * @param applyamount2 the applyamount2 to set
     */
    public void setApplyamount2(BigDecimal applyamount2) {
        this.applyamount2 = applyamount2;
    }

    /**
     * @return the bankrate
     */
    public BigDecimal getBankrate() {
        return bankrate;
    }

    /**
     * @param bankrate the bankrate to set
     */
    public void setBankrate(BigDecimal bankrate) {
        this.bankrate = bankrate;
    }

    /**
	 *  默认构造器
	 */
    public BillPurBankItem()
    {
    	super();
    }
}
