/*
 * @(#)BillClearCollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分02秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearcollect.domain;

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
import com.infolion.xdss3.settlesubjectbcc.domain.SettleSubjectBcc;
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.fundflowBcc.domain.FundFlowBcc;
import com.infolion.xdss3.billincollect.domain.BillInCollect;

/**
 * <pre>
 * 票清收款(BillClearCollect)实体类
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
@Table(name = "YBILLCLEAR")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillClearCollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 票清款ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BILLCLEARID")
      
    private String billclearid;   
    
	/*
	 * 票清款单号
	 */
    @Column(name="BILLCLEARNO")
    @ValidateRule(dataType=9,label="票清款单号",maxLength= 50,required=false)  
    private String billclearno;   
    
	/*
	 * 供应商
	 */
    @Column(name="SUPPLIER")
    @ValidateRule(dataType=9,label="供应商",maxLength= 50,required=false)  
    private String supplier;   
    
	/*
	 * 客户(付款单位)
	 */
    @Column(name="CUSTOMER")
    @ValidateRule(dataType=9,label="客户(付款单位)",maxLength= 255,required=false)  
    private String customer;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String deptid;   
    
	/*
	 * 抬头文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="抬头文本",maxLength= 150,required=true)  
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
    @ValidateRule(dataType=8,label="记账日期",required=false)  
    private String accountdate;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=8,label="凭证日期",required=false)  
    private String voucherdate;   
    
	/*
	 * 票清款ID
	 */
    @Column(name="OLDBILLCLEARID")
    @ValidateRule(dataType=9,label="票清款ID",maxLength= 36,required=false)  
    private String oldbillclearid;   
    
	/*
	 * 票清款单号
	 */
    @Column(name="OLDBILLCLEARNO")
    @ValidateRule(dataType=9,label="票清款单号",maxLength= 50,required=false)  
    private String oldbillclearno;   
    
	/*
	 * 清帐类型
	 */
    @Column(name="CLEARTYPE")
    @ValidateRule(dataType=9,label="清帐类型",maxLength= 2,required=false)  
    private String cleartype;   
    
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
	 * 最后修改者
	 */
    @Column(name="LASTMODIFYER")
    @ValidateRule(dataType=9,label="最后修改者",maxLength= 36,required=false)  
    private String lastmodifyer;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="LASTMODIFYTIME")
    @ValidateRule(dataType=8,label="最后修改日期",required=false)  
    private String lastmodifytime;   
    
	/*
	 * 结算科目
	 */
    @OneToOne(mappedBy="billClearCollect",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubjectBcc settleSubjectBcc;   
    
	/*
	 * 发票清帐
	 */
    @OneToMany(mappedBy="billClearCollect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BillClearItem> billclearitem;   
    
	/*
	 * 纯资金往来
	 */
    @OneToOne(mappedBy="billClearCollect",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private FundFlowBcc fundFlowBcc;   
    
	/*
	 * 未清收款
	 */
    @OneToMany(mappedBy="billClearCollect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BillInCollect> billincollect;   
    

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
     *    获得票清款ID
     * @return 票清款ID : String
     */
    public String getBillclearid()
    {
		return this.billclearid;
    }

    /**
     * 功能描述:
     *    设置票清款ID
     * @param billclearid : String
     */
    public void setBillclearid(String billclearid)
    {
	    this.billclearid = billclearid;
    }
    

    /**
     * 功能描述:
     *    获得票清款单号
     * @return 票清款单号 : String
     */
    public String getBillclearno()
    {
		return this.billclearno;
    }

    /**
     * 功能描述:
     *    设置票清款单号
     * @param billclearno : String
     */
    public void setBillclearno(String billclearno)
    {
	    this.billclearno = billclearno;
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
     *    获得客户(付款单位)
     * @return 客户(付款单位) : String
     */
    public String getCustomer()
    {
		return this.customer;
    }

    /**
     * 功能描述:
     *    设置客户(付款单位)
     * @param customer : String
     */
    public void setCustomer(String customer)
    {
	    this.customer = customer;
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
     *    获得票清款ID
     * @return 票清款ID : String
     */
    public String getOldbillclearid()
    {
		return this.oldbillclearid;
    }

    /**
     * 功能描述:
     *    设置票清款ID
     * @param oldbillclearid : String
     */
    public void setOldbillclearid(String oldbillclearid)
    {
	    this.oldbillclearid = oldbillclearid;
    }
    

    /**
     * 功能描述:
     *    获得票清款单号
     * @return 票清款单号 : String
     */
    public String getOldbillclearno()
    {
		return this.oldbillclearno;
    }

    /**
     * 功能描述:
     *    设置票清款单号
     * @param oldbillclearno : String
     */
    public void setOldbillclearno(String oldbillclearno)
    {
	    this.oldbillclearno = oldbillclearno;
    }
    

    /**
     * 功能描述:
     *    获得清帐类型
     * @return 清帐类型 : String
     */
    public String getCleartype()
    {
		return this.cleartype;
    }

    /**
     * 功能描述:
     *    设置清帐类型
     * @param cleartype : String
     */
    public void setCleartype(String cleartype)
    {
	    this.cleartype = cleartype;
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
     *    获得结算科目
     * @return 结算科目 : SettleSubjectBcc
     */
    public SettleSubjectBcc getSettleSubjectBcc()
    {
		return this.settleSubjectBcc;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settleSubjectBcc : SettleSubjectBcc
     */
    public void setSettleSubjectBcc(SettleSubjectBcc settleSubjectBcc)
    {
	    this.settleSubjectBcc = settleSubjectBcc;
    }
    

    /**
     * 功能描述:
     *    获得发票清帐
     * @return 发票清帐 : Set<BillClearItem>
     */
    public Set<BillClearItem> getBillclearitem()
    {
		return this.billclearitem;
    }

    /**
     * 功能描述:
     *    设置发票清帐
     * @param billclearitem : Set<BillClearItem>
     */
    public void setBillclearitem(Set<BillClearItem> billclearitem)
    {
	    this.billclearitem = billclearitem;
    }
    

    /**
     * 功能描述:
     *    获得纯资金往来
     * @return 纯资金往来 : FundFlowBcc
     */
    public FundFlowBcc getFundFlowBcc()
    {
		return this.fundFlowBcc;
    }

    /**
     * 功能描述:
     *    设置纯资金往来
     * @param fundFlowBcc : FundFlowBcc
     */
    public void setFundFlowBcc(FundFlowBcc fundFlowBcc)
    {
	    this.fundFlowBcc = fundFlowBcc;
    }
    

    /**
     * 功能描述:
     *    获得未清收款
     * @return 未清收款 : Set<BillInCollect>
     */
    public Set<BillInCollect> getBillincollect()
    {
		return this.billincollect;
    }

    /**
     * 功能描述:
     *    设置未清收款
     * @param billincollect : Set<BillInCollect>
     */
    public void setBillincollect(Set<BillInCollect> billincollect)
    {
	    this.billincollect = billincollect;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BillClearCollect()
    {
    	super();
    }
}
