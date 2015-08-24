/*
 * @(#)PurchaseRows.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年01月09日 11点03分54秒
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
import com.infolion.sample.purchase.domain.PurchaseInfo;

/**
 * <pre>
 * 采购订单行项目信息(SAP)(PurchaseRows)实体类
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
@Table(name = "YPURCHASEROWS")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class PurchaseRows extends BaseObject
{
	//fields
	/*
	 * 采购行项目ID
	 */
    @Id
    @GeneratedValue(generator ="paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PURCHASEROWSID")
     
    private String purchaserowsId;   
    
	/*
	 * 采购凭证中的项目类别
	 */
    @Column(name="PSTYP")
    @ValidateRule(dataType=9,label="采购凭证中的项目类别",maxLength= 1,required=false) 
    private String pstyp;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATNR")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false) 
    private String matnr;   
    
	/*
	 * 物料描述
	 */
    @Column(name="TXZO1")
    @ValidateRule(dataType=9,label="物料描述",maxLength= 200,required=false) 
    private String txzo1;   
    
	/*
	 * 基本计量单位
	 */
    @Column(name="MEINS")
    @ValidateRule(dataType=9,label="基本计量单位",maxLength= 36,required=false) 
    private String meins;   
    
	/*
	 * 采购订单数量
	 */
    @Column(name="MENGE")
    @ValidateRule(dataType=0,label="采购订单数量",required=false) 
    private BigDecimal menge;   
    
	/*
	 * 采购凭证中的净价(以凭证货币计)
	 */
    @Column(name="NETPR")
    @ValidateRule(dataType=0,label="采购凭证中的净价(以凭证货币计)",required=false) 
    private BigDecimal netpr;   
    
	/*
	 * 价格单位(货币)
	 */
    @Column(name="BPRME")
    @ValidateRule(dataType=9,label="价格单位(货币)",maxLength= 36,required=false) 
    private String bprme;   
    
	/*
	 * 每价格单位
	 */
    @Column(name="PEINH")
    @ValidateRule(dataType=9,label="每价格单位",maxLength= 36,required=false) 
    private String peinh;   
    
	/*
	 * 工厂编号
	 */
    @Column(name="WERKS")
    @ValidateRule(dataType=9,label="工厂编号",maxLength= 36,required=false) 
    private String werks;   
    
	/*
	 * 工厂名称
	 */
    @Column(name="WERKSNAME")
    @ValidateRule(dataType=9,label="工厂名称",maxLength= 100,required=false) 
    private String werksName;   
    
	/*
	 * 项目交货日期
	 */
    @Column(name="EINDT")
    @ValidateRule(dataType=9,label="项目交货日期",maxLength= 14,required=false) 
    private String eindt;   
    
	/*
	 * 标识：基于收货的发票验证
	 */
    @Column(name="WEBRE")
    @ValidateRule(dataType=9,label="标识：基于收货的发票验证",maxLength= 1,required=false) 
    private String webre;   
    
	/*
	 * 销售税代码
	 */
    @Column(name="MWSKZ")
    @ValidateRule(dataType=9,label="销售税代码",maxLength= 2,required=false) 
    private String mwskz;   
    
	/*
	 * 销售税名称
	 */
    @Column(name="MWSKZNAME")
    @ValidateRule(dataType=9,label="销售税名称",maxLength= 100,required=false) 
    private String mwskzName;   
    
	/*
	 * 库存地点
	 */
    @Column(name="FACTORYLOCAL")
    @ValidateRule(dataType=9,label="库存地点",maxLength= 200,required=false) 
    private String factorylocal;   
    
	/*
	 * 总价值
	 */
    @Column(name="TOTALVALUE")
    @ValidateRule(dataType=0,label="总价值",required=false) 
    private BigDecimal totalValue;   
    
	/*
	 * 含税单价
	 */
    @Column(name="PRICE")
    @ValidateRule(dataType=0,label="含税单价",required=false) 
    private BigDecimal price;   
    
	/*
	 * 创建人
	 */
    @Column(name="CREATOR")
    @ValidateRule(dataType=9,label="创建人",maxLength= 36,required=false) 
    private String creator;   
    
	/*
	 * 创建时间
	 */
    @Column(name="CREATETIME")
    @ValidateRule(dataType=9,label="创建时间",maxLength= 14,required=false) 
    private String createTime;   
    
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
    @ValidateRule(dataType=9,label="最后修改日期",maxLength= 14,required=false) 
    private String lastmodifyTime;   
    
	/*
	 * 订单单位的分子
	 */
    @Column(name="CONVNUM1")
    @ValidateRule(dataType=9,label="订单单位的分子",maxLength= 36,required=false) 
    private String convnum1;   
    
	/*
	 * 订单单位的分母
	 */
    @Column(name="CONVNUM2")
    @ValidateRule(dataType=9,label="订单单位的分母",maxLength= 36,required=false) 
    private String convnum2;   
    
	/*
	 * 交货日期的类别
	 */
    @Column(name="LPEIN")
    @ValidateRule(dataType=9,label="交货日期的类别",maxLength= 36,required=false) 
    private String lpein;   
    
	/*
	 * SAPROWNO
	 */
    @Column(name="SAPROWNO")
    @ValidateRule(dataType=9,label="SAPROWNO",maxLength= 6,required=false) 
    private String sapRowNo;   
    
	/*
	 * 采购订单(SAP)
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PURCHASEINFOID")
     
    private PurchaseInfo purchaseInfo;   
    

    /**
     * 功能描述:
     *    获得采购行项目ID
     * @return 采购行项目ID : String
     */
    public String getPurchaserowsId()
    {
		return this.purchaserowsId;
    }

    /**
     * 功能描述:
     *    设置采购行项目ID
     * @param purchaserowsId : String
     */
    public void setPurchaserowsId(String purchaserowsId)
    {
	    this.purchaserowsId = purchaserowsId;
    }
    

    /**
     * 功能描述:
     *    获得采购凭证中的项目类别
     * @return 采购凭证中的项目类别 : String
     */
    public String getPstyp()
    {
		return this.pstyp;
    }

    /**
     * 功能描述:
     *    设置采购凭证中的项目类别
     * @param pstyp : String
     */
    public void setPstyp(String pstyp)
    {
	    this.pstyp = pstyp;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMatnr()
    {
		return this.matnr;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param matnr : String
     */
    public void setMatnr(String matnr)
    {
	    this.matnr = matnr;
    }
    

    /**
     * 功能描述:
     *    获得物料描述
     * @return 物料描述 : String
     */
    public String getTxzo1()
    {
		return this.txzo1;
    }

    /**
     * 功能描述:
     *    设置物料描述
     * @param txzo1 : String
     */
    public void setTxzo1(String txzo1)
    {
	    this.txzo1 = txzo1;
    }
    

    /**
     * 功能描述:
     *    获得基本计量单位
     * @return 基本计量单位 : String
     */
    public String getMeins()
    {
		return this.meins;
    }

    /**
     * 功能描述:
     *    设置基本计量单位
     * @param meins : String
     */
    public void setMeins(String meins)
    {
	    this.meins = meins;
    }
    

    /**
     * 功能描述:
     *    获得采购订单数量
     * @return 采购订单数量 : BigDecimal
     */
    public BigDecimal getMenge()
    {
		return this.menge;
    }

    /**
     * 功能描述:
     *    设置采购订单数量
     * @param menge : BigDecimal
     */
    public void setMenge(BigDecimal menge)
    {
	    this.menge = menge;
    }
    

    /**
     * 功能描述:
     *    获得采购凭证中的净价(以凭证货币计)
     * @return 采购凭证中的净价(以凭证货币计) : BigDecimal
     */
    public BigDecimal getNetpr()
    {
		return this.netpr;
    }

    /**
     * 功能描述:
     *    设置采购凭证中的净价(以凭证货币计)
     * @param netpr : BigDecimal
     */
    public void setNetpr(BigDecimal netpr)
    {
	    this.netpr = netpr;
    }
    

    /**
     * 功能描述:
     *    获得价格单位(货币)
     * @return 价格单位(货币) : String
     */
    public String getBprme()
    {
		return this.bprme;
    }

    /**
     * 功能描述:
     *    设置价格单位(货币)
     * @param bprme : String
     */
    public void setBprme(String bprme)
    {
	    this.bprme = bprme;
    }
    

    /**
     * 功能描述:
     *    获得每价格单位
     * @return 每价格单位 : String
     */
    public String getPeinh()
    {
		return this.peinh;
    }

    /**
     * 功能描述:
     *    设置每价格单位
     * @param peinh : String
     */
    public void setPeinh(String peinh)
    {
	    this.peinh = peinh;
    }
    

    /**
     * 功能描述:
     *    获得工厂编号
     * @return 工厂编号 : String
     */
    public String getWerks()
    {
		return this.werks;
    }

    /**
     * 功能描述:
     *    设置工厂编号
     * @param werks : String
     */
    public void setWerks(String werks)
    {
	    this.werks = werks;
    }
    

    /**
     * 功能描述:
     *    获得工厂名称
     * @return 工厂名称 : String
     */
    public String getWerksName()
    {
		return this.werksName;
    }

    /**
     * 功能描述:
     *    设置工厂名称
     * @param werksName : String
     */
    public void setWerksName(String werksName)
    {
	    this.werksName = werksName;
    }
    

    /**
     * 功能描述:
     *    获得项目交货日期
     * @return 项目交货日期 : String
     */
    public String getEindt()
    {
		return this.eindt;
    }

    /**
     * 功能描述:
     *    设置项目交货日期
     * @param eindt : String
     */
    public void setEindt(String eindt)
    {
	    this.eindt = eindt;
    }
    

    /**
     * 功能描述:
     *    获得标识：基于收货的发票验证
     * @return 标识：基于收货的发票验证 : String
     */
    public String getWebre()
    {
		return this.webre;
    }

    /**
     * 功能描述:
     *    设置标识：基于收货的发票验证
     * @param webre : String
     */
    public void setWebre(String webre)
    {
	    this.webre = webre;
    }
    

    /**
     * 功能描述:
     *    获得销售税代码
     * @return 销售税代码 : String
     */
    public String getMwskz()
    {
		return this.mwskz;
    }

    /**
     * 功能描述:
     *    设置销售税代码
     * @param mwskz : String
     */
    public void setMwskz(String mwskz)
    {
	    this.mwskz = mwskz;
    }
    

    /**
     * 功能描述:
     *    获得销售税名称
     * @return 销售税名称 : String
     */
    public String getMwskzName()
    {
		return this.mwskzName;
    }

    /**
     * 功能描述:
     *    设置销售税名称
     * @param mwskzName : String
     */
    public void setMwskzName(String mwskzName)
    {
	    this.mwskzName = mwskzName;
    }
    

    /**
     * 功能描述:
     *    获得库存地点
     * @return 库存地点 : String
     */
    public String getFactorylocal()
    {
		return this.factorylocal;
    }

    /**
     * 功能描述:
     *    设置库存地点
     * @param factorylocal : String
     */
    public void setFactorylocal(String factorylocal)
    {
	    this.factorylocal = factorylocal;
    }
    

    /**
     * 功能描述:
     *    获得总价值
     * @return 总价值 : BigDecimal
     */
    public BigDecimal getTotalValue()
    {
		return this.totalValue;
    }

    /**
     * 功能描述:
     *    设置总价值
     * @param totalValue : BigDecimal
     */
    public void setTotalValue(BigDecimal totalValue)
    {
	    this.totalValue = totalValue;
    }
    

    /**
     * 功能描述:
     *    获得含税单价
     * @return 含税单价 : BigDecimal
     */
    public BigDecimal getPrice()
    {
		return this.price;
    }

    /**
     * 功能描述:
     *    设置含税单价
     * @param price : BigDecimal
     */
    public void setPrice(BigDecimal price)
    {
	    this.price = price;
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
     *    获得创建时间
     * @return 创建时间 : String
     */
    public String getCreateTime()
    {
		return this.createTime;
    }

    /**
     * 功能描述:
     *    设置创建时间
     * @param createTime : String
     */
    public void setCreateTime(String createTime)
    {
	    this.createTime = createTime;
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
    public String getLastmodifyTime()
    {
		return this.lastmodifyTime;
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifyTime : String
     */
    public void setLastmodifyTime(String lastmodifyTime)
    {
	    this.lastmodifyTime = lastmodifyTime;
    }
    

    /**
     * 功能描述:
     *    获得订单单位的分子
     * @return 订单单位的分子 : String
     */
    public String getConvnum1()
    {
		return this.convnum1;
    }

    /**
     * 功能描述:
     *    设置订单单位的分子
     * @param convnum1 : String
     */
    public void setConvnum1(String convnum1)
    {
	    this.convnum1 = convnum1;
    }
    

    /**
     * 功能描述:
     *    获得订单单位的分母
     * @return 订单单位的分母 : String
     */
    public String getConvnum2()
    {
		return this.convnum2;
    }

    /**
     * 功能描述:
     *    设置订单单位的分母
     * @param convnum2 : String
     */
    public void setConvnum2(String convnum2)
    {
	    this.convnum2 = convnum2;
    }
    

    /**
     * 功能描述:
     *    获得交货日期的类别
     * @return 交货日期的类别 : String
     */
    public String getLpein()
    {
		return this.lpein;
    }

    /**
     * 功能描述:
     *    设置交货日期的类别
     * @param lpein : String
     */
    public void setLpein(String lpein)
    {
	    this.lpein = lpein;
    }
    

    /**
     * 功能描述:
     *    获得SAPROWNO
     * @return SAPROWNO : String
     */
    public String getSapRowNo()
    {
		return this.sapRowNo;
    }

    /**
     * 功能描述:
     *    设置SAPROWNO
     * @param sapRowNo : String
     */
    public void setSapRowNo(String sapRowNo)
    {
	    this.sapRowNo = sapRowNo;
    }
    

    /**
     * 功能描述:
     *    获得采购订单(SAP)
     * @return 采购订单(SAP) : PurchaseInfo
     */
    public PurchaseInfo getPurchaseInfo()
    {
		return this.purchaseInfo;
    }

    /**
     * 功能描述:
     *    设置采购订单(SAP)
     * @param purchaseInfo : PurchaseInfo
     */
    public void setPurchaseInfo(PurchaseInfo purchaseInfo)
    {
	    this.purchaseInfo = purchaseInfo;
    }
    
    
	/**
	 *  默认构造器
	 */
    public PurchaseRows()
    {
    	super();
    }
}
