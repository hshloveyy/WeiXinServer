/*
 * @(#)PurchaseInfo.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年01月09日 11点03分53秒
 *  描　述：创建
 */
package com.infolion.sample.purchase.domain;

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
          
import com.infolion.sample.purchase.domain.PurchaseRows;

/**
 * <pre>
 * 采购订单(SAP)(PurchaseInfo)实体类
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
@Table(name = "YPURCHASEINFO")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class PurchaseInfo extends BaseObject
{
	//fields
	/*
	 * 采购订单行项目信息(SAP)
	 */
    @OneToMany(mappedBy="purchaseInfo",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
     
    private Set<PurchaseRows> purchaseRows;   
    
	/*
	 * 采购合同信息ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PURCHASEINFOID")
     
    private String purchaseinfoId;   
    
	/*
	 *  和同组ID
	 */
    @Column(name="CONTRACTGROUPID")
    @ValidateRule(dataType=9,label=" 和同组ID",maxLength= 36,required=false) 
    private String contractgroupId;   
    
	/*
	 * 项目ID
	 */
    @Column(name="PROJECTID")
    @ValidateRule(dataType=9,label="项目ID",maxLength= 36,required=false) 
    private String projectId;   
    
	/*
	 * 项目名称
	 */
    @Column(name="PROJECTNAME")
    @ValidateRule(dataType=9,label="项目名称",maxLength= 200,required=false) 
    private String projectName;   
    
	/*
	 * 贸易方式
	 */
    @Column(name="TRADETYPE")
    @ValidateRule(dataType=9,label="贸易方式",maxLength= 2,required=false) 
    private String tradeType;   
    
	/*
	 * 采购定单编号
	 */
    @Column(name="CONTRACTNO")
    @ValidateRule(dataType=9,label="采购定单编号",maxLength= 36,required=false) 
    private String contractNo;   
    
	/*
	 * 原合同号
	 */
    @Column(name="OLDCONTRACTNO")
    @ValidateRule(dataType=9,label="原合同号",maxLength= 36,required=false) 
    private String oldcontractNo;   
    
	/*
	 * 采购定单名称
	 */
    @Column(name="CONTRACTNAME")
    @ValidateRule(dataType=9,label="采购定单名称",maxLength= 50,required=false) 
    private String contractName;   
    
	/*
	 * 开票方
	 */
    @Column(name="INVOICINGPARTY")
    @ValidateRule(dataType=9,label="开票方",maxLength= 36,required=false) 
    private String invoicingParty;   
    
	/*
	 * 开票方名称
	 */
    @Column(name="INVOICINGNAME")
    @ValidateRule(dataType=9,label="开票方名称",maxLength= 100,required=false) 
    private String invoicingName;   
    
	/*
	 * 付款方
	 */
    @Column(name="PAYER")
    @ValidateRule(dataType=9,label="付款方",maxLength= 36,required=false) 
    private String payer;   
    
	/*
	 * 付款方名称
	 */
    @Column(name="PAYERNAME")
    @ValidateRule(dataType=9,label="付款方名称",maxLength= 100,required=false) 
    private String payerName;   
    
	/*
	 * 订单类型（采购）
	 */
    @Column(name="BSART")
    @ValidateRule(dataType=9,label="订单类型（采购）",maxLength= 4,required=false) 
    private String bsart;   
    
	/*
	 * 采购凭证类别
	 */
    @Column(name="BSTYP")
    @ValidateRule(dataType=9,label="采购凭证类别",maxLength= 1,required=false) 
    private String bstyp;   
    
	/*
	 * 供应商或债权人的帐号
	 */
    @Column(name="LIFNR")
    @ValidateRule(dataType=9,label="供应商或债权人的帐号",maxLength= 10,required=false) 
    private String lifnr;   
    
	/*
	 * 供应商名称
	 */
    @Column(name="LIFNRNAME")
    @ValidateRule(dataType=9,label="供应商名称",maxLength= 100,required=false) 
    private String lifnrName;   
    
	/*
	 * 采购订单日期
	 */
    @Column(name="BEDAT")
    @ValidateRule(dataType=8,label="采购订单日期",required=false) 
    private String bedat;   
    
	/*
	 * 采购组织
	 */
    @Column(name="EKORG")
    @ValidateRule(dataType=9,label="采购组织",maxLength= 4,required=false) 
    private String ekorg;   
    
	/*
	 * 采购组
	 */
    @Column(name="EKGRP")
    @ValidateRule(dataType=9,label="采购组",maxLength= 3,required=false) 
    private String ekgrp;   
    
	/*
	 * 付款条件代码
	 */
    @Column(name="ZTERM")
    @ValidateRule(dataType=9,label="付款条件代码",maxLength= 36,required=false) 
    private String zterm;   
    
	/*
	 * 付款条件名称
	 */
    @Column(name="ZTERMNAME")
    @ValidateRule(dataType=9,label="付款条件名称",maxLength= 100,required=false) 
    private String ztermName;   
    
	/*
	 * 国际贸易条款 (部分1)
	 */
    @Column(name="INCO1")
    @ValidateRule(dataType=9,label="国际贸易条款 (部分1)",maxLength= 3,required=false) 
    private String inco1;   
    
	/*
	 * 国际贸易条款1
	 */
    @Column(name="INCO1NAME")
    @ValidateRule(dataType=9,label="国际贸易条款1",maxLength= 3,required=false) 
    private String inco1Name;   
    
	/*
	 * 国际贸易条件(部分2)
	 */
    @Column(name="INCO2")
    @ValidateRule(dataType=9,label="国际贸易条件(部分2)",maxLength= 28,required=false) 
    private String inco2;   
    
	/*
	 * 货币码
	 */
    @Column(name="WAERS")
    @ValidateRule(dataType=9,label="货币码",maxLength= 5,required=false) 
    private String waers;   
    
	/*
	 * 货币码名称
	 */
    @Column(name="WAERSNAME")
    @ValidateRule(dataType=9,label="货币码名称",maxLength= 100,required=false) 
    private String waersName;   
    
	/*
	 * 汇率
	 */
    @Column(name="WKURS")
    @ValidateRule(dataType=0,label="汇率",required=false) 
    private BigDecimal wkurs;   
    
	/*
	 * 采购合同号
	 */
    @Column(name="IHREZ")
    @ValidateRule(dataType=9,label="采购合同号",maxLength= 36,required=false) 
    private String ihrez;   
    
	/*
	 * 供应商销售合同号
	 */
    @Column(name="UNSEZ")
    @ValidateRule(dataType=9,label="供应商销售合同号",maxLength= 36,required=false) 
    private String unsez;   
    
	/*
	 * 手册号
	 */
    @Column(name="TELF1")
    @ValidateRule(dataType=9,label="手册号",maxLength= 36,required=false) 
    private String telf1;   
    
	/*
	 * 装运港
	 */
    @Column(name="SHIPMENTPORT")
    @ValidateRule(dataType=9,label="装运港",maxLength= 200,required=false) 
    private String shipmentPort;   
    
	/*
	 * 目的港
	 */
    @Column(name="DESTINATIONPORT")
    @ValidateRule(dataType=9,label="目的港",maxLength= 200,required=false) 
    private String destinationPort;   
    
	/*
	 * 装运期
	 */
    @Column(name="SHIPMENTDATE")
    @ValidateRule(dataType=8,label="装运期",required=false) 
    private String shipmentDate;   
    
	/*
	 * 总金额
	 */
    @Column(name="TOTALAMOUNT")
    @ValidateRule(dataType=0,label="总金额",required=false) 
    private BigDecimal totalAmount;   
    
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
    private String createTime;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="LASTMODIFYTIME")
    @ValidateRule(dataType=8,label="最后修改日期",required=false) 
    private String lastmodifyTime;   
    
	/*
	 * 最后修改者
	 */
    @Column(name="LASTMODIFYER")
    @ValidateRule(dataType=9,label="最后修改者",maxLength= 36,required=false) 
    private String lastmodifyer;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false) 
    private String processState;   
    
	/*
	 * 项目编号间隔
	 */
    @Column(name="PINCR")
    @ValidateRule(dataType=9,label="项目编号间隔",maxLength= 36,required=false) 
    private String pincr;   
    
	/*
	 * 公司代码
	 */
    @Column(name="BUKRS")
    @ValidateRule(dataType=9,label="公司代码",maxLength= 100,required=false) 
    private String bukrs;   
    

    /**
     * 功能描述:
     *    获得采购订单行项目信息(SAP)
     * @return 采购订单行项目信息(SAP) : Set<PurchaseRows>
     */
    public Set<PurchaseRows> getPurchaseRows()
    {
		return this.purchaseRows;
    }

    /**
     * 功能描述:
     *    设置采购订单行项目信息(SAP)
     * @param purchaseRows : Set<PurchaseRows>
     */
    public void setPurchaseRows(Set<PurchaseRows> purchaseRows)
    {
	    this.purchaseRows = purchaseRows;
    }
    

    /**
     * 功能描述:
     *    获得采购合同信息ID
     * @return 采购合同信息ID : String
     */
    public String getPurchaseinfoId()
    {
		return this.purchaseinfoId;
    }

    /**
     * 功能描述:
     *    设置采购合同信息ID
     * @param purchaseinfoId : String
     */
    public void setPurchaseinfoId(String purchaseinfoId)
    {
	    this.purchaseinfoId = purchaseinfoId;
    }
    

    /**
     * 功能描述:
     *    获得 和同组ID
     * @return  和同组ID : String
     */
    public String getContractgroupId()
    {
		return this.contractgroupId;
    }

    /**
     * 功能描述:
     *    设置 和同组ID
     * @param contractgroupId : String
     */
    public void setContractgroupId(String contractgroupId)
    {
	    this.contractgroupId = contractgroupId;
    }
    

    /**
     * 功能描述:
     *    获得项目ID
     * @return 项目ID : String
     */
    public String getProjectId()
    {
		return this.projectId;
    }

    /**
     * 功能描述:
     *    设置项目ID
     * @param projectId : String
     */
    public void setProjectId(String projectId)
    {
	    this.projectId = projectId;
    }
    

    /**
     * 功能描述:
     *    获得项目名称
     * @return 项目名称 : String
     */
    public String getProjectName()
    {
		return this.projectName;
    }

    /**
     * 功能描述:
     *    设置项目名称
     * @param projectName : String
     */
    public void setProjectName(String projectName)
    {
	    this.projectName = projectName;
    }
    

    /**
     * 功能描述:
     *    获得贸易方式
     * @return 贸易方式 : String
     */
    public String getTradeType()
    {
		return this.tradeType;
    }

    /**
     * 功能描述:
     *    设置贸易方式
     * @param tradeType : String
     */
    public void setTradeType(String tradeType)
    {
	    this.tradeType = tradeType;
    }
    

    /**
     * 功能描述:
     *    获得采购定单编号
     * @return 采购定单编号 : String
     */
    public String getContractNo()
    {
		return this.contractNo;
    }

    /**
     * 功能描述:
     *    设置采购定单编号
     * @param contractNo : String
     */
    public void setContractNo(String contractNo)
    {
	    this.contractNo = contractNo;
    }
    

    /**
     * 功能描述:
     *    获得原合同号
     * @return 原合同号 : String
     */
    public String getOldcontractNo()
    {
		return this.oldcontractNo;
    }

    /**
     * 功能描述:
     *    设置原合同号
     * @param oldcontractNo : String
     */
    public void setOldcontractNo(String oldcontractNo)
    {
	    this.oldcontractNo = oldcontractNo;
    }
    

    /**
     * 功能描述:
     *    获得采购定单名称
     * @return 采购定单名称 : String
     */
    public String getContractName()
    {
		return this.contractName;
    }

    /**
     * 功能描述:
     *    设置采购定单名称
     * @param contractName : String
     */
    public void setContractName(String contractName)
    {
	    this.contractName = contractName;
    }
    

    /**
     * 功能描述:
     *    获得开票方
     * @return 开票方 : String
     */
    public String getInvoicingParty()
    {
		return this.invoicingParty;
    }

    /**
     * 功能描述:
     *    设置开票方
     * @param invoicingParty : String
     */
    public void setInvoicingParty(String invoicingParty)
    {
	    this.invoicingParty = invoicingParty;
    }
    

    /**
     * 功能描述:
     *    获得开票方名称
     * @return 开票方名称 : String
     */
    public String getInvoicingName()
    {
		return this.invoicingName;
    }

    /**
     * 功能描述:
     *    设置开票方名称
     * @param invoicingName : String
     */
    public void setInvoicingName(String invoicingName)
    {
	    this.invoicingName = invoicingName;
    }
    

    /**
     * 功能描述:
     *    获得付款方
     * @return 付款方 : String
     */
    public String getPayer()
    {
		return this.payer;
    }

    /**
     * 功能描述:
     *    设置付款方
     * @param payer : String
     */
    public void setPayer(String payer)
    {
	    this.payer = payer;
    }
    

    /**
     * 功能描述:
     *    获得付款方名称
     * @return 付款方名称 : String
     */
    public String getPayerName()
    {
		return this.payerName;
    }

    /**
     * 功能描述:
     *    设置付款方名称
     * @param payerName : String
     */
    public void setPayerName(String payerName)
    {
	    this.payerName = payerName;
    }
    

    /**
     * 功能描述:
     *    获得订单类型（采购）
     * @return 订单类型（采购） : String
     */
    public String getBsart()
    {
		return this.bsart;
    }

    /**
     * 功能描述:
     *    设置订单类型（采购）
     * @param bsart : String
     */
    public void setBsart(String bsart)
    {
	    this.bsart = bsart;
    }
    

    /**
     * 功能描述:
     *    获得采购凭证类别
     * @return 采购凭证类别 : String
     */
    public String getBstyp()
    {
		return this.bstyp;
    }

    /**
     * 功能描述:
     *    设置采购凭证类别
     * @param bstyp : String
     */
    public void setBstyp(String bstyp)
    {
	    this.bstyp = bstyp;
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
     *    获得供应商名称
     * @return 供应商名称 : String
     */
    public String getLifnrName()
    {
		return this.lifnrName;
    }

    /**
     * 功能描述:
     *    设置供应商名称
     * @param lifnrName : String
     */
    public void setLifnrName(String lifnrName)
    {
	    this.lifnrName = lifnrName;
    }
    

    /**
     * 功能描述:
     *    获得采购订单日期
     * @return 采购订单日期 : String
     */
    public String getBedat()
    {
    	return DateUtils.toDisplayStr(this.bedat, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置采购订单日期
     * @param bedat : String
     */
    public void setBedat(String bedat)
    {
    	bedat = DateUtils.toStoreStr(bedat);
	    this.bedat = bedat;
    }
    

    /**
     * 功能描述:
     *    获得采购组织
     * @return 采购组织 : String
     */
    public String getEkorg()
    {
		return this.ekorg;
    }

    /**
     * 功能描述:
     *    设置采购组织
     * @param ekorg : String
     */
    public void setEkorg(String ekorg)
    {
	    this.ekorg = ekorg;
    }
    

    /**
     * 功能描述:
     *    获得采购组
     * @return 采购组 : String
     */
    public String getEkgrp()
    {
		return this.ekgrp;
    }

    /**
     * 功能描述:
     *    设置采购组
     * @param ekgrp : String
     */
    public void setEkgrp(String ekgrp)
    {
	    this.ekgrp = ekgrp;
    }
    

    /**
     * 功能描述:
     *    获得付款条件代码
     * @return 付款条件代码 : String
     */
    public String getZterm()
    {
		return this.zterm;
    }

    /**
     * 功能描述:
     *    设置付款条件代码
     * @param zterm : String
     */
    public void setZterm(String zterm)
    {
	    this.zterm = zterm;
    }
    

    /**
     * 功能描述:
     *    获得付款条件名称
     * @return 付款条件名称 : String
     */
    public String getZtermName()
    {
		return this.ztermName;
    }

    /**
     * 功能描述:
     *    设置付款条件名称
     * @param ztermName : String
     */
    public void setZtermName(String ztermName)
    {
	    this.ztermName = ztermName;
    }
    

    /**
     * 功能描述:
     *    获得国际贸易条款 (部分1)
     * @return 国际贸易条款 (部分1) : String
     */
    public String getInco1()
    {
		return this.inco1;
    }

    /**
     * 功能描述:
     *    设置国际贸易条款 (部分1)
     * @param inco1 : String
     */
    public void setInco1(String inco1)
    {
	    this.inco1 = inco1;
    }
    

    /**
     * 功能描述:
     *    获得国际贸易条款1
     * @return 国际贸易条款1 : String
     */
    public String getInco1Name()
    {
		return this.inco1Name;
    }

    /**
     * 功能描述:
     *    设置国际贸易条款1
     * @param inco1Name : String
     */
    public void setInco1Name(String inco1Name)
    {
	    this.inco1Name = inco1Name;
    }
    

    /**
     * 功能描述:
     *    获得国际贸易条件(部分2)
     * @return 国际贸易条件(部分2) : String
     */
    public String getInco2()
    {
		return this.inco2;
    }

    /**
     * 功能描述:
     *    设置国际贸易条件(部分2)
     * @param inco2 : String
     */
    public void setInco2(String inco2)
    {
	    this.inco2 = inco2;
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
     *    获得货币码名称
     * @return 货币码名称 : String
     */
    public String getWaersName()
    {
		return this.waersName;
    }

    /**
     * 功能描述:
     *    设置货币码名称
     * @param waersName : String
     */
    public void setWaersName(String waersName)
    {
	    this.waersName = waersName;
    }
    

    /**
     * 功能描述:
     *    获得汇率
     * @return 汇率 : BigDecimal
     */
    public BigDecimal getWkurs()
    {
		return this.wkurs;
    }

    /**
     * 功能描述:
     *    设置汇率
     * @param wkurs : BigDecimal
     */
    public void setWkurs(BigDecimal wkurs)
    {
	    this.wkurs = wkurs;
    }
    

    /**
     * 功能描述:
     *    获得采购合同号
     * @return 采购合同号 : String
     */
    public String getIhrez()
    {
		return this.ihrez;
    }

    /**
     * 功能描述:
     *    设置采购合同号
     * @param ihrez : String
     */
    public void setIhrez(String ihrez)
    {
	    this.ihrez = ihrez;
    }
    

    /**
     * 功能描述:
     *    获得供应商销售合同号
     * @return 供应商销售合同号 : String
     */
    public String getUnsez()
    {
		return this.unsez;
    }

    /**
     * 功能描述:
     *    设置供应商销售合同号
     * @param unsez : String
     */
    public void setUnsez(String unsez)
    {
	    this.unsez = unsez;
    }
    

    /**
     * 功能描述:
     *    获得手册号
     * @return 手册号 : String
     */
    public String getTelf1()
    {
		return this.telf1;
    }

    /**
     * 功能描述:
     *    设置手册号
     * @param telf1 : String
     */
    public void setTelf1(String telf1)
    {
	    this.telf1 = telf1;
    }
    

    /**
     * 功能描述:
     *    获得装运港
     * @return 装运港 : String
     */
    public String getShipmentPort()
    {
		return this.shipmentPort;
    }

    /**
     * 功能描述:
     *    设置装运港
     * @param shipmentPort : String
     */
    public void setShipmentPort(String shipmentPort)
    {
	    this.shipmentPort = shipmentPort;
    }
    

    /**
     * 功能描述:
     *    获得目的港
     * @return 目的港 : String
     */
    public String getDestinationPort()
    {
		return this.destinationPort;
    }

    /**
     * 功能描述:
     *    设置目的港
     * @param destinationPort : String
     */
    public void setDestinationPort(String destinationPort)
    {
	    this.destinationPort = destinationPort;
    }
    

    /**
     * 功能描述:
     *    获得装运期
     * @return 装运期 : String
     */
    public String getShipmentDate()
    {
    	return DateUtils.toDisplayStr(this.shipmentDate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置装运期
     * @param shipmentDate : String
     */
    public void setShipmentDate(String shipmentDate)
    {
    	shipmentDate = DateUtils.toStoreStr(shipmentDate);
	    this.shipmentDate = shipmentDate;
    }
    

    /**
     * 功能描述:
     *    获得总金额
     * @return 总金额 : BigDecimal
     */
    public BigDecimal getTotalAmount()
    {
		return this.totalAmount;
    }

    /**
     * 功能描述:
     *    设置总金额
     * @param totalAmount : BigDecimal
     */
    public void setTotalAmount(BigDecimal totalAmount)
    {
	    this.totalAmount = totalAmount;
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
    public String getCreateTime()
    {
    	return DateUtils.toDisplayStr(this.createTime, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置创建日期
     * @param createTime : String
     */
    public void setCreateTime(String createTime)
    {
    	createTime = DateUtils.toStoreStr(createTime);
	    this.createTime = createTime;
    }
    

    /**
     * 功能描述:
     *    获得最后修改日期
     * @return 最后修改日期 : String
     */
    public String getLastmodifyTime()
    {
    	return DateUtils.toDisplayStr(this.lastmodifyTime, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifyTime : String
     */
    public void setLastmodifyTime(String lastmodifyTime)
    {
    	lastmodifyTime = DateUtils.toStoreStr(lastmodifyTime);
	    this.lastmodifyTime = lastmodifyTime;
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
     *    获得流程状态
     * @return 流程状态 : String
     */
    public String getProcessState()
    {
		return this.processState;
    }

    /**
     * 功能描述:
     *    设置流程状态
     * @param processState : String
     */
    public void setProcessState(String processState)
    {
	    this.processState = processState;
    }
    

    /**
     * 功能描述:
     *    获得项目编号间隔
     * @return 项目编号间隔 : String
     */
    public String getPincr()
    {
		return this.pincr;
    }

    /**
     * 功能描述:
     *    设置项目编号间隔
     * @param pincr : String
     */
    public void setPincr(String pincr)
    {
	    this.pincr = pincr;
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
	 *  默认构造器
	 */
    public PurchaseInfo()
    {
    	super();
    }
}
