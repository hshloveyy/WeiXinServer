/*
 * @(#)BillInPaymentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月23日 14点36分02秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.xdss3.billClear.dao.BillInPaymentJdbcDao;
import com.infolion.xdss3.billClearGen.service.BillInPaymentServiceGen;

/**
 * <pre>
 * 未清预付款表(BillInPayment)服务类
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
public class BillInPaymentService extends BillInPaymentServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private BillInPaymentJdbcDao billInPaymentJdbcDao;

	/**
	 * @param billInPaymentJdbcDao
	 *            the billInPaymentJdbcDao to set
	 */
	public void setBillInPaymentJdbcDao(BillInPaymentJdbcDao billInPaymentJdbcDao)
	{
		this.billInPaymentJdbcDao = billInPaymentJdbcDao;
	}
}