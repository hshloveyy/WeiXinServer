/*
 * @(#)SettleSubjectOther.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点58分15秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOther.domain;

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
import com.infolion.xdss3.financeSquare.domain.ISettleSubject;

import java.math.BigDecimal;

/**
 * <pre>
 * 其他公司结算科目(SettleSubjectOther)实体类
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
@Table(name = "YSETSUBJECTOTHER")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class SettleSubjectOther extends BaseObject implements ISettleSubject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 结算科目ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="SETTLESUBJECTID")
      
    private String settlesubjectid;   
    
	/*
	 * 票清款ID
	 */
    @Column(name="BILLCLEARID")
    @ValidateRule(dataType=9,label="票清款ID",maxLength= 36,required=false)  
    private String billclearid;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT1")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit1;   
    
	/*
	 * 金额1
	 */
    @Column(name="AMOUNT1")
    @ValidateRule(dataType=0,label="金额1",required=false)  
    private BigDecimal amount1;   
    
	/*
	 * 金额2
	 */
    @Column(name="AMOUNT2")
    @ValidateRule(dataType=0,label="金额2",required=false)  
    private BigDecimal amount2;   
    
	/*
	 * 金额3
	 */
    @Column(name="AMOUNT3")
    @ValidateRule(dataType=0,label="金额3",required=false)  
    private BigDecimal amount3;   
    
	/*
	 * 金额4
	 */
    @Column(name="AMOUNT4")
    @ValidateRule(dataType=0,label="金额4",required=false)  
    private BigDecimal amount4;   
    
	/*
	 * 本位币金额1
	 */
    @Column(name="STANDARDAMOUNT1")
    @ValidateRule(dataType=0,label="本位币金额1",required=false)  
    private BigDecimal standardamount1;   
    
	/*
	 * 本位币金额2
	 */
    @Column(name="STANDARDAMOUNT2")
    @ValidateRule(dataType=0,label="本位币金额2",required=false)  
    private BigDecimal standardamount2;   
    
	/*
	 * 本位币金额3
	 */
    @Column(name="STANDARDAMOUNT3")
    @ValidateRule(dataType=0,label="本位币金额3",required=false)  
    private BigDecimal standardamount3;   
    
	/*
	 * 本位币金额4
	 */
    @Column(name="STANDARDAMOUNT4")
    @ValidateRule(dataType=0,label="本位币金额4",required=false)  
    private BigDecimal standardamount4;   
    
	/*
	 * 结算科目1
	 */
    @Column(name="SETTLESUBJECT1")
    @ValidateRule(dataType=9,label="结算科目1",maxLength= 50,required=false)  
    private String settlesubject1;   
    
	/*
	 * 结算科目2
	 */
    @Column(name="SETTLESUBJECT2")
    @ValidateRule(dataType=9,label="结算科目2",maxLength= 50,required=false)  
    private String settlesubject2;   
    
	/*
	 * 结算科目3
	 */
    @Column(name="SETTLESUBJECT3")
    @ValidateRule(dataType=9,label="结算科目3",maxLength= 50,required=false)  
    private String settlesubject3;   
    
	/*
	 * 结算科目4
	 */
    @Column(name="SETTLESUBJECT4")
    @ValidateRule(dataType=9,label="结算科目4",maxLength= 50,required=false)  
    private String settlesubject4;   
    
	/*
	 * 成本中心1
	 */
    @Column(name="COSTCENTER1")
    @ValidateRule(dataType=9,label="成本中心1",maxLength= 36,required=false)  
    private String costcenter1;   
    
	/*
	 * 成本中心2
	 */
    @Column(name="COSTCENTER2")
    @ValidateRule(dataType=9,label="成本中心2",maxLength= 36,required=false)  
    private String costcenter2;   
    
	/*
	 * 成本中心3
	 */
    @Column(name="COSTCENTER3")
    @ValidateRule(dataType=9,label="成本中心3",maxLength= 36,required=false)  
    private String costcenter3;   
    
	/*
	 * 利润中心
	 */
    @Column(name="PROFITCENTER")
    @ValidateRule(dataType=9,label="利润中心",maxLength= 100,required=false)  
    private String profitcenter;   
    
	/*
	 * 业务范围1
	 */
    @Column(name="DEPID1")
    @ValidateRule(dataType=9,label="业务范围1",maxLength= 4,required=false)  
    private String depid1;   
    
	/*
	 * 业务范围2
	 */
    @Column(name="DEPID2")
    @ValidateRule(dataType=9,label="业务范围2",maxLength= 4,required=false)  
    private String depid2;   
    
	/*
	 * 业务范围3
	 */
    @Column(name="DEPID3")
    @ValidateRule(dataType=9,label="业务范围3",maxLength= 4,required=false)  
    private String depid3;   
    
	/*
	 * 业务范围4
	 */
    @Column(name="DEPID4")
    @ValidateRule(dataType=9,label="业务范围4",maxLength= 4,required=false)  
    private String depid4;   
    
	/*
	 * 销售订单号1
	 */
    @Column(name="ORDERNO1")
    @ValidateRule(dataType=9,label="销售订单号1",maxLength= 50,required=false)  
    private String orderno1;   
    
	/*
	 * 销售订单号2
	 */
    @Column(name="ORDERNO2")
    @ValidateRule(dataType=9,label="销售订单号2",maxLength= 50,required=false)  
    private String orderno2;   
    
	/*
	 * 销售订单号3
	 */
    @Column(name="ORDERNO3")
    @ValidateRule(dataType=9,label="销售订单号3",maxLength= 50,required=false)  
    private String orderno3;   
    
	/*
	 * 销售订单号4
	 */
    @Column(name="ORDERNO4")
    @ValidateRule(dataType=9,label="销售订单号4",maxLength= 50,required=false)  
    private String orderno4;   
    
	/*
	 * 行项目1
	 */
    @Column(name="ROWNO1")
    @ValidateRule(dataType=9,label="行项目1",maxLength= 50,required=false)  
    private String rowno1;   
    
	/*
	 * 行项目2
	 */
    @Column(name="ROWNO2")
    @ValidateRule(dataType=9,label="行项目2",maxLength= 50,required=false)  
    private String rowno2;   
    
	/*
	 * 行项目3
	 */
    @Column(name="ROWNO3")
    @ValidateRule(dataType=9,label="行项目3",maxLength= 50,required=false)  
    private String rowno3;   
    
	/*
	 * 行项目4
	 */
    @Column(name="ROWNO4")
    @ValidateRule(dataType=9,label="行项目4",maxLength= 50,required=false)  
    private String rowno4;   
    
	/*
	 * 反记账1
	 */
    @Column(name="ANTIACCOUNT1")
    @ValidateRule(dataType=9,label="反记账1",maxLength= 1,required=false)  
    private String antiaccount1;   
    
	/*
	 * 反记账2
	 */
    @Column(name="ANTIACCOUNT2")
    @ValidateRule(dataType=9,label="反记账2",maxLength= 1,required=false)  
    private String antiaccount2;   
    
	/*
	 * 反记账3
	 */
    @Column(name="ANTIACCOUNT3")
    @ValidateRule(dataType=9,label="反记账3",maxLength= 1,required=false)  
    private String antiaccount3;   
    
	/*
	 * 反记账4
	 */
    @Column(name="ANTIACCOUNT4")
    @ValidateRule(dataType=9,label="反记账4",maxLength= 1,required=false)  
    private String antiaccount4;   
    
	/*
	 * 借贷标识2
	 */
    @Column(name="DEBTCREDIT2")
    @ValidateRule(dataType=9,label="借贷标识2",maxLength= 1,required=false)  
    private String debtcredit2;   
    
	/*
	 * 借贷标识3
	 */
    @Column(name="DEBTCREDIT3")
    @ValidateRule(dataType=9,label="借贷标识3",maxLength= 1,required=false)  
    private String debtcredit3;   
    
	/*
	 * 借贷标识4
	 */
    @Column(name="DEBTCREDIT4")
    @ValidateRule(dataType=9,label="借贷标识4",maxLength= 1,required=false)  
    private String debtcredit4;   
    
	/*
	 * 付款ID
	 */
    @Column(name="PAYMENTID")
    @ValidateRule(dataType=9,label="付款ID",maxLength= 36,required=false)  
    private String paymentid;   
    
	/*
	 * 收款ID
	 */
    @Column(name="COLLECTID")
    @ValidateRule(dataType=9,label="收款ID",maxLength= 36,required=false)  
    private String collectid;   
    
//	/*
//	 * 供应商单清帐ID
//	 */
//    @Column(name="SUPPLIERSCLEARID")
//    @ValidateRule(dataType=9,label="供应商单清帐ID",maxLength= 36,required=false)  
//    private String suppliersclearid;   
//    
//	/*
//	 * 客户单清帐ID
//	 */
//    @Column(name="CUSTOMSCLEARID")
//    @ValidateRule(dataType=9,label="客户单清帐ID",maxLength= 36,required=false)  
//    private String customsclearid;   
    
	/*
	 * 客户单清帐
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "CUSTOMSCLEARID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private CustomSingleOther customSingleOther;
	/*
	 * 供应商单清帐
	 */
	@OneToOne(optional = true)
	@JoinColumn(name = "SUPPLIERSCLEARID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private SupplierSinglOther supplierSinglOther;
	
	/*
	 * 退款ID
	 */
    @Column(name="REFUNDMENTID")
    @ValidateRule(dataType=9,label="退款ID",maxLength= 36,required=false)  
    private String refundmentid;   
    
	/*
	 * 退款ID
	 */
    @Column(name="CUSTREFUNDMENTID")
    @ValidateRule(dataType=9,label="退款ID",maxLength= 36,required=false)  
    private String custrefundmentid;   
    
	/*
	 * 出口押汇ID
	 */
    @Column(name="BILLPURID")
    @ValidateRule(dataType=9,label="出口押汇ID",maxLength= 36,required=false)  
    private String billpurid;   
    
	/*
	 * 打包贷款ID
	 */
    @Column(name="PACKINGID")
    @ValidateRule(dataType=9,label="打包贷款ID",maxLength= 36,required=false)  
    private String packingid;   
    
	/*
	 * 数据标识
	 */
    @Column(name="FLAG")
    @ValidateRule(dataType=9,label="数据标识",maxLength= 2,required=false)  
    private String flag;   
    

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
     *    获得结算科目ID
     * @return 结算科目ID : String
     */
    public String getSettlesubjectid()
    {
		return this.settlesubjectid;
    }

    /**
     * 功能描述:
     *    设置结算科目ID
     * @param settlesubjectid : String
     */
    public void setSettlesubjectid(String settlesubjectid)
    {
	    this.settlesubjectid = settlesubjectid;
    }
    

    /**
     * 功能描述:
     *    获得票清款ID
     * @return 票清款ID : String
     */
    public String getBillclearid()
    {
		return this.billclearid;
    }

    /**
     * 功能描述:
     *    设置票清款ID
     * @param billclearid : String
     */
    public void setBillclearid(String billclearid)
    {
	    this.billclearid = billclearid;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit1()
    {
		return this.debtcredit1;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit1 : String
     */
    public void setDebtcredit1(String debtcredit1)
    {
	    this.debtcredit1 = debtcredit1;
    }
    

    /**
     * 功能描述:
     *    获得金额1
     * @return 金额1 : BigDecimal
     */
    public BigDecimal getAmount1()
    {
		return this.amount1;
    }

    /**
     * 功能描述:
     *    设置金额1
     * @param amount1 : BigDecimal
     */
    public void setAmount1(BigDecimal amount1)
    {
	    this.amount1 = amount1;
    }
    

    /**
     * 功能描述:
     *    获得金额2
     * @return 金额2 : BigDecimal
     */
    public BigDecimal getAmount2()
    {
		return this.amount2;
    }

    /**
     * 功能描述:
     *    设置金额2
     * @param amount2 : BigDecimal
     */
    public void setAmount2(BigDecimal amount2)
    {
	    this.amount2 = amount2;
    }
    

    /**
     * 功能描述:
     *    获得金额3
     * @return 金额3 : BigDecimal
     */
    public BigDecimal getAmount3()
    {
		return this.amount3;
    }

    /**
     * 功能描述:
     *    设置金额3
     * @param amount3 : BigDecimal
     */
    public void setAmount3(BigDecimal amount3)
    {
	    this.amount3 = amount3;
    }
    

    /**
     * 功能描述:
     *    获得金额4
     * @return 金额4 : BigDecimal
     */
    public BigDecimal getAmount4()
    {
		return this.amount4;
    }

    /**
     * 功能描述:
     *    设置金额4
     * @param amount4 : BigDecimal
     */
    public void setAmount4(BigDecimal amount4)
    {
	    this.amount4 = amount4;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额1
     * @return 本位币金额1 : BigDecimal
     */
    public BigDecimal getStandardamount1()
    {
		return this.standardamount1;
    }

    /**
     * 功能描述:
     *    设置本位币金额1
     * @param standardamount1 : BigDecimal
     */
    public void setStandardamount1(BigDecimal standardamount1)
    {
	    this.standardamount1 = standardamount1;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额2
     * @return 本位币金额2 : BigDecimal
     */
    public BigDecimal getStandardamount2()
    {
		return this.standardamount2;
    }

    /**
     * 功能描述:
     *    设置本位币金额2
     * @param standardamount2 : BigDecimal
     */
    public void setStandardamount2(BigDecimal standardamount2)
    {
	    this.standardamount2 = standardamount2;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额3
     * @return 本位币金额3 : BigDecimal
     */
    public BigDecimal getStandardamount3()
    {
		return this.standardamount3;
    }

    /**
     * 功能描述:
     *    设置本位币金额3
     * @param standardamount3 : BigDecimal
     */
    public void setStandardamount3(BigDecimal standardamount3)
    {
	    this.standardamount3 = standardamount3;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额4
     * @return 本位币金额4 : BigDecimal
     */
    public BigDecimal getStandardamount4()
    {
		return this.standardamount4;
    }

    /**
     * 功能描述:
     *    设置本位币金额4
     * @param standardamount4 : BigDecimal
     */
    public void setStandardamount4(BigDecimal standardamount4)
    {
	    this.standardamount4 = standardamount4;
    }
    

    /**
     * 功能描述:
     *    获得结算科目1
     * @return 结算科目1 : String
     */
    public String getSettlesubject1()
    {
		return this.settlesubject1;
    }

    /**
     * 功能描述:
     *    设置结算科目1
     * @param settlesubject1 : String
     */
    public void setSettlesubject1(String settlesubject1)
    {
	    this.settlesubject1 = settlesubject1;
    }
    

    /**
     * 功能描述:
     *    获得结算科目2
     * @return 结算科目2 : String
     */
    public String getSettlesubject2()
    {
		return this.settlesubject2;
    }

    /**
     * 功能描述:
     *    设置结算科目2
     * @param settlesubject2 : String
     */
    public void setSettlesubject2(String settlesubject2)
    {
	    this.settlesubject2 = settlesubject2;
    }
    

    /**
     * 功能描述:
     *    获得结算科目3
     * @return 结算科目3 : String
     */
    public String getSettlesubject3()
    {
		return this.settlesubject3;
    }

    /**
     * 功能描述:
     *    设置结算科目3
     * @param settlesubject3 : String
     */
    public void setSettlesubject3(String settlesubject3)
    {
	    this.settlesubject3 = settlesubject3;
    }
    

    /**
     * 功能描述:
     *    获得结算科目4
     * @return 结算科目4 : String
     */
    public String getSettlesubject4()
    {
		return this.settlesubject4;
    }

    /**
     * 功能描述:
     *    设置结算科目4
     * @param settlesubject4 : String
     */
    public void setSettlesubject4(String settlesubject4)
    {
	    this.settlesubject4 = settlesubject4;
    }
    

    /**
     * 功能描述:
     *    获得成本中心1
     * @return 成本中心1 : String
     */
    public String getCostcenter1()
    {
		return this.costcenter1;
    }

    /**
     * 功能描述:
     *    设置成本中心1
     * @param costcenter1 : String
     */
    public void setCostcenter1(String costcenter1)
    {
	    this.costcenter1 = costcenter1;
    }
    

    /**
     * 功能描述:
     *    获得成本中心2
     * @return 成本中心2 : String
     */
    public String getCostcenter2()
    {
		return this.costcenter2;
    }

    /**
     * 功能描述:
     *    设置成本中心2
     * @param costcenter2 : String
     */
    public void setCostcenter2(String costcenter2)
    {
	    this.costcenter2 = costcenter2;
    }
    

    /**
     * 功能描述:
     *    获得成本中心3
     * @return 成本中心3 : String
     */
    public String getCostcenter3()
    {
		return this.costcenter3;
    }

    /**
     * 功能描述:
     *    设置成本中心3
     * @param costcenter3 : String
     */
    public void setCostcenter3(String costcenter3)
    {
	    this.costcenter3 = costcenter3;
    }
    

    /**
     * 功能描述:
     *    获得利润中心
     * @return 利润中心 : String
     */
    public String getProfitcenter()
    {
		return this.profitcenter;
    }

    /**
     * 功能描述:
     *    设置利润中心
     * @param profitcenter : String
     */
    public void setProfitcenter(String profitcenter)
    {
	    this.profitcenter = profitcenter;
    }
    

    /**
     * 功能描述:
     *    获得业务范围1
     * @return 业务范围1 : String
     */
    public String getDepid1()
    {
		return this.depid1;
    }

    /**
     * 功能描述:
     *    设置业务范围1
     * @param depid1 : String
     */
    public void setDepid1(String depid1)
    {
	    this.depid1 = depid1;
    }
    

    /**
     * 功能描述:
     *    获得业务范围2
     * @return 业务范围2 : String
     */
    public String getDepid2()
    {
		return this.depid2;
    }

    /**
     * 功能描述:
     *    设置业务范围2
     * @param depid2 : String
     */
    public void setDepid2(String depid2)
    {
	    this.depid2 = depid2;
    }
    

    /**
     * 功能描述:
     *    获得业务范围3
     * @return 业务范围3 : String
     */
    public String getDepid3()
    {
		return this.depid3;
    }

    /**
     * 功能描述:
     *    设置业务范围3
     * @param depid3 : String
     */
    public void setDepid3(String depid3)
    {
	    this.depid3 = depid3;
    }
    

    /**
     * 功能描述:
     *    获得业务范围4
     * @return 业务范围4 : String
     */
    public String getDepid4()
    {
		return this.depid4;
    }

    /**
     * 功能描述:
     *    设置业务范围4
     * @param depid4 : String
     */
    public void setDepid4(String depid4)
    {
	    this.depid4 = depid4;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号1
     * @return 销售订单号1 : String
     */
    public String getOrderno1()
    {
		return this.orderno1;
    }

    /**
     * 功能描述:
     *    设置销售订单号1
     * @param orderno1 : String
     */
    public void setOrderno1(String orderno1)
    {
	    this.orderno1 = orderno1;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号2
     * @return 销售订单号2 : String
     */
    public String getOrderno2()
    {
		return this.orderno2;
    }

    /**
     * 功能描述:
     *    设置销售订单号2
     * @param orderno2 : String
     */
    public void setOrderno2(String orderno2)
    {
	    this.orderno2 = orderno2;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号3
     * @return 销售订单号3 : String
     */
    public String getOrderno3()
    {
		return this.orderno3;
    }

    /**
     * 功能描述:
     *    设置销售订单号3
     * @param orderno3 : String
     */
    public void setOrderno3(String orderno3)
    {
	    this.orderno3 = orderno3;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号4
     * @return 销售订单号4 : String
     */
    public String getOrderno4()
    {
		return this.orderno4;
    }

    /**
     * 功能描述:
     *    设置销售订单号4
     * @param orderno4 : String
     */
    public void setOrderno4(String orderno4)
    {
	    this.orderno4 = orderno4;
    }
    

    /**
     * 功能描述:
     *    获得行项目1
     * @return 行项目1 : String
     */
    public String getRowno1()
    {
		return this.rowno1;
    }

    /**
     * 功能描述:
     *    设置行项目1
     * @param rowno1 : String
     */
    public void setRowno1(String rowno1)
    {
	    this.rowno1 = rowno1;
    }
    

    /**
     * 功能描述:
     *    获得行项目2
     * @return 行项目2 : String
     */
    public String getRowno2()
    {
		return this.rowno2;
    }

    /**
     * 功能描述:
     *    设置行项目2
     * @param rowno2 : String
     */
    public void setRowno2(String rowno2)
    {
	    this.rowno2 = rowno2;
    }
    

    /**
     * 功能描述:
     *    获得行项目3
     * @return 行项目3 : String
     */
    public String getRowno3()
    {
		return this.rowno3;
    }

    /**
     * 功能描述:
     *    设置行项目3
     * @param rowno3 : String
     */
    public void setRowno3(String rowno3)
    {
	    this.rowno3 = rowno3;
    }
    

    /**
     * 功能描述:
     *    获得行项目4
     * @return 行项目4 : String
     */
    public String getRowno4()
    {
		return this.rowno4;
    }

    /**
     * 功能描述:
     *    设置行项目4
     * @param rowno4 : String
     */
    public void setRowno4(String rowno4)
    {
	    this.rowno4 = rowno4;
    }
    

    /**
     * 功能描述:
     *    获得反记账1
     * @return 反记账1 : String
     */
    public String getAntiaccount1()
    {
		return this.antiaccount1;
    }

    /**
     * 功能描述:
     *    设置反记账1
     * @param antiaccount1 : String
     */
    public void setAntiaccount1(String antiaccount1)
    {
	    this.antiaccount1 = antiaccount1;
    }
    

    /**
     * 功能描述:
     *    获得反记账2
     * @return 反记账2 : String
     */
    public String getAntiaccount2()
    {
		return this.antiaccount2;
    }

    /**
     * 功能描述:
     *    设置反记账2
     * @param antiaccount2 : String
     */
    public void setAntiaccount2(String antiaccount2)
    {
	    this.antiaccount2 = antiaccount2;
    }
    

    /**
     * 功能描述:
     *    获得反记账3
     * @return 反记账3 : String
     */
    public String getAntiaccount3()
    {
		return this.antiaccount3;
    }

    /**
     * 功能描述:
     *    设置反记账3
     * @param antiaccount3 : String
     */
    public void setAntiaccount3(String antiaccount3)
    {
	    this.antiaccount3 = antiaccount3;
    }
    

    /**
     * 功能描述:
     *    获得反记账4
     * @return 反记账4 : String
     */
    public String getAntiaccount4()
    {
		return this.antiaccount4;
    }

    /**
     * 功能描述:
     *    设置反记账4
     * @param antiaccount4 : String
     */
    public void setAntiaccount4(String antiaccount4)
    {
	    this.antiaccount4 = antiaccount4;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识2
     * @return 借贷标识2 : String
     */
    public String getDebtcredit2()
    {
		return this.debtcredit2;
    }

    /**
     * 功能描述:
     *    设置借贷标识2
     * @param debtcredit2 : String
     */
    public void setDebtcredit2(String debtcredit2)
    {
	    this.debtcredit2 = debtcredit2;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识3
     * @return 借贷标识3 : String
     */
    public String getDebtcredit3()
    {
		return this.debtcredit3;
    }

    /**
     * 功能描述:
     *    设置借贷标识3
     * @param debtcredit3 : String
     */
    public void setDebtcredit3(String debtcredit3)
    {
	    this.debtcredit3 = debtcredit3;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识4
     * @return 借贷标识4 : String
     */
    public String getDebtcredit4()
    {
		return this.debtcredit4;
    }

    /**
     * 功能描述:
     *    设置借贷标识4
     * @param debtcredit4 : String
     */
    public void setDebtcredit4(String debtcredit4)
    {
	    this.debtcredit4 = debtcredit4;
    }
    

    /**
     * 功能描述:
     *    获得付款ID
     * @return 付款ID : String
     */
    public String getPaymentid()
    {
		return this.paymentid;
    }

    /**
     * 功能描述:
     *    设置付款ID
     * @param paymentid : String
     */
    public void setPaymentid(String paymentid)
    {
	    this.paymentid = paymentid;
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
    

//    /**
//     * 功能描述:
//     *    获得供应商单清帐ID
//     * @return 供应商单清帐ID : String
//     */
//    public String getSuppliersclearid()
//    {
//		return this.suppliersclearid;
//    }
//
//    /**
//     * 功能描述:
//     *    设置供应商单清帐ID
//     * @param suppliersclearid : String
//     */
//    public void setSuppliersclearid(String suppliersclearid)
//    {
//	    this.suppliersclearid = suppliersclearid;
//    }
//    
//
//    /**
//     * 功能描述:
//     *    获得客户单清帐ID
//     * @return 客户单清帐ID : String
//     */
//    public String getCustomsclearid()
//    {
//		return this.customsclearid;
//    }
//
//    /**
//     * 功能描述:
//     *    设置客户单清帐ID
//     * @param customsclearid : String
//     */
//    public void setCustomsclearid(String customsclearid)
//    {
//	    this.customsclearid = customsclearid;
//    }
    

    /**
     * 功能描述:
     *    获得退款ID
     * @return 退款ID : String
     */
    public String getRefundmentid()
    {
		return this.refundmentid;
    }

    /**
     * 功能描述:
     *    设置退款ID
     * @param refundmentid : String
     */
    public void setRefundmentid(String refundmentid)
    {
	    this.refundmentid = refundmentid;
    }
    

    /**
     * 功能描述:
     *    获得退款ID
     * @return 退款ID : String
     */
    public String getCustrefundmentid()
    {
		return this.custrefundmentid;
    }

    /**
     * 功能描述:
     *    设置退款ID
     * @param custrefundmentid : String
     */
    public void setCustrefundmentid(String custrefundmentid)
    {
	    this.custrefundmentid = custrefundmentid;
    }
    

    /**
     * 功能描述:
     *    获得出口押汇ID
     * @return 出口押汇ID : String
     */
    public String getBillpurid()
    {
		return this.billpurid;
    }

    /**
     * 功能描述:
     *    设置出口押汇ID
     * @param billpurid : String
     */
    public void setBillpurid(String billpurid)
    {
	    this.billpurid = billpurid;
    }
    

    /**
     * 功能描述:
     *    获得打包贷款ID
     * @return 打包贷款ID : String
     */
    public String getPackingid()
    {
		return this.packingid;
    }

    /**
     * 功能描述:
     *    设置打包贷款ID
     * @param packingid : String
     */
    public void setPackingid(String packingid)
    {
	    this.packingid = packingid;
    }
    

    /**
     * 功能描述:
     *    获得数据标识
     * @return 数据标识 : String
     */
    public String getFlag()
    {
		return this.flag;
    }

    /**
     * 功能描述:
     *    设置数据标识
     * @param flag : String
     */
    public void setFlag(String flag)
    {
	    this.flag = flag;
    }
    
    
	/**
	 *  默认构造器
	 */
    public SettleSubjectOther()
    {
    	super();
    }
    
    /*
	 * 结算科目1
	 */

	@Transient
	private String settlesubject1_htext;

	/*
	 * 结算科目2
	 */
	@Transient
	private String settlesubject2_htext;

	/*
	 * 结算科目1
	 */
	@Transient
	private String settlesubject3_htext;

	/*
	 * 结算科目1
	 */
	@Transient
	private String settlesubject4_htext;

	/*
	 * 成本中心
	 */
	@Transient
	private String costcenter1_htext;

	/*
	 * 成本中心
	 */
	@Transient
	private String costcenter2_htext;

	/*
	 * 成本中心
	 */
	@Transient
	private String costcenter3_htext;

	/*
	 * 利润中心
	 */
	@Transient
	private String profitcenter_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid1_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid2_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid3_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid4_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno1_htext;

	/*
	 * 销售订单2
	 */
	@Transient
	private String orderno2_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno3_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno4_htext;


	/**
	 * @return the settlesubject1_htext
	 */
	public String getSettlesubject1_htext() {
		return settlesubject1_htext;
	}

	/**
	 * @param settlesubject1_htext the settlesubject1_htext to set
	 */
	public void setSettlesubject1_htext(String settlesubject1_htext) {
		this.settlesubject1_htext = settlesubject1_htext;
	}

	/**
	 * @return the settlesubject2_htext
	 */
	public String getSettlesubject2_htext() {
		return settlesubject2_htext;
	}

	/**
	 * @param settlesubject2_htext the settlesubject2_htext to set
	 */
	public void setSettlesubject2_htext(String settlesubject2_htext) {
		this.settlesubject2_htext = settlesubject2_htext;
	}

	/**
	 * @return the settlesubject3_htext
	 */
	public String getSettlesubject3_htext() {
		return settlesubject3_htext;
	}

	/**
	 * @param settlesubject3_htext the settlesubject3_htext to set
	 */
	public void setSettlesubject3_htext(String settlesubject3_htext) {
		this.settlesubject3_htext = settlesubject3_htext;
	}

	/**
	 * @return the settlesubject4_htext
	 */
	public String getSettlesubject4_htext() {
		return settlesubject4_htext;
	}

	/**
	 * @param settlesubject4_htext the settlesubject4_htext to set
	 */
	public void setSettlesubject4_htext(String settlesubject4_htext) {
		this.settlesubject4_htext = settlesubject4_htext;
	}

	/**
	 * @return the costcenter1_htext
	 */
	public String getCostcenter1_htext() {
		return costcenter1_htext;
	}

	/**
	 * @param costcenter1_htext the costcenter1_htext to set
	 */
	public void setCostcenter1_htext(String costcenter1_htext) {
		this.costcenter1_htext = costcenter1_htext;
	}

	/**
	 * @return the costcenter2_htext
	 */
	public String getCostcenter2_htext() {
		return costcenter2_htext;
	}

	/**
	 * @param costcenter2_htext the costcenter2_htext to set
	 */
	public void setCostcenter2_htext(String costcenter2_htext) {
		this.costcenter2_htext = costcenter2_htext;
	}

	/**
	 * @return the costcenter3_htext
	 */
	public String getCostcenter3_htext() {
		return costcenter3_htext;
	}

	/**
	 * @param costcenter3_htext the costcenter3_htext to set
	 */
	public void setCostcenter3_htext(String costcenter3_htext) {
		this.costcenter3_htext = costcenter3_htext;
	}

	/**
	 * @return the profitcenter_htext
	 */
	public String getProfitcenter_htext() {
		return profitcenter_htext;
	}

	/**
	 * @param profitcenter_htext the profitcenter_htext to set
	 */
	public void setProfitcenter_htext(String profitcenter_htext) {
		this.profitcenter_htext = profitcenter_htext;
	}

	/**
	 * @return the depid1_htext
	 */
	public String getDepid1_htext() {
		return depid1_htext;
	}

	/**
	 * @param depid1_htext the depid1_htext to set
	 */
	public void setDepid1_htext(String depid1_htext) {
		this.depid1_htext = depid1_htext;
	}

	/**
	 * @return the depid2_htext
	 */
	public String getDepid2_htext() {
		return depid2_htext;
	}

	/**
	 * @param depid2_htext the depid2_htext to set
	 */
	public void setDepid2_htext(String depid2_htext) {
		this.depid2_htext = depid2_htext;
	}

	/**
	 * @return the depid3_htext
	 */
	public String getDepid3_htext() {
		return depid3_htext;
	}

	/**
	 * @param depid3_htext the depid3_htext to set
	 */
	public void setDepid3_htext(String depid3_htext) {
		this.depid3_htext = depid3_htext;
	}

	/**
	 * @return the depid4_htext
	 */
	public String getDepid4_htext() {
		return depid4_htext;
	}

	/**
	 * @param depid4_htext the depid4_htext to set
	 */
	public void setDepid4_htext(String depid4_htext) {
		this.depid4_htext = depid4_htext;
	}

	/**
	 * @return the orderno1_htext
	 */
	public String getOrderno1_htext() {
		return orderno1_htext;
	}

	/**
	 * @param orderno1_htext the orderno1_htext to set
	 */
	public void setOrderno1_htext(String orderno1_htext) {
		this.orderno1_htext = orderno1_htext;
	}

	/**
	 * @return the orderno2_htext
	 */
	public String getOrderno2_htext() {
		return orderno2_htext;
	}

	/**
	 * @param orderno2_htext the orderno2_htext to set
	 */
	public void setOrderno2_htext(String orderno2_htext) {
		this.orderno2_htext = orderno2_htext;
	}

	/**
	 * @return the orderno3_htext
	 */
	public String getOrderno3_htext() {
		return orderno3_htext;
	}

	/**
	 * @param orderno3_htext the orderno3_htext to set
	 */
	public void setOrderno3_htext(String orderno3_htext) {
		this.orderno3_htext = orderno3_htext;
	}

	/**
	 * @return the orderno4_htext
	 */
	public String getOrderno4_htext() {
		return orderno4_htext;
	}

	/**
	 * @param orderno4_htext the orderno4_htext to set
	 */
	public void setOrderno4_htext(String orderno4_htext) {
		this.orderno4_htext = orderno4_htext;
	}
	



	/**
	 * @return the customSingleOther
	 */
	public CustomSingleOther getCustomSingleOther() {
		return customSingleOther;
	}

	/**
	 * @param customSingleOther the customSingleOther to set
	 */
	public void setCustomSingleOther(CustomSingleOther customSingleOther) {
		this.customSingleOther = customSingleOther;
	}

	/**
	 * @return the supplierSinglOther
	 */
	public SupplierSinglOther getSupplierSinglOther() {
		return supplierSinglOther;
	}

	/**
	 * @param supplierSinglOther the supplierSinglOther to set
	 */
	public void setSupplierSinglOther(SupplierSinglOther supplierSinglOther) {
		this.supplierSinglOther = supplierSinglOther;
	}
	
}
