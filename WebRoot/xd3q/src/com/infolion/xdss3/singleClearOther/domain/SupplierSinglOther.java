/*
 * @(#)SupplierSinglOther.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点51分23秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOther.domain;

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
import com.infolion.xdss3.singleClearOther.domain.UnCleaPaymentOther;
import com.infolion.xdss3.singleClearOther.domain.UnSupplieBillOther;
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;

/**
 * <pre>
 * 其他公司供应商单清帐(SupplierSinglOther)实体类
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
@Table(name = "YSUPPLICLEAROTHE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class SupplierSinglOther extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 供应商单清帐ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="SUPPLIERSCLEARID")
      
    private String suppliersclearid;   
    
	/*
	 * 供应商
	 */
    @Column(name="SUPPLIER")
    @ValidateRule(dataType=9,label="供应商",maxLength= 50,required=true)  
    private String supplier;   
    
	/*
	 * 统驭科目
	 */
    @Column(name="SPECIALACCOUNT")
    @ValidateRule(dataType=9,label="统驭科目",maxLength= 1,required=false)  
    private String specialaccount;   
    
	/*
	 * 抬头文本(清帐用途)
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="抬头文本(清帐用途)",maxLength= 100,required=true)  
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
    @ValidateRule(dataType=8,label="记账日期",required=true)  
    private String accountdate;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=8,label="凭证日期",required=true)  
    private String voucherdate;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String deptid;   
    
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
    @ValidateRule(dataType=9,label="创建日期",maxLength= 14,required=false)  
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
    @ValidateRule(dataType=9,label="最后修改日期",maxLength= 14,required=false)  
    private String lastmodifytime;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 50,required=false)  
    private String processstate;   
    
	/*
	 * 业务状态
	 */
    @Column(name="BUSINESSSTATE")
    @ValidateRule(dataType=9,label="业务状态",maxLength= 2,required=false)  
    private String businessstate;   
    
	/*
	 * 会计科目号
	 */
    @Column(name="SUBJECT")
    @ValidateRule(dataType=9,label="会计科目号",maxLength= 10,required=true)  
    private String subject;   
    
	/*
	 * 货币文本
	 */
    @Column(name="CURRENCYTEXT")
    @ValidateRule(dataType=9,label="货币文本",maxLength= 20,required=true)  
    private String currencytext;   
    
	/*
	 * 公司代码
	 */
    @Column(name="COMPANYNO")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 20,required=true)  
    private String companyno;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 4,required=false)  
    private String depid;   
    
	/*
	 * 供应商单清帐单号
	 */
    @Column(name="SUPPLIERCLEARNO")
    @ValidateRule(dataType=9,label="供应商单清帐单号",maxLength= 50,required=false)  
    private String supplierclearno;   
    
	/*
	 * 其他公司未清付款（借方）
	 */
    @OneToMany(mappedBy="supplierSinglOther",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<UnCleaPaymentOther> UnCleaPaymentOther;   
    
	/*
	 * 其他公司未清应付（贷方）
	 */
    @OneToMany(mappedBy="supplierSinglOther",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<UnSupplieBillOther> UnSupplieBillOther;   
    
	/*
	 * 其他公司结算科目
	 */
    @OneToOne(mappedBy="supplierSinglOther",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubjectOther SettleSubjectOther;   
    
	/*
	 * 其他公司纯资金往来
	 */
    @OneToOne(mappedBy="supplierSinglOther",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private FundFlowOther FundFlowOther;   
    

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
     *    获得供应商单清帐ID
     * @return 供应商单清帐ID : String
     */
    public String getSuppliersclearid()
    {
		return this.suppliersclearid;
    }

    /**
     * 功能描述:
     *    设置供应商单清帐ID
     * @param suppliersclearid : String
     */
    public void setSuppliersclearid(String suppliersclearid)
    {
	    this.suppliersclearid = suppliersclearid;
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
     *    获得统驭科目
     * @return 统驭科目 : String
     */
    public String getSpecialaccount()
    {
		return this.specialaccount;
    }

    /**
     * 功能描述:
     *    设置统驭科目
     * @param specialaccount : String
     */
    public void setSpecialaccount(String specialaccount)
    {
	    this.specialaccount = specialaccount;
    }
    

    /**
     * 功能描述:
     *    获得抬头文本(清帐用途)
     * @return 抬头文本(清帐用途) : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置抬头文本(清帐用途)
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
     *    获得部门ID
     * @return 部门ID : String
     */
    public String getDeptid()
    {
		return this.deptid;
    }

    /**
     * 功能描述:
     *    设置部门ID
     * @param deptid : String
     */
    public void setDeptid(String deptid)
    {
	    this.deptid = deptid;
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
		return this.createtime;
    }

    /**
     * 功能描述:
     *    设置创建日期
     * @param createtime : String
     */
    public void setCreatetime(String createtime)
    {
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
     *    获得会计科目号
     * @return 会计科目号 : String
     */
    public String getSubject()
    {
		return this.subject;
    }

    /**
     * 功能描述:
     *    设置会计科目号
     * @param subject : String
     */
    public void setSubject(String subject)
    {
	    this.subject = subject;
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
     * @return 业务范围 : String
     */
    public String getDepid()
    {
		return this.depid;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid : String
     */
    public void setDepid(String depid)
    {
	    this.depid = depid;
    }
    

    /**
     * 功能描述:
     *    获得供应商单清帐单号
     * @return 供应商单清帐单号 : String
     */
    public String getSupplierclearno()
    {
		return this.supplierclearno;
    }

    /**
     * 功能描述:
     *    设置供应商单清帐单号
     * @param supplierclearno : String
     */
    public void setSupplierclearno(String supplierclearno)
    {
	    this.supplierclearno = supplierclearno;
    }
    

    /**
     * 功能描述:
     *    获得其他公司未清付款（借方）
     * @return 其他公司未清付款（借方） : Set<UnCleaPaymentOther>
     */
    public Set<UnCleaPaymentOther> getUnCleaPaymentOther()
    {
		return this.UnCleaPaymentOther;
    }

    /**
     * 功能描述:
     *    设置其他公司未清付款（借方）
     * @param UnCleaPaymentOther : Set<UnCleaPaymentOther>
     */
    public void setUnCleaPaymentOther(Set<UnCleaPaymentOther> UnCleaPaymentOther)
    {
	    this.UnCleaPaymentOther = UnCleaPaymentOther;
    }
    

    /**
     * 功能描述:
     *    获得其他公司未清应付（贷方）
     * @return 其他公司未清应付（贷方） : Set<UnSupplieBillOther>
     */
    public Set<UnSupplieBillOther> getUnSupplieBillOther()
    {
		return this.UnSupplieBillOther;
    }

    /**
     * 功能描述:
     *    设置其他公司未清应付（贷方）
     * @param UnSupplieBillOther : Set<UnSupplieBillOther>
     */
    public void setUnSupplieBillOther(Set<UnSupplieBillOther> UnSupplieBillOther)
    {
	    this.UnSupplieBillOther = UnSupplieBillOther;
    }
    

    /**
     * 功能描述:
     *    获得其他公司结算科目
     * @return 其他公司结算科目 : SettleSubjectOther
     */
    public SettleSubjectOther getSettleSubjectOther()
    {
		return this.SettleSubjectOther;
    }

    /**
     * 功能描述:
     *    设置其他公司结算科目
     * @param SettleSubjectOther : SettleSubjectOther
     */
    public void setSettleSubjectOther(SettleSubjectOther SettleSubjectOther)
    {
	    this.SettleSubjectOther = SettleSubjectOther;
    }
    

    /**
     * 功能描述:
     *    获得其他公司纯资金往来
     * @return 其他公司纯资金往来 : FundFlowOther
     */
    public FundFlowOther getFundFlowOther()
    {
		return this.FundFlowOther;
    }

    /**
     * 功能描述:
     *    设置其他公司纯资金往来
     * @param FundFlowOther : FundFlowOther
     */
    public void setFundFlowOther(FundFlowOther FundFlowOther)
    {
	    this.FundFlowOther = FundFlowOther;
    }
    
    
	/**
	 *  默认构造器
	 */
    public SupplierSinglOther()
    {
    	super();
    }
}
