/*
 * @(#)Tcurr.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月05日 07点18分16秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

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
 * 汇率(Tcurr)实体类
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
@Table(name = "YTCURR")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Tcurr extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 汇率表ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="TCURRID")
      
    private String tcurrid;   
    
	/*
	 * 货币
	 */
    @Column(name="TCURR")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String tcurr;   
    
	/*
	 * 原始币别
	 */
    @Column(name="FCURR")
    @ValidateRule(dataType=9,label="原始币别",maxLength= 5,required=false)  
    private String fcurr;   
    
	/*
	 * 汇率时间
	 */
    @Column(name="GDATU")
    @ValidateRule(dataType=9,label="汇率时间",maxLength= 8,required=false)  
    private String gdatu;   
    
	/*
	 * 汇率
	 */
    @Column(name="UKURS")
    @ValidateRule(dataType=0,label="汇率",required=false)  
    private BigDecimal ukurs;   

    
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
     *    获得汇率表ID
     * @return 汇率表ID : String
     */
    public String getTcurrid()
    {
		return this.tcurrid;
    }

    /**
     * 功能描述:
     *    设置汇率表ID
     * @param tcurrid : String
     */
    public void setTcurrid(String tcurrid)
    {
	    this.tcurrid = tcurrid;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getTcurr()
    {
		return this.tcurr;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param tcurr : String
     */
    public void setTcurr(String tcurr)
    {
	    this.tcurr = tcurr;
    }
    

    /**
     * 功能描述:
     *    获得原始币别
     * @return 原始币别 : String
     */
    public String getFcurr()
    {
		return this.fcurr;
    }

    /**
     * 功能描述:
     *    设置原始币别
     * @param fcurr : String
     */
    public void setFcurr(String fcurr)
    {
	    this.fcurr = fcurr;
    }
    

    /**
     * 功能描述:
     *    获得汇率时间
     * @return 汇率时间 : String
     */
    public String getGdatu()
    {
		return this.gdatu;
    }

    /**
     * 功能描述:
     *    设置汇率时间
     * @param gdatu : String
     */
    public void setGdatu(String gdatu)
    {
	    this.gdatu = gdatu;
    }
    

    /**
     * 功能描述:
     *    获得汇率
     * @return 汇率 : BigDecimal
     */
    public BigDecimal getUkurs()
    {
		return this.ukurs;
    }

    /**
     * 功能描述:
     *    设置汇率
     * @param ukurs : BigDecimal
     */
    public void setUkurs(BigDecimal ukurs)
    {
	    this.ukurs = ukurs;
    }
    
	/**
	 *  默认构造器
	 */
    public Tcurr()
    {
    	super();
    }
}
