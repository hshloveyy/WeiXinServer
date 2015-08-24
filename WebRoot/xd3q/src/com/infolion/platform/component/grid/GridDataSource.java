/*
 * @(#)GridDataSource.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-26
 *  描　述：创建
 */

package com.infolion.platform.component.grid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * 
 * <pre>grid数据获取器</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface GridDataSource {
	public  int countTotalRecords(String sql);
	public  List getRecords(String sql,int start,int limit,String primary);
	
}
