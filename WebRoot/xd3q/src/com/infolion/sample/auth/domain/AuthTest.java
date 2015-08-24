/*
 * @(#)AuthTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月16日 09点05分47秒
 *  描　述：创建
 */
package com.infolion.sample.auth.domain;

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
 * AuthTest(AuthTest)实体类
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
@Table(name = "YAUTHRESOURCEA")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AuthTest extends BaseObject
{
	//fields
	/*
	 * 权限资源ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="AUTHRESOURCEID")
      
    private String authresourceid;   
    
	/*
	 * 父资源ID
	 */
    @Column(name="PARENTAUTHRESID")
    @ValidateRule(dataType=9,label="父资源ID",maxLength= 36,required=false)  
    private String parentauthresid;   
    
	/*
	 * 资源类型
	 */
    @Column(name="AUTHRESOURCETYPE")
    @ValidateRule(dataType=9,label="资源类型",maxLength= 1,required=false)  
    private String authresourcetype;   
    
	/*
	 * 资源描述
	 */
    @Column(name="AUTHRESOURCEDESC")
    @ValidateRule(dataType=9,label="资源描述",maxLength= 255,required=false)  
    private String authresourcedesc;   
    
	/*
	 * 对象类型
	 */
    @Column(name="OBJECTTYPE")
    @ValidateRule(dataType=9,label="对象类型",maxLength= 1,required=false)  
    private String objecttype;   
    
	/*
	 * 对象(业务对象名称或者其他)
	 */
    @Column(name="OBJECT")
    @ValidateRule(dataType=9,label="对象(业务对象名称或者其他)",maxLength= 100,required=false)  
    private String object;   
    
	/*
	 * 对象描述
	 */
    @Column(name="OBJECTDESC")
    @ValidateRule(dataType=9,label="对象描述",maxLength= 200,required=false)  
    private String objectdesc;   
    
	/*
	 * 方法名
	 */
    @Column(name="METHODNAME")
    @ValidateRule(dataType=9,label="方法名",maxLength= 32,required=false)  
    private String methodname;   
    
	/*
	 * 方法类型
	 */
    @Column(name="METHODTYPE")
    @ValidateRule(dataType=9,label="方法类型",maxLength= 10,required=false)  
    private String methodtype;   
    
	/*
	 * 方法描述
	 */
    @Column(name="METHODDESC")
    @ValidateRule(dataType=9,label="方法描述",maxLength= 100,required=false)  
    private String methoddesc;   
    
	/*
	 * URL地址
	 */
    @Column(name="URL")
    @ValidateRule(dataType=9,label="URL地址",maxLength= 255,required=false)  
    private String url;   
    
	/*
	 * 事务
	 */
    @Column(name="TCODE")
    @ValidateRule(dataType=9,label="事务",maxLength= 36,required=false)  
    private String tcode;   
    
	/*
	 * 使用次数
	 */
    @Column(name="DEGREE")
    @ValidateRule(dataType=0,label="使用次数",required=false)  
    private BigDecimal degree;   
    

    /**
     * 功能描述:
     *    获得权限资源ID
     * @return 权限资源ID : String
     */
    public String getAuthresourceid()
    {
		return this.authresourceid;
    }

    /**
     * 功能描述:
     *    设置权限资源ID
     * @param authresourceid : String
     */
    public void setAuthresourceid(String authresourceid)
    {
	    this.authresourceid = authresourceid;
    }
    

    /**
     * 功能描述:
     *    获得父资源ID
     * @return 父资源ID : String
     */
    public String getParentauthresid()
    {
		return this.parentauthresid;
    }

    /**
     * 功能描述:
     *    设置父资源ID
     * @param parentauthresid : String
     */
    public void setParentauthresid(String parentauthresid)
    {
	    this.parentauthresid = parentauthresid;
    }
    

    /**
     * 功能描述:
     *    获得资源类型
     * @return 资源类型 : String
     */
    public String getAuthresourcetype()
    {
		return this.authresourcetype;
    }

    /**
     * 功能描述:
     *    设置资源类型
     * @param authresourcetype : String
     */
    public void setAuthresourcetype(String authresourcetype)
    {
	    this.authresourcetype = authresourcetype;
    }
    

    /**
     * 功能描述:
     *    获得资源描述
     * @return 资源描述 : String
     */
    public String getAuthresourcedesc()
    {
		return this.authresourcedesc;
    }

    /**
     * 功能描述:
     *    设置资源描述
     * @param authresourcedesc : String
     */
    public void setAuthresourcedesc(String authresourcedesc)
    {
	    this.authresourcedesc = authresourcedesc;
    }
    

    /**
     * 功能描述:
     *    获得对象类型
     * @return 对象类型 : String
     */
    public String getObjecttype()
    {
		return this.objecttype;
    }

    /**
     * 功能描述:
     *    设置对象类型
     * @param objecttype : String
     */
    public void setObjecttype(String objecttype)
    {
	    this.objecttype = objecttype;
    }
    

    /**
     * 功能描述:
     *    获得对象(业务对象名称或者其他)
     * @return 对象(业务对象名称或者其他) : String
     */
    public String getObject()
    {
		return this.object;
    }

    /**
     * 功能描述:
     *    设置对象(业务对象名称或者其他)
     * @param object : String
     */
    public void setObject(String object)
    {
	    this.object = object;
    }
    

    /**
     * 功能描述:
     *    获得对象描述
     * @return 对象描述 : String
     */
    public String getObjectdesc()
    {
		return this.objectdesc;
    }

    /**
     * 功能描述:
     *    设置对象描述
     * @param objectdesc : String
     */
    public void setObjectdesc(String objectdesc)
    {
	    this.objectdesc = objectdesc;
    }
    

    /**
     * 功能描述:
     *    获得方法名
     * @return 方法名 : String
     */
    public String getMethodname()
    {
		return this.methodname;
    }

    /**
     * 功能描述:
     *    设置方法名
     * @param methodname : String
     */
    public void setMethodname(String methodname)
    {
	    this.methodname = methodname;
    }
    

    /**
     * 功能描述:
     *    获得方法类型
     * @return 方法类型 : String
     */
    public String getMethodtype()
    {
		return this.methodtype;
    }

    /**
     * 功能描述:
     *    设置方法类型
     * @param methodtype : String
     */
    public void setMethodtype(String methodtype)
    {
	    this.methodtype = methodtype;
    }
    

    /**
     * 功能描述:
     *    获得方法描述
     * @return 方法描述 : String
     */
    public String getMethoddesc()
    {
		return this.methoddesc;
    }

    /**
     * 功能描述:
     *    设置方法描述
     * @param methoddesc : String
     */
    public void setMethoddesc(String methoddesc)
    {
	    this.methoddesc = methoddesc;
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
     *    获得事务
     * @return 事务 : String
     */
    public String getTcode()
    {
		return this.tcode;
    }

    /**
     * 功能描述:
     *    设置事务
     * @param tcode : String
     */
    public void setTcode(String tcode)
    {
	    this.tcode = tcode;
    }
    

    /**
     * 功能描述:
     *    获得使用次数
     * @return 使用次数 : BigDecimal
     */
    public BigDecimal getDegree()
    {
		return this.degree;
    }

    /**
     * 功能描述:
     *    设置使用次数
     * @param degree : BigDecimal
     */
    public void setDegree(BigDecimal degree)
    {
	    this.degree = degree;
    }
    
    
	/**
	 *  默认构造器
	 */
    public AuthTest()
    {
    	super();
    }
}
