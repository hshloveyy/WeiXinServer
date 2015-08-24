/*
 * @(#)SysParameterCacheManager.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infolion.platform.console.sysparamter.domain.SysParameter;
import com.infolion.platform.console.sysparamter.service.SysParameterService;

/**
 * 
 * <pre>
 * 系统参数配置缓存管理器
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
public class SysParameterCacheManagerXd extends CacheItemManagerAdapter
{

	@Autowired
	private SysParameterService sysParameterService;

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 初始化系统参数
	 */
	public void init()
	{
		log.debug("开始初始化信达一期系统参数...");
		Map<String, SysParameter> sysParamMap = sysParameterService.getAllSysParameters();

		log.debug("done.");
	}

	/**
	 * 刷新系统配置参数
	 */
	public void refreshAll()
	{
		init();
	}

	public void setSysParameterService(SysParameterService sysParameterService)
	{
		this.sysParameterService = sysParameterService;
	}

}
