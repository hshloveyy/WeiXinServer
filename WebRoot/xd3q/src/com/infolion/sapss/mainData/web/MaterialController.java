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
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.domain.TMaterial;
import com.infolion.sapss.mainData.service.MaterialHibernateService;

public class MaterialController extends BaseMultiActionController {
	   @Autowired
	   private MaterialHibernateService materialHibernateService;
	   
		public void setMaterialHibernateService(
			MaterialHibernateService materialHibernateService) {
		this.materialHibernateService = materialHibernateService;
	}
		public ModelAndView defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/maindata/materialManager");
	}
		public ModelAndView createMaterial(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			return new ModelAndView("sapss/maindata/materialDetail");
		}
		/**
		 * 保存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void save(HttpServletRequest request, HttpServletResponse response,TMaterial info) throws IOException{
			String update = request.getParameter("update");
			if(update!=null && "workflow".equals(update)){
				update(request, response,info);
			}else{
	
				UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
				if(StringUtils.isEmpty(info.getMaterialId())){
					info.setMaterialId(CodeGenerator.getUUID());
				}
				info.setCreator(context.getSysUser().getUserId());
				info.setCreatorTime(DateUtils.getCurrTime(2));
				info.setDeptId(context.getSysDept().getDeptid());
				info.setIsAvailable("1");
				info.setExamineState("1");
			}
			this.materialHibernateService.saveOrUpdate(info);
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
		private void update(HttpServletRequest request, HttpServletResponse response,TMaterial info){
			TMaterial db = this.materialHibernateService.find(info.getMaterialId());
			info.setCreator(db.getCreator());
			info.setCreatorTime(db.getCreatorTime());
			info.setDeptId(db.getDeptId());
			info.setIsAvailable(db.getIsAvailable());
			info.setExamineState(db.getExamineState());
			info.setApplyTime(db.getApplyTime());
			this.materialHibernateService.saveOrUpdate(info);
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
				HttpServletResponse response, TMaterial info) throws IOException
		{
			UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
			String taskId = request.getParameter("workflowTaskId");
			if(StringUtils.isEmpty(info.getMaterialId())){
				info.setMaterialId(CodeGenerator.getUUID());
			}
			info.setCreator(context.getSysUser().getUserId());
			info.setCreatorTime(DateUtils.getCurrTime(2));
			info.setDeptId(context.getSysDept().getDeptid());
			info.setIsAvailable("1");
			info.setExamineState("2");
			info.setApplyTime(DateUtils.getCurrTime(2));
			this.materialHibernateService.saveAndSubmit(taskId, info);
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
			sb.append("select SALE_ORG,MATERIAL_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,MATERIAL_GROUP,MATERIAL_GROUP_NAME,MATERIAL_SALE_TAX,EXP_SALE_TAX,IMP_SALE_TAX,WITHIN_SALE_TAX,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE from t_material where IS_AVAILABLE = 1 ");
			String materialID = map.get("material_ID") == null ? "" : map.get("material_ID");
			if(materialID != "")
			{
				sb.append("and material_Code = '" + materialID + "'");
			}
			String materialName = map.get("material_Name") == null ? "" : map.get("material_Name");
			if(materialName != "")
			{
				sb.append("and material_Name like '%" + materialName + "%'");
			}
			String materialGroup = map.get("material_Group") == null ? "" : map.get("material_Group");
			if(materialGroup != "")
			{
				sb.append("and material_Group = '" + materialGroup + "'");
			}
			String grid_sql = sb.toString();
			String grid_columns = "SALE_ORG,MATERIAL_ID,MATERIAL_CODE,MATERIAL_NAME,MATERIAL_UNIT,MATERIAL_GROUP,MATERIAL_GROUP_NAME,MATERIAL_SALE_TAX,EXP_SALE_TAX,IMP_SALE_TAX,WITHIN_SALE_TAX,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE";
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
			this.materialHibernateService.delete(id);
			JSONObject js = new JSONObject();
			js.put("ok", "删除成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TMaterial info =  this.materialHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("sapss/maindata/materialDetail");
		}
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			TMaterial info =  this.materialHibernateService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "true");
			return new ModelAndView("sapss/maindata/materialDetail");
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
			TMaterial tMaterial = this.materialHibernateService.find(id);
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			Boolean modify = false;
			// 创建者能修改：
			if(taskName!=null && taskName.indexOf("修改")!=-1){
				modify = true; 
			}
			if(taskName!=null && taskName.indexOf("信息中心维护")!=-1){
				modify = true; 
			}
			// 业务修改权限进入修改页面
			if (modify)
			{
				request.setAttribute("iframeSrc",
						"materialController.spr?action=forModify&id="+ tMaterial.getMaterialId()+"&from=workflow");
			}
			else
			{
				request.setAttribute("iframeSrc",
						"materialController.spr?action=forView&id=" + tMaterial.getMaterialId() + "&taskType=" + taskType);
			}
			String submitUrl = "materialController.spr?action=submit";
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
				HttpServletResponse response, TMaterial tMaterial)
				throws IllegalAccessException, InvocationTargetException,
				IOException, Exception
		{

			String taskId = request.getParameter("workflowTaskId");
			String rtn = this.materialHibernateService.verifyFilds(taskId, tMaterial);
			if (!"".equals(rtn)){
				throw new BusinessException(rtn);
			}
			this.materialHibernateService.submit(taskId, tMaterial);
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
