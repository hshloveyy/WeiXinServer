/*
 * @(#)PackingReBankTwo.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年06月15日 15点21分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloan.domain;

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

/**
 * <pre>
 * 还打包银行2(PackingReBankTwo)实体类
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
@Table(name = "YPACKINGREBANK")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class PackingReBankTwo extends BaseObject {
    // fields
    /*
     * 客户端
     */
    @Column(name = "MANDT")
    private String client;

    /*
     * 打包贷款银行ID
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name = "BANKITEMID")
    private String bankitemid;

    /*
     * 是否做账
     */
    @Column(name = "BUSINESSTYPE")
    @ValidateRule(dataType = 9, label = "是否做账", maxLength = 20, required = false)
    private String businesstype;

    /*
     * 还押汇银行帐号2
     */
    @Column(name = "BANKACC3")
    @ValidateRule(dataType = 9, label = "还押汇银行帐号2", maxLength = 100, required = false)
    private String bankacc3;

    /*
     * 借贷标识
     */
    @Column(name = "DEBTCREDIT")
    @ValidateRule(dataType = 9, label = "借贷标识", maxLength = 1, required = false)
    private String debtcredit;

    /*
     * 币别
     */
    @Column(name = "CURRENCY3")
    @ValidateRule(dataType = 9, label = "币别", maxLength = 10, required = false)
    private String currency3;

    /*
     * 还打包汇率
     */
    @Column(name = "REBILLPURRATE3")
    @ValidateRule(dataType = 0, label = "还打包汇率", required = false)
    private BigDecimal rebillpurrate3;

    /*
     * 实际打包金额
     */
    @Column(name = "APPLYAMOUNT3")
    @ValidateRule(dataType = 0, label = "实际打包金额", required = false)
    private BigDecimal applyamount3;

    /*
     * 实际打包金额(本位币)
     */
    @Column(name = "APPLYAMOUNT4")
    @ValidateRule(dataType = 0, label = "实际打包金额(本位币)", required = false)
    private BigDecimal applyamount4;

    /*
     * 现金流项目
     */
    @Column(name = "CASHFLOWITEM3")
    @ValidateRule(dataType = 9, label = "现金流项目", maxLength = 10, required = false)
    private String cashflowitem3;

    /*
     * 打包贷款
     */
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "PACKINGID")
    @NotFound(action = NotFoundAction.IGNORE)
    private PackingLoan packingLoan;

    /**
     * 功能描述: 获得客户端
     * 
     * @return 客户端 : String
     */
    public String getClient() {
        return this.client;
    }

    /**
     * 功能描述: 设置客户端
     * 
     * @param client
     *            : String
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * 功能描述: 获得打包贷款银行ID
     * 
     * @return 打包贷款银行ID : String
     */
    public String getBankitemid() {
        return this.bankitemid;
    }

    /**
     * 功能描述: 设置打包贷款银行ID
     * 
     * @param bankitemid
     *            : String
     */
    public void setBankitemid(String bankitemid) {
        this.bankitemid = bankitemid;
    }

    /**
     * 功能描述: 获得是否做账
     * 
     * @return 是否做账 : String
     */
    public String getBusinesstype() {
        return this.businesstype;
    }

    /**
     * 功能描述: 设置是否做账
     * 
     * @param businesstype
     *            : String
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    /**
     * 功能描述: 获得还押汇银行帐号2
     * 
     * @return 还押汇银行帐号2 : String
     */
    public String getBankacc3() {
        return this.bankacc3;
    }

    /**
     * 功能描述: 设置还押汇银行帐号2
     * 
     * @param bankacc3
     *            : String
     */
    public void setBankacc3(String bankacc3) {
        this.bankacc3 = bankacc3;
    }

    /**
     * 功能描述: 获得借贷标识
     * 
     * @return 借贷标识 : String
     */
    public String getDebtcredit() {
        return this.debtcredit;
    }

    /**
     * 功能描述: 设置借贷标识
     * 
     * @param debtcredit
     *            : String
     */
    public void setDebtcredit(String debtcredit) {
        this.debtcredit = debtcredit;
    }

    /**
     * 功能描述: 获得币别
     * 
     * @return 币别 : String
     */
    public String getCurrency3() {
        return this.currency3;
    }

    /**
     * 功能描述: 设置币别
     * 
     * @param currency3
     *            : String
     */
    public void setCurrency3(String currency3) {
        this.currency3 = currency3;
    }

    /**
     * 功能描述: 获得还打包汇率
     * 
     * @return 还打包汇率 : BigDecimal
     */
    public BigDecimal getRebillpurrate3() {
        return this.rebillpurrate3;
    }

    /**
     * 功能描述: 设置还打包汇率
     * 
     * @param rebillpurrate3
     *            : BigDecimal
     */
    public void setRebillpurrate3(BigDecimal rebillpurrate3) {
        this.rebillpurrate3 = rebillpurrate3;
    }

    /**
     * 功能描述: 获得实际打包金额
     * 
     * @return 实际打包金额 : BigDecimal
     */
    public BigDecimal getApplyamount3() {
        return this.applyamount3;
    }

    /**
     * 功能描述: 设置实际打包金额
     * 
     * @param applyamount3
     *            : BigDecimal
     */
    public void setApplyamount3(BigDecimal applyamount3) {
        this.applyamount3 = applyamount3;
    }

    /**
     * 功能描述: 获得实际打包金额(本位币)
     * 
     * @return 实际打包金额(本位币) : BigDecimal
     */
    public BigDecimal getApplyamount4() {
        return this.applyamount4;
    }

    /**
     * 功能描述: 设置实际打包金额(本位币)
     * 
     * @param applyamount4
     *            : BigDecimal
     */
    public void setApplyamount4(BigDecimal applyamount4) {
        this.applyamount4 = applyamount4;
    }

    /**
     * 功能描述: 获得现金流项目
     * 
     * @return 现金流项目 : String
     */
    public String getCashflowitem3() {
        return this.cashflowitem3;
    }

    /**
     * 功能描述: 设置现金流项目
     * 
     * @param cashflowitem3
     *            : String
     */
    public void setCashflowitem3(String cashflowitem3) {
        this.cashflowitem3 = cashflowitem3;
    }

    /**
     * 功能描述: 获得打包贷款
     * 
     * @return 打包贷款 : PackingLoan
     */
    public PackingLoan getPackingLoan() {
        return this.packingLoan;
    }

    /**
     * 功能描述: 设置打包贷款
     * 
     * @param packingLoan
     *            : PackingLoan
     */
    public void setPackingLoan(PackingLoan packingLoan) {
        this.packingLoan = packingLoan;
    }

    /**
     * 默认构造器
     */
    public PackingReBankTwo() {
        super();
    }
}
