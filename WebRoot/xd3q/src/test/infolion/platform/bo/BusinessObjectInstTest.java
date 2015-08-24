/*
 * @(#)BusinessObjectInstTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-12-4
 *  描　述：创建
 */

package test.infolion.platform.bo;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

import test.infolion.platform.core.service.ServiceTest;

public class BusinessObjectInstTest extends ServiceTest
{

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testGetBoInstance()
	{
		BusinessObject businessObject = BusinessObjectService.getBusinessObject("KnowledgeBase");
		BusinessObjectService bos = (BusinessObjectService) EasyApplicationContextUtils.getBeanByName("businessObjectService");
		System.out.println(bos.getBoInstanceUseJdbc(businessObject, "4028a750255796ff0125579885c20002"));
	}

}
