/*
 * @(#)UnSupplieBillOther.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点58分52秒
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
import java.math.BigDecimal;

/**
 * <pre>
 * 其他公司未清应付（贷方）(UnSupplieBillOther)实体类
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
@Table(name = "YUNSUPPBILLOTHER")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class UnSupplieBillOther extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 未清应付（贷方）ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="UNCLEARSBILLID")
      
    private String unclearsbillid;   
    
//	/*
//	 * 供应商单清帐ID
//	 */
//    @Column(name="SUPPLIERSCLEARID")
//    @ValidateRule(dataType=9,label="供应商单清帐ID",maxLength= 36,required=false)  
//    private String suppliersclearid;   
    
	/*
	 * 合同编号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同编号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 36,required=false)  
    private String project_no;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 过账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=9,label="过账日期",maxLength= 20,required=false)  
    private String accountdate;   
    
	/*
	 * SAP开票号
	 */
    @Column(name="INVOICE")
    @ValidateRule(dataType=9,label="SAP开票号",maxLength= 10,required=false)  
    private String invoice;   
    
	/*
	 * 文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="文本",maxLength= 100,required=false)  
    private String text;   
    
	/*
	 * 外部纸质合同号
	 */
    @Column(name="OLD_CONTRACT_NO")
    @ValidateRule(dataType=9,label="外部纸质合同号",maxLength= 200,required=false)  
    private String old_contract_no;   
    
	/*
	 * SAP订单号
	 */
    @Column(name="SAP_ORDER_NO")
    @ValidateRule(dataType=9,label="SAP订单号",maxLength= 255,required=false)  
    private String sap_order_no;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 应付款
	 */
    @Column(name="RECEIVABLEAMOUNT")
    @ValidateRule(dataType=0,label="应付款",required=false)  
    private BigDecimal receivableamount;   
    
	/*
	 * 应付款日
	 */
    @Column(name="RECEIVABLEDATE")
    @ValidateRule(dataType=9,label="应付款日",maxLength= 20,required=false)  
    private String receivabledate;   
    
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
    @Column(name="CLEARAMOUNT")
    @ValidateRule(dataType=0,label="清帐金额",required=false)  
    private BigDecimal clearamount;   
    
	/*
	 * 调整差额
	 */
    @Column(name="ADJUSTAMOUNT")
    @ValidateRule(dataType=0,label="调整差额",required=false)  
    private BigDecimal adjustamount;   
    
	/*
	 * 供应商未清数据抬头ID
	 */
    @Column(name="VENDORTITLEID")
    @ValidateRule(dataType=9,label="供应商未清数据抬头ID",maxLength= 36,required=false)  
    private String vendortitleid;   
    
	/*
	 * 本位币金额(判断汇兑损益用)
	 */
    @Column(name="BWBJE")
    @ValidateRule(dataType=0,label="本位币金额(判断汇兑损益用)",required=false)  
    private BigDecimal bwbje;   
    
	/*
	 * 未清本位币金额
	 */
    @Column(name="UNBWBJE")
    @ValidateRule(dataType=0,label="未清本位币金额",required=false)  
    private BigDecimal unbwbje;   
    
	/*
	 * 汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="汇率",required=false)  
    private BigDecimal exchangerate;   
    
    /*
	 * 供应商单清帐
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "SUPPLIERSCLEARID")
	@NotFound(action = NotFoundAction.IGNORE)
    private SupplierSinglOther supplierSinglOther;

	
	
    /**
	 * @return the supplierSinglOther
	 */
	public SupplierSinglOther getSupplierSinglOther() {
		return supplierSinglOther;
	}

	/**
	 * @param supplierSinglOther the supplierSinglOther to set
	 */
	public void setSupplierSinglOther(SupplierSinglOther supplierSinglOther) {
		this.supplierSinglOther = supplierSinglOther;
	}

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
     *    获得未清应付（贷方）ID
     * @return 未清应付（贷方）ID : String
     */
    public String getUnclearsbillid()
    {
		return this.unclearsbillid;
    }

    /**
     * 功能描述:
     *    设置未清应付（贷方）ID
     * @param unclearsbillid : String
     */
    public void setUnclearsbillid(String unclearsbillid)
    {
	    this.unclearsbillid = unclearsbillid;
    }
    

//    /**
//     * 功能描述:
//     *    获得供应商单清帐ID
//     * @return 供应商单清帐ID : String
//     */
//    public String getSuppliersclearid()
//    {
//		return this.suppliersclearid;
//    }
//
//    /**
//     * 功能描述:
//     *    设置供应商单清帐ID
//     * @param suppliersclearid : String
//     */
//    public void setSuppliersclearid(String suppliersclearid)
//    {
//	    this.suppliersclearid = suppliersclearid;
//    }
    

    /**
     * 功能描述:
     *    获得合同编号
     * @return 合同编号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置合同编号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
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
     *    获得过账日期
     * @return 过账日期 : String
     */
    public String getAccountdate()
    {
		return this.accountdate;
    }

    /**
     * 功能描述:
     *    设置过账日期
     * @param accountdate : String
     */
    public void setAccountdate(String accountdate)
    {
	    this.accountdate = accountdate;
    }
    

    /**
     * 功能描述:
     *    获得SAP开票号
     * @return SAP开票号 : String
     */
    public String getInvoice()
    {
		return this.invoice;
    }

    /**
     * 功能描述:
     *    设置SAP开票号
     * @param invoice : String
     */
    public void setInvoice(String invoice)
    {
	    this.invoice = invoice;
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
     *    获得应付款
     * @return 应付款 : BigDecimal
     */
    public BigDecimal getReceivableamount()
    {
		return this.receivableamount;
    }

    /**
     * 功能描述:
     *    设置应付款
     * @param receivableamount : BigDecimal
     */
    public void setReceivableamount(BigDecimal receivableamount)
    {
	    this.receivableamount = receivableamount;
    }
    

    /**
     * 功能描述:
     *    获得应付款日
     * @return 应付款日 : String
     */
    public String getReceivabledate()
    {
		return this.receivabledate;
    }

    /**
     * 功能描述:
     *    设置应付款日
     * @param receivabledate : String
     */
    public void setReceivabledate(String receivabledate)
    {
	    this.receivabledate = receivabledate;
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
    public BigDecimal getClearamount()
    {
		return this.clearamount;
    }

    /**
     * 功能描述:
     *    设置清帐金额
     * @param clearamount : BigDecimal
     */
    public void setClearamount(BigDecimal clearamount)
    {
	    this.clearamount = clearamount;
    }
    

    /**
     * 功能描述:
     *    获得调整差额
     * @return 调整差额 : BigDecimal
     */
    public BigDecimal getAdjustamount()
    {
		return this.adjustamount;
    }

    /**
     * 功能描述:
     *    设置调整差额
     * @param adjustamount : BigDecimal
     */
    public void setAdjustamount(BigDecimal adjustamount)
    {
	    this.adjustamount = adjustamount;
    }
    

    /**
     * 功能描述:
     *    获得供应商未清数据抬头ID
     * @return 供应商未清数据抬头ID : String
     */
    public String getVendortitleid()
    {
		return this.vendortitleid;
    }

    /**
     * 功能描述:
     *    设置供应商未清数据抬头ID
     * @param vendortitleid : String
     */
    public void setVendortitleid(String vendortitleid)
    {
	    this.vendortitleid = vendortitleid;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额(判断汇兑损益用)
     * @return 本位币金额(判断汇兑损益用) : BigDecimal
     */
    public BigDecimal getBwbje()
    {
		return this.bwbje;
    }

    /**
     * 功能描述:
     *    设置本位币金额(判断汇兑损益用)
     * @param bwbje : BigDecimal
     */
    public void setBwbje(BigDecimal bwbje)
    {
	    this.bwbje = bwbje;
    }
    

    /**
     * 功能描述:
     *    获得未清本位币金额
     * @return 未清本位币金额 : BigDecimal
     */
    public BigDecimal getUnbwbje()
    {
		return this.unbwbje;
    }

    /**
     * 功能描述:
     *    设置未清本位币金额
     * @param unbwbje : BigDecimal
     */
    public void setUnbwbje(BigDecimal unbwbje)
    {
	    this.unbwbje = unbwbje;
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
	 *  默认构造器
	 */
    public UnSupplieBillOther()
    {
    	super();
    }
}
