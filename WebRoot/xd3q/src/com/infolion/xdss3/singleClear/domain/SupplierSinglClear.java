/*
 * @(#)SupplierSinglClear.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月12日 15点46分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.domain;

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
 * 供应商单清帐(SupplierSinglClear)实体类
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
@Table(name = "YSUPPLIERSICLEAR")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SupplierSinglClear extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 供应商单清帐ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "SUPPLIERSCLEARID")
	private String suppliersclearid;

	/*
	 * 供应商单清帐单号
	 */
    @Column(name="SUPPLIERCLEARNO")
    @ValidateRule(dataType=9,label="供应商单清帐单号",maxLength= 50,required=false)  
    private String supplierclearno;   
	
	/*
	 * 供应商
	 */
	@Column(name = "SUPPLIER")
	@ValidateRule(dataType = 9, label = "供应商", maxLength = 50, required = true)
	private String supplier;

	/*
	 * 统驭科目
	 */
	@Column(name = "SPECIALACCOUNT")
	@ValidateRule(dataType = 9, label = "统驭科目", maxLength = 1, required = false)
	private String specialaccount;

	/*
	 * 文本
	 */
	@Column(name = "TEXT")
	@ValidateRule(dataType = 9, label = "文本", maxLength = 100, required = false)
	private String text;

	/*
	 * 备注
	 */
	@Column(name = "REMARK")
	@ValidateRule(dataType = 9, label = "备注", maxLength = 255, required = false)
	private String remark;

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
	 * 部门ID
	 */
	@Column(name = "DEPTID")
	@ValidateRule(dataType = 9, label = "部门ID", maxLength = 36, required = false)
	private String deptid;

	/*
	 * 创建人
	 */
	@Column(name = "CREATOR")
	@ValidateRule(dataType = 9, label = "创建人", maxLength = 36, required = false)
	private String creator;

	/*
	 * 创建日期
	 */
	@Column(name = "CREATETIME")
	@ValidateRule(dataType = 8, label = "创建日期", required = false)
	private String createtime;

	/*
	 * 最后修改者
	 */
	@Column(name = "LASTMODIFYER")
	@ValidateRule(dataType = 9, label = "最后修改者", maxLength = 36, required = false)
	private String lastmodifyer;

	/*
	 * 最后修改日期
	 */
	@Column(name = "LASTMODIFYTIME")
	@ValidateRule(dataType = 8, label = "最后修改日期", required = false)
	private String lastmodifytime;

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
	 * 会计科目号
	 */
	@Column(name = "SUBJECT")
	@ValidateRule(dataType = 9, label = "会计科目号", maxLength = 10, required = true)
	private String subject;

	/*
	 * 货币文本
	 */
    @Column(name="CURRENCYTEXT")
    @ValidateRule(dataType=9,label="货币文本",maxLength= 20,required=false)  
    private String currencytext;   
    
	/*
	 * 公司代码
	 */
    @Column(name="COMPANYNO")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 20,required=false)  
    private String companyno;   
    
    /*
	 * 业务范围
	 */
	@Column(name = "DEPID")
	@ValidateRule(dataType = 9, label = "业务范围", maxLength = 12, required = false)
	private String depid;
	
	/*
	 * 未清应付（贷方）
	 */
	@OneToMany(mappedBy = "supplierSinglClear", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<UnClearSupplieBill> unClearSupplieBill;

	/*
	 * 未清付款（借方）
	 */
	@OneToMany(mappedBy = "supplierSinglClear", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<UnClearPayment> unClearPayment;

	/*
	 * 纯资金往来
	 */
	@OneToOne(mappedBy = "supplierSinglClear", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
	private FundFlow fundFlow;

	/*
	 * 结算科目
	 */
	@OneToOne(mappedBy = "supplierSinglClear", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
	private SettleSubject settleSubject;

	/**
	 * 供应商名称
	 */
	@Transient
	private String supplier_htext;

	/**
	 * 清帐科目名称
	 */
	@Transient
	private String subject_htext;

	/**
	 * 清帐科目名称
	 * 
	 * @return the subject_htext
	 */
	public String getSubject_htext()
	{
		return subject_htext;
	}

	/**
	 * 清帐科目名称
	 * 
	 * @param subject_htext
	 *            the subject_htext to set
	 */
	public void setSubject_htext(String subject_htext)
	{
		this.subject_htext = subject_htext;
	}

	/**
	 * 供应商名称
	 * 
	 * @return the supplier_htext
	 */
	public String getSupplier_htext()
	{
		return supplier_htext;
	}

	/**
	 * 供应商名称
	 * 
	 * @param supplier_htext
	 *            the supplier_htext to set
	 */
	public void setSupplier_htext(String supplier_htext)
	{
		this.supplier_htext = supplier_htext;
	}

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
	 * 功能描述: 获得供应商单清帐ID
	 * 
	 * @return 供应商单清帐ID : String
	 */
	public String getSuppliersclearid()
	{
		return this.suppliersclearid;
	}

	/**
	 * 功能描述: 设置供应商单清帐ID
	 * 
	 * @param suppliersclearid
	 *            : String
	 */
	public void setSuppliersclearid(String suppliersclearid)
	{
		this.suppliersclearid = suppliersclearid;
	}

	/**
	 * 供应商单清帐单号
	 * @return
	 */
	public String getSupplierclearno() {
		return supplierclearno;
	}
	/**
	 * 供应商单清帐单号
	 * @param supplierClearNo
	 */	
	public void setSupplierclearno(String supplierclearno) {
		this.supplierclearno = supplierclearno;
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
	 * 功能描述: 获得统驭科目
	 * 
	 * @return 统驭科目 : String
	 */
	public String getSpecialaccount()
	{
		return this.specialaccount;
	}

	/**
	 * 功能描述: 设置统驭科目
	 * 
	 * @param specialaccount
	 *            : String
	 */
	public void setSpecialaccount(String specialaccount)
	{
		this.specialaccount = specialaccount;
	}

	/**
	 * 功能描述: 获得文本
	 * 
	 * @return 文本 : String
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * 功能描述: 设置文本
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
	 * 功能描述: 获得部门ID
	 * 
	 * @return 部门ID : String
	 */
	public String getDeptid()
	{
		return this.deptid;
	}

	/**
	 * 功能描述: 设置部门ID
	 * 
	 * @param deptid
	 *            : String
	 */
	public void setDeptid(String deptid)
	{
		this.deptid = deptid;
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
	public String getLastmodifyer()
	{
		return this.lastmodifyer;
	}

	/**
	 * 功能描述: 设置最后修改者
	 * 
	 * @param lastmodifyer
	 *            : String
	 */
	public void setLastmodifyer(String lastmodifyer)
	{
		this.lastmodifyer = lastmodifyer;
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
	 * 功能描述: 获得会计科目号
	 * 
	 * @return 会计科目号 : String
	 */
	public String getSubject()
	{
		return this.subject;
	}

	/**
	 * 功能描述: 设置会计科目号
	 * 
	 * @param subject
	 *            : String
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * 功能描述: 获得未清应付（贷方）
	 * 
	 * @return 未清应付（贷方） : Set<UnClearSupplieBill>
	 */
	public Set<UnClearSupplieBill> getUnClearSupplieBill()
	{
		return this.unClearSupplieBill;
	}

	/**
	 * 功能描述: 设置未清应付（贷方）
	 * 
	 * @param unClearSupplieBill
	 *            : Set<UnClearSupplieBill>
	 */
	public void setUnClearSupplieBill(Set<UnClearSupplieBill> unClearSupplieBill)
	{
		this.unClearSupplieBill = unClearSupplieBill;
	}

	/**
	 * 功能描述: 获得未清付款（借方）
	 * 
	 * @return 未清付款（借方） : Set<UnClearPayment>
	 */
	public Set<UnClearPayment> getUnClearPayment()
	{
		return this.unClearPayment;
	}

	/**
	 * 功能描述: 设置未清付款（借方）
	 * 
	 * @param unClearPayment
	 *            : Set<UnClearPayment>
	 */
	public void setUnClearPayment(Set<UnClearPayment> unClearPayment)
	{
		this.unClearPayment = unClearPayment;
	}

	/**
	 * 功能描述: 获得纯资金往来
	 * 
	 * @return 纯资金往来 : FundFlow
	 */
	public FundFlow getFundFlow()
	{
		return this.fundFlow;
	}

	/**
	 * 功能描述: 设置纯资金往来
	 * 
	 * @param fundFlow
	 *            : FundFlow
	 */
	public void setFundFlow(FundFlow fundFlow)
	{
		this.fundFlow = fundFlow;
	}

	/**
	 * 功能描述: 获得结算科目
	 * 
	 * @return 结算科目 : SettleSubject
	 */
	public SettleSubject getSettleSubject()
	{
		return this.settleSubject;
	}

	/**
	 * 功能描述: 设置结算科目
	 * 
	 * @param settleSubject
	 *            : SettleSubject
	 */
	public void setSettleSubject(SettleSubject settleSubject)
	{
		this.settleSubject = settleSubject;
	}

	 /**
     * 功能描述:
     *    获得货币文本
     * @return 货币文本 : String
     */
    public String getCurrencytext()
    {
		return this.currencytext;
    }

    /**
     * 功能描述:
     *    设置货币文本
     * @param currencytext : String
     */
    public void setCurrencytext(String currencytext)
    {
	    this.currencytext = currencytext;
    }
    

    /**
     * 功能描述:
     *    获得公司代码
     * @return 公司代码 : String
     */
    public String getCompanyno()
    {
		return this.companyno;
    }

    /**
     * 功能描述:
     *    设置公司代码
     * @param companyno : String
     */
    public void setCompanyno(String companyno)
    {
	    this.companyno = companyno;
    }
    
    /**
     * 功能描述:
     *    获得业务范围
     * @return
     */
	public String getDepid() {
		return depid;
	}
	
	/**
	 * 功能描述:
     *    设置业务范围
	 * @param depid
	 */
	public void setDepid(String depid) {
		this.depid = depid;
	}

	/**
	 * 默认构造器
	 */
	public SupplierSinglClear()
	{
		super();
	}
}
