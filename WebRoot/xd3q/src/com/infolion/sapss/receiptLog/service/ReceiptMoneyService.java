/*
 * @(#)ExportIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.receiptLog.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.receiptLog.dao.ReceiptMoneyHibernateDao;
import com.infolion.sapss.receiptLog.domain.TReceiptMoneyInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class ReceiptMoneyService extends  BaseHibernateService{
	@Autowired
	private ReceiptMoneyHibernateDao receiptMoneyHibernateDao;
	
	public void setReceiptMoneyHibernateDao(
			ReceiptMoneyHibernateDao receiptMoneyHibernateDao) {
		this.receiptMoneyHibernateDao = receiptMoneyHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TReceiptMoneyInfo info) {
		this.receiptMoneyHibernateDao.saveOrUpdate(info);
		
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		TReceiptMoneyInfo info = this.receiptMoneyHibernateDao.get(id);
		info.setIsAvailable("0");
		this.receiptMoneyHibernateDao.update(info);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TReceiptMoneyInfo find(String id) {
		return this.receiptMoneyHibernateDao.get(id);
		
	}

	public String querySQL(HttpServletRequest request) {
		UserContext usercontext = UserContextHolder.getLocalUserContext().getUserContext();
		StringBuffer sb = new StringBuffer();
		sb.append("select t.*,t.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE from t_receipt_money_info t where t.is_available=1 ");
		if(StringUtils.isNotEmpty(request.getParameter("contractNo")))
			sb.append(" and t.contract_no='"+request.getParameter("contractNo")+"'");
		if(StringUtils.isNotEmpty(request.getParameter("receiptDate")))
			sb.append(" and t.receipt_date='"+request.getParameter("receiptDate")+"'");
		if(StringUtils.isNotEmpty(request.getParameter("tradeType")))
			sb.append(" and t.trade_type='"+request.getParameter("tradeType")+"'");
		sb.append(" and t.dept_id='"+usercontext.getSysDept().getDeptid()+"'");
		sb.append(" order by create_time desc");
		return sb.toString();
	}
	
	public void submitAndSaveInfo(String taskId,
			TReceiptMoneyInfo info)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		info.setExamineState("2");
		info.setApplyTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(info);

		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|收款审核";

		info.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitReceiptInfo(taskId, info);
	}	
	public void submitReceiptInfo(String taskId, TReceiptMoneyInfo info)
	{
		info.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("receiver_v1"));
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(info,
					info.getInfoId());
		}
		else
		{
			WorkflowService.signalProcessInstance(info, info
					.getInfoId(), null);
		}
	}

	public JSONObject submitWorkflow(TReceiptMoneyInfo info, String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";

		info.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("receiver_v1"));

		try {
			
			submitReceiptInfo(taskId, info);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"
					+ e.getMessage();
			e.printStackTrace();
		}

		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		return jo;
	}
	
	
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(examineResult))
		{
			examineState = "5";
		}
		else
		{
			examineState = "4";
		}
		TReceiptMoneyInfo info = this.find(businessRecordId);
		info.setExamineState(examineState);
		info.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveOrUpdate(info);
	}
	

}
