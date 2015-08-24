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
import com.infolion.sapss.mainData.domain.TMaterial;
import com.infolion.sapss.mainData.domain.TSuppliers;
import com.infolion.sapss.mainData.service.SupplyHibernateService;
import com.infolion.xdss3.customerSupplierMaster.domain.CustomerMaster;
import com.infolion.xdss3.customerSupplierMaster.domain.DeptByCusu;
import com.infolion.xdss3.customerSupplierMaster.domain.SupplierMaster;
import com.infolion.xdss3.customerSupplierMaster.service.DeptByCusuService;
import com.infolion.xdss3.customerSupplierMaster.service.SupplierMasterService;

public class SupplyController extends BaseMultiActionController {
	   @Autowired
	   private SupplyHibernateService supplyHibernateService;
	   
		public void setSupplyHibernateService(
				SupplyHibernateService supplyHibernateService) {
		this.supplyHibernateService = supplyHibernateService;
	}
		@Autowired
		   private SupplierMasterService supplierMasterService;
		   
			public void setSupplierMasterService(
					SupplierMasterService supplierMasterService) {
			this.supplierMasterService = supplierMasterService;
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
		return new ModelAndView("sapss/maindata/supplyManager");
	}
		public ModelAndView createSupply(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			return new ModelAndView("sapss/maindata/supplyDetail");
		}
		 public void updateSupplerMaster(HttpServletRequest request,
					HttpServletResponse response) throws IllegalAccessException,
					InvocationTargetException, IOException {
				UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
				request.setAttribute("loginer", userContext.getSysUser().getUserId());
				String guestcode = request.getParameter("lifnr");
//				guestcode = guestcode.substring(2, guestcode.length());				
				JSONObject js = new JSONObject();	
				
					String id2 = this.supplierMasterService.isExist(guestcode);
					if(!StringUtils.isNotBlank(id2)){
		
						js.put("success", "false");
						js.put("msg", "该供应商的数据不存在供应商信息管理中！");

					}else{						
						js.put("success", "true");				
						js.put("id", id2);
					}				
				this.operateSuccessfullyWithString(response, js.toString());
			}
		 public ModelAndView forModify2(HttpServletRequest request, HttpServletResponse response){
				String id = request.getParameter("id");
				String buttonType = request.getParameter("buttonType");
				SupplierMaster supplierMaster=this.supplierMasterService.find(id);			
				TSuppliers info =  this.supplierMasterService.copyMaster(supplierMaster);
				info.setSuppliersId("");
				info.setAddType(buttonType);
				request.setAttribute("main", info);
				request.setAttribute("Type", "Modify");			
				request.setAttribute("modify", request.getParameter("from"));
				return new ModelAndView("sapss/maindata/supplyDetail");
			}
		/**
		 * 保存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void save(HttpServletRequest request, HttpServletResponse response,TSuppliers info) throws IOException{
			String update = request.getParameter("update");
			if(update!=null && "workflow".equals(update)){
				update(request, response,info);
			}else{
				UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
				if(StringUtils.isBlank(info.getSuppliersId())){
					info.setSuppliersId(CodeGenerator.getUUID());
					info.setCreatorTime(DateUtils.getCurrTime(2));
					info.setCreator(context.getSysUser().getUserId());
					info.setIsAvailable("1");
					info.setExamineState("1");
					info.setDeptId(context.getSysDept().getDeptid());
				}				
				
				
			}
			this.supplyHibernateService.saveOrUpdate(info);
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
		private void update(HttpServletRequest request, HttpServletResponse response,TSuppliers info){
			TSuppliers db = this.supplyHibernateService.find(info.getSuppliersId());
			info.setCreator(db.getCreator());
			info.setCreatorTime(db.getCreatorTime());
			info.setDeptId(db.getDeptId());
			info.setIsAvailable(db.getIsAvailable());
			info.setExamineState(db.getExamineState());
			info.setApplyTime(db.getApplyTime());
			this.supplyHibernateService.saveOrUpdate(info);
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
				HttpServletResponse response, TSuppliers info) throws IOException
		{
			UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
			String taskId = request.getParameter("workflowTaskId");
			if(StringUtils.isEmpty(info.getSuppliersId())){
				info.setSuppliersId(CodeGenerator.getUUID());
				info.setCreator(context.getSysUser().getUserId());
				info.setCreatorTime(DateUtils.getCurrTime(2));
				info.setDeptId(context.getSysDept().getDeptid());
			}
			
			info.setIsAvailable("1");
			info.setExamineState("2");
			info.setApplyTime(DateUtils.getCurrTime(2));
			this.supplyHibernateService.saveAndSubmit(taskId, info);
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
			sb.append("select SUPPLIERS_ID,ACCOUNT_GROUP,COMPANY_CODE,PURCHASE_ORG,TITLE,SUPPLIERS_CODE,SUPPLIERS_NAME1,SUPPLIERS_NAME2,STREET,REGION,SEARCH_TERM,CITY,COUNTRY,AREA,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_GUEST_CODE,SAP_GUEST_NAME,CURRENCY,SUBJECTS,CASH_GROUP,PAY_WAY,PAY_TERMS,CMD,DEPT_ID," +
					"CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE from t_suppliers where IS_AVAILABLE = 1 ");
			String guestCode = map.get("guest_Code") == null ? "" : map.get("guest_Code");
			if(guestCode != "")
			{
				sb.append("and SUPPLIERS_ID = '" + guestCode + "'");
			}
			String guestName = map.get("guest_Name") == null ? "" : map.get("guest_Name");
			if(guestName != "")
			{
				sb.append("and guest_Name like '%" + guestName + "%'");
			}
			String suppply_Name = map.get("suppply_Name") == null ? "" : map.get("suppply_Name");
			if(suppply_Name != "")
			{
				sb.append("and SUPPLIERS_NAME1 like '%" + suppply_Name + "%'");
			}
			String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
			if(SEARCHTERM != "")
			{
				sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
			}
			String grid_sql = sb.toString();
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				grid_sql += " and dept_id in ( "+ userContext.getGrantedDepartmentsId() + ")";
			} else {
				grid_sql += " and dept_id = '"	+ userContext.getSysDept().getDeptid() + "'";
			}
			grid_sql = grid_sql+ " order by creator_time desc";
			String grid_columns = "SUPPLIERS_ID,ACCOUNT_GROUP,COMPANY_CODE,PURCHASE_ORG,TITLE,SUPPLIERS_CODE,SUPPLIERS_NAME1,SUPPLIERS_NAME2,STREET,REGION,SEARCH_TERM,CITY,COUNTRY,AREA,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_GUEST_CODE,SAP_GUEST_NAME,CURRENCY,SUBJECTS,CASH_GROUP,PAY_WAY,PAY_TERMS,CMD,DEPT_ID,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE";
			String grid_size = "40";
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
			this.supplyHibernateService.delete(id);
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
			TSuppliers db = this.supplyHibernateService.find(id);
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
			this.supplyHibernateService.saveOrUpdate(db);
			JSONObject js = new JSONObject();
			js.put("success", true);
			js.put("msg", "删除成功");
			js.put("boname", boname);
			this.operateSuccessfullyWithString(response, js.toString());
		}
		public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TSuppliers info =  this.supplyHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("sapss/maindata/supplyDetail");
		}
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TSuppliers info =  this.supplyHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "true");
			return new ModelAndView("sapss/maindata/supplyDetail");
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
			TSuppliers tSuppliers = this.supplyHibernateService.find(id);
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			Boolean modify = false;
			//修改
			if(taskName!=null && taskName.indexOf("修改")!=-1 ){
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
						"supplyController.spr?action=forModify&id="+ tSuppliers.getSuppliersId()+"&from=workflow");
			}
			else
			{
				request.setAttribute("iframeSrc",
						"supplyController.spr?action=forView&id=" + tSuppliers.getSuppliersId() + "&taskType=" + taskType);
			}
			String submitUrl = "supplyController.spr?action=submit";
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
				HttpServletResponse response, TSuppliers tSuppliers)
				throws IllegalAccessException, InvocationTargetException,
				IOException, Exception
		{

			String taskId = request.getParameter("workflowTaskId");
			String rtn = this.supplyHibernateService.verifyFilds(taskId, tSuppliers);
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
				if(StringUtils.isBlank(tSuppliers.getSuppliersCode())){
					throw new BusinessException("供应商号不能为空！");
				}else{
				TSuppliers db = this.supplyHibernateService.find(tSuppliers.getSuppliersId());
				SupplierMaster master=supplierMasterService.copyMaster(db);
				String id2 = this.supplierMasterService.isExist(tSuppliers.getSuppliersCode());
				if(StringUtils.isNotBlank(id2)){
					master.setSuppliersId(id2);
				}else{
					master.setSuppliersId(CodeGenerator.getUUID());
				}
				this.supplierMasterService.saveOrUpdate(master);
				
				DeptByCusu deptByCusu =  new DeptByCusu();					
				deptByCusu.setDeptByCusuId(CodeGenerator.getUUID());					
				deptByCusu.setCompany_code(master.getCreateDept().substring(0, 2) + "00");
				deptByCusu.setDeptid(master.getDeptId());
				deptByCusu.setGsber(master.getCreateDept());
				deptByCusu.setSuppliercode(master.getSuppliersCode());
				this.deptByCusuService.saveOrUpdate(deptByCusu);
				
				}
			}
			this.supplyHibernateService.submit(taskId, tSuppliers);
			
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
