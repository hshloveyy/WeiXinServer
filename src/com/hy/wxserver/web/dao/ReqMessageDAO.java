package com.hy.wxserver.web.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hy.wxserver.web.pojo.Message;
import com.hy.wxserver.web.pojo.ReqMessage;

/**
 * A data access object (DAO) providing persistence and search support for
 * ReqMessage entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hy.wxserver.web.pojo.ReqMessage
 * @author MyEclipse Persistence Tools
 */
public class ReqMessageDAO extends HibernateDaoSupport implements IReqMessageDao {
	private static final Logger log = LoggerFactory
			.getLogger(ReqMessageDAO.class);

	// property constants

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IReqMessageDao#save(com.hy.wxserver.web.pojo.ReqMessage)
	 */
	public void save(ReqMessage transientInstance) {
		log.debug("saving ReqMessage instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IReqMessageDao#delete(com.hy.wxserver.web.pojo.ReqMessage)
	 */
	public void delete(ReqMessage persistentInstance) {
		log.debug("deleting ReqMessage instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IReqMessageDao#findById(java.lang.String)
	 */
	public ReqMessage findById(String msgId) {
		log.debug("getting ReqMessage instance with msgId: " + msgId);
		try {
			ReqMessage instance = (ReqMessage) getHibernateTemplate().get(ReqMessage.class, msgId);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IReqMessageDao#findAll()
	 */
	public List<Message> findAll() {
		log.debug("finding all ReqMessage instances");
		try {
			String queryString = "from ReqMessage";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}