/*
 * @(#)CollectCbill.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月20日 01点22分48秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectcbill.domain;

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
import com.infolion.xdss3.collect.domain.Collect;

/**
 * <pre>
 * 收款清票(CollectCbill)实体类
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
@Table(name = "YCOLLECTCBILL")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CollectCbill extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 收款清票ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="COLLECTCBILLID")
      
    private String collectcbillid;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 记账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=9,label="记账日期",maxLength= 20,required=false)  
    private String accountdate;   
    
	/*
	 * 发票号
	 */
    @Column(name="BILLNO")
    @ValidateRule(dataType=9,label="发票号",maxLength= 50,required=false)  
    private String billno;   
    
	/*
	 * 消息文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="消息文本",maxLength= 150,required=false)  
    private String text;   
    
	/*
	 * 原合同号
	 */
    @Column(name="OLD_CONTRACT_NO")
    @ValidateRule(dataType=9,label="原合同号",maxLength= 36,required=false)  
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
	 * 应收款
	 */
    @Column(name="RECEIVABLEAMOUNT")
    @ValidateRule(dataType=0,label="应收款",required=false)  
    private BigDecimal receivableamount;   
    
	/*
	 * 已收款
	 */
    @Column(name="RECEIVEDAMOUNT")
    @ValidateRule(dataType=0,label="已收款",required=false)  
    private BigDecimal receivedamount;   
    
	/*
	 * 未收款
	 */
    @Column(name="UNRECEIVEDAMOUNT")
    @ValidateRule(dataType=0,label="未收款",required=false)  
    private BigDecimal unreceivedamount;   
    
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
	 * 收款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="COLLECTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Collect collect;   
    

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
     *    获得收款清票ID
     * @return 收款清票ID : String
     */
    public String getCollectcbillid()
    {
		return this.collectcbillid;
    }

    /**
     * 功能描述:
     *    设置收款清票ID
     * @param collectcbillid : String
     */
    public void setCollectcbillid(String collectcbillid)
    {
	    this.collectcbillid = collectcbillid;
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
     *    获得记账日期
     * @return 记账日期 : String
     */
    public String getAccountdate()
    {
		return this.accountdate;
    }

    /**
     * 功能描述:
     *    设置记账日期
     * @param accountdate : String
     */
    public void setAccountdate(String accountdate)
    {
	    this.accountdate = accountdate;
    }
    

    /**
     * 功能描述:
     *    获得发票号
     * @return 发票号 : String
     */
    public String getBillno()
    {
		return this.billno;
    }

    /**
     * 功能描述:
     *    设置发票号
     * @param billno : String
     */
    public void setBillno(String billno)
    {
	    this.billno = billno;
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
     *    获得原合同号
     * @return 原合同号 : String
     */
    public String getOld_contract_no()
    {
		return this.old_contract_no;
    }

    /**
     * 功能描述:
     *    设置原合同号
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
     *    获得应收款
     * @return 应收款 : BigDecimal
     */
    public BigDecimal getReceivableamount()
    {
		return this.receivableamount;
    }

    /**
     * 功能描述:
     *    设置应收款
     * @param receivableamount : BigDecimal
     */
    public void setReceivableamount(BigDecimal receivableamount)
    {
	    this.receivableamount = receivableamount;
    }
    

    /**
     * 功能描述:
     *    获得已收款
     * @return 已收款 : BigDecimal
     */
    public BigDecimal getReceivedamount()
    {
		return this.receivedamount;
    }

    /**
     * 功能描述:
     *    设置已收款
     * @param receivedamount : BigDecimal
     */
    public void setReceivedamount(BigDecimal receivedamount)
    {
	    this.receivedamount = receivedamount;
    }
    

    /**
     * 功能描述:
     *    获得未收款
     * @return 未收款 : BigDecimal
     */
    public BigDecimal getUnreceivedamount()
    {
		return this.unreceivedamount;
    }

    /**
     * 功能描述:
     *    设置未收款
     * @param unreceivedamount : BigDecimal
     */
    public void setUnreceivedamount(BigDecimal unreceivedamount)
    {
	    this.unreceivedamount = unreceivedamount;
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
     *    获得收款
     * @return 收款 : Collect
     */
    public Collect getCollect()
    {
		return this.collect;
    }

    /**
     * 功能描述:
     *    设置收款
     * @param collect : Collect
     */
    public void setCollect(Collect collect)
    {
	    this.collect = collect;
    }

	/**
	 *  默认构造器
	 */
    public CollectCbill()
    {
    	super();
    }
}
