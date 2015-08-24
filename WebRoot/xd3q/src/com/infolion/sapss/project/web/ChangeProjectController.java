/*
 * @(#)ChangeProjectController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.project.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectChange;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.service.ChangeProjectHibernateService;
import com.infolion.sapss.project.service.ChangeProjectJdbcService;
import com.infolion.sapss.project.service.ProjectHibernateService;
import com.infolion.sapss.project.service.ProjectJdbcService;
public class ChangeProjectController extends BaseMultiActionController {
	@Autowired
	private ChangeProjectHibernateService changeProjectHibernateService;
	@Autowired
	private ChangeProjectJdbcService changeProjectJdbcService;
	@Autowired
	private ProjectHibernateService  projectHibernateService;
	@Autowired
	private ProjectJdbcService projectJdbcService;
	
	public void setProjectJdbcService(ProjectJdbcService projectJdbcService) {
		this.projectJdbcService = projectJdbcService;
	}
	public void setProjectHibernateService(
			ProjectHibernateService projectHibernateService) {
		this.projectHibernateService = projectHibernateService;
	}
	public void setChangeProjectJdbcService(
			ChangeProjectJdbcService changeProjectJdbcService) {
		this.changeProjectJdbcService = changeProjectJdbcService;
	}
	public void setChangeProjectHibernateService(
			ChangeProjectHibernateService changeProjectHibernateService) {
		this.changeProjectHibernateService = changeProjectHibernateService;
	}
	/**
	 * 保存变更信息
	 * @param req
	 * @param resp
	 * @param tc
	 * @throws Exception
	 */
	public void saveChange(HttpServletRequest req,HttpServletResponse resp,TProjectChange tc) throws Exception{
		String projectId = req.getParameter("projectId");
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		tc.setProjectId(projectId);
		tc.setCreator(ut.getSysUser().getUserId());
		tc.setCreateTime(DateUtils.getCurrTimeStr(2));
		tc.setApplyTime(DateUtils.getCurrTimeStr(2));
		tc.setIsAvailable("1");
		tc.setChangeState("1");
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");

		if(tc.getChangeId()==null || "".equals(tc.getChangeId().trim())){
			int no=this.changeProjectJdbcService.getChangeNo(projectId,"4");
			no++;
			tc.setChangeNo(no+"");
			String changeId = changeProjectHibernateService.saveChage(tc);
			resp.getWriter().print("{success:true,msg:'保存成功!',changeId:'"+changeId+"',changeNo:'"+no+"'}");
		}	
		else{
			int rst = changeProjectJdbcService.updateChange(tc);
			if(rst>0)
				resp.getWriter().print("{success:true,msg:'更新成功!'}");
			else
				resp.getWriter().print("{success:true,msg:'更新失败!'}");
		}	
	}
	/**
	 * 返回变更列表
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void findChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String projectId= req.getParameter("projectId");
		String sql = "select tpc.*, change_state CHANGE_STATE_D_CHANGE_STATE from" +
				" t_project_change tpc where is_available='1' and project_id='"+projectId+"'";
		String columns="change_desc,change_time,changer,apply_time,approved_time,project_id,change_id,CHANGE_STATE_D_CHANGE_STATE,change_no,currency,amount";
		String size= req.getParameter("limit");
		ExtComponentServlet servlet = new ExtComponentServlet();
		req.setAttribute("grid_sql", sql);
		req.setAttribute("grid_columns", columns);
		req.setAttribute("grid_size", size);
		servlet.processGrid(req, resp, true);
	}
	/**
	 * 删除变更
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void deleteChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("changeId");
		int rst = changeProjectJdbcService.deleteChange(changeId);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (rst > 0)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}

	}
	/**
	 * 转到变更列表页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toProjectChangeTab(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		String projectId = req.getParameter("projectId");
		req.setAttribute("projectId", projectId);
		String projectNo = req.getParameter("projectNo");
		req.setAttribute("projectNo", projectNo);
		//查找变更审批中的记录
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		List<TProjectChange> list =  changeProjectHibernateService.findChangeByProjectId(projectId,"2");		
		TProjectChange bo = new TProjectChange();
		if(list!=null && list.size()>0){
			bo =list.get(0);
		} else {
			bo.setChanger(ut.getSysUser().getRealName());
			bo.setProjectId(projectId);
			bo.setChangeTime(DateUtils.getCurrTimeStr(2));
			bo.setChangeNo("0");
			bo.setCreator(uc.getSysUser().getUserId());
		}
		
		//判断是不是信息中心
		if("XXZX".equals(ut.getSysDept().getDeptShortCode())){
			req.setAttribute("closeChange", false);
			bo.setChangeOperator(ut.getSysUser().getRealName());
		}else
			req.setAttribute("closeChange", true);
		//流程中:当退回给业务人员修改时,判断是不是创建人
		String from = req.getParameter("from");
		boolean wfCanModify = false;
		if(from!=null && "changeW".equals(from)){
			if(bo.getCreator().equals(uc.getSysUser().getUserId())){
				wfCanModify = true;
			}
		}
		req.setAttribute("wfCanModify", wfCanModify);
		req.setAttribute("bo", bo);
		req.setAttribute("isChanged", bo.getIsChanged());
		if(wfCanModify)
			return new ModelAndView("sapss/project/projectChangeTab");
		else
			return new ModelAndView("sapss/project/projectChangeTabR");
	}
	/**
	 * 提交审批(提交动作)
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String changeId = request.getParameter("changeId");
		//查找变更内容
		TProjectChange change =  this.changeProjectHibernateService.findChange(changeId);
		UserContext uct = UserContextHolder.getLocalUserContext().getUserContext();
		//如果是信息中心,检查是否填写
		String msg = "";
		try {
		/*if("XXZX".equals(uct.getSysDept().getDeptShortCode())){
			if(!"1".equals(change.getIsChanged()))
				throw new BusinessException("请确认变更是否已执行");
		}*/
		TProjectInfo info = this.projectHibernateService.getTProjectInfo(change.getProjectId());
		String taskId = request.getParameter("workflowTaskId");
		change.setWorkflowTaskId(taskId);
		change.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		change.setWorkflowBusinessNote(userContext.getSysDept().getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ info.getProjectNo()
				+ "|"
				+ info.getProjectName());
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			change.setWorkflowExamine(workflowExamine);
			change.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		
			this.changeProjectJdbcService.submitWorkflow(taskId, change,info);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", true);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
		//response.getWriter().print(jo.toString());
	}
	/**
	 * 代办打开审批(代办链接)
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public ModelAndView workflowSubmit(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		//从代办中取得业务记录ID
		String changeId = req.getParameter("businessRecordId");
		TProjectChange tpc =  this.changeProjectHibernateService.findChange(changeId);
		TProjectInfo tpi =  this.projectHibernateService.getTProjectInfo(tpc.getProjectId());
		//显示业务类型
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tpi.getTradeType().toString());
		req.setAttribute("tradeType", tradeType);
		
		String taskId = req.getParameter("taskId");
		//历史跟踪
		req.setAttribute("businessRecordId", changeId);
		//
		req.setAttribute("taskId", taskId);
		//业务信息
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		if(ut.getSysUser().getUserId().equals(tpc.getCreator()))
			req.setAttribute("iframeSrc",
				"changeProjectController.spr?action=findProjectInfo&from=changeW&changeId="+ changeId);
		else
			req.setAttribute("iframeSrc",
					"changeProjectController.spr?action=findProjectInfo&from=changeR&changeId="+ changeId);
			
		//提交动作
		req.setAttribute("submitUrl", "changeProjectController.spr");
		req.setAttribute("action", "submitWorkflow&changeId="+ changeId);
		//动作参数
		req.setAttribute("revisable", "false");// 
		req.setAttribute("iframeForms","Form.serialize(window.frames['content']['mainForm'])");
		return new ModelAndView("sapss/project/workflowManager");
		
	}
	/**
	 * 查找立项信息,用于流程审批中信息显示
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public ModelAndView findProjectInfo(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("changeId");
		//查找变更内容
		TProjectChange bo =  this.changeProjectHibernateService.findChange(changeId);
		//req.setAttribute("bo", bo);
		//查找立项主信息和核算内容
		TProjectInfo info =  this.projectHibernateService.getTProjectInfo(bo.getProjectId());
		Map<String, TProjectAccounting> map = this.projectJdbcService.getProjectAccounting(bo.getProjectId());
		TProjectAccounting ta;
		for (int i = 1; i < map.size() + 1; i++) {
			ta = (TProjectAccounting) map.get(i + "");
			req.setAttribute(ta.getCurrency().toLowerCase()	+ ta.getAccountingItemId(), ta.getAccountingValue());
		}
		//原来的币别
		String cry = (String) req.getAttribute("other5");
		if(info.getCurrency()==null && req.getAttribute("other5")!=null){
			if("1".equals(cry))
				info.setCurrency("CNY");
			else if ("2".equals(cry))
				info.setCurrency("USD");
		}
		//原来的备注
		if(info.getMask()==null && req.getAttribute("other6")!=null){
			info.setMask((String)req.getAttribute("other6"));
		}
		
		req.setAttribute("projectId", bo.getProjectId());
		req.setAttribute("main", info);
		req.setAttribute("disabledChangeTab",false);
		req.setAttribute("from", req.getParameter("from"));
		
		//
		UserContext uct = UserContextHolder.getLocalUserContext().getUserContext();
		req.setAttribute("increasable", "MG".equals(uct.getSysDept().getDeptShortCode()));
		if("XXZX".equals(uct.getSysDept().getDeptShortCode())){
			return new ModelAndView("sapss/project/createInfoWChange");
		}else
			return new ModelAndView("sapss/project/createInfoRChange");
	}
	/**
	 * 保存信息中心执行信息
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void saveChanged(HttpServletRequest req,HttpServletResponse resp,TProjectChange tpc) throws Exception{
		String changeId =req.getParameter("changeId");
		tpc.setChangeId(changeId);
		tpc.setOperateTime(DateUtils.getCurrTimeStr(2));
		int rtn = changeProjectJdbcService.saveChanged(tpc);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if (rtn>0){
			jo.put("returnMessage", "保存成功!");
		}	
		else{
			jo.put("returnMessage","保存失败!");
		}	
		resp.getWriter().print(jo.toString());
		//this.operateSuccessfullyWithString(resp, jo.toString());
			
	}
	/**
	 * 转到工作流程图
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public void toWorkflowPictureViw(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String projectId = req.getParameter("projectId");
		String state = req.getParameter("state");
		String cd="2";
		if(state!=null && "finish".equals(state)){
			cd="4";
		}
		List<TProjectChange> list= changeProjectHibernateService.findChangeByProjectId(projectId,cd);
		String changeId="";
		if(list!=null && list.size()>0){
			changeId = list.get(0).getChangeId();
		}
		req.getRequestDispatcher("workflowController.spr?action=finishWorkDetailView&businessRecordId="+changeId).forward(req, resp);
	}
	/**
	 * 转到工作流程图
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public void toChagneWorkflowPictureViw(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("projectId");
		req.getRequestDispatcher("workflowController.spr?action=finishWorkDetailView&businessRecordId="+changeId).forward(req, resp);
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
}
