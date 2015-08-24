/*
 * @(#)SampleServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-10-13
 *  描　述：创建
 */

package test.infolion.platform.sample.service;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.sample.orderManage.purchaseOrder.domain.PurchaseOrder;
import com.infolion.sample.orderManage.purchaseOrder.service.PurchaseOrderService;

/**
 * 
 * <pre>
 * 示例测试服务类
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
public class SampleServiceTest extends JdbcServiceTest
{
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	/**
	 * @param purchaseOrderService
	 *            the purchaseOrderService to set
	 */
	public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService)
	{
		this.purchaseOrderService = purchaseOrderService;
	}

	public void testSave()
	{
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setAddress("a");
		purchaseOrder.setCertificateDate("2009-01-01");
		purchaseOrder.setCertificateType("c");
		purchaseOrder.setClient("800");
		purchaseOrder.setCreateTime("a");
		purchaseOrder.setCreator("a");
		purchaseOrder.setLastModifyor("a");
		purchaseOrder.setLastModifyTime("a");
		purchaseOrder.setMemo("a");
		purchaseOrder.setProcessState("a");
		purchaseOrder.setPurchaseOrderNo("a");
		purchaseOrder.setSalespeople("a");
		purchaseOrder.setSupplierNo("a");
		// Address address = new Address();
		// address.setClient("800");
		// address.setAddresstype("a");
		// address.setArea("aaaaddd");
		// address.setAddress(" ");
		// address.setCity("aaa");
		// address.setCountry("accc");
		// address.setEmail("cccc");
		// address.setExtnumber("yyyy");
		// address.setFax("nnnn");
		// address.setHomephone("bbbb");
		// address.setMobilephone("aaa");
		// address.setOperationType("ddd");
		// address.setQq_msn("ssss");
		// address.setPhone("vvvv");
		// address.setZipcode("cccc");
		// address.setWebsite("cccc");
		// purchaseOrder.setAddressObj(address);
		purchaseOrderService._save(purchaseOrder);
		// this.applyService.submitApply(new Apply());
	}

}
