/*
 * @(#)ImportPaymentAdvanceService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 19点04分12秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.exservice;

import com.infolion.platform.dpframework.core.service.AdvanceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 进口付款(ImportPayment)扩展服务类
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
public class ImportPaymentAdvanceService implements AdvanceService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	public void postDelete(Object domainObject)
	{
		System.out.println("----扩展服务----删除后执行 ");
	}

	public void postSaveOrUpdate(Object domainObject)
	{
		System.out.println("----扩展服务----保存后执行 ");
	}

	public void preDelete(Object domainObject)
	{
		System.out.println("----扩展服务----删除前执行 ");
	}

	public void preSaveOrUpdate(Object domainObject)
	{
		System.out.println("----扩展服务----保存前执行 ");
	}
}