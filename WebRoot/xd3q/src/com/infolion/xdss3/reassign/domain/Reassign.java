/*
 * @(#)Reassign.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 12点02分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.reassign.domain;

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
 * 重分配(Reassign)实体类
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
@Table(name = "YREASSIGN")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Reassign extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 重分配ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="REASSIGNID")
      
    private String reassignid;   
    
	/*
	 * 重分配方式
	 */
    @Column(name="REASSIGNTYPE")
    @ValidateRule(dataType=9,label="重分配方式",maxLength= 1,required=false)  
    private String reassigntype;   
    
	/*
	 * 重分配对象ID
	 */
    @Column(name="REASSIGNBOID")
    @ValidateRule(dataType=9,label="重分配对象ID",maxLength= 36,required=false)  
    private String reassignboid;   
    
	/*
	 * 重分配对象单号
	 */
    @Column(name="REASSIGNBONO")
    @ValidateRule(dataType=9,label="重分配对象单号",maxLength= 12,required=false)  
    private String reassignbono;   
    
	/*
	 * 重分配方式
	 */
    @Column(name="REASSIGNTMETHOD")
    @ValidateRule(dataType=9,label="重分配方式",maxLength= 1,required=false)  
    private String reassigntmethod;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
	/*
	 * 业务状态
	 */
    @Column(name="BUSSINESSSTATE")
    @ValidateRule(dataType=9,label="业务状态",maxLength= 2,required=false)  
    private String bussinessstate;   
    
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
	 * 最后修改日期
	 */
    @Column(name="DEPT_ID")
    @ValidateRule(dataType=8,label="部门ID",required=false)  
    private String dept_id;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="OCREATOR")
    @ValidateRule(dataType=8,label="原创建人",required=false)  
    private String ocreator;   
    
	/*
	 * 抬头文本
	 */
	@Column(name = "TEXT")
	@ValidateRule(dataType = 9, label = "抬头文本", maxLength = 255, required = true)
	private String text;
	
    
    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getOcreator() {
		return ocreator;
	}

	public void setOcreator(String ocreator) {
		this.ocreator = ocreator;
	}

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
     *    获得重分配ID
     * @return 重分配ID : String
     */
    public String getReassignid()
    {
		return this.reassignid;
    }

    /**
     * 功能描述:
     *    设置重分配ID
     * @param reassignid : String
     */
    public void setReassignid(String reassignid)
    {
	    this.reassignid = reassignid;
    }
    

    /**
     * 功能描述:
     *    获得重分配方式
     * @return 重分配方式 : String
     */
    public String getReassigntype()
    {
		return this.reassigntype;
    }

    /**
     * 功能描述:
     *    设置重分配方式
     * @param reassigntype : String
     */
    public void setReassigntype(String reassigntype)
    {
	    this.reassigntype = reassigntype;
    }
    

    /**
     * 功能描述:
     *    获得重分配对象ID
     * @return 重分配对象ID : String
     */
    public String getReassignboid()
    {
		return this.reassignboid;
    }

    /**
     * 功能描述:
     *    设置重分配对象ID
     * @param reassignboid : String
     */
    public void setReassignboid(String reassignboid)
    {
	    this.reassignboid = reassignboid;
    }
    

    /**
     * 功能描述:
     *    获得重分配对象单号
     * @return 重分配对象单号 : String
     */
    public String getReassignbono()
    {
		return this.reassignbono;
    }

    /**
     * 功能描述:
     *    设置重分配对象单号
     * @param reassignbono : String
     */
    public void setReassignbono(String reassignbono)
    {
	    this.reassignbono = reassignbono;
    }
    

    /**
     * 功能描述:
     *    获得重分配方式
     * @return 重分配方式 : String
     */
    public String getReassigntmethod()
    {
		return this.reassigntmethod;
    }

    /**
     * 功能描述:
     *    设置重分配方式
     * @param reassigntmethod : String
     */
    public void setReassigntmethod(String reassigntmethod)
    {
	    this.reassigntmethod = reassigntmethod;
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
     *    获得业务状态
     * @return 业务状态 : String
     */
    public String getBussinessstate()
    {
		return this.bussinessstate;
    }

    /**
     * 功能描述:
     *    设置业务状态
     * @param bussinessstate : String
     */
    public void setBussinessstate(String bussinessstate)
    {
	    this.bussinessstate = bussinessstate;
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
    public Reassign()
    {
    	super();
    }
}
