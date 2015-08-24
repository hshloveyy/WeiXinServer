/*
 * @(#)BaseHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-9
 *  描　述：创建
 */

package com.infolion.platform.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * <pre>
 * 所有Hibernate Dao的基类
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
public class BaseHibernateDao<T> extends HibernateDaoSupport
{
	/**
	 * 操作的领域对象的类型
	 */
	protected Class<T> entityClass;

	protected Log log = (Log) LogFactory.getLog(this.getClass());

	/**
	 * 在构造函数中获取Dao所操作的领域对象类型
	 */
	public BaseHibernateDao()
	{
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	/**
	 * 通过注入注解驱动注入HibernateTemplate
	 * 
	 * @param template
	 */
	@Autowired
	public void initHibernateTemplate(HibernateTemplate template)
	{
		super.setHibernateTemplate(template);
	}

	/**
	 * 调用该方法加载延迟加载的数据
	 * 
	 * @param obj
	 *            需要缓存的对象
	 */
	protected void initialize(Object obj)
	{
		super.getHibernateTemplate().initialize(obj);
	}

	/**
	 * 根据ID加载领域对象
	 * 
	 * @param id
	 *            领域对象的Id
	 * @return 领域对象
	 */
	public T load(Serializable id)
	{
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据ID获取领域对象
	 * 
	 * @param id
	 *            领域对象的Id
	 * @return 领域对象
	 */
	public T get(Serializable id)
	{
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 根据ID获取领域对象游离态(脱管状态)实例。
	 * @param id
	 * @return
	 */
	public T getDetached(Serializable id)
	{
		Object obj = getHibernateTemplate().get(entityClass, id);
		getHibernateTemplate().evict(obj);
		return (T) obj ;
	}
	
	/**
	 * 加载所有领域对象
	 * 
	 * @return
	 */
	public List<T> getAll()
	{
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 保存领域对象
	 * 
	 * @param obj
	 */
	public void save(T obj)
	{
		//getHibernateTemplate().setFlushMode(getHibernateTemplate().FLUSH_AUTO);
		getHibernateTemplate().save(obj);
	}

	/**
	 * 保存或更新领域对象
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(T obj)
	{
		getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * 批量保存
	 * 
	 * @param objList
	 */
	public void saveBatch(List<T> objList)
	{
		if (objList == null || objList.size() == 0)
			return;

		int i = 0;

		for (T obj : objList)
		{

			save(obj);

			/*
			 * 将本批插入的对象立即写入数据库并释放内存 如果要将很多对象持久化，必须通过经常的调用 flush() 以及稍后调用 clear()
			 * 来控制第一级缓存的大小
			 */
			if (++i % 20 == 0)
			{
				flush();
				clear();
			}
		}
	}

	/**
	 * 删除领域对象
	 * 
	 * @param obj
	 */
	public void remove(T obj)
	{
		getHibernateTemplate().delete(obj);
	}

	/**
	 * 更改领域对象
	 * 
	 * @param obj
	 */
	public void update(T obj)
	{
		getHibernateTemplate().update(obj);
	}

	/**
	 * 批量更新业务对象
	 * 
	 * @param objList
	 */
	public void updateBatch(List<T> objList)
	{

		if (objList == null || objList.size() == 0)
			return;

		int i = 0;

		for (T obj : objList)
		{

			update(obj);

			/*
			 * 将本批插入的对象立即写入数据库并释放内存 如果要将很多对象持久化，必须通过经常的调用 flush() 以及稍后调用 clear()
			 * 来控制第一级缓存的大小
			 */
			if (++i % 20 == 0)
			{
				flush();
				clear();
			}
		}
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param hql
	 * @return
	 */
	public List find(String hql)
	{
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List find(String hql, Object... params)
	{
		return this.getHibernateTemplate().find(hql, params);
	}

	public void flush()
	{
		this.getHibernateTemplate().flush();
	}

	public void clear()
	{
		this.getHibernateTemplate().clear();
	}
}
