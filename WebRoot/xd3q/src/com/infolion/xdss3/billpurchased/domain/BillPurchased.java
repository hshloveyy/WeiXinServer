/*
 * @(#)BillPurchased.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.billpurchased.domain;

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
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankTwo;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.domain.FundFlow;

/**
 * <pre>
 * 出口押汇(BillPurchased)实体类
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
@Table(name = "YBILLPURCHASED")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillPurchased extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 出口押汇ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BILLPURID")
      
    private String billpurid;   
    
	/*
	 * 出口押汇单号
	 */
    @Column(name="BILLPUR_NO")
    @ValidateRule(dataType=9,label="出口押汇单号",maxLength= 100,required=false)  
    private String billpur_no;   
    
	/*
	 * 申请押汇金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请押汇金额",required=true)  
    private BigDecimal applyamount;   
    
	/*
	 * 押汇币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="押汇币别",maxLength= 10,required=true)  
    private String currency;   
    
	/*
	 * 押汇汇率
	 */
    @Column(name="BILLPURRATE")
    @ValidateRule(dataType=0,label="押汇汇率",required=false)  
    private BigDecimal billpurrate;   
    
    /*
     * 押汇利率
     */
    @Column(name="DOCUMENTARYINTER")
    @ValidateRule(dataType=0,label="押汇利率",required=false)  
    private BigDecimal documentaryinter;
    
    /*
     * 押汇期限
     */
    @Column(name="DOCUMENTARYLIMIT")
    @ValidateRule(dataType=9,label="押汇期限",required=false)  
    private String documentarylimit;   
    
	/*
	 * 押汇收款日
	 */
    @Column(name="PAYREALDATE")
    @ValidateRule(dataType=8,label="押汇收款日",required=false)  
    private String payrealdate;   
    
	/*
	 * 押汇到期日
	 */
    @Column(name="MATURITY")
    @ValidateRule(dataType=8,label="押汇到期日",required=false)  
    private String maturity;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=8,label="凭证日期",required=false)  
    private String voucherdate;   
    
	/*
	 * 记账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=8,label="记账日期",required=false)  
    private String accountdate;   
    
	/*
	 * 抬头文本(押汇用途)
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="抬头文本(押汇用途)",maxLength= 200,required=false)  
    private String text;   
    
	/*
	 * 还押汇凭证日期
	 */
    @Column(name="REVOUCHERDATE")
    @ValidateRule(dataType=8,label="还押汇凭证日期",required=false)  
    private String revoucherdate;   
    
	/*
	 * 还押汇记帐日期
	 */
    @Column(name="REACCOUNTDATE")
    @ValidateRule(dataType=8,label="还押汇记帐日期",required=false)  
    private String reaccountdate;   
    
	/*
	 * 抬头文本(还押汇用途)
	 */
    @Column(name="RETEXT")
    @ValidateRule(dataType=9,label="抬头文本(还押汇用途)",maxLength= 200,required=false)  
    private String retext;   
    
	/*
	 * 备注
	 */
    @Column(name="REMARK")
    @ValidateRule(dataType=9,label="备注",maxLength= 255,required=false)  
    private String remark;   
    
	/*
	 * 创建人
	 */
    @Column(name="CREATOR")
    @ValidateRule(dataType=9,label="创建人",maxLength= 36,required=false)  
    private String creator;   
    
	/*
	 * 创建日期
	 */
    @Column(name="CREATETIME")
    @ValidateRule(dataType=8,label="创建日期",required=false)  
    private String createtime;   
    
	/*
	 * 部门编号
	 */
    @Column(name="DEPT_ID")
    @ValidateRule(dataType=9,label="部门编号",maxLength= 36,required=false)  
    private String dept_id;   
    
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
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
	/*
	 * 最后修改者
	 */
    @Column(name="LASTMODIYER")
    @ValidateRule(dataType=9,label="最后修改者",maxLength= 36,required=false)  
    private String lastmodiyer;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="LASTMODIFYTIME")
    @ValidateRule(dataType=9,label="最后修改日期",maxLength= 14,required=false)  
    private String lastmodifytime;   
    
	/*
	 * 发票
	 */
    @OneToMany(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BillPurBillItem> billPurBillItem;   
    
	/*
	 * 押汇银行
	 */
    @OneToMany(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BillPurBankItem> billPurBankItem;   
    
	/*
	 * 还押汇银行
	 */
    @OneToMany(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BillPurReBankItem> billPurReBankItem;   
    
    /*
     * 还押汇银行2
     */
    @OneToMany(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
    
    private Set<BillPurReBankTwo> billPurReBankTwo;   
    
	/*
	 * 结算科目
	 */
    @OneToOne(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubject settleSubject;   
    
	/*
	 * 纯资金往来
	 */
    @OneToOne(mappedBy="billPurchased",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private FundFlow fundFlow;   
    

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
     *    获得出口押汇ID
     * @return 出口押汇ID : String
     */
    public String getBillpurid()
    {
		return this.billpurid;
    }

    /**
     * 功能描述:
     *    设置出口押汇ID
     * @param billpurid : String
     */
    public void setBillpurid(String billpurid)
    {
	    this.billpurid = billpurid;
    }
    

    /**
     * 功能描述:
     *    获得出口押汇单号
     * @return 出口押汇单号 : String
     */
    public String getBillpur_no()
    {
		return this.billpur_no;
    }

    /**
     * 功能描述:
     *    设置出口押汇单号
     * @param billpur_no : String
     */
    public void setBillpur_no(String billpur_no)
    {
	    this.billpur_no = billpur_no;
    }
    

    /**
     * 功能描述:
     *    获得申请押汇金额
     * @return 申请押汇金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
		return this.applyamount;
    }

    /**
     * 功能描述:
     *    设置申请押汇金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
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
     *    获得押汇汇率
     * @return 押汇汇率 : BigDecimal
     */
    public BigDecimal getBillpurrate()
    {
		return this.billpurrate;
    }

    /**
     * 功能描述:
     *    设置押汇汇率
     * @param billpurrate : BigDecimal
     */
    public void setBillpurrate(BigDecimal billpurrate)
    {
	    this.billpurrate = billpurrate;
    }
    

    /**
     * 功能描述:
     *    获得押汇收款日
     * @return 押汇收款日 : String
     */
    public String getPayrealdate()
    {
    	return DateUtils.toDisplayStr(this.payrealdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置押汇收款日
     * @param payrealdate : String
     */
    public void setPayrealdate(String payrealdate)
    {
    	payrealdate = DateUtils.toStoreStr(payrealdate);
	    this.payrealdate = payrealdate;
    }
    

    /**
     * 功能描述:
     *    获得押汇到期日
     * @return 押汇到期日 : String
     */
    public String getMaturity()
    {
    	return DateUtils.toDisplayStr(this.maturity, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置押汇到期日
     * @param maturity : String
     */
    public void setMaturity(String maturity)
    {
    	maturity = DateUtils.toStoreStr(maturity);
	    this.maturity = maturity;
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
     *    获得抬头文本(押汇用途)
     * @return 抬头文本(押汇用途) : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置抬头文本(押汇用途)
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
    }
    

    /**
     * 功能描述:
     *    获得还押汇凭证日期
     * @return 还押汇凭证日期 : String
     */
    public String getRevoucherdate()
    {
    	return DateUtils.toDisplayStr(this.revoucherdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置还押汇凭证日期
     * @param revoucherdate : String
     */
    public void setRevoucherdate(String revoucherdate)
    {
    	revoucherdate = DateUtils.toStoreStr(revoucherdate);
	    this.revoucherdate = revoucherdate;
    }
    

    /**
     * 功能描述:
     *    获得还押汇记帐日期
     * @return 还押汇记帐日期 : String
     */
    public String getReaccountdate()
    {
    	return DateUtils.toDisplayStr(this.reaccountdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置还押汇记帐日期
     * @param reaccountdate : String
     */
    public void setReaccountdate(String reaccountdate)
    {
    	reaccountdate = DateUtils.toStoreStr(reaccountdate);
	    this.reaccountdate = reaccountdate;
    }
    

    /**
     * 功能描述:
     *    获得抬头文本(还押汇用途)
     * @return 抬头文本(还押汇用途) : String
     */
    public String getRetext()
    {
		return this.retext;
    }

    /**
     * 功能描述:
     *    设置抬头文本(还押汇用途)
     * @param retext : String
     */
    public void setRetext(String retext)
    {
	    this.retext = retext;
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
     *    获得部门编号
     * @return 部门编号 : String
     */
    public String getDept_id()
    {
		return this.dept_id;
    }

    /**
     * 功能描述:
     *    设置部门编号
     * @param dept_id : String
     */
    public void setDept_id(String dept_id)
    {
	    this.dept_id = dept_id;
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
     *    获得最后修改者
     * @return 最后修改者 : String
     */
    public String getLastmodiyer()
    {
		return this.lastmodiyer;
    }

    /**
     * 功能描述:
     *    设置最后修改者
     * @param lastmodiyer : String
     */
    public void setLastmodiyer(String lastmodiyer)
    {
	    this.lastmodiyer = lastmodiyer;
    }
    

    /**
     * 功能描述:
     *    获得最后修改日期
     * @return 最后修改日期 : String
     */
    public String getLastmodifytime()
    {
		return this.lastmodifytime;
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifytime : String
     */
    public void setLastmodifytime(String lastmodifytime)
    {
	    this.lastmodifytime = lastmodifytime;
    }
    

    /**
     * 功能描述:
     *    获得发票
     * @return 发票 : Set<BillPurBillItem>
     */
    public Set<BillPurBillItem> getBillPurBillItem()
    {
		return this.billPurBillItem;
    }

    /**
     * 功能描述:
     *    设置发票
     * @param billPurBillItem : Set<BillPurBillItem>
     */
    public void setBillPurBillItem(Set<BillPurBillItem> billPurBillItem)
    {
	    this.billPurBillItem = billPurBillItem;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行
     * @return 押汇银行 : Set<BillPurBankItem>
     */
    public Set<BillPurBankItem> getBillPurBankItem()
    {
		return this.billPurBankItem;
    }

    /**
     * 功能描述:
     *    设置押汇银行
     * @param billPurBankItem : Set<BillPurBankItem>
     */
    public void setBillPurBankItem(Set<BillPurBankItem> billPurBankItem)
    {
	    this.billPurBankItem = billPurBankItem;
    }
    

    /**
     * 功能描述:
     *    获得还押汇银行
     * @return 还押汇银行 : Set<BillPurReBankItem>
     */
    public Set<BillPurReBankItem> getBillPurReBankItem()
    {
		return this.billPurReBankItem;
    }

    /**
     * 功能描述:
     *    设置还押汇银行
     * @param billPurReBankItem : Set<BillPurReBankItem>
     */
    public void setBillPurReBankItem(Set<BillPurReBankItem> billPurReBankItem)
    {
	    this.billPurReBankItem = billPurReBankItem;
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
     * @return the documentaryinter
     */
    public BigDecimal getDocumentaryinter() {
        return documentaryinter;
    }

    /**
     * @param documentaryinter the documentaryinter to set
     */
    public void setDocumentaryinter(BigDecimal documentaryinter) {
        this.documentaryinter = documentaryinter;
    }

    /**
     * @return the documentarylimit
     */
    public String getDocumentarylimit() {
        return documentarylimit;
    }

    /**
     * @param documentarylimit the documentarylimit to set
     */
    public void setDocumentarylimit(String documentarylimit) {
        this.documentarylimit = documentarylimit;
    }

    /**
     * @return the billPurReBankTwo
     */
    public Set<BillPurReBankTwo> getBillPurReBankTwo() {
        return billPurReBankTwo;
    }

    /**
     * @param billPurReBankTwo the billPurReBankTwo to set
     */
    public void setBillPurReBankTwo(Set<BillPurReBankTwo> billPurReBankTwo) {
        this.billPurReBankTwo = billPurReBankTwo;
    }

    /**
	 *  默认构造器
	 */
    public BillPurchased()
    {
    	super();
    }
}
