/*
 * @(#)PurchasingDocItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 08点56分42秒
 *  描　述：创建
 */
package com.infolion.sample.purchase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * SAP采购凭证行项目(PurchasingDocItem)实体类
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
@Table(name = "EKPO")
@IdClass(PurchasingDocItemKey.class)
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class PurchasingDocItem extends BaseObject
{
	// fields

	// @EmbeddedId
	// @AttributeOverrides({
	// @AttributeOverride(name = "client", column = @Column(name = "MANDT",
	// nullable = false)),
	// @AttributeOverride(name = "ebelp", column = @Column(name = "EBELP",
	// nullable = false)) })
	// private PurchasingDocItemKey purchasingDocItemKey ;

	/*
	 * SAP采购凭证
	 */
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "MANDT"), @JoinColumn(name = "EBELN") })
	// @Transient
	private PurchasingDoc purchasingDoc;

	@Id
	private String ebelp;
	/*
	 * 客户端
	 */
	@Id
	private String client;

	@Id
	@Column(insertable=false ,updatable =false)
	private String ebeln;

	
	/**
	 * @return the ebeln
	 */
	public String getEbeln()
	{
		return ebeln;
	}

	/**
	 * @param ebeln the ebeln to set
	 */
	public void setEbeln(String ebeln)
	{
		this.ebeln = ebeln;
	}



	/*
	 * 物料号
	 */
	@Column(name = "MATNR")
	@ValidateRule(dataType = 9, label = "物料号", maxLength = 18, required = false)
	private String matnr;

	/*
	 * 短文本
	 */
	@Column(name = "TXZ01")
	@ValidateRule(dataType = 9, label = "短文本", maxLength = 40, required = false)
	private String txz01;

	/*
	 * 采购订单数量
	 */
	@Column(name = "MENGE")
	@ValidateRule(dataType = 9, label = "采购订单数量", maxLength = 17, required = false)
	private String menge;

	/*
	 * 采购凭证中的净价(以凭证货币计)
	 */
	@Column(name = "NETPR")
	@ValidateRule(dataType = 9, label = "采购凭证中的净价(以凭证货币计)", maxLength = 14, required = false)
	private String netpr;
	
	
	 /*
	 * 采购订单货币的订单净值
	 */
	@Column(name = "NETWR")
	@ValidateRule(dataType = 9, label = "采购订单货币的订单净值", maxLength = 16, required = false)
	private String netwr;
	
	/**
	 * @return the purchasingDoc
	 */
	public PurchasingDoc getPurchasingDoc()
	{
		return purchasingDoc;
	}

	/**
	 * @param purchasingDoc
	 *            the purchasingDoc to set
	 */
	public void setPurchasingDoc(PurchasingDoc purchasingDoc)
	{
		this.purchasingDoc = purchasingDoc;
	}

	/**
	 * @return the ebelp
	 */
	public String getEbelp()
	{
		return ebelp;
	}

	/**
	 * @param ebelp
	 *            the ebelp to set
	 */
	public void setEbelp(String ebelp)
	{
		this.ebelp = ebelp;
	}

	/**
	 * @return the client
	 */
	public String getClient()
	{
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client)
	{
		this.client = client;
	}
	
	/**
	 * @return the matnr
	 */
	public String getMatnr()
	{
		return matnr;
	}

	/**
	 * @param matnr the matnr to set
	 */
	public void setMatnr(String matnr)
	{
		this.matnr = matnr;
	}

	/**
	 * @return the txz01
	 */
	public String getTxz01()
	{
		return txz01;
	}

	/**
	 * @param txz01 the txz01 to set
	 */
	public void setTxz01(String txz01)
	{
		this.txz01 = txz01;
	}

	/**
	 * @return the menge
	 */
	public String getMenge()
	{
		return menge;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(String menge)
	{
		this.menge = menge;
	}

	/**
	 * @return the netpr
	 */
	public String getNetpr()
	{
		return netpr;
	}

	/**
	 * @param netpr the netpr to set
	 */
	public void setNetpr(String netpr)
	{
		this.netpr = netpr;
	}

	/**
	 * @return the netwr
	 */
	public String getNetwr()
	{
		return netwr;
	}

	/**
	 * @param netwr the netwr to set
	 */
	public void setNetwr(String netwr)
	{
		this.netwr = netwr;
	}
	
	

	//    
	// /*
	// * 采购凭证的项目编号
	// */
	// @Id
	// @GeneratedValue(generator ="paymentableGenerator")
	// @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	// @Column(name="EBELP")
	// private String ebelp;

	// /*
	// * 采购凭证中的删除标识
	// */
	// @Column(name="LOEKZ")
	// @ValidateRule(dataType=9,label="采购凭证中的删除标识",maxLength= 1,required=false)
	// private String loekz;
	//    
	// /*
	// * 询价状态
	// */
	// @Column(name="STATU")
	// @ValidateRule(dataType=9,label="询价状态",maxLength= 1,required=false)
	// private String statu;
	//    
	// /*
	// * 采购凭证项目更改日期
	// */
	// @Column(name="AEDAT")
	// @ValidateRule(dataType=8,label="采购凭证项目更改日期",required=false)
	// private Date aedat;
	//    
	//     
	//    
	// /*
	// * 物料号
	// */
	// @Column(name="EMATN")
	// @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false)
	// private String ematn;
	//    
	// /*
	// * 公司代码
	// */
	// @Column(name="BUKRS")
	// @ValidateRule(dataType=9,label="公司代码",maxLength= 4,required=false)
	// private String bukrs;
	//    
	// /*
	// * 工厂
	// */
	// @Column(name="WERKS")
	// @ValidateRule(dataType=9,label="工厂",maxLength= 4,required=false)
	// private String werks;
	//    
	// /*
	// * 库存地点
	// */
	// @Column(name="LGORT")
	// @ValidateRule(dataType=9,label="库存地点",maxLength= 4,required=false)
	// private String lgort;
	//    
	// /*
	// * 需求跟踪号
	// */
	// @Column(name="BEDNR")
	// @ValidateRule(dataType=9,label="需求跟踪号",maxLength= 10,required=false)
	// private String bednr;
	//    
	// /*
	// * 物料组
	// */
	// @Column(name="MATKL")
	// @ValidateRule(dataType=9,label="物料组",maxLength= 9,required=false)
	// private String matkl;
	//    
	// /*
	// * 采购信息记录的编号
	// */
	// @Column(name="INFNR")
	// @ValidateRule(dataType=9,label="采购信息记录的编号",maxLength= 10,required=false)
	// private String infnr;
	//    
	// /*
	// * 供应商使用的物料编号
	// */
	// @Column(name="IDNLF")
	// @ValidateRule(dataType=9,label="供应商使用的物料编号",maxLength= 35,required=false)
	// private String idnlf;
	//    
	// /*
	// * 目标数量
	// */
	// @Column(name="KTMNG")
	// @ValidateRule(dataType=9,label="目标数量",maxLength= 17,required=false)
	// private String ktmng;
	//    
	//    
	// /*
	// * 采购订单的计量单位
	// */
	// @Column(name="MEINS")
	// @ValidateRule(dataType=9,label="采购订单的计量单位",maxLength= 3,required=false)
	// private String meins;
	//    
	// /*
	// * 订单价格单位(采购)
	// */
	// @Column(name="BPRME")
	// @ValidateRule(dataType=9,label="订单价格单位(采购)",maxLength= 3,required=false)
	// private String bprme;
	//    
	// /*
	// * 订单价格单位转换为订单单位的分子
	// */
	// @Column(name="BPUMZ")
	// @ValidateRule(dataType=0,label="订单价格单位转换为订单单位的分子",required=false)
	// private BigDecimal bpumz;
	//    
	// /*
	// * 订单价格单位转换为订单单位的分母
	// */
	// @Column(name="BPUMN")
	// @ValidateRule(dataType=0,label="订单价格单位转换为订单单位的分母",required=false)
	// private BigDecimal bpumn;
	//    
	// /*
	// * 有关订货价格单位转换为基本单位的分子
	// */
	// @Column(name="UMREZ")
	// @ValidateRule(dataType=0,label="有关订货价格单位转换为基本单位的分子",required=false)
	// private BigDecimal umrez;
	//    
	// /*
	// * 订单单位到基本单位转换的分母
	// */
	// @Column(name="UMREN")
	// @ValidateRule(dataType=0,label="订单单位到基本单位转换的分母",required=false)
	// private BigDecimal umren;
	//    
	//    
	// /*
	// * 价格单位
	// */
	// @Column(name="PEINH")
	// @ValidateRule(dataType=0,label="价格单位",required=false)
	// private BigDecimal peinh;
	//    
	//    
	// /*
	// * PO货币的全部订单值
	// */
	// @Column(name="BRTWR")
	// @ValidateRule(dataType=9,label="PO货币的全部订单值",maxLength= 16,required=false)
	// private String brtwr;
	//    
	// /*
	// * 投标/报价提交的截止日期
	// */
	// @Column(name="AGDAT")
	// @ValidateRule(dataType=8,label="投标/报价提交的截止日期",required=false)
	// private Date agdat;
	//    
	// /*
	// * 以天计的收货处理时间
	// */
	// @Column(name="WEBAZ")
	// @ValidateRule(dataType=0,label="以天计的收货处理时间",required=false)
	// private BigDecimal webaz;
	//    
	// /*
	// * 销售税代码
	// */
	// @Column(name="MWSKZ")
	// @ValidateRule(dataType=9,label="销售税代码",maxLength= 2,required=false)
	// private String mwskz;
	//    
	// /*
	// * 结算组1（采购）
	// */
	// @Column(name="BONUS")
	// @ValidateRule(dataType=9,label="结算组1（采购）",maxLength= 2,required=false)
	// private String bonus;
	//    
	// /*
	// * 库存类型
	// */
	// @Column(name="INSMK")
	// @ValidateRule(dataType=9,label="库存类型",maxLength= 1,required=false)
	// private String insmk;
	//    
	// /*
	// * 标识: 更新信息记录
	// */
	// @Column(name="SPINF")
	// @ValidateRule(dataType=9,label="标识: 更新信息记录",maxLength= 1,required=false)
	// private String spinf;
	//    
	// /*
	// * 价格打印输出
	// */
	// @Column(name="PRSDR")
	// @ValidateRule(dataType=9,label="价格打印输出",maxLength= 1,required=false)
	// private String prsdr;
	//    
	// /*
	// * 标识：估计价格
	// */
	// @Column(name="SCHPR")
	// @ValidateRule(dataType=9,label="标识：估计价格",maxLength= 1,required=false)
	// private String schpr;
	//    
	// /*
	// * 催询单数
	// */
	// @Column(name="MAHNZ")
	// @ValidateRule(dataType=0,label="催询单数",required=false)
	// private BigDecimal mahnz;
	//    
	// /*
	// * 第一封催询单天数
	// */
	// @Column(name="MAHN1")
	// @ValidateRule(dataType=0,label="第一封催询单天数",required=false)
	// private BigDecimal mahn1;
	//    
	// /*
	// * 第二封催询单的天数
	// */
	// @Column(name="MAHN2")
	// @ValidateRule(dataType=0,label="第二封催询单的天数",required=false)
	// private BigDecimal mahn2;
	//    
	// /*
	// * 第三封催询单的天数
	// */
	// @Column(name="MAHN3")
	// @ValidateRule(dataType=0,label="第三封催询单的天数",required=false)
	// private BigDecimal mahn3;
	//    
	// /*
	// * 过量交货限度
	// */
	// @Column(name="UEBTO")
	// @ValidateRule(dataType=0,label="过量交货限度",required=false)
	// private BigDecimal uebto;
	//    
	// /*
	// * 标识：允许未限制的过量交货
	// */
	// @Column(name="UEBTK")
	// @ValidateRule(dataType=9,label="标识：允许未限制的过量交货",maxLength=
	// 1,required=false)
	// private String uebtk;
	//    
	// /*
	// * 交货不足限度
	// */
	// @Column(name="UNTTO")
	// @ValidateRule(dataType=0,label="交货不足限度",required=false)
	// private BigDecimal untto;
	//    
	// /*
	// * 评估类型
	// */
	// @Column(name="BWTAR")
	// @ValidateRule(dataType=9,label="评估类型",maxLength= 10,required=false)
	// private String bwtar;
	//    
	// /*
	// * 评估类别
	// */
	// @Column(name="BWTTY")
	// @ValidateRule(dataType=9,label="评估类别",maxLength= 1,required=false)
	// private String bwtty;
	//    
	// /*
	// * 拒绝标识
	// */
	// @Column(name="ABSKZ")
	// @ValidateRule(dataType=9,label="拒绝标识",maxLength= 1,required=false)
	// private String abskz;
	//    
	// /*
	// * 报价的内部说明
	// */
	// @Column(name="AGMEM")
	// @ValidateRule(dataType=9,label="报价的内部说明",maxLength= 3,required=false)
	// private String agmem;
	//    
	// /*
	// * "交货已完成"标识
	// */
	// @Column(name="ELIKZ")
	// @ValidateRule(dataType=9,label=""交货已完成"标识",maxLength= 1,required=false)
	// private String elikz;
	//    
	// /*
	// * 最后发票标识
	// */
	// @Column(name="EREKZ")
	// @ValidateRule(dataType=9,label="最后发票标识",maxLength= 1,required=false)
	// private String erekz;
	//    
	// /*
	// * 采购凭证中的项目类别
	// */
	// @Column(name="PSTYP")
	// @ValidateRule(dataType=9,label="采购凭证中的项目类别",maxLength= 1,required=false)
	// private String pstyp;
	//    
	// /*
	// * 科目分配类别
	// */
	// @Column(name="KNTTP")
	// @ValidateRule(dataType=9,label="科目分配类别",maxLength= 1,required=false)
	// private String knttp;
	//    
	// /*
	// * 消耗过帐
	// */
	// @Column(name="KZVBR")
	// @ValidateRule(dataType=9,label="消耗过帐",maxLength= 1,required=false)
	// private String kzvbr;
	//    
	// /*
	// * 多科目设置的分配标识
	// */
	// @Column(name="VRTKZ")
	// @ValidateRule(dataType=9,label="多科目设置的分配标识",maxLength= 1,required=false)
	// private String vrtkz;
	//    
	// /*
	// * 部分发票标识
	// */
	// @Column(name="TWRKZ")
	// @ValidateRule(dataType=9,label="部分发票标识",maxLength= 1,required=false)
	// private String twrkz;
	//    
	// /*
	// * 货物收据标识
	// */
	// @Column(name="WEPOS")
	// @ValidateRule(dataType=9,label="货物收据标识",maxLength= 1,required=false)
	// private String wepos;
	//    
	// /*
	// * 收货，未估价
	// */
	// @Column(name="WEUNB")
	// @ValidateRule(dataType=9,label="收货，未估价",maxLength= 1,required=false)
	// private String weunb;
	//    
	// /*
	// * 发票收据标识
	// */
	// @Column(name="REPOS")
	// @ValidateRule(dataType=9,label="发票收据标识",maxLength= 1,required=false)
	// private String repos;
	//    
	// /*
	// * 标识：基于收货的发票验证
	// */
	// @Column(name="WEBRE")
	// @ValidateRule(dataType=9,label="标识：基于收货的发票验证",maxLength=
	// 1,required=false)
	// private String webre;
	//    
	// /*
	// * 订单回执需求
	// */
	// @Column(name="KZABS")
	// @ValidateRule(dataType=9,label="订单回执需求",maxLength= 1,required=false)
	// private String kzabs;
	//    
	// /*
	// * 订单回执编号
	// */
	// @Column(name="LABNR")
	// @ValidateRule(dataType=9,label="订单回执编号",maxLength= 20,required=false)
	// private String labnr;
	//    
	// /*
	// * 重要的采购协议号
	// */
	// @Column(name="KONNR")
	// @ValidateRule(dataType=9,label="重要的采购协议号",maxLength= 10,required=false)
	// private String konnr;
	//    
	// /*
	// * 主要采购协议的项目编号
	// */
	// @Column(name="KTPNR")
	// @ValidateRule(dataType=0,label="主要采购协议的项目编号",required=false)
	// private String ktpnr;
	//    
	// /*
	// * 同意的累积数量对帐日期
	// */
	// @Column(name="ABDAT")
	// @ValidateRule(dataType=8,label="同意的累积数量对帐日期",required=false)
	// private Date abdat;
	//    
	// /*
	// * 允许的累计数量
	// */
	// @Column(name="ABFTZ")
	// @ValidateRule(dataType=9,label="允许的累计数量",maxLength= 17,required=false)
	// private String abftz;
	//    
	// /*
	// * 确定的域(生产前)
	// */
	// @Column(name="ETFZ1")
	// @ValidateRule(dataType=0,label="确定的域(生产前)",required=false)
	// private BigDecimal etfz1;
	//    
	// /*
	// * 平衡区域 (向前为物料采购)
	// */
	// @Column(name="ETFZ2")
	// @ValidateRule(dataType=0,label="平衡区域 (向前为物料采购)",required=false)
	// private BigDecimal etfz2;
	//    
	// /*
	// * 关于物料计划的固定/平衡区域的绑定
	// */
	// @Column(name="KZSTU")
	// @ValidateRule(dataType=9,label="关于物料计划的固定/平衡区域的绑定",maxLength=
	// 1,required=false)
	// private String kzstu;
	//    
	// /*
	// * 排除含有物料类的框架协议
	// */
	// @Column(name="NOTKZ")
	// @ValidateRule(dataType=9,label="排除含有物料类的框架协议",maxLength=
	// 1,required=false)
	// private String notkz;
	//    
	// /*
	// * 基本计量单位
	// */
	// @Column(name="LMEIN")
	// @ValidateRule(dataType=9,label="基本计量单位",maxLength= 3,required=false)
	// private String lmein;
	//    
	// /*
	// * 装运须知
	// */
	// @Column(name="EVERS")
	// @ValidateRule(dataType=9,label="装运须知",maxLength= 2,required=false)
	// private String evers;
	//    
	// /*
	// * 在框架协议中凭证货币的目标值
	// */
	// @Column(name="ZWERT")
	// @ValidateRule(dataType=9,label="在框架协议中凭证货币的目标值",maxLength=
	// 16,required=false)
	// private String zwert;
	//    
	// /*
	// * 不可打折的进项税
	// */
	// @Column(name="NAVNW")
	// @ValidateRule(dataType=9,label="不可打折的进项税",maxLength= 16,required=false)
	// private String navnw;
	//    
	// /*
	// * 标准审批订单数量
	// */
	// @Column(name="ABMNG")
	// @ValidateRule(dataType=9,label="标准审批订单数量",maxLength= 17,required=false)
	// private String abmng;
	//    
	// /*
	// * 价格确定日期
	// */
	// @Column(name="PRDAT")
	// @ValidateRule(dataType=8,label="价格确定日期",required=false)
	// private Date prdat;
	//    
	// /*
	// * 采购凭证类别
	// */
	// @Column(name="BSTYP")
	// @ValidateRule(dataType=9,label="采购凭证类别",maxLength= 1,required=false)
	// private String bstyp;
	//    
	// /*
	// * 项目的有效值
	// */
	// @Column(name="EFFWR")
	// @ValidateRule(dataType=9,label="项目的有效值",maxLength= 16,required=false)
	// private String effwr;
	//    
	// /*
	// * 项目影响委托
	// */
	// @Column(name="XOBLR")
	// @ValidateRule(dataType=9,label="项目影响委托",maxLength= 1,required=false)
	// private String xoblr;
	//    
	// /*
	// * 客户
	// */
	// @Column(name="KUNNR")
	// @ValidateRule(dataType=9,label="客户",maxLength= 10,required=false)
	// private String kunnr;
	//    
	// /*
	// * 采购凭证项目中的人工地址号
	// */
	// @Column(name="ADRNR")
	// @ValidateRule(dataType=9,label="采购凭证项目中的人工地址号",maxLength=
	// 10,required=false)
	// private String adrnr;
	//    
	// /*
	// * 供应商条件组
	// */
	// @Column(name="EKKOL")
	// @ValidateRule(dataType=9,label="供应商条件组",maxLength= 4,required=false)
	// private String ekkol;
	//    
	// /*
	// * 项目不允许现金折扣
	// */
	// @Column(name="SKTOF")
	// @ValidateRule(dataType=9,label="项目不允许现金折扣",maxLength= 1,required=false)
	// private String sktof;
	//    
	// /*
	// * 更新组用于统计更新
	// */
	// @Column(name="STAFO")
	// @ValidateRule(dataType=9,label="更新组用于统计更新",maxLength= 6,required=false)
	// private String stafo;
	//    
	// /*
	// * 按天的计划交货时间
	// */
	// @Column(name="PLIFZ")
	// @ValidateRule(dataType=0,label="按天的计划交货时间",required=false)
	// private BigDecimal plifz;
	//    
	// /*
	// * 净重
	// */
	// @Column(name="NTGEW")
	// @ValidateRule(dataType=9,label="净重",maxLength= 17,required=false)
	// private String ntgew;
	//    
	// /*
	// * 重量单位
	// */
	// @Column(name="GEWEI")
	// @ValidateRule(dataType=9,label="重量单位",maxLength= 3,required=false)
	// private String gewei;
	//    
	// /*
	// * 地区税务代码
	// */
	// @Column(name="TXJCD")
	// @ValidateRule(dataType=9,label="地区税务代码",maxLength= 15,required=false)
	// private String txjcd;
	//    
	// /*
	// * 标识: 打印相关的计划行存在
	// */
	// @Column(name="ETDRK")
	// @ValidateRule(dataType=9,label="标识: 打印相关的计划行存在",maxLength=
	// 1,required=false)
	// private String etdrk;
	//    
	// /*
	// * 特殊库存标识
	// */
	// @Column(name="SOBKZ")
	// @ValidateRule(dataType=9,label="特殊库存标识",maxLength= 1,required=false)
	// private String sobkz;
	//    
	// /*
	// * 结算预定号
	// */
	// @Column(name="ARSNR")
	// @ValidateRule(dataType=0,label="结算预定号",required=false)
	// private String arsnr;
	//    
	// /*
	// * 结算的预定的项目号
	// */
	// @Column(name="ARSPS")
	// @ValidateRule(dataType=0,label="结算的预定的项目号",required=false)
	// private String arsps;
	//    
	// /*
	// * 质量检查标识不能被更改
	// */
	// @Column(name="INSNC")
	// @ValidateRule(dataType=9,label="质量检查标识不能被更改",maxLength= 1,required=false)
	// private String insnc;
	//    
	// /*
	// * 采购中质量管理的控制码
	// */
	// @Column(name="SSQSS")
	// @ValidateRule(dataType=9,label="采购中质量管理的控制码",maxLength= 8,required=false)
	// private String ssqss;
	//    
	// /*
	// * 证书类型
	// */
	// @Column(name="ZGTYP")
	// @ValidateRule(dataType=9,label="证书类型",maxLength= 4,required=false)
	// private String zgtyp;
	//    
	// /*
	// * 国际文件号(EAN/UPC)
	// */
	// @Column(name="EAN11")
	// @ValidateRule(dataType=9,label="国际文件号(EAN/UPC)",maxLength=
	// 18,required=false)
	// private String ean11;
	//    
	// /*
	// * 确认控制代码
	// */
	// @Column(name="BSTAE")
	// @ValidateRule(dataType=9,label="确认控制代码",maxLength= 4,required=false)
	// private String bstae;
	//    
	// /*
	// * 版次
	// */
	// @Column(name="REVLV")
	// @ValidateRule(dataType=9,label="版次",maxLength= 2,required=false)
	// private String revlv;
	//    
	// /*
	// * 基金
	// */
	// @Column(name="GEBER")
	// @ValidateRule(dataType=9,label="基金",maxLength= 10,required=false)
	// private String geber;
	//    
	// /*
	// * 基金中心
	// */
	// @Column(name="FISTL")
	// @ValidateRule(dataType=9,label="基金中心",maxLength= 16,required=false)
	// private String fistl;
	//    
	// /*
	// * 承诺项目
	// */
	// @Column(name="FIPOS")
	// @ValidateRule(dataType=9,label="承诺项目",maxLength= 24,required=false)
	// private String fipos;
	//    
	// /*
	// * 对业务伙伴报告的业务部门
	// */
	// @Column(name="KO_GSBER")
	// @ValidateRule(dataType=9,label="对业务伙伴报告的业务部门",maxLength=
	// 4,required=false)
	// private String ko_gsber;
	//    
	// /*
	// * 业务伙伴的假设业务部门
	// */
	// @Column(name="KO_PARGB")
	// @ValidateRule(dataType=9,label="业务伙伴的假设业务部门",maxLength= 4,required=false)
	// private String ko_pargb;
	//    
	// /*
	// * 利润中心
	// */
	// @Column(name="KO_PRCTR")
	// @ValidateRule(dataType=9,label="利润中心",maxLength= 10,required=false)
	// private String ko_prctr;
	//    
	// /*
	// * 伙伴利润中心
	// */
	// @Column(name="KO_PPRCTR")
	// @ValidateRule(dataType=9,label="伙伴利润中心",maxLength= 10,required=false)
	// private String ko_pprctr;
	//    
	// /*
	// * 价格确定（定价）日期控制
	// */
	// @Column(name="MEPRF")
	// @ValidateRule(dataType=9,label="价格确定（定价）日期控制",maxLength=
	// 1,required=false)
	// private String meprf;
	//    
	// /*
	// * 毛重
	// */
	// @Column(name="BRGEW")
	// @ValidateRule(dataType=9,label="毛重",maxLength= 17,required=false)
	// private String brgew;
	//    
	// /*
	// * 业务量
	// */
	// @Column(name="VOLUM")
	// @ValidateRule(dataType=9,label="业务量",maxLength= 17,required=false)
	// private String volum;
	//    
	// /*
	// * 体积单位
	// */
	// @Column(name="VOLEH")
	// @ValidateRule(dataType=9,label="体积单位",maxLength= 3,required=false)
	// private String voleh;
	//    
	// /*
	// * 国际贸易条款 (部分1)
	// */
	// @Column(name="INCO1")
	// @ValidateRule(dataType=9,label="国际贸易条款 (部分1)",maxLength=
	// 3,required=false)
	// private String inco1;
	//    
	// /*
	// * 国际贸易条件(部分2)
	// */
	// @Column(name="INCO2")
	// @ValidateRule(dataType=9,label="国际贸易条件(部分2)",maxLength=
	// 28,required=false)
	// private String inco2;
	//    
	// /*
	// * 提前采购：项目库存
	// */
	// @Column(name="VORAB")
	// @ValidateRule(dataType=9,label="提前采购：项目库存",maxLength= 1,required=false)
	// private String vorab;
	//    
	// /*
	// * 原始供应商
	// */
	// @Column(name="KOLIF")
	// @ValidateRule(dataType=9,label="原始供应商",maxLength= 10,required=false)
	// private String kolif;
	//    
	// /*
	// * 供应商子范围
	// */
	// @Column(name="LTSNR")
	// @ValidateRule(dataType=9,label="供应商子范围",maxLength= 6,required=false)
	// private String ltsnr;
	//    
	// /*
	// * 软件包编号
	// */
	// @Column(name="PACKNO")
	// @ValidateRule(dataType=0,label="软件包编号",required=false)
	// private String packno;
	//    
	// /*
	// * 发票计划编号
	// */
	// @Column(name="FPLNR")
	// @ValidateRule(dataType=9,label="发票计划编号",maxLength= 10,required=false)
	// private String fplnr;
	//    
	// /*
	// * 当前未使用
	// */
	// @Column(name="GNETWR")
	// @ValidateRule(dataType=9,label="当前未使用",maxLength= 16,required=false)
	// private String gnetwr;
	//    
	// /*
	// * 项目是统计的
	// */
	// @Column(name="STAPO")
	// @ValidateRule(dataType=9,label="项目是统计的",maxLength= 1,required=false)
	// private String stapo;
	//    
	// /*
	// * 采购凭证中上层项目
	// */
	// @Column(name="UEBPO")
	// @ValidateRule(dataType=0,label="采购凭证中上层项目",required=false)
	// private String uebpo;
	//    
	// /*
	// * 最近可能的收货
	// */
	// @Column(name="LEWED")
	// @ValidateRule(dataType=8,label="最近可能的收货",required=false)
	// private Date lewed;
	//    
	// /*
	// * 被供应的供应商/接受供货者
	// */
	// @Column(name="EMLIF")
	// @ValidateRule(dataType=9,label="被供应的供应商/接受供货者",maxLength=
	// 10,required=false)
	// private String emlif;
	//    
	// /*
	// * 分包合同供应商
	// */
	// @Column(name="LBLKZ")
	// @ValidateRule(dataType=9,label="分包合同供应商",maxLength= 1,required=false)
	// private String lblkz;
	//    
	// /*
	// * 一般可配置物料
	// */
	// @Column(name="SATNR")
	// @ValidateRule(dataType=9,label="一般可配置物料",maxLength= 18,required=false)
	// private String satnr;
	//    
	// /*
	// * 物料类别
	// */
	// @Column(name="ATTYP")
	// @ValidateRule(dataType=9,label="物料类别",maxLength= 2,required=false)
	// private String attyp;
	//    
	// /*
	// * 看板标识
	// */
	// @Column(name="KANBA")
	// @ValidateRule(dataType=9,label="看板标识",maxLength= 1,required=false)
	// private String kanba;
	//    
	// /*
	// * 交货地址编号
	// */
	// @Column(name="ADRN2")
	// @ValidateRule(dataType=9,label="交货地址编号",maxLength= 10,required=false)
	// private String adrn2;
	//    
	// /*
	// * 配置(内部对象号)
	// */
	// @Column(name="CUOBJ")
	// @ValidateRule(dataType=0,label="配置(内部对象号)",required=false)
	// private String cuobj;
	//    
	// /*
	// * 评估收货结算 (ERS)
	// */
	// @Column(name="XERSY")
	// @ValidateRule(dataType=9,label="评估收货结算 (ERS)",maxLength=
	// 1,required=false)
	// private String xersy;
	//    
	// /*
	// * 基于收货的结算的开始日期
	// */
	// @Column(name="EILDT")
	// @ValidateRule(dataType=8,label="基于收货的结算的开始日期",required=false)
	// private Date eildt;
	//    
	// /*
	// * 上一次传输
	// */
	// @Column(name="DRDAT")
	// @ValidateRule(dataType=8,label="上一次传输",required=false)
	// private Date drdat;
	//    
	// /*
	// * 时间
	// */
	// @Column(name="DRUHR")
	// @ValidateRule(dataType=9,label="时间",maxLength= 8,required=false)
	// private String druhr;
	//    
	// /*
	// * 顺序编号
	// */
	// @Column(name="DRUNR")
	// @ValidateRule(dataType=0,label="顺序编号",required=false)
	// private String drunr;
	//    
	// /*
	// * 促销
	// */
	// @Column(name="AKTNR")
	// @ValidateRule(dataType=9,label="促销",maxLength= 10,required=false)
	// private String aktnr;
	//    
	// /*
	// * 分配表编号
	// */
	// @Column(name="ABELN")
	// @ValidateRule(dataType=9,label="分配表编号",maxLength= 10,required=false)
	// private String abeln;
	//    
	// /*
	// * 分配表项目号
	// */
	// @Column(name="ABELP")
	// @ValidateRule(dataType=0,label="分配表项目号",required=false)
	// private String abelp;
	//    
	// /*
	// * 点数
	// */
	// @Column(name="ANZPU")
	// @ValidateRule(dataType=9,label="点数",maxLength= 17,required=false)
	// private String anzpu;
	//    
	// /*
	// * 点单位
	// */
	// @Column(name="PUNEI")
	// @ValidateRule(dataType=9,label="点单位",maxLength= 3,required=false)
	// private String punei;
	//    
	// /*
	// * 季节类别
	// */
	// @Column(name="SAISO")
	// @ValidateRule(dataType=9,label="季节类别",maxLength= 4,required=false)
	// private String saiso;
	//    
	// /*
	// * 季度年
	// */
	// @Column(name="SAISJ")
	// @ValidateRule(dataType=9,label="季度年",maxLength= 4,required=false)
	// private String saisj;
	//    
	// /*
	// * 结算组2 (回扣结算, 采购)
	// */
	// @Column(name="EBON2")
	// @ValidateRule(dataType=9,label="结算组2 (回扣结算, 采购)",maxLength=
	// 2,required=false)
	// private String ebon2;
	//    
	// /*
	// * 结算组3（回扣结算,采购）
	// */
	// @Column(name="EBON3")
	// @ValidateRule(dataType=9,label="结算组3（回扣结算,采购）",maxLength=
	// 2,required=false)
	// private String ebon3;
	//    
	// /*
	// * 与后继（期末回扣）结算相关的项目
	// */
	// @Column(name="EBONF")
	// @ValidateRule(dataType=9,label="与后继（期末回扣）结算相关的项目",maxLength=
	// 1,required=false)
	// private String ebonf;
	//    
	// /*
	// * 物料帐簿在物料层被激活
	// */
	// @Column(name="MLMAA")
	// @ValidateRule(dataType=9,label="物料帐簿在物料层被激活",maxLength= 1,required=false)
	// private String mlmaa;
	//    
	// /*
	// * 最短剩余货架寿命
	// */
	// @Column(name="MHDRZ")
	// @ValidateRule(dataType=0,label="最短剩余货架寿命",required=false)
	// private BigDecimal mhdrz;
	//    
	// /*
	// * RFQ 编号
	// */
	// @Column(name="ANFNR")
	// @ValidateRule(dataType=9,label="RFQ 编号",maxLength= 10,required=false)
	// private String anfnr;
	//    
	// /*
	// * RFQ 项目号码
	// */
	// @Column(name="ANFPS")
	// @ValidateRule(dataType=0,label="RFQ 项目号码",required=false)
	// private String anfps;
	//    
	// /*
	// * 原始配置
	// */
	// @Column(name="KZKFG")
	// @ValidateRule(dataType=9,label="原始配置",maxLength= 1,required=false)
	// private String kzkfg;
	//    
	// /*
	// * 配额分配使用
	// */
	// @Column(name="USEQU")
	// @ValidateRule(dataType=9,label="配额分配使用",maxLength= 1,required=false)
	// private String usequ;
	//    
	// /*
	// * 库存调拨的特殊库存标识
	// */
	// @Column(name="UMSOK")
	// @ValidateRule(dataType=9,label="库存调拨的特殊库存标识",maxLength= 1,required=false)
	// private String umsok;
	//    
	// /*
	// * 采购申请编号
	// */
	// @Column(name="BANFN")
	// @ValidateRule(dataType=9,label="采购申请编号",maxLength= 10,required=false)
	// private String banfn;
	//    
	// /*
	// * 采购申请的项目编号
	// */
	// @Column(name="BNFPO")
	// @ValidateRule(dataType=0,label="采购申请的项目编号",required=false)
	// private String bnfpo;
	//    
	// /*
	// * 物料类型
	// */
	// @Column(name="MTART")
	// @ValidateRule(dataType=9,label="物料类型",maxLength= 4,required=false)
	// private String mtart;
	//    
	// /*
	// * 子项目类别，采购凭证
	// */
	// @Column(name="UPTYP")
	// @ValidateRule(dataType=9,label="子项目类别，采购凭证",maxLength= 1,required=false)
	// private String uptyp;
	//    
	// /*
	// * 子项目存在
	// */
	// @Column(name="UPVOR")
	// @ValidateRule(dataType=9,label="子项目存在",maxLength= 1,required=false)
	// private String upvor;
	//    
	// /*
	// * 从条件定价过程小计1
	// */
	// @Column(name="KZWI1")
	// @ValidateRule(dataType=9,label="从条件定价过程小计1",maxLength= 18,required=false)
	// private String kzwi1;
	//    
	// /*
	// * 从条件定价过程小计2
	// */
	// @Column(name="KZWI2")
	// @ValidateRule(dataType=9,label="从条件定价过程小计2",maxLength= 18,required=false)
	// private String kzwi2;
	//    
	// /*
	// * 从条件定价过程小计3
	// */
	// @Column(name="KZWI3")
	// @ValidateRule(dataType=9,label="从条件定价过程小计3",maxLength= 18,required=false)
	// private String kzwi3;
	//    
	// /*
	// * 从条件定价过程小计4
	// */
	// @Column(name="KZWI4")
	// @ValidateRule(dataType=9,label="从条件定价过程小计4",maxLength= 18,required=false)
	// private String kzwi4;
	//    
	// /*
	// * 从条件定价过程小计5
	// */
	// @Column(name="KZWI5")
	// @ValidateRule(dataType=9,label="从条件定价过程小计5",maxLength= 18,required=false)
	// private String kzwi5;
	//    
	// /*
	// * 从条件定价过程小计6
	// */
	// @Column(name="KZWI6")
	// @ValidateRule(dataType=9,label="从条件定价过程小计6",maxLength= 18,required=false)
	// private String kzwi6;
	//    
	// /*
	// * 子项目的处理代码
	// */
	// @Column(name="SIKGR")
	// @ValidateRule(dataType=9,label="子项目的处理代码",maxLength= 3,required=false)
	// private String sikgr;
	//    
	// /*
	// * 最大累计物料提前量
	// */
	// @Column(name="MFZHI")
	// @ValidateRule(dataType=9,label="最大累计物料提前量",maxLength= 19,required=false)
	// private String mfzhi;
	//    
	// /*
	// * 最大累计产品向前数量
	// */
	// @Column(name="FFZHI")
	// @ValidateRule(dataType=9,label="最大累计产品向前数量",maxLength= 19,required=false)
	// private String ffzhi;
	//    
	// /*
	// * 退货项目
	// */
	// @Column(name="RETPO")
	// @ValidateRule(dataType=9,label="退货项目",maxLength= 1,required=false)
	// private String retpo;
	//    
	// /*
	// * 与分配表相关
	// */
	// @Column(name="AUREL")
	// @ValidateRule(dataType=9,label="与分配表相关",maxLength= 1,required=false)
	// private String aurel;
	//    
	// /*
	// * 订货原因
	// */
	// @Column(name="BSGRU")
	// @ValidateRule(dataType=9,label="订货原因",maxLength= 3,required=false)
	// private String bsgru;
	//    
	// /*
	// * 退货给供应商的交货类型
	// */
	// @Column(name="LFRET")
	// @ValidateRule(dataType=9,label="退货给供应商的交货类型",maxLength= 4,required=false)
	// private String lfret;
	//    
	// /*
	// * 物料运输组
	// */
	// @Column(name="MFRGR")
	// @ValidateRule(dataType=9,label="物料运输组",maxLength= 8,required=false)
	// private String mfrgr;
	//    
	// /*
	// * 针对物料的折扣类型
	// */
	// @Column(name="NRFHG")
	// @ValidateRule(dataType=9,label="针对物料的折扣类型",maxLength= 1,required=false)
	// private String nrfhg;
	//    
	// /*
	// * NBM编码(巴西)
	// */
	// @Column(name="J_1BNBM")
	// @ValidateRule(dataType=9,label="NBM编码(巴西)",maxLength= 16,required=false)
	// private String j_1bnbm;
	//    
	// /*
	// * 物料用途
	// */
	// @Column(name="J_1BMATUSE")
	// @ValidateRule(dataType=9,label="物料用途",maxLength= 1,required=false)
	// private String j_1bmatuse;
	//    
	// /*
	// * 物料源
	// */
	// @Column(name="J_1BMATORG")
	// @ValidateRule(dataType=9,label="物料源",maxLength= 1,required=false)
	// private String j_1bmatorg;
	//    
	// /*
	// * 自制产品
	// */
	// @Column(name="J_1BOWNPRO")
	// @ValidateRule(dataType=9,label="自制产品",maxLength= 1,required=false)
	// private String j_1bownpro;
	//    
	// /*
	// * 物料: CFOP类别
	// */
	// @Column(name="J_1BINDUST")
	// @ValidateRule(dataType=9,label="物料: CFOP类别",maxLength= 2,required=false)
	// private String j_1bindust;
	//    
	// /*
	// * 批准创建参数文件
	// */
	// @Column(name="ABUEB")
	// @ValidateRule(dataType=9,label="批准创建参数文件",maxLength= 4,required=false)
	// private String abueb;
	//    
	// /*
	// * 下一个预测交货计划传输
	// */
	// @Column(name="NLABD")
	// @ValidateRule(dataType=8,label="下一个预测交货计划传输",required=false)
	// private Date nlabd;
	//    
	// /*
	// * 下一个 JIT 交货计划传输
	// */
	// @Column(name="NFABD")
	// @ValidateRule(dataType=8,label="下一个 JIT 交货计划传输",required=false)
	// private Date nfabd;
	//    
	// /*
	// * 特定库存的评估
	// */
	// @Column(name="KZBWS")
	// @ValidateRule(dataType=9,label="特定库存的评估",maxLength= 1,required=false)
	// private String kzbws;
	//    
	// /*
	// * 回扣基数 1
	// */
	// @Column(name="BONBA")
	// @ValidateRule(dataType=9,label="回扣基数 1",maxLength= 18,required=false)
	// private String bonba;
	//    
	// /*
	// * 标识: 与 JIT 交货计划相关的物料
	// */
	// @Column(name="FABKZ")
	// @ValidateRule(dataType=9,label="标识: 与 JIT 交货计划相关的物料",maxLength=
	// 1,required=false)
	// private String fabkz;
	//    
	// /*
	// * 通货膨胀指数
	// */
	// @Column(name="J_1AINDXP")
	// @ValidateRule(dataType=9,label="通货膨胀指数",maxLength= 5,required=false)
	// private String j_1aindxp;
	//    
	// /*
	// * 通货膨胀指数日期
	// */
	// @Column(name="J_1AIDATEP")
	// @ValidateRule(dataType=8,label="通货膨胀指数日期",required=false)
	// private Date j_1aidatep;
	//    
	// /*
	// * 制造商部件参数文件
	// */
	// @Column(name="MPROF")
	// @ValidateRule(dataType=9,label="制造商部件参数文件",maxLength= 4,required=false)
	// private String mprof;
	//    
	// /*
	// * "出库交货已完成" 标识
	// */
	// @Column(name="EGLKZ")
	// @ValidateRule(dataType=9,label=""出库交货已完成" 标识",maxLength=
	// 1,required=false)
	// private String eglkz;
	//    
	// /*
	// * 项目级别的部分交货 (库存转储)
	// */
	// @Column(name="KZTLF")
	// @ValidateRule(dataType=9,label="项目级别的部分交货 (库存转储)",maxLength=
	// 1,required=false)
	// private String kztlf;
	//    
	// /*
	// * 计量单位使用
	// */
	// @Column(name="KZFME")
	// @ValidateRule(dataType=9,label="计量单位使用",maxLength= 1,required=false)
	// private String kzfme;
	//    
	// /*
	// * 舍入参数文件
	// */
	// @Column(name="RDPRF")
	// @ValidateRule(dataType=9,label="舍入参数文件",maxLength= 4,required=false)
	// private String rdprf;
	//    
	// /*
	// * 参数变式/标准变式
	// */
	// @Column(name="TECHS")
	// @ValidateRule(dataType=9,label="参数变式/标准变式",maxLength= 12,required=false)
	// private String techs;
	//    
	// /*
	// * 配置已修改
	// */
	// @Column(name="CHG_SRV")
	// @ValidateRule(dataType=9,label="配置已修改",maxLength= 1,required=false)
	// private String chg_srv;
	//    
	// /*
	// * 没有此项目的发票尽管不免费
	// */
	// @Column(name="CHG_FPLNR")
	// @ValidateRule(dataType=9,label="没有此项目的发票尽管不免费",maxLength=
	// 1,required=false)
	// private String chg_fplnr;
	//    
	// /*
	// * 制造商零件编号
	// */
	// @Column(name="MFRPN")
	// @ValidateRule(dataType=9,label="制造商零件编号",maxLength= 40,required=false)
	// private String mfrpn;
	//    
	// /*
	// * 制造商编号
	// */
	// @Column(name="MFRNR")
	// @ValidateRule(dataType=9,label="制造商编号",maxLength= 10,required=false)
	// private String mfrnr;
	//    
	// /*
	// * 外部制造商代码名称或编号
	// */
	// @Column(name="EMNFR")
	// @ValidateRule(dataType=9,label="外部制造商代码名称或编号",maxLength=
	// 10,required=false)
	// private String emnfr;
	//    
	// /*
	// * SD 交货的已冻结项目
	// */
	// @Column(name="NOVET")
	// @ValidateRule(dataType=9,label="SD 交货的已冻结项目",maxLength= 1,required=false)
	// private String novet;
	//    
	// /*
	// * 需求者/请求者姓名
	// */
	// @Column(name="AFNAM")
	// @ValidateRule(dataType=9,label="需求者/请求者姓名",maxLength= 12,required=false)
	// private String afnam;
	//    
	// /*
	// * 接收位置的时区
	// */
	// @Column(name="TZONRC")
	// @ValidateRule(dataType=9,label="接收位置的时区",maxLength= 6,required=false)
	// private String tzonrc;
	//    
	// /*
	// * 货架寿命到期日的期间标识
	// */
	// @Column(name="IPRKZ")
	// @ValidateRule(dataType=9,label="货架寿命到期日的期间标识",maxLength=
	// 1,required=false)
	// private String iprkz;
	//    
	// /*
	// * 基于服务的发票校验标识
	// */
	// @Column(name="LEBRE")
	// @ValidateRule(dataType=9,label="基于服务的发票校验标识",maxLength= 1,required=false)
	// private String lebre;
	//    
	// /*
	// * MRP 范围
	// */
	// @Column(name="BERID")
	// @ValidateRule(dataType=9,label="MRP 范围",maxLength= 10,required=false)
	// private String berid;
	//    
	// /*
	// * 无发票时的项目定价
	// */
	// @Column(name="XCONDITIONS")
	// @ValidateRule(dataType=9,label="无发票时的项目定价",maxLength= 1,required=false)
	// private String xconditions;
	//    
	// /*
	// * APO 作为计划系统
	// */
	// @Column(name="APOMS")
	// @ValidateRule(dataType=9,label="APO 作为计划系统",maxLength= 1,required=false)
	// private String apoms;
	//    
	// /*
	// * 存货转储的过帐逻辑
	// */
	// @Column(name="CCOMP")
	// @ValidateRule(dataType=9,label="存货转储的过帐逻辑",maxLength= 1,required=false)
	// private String ccomp;
	//    
	// /*
	// * 同意
	// */
	// @Column(name="GRANT_NBR")
	// @ValidateRule(dataType=9,label="同意",maxLength= 20,required=false)
	// private String grant_nbr;
	//    
	// /*
	// * 功能范围
	// */
	// @Column(name="FKBER")
	// @ValidateRule(dataType=9,label="功能范围",maxLength= 16,required=false)
	// private String fkber;
	//    
	// /*
	// * 采购凭证项目的状态
	// */
	// @Column(name="STATUS")
	// @ValidateRule(dataType=9,label="采购凭证项目的状态",maxLength= 1,required=false)
	// private String status;
	//    
	// /*
	// * 库存运输订单发货存储地点
	// */
	// @Column(name="RESLO")
	// @ValidateRule(dataType=9,label="库存运输订单发货存储地点",maxLength=
	// 4,required=false)
	// private String reslo;
	//    
	// /*
	// * 专项基金凭证编号
	// */
	// @Column(name="KBLNR")
	// @ValidateRule(dataType=9,label="专项基金凭证编号",maxLength= 10,required=false)
	// private String kblnr;
	//    
	// /*
	// * 资金储备：凭证项
	// */
	// @Column(name="KBLPOS")
	// @ValidateRule(dataType=0,label="资金储备：凭证项",required=false)
	// private String kblpos;
	//    
	// /*
	// * 原始接受
	// */
	// @Column(name="WEORA")
	// @ValidateRule(dataType=9,label="原始接受",maxLength= 1,required=false)
	// private String weora;
	//    
	// /*
	// * 基于服务的承诺
	// */
	// @Column(name="SRV_BAS_COM")
	// @ValidateRule(dataType=9,label="基于服务的承诺",maxLength= 1,required=false)
	// private String srv_bas_com;
	//    
	// /*
	// * Bedarfsdringlichkeit
	// */
	// @Column(name="PRIO_URG")
	// @ValidateRule(dataType=0,label="Bedarfsdringlichkeit",required=false)
	// private String prio_urg;
	//    
	// /*
	// * Bedarfspriorität
	// */
	// @Column(name="PRIO_REQ")
	// @ValidateRule(dataType=0,label="Bedarfspriorität",required=false)
	// private String prio_req;
	//    
	// /*
	// * 报价和销售订单的拒绝原因
	// */
	// @Column(name="SPE_ABGRU")
	// @ValidateRule(dataType=9,label="报价和销售订单的拒绝原因",maxLength=
	// 2,required=false)
	// private String spe_abgru;
	//    
	// /*
	// * TPOP 处理中的 CRM 销售订单编号
	// */
	// @Column(name="SPE_CRM_SO")
	// @ValidateRule(dataType=9,label="TPOP 处理中的 CRM 销售订单编号",maxLength=
	// 10,required=false)
	// private String spe_crm_so;
	//    
	// /*
	// * TPOP 处理中的 CRM 销售订单项目编号
	// */
	// @Column(name="SPE_CRM_SO_ITEM")
	// @ValidateRule(dataType=0,label="TPOP 处理中的 CRM 销售订单项目编号",required=false)
	// private String spe_crm_so_item;
	//    
	// /*
	// * TPOP 处理中的 CRM 参考订单编号
	// */
	// @Column(name="SPE_CRM_REF_SO")
	// @ValidateRule(dataType=9,label="TPOP 处理中的 CRM 参考订单编号",maxLength=
	// 35,required=false)
	// private String spe_crm_ref_so;
	//    
	// /*
	// * TPOP 处理中的 CRM 参考销售订单项目编号
	// */
	// @Column(name="SPE_CRM_REF_ITEM")
	// @ValidateRule(dataType=9,label="TPOP 处理中的 CRM 参考销售订单项目编号",maxLength=
	// 6,required=false)
	// private String spe_crm_ref_item;
	//    
	// /*
	// * 上一更改者的系统类型
	// */
	// @Column(name="SPE_CHNG_SYS")
	// @ValidateRule(dataType=9,label="上一更改者的系统类型",maxLength= 1,required=false)
	// private String spe_chng_sys;
	//    
	// /*
	// * STO 中的源存储地点库存类型
	// */
	// @Column(name="SPE_INSMK_SRC")
	// @ValidateRule(dataType=9,label="STO 中的源存储地点库存类型",maxLength=
	// 1,required=false)
	// private String spe_insmk_src;
	//    
	// /*
	// * CQ 控制类型
	// */
	// @Column(name="SPE_CQ_CTRLTYPE")
	// @ValidateRule(dataType=9,label="CQ 控制类型",maxLength= 1,required=false)
	// private String spe_cq_ctrltype;
	//    
	// /*
	// * 在 SA 释放中无累积量的传输
	// */
	// @Column(name="SPE_CQ_NOCQ")
	// @ValidateRule(dataType=9,label="在 SA 释放中无累积量的传输",maxLength=
	// 1,required=false)
	// private String spe_cq_nocq;
	//    
	// /*
	// *
	// */
	// @Column(name="REASON_CODE")
	// @ValidateRule(dataType=9,label=" ",maxLength= 4,required=false)
	// private String reason_code;
	//    
	// /*
	// * 来自重定向采购订单的累计收货
	// */
	// @Column(name="CQU_SAR")
	// @ValidateRule(dataType=9,label="来自重定向采购订单的累计收货",maxLength=
	// 19,required=false)
	// private String cqu_sar;
	//    
	// /*
	// * 标识：项目被生成
	// */
	// @Column(name="/BEV1/NEGEN_ITEM")
	// @ValidateRule(dataType=9,label="标识：项目被生成",maxLength= 1,required=false)
	// private String /bev1/negen_item;
	//    
	// /*
	// * 标识：相关项目免费
	// */
	// @Column(name="/BEV1/NEDEPFREE")
	// @ValidateRule(dataType=9,label="标识：相关项目免费",maxLength= 1,required=false)
	// private String /bev1/nedepfree;
	//    
	// /*
	// * 物料关系的结构类别
	// */
	// @Column(name="/BEV1/NESTRUCCAT")
	// @ValidateRule(dataType=9,label="物料关系的结构类别",maxLength= 1,required=false)
	// private String /bev1/nestruccat;
	//    
	// /*
	// * Hinweiskode
	// */
	// @Column(name="ADVCODE")
	// @ValidateRule(dataType=9,label="Hinweiskode",maxLength= 2,required=false)
	// private String advcode;
	//    
	// /*
	// * Acceptance Period
	// */
	// @Column(name="EXCPE")
	// @ValidateRule(dataType=0,label="Acceptance Period",required=false)
	// private String excpe;
	//    
	// /*
	// * 采购的参考站点
	// */
	// @Column(name="REFSITE")
	// @ValidateRule(dataType=9,label="采购的参考站点",maxLength= 4,required=false)
	// private String refsite;
	//    
	// /*
	// * 用于保留数量取消的参考项目
	// */
	// @Column(name="REF_ITEM")
	// @ValidateRule(dataType=0,label="用于保留数量取消的参考项目",required=false)
	// private String ref_item;
	//    
	// /*
	// * 原始参数文件
	// */
	// @Column(name="SOURCE_ID")
	// @ValidateRule(dataType=9,label="原始参数文件",maxLength= 3,required=false)
	// private String source_id;
	//    
	// /*
	// * 源系统中的键值
	// */
	// @Column(name="SOURCE_KEY")
	// @ValidateRule(dataType=9,label="源系统中的键值",maxLength= 32,required=false)
	// private String source_key;
	//    
	// /*
	// * 用于从已分组采购订单凭证返回的标识
	// */
	// @Column(name="PUT_BACK")
	// @ValidateRule(dataType=9,label="用于从已分组采购订单凭证返回的标识",maxLength=
	// 1,required=false)
	// private String put_back;
	//    
	// /*
	// * 订单清单项目编号
	// */
	// @Column(name="POL_ID")
	// @ValidateRule(dataType=9,label="订单清单项目编号",maxLength= 10,required=false)
	// private String pol_id;
	//    
	// /*
	// * 寄售的采购清单
	// */
	// @Column(name="CONS_ORDER")
	// @ValidateRule(dataType=9,label="寄售的采购清单",maxLength= 1,required=false)
	// private String cons_order;


	/**
	 * 默认构造器
	 */
	public PurchasingDocItem()
	{
		super();
	}
}
