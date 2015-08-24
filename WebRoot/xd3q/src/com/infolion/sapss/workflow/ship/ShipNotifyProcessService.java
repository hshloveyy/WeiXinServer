/*
 * @(#)SalesProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.ship;

import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.bapi.domain.TBapiLogDetail;
import com.infolion.sapss.bapi.service.BapiHibernateService;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.contract.service.ContractSaleService;
import com.infolion.sapss.contract.service.PurchaseChangeJdbcService;
import com.infolion.sapss.contract.service.SaleChangeJdbcService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.ship.service.ShipNotifyHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 出口货物通知单流程推进回调服务
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
@Service
public class ShipNotifyProcessService extends BaseHibernateService
{
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId,String writeTo)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "出口货物通知单申请通过");
		ShipNotifyHibernateService shipNotifyHibernateService = (ShipNotifyHibernateService) EasyApplicationContextUtils
				.getBeanByName("shipNotifyHibernateService");
		shipNotifyHibernateService.updateStateAndWriteTo(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, writeTo);
	}

	/**
	 * 申请未通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId,String writeTo)
	{
		WorkflowService
				.updateProcessInstanceEndState(processId, "出口货物通知单申请不通过");
		ShipNotifyHibernateService shipNotifyHibernateService = (ShipNotifyHibernateService) EasyApplicationContextUtils
				.getBeanByName("shipNotifyHibernateService");
		shipNotifyHibernateService.updateFailExportApplyMateriel(new String[]{businessRecordId});
		shipNotifyHibernateService.updateStateAndWriteTo(businessRecordId, ProcessCallBack.EXAMINE_FAILE, writeTo);
	}

}
