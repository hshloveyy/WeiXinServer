/*
 * @(#)BillPurBillItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 16点10分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.billPurBillItem.domain;

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
import com.infolion.xdss3.billpurchased.domain.BillPurchased;

/**
 * <pre>
 * 发票(BillPurBillItem)实体类
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
@Table(name = "YBILLPURBILLITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BillPurBillItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 出口押汇发票行项ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BILLITEMID")
      
    private String billitemid;   
    
	/*
	 * 发票号
	 */
    @Column(name="BILL_NO")
    @ValidateRule(dataType=9,label="发票号",maxLength= 1024,required=false)  
    private String bill_no;   
    
	/*
	 * 金额
	 */
    @Column(name="AMOUNT")
    @ValidateRule(dataType=9,label="金额",maxLength= 18,required=false)  
    private String amount;   
    
	/*
	 * 出单日期
	 */
    @Column(name="EXAMDATE")
    @ValidateRule(dataType=9,label="出单日期",maxLength= 20,required=false)  
    private String examdate;   
    
	/*
	 * 票据类型
	 */
    @Column(name="BILLTYPE")
    @ValidateRule(dataType=9,label="票据类型",maxLength= 4,required=false)  
    private String billtype;   
    
	/*
	 * LC/DP/DANO
	 */
    @Column(name="LCDPDANO")
    @ValidateRule(dataType=9,label="LC/DP/DANO",maxLength= 1024,required=false)  
    private String lcdpdano;   
    
	/*
	 * 核销单号
	 */
    @Column(name="WRITE_NO")
    @ValidateRule(dataType=9,label="核销单号",maxLength= 1024,required=false)  
    private String write_no;   
    
	/*
	 * 合同编号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同编号",maxLength= 1024,required=false)  
    private String contract_no;   
    
	/*
	 * 议付银行
	 */
    @Column(name="BANKID")
    @ValidateRule(dataType=9,label="议付银行",maxLength= 36,required=false)  
    private String bankid;   
    
	/*
	 * 银行名称
	 */
    @Column(name="BANKNAME")
    @ValidateRule(dataType=9,label="银行名称",maxLength= 60,required=false)  
    private String bankname;   
    
	/*
	 * 应收汇日
	 */
    @Column(name="SHOULDINCOMEDATE")
    @ValidateRule(dataType=9,label="应收汇日",maxLength= 20,required=false)  
    private String shouldincomedate;   
    
	/*
	 * 出口押汇
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="BILLPURID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private BillPurchased billPurchased;   
    

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
     *    获得出口押汇发票行项ID
     * @return 出口押汇发票行项ID : String
     */
    public String getBillitemid()
    {
		return this.billitemid;
    }

    /**
     * 功能描述:
     *    设置出口押汇发票行项ID
     * @param billitemid : String
     */
    public void setBillitemid(String billitemid)
    {
	    this.billitemid = billitemid;
    }
    

    /**
     * 功能描述:
     *    获得发票号
     * @return 发票号 : String
     */
    public String getBill_no()
    {
		return this.bill_no;
    }

    /**
     * 功能描述:
     *    设置发票号
     * @param bill_no : String
     */
    public void setBill_no(String bill_no)
    {
	    this.bill_no = bill_no;
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
     *    获得出单日期
     * @return 出单日期 : String
     */
    public String getExamdate()
    {
		return this.examdate;
    }

    /**
     * 功能描述:
     *    设置出单日期
     * @param examdate : String
     */
    public void setExamdate(String examdate)
    {
	    this.examdate = examdate;
    }
    

    /**
     * 功能描述:
     *    获得票据类型
     * @return 票据类型 : String
     */
    public String getBilltype()
    {
		return this.billtype;
    }

    /**
     * 功能描述:
     *    设置票据类型
     * @param billtype : String
     */
    public void setBilltype(String billtype)
    {
	    this.billtype = billtype;
    }
    

    /**
     * 功能描述:
     *    获得LC/DP/DANO
     * @return LC/DP/DANO : String
     */
    public String getLcdpdano()
    {
		return this.lcdpdano;
    }

    /**
     * 功能描述:
     *    设置LC/DP/DANO
     * @param lcdpdano : String
     */
    public void setLcdpdano(String lcdpdano)
    {
	    this.lcdpdano = lcdpdano;
    }
    

    /**
     * 功能描述:
     *    获得核销单号
     * @return 核销单号 : String
     */
    public String getWrite_no()
    {
		return this.write_no;
    }

    /**
     * 功能描述:
     *    设置核销单号
     * @param write_no : String
     */
    public void setWrite_no(String write_no)
    {
	    this.write_no = write_no;
    }
    

    /**
     * 功能描述:
     *    获得合同编号
     * @return 合同编号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置合同编号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
    }
    

    /**
     * 功能描述:
     *    获得议付银行
     * @return 议付银行 : String
     */
    public String getBankid()
    {
		return this.bankid;
    }

    /**
     * 功能描述:
     *    设置议付银行
     * @param bankid : String
     */
    public void setBankid(String bankid)
    {
	    this.bankid = bankid;
    }
    

    /**
     * 功能描述:
     *    获得银行名称
     * @return 银行名称 : String
     */
    public String getBankname()
    {
		return this.bankname;
    }

    /**
     * 功能描述:
     *    设置银行名称
     * @param bankname : String
     */
    public void setBankname(String bankname)
    {
	    this.bankname = bankname;
    }
    

    /**
     * 功能描述:
     *    获得应收汇日
     * @return 应收汇日 : String
     */
    public String getShouldincomedate()
    {
		return this.shouldincomedate;
    }

    /**
     * 功能描述:
     *    设置应收汇日
     * @param shouldincomedate : String
     */
    public void setShouldincomedate(String shouldincomedate)
    {
	    this.shouldincomedate = shouldincomedate;
    }
    

    /**
     * 功能描述:
     *    获得出口押汇
     * @return 出口押汇 : BillPurchased
     */
    public BillPurchased getBillPurchased()
    {
		return this.billPurchased;
    }

    /**
     * 功能描述:
     *    设置出口押汇
     * @param billPurchased : BillPurchased
     */
    public void setBillPurchased(BillPurchased billPurchased)
    {
	    this.billPurchased = billPurchased;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BillPurBillItem()
    {
    	super();
    }
}
