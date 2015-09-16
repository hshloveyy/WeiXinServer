package com.hy.wxserver.web.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hy.wxserver.web.pojo.IpInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * Message entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hy.wxserver.web.pojo.Message
 * @author MyEclipse Persistence Tools
 */
public class IPInfoDao extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(IPInfoDao.class);
	
//	/* (non-Javadoc)
//	 * @see com.hy.wxserver.web.dao.IMessageDao#save(com.hy.wxserver.web.pojo.Message)
//	 */
//	public void save(IpInfo transientInstance) {
//		log.debug("saving Message instance");
//		try {
//			getHibernateTemplate().save(transientInstance);
//			log.debug("save successful");
//		} catch (RuntimeException re) {
//			log.error("save failed", re);
//			throw re;
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see com.hy.wxserver.web.dao.IMessageDao#delete(com.hy.wxserver.web.pojo.Message)
//	 */
//	public void delete(IpInfo persistentInstance) {
//		log.debug("deleting Message instance");
//		try {
//			getHibernateTemplate().delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see com.hy.wxserver.web.dao.IMessageDao#findById(java.lang.String)
//	 */
//	public IpInfo findById(String msgId) {
//		log.debug("getting Message instance with id: " + msgId);
//		try {
//			return getHibernateTemplate().get(IpInfo.class, msgId);
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see com.hy.wxserver.web.dao.IMessageDao#findAll()
//	 */
	public List<IpInfo> findAll() {
		log.debug("finding all Message instances");
		try {
			String queryString = "from IpInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List<IpInfo> findByIP(final String ip) {
		log.debug("finding all Message instances");
		try {
			return getHibernateTemplate().execute(
					new HibernateCallback<List<IpInfo>>() {
						@Override
						public List<IpInfo> doInHibernate(Session arg0)
								throws HibernateException, SQLException {
							List<IpInfo> list = new ArrayList<IpInfo>();
							SQLQuery query = arg0.createSQLQuery("select t.* from ipdb t "
									+ "where substring_index(t.ip_start, '.', 1) = substring_index(?, '.', 1) "
									+ "and substring_index(substring_index(t.ip_start, '.', -3), '.', 1) = substring_index(substring_index(?, '.', -3), '.', 1) "
									+ "and substring_index(substring_index(t.ip_start, '.', -2), '.', 1) <= substring_index(substring_index(?, '.', -2), '.', 1) "
									+ "and substring_index(substring_index(t.ip_end, '.', -2), '.', 1) >= substring_index(substring_index(?, '.', -2), '.', 1)");
							List<Object[]> objects = query.setString(0, ip).setString(1, ip).setString(2, ip).setString(3, ip).list();
							for (Object[] obj : objects) {
								IpInfo ipInfo = new IpInfo(Integer.valueOf(String.valueOf(obj[0])), String.valueOf(obj[1]), String.valueOf(obj[2]), String.valueOf(obj[3]), String.valueOf(obj[4]));
								list.add(ipInfo);
							}
							
							return list;
						}
					});
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}