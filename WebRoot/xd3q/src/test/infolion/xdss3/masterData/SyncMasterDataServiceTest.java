/*
 * @(#)SyncMasterDataServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-25
 *  描　述：创建
 */

package test.infolion.xdss3.masterData;

import org.junit.Test;

import test.infolion.platform.core.service.HibernateServiceTest;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.xdss3.masterData.service.SyncMasterDataService;

/**
 * 
 * <pre>
 * 服务测试类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SyncMasterDataServiceTest extends HibernateServiceTest
{
	@Test
	public void testGetOutsideInterface()
	{
		UserContext userContext = new UserContext();
		userContext.setClient("800");
		UserContextHolder.setCurrentContext(userContext);

		SyncMasterDataService syncMasterDataService = (SyncMasterDataService) EasyApplicationContextUtils.getBeanByName("syncMasterDataService");
		syncMasterDataService._synchronizeCustomer();
		syncMasterDataService._synchronizeHkont();
		syncMasterDataService._synchronizeCashFlowItem();
		syncMasterDataService._synchronizeCostCenter();
		syncMasterDataService._synchronizePrctr();
		syncMasterDataService._synchronizeVbrk();
		syncMasterDataService._synchronizeRseg();
	}

	@Test
	public void BBtestInvoke()
	{
		UserContext userContext = new UserContext();
		userContext.setClient("800");
		UserContextHolder.setCurrentContext(userContext);

		SyncMasterDataService syncMasterDataService = (SyncMasterDataService) EasyApplicationContextUtils.getBeanByName("syncMasterDataService");
		syncMasterDataService.invoke("_synchronizeSupplier");

		// try
		// {
		// syncMasterDataService.getClass().getMethod("_synchronizeSupplier").invoke(syncMasterDataService);
		// }
		// catch (IllegalArgumentException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (SecurityException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (IllegalAccessException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (InvocationTargetException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (NoSuchMethodException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}
}
