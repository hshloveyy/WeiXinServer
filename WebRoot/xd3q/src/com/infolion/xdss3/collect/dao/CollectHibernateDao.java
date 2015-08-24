/*
 * @(#)CollectHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 07点45分08秒
 *  描　述：创建
 */
package com.infolion.xdss3.collect.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectGen.dao.CollectHibernateDaoGen;
import com.infolion.xdss3.voucher.domain.Voucher;


/**
 * <pre>
 * 收款信息表(Collect),HibernateDao 操作类
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
public class CollectHibernateDao extends CollectHibernateDaoGen<Collect>
{
	@Autowired
	private CollectJdbcDao collectJdbcDao;
	
	public void setCollectJdbcDao(CollectJdbcDao collectJdbcDao) {
		this.collectJdbcDao = collectJdbcDao;
	}
	
	public List<Collect> getCollectListByOldcollectid(String oldcollectid)
	{
		String hql = "from Collect where oldcollectid=?";
		List<Collect> list = getHibernateTemplate().find(hql, new Object[] { oldcollectid });
		log.debug("根据业务ID,凭证分类，取得凭证对象:select * from Collect where oldcollectid='" + oldcollectid + "'");
		return list;
	}
	
	public Collect getCollectByNo(String collectNo){
		String hql = "from Collect c where collectno='" + collectNo + "'";
		List<Collect> list = this.getHibernateTemplate().find(hql);
		return list.get(0);
	}

	/* (non-Javadoc)
	 * @see com.infolion.platform.dpframework.core.dao.BaseHibernateDao#get(java.io.Serializable)
	 */
//	@Override
//	public Collect get(Serializable id) {
//		// TODO Auto-generated method stub		
//		return collectJdbcDao.getCollectByCollectId(id.toString());
//	}
	
}