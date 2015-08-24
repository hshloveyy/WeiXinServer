/*
 * @(#)SettleSubjectBcc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月16日 09点35分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.settlesubjectbcc.domain;

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
import com.infolion.xdss3.financeSquare.domain.ISettleSubject;

/**
 * <pre>
 * 结算科目(SettleSubjectBcc)实体类
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
@Table(name = "YSETTLESUBJECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class SettleSubjectBcc extends BaseObject implements ISettleSubject
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
	 * 本位币金额1
	 */
    @Column(name="STANDARDAMOUNT2")
    @ValidateRule(dataType=0,label="本位币金额1",required=false)  
    private BigDecimal standardamount2;   
    
	/*
	 * 本位币金额1
	 */
    @Column(name="STANDARDAMOUNT3")
    @ValidateRule(dataType=0,label="本位币金额1",required=false)  
    private BigDecimal standardamount3;   
    
	/*
	 * 本位币金额1
	 */
    @Column(name="STANDARDAMOUNT4")
    @ValidateRule(dataType=0,label="本位币金额1",required=false)  
    private BigDecimal standardamount4;   
    
	/*
	 * 结算科目
	 */
    @Column(name="SETTLESUBJECT1")
    @ValidateRule(dataType=9,label="结算科目",maxLength= 50,required=false)  
    private String settlesubject1;   
    
	/*
	 * 结算科目
	 */
    @Column(name="SETTLESUBJECT2")
    @ValidateRule(dataType=9,label="结算科目",maxLength= 50,required=false)  
    private String settlesubject2;   
    
	/*
	 * 结算科目
	 */
    @Column(name="SETTLESUBJECT3")
    @ValidateRule(dataType=9,label="结算科目",maxLength= 50,required=false)  
    private String settlesubject3;   
    
	/*
	 * 结算科目
	 */
    @Column(name="SETTLESUBJECT4")
    @ValidateRule(dataType=9,label="结算科目",maxLength= 50,required=false)  
    private String settlesubject4;   
    
	/*
	 * 成本中心
	 */
    @Column(name="COSTCENTER1")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 36,required=false)  
    private String costcenter1;   
    
	/*
	 * 成本中心
	 */
    @Column(name="COSTCENTER2")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 36,required=false)  
    private String costcenter2;   
    
	/*
	 * 成本中心
	 */
    @Column(name="COSTCENTER3")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 36,required=false)  
    private String costcenter3;   
    
	/*
	 * 利润中心
	 */
    @Column(name="PROFITCENTER")
    @ValidateRule(dataType=9,label="利润中心",maxLength= 100,required=false)  
    private String profitcenter;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID1")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid1;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID2")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid2;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID3")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid3;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID4")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 36,required=false)  
    private String depid4;   
    
	/*
	 * 销售订单号
	 */
    @Column(name="ORDERNO1")
    @ValidateRule(dataType=9,label="销售订单号",maxLength= 50,required=false)  
    private String orderno1;   
    
	/*
	 * 销售订单号
	 */
    @Column(name="ORDERNO2")
    @ValidateRule(dataType=9,label="销售订单号",maxLength= 50,required=false)  
    private String orderno2;   
    
	/*
	 * 销售订单号
	 */
    @Column(name="ORDERNO3")
    @ValidateRule(dataType=9,label="销售订单号",maxLength= 50,required=false)  
    private String orderno3;   
    
	/*
	 * 销售订单号
	 */
    @Column(name="ORDERNO4")
    @ValidateRule(dataType=9,label="销售订单号",maxLength= 50,required=false)  
    private String orderno4;   
    
	/*
	 * 行号
	 */
    @Column(name="ROWNO1")
    @ValidateRule(dataType=9,label="行号",maxLength= 10,required=false)  
    private String rowno1;   
    
	/*
	 * 行号
	 */
    @Column(name="ROWNO2")
    @ValidateRule(dataType=9,label="行号",maxLength= 10,required=false)  
    private String rowno2;   
    
	/*
	 * 行号
	 */
    @Column(name="ROWNO3")
    @ValidateRule(dataType=9,label="行号",maxLength= 10,required=false)  
    private String rowno3;   
    
	/*
	 * 行号
	 */
    @Column(name="ROWNO4")
    @ValidateRule(dataType=9,label="行号",maxLength= 10,required=false)  
    private String rowno4;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT1")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount1;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT2")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount2;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT3")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount3;   
    
	/*
	 * 反记账
	 */
    @Column(name="ANTIACCOUNT4")
    @ValidateRule(dataType=9,label="反记账",maxLength= 1,required=false)  
    private String antiaccount4;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT2")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit2;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT3")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
    private String debtcredit3;   
    
	/*
	 * 借贷标识
	 */
    @Column(name="DEBTCREDIT4")
    @ValidateRule(dataType=9,label="借贷标识",maxLength= 1,required=false)  
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
    
	/*
	 * 票清收款
	 */
    @OneToOne(optional=true)
    @JoinColumn(name = "BILLCLEARID",nullable = true)
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
     *    获得本位币金额1
     * @return 本位币金额1 : BigDecimal
     */
    public BigDecimal getStandardamount2()
    {
		return this.standardamount2;
    }

    /**
     * 功能描述:
     *    设置本位币金额1
     * @param standardamount2 : BigDecimal
     */
    public void setStandardamount2(BigDecimal standardamount2)
    {
	    this.standardamount2 = standardamount2;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额1
     * @return 本位币金额1 : BigDecimal
     */
    public BigDecimal getStandardamount3()
    {
		return this.standardamount3;
    }

    /**
     * 功能描述:
     *    设置本位币金额1
     * @param standardamount3 : BigDecimal
     */
    public void setStandardamount3(BigDecimal standardamount3)
    {
	    this.standardamount3 = standardamount3;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额1
     * @return 本位币金额1 : BigDecimal
     */
    public BigDecimal getStandardamount4()
    {
		return this.standardamount4;
    }

    /**
     * 功能描述:
     *    设置本位币金额1
     * @param standardamount4 : BigDecimal
     */
    public void setStandardamount4(BigDecimal standardamount4)
    {
	    this.standardamount4 = standardamount4;
    }
    

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : String
     */
    public String getSettlesubject1()
    {
		return this.settlesubject1;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settlesubject1 : String
     */
    public void setSettlesubject1(String settlesubject1)
    {
	    this.settlesubject1 = settlesubject1;
    }
    

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : String
     */
    public String getSettlesubject2()
    {
		return this.settlesubject2;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settlesubject2 : String
     */
    public void setSettlesubject2(String settlesubject2)
    {
	    this.settlesubject2 = settlesubject2;
    }
    

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : String
     */
    public String getSettlesubject3()
    {
		return this.settlesubject3;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settlesubject3 : String
     */
    public void setSettlesubject3(String settlesubject3)
    {
	    this.settlesubject3 = settlesubject3;
    }
    

    /**
     * 功能描述:
     *    获得结算科目
     * @return 结算科目 : String
     */
    public String getSettlesubject4()
    {
		return this.settlesubject4;
    }

    /**
     * 功能描述:
     *    设置结算科目
     * @param settlesubject4 : String
     */
    public void setSettlesubject4(String settlesubject4)
    {
	    this.settlesubject4 = settlesubject4;
    }
    

    /**
     * 功能描述:
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getCostcenter1()
    {
		return this.costcenter1;
    }

    /**
     * 功能描述:
     *    设置成本中心
     * @param costcenter1 : String
     */
    public void setCostcenter1(String costcenter1)
    {
	    this.costcenter1 = costcenter1;
    }
    

    /**
     * 功能描述:
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getCostcenter2()
    {
		return this.costcenter2;
    }

    /**
     * 功能描述:
     *    设置成本中心
     * @param costcenter2 : String
     */
    public void setCostcenter2(String costcenter2)
    {
	    this.costcenter2 = costcenter2;
    }
    

    /**
     * 功能描述:
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getCostcenter3()
    {
		return this.costcenter3;
    }

    /**
     * 功能描述:
     *    设置成本中心
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
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid1()
    {
		return this.depid1;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid1 : String
     */
    public void setDepid1(String depid1)
    {
	    this.depid1 = depid1;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid2()
    {
		return this.depid2;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid2 : String
     */
    public void setDepid2(String depid2)
    {
	    this.depid2 = depid2;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid3()
    {
		return this.depid3;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid3 : String
     */
    public void setDepid3(String depid3)
    {
	    this.depid3 = depid3;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid4()
    {
		return this.depid4;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid4 : String
     */
    public void setDepid4(String depid4)
    {
	    this.depid4 = depid4;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号
     * @return 销售订单号 : String
     */
    public String getOrderno1()
    {
		return this.orderno1;
    }

    /**
     * 功能描述:
     *    设置销售订单号
     * @param orderno1 : String
     */
    public void setOrderno1(String orderno1)
    {
	    this.orderno1 = orderno1;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号
     * @return 销售订单号 : String
     */
    public String getOrderno2()
    {
		return this.orderno2;
    }

    /**
     * 功能描述:
     *    设置销售订单号
     * @param orderno2 : String
     */
    public void setOrderno2(String orderno2)
    {
	    this.orderno2 = orderno2;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号
     * @return 销售订单号 : String
     */
    public String getOrderno3()
    {
		return this.orderno3;
    }

    /**
     * 功能描述:
     *    设置销售订单号
     * @param orderno3 : String
     */
    public void setOrderno3(String orderno3)
    {
	    this.orderno3 = orderno3;
    }
    

    /**
     * 功能描述:
     *    获得销售订单号
     * @return 销售订单号 : String
     */
    public String getOrderno4()
    {
		return this.orderno4;
    }

    /**
     * 功能描述:
     *    设置销售订单号
     * @param orderno4 : String
     */
    public void setOrderno4(String orderno4)
    {
	    this.orderno4 = orderno4;
    }
    

    /**
     * 功能描述:
     *    获得行号
     * @return 行号 : String
     */
    public String getRowno1()
    {
		return this.rowno1;
    }

    /**
     * 功能描述:
     *    设置行号
     * @param rowno1 : String
     */
    public void setRowno1(String rowno1)
    {
	    this.rowno1 = rowno1;
    }
    

    /**
     * 功能描述:
     *    获得行号
     * @return 行号 : String
     */
    public String getRowno2()
    {
		return this.rowno2;
    }

    /**
     * 功能描述:
     *    设置行号
     * @param rowno2 : String
     */
    public void setRowno2(String rowno2)
    {
	    this.rowno2 = rowno2;
    }
    

    /**
     * 功能描述:
     *    获得行号
     * @return 行号 : String
     */
    public String getRowno3()
    {
		return this.rowno3;
    }

    /**
     * 功能描述:
     *    设置行号
     * @param rowno3 : String
     */
    public void setRowno3(String rowno3)
    {
	    this.rowno3 = rowno3;
    }
    

    /**
     * 功能描述:
     *    获得行号
     * @return 行号 : String
     */
    public String getRowno4()
    {
		return this.rowno4;
    }

    /**
     * 功能描述:
     *    设置行号
     * @param rowno4 : String
     */
    public void setRowno4(String rowno4)
    {
	    this.rowno4 = rowno4;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount1()
    {
		return this.antiaccount1;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount1 : String
     */
    public void setAntiaccount1(String antiaccount1)
    {
	    this.antiaccount1 = antiaccount1;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount2()
    {
		return this.antiaccount2;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount2 : String
     */
    public void setAntiaccount2(String antiaccount2)
    {
	    this.antiaccount2 = antiaccount2;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount3()
    {
		return this.antiaccount3;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount3 : String
     */
    public void setAntiaccount3(String antiaccount3)
    {
	    this.antiaccount3 = antiaccount3;
    }
    

    /**
     * 功能描述:
     *    获得反记账
     * @return 反记账 : String
     */
    public String getAntiaccount4()
    {
		return this.antiaccount4;
    }

    /**
     * 功能描述:
     *    设置反记账
     * @param antiaccount4 : String
     */
    public void setAntiaccount4(String antiaccount4)
    {
	    this.antiaccount4 = antiaccount4;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit2()
    {
		return this.debtcredit2;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit2 : String
     */
    public void setDebtcredit2(String debtcredit2)
    {
	    this.debtcredit2 = debtcredit2;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit3()
    {
		return this.debtcredit3;
    }

    /**
     * 功能描述:
     *    设置借贷标识
     * @param debtcredit3 : String
     */
    public void setDebtcredit3(String debtcredit3)
    {
	    this.debtcredit3 = debtcredit3;
    }
    

    /**
     * 功能描述:
     *    获得借贷标识
     * @return 借贷标识 : String
     */
    public String getDebtcredit4()
    {
		return this.debtcredit4;
    }

    /**
     * 功能描述:
     *    设置借贷标识
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
	 *  默认构造器
	 */
    public SettleSubjectBcc()
    {
    	super();
    }


}
