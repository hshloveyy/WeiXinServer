/*
 * @(#)BudgetOrganization.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分04秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrganization.domain;

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
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;

/**
 * <pre>
 * 预算组织(BudgetOrganization)实体类
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
@Table(name = "YBUDORG")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudgetOrganization extends BaseObject
{
	//fields
	/*
	 * 预算组织模版
	 */
    @OneToMany(mappedBy="budgetOrganization",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BudgetOrgTemp> budgetOrgTemp;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预算组织ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDORGID")
      
    private String budorgid;   
    
	/*
	 * 预算组织名称
	 */
    @Column(name="BUDORGNAME")
    @ValidateRule(dataType=9,label="预算组织名称",maxLength= 100,required=true)  
    private String budorgname;   
    
	/*
	 * 预算组织类型
	 */
    @Column(name="BUDORGTYPE")
    @ValidateRule(dataType=9,label="预算组织类型",maxLength= 10,required=true)  
    private String budorgtype;   
    
	/*
	 * 预算控制类型
	 */
    @Column(name="BUDCONTYPE")
    @ValidateRule(dataType=9,label="预算控制类型",maxLength= 10,required=true)  
    private String budcontype;   
    
	/*
	 * 公司代码
	 */
    @Column(name="COMPANYCODE")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 36,required=true)  
    private String companycode;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=true)  
    private String deptid;   
    
	/*
	 * 成本中心
	 */
    @Column(name="COSTCENTER")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 36,required=true)  
    private String costcenter;   
    
	/*
	 * 上级预算组织ID
	 */
    @Column(name="BUDUPORGID")
    @ValidateRule(dataType=9,label="上级预算组织ID",maxLength= 36,required=true)  
    private String buduporgid;   
    
	/*
	 * 状态
	 */
    @Column(name="STATUS")
    @ValidateRule(dataType=9,label="状态",maxLength= 1,required=true)  
    private String status;   
    
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
     *    获得预算组织模版
     * @return 预算组织模版 : Set<BudgetOrgTemp>
     */
    public Set<BudgetOrgTemp> getBudgetOrgTemp()
    {
		return this.budgetOrgTemp;
    }

    /**
     * 功能描述:
     *    设置预算组织模版
     * @param budgetOrgTemp : Set<BudgetOrgTemp>
     */
    public void setBudgetOrgTemp(Set<BudgetOrgTemp> budgetOrgTemp)
    {
	    this.budgetOrgTemp = budgetOrgTemp;
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
     *    获得预算组织名称
     * @return 预算组织名称 : String
     */
    public String getBudorgname()
    {
		return this.budorgname;
    }

    /**
     * 功能描述:
     *    设置预算组织名称
     * @param budorgname : String
     */
    public void setBudorgname(String budorgname)
    {
	    this.budorgname = budorgname;
    }
    

    /**
     * 功能描述:
     *    获得预算组织类型
     * @return 预算组织类型 : String
     */
    public String getBudorgtype()
    {
		return this.budorgtype;
    }

    /**
     * 功能描述:
     *    设置预算组织类型
     * @param budorgtype : String
     */
    public void setBudorgtype(String budorgtype)
    {
	    this.budorgtype = budorgtype;
    }
    

    /**
     * 功能描述:
     *    获得预算控制类型
     * @return 预算控制类型 : String
     */
    public String getBudcontype()
    {
		return this.budcontype;
    }

    /**
     * 功能描述:
     *    设置预算控制类型
     * @param budcontype : String
     */
    public void setBudcontype(String budcontype)
    {
	    this.budcontype = budcontype;
    }
    

    /**
     * 功能描述:
     *    获得公司代码
     * @return 公司代码 : String
     */
    public String getCompanycode()
    {
		return this.companycode;
    }

    /**
     * 功能描述:
     *    设置公司代码
     * @param companycode : String
     */
    public void setCompanycode(String companycode)
    {
	    this.companycode = companycode;
    }
    

    /**
     * 功能描述:
     *    获得部门ID
     * @return 部门ID : String
     */
    public String getDeptid()
    {
		return this.deptid;
    }

    /**
     * 功能描述:
     *    设置部门ID
     * @param deptid : String
     */
    public void setDeptid(String deptid)
    {
	    this.deptid = deptid;
    }
    

    /**
     * 功能描述:
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getCostcenter()
    {
		return this.costcenter;
    }

    /**
     * 功能描述:
     *    设置成本中心
     * @param costcenter : String
     */
    public void setCostcenter(String costcenter)
    {
	    this.costcenter = costcenter;
    }
    

    /**
     * 功能描述:
     *    获得上级预算组织ID
     * @return 上级预算组织ID : String
     */
    public String getBuduporgid()
    {
		return this.buduporgid;
    }

    /**
     * 功能描述:
     *    设置上级预算组织ID
     * @param buduporgid : String
     */
    public void setBuduporgid(String buduporgid)
    {
	    this.buduporgid = buduporgid;
    }
    

    /**
     * 功能描述:
     *    获得状态
     * @return 状态 : String
     */
    public String getStatus()
    {
		return this.status;
    }

    /**
     * 功能描述:
     *    设置状态
     * @param status : String
     */
    public void setStatus(String status)
    {
	    this.status = status;
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
    public BudgetOrganization()
    {
    	super();
    }
}
