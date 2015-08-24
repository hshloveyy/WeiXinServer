/*
 * @(#)ReceiptCpayment.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 15点46分16秒
 *  描　述：创建
 */
package com.infolion.sapss.receipts.domain;

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
 * 进仓清款(ReceiptCpayment)实体类
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
@Table(name = "YRECEIPTCPAYMENT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ReceiptCpayment extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 进仓清款编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="RECECPAYMENTID")
      
    private String rececpaymentid;   
    
	/*
	 * 进仓物料行编号
	 */
    @Column(name="RECEIPTDETAILID")
    @ValidateRule(dataType=9,label="进仓物料行编号",maxLength= 36,required=false)  
    private String receiptdetailid;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 36,required=false)  
    private String project_no;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 付款单状态
	 */
    @Column(name="PAYMENTSTATE")
    @ValidateRule(dataType=9,label="付款单状态",maxLength= 1,required=false)  
    private String paymentstate;   
    
	/*
	 * 预计到货日
	 */
    @Column(name="ARRIVEGOODSDATE")
    @ValidateRule(dataType=9,label="预计到货日",maxLength= 20,required=false)  
    private String arrivegoodsdate;   
    
	/*
	 * 付款金额
	 */
    @Column(name="PAYMENTAMOUNT")
    @ValidateRule(dataType=0,label="付款金额",required=false)  
    private BigDecimal paymentamount;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="汇率",required=false)  
    private BigDecimal exchangerate;   
    
	/*
	 * 已清金额
	 */
    @Column(name="OFFSETAMOUNT")
    @ValidateRule(dataType=0,label="已清金额",required=false)  
    private BigDecimal offsetamount;   
    
	/*
	 * 未清金额
	 */
    @Column(name="UNOFFSETAMOUNT")
    @ValidateRule(dataType=0,label="未清金额",required=false)  
    private BigDecimal unoffsetamount;   
    
	/*
	 * 在批金额
	 */
    @Column(name="ONROADAMOUNT")
    @ValidateRule(dataType=0,label="在批金额",required=false)  
    private BigDecimal onroadamount;   
    
	/*
	 * 本次已抵消金额
	 */
    @Column(name="NOWOFFSETAMOUNT")
    @ValidateRule(dataType=0,label="本次已抵消金额",required=false)  
    private BigDecimal nowoffsetamount;   
    
	/*
	 * 本行抵消金额
	 */
    @Column(name="CLEARAMOUNT")
    @ValidateRule(dataType=0,label="本行抵消金额",required=false)  
    private BigDecimal clearamount;   
    

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
     *    获得进仓清款编号
     * @return 进仓清款编号 : String
     */
    public String getRececpaymentid()
    {
		return this.rececpaymentid;
    }

    /**
     * 功能描述:
     *    设置进仓清款编号
     * @param rececpaymentid : String
     */
    public void setRececpaymentid(String rececpaymentid)
    {
	    this.rececpaymentid = rececpaymentid;
    }
    

    /**
     * 功能描述:
     *    获得进仓物料行编号
     * @return 进仓物料行编号 : String
     */
    public String getReceiptdetailid()
    {
		return this.receiptdetailid;
    }

    /**
     * 功能描述:
     *    设置进仓物料行编号
     * @param receiptdetailid : String
     */
    public void setReceiptdetailid(String receiptdetailid)
    {
	    this.receiptdetailid = receiptdetailid;
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
     *    获得项目编号
     * @return 项目编号 : String
     */
    public String getProject_no()
    {
		return this.project_no;
    }

    /**
     * 功能描述:
     *    设置项目编号
     * @param project_no : String
     */
    public void setProject_no(String project_no)
    {
	    this.project_no = project_no;
    }
    

    /**
     * 功能描述:
     *    获得合同号
     * @return 合同号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置合同号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
    }
    

    /**
     * 功能描述:
     *    获得付款单状态
     * @return 付款单状态 : String
     */
    public String getPaymentstate()
    {
		return this.paymentstate;
    }

    /**
     * 功能描述:
     *    设置付款单状态
     * @param paymentstate : String
     */
    public void setPaymentstate(String paymentstate)
    {
	    this.paymentstate = paymentstate;
    }
    

    /**
     * 功能描述:
     *    获得预计到货日
     * @return 预计到货日 : String
     */
    public String getArrivegoodsdate()
    {
		return this.arrivegoodsdate;
    }

    /**
     * 功能描述:
     *    设置预计到货日
     * @param arrivegoodsdate : String
     */
    public void setArrivegoodsdate(String arrivegoodsdate)
    {
	    this.arrivegoodsdate = arrivegoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得付款金额
     * @return 付款金额 : BigDecimal
     */
    public BigDecimal getPaymentamount()
    {
		return this.paymentamount;
    }

    /**
     * 功能描述:
     *    设置付款金额
     * @param paymentamount : BigDecimal
     */
    public void setPaymentamount(BigDecimal paymentamount)
    {
	    this.paymentamount = paymentamount;
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
     *    获得汇率
     * @return 汇率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置汇率
     * @param exchangerate : BigDecimal
     */
    public void setExchangerate(BigDecimal exchangerate)
    {
	    this.exchangerate = exchangerate;
    }
    

    /**
     * 功能描述:
     *    获得已清金额
     * @return 已清金额 : BigDecimal
     */
    public BigDecimal getOffsetamount()
    {
		return this.offsetamount;
    }

    /**
     * 功能描述:
     *    设置已清金额
     * @param offsetamount : BigDecimal
     */
    public void setOffsetamount(BigDecimal offsetamount)
    {
	    this.offsetamount = offsetamount;
    }
    

    /**
     * 功能描述:
     *    获得未清金额
     * @return 未清金额 : BigDecimal
     */
    public BigDecimal getUnoffsetamount()
    {
		return this.unoffsetamount;
    }

    /**
     * 功能描述:
     *    设置未清金额
     * @param unoffsetamount : BigDecimal
     */
    public void setUnoffsetamount(BigDecimal unoffsetamount)
    {
	    this.unoffsetamount = unoffsetamount;
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
     *    获得本次已抵消金额
     * @return 本次已抵消金额 : BigDecimal
     */
    public BigDecimal getNowoffsetamount()
    {
		return this.nowoffsetamount;
    }

    /**
     * 功能描述:
     *    设置本次已抵消金额
     * @param nowoffsetamount : BigDecimal
     */
    public void setNowoffsetamount(BigDecimal nowoffsetamount)
    {
	    this.nowoffsetamount = nowoffsetamount;
    }
    

    /**
     * 功能描述:
     *    获得本行抵消金额
     * @return 本行抵消金额 : BigDecimal
     */
    public BigDecimal getClearamount()
    {
		return this.clearamount;
    }

    /**
     * 功能描述:
     *    设置本行抵消金额
     * @param clearamount : BigDecimal
     */
    public void setClearamount(BigDecimal clearamount)
    {
	    this.clearamount = clearamount;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ReceiptCpayment()
    {
    	super();
    }
}
