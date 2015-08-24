/*
 * @(#)SessionLogService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年08月18日 10点48分27秒
 *  描　述：创建
 */
package com.infolion.platform.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * <pre>
 * 用户会话记录表(SessionLog)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class XmdpService
{
	/**
	 * 根据用户名判断用户是否在线
	 */
	public double isOnlineForPortlet(String userName,String ipAddres,String sessionId)
	{
		int count = 0;

		try {
			Properties props = PropertiesLoaderUtils.loadAllProperties("config/jdbc.properties");
	        String driver = props.getProperty("jdbc.driverClassName"); 
	        String url = props.getProperty("jdbc.url"); 
	        String user = props.getProperty("jdbc.username"); 
	        String pwd = props.getProperty("jdbc.password"); 
			   
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url, user, pwd);
			
			StringBuffer sql = new StringBuffer("").append("select count(1) count from YSESSIONLOG where LOGOUTTIME='0' and LOGOUTYPE='0' ");
			
			if(!"".equals(userName)) sql.append(" and USERNAME = '"+userName + "'");
			if(!"".equals(ipAddres)) sql.append(" and IPADDRES = '"+ipAddres + "'");
			if(!"".equals(sessionId)) sql.append(" and SESSIONID = '"+sessionId + "'");
			ResultSet rs = conn.createStatement().executeQuery(sql.toString());
			rs.next();
			count = Integer.parseInt(rs.getString("COUNT"));
		} catch (Exception ex) {
			return 0;
		}
		return count;
	}
}