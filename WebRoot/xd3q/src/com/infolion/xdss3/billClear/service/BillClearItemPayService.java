/*
 * @(#)BillClearItemPayService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月22日 15点15分22秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.xdss3.billClear.dao.BillClearItemPayJdbcDao;
import com.infolion.xdss3.billClearGen.service.BillClearItemPayServiceGen;

/**
 * <pre>
 * 票清付款行项目(BillClearItemPay)服务类
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
public class BillClearItemPayService extends BillClearItemPayServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private BillClearItemPayJdbcDao billClearItemPayJdbcDao;

	/**
	 * @param billClearItemPayJdbcDao
	 *            the billClearItemPayJdbcDao to set
	 */
	public void setBillClearItemPayJdbcDao(BillClearItemPayJdbcDao billClearItemPayJdbcDao)
	{
		this.billClearItemPayJdbcDao = billClearItemPayJdbcDao;
	}

}