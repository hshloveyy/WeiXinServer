package com.infolion.sapss.interestBill.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.EnhanceMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.interestBill.domain.TInterestBill;
import com.infolion.sapss.interestBill.service.InterestBillService;

public class InterestBillController extends EnhanceMultiActionController{
	
	@Autowired
	private InterestBillService interestBillService;

	public void setInterestBillService(InterestBillService interestBillService) {
		this.interestBillService = interestBillService;
	}
	
	
	public ModelAndView manageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/interestBill/interestBillManage");
	}
	
	public ModelAndView commonQueryView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/interestBill/interestBillCommonQuery");
	}
	
	
	public void query(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
	
		Map<String, String> map = extractFR(request);

		String grid_sql = getQuerySql(map);
		String grid_columns = "INTEREST_BILL_ID,PROJECT_NO,PROJECT_ID,INTEREST_BILL_NO,DEPT_ID,DEPT_NAME,UNIT_NAME,UNIT_NO," +
				"UNIT_TYPE,BILL_PARTY_NO,APPLY_DATE,SAP_RETURN_NO,TAX_NO,MAKE_DATE,PAPER_NO,RECEIPT_DATE,VALUE,MEMO,APPLY_TIME," +
				"APPROVED_TIME,CREATE_TIME,EXAM_STATE,CREATOR,IS_AVAILABLE,EXAMINE_STATE_D_EXAMINE_STATE";
		String grid_size = "10";

		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public String getQuerySql(Map<String, String> map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Select t.*,t.EXAM_STATE EXAMINE_STATE_D_EXAMINE_STATE" +
				" From t_interest_bill T" +
				" Where T.Is_Available='1'");	
		// 开票申请单号
		if (StringUtils.isNotBlank(map.get("interestBillNo")))
			sb.append(" and t.interest_Bill_No like '%" + map.get("interestBillNo") + "%'");
		// 部门名称
		if (StringUtils.isNotBlank(map.get("dept")))
			sb.append(" and t.DEPT_ID like '%" + map.get("dept") + "%'");
		// 
		if (StringUtils.isNotBlank(map.get("projectNo")))
			sb.append(" and t.project_no like '%" + map.get("projectNo") + "%'");
		if (StringUtils.isNotBlank(map.get("sapReturnNo")))
			sb.append(" and t.SAP_RETURN_NO like '%" + map.get("sapReturnNo") + "%'");
		
		if (StringUtils.isNotBlank(map.get("taxNo")))
			sb.append(" and t.tax_no like '%" + map.get("taxNo") + "%'");

		// 申报日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.APPLY_TIME >= '" + map.get("sDate") + "'");
		//申报日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.APPLY_TIME <= '" + map.get("eDate") + "'");
		if (StringUtils.isNotBlank(map.get("unitName")))
			sb.append(" and t.unit_name like '%" + map.get("unitName") + "%'");
		if (StringUtils.isNotBlank(map.get("unitNo")))
			sb.append(" and t.unit_no like '%" + map.get("unitNo") + "%'");
		if (StringUtils.isNotBlank(map.get("paperNo")))
			sb.append(" and t.paper_no like '%" + map.get("paperNo") + "%'");
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		sb.append(" and t.dept_id in ("+ userContext.getGrantedDepartmentsId() + ",");
		sb.append("'" + userContext.getSysDept().getDeptid() + "')");

		sb.append(" Order By t.CREATE_TIME Desc");
		return sb.toString();
	}
	
	public ModelAndView newCreateView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String operType = "101";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("deptId", userContext.getSysDept().getDeptid());
		request.setAttribute("deptName", userContext.getSysDept().getDeptname());
		request.setAttribute("editable", true);
		return new ModelAndView("sapss/interestBill/interestBillCreate");
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		String interestBillId = (String) request.getParameter("interestBillId");
		TInterestBill bill = interestBillService.getInterestBill(interestBillId);
		bill.setIsAvailable("0");
		interestBillService.saveOrUpdateInterestBill(bill);
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
	    ExceptionPostHandle.generateInfoMessage(response, "删除成功");
    }
	
	public ModelAndView interestBillView(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("interestBillId");
		TInterestBill bill = interestBillService.getInterestBill(id);
		request.setAttribute("main", bill);
		
		String operType = request.getParameter("operType");
		if(operType==null) operType="001";
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		request.setAttribute("tabable", true);
		request.setAttribute("editable", false);
		return new ModelAndView("sapss/interestBill/interestBillCreate");
	}
	
	public ModelAndView updateInterestBillView(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("interestBillId");
		TInterestBill bill = interestBillService.getInterestBill(id);
		request.setAttribute("main", bill);
		String operType = request.getParameter("operType");
		request.setAttribute("save", operType.substring(0, 1).equals("0"));
		request.setAttribute("submit", operType.substring(1, 2).equals("0"));
		request.setAttribute("close", operType.substring(2, 3).equals("0"));
		request.setAttribute("tabable", true);
		request.setAttribute("editable", true);
		return new ModelAndView("sapss/interestBill/interestBillCreate");
	}
	
	public ModelAndView updateView(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("sapss/bill/billApplyCreate");
	}
	
	public void saveBillApply(HttpServletRequest request,
			HttpServletResponse response, TInterestBill tBillApply)
			throws IOException {
		String msg = interestBillService.checkValue(tBillApply.getUnitNo(),tBillApply.getValue().doubleValue());
		if(msg!=null) throw new BusinessException(msg);
		UserContext userContext = getUserContext();
		if(tBillApply.getInterestBillId()==null||"".equals(tBillApply.getInterestBillId())){
			tBillApply.setCreateTime(DateUtils.getCurrDate(1));
			tBillApply.setCreator(userContext.getSysUser().getUserId());
			tBillApply.setIsAvailable("1"); // 可用标识
			tBillApply.setExamState("1"); // 新增标识
			tBillApply.setInterestBillNo(interestBillService.getInterestBillNo());
			tBillApply.setInterestBillId(CodeGenerator.getUUID());
		}
		
		interestBillService.saveOrUpdateInterestBill(tBillApply);
		
		response.getWriter().print(	"{success:true,interestBillId:'" + tBillApply.getInterestBillId() + 
				"',createTime:'"+tBillApply.getCreateTime()+
				"',creator:'"+tBillApply.getCreator()+
				"',isAvailable:'"+tBillApply.getIsAvailable()+
				"',examState:'"+tBillApply.getExamState()+
				"',interestBillNo:'"+tBillApply.getInterestBillNo()+"'}");
	}
	
	public void submitAndSaveBillApply(HttpServletRequest request,
			HttpServletResponse response, TInterestBill  bill)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		interestBillService.submitAndSaveBillApply(taskId, bill);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	
	public ModelAndView entryExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String billApplyId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");

		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		
		String isPrintHidden = "true";
		
		request.setAttribute("isPrintHidden", isPrintHidden);
		request.setAttribute("businessRecordId", billApplyId);
		request.setAttribute("taskId", taskId);

		request.setAttribute("businessRecordId", billApplyId);
		TInterestBill bill = this.interestBillService.getInterestBill(billApplyId);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String operType = "000";
		// 创建者能修改：
		if (bill.getCreator()
				.equals(userContext.getSysUser().getUserId())&&taskInstanceContext.getTaskName().equals("修改")) {
			operType = "100";
			request.setAttribute("iframeSrc",
					"interestBillController.spr?action=updateInterestBillView"
							+"&operate=iframe&interestBillId="
							+ billApplyId + "&operType=" + operType);
		}
		else {
			request.setAttribute("iframeSrc",
					"interestBillController.spr?action=interestBillView"
							+"&operate=iframe&interestBillId="
							+ billApplyId + "&operType=" + operType);
		}
		
		request.setAttribute("submitUrl",
				"interestBillController.spr?action=submitWorkflow");
		return new ModelAndView("sapss/interestBill/interestBillProcessPage");
	}
	
	public void submitWorkflow(HttpServletRequest request,
			HttpServletResponse response, TInterestBill tBillApply)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");

		String rtn = this.interestBillService.verifyFilds(taskId,tBillApply);
		if (!"".equals(rtn)) {
			throw new BusinessException(rtn);
		}
		String doWorkflow = request.getParameter("doWorkflow");
		if (doWorkflow == null && !"mainForm".equals(doWorkflow)) {
			String workflowLeaveTransitionName = request
					.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			tBillApply.setWorkflowExamine(workflowExamine);
			tBillApply
					.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		// 取得工作流节点名称
		JSONObject jo = new JSONObject();
		interestBillService.submitBillApply(taskId,tBillApply);
		jo.put("returnMessage", "提交审批成功！");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	

}
