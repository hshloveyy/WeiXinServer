/**
 * 控制器
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张小军
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
package com.infolion.sapss.mainData.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.service.GuestHibernateService;
import com.infolion.xdss3.customerSupplierMaster.domain.CustomerMaster;
import com.infolion.xdss3.customerSupplierMaster.domain.DeptByCusu;
import com.infolion.xdss3.customerSupplierMaster.service.CustomerMasterService;
import com.infolion.xdss3.customerSupplierMaster.service.DeptByCusuService;

public class GuestController extends BaseMultiActionController {
	   @Autowired
	   private GuestHibernateService guestHibernateService;
	   
		public void setGuestHibernateService(
				GuestHibernateService guestHibernateService) {
		this.guestHibernateService = guestHibernateService;
	}
		@Autowired
		   private CustomerMasterService customerMasterService;
		   
			public void setCustomerMasterService(
					CustomerMasterService customerMasterService) {
			this.customerMasterService = customerMasterService;
		}
			 @Autowired
			 private DeptByCusuService deptByCusuService;

			/**
			 * @param deptByCusuService the deptByCusuService to set
			 */
			public void setDeptByCusuService(DeptByCusuService deptByCusuService) {
				this.deptByCusuService = deptByCusuService;
			}	
		public ModelAndView defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/maindata/guestManager");
	}
		public ModelAndView createGuest(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			return new ModelAndView("sapss/maindata/guestDetail");
		}
		 public void updateCustomerMaster(HttpServletRequest request,
					HttpServletResponse response) throws IllegalAccessException,
					InvocationTargetException, IOException {
				UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
				request.setAttribute("loginer", userContext.getSysUser().getUserId());
				String guestcode = request.getParameter("kunnr");
//				guestcode = guestcode.substring(2, guestcode.length());				
				JSONObject js = new JSONObject();	
				
					String id2 = this.customerMasterService.isExist(guestcode);
					if(!StringUtils.isNotBlank(id2)){
		
						js.put("success", "false");
						js.put("msg", "该客户的数据不存在客户信息管理中！");

					}else{						
						js.put("success", "true");				
						js.put("id", id2);
					}				
				this.operateSuccessfullyWithString(response, js.toString());
			}
		 
		/**
		 * 保存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void save(HttpServletRequest request, HttpServletResponse response,TGuest info) throws IOException{
			String update = request.getParameter("update");
			if(update!=null && "workflow".equals(update)){
				update(request, response,info);
			}else{
				UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
				if(StringUtils.isEmpty(info.getGuestId())){
					info.setGuestId(CodeGenerator.getUUID());
					info.setCreator(context.getSysUser().getUserId());
					info.setCreatorTime(DateUtils.getCurrTime(2));
					info.setIsAvailable("1");
					info.setExamineState("1");
					info.setDeptId(context.getSysDept().getDeptid());
				}
				this.guestHibernateService.saveOrUpdate(info);
			}
			JSONObject js = new JSONObject();
			js.put("ok", "保存成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		/**
		 * 
		 * @param request
		 * @param response
		 * @param info
		 */
		private void update(HttpServletRequest request, HttpServletResponse response,TGuest info){
			TGuest db = this.guestHibernateService.find(info.getGuestId());
			info.setCreator(db.getCreator());
			info.setCreatorTime(db.getCreatorTime());
			info.setDeptId(db.getDeptId());
			info.setIsAvailable(db.getIsAvailable());
			info.setExamineState(db.getExamineState());
			info.setApplyTime(db.getApplyTime());
			this.guestHibernateService.saveOrUpdate(info);
		}
		/**
		 * 提交审批
		 * 
		 * @param request
		 * @param response
		 * @param info
		 * @throws IOException
		 */
		public void saveAndsubmit(HttpServletRequest request,
				HttpServletResponse response, TGuest info) throws IOException
		{
			UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
			String taskId = request.getParameter("workflowTaskId");
			if(StringUtils.isEmpty(info.getGuestId())){
				info.setGuestId(CodeGenerator.getUUID());
				info.setDeptId(context.getSysDept().getDeptid());
				info.setCreator(context.getSysUser().getUserId());
				info.setCreatorTime(DateUtils.getCurrTime(2));
			}			
			info.setIsAvailable("1");			
			info.setExamineState("2");
			info.setApplyTime(DateUtils.getCurrTime(2));
			
			this.guestHibernateService.saveAndSubmit(taskId, info);
			JSONObject js = new JSONObject();
			js.put("ok", "提交成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		
		/**
		 * 查找
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void find(HttpServletRequest req, HttpServletResponse resp)
		throws Exception {
			ExtComponentServlet servlet = new ExtComponentServlet();
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			Map<String, String> map = extractFR(req);
			StringBuffer sb = new StringBuffer();
			sb.append("select GUEST_ID,ACCOUNT_GROUP,COMPANY_CODE,SALES_ORG,TITLE,GUEST_CODE,GUEST_NAME,ADDRESS,SEARCH_TERM,CITY,COUNTRY,AREA,VAT_NUM,BUSINESS_NO,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_SUPPLIERS_CODE,SAP_SUPPLIERS_NAME,CURRENCY,TAX_SORT,ACCOUNT_ALLOT,SUBJECTS,CASH_GROUP,PAY_TERMS,PAY_WAY,CMD,DEPT_ID," +
					"CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE from t_guest where IS_AVAILABLE = 1 ");
			String guestCode = map.get("guest_Code") == null ? "" : map.get("guest_Code");
			if(guestCode != "")
			{
				sb.append("and guest_Code = '" + guestCode + "'");
			}
			String guestName = map.get("guest_Name") == null ? "" : map.get("guest_Name");
			if(guestName != "")
			{
				sb.append("and guest_Name like '%" + guestName + "%'");
			}
			String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
			if(SEARCHTERM != "")
			{
				sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
			}

			String grid_sql = sb.toString();
			String grid_columns = "GUEST_ID,ACCOUNT_GROUP,COMPANY_CODE,SALES_ORG,TITLE,GUEST_CODE,GUEST_NAME,ADDRESS,SEARCH_TERM,CITY,COUNTRY,AREA,VAT_NUM,BUSINESS_NO,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_SUPPLIERS_CODE,SAP_SUPPLIERS_NAME,CURRENCY,TAX_SORT,ACCOUNT_ALLOT,SUBJECTS,CASH_GROUP,PAY_TERMS,PAY_WAY,CMD,DEPT_ID,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE";
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				grid_sql += " and dept_id in ( "+ userContext.getGrantedDepartmentsId() + ")";
			} else {
				grid_sql += " and dept_id = '"	+ userContext.getSysDept().getDeptid() + "'";
			}
			grid_sql = grid_sql +" order by creator_time desc";
			String grid_size = "20";
			req.setAttribute("grid_sql", grid_sql);
			String loginer = userContext.getSysUser().getUserId();
			req.setAttribute("loginer", loginer);
			req.setAttribute("grid_columns", grid_columns);
			req.setAttribute("grid_size", grid_size);
			servlet.processGrid(req, resp, true);
		}
		/**
		 * 删除
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String id = request.getParameter("id");
			this.guestHibernateService.delete(id);
			JSONObject js = new JSONObject();
			js.put("ok", "删除成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		/**
		 * 删除
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void delAtt(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String id = request.getParameter("id");
			String boname = request.getParameter("boname");
			TGuest db = this.guestHibernateService.find(id);
			if("license".equals(boname)){
				db.setLicensePath("");
			}
			if("organization".equals(boname)){
				db.setOrganizationPath("");
			}
			if("taxation".equals(boname)){
				db.setTaxationPath("");
			}
			if("survey".equals(boname)){
				db.setSurveyPath("");
			}
			if("financial".equals(boname)){
				db.setFinancialPath("");
			}
			if("credit".equals(boname)){
				db.setCreditPath("");
			}
			if("other".equals(boname)){
				db.setOtherPath("");
			}
			this.guestHibernateService.saveOrUpdate(db);
			JSONObject js = new JSONObject();
			js.put("success", true);
			js.put("msg", "删除成功");
			js.put("boname", boname);
			this.operateSuccessfullyWithString(response, js.toString());
		}
		public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TGuest info =  this.guestHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("sapss/maindata/guestDetail");
		}
		public ModelAndView forModify2(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			String buttonType = request.getParameter("buttonType");
			CustomerMaster customerMaster=this.customerMasterService.find(id);			
			TGuest info =  this.customerMasterService.copyMaster(customerMaster);
			info.setGuestId("");
			info.setAddType(buttonType);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");			
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("sapss/maindata/guestDetail");
		}
		
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TGuest info =  this.guestHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "true");
			return new ModelAndView("sapss/maindata/guestDetail");
		}
		/**
		 * 进入到流程审批界面
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		public ModelAndView examine(HttpServletRequest request,
				HttpServletResponse response) throws IOException
		{
			String id = request.getParameter("businessRecordId");
			String taskId = request.getParameter("taskId");
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			String taskName = taskInstanceContext.getTaskName();
			String taskType = "";
			request.setAttribute("taskId", taskId);
			request.setAttribute("businessRecordId", id);
			request.setAttribute("taskName", taskName);
			TGuest tGuest = this.guestHibernateService.find(id);
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			Boolean modify = false;
			//修改
			if(taskName!=null && taskName.indexOf("修改")!=-1){
				modify = true; 
			}
			//维护公司层代码信息
			if(taskName!=null && taskName.indexOf("财务")!=-1){
				modify = true; 
			}
			if(taskName!=null && taskName.indexOf("信息中心维护")!=-1){
				modify = true; 
			}
			if(taskName!=null && taskName.indexOf("贸管专员审核")!=-1){
				modify = true; 
			}
			// 业务修改权限进入修改页面
			if (modify)
			{
				request.setAttribute("iframeSrc",
						"guestController.spr?action=forModify&id="+ tGuest.getGuestId()+"&from=workflow");
			}
			else
			{
				request.setAttribute("iframeSrc",
						"guestController.spr?action=forView&id=" + tGuest.getGuestId() + "&taskType=" + taskType);
			}
			String submitUrl = "guestController.spr?action=submit";
			request.setAttribute("submitUrl", submitUrl);
			return new ModelAndView("sapss/workflow/commonProcessPage");

		}
		/**
		 * 流程审批提交动作
		 * @param request
		 * @param response
		 * @param tBaleLoan
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 * @throws IOException
		 * @throws Exception
		 */
		public void submit(HttpServletRequest request,
				HttpServletResponse response, TGuest tGuest)
				throws IllegalAccessException, InvocationTargetException,
				IOException, Exception
		{

			String taskId = request.getParameter("workflowTaskId");
			String rtn = this.guestHibernateService.verifyFilds(taskId, tGuest);
			if (!"".equals(rtn)){
				throw new BusinessException(rtn);
			}
			String id = request.getParameter("businessRecordId");			
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			String taskName = taskInstanceContext.getTaskName();
			String taskType = "";			
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			
			if(taskName!=null && "信息中心维护".equals(taskName)  && "已维护".equals(workflowLeaveTransitionName)){
				if(StringUtils.isBlank(tGuest.getGuestCode())){
					throw new BusinessException("客户号不能为空！");
				}else{
					TGuest db = this.guestHibernateService.find(tGuest.getGuestId());
					CustomerMaster master=customerMasterService.copyMaster(db);
					String id2 = this.customerMasterService.isExist(tGuest.getGuestCode());
					if(StringUtils.isNotBlank(id2)){
						master.setCustomerId(id2);
					}else{
						master.setCustomerId(CodeGenerator.getUUID());
					}
					this.customerMasterService.saveOrUpdate(master);
					
					DeptByCusu deptByCusu =  new DeptByCusu();					
					deptByCusu.setDeptByCusuId(CodeGenerator.getUUID());					
					deptByCusu.setCompany_code(master.getCreateDept().substring(0, 2) + "00");
					deptByCusu.setDeptid(master.getDeptId());
					deptByCusu.setGsber(master.getCreateDept());
					deptByCusu.setGuestcode(master.getGuestCode());
					this.deptByCusuService.saveOrUpdate(deptByCusu);
					
				}
			}
			this.guestHibernateService.submit(taskId, tGuest);
			JSONObject jo = new JSONObject();
			jo.put("returnMessage", "提交成功！");
			String jsontext = jo.toString();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html; charset=GBK");
			this.operateSuccessfullyWithString(response, jsontext);
		}
		/**
		 * 萃取url
		 * 
		 * @param req
		 * @return 参数名,参数值的map
		 */
		private Map<String, String> extractFR(HttpServletRequest req) {
			try {
				String wait = java.net.URLDecoder.decode(req.getQueryString(),
						"UTF-8");
				String ar1[] = wait.split("&");
				Map<String, String> map = new HashMap<String, String>();
				String str = "";
				String ar2[];
				for (int i = 0; i < ar1.length; i++) {
					str = ar1[i];
					ar2 = str.split("=");
					if (ar2.length == 1)
						continue;
					map.put(ar2[0], ar2[1]);
				}
				return map;
			} catch (UnsupportedEncodingException e) {
			}
			return Collections.EMPTY_MAP;
		}
}
