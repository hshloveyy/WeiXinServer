/*
 * @(#)HomePaymentRelat.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月27日 08点19分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.domain;

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
import com.infolion.xdss3.payment.domain.HomePayment;

/**
 * <pre>
 * 付款相关单据(HomePaymentRelat)实体类
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
@Table(name = "YPAYMENTRELATED")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomePaymentRelat extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 相关单据编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="RELATEDPAYMENTID")
      
    private String relatedpaymentid;   
    
	/*
	 * 退款ID
	 */
    @Column(name="REFUNDMENTID")
    @ValidateRule(dataType=9,label="退款ID",maxLength= 36,required=false)  
    private String refundmentid;   
    
	/*
	 * 关联单据号
	 */
    @Column(name="RELATEDNO")
    @ValidateRule(dataType=9,label="关联单据号",maxLength= 30,required=false)  
    private String relatedno;   
    
	/*
	 * 单据类型
	 */
    @Column(name="RELATEDTYPE")
    @ValidateRule(dataType=9,label="单据类型",maxLength= 2,required=false)  
    private String relatedtype;   
    
	/*
	 * 申请收款金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请收款金额",required=false)  
    private BigDecimal applyamount;   
    
	/*
	 * 重分配ID
	 */
    @Column(name="REASSIGNID")
    @ValidateRule(dataType=9,label="重分配ID",maxLength= 36,required=false)  
    private String reassignid;   
    
	/*
	 * 内贸付款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PAYMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private HomePayment homePayment;   
    

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
     *    获得相关单据编号
     * @return 相关单据编号 : String
     */
    public String getRelatedpaymentid()
    {
		return this.relatedpaymentid;
    }

    /**
     * 功能描述:
     *    设置相关单据编号
     * @param relatedpaymentid : String
     */
    public void setRelatedpaymentid(String relatedpaymentid)
    {
	    this.relatedpaymentid = relatedpaymentid;
    }
    

    /**
     * 功能描述:
     *    获得退款ID
     * @return 退款ID : String
     */
    public String getRefundmentid()
    {
		return this.refundmentid;
    }

    /**
     * 功能描述:
     *    设置退款ID
     * @param refundmentid : String
     */
    public void setRefundmentid(String refundmentid)
    {
	    this.refundmentid = refundmentid;
    }
    

    /**
     * 功能描述:
     *    获得关联单据号
     * @return 关联单据号 : String
     */
    public String getRelatedno()
    {
		return this.relatedno;
    }

    /**
     * 功能描述:
     *    设置关联单据号
     * @param relatedno : String
     */
    public void setRelatedno(String relatedno)
    {
	    this.relatedno = relatedno;
    }
    

    /**
     * 功能描述:
     *    获得单据类型
     * @return 单据类型 : String
     */
    public String getRelatedtype()
    {
		return this.relatedtype;
    }

    /**
     * 功能描述:
     *    设置单据类型
     * @param relatedtype : String
     */
    public void setRelatedtype(String relatedtype)
    {
	    this.relatedtype = relatedtype;
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
     *    获得重分配ID
     * @return 重分配ID : String
     */
    public String getReassignid()
    {
		return this.reassignid;
    }

    /**
     * 功能描述:
     *    设置重分配ID
     * @param reassignid : String
     */
    public void setReassignid(String reassignid)
    {
	    this.reassignid = reassignid;
    }
    

    /**
     * 功能描述:
     *    获得内贸付款
     * @return 内贸付款 : HomePayment
     */
    public HomePayment getHomePayment()
    {
		return this.homePayment;
    }

    /**
     * 功能描述:
     *    设置内贸付款
     * @param homePayment : HomePayment
     */
    public void setHomePayment(HomePayment homePayment)
    {
	    this.homePayment = homePayment;
    }
    
    
	/**
	 *  默认构造器
	 */
    public HomePaymentRelat()
    {
    	super();
    }
}
