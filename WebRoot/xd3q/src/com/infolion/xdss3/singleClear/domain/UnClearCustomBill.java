/*
 * @(#)UnClearCustomBill.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月17日 11点12分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 未清应收(借方)(UnClearCustomBill)实体类
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
@Table(name = "YUNCLEARCUSTBILL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UnClearCustomBill extends BaseObject
{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;

	/*
	 * 未清应收（借方）ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
	@Column(name = "UNCLEARCUSBILLID")
	private String unclearcusbillid;

	/*
	 * 未清客户数据抬头ID
	 */
	@Column(name = "CUSTOMERTITLEID")
	@ValidateRule(dataType = 9, label = "未清客户数据抬头ID", maxLength = 36, required = false)
	private String customertitleid;

	/*
	 * 合同编号
	 */
	@Column(name = "CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "合同编号", maxLength = 50, required = false)
	private String contract_no;

	/*
	 * 立项号
	 */
	@Column(name = "PROJECT_NO")
	@ValidateRule(dataType = 9, label = "立项号", maxLength = 100, required = false)
	private String project_no;

	/*
	 * 财务凭证号
	 */
	@Column(name = "VOUCHERNO")
	@ValidateRule(dataType = 9, label = "财务凭证号", maxLength = 30, required = false)
	private String voucherno;

	/*
	 * 记账日期
	 */
	@Column(name = "ACCOUNTDATE")
	@ValidateRule(dataType = 9, label = "记账日期", maxLength = 20, required = false)
	private String accountdate;

	/*
	 * SAP开票号
	 */
	@Column(name = "BILLNO")
	@ValidateRule(dataType = 9, label = "SAP开票号", maxLength = 50, required = false)
	private String billno;

	/*
	 * 清帐用途
	 */
	@Column(name = "TEXT")
	@ValidateRule(dataType = 9, label = "清帐用途", maxLength = 200, required = false)
	private String text;

	/*
	 * 原合同号
	 */
	@Column(name = "OLD_CONTRACT_NO")
	@ValidateRule(dataType = 9, label = "原合同号", maxLength = 36, required = false)
	private String old_contract_no;

	/*
	 * SAP订单号
	 */
	@Column(name = "SAP_ORDER_NO")
	@ValidateRule(dataType = 9, label = "SAP订单号", maxLength = 255, required = false)
	private String sap_order_no;

	/*
	 * 货币
	 */
	@Column(name = "CURRENCY")
	@ValidateRule(dataType = 9, label = "货币", maxLength = 5, required = false)
	private String currency;

	/*
	 * 押汇汇率
	 */
	@Column(name = "EXCHANGERATE")
	@ValidateRule(dataType = 0, label = "押汇汇率", required = false)
	private BigDecimal exchangerate;

	/*
	 * 应收款
	 */
	@Column(name = "RECEIVABLEAMOUNT")
	@ValidateRule(dataType = 0, label = "应收款", required = false)
	private BigDecimal receivableamount;

	/*
	 * 应收款日
	 */
	@Column(name = "RECEIVABLEDATE")
	@ValidateRule(dataType = 9, label = "应收款日", maxLength = 20, required = false)
	private String receivabledate;

	/*
	 * 在批金额
	 */
	@Column(name = "ONROADAMOUNT")
	@ValidateRule(dataType = 0, label = "在批金额", required = false)
	private BigDecimal onroadamount;

	/*
	 * 清帐金额
	 */
	@Column(name = "CLEARAMOUNT")
	@ValidateRule(dataType = 0, label = "清帐金额", required = false)
	private BigDecimal clearamount;

	/*
	 * 调整差额
	 */
	@Column(name = "ADJUSTAMOUNT")
	@ValidateRule(dataType = 0, label = "调整差额", required = false)
	private BigDecimal adjustamount;

	/*
	 * 已收款
	 */
	@Column(name = "RECEIVEDAMOUNT")
	@ValidateRule(dataType = 0, label = "已收款", required = false)
	private BigDecimal receivedamount;

	/*
	 * 未收款
	 */
	@Column(name = "UNRECEIVABLEAMOU")
	@ValidateRule(dataType = 0, label = "未收款", required = false)
	private BigDecimal unreceivableamou;

	/*
	 * 付款单号
	 */
	@Column(name = "PAYMENTNO")
	@ValidateRule(dataType = 9, label = "付款单号", maxLength = 30, required = false)
	private String paymentno;

	/*
	 * 付款ID
	 */
	@Column(name = "PAYMENTID")
	@ValidateRule(dataType = 9, label = "付款ID", maxLength = 36, required = false)
	private String paymentid;

	/*
	 * 本位币金额
	 */
	@Column(name = "BWBJE")
	@ValidateRule(dataType = 0, label = "本位币金额", required = false)
	private BigDecimal bwbje;
	
	/*
	 * 未清本位币金额
	 */
	@Column(name = "UNBWBJE")
	@ValidateRule(dataType = 0, label = "未清本位币金额", required = false)
	private BigDecimal unbwbje;
	
	/*
	 * 付款金额分配ID
	 */
	@Column(name = "PAYMENTITEMID")
	@ValidateRule(dataType = 9, label = "付款金额分配ID", maxLength = 36, required = false)
	private String paymentitemid;

	/*
	 * 客户单清帐
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CUSTOMSCLEARID")
	@NotFound(action = NotFoundAction.IGNORE)
	private CustomSingleClear customSingleClear;

	/**
	 * @return the customertitleid
	 */
	public String getCustomertitleid()
	{
		return customertitleid;
	}

	/**
	 * @param customertitleid
	 *            the customertitleid to set
	 */
	public void setCustomertitleid(String customertitleid)
	{
		this.customertitleid = customertitleid;
	}

	/**
	 * 功能描述: 获得客户端
	 * 
	 * @return 客户端 : String
	 */
	public String getClient()
	{
		return this.client;
	}

	/**
	 * 功能描述: 设置客户端
	 * 
	 * @param client
	 *            : String
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * 功能描述: 获得未清应收（借方）ID
	 * 
	 * @return 未清应收（借方）ID : String
	 */
	public String getUnclearcusbillid()
	{
		return this.unclearcusbillid;
	}

	/**
	 * 功能描述: 设置未清应收（借方）ID
	 * 
	 * @param unclearcusbillid
	 *            : String
	 */
	public void setUnclearcusbillid(String unclearcusbillid)
	{
		this.unclearcusbillid = unclearcusbillid;
	}

	/**
	 * 功能描述: 获得合同编号
	 * 
	 * @return 合同编号 : String
	 */
	public String getContract_no()
	{
		return this.contract_no;
	}

	/**
	 * 功能描述: 设置合同编号
	 * 
	 * @param contract_no
	 *            : String
	 */
	public void setContract_no(String contract_no)
	{
		this.contract_no = contract_no;
	}

	/**
	 * 功能描述: 获得立项号
	 * 
	 * @return 立项号 : String
	 */
	public String getProject_no()
	{
		return this.project_no;
	}

	/**
	 * 功能描述: 设置立项号
	 * 
	 * @param project_no
	 *            : String
	 */
	public void setProject_no(String project_no)
	{
		this.project_no = project_no;
	}

	/**
	 * 功能描述: 获得财务凭证号
	 * 
	 * @return 财务凭证号 : String
	 */
	public String getVoucherno()
	{
		return this.voucherno;
	}

	/**
	 * 功能描述: 设置财务凭证号
	 * 
	 * @param voucherno
	 *            : String
	 */
	public void setVoucherno(String voucherno)
	{
		this.voucherno = voucherno;
	}

	/**
	 * 功能描述: 获得记账日期
	 * 
	 * @return 记账日期 : String
	 */
	public String getAccountdate()
	{
		return this.accountdate;
	}

	/**
	 * 功能描述: 设置记账日期
	 * 
	 * @param accountdate
	 *            : String
	 */
	public void setAccountdate(String accountdate)
	{
		this.accountdate = accountdate;
	}

	/**
	 * 功能描述: 获得发票号
	 * 
	 * @return 发票号 : String
	 */
	public String getBillno()
	{
		return this.billno;
	}

	/**
	 * 功能描述: 设置发票号
	 * 
	 * @param billno
	 *            : String
	 */
	public void setBillno(String billno)
	{
		this.billno = billno;
	}

	/**
	 * 功能描述: 获得清帐用途
	 * 
	 * @return 清帐用途 : String
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * 功能描述: 设置清帐用途
	 * 
	 * @param text
	 *            : String
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * 功能描述: 获得原合同号
	 * 
	 * @return 原合同号 : String
	 */
	public String getOld_contract_no()
	{
		return this.old_contract_no;
	}

	/**
	 * 功能描述: 设置原合同号
	 * 
	 * @param old_contract_no
	 *            : String
	 */
	public void setOld_contract_no(String old_contract_no)
	{
		this.old_contract_no = old_contract_no;
	}

	/**
	 * 功能描述: 获得退货SAP订单号
	 * 
	 * @return 退货SAP订单号 : String
	 */
	public String getSap_order_no()
	{
		return this.sap_order_no;
	}

	/**
	 * 功能描述: 设置退货SAP订单号
	 * 
	 * @param sap_order_no
	 *            : String
	 */
	public void setSap_order_no(String sap_order_no)
	{
		this.sap_order_no = sap_order_no;
	}

	/**
	 * 功能描述: 获得货币
	 * 
	 * @return 货币 : String
	 */
	public String getCurrency()
	{
		return this.currency;
	}

	/**
	 * 功能描述: 设置货币
	 * 
	 * @param currency
	 *            : String
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * 功能描述: 获得押汇汇率
	 * 
	 * @return 押汇汇率 : BigDecimal
	 */
	public BigDecimal getExchangerate()
	{
		return this.exchangerate;
	}

	/**
	 * 功能描述: 设置押汇汇率
	 * 
	 * @param exchangerate
	 *            : BigDecimal
	 */
	public void setExchangerate(BigDecimal exchangerate)
	{
		this.exchangerate = exchangerate;
	}

	/**
	 * 功能描述: 获得应收款
	 * 
	 * @return 应收款 : BigDecimal
	 */
	public BigDecimal getReceivableamount()
	{
		return this.receivableamount;
	}

	/**
	 * 功能描述: 设置应收款
	 * 
	 * @param receivableamount
	 *            : BigDecimal
	 */
	public void setReceivableamount(BigDecimal receivableamount)
	{
		this.receivableamount = receivableamount;
	}

	/**
	 * 功能描述: 获得应收款日
	 * 
	 * @return 应收款日 : String
	 */
	public String getReceivabledate()
	{
		return this.receivabledate;
	}

	/**
	 * 功能描述: 设置应收款日
	 * 
	 * @param receivabledate
	 *            : String
	 */
	public void setReceivabledate(String receivabledate)
	{
		this.receivabledate = receivabledate;
	}

	/**
	 * 功能描述: 获得在批金额
	 * 
	 * @return 在批金额 : BigDecimal
	 */
	public BigDecimal getOnroadamount()
	{
		return this.onroadamount;
	}

	/**
	 * 功能描述: 设置在批金额
	 * 
	 * @param onroadamount
	 *            : BigDecimal
	 */
	public void setOnroadamount(BigDecimal onroadamount)
	{
		this.onroadamount = onroadamount;
	}

	/**
	 * 功能描述: 获得清帐金额
	 * 
	 * @return 清帐金额 : BigDecimal
	 */
	public BigDecimal getClearamount()
	{
		return this.clearamount;
	}

	/**
	 * 功能描述: 设置清帐金额
	 * 
	 * @param clearamount
	 *            : BigDecimal
	 */
	public void setClearamount(BigDecimal clearamount)
	{
		this.clearamount = clearamount;
	}

	/**
	 * 功能描述: 获得调整差额
	 * 
	 * @return 调整差额 : BigDecimal
	 */
	public BigDecimal getAdjustamount()
	{
		return this.adjustamount;
	}

	/**
	 * 功能描述: 设置调整差额
	 * 
	 * @param adjustamount
	 *            : BigDecimal
	 */
	public void setAdjustamount(BigDecimal adjustamount)
	{
		this.adjustamount = adjustamount;
	}
	
	
	
	/**
	 * 功能描述: 获得已收款
	 * 
	 * @return 已收款 : BigDecimal
	 */
	public BigDecimal getReceivedamount()
	{
		return this.receivedamount;
	}

	/**
	 * 功能描述: 设置已收款
	 * 
	 * @param receivedamount
	 *            : BigDecimal
	 */
	public void setReceivedamount(BigDecimal receivedamount)
	{
		this.receivedamount = receivedamount;
	}

	/**
	 * 功能描述: 获得未收款
	 * 
	 * @return 未收款 : BigDecimal
	 */
	public BigDecimal getUnreceivableamou()
	{
		return this.unreceivableamou;
	}

	/**
	 * 功能描述: 设置未收款
	 * 
	 * @param unreceivableamou
	 *            : BigDecimal
	 */
	public void setUnreceivableamou(BigDecimal unreceivableamou)
	{
		this.unreceivableamou = unreceivableamou;
	}

	/**
	 * 功能描述: 获得付款单号
	 * 
	 * @return 付款单号 : String
	 */
	public String getPaymentno()
	{
		return this.paymentno;
	}

	/**
	 * 功能描述: 设置付款单号
	 * 
	 * @param paymentno
	 *            : String
	 */
	public void setPaymentno(String paymentno)
	{
		this.paymentno = paymentno;
	}

	/**
	 * 功能描述: 获得付款ID
	 * 
	 * @return 付款ID : String
	 */
	public String getPaymentid()
	{
		return this.paymentid;
	}

	/**
	 * 功能描述: 设置付款ID
	 * 
	 * @param paymentid
	 *            : String
	 */
	public void setPaymentid(String paymentid)
	{
		this.paymentid = paymentid;
	}

	/**
	 * 功能描述: 获得付款金额分配ID
	 * 
	 * @return 付款金额分配ID : String
	 */
	public String getPaymentitemid()
	{
		return this.paymentitemid;
	}

	/**
	 * 功能描述: 设置付款金额分配ID
	 * 
	 * @param paymentitemid
	 *            : String
	 */
	public void setPaymentitemid(String paymentitemid)
	{
		this.paymentitemid = paymentitemid;
	}

	/**
	 * 功能描述: 获得客户单清帐
	 * 
	 * @return 客户单清帐 : CustomSingleClear
	 */
	public CustomSingleClear getCustomSingleClear()
	{
		return this.customSingleClear;
	}

	/**
	 * 功能描述: 设置客户单清帐
	 * 
	 * @param customSingleClear
	 *            : CustomSingleClear
	 */
	public void setCustomSingleClear(CustomSingleClear customSingleClear)
	{
		this.customSingleClear = customSingleClear;
	}

	public BigDecimal getBwbje() {
		return bwbje;
	}

	public void setBwbje(BigDecimal bwbje) {
		this.bwbje = bwbje;
	}

	/**
	 * 功能描述: 设置未清本位币金额
	 * @return
	 */
	public BigDecimal getUnbwbje() {
		return unbwbje;
	}
	/**
	 * 功能描述: 取得未清本位币金额
	 * @param unbwbje
	 */
	public void setUnbwbje(BigDecimal unbwbje) {
		this.unbwbje = unbwbje;
	}
	
	/**
	 * 默认构造器
	 */
	public UnClearCustomBill()
	{
		super();
	}
}
