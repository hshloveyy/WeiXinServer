/*
 * @(#)CustomerRefundItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 17点13分20秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.domain;

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
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;

/**
 * <pre>
 * 客户退款行项目(CustomerRefundItem)实体类
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
@Table(name = "YREFUNDMENTITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustomerRefundItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 退款项目表ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="REFUNDMENTITEMID")
      
    private String refundmentitemid;   
    
	/*
	 * 收款金额分配ID
	 */
    @Column(name="COLLECTITEMID")
    @ValidateRule(dataType=9,label="收款金额分配ID",maxLength= 36,required=false)  
    private String collectitemid;   
    
	/*
	 * 付款金额分配ID
	 */
    @Column(name="PAYMENTITEMID")
    @ValidateRule(dataType=9,label="付款金额分配ID",maxLength= 36,required=false)  
    private String paymentitemid;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
	/*
	 * 付款单号
	 */
    @Column(name="PAYMENTNO")
    @ValidateRule(dataType=9,label="付款单号",maxLength= 30,required=false)  
    private String paymentno;   
    
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
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 金额
	 */
    @Column(name="ASSIGNAMOUNT")
    @ValidateRule(dataType=0,label="金额",required=false)  
    private BigDecimal assignamount;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 未分配金额
	 */
    @Column(name="UNASSIGNAMOUNT")
    @ValidateRule(dataType=0,label="未分配金额",required=false)  
    private BigDecimal unassignamount;   
    
	/*
	 * 退款币别
	 */
    @Column(name="REFUNDCURRENCY")
    @ValidateRule(dataType=9,label="退款币别",maxLength= 10,required=false)  
    private String refundcurrency;   
    
	/*
	 * 退款金额
	 */
    @Column(name="REFUNDMENTAMOUNT")
    @ValidateRule(dataType=0,label="退款金额",required=false)  
    private BigDecimal refundmentamount;   
    
	/*
	 * 实际退款金额
	 */
    @Column(name="PEFUNDMENTAMOUNT")
    @ValidateRule(dataType=0,label="实际退款金额",required=false)  
    private BigDecimal pefundmentamount;   
    
	/*
	 * 清放货额度金额（本位币）
	 */
    @Column(name="CLEARGOODSAMOUNT")
    @ValidateRule(dataType=0,label="清放货额度金额（本位币）",required=false)  
    private BigDecimal cleargoodsamount;   
    
	/*
	 * 退款金额(本位币)
	 */
    @Column(name="REFUNDMENTVALUE")
    @ValidateRule(dataType=0,label="退款金额(本位币)",required=false)  
    private BigDecimal refundmentvalue;   
    
	/*
	 * 扣含税利润金额（清代垫额度）（本位币）
	 */
    @Column(name="CLEARPREPAYVALUE")
    @ValidateRule(dataType=0,label="扣含税利润金额（清代垫额度）（本位币）",required=false)  
    private BigDecimal clearprepayvalue;   
    
	/*
	 * 扣含税利润金额（清供应商额度）（本位币）
	 */
    @Column(name="CLEARVENDORVALUE")
    @ValidateRule(dataType=0,label="扣含税利润金额（清供应商额度）（本位币）",required=false)  
    private BigDecimal clearvendorvalue;   
    
	/*
	 * 汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="汇率",required=false)  
    private BigDecimal exchangerate;   
    
	/*
	 * 是否保证金
	 */
    @Column(name="ISTYBOND")
    @ValidateRule(dataType=9,label="是否保证金",maxLength= 1,required=false)  
    private String istybond;   
    
    /*
     * 是否已清
     */
    @Column(name="ISCLEAR")
    @ValidateRule(dataType=9,label="是否已清",maxLength= 1,required=false)  
    private String isclear;   
    
	/*
	 * 客户退款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="REFUNDMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private CustomerRefundment customerRefundment;   
    

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
     *    获得退款项目表ID
     * @return 退款项目表ID : String
     */
    public String getRefundmentitemid()
    {
		return this.refundmentitemid;
    }

    /**
     * 功能描述:
     *    设置退款项目表ID
     * @param refundmentitemid : String
     */
    public void setRefundmentitemid(String refundmentitemid)
    {
	    this.refundmentitemid = refundmentitemid;
    }
    

    /**
     * 功能描述:
     *    获得收款金额分配ID
     * @return 收款金额分配ID : String
     */
    public String getCollectitemid()
    {
		return this.collectitemid;
    }

    /**
     * 功能描述:
     *    设置收款金额分配ID
     * @param collectitemid : String
     */
    public void setCollectitemid(String collectitemid)
    {
	    this.collectitemid = collectitemid;
    }
    

    /**
     * 功能描述:
     *    获得付款金额分配ID
     * @return 付款金额分配ID : String
     */
    public String getPaymentitemid()
    {
		return this.paymentitemid;
    }

    /**
     * 功能描述:
     *    设置付款金额分配ID
     * @param paymentitemid : String
     */
    public void setPaymentitemid(String paymentitemid)
    {
	    this.paymentitemid = paymentitemid;
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
     *    获得金额
     * @return 金额 : BigDecimal
     */
    public BigDecimal getAssignamount()
    {
		return this.assignamount;
    }

    /**
     * 功能描述:
     *    设置金额
     * @param assignamount : BigDecimal
     */
    public void setAssignamount(BigDecimal assignamount)
    {
	    this.assignamount = assignamount;
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
     *    获得未分配金额
     * @return 未分配金额 : BigDecimal
     */
    public BigDecimal getUnassignamount()
    {
		return this.unassignamount;
    }

    /**
     * 功能描述:
     *    设置未分配金额
     * @param unassignamount : BigDecimal
     */
    public void setUnassignamount(BigDecimal unassignamount)
    {
	    this.unassignamount = unassignamount;
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
    

    /**
     * 功能描述:
     *    获得 清原币金额
     * @return 清原币金额 : BigDecimal
     */
    public BigDecimal getRefundmentamount()
    {
		return this.refundmentamount;
    }

    /**
     * 功能描述:
     *    设置 清原币金额
     * @param refundmentamount : BigDecimal
     */
    public void setRefundmentamount(BigDecimal refundmentamount)
    {
	    this.refundmentamount = refundmentamount;
    }
    

    /**
     * 功能描述:
     *    获得 实际退款金额
     * @return  实际退款金额 : BigDecimal
     */
    public BigDecimal getPefundmentamount()
    {
		return this.pefundmentamount;
    }

    /**
     * 功能描述:
     *    设置 实际退款金额
     * @param pefundmentamount : BigDecimal
     */
    public void setPefundmentamount(BigDecimal pefundmentamount)
    {
	    this.pefundmentamount = pefundmentamount;
    }
    

    /**
     * 功能描述:
     *    获得清放货额度金额（本位币）
     * @return 清放货额度金额（本位币） : BigDecimal
     */
    public BigDecimal getCleargoodsamount()
    {
		return this.cleargoodsamount;
    }

    /**
     * 功能描述:
     *    设置清放货额度金额（本位币）
     * @param cleargoodsamount : BigDecimal
     */
    public void setCleargoodsamount(BigDecimal cleargoodsamount)
    {
	    this.cleargoodsamount = cleargoodsamount;
    }
    

    /**
     * 功能描述:
     *    获得退款金额(本位币)
     * @return 退款金额(本位币) : BigDecimal
     */
    public BigDecimal getRefundmentvalue()
    {
		return this.refundmentvalue;
    }

    /**
     * 功能描述:
     *    设置退款金额(本位币)
     * @param refundmentvalue : BigDecimal
     */
    public void setRefundmentvalue(BigDecimal refundmentvalue)
    {
	    this.refundmentvalue = refundmentvalue;
    }
    

    /**
     * 功能描述:
     *    获得扣含税利润金额（清代垫额度）（本位币）
     * @return 扣含税利润金额（清代垫额度）（本位币） : BigDecimal
     */
    public BigDecimal getClearprepayvalue()
    {
		return this.clearprepayvalue;
    }

    /**
     * 功能描述:
     *    设置扣含税利润金额（清代垫额度）（本位币）
     * @param clearprepayvalue : BigDecimal
     */
    public void setClearprepayvalue(BigDecimal clearprepayvalue)
    {
	    this.clearprepayvalue = clearprepayvalue;
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
     *    获得付款利率
     * @return 付款利率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置付款利率
     * @param exchangerate : BigDecimal
     */
    public void setExchangerate(BigDecimal exchangerate)
    {
	    this.exchangerate = exchangerate;
    }
    

    /**
     * 功能描述:
     *    获得是否保证金
     * @return 是否保证金 : String
     */
    public String getIstybond()
    {
		return this.istybond;
    }

    /**
     * 功能描述:
     *    设置是否保证金
     * @param istybond : String
     */
    public void setIstybond(String istybond)
    {
	    this.istybond = istybond;
    }
    

    /**
     * 功能描述:
     *    获得客户退款
     * @return 客户退款 : CustomerRefundment
     */
    public CustomerRefundment getCustomerRefundment()
    {
		return this.customerRefundment;
    }

    /**
     * 功能描述:
     *    设置客户退款
     * @param customerRefundment : CustomerRefundment
     */
    public void setCustomerRefundment(CustomerRefundment customerRefundment)
    {
	    this.customerRefundment = customerRefundment;
    }
    
    /**
     * 功能描述:
     *    获得是否已清
     * @return 是否已清 : String
     */
    public String getIsclear()
    {
        return this.isclear;
    }

    /**
     * 功能描述:
     *    设置是否已清
     * @param isclear : String
     */
    public void setIsclear(String isclear)
    {
        this.isclear = isclear;
    }
    
	/**
	 *  默认构造器
	 */
    public CustomerRefundItem()
    {
    	super();
    }
}
