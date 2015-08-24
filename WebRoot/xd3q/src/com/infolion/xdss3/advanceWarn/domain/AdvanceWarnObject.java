/*
 * @(#)AdvanceWarnObject.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 10点00分50秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.domain;

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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;

/**
 * <pre>
 * 预警对像配置(AdvanceWarnObject)实体类
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
@Table(name = "YADVAWARNOBJE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AdvanceWarnObject extends BaseObject
{
	//fields
	/*
	 * 预警对像接收人
	 */
    @OneToMany(mappedBy="advanceWarnObject",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<AdvanceWarnRecv> advanceWarnReceiver;   
    
	/*
	 * 预警对像条件
	 */
    @OneToMany(mappedBy="advanceWarnObject",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<AdvanceWarnCond> advanceWarnCondition;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预警对像编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="WARNID")
      
    private String warnid;   
    
	/*
	 * 业务对象
	 */
    @Column(name="BOID")
    @ValidateRule(dataType=9,label="业务对象",maxLength= 70,required=true)  
    private String boid;   
    
	/*
	 * 预警字段
	 */
    @Column(name="FIELDCODE")
    @ValidateRule(dataType=9,label="预警字段",maxLength= 128,required=true)  
    private String fieldcode;   
    
	/*
	 * 预警规则
	 */
    @Column(name="WARNROLE")
    @ValidateRule(dataType=9,label="预警规则",maxLength= 5,required=true)  
    private String warnrole;   
    
	/*
	 * 预警值
	 */
    @Column(name="WARNVALUE")
    @ValidateRule(dataType=9,label="预警值",maxLength= 10,required=true)  
    private String warnvalue;   
    
	/*
	 * 处理函数
	 */
    @Column(name="DEALFUNC")
    @ValidateRule(dataType=9,label="处理函数",maxLength= 128,required=false)  
    private String dealfunc;   
    
	/*
	 * 关键属性
	 */
    @Column(name="PRIMARYKEY")
    @ValidateRule(dataType=9,label="关键属性",maxLength= 128,required=true)  
    private String primarykey;   
    
	/*
	 * 状态
	 */
    @Column(name="STATE")
    @ValidateRule(dataType=9,label="状态",maxLength= 2,required=true)  
    private String state;   
    
	/*
	 * 查看地址
	 */
    @Column(name="VIEWURL")
    @ValidateRule(dataType=9,label="查看地址",maxLength= 128,required=true)  
    private String viewurl;   
    
	/*
	 * 清除方式
	 */
    @Column(name="CLEARTYPE")
    @ValidateRule(dataType=9,label="清除方式",maxLength= 2,required=true)  
    private String cleartype;   
    
	/*
	 * 清除人
	 */
    @Column(name="CLEARUSER")
    @ValidateRule(dataType=9,label="清除人",maxLength= 128,required=false)  
    private String clearuser;   
    
	/*
	 * 预警提示信息
	 */
    @Column(name="WARNINFO")
    @ValidateRule(dataType=9,label="预警提示信息",maxLength= 255,required=true)  
    private String warninfo;   
    
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
     *    获得预警对像接收人
     * @return 预警对像接收人 : Set<AdvanceWarnRecv>
     */
    public Set<AdvanceWarnRecv> getAdvanceWarnReceiver()
    {
		return this.advanceWarnReceiver;
    }

    /**
     * 功能描述:
     *    设置预警对像接收人
     * @param advanceWarnReceiver : Set<AdvanceWarnRecv>
     */
    public void setAdvanceWarnReceiver(Set<AdvanceWarnRecv> advanceWarnReceiver)
    {
	    this.advanceWarnReceiver = advanceWarnReceiver;
    }
    

    /**
     * 功能描述:
     *    获得预警对像条件
     * @return 预警对像条件 : Set<AdvanceWarnCond>
     */
    public Set<AdvanceWarnCond> getAdvanceWarnCondition()
    {
		return this.advanceWarnCondition;
    }

    /**
     * 功能描述:
     *    设置预警对像条件
     * @param advanceWarnCondition : Set<AdvanceWarnCond>
     */
    public void setAdvanceWarnCondition(Set<AdvanceWarnCond> advanceWarnCondition)
    {
	    this.advanceWarnCondition = advanceWarnCondition;
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
     *    获得预警对像编号
     * @return 预警对像编号 : String
     */
    public String getWarnid()
    {
		return this.warnid;
    }

    /**
     * 功能描述:
     *    设置预警对像编号
     * @param warnid : String
     */
    public void setWarnid(String warnid)
    {
	    this.warnid = warnid;
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
     *    获得预警字段
     * @return 预警字段 : String
     */
    public String getFieldcode()
    {
		return this.fieldcode;
    }

    /**
     * 功能描述:
     *    设置预警字段
     * @param fieldcode : String
     */
    public void setFieldcode(String fieldcode)
    {
	    this.fieldcode = fieldcode;
    }
    

    /**
     * 功能描述:
     *    获得预警规则
     * @return 预警规则 : String
     */
    public String getWarnrole()
    {
		return this.warnrole;
    }

    /**
     * 功能描述:
     *    设置预警规则
     * @param warnrole : String
     */
    public void setWarnrole(String warnrole)
    {
	    this.warnrole = warnrole;
    }
    

    /**
     * 功能描述:
     *    获得预警值
     * @return 预警值 : String
     */
    public String getWarnvalue()
    {
		return this.warnvalue;
    }

    /**
     * 功能描述:
     *    设置预警值
     * @param warnvalue : String
     */
    public void setWarnvalue(String warnvalue)
    {
	    this.warnvalue = warnvalue;
    }
    

    /**
     * 功能描述:
     *    获得处理函数
     * @return 处理函数 : String
     */
    public String getDealfunc()
    {
		return this.dealfunc;
    }

    /**
     * 功能描述:
     *    设置处理函数
     * @param dealfunc : String
     */
    public void setDealfunc(String dealfunc)
    {
	    this.dealfunc = dealfunc;
    }
    

    /**
     * 功能描述:
     *    获得关键属性
     * @return 关键属性 : String
     */
    public String getPrimarykey()
    {
		return this.primarykey;
    }

    /**
     * 功能描述:
     *    设置关键属性
     * @param primarykey : String
     */
    public void setPrimarykey(String primarykey)
    {
	    this.primarykey = primarykey;
    }
    

    /**
     * 功能描述:
     *    获得状态
     * @return 状态 : String
     */
    public String getState()
    {
		return this.state;
    }

    /**
     * 功能描述:
     *    设置状态
     * @param state : String
     */
    public void setState(String state)
    {
	    this.state = state;
    }
    

    /**
     * 功能描述:
     *    获得查看地址
     * @return 查看地址 : String
     */
    public String getViewurl()
    {
		return this.viewurl;
    }

    /**
     * 功能描述:
     *    设置查看地址
     * @param viewurl : String
     */
    public void setViewurl(String viewurl)
    {
	    this.viewurl = viewurl;
    }
    

    /**
     * 功能描述:
     *    获得清除方式
     * @return 清除方式 : String
     */
    public String getCleartype()
    {
		return this.cleartype;
    }

    /**
     * 功能描述:
     *    设置清除方式
     * @param cleartype : String
     */
    public void setCleartype(String cleartype)
    {
	    this.cleartype = cleartype;
    }
    

    /**
     * 功能描述:
     *    获得清除人
     * @return 清除人 : String
     */
    public String getClearuser()
    {
		return this.clearuser;
    }

    /**
     * 功能描述:
     *    设置清除人
     * @param clearuser : String
     */
    public void setClearuser(String clearuser)
    {
	    this.clearuser = clearuser;
    }
    

    /**
     * 功能描述:
     *    获得预警提示信息
     * @return 预警提示信息 : String
     */
    public String getWarninfo()
    {
		return this.warninfo;
    }

    /**
     * 功能描述:
     *    设置预警提示信息
     * @param warninfo : String
     */
    public void setWarninfo(String warninfo)
    {
	    this.warninfo = warninfo;
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
    public AdvanceWarnObject()
    {
    	super();
    }
}
