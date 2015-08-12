package com.hy.wxserver.web.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hy.wxserver.web.pojo.Message;

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
public class MessageDAO extends HibernateDaoSupport implements IMessageDao {
	private static final Logger log = LoggerFactory.getLogger(MessageDAO.class);
	// property constants
	public static final String MSG_TYPE = "msgType";
	public static final String MSG_BODY = "msgBody";
	

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#save(com.hy.wxserver.web.pojo.Message)
	 */
	public void save(Message transientInstance) {
		log.debug("saving Message instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#delete(com.hy.wxserver.web.pojo.Message)
	 */
	public void delete(Message persistentInstance) {
		log.debug("deleting Message instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#findById(java.lang.String)
	 */
	public Message findById(String msgId) {
		log.debug("getting Message instance with id: " + msgId);
		try {
			return getHibernateTemplate().get(Message.class, msgId);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#findAll()
	 */
	public List<Message> findAll() {
		log.debug("finding all Message instances");
		try {
			String queryString = "from Message";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}