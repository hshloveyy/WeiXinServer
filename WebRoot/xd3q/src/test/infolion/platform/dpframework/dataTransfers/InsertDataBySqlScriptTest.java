/*
 * @(#)SystemMessageServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-7-23
 *  描　述：创建
 */

package test.infolion.platform.dpframework.dataTransfers;

import org.junit.Test;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.dataTransfers.dao.ExecuteSqlScriptDao;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

public class InsertDataBySqlScriptTest extends JdbcServiceTest
{

	@Test
	public void testInsertData()
	{
		ExecuteSqlScriptDao executeSqlScriptDao = (ExecuteSqlScriptDao) EasyApplicationContextUtils.getBeanByName("executeSqlScriptDao");
		executeSqlScriptDao.executeSqlScript("insert.sql");

	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}
}
