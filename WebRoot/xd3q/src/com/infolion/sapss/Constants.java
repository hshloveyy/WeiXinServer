/*
 * @(#)Constons.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-17
 *  描　述：创建
 */

package com.infolion.sapss;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;

/**
 * 
 * <pre>全局静态变量</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * 
 * @see The author for more details
 * @since 1.0
 */
public class Constants {
	/**
	 * 数据库schema
	 */
	//public final static String DB_SCHEMA = "XDSS";
	
    private static Log log = LogFactory.getLog("com.infolion.sapss.Constants.class");;
	
	
	private static String propertiesPah ="\\config\\config.properties";
	
	public static Properties properties = new Properties();
	static{
		try{
			properties = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
		}
		catch (Exception e) {
			log.error("加载配置文件失败:"+e.getMessage());
			// TODO: handle exception
		}
	}
	
	/***香港信达诺部门ID列表**/
	public static Set<String> DEPT_ID_HK =  new HashSet<String>();//"65A90F40-CF91-4F4F-B5B0-43DC62C273D9";
	static {
		DEPT_ID_HK.add("65A90F40-CF91-4F4F-B5B0-43DC62C273D9");
		DEPT_ID_HK.add("15A8C7BD-C300-48DC-B56E-F8107DFD36B7");//香港信达诺业务五部
		DEPT_ID_HK.add("3748F4D9-B06D-484F-9738-7DA420351696");//香港信达诺铁矿二部
		DEPT_ID_HK.add("0A7036C1-CE0F-4570-B8D4-A7B1FC624B1B");//香港信达诺金属部
		DEPT_ID_HK.add("60F7A977-3856-4DBA-952F-B53428B76C18");//香港信达诺业务一部
		DEPT_ID_HK.add("32C818D1-2962-48ED-9988-424A04701ED4");//香港信达诺业务九部
		DEPT_ID_HK.add("21D30737-1D16-40B1-9D27-81E5F4E9A59B");//香港信达诺化工部
		DEPT_ID_HK.add("16EA8CC7-76BE-4EC0-97B6-595DABE79EB6");//香港信达诺十部
		DEPT_ID_HK.add("5CB16C3B-C30A-49F2-AAF8-416872E4A8B3");//香港信达诺农化部
		DEPT_ID_HK.add("20EA495C-8D1D-4468-AF48-BAF87D6F7AAD");//香港信达诺能源部
		DEPT_ID_HK.add("FB911F98-6118-41D8-8D91-E4355C0C6C49");//香港信达诺七部 
	}
	
	/***新加坡部门ID列表**/
	public static Set<String> DEPT_ID_SG =  new HashSet<String>();//"65A90F40-CF91-4F4F-B5B0-43DC62C273D9";
	static {
		DEPT_ID_SG.add("7A63789C-69E6-46B7-B52E-A32B25596653");
	}
	
	/***2013股份公司上年度归属母公司净利润5%为1200万CNY  立项净利润超过此值需提交证券部审核*/
	public static Double GFGSSNDGSMGSJLR = 12000000d;
	
	public static String FILE_LOCATION_URL = properties.getProperty("file_location_url");
	
	private static Constants ins = null;
	public static Constants ins(){
		if(ins==null) ins = new Constants();
		return ins;
	}
	
	public String getCurrentUserId(){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		return userContext.getSysUser().getUserId();
	}
	/**
	 *  采购端区分进口、内贸、出口方法
	 *  1：进口 2:内贸 
	 */
	public static String getPurTradeType(String businessType){
		if("1".equals(businessType)||"3".equals(businessType)||"6".equals(businessType)||"9".equals(businessType)||
				"11".equals(businessType)){
			return "1";
		}
		else if("2".equals(businessType)||"4".equals(businessType)||"5".equals(businessType)||"7".equals(businessType)||
				"8".equals(businessType)||"10".equals(businessType)||"12".equals(businessType)){
			return "2";
		}
		else return "";
	}
	/**
	 *  销售端区分进口、内贸、出口方法
	 *  2:内贸 3：出口
	 */
	public static String getSaleTradeType(String businessType){
		if("1".equals(businessType)||"3".equals(businessType)||"6".equals(businessType)||"7".equals(businessType)||
				"8".equals(businessType)||"9".equals(businessType)||"10".equals(businessType)){
			return "2";
		}
		else if("2".equals(businessType)||"4".equals(businessType)||"5".equals(businessType)||"11".equals(businessType)||
				"12".equals(businessType)){
			return "3";
		}
		else return "";
	}
	
	//立项，合同、仓储协议归档部门 //外贸办公室
	public static String FILE_DEPT_NAME_ID="BD975A4C-0DE2-437B-872D-A96A31C6FCA2";
	
	//立项关闭 //贸管
	public static String CLOSE_DEPT_NAME_ID="E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D";
	
	//立项货款利息年利率
	public static String INTEREST_RATE = "7"; 
	
}
