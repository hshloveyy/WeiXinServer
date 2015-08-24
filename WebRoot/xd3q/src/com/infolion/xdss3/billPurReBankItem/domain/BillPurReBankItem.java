/*
 * @(#)BillPurReBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurReBankItem.domain;

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
import com.infolion.xdss3.billpurchased.domain.BillPurchased;

/**
 * <pre>
 * 还押汇银行(BillPurReBankItem)实体类
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
@Table(name = "YBILLPURREBANK")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillPurReBankItem extends BaseObject
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
	 * 还押汇日
	 */
    @Column(name="REMATURITY")
    @ValidateRule(dataType=9,label="还押汇日",maxLength= 20,required=false)  
    private String rematurity;   
    
    
//
//    /*
//     * 借贷
//     */
//    @Column(name = "DEBTCREDIT")
//    @ValidateRule(dataType = 9, label = "借贷", maxLength = 2, required = false)
//    private String debtcredit;
//    
//    /*
//     * 还押汇银行帐号2
//     */
//    @Column(name="BANKACC3")
//    @ValidateRule(dataType=9,label="还押汇银行帐号2",maxLength= 100,required=false)  
//    private String bankacc3;   
//    
//    /*
//     * 还押汇币别
//     */
//    @Column(name="CURRENCY3")
//    @ValidateRule(dataType=9,label="还押汇币别",maxLength= 10,required=false)  
//    private String currency3;   
//    
//    /*
//     * 实际还款金额
//     */
//    @Column(name="REALMONEY3")
//    @ValidateRule(dataType=0,label="实际还款金额",required=false)  
//    private BigDecimal realmoney3;   
//    
//    /*
//     * 实际还款金额(本位币)
//     */
//    @Column(name="REALMONEY4")
//    @ValidateRule(dataType=0,label="实际还款金额(本位币)",required=false)  
//    private BigDecimal realmoney4;   
//    
//    /*
//     * 实际还款金额
//     */
//    @Column(name="APPLYAMOUNT3")
//    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
//    private BigDecimal applyamount3;   
//    
//    /*
//     * 实际还款金额(本位币)
//     */
//    @Column(name="APPLYAMOUNT4")
//    @ValidateRule(dataType=0,label="还押汇金额(本位币)",required=false)  
//    private BigDecimal applyamount4;
    
	/*
	 * 出口押汇
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BILLPURID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillPurchased billPurchased;   
    

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
	 *  默认构造器
	 */
    public BillPurReBankItem()
    {
    	super();
    }
}
