/*
 * @(#)SysInitializer.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */
package com.infolion.platform.sys;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.infolion.platform.sys.cache.core.SysCachePoolXd;
import com.infolion.platform.util.EasyApplicationContextUtils;

/**
 * 
 * <pre>
 * 平台启动时调用该类，完成初始化工作
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
 @Component
public class SysInitializerXd implements ApplicationContextAware
{
	private Log log = LogFactory.getLog(this.getClass());
	private ApplicationContext ctx;

	// @Autowired
	// private DataSource dataSource;
	@Autowired
	private SysCachePoolXd sysCachePoolXd;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.ctx = applicationContext;
	}

	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }
	@PostConstruct
	private void init()
	{
		log.debug("初始化平台工具类EasyApplicationContextUtils.");
		EasyApplicationContextUtils.setApplicationContext(this.ctx);

		// 将DataSource设置到DBConnectionUtils中。
		// System.out.println("初始化平台工具类DBConnectionUtils.");
		// DBConnectionUtils.setDataSource(this.dataSource);
		// System.out.println("初始化平台缓存池.");
		sysCachePoolXd.init();
		log.info("平台初始化成功.");
	}
}
