/*
 * @(#)ShipCcollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 15点43分22秒
 *  描　述：创建
 */
package com.infolion.sapss.ship.domain;

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
 * 出仓货清款(ShipCcollect)实体类
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
@Table(name = "YSHIPCCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ShipCcollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 出仓货清款编号
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="SHIPCCOLLECTID")
      
    private String shipccollectid;   
    
	/*
	 * 出仓物料行编号
	 */
    @Column(name="SHIP_DETAIL_ID")
    @ValidateRule(dataType=9,label="出仓物料行编号",maxLength= 36,required=false)  
    private String ship_detail_id;   
    
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
    @ValidateRule(dataType=9,label="项目编号",maxLength= 36,required=false)  
    private String project_no;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 收款ID
	 */
    @Column(name="COLLECTID")
    @ValidateRule(dataType=9,label="收款ID",maxLength= 36,required=false)  
    private String collectid;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
	/*
	 * 收款状态
	 */
    @Column(name="COLLECTSTATE")
    @ValidateRule(dataType=9,label="收款状态",maxLength= 1,required=false)  
    private String collectstate;   
    
	/*
	 * 预计到货日
	 */
    @Column(name="ARRIVEGOODSDATE")
    @ValidateRule(dataType=9,label="预计到货日",maxLength= 20,required=false)  
    private String arrivegoodsdate;   
    
	/*
	 * 金额
	 */
    @Column(name="COLLECTAMOUNT")
    @ValidateRule(dataType=0,label="金额",required=false)  
    private BigDecimal collectamount;   
    
	/*
	 * 币别
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="币别",maxLength= 10,required=false)  
    private String currency;   
    
	/*
	 * 汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="汇率",required=false)  
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
	 * 本次已抵消金额
	 */
    @Column(name="NOWOFFSETAMOUNT")
    @ValidateRule(dataType=0,label="本次已抵消金额",required=false)  
    private BigDecimal nowoffsetamount;   
    
	/*
	 * 本行抵消金额
	 */
    @Column(name="CLEARAMOUNT")
    @ValidateRule(dataType=0,label="本行抵消金额",required=false)  
    private BigDecimal clearamount;   
    

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
     *    获得出仓货清款编号
     * @return 出仓货清款编号 : String
     */
    public String getShipccollectid()
    {
		return this.shipccollectid;
    }

    /**
     * 功能描述:
     *    设置出仓货清款编号
     * @param shipccollectid : String
     */
    public void setShipccollectid(String shipccollectid)
    {
	    this.shipccollectid = shipccollectid;
    }
    

    /**
     * 功能描述:
     *    获得出仓物料行编号
     * @return 出仓物料行编号 : String
     */
    public String getShip_detail_id()
    {
		return this.ship_detail_id;
    }

    /**
     * 功能描述:
     *    设置出仓物料行编号
     * @param ship_detail_id : String
     */
    public void setShip_detail_id(String ship_detail_id)
    {
	    this.ship_detail_id = ship_detail_id;
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
     *    获得收款ID
     * @return 收款ID : String
     */
    public String getCollectid()
    {
		return this.collectid;
    }

    /**
     * 功能描述:
     *    设置收款ID
     * @param collectid : String
     */
    public void setCollectid(String collectid)
    {
	    this.collectid = collectid;
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
     *    获得收款状态
     * @return 收款状态 : String
     */
    public String getCollectstate()
    {
		return this.collectstate;
    }

    /**
     * 功能描述:
     *    设置收款状态
     * @param collectstate : String
     */
    public void setCollectstate(String collectstate)
    {
	    this.collectstate = collectstate;
    }
    

    /**
     * 功能描述:
     *    获得预计到货日
     * @return 预计到货日 : String
     */
    public String getArrivegoodsdate()
    {
		return this.arrivegoodsdate;
    }

    /**
     * 功能描述:
     *    设置预计到货日
     * @param arrivegoodsdate : String
     */
    public void setArrivegoodsdate(String arrivegoodsdate)
    {
	    this.arrivegoodsdate = arrivegoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得金额
     * @return 金额 : BigDecimal
     */
    public BigDecimal getCollectamount()
    {
		return this.collectamount;
    }

    /**
     * 功能描述:
     *    设置金额
     * @param collectamount : BigDecimal
     */
    public void setCollectamount(BigDecimal collectamount)
    {
	    this.collectamount = collectamount;
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
     *    获得汇率
     * @return 汇率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置汇率
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
     *    获得本次已抵消金额
     * @return 本次已抵消金额 : BigDecimal
     */
    public BigDecimal getNowoffsetamount()
    {
		return this.nowoffsetamount;
    }

    /**
     * 功能描述:
     *    设置本次已抵消金额
     * @param nowoffsetamount : BigDecimal
     */
    public void setNowoffsetamount(BigDecimal nowoffsetamount)
    {
	    this.nowoffsetamount = nowoffsetamount;
    }
    

    /**
     * 功能描述:
     *    获得本行抵消金额
     * @return 本行抵消金额 : BigDecimal
     */
    public BigDecimal getClearamount()
    {
		return this.clearamount;
    }

    /**
     * 功能描述:
     *    设置本行抵消金额
     * @param clearamount : BigDecimal
     */
    public void setClearamount(BigDecimal clearamount)
    {
	    this.clearamount = clearamount;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ShipCcollect()
    {
    	super();
    }
}
