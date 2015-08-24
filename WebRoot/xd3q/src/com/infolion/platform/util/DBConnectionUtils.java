/*
 * @(#)DBConnectionUtils.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.util;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * 
 * <pre>
 * 数据连接获取器。在系统初始化时将Spring容器中的DataSource设置给该帮助类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class DBConnectionUtils
{

	private static DataSource dataSource;

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection()
	{
		try
		{
			return dataSource.getConnection();
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置数据源
	 * 
	 * @param ds
	 */
	public static void setDataSource(DataSource ds)
	{
		dataSource = ds;
	}
}
