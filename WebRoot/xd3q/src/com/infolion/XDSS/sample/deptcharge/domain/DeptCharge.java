/*
 * @(#)DeptCharge.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分48秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptcharge.domain;

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
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;

/**
 * <pre>
 * 管理费用预算(DeptCharge)实体类
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
@Table(name = "YDEPTCHARGE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class DeptCharge extends BaseObject
{
	//fields
	/*
	 * 费用明细
	 */
    @OneToMany(mappedBy="deptCharge",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<DeptCharDetail> chargeDetail;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 部门预算信息ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DEPTCHARGEID")
      
    private String deptchargeid;   
    
	/*
	 * 预算组织名称
	 */
    @Column(name="BUDORGNAME")
    @ValidateRule(dataType=9,label="预算组织名称",maxLength= 100,required=false)  
    private String budorgname;   
    
	/*
	 * 年份
	 */
    @Column(name="AYEAR")
    @ValidateRule(dataType=9,label="年份",maxLength= 4,required=false)  
    private String ayear;   
    
	/*
	 * 单位
	 */
    @Column(name="UNIT")
    @ValidateRule(dataType=9,label="单位",maxLength= 10,required=false)  
    private String unit;   
    
	/*
	 * 审批状态
	 */
    @Column(name="EXAMINESTATE")
    @ValidateRule(dataType=9,label="审批状态",maxLength= 8,required=false)  
    private String examinestate;   
    
	/*
	 * 有效性
	 */
    @Column(name="VALIDITY")
    @ValidateRule(dataType=9,label="有效性",maxLength= 1,required=false)  
    private String validity;   
    
	/*
	 * 版本号
	 */
    @Column(name="VERSION")
    @ValidateRule(dataType=9,label="版本号",maxLength= 3,required=false)  
    private String version;   
    
	/*
	 * 预算分类ID
	 */
    @Column(name="BUDSORTID")
    @ValidateRule(dataType=9,label="预算分类ID",maxLength= 36,required=false)  
    private String budclassid;   
    
	/*
	 * 编制说明
	 */
    @Column(name="ORGMEMO")
    @ValidateRule(dataType=9,label="编制说明",maxLength= 100,required=false)  
    private String orgmemo;   
    
	/**
	 * 预算组织ID
	 */
	@Column(name = "BUDORGID")
	@ValidateRule(dataType = 9, label = "预算组织ID", maxLength = 36, required = false)
	private String budorgid;

	/*
	 * 流程状态
	 */
	@Column(name = "PROCESSSTATE")
	@ValidateRule(dataType = 9, label = "流程状态", maxLength = 30, required = false)
	private String processstate;

    /**
     * 功能描述:
     *    获得费用明细
     * @return 费用明细 : Set<DeptCharDetail>
     */
    public Set<DeptCharDetail> getChargeDetail()
    {
		return this.chargeDetail;
    }

    /**
     * 功能描述:
     *    设置费用明细
     * @param chargeDetail : Set<DeptCharDetail>
     */
    public void setChargeDetail(Set<DeptCharDetail> chargeDetail)
    {
	    this.chargeDetail = chargeDetail;
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
     *    获得部门预算信息ID
     * @return 部门预算信息ID : String
     */
    public String getDeptchargeid()
    {
		return this.deptchargeid;
    }

    /**
     * 功能描述:
     *    设置部门预算信息ID
     * @param deptchargeid : String
     */
    public void setDeptchargeid(String deptchargeid)
    {
	    this.deptchargeid = deptchargeid;
    }
    

    /**
     * 功能描述:
     *    获得预算组织名称
     * @return 预算组织名称 : String
     */
    public String getBudorgname()
    {
		return this.budorgname;
    }

    /**
     * 功能描述:
     *    设置预算组织名称
     * @param budorgname : String
     */
    public void setBudorgname(String budorgname)
    {
	    this.budorgname = budorgname;
    }
    

    /**
     * 功能描述:
     *    获得年份
     * @return 年份 : String
     */
    public String getAyear()
    {
		return this.ayear;
    }

    /**
     * 功能描述:
     *    设置年份
     * @param ayear : String
     */
    public void setAyear(String ayear)
    {
	    this.ayear = ayear;
    }
    

    /**
     * 功能描述:
     *    获得单位
     * @return 单位 : String
     */
    public String getUnit()
    {
		return this.unit;
    }

    /**
     * 功能描述:
     *    设置单位
     * @param unit : String
     */
    public void setUnit(String unit)
    {
	    this.unit = unit;
    }
    

    /**
     * 功能描述:
     *    获得审批状态
     * @return 审批状态 : String
     */
    public String getExaminestate()
    {
		return this.examinestate;
    }

    /**
     * 功能描述:
     *    设置审批状态
     * @param examinestate : String
     */
    public void setExaminestate(String examinestate)
    {
	    this.examinestate = examinestate;
    }
    

    /**
     * 功能描述:
     *    获得有效性
     * @return 有效性 : String
     */
    public String getValidity()
    {
		return this.validity;
    }

    /**
     * 功能描述:
     *    设置有效性
     * @param validity : String
     */
    public void setValidity(String validity)
    {
	    this.validity = validity;
    }
    

    /**
     * 功能描述:
     *    获得版本号
     * @return 版本号 : String
     */
    public String getVersion()
    {
		return this.version;
    }

    /**
     * 功能描述:
     *    设置版本号
     * @param version : String
     */
    public void setVersion(String version)
    {
	    this.version = version;
    }
    

    /**
     * 功能描述:
     *    获得预算分类ID
     * @return 预算分类ID : String
     */
    public String getBudclassid()
    {
		return this.budclassid;
    }

    /**
     * 功能描述:
     *    设置预算分类ID
     * @param budclassid : String
     */
    public void setBudclassid(String budclassid)
    {
	    this.budclassid = budclassid;
    }
    

    /**
     * 功能描述:
     *    获得编制说明
     * @return 编制说明 : String
     */
    public String getOrgmemo()
    {
		return this.orgmemo;
    }

    /**
     * 功能描述:
     *    设置编制说明
     * @param orgmemo : String
     */
    public void setOrgmemo(String orgmemo)
    {
	    this.orgmemo = orgmemo;
    }
    
    
	/**
	 *  默认构造器
	 */
    public DeptCharge()
    {
    	super();
    }

	public void setBudorgid(String budorgid)
	{
		this.budorgid = budorgid;
	}

	public String getBudorgid()
	{
		return budorgid;
	}

	public void setProcessstate(String processstate)
	{
		this.processstate = processstate;
	}

	public String getProcessstate()
	{
		return processstate;
	}
}
