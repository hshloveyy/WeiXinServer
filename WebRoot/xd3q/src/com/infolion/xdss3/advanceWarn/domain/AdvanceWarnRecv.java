/*
 * @(#)AdvanceWarnRecv.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 10点00分52秒
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
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;

/**
 * <pre>
 * 预警对像接收人(AdvanceWarnRecv)实体类
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
@Table(name = "YADVAWARNRECE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AdvanceWarnRecv extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 预警接收人配置编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="RECEIVEID")
      
    private String receiveid;   
    
	/*
	 * 实例创建部门
	 */
    @Column(name="CREATEDEPTID")
    @ValidateRule(dataType=9,label="实例创建部门",maxLength= 36,required=true)  
    private String createdeptid;   
    
	/*
	 * 接收用户
	 */
    @Column(name="RECEIVEUSERID")
    @ValidateRule(dataType=9,label="接收用户",maxLength= 36,required=true)  
    private String receiveuserid;   
    
	/*
	 * 接收方式
	 */
    @Column(name="RECEIVETYPE")
    @ValidateRule(dataType=9,label="接收方式",maxLength= 2,required=true)  
    private String receivetype;   
    
	/*
	 * 接收方式地址
	 */
    @Column(name="RECEIVEADDR")
    @ValidateRule(dataType=9,label="接收方式地址",maxLength= 128,required=false)  
    private String receiveaddr;   
    
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
	 * 预警对像配置
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="WARNID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private AdvanceWarnObject advanceWarnObject;   
    

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
     *    获得预警接收人配置编号
     * @return 预警接收人配置编号 : String
     */
    public String getReceiveid()
    {
		return this.receiveid;
    }

    /**
     * 功能描述:
     *    设置预警接收人配置编号
     * @param receiveid : String
     */
    public void setReceiveid(String receiveid)
    {
	    this.receiveid = receiveid;
    }
    

    /**
     * 功能描述:
     *    获得实例创建部门
     * @return 实例创建部门 : String
     */
    public String getCreatedeptid()
    {
		return this.createdeptid;
    }

    /**
     * 功能描述:
     *    设置实例创建部门
     * @param createdeptid : String
     */
    public void setCreatedeptid(String createdeptid)
    {
	    this.createdeptid = createdeptid;
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
     *    获得接收方式
     * @return 接收方式 : String
     */
    public String getReceivetype()
    {
		return this.receivetype;
    }

    /**
     * 功能描述:
     *    设置接收方式
     * @param receivetype : String
     */
    public void setReceivetype(String receivetype)
    {
	    this.receivetype = receivetype;
    }
    

    /**
     * 功能描述:
     *    获得接收方式地址
     * @return 接收方式地址 : String
     */
    public String getReceiveaddr()
    {
		return this.receiveaddr;
    }

    /**
     * 功能描述:
     *    设置接收方式地址
     * @param receiveaddr : String
     */
    public void setReceiveaddr(String receiveaddr)
    {
	    this.receiveaddr = receiveaddr;
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
     *    获得预警对像配置
     * @return 预警对像配置 : AdvanceWarnObject
     */
    public AdvanceWarnObject getAdvanceWarnObject()
    {
		return this.advanceWarnObject;
    }

    /**
     * 功能描述:
     *    设置预警对像配置
     * @param advanceWarnObject : AdvanceWarnObject
     */
    public void setAdvanceWarnObject(AdvanceWarnObject advanceWarnObject)
    {
	    this.advanceWarnObject = advanceWarnObject;
    }
    
    
	/**
	 *  默认构造器
	 */
    public AdvanceWarnRecv()
    {
    	super();
    }
}
