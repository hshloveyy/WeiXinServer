/*
 * @(#)BusinessState.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-14
 *  描　述：创建
 */

package com.infolion.xdss3;

/**
 * <pre>
 * 信达业务状态常量类。
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
public class BusinessState
{
	
	/**
	 * 作废
	 */
	public static String BLANKOUT = "-1";

	
	/**
	 * 重做（重分配）
	 */
	public static String REASSIGNED = "-2";
	
	/**
	 * 单据新增状态
	 */
	public static String ADDNEW = "0";

	/**
	 * 进入审批流
	 */
	public static String RUNWORKFLOW = "1";

	/**
	 * 在审批途中
	 */
	public static String RUNINWORKFLOW = "2','3";
	
	/**
	 * 收款审批通过。
	 */
	public static String COLLECTSUBMITPASS = "3";

	/**
	 * 审批通过。
	 */
	public static String SUBMITPASS = "4";

	/**
	 * 审批未通过。
	 */
	public static String SUBMITNOTPASS = "5";

	
	/**
	 * 特批审批中
	 */
	public static String SPECIALRUNINWORKFLOW = "6";

	/**
	 * 特批审批通过
	 */
	public static String SPECIALSUBMITPASS = "7";

	/**
	 * 特批审批不通过
	 */
	public static String SPECIALSUBMITNOTPASS = "8";

	/**
	 * 所有在途状态，SQL IN 查询字串
	 */
	public static String ALL_ONROAD = "'" + RUNWORKFLOW + "','" + RUNINWORKFLOW + "','" + SPECIALRUNINWORKFLOW + "'";

	/**
	 * 所有审批通过状态，SQL IN 查询字串
	 */
	public static String ALL_SUBMITPASS = "'" + SUBMITPASS + "','" + SPECIALSUBMITPASS + "'";
	
	/* 邱杰烜 2010-10-25 以下状态只为了收付款获取已批、在批等金额使用  */
	
	/**
	 * 所有已批通过的收款金额状态
	 */
	public static String ALL_COLLECT_PAIDUP = "'3'";
	/**
	 * 所有在批的收款金额状态
	 */
	public static String ALL_COLLECT_ONROAD = "'-2','0','1','2','4',' ',''";
	
	/**
	 * 所有已批通过的付款金额状态
	 */
	public static String ALL_PAYMENT_PAIDUP = "'4'";
	/**
	 * 所有在批的付款金额状态
	 */
	public static String ALL_PAYMENT_ONROAD = "'-2','0','1','2','3','5','6','7','8',' ',''";
	
	/**
	 * 所有已批通过的票清金额状态
	 */
	public static String ALL_BILLCLEAR_PAIDUP = "'3'";
	/**
	 * 所有在批的票清金额状态
	 */
	public static String ALL_BILLCLEAR_ONROAD = "'-2','0','1','4',' ',''";
	
	/**
	 * 所有已批通过的单清帐状态
	 */
	public static String ALL_SINGLECLEAR_PAIDUP = "'4'";
	/**
	 * 所有在批的单清帐状态
	 */
	public static String ALL_SINGLECLEAR_ONROAD = "'0','1','2','3',' ',''";

}
