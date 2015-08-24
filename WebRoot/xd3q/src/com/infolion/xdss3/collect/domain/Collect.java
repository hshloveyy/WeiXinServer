/*
 * @(#)Collect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月20日 01点22分46秒
 *  描　述：创建
 */
package com.infolion.xdss3.collect.domain;

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
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;

/**
 * <pre>
 * 收款(Collect)实体类
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
@Table(name = "YCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Collect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 收款ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="COLLECTID")
      
    private String collectid;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMER")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=true)  
    private String customer;   
    
	/*
	 * 贸易方式
	 */
    @Column(name="TRADE_TYPE")
    @ValidateRule(dataType=9,label="贸易方式",maxLength= 2,required=false)  
    private String trade_type;   
    
	/*
	 * 是否包含保证金
	 */
    @Column(name="INCSURETYBOND")
    @ValidateRule(dataType=9,label="是否包含保证金",maxLength= 1,required=false)  
    private String incsuretybond;   
    
	/*
	 * 收款方式
	 */
    @Column(name="COLLECTTYPE")
    @ValidateRule(dataType=9,label="收款方式",maxLength= 2,required=true)  
    private String collecttype;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=true)  
    private String currency;   
    
	/*
	 * 申请收款金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请收款金额",required=true)  
    private BigDecimal applyamount;   
    
	/*
	 * 开票币别
	 */
    @Column(name="BILLCURRENCY")
    @ValidateRule(dataType=9,label="开票币别",maxLength= 10,required=true)  
    private String billcurrency;   
    
	/*
	 * 折算金额/保证金转货款金额
	 */
    @Column(name="CONVERTAMOUNT")
    @ValidateRule(dataType=0,label="折算金额/保证金转货款金额",required=true)  
    private BigDecimal convertamount;   
    
	/*
	 * 结算汇率
	 */
    @Column(name="SETTLERATE")
    @ValidateRule(dataType=0,label="结算汇率",required=true)  
    private BigDecimal settlerate;   
    
	/*
	 * 单据号码
	 */
    @Column(name="DRAFT")
    @ValidateRule(dataType=9,label="单据号码",maxLength= 30,required=false)  
    private String draft;   
    
	/*
	 * 到期日
	 */
    @Column(name="EXPIREDATE")
    @ValidateRule(dataType=8,label="到期日",required=false)  
    private String expiredate;   
    
	/*
	 * 票据业务范围
	 */
    @Column(name="BILLBC")
    @ValidateRule(dataType=9,label="票据业务范围",maxLength= 36,required=false)  
    private String billbc;   
    
	/*
	 * 银行承兑汇票/国内信用证到期日
	 */
    @Column(name="DRAFTDATE")
    @ValidateRule(dataType=8,label="银行承兑汇票/国内信用证到期日",required=false)  
    private String draftdate;   
    
	/*
	 * 出单发票号
	 */
    @Column(name="EXPORT_APPLY_NO")
    @ValidateRule(dataType=9,label="出单发票号",maxLength= 50,required=false)  
    private String export_apply_no;   
    
	/*
	 * 预计发货日
	 */
    @Column(name="SENDGOODSDATE")
    @ValidateRule(dataType=8,label="预计发货日",required=true)  
    private String sendgoodsdate;   
    
	/*
	 * 应开票日
	 */
    @Column(name="BILLCHECKDATE")
    @ValidateRule(dataType=8,label="应开票日",required=true)  
    private String billcheckdate;   
    
	/*
	 * 清放货额度金额（本位币）
	 */
    @Column(name="GOODSAMOUNT")
    @ValidateRule(dataType=0,label="清放货额度金额（本位币）",required=false)  
    private BigDecimal goodsamount;   
    
	/*
	 * 清代垫额度（本位币）
	 */
    @Column(name="REPLACEAMOUNT")
    @ValidateRule(dataType=0,label="清代垫额度（本位币）",required=false)  
    private BigDecimal replaceamount;   
    
	/*
	 * 清供应商额度（本位币）
	 */
    @Column(name="SUPPLIERAMOUNT")
    @ValidateRule(dataType=0,label="清供应商额度（本位币）",required=false)  
    private BigDecimal supplieramount;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPT_ID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=true)  
    private String dept_id;
    
    /*
     * 本次贴现/议付金额
     */
    @Column(name="CURDISNEGAMOUNT")
    @ValidateRule(dataType=0,label="本次贴现/议付金额",required=false)  
    private BigDecimal curdisnegamount;
    
    /*
     * 累计贴现/议付金额
     */
    @Column(name="TOLDISNEGAMOUNT")
    @ValidateRule(dataType=0,label="累计贴现/议付金额",required=false)  
    private BigDecimal toldisnegamount;
    
	/*
	 * 消息文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="消息文本",maxLength= 150,required=true)  
    private String text;   
    
	/*
	 * 备注
	 */
    @Column(name="REMARK")
    @ValidateRule(dataType=9,label="备注",maxLength= 2000,required=false)  
    private String remark;   
    
	/*
	 * 未名户收款单号
	 */
    @Column(name="UNNAMERCOLLECTID")
    @ValidateRule(dataType=9,label="未名户收款单号",maxLength= 36,required=false)  
    private String unnamercollectid;   
    
	/*
	 * 原收款单ID
	 */
    @Column(name="OLDCOLLECTID")
    @ValidateRule(dataType=9,label="原收款单ID",maxLength= 36,required=false)  
    private String oldcollectid;   
    
	/*
	 * 原收款单号
	 */
    @Column(name="OLDCOLLECTNO")
    @ValidateRule(dataType=9,label="原收款单号",maxLength= 30,required=false)  
    private String oldcollectno;   
    
	/*
	 * 原收款单行项目
	 */
    @Column(name="OLDCOLLECTITEMID")
    @ValidateRule(dataType=9,label="原收款单行项目",maxLength= 36,required=false)  
    private String oldcollectitemid;   
    
	/*
	 * 原合同号
	 */
    @Column(name="OLDCONTRACT_NO")
    @ValidateRule(dataType=9,label="原合同号",maxLength= 36,required=false)  
    private String oldcontract_no;   
    
	/*
	 * 原项目编号
	 */
    @Column(name="OLDPROJECT_NO")
    @ValidateRule(dataType=9,label="原项目编号",maxLength= 100,required=false)  
    private String oldproject_no;   
    
	/*
	 * 剩余保证金
	 */
    @Column(name="REMAINSURETYBOND")
    @ValidateRule(dataType=9,label="剩余保证金",required=false)  
    private BigDecimal remainsuretybond;   
    
	/*
	 * 收款汇率
	 */
    @Column(name="COLLECTRATE")
    @ValidateRule(dataType=0,label="收款汇率",required=false)  
    private BigDecimal collectrate;   
    
	/*
	 * 票据银行
	 */
    @Column(name="TICKETBANKID")
    @ValidateRule(dataType=9,label="票据银行",maxLength= 36,required=false)  
    private String ticketbankid;   
    
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
	 * 实际收款金额
	 */
    @Column(name="ACTAMOUNT")
    @ValidateRule(dataType=0,label="实际收款金额",required=false)  
    private BigDecimal actamount;   
    
	/*
	 * 实际收款币别
	 */
    @Column(name="ACTCURRENCY")
    @ValidateRule(dataType=9,label="实际收款币别",maxLength= 10,required=false)  
    private String actcurrency;   
    
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
    @ValidateRule(dataType=9,label="最后修改日期",required=false)  
    private String lastmodifytime;   
    
	/*
	 * 收款金额分配
	 */
    @OneToMany(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<CollectItem> collectitem;   
    
	/*
	 * 收款清票
	 */
    @OneToMany(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<CollectCbill> collectcbill;   
    
	/*
	 * 收款关联单据
	 */
    @OneToMany(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<CollectRelated> collectrelated;   
    
	/*
	 * 结算科目
	 */
    @OneToOne(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubject settleSubject;   
    
	/*
	 * 纯资金往来
	 */
    @OneToOne(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private FundFlow fundFlow;   
    
	/*
	 * 收款银行行项
	 */
    @OneToMany(mappedBy="collect",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<CollectBankItem> collectbankitem;   
    

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
     *    获得收款ID
     * @return 收款ID : String
     */
    public String getCollectid()
    {
		return this.collectid;
    }

    /**
     * 功能描述:
     *    设置收款ID
     * @param collectid : String
     */
    public void setCollectid(String collectid)
    {
	    this.collectid = collectid;
    }
    

    /**
     * 功能描述:
     *    获得收款单号
     * @return 收款单号 : String
     */
    public String getCollectno()
    {
		return this.collectno;
    }

    /**
     * 功能描述:
     *    设置收款单号
     * @param collectno : String
     */
    public void setCollectno(String collectno)
    {
	    this.collectno = collectno;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomer()
    {
		return this.customer;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customer : String
     */
    public void setCustomer(String customer)
    {
	    this.customer = customer;
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
     *    获得是否包含保证金
     * @return 是否包含保证金 : String
     */
    public String getIncsuretybond()
    {
		return this.incsuretybond;
    }

    /**
     * 功能描述:
     *    设置是否包含保证金
     * @param incsuretybond : String
     */
    public void setIncsuretybond(String incsuretybond)
    {
	    this.incsuretybond = incsuretybond;
    }
    

    /**
     * 功能描述:
     *    获得收款方式
     * @return 收款方式 : String
     */
    public String getCollecttype()
    {
		return this.collecttype;
    }

    /**
     * 功能描述:
     *    设置收款方式
     * @param collecttype : String
     */
    public void setCollecttype(String collecttype)
    {
	    this.collecttype = collecttype;
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
     *    获得申请收款金额
     * @return 申请收款金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
		return this.applyamount;
    }

    /**
     * 功能描述:
     *    设置申请收款金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
    }
    

    /**
     * 功能描述:
     *    获得开票币别
     * @return 开票币别 : String
     */
    public String getBillcurrency()
    {
		return this.billcurrency;
    }

    /**
     * 功能描述:
     *    设置开票币别
     * @param billcurrency : String
     */
    public void setBillcurrency(String billcurrency)
    {
	    this.billcurrency = billcurrency;
    }
    

    /**
     * 功能描述:
     *    获得折算金额/保证金转货款金额
     * @return 折算金额/保证金转货款金额 : BigDecimal
     */
    public BigDecimal getConvertamount()
    {
		return this.convertamount;
    }

    /**
     * 功能描述:
     *    设置折算金额/保证金转货款金额
     * @param convertamount : BigDecimal
     */
    public void setConvertamount(BigDecimal convertamount)
    {
	    this.convertamount = convertamount;
    }
    

    /**
     * 功能描述:
     *    获得结算汇率
     * @return 结算汇率 : BigDecimal
     */
    public BigDecimal getSettlerate()
    {
		return this.settlerate;
    }

    /**
     * 功能描述:
     *    设置结算汇率
     * @param settlerate : BigDecimal
     */
    public void setSettlerate(BigDecimal settlerate)
    {
	    this.settlerate = settlerate;
    }
    

    /**
     * 功能描述:
     *    获得银行承兑汇票号
     * @return 银行承兑汇票号 : String
     */
    public String getDraft()
    {
		return this.draft;
    }

    /**
     * 功能描述:
     *    设置银行承兑汇票号
     * @param draft : String
     */
    public void setDraft(String draft)
    {
	    this.draft = draft;
    }
    

    /**
     * 功能描述:
     *    获得到期日
     * @return 到期日 : String
     */
    public String getExpiredate()
    {
    	return DateUtils.toDisplayStr(this.expiredate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置到期日
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
     *    获得出单发票号
     * @return 出单发票号 : String
     */
    public String getExport_apply_no()
    {
		return this.export_apply_no;
    }

    /**
     * 功能描述:
     *    设置出单发票号
     * @param export_apply_no : String
     */
    public void setExport_apply_no(String export_apply_no)
    {
	    this.export_apply_no = export_apply_no;
    }
    

    /**
     * 功能描述:
     *    获得预计发货日
     * @return 预计发货日 : String
     */
    public String getSendgoodsdate()
    {
    	return DateUtils.toDisplayStr(this.sendgoodsdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置预计发货日
     * @param sendgoodsdate : String
     */
    public void setSendgoodsdate(String sendgoodsdate)
    {
    	sendgoodsdate = DateUtils.toStoreStr(sendgoodsdate);
	    this.sendgoodsdate = sendgoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得应开票日
     * @return 应开票日 : String
     */
    public String getBillcheckdate()
    {
    	return DateUtils.toDisplayStr(this.billcheckdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置应开票日
     * @param billcheckdate : String
     */
    public void setBillcheckdate(String billcheckdate)
    {
    	billcheckdate = DateUtils.toStoreStr(billcheckdate);
	    this.billcheckdate = billcheckdate;
    }
    

    /**
     * 功能描述:
     *    获得清放货额度金额（本位币）
     * @return 清放货额度金额（本位币） : BigDecimal
     */
    public BigDecimal getGoodsamount()
    {
		return this.goodsamount;
    }

    /**
     * 功能描述:
     *    设置清放货额度金额（本位币）
     * @param goodsamount : BigDecimal
     */
    public void setGoodsamount(BigDecimal goodsamount)
    {
	    this.goodsamount = goodsamount;
    }
    

    /**
     * 功能描述:
     *    获得清代垫额度（本位币）
     * @return 清代垫额度（本位币） : BigDecimal
     */
    public BigDecimal getReplaceamount()
    {
		return this.replaceamount;
    }

    /**
     * 功能描述:
     *    设置清代垫额度（本位币）
     * @param replaceamount : BigDecimal
     */
    public void setReplaceamount(BigDecimal replaceamount)
    {
	    this.replaceamount = replaceamount;
    }
    

    /**
     * 功能描述:
     *    获得清供应商额度（本位币）
     * @return 清供应商额度（本位币） : BigDecimal
     */
    public BigDecimal getSupplieramount()
    {
		return this.supplieramount;
    }

    /**
     * 功能描述:
     *    设置清供应商额度（本位币）
     * @param supplieramount : BigDecimal
     */
    public void setSupplieramount(BigDecimal supplieramount)
    {
	    this.supplieramount = supplieramount;
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

    public BigDecimal getCurdisnegamount(){
        return curdisnegamount;
    }

    public void setCurdisnegamount(BigDecimal curdisnegamount){
        this.curdisnegamount = curdisnegamount;
    }

    public BigDecimal getToldisnegamount(){
        return toldisnegamount;
    }

    public void setToldisnegamount(BigDecimal toldisnegamount){
        this.toldisnegamount = toldisnegamount;
    }

    /**
     * 功能描述:
     *    获得消息文本
     * @return 消息文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置消息文本
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
     *    获得未名户收款单号
     * @return 未名户收款单号 : String
     */
    public String getUnnamercollectid()
    {
		return this.unnamercollectid;
    }

    /**
     * 功能描述:
     *    设置未名户收款单号
     * @param unnamercollectid : String
     */
    public void setUnnamercollectid(String unnamercollectid)
    {
	    this.unnamercollectid = unnamercollectid;
    }
    

    /**
     * 功能描述:
     *    获得原收款单ID
     * @return 原收款单ID : String
     */
    public String getOldcollectid()
    {
		return this.oldcollectid;
    }

    /**
     * 功能描述:
     *    设置原收款单ID
     * @param oldcollectid : String
     */
    public void setOldcollectid(String oldcollectid)
    {
	    this.oldcollectid = oldcollectid;
    }
    

    /**
     * 功能描述:
     *    获得原收款单号
     * @return 原收款单号 : String
     */
    public String getOldcollectno()
    {
		return this.oldcollectno;
    }

    /**
     * 功能描述:
     *    设置原收款单号
     * @param oldcollectno : String
     */
    public void setOldcollectno(String oldcollectno)
    {
	    this.oldcollectno = oldcollectno;
    }
    

    /**
     * 功能描述:
     *    获得原收款单行项目
     * @return 原收款单行项目 : String
     */
    public String getOldcollectitemid()
    {
		return this.oldcollectitemid;
    }

    /**
     * 功能描述:
     *    设置原收款单行项目
     * @param oldcollectitemid : String
     */
    public void setOldcollectitemid(String oldcollectitemid)
    {
	    this.oldcollectitemid = oldcollectitemid;
    }
    

    /**
     * 功能描述:
     *    获得原合同号
     * @return 原合同号 : String
     */
    public String getOldcontract_no()
    {
		return this.oldcontract_no;
    }

    /**
     * 功能描述:
     *    设置原合同号
     * @param oldcontract_no : String
     */
    public void setOldcontract_no(String oldcontract_no)
    {
	    this.oldcontract_no = oldcontract_no;
    }
    

    /**
     * 功能描述:
     *    获得原项目编号
     * @return 原项目编号 : String
     */
    public String getOldproject_no()
    {
		return this.oldproject_no;
    }

    /**
     * 功能描述:
     *    设置原项目编号
     * @param oldproject_no : String
     */
    public void setOldproject_no(String oldproject_no)
    {
	    this.oldproject_no = oldproject_no;
    }
    

    /**
     * 功能描述:
     *    获得剩余保证金
     * @return 剩余保证金 : String
     */
    public BigDecimal getRemainsuretybond()
    {
		return this.remainsuretybond;
    }

    /**
     * 功能描述:
     *    设置剩余保证金
     * @param remainsuretybond : String
     */
    public void setRemainsuretybond(BigDecimal remainsuretybond)
    {
	    this.remainsuretybond = remainsuretybond;
    }
    

    /**
     * 功能描述:
     *    获得收款汇率
     * @return 收款汇率 : BigDecimal
     */
    public BigDecimal getCollectrate()
    {
		return this.collectrate;
    }

    /**
     * 功能描述:
     *    设置收款汇率
     * @param collectrate : BigDecimal
     */
    public void setCollectrate(BigDecimal collectrate)
    {
	    this.collectrate = collectrate;
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
     *    获得实际收款金额
     * @return 实际收款金额 : BigDecimal
     */
    public BigDecimal getActamount()
    {
		return this.actamount;
    }

    /**
     * 功能描述:
     *    设置实际收款金额
     * @param actamount : BigDecimal
     */
    public void setActamount(BigDecimal actamount)
    {
	    this.actamount = actamount;
    }
    

    /**
     * 功能描述:
     *    获得实际收款币别
     * @return 实际收款币别 : String
     */
    public String getActcurrency()
    {
		return this.actcurrency;
    }

    /**
     * 功能描述:
     *    设置实际收款币别
     * @param actcurrency : String
     */
    public void setActcurrency(String actcurrency)
    {
	    this.actcurrency = actcurrency;
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
     *    获得收款金额分配
     * @return 收款金额分配 : Set<CollectItem>
     */
    public Set<CollectItem> getCollectitem()
    {
		return this.collectitem;
    }

    /**
     * 功能描述:
     *    设置收款金额分配
     * @param collectitem : Set<CollectItem>
     */
    public void setCollectitem(Set<CollectItem> collectitem)
    {
	    this.collectitem = collectitem;
    }
    

    /**
     * 功能描述:
     *    获得收款清票
     * @return 收款清票 : Set<CollectCbill>
     */
    public Set<CollectCbill> getCollectcbill()
    {
		return this.collectcbill;
    }

    /**
     * 功能描述:
     *    设置收款清票
     * @param collectcbill : Set<CollectCbill>
     */
    public void setCollectcbill(Set<CollectCbill> collectcbill)
    {
	    this.collectcbill = collectcbill;
    }
    

    /**
     * 功能描述:
     *    获得收款关联单据
     * @return 收款关联单据 : Set<CollectRelated>
     */
    public Set<CollectRelated> getCollectrelated()
    {
		return this.collectrelated;
    }

    /**
     * 功能描述:
     *    设置收款关联单据
     * @param collectrelated : Set<CollectRelated>
     */
    public void setCollectrelated(Set<CollectRelated> collectrelated)
    {
	    this.collectrelated = collectrelated;
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
     *    获得收款银行行项
     * @return 收款银行行项 : Set<CollectBankItem>
     */
    public Set<CollectBankItem> getCollectbankitem()
    {
		return this.collectbankitem;
    }

    /**
     * 功能描述:
     *    设置收款银行行项
     * @param collectbankitem : Set<CollectBankItem>
     */
    public void setCollectbankitem(Set<CollectBankItem> collectbankitem)
    {
	    this.collectbankitem = collectbankitem;
    }
    
    
	/**
	 *  默认构造器
	 */
    public Collect()
    {
    	super();
    }

	public String getTicketbankid() {
		return ticketbankid;
	}

	public void setTicketbankid(String ticketbankid) {
		this.ticketbankid = ticketbankid;
	}
}
