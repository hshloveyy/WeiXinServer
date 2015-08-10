package com.hy.wxserver.web.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hy.wxserver.web.pojo.ServiceMenu;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServiceMenu entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hy.wxserver.web.pojo.ServiceMenu
 * @author MyEclipse Persistence Tools
 */
public class ServiceMenuDAO extends HibernateDaoSupport implements IServiceMenuDao {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceMenuDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String CODE_DESC = "codeDesc";
	public static final String ORDER = "order";
	public static final String MSG_ID = "msgId";

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IServiceMenuDao#save(com.hy.wxserver.web.pojo.ServiceMenu)
	 */
	public void save(ServiceMenu transientInstance) {
		log.debug("saving ServiceMenu instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IServiceMenuDao#delete(com.hy.wxserver.web.pojo.ServiceMenu)
	 */
	public void delete(ServiceMenu persistentInstance) {
		log.debug("deleting ServiceMenu instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IServiceMenuDao#findById(java.lang.Integer)
	 */
	public ServiceMenu findById(Integer id) {
		log.debug("getting ServiceMenu instance with id: " + id);
		try {
			ServiceMenu instance = (ServiceMenu) getSession().get(ServiceMenu.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IServiceMenuDao#findAll()
	 */
	public List<ServiceMenu> findAll() {
		log.debug("finding all ServiceMenu instances");
		try {
			String queryString = "from ServiceMenu";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}