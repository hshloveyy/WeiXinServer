/*
 * @(#)ProjectProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.export;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.export.service.BaleLoanService;
import com.infolion.sapss.export.service.DiscountService;
import com.infolion.sapss.export.service.NegotiationService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.project.service.ProjectJdbcService;

import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.export.service.ExportService;

/**
 * 
 * <pre>
 * 贴现或议付、出口押汇、打包贷款审批流程推进回调服务
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
public class ExportProcessService extends BaseHibernateService
{
	/**
	 * 打包贷款流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBaleLoanSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		BaleLoanService baleLoanService = (BaleLoanService) EasyApplicationContextUtils
				.getBeanByName("baleLoanService");
		ProcessCallBack callBack = (ProcessCallBack) baleLoanService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 打包贷款流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBaleLoanFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		BaleLoanService baleLoanService = (BaleLoanService) EasyApplicationContextUtils
				.getBeanByName("baleLoanService");
		ProcessCallBack callBack = (ProcessCallBack) baleLoanService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
	
	/**
	 * 贴现流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processDiscountSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		DiscountService discountService = (DiscountService) EasyApplicationContextUtils
				.getBeanByName("discountService");
		ProcessCallBack callBack = (ProcessCallBack) discountService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 贴现流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processDiscountFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		DiscountService discountService = (DiscountService) EasyApplicationContextUtils
				.getBeanByName("discountService");
		ProcessCallBack callBack = (ProcessCallBack) discountService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}	
	
	/**
	 * 议付流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processNegotiationSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		NegotiationService negotiationService = (NegotiationService) EasyApplicationContextUtils
				.getBeanByName("negotiationService");
		ProcessCallBack callBack = (ProcessCallBack) negotiationService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 议付流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processNegotiationFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		NegotiationService negotiationService = (NegotiationService) EasyApplicationContextUtils
				.getBeanByName("negotiationService");
		ProcessCallBack callBack = (ProcessCallBack) negotiationService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}		
	
	/**
	 * 出口押汇流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processExportBillsSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		ExportService exportService = (ExportService) EasyApplicationContextUtils
			.getBeanByName("exportService");
		exportService.updateExportBillsBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 出口押汇流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processExportBillsFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		ExportService exportService = (ExportService) EasyApplicationContextUtils
				.getBeanByName("exportService");
		exportService.updateExportBillsBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
