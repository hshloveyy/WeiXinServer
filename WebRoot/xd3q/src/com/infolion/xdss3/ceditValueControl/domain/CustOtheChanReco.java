/*
 * @(#)CustOtheChanReco.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月28日 13点31分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.domain;

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
import java.math.BigDecimal;

/**
 * <pre>
 * 客户代垫费用查询(CustOtheChanReco)实体类
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
@Table(name = "YOTHECHANRECO")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustOtheChanReco extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 代垫费用变更记录ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CHANGEID")
      
    private String changeid;   
    
	/*
	 * 客户名称
	 */
    @Column(name="USERID")
    @ValidateRule(dataType=9,label="客户名称",maxLength= 36,required=false)  
    private String userid;   
    
	/*
	 * 授信额度类型
	 */
    @Column(name="USERTYPE")
    @ValidateRule(dataType=9,label="授信额度类型",maxLength= 1,required=false)  
    private String usertype;   
    
	/*
	 * 老数据
	 */
    @Column(name="OLDVALUE")
    @ValidateRule(dataType=0,label="老数据",required=false)  
    private BigDecimal oldvalue;   
    
	/*
	 * 新数据
	 */
    @Column(name="NEWVALUE")
    @ValidateRule(dataType=0,label="新数据",required=false)  
    private BigDecimal newvalue;   
    
	/*
	 * 备注
	 */
    @Column(name="REMARK")
    @ValidateRule(dataType=9,label="备注",maxLength= 255,required=false)  
    private String remark;   
    
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
	 * 项目名称
	 */
    @Column(name="PROJECTID")
    @ValidateRule(dataType=9,label="项目名称",maxLength= 36,required=false)  
    private String projectid;   
    

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
     *    获得代垫费用变更记录ID
     * @return 代垫费用变更记录ID : String
     */
    public String getChangeid()
    {
		return this.changeid;
    }

    /**
     * 功能描述:
     *    设置代垫费用变更记录ID
     * @param changeid : String
     */
    public void setChangeid(String changeid)
    {
	    this.changeid = changeid;
    }
    

    /**
     * 功能描述:
     *    获得客户名称
     * @return 客户名称 : String
     */
    public String getUserid()
    {
		return this.userid;
    }

    /**
     * 功能描述:
     *    设置客户名称
     * @param userid : String
     */
    public void setUserid(String userid)
    {
	    this.userid = userid;
    }
    

    /**
     * 功能描述:
     *    获得用户类型
     * @return 用户类型 : String
     */
    public String getUsertype()
    {
		return this.usertype;
    }

    /**
     * 功能描述:
     *    设置用户类型
     * @param usertype : String
     */
    public void setUsertype(String usertype)
    {
	    this.usertype = usertype;
    }
    

    /**
     * 功能描述:
     *    获得老数据
     * @return 老数据 : BigDecimal
     */
    public BigDecimal getOldvalue()
    {
		return this.oldvalue;
    }

    /**
     * 功能描述:
     *    设置老数据
     * @param oldvalue : BigDecimal
     */
    public void setOldvalue(BigDecimal oldvalue)
    {
	    this.oldvalue = oldvalue;
    }
    

    /**
     * 功能描述:
     *    获得新数据
     * @return 新数据 : BigDecimal
     */
    public BigDecimal getNewvalue()
    {
		return this.newvalue;
    }

    /**
     * 功能描述:
     *    设置新数据
     * @param newvalue : BigDecimal
     */
    public void setNewvalue(BigDecimal newvalue)
    {
	    this.newvalue = newvalue;
    }
    

    /**
     * 功能描述:
     *    获得备注
     * @return 备注 : String
     */
    public String getRemark()
    {
		return this.remark;
    }

    /**
     * 功能描述:
     *    设置备注
     * @param remark : String
     */
    public void setRemark(String remark)
    {
	    this.remark = remark;
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
     *    获得项目名称
     * @return 项目名称 : String
     */
    public String getProjectid()
    {
		return this.projectid;
    }

    /**
     * 功能描述:
     *    设置项目名称
     * @param projectid : String
     */
    public void setProjectid(String projectid)
    {
	    this.projectid = projectid;
    }
    
    
	/**
	 *  默认构造器
	 */
    public CustOtheChanReco()
    {
    	super();
    }
}
