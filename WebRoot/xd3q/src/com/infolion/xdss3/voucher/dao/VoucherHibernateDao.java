/*
 * @(#)VoucherHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月30日 06点58分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucher.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucherGen.dao.VoucherHibernateDaoGen;

/**
 * <pre>
 * 凭证预览(Voucher),HibernateDao 操作类
 * 
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
@Repository
public class VoucherHibernateDao extends VoucherHibernateDaoGen<Voucher>
{

	/**
	 * 根据业务ID,凭证分类，取得凭证对象。
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public Voucher getVoucherByBusinessId(String businessId, String voucherclass)
	{
		String hql = "from Voucher where businessid=? and voucherclass=?";
		List<Voucher> list = getHibernateTemplate().find(hql, new Object[] { businessId, voucherclass });
		log.debug("根据业务ID,凭证分类，取得凭证对象:select * from YVOUCHER where businessid='" + businessId + "' and voucherclass='" + voucherclass + "'");
		if (null != list && list.size() == 1)
		{
			return list.get(0);
		}

		return null;
	}

	/**
	 * 根据业务ID，取得凭证对象。 只返回一个凭证
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public Voucher getVoucherByBusinessId(String businessId)
	{
		String hql = "from Voucher where businessid=? ";
		List<Voucher> list = getHibernateTemplate().find(hql, new Object[] { businessId });

		if (null != list && list.size() == 1)
		{
			return list.get(0);
		}

		return null;
	}
	
	/**
	 * 根据业务ID，取得凭证对象LIST。 
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public List<Voucher> getVoucherByBusinessId2(String businessId)
	{
		String hql = "from Voucher where businessid=? ";
		List<Voucher> list = getHibernateTemplate().find(hql, new Object[] { businessId });
		return list;
	}
	
	/**
	 * 根据业务ID，取得凭证对象LIST。 
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public List<Voucher> getVoucherByBusinessId2(String businessId,String bstat)
	{
		String hql = "from Voucher where businessid=? and bstat=?";
		List<Voucher> list = getHibernateTemplate().find(hql, new Object[] { businessId ,bstat});
		return list;
	}
	
}