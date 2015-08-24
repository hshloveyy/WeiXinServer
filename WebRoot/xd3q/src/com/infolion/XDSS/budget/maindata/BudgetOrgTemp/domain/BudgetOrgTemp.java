/*
 * @(#)BudgetOrgTemp.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分06秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain;

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
import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;

/**
 * <pre>
 * 预算组织模版(BudgetOrgTemp)实体类
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
@Table(name = "YBUDORGTEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudgetOrgTemp extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预算组织模版编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDORGTEMID")
      
    private String budorgtemid;   
    
	/*
	 * 预算模版
	 */
    @Column(name="BUDCLASSID")
    @ValidateRule(dataType=9,label="预算模版",maxLength= 36,required=true)  
    private String budclassid;   
    
	public String getBudclassid() {
		return budclassid;
	}

	public void setBudclassid(String budclassid) {
		this.budclassid = budclassid;
	}


	/*
	 * 编制起始时间
	 */
    @Column(name="WEASTARTDATE")
    @ValidateRule(dataType=8,label="编制起始时间",required=true)  
    private String weastartdate;   
    
	/*
	 * 编制结束时间
	 */
    @Column(name="WEAENTDATE")
    @ValidateRule(dataType=8,label="编制结束时间",required=true)  
    private String weaentdate;   
    
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
	 * 预算组织
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BUDORGID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BudgetOrganization budgetOrganization;   
    

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
     *    获得预算组织模版编号
     * @return 预算组织模版编号 : String
     */
    public String getBudorgtemid()
    {
		return this.budorgtemid;
    }

    /**
     * 功能描述:
     *    设置预算组织模版编号
     * @param budorgtemid : String
     */
    public void setBudorgtemid(String budorgtemid)
    {
	    this.budorgtemid = budorgtemid;
    }
    

    /**
     * 功能描述:
     *    获得编制起始时间
     * @return 编制起始时间 : String
     */
    public String getWeastartdate()
    {
    	return DateUtils.toDisplayStr(this.weastartdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置编制起始时间
     * @param weastartdate : String
     */
    public void setWeastartdate(String weastartdate)
    {
    	weastartdate = DateUtils.toStoreStr(weastartdate);
	    this.weastartdate = weastartdate;
    }
    

    /**
     * 功能描述:
     *    获得编制结束时间
     * @return 编制结束时间 : String
     */
    public String getWeaentdate()
    {
    	return DateUtils.toDisplayStr(this.weaentdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置编制结束时间
     * @param weaentdate : String
     */
    public void setWeaentdate(String weaentdate)
    {
    	weaentdate = DateUtils.toStoreStr(weaentdate);
	    this.weaentdate = weaentdate;
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
     *    获得预算组织
     * @return 预算组织 : BudgetOrganization
     */
    public BudgetOrganization getBudgetOrganization()
    {
		return this.budgetOrganization;
    }

    /**
     * 功能描述:
     *    设置预算组织
     * @param budgetOrganization : BudgetOrganization
     */
    public void setBudgetOrganization(BudgetOrganization budgetOrganization)
    {
	    this.budgetOrganization = budgetOrganization;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BudgetOrgTemp()
    {
    	super();
    }
}
