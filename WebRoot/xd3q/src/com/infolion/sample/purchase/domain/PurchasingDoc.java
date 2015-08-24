/*
 * @(#)PurchasingDoc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 08点56分13秒
 *  描　述：创建
 */
package com.infolion.sample.purchase.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;

/**
 * <pre>
 * SAP采购凭证(PurchasingDoc)实体类
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
@Table(name = "EKKO")
@IdClass(PurchasingDocKey.class)
public class PurchasingDoc extends BaseObject implements Cloneable
{
	// fields

	// /*
	// * 客户端
	// */
	// @Column(name = "MANDT")
	// private String client;
	//
	// /*
	// * 采购凭证号
	// */
	// @Id
	// @GeneratedValue(generator = "paymentableGenerator")
	// @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	// @Column(name = "EBELN")
	// private String ebeln;

	@Id
	@Column(name = "EBELN")
	private String ebeln;
	@Id
	@Column(name = "MANDT")
	private String client;

	/*
	 * SAP采购凭证行项目
	 */
	@OneToMany(mappedBy = "purchasingDoc", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumns( { @JoinColumn(name = "MANDT"), @JoinColumn(name = "EBELN") })
	// @Transient
	private Set<PurchasingDocItem> purchasingDocItems;

	@Column(name = "FRGZU")
	private String frgzu;

	/**
	 * @return the frgzu
	 */
	public String getFrgzu()
	{
		return frgzu;
	}

	/**
	 * @param frgzu
	 *            the frgzu to set
	 */
	public void setFrgzu(String frgzu)
	{
		this.frgzu = frgzu;
	}

	/*
	 * 公司代码
	 */
	@Column(name = "BUKRS")
	@ValidateRule(dataType = 9, label = "公司代码", maxLength = 4, required = false)
	private String bukrs;

	/*
	 * 采购凭证的状态
	 */
	@Column(name = "STATU")
	@ValidateRule(dataType = 9, label = "采购凭证的状态", maxLength = 1, required = false)
	private String statu;

	/*
	 * 记录的创建日期
	 */
	@Column(name = "AEDAT")
	@ValidateRule(dataType = 8, label = "记录的创建日期", required = false)
	private String aedat;

	/*
	 * 创建对象的人员名称
	 */
	@Column(name = "ERNAM")
	@ValidateRule(dataType = 9, label = "创建对象的人员名称", maxLength = 12, required = false)
	private String ernam;

	/*
	 * 付款条件代码
	 */
	@Column(name = "ZTERM")
	@ValidateRule(dataType = 9, label = "付款条件代码", maxLength = 4, required = false)
	private String zterm;

	/*
	 * 货币码
	 */
	@Column(name = "WAERS")
	@ValidateRule(dataType = 9, label = "货币码", maxLength = 5, required = false)
	private String waers;

	@Column(name = "PROCESSSTATE")
	private String processstate;

	// @EmbeddedId
	// @AttributeOverrides({
	// @AttributeOverride(name = "client", column = @Column(name = "MANDT",
	// nullable = false)),
	// @AttributeOverride(name = "ebeln", column = @Column(name = "EBELN",
	// nullable = false)) })
	// private PurchasingDocKey purchasingDocKey ;

	/**
	 * @return the processstate
	 */
	public String getProcessstate()
	{
		return processstate;
	}

	/**
	 * @param processstate
	 *            the processstate to set
	 */
	public void setProcessstate(String processstate)
	{
		this.processstate = processstate;
	}

	/**
	 * @return the ebeln
	 */
	public String getEbeln()
	{
		return ebeln;
	}

	/**
	 * @param ebeln
	 *            the ebeln to set
	 */
	public void setEbeln(String ebeln)
	{
		this.ebeln = ebeln;
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

	// /*
	// * 采购凭证类别
	// */
	// @Column(name = "BSTYP")
	// @ValidateRule(dataType = 9, label = "采购凭证类别", maxLength = 1, required =
	// false)
	// private String bstyp;
	//
	// /*
	// * 采购凭证类型
	// */
	// @Column(name = "BSART")
	// @ValidateRule(dataType = 9, label = "采购凭证类型", maxLength = 4, required =
	// false)
	// private String bsart;
	//
	// /*
	// * 采购凭证类型的控制标识
	// */
	// @Column(name = "BSAKZ")
	// @ValidateRule(dataType = 9, label = "采购凭证类型的控制标识", maxLength = 1,
	// required = false)
	// private String bsakz;
	//
	// /*
	// * 采购凭证中的删除标识
	// */
	// @Column(name = "LOEKZ")
	// @ValidateRule(dataType = 9, label = "采购凭证中的删除标识", maxLength = 1, required
	// = false)
	// private String loekz;

	// /*
	// * 项目编号间隔
	// */
	// @Column(name = "PINCR")
	// @ValidateRule(dataType = 0, label = "项目编号间隔", required = false)
	// private String pincr;
	//
	// /*
	// * 最后项目编号
	// */
	// @Column(name = "LPONR")
	// @ValidateRule(dataType = 0, label = "最后项目编号", required = false)
	// private String lponr;
	//
	// /*
	// * 供应商帐户号
	// */
	// @Column(name = "LIFNR")
	// @ValidateRule(dataType = 9, label = "供应商帐户号", maxLength = 10, required =
	// false)
	// private String lifnr;
	//
	// /*
	// * 语言代码
	// */
	// @Column(name = "SPRAS")
	// @ValidateRule(dataType = 9, label = "语言代码", maxLength = 2, required =
	// false)
	// private String spras;
	// /*
	// * 现金(提示付款)折扣天数
	// */
	// @Column(name = "ZBD1T")
	// @ValidateRule(dataType = 0, label = "现金(提示付款)折扣天数", required = false)
	// private BigDecimal zbd1t;
	//
	// /*
	// * 现金(提示付款)折扣天数
	// */
	// @Column(name = "ZBD2T")
	// @ValidateRule(dataType = 0, label = "现金(提示付款)折扣天数", required = false)
	// private BigDecimal zbd2t;
	//
	// /*
	// * 现金(提示付款)折扣天数
	// */
	// @Column(name = "ZBD3T")
	// @ValidateRule(dataType = 0, label = "现金(提示付款)折扣天数", required = false)
	// private BigDecimal zbd3t;
	//
	// /*
	// * 现金折扣百分率1
	// */
	// @Column(name = "ZBD1P")
	// @ValidateRule(dataType = 0, label = "现金折扣百分率1", required = false)
	// private BigDecimal zbd1p;
	//
	// /*
	// * 现金折扣百分比2
	// */
	// @Column(name = "ZBD2P")
	// @ValidateRule(dataType = 0, label = "现金折扣百分比2", required = false)
	// private BigDecimal zbd2p;
	//
	// /*
	// * 采购组织
	// */
	// @Column(name = "EKORG")
	// @ValidateRule(dataType = 9, label = "采购组织", maxLength = 4, required =
	// false)
	// private String ekorg;
	//
	// /*
	// * 采购组
	// */
	// @Column(name = "EKGRP")
	// @ValidateRule(dataType = 9, label = "采购组", maxLength = 3, required =
	// false)
	// private String ekgrp;
	// /*
	// * 汇率
	// */
	// @Column(name = "WKURS")
	// @ValidateRule(dataType = 0, label = "汇率", required = false)
	// private BigDecimal wkurs;
	//
	// /*
	// * 标识：固定汇率
	// */
	// @Column(name = "KUFIX")
	// @ValidateRule(dataType = 9, label = "标识：固定汇率", maxLength = 1, required =
	// false)
	// private String kufix;
	//
	// /*
	// * 采购凭证日期
	// */
	// @Column(name = "BEDAT")
	// @ValidateRule(dataType = 8, label = "采购凭证日期", required = false)
	// private Date bedat;
	//
	// /*
	// * 有效起始日期
	// */
	// @Column(name = "KDATB")
	// @ValidateRule(dataType = 8, label = "有效起始日期", required = false)
	// private Date kdatb;
	//
	// /*
	// * 有效截至日期
	// */
	// @Column(name = "KDATE")
	// @ValidateRule(dataType = 8, label = "有效截至日期", required = false)
	// private Date kdate;
	//
	// /*
	// * 应用程序结算日期
	// */
	// @Column(name = "BWBDT")
	// @ValidateRule(dataType = 8, label = "应用程序结算日期", required = false)
	// private Date bwbdt;
	//
	// /*
	// * 投标/报价提交的截止日期
	// */
	// @Column(name = "ANGDT")
	// @ValidateRule(dataType = 8, label = "投标/报价提交的截止日期", required = false)
	// private Date angdt;
	//
	// /*
	// * 报价约束期
	// */
	// @Column(name = "BNDDT")
	// @ValidateRule(dataType = 8, label = "报价约束期", required = false)
	// private Date bnddt;
	//
	// /*
	// * 担保日期
	// */
	// @Column(name = "GWLDT")
	// @ValidateRule(dataType = 8, label = "担保日期", required = false)
	// private Date gwldt;
	//
	// /*
	// * 招标号
	// */
	// @Column(name = "AUSNR")
	// @ValidateRule(dataType = 9, label = "招标号", maxLength = 10, required =
	// false)
	// private String ausnr;
	//
	// /*
	// * 报价单编号
	// */
	// @Column(name = "ANGNR")
	// @ValidateRule(dataType = 9, label = "报价单编号", maxLength = 10, required =
	// false)
	// private String angnr;
	//
	// /*
	// * 报价提交日期
	// */
	// @Column(name = "IHRAN")
	// @ValidateRule(dataType = 8, label = "报价提交日期", required = false)
	// private Date ihran;
	//
	// /*
	// * 您的参考
	// */
	// @Column(name = "IHREZ")
	// @ValidateRule(dataType = 9, label = "您的参考", maxLength = 12, required =
	// false)
	// private String ihrez;
	//
	// /*
	// * 供应商办公室的责任销售员
	// */
	// @Column(name = "VERKF")
	// @ValidateRule(dataType = 9, label = "供应商办公室的责任销售员", maxLength = 30,
	// required = false)
	// private String verkf;
	//
	// /*
	// * 供应商电话号码
	// */
	// @Column(name = "TELF1")
	// @ValidateRule(dataType = 9, label = "供应商电话号码", maxLength = 16, required =
	// false)
	// private String telf1;
	//
	// /*
	// * 商品供应商
	// */
	// @Column(name = "LLIEF")
	// @ValidateRule(dataType = 9, label = "商品供应商", maxLength = 10, required =
	// false)
	// private String llief;
	//
	/*
	 * 客户编号1
	 */
	@Column(name = "KUNNR")
	@ValidateRule(dataType = 9, label = "客户编号1", maxLength = 10, required = false)
	private String kunnr;

	// /*
	// * 重要的采购协议号
	// */
	// @Column(name = "KONNR")
	// @ValidateRule(dataType = 9, label = "重要的采购协议号", maxLength = 10, required
	// = false)
	// private String konnr;
	//
	// /*
	// * 没有使用的字段
	// */
	// @Column(name = "ABGRU")
	// @ValidateRule(dataType = 9, label = "没有使用的字段", maxLength = 2, required =
	// false)
	// private String abgru;
	//
	// /*
	// * 对每个采购订单规定全部交货
	// */
	// @Column(name = "AUTLF")
	// @ValidateRule(dataType = 9, label = "对每个采购订单规定全部交货", maxLength = 1,
	// required = false)
	// private String autlf;
	//
	// /*
	// * 标识：收货信息
	// */
	// @Column(name = "WEAKT")
	// @ValidateRule(dataType = 9, label = "标识：收货信息", maxLength = 1, required =
	// false)
	// private String weakt;
	//
	// /*
	// * 转储单的供应(发出)工厂
	// */
	// @Column(name = "RESWK")
	// @ValidateRule(dataType = 9, label = "转储单的供应(发出)工厂", maxLength = 4,
	// required = false)
	// private String reswk;
	//
	// /*
	// * 没有使用的字段
	// */
	// @Column(name = "LBLIF")
	// @ValidateRule(dataType = 9, label = "没有使用的字段", maxLength = 10, required =
	// false)
	// private String lblif;
	//
	// /*
	// * 国际贸易条款 (部分1)
	// */
	// @Column(name = "INCO1")
	// @ValidateRule(dataType = 9, label = "国际贸易条款 (部分1)", maxLength = 3,
	// required = false)
	// private String inco1;
	//
	// /*
	// * 国际贸易条件(部分2)
	// */
	// @Column(name = "INCO2")
	// @ValidateRule(dataType = 9, label = "国际贸易条件(部分2)", maxLength = 28,
	// required = false)
	// private String inco2;
	//
	// /*
	// * 累计计划值
	// */
	// @Column(name = "KTWRT")
	// @ValidateRule(dataType = 9, label = "累计计划值", maxLength = 20, required =
	// false)
	// private String ktwrt;
	//
	// /*
	// * 汇总号
	// */
	// @Column(name = "SUBMI")
	// @ValidateRule(dataType = 9, label = "汇总号", maxLength = 10, required =
	// false)
	// private String submi;
	//
	// /*
	// * 单据条件数
	// */
	// @Column(name = "KNUMV")
	// @ValidateRule(dataType = 9, label = "单据条件数", maxLength = 10, required =
	// false)
	// private String knumv;
	//
	// /*
	// * 过程（定价、输出控制、科目、细节、成本...）
	// */
	// @Column(name = "KALSM")
	// @ValidateRule(dataType = 9, label = "过程（定价、输出控制、科目、细节、成本...）", maxLength
	// = 6, required = false)
	// private String kalsm;
	//
	// /*
	// * 更新组用于统计更新
	// */
	// @Column(name = "STAFO")
	// @ValidateRule(dataType = 9, label = "更新组用于统计更新", maxLength = 6, required
	// = false)
	// private String stafo;
	//
	// /*
	// * 不同出票方
	// */
	// @Column(name = "LIFRE")
	// @ValidateRule(dataType = 9, label = "不同出票方", maxLength = 10, required =
	// false)
	// private String lifre;
	//
	// /*
	// * MM 和 SD 凭证中的外贸数据编号
	// */
	// @Column(name = "EXNUM")
	// @ValidateRule(dataType = 9, label = "MM 和 SD 凭证中的外贸数据编号", maxLength = 10,
	// required = false)
	// private String exnum;
	//
	// /*
	// * 我们的参考
	// */
	// @Column(name = "UNSEZ")
	// @ValidateRule(dataType = 9, label = "我们的参考", maxLength = 12, required =
	// false)
	// private String unsez;
	//
	// /*
	// * 逻辑系统
	// */
	// @Column(name = "LOGSY")
	// @ValidateRule(dataType = 9, label = "逻辑系统", maxLength = 10, required =
	// false)
	// private String logsy;
	//
	// /*
	// * 子项目的项目间隔
	// */
	// @Column(name = "UPINC")
	// @ValidateRule(dataType = 0, label = "子项目的项目间隔", required = false)
	// private String upinc;
	//
	// /*
	// * 含时间依赖条件的凭证
	// */
	// @Column(name = "STAKO")
	// @ValidateRule(dataType = 9, label = "含时间依赖条件的凭证", maxLength = 1, required
	// = false)
	// private String stako;
	//
	// /*
	// * 审批组
	// */
	// @Column(name = "FRGGR")
	// @ValidateRule(dataType = 9, label = "审批组", maxLength = 2, required =
	// false)
	// private String frggr;
	//
	// /*
	// * 审批策略
	// */
	// @Column(name = "FRGSX")
	// @ValidateRule(dataType = 9, label = "审批策略", maxLength = 2, required =
	// false)
	// private String frgsx;
	//
	// /*
	// * 批准标识：采购凭证
	// */
	// @Column(name = "FRGKE")
	// @ValidateRule(dataType = 9, label = "批准标识：采购凭证", maxLength = 1, required
	// = false)
	// private String frgke;
	//
	// /*
	// * 版本状态
	// */
	// @Column(name = "FRGZU")
	// @ValidateRule(dataType = 9, label = "版本状态", maxLength = 8, required =
	// false)
	// private String frgzu;
	//
	// /*
	// * 批准尚未完全生效
	// */
	// @Column(name = "FRGRL")
	// @ValidateRule(dataType = 9, label = "批准尚未完全生效", maxLength = 1, required =
	// false)
	// private String frgrl;
	//
	// /*
	// * 纳税返回国家
	// */
	// @Column(name = "LANDS")
	// @ValidateRule(dataType = 9, label = "纳税返回国家", maxLength = 3, required =
	// false)
	// private String lands;
	//
	// /*
	// * 计划协议批准凭证标识
	// */
	// @Column(name = "LPHIS")
	// @ValidateRule(dataType = 9, label = "计划协议批准凭证标识", maxLength = 1, required
	// = false)
	// private String lphis;
	//
	// /*
	// * 地址号码
	// */
	// @Column(name = "ADRNR")
	// @ValidateRule(dataType = 9, label = "地址号码", maxLength = 10, required =
	// false)
	// private String adrnr;
	//
	// /*
	// * 国家销售税ID编号
	// */
	// @Column(name = "STCEG_L")
	// @ValidateRule(dataType = 9, label = "国家销售税ID编号", maxLength = 3, required
	// = false)
	// private String stceg_l;
	//
	// /*
	// * 增值税登记号
	// */
	// @Column(name = "STCEG")
	// @ValidateRule(dataType = 9, label = "增值税登记号", maxLength = 20, required =
	// false)
	// private String stceg;
	//
	// /*
	// * 取消原因
	// */
	// @Column(name = "ABSGR")
	// @ValidateRule(dataType = 0, label = "取消原因", required = false)
	// private String absgr;
	//
	// /*
	// * 附加项目的凭证号
	// */
	// @Column(name = "ADDNR")
	// @ValidateRule(dataType = 9, label = "附加项目的凭证号", maxLength = 10, required
	// = false)
	// private String addnr;
	//
	// /*
	// * 混合供应修正
	// */
	// @Column(name = "KORNR")
	// @ValidateRule(dataType = 9, label = "混合供应修正", maxLength = 1, required =
	// false)
	// private String kornr;
	//
	// /*
	// * 采购订单未完成
	// */
	// @Column(name = "MEMORY")
	// @ValidateRule(dataType = 9, label = "采购订单未完成", maxLength = 1, required =
	// false)
	// private String memory;
	//
	// /*
	// * 采购凭证处理状态
	// */
	// @Column(name = "PROCSTAT")
	// @ValidateRule(dataType = 9, label = "采购凭证处理状态", maxLength = 2, required =
	// false)
	// private String procstat;
	//
	// /*
	// * 下达时的总计值
	// */
	// @Column(name = "RLWRT")
	// @ValidateRule(dataType = 9, label = "下达时的总计值", maxLength = 20, required =
	// false)
	// private String rlwrt;
	//
	// /*
	// * 采购的版本号
	// */
	// @Column(name = "REVNO")
	// @ValidateRule(dataType = 9, label = "采购的版本号", maxLength = 8, required =
	// false)
	// private String revno;
	//
	// /*
	// * 创建此采购订单的 SCM 过程
	// */
	// @Column(name = "SCMPROC")
	// @ValidateRule(dataType = 9, label = "创建此采购订单的 SCM 过程", maxLength = 1,
	// required = false)
	// private String scmproc;
	//
	// /*
	// *
	// */
	// @Column(name = "REASON_CODE")
	// @ValidateRule(dataType = 9, label = " ", maxLength = 4, required = false)
	// private String reason_code;
	//
	// /*
	// * Interner Schlüssel für ein Strukturelement
	// */
	// @Column(name = "FORCE_ID")
	// @ValidateRule(dataType = 9, label =
	// "Interner Schlüssel für ein Strukturelement", maxLength = 32, required =
	// false)
	// private String force_id;
	//
	// /*
	// * Interner (Versions-) Zähler
	// */
	// @Column(name = "FORCE_CNT")
	// @ValidateRule(dataType = 0, label = "Interner (Versions-) Zähler",
	// required = false)
	// private String force_cnt;
	//
	// /*
	// * ID der Verlegung
	// */
	// @Column(name = "RELOC_ID")
	// @ValidateRule(dataType = 9, label = "ID der Verlegung", maxLength = 10,
	// required = false)
	// private String reloc_id;
	//
	// /*
	// * Verlegeschritt-ID
	// */
	// @Column(name = "RELOC_SEQ_ID")
	// @ValidateRule(dataType = 9, label = "Verlegeschritt-ID", maxLength = 4,
	// required = false)
	// private String reloc_seq_id;

	/*
	 * 最后审批人
	 */
	@Column(name = "LASTAPPNAME")
	@ValidateRule(dataType = 9, label = "最后审批人", maxLength = 64, required = false)
	private String lastappname;

	/*
	 * 最后审批时间
	 */
	@Column(name = "LASTAPPTIME")
	@ValidateRule(dataType = 9, label = "最后审批时间", required = false)
	private String lastapptime;

	// /*
	// * 流程状态
	// */
	// @Column(name = "PROCESSSTATE")
	// @ValidateRule(dataType = 9, label = "流程状态", maxLength = 30, required =
	// false)
	// private String processstate;
	//
	// /*
	// * 季节采购订单处理的凭证
	// */
	// @Column(name = "POHF_TYPE")
	// @ValidateRule(dataType = 9, label = "季节采购订单处理的凭证", maxLength = 1,
	// required = false)
	// private String pohf_type;
	//
	// /*
	// * 交货日期表头：全部项目具有相同的交货日期
	// */
	// @Column(name = "EQ_EINDT")
	// @ValidateRule(dataType = 8, label = "交货日期表头：全部项目具有相同的交货日期", required =
	// false)
	// private Date eq_eindt;
	//
	// /*
	// * 工厂表头：全部项目具有相同的接收工厂
	// */
	// @Column(name = "EQ_WERKS")
	// @ValidateRule(dataType = 9, label = "工厂表头：全部项目具有相同的接收工厂", maxLength = 4,
	// required = false)
	// private String eq_werks;
	//
	// /*
	// * 公司交易标识
	// */
	// @Column(name = "FIXPO")
	// @ValidateRule(dataType = 9, label = "公司交易标识", maxLength = 1, required =
	// false)
	// private String fixpo;
	//
	// /*
	// * 考虑采购组
	// */
	// @Column(name = "EKGRP_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑采购组", maxLength = 1, required =
	// false)
	// private String ekgrp_allow;
	//
	// /*
	// * 考虑工厂
	// */
	// @Column(name = "WERKS_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑工厂", maxLength = 1, required =
	// false)
	// private String werks_allow;
	//
	// /*
	// * 考虑合同
	// */
	// @Column(name = "CONTRACT_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑合同", maxLength = 1, required =
	// false)
	// private String contract_allow;
	//
	// /*
	// * 考虑项目类别
	// */
	// @Column(name = "PSTYP_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑项目类别", maxLength = 1, required =
	// false)
	// private String pstyp_allow;
	//
	// /*
	// * 考虑固定日期采购标识
	// */
	// @Column(name = "FIXPO_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑固定日期采购标识", maxLength = 1, required
	// = false)
	// private String fixpo_allow;
	//
	// /*
	// * 考虑预算
	// */
	// @Column(name = "KEY_ID_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑预算", maxLength = 1, required =
	// false)
	// private String key_id_allow;
	//
	// /*
	// * 考虑分配表相关性
	// */
	// @Column(name = "AUREL_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑分配表相关性", maxLength = 1, required =
	// false)
	// private String aurel_allow;
	//
	// /*
	// * 考虑交付期
	// */
	// @Column(name = "DELPER_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑交付期", maxLength = 1, required =
	// false)
	// private String delper_allow;
	//
	// /*
	// * 考虑交货日期
	// */
	// @Column(name = "EINDT_ALLOW")
	// @ValidateRule(dataType = 9, label = "考虑交货日期", maxLength = 1, required =
	// false)
	// private String eindt_allow;
	//
	// /*
	// * OTB 检查级别
	// */
	// @Column(name = "OTB_LEVEL")
	// @ValidateRule(dataType = 9, label = "OTB 检查级别", maxLength = 1, required =
	// false)
	// private String otb_level;
	//
	// /*
	// * OTB 条件类型
	// */
	// @Column(name = "OTB_COND_TYPE")
	// @ValidateRule(dataType = 9, label = "OTB 条件类型", maxLength = 4, required =
	// false)
	// private String otb_cond_type;
	//
	// /*
	// * 预算的唯一编号
	// */
	// @Column(name = "KEY_ID")
	// @ValidateRule(dataType = 0, label = "预算的唯一编号", required = false)
	// private String key_id;
	//
	// /*
	// * 必需的预算
	// */
	// @Column(name = "OTB_VALUE")
	// @ValidateRule(dataType = 9, label = "必需的预算", maxLength = 23, required =
	// false)
	// private String otb_value;
	//
	// /*
	// * OTB 货币
	// */
	// @Column(name = "OTB_CURR")
	// @ValidateRule(dataType = 9, label = "OTB 货币", maxLength = 5, required =
	// false)
	// private String otb_curr;
	//
	// /*
	// * 保留 OTB-相关采购凭证的预算
	// */
	// @Column(name = "OTB_RES_VALUE")
	// @ValidateRule(dataType = 9, label = "保留 OTB-相关采购凭证的预算", maxLength = 22,
	// required = false)
	// private String otb_res_value;
	//
	// /*
	// * 特殊发布预算
	// */
	// @Column(name = "OTB_SPEC_VALUE")
	// @ValidateRule(dataType = 9, label = "特殊发布预算", maxLength = 22, required =
	// false)
	// private String otb_spec_value;
	//
	// /*
	// * OTB 特别审批的参数文件原因
	// */
	// @Column(name = "SPR_RSN_PROFILE")
	// @ValidateRule(dataType = 9, label = "OTB 特别审批的参数文件原因", maxLength = 4,
	// required = false)
	// private String spr_rsn_profile;
	//
	// /*
	// * 预算类型
	// */
	// @Column(name = "BUDG_TYPE")
	// @ValidateRule(dataType = 9, label = "预算类型", maxLength = 2, required =
	// false)
	// private String budg_type;
	//
	// /*
	// * OTB 检查状态
	// */
	// @Column(name = "OTB_STATUS")
	// @ValidateRule(dataType = 9, label = "OTB 检查状态", maxLength = 1, required =
	// false)
	// private String otb_status;
	//
	// /*
	// * OTB 检查状态的原因标识
	// */
	// @Column(name = "OTB_REASON")
	// @ValidateRule(dataType = 9, label = "OTB 检查状态的原因标识", maxLength = 3,
	// required = false)
	// private String otb_reason;
	//
	// /*
	// * OTB 检查的类型
	// */
	// @Column(name = "CHECK_TYPE")
	// @ValidateRule(dataType = 9, label = "OTB 检查的类型", maxLength = 1, required
	// = false)
	// private String check_type;
	//
	// /*
	// * OTB 相关的合同
	// */
	// @Column(name = "CON_OTB_REQ")
	// @ValidateRule(dataType = 9, label = "OTB 相关的合同", maxLength = 1, required
	// = false)
	// private String con_otb_req;
	//
	// /*
	// * 合同的 OTB 标识级别
	// */
	// @Column(name = "CON_PREBOOK_LEV")
	// @ValidateRule(dataType = 9, label = "合同的 OTB 标识级别", maxLength = 1,
	// required = false)
	// private String con_prebook_lev;
	//
	// /*
	// * 使用目标值或项目数据分配
	// */
	// @Column(name = "CON_DISTR_LEV")
	// @ValidateRule(dataType = 9, label = "使用目标值或项目数据分配", maxLength = 1,
	// required = false)
	// private String con_distr_lev;

	/**
	 * 功能描述: 获得SAP采购凭证行项目
	 * 
	 * @return SAP采购凭证行项目 : Set<PurchasingDocItem>
	 */
	public Set<PurchasingDocItem> getPurchasingDocItems()
	{
		return this.purchasingDocItems;
	}

	/**
	 * 功能描述: 设置SAP采购凭证行项目
	 * 
	 * @param purchasingDocItems
	 *            : Set<PurchasingDocItem>
	 */
	public void setPurchasingDocItems(Set<PurchasingDocItem> purchasingDocItems)
	{
		this.purchasingDocItems = purchasingDocItems;
	}

	// /**
	// * 功能描述: 获得客户端
	// *
	// * @return 客户端 : String
	// */
	// public String getClient()
	// {
	// return this.client;
	// }
	//
	// /**
	// * 功能描述: 设置客户端
	// *
	// * @param client
	// * : String
	// */
	// public void setClient(String client)
	// {
	// this.client = client;
	// }
	//
	// /**
	// * 功能描述: 获得采购凭证号
	// *
	// * @return 采购凭证号 : String
	// */
	// public String getEbeln()
	// {
	// return this.ebeln;
	// }
	//
	// /**
	// * 功能描述: 设置采购凭证号
	// *
	// * @param ebeln
	// * : String
	// */
	// public void setEbeln(String ebeln)
	// {
	// this.ebeln = ebeln;
	// }

	/**
	 * 功能描述: 获得公司代码
	 * 
	 * @return 公司代码 : String
	 */
	public String getBukrs()
	{
		return this.bukrs;
	}

	/**
	 * 功能描述: 设置公司代码
	 * 
	 * @param bukrs
	 *            : String
	 */
	public void setBukrs(String bukrs)
	{
		this.bukrs = bukrs;
	}

	/**
	 * 功能描述: 获得采购凭证的状态
	 * 
	 * @return 采购凭证的状态 : String
	 */
	public String getStatu()
	{
		return this.statu;
	}

	/**
	 * 功能描述: 设置采购凭证的状态
	 * 
	 * @param statu
	 *            : String
	 */
	public void setStatu(String statu)
	{
		this.statu = statu;
	}

	/**
	 * 功能描述: 获得记录的创建日期
	 * 
	 * @return 记录的创建日期 : Date
	 */
	public String getAedat()
	{
		return DateUtils.toDisplayStr(this.aedat, DateUtils.HYPHEN_DISPLAY_DATE);
		// return this.aedat;
	}

	/**
	 * 功能描述: 设置记录的创建日期
	 * 
	 * @param aedat
	 *            : Date
	 */
	public void setAedat(String aedat)
	{
		aedat = DateUtils.toStoreStr(aedat);
		this.aedat = aedat;
	}

	/**
	 * 功能描述: 获得创建对象的人员名称
	 * 
	 * @return 创建对象的人员名称 : String
	 */
	public String getErnam()
	{
		return this.ernam;
	}

	/**
	 * 功能描述: 设置创建对象的人员名称
	 * 
	 * @param ernam
	 *            : String
	 */
	public void setErnam(String ernam)
	{
		this.ernam = ernam;
	}

	/**
	 * @return the zterm
	 */
	public String getZterm()
	{
		return zterm;
	}

	/**
	 * @param zterm
	 *            the zterm to set
	 */
	public void setZterm(String zterm)
	{
		this.zterm = zterm;
	}

	/**
	 * @return the kunnr
	 */
	public String getKunnr()
	{
		return kunnr;
	}

	/**
	 * @param kunnr
	 *            the kunnr to set
	 */
	public void setKunnr(String kunnr)
	{
		this.kunnr = kunnr;
	}

	/**
	 * @return the lastappname
	 */
	public String getLastappname()
	{
		return lastappname;
	}

	/**
	 * @param lastappname
	 *            the lastappname to set
	 */
	public void setLastappname(String lastappname)
	{
		this.lastappname = lastappname;
	}

	/**
	 * @return the lastapptime
	 */
	public String getLastapptime()
	{
		return DateUtils.toDisplayStr(this.lastapptime, DateUtils.HYPHEN_DISPLAY_DATE);
	}

	/**
	 * @param lastapptime
	 *            the lastapptime to set
	 */
	public void setLastapptime(String lastapptime)
	{
		lastapptime = DateUtils.toStoreStr(lastapptime);
		this.lastapptime = lastapptime;
	}

	/**
	 * @return the waers
	 */
	public String getWaers()
	{
		return waers;
	}

	/**
	 * @param waers
	 *            the waers to set
	 */
	public void setWaers(String waers)
	{
		this.waers = waers;
	}

	/**
	 * 默认构造器
	 */
	public PurchasingDoc()
	{
		super();
	}

	public Object copy()
	{
		// TODO Auto-generated method stub
		try
		{
			return this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
