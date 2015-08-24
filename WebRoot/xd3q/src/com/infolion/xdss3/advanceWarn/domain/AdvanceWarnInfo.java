/*
 * @(#)AdvanceWarnInfo.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 14点38分10秒
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


/**
 * <pre>
 * 业务预警信息(AdvanceWarnInfo)实体类
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
@Table(name = "YADVAWARNINFO")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AdvanceWarnInfo extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预警信息编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="WARNINFOID")
      
    private String warninfoid;   
    
	/*
	 * 预警对像编号
	 */
    @Column(name="WARNID")
    @ValidateRule(dataType=9,label="预警对像编号",maxLength= 36,required=false)  
    private String warnid;   
    
	/*
	 * 业务对象
	 */
    @Column(name="BOID")
    @ValidateRule(dataType=9,label="业务对象",maxLength= 12,required=false)  
    private String boid;   
    
	/*
	 * 接收用户
	 */
    @Column(name="RECEIVEUSERID")
    @ValidateRule(dataType=9,label="接收用户",maxLength= 36,required=false)  
    private String receiveuserid;   
    
	/*
	 * URL地址
	 */
    @Column(name="URL")
    @ValidateRule(dataType=9,label="URL地址",maxLength= 255,required=false)  
    private String url;   
    
	/*
	 * 业务对像编号
	 */
    @Column(name="OBJECTINSTANCEID")
    @ValidateRule(dataType=9,label="业务对像编号",maxLength= 36,required=false)  
    private String objectinstanceid;   
    
	/*
	 * 预警提示信息
	 */
    @Column(name="WARNINFO")
    @ValidateRule(dataType=9,label="预警提示信息",maxLength= 255,required=false)  
    private String warninfo;   
    
	/*
	 * 关闭人
	 */
    @Column(name="CLOSEUSERID")
    @ValidateRule(dataType=9,label="关闭人",maxLength= 36,required=false)  
    private String closeuserid;   
    
	/*
	 * 关闭时间
	 */
    @Column(name="CLOSETIME")
    @ValidateRule(dataType=9,label="关闭时间",maxLength= 14,required=false)  
    private String closetime;   
    
	/*
	 * 再次提醒天数
	 */
    @Column(name="AGAINWARNTIME")
    @ValidateRule(dataType=9,label="再次提醒天数",maxLength= 3,required=false)  
    private String againwarntime;   
    
	/*
	 * 状态
	 */
    @Column(name="STATE")
    @ValidateRule(dataType=9,label="状态",maxLength= 2,required=false)  
    private String state;   
    
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
    @ValidateRule(dataType=9,label="创建日期",maxLength= 14,required=false)  
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
    @ValidateRule(dataType=9,label="最后修改日期",maxLength= 14,required=false)  
    private String lastmodifytime;   
    

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
     *    获得预警信息编号
     * @return 预警信息编号 : String
     */
    public String getWarninfoid()
    {
		return this.warninfoid;
    }

    /**
     * 功能描述:
     *    设置预警信息编号
     * @param warninfoid : String
     */
    public void setWarninfoid(String warninfoid)
    {
	    this.warninfoid = warninfoid;
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
     *    获得接收用户
     * @return 接收用户 : String
     */
    public String getReceiveuserid()
    {
		return this.receiveuserid;
    }

    /**
     * 功能描述:
     *    设置接收用户
     * @param receiveuserid : String
     */
    public void setReceiveuserid(String receiveuserid)
    {
	    this.receiveuserid = receiveuserid;
    }
    

    /**
     * 功能描述:
     *    获得URL地址
     * @return URL地址 : String
     */
    public String getUrl()
    {
		return this.url;
    }

    /**
     * 功能描述:
     *    设置URL地址
     * @param url : String
     */
    public void setUrl(String url)
    {
	    this.url = url;
    }
    

    /**
     * 功能描述:
     *    获得业务对像编号
     * @return 业务对像编号 : String
     */
    public String getObjectinstanceid()
    {
		return this.objectinstanceid;
    }

    /**
     * 功能描述:
     *    设置业务对像编号
     * @param objectinstanceid : String
     */
    public void setObjectinstanceid(String objectinstanceid)
    {
	    this.objectinstanceid = objectinstanceid;
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
     *    获得关闭人
     * @return 关闭人 : String
     */
    public String getCloseuserid()
    {
		return this.closeuserid;
    }

    /**
     * 功能描述:
     *    设置关闭人
     * @param closeuserid : String
     */
    public void setCloseuserid(String closeuserid)
    {
	    this.closeuserid = closeuserid;
    }
    

    /**
     * 功能描述:
     *    获得关闭时间
     * @return 关闭时间 : String
     */
    public String getClosetime()
    {
		return this.closetime;
    }

    /**
     * 功能描述:
     *    设置关闭时间
     * @param closetime : String
     */
    public void setClosetime(String closetime)
    {
	    this.closetime = closetime;
    }
    

    /**
     * 功能描述:
     *    获得再次提醒天数
     * @return 再次提醒天数 : String
     */
    public String getAgainwarntime()
    {
		return this.againwarntime;
    }

    /**
     * 功能描述:
     *    设置再次提醒天数
     * @param againwarntime : String
     */
    public void setAgainwarntime(String againwarntime)
    {
	    this.againwarntime = againwarntime;
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
		return this.createtime;
    }

    /**
     * 功能描述:
     *    设置创建日期
     * @param createtime : String
     */
    public void setCreatetime(String createtime)
    {
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
		return this.lastmodifytime;
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifytime : String
     */
    public void setLastmodifytime(String lastmodifytime)
    {
	    this.lastmodifytime = lastmodifytime;
    }
    
    
	/**
	 *  默认构造器
	 */
    public AdvanceWarnInfo()
    {
    	super();
    }
}
