/*
 * @(#)Rate.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年11月25日 11点58分55秒
 *  描　述：创建
 */
package com.infolion.xdss3.rate.domain;

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
 * 利率(Rate)实体类
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
@Table(name = "YRATE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Rate extends BaseObject
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
    @Column(name="RATEID")
      
    private String rateid;   
    
	/*
	 * 利率
	 */
    @Column(name="RATE")
    @ValidateRule(dataType=0,label="利率",required=true)  
    private BigDecimal rate;   
    
	/*
	 * 开始时间
	 */
    @Column(name="STARTDATE")
    @ValidateRule(dataType=8,label="开始时间",required=true)  
    private String startdate;   
    
	/*
	 * 结束时间
	 */
    @Column(name="ENDDATE")
    @ValidateRule(dataType=8,label="结束时间",required=true)  
    private String enddate;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPT_ID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String dept_id;   
    
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
    public String getRateid()
    {
		return this.rateid;
    }

    /**
     * 功能描述:
     *    设置收款ID
     * @param rateid : String
     */
    public void setRateid(String rateid)
    {
	    this.rateid = rateid;
    }
    

    /**
     * 功能描述:
     *    获得利率
     * @return 利率 : BigDecimal
     */
    public BigDecimal getRate()
    {
		return this.rate;
    }

    /**
     * 功能描述:
     *    设置利率
     * @param rate : BigDecimal
     */
    public void setRate(BigDecimal rate)
    {
	    this.rate = rate;
    }
    

    /**
     * 功能描述:
     *    获得开始时间
     * @return 开始时间 : String
     */
    public String getStartdate()
    {
    	return DateUtils.toDisplayStr(this.startdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置开始时间
     * @param startdate : String
     */
    public void setStartdate(String startdate)
    {
    	startdate = DateUtils.toStoreStr(startdate);
	    this.startdate = startdate;
    }
    

    /**
     * 功能描述:
     *    获得结束时间
     * @return 结束时间 : String
     */
    public String getEnddate()
    {
    	return DateUtils.toDisplayStr(this.enddate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置结束时间
     * @param enddate : String
     */
    public void setEnddate(String enddate)
    {
    	enddate = DateUtils.toStoreStr(enddate);
	    this.enddate = enddate;
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
    public Rate()
    {
    	super();
    }
}
