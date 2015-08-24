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
import com.infolion.sapss.export.dao.BaleLoanHibernateDao;
import com.infolion.sapss.export.dao.BaleLoanJdbcDao;
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.payment.domain.TPickListInfo;
import com.infolion.sapss.ship.dao.ShipInfoJdbcDao;
import com.infolion.sapss.workflow.ProcessCallBack;

;
@Service
public class BaleLoanService extends BaseHibernateService implements
		ProcessCallBack
{
	@Autowired
	private BaleLoanHibernateDao baleLoanHibernateDao;
	@Autowired
	private BaleLoanJdbcDao baleLoanJdbcDao;

	public void setBaleLoanHibernateDao(
			BaleLoanHibernateDao baleLoanHibernateDao)
	{
		this.baleLoanHibernateDao = baleLoanHibernateDao;
	}

	public void setBaleLoanJdbcDao(BaleLoanJdbcDao baleLoanJdbcDao)
	{
		this.baleLoanJdbcDao = baleLoanJdbcDao;
	}

	/**
	 * 获取打包贷款编号
	 * 
	 * @return
	 */
	public String getBaleLoanNo()
	{
		return this.baleLoanJdbcDao.getBaleLoanNo();
	}

	/**
	 * 保存或更新
	 * 
	 * @param info
	 */
	public void saveOrUpdate(TBaleLoan tBaleLoan)
	{
		this.baleLoanHibernateDao.saveOrUpdate(tBaleLoan);

	}

	/**
	 * 保存
	 * 
	 * @param tBaleLoan
	 */
	public void save(TBaleLoan tBaleLoan)
	{
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tBaleLoan.setCreator(context.getSysUser().getUserId());
		tBaleLoan.setDeptId(context.getSysDept().getDeptid());
		tBaleLoan.setCreatorTime(DateUtils.getCurrTime(2));
		this.baleLoanHibernateDao.saveOrUpdate(tBaleLoan);
	}

	public String verifyFilds(String taskId, TBaleLoan tBaleLoan)
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
			Double realMoney = tBaleLoan.getRealMoney();
			if (realMoney == null)
			{
				rtn = "请资金部人员填写实际金额！";
				return rtn;
			}
			this.saveOrUpdate(tBaleLoan);
		}
		return rtn;
	}

	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void submit(String taskId, TBaleLoan tBaleLoan)
	{
		tBaleLoan.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("export_lend_v1"));	
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "打包贷款编号："
				+ tBaleLoan.getBaleLoanNo();
		tBaleLoan.setWorkflowBusinessNote(workflowBusinessNote);
		Map parameters = new HashMap();
		parameters.put("type", "1");
		tBaleLoan.setWorkflowUserDefineTaskVariable(parameters);
		if (StringUtils.isEmpty(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tBaleLoan, tBaleLoan
					.getBaleLoanId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tBaleLoan, tBaleLoan
					.getBaleLoanId(), null);
		}
	}

	/**
	 * 保存并提交
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void saveAndSubmit(String taskId, TBaleLoan tBaleLoan)
	{
		this.save(tBaleLoan);
		this.submit(taskId, tBaleLoan);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		TBaleLoan tBaleLoan = this.baleLoanHibernateDao.get(id);
		tBaleLoan.setIsAvailable("0");
		this.baleLoanHibernateDao.update(tBaleLoan);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 */
	public TBaleLoan find(String id)
	{
		return this.baleLoanHibernateDao.get(id);

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
	 * 组装打包贷款查询条件
	 * 
	 * @param request
	 * @return
	 */
	public String querySQL(HttpServletRequest request)
	{
		Map<String, String> filter = extractFR(request);
		String creditNo = filter.get("creditNo");
		String createBank = filter.get("createBank");
		String invoice = filter.get("invoice");
		String contractNo = filter.get("contractNo");
		String deptId = filter.get("deptId");
		String startDate = filter.get("startDate");
		String endDate = filter.get("endDate");
		String sql = "select t1.*,t1.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE, t2.trade_type,t2.credit_no,t2.contract_no,t2.Create_Or_Rec,t2.credit_rec_date,t2.create_bank,"
				+ "t2.invoice,t3.dept_name,t4.user_name"
				+ " from t_bale_loan t1 inner join  t_credit_info t2 on t1.credit_id = t2.credit_id"
				+ " left outer join t_sys_dept t3 on t1.dept_id = t3.dept_id"
				+ " left outer join t_sys_user t4 on t1.creator = t4.user_id"
				+ " where t1.is_available = '1'";
		if (StringUtils.isNotBlank(creditNo))
		{
			sql += " and t2.credit_no like '%" + creditNo + "%'";
		}
		if (StringUtils.isNotBlank(createBank))
		{
			sql += " and t2.create_bank like '%" + createBank + "%'";
		}
		if (StringUtils.isNotBlank(invoice))
		{
			sql += " and t2.invoice like '%" + invoice + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql += " and t2.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(startDate))
		{
			sql += " and t2.credit_rec_date >= '" + startDate + "'";
		}
		if (StringUtils.isNotBlank(endDate))
		{
			sql += " and t2.credit_rec_date <= '" + endDate + "'";
		}
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 部门的过滤
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
		TBaleLoan tBaleLoan = this.baleLoanHibernateDao.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			tBaleLoan.setExamineState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			tBaleLoan.setExamineState("4");
		}
		tBaleLoan.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tBaleLoan);
	}
}
