package com.infolion.xdss3.customerSupplierMaster.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.xdss3.customerSupplierMaster.domain.DeptByCusu;
import com.infolion.xdss3.customerSupplierMaster.service.DeptByCusuService;

public class DeptByCusuController  extends BaseMultiActionController{
	 @Autowired
	 private DeptByCusuService deptByCusuService;

	/**
	 * @param deptByCusuService the deptByCusuService to set
	 */
	public void setDeptByCusuService(DeptByCusuService deptByCusuService) {
		this.deptByCusuService = deptByCusuService;
	}
	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	 public ModelAndView deptByCusuManage(HttpServletRequest request,
	            HttpServletResponse response) {
		 UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			for(int i=0;i <userContext.getGrantedRoles().size();i++){
				SysRole sr=(SysRole)userContext.getGrantedRoles().get(i);
				
				if("增加修改角色".equals(sr.getRoleName())){
					request.setAttribute("addupdate", true);
					break;
				}else{
					request.setAttribute("addupdate", false);
				}
			}
	        return new ModelAndView("xdss3/customerSupplierMaster/deptByCusuManager");
	    }
	 public ModelAndView deptByCusuDetail(HttpServletRequest request,
	            HttpServletResponse response) {
	        return new ModelAndView("xdss3/customerSupplierMaster/deptByCusuDetail");
	    }
	 public ModelAndView createDeptByCusu(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			
			
			 return new ModelAndView("xdss3/customerSupplierMaster/deptByCusuDetail");
		}
	 public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			DeptByCusu info =  this.deptByCusuService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("xdss3/customerSupplierMaster/deptByCusuDetail");
		}
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			DeptByCusu info =  this.deptByCusuService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "view");
			return new ModelAndView("xdss3/customerSupplierMaster/deptByCusuDetail");
		}
		
		/**
		 * 删除
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String id = request.getParameter("id");
			this.deptByCusuService.delete(id);
			JSONObject js = new JSONObject();
			js.put("ok", "删除成功");
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
			sb.append("select DEPTBYCUSUID,GUESTCODE,SUPPLIERCODE,DEPTID,GSBER,COMPANY_CODE from ydeptbycusu where 1=1 ");				
			String guestCode = map.get("guestcode") == null ? "" : map.get("guestcode");
			if(guestCode != "")
			{
				guestCode = guestCode.substring(2, guestCode.length());
				sb.append(" and  guestcode like '%" + guestCode + "%' ");
			}
			String suppliercode = map.get("suppliercode") == null ? "" : map.get("suppliercode");
			if(suppliercode != "")
			{
				suppliercode = suppliercode.substring(2, suppliercode.length());
				sb.append(" and suppliercode like  '%" + suppliercode + "%' ");
			}
			

			String grid_sql = sb.toString();
			String grid_columns = "DEPTBYCUSUID,GUESTCODE,SUPPLIERCODE,DEPTID,GSBER,COMPANY_CODE";
			
			String grid_size = "20";
			req.setAttribute("grid_sql", grid_sql);
			String loginer = userContext.getSysUser().getUserId();
			req.setAttribute("loginer", loginer);
			req.setAttribute("grid_columns", grid_columns);
			req.setAttribute("grid_size", grid_size);
			servlet.processGrid(req, resp, true);
		}
		
		/**
		 * 保存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void save(HttpServletRequest request, HttpServletResponse response,DeptByCusu info) throws IOException{
			
			if(StringUtils.isEmpty(info.getDeptByCusuId())){
				info.setDeptByCusuId(CodeGenerator.getUUID());
			}
			String guestCode=info.getGuestcode();
			if(!StringUtils.isEmpty(guestCode)){
//			 guestCode = guestCode.substring(2, guestCode.length());
			info.setGuestcode(guestCode);
			}
			String suppliercode=info.getSuppliercode();
			if(!StringUtils.isEmpty(suppliercode)){
//				suppliercode = suppliercode.substring(2, suppliercode.length());
				info.setSuppliercode(suppliercode);
			}
			
			List<String> depts = this.sysDeptJdbcDao.getDeptIdByDeptCode(info.getGsber());
			if(null != depts && depts.size()>0){
				info.setDeptid(depts.get(0));
			}
			this.deptByCusuService.saveOrUpdate(info);
			JSONObject js = new JSONObject();
			js.put("ok", "保存成功");
			this.operateSuccessfullyWithString(response, js.toString());
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
