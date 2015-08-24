/*
 * @(#)BudgetTemplateItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 09点37分58秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain;

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
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.XDSS.budget.maindata.BudgetItem.domain.BudgetItem;
import com.infolion.XDSS.budget.maindata.BudgetItem.service.BudgetItemService;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;

/**
 * <pre>
 * 模版预算项(BudgetTemplateItem)实体类
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
@Table(name = "YBUDTEMITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudgetTemplateItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 模版预算项ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDTEMITEMID")
      
    private String budtemitemid;   
    
	/*
	 * 预算项ID
	 */
    @Column(name="BUDITEMID")
    @ValidateRule(dataType=9,label="预算项ID",maxLength= 36,required=true)  
    private String buditemid;   
    
	/*
	 * 序号
	 */
    @Column(name="SEQ")
    @ValidateRule(dataType=9,label="序号",maxLength= 5,required=false)  
    private String seq;   
    
	/*
	 * 对应会计科目
	 */
    @Column(name="SUBJECT")
    @ValidateRule(dataType=9,label="对应会计科目",maxLength= 36,required=false)  
    private String subject;   
    
	/*
	 * 预算控制周期
	 */
    @Column(name="BUDCONCYCLE")
    @ValidateRule(dataType=9,label="预算控制周期",maxLength= 10,required=false)  
    private String budconcycle;   
    
	/*
	 * 状态
	 */
    @Column(name="STATUS")
    @ValidateRule(dataType=9,label="状态",maxLength= 1,required=false)  
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
    
	/*
	 * 预算模版
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BUDTEMID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BudgetTemplate budgetTemplate;   
    

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
     *    获得模版预算项ID
     * @return 模版预算项ID : String
     */
    public String getBudtemitemid()
    {
		return this.budtemitemid;
    }

    /**
     * 功能描述:
     *    设置模版预算项ID
     * @param budtemitemid : String
     */
    public void setBudtemitemid(String budtemitemid)
    {
	    this.budtemitemid = budtemitemid;
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
     *    获得序号
     * @return 序号 : String
     */
    public String getSeq()
    {
		return this.seq;
    }

    /**
     * 功能描述:
     *    设置序号
     * @param seq : String
     */
    public void setSeq(String seq)
    {
	    this.seq = seq;
    }
    

    /**
     * 功能描述:
     *    获得对应会计科目
     * @return 对应会计科目 : String
     */
    public String getSubject()
    {
		return this.subject;
    }

    /**
     * 功能描述:
     *    设置对应会计科目
     * @param subject : String
     */
    public void setSubject(String subject)
    {
	    this.subject = subject;
    }
    

    /**
     * 功能描述:
     *    获得预算控制周期
     * @return 预算控制周期 : String
     */
    public String getBudconcycle()
    {
		return this.budconcycle;
    }

    /**
     * 功能描述:
     *    设置预算控制周期
     * @param budconcycle : String
     */
    public void setBudconcycle(String budconcycle)
    {
	    this.budconcycle = budconcycle;
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
     * 功能描述:
     *    获得预算模版
     * @return 预算模版 : BudgetTemplate
     */
    public BudgetTemplate getBudgetTemplate()
    {
		return this.budgetTemplate;
    }

    /**
     * 功能描述:
     *    设置预算模版
     * @param budgetTemplate : BudgetTemplate
     */
    public void setBudgetTemplate(BudgetTemplate budgetTemplate)
    {
	    this.budgetTemplate = budgetTemplate;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BudgetTemplateItem()
    {
    	super();
    }

	@Transient
	private BudgetItem budgetItem;

	/**
	 * 取得关联的预算项
	 * 
	 * @return
	 */
	public BudgetItem getBudgetItem()
	{
		if (this.budgetItem == null)
		{
			BudgetItemService budgetItemService = (BudgetItemService) EasyApplicationContextUtils.getBeanByName("budgetItemService");
			this.budgetItem = budgetItemService._get(this.buditemid);
		}
		return this.budgetItem;
	}
}
