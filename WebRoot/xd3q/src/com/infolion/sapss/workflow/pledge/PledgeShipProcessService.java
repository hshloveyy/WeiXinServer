/*
 * @(#)SalesProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.pledge;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
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
import com.infolion.sapss.pledge.service.PledgeShipHibernateService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.service.ShipHibernateService;
import com.infolion.sapss.ship.service.ShipNotifyHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 出仓单流程推进回调服务
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
public class PledgeShipProcessService extends BaseHibernateService
{
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		WorkflowService.updateProcessInstanceEndState(processId, "更新出仓单状态为通过");
		PledgeShipHibernateService pledgeShipHibernateService = (PledgeShipHibernateService) EasyApplicationContextUtils
				.getBeanByName("pledgeShipHibernateService");
		pledgeShipHibernateService.updateBusinessRecord(businessRecordId, ProcessCallBack.EXAMINE_SUCCESSFUL, null);;

	}

	/**
	 * 申请未通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "更新出仓单状态为不通过");
		PledgeShipHibernateService pledgeShipHibernateService = (PledgeShipHibernateService) EasyApplicationContextUtils
				.getBeanByName("pledgeShipHibernateService");
		pledgeShipHibernateService.updateBusinessRecord(businessRecordId,ProcessCallBack.EXAMINE_FAILE, null);
	}

}
