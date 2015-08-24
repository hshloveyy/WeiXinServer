/*
 * @(#)SyncMasterDataConstants.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-26
 *  描　述：创建
 */

package com.infolion.xdss3.masterData;

/**
 * 
 * <pre>
 * 主数据同步常量
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
public class SyncMasterDataConstants
{

	/**
	 * 执行同步时间
	 */
	public static String SYNCDATE = "SYNCDATE";

	/**
	 * 最后一次执行同步成功时间
	 */
	public static String ZDATE = "ZDATE";

	/**
	 * RFC内部错误信息。
	 */
	public static String ERRMESSAGE = "MESSAGE";

	/**
	 * 新增的数据集合，JCO.TABLE 名称
	 */
	public static String ADDDATA_TABLE = "ADDDATA";

	/**
	 * 修改的数据集合，JCO.TABLE 名称
	 */
	public static String MODIFYDATA_TABLE = "MODIFYDATA";

	/**
	 * 错误信息，JCO.TABLE 名称
	 */
	public static String ERRORTABLE_TABLE = "ERRORTABLE";

	// 供应商主数据
	/**
	 * 供应商表名称
	 */
	public static String Supplier_TableName = "YGETLIFNR";

	/**
	 * 供应商RFC FunctionName
	 */
	public static String Supplier_RFCFunctionName = "ZF_GETLIFNR";

	// 客户主数据
	/**
	 * 客户表名称
	 */
	public static String Customer_TableName = "YGETKUNNR";

	/**
	 * 客户RFC FunctionName
	 */
	public static String Customer_RFCFunctionName = "ZF_GETKUNNR";

	/**
	 * 会计科目
	 */
	public static String Hkont_TableName = "YSKAT";

	/**
	 * 会计科目RFC FunctionName
	 */
	public static String Hkont_RFCFunctionName = "ZF_GETSKB1";

	/**
	 * 现金流量项
	 */
	public static String CashFlowItem_TableName = "YT053S";

	/**
	 * 现金流量项RFC FunctionName
	 */
	public static String CashFlowItem_RFCFunctionName = "ZF_GETT053S";

	/**
	 * 成本中心
	 */
	public static String CostCenter_TableName = "YCOSTCENTER";

	/**
	 * 成本中心RFC FunctionName
	 */
	public static String CostCenter_RFCFunctionName = "ZF_GETCSKS";

	/**
	 * 利润中心
	 */
	public static String Prctr_TableName = "YPRCTR";

	/**
	 * 利润中心RFC FunctionName
	 */
	public static String Prctr_RFCFunctionName = "ZF_GETCEPC";

	/**
	 * 开票业务数据（抬头）
	 */
	public static String Vbrk_TableName = "YGETVBRK";

	/**
	 * 开票业务数据（明细）
	 */
	public static String Vbrp_TableName = "YGETVBRP";

	/**
	 * 开票业务数据RFC FunctionName
	 */
	public static String Vbrk_RFCFunctionName = "ZF_GETVBRP";

	/**
	 * 发票校验（抬头）
	 */
	public static String RSEG_TableName = "YGETBELNR";

	/**
	 * 发票校验（明细）
	 */
	public static String RSEGItem_TableName = "YGETITEM";

	/**
	 * 发票校验RFC FunctionName
	 */
	public static String RSEG_RFCFunctionName = "ZF_GETRSEG";
	
	/**
	 * 未清供应商RFC FunctionName
	 */
	public static String UNCLEARVENDOR_RFCFunctionName = "ZF_VENDOR";
	
	/**
	 * 未清客户RFC FunctionName
	 */
	public static String UNCLEARCUSTOMER_RFCFunctionName = "ZF_CUSTOMER";
	
	/**
	 * 未清供应商其他公司RFC FunctionName
	 */
	public static String UNCLEARVENDOR_OTHERS_RFCFunctionName = "ZF_VENDOR_OTHERS";
	
	/**
	 * 未清客户其他公司RFC FunctionName
	 */
	public static String UNCLEARCUSTOMER_OTHERS_RFCFunctionName = "ZF_CUSTOMER_OTHERS";
	
	/**
	 * 公司信息RFC FunctionName
	 */
	public static String COMPANY_RFCFuctionName = "ZF_GETBUKRS";
	
	/**
	 * 部门信息RFC FunctionName
	 */
	public static String DEPT_RFCFuctionName = "ZF_GETGSBER";
	
	/**
	 * 物料组RFC FunctionName
	 */
	public static String MATGROUP_RFCFuctionName = "ZF_GETMATGROUP";
	
	/**
	 * 汇率(明细)
	 */
	public static String TCURR_TableName = "YTCURR";
	
	/**
	 * 汇率RFC
	 */
	public static String TCURR_RFCFunctionName = "ZF_TCURRC";
	
	/**
     * 凭证数据RFC
     */
	public static String BSEG_RFCFunctionName = "ZF_GETBSEG";
}
