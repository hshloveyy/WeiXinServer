/*
 * @(#)ExportIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林哲文
 *  时　间：2009-6-10
 *  描　述：创建
 */

package com.infolion.sapss.export.service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.export.dao.NegotiationHibernateDao;
import com.infolion.sapss.export.dao.NegotiationJdbcDao;
import com.infolion.sapss.export.domain.TNegotiation;
import com.infolion.sapss.workflow.ProcessCallBack;

;
@Service
public class NegotiationService extends BaseHibernateService implements
		ProcessCallBack
{
	@Autowired
	private NegotiationHibernateDao negotiationHibernateDao;
	@Autowired
	private NegotiationJdbcDao negotiationJdbcDao;

	public void setNegotiationHibernateDao(
			NegotiationHibernateDao negotiationHibernateDao)
	{
		this.negotiationHibernateDao = negotiationHibernateDao;
	}

	public void setNegotiationJdbcDao(NegotiationJdbcDao negotiationJdbcDao)
	{
		this.negotiationJdbcDao = negotiationJdbcDao;
	}

	/**
	 * 获取贴现编号
	 * 
	 * @return
	 */
	public String getNegotiationNo()
	{
		return this.negotiationJdbcDao.getNegotiationNo();
	}

	/**
	 * 保存或更新
	 * 
	 * @param info
	 */
	public void saveOrUpdate(TNegotiation tNegotiation)
	{
		this.negotiationHibernateDao.saveOrUpdate(tNegotiation);

	}

	/**
	 * 保存
	 * 
	 * @param tNegotiation
	 */
	public void save(TNegotiation tNegotiation)
	{
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tNegotiation.setCreator(context.getSysUser().getUserId());
		tNegotiation.setDeptId(context.getSysDept().getDeptid());
		tNegotiation.setCreatorTime(DateUtils.getCurrTime(2));
		this.negotiationHibernateDao.saveOrUpdate(tNegotiation);
	}

	public String verifyFilds(String taskId, TNegotiation tNegotiation)
	{
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId))
		{
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if (taskName.equals("资金部人员办理并填写实收金额及币别"))
		{
			String realMoney = tNegotiation.getRealMoney();
			if (realMoney == null||"".equals(realMoney))
			{
				rtn = "请资金部人员填写实际金额！";
				return rtn;
			}
			this.saveOrUpdate(tNegotiation);
		}
		return rtn;
	}

	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tNegotiation
	 */
	public void submit(String taskId, TNegotiation tNegotiation)
	{
		tNegotiation.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("export_lend_v1"));		
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "贴现编号："
				+ tNegotiation.getNegotiationNo();
		tNegotiation.setWorkflowBusinessNote(workflowBusinessNote);
		Map parameters = new HashMap();
		parameters.put("type", "3");
		tNegotiation.setWorkflowUserDefineTaskVariable(parameters);
		if (StringUtils.isEmpty(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tNegotiation,
					tNegotiation.getNegotiationId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tNegotiation, tNegotiation
					.getNegotiationId(), null);
		}
	}

	/**
	 * 保存并提交
	 * 
	 * @param taskId
	 * @param tNegotiation
	 */
	public void saveAndSubmit(String taskId, TNegotiation tNegotiation)
	{
		this.save(tNegotiation);
		this.submit(taskId, tNegotiation);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		TNegotiation tNegotiation = this.negotiationHibernateDao.get(id);
		tNegotiation.setIsAvailable("0");
		this.negotiationHibernateDao.update(tNegotiation);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 */
	public TNegotiation find(String id)
	{
		return this.negotiationHibernateDao.get(id);

	}
	
	private Map<String, String> extractFR(HttpServletRequest req)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++)
			{
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		}
		catch (UnsupportedEncodingException e)
		{}
		return Collections.EMPTY_MAP;
	}
	/**
	 * 组装议付查询条件
	 * 
	 * @param request
	 * @return
	 */
	public String querySQL(HttpServletRequest request)
	{
		Map<String, String> filter = extractFR(request);		
		String projectNo = filter.get("projectNo");
		String contractGroupNo = filter.get("contractGroupNo");
		String contractNo = filter.get("contractNo");
		String contractName = filter.get("contractName");
		String deptId = filter.get("deptId");
		String startDate = filter.get("startDate");
		String endDate = filter.get("endDate");
		String sql = "select t1.*,t1.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE, t2.trade_type,t3.project_id,t4.dept_name,"
				+ "t6.project_no,t6.project_name,t3.contract_group_name,t3.contract_group_no,t2.contract_name,t2.contract_no "
				+ "from t_negotiation t1 inner join t_contract_sales_info t2 on t1.contract_sales_id = t2.contract_sales_id"
				+ " inner join t_contract_group t3 on t2.contract_group_id = t3.contract_group_id"
				+ " left outer join t_sys_dept t4 on t1.dept_id = t4.dept_id"
				+ " left outer join t_sys_user t5 on t1.creator = t5.user_id"
				+ " inner join t_project_info t6 on t2.project_id = t6.project_id"
				+ " where t1.is_available = '1'";
		if (StringUtils.isNotBlank(projectNo))
		{
			sql += " and t6.project_no like '%" + projectNo + "%'";
		}
		if (StringUtils.isNotBlank(contractGroupNo))
		{
			sql += " and t3.contract_group_no like '%" + contractGroupNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql += " and t2.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql += " and t2.contract_no like '%" + contractNo + "%'";
		}
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if ("1".equals(userContext.getSysDept().getIsFuncDept()))
		{
			if (StringUtils.isNotBlank(deptId))
			{
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++)
				{
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				sql += " and t1.dept_id in (" + deptId + ")";
			}
			sql += " and t1.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ")";
		}
		else
		{
			sql += " and t1.dept_id = '" + userContext.getSysDept().getDeptid()
					+ "'";
		}
		sql += " order by t1.creator_time desc";		
		return sql;
	}

	/**
	 * 流程审批状态更新
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		TNegotiation tNegotiation = this.negotiationHibernateDao
				.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			tNegotiation.setExamineState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			tNegotiation.setExamineState("4");
		}
		tNegotiation.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tNegotiation);
	}
}
