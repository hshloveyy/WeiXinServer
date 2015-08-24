/*
 * @(#)DeptBudgetting.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月12日 11点34分35秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptBudgetting.domain;

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


/**
 * <pre>
 * 部门预算编制(DeptBudgetting)实体类
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
@Table(name = "YDEPTBUDGETTING")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class DeptBudgetting extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 部门预算编制ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DEPTBUDGETTINGID")
      
    private String deptbudgettingid;   
    
	/*
	 * 年份
	 */
    @Column(name="AYEAR")
    @ValidateRule(dataType=9,label="年份",maxLength= 4,required=false)  
    private String ayear;   
    
	/*
	 * 预算组织ID
	 */
    @Column(name="BUDORGID")
    @ValidateRule(dataType=9,label="预算组织ID",maxLength= 36,required=false)  
    private String budorgid;   
    
	/*
	 * 预算分类ID
	 */
    @Column(name="BUDCLASSID")
    @ValidateRule(dataType=9,label="预算分类ID",maxLength= 36,required=false)  
    private String budclassid;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
	/*
	 * 版本号
	 */
    @Column(name="VERSION")
    @ValidateRule(dataType=9,label="版本号",maxLength= 3,required=false)  
    private String version;   
    

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
     *    获得部门预算编制ID
     * @return 部门预算编制ID : String
     */
    public String getDeptbudgettingid()
    {
		return this.deptbudgettingid;
    }

    /**
     * 功能描述:
     *    设置部门预算编制ID
     * @param deptbudgettingid : String
     */
    public void setDeptbudgettingid(String deptbudgettingid)
    {
	    this.deptbudgettingid = deptbudgettingid;
    }
    

    /**
     * 功能描述:
     *    获得年份
     * @return 年份 : String
     */
    public String getAyear()
    {
		return this.ayear;
    }

    /**
     * 功能描述:
     *    设置年份
     * @param ayear : String
     */
    public void setAyear(String ayear)
    {
	    this.ayear = ayear;
    }
    

    /**
     * 功能描述:
     *    获得预算组织ID
     * @return 预算组织ID : String
     */
    public String getBudorgid()
    {
		return this.budorgid;
    }

    /**
     * 功能描述:
     *    设置预算组织ID
     * @param budorgid : String
     */
    public void setBudorgid(String budorgid)
    {
	    this.budorgid = budorgid;
    }
    

    /**
     * 功能描述:
     *    获得预算分类ID
     * @return 预算分类ID : String
     */
    public String getBudclassid()
    {
		return this.budclassid;
    }

    /**
     * 功能描述:
     *    设置预算分类ID
     * @param budclassid : String
     */
    public void setBudclassid(String budclassid)
    {
	    this.budclassid = budclassid;
    }
    

    /**
     * 功能描述:
     *    获得流程状态
     * @return 流程状态 : String
     */
    public String getProcessstate()
    {
		return this.processstate;
    }

    /**
     * 功能描述:
     *    设置流程状态
     * @param processstate : String
     */
    public void setProcessstate(String processstate)
    {
	    this.processstate = processstate;
    }
    

    /**
     * 功能描述:
     *    获得版本号
     * @return 版本号 : String
     */
    public String getVersion()
    {
		return this.version;
    }

    /**
     * 功能描述:
     *    设置版本号
     * @param version : String
     */
    public void setVersion(String version)
    {
	    this.version = version;
    }
    
    
	/**
	 *  默认构造器
	 */
    public DeptBudgetting()
    {
    	super();
    }
}
