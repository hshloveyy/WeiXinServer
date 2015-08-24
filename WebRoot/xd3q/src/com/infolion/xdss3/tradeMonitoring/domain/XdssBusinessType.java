/*
 * @(#)XdssBusinessType.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-8
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.domain;

/**
 * <pre>
 * 信达业务类型常量类。
 * eg:立项、合同组、合同、等。
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class XdssBusinessType
{

	/**
	 * 立项,PRO
	 */
	public static String PROJECT = "PRO";

	/**
	 * 合同组,CG
	 */
	public static String CONTRACTGROUP = "CG";

	/**
	 * 其他收付款
	 */
	public static String OTHERPAYMENTCOLLECT = "OTH";

	/**
	 * 合同,C
	 */
	public static String CONTRACT = "C";

	/**
	 * 采购合同,PC
	 */
	public static String CONTRACT_PURCHASEINFO = "PC";

	/**
	 * 销售合同,SC
	 */
	public static String CONTRACT_SALESINFO = "SC";

	/**
	 * 收货
	 */
	private static String RECEIPTS = "REC";

	/**
	 * 退货
	 */
	private static String SALES_RETURN = "SRE";

	/**
	 * 发票校验
	 */
	private static String InvoiceVerify = "IV";

	/**
	 * 付款
	 */
	private static String PAYMENT = "PAY";
	/**
	 * 收款
	 */
	private static String COLLECT = "COL";

	/**
	 * 退款
	 */
	private static String DRAWBACK = "BAK";

	/**
	 * 开证
	 */
	private static String CREDIT_CREATE = "CREC";

	/**
	 * 到单
	 */
	private static String PICKLIST = "PIC";

	/**
	 * 发货
	 */
	private static String SHIP = "SHIP";

	/**
	 * 内贸开票
	 */
	private static String BILL = "BILL";

	/**
	 * 进口开票
	 */
	private static String IMPORTBILL = "IMPORTBILL";

	/**
	 * 收证
	 */
	private static String CREDIT_REC = "CRER";

	/**
	 * 出单
	 */
	private static String EXPORT = "EXP";

}
