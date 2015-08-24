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
 * 还押汇银行(BillPurReBankTwo)实体类
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
public class BillPurReBankTwo extends BaseObject
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
     * 借贷
     */
    @Column(name = "DEBTCREDIT")
    @ValidateRule(dataType = 9, label = "借贷", maxLength = 2, required = false)
    private String debtcredit;
    
    /*
     * 还押汇银行帐号2
     */
    @Column(name="BANKACC3")
    @ValidateRule(dataType=9,label="还押汇银行帐号2",maxLength= 100,required=false)  
    private String bankacc3;   
    
    /*
     * 还押汇币别
     */
    @Column(name="CURRENCY3")
    @ValidateRule(dataType=9,label="还押汇币别",maxLength= 10,required=false)  
    private String currency3;   
    
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
    
    /*
     * 实际还款金额
     */
    @Column(name="APPLYAMOUNT3")
    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
    private BigDecimal applyamount3;   
    
    /*
     * 实际还款金额(本位币)
     */
    @Column(name="APPLYAMOUNT4")
    @ValidateRule(dataType=0,label="还押汇金额(本位币)",required=false)  
    private BigDecimal applyamount4;
    
    /*
     * 还押汇汇率2
     */
    @Column(name="REBILLPURRATE2")
    @ValidateRule(dataType=9,label="还押汇汇率2",maxLength= 10,required=false)  
    private String rebillpurrate2;   
    
    /*
     * 现金流项目
     */
    @Column(name="CASHFLOWITEM3")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem3;   
    
	/*
	 * 出口押汇
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BILLPURID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillPurchased billPurchased;   
    
    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the bankitemid
     */
    public String getBankitemid() {
        return bankitemid;
    }

    /**
     * @param bankitemid the bankitemid to set
     */
    public void setBankitemid(String bankitemid) {
        this.bankitemid = bankitemid;
    }


    /**
     * @return the debtcredit
     */
    public String getDebtcredit() {
        return debtcredit;
    }

    /**
     * @param debtcredit the debtcredit to set
     */
    public void setDebtcredit(String debtcredit) {
        this.debtcredit = debtcredit;
    }

    /**
     * @return the bankacc3
     */
    public String getBankacc3() {
        return bankacc3;
    }

    /**
     * @param bankacc3 the bankacc3 to set
     */
    public void setBankacc3(String bankacc3) {
        this.bankacc3 = bankacc3;
    }

    /**
     * @return the currency3
     */
    public String getCurrency3() {
        return currency3;
    }

    /**
     * @param currency3 the currency3 to set
     */
    public void setCurrency3(String currency3) {
        this.currency3 = currency3;
    }
//
//    /**
//     * @return the realmoney3
//     */
//    public BigDecimal getRealmoney3() {
//        return realmoney3;
//    }
//
//    /**
//     * @param realmoney3 the realmoney3 to set
//     */
//    public void setRealmoney3(BigDecimal realmoney3) {
//        this.realmoney3 = realmoney3;
//    }
//
//    /**
//     * @return the realmoney4
//     */
//    public BigDecimal getRealmoney4() {
//        return realmoney4;
//    }
//
//    /**
//     * @param realmoney4 the realmoney4 to set
//     */
//    public void setRealmoney4(BigDecimal realmoney4) {
//        this.realmoney4 = realmoney4;
//    }

    /**
     * @return the applyamount3
     */
    public BigDecimal getApplyamount3() {
        return applyamount3;
    }

    /**
     * @param applyamount3 the applyamount3 to set
     */
    public void setApplyamount3(BigDecimal applyamount3) {
        this.applyamount3 = applyamount3;
    }

    /**
     * @return the applyamount4
     */
    public BigDecimal getApplyamount4() {
        return applyamount4;
    }

    /**
     * @param applyamount4 the applyamount4 to set
     */
    public void setApplyamount4(BigDecimal applyamount4) {
        this.applyamount4 = applyamount4;
    }

    /**
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

    public String getRebillpurrate2() {
        return rebillpurrate2;
    }

    public void setRebillpurrate2(String rebillpurrate2) {
        this.rebillpurrate2 = rebillpurrate2;
    }

    public String getCashflowitem3() {
        return cashflowitem3;
    }

    public void setCashflowitem3(String cashflowitem3) {
        this.cashflowitem3 = cashflowitem3;
    }

    /**
	 *  默认构造器
	 */
    public BillPurReBankTwo()
    {
    	super();
    }
}
