/*
 * @(#)Voucher.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月27日 07点47分00秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucher.domain;

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
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 凭证预览(Voucher)实体类
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
@Table(name = "YVOUCHER")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Voucher extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 凭证预览ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="VOUCHERID")
      
    private String voucherid;   
    
	/*
	 * 业务编号
	 */
    @Column(name="BUSINESSID")
    @ValidateRule(dataType=9,label="业务编号",maxLength= 36,required=false)  
    private String businessid;   
    
	/*
	 * 业务类型
	 */
    @Column(name="BUSINESSTYPE")
    @ValidateRule(dataType=9,label="业务类型",maxLength= 2,required=false)  
    private String businesstype;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 凭证类型
	 */
    @Column(name="VOUCHERTYPE")
    @ValidateRule(dataType=9,label="凭证类型",maxLength= 2,required=false)  
    private String vouchertype;   
    
	/*
	 * 凭证分类
	 */
    @Column(name="VOUCHERCLASS")
    @ValidateRule(dataType=9,label="凭证分类",maxLength= 2,required=false)  
    private String voucherclass;   
    
	/*
	 * 凭证抬头文本
	 */
    @Column(name="VOUCHERTEXT")
    @ValidateRule(dataType=9,label="凭证抬头文本",maxLength= 25,required=false)  
    private String vouchertext;   
    
	/*
	 * 过账日期
	 */
    @Column(name="CHECKDATE")
    @ValidateRule(dataType=9,label="过账日期",maxLength= 20,required=false)  
    private String checkdate;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=9,label="凭证日期",maxLength= 20,required=false)  
    private String voucherdate;   
    
	/*
	 * 公司代码
	 */
    @Column(name="COMPANYCODE")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 36,required=false)  
    private String companycode;   
    
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
	 * 会计年度
	 */
    @Column(name="FIYEAR")
    @ValidateRule(dataType=9,label="会计年度",maxLength= 4,required=false)  
    private String fiyear;   
    
	/*
	 * 会计期间
	 */
    @Column(name="FIPERIOD")
    @ValidateRule(dataType=9,label="会计期间",maxLength= 2,required=false)  
    private String fiperiod;   
    
	/*
	 * 参考码
	 */
    @Column(name="REFERENCECODE")
    @ValidateRule(dataType=9,label="参考码",maxLength= 50,required=false)  
    private String referencecode;   
    
	/*
	 * 输入日期
	 */
    @Column(name="IMPORTDATE")
    @ValidateRule(dataType=9,label="输入日期",maxLength= 20,required=false)  
    private String importdate;   
    
	/*
	 * 输入者
	 */
    @Column(name="IMPORTER")
    @ValidateRule(dataType=9,label="输入者",maxLength= 36,required=false)  
    private String importer;   
    
	/*
	 * 预制人
	 */
    @Column(name="PREPARER")
    @ValidateRule(dataType=9,label="预制人",maxLength= 36,required=false)  
    private String preparer;   
    
	/*
	 * 冲销凭证号年度
	 */
    @Column(name="OFFYEAR")
    @ValidateRule(dataType=9,label="冲销凭证号年度",maxLength= 4,required=false)  
    private String offyear;   
    
	/*
	 * 冲销凭证号
	 */
    @Column(name="OFFSETVOUCHERNO")
    @ValidateRule(dataType=9,label="冲销凭证号",maxLength= 30,required=false)  
    private String offsetvoucherno;   
    
	/*
	 * 模拟标识
	 */
    @Column(name="IFLAG")
    @ValidateRule(dataType=9,label="模拟标识",maxLength= 1,required=false)  
    private String iflag;   
    
	/*
	 * 成功标识
	 */
    @Column(name="EFLAG")
    @ValidateRule(dataType=9,label="成功标识",maxLength= 1,required=false)  
    private String eflag;   
    
	/*
	 * 清帐凭证状态
	 */
    @Column(name="BSTAT")
    @ValidateRule(dataType=9,label="清帐凭证状态",maxLength= 1,required=false)  
    private String bstat;   
    
	/*
	 * 清帐银行科目号
	 */
    @Column(name="KONTO")
    @ValidateRule(dataType=9,label="清帐银行科目号",maxLength= 16,required=false)  
    private String konto;   
    
	/*
	 * 起息日
	 */
    @Column(name="VALUT")
    @ValidateRule(dataType=9,label="起息日",maxLength= 8,required=false)  
    private String valut;   
    
	/*
	 * 供应商或客户编号
	 */
    @Column(name="AGKON")
    @ValidateRule(dataType=9,label="供应商或客户编号",maxLength= 16,required=false)  
    private String agkon;   
    
	/*
	 * 供应商或客户标识
	 */
    @Column(name="AGKOA")
    @ValidateRule(dataType=9,label="供应商或客户标识",maxLength= 1,required=false)  
    private String agkoa;   
    
	/*
	 * 特殊总账标识
	 */
    @Column(name="AGUMS")
    @ValidateRule(dataType=9,label="特殊总账标识",maxLength= 10,required=false)  
    private String agums;   
    
	/*
	 * 业务范围
	 */
    @Column(name="GSBER")
    @ValidateRule(dataType=9,label="业务范围",maxLength= 4,required=false)  
    private String gsber;   
    
	/*
	 * 现金流量项代码
	 */
    @Column(name="RSTGR")
    @ValidateRule(dataType=9,label="现金流量项代码",maxLength= 4,required=false)  
    private String rstgr;   
    
	/*
	 * 成本中心
	 */
    @Column(name="KOSTL")
    @ValidateRule(dataType=9,label="成本中心",maxLength= 10,required=false)  
    private String kostl;   
    
	/*
	 * 收付款清帐标识
	 */
    @Column(name="FLAG")
    @ValidateRule(dataType=9,label="收付款清帐标识",maxLength= 1,required=false)  
    private String flag;   
    
	/*
	 * 应付票据
	 */
    @Column(name="PAY")
    @ValidateRule(dataType=9,label="应付票据",maxLength= 1,required=false)  
    private String pay;   
    
	/*
	 * 应收票据
	 */
    @Column(name="RECEIVE")
    @ValidateRule(dataType=9,label="应收票据",maxLength= 1,required=false)  
    private String receive;   
    
	/*
	 * 是否确认
	 */
    @Column(name="ISCONFIRM")
    @ValidateRule(dataType=9,label="是否确认",maxLength= 1,required=false)  
    private String isconfirm;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
	/*
	 * 凭证预览明细
	 */
    @OneToMany(mappedBy="voucher",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<VoucherItem> voucherItem;   
    

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
     *    获得凭证预览ID
     * @return 凭证预览ID : String
     */
    public String getVoucherid()
    {
		return this.voucherid;
    }

    /**
     * 功能描述:
     *    设置凭证预览ID
     * @param voucherid : String
     */
    public void setVoucherid(String voucherid)
    {
	    this.voucherid = voucherid;
    }
    

    /**
     * 功能描述:
     *    获得业务编号
     * @return 业务编号 : String
     */
    public String getBusinessid()
    {
		return this.businessid;
    }

    /**
     * 功能描述:
     *    设置业务编号
     * @param businessid : String
     */
    public void setBusinessid(String businessid)
    {
	    this.businessid = businessid;
    }
    

    /**
     * 功能描述:
     *    获得业务类型
     * @return 业务类型 : String
     */
    public String getBusinesstype()
    {
		return this.businesstype;
    }

    /**
     * 功能描述:
     *    设置业务类型
     * @param businesstype : String
     */
    public void setBusinesstype(String businesstype)
    {
	    this.businesstype = businesstype;
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
     *    获得凭证类型
     * @return 凭证类型 : String
     */
    public String getVouchertype()
    {
		return this.vouchertype;
    }

    /**
     * 功能描述:
     *    设置凭证类型
     * @param vouchertype : String
     */
    public void setVouchertype(String vouchertype)
    {
	    this.vouchertype = vouchertype;
    }
    

    /**
     * 功能描述:
     *    获得凭证分类
     * @return 凭证分类 : String
     */
    public String getVoucherclass()
    {
		return this.voucherclass;
    }

    /**
     * 功能描述:
     *    设置凭证分类
     * @param voucherclass : String
     */
    public void setVoucherclass(String voucherclass)
    {
	    this.voucherclass = voucherclass;
    }
    

    /**
     * 功能描述:
     *    获得凭证抬头文本
     * @return 凭证抬头文本 : String
     */
    public String getVouchertext()
    {
		return this.vouchertext;
    }

    /**
     * 功能描述:
     *    设置凭证抬头文本
     * @param vouchertext : String
     */
    public void setVouchertext(String vouchertext)
    {
	    this.vouchertext = vouchertext;
    }
    

    /**
     * 功能描述:
     *    获得过账日期
     * @return 过账日期 : String
     */
    public String getCheckdate()
    {
		return this.checkdate;
    }

    /**
     * 功能描述:
     *    设置过账日期
     * @param checkdate : String
     */
    public void setCheckdate(String checkdate)
    {
	    this.checkdate = checkdate;
    }
    

    /**
     * 功能描述:
     *    获得凭证日期
     * @return 凭证日期 : String
     */
    public String getVoucherdate()
    {
		return this.voucherdate;
    }

    /**
     * 功能描述:
     *    设置凭证日期
     * @param voucherdate : String
     */
    public void setVoucherdate(String voucherdate)
    {
	    this.voucherdate = voucherdate;
    }
    

    /**
     * 功能描述:
     *    获得公司代码
     * @return 公司代码 : String
     */
    public String getCompanycode()
    {
		return this.companycode;
    }

    /**
     * 功能描述:
     *    设置公司代码
     * @param companycode : String
     */
    public void setCompanycode(String companycode)
    {
	    this.companycode = companycode;
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
     *    获得会计期间
     * @return 会计期间 : String
     */
    public String getFiperiod()
    {
		return this.fiperiod;
    }

    /**
     * 功能描述:
     *    设置会计期间
     * @param fiperiod : String
     */
    public void setFiperiod(String fiperiod)
    {
	    this.fiperiod = fiperiod;
    }
    

    /**
     * 功能描述:
     *    获得参考码
     * @return 参考码 : String
     */
    public String getReferencecode()
    {
		return this.referencecode;
    }

    /**
     * 功能描述:
     *    设置参考码
     * @param referencecode : String
     */
    public void setReferencecode(String referencecode)
    {
	    this.referencecode = referencecode;
    }
    

    /**
     * 功能描述:
     *    获得输入日期
     * @return 输入日期 : String
     */
    public String getImportdate()
    {
		return this.importdate;
    }

    /**
     * 功能描述:
     *    设置输入日期
     * @param importdate : String
     */
    public void setImportdate(String importdate)
    {
	    this.importdate = importdate;
    }
    

    /**
     * 功能描述:
     *    获得输入者
     * @return 输入者 : String
     */
    public String getImporter()
    {
		return this.importer;
    }

    /**
     * 功能描述:
     *    设置输入者
     * @param importer : String
     */
    public void setImporter(String importer)
    {
	    this.importer = importer;
    }
    

    /**
     * 功能描述:
     *    获得预制人
     * @return 预制人 : String
     */
    public String getPreparer()
    {
		return this.preparer;
    }

    /**
     * 功能描述:
     *    设置预制人
     * @param preparer : String
     */
    public void setPreparer(String preparer)
    {
	    this.preparer = preparer;
    }
    

    /**
     * 功能描述:
     *    获得冲销凭证号年度
     * @return 冲销凭证号年度 : String
     */
    public String getOffyear()
    {
		return this.offyear;
    }

    /**
     * 功能描述:
     *    设置冲销凭证号年度
     * @param offyear : String
     */
    public void setOffyear(String offyear)
    {
	    this.offyear = offyear;
    }
    

    /**
     * 功能描述:
     *    获得冲销凭证号
     * @return 冲销凭证号 : String
     */
    public String getOffsetvoucherno()
    {
		return this.offsetvoucherno;
    }

    /**
     * 功能描述:
     *    设置冲销凭证号
     * @param offsetvoucherno : String
     */
    public void setOffsetvoucherno(String offsetvoucherno)
    {
	    this.offsetvoucherno = offsetvoucherno;
    }
    

    /**
     * 功能描述:
     *    获得模拟标识
     * @return 模拟标识 : String
     */
    public String getIflag()
    {
		return this.iflag;
    }

    /**
     * 功能描述:
     *    设置模拟标识
     * @param iflag : String
     */
    public void setIflag(String iflag)
    {
	    this.iflag = iflag;
    }
    

    /**
     * 功能描述:
     *    获得成功标识
     * @return 成功标识 : String
     */
    public String getEflag()
    {
		return this.eflag;
    }

    /**
     * 功能描述:
     *    设置成功标识
     * @param eflag : String
     */
    public void setEflag(String eflag)
    {
	    this.eflag = eflag;
    }
    

    /**
     * 功能描述:
     *    获得清帐凭证状态
     * @return 清帐凭证状态 : String
     */
    public String getBstat()
    {
		return this.bstat;
    }

    /**
     * 功能描述:
     *    设置清帐凭证状态
     * @param bstat : String
     */
    public void setBstat(String bstat)
    {
	    this.bstat = bstat;
    }
    

    /**
     * 功能描述:
     *    获得清帐银行科目号
     * @return 清帐银行科目号 : String
     */
    public String getKonto()
    {
		return this.konto;
    }

    /**
     * 功能描述:
     *    设置清帐银行科目号
     * @param konto : String
     */
    public void setKonto(String konto)
    {
	    this.konto = konto;
    }
    

    /**
     * 功能描述:
     *    获得起息日
     * @return 起息日 : String
     */
    public String getValut()
    {
		return this.valut;
    }

    /**
     * 功能描述:
     *    设置起息日
     * @param valut : String
     */
    public void setValut(String valut)
    {
	    this.valut = valut;
    }
    

    /**
     * 功能描述:
     *    获得供应商或客户编号
     * @return 供应商或客户编号 : String
     */
    public String getAgkon()
    {
		return this.agkon;
    }

    /**
     * 功能描述:
     *    设置供应商或客户编号
     * @param agkon : String
     */
    public void setAgkon(String agkon)
    {
	    this.agkon = agkon;
    }
    

    /**
     * 功能描述:
     *    获得供应商或客户标识
     * @return 供应商或客户标识 : String
     */
    public String getAgkoa()
    {
		return this.agkoa;
    }

    /**
     * 功能描述:
     *    设置供应商或客户标识
     * @param agkoa : String
     */
    public void setAgkoa(String agkoa)
    {
	    this.agkoa = agkoa;
    }
    

    /**
     * 功能描述:
     *    获得特殊总账标识
     * @return 特殊总账标识 : String
     */
    public String getAgums()
    {
		return this.agums;
    }

    /**
     * 功能描述:
     *    设置特殊总账标识
     * @param agums : String
     */
    public void setAgums(String agums)
    {
	    this.agums = agums;
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
     *    获得现金流量项代码
     * @return 现金流量项代码 : String
     */
    public String getRstgr()
    {
		return this.rstgr;
    }

    /**
     * 功能描述:
     *    设置现金流量项代码
     * @param rstgr : String
     */
    public void setRstgr(String rstgr)
    {
	    this.rstgr = rstgr;
    }
    

    /**
     * 功能描述:
     *    获得成本中心
     * @return 成本中心 : String
     */
    public String getKostl()
    {
		return this.kostl;
    }

    /**
     * 功能描述:
     *    设置成本中心
     * @param kostl : String
     */
    public void setKostl(String kostl)
    {
	    this.kostl = kostl;
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
     *    获得应付票据
     * @return 应付票据 : String
     */
    public String getPay()
    {
		return this.pay;
    }

    /**
     * 功能描述:
     *    设置应付票据
     * @param pay : String
     */
    public void setPay(String pay)
    {
	    this.pay = pay;
    }
    

    /**
     * 功能描述:
     *    获得应收票据
     * @return 应收票据 : String
     */
    public String getReceive()
    {
		return this.receive;
    }

    /**
     * 功能描述:
     *    设置应收票据
     * @param receive : String
     */
    public void setReceive(String receive)
    {
	    this.receive = receive;
    }
    

    /**
     * 功能描述:
     *    获得是否确认
     * @return 是否确认 : String
     */
    public String getIsconfirm()
    {
		return this.isconfirm;
    }

    /**
     * 功能描述:
     *    设置是否确认
     * @param isconfirm : String
     */
    public void setIsconfirm(String isconfirm)
    {
	    this.isconfirm = isconfirm;
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
     *    获得凭证预览明细
     * @return 凭证预览明细 : Set<VoucherItem>
     */
    public Set<VoucherItem> getVoucherItem()
    {
		return this.voucherItem;
    }

    /**
     * 功能描述:
     *    设置凭证预览明细
     * @param voucherItem : Set<VoucherItem>
     */
    public void setVoucherItem(Set<VoucherItem> voucherItem)
    {
	    this.voucherItem = voucherItem;
    }
    
    
	/**
	 *  默认构造器
	 */
    public Voucher()
    {
    	super();
    }
}
