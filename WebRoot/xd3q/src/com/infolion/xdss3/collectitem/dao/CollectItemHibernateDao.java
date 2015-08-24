/*
 * @(#)CollectItemHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月12日 07点01分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectitem.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitemGen.dao.CollectItemHibernateDaoGen;


/**
 * <pre>
 * 收款金额分配(CollectItem),HibernateDao 操作类
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
public class CollectItemHibernateDao extends CollectItemHibernateDaoGen<CollectItem>
{
	/**
	 * 取得未清预收票款
	 * @param id
	 * @return
	 */
	public List getUnclearPreBillCollectList(String collectitemids)
	{		
		String hql = "from CollectItem where collectitemid in (:ids) and  ( isclear = '0' or trim(isclear) is null ) ";
		Query query = getSession().createQuery(hql); 
		query.setParameterList("ids", collectitemids.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 更新收款分配表 预付票结清标识
	 * @param id
	 * @return
	 */
	public void clearCollectItem(String collectitemid){
		String hql = "update CollectItem set isclear = '1' where collectitemid = :id";
		Query query= getSession().createQuery(hql); 
		query.setParameter("id",collectitemid);
		query.executeUpdate();
	}
}