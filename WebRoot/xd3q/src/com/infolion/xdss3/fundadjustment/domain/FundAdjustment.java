/*
 * @(#)FundAdjustment.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月26日 11点36分04秒
 *  描　述：创建
 */
package com.infolion.xdss3.fundadjustment.domain;

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
 * 资金占用调整(FundAdjustment)实体类
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
@Table(name = "YFUNDADJUSTMENT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class FundAdjustment extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 资金占用调整ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="FUNDID")
      
    private String fundid;   
    
	/*
	 * 收付款单号
	 */
    @Column(name="FUNDNO")
    @ValidateRule(dataType=9,label="收付款单号",maxLength= 50,required=false)  
    private String fundno;   
    
	/*
	 * 收付款标识
	 */
    @Column(name="FUNDFLAG")
    @ValidateRule(dataType=9,label="收付款标识",maxLength= 2,required=false)  
    private String fundflag;   
    
	/*
	 * 客户
	 */
    @Column(name="CUSTOMER")
    @ValidateRule(dataType=9,label="客户",maxLength= 36,required=false)  
    private String customer;   
    
	/*
	 * 供应商
	 */
    @Column(name="SUPPLIER")
    @ValidateRule(dataType=9,label="供应商",maxLength= 50,required=false)  
    private String supplier;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String deptid;   
    
	/*
	 * 项目ID
	 */
    @Column(name="PROJECTID")
    @ValidateRule(dataType=9,label="项目ID",maxLength= 36,required=false)  
    private String projectid;   
    
	/*
	 * 贸易方式
	 */
    @Column(name="TRADE_TYPE")
    @ValidateRule(dataType=9,label="贸易方式",maxLength= 10,required=false)  
    private String trade_type;   
    
	/*
	 * 物料组
	 */
    @Column(name="MAT_GROUP")
    @ValidateRule(dataType=9,label="物料组",maxLength= 36,required=false)  
    private String mat_group;   
    
	/*
	 * 时间
	 */
    @Column(name="LOCKTIME")
    @ValidateRule(dataType=8,label="时间",required=false)  
    private String locktime;   
    
	/*
	 * 金额
	 */
    @Column(name="AMOUNT")
    @ValidateRule(dataType=9,label="金额",maxLength= 18,required=false)  
    private String amount;   
    
	/*
	 * 金额(仅票据)
	 */
    @Column(name="AMOUNT2")
    @ValidateRule(dataType=0,label="金额(仅票据)",required=false)  
    private BigDecimal amount2;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 备注
	 */
    @Column(name="CMD")
    @ValidateRule(dataType=9,label="备注",maxLength= 255,required=false)  
    private String cmd;   
    
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
     *    获得资金占用调整ID
     * @return 资金占用调整ID : String
     */
    public String getFundid()
    {
		return this.fundid;
    }

    /**
     * 功能描述:
     *    设置资金占用调整ID
     * @param fundid : String
     */
    public void setFundid(String fundid)
    {
	    this.fundid = fundid;
    }
    

    /**
     * 功能描述:
     *    获得收付款单号
     * @return 收付款单号 : String
     */
    public String getFundno()
    {
		return this.fundno;
    }

    /**
     * 功能描述:
     *    设置收付款单号
     * @param fundno : String
     */
    public void setFundno(String fundno)
    {
	    this.fundno = fundno;
    }
    

    /**
     * 功能描述:
     *    获得收付款标识
     * @return 收付款标识 : String
     */
    public String getFundflag()
    {
		return this.fundflag;
    }

    /**
     * 功能描述:
     *    设置收付款标识
     * @param fundflag : String
     */
    public void setFundflag(String fundflag)
    {
	    this.fundflag = fundflag;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getCustomer()
    {
		return this.customer;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param customer : String
     */
    public void setCustomer(String customer)
    {
	    this.customer = customer;
    }
    

    /**
     * 功能描述:
     *    获得供应商
     * @return 供应商 : String
     */
    public String getSupplier()
    {
		return this.supplier;
    }

    /**
     * 功能描述:
     *    设置供应商
     * @param supplier : String
     */
    public void setSupplier(String supplier)
    {
	    this.supplier = supplier;
    }
    

    /**
     * 功能描述:
     *    获得部门ID
     * @return 部门ID : String
     */
    public String getDeptid()
    {
		return this.deptid;
    }

    /**
     * 功能描述:
     *    设置部门ID
     * @param deptid : String
     */
    public void setDeptid(String deptid)
    {
	    this.deptid = deptid;
    }
    

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
     *    获得贸易方式
     * @return 贸易方式 : String
     */
    public String getTrade_type()
    {
		return this.trade_type;
    }

    /**
     * 功能描述:
     *    设置贸易方式
     * @param trade_type : String
     */
    public void setTrade_type(String trade_type)
    {
	    this.trade_type = trade_type;
    }
    

    /**
     * 功能描述:
     *    获得物料组
     * @return 物料组 : String
     */
    public String getMat_group()
    {
		return this.mat_group;
    }

    /**
     * 功能描述:
     *    设置物料组
     * @param mat_group : String
     */
    public void setMat_group(String mat_group)
    {
	    this.mat_group = mat_group;
    }
    

    /**
     * 功能描述:
     *    获得时间
     * @return 时间 : String
     */
    public String getLocktime()
    {
    	return DateUtils.toDisplayStr(this.locktime, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置时间
     * @param locktime : String
     */
    public void setLocktime(String locktime)
    {
    	locktime = DateUtils.toStoreStr(locktime);
	    this.locktime = locktime;
    }
    

    /**
     * 功能描述:
     *    获得金额
     * @return 金额 : String
     */
    public String getAmount()
    {
		return this.amount;
    }

    /**
     * 功能描述:
     *    设置金额
     * @param amount : String
     */
    public void setAmount(String amount)
    {
	    this.amount = amount;
    }
    

    /**
     * 功能描述:
     *    获得金额(仅票据)
     * @return 金额(仅票据) : BigDecimal
     */
    public BigDecimal getAmount2()
    {
		return this.amount2;
    }

    /**
     * 功能描述:
     *    设置金额(仅票据)
     * @param amount2 : BigDecimal
     */
    public void setAmount2(BigDecimal amount2)
    {
	    this.amount2 = amount2;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得备注
     * @return 备注 : String
     */
    public String getCmd()
    {
		return this.cmd;
    }

    /**
     * 功能描述:
     *    设置备注
     * @param cmd : String
     */
    public void setCmd(String cmd)
    {
	    this.cmd = cmd;
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
    public FundAdjustment()
    {
    	super();
    }
}
