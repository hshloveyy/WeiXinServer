package com.infolion.sapss.project.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseHibernateDao;
import com.infolion.sapss.project.domain.TProjectInfo;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class ProjectInfoHibernate extends BaseHibernateDao<TProjectInfo>{
	public void evict(TProjectInfo o){
		this.getHibernateTemplate().evict(o);
	}
}
