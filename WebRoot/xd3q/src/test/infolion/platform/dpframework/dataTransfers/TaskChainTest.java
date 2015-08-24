/*
 * @(#)TaskChainTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-1-14
 *  描　述：创建
 */

package test.infolion.platform.dpframework.dataTransfers;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.infolion.platform.dpframework.dataTransfers.dbOperation.TaskChainBuilder;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

public class TaskChainTest
{
	static DataSource createDataSource(String driverClass, String url, String userName, String password)
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	public static void main(String args[])
	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/context/dataTransfer.xml");
		EasyApplicationContextUtils.setApplicationContext(ctx);
		DataSource targetDataSource = createDataSource("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.39.203:1527:nw1", "sapsr3", "ffcs1234");
		Object obj = TaskChainBuilder.buildTaskChain("notSapDataTransfer", targetDataSource);
		System.out.println(obj);
	}
}
