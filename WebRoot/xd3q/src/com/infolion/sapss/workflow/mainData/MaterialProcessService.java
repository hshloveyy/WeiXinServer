/*
 * @(#)ProjectProcessService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-20
 *  描　述：创建
 */

package com.infolion.sapss.workflow.mainData;

import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.export.service.BaleLoanService;
import com.infolion.sapss.export.service.ExportService;
import com.infolion.sapss.mainData.service.MaterialHibernateService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 物料主数据审批流程推进回调服务
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张小军
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class MaterialProcessService extends BaseHibernateService
{
	/**
	 * 物料主数据流程审批通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBaleLoanSucessful(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请通过");
		MaterialHibernateService materialHibernateService = (MaterialHibernateService) EasyApplicationContextUtils
			.getBeanByName("materialHibernateService");
		materialHibernateService.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_SUCCESSFUL, null);
	}

	/**
	 * 物料主数据流程审批不通过
	 * 
	 * @param businessRecordId
	 * @param processId
	 */
	public void processBaleLoanFaile(String businessRecordId, long processId)
	{
		WorkflowService.updateProcessInstanceEndState(processId, "申请未通过");
		MaterialHibernateService materialHibernateService = (MaterialHibernateService) EasyApplicationContextUtils
				.getBeanByName("materialHibernateService");
		ProcessCallBack callBack = (ProcessCallBack) materialHibernateService;
		callBack.updateBusinessRecord(businessRecordId,
				ProcessCallBack.EXAMINE_FAILE, null);
	}
}
