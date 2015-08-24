/*
 * @(#)SalesProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.sales;

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
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 销售订单流程推进回调服务
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
public class SalesProcessService extends BaseHibernateService {
	/**
	 * 流程通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processSucessful(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "销售合同申请通过");
		ContractSaleService sales = (ContractSaleService) EasyApplicationContextUtils
				.getBeanByName("contractSaleService");
		ProcessCallBack callBack = (ProcessCallBack) sales;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 申请未通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processFaile(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "销售合同申请未通过");
		ContractSaleService sales = (ContractSaleService) EasyApplicationContextUtils
				.getBeanByName("contractSaleService");
		ProcessCallBack callBack = (ProcessCallBack) sales;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}

	/**
	 * 未经领导审批
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processNoExamine(String businessRecordId, long processId) {
		WorkflowService.updateProcessInstanceEndState(processId, "未经领导审批");
		ContractSaleService sales = (ContractSaleService) EasyApplicationContextUtils
				.getBeanByName("contractSaleService");
		ProcessCallBack callBack = (ProcessCallBack) sales;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.NO_EXAMINE, null);
	}

	/**
	 * 写入SAP订单
	 * 
	 * @param businessRecordId
	 * @return
	 */
	public String writeSapOrder(String businessRecordId) {
		ContractHibernateService orderService = (ContractHibernateService) EasyApplicationContextUtils
				.getBeanByName("contractHibernateService");
		BapiHibernateService service = (BapiHibernateService) EasyApplicationContextUtils
				.getBeanByName("bapiHibernateService");
		String result = "yes";
		TContractSalesInfo info = orderService.getContractSalesInfo(businessRecordId);
		TBapiLog bapiLog = service.insertSalesOrder(orderService
				.querySales(businessRecordId));
		Set<TBapiLogDetail> logDetails = bapiLog.getTBapiLogDetails();
		for (Iterator iterator = logDetails.iterator(); iterator.hasNext();) {
			TBapiLogDetail bapiLogDetail = (TBapiLogDetail) iterator.next();
			if ("E".equals(bapiLogDetail.getLogType())||"A".equals(bapiLogDetail.getLogType())) {
				result = "no";
				break;
			}
		}
		//回填SAP订单号
		/*if(result.equals("yes")){
			info.setSapOrderNo(bapiLog.getContractNo());
			orderService.updateContractSalesSapOrderNo(info);
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
		WorkflowService.updateProcessInstanceEndState(processId,"销售合同变更申请通过");
		SaleChangeJdbcService project = (SaleChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("saleChangeJdbcService");
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
		WorkflowService.updateProcessInstanceEndState(processId,"销售合同变更申请未通过");
		SaleChangeJdbcService project = (SaleChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("saleChangeJdbcService");
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
		SaleChangeJdbcService sales = (SaleChangeJdbcService) EasyApplicationContextUtils
				.getBeanByName("saleChangeJdbcService");
		sales.copyFile(businessRecordId);
		sales.updateBusinessRecord(businessRecordId,
				ProcessCallBack.NO_EXAMINE, null);
	}
	
}
