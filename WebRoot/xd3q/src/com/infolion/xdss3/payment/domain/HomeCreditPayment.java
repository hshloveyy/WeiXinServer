/*
 * @(#)HomeCreditPayment.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 15点22分20秒
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
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;

/**
 * <pre>
 * 国内信用证(HomeCreditPayment)实体类
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
@Table(name = "YPAYMENT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomeCreditPayment extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 付款ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PAYMENTID")
      
    private String paymentid;   
    
	/*
	 * 付款单号
	 */
    @Column(name="PAYMENTNO")
    @ValidateRule(dataType=9,label="付款单号",maxLength= 30,required=false)  
    private String paymentno;   
    
	/*
	 * 供应商
	 */
    @Column(name="SUPPLIER")
    @ValidateRule(dataType=9,label="供应商",maxLength= 50,required=false)  
    private String supplier;   
    
	/*
	 * 付款单状态
	 */
    @Column(name="PAYMENTSTATE")
    @ValidateRule(dataType=9,label="付款单状态",maxLength= 1,required=false)  
    private String paymentstate;   
    
	/*
	 * 贸易方式
	 */
    @Column(name="TRADE_TYPE")
    @ValidateRule(dataType=9,label="贸易方式",maxLength= 2,required=false)  
    private String trade_type;   
    
	/*
	 * 付款方式
	 */
    @Column(name="PAYMENTTYPE")
    @ValidateRule(dataType=9,label="付款方式",maxLength= 3,required=false)  
    private String paymenttype;   
    
	/*
	 * 申请金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请金额",required=false)  
    private BigDecimal applyamount;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 收款银行
	 */
    @Column(name="COLLECTBANKID")
    @ValidateRule(dataType=9,label="收款银行",maxLength= 36,required=false)  
    private String collectbankid;   
    
	/*
	 * 付款人
	 */
    @Column(name="PAYER")
    @ValidateRule(dataType=9,label="付款人",maxLength= 50,required=false)  
    private String payer;   
    
	/*
	 * 银行对外付款日
	 */
    @Column(name="PAYDATE")
    @ValidateRule(dataType=9,label="银行对外付款日",maxLength= 20,required=false)  
    private String paydate;   
    
	/*
	 * 押汇币别
	 */
    @Column(name="CURRENCY2")
    @ValidateRule(dataType=9,label="押汇币别",maxLength= 10,required=false)  
    private String currency2;   
    
	/*
	 * 押汇日(押汇)
	 */
    @Column(name="DOCUMENTARYDATE")
    @ValidateRule(dataType=9,label="押汇日(押汇)",maxLength= 20,required=false)  
    private String documentarydate;   
    
	/*
	 * 押汇到期付款日
	 */
    @Column(name="DOCUMENTARYPAYDT")
    @ValidateRule(dataType=9,label="押汇到期付款日",maxLength= 20,required=false)  
    private String documentarypaydt;   
    
	/*
	 * 押汇期限
	 */
    @Column(name="DOCUMENTARYLIMIT")
    @ValidateRule(dataType=9,label="押汇期限",maxLength= 20,required=false)  
    private String documentarylimit;   
    
	/*
	 * 押汇汇率
	 */
    @Column(name="DOCUMENTARYRATE")
    @ValidateRule(dataType=0,label="押汇汇率",required=false)  
    private BigDecimal documentaryrate;   
    
	/*
	 * 实际付款日期
	 */
    @Column(name="PAYREALDATE")
    @ValidateRule(dataType=9,label="实际付款日期",maxLength= 20,required=false)  
    private String payrealdate;   
    
	/*
	 * 实际付款金额（押汇）
	 */
    @Column(name="PAYREALAMOUNT")
    @ValidateRule(dataType=0,label="实际付款金额（押汇）",required=false)  
    private BigDecimal payrealamount;   
    
	/*
	 * 实际押汇期限
	 */
    @Column(name="DOCTARYREALLIMIT")
    @ValidateRule(dataType=9,label="实际押汇期限",maxLength= 20,required=false)  
    private String doctaryreallimit;   
    
	/*
	 * 实际押汇汇率
	 */
    @Column(name="DOCTARYREALRATE")
    @ValidateRule(dataType=0,label="实际押汇汇率",required=false)  
    private BigDecimal doctaryrealrate;   
    
	/*
	 * 押汇利率
	 */
    @Column(name="DOCTARYINTEREST")
    @ValidateRule(dataType=9,label="押汇利率",maxLength= 20,required=false)  
    private String doctaryinterest;   
    
	/*
	 * 核销单号
	 */
    @Column(name="WRITELISTNO")
    @ValidateRule(dataType=9,label="核销单号",maxLength= 100,required=false)  
    private String writelistno;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPT_ID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String dept_id;   
    
    /**
	 * 部门名称
	 */
	@Transient
	private String deptName;

	
	/*
	 * 预计到货日
	 */
    @Column(name="ARRIVEGOODSDATE")
    @ValidateRule(dataType=9,label="预计到货日",maxLength= 20,required=false)  
    private String arrivegoodsdate;   
    
	/*
	 * 抬头文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="抬头文本",maxLength= 255,required=false)  
    private String text;   
    
	/*
	 * 备注
	 */
    @Column(name="REMARK")
    @ValidateRule(dataType=9,label="备注",maxLength= 255,required=false)  
    private String remark;   
    
	/*
	 * 记账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=9,label="记账日期",maxLength= 20,required=false)  
    private String accountdate;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=9,label="凭证日期",maxLength= 20,required=false)  
    private String voucherdate;   
    
	/*
	 * 实际付款金额
	 */
    @Column(name="FACTAMOUNT")
    @ValidateRule(dataType=0,label="实际付款金额",required=false)  
    private BigDecimal factamount;   
    
	/*
	 * 实际币别
	 */
    @Column(name="FACTCURRENCY")
    @ValidateRule(dataType=9,label="实际币别",maxLength= 5,required=false)  
    private String factcurrency;   
    
	/*
	 * 付款汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="付款汇率",required=false)  
    private BigDecimal exchangerate;   
    
	/*
	 * 实际汇率
	 */
    @Column(name="FACTEXCHANGERATE")
    @ValidateRule(dataType=0,label="实际汇率",required=false)  
    private BigDecimal factexchangerate;   
    
	/*
	 * 业务状态
	 */
    @Column(name="BUSINESSSTATE")
    @ValidateRule(dataType=9,label="业务状态",maxLength= 2,required=false)  
    private String businessstate;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 50,required=false)  
    private String processstate;   
    
	/*
	 * 创建人
	 */
    @Column(name="CREATOR")
    @ValidateRule(dataType=9,label="创建人",maxLength= 36,required=false)  
    private String creator;   
    
    /**
	 * 申请人
	 */
	@Transient
	private String creatorName;
	
	/*
	 * 创建日期
	 */
    @Column(name="CREATETIME")
    @ValidateRule(dataType=9,label="创建日期",required=false)  
    private String createtime;   
    
	/*
	 * 最后修改者
	 */
    @Column(name="LASTMODIFYER")
    @ValidateRule(dataType=9,label="最后修改者",maxLength= 36,required=false)  
    private String lastmodifyer;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="LASTMODIFYTIME")
    @ValidateRule(dataType=9,label="最后修改日期",required=false)  
    private String lastmodifytime;   
    
	/*
	 * 付款类型
	 */
    @Column(name="PAY_TYPE")
    @ValidateRule(dataType=9,label="付款类型",maxLength= 20,required=false)  
    private String pay_type;   
    
	/*
	 * 收款银行
	 */
    @Column(name="COLLECTBANKNAME")
    @ValidateRule(dataType=9,label="收款银行",maxLength= 150,required=false)  
    private String collectbankname;   
    
	/*
	 * 收款银行账号
	 */
    @Column(name="COLLECTBANKACC")
    @ValidateRule(dataType=9,label="收款银行账号",maxLength= 50,required=false)  
    private String collectbankacc;   
    
	/*
	 * 是否贸管预付款
	 */
    @Column(name="ISTRADESUBSIST")
    @ValidateRule(dataType=9,label="是否贸管预付款",maxLength= 1,required=false)  
    private String istradesubsist;   
    
	/*
	 * 是否纯代理付款
	 */
    @Column(name="ISREPRESENTPAY")
    @ValidateRule(dataType=9,label="是否纯代理付款",maxLength= 1,required=false)  
    private String isrepresentpay;   
    
	/*
	 * 纯代理付款客户
	 */
    @Column(name="REPRESENTPAYCUST")
    @ValidateRule(dataType=9,label="纯代理付款客户",maxLength= 10,required=false)  
    private String representpaycust;   
    
	/*
	 * 结算汇率
	 */
    @Column(name="CLOSEEXCHANGERAT")
    @ValidateRule(dataType=0,label="结算汇率",required=false)  
    private BigDecimal closeexchangerat;   
    
	/*
	 * 收款银行科目
	 */
    @Column(name="COLLECTBANKSUBJE")
    @ValidateRule(dataType=9,label="收款银行科目",maxLength= 20,required=false)  
    private String collectbanksubje;   
    
	/*
	 * 应收票日
	 */
    @Column(name="MUSTTAKETICKLEDA")
    @ValidateRule(dataType=9,label="应收票日",maxLength= 20,required=false)  
    private String musttaketickleda;   
    
	/*
	 * 财务编号
	 */
    @Column(name="FINUMBER")
    @ValidateRule(dataType=9,label="财务编号",maxLength= 20,required=false)  
    private String finumber;   
    
	/*
	 * 票据银行(国内证)
	 */
    @Column(name="TICKETBANKID")
    @ValidateRule(dataType=9,label="票据银行(国内证)",maxLength= 36,required=false)  
    private String ticketbankid;   
    
	/*
	 * 票据做账银行
	 */
    @Column(name="TICKETBANKNAME")
    @ValidateRule(dataType=9,label="票据做账银行",maxLength= 255,required=false)  
    private String ticketbankname;   
    
	/*
	 * 票据做账银行科目
	 */
    @Column(name="TICKETBANKSUBJEC")
    @ValidateRule(dataType=9,label="票据做账银行科目",maxLength= 20,required=false)  
    private String ticketbanksubjec;   
    
	/*
	 * 单据号码
	 */
    @Column(name="DRAFT")
    @ValidateRule(dataType=9,label="单据号码",maxLength= 30,required=false)  
    private String draft;   
    
	/*
	 * 银行承兑汇票/国内信用证到期日
	 */
    @Column(name="DRAFTDATE")
    @ValidateRule(dataType=9,label="银行承兑汇票/国内信用证到期日",maxLength= 20,required=false)  
    private String draftdate;   
    
	/*
	 * 代垫到期日
	 */
    @Column(name="REPLACEDATE")
    @ValidateRule(dataType=9,label="代垫到期日",maxLength= 20,required=false)  
    private String replacedate;   
    
	/*
	 * 被重做付款ID
	 */
    @Column(name="REPAYMENTID")
    @ValidateRule(dataType=9,label="被重做付款ID",maxLength= 36,required=false)  
    private String repaymentid;   
    
	/*
	 * 是否海外代付
	 */
    @Column(name="ISOVERREPAY")
    @ValidateRule(dataType=9,label="是否海外代付",maxLength= 1,required=false)  
    private String isoverrepay;   
    
	/*
	 * 还押汇金额
	 */
    @Column(name="REDOCARYAMOUNT")
    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
    private BigDecimal redocaryamount;   
    
	/*
	 * 还押汇汇率
	 */
    @Column(name="REDOCARYRATE")
    @ValidateRule(dataType=0,label="还押汇汇率",required=false)  
    private BigDecimal redocaryrate;   
    
	/*
	 * 押汇业务范围
	 */
    @Column(name="REDOCARYBC")
    @ValidateRule(dataType=9,label="押汇业务范围",maxLength= 36,required=false)  
    private String redocarybc;   
    
	/*
	 * 付款基准日
	 */
    @Column(name="EXPIREDATE")
    @ValidateRule(dataType=9,label="付款基准日",maxLength= 20,required=false)  
    private String expiredate;   
    
	/*
	 * 票据业务范围
	 */
    @Column(name="BILLBC")
    @ValidateRule(dataType=9,label="票据业务范围",maxLength= 36,required=false)  
    private String billbc;   
    
	/*
	 * 折算人民币金额
	 */
    @Column(name="CONVERTAMOUNT")
    @ValidateRule(dataType=0,label="折算人民币金额",required=false)  
    private BigDecimal convertamount;   
    
	/*
	 * 国内证付款行项目
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditPayItem> homeCreditPayItem;   
    
	/*
	 * 发票清帐
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditPayCbill> homeCreditPayCbill;   
    
	/*
	 * 付款银行
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditBankItem> homeCreditBankItem;   
    
	/*
	 * 押汇银行
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditDocuBank> homeCreditDocuBank;   
    
	/*
	 * 结算科目
	 */
    @OneToOne(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubject settleSubject;   
    
	/*
	 * 纯资金往来
	 */
    @OneToOne(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private FundFlow fundFlow;   
    
	/*
	 * 国内证还押汇银行
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditRebank> homeCreditRebank;   
    
	/*
	 * 相关单据
	 */
    @OneToMany(mappedBy="homeCreditPayment",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<HomeCreditRelatPay> homeCreditRelatPay;   
    
	/*
	 * 还押汇金额
	 */
    @Column(name="REDOCARYAMOUNT2")
    @ValidateRule(dataType=0,label="还押汇金额",required=false)  
    private BigDecimal redocaryamount2;   
    

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
     *    获得付款ID
     * @return 付款ID : String
     */
    public String getPaymentid()
    {
		return this.paymentid;
    }

    /**
     * 功能描述:
     *    设置付款ID
     * @param paymentid : String
     */
    public void setPaymentid(String paymentid)
    {
	    this.paymentid = paymentid;
    }
    

    /**
     * 功能描述:
     *    获得付款单号
     * @return 付款单号 : String
     */
    public String getPaymentno()
    {
		return this.paymentno;
    }

    /**
     * 功能描述:
     *    设置付款单号
     * @param paymentno : String
     */
    public void setPaymentno(String paymentno)
    {
	    this.paymentno = paymentno;
    }
    

    /**
     * 功能描述:
     *    获得供应商
     * @return 供应商 : String
     */
    public String getSupplier()
    {
		return this.supplier;
    }

    /**
     * 功能描述:
     *    设置供应商
     * @param supplier : String
     */
    public void setSupplier(String supplier)
    {
	    this.supplier = supplier;
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
     *    获得贸易方式
     * @return 贸易方式 : String
     */
    public String getTrade_type()
    {
		return this.trade_type;
    }

    /**
     * 功能描述:
     *    设置贸易方式
     * @param trade_type : String
     */
    public void setTrade_type(String trade_type)
    {
	    this.trade_type = trade_type;
    }
    

    /**
     * 功能描述:
     *    获得付款方式
     * @return 付款方式 : String
     */
    public String getPaymenttype()
    {
		return this.paymenttype;
    }

    /**
     * 功能描述:
     *    设置付款方式
     * @param paymenttype : String
     */
    public void setPaymenttype(String paymenttype)
    {
	    this.paymenttype = paymenttype;
    }
    

    /**
     * 功能描述:
     *    获得申请金额
     * @return 申请金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
    	if (applyamount == null)
			return new BigDecimal(0L);
		else
			return this.applyamount;
		
    }

    /**
     * 功能描述:
     *    设置申请金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
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
     *    获得收款银行
     * @return 收款银行 : String
     */
    public String getCollectbankid()
    {
		return this.collectbankid;
    }

    /**
     * 功能描述:
     *    设置收款银行
     * @param collectbankid : String
     */
    public void setCollectbankid(String collectbankid)
    {
	    this.collectbankid = collectbankid;
    }
    

    /**
     * 功能描述:
     *    获得付款人
     * @return 付款人 : String
     */
    public String getPayer()
    {
		return this.payer;
    }

    /**
     * 功能描述:
     *    设置付款人
     * @param payer : String
     */
    public void setPayer(String payer)
    {
	    this.payer = payer;
    }
    

    /**
     * 功能描述:
     *    获得银行对外付款日
     * @return 银行对外付款日 : String
     */
    public String getPaydate()
    {
    	return DateUtils.toDisplayStr(this.paydate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置银行对外付款日
     * @param paydate : String
     */
    public void setPaydate(String paydate)
    {
    	paydate = DateUtils.toStoreStr(paydate);
	    this.paydate = paydate;
    }
    

    /**
     * 功能描述:
     *    获得押汇币别
     * @return 押汇币别 : String
     */
    public String getCurrency2()
    {
		return this.currency2;
    }

    /**
     * 功能描述:
     *    设置押汇币别
     * @param currency2 : String
     */
    public void setCurrency2(String currency2)
    {
	    this.currency2 = currency2;
    }
    

    /**
     * 功能描述:
     *    获得押汇日(押汇)
     * @return 押汇日(押汇) : String
     */
    public String getDocumentarydate()
    {
    	return DateUtils.toDisplayStr(this.documentarydate, DateUtils.HYPHEN_DISPLAY_DATE);		
    }

    /**
     * 功能描述:
     *    设置押汇日(押汇)
     * @param documentarydate : String
     */
    public void setDocumentarydate(String documentarydate)
    {
    	documentarydate = DateUtils.toStoreStr(documentarydate);
	    this.documentarydate = documentarydate;
    }
    

    /**
     * 功能描述:
     *    获得押汇到期付款日
     * @return 押汇到期付款日 : String
     */
    public String getDocumentarypaydt()
    {
    	return DateUtils.toDisplayStr(this.documentarypaydt, DateUtils.HYPHEN_DISPLAY_DATE);		
		
    }

    /**
     * 功能描述:
     *    设置押汇到期付款日
     * @param documentarypaydt : String
     */
    public void setDocumentarypaydt(String documentarypaydt)
    {
    	documentarypaydt = DateUtils.toStoreStr(documentarypaydt);
	    this.documentarypaydt = documentarypaydt;
    }
    

    /**
     * 功能描述:
     *    获得押汇期限
     * @return 押汇期限 : String
     */
    public String getDocumentarylimit()
    {
		return this.documentarylimit;
    }

    /**
     * 功能描述:
     *    设置押汇期限
     * @param documentarylimit : String
     */
    public void setDocumentarylimit(String documentarylimit)
    {
	    this.documentarylimit = documentarylimit;
    }
    

    /**
     * 功能描述:
     *    获得押汇汇率
     * @return 押汇汇率 : BigDecimal
     */
    public BigDecimal getDocumentaryrate()
    {
    	if (documentaryrate == null)
			return new BigDecimal("1");
		else
		return this.documentaryrate;
    }

    /**
     * 功能描述:
     *    设置押汇汇率
     * @param documentaryrate : BigDecimal
     */
    public void setDocumentaryrate(BigDecimal documentaryrate)
    {
	    this.documentaryrate = documentaryrate;
    }
    

    /**
     * 功能描述:
     *    获得实际付款日期
     * @return 实际付款日期 : String
     */
    public String getPayrealdate()
    {
    	return DateUtils.toDisplayStr(this.payrealdate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置实际付款日期
     * @param payrealdate : String
     */
    public void setPayrealdate(String payrealdate)
    {
    	payrealdate = DateUtils.toStoreStr(payrealdate);
	    this.payrealdate = payrealdate;
    }
    

    /**
     * 功能描述:
     *    获得实际付款金额（押汇）
     * @return 实际付款金额（押汇） : BigDecimal
     */
    public BigDecimal getPayrealamount()
    {
    	if (payrealamount == null)
			return new BigDecimal(0L);
		else
		return this.payrealamount;
    }

    /**
     * 功能描述:
     *    设置实际付款金额（押汇）
     * @param payrealamount : BigDecimal
     */
    public void setPayrealamount(BigDecimal payrealamount)
    {
	    this.payrealamount = payrealamount;
    }
    

    /**
     * 功能描述:
     *    获得实际押汇期限
     * @return 实际押汇期限 : String
     */
    public String getDoctaryreallimit()
    {
		return this.doctaryreallimit;
    }

    /**
     * 功能描述:
     *    设置实际押汇期限
     * @param doctaryreallimit : String
     */
    public void setDoctaryreallimit(String doctaryreallimit)
    {
	    this.doctaryreallimit = doctaryreallimit;
    }
    

    /**
     * 功能描述:
     *    获得实际押汇汇率
     * @return 实际押汇汇率 : BigDecimal
     */
    public BigDecimal getDoctaryrealrate()
    {
    	if (doctaryrealrate == null)
			return new BigDecimal("1");
		else
		return this.doctaryrealrate;
    }

    /**
     * 功能描述:
     *    设置实际押汇汇率
     * @param doctaryrealrate : BigDecimal
     */
    public void setDoctaryrealrate(BigDecimal doctaryrealrate)
    {
	    this.doctaryrealrate = doctaryrealrate;
    }
    

    /**
     * 功能描述:
     *    获得押汇利率
     * @return 押汇利率 : String
     */
    public String getDoctaryinterest()
    {
		return this.doctaryinterest;
    }

    /**
     * 功能描述:
     *    设置押汇利率
     * @param doctaryinterest : String
     */
    public void setDoctaryinterest(String doctaryinterest)
    {
	    this.doctaryinterest = doctaryinterest;
    }
    

    /**
     * 功能描述:
     *    获得核销单号
     * @return 核销单号 : String
     */
    public String getWritelistno()
    {
		return this.writelistno;
    }

    /**
     * 功能描述:
     *    设置核销单号
     * @param writelistno : String
     */
    public void setWritelistno(String writelistno)
    {
	    this.writelistno = writelistno;
    }
    

    /**
     * 功能描述:
     *    获得部门ID
     * @return 部门ID : String
     */
    public String getDept_id()
    {
		return this.dept_id;
    }

    /**
     * 功能描述:
     *    设置部门ID
     * @param dept_id : String
     */
    public void setDept_id(String dept_id)
    {
	    this.dept_id = dept_id;
    }
    

    /**
     * 功能描述:
     *    获得预计到货日
     * @return 预计到货日 : String
     */
    public String getArrivegoodsdate()
    {
    	return DateUtils.toDisplayStr(this.arrivegoodsdate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置预计到货日
     * @param arrivegoodsdate : String
     */
    public void setArrivegoodsdate(String arrivegoodsdate)
    {
    	arrivegoodsdate = DateUtils.toStoreStr(arrivegoodsdate);
	    this.arrivegoodsdate = arrivegoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得抬头文本
     * @return 抬头文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置抬头文本
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
    }
    

    /**
     * 功能描述:
     *    获得备注
     * @return 备注 : String
     */
    public String getRemark()
    {
		return this.remark;
    }

    /**
     * 功能描述:
     *    设置备注
     * @param remark : String
     */
    public void setRemark(String remark)
    {
	    this.remark = remark;
    }
    

    /**
     * 功能描述:
     *    获得记账日期
     * @return 记账日期 : String
     */
    public String getAccountdate()
    {
    	return DateUtils.toDisplayStr(this.accountdate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置记账日期
     * @param accountdate : String
     */
    public void setAccountdate(String accountdate)
    {
    	accountdate = DateUtils.toStoreStr(accountdate);
	    this.accountdate = accountdate;
    }
    

    /**
     * 功能描述:
     *    获得凭证日期
     * @return 凭证日期 : String
     */
    public String getVoucherdate()
    {
    	return DateUtils.toDisplayStr(this.voucherdate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置凭证日期
     * @param voucherdate : String
     */
    public void setVoucherdate(String voucherdate)
    {
    	voucherdate = DateUtils.toStoreStr(voucherdate);
	    this.voucherdate = voucherdate;
    }
    

    /**
     * 功能描述:
     *    获得实际付款金额
     * @return 实际付款金额 : BigDecimal
     */
    public BigDecimal getFactamount()
    {
    	if (factamount == null)
			return new BigDecimal(0L);
		else
		return this.factamount;
    }

    /**
     * 功能描述:
     *    设置实际付款金额
     * @param factamount : BigDecimal
     */
    public void setFactamount(BigDecimal factamount)
    {
	    this.factamount = factamount;
    }
    

    /**
     * 功能描述:
     *    获得实际币别
     * @return 实际币别 : String
     */
    public String getFactcurrency()
    {
		return this.factcurrency;
    }

    /**
     * 功能描述:
     *    设置实际币别
     * @param factcurrency : String
     */
    public void setFactcurrency(String factcurrency)
    {
	    this.factcurrency = factcurrency;
    }
    

    /**
     * 功能描述:
     *    获得付款汇率
     * @return 付款汇率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
    	if (exchangerate == null)
			return new BigDecimal(0L);
		else
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置付款汇率
     * @param exchangerate : BigDecimal
     */
    public void setExchangerate(BigDecimal exchangerate)
    {
	    this.exchangerate = exchangerate;
    }
    

    /**
     * 功能描述:
     *    获得实际汇率
     * @return 实际汇率 : BigDecimal
     */
    public BigDecimal getFactexchangerate()
    {
    	if (factexchangerate == null)
			return new BigDecimal(0L);
		else
		return this.factexchangerate;
    }

    /**
     * 功能描述:
     *    设置实际汇率
     * @param factexchangerate : BigDecimal
     */
    public void setFactexchangerate(BigDecimal factexchangerate)
    {
	    this.factexchangerate = factexchangerate;
    }
    

    /**
     * 功能描述:
     *    获得业务状态
     * @return 业务状态 : String
     */
    public String getBusinessstate()
    {
		return this.businessstate;
    }

    /**
     * 功能描述:
     *    设置业务状态
     * @param businessstate : String
     */
    public void setBusinessstate(String businessstate)
    {
	    this.businessstate = businessstate;
    }
    

    /**
     * 功能描述:
     *    获得流程状态
     * @return 流程状态 : String
     */
    public String getProcessstate()
    {
		return this.processstate;
    }

    /**
     * 功能描述:
     *    设置流程状态
     * @param processstate : String
     */
    public void setProcessstate(String processstate)
    {
	    this.processstate = processstate;
    }
    

    /**
     * 功能描述:
     *    获得创建人
     * @return 创建人 : String
     */
    public String getCreator()
    {
		return this.creator;
    }

    /**
     * 功能描述:
     *    设置创建人
     * @param creator : String
     */
    public void setCreator(String creator)
    {
	    this.creator = creator;
    }
    

    /**
     * 功能描述:
     *    获得创建日期
     * @return 创建日期 : String
     */
    public String getCreatetime()
    {
    	return DateUtils.toDisplayStr(this.createtime, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置创建日期
     * @param createtime : String
     */
    public void setCreatetime(String createtime)
    {
    	createtime = DateUtils.toStoreStr(createtime);
	    this.createtime = createtime;
    }
    

    /**
     * 功能描述:
     *    获得最后修改者
     * @return 最后修改者 : String
     */
    public String getLastmodifyer()
    {
		return this.lastmodifyer;
    }

    /**
     * 功能描述:
     *    设置最后修改者
     * @param lastmodifyer : String
     */
    public void setLastmodifyer(String lastmodifyer)
    {
	    this.lastmodifyer = lastmodifyer;
    }
    

    /**
     * 功能描述:
     *    获得最后修改日期
     * @return 最后修改日期 : String
     */
    public String getLastmodifytime()
    {
    	return DateUtils.toDisplayStr(this.lastmodifytime, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifytime : String
     */
    public void setLastmodifytime(String lastmodifytime)
    {
    	lastmodifytime = DateUtils.toStoreStr(lastmodifytime);
	    this.lastmodifytime = lastmodifytime;
    }
    

    /**
     * 功能描述:
     *    获得付款类型
     * @return 付款类型 : String
     */
    public String getPay_type()
    {
		return this.pay_type;
    }

    /**
     * 功能描述:
     *    设置付款类型
     * @param pay_type : String
     */
    public void setPay_type(String pay_type)
    {
	    this.pay_type = pay_type;
    }
    

    /**
     * 功能描述:
     *    获得收款银行
     * @return 收款银行 : String
     */
    public String getCollectbankname()
    {
		return this.collectbankname;
    }

    /**
     * 功能描述:
     *    设置收款银行
     * @param collectbankname : String
     */
    public void setCollectbankname(String collectbankname)
    {
	    this.collectbankname = collectbankname;
    }
    

    /**
     * 功能描述:
     *    获得收款银行账号
     * @return 收款银行账号 : String
     */
    public String getCollectbankacc()
    {
		return this.collectbankacc;
    }

    /**
     * 功能描述:
     *    设置收款银行账号
     * @param collectbankacc : String
     */
    public void setCollectbankacc(String collectbankacc)
    {
	    this.collectbankacc = collectbankacc;
    }
    

    /**
     * 功能描述:
     *    获得是否贸管预付款
     * @return 是否贸管预付款 : String
     */
    public String getIstradesubsist()
    {
		return this.istradesubsist;
    }

    /**
     * 功能描述:
     *    设置是否贸管预付款
     * @param istradesubsist : String
     */
    public void setIstradesubsist(String istradesubsist)
    {
	    this.istradesubsist = istradesubsist;
    }
    

    /**
     * 功能描述:
     *    获得是否纯代理付款
     * @return 是否纯代理付款 : String
     */
    public String getIsrepresentpay()
    {
		return this.isrepresentpay;
    }

    /**
     * 功能描述:
     *    设置是否纯代理付款
     * @param isrepresentpay : String
     */
    public void setIsrepresentpay(String isrepresentpay)
    {
	    this.isrepresentpay = isrepresentpay;
    }
    

    /**
     * 功能描述:
     *    获得纯代理付款客户
     * @return 纯代理付款客户 : String
     */
    public String getRepresentpaycust()
    {
		return this.representpaycust;
    }

    /**
     * 功能描述:
     *    设置纯代理付款客户
     * @param representpaycust : String
     */
    public void setRepresentpaycust(String representpaycust)
    {
	    this.representpaycust = representpaycust;
    }
    

    /**
     * 功能描述:
     *    获得结算汇率
     * @return 结算汇率 : BigDecimal
     */
    public BigDecimal getCloseexchangerat()
    {
    	if (closeexchangerat == null)
			return new BigDecimal(0L);
		else
		return this.closeexchangerat;
    }

    /**
     * 功能描述:
     *    设置结算汇率
     * @param closeexchangerat : BigDecimal
     */
    public void setCloseexchangerat(BigDecimal closeexchangerat)
    {
	    this.closeexchangerat = closeexchangerat;
    }
    

    /**
     * 功能描述:
     *    获得收款银行科目
     * @return 收款银行科目 : String
     */
    public String getCollectbanksubje()
    {
		return this.collectbanksubje;
    }

    /**
     * 功能描述:
     *    设置收款银行科目
     * @param collectbanksubje : String
     */
    public void setCollectbanksubje(String collectbanksubje)
    {
	    this.collectbanksubje = collectbanksubje;
    }
    

    /**
     * 功能描述:
     *    获得应收票日
     * @return 应收票日 : String
     */
    public String getMusttaketickleda()
    {
    	return DateUtils.toDisplayStr(this.musttaketickleda, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置应收票日
     * @param musttaketickleda : String
     */
    public void setMusttaketickleda(String musttaketickleda)
    {
    	musttaketickleda = DateUtils.toStoreStr(musttaketickleda);
	    this.musttaketickleda = musttaketickleda;
    }
    

    /**
     * 功能描述:
     *    获得财务编号
     * @return 财务编号 : String
     */
    public String getFinumber()
    {
		return this.finumber;
    }

    /**
     * 功能描述:
     *    设置财务编号
     * @param finumber : String
     */
    public void setFinumber(String finumber)
    {
	    this.finumber = finumber;
    }
    

    /**
     * 功能描述:
     *    获得票据银行(国内证)
     * @return 票据银行(国内证) : String
     */
    public String getTicketbankid()
    {
		return this.ticketbankid;
    }

    /**
     * 功能描述:
     *    设置票据银行(国内证)
     * @param ticketbankid : String
     */
    public void setTicketbankid(String ticketbankid)
    {
	    this.ticketbankid = ticketbankid;
    }
    

    /**
     * 功能描述:
     *    获得票据做账银行
     * @return 票据做账银行 : String
     */
    public String getTicketbankname()
    {
		return this.ticketbankname;
    }

    /**
     * 功能描述:
     *    设置票据做账银行
     * @param ticketbankname : String
     */
    public void setTicketbankname(String ticketbankname)
    {
	    this.ticketbankname = ticketbankname;
    }
    

    /**
     * 功能描述:
     *    获得票据做账银行科目
     * @return 票据做账银行科目 : String
     */
    public String getTicketbanksubjec()
    {
		return this.ticketbanksubjec;
    }

    /**
     * 功能描述:
     *    设置票据做账银行科目
     * @param ticketbanksubjec : String
     */
    public void setTicketbanksubjec(String ticketbanksubjec)
    {
	    this.ticketbanksubjec = ticketbanksubjec;
    }
    

    /**
     * 功能描述:
     *    获得单据号码
     * @return 单据号码 : String
     */
    public String getDraft()
    {
		return this.draft;
    }

    /**
     * 功能描述:
     *    设置单据号码
     * @param draft : String
     */
    public void setDraft(String draft)
    {
	    this.draft = draft;
    }
    

    /**
     * 功能描述:
     *    获得银行承兑汇票/国内信用证到期日
     * @return 银行承兑汇票/国内信用证到期日 : String
     */
    public String getDraftdate()
    {
    	return DateUtils.toDisplayStr(this.draftdate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置银行承兑汇票/国内信用证到期日
     * @param draftdate : String
     */
    public void setDraftdate(String draftdate)
    {
    	draftdate = DateUtils.toStoreStr(draftdate);
	    this.draftdate = draftdate;
    }
    

    /**
     * 功能描述:
     *    获得代垫到期日
     * @return 代垫到期日 : String
     */
    public String getReplacedate()
    {
    	return DateUtils.toDisplayStr(this.replacedate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置代垫到期日
     * @param replacedate : String
     */
    public void setReplacedate(String replacedate)
    {
    	replacedate = DateUtils.toStoreStr(replacedate);
	    this.replacedate = replacedate;
    }
    

    /**
     * 功能描述:
     *    获得被重做付款ID
     * @return 被重做付款ID : String
     */
    public String getRepaymentid()
    {
		return this.repaymentid;
    }

    /**
     * 功能描述:
     *    设置被重做付款ID
     * @param repaymentid : String
     */
    public void setRepaymentid(String repaymentid)
    {
	    this.repaymentid = repaymentid;
    }
    

    /**
     * 功能描述:
     *    获得是否海外代付
     * @return 是否海外代付 : String
     */
    public String getIsoverrepay()
    {
		return this.isoverrepay;
    }

    /**
     * 功能描述:
     *    设置是否海外代付
     * @param isoverrepay : String
     */
    public void setIsoverrepay(String isoverrepay)
    {
	    this.isoverrepay = isoverrepay;
    }
    

    /**
     * 功能描述:
     *    获得还押汇金额
     * @return 还押汇金额 : BigDecimal
     */
    public BigDecimal getRedocaryamount()
    {
    	if (redocaryamount == null)
			return new BigDecimal(0L);
		else
		return this.redocaryamount;
    }

    /**
     * 功能描述:
     *    设置还押汇金额
     * @param redocaryamount : BigDecimal
     */
    public void setRedocaryamount(BigDecimal redocaryamount)
    {
	    this.redocaryamount = redocaryamount;
    }
    

    /**
     * 功能描述:
     *    获得还押汇汇率
     * @return 还押汇汇率 : BigDecimal
     */
    public BigDecimal getRedocaryrate()
    {
    	if (redocaryrate == null)
			return new BigDecimal("1");
		else
		return this.redocaryrate;
    }

    /**
     * 功能描述:
     *    设置还押汇汇率
     * @param redocaryrate : BigDecimal
     */
    public void setRedocaryrate(BigDecimal redocaryrate)
    {
	    this.redocaryrate = redocaryrate;
    }
    

    /**
     * 功能描述:
     *    获得押汇业务范围
     * @return 押汇业务范围 : String
     */
    public String getRedocarybc()
    {
		return this.redocarybc;
    }

    /**
     * 功能描述:
     *    设置押汇业务范围
     * @param redocarybc : String
     */
    public void setRedocarybc(String redocarybc)
    {
	    this.redocarybc = redocarybc;
    }
    

    /**
     * 功能描述:
     *    获得付款基准日
     * @return 付款基准日 : String
     */
    public String getExpiredate()
    {
    	return DateUtils.toDisplayStr(this.expiredate, DateUtils.HYPHEN_DISPLAY_DATE);
		
    }

    /**
     * 功能描述:
     *    设置付款基准日
     * @param expiredate : String
     */
    public void setExpiredate(String expiredate)
    {
    	expiredate = DateUtils.toStoreStr(expiredate);
	    this.expiredate = expiredate;
    }
    

    /**
     * 功能描述:
     *    获得票据业务范围
     * @return 票据业务范围 : String
     */
    public String getBillbc()
    {
		return this.billbc;
    }

    /**
     * 功能描述:
     *    设置票据业务范围
     * @param billbc : String
     */
    public void setBillbc(String billbc)
    {
	    this.billbc = billbc;
    }
    

    /**
     * 功能描述:
     *    获得折算人民币金额
     * @return 折算人民币金额 : BigDecimal
     */
    public BigDecimal getConvertamount()
    {
    	if (convertamount == null)
			return new BigDecimal(0L);
		else
		return this.convertamount;
    }

    /**
     * 功能描述:
     *    设置折算人民币金额
     * @param convertamount : BigDecimal
     */
    public void setConvertamount(BigDecimal convertamount)
    {
	    this.convertamount = convertamount;
    }
    

    /**
     * 功能描述:
     *    获得国内证付款行项目
     * @return 国内证付款行项目 : Set<HomeCreditPayItem>
     */
    public Set<HomeCreditPayItem> getHomeCreditPayItem()
    {
		return this.homeCreditPayItem;
    }

    /**
     * 功能描述:
     *    设置国内证付款行项目
     * @param homeCreditPayItem : Set<HomeCreditPayItem>
     */
    public void setHomeCreditPayItem(Set<HomeCreditPayItem> homeCreditPayItem)
    {
	    this.homeCreditPayItem = homeCreditPayItem;
    }
    

    /**
     * 功能描述:
     *    获得发票清帐
     * @return 发票清帐 : Set<HomeCreditPayCbill>
     */
    public Set<HomeCreditPayCbill> getHomeCreditPayCbill()
    {
		return this.homeCreditPayCbill;
    }

    /**
     * 功能描述:
     *    设置发票清帐
     * @param homeCreditPayCbill : Set<HomeCreditPayCbill>
     */
    public void setHomeCreditPayCbill(Set<HomeCreditPayCbill> homeCreditPayCbill)
    {
	    this.homeCreditPayCbill = homeCreditPayCbill;
    }
    

    /**
     * 功能描述:
     *    获得付款银行
     * @return 付款银行 : Set<HomeCreditBankItem>
     */
    public Set<HomeCreditBankItem> getHomeCreditBankItem()
    {
		return this.homeCreditBankItem;
    }

    /**
     * 功能描述:
     *    设置付款银行
     * @param homeCreditBankItem : Set<HomeCreditBankItem>
     */
    public void setHomeCreditBankItem(Set<HomeCreditBankItem> homeCreditBankItem)
    {
	    this.homeCreditBankItem = homeCreditBankItem;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行
     * @return 押汇银行 : Set<HomeCreditDocuBank>
     */
    public Set<HomeCreditDocuBank> getHomeCreditDocuBank()
    {
		return this.homeCreditDocuBank;
    }

    /**
     * 功能描述:
     *    设置押汇银行
     * @param homeCreditDocuBank : Set<HomeCreditDocuBank>
     */
    public void setHomeCreditDocuBank(Set<HomeCreditDocuBank> homeCreditDocuBank)
    {
	    this.homeCreditDocuBank = homeCreditDocuBank;
    }
    

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : SettleSubject
     */
    public SettleSubject getSettleSubject()
    {
		return this.settleSubject;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settleSubject : SettleSubject
     */
    public void setSettleSubject(SettleSubject settleSubject)
    {
	    this.settleSubject = settleSubject;
    }
    

    /**
     * 功能描述:
     *    获得纯资金往来
     * @return 纯资金往来 : FundFlow
     */
    public FundFlow getFundFlow()
    {
		return this.fundFlow;
    }

    /**
     * 功能描述:
     *    设置纯资金往来
     * @param fundFlow : FundFlow
     */
    public void setFundFlow(FundFlow fundFlow)
    {
	    this.fundFlow = fundFlow;
    }
    

    /**
     * 功能描述:
     *    获得国内证还押汇银行
     * @return 国内证还押汇银行 : Set<HomeCreditRebank>
     */
    public Set<HomeCreditRebank> getHomeCreditRebank()
    {
		return this.homeCreditRebank;
    }

    /**
     * 功能描述:
     *    设置国内证还押汇银行
     * @param homeCreditRebank : Set<HomeCreditRebank>
     */
    public void setHomeCreditRebank(Set<HomeCreditRebank> homeCreditRebank)
    {
	    this.homeCreditRebank = homeCreditRebank;
    }
    

    /**
     * 功能描述:
     *    获得相关单据
     * @return 相关单据 : Set<HomeCreditRelatPay>
     */
    public Set<HomeCreditRelatPay> getHomeCreditRelatPay()
    {
		return this.homeCreditRelatPay;
    }

    /**
     * 功能描述:
     *    设置相关单据
     * @param homeCreditRelatPay : Set<HomeCreditRelatPay>
     */
    public void setHomeCreditRelatPay(Set<HomeCreditRelatPay> homeCreditRelatPay)
    {
	    this.homeCreditRelatPay = homeCreditRelatPay;
    }
    

    /**
     * 功能描述:
     *    获得还押汇金额
     * @return 还押汇金额 : BigDecimal
     */
    public BigDecimal getRedocaryamount2()
    {
    	if (redocaryamount2 == null)
			return new BigDecimal(0L);
		else
		return this.redocaryamount2;
    }

    /**
     * 功能描述:
     *    设置还押汇金额
     * @param redocaryamount2 : BigDecimal
     */
    public void setRedocaryamount2(BigDecimal redocaryamount2)
    {
	    this.redocaryamount2 = redocaryamount2;
    }
    
    
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 *  默认构造器
	 */
    public HomeCreditPayment()
    {
    	super();
    }
}
