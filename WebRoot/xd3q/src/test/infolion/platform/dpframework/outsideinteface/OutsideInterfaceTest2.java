/*
 * @(#)OutsideInterfaceTest2.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-9-27
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.outsideInterface.OutsideInterfaceUtils;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.platform.dpframework.outsideInterface.dao.OutsideDaoFactory;

/**
 * 
 * <pre>
 * get方法测试
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 刘俊杰
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class OutsideInterfaceTest2 extends ServiceTest
{

	public void initTest()
	{
		// 构造虚拟数据
		OutsideInterfaceUtils
				.registerBoClassName("BOneTestItem",
						"test.infolion.platform.dpframework.outsideinteface.domain.BOneTestItem");
		OutsideInterfaceUtils
				.registerBoClassName("BOneTest",
						"test.infolion.platform.dpframework.outsideinteface.domain.BOneTest");
	}

	public void testGet()
	{
		initTest();
		Object obj;
		// 销售定单测试
		obj = OutsidePersistenceService.get("SalesOrder", "1",
				OutsideDaoFactory.Type.B1_DAO);
		// 合作火伴测试
		// obj = OutsidePersistenceService.get("Partner", "V10000",
		// OutsideDaoFactory.Type.B1_DAO);
		// 物料测试
		// obj = OutsidePersistenceService.get("MaterielInfo", "A00001",
		// OutsideDaoFactory.Type.B1_DAO);
		System.out.println(obj);
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

}
