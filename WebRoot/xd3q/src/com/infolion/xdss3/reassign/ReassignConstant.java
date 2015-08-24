package com.infolion.xdss3.reassign;

/**
 * 重分配类型变量定义
 * @author QiuRongfa
 *
 */
public class ReassignConstant {
	
	/**
	 * 重分配类型定义
	 */
	public static String COLLECT = "1";	//收款
	
	public static String PAYMENT = "2";	//付款
	
	public static String BILLCLEARPAYMENT = "3";       //票清付款
	
	public static String BILLCLEARCOLLECT = "4";       //票清收款
	
	public static String CUSTOMERREFUNDMENT = "5";     //客户退款
	
	public static String SUPPLIERREFUNDMENT = "6";     //供应商退款
	
	public static String CUSTOMERSINGLECLEAR = "7";    //客户单清帐
	
	public static String SUPPLIERSINGLECLEAR = "8";    //供应商单清帐
	
	/**
	 * 重分配方式定义
	 */
	/*-----------------------------------------------------------------*/
	/**
	 * 重置（到业务部门重新分配）
	 */
	public static String RESET_TO_BS = "1";		
	/**
     * 重置并冲销（到业务部门重新分配）
     */
	public static String RESET_AND_CLEAR = "2";	
	/**
     * 重置（财务部直接解除分配关系）
     */
	public static String RESET_TO_FI = "3";
	/**
     * 冲销（财务部冲销并作废）
     */
	public static String FI_CLEAR = "4";			
	/**
	 * 重置并冲销（到业务部门重新分配，过资金部）
	 */
	public static String RESET_AND_CLEAR_TO_CASH = "5";

}
