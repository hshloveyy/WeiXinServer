/*
 * @(#)VoucherItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月27日 07点47分03秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucheritem.domain;

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
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 * <pre>
 * 凭证预览明细(VoucherItem)实体类
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
@Table(name = "YVOUCHERITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class VoucherItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 凭证预览明细ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="VOUCHERITEMID")
      
    private String voucheritemid;   
    
	/*
	 * 行号
	 */
    @Column(name="ROWNUMBER")
    @ValidateRule(dataType=9,label="行号",maxLength= 10,required=false)  
    private String rownumber;   
    
	/*
	 * 记账代码
	 */
    @Column(name="CHECKCODE")
    @ValidateRule(dataType=9,label="记账代码",maxLength= 50,required=false)  
    private String checkcode;   
    
	/*
	 * 特殊G/L标识
	 */
    @Column(name="GLFLAG")
    @ValidateRule(dataType=9,label="特殊G/L标识",maxLength= 1,required=false)  
    private String glflag;   
    
	/*
	 * 对应会计科目
	 */
    @Column(name="SUBJECT")
    @ValidateRule(dataType=9,label="对应会计科目",maxLength= 36,required=false)  
    private String subject;   
    
	/*
	 * 科目说明
	 */
    @Column(name="SUBJECTDESCRIBE")
    @ValidateRule(dataType=9,label="科目说明",maxLength= 255,required=false)  
    private String subjectdescribe;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 金额
	 */
    @Column(name="AMOUNT")
    @ValidateRule(dataType=0,label="金额",required=false)  
    private BigDecimal amount;   
    
	/*
	 * 本位币金额
	 */
    @Column(name="AMOUNT2")
    @ValidateRule(dataType=0,label="本位币金额",required=false)  
    private BigDecimal amount2;   
    
	/*
	 * 业务范围
	 */
    @Column(name="DEPID")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 4,required=false)  
    private String depid;   
    
	/*
	 * 消息文本
	 */
    @Column(name="TEXT")
    @ValidateRule(dataType=9,label="消息文本",maxLength= 150,required=false)  
    private String text;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 反记账标识
	 */
    @Column(name="UNCHECKFLAG")
    @ValidateRule(dataType=9,label="反记账标识",maxLength= 1,required=false)  
    private String uncheckflag;   
    
	/*
	 * 统驭项目
	 */
    @Column(name="CONTROLACCOUNT")
    @ValidateRule(dataType=9,label="统驭项目",maxLength= 20,required=false)  
    private String controlaccount;   
    
	/*
	 * 统驭科目说明
	 */
    @Column(name="CAREMARK")
    @ValidateRule(dataType=9,label="统驭科目说明",maxLength= 255,required=false)  
    private String caremark;   
    
	/*
	 * 销售订单
	 */
    @Column(name="SALESORDER")
    @ValidateRule(dataType=9,label="销售订单",maxLength= 50,required=false)  
    private String salesorder;   
    
	/*
	 * 销售订单行项目号
	 */
    @Column(name="ORDERROWNO")
    @ValidateRule(dataType=9,label="销售订单行项目号",maxLength= 50,required=false)  
    private String orderrowno;   
    
	/*
	 * 利润中心
	 */
    @Column(name="PROFITCENTER")
    @ValidateRule(dataType=9,label="利润中心",maxLength= 100,required=false)  
    private String profitcenter;   
    
	/*
	 * 成本中心
	 */
    @Column(name="COSTCENTER")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 36,required=false)  
    private String costcenter;   
    
	/*
	 * 税收代码
	 */
    @Column(name="TAXCODE")
    @ValidateRule(dataType=9,label="税收代码",maxLength= 20,required=false)  
    private String taxcode;   
    
	/*
	 * 清帐凭证
	 */
    @Column(name="CLEARVOUCHER")
    @ValidateRule(dataType=9,label="清帐凭证",maxLength= 50,required=false)  
    private String clearvoucher;   
    
	/*
	 * 借/贷标识符
	 */
    @Column(name="DEBITCREDIT")
    @ValidateRule(dataType=9,label="借/贷标识符",maxLength= 1,required=false)  
    private String debitcredit;   
    
	/*
	 * 业务范围
	 */
    @Column(name="GSBER")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 4,required=false)  
    private String gsber;   
    
	/*
	 * 票据到期日
	 */
    @Column(name="ZFBDT")
    @ValidateRule(dataType=9,label="票据到期日",maxLength= 8,required=false)  
    private String zfbdt;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 会计年度
	 */
    @Column(name="FIYEAR")
    @ValidateRule(dataType=9,label="会计年度",maxLength= 4,required=false)  
    private String fiyear;   
    
	/*
	 * 收付款清帐标识
	 */
    @Column(name="FLAG")
    @ValidateRule(dataType=9,label="收付款清帐标识",maxLength= 1,required=false)  
    private String flag;   
    
	/*
	 * 业务行项目ID
	 */
    @Column(name="BUSINESSITEMID")
    @ValidateRule(dataType=9,label="业务行项目ID",maxLength= 36,required=false)  
    private String businessitemid;   
    
	/*
	 * 凭证预览ID
	 */
    @Column(name="BUSVOUCHERID")
    @ValidateRule(dataType=9,label="凭证预览ID",maxLength= 36,required=false)  
    private String busvoucherid;   
    
	/*
	 * 凭证预览
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="VOUCHERID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Voucher voucher;   
    

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
     *    获得凭证预览明细ID
     * @return 凭证预览明细ID : String
     */
    public String getVoucheritemid()
    {
		return this.voucheritemid;
    }

    /**
     * 功能描述:
     *    设置凭证预览明细ID
     * @param voucheritemid : String
     */
    public void setVoucheritemid(String voucheritemid)
    {
	    this.voucheritemid = voucheritemid;
    }
    

    /**
     * 功能描述:
     *    获得行号
     * @return 行号 : String
     */
    public String getRownumber()
    {
		return this.rownumber;
    }

    /**
     * 功能描述:
     *    设置行号
     * @param rownumber : String
     */
    public void setRownumber(String rownumber)
    {
	    this.rownumber = rownumber;
    }
    

    /**
     * 功能描述:
     *    获得记账代码
     * @return 记账代码 : String
     */
    public String getCheckcode()
    {
		return this.checkcode;
    }

    /**
     * 功能描述:
     *    设置记账代码
     * @param checkcode : String
     */
    public void setCheckcode(String checkcode)
    {
	    this.checkcode = checkcode;
    }
    

    /**
     * 功能描述:
     *    获得特殊G/L标识
     * @return 特殊G/L标识 : String
     */
    public String getGlflag()
    {
		return this.glflag;
    }

    /**
     * 功能描述:
     *    设置特殊G/L标识
     * @param glflag : String
     */
    public void setGlflag(String glflag)
    {
	    this.glflag = glflag;
    }
    

    /**
     * 功能描述:
     *    获得对应会计科目
     * @return 对应会计科目 : String
     */
    public String getSubject()
    {
		return this.subject;
    }

    /**
     * 功能描述:
     *    设置对应会计科目
     * @param subject : String
     */
    public void setSubject(String subject)
    {
	    this.subject = subject;
    }
    

    /**
     * 功能描述:
     *    获得科目说明
     * @return 科目说明 : String
     */
    public String getSubjectdescribe()
    {
		return this.subjectdescribe;
    }

    /**
     * 功能描述:
     *    设置科目说明
     * @param subjectdescribe : String
     */
    public void setSubjectdescribe(String subjectdescribe)
    {
	    this.subjectdescribe = subjectdescribe;
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
     *    获得金额
     * @return 金额 : BigDecimal
     */
    public BigDecimal getAmount()
    {
		return this.amount;
    }

    /**
     * 功能描述:
     *    设置金额
     * @param amount : BigDecimal
     */
    public void setAmount(BigDecimal amount)
    {
	    this.amount = amount;
    }
    

    /**
     * 功能描述:
     *    获得本位币金额
     * @return 本位币金额 : BigDecimal
     */
    public BigDecimal getAmount2()
    {
		return this.amount2;
    }

    /**
     * 功能描述:
     *    设置本位币金额
     * @param amount2 : BigDecimal
     */
    public void setAmount2(BigDecimal amount2)
    {
	    this.amount2 = amount2;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getDepid()
    {
		return this.depid;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param depid : String
     */
    public void setDepid(String depid)
    {
	    this.depid = depid;
    }
    

    /**
     * 功能描述:
     *    获得消息文本
     * @return 消息文本 : String
     */
    public String getText()
    {
		return this.text;
    }

    /**
     * 功能描述:
     *    设置消息文本
     * @param text : String
     */
    public void setText(String text)
    {
	    this.text = text;
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
     *    获得反记账标识
     * @return 反记账标识 : String
     */
    public String getUncheckflag()
    {
		return this.uncheckflag;
    }

    /**
     * 功能描述:
     *    设置反记账标识
     * @param uncheckflag : String
     */
    public void setUncheckflag(String uncheckflag)
    {
	    this.uncheckflag = uncheckflag;
    }
    

    /**
     * 功能描述:
     *    获得统驭项目
     * @return 统驭项目 : String
     */
    public String getControlaccount()
    {
		return this.controlaccount;
    }

    /**
     * 功能描述:
     *    设置统驭项目
     * @param controlaccount : String
     */
    public void setControlaccount(String controlaccount)
    {
	    this.controlaccount = controlaccount;
    }
    

    /**
     * 功能描述:
     *    获得统驭科目说明
     * @return 统驭科目说明 : String
     */
    public String getCaremark()
    {
		return this.caremark;
    }

    /**
     * 功能描述:
     *    设置统驭科目说明
     * @param caremark : String
     */
    public void setCaremark(String caremark)
    {
	    this.caremark = caremark;
    }
    

    /**
     * 功能描述:
     *    获得销售订单
     * @return 销售订单 : String
     */
    public String getSalesorder()
    {
		return this.salesorder;
    }

    /**
     * 功能描述:
     *    设置销售订单
     * @param salesorder : String
     */
    public void setSalesorder(String salesorder)
    {
	    this.salesorder = salesorder;
    }
    

    /**
     * 功能描述:
     *    获得销售订单行项目号
     * @return 销售订单行项目号 : String
     */
    public String getOrderrowno()
    {
		return this.orderrowno;
    }

    /**
     * 功能描述:
     *    设置销售订单行项目号
     * @param orderrowno : String
     */
    public void setOrderrowno(String orderrowno)
    {
	    this.orderrowno = orderrowno;
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
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getCostcenter()
    {
		return this.costcenter;
    }

    /**
     * 功能描述:
     *    设置成本中心
     * @param costcenter : String
     */
    public void setCostcenter(String costcenter)
    {
	    this.costcenter = costcenter;
    }
    

    /**
     * 功能描述:
     *    获得税收代码
     * @return 税收代码 : String
     */
    public String getTaxcode()
    {
		return this.taxcode;
    }

    /**
     * 功能描述:
     *    设置税收代码
     * @param taxcode : String
     */
    public void setTaxcode(String taxcode)
    {
	    this.taxcode = taxcode;
    }
    

    /**
     * 功能描述:
     *    获得清帐凭证
     * @return 清帐凭证 : String
     */
    public String getClearvoucher()
    {
		return this.clearvoucher;
    }

    /**
     * 功能描述:
     *    设置清帐凭证
     * @param clearvoucher : String
     */
    public void setClearvoucher(String clearvoucher)
    {
	    this.clearvoucher = clearvoucher;
    }
    

    /**
     * 功能描述:
     *    获得借/贷标识符
     * @return 借/贷标识符 : String
     */
    public String getDebitcredit()
    {
		return this.debitcredit;
    }

    /**
     * 功能描述:
     *    设置借/贷标识符
     * @param debitcredit : String
     */
    public void setDebitcredit(String debitcredit)
    {
	    this.debitcredit = debitcredit;
    }
    

    /**
     * 功能描述:
     *    获得业务范围
     * @return 业务范围 : String
     */
    public String getGsber()
    {
		return this.gsber;
    }

    /**
     * 功能描述:
     *    设置业务范围
     * @param gsber : String
     */
    public void setGsber(String gsber)
    {
	    this.gsber = gsber;
    }
    

    /**
     * 功能描述:
     *    获得票据到期日
     * @return 票据到期日 : String
     */
    public String getZfbdt()
    {
		return this.zfbdt;
    }

    /**
     * 功能描述:
     *    设置票据到期日
     * @param zfbdt : String
     */
    public void setZfbdt(String zfbdt)
    {
	    this.zfbdt = zfbdt;
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
     *    获得会计年度
     * @return 会计年度 : String
     */
    public String getFiyear()
    {
		return this.fiyear;
    }

    /**
     * 功能描述:
     *    设置会计年度
     * @param fiyear : String
     */
    public void setFiyear(String fiyear)
    {
	    this.fiyear = fiyear;
    }
    

    /**
     * 功能描述:
     *    获得收付款清帐标识
     * @return 收付款清帐标识 : String
     */
    public String getFlag()
    {
		return this.flag;
    }

    /**
     * 功能描述:
     *    设置收付款清帐标识
     * @param flag : String
     */
    public void setFlag(String flag)
    {
	    this.flag = flag;
    }
    

    /**
     * 功能描述:
     *    获得凭证预览
     * @return 凭证预览 : Voucher
     */
    public Voucher getVoucher()
    {
		return this.voucher;
    }

    /**
     * 功能描述:
     *    设置凭证预览
     * @param voucher : Voucher
     */
    public void setVoucher(Voucher voucher)
    {
	    this.voucher = voucher;
    }
    
    
	/**
	 *  默认构造器
	 */
    public VoucherItem()
    {
    	super();
    }

	public String getBusinessitemid() {
		return businessitemid;
	}

	public void setBusinessitemid(String businessitemid) {
		this.businessitemid = businessitemid;
	}

	public String getBusvoucherid() {
		return busvoucherid;
	}

	public void setBusvoucherid(String busvoucherid) {
		this.busvoucherid = busvoucherid;
	}
}
