/*
 * @(#)OutsideInterfaceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-9-18
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import java.util.HashSet;
import java.util.Set;

import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;

import test.infolion.platform.core.service.ServiceTest;
import test.infolion.platform.dpframework.outsideinteface.domain.BOneTest;
import test.infolion.platform.dpframework.outsideinteface.domain.BOneTestItem;

/**
 * 
 * <pre>
 * 多线程测试
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
public class OutsideInterfaceTest extends ServiceTest
{
	public void testSave()
	{
		try
		{
			for (int i = 0; i < 3; i++)
			{
				final double d = i;
				new Thread()
				{
					@Override
					public void run()
					{
						for (int j = 0; j < 3; j++)
						{
							try
							{
								// 构造测试数据
								BOneTest purchaseOrder = new BOneTest();
								purchaseOrder.setCardcode("V70000");
								purchaseOrder.setDocdate("20091005");
								purchaseOrder.setDocduedate("20091105");
								purchaseOrder.setId("D001");
								purchaseOrder.setDiscountpercent(d);
								Set<BOneTestItem> set = new HashSet<BOneTestItem>();
								purchaseOrder.setDoclines(set);

								BOneTestItem item = new BOneTestItem();
								item.setId("I001");
								item.setItemcode("A00001");
								item.setDiscountpercent(0.0);
								item.setQuantity(3.0);
								item.setUnitprice(15.0);
								item.setVatgroup("J0");
								item.setWarehousecode("01");
								item.setPriceaftervat(20.0);
								set.add(item);

								item = new BOneTestItem();
								item.setId("I002");
								item.setItemcode("A00002");
								item.setDiscountpercent(0.0);
								item.setQuantity(4.0);
								item.setUnitprice(16.0);
								item.setVatgroup("J0");
								item.setWarehousecode("01");
								item.setPriceaftervat(19.2);
								set.add(item);
								System.out.println("加载测试数据完成");
								// 调用外部接口服务
								OutsidePersistenceService.save(purchaseOrder);
							}
							catch (Exception e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}.start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			Object obj;
			synchronized (obj = new Object())
			{
				obj.wait();
			}
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

}
