/*
 * @(#)PackingBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分56秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloan.domain;

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
import com.infolion.xdss3.packingloan.domain.PackingLoan;

/**
 * <pre>
 * 打包贷款银行(PackingBankItem)实体类
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
@Table(name = "YPACKINGBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class PackingBankItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 打包贷款银行ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BANKITEMID")
      
    private String bankitemid;   
    
	/*
	 * 银行账号
	 */
    @Column(name="BANKACC")
    @ValidateRule(dataType=9,label="银行账号",maxLength= 40,required=false)  
    private String bankacc;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 打包汇率
	 */
    @Column(name="BANKRATE")
    @ValidateRule(dataType=0,label="打包汇率",required=false)  
    private BigDecimal bankrate;   
    
	/*
	 * 实际收款金额
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
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 银行科目
	 */
    @Column(name="BANKSUBJ")
    @ValidateRule(dataType=9,label="银行科目",maxLength= 20,required=false)  
    private String banksubj;   
    
	/*
	 * 业务类型
	 */
    @Column(name="BUSINESSTYPE")
    @ValidateRule(dataType=9,label="业务类型",maxLength= 20,required=false)  
    private String businesstype;   
    
	/*
	 * 实际打包金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="实际打包金额",required=false)  
    private BigDecimal applyamount;   
    
	/*
	 * 实际打包金额(本位币)
	 */
    @Column(name="APPLYAMOUNT2")
    @ValidateRule(dataType=0,label="实际打包金额(本位币)",required=false)  
    private BigDecimal applyamount2;   
    
	/*
	 * 打包贷款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PACKINGID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private PackingLoan packingLoan;   
    

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
     *    获得打包贷款银行ID
     * @return 打包贷款银行ID : String
     */
    public String getBankitemid()
    {
		return this.bankitemid;
    }

    /**
     * 功能描述:
     *    设置打包贷款银行ID
     * @param bankitemid : String
     */
    public void setBankitemid(String bankitemid)
    {
	    this.bankitemid = bankitemid;
    }
    

    /**
     * 功能描述:
     *    获得银行账号
     * @return 银行账号 : String
     */
    public String getBankacc()
    {
		return this.bankacc;
    }

    /**
     * 功能描述:
     *    设置银行账号
     * @param bankacc : String
     */
    public void setBankacc(String bankacc)
    {
	    this.bankacc = bankacc;
    }
    

    /**
     * 功能描述:
     *    获得币别
     * @return 币别 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置币别
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得打包汇率
     * @return 打包汇率 : BigDecimal
     */
    public BigDecimal getBankrate()
    {
		return this.bankrate;
    }

    /**
     * 功能描述:
     *    设置打包汇率
     * @param bankrate : BigDecimal
     */
    public void setBankrate(BigDecimal bankrate)
    {
	    this.bankrate = bankrate;
    }
    

    /**
     * 功能描述:
     *    获得实际收款金额
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
     *    获得银行科目
     * @return 银行科目 : String
     */
    public String getBanksubj()
    {
		return this.banksubj;
    }

    /**
     * 功能描述:
     *    设置银行科目
     * @param banksubj : String
     */
    public void setBanksubj(String banksubj)
    {
	    this.banksubj = banksubj;
    }
    

    /**
     * 功能描述:
     *    获得业务类型
     * @return 业务类型 : String
     */
    public String getBusinesstype()
    {
		return this.businesstype;
    }

    /**
     * 功能描述:
     *    设置业务类型
     * @param businesstype : String
     */
    public void setBusinesstype(String businesstype)
    {
	    this.businesstype = businesstype;
    }
    

    /**
     * 功能描述:
     *    获得实际打包金额
     * @return 实际打包金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
		return this.applyamount;
    }

    /**
     * 功能描述:
     *    设置实际打包金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
    }
    

    /**
     * 功能描述:
     *    获得实际打包金额(本位币)
     * @return 实际打包金额(本位币) : BigDecimal
     */
    public BigDecimal getApplyamount2()
    {
		return this.applyamount2;
    }

    /**
     * 功能描述:
     *    设置实际打包金额(本位币)
     * @param applyamount2 : BigDecimal
     */
    public void setApplyamount2(BigDecimal applyamount2)
    {
	    this.applyamount2 = applyamount2;
    }
    

    /**
     * 功能描述:
     *    获得打包贷款
     * @return 打包贷款 : PackingLoan
     */
    public PackingLoan getPackingLoan()
    {
		return this.packingLoan;
    }

    /**
     * 功能描述:
     *    设置打包贷款
     * @param packingLoan : PackingLoan
     */
    public void setPackingLoan(PackingLoan packingLoan)
    {
	    this.packingLoan = packingLoan;
    }
    
    
	/**
	 *  默认构造器
	 */
    public PackingBankItem()
    {
    	super();
    }
}
