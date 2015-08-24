package com.infolion.sapss;

import java.util.HashSet;
import java.util.Set;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;

public class ReceiptShipConstants {
	
	public static int HIDE_LEVEL_COMMON = 1; //不屏蔽
	
	public static int HIDE_LEVEL_AUDIT = 2;//审计帐号屏蔽
	
	public static int HIDE_LEVEL_ALL = 3;//全屏蔽
	
	public static int HIDE_LEVEL = HIDE_LEVEL_AUDIT; //系统设置该属性即可
	
	public static int EXAM_STATE_1 = 1;//审批属性 新增
	public static int EXAM_STATE_2 = 2;//审批属性 修改
	public static int EXAM_STATE_3 = 3;//审批属性 审批通过
	public static int EXAM_STATE_4 = 4;//审批属性 审批不通过
	public static int EXAM_STATE_5 = 5;//审批属性 二次结算
	
	
	public static String START_DATE = "2012-07-03 22:00:00";//
	
	private static Set<String> processSet = new HashSet<String>();
	static {
		processSet.add("consignment_export_v1");
		processSet.add("consignment_import_home_v1");
		processSet.add("receipt_v1");
	}
	private static ReceiptShipConstants ins = null;
	public static ReceiptShipConstants ins(){
		if(ins==null) ins = new ReceiptShipConstants();
		return ins;
	}
	
	public String chooseProcessimage(String processName){
		if(processSet.contains(processName)){
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
	        String deptShortCode = userContext.getSysDept().getDeptShortCode();
	        if(HIDE_LEVEL==HIDE_LEVEL_ALL) return "processimage1.jpg";
	        else if(HIDE_LEVEL==HIDE_LEVEL_AUDIT&&deptShortCode.startsWith("SJ")) return "processimage1.jpg";
		}
		return "processimage.jpg";
	}
	
	public boolean isShouldHide(){
		if(HIDE_LEVEL==HIDE_LEVEL_ALL) return true;
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        String deptShortCode = userContext.getSysDept().getDeptShortCode();
        if(HIDE_LEVEL==HIDE_LEVEL_AUDIT&&deptShortCode.startsWith("SJ")) return true;
        return false;
	}
}
