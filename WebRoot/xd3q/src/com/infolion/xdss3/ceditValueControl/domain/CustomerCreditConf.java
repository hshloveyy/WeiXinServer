/*
 * @(#)CustomerCreditConf.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点33分41秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.domain;

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
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;

/**
 * <pre>
 * 客户代垫额度和发货额度配置(CustomerCreditConf)实体类
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
@Table(name = "YCUSTCREDCONF")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustomerCreditConf extends BaseObject
{
	//fields
	/*
	 * 客户信用额度下挂立项配置表
	 */
    @OneToMany(mappedBy="customerCreditConf",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<CustomerCreditProj> customerCreditProject;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 供应商配置编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CONFIGID")
      
    private String configid;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMERID")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customerid;    
    
	/*
	 * 代垫初始额度
	 */
    @Column(name="PREPAYVALUE")
    @ValidateRule(dataType=0,label="代垫初始额度",required=false)  
    private BigDecimal prepayvalue;   
    
	/*
	 * 赊销放货额度
	 */
    @Column(name="SENDVALUE")
    @ValidateRule(dataType=0,label="赊销放货额度",required=false)  
    private BigDecimal sendvalue;      
    
	/*
	 * 有效起始时间
	 */
    @Column(name="STARTINGTIME")
    @ValidateRule(dataType=8,label="有效起始时间",required=false)  
    private String startingtime;   
    
	/*
	 * 授信类型
	 */
    @Column(name="CREDITTYPE")
    @ValidateRule(dataType=9,label="授信类型",maxLength= 1,required=false)  
    private String credittype;   
    
	/*
	 * 有效结束时间
	 */
    @Column(name="ENDTIME")
    @ValidateRule(dataType=8,label="有效结束时间",required=false)  
    private String endtime;   
    
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
    

    /**
     * 功能描述:
     *    获得客户信用额度下挂立项配置表
     * @return 客户信用额度下挂立项配置表 : Set<CustomerCreditProj>
     */
    public Set<CustomerCreditProj> getCustomerCreditProject()
    {
		return this.customerCreditProject;
    }

    /**
     * 功能描述:
     *    设置客户信用额度下挂立项配置表
     * @param customerCreditProject : Set<CustomerCreditProj>
     */
    public void setCustomerCreditProject(Set<CustomerCreditProj> customerCreditProject)
    {
	    this.customerCreditProject = customerCreditProject;
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
     *    获得供应商配置编号
     * @return 供应商配置编号 : String
     */
    public String getConfigid()
    {
		return this.configid;
    }

    /**
     * 功能描述:
     *    设置供应商配置编号
     * @param configid : String
     */
    public void setConfigid(String configid)
    {
	    this.configid = configid;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomerid()
    {
		return this.customerid;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customerid : String
     */
    public void setCustomerid(String customerid)
    {
	    this.customerid = customerid;
    }
    

    /**
     * 功能描述:
     *    获得代垫初始额度
     * @return 代垫初始额度 : BigDecimal
     */
    public BigDecimal getPrepayvalue()
    {
		return this.prepayvalue;
    }

    /**
     * 功能描述:
     *    设置代垫初始额度
     * @param prepayvalue : BigDecimal
     */
    public void setPrepayvalue(BigDecimal prepayvalue)
    {
	    this.prepayvalue = prepayvalue;
    }
    

    /**
     * 功能描述:
     *    获得赊销放货额度
     * @return 赊销放货额度 : BigDecimal
     */
    public BigDecimal getSendvalue()
    {
		return this.sendvalue;
    }

    /**
     * 功能描述:
     *    设置赊销放货额度
     * @param sendvalue : BigDecimal
     */
    public void setSendvalue(BigDecimal sendvalue)
    {
	    this.sendvalue = sendvalue;
    }
    

    /**
     * 功能描述:
     *    获得有效起始时间
     * @return 有效起始时间 : String
     */
    public String getStartingtime()
    {
    	return DateUtils.toDisplayStr(this.startingtime, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置有效起始时间
     * @param startingtime : String
     */
    public void setStartingtime(String startingtime)
    {
    	startingtime = DateUtils.toStoreStr(startingtime);
	    this.startingtime = startingtime;
    }
    

    /**
     * 功能描述:
     *    获得授信类型
     * @return 授信类型 : String
     */
    public String getCredittype()
    {
		return this.credittype;
    }

    /**
     * 功能描述:
     *    设置授信类型
     * @param credittype : String
     */
    public void setCredittype(String credittype)
    {
	    this.credittype = credittype;
    }
    

    /**
     * 功能描述:
     *    获得有效结束时间
     * @return 有效结束时间 : String
     */
    public String getEndtime()
    {
    	return DateUtils.toDisplayStr(this.endtime, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置有效结束时间
     * @param endtime : String
     */
    public void setEndtime(String endtime)
    {
    	endtime = DateUtils.toStoreStr(endtime);
	    this.endtime = endtime;
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
	 *  默认构造器
	 */
    public CustomerCreditConf()
    {
    	super();
    }
}
