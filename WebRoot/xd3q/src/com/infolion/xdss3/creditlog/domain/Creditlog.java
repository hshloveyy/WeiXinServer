/*
 * @(#)Creditlog.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月22日 12点09分38秒
 *  描　述：创建
 */
package com.infolion.xdss3.creditlog.domain;

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
 * 授信日志表(Creditlog)实体类
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
@Table(name = "YCREDITLOG")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Creditlog extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 授信日志表ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="YCREDITLOGID")
      
    private String ycreditlogid;   
    
	/*
	 * 客户或供应商类型
	 */
    @Column(name="YTYPE")
    @ValidateRule(dataType=9,label="客户或供应商类型",maxLength= 1,required=false)  
    private String ytype;   
    
	/*
	 * 授信日志模块
	 */
    @Column(name="YMODULE")
    @ValidateRule(dataType=9,label="授信日志模块",maxLength= 20,required=false)  
    private String ymodule;   
    
	/*
	 * 相关模块单号
	 */
    @Column(name="BUSNUM")
    @ValidateRule(dataType=9,label="相关模块单号",maxLength= 50,required=false)  
    private String busnum;   
    
	/*
	 * 金额
	 */
    @Column(name="AMOUT")
    @ValidateRule(dataType=0,label="金额",required=false)  
    private BigDecimal amout;   
    
	/*
	 * 授信日志增/减标志
	 */
    @Column(name="ACTION")
    @ValidateRule(dataType=9,label="授信日志增/减标志",maxLength= 1,required=false)  
    private String action;   
    
	/*
	 * 授信类型
	 */
    @Column(name="CREDITTYPE")
    @ValidateRule(dataType=9,label="授信类型",maxLength= 1,required=false)  
    private String credittype;   
    
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
	 * 客户ID
	 */
    @Column(name="CUSTNO")
    @ValidateRule(dataType=9,label="客户ID",maxLength= 36,required=false)  
    private String custno;   
    
	/*
	 * 供应商/委托方ID
	 */
    @Column(name="PROVIDERNO")
    @ValidateRule(dataType=9,label="供应商/委托方ID",maxLength= 36,required=false)  
    private String providerno;   
    
	/*
	 * 用户ID
	 */
    @Column(name="USERID")
    @ValidateRule(dataType=9,label="用户ID",maxLength= 36,required=false)  
    private String userid;   
    
	/*
	 * 生效日期
	 */
    @Column(name="ACTIVEDATE")
    @ValidateRule(dataType=9,label="生效日期",maxLength= 8,required=false)  
    private String activedate;   
    

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
     *    获得授信日志表ID
     * @return 授信日志表ID : String
     */
    public String getYcreditlogid()
    {
		return this.ycreditlogid;
    }

    /**
     * 功能描述:
     *    设置授信日志表ID
     * @param ycreditlogid : String
     */
    public void setYcreditlogid(String ycreditlogid)
    {
	    this.ycreditlogid = ycreditlogid;
    }
    

    /**
     * 功能描述:
     *    获得客户或供应商类型
     * @return 客户或供应商类型 : String
     */
    public String getYtype()
    {
		return this.ytype;
    }

    /**
     * 功能描述:
     *    设置客户或供应商类型
     * @param ytype : String
     */
    public void setYtype(String ytype)
    {
	    this.ytype = ytype;
    }
    

    /**
     * 功能描述:
     *    获得授信日志模块
     * @return 授信日志模块 : String
     */
    public String getYmodule()
    {
		return this.ymodule;
    }

    /**
     * 功能描述:
     *    设置授信日志模块
     * @param ymodule : String
     */
    public void setYmodule(String ymodule)
    {
	    this.ymodule = ymodule;
    }
    

    /**
     * 功能描述:
     *    获得相关模块单号
     * @return 相关模块单号 : String
     */
    public String getBusnum()
    {
		return this.busnum;
    }

    /**
     * 功能描述:
     *    设置相关模块单号
     * @param busnum : String
     */
    public void setBusnum(String busnum)
    {
	    this.busnum = busnum;
    }
    

    /**
     * 功能描述:
     *    获得金额
     * @return 金额 : BigDecimal
     */
    public BigDecimal getAmout()
    {
		return this.amout;
    }

    /**
     * 功能描述:
     *    设置金额
     * @param amout : BigDecimal
     */
    public void setAmout(BigDecimal amout)
    {
	    this.amout = amout;
    }
    

    /**
     * 功能描述:
     *    获得授信日志增/减标志
     * @return 授信日志增/减标志 : String
     */
    public String getAction()
    {
		return this.action;
    }

    /**
     * 功能描述:
     *    设置授信日志增/减标志
     * @param action : String
     */
    public void setAction(String action)
    {
	    this.action = action;
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
    

    /**
     * 功能描述:
     *    获得项目编号
     * @return 项目编号 : String
     */
    public String getProjectno()
    {
		return this.projectno;
    }

    /**
     * 功能描述:
     *    设置项目编号
     * @param projectno : String
     */
    public void setProjectno(String projectno)
    {
	    this.projectno = projectno;
    }
    

    /**
     * 功能描述:
     *    获得客户ID
     * @return 客户ID : String
     */
    public String getCustno()
    {
		return this.custno;
    }

    /**
     * 功能描述:
     *    设置客户ID
     * @param custno : String
     */
    public void setCustno(String custno)
    {
	    this.custno = custno;
    }
    

    /**
     * 功能描述:
     *    获得供应商/委托方ID
     * @return 供应商/委托方ID : String
     */
    public String getProviderno()
    {
		return this.providerno;
    }

    /**
     * 功能描述:
     *    设置供应商/委托方ID
     * @param providerno : String
     */
    public void setProviderno(String providerno)
    {
	    this.providerno = providerno;
    }
    

    /**
     * 功能描述:
     *    获得用户ID
     * @return 用户ID : String
     */
    public String getUserid()
    {
		return this.userid;
    }

    /**
     * 功能描述:
     *    设置用户ID
     * @param userid : String
     */
    public void setUserid(String userid)
    {
	    this.userid = userid;
    }
    

    /**
     * 功能描述:
     *    获得生效日期
     * @return 生效日期 : String
     */
    public String getActivedate()
    {
		return this.activedate;
    }

    /**
     * 功能描述:
     *    设置生效日期
     * @param activedate : String
     */
    public void setActivedate(String activedate)
    {
	    this.activedate = activedate;
    }
    
    
	/**
	 *  默认构造器
	 */
    public Creditlog()
    {
    	super();
    }
}
