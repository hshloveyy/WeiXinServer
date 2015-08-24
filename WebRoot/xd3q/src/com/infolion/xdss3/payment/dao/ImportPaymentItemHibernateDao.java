/*
 * @(#)ImportPaymentItemHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月05日 09点48分51秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.paymentGen.dao.ImportPaymentItemHibernateDaoGen;

/**
 * <pre>
 * 付款金额分配(ImportPaymentItem),HibernateDao 操作类
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
public class ImportPaymentItemHibernateDao extends ImportPaymentItemHibernateDaoGen<ImportPaymentItem>
{

	/**
	 * 更新收款分配表 预付票结清标识
	 * 
	 * @param id
	 * @return
	 */
	public void updateSettlePaymentItem(String paymentItemId)
	{
		String hql = "update ImportPaymentItem set billisclear = '1' where paymentitemid = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", paymentItemId);
		query.executeUpdate();
	}
}