/*
 * @(#)UserMappingHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月05日 04点41分35秒
 *  描　述：创建
 */
package com.infolion.xdss3.usermapping.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.infolion.xdss3.usermapping.domain.UserMapping;
import com.infolion.xdss3.usermappingGen.dao.UserMappingHibernateDaoGen;
import com.infolion.xdss3.voucher.domain.Voucher;


/**
 * <pre>
 * 账户用户关系(UserMapping),HibernateDao 操作类
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
public class UserMappingHibernateDao extends UserMappingHibernateDaoGen<UserMapping>
{
	
	/**
	 * 根据用户名 业务范围，取得SAP账号。
	 * 
	 * @param businessId
	 * @param voucherclass
	 * @return
	 */
	public String getSAPAccount(String username, String depid)
	{
		String hql = "from UserMapping where username=?";
		List<UserMapping> list = getHibernateTemplate().find(hql, new Object[] { username });
		log.debug("根据用户名 业务范围，取得SAP账号:select * from UserMapping where username='" + username + "'");
		if (null != list && list.size() !=0)
		{
			UserMapping userMapping = list.get(0);
			return userMapping.getSapaccount();
		}

		return null;
	}

}