/*
 * @(#)BillInCollectHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月12日 07点53分34秒
 *  描　述：创建
 */
package com.infolion.xdss3.billincollect.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.infolion.xdss3.billincollect.domain.BillInCollect;
import com.infolion.xdss3.billincollectGen.dao.BillInCollectHibernateDaoGen;
import com.infolion.xdss3.voucher.domain.Voucher;


/**
 * <pre>
 * 发票收款关系(BillInCollect),HibernateDao 操作类
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
public class BillInCollectHibernateDao extends BillInCollectHibernateDaoGen<BillInCollect>
{
	public List<Voucher> getVoucherIdList(String collectitemids)
	{		
		String hql = "from Voucher where businessid in (:ids) and voucherclass='4'";
		Query query = getSession().createQuery(hql); 
		query.setParameterList("ids", collectitemids.split(",")); 
		return query.list(); 
	}

	public String getVoucherIdByCollectId(String collectid) {
		String hql = "from Voucher where businessid = ? and voucherclass='1'";
		Query query = getSession().createQuery(hql); 
		query.setParameter(0, collectid);
		List<Voucher> result = query.list();
		if(result != null && result.size() > 0)
			return result.get(0).getVoucherno();
		else
			return "";
	}
}