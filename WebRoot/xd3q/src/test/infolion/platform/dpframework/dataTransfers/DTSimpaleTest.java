/*
 * @(#)Test.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-9-22
 *  描　述：创建
 */

package test.infolion.platform.dpframework.dataTransfers;

import java.io.Reader;
import java.nio.CharBuffer;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.infolion.platform.dpframework.dataTransfers.dao.DataTransfersJdbcDao;
import com.infolion.platform.dpframework.dataTransfers.dbOperation.TableCreater;

public class DTSimpaleTest
{
	public static void main(String[] args)
	{
		try
		{
//			testDao();
			test2();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 源数据库
	 * @return
	 */
	public static JdbcTemplate createJdbcTemplate()
	{
		BasicDataSource dataSource = new BasicDataSource();
		oracle.jdbc.driver.OracleDriver o;
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@192.168.39.204:1527:t11");
		dataSource.setUsername("sapsr3");
		dataSource.setPassword("ffcs1234");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	/**
	 * 目标数据库
	 * @return
	 */
	public static JdbcTemplate createTBJdbcTemplate()
	{
		BasicDataSource dataSource = new BasicDataSource();
		oracle.jdbc.driver.OracleDriver o;
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@192.168.39.158:1521:private");
		dataSource.setUsername("jbpm");
		dataSource.setPassword("jbpm");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	public static DataTransfersJdbcDao createDataTransfersJdbcDao(JdbcTemplate t)
	{
		DataTransfersJdbcDao dao = new DataTransfersJdbcDao();
		dao.initJdbcTemplate(t);
		return dao;
	}
	
	/**
	 * 
	 */
	public static void test2(){
		DataTransfersJdbcDao srcDao=createDataTransfersJdbcDao(createJdbcTemplate());
		DataTransfersJdbcDao destDao=createDataTransfersJdbcDao(createTBJdbcTemplate());
		TableCreater tc=new TableCreater();
		tc.setSrcDao(srcDao);
		tc.setDestDao(destDao);
		tc.createTable("AA");
	}

	public static void test1(){
		JdbcTemplate template = createJdbcTemplate();
		DataTransfersJdbcDao srcDao=createDataTransfersJdbcDao(template);
		DataTransfersJdbcDao destDao=createDataTransfersJdbcDao(createTBJdbcTemplate());
		TableCreater tc=new TableCreater();
		tc.setSrcDao(srcDao);
		tc.setDestDao(destDao);
		List list = template.queryForList("select y.tabname from ybusinessobject y where y.appmodel='CRM'");
		System.out.println(list.size());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map m = (Map) iterator.next();
			String tbName = (String)m.get("tabname");
			System.out.println(tbName);
			tc.createTable(tbName);
		}
//		tc.createTable("yaccounting");
//		tc.createTable("yActivity");
//		

	}

	

	public static void testDao()
	{
		DataTransfersJdbcDao dao = createDataTransfersJdbcDao(createJdbcTemplate());
		// System.out.println(dao.getTableDDL("AA"));
		// List<String> list = dao.getRefTables("AA");
		// if (list != null)
		// {
		// for (String str : list)
		// {
		// System.out.println(str);
		// }
		// }
		// else
		// System.out.println("null");
		// try
		// {
		// getImportedKeys(dao.getDataSource().getConnection().getMetaData());
		// }
		// catch (SQLException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// for(String str:dao.getIndexDDLInTable("AA")){
		// System.out.println(str);
		// }
		String tableName = "AA";
		String ddl = dao.getTableDDL(tableName,"TSC",null);
		List<String> indexDDLs=dao.getIndexDDLInTable(tableName,null);
		System.out.println(ddl);
		for(String str:indexDDLs){
			System.out.println(str);
		}

	}

	public static void getImportedKeys(DatabaseMetaData metaData)
	{
		try
		{
			ResultSet resultSet = metaData.getImportedKeys(null, null, "AA");
			while (resultSet.next())
			{
				// resultSet.toString();
				for (int i = 1; i <= 14; i++)
				{
					System.out.print(i + ":" + resultSet.getObject(i) + ",");
				}
				// System.out.println(" "+resultSet.toString());
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
