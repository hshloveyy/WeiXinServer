/*
 * @(#)UnnamerCollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月19日 16点37分30秒
 *  描　述：创建
 */
package com.infolion.xdss3.unnameCollect.domain;

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
 * 未明户收款(UnnamerCollect)实体类
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
@Table(name = "YUNNAMERCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class UnnamerCollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 未名户收款单号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="UNNAMERCOLLECTID")
      
    private String unnamercollectid;   
    
	/*
	 * 未明户收款单号
	 */
    @Column(name="UNNAMERCOLLECTNO")
    @ValidateRule(dataType=9,label="未明户收款单号",maxLength= 12,required=false)  
    private String unnamercollectno;   
    
	/*
	 * 申请收款金额
	 */
    @Column(name="APPLYAMOUNT")
    @ValidateRule(dataType=0,label="申请收款金额",required=true)  
    private BigDecimal applyamount;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=true)  
    private String currency;   
    
	/*
	 * 收款银行
	 */
    @Column(name="COLLECTBANKID")
    @ValidateRule(dataType=9,label="收款银行",maxLength= 36,required=false)  
    private String collectbankid;   
    
	/*
	 * 收款银行
	 */
    @Column(name="COLLECTBANKNAME")
    @ValidateRule(dataType=9,label="收款银行",maxLength= 50,required=true)  
    private String collectbankname;   
    
	/*
	 * 收款银行账号
	 */
    @Column(name="COLLCETBANKACC")
    @ValidateRule(dataType=9,label="收款银行账号",maxLength= 50,required=true)  
    private String collcetbankacc;   
    
	/*
	 * 收款银行科目
	 */
    @Column(name="COLLECTBANKSBJ")
    @ValidateRule(dataType=9,label="收款银行科目",maxLength= 20,required=false)  
    private String collectbanksbj;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=8,label="凭证日期",required=false)  
    private String voucherdate;   
    
	/*
	 * 申请部门
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="申请部门",maxLength= 36,required=false)  
    private String deptid;   
    
	/*
	 * 抬头文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="抬头文本",maxLength= 255,required=true)  
    private String text;   
    
	/*
	 * 备注
	 */
    @Column(name="REMARK")
    @ValidateRule(dataType=9,label="备注",maxLength= 255,required=false)  
    private String remark;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 50,required=false)  
    private String processstate;   
    
	/*
	 * 业务状态
	 */
    @Column(name="BUSINESSSTATE")
    @ValidateRule(dataType=9,label="业务状态",maxLength= 2,required=false)  
    private String businessstate;   
    
	/*
	 * 申请收款金额（本位币）
	 */
    @Column(name="APPLYAMOUNT2")
    @ValidateRule(dataType=0,label="申请收款金额（本位币）",required=false)  
    private BigDecimal applyamount2;   
    
	/*
	 * 记账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=8,label="记账日期",required=false)  
    private String accountdate;   
    
	/*
	 * 收款汇率
	 */
    @Column(name="COLLECTRATE")
    @ValidateRule(dataType=0,label="收款汇率",required=false)  
    private BigDecimal collectrate;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 是否已认领
	 */
    @Column(name="ISCLAIM")
    @ValidateRule(dataType=9,label="是否已认领",maxLength= 1,required=false)  
    private String isclaim;   
    
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
     *    获得未名户收款单号
     * @return 未名户收款单号 : String
     */
    public String getUnnamercollectid()
    {
		return this.unnamercollectid;
    }

    /**
     * 功能描述:
     *    设置未名户收款单号
     * @param unnamercollectid : String
     */
    public void setUnnamercollectid(String unnamercollectid)
    {
	    this.unnamercollectid = unnamercollectid;
    }
    

    /**
     * 功能描述:
     *    获得未明户收款单号
     * @return 未明户收款单号 : String
     */
    public String getUnnamercollectno()
    {
		return this.unnamercollectno;
    }

    /**
     * 功能描述:
     *    设置未明户收款单号
     * @param unnamercollectno : String
     */
    public void setUnnamercollectno(String unnamercollectno)
    {
	    this.unnamercollectno = unnamercollectno;
    }
    

    /**
     * 功能描述:
     *    获得申请收款金额
     * @return 申请收款金额 : BigDecimal
     */
    public BigDecimal getApplyamount()
    {
		return this.applyamount;
    }

    /**
     * 功能描述:
     *    设置申请收款金额
     * @param applyamount : BigDecimal
     */
    public void setApplyamount(BigDecimal applyamount)
    {
	    this.applyamount = applyamount;
    }
    

    /**
     * 功能描述:
     *    获得币别
     * @return 币别 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置币别
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得收款银行
     * @return 收款银行 : String
     */
    public String getCollectbankid()
    {
		return this.collectbankid;
    }

    /**
     * 功能描述:
     *    设置收款银行
     * @param collectbankid : String
     */
    public void setCollectbankid(String collectbankid)
    {
	    this.collectbankid = collectbankid;
    }
    

    /**
     * 功能描述:
     *    获得收款银行
     * @return 收款银行 : String
     */
    public String getCollectbankname()
    {
		return this.collectbankname;
    }

    /**
     * 功能描述:
     *    设置收款银行
     * @param collectbankname : String
     */
    public void setCollectbankname(String collectbankname)
    {
	    this.collectbankname = collectbankname;
    }
    

    /**
     * 功能描述:
     *    获得收款银行账号
     * @return 收款银行账号 : String
     */
    public String getCollcetbankacc()
    {
		return this.collcetbankacc;
    }

    /**
     * 功能描述:
     *    设置收款银行账号
     * @param collcetbankacc : String
     */
    public void setCollcetbankacc(String collcetbankacc)
    {
	    this.collcetbankacc = collcetbankacc;
    }
    

    /**
     * 功能描述:
     *    获得收款银行科目
     * @return 收款银行科目 : String
     */
    public String getCollectbanksbj()
    {
		return this.collectbanksbj;
    }

    /**
     * 功能描述:
     *    设置收款银行科目
     * @param collectbanksbj : String
     */
    public void setCollectbanksbj(String collectbanksbj)
    {
	    this.collectbanksbj = collectbanksbj;
    }
    

    /**
     * 功能描述:
     *    获得凭证日期
     * @return 凭证日期 : String
     */
    public String getVoucherdate()
    {
    	return DateUtils.toDisplayStr(this.voucherdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置凭证日期
     * @param voucherdate : String
     */
    public void setVoucherdate(String voucherdate)
    {
    	voucherdate = DateUtils.toStoreStr(voucherdate);
	    this.voucherdate = voucherdate;
    }
    

    /**
     * 功能描述:
     *    获得申请部门
     * @return 申请部门 : String
     */
    public String getDeptid()
    {
		return this.deptid;
    }

    /**
     * 功能描述:
     *    设置申请部门
     * @param deptid : String
     */
    public void setDeptid(String deptid)
    {
	    this.deptid = deptid;
    }
    

    /**
     * 功能描述:
     *    获得抬头文本
     * @return 抬头文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置抬头文本
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
    }
    

    /**
     * 功能描述:
     *    获得备注
     * @return 备注 : String
     */
    public String getRemark()
    {
		return this.remark;
    }

    /**
     * 功能描述:
     *    设置备注
     * @param remark : String
     */
    public void setRemark(String remark)
    {
	    this.remark = remark;
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
    public String getBusinessstate()
    {
		return this.businessstate;
    }

    /**
     * 功能描述:
     *    设置业务状态
     * @param businessstate : String
     */
    public void setBusinessstate(String businessstate)
    {
	    this.businessstate = businessstate;
    }
    

    /**
     * 功能描述:
     *    获得申请收款金额（本位币）
     * @return 申请收款金额（本位币） : BigDecimal
     */
    public BigDecimal getApplyamount2()
    {
		return this.applyamount2;
    }

    /**
     * 功能描述:
     *    设置申请收款金额（本位币）
     * @param applyamount2 : BigDecimal
     */
    public void setApplyamount2(BigDecimal applyamount2)
    {
	    this.applyamount2 = applyamount2;
    }
    

    /**
     * 功能描述:
     *    获得记账日期
     * @return 记账日期 : String
     */
    public String getAccountdate()
    {
    	return DateUtils.toDisplayStr(this.accountdate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置记账日期
     * @param accountdate : String
     */
    public void setAccountdate(String accountdate)
    {
    	accountdate = DateUtils.toStoreStr(accountdate);
	    this.accountdate = accountdate;
    }
    

    /**
     * 功能描述:
     *    获得收款汇率
     * @return 收款汇率 : BigDecimal
     */
    public BigDecimal getCollectrate()
    {
		return this.collectrate;
    }

    /**
     * 功能描述:
     *    设置收款汇率
     * @param collectrate : BigDecimal
     */
    public void setCollectrate(BigDecimal collectrate)
    {
	    this.collectrate = collectrate;
    }
    

    /**
     * 功能描述:
     *    获得现金流项目
     * @return 现金流项目 : String
     */
    public String getCashflowitem()
    {
		return this.cashflowitem;
    }

    /**
     * 功能描述:
     *    设置现金流项目
     * @param cashflowitem : String
     */
    public void setCashflowitem(String cashflowitem)
    {
	    this.cashflowitem = cashflowitem;
    }
    

    /**
     * 功能描述:
     *    获得是否已认领
     * @return 是否已认领 : String
     */
    public String getIsclaim()
    {
		return this.isclaim;
    }

    /**
     * 功能描述:
     *    设置是否已认领
     * @param isclaim : String
     */
    public void setIsclaim(String isclaim)
    {
	    this.isclaim = isclaim;
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
    public UnnamerCollect()
    {
    	super();
    }
}
