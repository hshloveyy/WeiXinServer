/*
 * @(#)BudgetItemGroup.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分33秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain;

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
import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;

/**
 * <pre>
 * 预算项分组(BudgetItemGroup)实体类
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
@Table(name = "YBUDITEMGROUP")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudgetItemGroup extends BaseObject
{
	//fields
	/*
	 * 预算项
	 */
    @OneToMany(mappedBy="budgetItemGroup",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BudgetItem> budgetItem;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预算分组ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDGROUPID")
      
    private String budgroupid;   
    
	/*
	 * 预算分组名称
	 */
    @Column(name="BUDGROUPNAME")
    @ValidateRule(dataType=9,label="预算分组名称",maxLength= 100,required=true)  
    private String budgroupname;   
    
	/*
	 * 预算分组描述
	 */
    @Column(name="BUDGROUPDESC")
    @ValidateRule(dataType=9,label="预算分组描述",maxLength= 128,required=false)  
    private String budgroupdesc;   
    
	/*
	 * 上级预算分组ID
	 */
    @Column(name="BUDUPGROUPID")
    @ValidateRule(dataType=9,label="上级预算分组ID",maxLength= 36,required=false)  
    private String budupgroupid;   
    
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
     *    获得预算项
     * @return 预算项 : Set<BudgetItem>
     */
    public Set<BudgetItem> getBudgetItem()
    {
		return this.budgetItem;
    }

    /**
     * 功能描述:
     *    设置预算项
     * @param budgetItem : Set<BudgetItem>
     */
    public void setBudgetItem(Set<BudgetItem> budgetItem)
    {
	    this.budgetItem = budgetItem;
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
     *    获得预算分组ID
     * @return 预算分组ID : String
     */
    public String getBudgroupid()
    {
		return this.budgroupid;
    }

    /**
     * 功能描述:
     *    设置预算分组ID
     * @param budgroupid : String
     */
    public void setBudgroupid(String budgroupid)
    {
	    this.budgroupid = budgroupid;
    }
    

    /**
     * 功能描述:
     *    获得预算分组名称
     * @return 预算分组名称 : String
     */
    public String getBudgroupname()
    {
		return this.budgroupname;
    }

    /**
     * 功能描述:
     *    设置预算分组名称
     * @param budgroupname : String
     */
    public void setBudgroupname(String budgroupname)
    {
	    this.budgroupname = budgroupname;
    }
    

    /**
     * 功能描述:
     *    获得预算分组描述
     * @return 预算分组描述 : String
     */
    public String getBudgroupdesc()
    {
		return this.budgroupdesc;
    }

    /**
     * 功能描述:
     *    设置预算分组描述
     * @param budgroupdesc : String
     */
    public void setBudgroupdesc(String budgroupdesc)
    {
	    this.budgroupdesc = budgroupdesc;
    }
    

    /**
     * 功能描述:
     *    获得上级预算分组ID
     * @return 上级预算分组ID : String
     */
    public String getBudupgroupid()
    {
		return this.budupgroupid;
    }

    /**
     * 功能描述:
     *    设置上级预算分组ID
     * @param budupgroupid : String
     */
    public void setBudupgroupid(String budupgroupid)
    {
	    this.budupgroupid = budupgroupid;
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
    public BudgetItemGroup()
    {
    	super();
    }
}
