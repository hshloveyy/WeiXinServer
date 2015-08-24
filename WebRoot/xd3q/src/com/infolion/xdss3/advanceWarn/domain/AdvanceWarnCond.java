/*
 * @(#)AdvanceWarnCond.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 10点00分53秒
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
 * 预警对像条件(AdvanceWarnCond)实体类
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
@Table(name = "YADVAWARNCOND")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class AdvanceWarnCond extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 条件编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CONDITIONID")
      
    private String conditionid;   
    
	/*
	 * 序号
	 */
    @Column(name="CONDNO")
    @ValidateRule(dataType=9,label="序号",maxLength= 3,required=true)  
    private String condno;   
    
	/*
	 * 左括号
	 */
    @Column(name="LEFTPARENTHESES")
    @ValidateRule(dataType=9,label="左括号",maxLength= 2,required=true)  
    private String leftparentheses;   
    
	/*
	 * 预警字段
	 */
    @Column(name="FIELDCODE")
    @ValidateRule(dataType=9,label="预警字段",maxLength= 128,required=true)  
    private String fieldcode;   
    
	/*
	 * 操作符
	 */
    @Column(name="CONDROLE")
    @ValidateRule(dataType=9,label="操作符",maxLength= 2,required=true)  
    private String condrole;   
    
	/*
	 * 条件值
	 */
    @Column(name="CONDVALUE")
    @ValidateRule(dataType=9,label="条件值",maxLength= 128,required=true)  
    private String condvalue;   
    
	/*
	 * 右括号
	 */
    @Column(name="RIGHTPARENTHESES")
    @ValidateRule(dataType=9,label="右括号",maxLength= 2,required=true)  
    private String rightparentheses;   
    
	/*
	 * 并列条件
	 */
    @Column(name="CONNECTCOND")
    @ValidateRule(dataType=9,label="并列条件",maxLength= 2,required=false)  
    private String connectcond;   
    
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
     *    获得条件编号
     * @return 条件编号 : String
     */
    public String getConditionid()
    {
		return this.conditionid;
    }

    /**
     * 功能描述:
     *    设置条件编号
     * @param conditionid : String
     */
    public void setConditionid(String conditionid)
    {
	    this.conditionid = conditionid;
    }
    

    /**
     * 功能描述:
     *    获得序号
     * @return 序号 : String
     */
    public String getCondno()
    {
		return this.condno;
    }

    /**
     * 功能描述:
     *    设置序号
     * @param condno : String
     */
    public void setCondno(String condno)
    {
	    this.condno = condno;
    }
    

    /**
     * 功能描述:
     *    获得左括号
     * @return 左括号 : String
     */
    public String getLeftparentheses()
    {
		return this.leftparentheses;
    }

    /**
     * 功能描述:
     *    设置左括号
     * @param leftparentheses : String
     */
    public void setLeftparentheses(String leftparentheses)
    {
	    this.leftparentheses = leftparentheses;
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
     *    获得操作符
     * @return 操作符 : String
     */
    public String getCondrole()
    {
		return this.condrole;
    }

    /**
     * 功能描述:
     *    设置操作符
     * @param condrole : String
     */
    public void setCondrole(String condrole)
    {
	    this.condrole = condrole;
    }
    

    /**
     * 功能描述:
     *    获得条件值
     * @return 条件值 : String
     */
    public String getCondvalue()
    {
		return this.condvalue;
    }

    /**
     * 功能描述:
     *    设置条件值
     * @param condvalue : String
     */
    public void setCondvalue(String condvalue)
    {
	    this.condvalue = condvalue;
    }
    

    /**
     * 功能描述:
     *    获得右括号
     * @return 右括号 : String
     */
    public String getRightparentheses()
    {
		return this.rightparentheses;
    }

    /**
     * 功能描述:
     *    设置右括号
     * @param rightparentheses : String
     */
    public void setRightparentheses(String rightparentheses)
    {
	    this.rightparentheses = rightparentheses;
    }
    

    /**
     * 功能描述:
     *    获得并列条件
     * @return 并列条件 : String
     */
    public String getConnectcond()
    {
		return this.connectcond;
    }

    /**
     * 功能描述:
     *    设置并列条件
     * @param connectcond : String
     */
    public void setConnectcond(String connectcond)
    {
	    this.connectcond = connectcond;
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
    public AdvanceWarnCond()
    {
    	super();
    }
}
