/*
 * @(#)ViewTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月22日 15点04分13秒
 *  描　述：创建
 */
package com.infolion.sample.b1.view.domain;

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
 * 视图测试(ViewTest)实体类
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
@Table(name = "YVLJX")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ViewTest extends BaseObject
{
	//fields
	/*
	 * 用户ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="USERID")
      
    private String yeuserid;   
    
	/*
	 *  用户名
	 */
    @Column(name="USERNAME")
    @ValidateRule(dataType=9,label=" 用户名",maxLength= 100,required=false)  
    private String yeusername;   
    
	/*
	 * 别名
	 */
    @Column(name="BYNAME")
    @ValidateRule(dataType=9,label="别名",maxLength= 100,required=false)  
    private String yebyname;   
    
	/*
	 * 角色ID
	 */
    @Column(name="ROLEID")
    @ValidateRule(dataType=9,label="角色ID",maxLength= 36,required=false)  
    private String yeroleid;   
    
	/*
	 * 角色名
	 */
    @Column(name="ROLENAME")
    @ValidateRule(dataType=9,label="角色名",maxLength= 100,required=false)  
    private String yerolename;   
    

    /**
     * 功能描述:
     *    获得用户ID
     * @return 用户ID : String
     */
    public String getYeuserid()
    {
		return this.yeuserid;
    }

    /**
     * 功能描述:
     *    设置用户ID
     * @param yeuserid : String
     */
    public void setYeuserid(String yeuserid)
    {
	    this.yeuserid = yeuserid;
    }
    

    /**
     * 功能描述:
     *    获得 用户名
     * @return  用户名 : String
     */
    public String getYeusername()
    {
		return this.yeusername;
    }

    /**
     * 功能描述:
     *    设置 用户名
     * @param yeusername : String
     */
    public void setYeusername(String yeusername)
    {
	    this.yeusername = yeusername;
    }
    

    /**
     * 功能描述:
     *    获得别名
     * @return 别名 : String
     */
    public String getYebyname()
    {
		return this.yebyname;
    }

    /**
     * 功能描述:
     *    设置别名
     * @param yebyname : String
     */
    public void setYebyname(String yebyname)
    {
	    this.yebyname = yebyname;
    }
    

    /**
     * 功能描述:
     *    获得角色ID
     * @return 角色ID : String
     */
    public String getYeroleid()
    {
		return this.yeroleid;
    }

    /**
     * 功能描述:
     *    设置角色ID
     * @param yeroleid : String
     */
    public void setYeroleid(String yeroleid)
    {
	    this.yeroleid = yeroleid;
    }
    

    /**
     * 功能描述:
     *    获得角色名
     * @return 角色名 : String
     */
    public String getYerolename()
    {
		return this.yerolename;
    }

    /**
     * 功能描述:
     *    设置角色名
     * @param yerolename : String
     */
    public void setYerolename(String yerolename)
    {
	    this.yerolename = yerolename;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ViewTest()
    {
    	super();
    }
}
