/*
 * @(#)BoConfigVerifyTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-01-18
 *  描　述：创建
 */

package test.infolion.platform.codeGenerator;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.codeGenerator.service.BoConfigVerifyService;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.sample.purchase.domain.PurchaseInfo;

public class BoConfigVerifyTest extends ServiceTest
{

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void _testAll()
	{
		PurchaseInfo purchaseInfo = (PurchaseInfo) BusinessObjectService.getBoInstance("PurchaseInfo", "4028a74e260d0c3c01260d34a0f20006");
		OutsidePersistenceService.execute(purchaseInfo, "_submitProcess");
	}

	public void testDoVerify()
	{
		BusinessObject businessObject = BusinessObjectService.getBusinessObject("Test");
		BoConfigVerifyService.doVerify(businessObject);
	}
}
