/*
 * @(#)BudgetTemplate.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 12点13分10秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetTemplate.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;

/**
 * <pre>
 * 预算模版(BudgetTemplate)实体类
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
@Table(name = "YBUDTEM")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BudgetTemplate extends BaseObject
{
	// fields
	/*
	 * 模版预算项
	 */
	@OneToMany(mappedBy = "budgetTemplate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<BudgetTemplateItem> budgetTemplateItem;

	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 预算模版ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "BUDTEMID")
	private String budtemid;

	/*
	 * 预算模版名称
	 */
	@Column(name = "BUDTEMNAME")
	@ValidateRule(dataType = 9, label = "预算模版名称", maxLength = 100, required = true)
	private String budtemname;

	/*
	 * 预算模版类型
	 */
	@Column(name = "BUDTEMTYPE")
	@ValidateRule(dataType = 9, label = "预算模版类型", maxLength = 10, required = true)
	private String budtemtype;

	/*
	 * 业务对象
	 */
	@Column(name = "BOID")
	@ValidateRule(dataType = 9, label = "业务对象", maxLength = 12, required = true)
	private String boid;

	/*
	 * 预算控制周期
	 */
	@Column(name = "BUDCONCYCLE")
	@ValidateRule(dataType = 9, label = "预算控制周期", maxLength = 10, required = true)
	private String budconcycle;

	/*
	 * 创建人
	 */
	@Column(name = "CREATOR")
	@ValidateRule(dataType = 9, label = "创建人", maxLength = 36, required = false)
	private String creator;

	/*
	 * 创建日期
	 */
	@Column(name = "CREATETIME")
	@ValidateRule(dataType = 8, label = "创建日期", required = false)
	private String createtime;

	/*
	 * 最后修改者
	 */
	@Column(name = "LASTMODIFYER")
	@ValidateRule(dataType = 9, label = "最后修改者", maxLength = 36, required = false)
	private String lastmodifyer;

	/*
	 * 最后修改日期
	 */
	@Column(name = "LASTMODIFYTIME")
	@ValidateRule(dataType = 8, label = "最后修改日期", required = false)
	private String lastmodifytime;

	/*
	 * 预算分类
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "BUDCLASSID")
	@NotFound(action = NotFoundAction.IGNORE)
	private BudgetClass budgetClass;

	/**
	 * 功能描述: 获得模版预算项
	 * 
	 * @return 模版预算项 : Set<BudgetTemplateItem>
	 */
	public Set<BudgetTemplateItem> getBudgetTemplateItem()
	{
		return this.budgetTemplateItem;
	}

	/**
	 * 功能描述: 设置模版预算项
	 * 
	 * @param budgetTemplateItem
	 *            : Set<BudgetTemplateItem>
	 */
	public void setBudgetTemplateItem(Set<BudgetTemplateItem> budgetTemplateItem)
	{
		this.budgetTemplateItem = budgetTemplateItem;
	}

	/**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient()
	{
		return this.client;
	}

	/**
	 * 功能描述: 设置客户端
	 * 
	 * @param client
	 *            : String
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * 功能描述: 获得预算模版ID
	 * 
	 * @return 预算模版ID : String
	 */
	public String getBudtemid()
	{
		return this.budtemid;
	}

	/**
	 * 功能描述: 设置预算模版ID
	 * 
	 * @param budtemid
	 *            : String
	 */
	public void setBudtemid(String budtemid)
	{
		this.budtemid = budtemid;
	}

	/**
	 * 功能描述: 获得预算模版名称
	 * 
	 * @return 预算模版名称 : String
	 */
	public String getBudtemname()
	{
		return this.budtemname;
	}

	/**
	 * 功能描述: 设置预算模版名称
	 * 
	 * @param budtemname
	 *            : String
	 */
	public void setBudtemname(String budtemname)
	{
		this.budtemname = budtemname;
	}

	/**
	 * 功能描述: 获得预算模版类型
	 * 
	 * @return 预算模版类型 : String
	 */
	public String getBudtemtype()
	{
		return this.budtemtype;
	}

	/**
	 * 功能描述: 设置预算模版类型
	 * 
	 * @param budtemtype
	 *            : String
	 */
	public void setBudtemtype(String budtemtype)
	{
		this.budtemtype = budtemtype;
	}

	/**
	 * 功能描述: 获得业务对象
	 * 
	 * @return 业务对象 : String
	 */
	public String getBoid()
	{
		return this.boid;
	}

	/**
	 * 功能描述: 设置业务对象
	 * 
	 * @param boid
	 *            : String
	 */
	public void setBoid(String boid)
	{
		this.boid = boid;
	}

	/**
	 * 功能描述: 获得预算控制周期
	 * 
	 * @return 预算控制周期 : String
	 */
	public String getBudconcycle()
	{
		return this.budconcycle;
	}

	/**
	 * 功能描述: 设置预算控制周期
	 * 
	 * @param budconcycle
	 *            : String
	 */
	public void setBudconcycle(String budconcycle)
	{
		this.budconcycle = budconcycle;
	}

	/**
	 * 功能描述: 获得创建人
	 * 
	 * @return 创建人 : String
	 */
	public String getCreator()
	{
		return this.creator;
	}

	/**
	 * 功能描述: 设置创建人
	 * 
	 * @param creator
	 *            : String
	 */
	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	/**
	 * 功能描述: 获得创建日期
	 * 
	 * @return 创建日期 : String
	 */
	public String getCreatetime()
	{
		return DateUtils.toDisplayStr(this.createtime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置创建日期
	 * 
	 * @param createtime
	 *            : String
	 */
	public void setCreatetime(String createtime)
	{
		createtime = DateUtils.toStoreStr(createtime);
		this.createtime = createtime;
	}

	/**
	 * 功能描述: 获得最后修改者
	 * 
	 * @return 最后修改者 : String
	 */
	public String getLastmodifyer()
	{
		return this.lastmodifyer;
	}

	/**
	 * 功能描述: 设置最后修改者
	 * 
	 * @param lastmodifyer
	 *            : String
	 */
	public void setLastmodifyer(String lastmodifyer)
	{
		this.lastmodifyer = lastmodifyer;
	}

	/**
	 * 功能描述: 获得最后修改日期
	 * 
	 * @return 最后修改日期 : String
	 */
	public String getLastmodifytime()
	{
		return DateUtils.toDisplayStr(this.lastmodifytime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * 功能描述: 设置最后修改日期
	 * 
	 * @param lastmodifytime
	 *            : String
	 */
	public void setLastmodifytime(String lastmodifytime)
	{
		lastmodifytime = DateUtils.toStoreStr(lastmodifytime);
		this.lastmodifytime = lastmodifytime;
	}

	/**
	 * 功能描述: 获得预算分类
	 * 
	 * @return 预算分类 : BudgetClass
	 */
	public BudgetClass getBudgetClass()
	{
		return this.budgetClass;
	}

	/**
	 * 功能描述: 设置预算分类
	 * 
	 * @param budgetClass
	 *            : BudgetClass
	 */
	public void setBudgetClass(BudgetClass budgetClass)
	{
		this.budgetClass = budgetClass;
	}

	/**
	 * 默认构造器
	 */
	public BudgetTemplate()
	{
		super();
	}

	@Transient
	private List<BudgetTemplateItem> sortedItems;

	/**
	 * 返回按seq排序后BudgetTemplateItem列表
	 * 
	 * @return
	 */
	public List<BudgetTemplateItem> getSortedItems()
	{
		if (sortedItems == null && this.budgetTemplateItem != null)
		{
			BudgetTemplateItem[] items = this.budgetTemplateItem.toArray(new BudgetTemplateItem[budgetTemplateItem.size()]);
			Arrays.sort(items, new Comparator<BudgetTemplateItem>()
			{

				public int compare(BudgetTemplateItem o1, BudgetTemplateItem o2)
				{
					return o1.getSeq().compareTo(o2.getSeq());
				}
			});
			sortedItems = Arrays.asList(items);
		}
		return this.sortedItems;
	}
}
