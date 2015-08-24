/*
 * @(#)PackingLoan.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloan.domain;

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
 * 打包贷款(PackingLoan)实体类
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
@Table(name = "YPACKINGLOAN")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class PackingLoan extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 打包贷款ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PACKINGID")
      
    private String packingid;   
    
	/*
	 * 打包贷款单号
	 */
    @Column(name="PACKING_NO")
    @ValidateRule(dataType=9,label="打包贷款单号",maxLength= 100,required=false)  
    private String packing_no;   
    
	/*
	 * 信用证号
	 */
    @Column(name="CREDITNO")
    @ValidateRule(dataType=9,label="信用证号",maxLength= 50,required=true)  
    private String creditno;   
    
	/*
	 * 到证日期(到证)
	 */
    @Column(name="CREDITRECDATE")
    @ValidateRule(dataType=8,label="到证日期(到证)",required=false)  
    private String creditrecdate;   
    
	/*
	 * 开证行
	 */
    @Column(name="CREATEBANK")
    @ValidateRule(dataType=9,label="开证行",maxLength= 100,required=false)  
    private String createbank;   
    
	/*
	 * 关联合同号
	 */
    @Column(name="CONTRACTNO")
    @ValidateRule(dataType=9,label="关联合同号",maxLength= 36,required=false)  
    private String contractno;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 申请金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请金额",required=false)  
    private BigDecimal applyamount;   
    
	/*
	 * 打包到期日
	 */
    @Column(name="DEALINE")
    @ValidateRule(dataType=8,label="打包到期日",required=false)  
    private String dealine;   
    
	/*
	 * 打包期限
	 */
    @Column(name="MATURE")
    @ValidateRule(dataType=9,label="打包期限",maxLength= 20,required=false)  
    private String mature;   
    
	/*
	 * 应收款日
	 */
    @Column(name="DEALDATE")
    @ValidateRule(dataType=8,label="应收款日",required=false)  
    private String dealdate;   
    
	/*
	 * 打包利率
	 */
    @Column(name="INTERESTRATE")
    @ValidateRule(dataType=9,label="打包利率",maxLength= 20,required=false)  
    private String interestrate;   
    
	/*
	 * 打包汇率
	 */
    @Column(name="PACKINGREATE")
    @ValidateRule(dataType=0,label="打包汇率",required=false)  
    private BigDecimal packingreate;   
    
	/*
	 * 扣含税利润金额（清供应商额度）（本位币）
	 */
    @Column(name="CLEARVENDORVALUE")
    @ValidateRule(dataType=0,label="扣含税利润金额（清供应商额度）（本位币）",required=false)  
    private BigDecimal clearvendorvalue;   
    
	/*
	 * 还打包汇率
	 */
    @Column(name="REPACKINGREATE")
    @ValidateRule(dataType=0,label="还打包汇率",required=false)  
    private BigDecimal repackingreate;   
    
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
	 * 打包贷款文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="打包贷款文本",maxLength= 255,required=false)  
    private String text;   
    
	/*
	 * 还款凭证日期
	 */
    @Column(name="REVOUCHERDATE")
    @ValidateRule(dataType=8,label="还款凭证日期",required=false)  
    private String revoucherdate;   
    
	/*
	 * 还款记帐日期
	 */
    @Column(name="REACCOUNTDATE")
    @ValidateRule(dataType=8,label="还款记帐日期",required=false)  
    private String reaccountdate;   
    
	/*
	 * 还打包贷款文本
	 */
    @Column(name="RETEXT")
    @ValidateRule(dataType=9,label="还打包贷款文本",maxLength= 255,required=false)  
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
    @ValidateRule(dataType=8,label="最后修改日期",required=false)  
    private String lastmodifytime;   
    
	/*
	 * 打包贷款银行
	 */
    @OneToMany(mappedBy="packingLoan",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<PackingBankItem> packingBankItem;   
    
	/*
	 * 还打包贷款银行
	 */
    @OneToMany(mappedBy="packingLoan",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<PackingReBankItem> packingReBankItem;   
    
	/*
	 * 还打包银行2
	 */
    @OneToMany(mappedBy="packingLoan",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<PackingReBankTwo> packingReBankTwo;   
    
	/*
	 * 结算科目
	 */
    @OneToOne(mappedBy="packingLoan",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
    private SettleSubject settleSubject;   
    
	/*
	 * 纯资金往来
	 */
    @OneToOne(mappedBy="packingLoan",cascade=CascadeType.ALL,fetch=FetchType.EAGER,optional=true)
      
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
     *    获得打包贷款ID
     * @return 打包贷款ID : String
     */
    public String getPackingid()
    {
		return this.packingid;
    }

    /**
     * 功能描述:
     *    设置打包贷款ID
     * @param packingid : String
     */
    public void setPackingid(String packingid)
    {
	    this.packingid = packingid;
    }
    

    /**
     * 功能描述:
     *    获得打包贷款单号
     * @return 打包贷款单号 : String
     */
    public String getPacking_no()
    {
		return this.packing_no;
    }

    /**
     * 功能描述:
     *    设置打包贷款单号
     * @param packing_no : String
     */
    public void setPacking_no(String packing_no)
    {
	    this.packing_no = packing_no;
    }
    

    /**
     * 功能描述:
     *    获得信用证号
     * @return 信用证号 : String
     */
    public String getCreditno()
    {
		return this.creditno;
    }

    /**
     * 功能描述:
     *    设置信用证号
     * @param creditno : String
     */
    public void setCreditno(String creditno)
    {
	    this.creditno = creditno;
    }
    

    /**
     * 功能描述:
     *    获得到证日期(到证)
     * @return 到证日期(到证) : String
     */
    public String getCreditrecdate()
    {
    	return DateUtils.toDisplayStr(this.creditrecdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置到证日期(到证)
     * @param creditrecdate : String
     */
    public void setCreditrecdate(String creditrecdate)
    {
    	creditrecdate = DateUtils.toStoreStr(creditrecdate);
	    this.creditrecdate = creditrecdate;
    }
    

    /**
     * 功能描述:
     *    获得开证行
     * @return 开证行 : String
     */
    public String getCreatebank()
    {
		return this.createbank;
    }

    /**
     * 功能描述:
     *    设置开证行
     * @param createbank : String
     */
    public void setCreatebank(String createbank)
    {
	    this.createbank = createbank;
    }
    

    /**
     * 功能描述:
     *    获得关联合同号
     * @return 关联合同号 : String
     */
    public String getContractno()
    {
		return this.contractno;
    }

    /**
     * 功能描述:
     *    设置关联合同号
     * @param contractno : String
     */
    public void setContractno(String contractno)
    {
	    this.contractno = contractno;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得申请金额
     * @return 申请金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
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
     *    获得打包到期日
     * @return 打包到期日 : String
     */
    public String getDealine()
    {
    	return DateUtils.toDisplayStr(this.dealine, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置打包到期日
     * @param dealine : String
     */
    public void setDealine(String dealine)
    {
    	dealine = DateUtils.toStoreStr(dealine);
	    this.dealine = dealine;
    }
    

    /**
     * 功能描述:
     *    获得打包期限
     * @return 打包期限 : String
     */
    public String getMature()
    {
		return this.mature;
    }

    /**
     * 功能描述:
     *    设置打包期限
     * @param mature : String
     */
    public void setMature(String mature)
    {
	    this.mature = mature;
    }
    

    /**
     * 功能描述:
     *    获得应收款日
     * @return 应收款日 : String
     */
    public String getDealdate()
    {
    	return DateUtils.toDisplayStr(this.dealdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置应收款日
     * @param dealdate : String
     */
    public void setDealdate(String dealdate)
    {
    	dealdate = DateUtils.toStoreStr(dealdate);
	    this.dealdate = dealdate;
    }
    

    /**
     * 功能描述:
     *    获得打包利率
     * @return 打包利率 : String
     */
    public String getInterestrate()
    {
		return this.interestrate;
    }

    /**
     * 功能描述:
     *    设置打包利率
     * @param interestrate : String
     */
    public void setInterestrate(String interestrate)
    {
	    this.interestrate = interestrate;
    }
    

    /**
     * 功能描述:
     *    获得打包汇率
     * @return 打包汇率 : BigDecimal
     */
    public BigDecimal getPackingreate()
    {
		return this.packingreate;
    }

    /**
     * 功能描述:
     *    设置打包汇率
     * @param packingreate : BigDecimal
     */
    public void setPackingreate(BigDecimal packingreate)
    {
	    this.packingreate = packingreate;
    }
    

    /**
     * 功能描述:
     *    获得扣含税利润金额（清供应商额度）（本位币）
     * @return 扣含税利润金额（清供应商额度）（本位币） : BigDecimal
     */
    public BigDecimal getClearvendorvalue()
    {
		return this.clearvendorvalue;
    }

    /**
     * 功能描述:
     *    设置扣含税利润金额（清供应商额度）（本位币）
     * @param clearvendorvalue : BigDecimal
     */
    public void setClearvendorvalue(BigDecimal clearvendorvalue)
    {
	    this.clearvendorvalue = clearvendorvalue;
    }
    

    /**
     * 功能描述:
     *    获得还打包汇率
     * @return 还打包汇率 : BigDecimal
     */
    public BigDecimal getRepackingreate()
    {
		return this.repackingreate;
    }

    /**
     * 功能描述:
     *    设置还打包汇率
     * @param repackingreate : BigDecimal
     */
    public void setRepackingreate(BigDecimal repackingreate)
    {
	    this.repackingreate = repackingreate;
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
     *    获得打包贷款文本
     * @return 打包贷款文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置打包贷款文本
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
    }
    

    /**
     * 功能描述:
     *    获得还款凭证日期
     * @return 还款凭证日期 : String
     */
    public String getRevoucherdate()
    {
    	return DateUtils.toDisplayStr(this.revoucherdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置还款凭证日期
     * @param revoucherdate : String
     */
    public void setRevoucherdate(String revoucherdate)
    {
    	revoucherdate = DateUtils.toStoreStr(revoucherdate);
	    this.revoucherdate = revoucherdate;
    }
    

    /**
     * 功能描述:
     *    获得还款记帐日期
     * @return 还款记帐日期 : String
     */
    public String getReaccountdate()
    {
    	return DateUtils.toDisplayStr(this.reaccountdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置还款记帐日期
     * @param reaccountdate : String
     */
    public void setReaccountdate(String reaccountdate)
    {
    	reaccountdate = DateUtils.toStoreStr(reaccountdate);
	    this.reaccountdate = reaccountdate;
    }
    

    /**
     * 功能描述:
     *    获得还打包贷款文本
     * @return 还打包贷款文本 : String
     */
    public String getRetext()
    {
		return this.retext;
    }

    /**
     * 功能描述:
     *    设置还打包贷款文本
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
     *    获得打包贷款银行
     * @return 打包贷款银行 : Set<PackingBankItem>
     */
    public Set<PackingBankItem> getPackingBankItem()
    {
		return this.packingBankItem;
    }

    /**
     * 功能描述:
     *    设置打包贷款银行
     * @param packingBankItem : Set<PackingBankItem>
     */
    public void setPackingBankItem(Set<PackingBankItem> packingBankItem)
    {
	    this.packingBankItem = packingBankItem;
    }
    

    /**
     * 功能描述:
     *    获得还打包贷款银行
     * @return 还打包贷款银行 : Set<PackingReBankItem>
     */
    public Set<PackingReBankItem> getPackingReBankItem()
    {
		return this.packingReBankItem;
    }

    /**
     * 功能描述:
     *    设置还打包贷款银行
     * @param packingReBankItem : Set<PackingReBankItem>
     */
    public void setPackingReBankItem(Set<PackingReBankItem> packingReBankItem)
    {
	    this.packingReBankItem = packingReBankItem;
    }
    

    /**
     * 功能描述:
     *    获得还打包银行2
     * @return 还打包银行2 : Set<PackingReBankTwo>
     */
    public Set<PackingReBankTwo> getPackingReBankTwo()
    {
		return this.packingReBankTwo;
    }

    /**
     * 功能描述:
     *    设置还打包银行2
     * @param packingReBankTwo : Set<PackingReBankTwo>
     */
    public void setPackingReBankTwo(Set<PackingReBankTwo> packingReBankTwo)
    {
	    this.packingReBankTwo = packingReBankTwo;
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
	 *  默认构造器
	 */
    public PackingLoan()
    {
    	super();
    }
}
