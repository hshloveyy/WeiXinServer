/*
 * @(#)ImportPaymentCbill.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分21秒
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
import com.infolion.xdss3.payment.domain.ImportPayment;

/**
 * <pre>
 * 发票清帐(ImportPaymentCbill)实体类
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
@Table(name = "YPAYMENTCBILL")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ImportPaymentCbill extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 付款清票ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PAYMENTCBILLID")
      
    private String paymentcbillid;   
    
	/*
	 * 立项号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="立项号",maxLength= 100,required=false)  
    private String project_no;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 发票号
	 */
    @Column(name="BILLNO")
    @ValidateRule(dataType=9,label="发票号",maxLength= 50,required=false)  
    private String billno;   
    
	/*
	 * 外部纸质合同号
	 */
    @Column(name="OLD_CONTRACT_NO")
    @ValidateRule(dataType=9,label="外部纸质合同号",maxLength= 50,required=false)  
    private String old_contract_no;   
    
	/*
	 * SAP订单号
	 */
    @Column(name="SAP_ORDER_NO")
    @ValidateRule(dataType=9,label="SAP订单号",maxLength= 50,required=false)  
    private String sap_order_no;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 应付款金额
	 */
    @Column(name="PAYABLEAMOUNT")
    @ValidateRule(dataType=0,label="应付款金额",required=false)  
    private BigDecimal payableamount;   
    
	/*
	 * 应付款日
	 */
    @Column(name="PAYABLEDATE")
    @ValidateRule(dataType=9,label="应付款日",maxLength= 20,required=false)  
    private String payabledate;   
    
	/*
	 * 已付款
	 */
    @Column(name="PAIDAMOUNT")
    @ValidateRule(dataType=0,label="已付款",required=false)  
    private BigDecimal paidamount;   
    
	/*
	 * 未付款
	 */
    @Column(name="UNPAIDAMOUNT")
    @ValidateRule(dataType=0,label="未付款",required=false)  
    private BigDecimal unpaidamount;   
    
	/*
	 * 在批金额
	 */
    @Column(name="ONROADAMOUNT")
    @ValidateRule(dataType=0,label="在批金额",required=false)  
    private BigDecimal onroadamount;   
    
	/*
	 * 清帐金额
	 */
    @Column(name="CLEARAMOUNT2")
    @ValidateRule(dataType=0,label="清帐金额",required=false)  
    private BigDecimal clearamount2;   
    
	/*
	 * 采购合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="采购合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 记账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=9,label="记账日期",maxLength= 20,required=false)  
    private String accountdate;   
    
	/*
	 * 文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="文本",maxLength= 255,required=false)  
    private String text;   
    
	/*
	 * 发票ID
	 */
    @Column(name="BILLID")
    @ValidateRule(dataType=9,label="发票ID",maxLength= 36,required=false)  
    private String billid;   
    
	/*
	 * 进口付款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PAYMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private ImportPayment importPayment;   
    

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
     *    获得付款清票ID
     * @return 付款清票ID : String
     */
    public String getPaymentcbillid()
    {
		return this.paymentcbillid;
    }

    /**
     * 功能描述:
     *    设置付款清票ID
     * @param paymentcbillid : String
     */
    public void setPaymentcbillid(String paymentcbillid)
    {
	    this.paymentcbillid = paymentcbillid;
    }
    

    /**
     * 功能描述:
     *    获得立项号
     * @return 立项号 : String
     */
    public String getProject_no()
    {
		return this.project_no;
    }

    /**
     * 功能描述:
     *    设置立项号
     * @param project_no : String
     */
    public void setProject_no(String project_no)
    {
	    this.project_no = project_no;
    }
    

    /**
     * 功能描述:
     *    获得财务凭证号
     * @return 财务凭证号 : String
     */
    public String getVoucherno()
    {
		return this.voucherno;
    }

    /**
     * 功能描述:
     *    设置财务凭证号
     * @param voucherno : String
     */
    public void setVoucherno(String voucherno)
    {
	    this.voucherno = voucherno;
    }
    

    /**
     * 功能描述:
     *    获得发票号
     * @return 发票号 : String
     */
    public String getBillno()
    {
		return this.billno;
    }

    /**
     * 功能描述:
     *    设置发票号
     * @param billno : String
     */
    public void setBillno(String billno)
    {
	    this.billno = billno;
    }
    

    /**
     * 功能描述:
     *    获得外部纸质合同号
     * @return 外部纸质合同号 : String
     */
    public String getOld_contract_no()
    {
		return this.old_contract_no;
    }

    /**
     * 功能描述:
     *    设置外部纸质合同号
     * @param old_contract_no : String
     */
    public void setOld_contract_no(String old_contract_no)
    {
	    this.old_contract_no = old_contract_no;
    }
    

    /**
     * 功能描述:
     *    获得SAP订单号
     * @return SAP订单号 : String
     */
    public String getSap_order_no()
    {
		return this.sap_order_no;
    }

    /**
     * 功能描述:
     *    设置SAP订单号
     * @param sap_order_no : String
     */
    public void setSap_order_no(String sap_order_no)
    {
	    this.sap_order_no = sap_order_no;
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
     *    获得应付款金额
     * @return 应付款金额 : BigDecimal
     */
    public BigDecimal getPayableamount()
    {
		return this.payableamount;
    }

    /**
     * 功能描述:
     *    设置应付款金额
     * @param payableamount : BigDecimal
     */
    public void setPayableamount(BigDecimal payableamount)
    {
	    this.payableamount = payableamount;
    }
    

    /**
     * 功能描述:
     *    获得应付款日
     * @return 应付款日 : String
     */
    public String getPayabledate()
    {
		return this.payabledate;
    }

    /**
     * 功能描述:
     *    设置应付款日
     * @param payabledate : String
     */
    public void setPayabledate(String payabledate)
    {
	    this.payabledate = payabledate;
    }
    

    /**
     * 功能描述:
     *    获得已付款
     * @return 已付款 : BigDecimal
     */
    public BigDecimal getPaidamount()
    {
		return this.paidamount;
    }

    /**
     * 功能描述:
     *    设置已付款
     * @param paidamount : BigDecimal
     */
    public void setPaidamount(BigDecimal paidamount)
    {
	    this.paidamount = paidamount;
    }
    

    /**
     * 功能描述:
     *    获得未付款
     * @return 未付款 : BigDecimal
     */
    public BigDecimal getUnpaidamount()
    {
		return this.unpaidamount;
    }

    /**
     * 功能描述:
     *    设置未付款
     * @param unpaidamount : BigDecimal
     */
    public void setUnpaidamount(BigDecimal unpaidamount)
    {
	    this.unpaidamount = unpaidamount;
    }
    

    /**
     * 功能描述:
     *    获得在批金额
     * @return 在批金额 : BigDecimal
     */
    public BigDecimal getOnroadamount()
    {
		return this.onroadamount;
    }

    /**
     * 功能描述:
     *    设置在批金额
     * @param onroadamount : BigDecimal
     */
    public void setOnroadamount(BigDecimal onroadamount)
    {
	    this.onroadamount = onroadamount;
    }
    

    /**
     * 功能描述:
     *    获得清帐金额
     * @return 清帐金额 : BigDecimal
     */
    public BigDecimal getClearamount2()
    {
		return this.clearamount2;
    }

    /**
     * 功能描述:
     *    设置清帐金额
     * @param clearamount2 : BigDecimal
     */
    public void setClearamount2(BigDecimal clearamount2)
    {
	    this.clearamount2 = clearamount2;
    }
    

    /**
     * 功能描述:
     *    获得采购合同号
     * @return 采购合同号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置采购合同号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
    }
    

    /**
     * 功能描述:
     *    获得记账日期
     * @return 记账日期 : String
     */
    public String getAccountdate()
    {
		return this.accountdate;
    }

    /**
     * 功能描述:
     *    设置记账日期
     * @param accountdate : String
     */
    public void setAccountdate(String accountdate)
    {
	    this.accountdate = accountdate;
    }
    

    /**
     * 功能描述:
     *    获得文本
     * @return 文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置文本
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
    }
    

    /**
     * 功能描述:
     *    获得发票ID
     * @return 发票ID : String
     */
    public String getBillid()
    {
		return this.billid;
    }

    /**
     * 功能描述:
     *    设置发票ID
     * @param billid : String
     */
    public void setBillid(String billid)
    {
	    this.billid = billid;
    }
    

    /**
     * 功能描述:
     *    获得进口付款
     * @return 进口付款 : ImportPayment
     */
    public ImportPayment getImportPayment()
    {
		return this.importPayment;
    }

    /**
     * 功能描述:
     *    设置进口付款
     * @param importPayment : ImportPayment
     */
    public void setImportPayment(ImportPayment importPayment)
    {
	    this.importPayment = importPayment;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ImportPaymentCbill()
    {
    	super();
    }
}
