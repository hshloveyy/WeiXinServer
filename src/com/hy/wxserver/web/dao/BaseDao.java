/*
 * �ļ�����BaseDao.java
 * ��Ȩ��Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * ������KURRENTϵͳϵ��
 */
package com.hy.wxserver.web.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hy.wxserver.web.pojo.Message;

/**
 * �޸��ˣ� Heshaohua
 * �޸�ʱ�䣺2015��8��12�� ����1:53:17 
 * �޸����ݣ����� 
 * ��˵����
 */

public class BaseDao<T extends Object> extends HibernateDaoSupport{

	private static final Logger log = LoggerFactory.getLogger(BaseDao.class);
	

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#save(com.hy.wxserver.web.pojo.Message)
	 */
	public void save(T transientInstance) {
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
	public void delete(T persistentInstance) {
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
	@SuppressWarnings("unchecked")
	public T findById(T t, String msgId) {
		log.debug("getting Message instance with id: " + msgId);
		try {
			return (T) getHibernateTemplate().get(t.getClass(), msgId);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.dao.IMessageDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
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
