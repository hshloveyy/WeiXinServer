/*
 * @(#)CustomerCreditProj.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月20日 12点33分44秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.domain;

import java.math.BigDecimal;
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
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;

/**
 * <pre>
 * 客户信用额度下挂立项配置表(CustomerCreditProj)实体类
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
@Table(name = "YCUSTCREDPROJ")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustomerCreditProj extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 配置立项编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CONFIGPROJECTID")
      
    private String configprojectid;   
    
	/*
	 * 项目ID
	 */
    @Column(name="PROJECTID")
    @ValidateRule(dataType=9,label="项目ID",maxLength= 36,required=false)  
    private String projectid;
    
    /*
	 * 项目编号
	 */
    @Column(name="PROJECTNO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 6,required=false)  
    private String projectno;
    
    /*
	 * 其他代垫费用
	 */
    @Column(name="OTHERPREPAYVALUE")
    @ValidateRule(dataType=0,label="其他代垫费用",required=false)  
    private BigDecimal otherprepayvalue;
    
    /*
	 * 其他放货费用
	 */
    @Column(name="OTHERSENDVALUE")
    @ValidateRule(dataType=0,label="其他放货费用",required=false)  
    private BigDecimal othersendvalue;   
    
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
	 * 客户代垫额度和发货额度配置
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="CONFIGID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private CustomerCreditConf customerCreditConf;   
    

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
     *    获得配置立项编号
     * @return 配置立项编号 : String
     */
    public String getConfigprojectid()
    {
		return this.configprojectid;
    }

    /**
     * 功能描述:
     *    设置配置立项编号
     * @param configprojectid : String
     */
    public void setConfigprojectid(String configprojectid)
    {
	    this.configprojectid = configprojectid;
    }
    

    /**
     * 功能描述:
     *    获得项目ID
     * @return 项目ID : String
     */
    public String getProjectid()
    {
		return this.projectid;
    }

    /**
     * 功能描述:
     *    设置项目ID
     * @param projectid : String
     */
    public void setProjectid(String projectid)
    {
	    this.projectid = projectid;
    }
    
    public String getProjectno() {
		return projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
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
     *    获得客户代垫额度和发货额度配置
     * @return 客户代垫额度和发货额度配置 : CustomerCreditConf
     */
    public CustomerCreditConf getCustomerCreditConf()
    {
		return this.customerCreditConf;
    }

    /**
     * 功能描述:
     *    设置客户代垫额度和发货额度配置
     * @param customerCreditConf : CustomerCreditConf
     */
    public void setCustomerCreditConf(CustomerCreditConf customerCreditConf)
    {
	    this.customerCreditConf = customerCreditConf;
    }
    
    /**
     * 功能描述:
     *    获得其他代垫费用
     * @return 其他代垫费用 : BigDecimal
     */
    public BigDecimal getOtherprepayvalue()
    {
		return this.otherprepayvalue;
    }

    /**
     * 功能描述:
     *    设置其他代垫费用
     * @param otherprepayvalue : BigDecimal
     */
    public void setOtherprepayvalue(BigDecimal otherprepayvalue)
    {
	    this.otherprepayvalue = otherprepayvalue;
    }
    
    /**
     * 功能描述:
     *    获得其他放货费用
     * @return 其他放货费用 : BigDecimal
     */
    public BigDecimal getOthersendvalue()
    {
		return this.othersendvalue;
    }

    /**
     * 功能描述:
     *    设置其他放货费用
     * @param othersendvalue : BigDecimal
     */
    public void setOthersendvalue(BigDecimal othersendvalue)
    {
	    this.othersendvalue = othersendvalue;
    }
    
    
	/**
	 *  默认构造器
	 */
    public CustomerCreditProj()
    {
    	super();
    }
}
