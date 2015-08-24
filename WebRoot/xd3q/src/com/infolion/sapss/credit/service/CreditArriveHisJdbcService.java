/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.credit.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.credit.dao.CreditArriveHibernateDao;
import com.infolion.sapss.credit.dao.CreditArriveHisJdbcDao;
import com.infolion.sapss.credit.dao.CreditArriveJdbcDao;
import com.infolion.sapss.credit.dao.CreditEntryHisJdbcDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.common.WorkflowUtils;

/**
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class CreditArriveHisJdbcService extends BaseJdbcService implements
		ProcessCallBack
{

	@Autowired
	private CreditArriveHisJdbcDao creditArriveHisJdbcDao;

	public void setCreditArriveJdbcDao(CreditArriveHisJdbcDao creditArriveHisJdbcDao)
	{
		this.creditArriveHisJdbcDao = creditArriveHisJdbcDao;
	}

	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	
	@Autowired
	private CreditArriveHibernateService creditArriveHibernateService;

	public void setCreditArriveHibernateService(CreditArriveHibernateService creditArriveService)
	{
		this.creditArriveHibernateService = creditArriveService;
	}
	
	@Autowired
	private CreditEntryHisJdbcDao creditEntryHisJdbcDao;

	public void setCreditEntryHisJdbcDao(CreditEntryHisJdbcDao creditHisEntryJdbcDao)
	{
		this.creditEntryHisJdbcDao = creditHisEntryJdbcDao;
	}
	
	@Autowired
	private CreditArriveHibernateDao creditArriveHibernateDao;

	public void setCreditArriveHibernate(
			CreditArriveHibernateDao creditArriveHibernateDao) {
		this.creditArriveHibernateDao = creditArriveHibernateDao;
	}
	
	/**
	 * 提交流程
	 * 
	 * @param creditInfo
	 * @return
	 */
	public void submitWorkflow(String taskId, TCreditHisInfo creditHisInfo)
	{
		
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(creditHisInfo, creditHisInfo.getCreditHisId(), creditHisInfo.getDeptId());
			//WorkflowService.createAndSignalProcessInstance(creditHisInfo, creditHisInfo.getCreditHisId());
			this.creditEntryHisJdbcDao.submitUpdateState(creditHisInfo.getCreditId(), creditHisInfo.getCreditHisId());
		}
		else
		{
			WorkflowService.signalProcessInstance(creditHisInfo, creditHisInfo.getCreditHisId(), null);
		}
	}

	/**
	 * 提交审批流程
	 * 
	 * @param creditInfo
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(TCreditHisInfo creditHisInfo, String taskId)
	{
		JSONObject jo = new JSONObject();
		String msg = "";
		try
		{
			submitWorkflow(taskId, creditHisInfo);
		}
		catch (Exception e)
		{
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:" + e.getMessage();
			e.printStackTrace();
		}

		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		return jo;
	}

	/**
	 * 回调更新信用证开证改证 状态 状态:1、信用证开证 2、信用证收证 3、审批中
	 * 4、生效5、备用6、过期7、撤销8、关闭9、作废10、改证11、改证通过 12、改正不通过
	 */
	public void updateBusinessRecord(String businessRecordId, String examineResult, String creditState)
	{
		String result = "";
		String creditId="";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			result = creditState;
		}
		
		this.creditArriveHisJdbcDao.workflowUpdateState(businessRecordId, examineResult, creditState);

		// 如果改正通过则更新改证数据到信用证主信息表。
		if ("5".equals(result)||"15".equals(result)||"11".equals(result))
		{
			// LJX 20090416 Modify 开始执行变更信息。 从信用证改证历史表 更新到 信用证主表。
			String oldContractId = "";
			String contractId = "";
			boolean isUpadate = false;
			
			TCreditInfo creditInfo = new TCreditInfo();// this.creditEntryHibernateDao.get(creditId)
			TCreditHisInfo creditHisInfo = this.creditHisInfoHibernateDao.get(businessRecordId);
			
			oldContractId = this.creditEntryHisJdbcDao.getoldContractId(creditHisInfo.getCreditId());
			contractId = creditHisInfo.getContractId();
			BeanUtils.copyProperties(creditHisInfo, creditInfo);
			this.creditArriveHibernateDao.update(creditInfo);
			
			if(oldContractId==null)
			{
				oldContractId="";
			}
			if(contractId==null)
			{
				contractId="";
			}
			
			if(contractId.equals(oldContractId)==false)
			{
				isUpadate = true;
			}
			
			if(isUpadate) //是否要更新 信用证合同关联表
			{
				int n = 0;
				String[] contractIdLst;
				String creator = creditInfo.getCreator();
				contractIdLst = contractId.split(",");
				n = contractIdLst.length;
				creditId = creditInfo.getCreditId();
				
				//System.out.println("oldContractId:" + oldContractId );
				//System.out.println("newContractId:" + contractId );
				
				//信用证收证改证。
				// 删除信用证收证表数据，重新插入数据
				this.creditArriveHibernateService.updateCreditRec(creditId, creator, n, contractIdLst);
				//System.out.println("信用证收证改证，审批通过后，重新更新信用证收证表数据成功！");
			}
   
		}
	}
	
	
	/**
	 * 审批时候，初始化各参数，并根据汇率计算出金额,同时传流程参数，做金额判断。
	 * 
	 * @param creditInfo
	 * @param doWorkflow
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 * @return
	 */
	public void initSubmitWorkflowBycreditArriveChange(TCreditHisInfo creditHisInfo, String doWorkflow, String workflowLeaveTransitionName, String workflowExamine, String creditState)
	{
		creditHisInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		creditHisInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + creditHisInfo.getProjectNo() + "|" + creditHisInfo.getProjectName());

		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			creditHisInfo.setWorkflowExamine(workflowExamine);
			creditHisInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		
		Map workflowUserDefineTaskVariable = new HashMap();
		workflowUserDefineTaskVariable.put("_workflow_credit_state", creditState);
		creditHisInfo.setWorkflowUserDefineTaskVariable(workflowUserDefineTaskVariable);
		
		//creditHisInfo.setWorkflowModelName("信用证到证改证申请");
//		creditHisInfo.setWorkflowProcessName("credit_receive_modify_v1");
		creditHisInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("credit_receive_modify_v1")); 
		/**流程新增信用证类型 modify by cat 20131114*/
		String wfModelName = "信用证到证改证申请";
		if("3".equals(Constants.getSaleTradeType(creditHisInfo.getTradeType()))) wfModelName = "出口信用证到证改证申请";
		else if ("2".equals(Constants.getSaleTradeType(creditHisInfo.getTradeType()))) wfModelName = "国内信用证到证改证申请";
		creditHisInfo.setWorkflowModelName(wfModelName);
		/***modify by cat 20131114**/
		creditHisInfo.setWorkflowProcessUrl("creditArriveHisController.spr?action=creditArriveExamine");

	}
	
	
	public String verifyFilds(TCreditHisInfo creditHisInfo, String taskId,String creditState,String workflowLeaveTransitionName)
	{
		String taskName;
		String exceptionMsg = "";
		// 取得工作流节点名称
		if (taskId != null || "".equals(taskId) != false) 
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
	        taskName = taskInstanceContext.getTaskName();
			if("信用证收证修改".equals(taskName))
			{
				if (creditHisInfo.getBenefit() == null || "".equals(creditHisInfo.getBenefit()))
				{
					exceptionMsg = "[受益人]为必填！";
				}
				else if(org.apache.commons.lang.StringUtils.isBlank(creditHisInfo.getCreditNo()))
				{
					exceptionMsg = "[信用证号]为必填！";	
				}
			}
			else if("贸管人员更改信用证状态".equals(taskName))
			{
//				if (creditHisInfo.getCreditState() == null || "".equals(creditHisInfo.getCreditState()))
//				{
//					exceptionMsg = "请贸管人员更改信用证状态！";
//				}
		
				if (("3".equals(creditState) || (creditState == null) || "".equals(creditState)) && "状态已更新".equals(workflowLeaveTransitionName))
				{
					exceptionMsg = "请贸管人员更改信用证状态！";
				}	
			}
		
		}
		
		return exceptionMsg;
	}
}
