/*
 * @(#)QueryField.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-12
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.domain;

/**
 * <pre>
 * 贸易监控查询条件字段共用常量。
 * 对应SAP DOMAIN  ：YDTRADEQUERYFIELD
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
public class QueryField
{

	/**
	 * 01 立项号
	 */
	public static String PROJECT_NO = "01";

	/**
	 * 02 立项名称
	 */
	public static String PROJECT_NAME = "02";
	
	
	/**
	 * 28立项品名
	 */
	public static String PROJECT_CLASSNAME = "28";

	/**
	 * 03 合同组号
	 */
	public static String CONTRACT_GROUP_NO = "03";

	/**
	 * 04 合同组名称
	 */
	public static String CONTRACT_GROUP_NAME = "04";

	/**
	 * 05 采购合同编号 CONTRACT_NO
	 */
	public static String CONTRACT_PURCHASE_NO = "05";

	/**
	 * 25 采购合同名称
	 */
	public static String CONTRACT_PURCHASE_NAME = "06";

	/**
	 * 06 采购纸质合同号
	 */
	public static String EKKO_UNSEZ = "07";

	/**
	 * 08销售合同编号 CONTRACT_NO
	 */
	public static String CONTRACT_SALES_NO = "08";

	/**
	 * 09 销售合同名称
	 */
	public static String CONTRACT_SALES_NAME = "09";

	/**
	 * 08 销售纸质合同号
	 */
	public static String VBKD_IHREZ = "10";

	/**
	 * 09 信用证号
	 */
	public static String CREDIT_NO = "11";

	/**
	 * 10 出单发票号
	 */
	public static String INV_NO = "12";

	/**
	 * 11 到单号
	 */
	public static String PICK_LIST_NO = "13";

	/**
	 * 12 内贸开票发票号
	 */
	public static String TAX_NO = "14";

	/**
	 * 13 内贸开票过账凭证号
	 */
	public static String SAP_BILL_NO = "15";

	/**
	 * 14 进仓单号
	 */
	public static String RECEIPT_NO = "16";

	/**
	 * 15 进仓物料凭证号
	 */
	public static String SAP_RETURN_NO1 = "17";

	/**
	 * 16 出仓单号
	 */
	public static String SHIP_NO = "18";

	/**
	 * 17 出仓物料凭证号
	 */
	public static String SAP_RETURN_NO2 = "19";

	/**
	 * 18 付款单号
	 */
	public static String PAYMENT_NO = "20";

	/**
	 * 19 付款SAP凭证号
	 */
	public static String PAYMENT_SAPORDERNO = "21";

	/**
	 * 20 收款单号
	 */
	public static String COLLECT_NO = "22";

	/**
	 * 21 收款SAP凭证号
	 */
	public static String COLLECT_SAPORDERNO = "23";

	/**
	 * 22 发票检验凭证号
	 */
	public static String BELNR = "24";

	/**
	 * 23 出口开票号
	 */
	public static String VBELN = "25";

	/**
	 * 退款单号
	 */
	public static String REFUNDMENTNO = "26";

	/**
	 * 退款凭证号
	 */
	public static String REFUNDMENTVOUCHERNO = "27";
	
	
	/**
	 * 供应商名称
	 */
	public static String CONTRACT_PURCHASE_PRONAME = "29";
	
	/**
	 * 客户名称
	 */
	
	public static String CONTRACT_SALES_CUSNAME = "30";


}
