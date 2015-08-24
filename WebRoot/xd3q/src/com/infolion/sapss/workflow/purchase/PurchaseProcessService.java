/*
 * @(#)PurchaseProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.purchase;

import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.bapi.domain.TBapiLogDetail;
import com.infolion.sapss.bapi.service.BapiHibernateService;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.contract.service.ContractService;
import com.infolion.sapss.contract.service.PurchaseChangeJdbcService;
import com.infolion.sapss.contract.service.SaleChangeJdbcService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 采购订单流程回调服务
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
public class PurchaseProcessService extends BaseHibernateService {
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "采购订单申请通过");
		ContractService service = (ContractService) EasyApplicationContextUtils
				.getBeanByName("contractService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 申请未通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "采购订单申请未通过");
		ContractService service = (ContractService) EasyApplicationContextUtils
				.getBeanByName("contractService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
	/**
	 * 未经领导审批
	 * @param businessRecordId
	 * @param processId
	 */
	public void processNoExamine(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "未经领导审批");
		ContractService service = (ContractService) EasyApplicationContextUtils
				.getBeanByName("contractService");
		ProcessCallBack callBack = (ProcessCallBack) service;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.NO_EXAMINE, null);
	}
	/**
	 * 写入SAP订单
	 * @param businessRecordId
	 * @return
	 */
	public String writeSapOrder(String businessRecordId)
	{
		ContractHibernateService orderService = (ContractHibernateService)EasyApplicationContextUtils.getBeanByName("contractHibernateService");
		BapiHibernateService service = (BapiHibernateService)EasyApplicationContextUtils.getBeanByName("bapiHibernateService");
		String result = "yes";
		TContractPurchaseInfo info = orderService.queryPurchase(businessRecordId);
		/**代理业务的采购订单不写入SAP系统*/
		if("5".equals(info.getTradeType())||"6".equals(info.getTradeType())){
			return result;
		}
		TBapiLog bapiLog = service.insertPurcharseOrder(info);
		Set<TBapiLogDetail> logDetails = bapiLog.getTBapiLogDetails();
		for (Iterator iterator = logDetails.iterator(); iterator.hasNext();) {
			TBapiLogDetail bapiLogDetail = (TBapiLogDetail) iterator.next();
			if("E".equals(bapiLogDetail.getLogType())||"A".equals(bapiLogDetail.getLogType())){
				result="no";
				break;
			}
		}
		//回填SAP订单号
		/*if(result.equals("yes")){
			info.setSapOrderNo(bapiLog.getContractNo());
			orderService.updateContractPurcharseSapOrderNo(info);
		}*/
		return result;
	}
	/**
	 * 变更流程通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processChangeSucessful(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"采购合同变更申请通过");
		PurchaseChangeJdbcService project = (PurchaseChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("purchaseChangeJdbcService");
		project.copyFile(businessRecordId);
		project.updateBusinessRecord(businessRecordId,	ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}
	/**
	 * 变更流程不通过
	 * @param businessRecordId
	 * @param processId
	 */
	public void processChangeFailure(String businessRecordId,long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId,"采购合同变更申请未通过");
		PurchaseChangeJdbcService project = (PurchaseChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("purchaseChangeJdbcService");
		project.updateBusinessRecord(businessRecordId,ProcessCallBack.EXAMINE_FAILE, null);
	}
	/**
	 * 未经领导审批写入SAP变更
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processChangeNoExamine(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "未经领导审批写入SAP变更");
		PurchaseChangeJdbcService purchase = (PurchaseChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("purchaseChangeJdbcService");
		purchase.copyFile(businessRecordId);
		purchase.updateBusinessRecord(businessRecordId,	ProcessCallBack.NO_EXAMINE, null);
		
	}
	
}
