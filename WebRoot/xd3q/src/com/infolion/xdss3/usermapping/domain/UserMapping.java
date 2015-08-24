/*
 * @(#)UserMapping.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月05日 04点41分35秒
 *  描　述：创建
 */
package com.infolion.xdss3.usermapping.domain;

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
 * 账户用户关系(UserMapping)实体类
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
@Table(name = "YUSERMAPPING")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class UserMapping extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * SAP账号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="SAPACCOUNT")
      
    private String sapaccount;   
    
	/*
	 *  用户名
	 */
    @Column(name="USERNAME")
    @ValidateRule(dataType=9,label=" 用户名",maxLength= 100,required=false)  
    private String username;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 4,required=false)  
    private String depid;   
    

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
     *    获得SAP账号
     * @return SAP账号 : String
     */
    public String getSapaccount()
    {
		return this.sapaccount;
    }

    /**
     * 功能描述:
     *    设置SAP账号
     * @param sapaccount : String
     */
    public void setSapaccount(String sapaccount)
    {
	    this.sapaccount = sapaccount;
    }
    

    /**
     * 功能描述:
     *    获得 用户名
     * @return  用户名 : String
     */
    public String getUsername()
    {
		return this.username;
    }

    /**
     * 功能描述:
     *    设置 用户名
     * @param username : String
     */
    public void setUsername(String username)
    {
	    this.username = username;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid()
    {
		return this.depid;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid : String
     */
    public void setDepid(String depid)
    {
	    this.depid = depid;
    }
    
    
	/**
	 *  默认构造器
	 */
    public UserMapping()
    {
    	super();
    }
}
