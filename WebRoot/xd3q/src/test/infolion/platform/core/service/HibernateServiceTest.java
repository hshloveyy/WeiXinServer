/*
 * @(#)HibernateServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-13
 *  描　述：创建
 */

package test.infolion.platform.core.service;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * <pre>
 * 基于Hibernate的service测试基类
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
public class HibernateServiceTest extends ServiceTest
{
	protected HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

}
