/*
 * @(#)CommonOpenSessionInViewFilter.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-19
 *  描　述：创建
 */

package com.infolion.platform.sys;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * 
 * <pre>
 * 自定义OpenSessionInViewFilter，事务有些问题，待问题解决后不用此扩展类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CommonOpenSessionInViewFilter extends OpenSessionInViewFilter {

	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory) {
//		session.flush();
		super.closeSession(session, sessionFactory);
	}

	@Override
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		// 手动设置事务提交方式
		session.setFlushMode(FlushMode.COMMIT);
		return session;
	}

}
