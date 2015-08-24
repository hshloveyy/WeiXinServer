/*
 * @(#)VendorTitle.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月12日 15点55分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

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
 * 未清供应商数据抬头(VendorTitle)实体类
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
@Table(name = "YVENDORTITLE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class VendorTitle extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 供应商未清数据抬头ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="VENDORTITLEID")
      
    private String vendortitleid;   
    
	/*
	 * 公司代码
	 */
    @Column(name="BUKRS")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 4,required=false)  
    private String bukrs;   
    
	/*
	 * 会计凭证号码
	 */
    @Column(name="BELNR")
    @ValidateRule(dataType=9,label="会计凭证号码",maxLength= 10,required=false)  
    private String belnr;   
    
	/*
	 * 会计年度
	 */
    @Column(name="GJAHR")
    @ValidateRule(dataType=0,label="会计年度",required=false)  
    private String gjahr;   
    
	/*
	 * 会计凭证中的行项目数
	 */
    @Column(name="BUZEI")
    @ValidateRule(dataType=0,label="会计凭证中的行项目数",required=false)  
    private String buzei;   
    
	/*
	 * 借方/贷方标识
	 */
    @Column(name="SHKZG")
    @ValidateRule(dataType=9,label="借方/贷方标识",maxLength= 1,required=false)  
    private String shkzg;   
    
	/*
	 * SAP开票号
	 */
    @Column(name="INVOICE")
    @ValidateRule(dataType=9,label="SAP开票号",maxLength= 10,required=false)  
    private String invoice;   
    
	/*
	 * 货币码
	 */
    @Column(name="WAERS")
    @ValidateRule(dataType=9,label="货币码",maxLength= 5,required=false)  
    private String waers;   
    
	/*
	 * 按本位币计的金额
	 */
    @Column(name="DMBTR")
    @ValidateRule(dataType=0,label="按本位币计的金额",required=false)  
    private BigDecimal dmbtr;   
    
	/*
	 * 供应商或债权人的帐号
	 */
    @Column(name="LIFNR")
    @ValidateRule(dataType=9,label="供应商或债权人的帐号",maxLength= 10,required=false)  
    private String lifnr;   
    
	/*
	 * 冲销凭证号
	 */
    @Column(name="STBLG")
    @ValidateRule(dataType=9,label="冲销凭证号",maxLength= 10,required=false)  
    private String stblg;   
    
	/*
	 * 冲销凭证会计年度
	 */
    @Column(name="STJAH")
    @ValidateRule(dataType=0,label="冲销凭证会计年度",required=false)  
    private String stjah;   
    
	/*
	 * 凭证中的过帐日期
	 */
    @Column(name="BUDAT")
    @ValidateRule(dataType=8,label="凭证中的过帐日期",required=false)  
    private String budat;   
    
	/*
	 * 凭证抬头文本
	 */
    @Column(name="BKTXT")
    @ValidateRule(dataType=9,label="凭证抬头文本",maxLength= 25,required=false)  
    private String bktxt;   
    
	/*
	 * 合同号
	 */
    @Column(name="VERKF")
    @ValidateRule(dataType=9,label="合同号",maxLength= 30,required=false)  
    private String verkf;   
    
	/*
	 * 立项号
	 */
    @Column(name="LIXIANG")
    @ValidateRule(dataType=9,label="立项号",maxLength= 6,required=false)  
    private String lixiang;   
    
	/*
	 * 结清标志
	 */
    @Column(name="ISCLEARED")
    @ValidateRule(dataType=9,label="结清标志",maxLength= 1,required=false)  
    private String iscleared;   
    
	/*
	 * 发票状态
	 */
    @Column(name="INVOICESTATE")
    @ValidateRule(dataType=9,label="发票状态",maxLength= 1,required=false)  
    private String invoicestate;   
    
	/*
	 * 采购凭证号
	 */
    @Column(name="EBELN")
    @ValidateRule(dataType=9,label="采购凭证号",maxLength= 10,required=false)  
    private String ebeln;   
    
	/*
	 * 应付款日
	 */
    @Column(name="YFKR")
    @ValidateRule(dataType=9,label="应付款日",maxLength= 14,required=false)  
    private String yfkr;   
    
	/*
	 * 应到货日
	 */
    @Column(name="YDHR")
    @ValidateRule(dataType=9,label="应到货日",maxLength= 14,required=false)  
    private String ydhr;   
    
	/*
	 * 特殊 G/L 标识
	 */
    @Column(name="UMSKZ")
    @ValidateRule(dataType=9,label="特殊 G/L 标识",maxLength= 1,required=false)  
    private String umskz;   
    
	/*
	 * 总帐科目编号
	 */
    @Column(name="SAKNR")
    @ValidateRule(dataType=9,label="总帐科目编号",maxLength= 10,required=false)  
    private String saknr;   
    
    /*
	 * 本位币金额(判断汇兑损益用)
	 */
    @Column(name="BWBJE")
    @ValidateRule(dataType=0,label="本位币金额(判断汇兑损益用)",required=false)  
    private BigDecimal bwbje;
    
    /*
	 * 业务范围
	 */
    @Column(name="GSBER")
    @ValidateRule(dataType=9,label="业务范围",required=false)  
    private String gsber;

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
     *    获得供应商未清数据抬头ID
     * @return 供应商未清数据抬头ID : String
     */
    public String getVendortitleid()
    {
		return this.vendortitleid;
    }

    /**
     * 功能描述:
     *    设置供应商未清数据抬头ID
     * @param vendortitleid : String
     */
    public void setVendortitleid(String vendortitleid)
    {
	    this.vendortitleid = vendortitleid;
    }
    

    /**
     * 功能描述:
     *    获得公司代码
     * @return 公司代码 : String
     */
    public String getBukrs()
    {
		return this.bukrs;
    }

    /**
     * 功能描述:
     *    设置公司代码
     * @param bukrs : String
     */
    public void setBukrs(String bukrs)
    {
	    this.bukrs = bukrs;
    }
    

    /**
     * 功能描述:
     *    获得会计凭证号码
     * @return 会计凭证号码 : String
     */
    public String getBelnr()
    {
		return this.belnr;
    }

    /**
     * 功能描述:
     *    设置会计凭证号码
     * @param belnr : String
     */
    public void setBelnr(String belnr)
    {
	    this.belnr = belnr;
    }
    

    /**
     * 功能描述:
     *    获得会计年度
     * @return 会计年度 : String
     */
    public String getGjahr()
    {
		return this.gjahr;
    }

    /**
     * 功能描述:
     *    设置会计年度
     * @param gjahr : String
     */
    public void setGjahr(String gjahr)
    {
	    this.gjahr = gjahr;
    }
    

    /**
     * 功能描述:
     *    获得会计凭证中的行项目数
     * @return 会计凭证中的行项目数 : String
     */
    public String getBuzei()
    {
		return this.buzei;
    }

    /**
     * 功能描述:
     *    设置会计凭证中的行项目数
     * @param buzei : String
     */
    public void setBuzei(String buzei)
    {
	    this.buzei = buzei;
    }
    

    /**
     * 功能描述:
     *    获得借方/贷方标识
     * @return 借方/贷方标识 : String
     */
    public String getShkzg()
    {
		return this.shkzg;
    }

    /**
     * 功能描述:
     *    设置借方/贷方标识
     * @param shkzg : String
     */
    public void setShkzg(String shkzg)
    {
	    this.shkzg = shkzg;
    }
    

    /**
     * 功能描述:
     *    获得发票号
     * @return 发票号 : String
     */
    public String getInvoice()
    {
		return this.invoice;
    }

    /**
     * 功能描述:
     *    设置发票号
     * @param invoice : String
     */
    public void setInvoice(String invoice)
    {
	    this.invoice = invoice;
    }
    

    /**
     * 功能描述:
     *    获得货币码
     * @return 货币码 : String
     */
    public String getWaers()
    {
		return this.waers;
    }

    /**
     * 功能描述:
     *    设置货币码
     * @param waers : String
     */
    public void setWaers(String waers)
    {
	    this.waers = waers;
    }
    

    /**
     * 功能描述:
     *    获得按本位币计的金额
     * @return 按本位币计的金额 : BigDecimal
     */
    public BigDecimal getDmbtr()
    {
		return this.dmbtr;
    }

    /**
     * 功能描述:
     *    设置按本位币计的金额
     * @param dmbtr : BigDecimal
     */
    public void setDmbtr(BigDecimal dmbtr)
    {
	    this.dmbtr = dmbtr;
    }
    

    /**
     * 功能描述:
     *    获得供应商或债权人的帐号
     * @return 供应商或债权人的帐号 : String
     */
    public String getLifnr()
    {
		return this.lifnr;
    }

    /**
     * 功能描述:
     *    设置供应商或债权人的帐号
     * @param lifnr : String
     */
    public void setLifnr(String lifnr)
    {
	    this.lifnr = lifnr;
    }
    

    /**
     * 功能描述:
     *    获得冲销凭证号
     * @return 冲销凭证号 : String
     */
    public String getStblg()
    {
		return this.stblg;
    }

    /**
     * 功能描述:
     *    设置冲销凭证号
     * @param stblg : String
     */
    public void setStblg(String stblg)
    {
	    this.stblg = stblg;
    }
    

    /**
     * 功能描述:
     *    获得冲销凭证会计年度
     * @return 冲销凭证会计年度 : String
     */
    public String getStjah()
    {
		return this.stjah;
    }

    /**
     * 功能描述:
     *    设置冲销凭证会计年度
     * @param stjah : String
     */
    public void setStjah(String stjah)
    {
	    this.stjah = stjah;
    }
    

    /**
     * 功能描述:
     *    获得凭证中的过帐日期
     * @return 凭证中的过帐日期 : String
     */
    public String getBudat()
    {
		return this.budat;
    }

    /**
     * 功能描述:
     *    设置凭证中的过帐日期
     * @param budat : String
     */
    public void setBudat(String budat)
    {
	    this.budat = budat;
    }
    

    /**
     * 功能描述:
     *    获得凭证抬头文本
     * @return 凭证抬头文本 : String
     */
    public String getBktxt()
    {
		return this.bktxt;
    }

    /**
     * 功能描述:
     *    设置凭证抬头文本
     * @param bktxt : String
     */
    public void setBktxt(String bktxt)
    {
	    this.bktxt = bktxt;
    }
    

    /**
     * 功能描述:
     *    获得合同号
     * @return 合同号 : String
     */
    public String getVerkf()
    {
		return this.verkf;
    }

    /**
     * 功能描述:
     *    设置合同号
     * @param verkf : String
     */
    public void setVerkf(String verkf)
    {
	    this.verkf = verkf;
    }
    

    /**
     * 功能描述:
     *    获得立项号
     * @return 立项号 : String
     */
    public String getLixiang()
    {
		return this.lixiang;
    }

    /**
     * 功能描述:
     *    设置立项号
     * @param lixiang : String
     */
    public void setLixiang(String lixiang)
    {
	    this.lixiang = lixiang;
    }
    

    /**
     * 功能描述:
     *    获得结清标志
     * @return 结清标志 : String
     */
    public String getIscleared()
    {
		return this.iscleared;
    }

    /**
     * 功能描述:
     *    设置结清标志
     * @param iscleared : String
     */
    public void setIscleared(String iscleared)
    {
	    this.iscleared = iscleared;
    }
    

    /**
     * 功能描述:
     *    获得发票状态
     * @return 发票状态 : String
     */
    public String getInvoicestate()
    {
		return this.invoicestate;
    }

    /**
     * 功能描述:
     *    设置发票状态
     * @param invoicestate : String
     */
    public void setInvoicestate(String invoicestate)
    {
	    this.invoicestate = invoicestate;
    }
    

    /**
     * 功能描述:
     *    获得采购凭证号
     * @return 采购凭证号 : String
     */
    public String getEbeln()
    {
		return this.ebeln;
    }

    /**
     * 功能描述:
     *    设置采购凭证号
     * @param ebeln : String
     */
    public void setEbeln(String ebeln)
    {
	    this.ebeln = ebeln;
    }
    

    /**
     * 功能描述:
     *    获得应付款日
     * @return 应付款日 : String
     */
    public String getYfkr()
    {
		return this.yfkr;
    }

    /**
     * 功能描述:
     *    设置应付款日
     * @param yfkr : String
     */
    public void setYfkr(String yfkr)
    {
	    this.yfkr = yfkr;
    }
    

    /**
     * 功能描述:
     *    获得应到货日
     * @return 应到货日 : String
     */
    public String getYdhr()
    {
		return this.ydhr;
    }

    /**
     * 功能描述:
     *    设置应到货日
     * @param ydhr : String
     */
    public void setYdhr(String ydhr)
    {
	    this.ydhr = ydhr;
    }
    

    /**
     * 功能描述:
     *    获得特殊 G/L 标识
     * @return 特殊 G/L 标识 : String
     */
    public String getUmskz()
    {
		return this.umskz;
    }

    /**
     * 功能描述:
     *    设置特殊 G/L 标识
     * @param umskz : String
     */
    public void setUmskz(String umskz)
    {
	    this.umskz = umskz;
    }
    

    /**
     * 功能描述:
     *    获得总帐科目编号
     * @return 总帐科目编号 : String
     */
    public String getSaknr()
    {
		return this.saknr;
    }

    /**
     * 功能描述:
     *    设置总帐科目编号
     * @param saknr : String
     */
    public void setSaknr(String saknr)
    {
	    this.saknr = saknr;
    }
    
    
	/**
	 * @return the bwbje
	 */
	public BigDecimal getBwbje() {
		return bwbje;
	}

	/**
	 * @param bwbje the bwbje to set
	 */
	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
	}
	

	public String getGsber() {
		return gsber;
	}

	public void setGsber(String gsber) {
		this.gsber = gsber;
	}

	/**
	 *  默认构造器
	 */
    public VendorTitle()
    {
    	super();
    }
}
