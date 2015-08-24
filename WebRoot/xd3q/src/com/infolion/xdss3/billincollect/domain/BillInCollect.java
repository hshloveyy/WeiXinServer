/*
 * @(#)BillInCollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.billincollect.domain;

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
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;

/**
 * <pre>
 * 未清收款(BillInCollect)实体类
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
@Table(name = "YBILLINCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillInCollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 发票收款关系ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BILLINCOLLECTID")
      
    private String billincollectid;   
    
	/*
	 * 收款金额分配ID
	 */
    @Column(name="COLLECTITEMID")
    @ValidateRule(dataType=9,label="收款金额分配ID",maxLength= 36,required=false)  
    private String collectitemid;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
	/*
	 * 预计发货日
	 */
    @Column(name="SENDGOODSDATE")
    @ValidateRule(dataType=9,label="预计发货日",maxLength= 14,required=false)  
    private String sendgoodsdate;   
    
	/*
	 * 预收款金额
	 */
    @Column(name="COLLECTAMOUNT")
    @ValidateRule(dataType=0,label="预收款金额",required=false)  
    private BigDecimal collectamount;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 押汇汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="押汇汇率",required=false)  
    private BigDecimal exchangerate;   
    
	/*
	 * 已清金额
	 */
    @Column(name="OFFSETAMOUNT")
    @ValidateRule(dataType=0,label="已清金额",required=false)  
    private BigDecimal offsetamount;   
    
	/*
	 * 未清金额
	 */
    @Column(name="UNOFFSETAMOUNT")
    @ValidateRule(dataType=0,label="未清金额",required=false)  
    private BigDecimal unoffsetamount;   
    
	/*
	 * 在批金额
	 */
    @Column(name="ONROADAMOUNT")
    @ValidateRule(dataType=0,label="在批金额",required=false)  
    private BigDecimal onroadamount;   
    
	/*
	 * 本行抵消金额
	 */
    @Column(name="NOWCLEARAMOUNT")
    @ValidateRule(dataType=0,label="本行抵消金额",required=false)  
    private BigDecimal nowclearamount;   
    /*
	 * 本位币金额
	 */
	@Column(name = "BWBJE")
	@ValidateRule(dataType = 0, label = "本位币金额", required = false)
	private BigDecimal bwbje;
	
	/*
	 * 未清本位币金额
	 */
	@Column(name = "UNBWBJE")
	@ValidateRule(dataType = 0, label = "未清本位币金额", required = false)
	private BigDecimal unbwbje;
	
	/*
	 * 保证金
	 */
	@Column(name = "SURETYBOND")
	@ValidateRule(dataType = 0, label = "保证金", required = false)
	private BigDecimal suretybond;
	
	 /*
	 * 抬头文本
	 */
    @Column(name="BKTXT")
    @ValidateRule(dataType=9,label="抬头文本",maxLength= 150,required=false)  
    private String bktxt;   
    
    /*
	 * 过账日期
	 */
    @Column(name="ACCOUNTDATE")
    @ValidateRule(dataType=9,label="过账日期",maxLength= 20,required=false)  
    private String accountdate;   
    
    /*
	 * 外部纸质合同号
	 */
    @Column(name="OLDCONTRACT_NO")
    @ValidateRule(dataType=9,label="外部纸质合同号",maxLength= 200,required=false)  
    private String oldcontract_no;   
    
	/*
	 * 票清收款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BILLCLEARID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillClearCollect billClearCollect;   
    

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
     *    获得发票收款关系ID
     * @return 发票收款关系ID : String
     */
    public String getBillincollectid()
    {
		return this.billincollectid;
    }

    /**
     * 功能描述:
     *    设置发票收款关系ID
     * @param billincollectid : String
     */
    public void setBillincollectid(String billincollectid)
    {
	    this.billincollectid = billincollectid;
    }
    

    /**
     * 功能描述:
     *    获得收款金额分配ID
     * @return 收款金额分配ID : String
     */
    public String getCollectitemid()
    {
		return this.collectitemid;
    }

    /**
     * 功能描述:
     *    设置收款金额分配ID
     * @param collectitemid : String
     */
    public void setCollectitemid(String collectitemid)
    {
	    this.collectitemid = collectitemid;
    }
    

    /**
     * 功能描述:
     *    获得财务凭证号
     * @return 财务凭证号 : String
     */
    public String getVoucherno()
    {
		return this.voucherno;
    }

    /**
     * 功能描述:
     *    设置财务凭证号
     * @param voucherno : String
     */
    public void setVoucherno(String voucherno)
    {
	    this.voucherno = voucherno;
    }
    

    /**
     * 功能描述:
     *    获得项目编号
     * @return 项目编号 : String
     */
    public String getProject_no()
    {
		return this.project_no;
    }

    /**
     * 功能描述:
     *    设置项目编号
     * @param project_no : String
     */
    public void setProject_no(String project_no)
    {
	    this.project_no = project_no;
    }
    

    /**
     * 功能描述:
     *    获得合同号
     * @return 合同号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置合同号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
    }
    

    /**
     * 功能描述:
     *    获得收款单号
     * @return 收款单号 : String
     */
    public String getCollectno()
    {
		return this.collectno;
    }

    /**
     * 功能描述:
     *    设置收款单号
     * @param collectno : String
     */
    public void setCollectno(String collectno)
    {
	    this.collectno = collectno;
    }
    

    /**
     * 功能描述:
     *    获得预计发货日
     * @return 预计发货日 : String
     */
    public String getSendgoodsdate()
    {
		return this.sendgoodsdate;
    }

    /**
     * 功能描述:
     *    设置预计发货日
     * @param sendgoodsdate : String
     */
    public void setSendgoodsdate(String sendgoodsdate)
    {
	    this.sendgoodsdate = sendgoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得预收款金额
     * @return 预收款金额 : BigDecimal
     */
    public BigDecimal getCollectamount()
    {
		return this.collectamount;
    }

    /**
     * 功能描述:
     *    设置预收款金额
     * @param collectamount : BigDecimal
     */
    public void setCollectamount(BigDecimal collectamount)
    {
	    this.collectamount = collectamount;
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
     *    获得押汇汇率
     * @return 押汇汇率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置押汇汇率
     * @param exchangerate : BigDecimal
     */
    public void setExchangerate(BigDecimal exchangerate)
    {
	    this.exchangerate = exchangerate;
    }
    

    /**
     * 功能描述:
     *    获得已清金额
     * @return 已清金额 : BigDecimal
     */
    public BigDecimal getOffsetamount()
    {
		return this.offsetamount;
    }

    /**
     * 功能描述:
     *    设置已清金额
     * @param offsetamount : BigDecimal
     */
    public void setOffsetamount(BigDecimal offsetamount)
    {
	    this.offsetamount = offsetamount;
    }
    

    /**
     * 功能描述:
     *    获得未清金额
     * @return 未清金额 : BigDecimal
     */
    public BigDecimal getUnoffsetamount()
    {
		return this.unoffsetamount;
    }

    /**
     * 功能描述:
     *    设置未清金额
     * @param unoffsetamount : BigDecimal
     */
    public void setUnoffsetamount(BigDecimal unoffsetamount)
    {
	    this.unoffsetamount = unoffsetamount;
    }
    

    /**
     * 功能描述:
     *    获得在批金额
     * @return 在批金额 : BigDecimal
     */
    public BigDecimal getOnroadamount()
    {
		return this.onroadamount;
    }

    /**
     * 功能描述:
     *    设置在批金额
     * @param onroadamount : BigDecimal
     */
    public void setOnroadamount(BigDecimal onroadamount)
    {
	    this.onroadamount = onroadamount;
    }
    

    /**
     * 功能描述:
     *    获得本行抵消金额
     * @return 本行抵消金额 : BigDecimal
     */
    public BigDecimal getNowclearamount()
    {
		return this.nowclearamount;
    }

    /**
     * 功能描述:
     *    设置本行抵消金额
     * @param nowclearamount : BigDecimal
     */
    public void setNowclearamount(BigDecimal nowclearamount)
    {
	    this.nowclearamount = nowclearamount;
    }
    

    /**
     * 功能描述:
     *    获得票清收款
     * @return 票清收款 : BillClearCollect
     */
    public BillClearCollect getBillClearCollect()
    {
		return this.billClearCollect;
    }

    /**
     * 功能描述:
     *    设置票清收款
     * @param billClearCollect : BillClearCollect
     */
    public void setBillClearCollect(BillClearCollect billClearCollect)
    {
	    this.billClearCollect = billClearCollect;
    }
    
    /**
     * 本位币
     * @return
     */
	public BigDecimal getBwbje() {
		return bwbje;
	}
	
	 /**
     * 本位币
     * @return
     */
	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
	}
	/**
	 * 保证金
	 * @return
	 */
	public BigDecimal getSuretybond() {
		return suretybond;
	}
	/**
	 * 保证金
	 * @return
	 */
	public void setSuretybond(BigDecimal suretybond) {
		this.suretybond = suretybond;
	}

	/**
	 * 功能描述: 设置未清本位币金额
	 * @return
	 */
	public BigDecimal getUnbwbje() {
		return unbwbje;
	}
	/**
	 * 功能描述: 取得未清本位币金额
	 * @param unbwbje
	 */
	public void setUnbwbje(BigDecimal unbwbje) {
		this.unbwbje = unbwbje;
	}
	
	/**
	 * 功能描述: 取得抬头文本
	 * @return
	 */
	public String getBktxt() {
		return bktxt;
	}
	
	/**
	 * 功能描述: 设置抬头文本
	 * @param bktxt
	 */
	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	
	/**
	 * 功能描述: 取得外部纸质合同号
	 * @return
	 */
	public String getOldcontract_no() {
		return oldcontract_no;
	}
	
	/**
	 * 功能描述: 设置外部纸质合同号
	 * @param oldcontract_no
	 */
	public void setOldcontract_no(String oldcontract_no) {
		this.oldcontract_no = oldcontract_no;
	}
	/**
	 * 功能描述: 取得过账日期
	 * @return
	 */
	public String getAccountdate() {
		return accountdate;
	}
	/**
	 * 功能描述: 设置过账日期
	 * @param oldcontract_no
	 */
	public void setAccountdate(String accountdate) {
		this.accountdate = accountdate;
	}
	
	/**
	 *  默认构造器
	 */
    public BillInCollect()
    {
    	super();
    }
}
