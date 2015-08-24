/*
 * @(#)BusinessObjectInstTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-12-4
 *  描　述：创建
 */

package test.infolion.platform.calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.workflow.CalActivityTriggerWorkFlowService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.sample.orderManage.purchaseOrder.service.PurchaseOrderService;

import test.infolion.platform.core.service.ServiceTest;

public class CalActivityTriggerWorkFlowServiceTest extends ServiceTest
{

	@Autowired
	private CalActivityTriggerWorkFlowService calActivityTriggerWorkFlowService;

	public void setCalActivityTriggerWorkFlowService(
			CalActivityTriggerWorkFlowService calActivityTriggerWorkFlowService) {
		this.calActivityTriggerWorkFlowService = calActivityTriggerWorkFlowService;
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testGetBoInstance()
	{
		calActivityTriggerWorkFlowService.tiggerWorkFlow();
	}

}
