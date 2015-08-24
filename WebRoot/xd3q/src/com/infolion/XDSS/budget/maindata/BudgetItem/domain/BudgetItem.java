/*
 * @(#)BudgetItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 17点03分36秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetItem.domain;

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
import com.infolion.XDSS.budget.maindata.BudgetItemGroup.domain.BudgetItemGroup;

/**
 * <pre>
 * 预算项(BudgetItem)实体类
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
@Table(name = "YBUDITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudgetItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预算项ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDITEMID")
      
    private String buditemid;   
    
	/*
	 * 预算项名称
	 */
    @Column(name="BUDITEMNAME")
    @ValidateRule(dataType=9,label="预算项名称",maxLength= 100,required=true)  
    private String buditemname;   
    
	/*
	 * 预算指标类型
	 */
    @Column(name="BUDGUIDETYPE")
    @ValidateRule(dataType=9,label="预算指标类型",maxLength= 10,required=true)  
    private String budguidetype;   
    
	/*
	 * 预算经营指标
	 */
    @Column(name="BUDFAREGUIDE")
    @ValidateRule(dataType=9,label="预算经营指标",maxLength= 100,required=false)  
    private String budfareguide;   
    
	/*
	 * 预算编制说明
	 */
    @Column(name="WEADESC")
    @ValidateRule(dataType=9,label="预算编制说明",maxLength= 130,required=false)  
    private String weadesc;   
    
	/*
	 * 预算项类型
	 */
    @Column(name="BUDITEMTYPE")
    @ValidateRule(dataType=9,label="预算项类型",maxLength= 10,required=true)  
    private String buditemtype;   
    
	/*
	 * 上级预算项编号
	 */
    @Column(name="BUDUPITEMID")
    @ValidateRule(dataType=9,label="上级预算项编号",maxLength= 36,required=false)  
    private String budupitemid;   
    
	/*
	 * 对应年预算项ID
	 */
    @Column(name="YEARITEMID")
    @ValidateRule(dataType=9,label="对应年预算项ID",maxLength= 36,required=false)  
    private String yearitemid;   
    
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
	 * 预算项分组
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BUDGROUPID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BudgetItemGroup budgetItemGroup;   
    

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
     *    获得预算项ID
     * @return 预算项ID : String
     */
    public String getBuditemid()
    {
		return this.buditemid;
    }

    /**
     * 功能描述:
     *    设置预算项ID
     * @param buditemid : String
     */
    public void setBuditemid(String buditemid)
    {
	    this.buditemid = buditemid;
    }
    

    /**
     * 功能描述:
     *    获得预算项名称
     * @return 预算项名称 : String
     */
    public String getBuditemname()
    {
		return this.buditemname;
    }

    /**
     * 功能描述:
     *    设置预算项名称
     * @param buditemname : String
     */
    public void setBuditemname(String buditemname)
    {
	    this.buditemname = buditemname;
    }
    

    /**
     * 功能描述:
     *    获得预算指标类型
     * @return 预算指标类型 : String
     */
    public String getBudguidetype()
    {
		return this.budguidetype;
    }

    /**
     * 功能描述:
     *    设置预算指标类型
     * @param budguidetype : String
     */
    public void setBudguidetype(String budguidetype)
    {
	    this.budguidetype = budguidetype;
    }
    

    /**
     * 功能描述:
     *    获得预算经营指标
     * @return 预算经营指标 : String
     */
    public String getBudfareguide()
    {
		return this.budfareguide;
    }

    /**
     * 功能描述:
     *    设置预算经营指标
     * @param budfareguide : String
     */
    public void setBudfareguide(String budfareguide)
    {
	    this.budfareguide = budfareguide;
    }
    

    /**
     * 功能描述:
     *    获得预算编制说明
     * @return 预算编制说明 : String
     */
    public String getWeadesc()
    {
		return this.weadesc;
    }

    /**
     * 功能描述:
     *    设置预算编制说明
     * @param weadesc : String
     */
    public void setWeadesc(String weadesc)
    {
	    this.weadesc = weadesc;
    }
    

    /**
     * 功能描述:
     *    获得预算项类型
     * @return 预算项类型 : String
     */
    public String getBuditemtype()
    {
		return this.buditemtype;
    }

    /**
     * 功能描述:
     *    设置预算项类型
     * @param buditemtype : String
     */
    public void setBuditemtype(String buditemtype)
    {
	    this.buditemtype = buditemtype;
    }
    

    /**
     * 功能描述:
     *    获得上级预算项编号
     * @return 上级预算项编号 : String
     */
    public String getBudupitemid()
    {
		return this.budupitemid;
    }

    /**
     * 功能描述:
     *    设置上级预算项编号
     * @param budupitemid : String
     */
    public void setBudupitemid(String budupitemid)
    {
	    this.budupitemid = budupitemid;
    }
    

    /**
     * 功能描述:
     *    获得对应年预算项ID
     * @return 对应年预算项ID : String
     */
    public String getYearitemid()
    {
		return this.yearitemid;
    }

    /**
     * 功能描述:
     *    设置对应年预算项ID
     * @param yearitemid : String
     */
    public void setYearitemid(String yearitemid)
    {
	    this.yearitemid = yearitemid;
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
     *    获得预算项分组
     * @return 预算项分组 : BudgetItemGroup
     */
    public BudgetItemGroup getBudgetItemGroup()
    {
		return this.budgetItemGroup;
    }

    /**
     * 功能描述:
     *    设置预算项分组
     * @param budgetItemGroup : BudgetItemGroup
     */
    public void setBudgetItemGroup(BudgetItemGroup budgetItemGroup)
    {
	    this.budgetItemGroup = budgetItemGroup;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BudgetItem()
    {
    	super();
    }
}
