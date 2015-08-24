/*
 * @(#)CustomerRemSum.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月22日 12点12分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerremainsum.domain;

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
 * 客户项目余额(CustomerRemSum)实体类
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
@Table(name = "YCUSTOMERREMSUM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustomerRemSum extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 客户项目余额id
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CPRID")
      
    private String cprid;   
    
	/*
	 * 部门名称
	 */
    @Column(name="DEPTNAME")
    @ValidateRule(dataType=9,label="部门名称",maxLength= 128,required=false)  
    private String deptname;   
    
	/*
	 * 供应商配置编号
	 */
    @Column(name="CONFIG")
    @ValidateRule(dataType=9,label="供应商配置编号",maxLength= 36,required=false)  
    private String config;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMERNO")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customerno;   
    
	/*
	 * 立项号
	 */
    @Column(name="PROJECT")
    @ValidateRule(dataType=9,label="立项号",maxLength= 100,required=false)  
    private String project;   
    
	/*
	 * 项目
	 */
    @Column(name="PROJECTNO")
    @ValidateRule(dataType=9,label="项目",maxLength= 200,required=false)  
    private String projectno;   
    
	/*
	 * 客户余额
	 */
    @Column(name="REMAININGSUM")
    @ValidateRule(dataType=0,label="客户余额",required=false)  
    private BigDecimal remainingsum;   
    
	/*
	 * 用户ID
	 */
    @Column(name="USERID")
    @ValidateRule(dataType=9,label="用户ID",maxLength= 36,required=false)  
    private String userid;   
    
	/*
	 * 授信类型
	 */
    @Column(name="CREDITTYPE")
    @ValidateRule(dataType=9,label="授信类型",maxLength= 1,required=false)  
    private String credittype;   
    
	/*
	 * 代垫额度
	 */
    @Column(name="PREPAYVALUE")
    @ValidateRule(dataType=0,label="代垫额度",required=false)  
    private BigDecimal prepayvalue;   
    
	/*
	 * 放货额度
	 */
    @Column(name="SENDVALUE")
    @ValidateRule(dataType=0,label="放货额度",required=false)  
    private BigDecimal sendvalue;   
    
	/*
	 * 有效结束日期
	 */
    @Column(name="ENDTIME")
    @ValidateRule(dataType=9,label="有效结束日期",maxLength= 14,required=false)  
    private String endtime;   
    

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
     *    获得客户项目余额id
     * @return 客户项目余额id : String
     */
    public String getCprid()
    {
		return this.cprid;
    }

    /**
     * 功能描述:
     *    设置客户项目余额id
     * @param cprid : String
     */
    public void setCprid(String cprid)
    {
	    this.cprid = cprid;
    }
    

    /**
     * 功能描述:
     *    获得部门名称
     * @return 部门名称 : String
     */
    public String getDeptname()
    {
		return this.deptname;
    }

    /**
     * 功能描述:
     *    设置部门名称
     * @param deptname : String
     */
    public void setDeptname(String deptname)
    {
	    this.deptname = deptname;
    }
    

    /**
     * 功能描述:
     *    获得供应商配置编号
     * @return 供应商配置编号 : String
     */
    public String getConfig()
    {
		return this.config;
    }

    /**
     * 功能描述:
     *    设置供应商配置编号
     * @param config : String
     */
    public void setConfig(String config)
    {
	    this.config = config;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomerno()
    {
		return this.customerno;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customerno : String
     */
    public void setCustomerno(String customerno)
    {
	    this.customerno = customerno;
    }
    

    /**
     * 功能描述:
     *    获得立项号
     * @return 立项号 : String
     */
    public String getProject()
    {
		return this.project;
    }

    /**
     * 功能描述:
     *    设置立项号
     * @param project : String
     */
    public void setProject(String project)
    {
	    this.project = project;
    }
    

    /**
     * 功能描述:
     *    获得项目
     * @return 项目 : String
     */
    public String getProjectno()
    {
		return this.projectno;
    }

    /**
     * 功能描述:
     *    设置项目
     * @param projectno : String
     */
    public void setProjectno(String projectno)
    {
	    this.projectno = projectno;
    }
    

    /**
     * 功能描述:
     *    获得客户余额
     * @return 客户余额 : BigDecimal
     */
    public BigDecimal getRemainingsum()
    {
		return this.remainingsum;
    }

    /**
     * 功能描述:
     *    设置客户余额
     * @param remainingsum : BigDecimal
     */
    public void setRemainingsum(BigDecimal remainingsum)
    {
	    this.remainingsum = remainingsum;
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
     *    获得代垫额度
     * @return 代垫额度 : BigDecimal
     */
    public BigDecimal getPrepayvalue()
    {
		return this.prepayvalue;
    }

    /**
     * 功能描述:
     *    设置代垫额度
     * @param prepayvalue : BigDecimal
     */
    public void setPrepayvalue(BigDecimal prepayvalue)
    {
	    this.prepayvalue = prepayvalue;
    }
    

    /**
     * 功能描述:
     *    获得放货额度
     * @return 放货额度 : BigDecimal
     */
    public BigDecimal getSendvalue()
    {
		return this.sendvalue;
    }

    /**
     * 功能描述:
     *    设置放货额度
     * @param sendvalue : BigDecimal
     */
    public void setSendvalue(BigDecimal sendvalue)
    {
	    this.sendvalue = sendvalue;
    }
    

    /**
     * 功能描述:
     *    获得有效结束日期
     * @return 有效结束日期 : String
     */
    public String getEndtime()
    {
		return this.endtime;
    }

    /**
     * 功能描述:
     *    设置有效结束日期
     * @param endtime : String
     */
    public void setEndtime(String endtime)
    {
	    this.endtime = endtime;
    }
    
    
	/**
	 *  默认构造器
	 */
    public CustomerRemSum()
    {
    	super();
    }
}
