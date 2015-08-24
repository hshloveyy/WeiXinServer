/*
 * @(#)HomeDocuBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月27日 08点19分52秒
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
 * 押汇/海外代付银行(HomeDocuBankItem)实体类
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
@Table(name = "YDOUCARYBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class HomeDocuBankItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 押汇银行项目编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DOCUARYITEMID")
      
    private String docuaryitemid;   
    
	/*
	 * 押汇银行编号
	 */
    @Column(name="DOCUARYBANKID")
    @ValidateRule(dataType=9,label="押汇银行编号",maxLength= 36,required=false)  
    private String docuarybankid;   
    
	/*
	 * 押汇银行名称
	 */
    @Column(name="DOCUARYBANKNAME")
    @ValidateRule(dataType=9,label="押汇银行名称",maxLength= 255,required=false)  
    private String docuarybankname;   
    
	/*
	 * 押汇银行帐号
	 */
    @Column(name="DOCUARYBANKACCO")
    @ValidateRule(dataType=9,label="押汇银行帐号",maxLength= 100,required=false)  
    private String docuarybankacco;   
    
	/*
	 * 押汇银行科目
	 */
    @Column(name="DOCUARYBANKSUBJ")
    @ValidateRule(dataType=9,label="押汇银行科目",maxLength= 36,required=false)  
    private String docuarybanksubj;   
    
	/*
	 * 付款金额
	 */
    @Column(name="DOCUARYPAYAMOUNT")
    @ValidateRule(dataType=0,label="付款金额",required=false)  
    private BigDecimal docuarypayamount;   
    
	/*
	 * 付款金额(本位币)
	 */
    @Column(name="DOCUARYPAYAMOUN2")
    @ValidateRule(dataType=0,label="付款金额(本位币)",required=false)  
    private BigDecimal docuarypayamoun2;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
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
     *    获得押汇银行项目编号
     * @return 押汇银行项目编号 : String
     */
    public String getDocuaryitemid()
    {
		return this.docuaryitemid;
    }

    /**
     * 功能描述:
     *    设置押汇银行项目编号
     * @param docuaryitemid : String
     */
    public void setDocuaryitemid(String docuaryitemid)
    {
	    this.docuaryitemid = docuaryitemid;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行编号
     * @return 押汇银行编号 : String
     */
    public String getDocuarybankid()
    {
		return this.docuarybankid;
    }

    /**
     * 功能描述:
     *    设置押汇银行编号
     * @param docuarybankid : String
     */
    public void setDocuarybankid(String docuarybankid)
    {
	    this.docuarybankid = docuarybankid;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行名称
     * @return 押汇银行名称 : String
     */
    public String getDocuarybankname()
    {
		return this.docuarybankname;
    }

    /**
     * 功能描述:
     *    设置押汇银行名称
     * @param docuarybankname : String
     */
    public void setDocuarybankname(String docuarybankname)
    {
	    this.docuarybankname = docuarybankname;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行帐号
     * @return 押汇银行帐号 : String
     */
    public String getDocuarybankacco()
    {
		return this.docuarybankacco;
    }

    /**
     * 功能描述:
     *    设置押汇银行帐号
     * @param docuarybankacco : String
     */
    public void setDocuarybankacco(String docuarybankacco)
    {
	    this.docuarybankacco = docuarybankacco;
    }
    

    /**
     * 功能描述:
     *    获得押汇银行科目
     * @return 押汇银行科目 : String
     */
    public String getDocuarybanksubj()
    {
		return this.docuarybanksubj;
    }

    /**
     * 功能描述:
     *    设置押汇银行科目
     * @param docuarybanksubj : String
     */
    public void setDocuarybanksubj(String docuarybanksubj)
    {
	    this.docuarybanksubj = docuarybanksubj;
    }
    

    /**
     * 功能描述:
     *    获得付款金额
     * @return 付款金额 : BigDecimal
     */
    public BigDecimal getDocuarypayamount()
    {
		return this.docuarypayamount;
    }

    /**
     * 功能描述:
     *    设置付款金额
     * @param docuarypayamount : BigDecimal
     */
    public void setDocuarypayamount(BigDecimal docuarypayamount)
    {
	    this.docuarypayamount = docuarypayamount;
    }
    

    /**
     * 功能描述:
     *    获得付款金额(本位币)
     * @return 付款金额(本位币) : BigDecimal
     */
    public BigDecimal getDocuarypayamoun2()
    {
		return this.docuarypayamoun2;
    }

    /**
     * 功能描述:
     *    设置付款金额(本位币)
     * @param docuarypayamoun2 : BigDecimal
     */
    public void setDocuarypayamoun2(BigDecimal docuarypayamoun2)
    {
	    this.docuarypayamoun2 = docuarypayamoun2;
    }
    

    /**
     * 功能描述:
     *    获得现金流项目
     * @return 现金流项目 : String
     */
    public String getCashflowitem()
    {
		return this.cashflowitem;
    }

    /**
     * 功能描述:
     *    设置现金流项目
     * @param cashflowitem : String
     */
    public void setCashflowitem(String cashflowitem)
    {
	    this.cashflowitem = cashflowitem;
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
    public HomeDocuBankItem()
    {
    	super();
    }
}
