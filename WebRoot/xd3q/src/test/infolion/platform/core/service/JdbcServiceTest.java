/*
 * @(#)JdbcServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-13
 *  描　述：创建
 */

package test.infolion.platform.core.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * <pre>
 * 基于spring jdbc的服务类的测试基类
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
public class JdbcServiceTest extends ServiceTest
{
	protected JdbcTemplate template;

	/**
	 * 覆盖父类onSetUpBeforeTransaction方法，注入JdbcTemplate
	 */
	@Override
	protected void onSetUpBeforeTransaction() throws Exception
	{
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource((DataSource) applicationContext
				.getBean("dataSource"));
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub
	}

}
