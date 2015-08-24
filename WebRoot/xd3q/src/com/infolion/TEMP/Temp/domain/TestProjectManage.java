/*
 * @(#)TestProjectManage.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月09日 17点07分38秒
 *  描　述：创建
 */
package com.infolion.TEMP.Temp.domain;

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
 * 项目管理（测试）(TestProjectManage)实体类
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
@Table(name = "YTESTPROJECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class TestProjectManage extends BaseObject
{
	//fields
	/*
	 * 项目ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PROJECTID")
      
    private String projectid;   
    
	/*
	 * 工程名称
	 */
    @Column(name="PROJECTNAME")
    @ValidateRule(dataType=9,label="工程名称",maxLength= 100,required=false)  
    private String projectname;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
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
     *    获得项目ID
     * @return 项目ID : String
     */
    public String getProjectid()
    {
		return this.projectid;
    }

    /**
     * 功能描述:
     *    设置项目ID
     * @param projectid : String
     */
    public void setProjectid(String projectid)
    {
	    this.projectid = projectid;
    }
    

    /**
     * 功能描述:
     *    获得工程名称
     * @return 工程名称 : String
     */
    public String getProjectname()
    {
		return this.projectname;
    }

    /**
     * 功能描述:
     *    设置工程名称
     * @param projectname : String
     */
    public void setProjectname(String projectname)
    {
	    this.projectname = projectname;
    }
    

    /**
     * 功能描述:
     *    获得流程状态
     * @return 流程状态 : String
     */
    public String getProcessstate()
    {
		return this.processstate;
    }

    /**
     * 功能描述:
     *    设置流程状态
     * @param processstate : String
     */
    public void setProcessstate(String processstate)
    {
	    this.processstate = processstate;
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
    public TestProjectManage()
    {
    	super();
    }
}
