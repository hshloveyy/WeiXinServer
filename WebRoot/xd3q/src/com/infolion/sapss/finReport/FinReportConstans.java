package com.infolion.sapss.finReport;

import java.util.LinkedHashMap;

public class FinReportConstans {
	
	/**经营情况概述***/
	public static LinkedHashMap<String,String> FIELD_GENNERAL = new LinkedHashMap<String,String>();
	static {		
		FIELD_GENNERAL.put("CKCHZE", "出口创汇总额");
		FIELD_GENNERAL.put("JKYHZE", "进口用汇总额");
		FIELD_GENNERAL.put("ZKYH", "转口用汇");
		FIELD_GENNERAL.put("NMZE", "内贸总额");
		FIELD_GENNERAL.put("YYSR", "营业收入");
		FIELD_GENNERAL.put("YYCB", "营业成本");
		FIELD_GENNERAL.put("YYSJJFJ", "营业税金及附加");
		FIELD_GENNERAL.put("SSFY", "销售费用");
		FIELD_GENNERAL.put("GLFY", "管理费用");
		FIELD_GENNERAL.put("CWFY", "财务费用");
		FIELD_GENNERAL.put("ZCJZSS", "资产减值损失");
		FIELD_GENNERAL.put("GYJZBDSY", "公允价值变动收益");
		FIELD_GENNERAL.put("TZSY", "投资收益");
		FIELD_GENNERAL.put("YYWSR", "营业外收入");
		FIELD_GENNERAL.put("YYWZC", "营业外支出");
		FIELD_GENNERAL.put("LRZE", "利润总额");
		FIELD_GENNERAL.put("SDS", "所得税");
		FIELD_GENNERAL.put("JLL", "净利润");
	}


}
