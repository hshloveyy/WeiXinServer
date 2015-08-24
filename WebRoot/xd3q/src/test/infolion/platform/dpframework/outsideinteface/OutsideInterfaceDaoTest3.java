/*
 * @(#)OutsideInterfaceDaoTest3.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-11-26
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import java.util.HashSet;

import org.junit.Test;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.sample.orderManage.purchaseOrder.domain.OrderItems;
import com.infolion.sample.orderManage.purchaseOrder.domain.PurchaseOrder;

public class OutsideInterfaceDaoTest3 extends ServiceTest
{

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	@Test
	public void testB1DaoGet()
	{
		// A1Dao a1Dao = new A1Dao();
		// BusinessObject boInst =
		// BusinessObjectService.getBusinessObject("KnowledgeBase");
		// po.setClient("800");
		// po.setAddress("2000");
		// po.setMemo("hello");
		// a1Dao.save(po, "_save");
		for (int i = 0; i < 5; i++)
		{
			PurchaseOrder po = new PurchaseOrder();
			po.setPurchaseOrderNo("1");
			OutsidePersistenceService.get(po);
			System.out.println("client:" + po.getClient());
			System.out.println("address:" + po.getAddress());
			System.out.println("purchaseOrderNo:" + po.getPurchaseOrderNo());
			System.out.println("supplierNo:" + po.getSupplierNo());
			System.out.println("lastModifyTime:" + po.getLastModifyTime());
			if (po.getOrderItems() != null)
			{
				for (OrderItems oi : po.getOrderItems())
				{
					System.out.println("orderItem:");
					System.out.println("MaterielNo:" + oi.getMaterielNo());
					System.out.println("Quantity:" + oi.getQuantity());
				}
			}
		}
	}

	@Test
	public void testB1DaoSave()
	{
		PurchaseOrder po = new PurchaseOrder();
		po.setSupplierNo("V10000");
		po.setLastModifyTime("20091211");
		OrderItems oi = new OrderItems();
		oi.setMaterielNo("A00001");
		// oi.setQuantity(new BigDecimal(5));
		HashSet<OrderItems> set = new HashSet<OrderItems>();
		set.add(oi);
		po.setOrderItems(set);

		OutsidePersistenceService.save(po);
		System.out.println("PurchaseOrderNo:" + po.getPurchaseOrderNo());
	}

}
