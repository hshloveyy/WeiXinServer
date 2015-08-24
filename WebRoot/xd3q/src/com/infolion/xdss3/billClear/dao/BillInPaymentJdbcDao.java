/*
 * @(#)BillInPaymentJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月23日 14点36分02秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

/**
 * <pre>
 * 未清预付款表(BillInPayment),JDBC 操作类
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
@Repository
public class BillInPaymentJdbcDao extends BaseJdbcDao
{

	/**
	 * 删除分配(清楚分配)
	 * 
	 * @param billClearId
	 */
	public void deletes(String billClearId)
	{
		String strSql = "delete from YBILLINPAYMENT where BILLCLEARID='" + billClearId + "'";
		this.getJdbcTemplate().execute(strSql);
	}
}