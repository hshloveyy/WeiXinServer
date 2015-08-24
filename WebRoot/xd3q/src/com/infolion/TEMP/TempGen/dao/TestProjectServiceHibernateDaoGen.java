/*
 * @(#)TestProjectServiceHibernateDaoGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月21日 15点06分42秒
 *  描　述：创建
 */
package com.infolion.TEMP.TempGen.dao;

import java.util.List;
import com.infolion.TEMP.Temp.domain.TestProjectService;
import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;


/**
 * <pre>
 * 项目服务（测试）(TestProjectService),HibernateDao 操作类
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
public class TestProjectServiceHibernateDaoGen<TestProjectService> extends BaseHibernateDao<TestProjectService>
{
 
 
	 
 
 
 
 
	/**
	 * 根据方法参数,取得项目服务（测试）实例
 	 * @param projectid
	 * @return 项目服务（测试）实例
	 */
     public TestProjectService _view(
String projectid
)
{
List<TestProjectService> testProjectServices = this.getHibernateTemplate().find("from TestProjectService where "
+ " projectid=? "
,new String[] {
projectid
});		if (null!= testProjectServices  && testProjectServices.size() > 0)
			return testProjectServices.get(0);
		else
			return null;
     }
	 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
}