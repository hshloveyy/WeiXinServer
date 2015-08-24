/*
 * @(#)BudgetSort.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月04日 13点22分31秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetSort.domain;

import java.util.HashMap;
import java.util.Map;
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
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;

/**
 * <pre>
 * 预算分类(BudgetSort)实体类
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

public class BudgetSort extends BaseObject
{
	//fields
	/*
	 * 预算模版
	 */
    //@OneToMany(mappedBy="budgetSort",cascade={CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.EAGER)
    //@NotFound(action=NotFoundAction.IGNORE)
      
    private Set<BudgetTemplate> budgetTemplate;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预算分类ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDCLASSID")
      
    private String budsortid;   
    
	/*
	 * 预算分类名称
	 */
    @Column(name="BUDCLASSNAME")
    @ValidateRule(dataType=9,label="预算分类名称",maxLength= 100,required=true)  
    private String budsortname;   
    
	/*
	 * 预算分类描述
	 */
    @Column(name="BUDCLASSDESC")
    @ValidateRule(dataType=9,label="预算分类描述",maxLength= 128,required=false)  
    private String budsortdesc;   
    
	/*
	 * 业务对象
	 */
    @Column(name="BOID")
    @ValidateRule(dataType=9,label="业务对象",maxLength= 12,required=false)  
    private String boid;   
    
	/*
	 * 上级预算分类ID
	 */
    @Column(name="BUDUPCLASSID")
    @ValidateRule(dataType=9,label="上级预算分类ID",maxLength= 36,required=true)  
    private String budupsortid;   
    
	/*
	 * 是否修改
	 */
    /*@Column(name="ISCHANGE")
    @ValidateRule(dataType=9,label="是否修改",maxLength= 10,required=true)  
    private String ischange;*/   
    
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
     *    获得预算模版
     * @return 预算模版 : Set<BudgetTemplate>
     */
    public Set<BudgetTemplate> getBudgetTemplate()
    {
		return this.budgetTemplate;
    }

    /**
     * 功能描述:
     *    设置预算模版
     * @param budgetTemplate : Set<BudgetTemplate>
     */
    public void setBudgetTemplate(Set<BudgetTemplate> budgetTemplate)
    {
	    this.budgetTemplate = budgetTemplate;
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
     *    获得预算分类ID
     * @return 预算分类ID : String
     */
    public String getBudsortid()
    {
		return this.budsortid;
    }

    /**
     * 功能描述:
     *    设置预算分类ID
     * @param budsortid : String
     */
    public void setBudsortid(String budsortid)
    {
	    this.budsortid = budsortid;
    }
    

    /**
     * 功能描述:
     *    获得预算分类名称
     * @return 预算分类名称 : String
     */
    public String getBudsortname()
    {
		return this.budsortname;
    }

    /**
     * 功能描述:
     *    设置预算分类名称
     * @param budsortname : String
     */
    public void setBudsortname(String budsortname)
    {
	    this.budsortname = budsortname;
    }
    

    /**
     * 功能描述:
     *    获得预算分类描述
     * @return 预算分类描述 : String
     */
    public String getBudsortdesc()
    {
		return this.budsortdesc;
    }

    /**
     * 功能描述:
     *    设置预算分类描述
     * @param budsortdesc : String
     */
    public void setBudsortdesc(String budsortdesc)
    {
	    this.budsortdesc = budsortdesc;
    }
    

    /**
     * 功能描述:
     *    获得业务对象
     * @return 业务对象 : String
     */
    public String getBoid()
    {
		return this.boid;
    }

    /**
     * 功能描述:
     *    设置业务对象
     * @param boid : String
     */
    public void setBoid(String boid)
    {
	    this.boid = boid;
    }
    

    /**
     * 功能描述:
     *    获得上级预算分类ID
     * @return 上级预算分类ID : String
     */
    public String getBudupsortid()
    {
		return this.budupsortid;
    }

    /**
     * 功能描述:
     *    设置上级预算分类ID
     * @param budupsortid : String
     */
    public void setBudupsortid(String budupsortid)
    {
	    this.budupsortid = budupsortid;
    }
    

    /**
     * 功能描述:
     *    获得是否修改
     * @return 是否修改 : String
     */
    /*public String getIschange()
    {
		return this.ischange;
    }*/

    /**
     * 功能描述:
     *    设置是否修改
     * @param ischange : String
     */
    /*public void setIschange(String ischange)
    {
	    this.ischange = ischange;
    }*/
    

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
    public BudgetSort()
    {
    	super();
    }

	@Transient
	private Map<String, BudgetTemplate> budgetTemps;

	/**
	 * 取得业务对象名与BudgetTemplate映射的Map
	 * 
	 * @return
	 */
	/*public Map<String, BudgetTemplate> getBudgetTemps()
	{
		if (budgetTemps == null)
		{
			Map<String, BudgetTemplate> map = new HashMap<String, BudgetTemplate>();
			for (BudgetTemplate bt : this.getBudgetTemplate())
			{
				map.put(SysCachePoolUtils.getBoNameById(bt.getBoid()), bt);
			}
			budgetTemps = map;
		}
		return budgetTemps;
	}*/
}
