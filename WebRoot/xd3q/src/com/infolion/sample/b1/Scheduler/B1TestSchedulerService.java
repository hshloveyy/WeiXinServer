/*
 * @(#)SalesDocItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 15点38分57秒
 *  描　述：创建
 */
package com.infolion.sample.b1.Scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class B1TestSchedulerService extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	/**
	 * 
	 */
	public void doAction()
	{
		log.debug("B1TEST调度开始:" + DateUtils.getCurrTimeStr(DateUtils.CN_DISPLAY_DATE));

		log.debug("--------DO !");

		log.debug("B1TEST调度结束:" + DateUtils.getCurrTimeStr(DateUtils.CN_DISPLAY_DATE));

	}
}