/*
 * @(#)ServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-13
 *  描　述：创建
 */

package test.infolion.platform.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;

/**
 * 
 * <pre>
 * Service测试类基类.
 *   自动支持测试方法事务回滚，一般情况下仅在测试方法中验证数据的合法性，尽量避免向数据库
 *   提交数据，保持数据库现场的可恢复性。子类可以覆盖onSetUpBeforeTransaction()和
 *   onTearDownAfterTransaction()方法，初始化数据，清理数据。
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
public abstract class ServiceTest extends
		AbstractAnnotationAwareTransactionalTests
{
	protected Log log = LogFactory.getLog(this.getClass());

	public ServiceTest()
	{
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);
	}

	@Override
	public final String[] getConfigLocations()
	{
		List<String> configs = new ArrayList<String>();
		configs.add("testContext/applicationContext.xml");
		configs.addAll(customConfigLocatioins());
		String[] configLocations = new String[configs.size()];
		int i = 0;
		for (String temp : configs)
		{
			configLocations[i++] = temp;
		}
		return configLocations;
	}

	/**
	 * 子类通过复写该方法添加新的Spring配置文件
	 * 
	 * @return
	 */
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}

	/**
	 * 清理缓存，同步到数据库中。
	 */
	protected abstract void flush();
}
