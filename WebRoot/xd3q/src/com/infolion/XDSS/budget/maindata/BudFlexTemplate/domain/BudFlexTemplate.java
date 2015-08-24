/*
 * @(#)BudFlexTemplate.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月08日 08点36分09秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudFlexTemplate.domain;

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
 * 预算模版flex文件流(BudFlexTemplate)实体类
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
@Table(name = "YBUDFLEXTEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BudFlexTemplate extends BaseObject
{
	//fields
	/*
	 * 预算分类ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BUDSORTID")
      
    private String budsortid;   
    
	/*
	 * 语言
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="LANGUAGE")
      
    private String language;   
    
	/*
	 * 上次修改时间
	 */
    @Column(name="LASTMODIFY")
    @ValidateRule(dataType=9,label="上次修改时间",maxLength= 16,required=false)  
    private String lastmodify;   
    
	/*
	 * 是否修改
	 */
    @Column(name="ISCHANGE")
    @ValidateRule(dataType=9,label="是否修改",maxLength= 10,required=false)  
    private String ischange;   
    
	/*
	 * EXPORT/IMPORT 表格中的用户数据长度字段
	 */
    @Column(name="CLUSTR")
    @ValidateRule(dataType=9,label="EXPORT/IMPORT 表格中的用户数据长度字段",maxLength= 5,required=false)  
    private String clustr;   
    
	/*
	 * flex文件流
	 */
    @Column(name="FLEXSTORE")
    @ValidateRule(dataType=9,label="flex文件流",maxLength= 32000,required=false)  
    private String flexstore;   
    

    /**
     * 功能描述:
     *    获得预算分类ID
     * @return 预算分类ID : String
     */
    public String getBudsortid()
    {
		return this.budsortid;
    }

    /**
     * 功能描述:
     *    设置预算分类ID
     * @param budsortid : String
     */
    public void setBudsortid(String budsortid)
    {
	    this.budsortid = budsortid;
    }
    

    /**
     * 功能描述:
     *    获得语言
     * @return 语言 : String
     */
    public String getLanguage()
    {
		return this.language;
    }

    /**
     * 功能描述:
     *    设置语言
     * @param language : String
     */
    public void setLanguage(String language)
    {
	    this.language = language;
    }
    

    /**
     * 功能描述:
     *    获得上次修改时间
     * @return 上次修改时间 : String
     */
    public String getLastmodify()
    {
		return this.lastmodify;
    }

    /**
     * 功能描述:
     *    设置上次修改时间
     * @param lastmodify : String
     */
    public void setLastmodify(String lastmodify)
    {
	    this.lastmodify = lastmodify;
    }
    

    /**
     * 功能描述:
     *    获得是否修改
     * @return 是否修改 : String
     */
    public String getIschange()
    {
		return this.ischange;
    }

    /**
     * 功能描述:
     *    设置是否修改
     * @param ischange : String
     */
    public void setIschange(String ischange)
    {
	    this.ischange = ischange;
    }
    

    /**
     * 功能描述:
     *    获得EXPORT/IMPORT 表格中的用户数据长度字段
     * @return EXPORT/IMPORT 表格中的用户数据长度字段 : String
     */
    public String getClustr()
    {
		return this.clustr;
    }

    /**
     * 功能描述:
     *    设置EXPORT/IMPORT 表格中的用户数据长度字段
     * @param clustr : String
     */
    public void setClustr(String clustr)
    {
	    this.clustr = clustr;
    }
    

    /**
     * 功能描述:
     *    获得flex文件流
     * @return flex文件流 : String
     */
    public String getFlexstore()
    {
		return this.flexstore;
    }

    /**
     * 功能描述:
     *    设置flex文件流
     * @param flexstore : String
     */
    public void setFlexstore(String flexstore)
    {
	    this.flexstore = flexstore;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BudFlexTemplate()
    {
    	super();
    }
}
