/*
 * @(#)AdvanceWarnObjectHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分36秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;
import com.infolion.xdss3.advanceWarnGen.dao.AdvanceWarnObjectHibernateDaoGen;


/**
 * <pre>
 * 预警对像配置(AdvanceWarnObject),HibernateDao 操作类
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
public class AdvanceWarnObjectHibernateDao extends AdvanceWarnObjectHibernateDaoGen<AdvanceWarnObject>
{		
	public String getClearType(String warnid)
	{
		String hql = "from AdvanceWarnObject advanceWarnObject where warnid = ? ";
		List warnObjectList = this.find(hql, warnid);
		return ((AdvanceWarnObject)warnObjectList.get(0)).getCleartype();
	}
}