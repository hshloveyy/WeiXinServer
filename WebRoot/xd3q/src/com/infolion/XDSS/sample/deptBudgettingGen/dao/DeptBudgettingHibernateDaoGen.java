/*
 * @(#)DeptBudgettingHibernateDaoGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月12日 11点34分35秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptBudgettingGen.dao;

import java.util.List;
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;


/**
 * <pre>
 * 部门预算编制(DeptBudgetting),HibernateDao 操作类
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
public class DeptBudgettingHibernateDaoGen<DeptBudgetting> extends BaseHibernateDao<DeptBudgetting>
{
 
 
	 
 
 
 
 
	/**
	 * 根据方法参数,取得部门预算编制实例
 	 * @param budorgid
 	 * @param ayear
	 * @return 部门预算编制实例
	 */
     public DeptBudgetting _view(
String budorgid,
String ayear
)
{
List<DeptBudgetting> deptBudgettings = this.getHibernateTemplate().find("from DeptBudgetting where "
+ " budorgid=? and "
+ " ayear=? "
,new String[] {
budorgid,
ayear
});		if (null!= deptBudgettings  && deptBudgettings.size() > 0)
			return deptBudgettings.get(0);
		else
			return null;
     }
	 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
}