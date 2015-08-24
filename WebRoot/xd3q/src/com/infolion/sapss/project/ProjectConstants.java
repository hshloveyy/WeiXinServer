package com.infolion.sapss.project;

import java.util.HashMap;
import java.util.Map;

/**
 * 立项常量表
 * @author Administrator
 *
 */
public class ProjectConstants {
	
	/**立项成本核算信息**/
	//内贸40过后为新增的测算项
	public static Integer[] EV_D_TT_HOMETRADE = new Integer[]{
		1,2,3,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,22,23,25,27,28,29,31,
		   40,41,42,43,44,45,46,47,48,49,57,58};
	//出口
	public static Integer[] EV_D_TT_EXPORT = new Integer[]{1,2,3, 6,7,8,9,10,11,12,13, 15,16,17,18,19,20, 21,22,23,24,25, 27,28,29,31,
		40,41,42,43,44,45,46,47,48,49};
	
	//进口
	public static Integer[] EV_D_TT_IMPORT = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13, 15,16,17 ,19, 21,22,23,25, 27,28,29,31,
		40,41,42,43,44,45,46,47,48,49,50,52,53,54,55,56,57,58};
	
	//代理
	public static Integer[] EV_D_TT_PROXY = new Integer[]{1,2,3,6,7,8,9,10,11,12,13,15,16,17 ,19,21,22,23,25,31,
		   40,41,42,43,44,45,46,47,58};
	
	/**立项数据校验*/
	/***内贸**/
	public static Map<String,String> VALIDATE_HOMETRADE = new HashMap<String,String>();
	//进口
	public static Map<String,String> VALIDATE_IMPORT = new HashMap<String,String>();
	//出口
	public static Map<String,String> VALIDATE_EXPORT = new HashMap<String,String>();
	//代理
	public static Map<String,String> VALIDATE_PROXY = new HashMap<String,String>();
	/**新立项校验***/
	static {
		
		VALIDATE_HOMETRADE.put("rmb40", "商品购进成本-税率");
		VALIDATE_HOMETRADE.put("rmb3", "商品购进成本-货款总额（含税）");
		VALIDATE_HOMETRADE.put("rmb25", "合同预计利润率（百分比或单位利润）");
		VALIDATE_HOMETRADE.put("rmb45", "商品销售价-税率");
		VALIDATE_HOMETRADE.put("rmb29", "商品销售价-货款总额（含税）");
		
		VALIDATE_IMPORT.put("us3", "商品购进成本-货款金额（外币）");
		VALIDATE_IMPORT.put("rmb40", "商品购进成本-海关关税");
		VALIDATE_IMPORT.put("rmb41", "商品购进成本-海关增值税");
		VALIDATE_IMPORT.put("rmb42", "商品购进成本-海关消费税");
		VALIDATE_IMPORT.put("rmb25", "合同预计利润率（百分比或单位利润）");
		VALIDATE_IMPORT.put("rmb52", "商品销售价-税率");
		VALIDATE_IMPORT.put("rmb29", "商品销售价-货款总额（含税）");
		
		VALIDATE_EXPORT.put("rmb40", "商品购进成本-税率");
		VALIDATE_EXPORT.put("rmb3", "商品购进成本-货款总额（含税）");
		VALIDATE_EXPORT.put("rmb25", "合同预计利润率（百分比或单位利润）");
		VALIDATE_EXPORT.put("us29", "商品销售价-金额(外币)");
		VALIDATE_EXPORT.put("other3", "退税率");
        
		VALIDATE_PROXY.put("rmb25", "合同预计利润率（百分比或单位利润）");
		VALIDATE_PROXY.put("rmb46", "代理收入（开票金额）");
		VALIDATE_PROXY.put("rmb23", "印花税");
		VALIDATE_PROXY.put("rmb31", "预计利润总额或亏损总额");
	}
	
	/****旧的立项申报校验**/
    public static Map<String,Map<String,String>> PROJECT_VALIDATE = new HashMap<String,Map<String,String>>();
	static {
		//合作进口
		Map<String,String> TRADE_TYPE_1 = new HashMap<String,String>();
		TRADE_TYPE_1.put("us6", "小计（C&F.CIF.进货总额）外币金额");
		TRADE_TYPE_1.put("rmb25", "预计利润率（百分比或单位利润）");
		TRADE_TYPE_1.put("rmb31", "预计利润总额或亏损总额");
		//合作出口
		Map<String,String> TRADE_TYPE_2 = new HashMap<String,String>();
		TRADE_TYPE_2.put("other3", "退税率%");
		TRADE_TYPE_2.put("rmb25", "预计利润率（百分比或单位利润）");
		TRADE_TYPE_2.put("us29", "商品销售价金额-外币金额");
		TRADE_TYPE_2.put("rmb31", "预计利润总额或亏损总额");
		//自营进口
		Map<String,String> TRADE_TYPE_3 = new HashMap<String,String>();
		TRADE_TYPE_3.put("us6", "小计（C&F.CIF.进货总额）外币金额");
		TRADE_TYPE_3.put("rmb7", "直接流通费合计");
		TRADE_TYPE_3.put("rmb8", "(1)短途运杂、装卸旨");
		TRADE_TYPE_3.put("rmb9", "(2)码头费");
		TRADE_TYPE_3.put("rmb10", "(3)仓储费");
		TRADE_TYPE_3.put("rmb11", "(4)报关、报检费等");
		TRADE_TYPE_3.put("rmb13", "佣金或手续费");
		TRADE_TYPE_3.put("rmb17", "货款利息");
		TRADE_TYPE_3.put("rmb20", "海关关税");
		TRADE_TYPE_3.put("rmb21", "增值税及附加费（利润×18.87%、14.43%）");
		TRADE_TYPE_3.put("rmb29", "商品销售价-金额");
		TRADE_TYPE_3.put("rmb31", "预计利润总额或亏损总额");
		//自营出口
		Map<String,String> TRADE_TYPE_4 = new HashMap<String,String>();
		TRADE_TYPE_4.put("rmb3", "商品购进成本(FOB)-金额");
		TRADE_TYPE_4.put("rmb6", "小计（C&F.CIF.进货总额）");
		TRADE_TYPE_4.put("rmb7", "直接流通费合计");
		TRADE_TYPE_4.put("rmb8", "(1)短途运杂、装卸旨");
		TRADE_TYPE_4.put("rmb9", "(2)码头费");
		TRADE_TYPE_4.put("rmb10", "(3)仓储费");
		TRADE_TYPE_4.put("rmb11", "(4)报关、报检费等");
		TRADE_TYPE_4.put("rmb13", "佣金或手续费");
		TRADE_TYPE_4.put("rmb15", "其它(样品费、商品损耗、包装费等)");
		TRADE_TYPE_4.put("rmb17", "货款利息");
		TRADE_TYPE_4.put("rmb18", "退税款占用利息");
		TRADE_TYPE_4.put("rmb19", "银行结算费");
		TRADE_TYPE_4.put("rmb20", "海关关税");
		TRADE_TYPE_4.put("rmb21", "增值税及附加费（利润×18.87%、14.43%）");
		TRADE_TYPE_4.put("other3", "退税率%");
		TRADE_TYPE_4.put("rmb24", "退税率");
		TRADE_TYPE_4.put("us29", "商品销售价-金额（外币）");
		TRADE_TYPE_4.put("rmb30", "换汇成本");
		TRADE_TYPE_4.put("rmb31", "预计利润总额或亏损总额");
		//代理出口
		Map<String,String> TRADE_TYPE_5 = new HashMap<String,String>();
		TRADE_TYPE_5.put("rmb22", "营业税及附加费（5.55%）");
		TRADE_TYPE_5.put("rmb25", "预计利润率（百分比或单位利润）");
		TRADE_TYPE_5.put("rmb31", "预计利润总额或亏损总额");
		//代理进口
		Map<String,String> TRADE_TYPE_6 = new HashMap<String,String>();
		TRADE_TYPE_6.put("rmb22", "营业税及附加费（5.55%）");
		TRADE_TYPE_6.put("rmb25", "预计利润率（百分比或单位利润）");
		TRADE_TYPE_6.put("rmb31", "预计利润总额或亏损总额");
		//内贸
		Map<String,String> TRADE_TYPE_7 = new HashMap<String,String>();
		TRADE_TYPE_7.put("rmb3", "商品购进成本（FOB）-金额");
		TRADE_TYPE_7.put("rmb8", "(1)短途运杂、装卸旨");
		TRADE_TYPE_7.put("rmb9", "(2)码头费");
		TRADE_TYPE_7.put("rmb10", "(3)仓储费");
		TRADE_TYPE_7.put("rmb13", "佣金或手续费");
		TRADE_TYPE_7.put("rmb17", "货款利息");
		TRADE_TYPE_7.put("rmb21", "增值税及附加费（利润×18.87%、14.43%）");
		TRADE_TYPE_7.put("rmb29", "商品销售价-金额");
		TRADE_TYPE_7.put("rmb31", "预计利润总额或亏损总额");
		//进料加工
		Map<String,String> TRADE_TYPE_8 = new HashMap<String,String>();
		TRADE_TYPE_8.put("us3", "商品购进成本(FOB)-金额-外币");
		TRADE_TYPE_8.put("us4", "国外运费(F)-外币");
		TRADE_TYPE_8.put("us5", "国外保险费(I)-外币");
		TRADE_TYPE_8.put("us6", "小计（C&F.CIF.进货总额）-外币");
		TRADE_TYPE_8.put("rmb25", "预计利润率（百分比或单位利润）");
		TRADE_TYPE_8.put("us29", "商品销售价-金额-外币");
		TRADE_TYPE_8.put("rmb31", "预计利润总额或亏损总额");
		//自营进口敞口
		Map<String,String> TRADE_TYPE_9 = new HashMap<String,String>();
		TRADE_TYPE_9.put("us6", "小计（C&F.CIF.进货总额）外币金额");
		TRADE_TYPE_9.put("rmb7", "直接流通费合计");
		TRADE_TYPE_9.put("rmb8", "(1)短途运杂、装卸旨");
		TRADE_TYPE_9.put("rmb9", "(2)码头费");
		TRADE_TYPE_9.put("rmb10", "(3)仓储费");
		TRADE_TYPE_9.put("rmb11", "(4)报关、报检费等");
		TRADE_TYPE_9.put("rmb13", "佣金或手续费");
		TRADE_TYPE_9.put("rmb17", "货款利息");
		TRADE_TYPE_9.put("rmb20", "海关关税");
		TRADE_TYPE_9.put("rmb21", "增值税及附加费（利润×18.87%、14.43%）");
		TRADE_TYPE_9.put("rmb29", "商品销售价-金额");
		TRADE_TYPE_9.put("rmb31", "预计利润总额或亏损总额");
		//内贸敞口
		Map<String,String> TRADE_TYPE_10 = new HashMap<String,String>();
		TRADE_TYPE_10.put("rmb3", "商品购进成本（FOB）-金额");
		TRADE_TYPE_10.put("rmb8", "(1)短途运杂、装卸旨");
		TRADE_TYPE_10.put("rmb9", "(2)码头费");
		TRADE_TYPE_10.put("rmb10", "(3)仓储费");
		TRADE_TYPE_10.put("rmb13", "佣金或手续费");
		TRADE_TYPE_10.put("rmb17", "货款利息");
		TRADE_TYPE_10.put("rmb21", "增值税及附加费（利润×18.87%、14.43%）");
		TRADE_TYPE_10.put("rmb29", "商品销售价-金额");
		TRADE_TYPE_10.put("rmb31", "预计利润总额或亏损总额");
		
		PROJECT_VALIDATE.put("1", TRADE_TYPE_1);
		PROJECT_VALIDATE.put("2", TRADE_TYPE_2);
		PROJECT_VALIDATE.put("3", TRADE_TYPE_3);
		PROJECT_VALIDATE.put("4", TRADE_TYPE_4);
		PROJECT_VALIDATE.put("5", TRADE_TYPE_5);
		PROJECT_VALIDATE.put("6", TRADE_TYPE_6);
		PROJECT_VALIDATE.put("7", TRADE_TYPE_7);
		PROJECT_VALIDATE.put("8", TRADE_TYPE_7);
		PROJECT_VALIDATE.put("9", TRADE_TYPE_9);
		PROJECT_VALIDATE.put("10", TRADE_TYPE_10);
		PROJECT_VALIDATE.put("11", TRADE_TYPE_1);
		PROJECT_VALIDATE.put("12", TRADE_TYPE_8);
	}
	

}
