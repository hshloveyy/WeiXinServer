package com.infolion.sapss.bapi;

import java.util.HashMap;
import java.util.Map;

public class BapiConstant {
	
	/**
	 * 字典表配置文件路径
	 */
	public static String DICTIONARY_PATH = "\\com\\infolion\\sapss\\bapi\\dictionaryConfig\\";
	
	/**
	 * 字典表配置文件配置文件扩展名称
	 */
	public static String DICTIONARY_CONFIGFILE_EXTEND_NAME = "properties";
	
	/**
	 * 全部BAPI字典配置文件同步数据，过滤文件
	 */
	public static String DICTIONARY_REGEX="BM_.*\\.properties";
	
	/**
	 * BAPI字典表配置文件，SAP函数名称KEY值
	 */
	public static String DICTIONARY_FUNCTION_NAME="SAP_FUNCTION_NAME";
	
	/**
	 * BAPI字典表配置文件，SAP内表名称KEY值
	 */
	public static String DICTIONARY_INNERTABLE_NAME="SAP_INNERTABLE_NAME";
	/**
	 * BAPI字典表配置文件，外围系统目的表名KEY值
	 */
	public static String DICTIONARY_DES_TABLE_NAME ="DES_TABLE_NAME";
	/**
	 * BAPI字典表配置文件，传入SAP系统参数名称KEY值
	 */
	public static String DICTIONARY_PARAMETER_NAME = "BAPI_PARAMETER";
	
	/**
	 * 根据配置文件中的参数字符串解析参数
	 * @param paraChar
	 * @return
	 */
	public static Map<String,String> getParaMeter(String paraChar){
		Map<String,String> result = new HashMap<String, String>();
		if (paraChar==null) return result;
		else {
			String[] paras = paraChar.split("!");
			for(String para:paras){
				String[] k_v = para.split("\\|");
				result.put(k_v[0], k_v[1]);
			}
		}
		return result;
	}
	
	
	/**
	 * SAP返回，供应商主数据返总记录数名称
	 */
	public static String SUPPLIER_MASTER_DATA_TOTALRECORDS ="O_TOTALRECORDS";
	/**
	 * SAP供应商主数据，函数名称
	 */
	public static String SUPPLIER_MASTER_DATA_FUNCTION_NAME = "ZM_GETLIFNR";
	/**
	 * SAP供应商主数据，内表名称
	 */
	public static String SUPPLIER_MASTER_DATA_INNERTABLE_NAME="ZTAB_OUTPUT";
	
	/**
	 * SAP返回，客户主数据返总记录数名称
	 */
	public static String CUSTOMER_MASTER_DATA_TOTALRECORDS ="E_TOTAL_LINE";
	/**
	 * SAP客户主数据，函数名称
	 */
	public static String CUSTOMER_MASTER_DATA_FUNCTION_NAME = "ZM_GETKUNNR";
	/**
	 * SAP供应商主数据，内表名称
	 */
	public static String CUSTOMER_MASTER_DATA_INNERTABLE_NAME="ZOUTPUT";
	
	/**
	 * SAP返回，物料主数据返总记录数名称
	 */
	public static String MATERIAL_MASTER_DATA_TOTALRECORDS ="E_TOTALRECORDS";
	/**
	 * SAP物料主数据，函数名称
	 */
	public static String MATERIAL_MASTER_DATA_FUNCTION_NAME = "ZM_GETMATNR";
	/**
	 * SAP物料主数据，内表名称
	 */
	public static String MATERIAL_MASTER_DATA_INNERTABLE_NAME="ZOUTPUT";
	
	/**
	 * BAPI生成销售订单，函数名称
	 */
	public static String BAPI_SALES_ORDER_FUNCTION_NAME = "ZMSALESORDER_CREATEFROMDAT";
	/**
	 * BAPI生成销售订单，抬头数据表名
	 */
	public static String BAPI_SALES_ORDER_HEAD_TABLE_NAME ="SALEORDER_TT";
	/**
	 * BAPI生成销售订单，行数据表名
	 */
	public static String BAPI_SALES_ORDER_ROW_TABLE_NAME ="SALEORDER_H";
	/**
	 * BAPI生成销售订单，费用类型据表名
	 */
	public static String BAPI_SALES_ORDER_CASE_TABLE_NAME ="SALEORDER_TJ";
	/**
	 * BAPI生成销售订单，返回订单号，参数明称
	 */
	public static String BAPI_SALES_ORDER_RETURN_NNUMBER_PARA_NAME = "SALESDOCUMENT";
	/**
	 * BAPI生成销售订单，返回消息列表名称
	 */
	public static String BAPI_SALES_ORDER_RETURN_MESSAGE_TABLE_NAME="BAPIRETURN";
	/**
	 * BAPI生成销售订单返回信息需要保存字段
	 */
	public static Map<String,String> BAPI_SALES_ORDER_MSG_FIELDS = new HashMap<String,String>();
	static {
		//key为BAPI返回表字段名称，value为TBapiLogDetail类字段名称
		BAPI_SALES_ORDER_MSG_FIELDS.put("TYPE", "logType");
		BAPI_SALES_ORDER_MSG_FIELDS.put("ID", "logCode");
		BAPI_SALES_ORDER_MSG_FIELDS.put("MESSAGE", "logContent");
	}
	
	/**
	 * BAPI生成采购订单，BAPI函数明称
	 */
	public static String BAPI_PURCHARSE_ORDER_FUNCTION_NAME = "ZMPO_CREATE";
	/**
	 * BAPI生成采购订单，抬头数据表名
	 */
	public static String BAPI_PURCHARSE_ORDER_HEAD_TABLE_NAME ="IT_EKKO";
	/**
	 * BAPI生成采购订单，行数据表名
	 */
	public static String BAPI_PURCHARSE_ORDER_ROW_TABLE_NAME ="IT_LINEDATA";
	/**
	 * BAPI生成采购订单，费用类型据表名
	 */
	public static String BAPI_PURCHARSE_ORDER_CASE_TABLE_NAME ="IT_LINEDATA2";
	/**
	 * BAPI生成采购订单，返回订单号参数名称
	 */
	public static String BAPI_PURCHARSE_ORDER_RETURN_NNUMBER_PARA_NAME ="PO_NUM";
	/**
	 * BAPI生成采购订单，返回消息列表名称
	 */
	public static String BAPI_PURCHARSE_ORDER_RETURN_MESSAGE_TABLE_NAME="IT_BAPIRETURN";
	/**
	 * BAPI生成采购订单返回信息需要保存字段
	 */
	public static Map<String,String> BAPI_PURCHARSE_ORDER_MSG_FIELDS = new HashMap<String,String>();
	
	/**
	 * SAP返回，存货浮动盈亏主数据返总记录数名称
	 */
	public static String PROFITLOSS_DATA_TOTALRECORDS ="E_TOTALRECORDS";
	/**
	 * SAP存货浮动盈亏主数据，函数名称
	 */
	public static String PROFITLOSS_DATA_FUNCTION_NAME = "ZF_GETMATNR";
	/**
	 * SAP存货浮动盈亏主数据，内表名称
	 */
	public static String PROFITLOSS_DATA_INNERTABLE_NAME="T_CTINFO";
	
	static {
		//key为BAPI返回表字段名称，value为TBapiLogDetail类字段名称
		BAPI_PURCHARSE_ORDER_MSG_FIELDS.put("TYPE", "logType");
		BAPI_PURCHARSE_ORDER_MSG_FIELDS.put("ID", "logCode");
		BAPI_PURCHARSE_ORDER_MSG_FIELDS.put("MESSAGE", "logContent");
	}
	
	
	public static void main(String[] args){
		System.out.println(BapiConstant.getParaMeter("I_DTYPE|08"));
	}
}
