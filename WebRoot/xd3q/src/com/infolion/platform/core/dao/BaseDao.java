/*
 * @(#)BaseDAO.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.core.dao;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * 
 * <pre>
 * 所有使用JDBC DAO的基类
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
public class BaseDao extends SimpleJdbcDaoSupport
{
	/**
	 * 子类均使用log对象作日志
	 */
	protected Log log = (Log) LogFactory.getLog(this.getClass());
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	@Autowired
	public void initJdbcTemplate(JdbcTemplate template)
	{
		DataSource dataSource = SessionFactoryUtils
				.getDataSource(hibernateTemplate.getSessionFactory());
		template.setDataSource(dataSource);
		super.setJdbcTemplate(template);
	}
}
