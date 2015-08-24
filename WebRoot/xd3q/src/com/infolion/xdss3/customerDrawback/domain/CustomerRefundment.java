/*
 * @(#)CustomerRefundment.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 17点13分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;

/**
 * <pre>
 * 客户退款(CustomerRefundment)实体类
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
@Table(name = "YREFUNDMENT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class CustomerRefundment extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 退款表ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "REFUNDMENTID")
	private String refundmentid;

	/*
	 * 退款单号
	 */
	@Column(name = "REFUNDMENTNO")
	@ValidateRule(dataType = 9, label = "退款单号", maxLength = 12, required = false)
	private String refundmentno;

	/*
	 * 被重分配退款ID
	 */
	@Column(name = "REASSIGNEDDBID")
	@ValidateRule(dataType = 9, label = "被重分配退款ID", maxLength = 36, required = false)
	private String reassigneddbid;

	/*
	 * 客户
	 */
	@Column(name = "CUSTOMER")
	@ValidateRule(dataType = 9, label = "客户", maxLength = 36, required = true)
	private String customer;

	/*
	 * 供应商
	 */
	@Column(name = "SUPPLIER")
	@ValidateRule(dataType = 9, label = "供应商", maxLength = 36, required = false)
	private String supplier;

	/*
	 * 部门
	 */
	@Column(name = "DEPT_ID")
	@ValidateRule(dataType = 9, label = "部门", maxLength = 36, required = false)
	private String dept_id;
    
	/**
     * 部门名称
     */
    @Transient
    private String deptName;
    
	/*
	 * 抬头文本
	 */
	@Column(name = "TEXT")
	@ValidateRule(dataType = 9, label = "抬头文本", maxLength = 255, required = true)
	private String text;

	/*
	 * 备注
	 */
	@Column(name = "REMARK")
	@ValidateRule(dataType = 9, label = "备注", maxLength = 255, required = false)
	private String remark;

	/*
	 * 汇率
	 */
	@Column(name = "EXCHANGERATE")
	@ValidateRule(dataType = 0, label = "汇率", required = false)
	private BigDecimal exchangerate;

	/*
	 * 记账日期
	 */
	@Column(name = "ACCOUNTDATE")
	@ValidateRule(dataType = 8, label = "记账日期", required = false)
	private String accountdate;

	/*
	 * 凭证日期
	 */
	@Column(name = "VOUCHERDATE")
	@ValidateRule(dataType = 8, label = "凭证日期", required = false)
	private String voucherdate;

	/*
	 * 现金流项目
	 */
	@Column(name = "CASHFLOWITEM")
	@ValidateRule(dataType = 9, label = "现金流项目", maxLength = 10, required = false)
	private String cashflowitem;

	/*
	 * 流程状态
	 */
	@Column(name = "PROCESSSTATE")
	@ValidateRule(dataType = 9, label = "流程状态", maxLength = 50, required = false)
	private String processstate;

	/*
	 * 业务状态
	 */
	@Column(name = "BUSINESSSTATE")
	@ValidateRule(dataType = 9, label = "业务状态", maxLength = 2, required = false)
	private String businessstate;

	/*
	 * 贸易方式
	 */
	@Column(name = "TRADETYPE")
	@ValidateRule(dataType = 9, label = "贸易方式", maxLength = 2, required = false)
	private String tradetype;

	/*
	 * 创建人
	 */
	@Column(name = "CREATOR")
	@ValidateRule(dataType = 9, label = "创建人", maxLength = 36, required = false)
	private String creator;

    /**
     * 申请人
     */
    @Transient
    private String creatorName;
    
	/*
	 * 创建日期
	 */
	@Column(name = "CREATETIME")
	@ValidateRule(dataType = 8, label = "创建日期", required = false)
	private String createtime;

	/*
	 * 最后修改者
	 */
	@Column(name = "LASTMODIYER")
	@ValidateRule(dataType = 9, label = "最后修改者", maxLength = 36, required = false)
	private String lastmodiyer;

	/*
	 * 最后修改日期
	 */
	@Column(name = "LASTMODIFYTIME")
	@ValidateRule(dataType = 8, label = "最后修改日期", required = false)
	private String lastmodifytime;

    /*
     * 收款银行
     */
    @Column(name = "COLLECTBANKNAME")
    @ValidateRule(dataType = 9, label = "收款银行", maxLength = 150, required = false)
    private String collectbankname;
    
    /*
     * 收款银行帐号
     */
    @Column(name = "COLLECTBANKACC")
    @ValidateRule(dataType = 9, label = "收款银行帐号", maxLength = 50, required = false)
    private String collectbankacc;
    
	/*
	 * 客户退款银行
	 */
	@OneToMany(mappedBy = "customerRefundment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<CustomerDbBankItem> customerDbBankItem;

	/*
	 * 客户退款行项目
	 */
	@OneToMany(mappedBy = "customerRefundment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<CustomerRefundItem> customerRefundItem;
    
    /*
     * 退款币别
     */
    @Column(name="REFUNDCURRENCY")
    @ValidateRule(dataType=9,label="退款币别",maxLength= 10,required=true)  
    private String refundcurrency;   
    
    /*
     * 退款金额
     */
    @Column(name="REFUNDAMOUNT")
    @ValidateRule(dataType=0,label="退款金额",required=true)  
    private BigDecimal refundamount;
    
    /*
     * 结算科目
     */
    @OneToOne(mappedBy="customerRefundment",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
    private SettleSubject settleSubject;
    
    /*
     * 纯资金往来
     */
    @OneToOne(mappedBy="customerRefundment",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
    private FundFlow fundFlow;
    
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
	 * 功能描述: 获得退款表ID
	 * 
	 * @return 退款表ID : String
	 */
	public String getRefundmentid()
	{
		return this.refundmentid;
	}

	/**
	 * 功能描述: 设置退款表ID
	 * 
	 * @param refundmentid
	 *            : String
	 */
	public void setRefundmentid(String refundmentid)
	{
		this.refundmentid = refundmentid;
	}

	/**
	 * 功能描述: 获得退款单号
	 * 
	 * @return 退款单号 : String
	 */
	public String getRefundmentno()
	{
		return this.refundmentno;
	}

	/**
	 * 功能描述: 设置退款单号
	 * 
	 * @param refundmentno
	 *            : String
	 */
	public void setRefundmentno(String refundmentno)
	{
		this.refundmentno = refundmentno;
	}

	/**
	 * 功能描述: 获得被重分配退款ID
	 * 
	 * @return 被重分配退款ID : String
	 */
	public String getReassigneddbid()
	{
		return this.reassigneddbid;
	}

	/**
	 * 功能描述: 设置被重分配退款ID
	 * 
	 * @param reassigneddbid
	 *            : String
	 */
	public void setReassigneddbid(String reassigneddbid)
	{
		this.reassigneddbid = reassigneddbid;
	}

	/**
	 * 功能描述: 获得客户
	 * 
	 * @return 客户 : String
	 */
	public String getCustomer()
	{
		return this.customer;
	}

	/**
	 * 功能描述: 设置客户
	 * 
	 * @param customer
	 *            : String
	 */
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	/**
	 * 功能描述: 获得供应商
	 * 
	 * @return 供应商 : String
	 */
	public String getSupplier()
	{
		return this.supplier;
	}

	/**
	 * 功能描述: 设置供应商
	 * 
	 * @param supplier
	 *            : String
	 */
	public void setSupplier(String supplier)
	{
		this.supplier = supplier;
	}

	/**
	 * 功能描述: 获得部门
	 * 
	 * @return 部门 : String
	 */
	public String getDept_id()
	{
		return this.dept_id;
	}

	/**
	 * 功能描述: 设置部门
	 * 
	 * @param dept_id
	 *            : String
	 */
	public void setDept_id(String dept_id)
	{
		this.dept_id = dept_id;
	}

	/**
	 * 功能描述: 获得抬头文本
	 * 
	 * @return 抬头文本 : String
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * 功能描述: 设置抬头文本
	 * 
	 * @param text
	 *            : String
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * 功能描述: 获得备注
	 * 
	 * @return 备注 : String
	 */
	public String getRemark()
	{
		return this.remark;
	}

	/**
	 * 功能描述: 设置备注
	 * 
	 * @param remark
	 *            : String
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	/**
	 * 功能描述: 获得汇率
	 * 
	 * @return 汇率 : BigDecimal
	 */
	public BigDecimal getExchangerate()
	{
		return this.exchangerate;
	}

	/**
	 * 功能描述: 设置汇率
	 * 
	 * @param exchangerate
	 *            : BigDecimal
	 */
	public void setExchangerate(BigDecimal exchangerate)
	{
		this.exchangerate = exchangerate;
	}

	/**
	 * 功能描述: 获得记账日期
	 * 
	 * @return 记账日期 : String
	 */
	public String getAccountdate()
	{
		return DateUtils.toDisplayStr(this.accountdate, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置记账日期
	 * 
	 * @param accountdate
	 *            : String
	 */
	public void setAccountdate(String accountdate)
	{
		accountdate = DateUtils.toStoreStr(accountdate);
		this.accountdate = accountdate;
	}

	/**
	 * 功能描述: 获得凭证日期
	 * 
	 * @return 凭证日期 : String
	 */
	public String getVoucherdate()
	{
		return DateUtils.toDisplayStr(this.voucherdate, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置凭证日期
	 * 
	 * @param voucherdate
	 *            : String
	 */
	public void setVoucherdate(String voucherdate)
	{
		voucherdate = DateUtils.toStoreStr(voucherdate);
		this.voucherdate = voucherdate;
	}

	/**
	 * 功能描述: 获得现金流项目
	 * 
	 * @return 现金流项目 : String
	 */
	public String getCashflowitem()
	{
		return this.cashflowitem;
	}

	/**
	 * 功能描述: 设置现金流项目
	 * 
	 * @param cashflowitem
	 *            : String
	 */
	public void setCashflowitem(String cashflowitem)
	{
		this.cashflowitem = cashflowitem;
	}

	/**
	 * 功能描述: 获得流程状态
	 * 
	 * @return 流程状态 : String
	 */
	public String getProcessstate()
	{
		return this.processstate;
	}

	/**
	 * 功能描述: 设置流程状态
	 * 
	 * @param processstate
	 *            : String
	 */
	public void setProcessstate(String processstate)
	{
		this.processstate = processstate;
	}

	/**
	 * 功能描述: 获得业务状态
	 * 
	 * @return 业务状态 : String
	 */
	public String getBusinessstate()
	{
		return this.businessstate;
	}

	/**
	 * 功能描述: 设置业务状态
	 * 
	 * @param businessstate
	 *            : String
	 */
	public void setBusinessstate(String businessstate)
	{
		this.businessstate = businessstate;
	}

	/**
	 * 功能描述: 获得贸易方式
	 * 
	 * @return 贸易方式 : String
	 */
	public String getTradetype()
	{
		return this.tradetype;
	}

	/**
	 * 功能描述: 设置贸易方式
	 * 
	 * @param tradetype
	 *            : String
	 */
	public void setTradetype(String tradetype)
	{
		this.tradetype = tradetype;
	}

	/**
	 * 功能描述: 获得创建人
	 * 
	 * @return 创建人 : String
	 */
	public String getCreator()
	{
		return this.creator;
	}

	/**
	 * 功能描述: 设置创建人
	 * 
	 * @param creator
	 *            : String
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	/**
	 * 功能描述: 获得创建日期
	 * 
	 * @return 创建日期 : String
	 */
	public String getCreatetime()
	{
		return DateUtils.toDisplayStr(this.createtime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置创建日期
	 * 
	 * @param createtime
	 *            : String
	 */
	public void setCreatetime(String createtime)
	{
		createtime = DateUtils.toStoreStr(createtime);
		this.createtime = createtime;
	}

	/**
	 * 功能描述: 获得最后修改者
	 * 
	 * @return 最后修改者 : String
	 */
	public String getLastmodiyer()
	{
		return this.lastmodiyer;
	}

	/**
	 * 功能描述: 设置最后修改者
	 * 
	 * @param lastmodiyer
	 *            : String
	 */
	public void setLastmodiyer(String lastmodiyer)
	{
		this.lastmodiyer = lastmodiyer;
	}

	/**
	 * 功能描述: 获得最后修改日期
	 * 
	 * @return 最后修改日期 : String
	 */
	public String getLastmodifytime()
	{
		return DateUtils.toDisplayStr(this.lastmodifytime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置最后修改日期
	 * 
	 * @param lastmodifytime
	 *            : String
	 */
	public void setLastmodifytime(String lastmodifytime)
	{
		lastmodifytime = DateUtils.toStoreStr(lastmodifytime);
		this.lastmodifytime = lastmodifytime;
	}

	/**
	 * 功能描述: 获得客户退款银行
	 * 
	 * @return 客户退款银行 : Set<CustomerDbBankItem>
	 */
	public Set<CustomerDbBankItem> getCustomerDbBankItem()
	{
		return this.customerDbBankItem;
	}

	/**
	 * 功能描述: 设置客户退款银行
	 * 
	 * @param customerDbBankItem
	 *            : Set<CustomerDbBankItem>
	 */
	public void setCustomerDbBankItem(Set<CustomerDbBankItem> customerDbBankItem)
	{
		this.customerDbBankItem = customerDbBankItem;
	}

	/**
	 * 功能描述: 获得客户退款行项目
	 * 
	 * @return 客户退款行项目 : Set<CustomerRefundItem>
	 */
	public Set<CustomerRefundItem> getCustomerRefundItem()
	{
		return this.customerRefundItem;
	}

	/**
	 * 功能描述: 设置客户退款行项目
	 * 
	 * @param customerRefundItem
	 *            : Set<CustomerRefundItem>
	 */
	public void setCustomerRefundItem(Set<CustomerRefundItem> customerRefundItem)
	{
		this.customerRefundItem = customerRefundItem;
	}
	 /**
     * 功能描述:
     *    获得退款币别
     * @return 退款币别 : String
     */
    public String getRefundcurrency()
    {
        return this.refundcurrency;
    }

    /**
     * 功能描述:
     *    设置退款币别
     * @param refundcurrency : String
     */
    public void setRefundcurrency(String refundcurrency)
    {
        this.refundcurrency = refundcurrency;
    }
    
	public BigDecimal getRefundamount() {
		return refundamount;
	}

	public void setRefundamount(BigDecimal refundamount) {
		this.refundamount = refundamount;
	}

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : SettleSubject
     */
    public SettleSubject getSettleSubject() {
        return settleSubject;
    }
    
    /**
     * 功能描述:
     *    设置结算科目
     * @param settleSubject : SettleSubject
     */
    public void setSettleSubject(SettleSubject settleSubject) {
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
     * @return the collectbankname
     */
    public String getCollectbankname() {
        return collectbankname;
    }

    /**
     * @param collectbankname the collectbankname to set
     */
    public void setCollectbankname(String collectbankname) {
        this.collectbankname = collectbankname;
    }

    /**
     * @return the collectbankacc
     */
    public String getCollectbankacc() {
        return collectbankacc;
    }

    /**
     * @param collectbankacc the collectbankacc to set
     */
    public void setCollectbankacc(String collectbankacc) {
        this.collectbankacc = collectbankacc;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
	 * 默认构造器
	 */
	public CustomerRefundment()
	{
		super();
	}
}
