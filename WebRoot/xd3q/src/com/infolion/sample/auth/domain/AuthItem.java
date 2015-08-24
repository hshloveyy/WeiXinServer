/*
 * @(#)AuthItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月15日 19点35分40秒
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
import com.infolion.sample.auth.domain.AuthTest;

/**
 * <pre>
 * AuthInfo(AuthItem)实体类
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
@Table(name = "YAUTHITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AuthItem extends BaseObject
{
	//fields
	/*
	 * 信息ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="AUTHINFOID")
      
    private String authinfoid;   
    
	/*
	 * 用户描述
	 */
    @Column(name="MEMO")
    @ValidateRule(dataType=9,label="用户描述",maxLength= 128,required=false)  
    private String memo;   
    
	/*
	 * AuthTest
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="AUTHRESOURCEID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private AuthTest authTest;   
    

    /**
     * 功能描述:
     *    获得信息ID
     * @return 信息ID : String
     */
    public String getAuthinfoid()
    {
		return this.authinfoid;
    }

    /**
     * 功能描述:
     *    设置信息ID
     * @param authinfoid : String
     */
    public void setAuthinfoid(String authinfoid)
    {
	    this.authinfoid = authinfoid;
    }
    

    /**
     * 功能描述:
     *    获得用户描述
     * @return 用户描述 : String
     */
    public String getMemo()
    {
		return this.memo;
    }

    /**
     * 功能描述:
     *    设置用户描述
     * @param memo : String
     */
    public void setMemo(String memo)
    {
	    this.memo = memo;
    }
    

    /**
     * 功能描述:
     *    获得AuthTest
     * @return AuthTest : AuthTest
     */
    public AuthTest getAuthTest()
    {
		return this.authTest;
    }

    /**
     * 功能描述:
     *    设置AuthTest
     * @param authTest : AuthTest
     */
    public void setAuthTest(AuthTest authTest)
    {
	    this.authTest = authTest;
    }
    
    
	/**
	 *  默认构造器
	 */
    public AuthItem()
    {
    	super();
    }
}
