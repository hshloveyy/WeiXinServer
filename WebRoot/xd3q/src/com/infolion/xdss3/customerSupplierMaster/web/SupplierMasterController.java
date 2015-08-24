/**
 * 
 */
package com.infolion.xdss3.customerSupplierMaster.web;

import java.io.IOException;
import java.io.InputStream;
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
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.mainData.domain.TSuppliers;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;
import com.infolion.xdss3.customerSupplierMaster.domain.SupplierMaster;
import com.infolion.xdss3.customerSupplierMaster.service.SupplierMasterService;


/**
 * @author user
 *
 */
public class SupplierMasterController  extends BaseMultiActionController{

	@Autowired
	   private SupplierMasterService supplierMasterService;
	   
		public void setSupplierMasterService(
				SupplierMasterService supplierMasterService) {
		this.supplierMasterService = supplierMasterService;
	}
		 @Autowired
		    private ReportService reportService;
		    public void setReportService(ReportService reportService) {
		        this.reportService = reportService;
		    }	
	 public ModelAndView supplierMasterManage(HttpServletRequest request,
	            HttpServletResponse response) {
		 UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			for(int i=0;i <userContext.getGrantedRoles().size();i++){
				SysRole sr=(SysRole)userContext.getGrantedRoles().get(i);
				
				if("客户供应商新增修改".equals(sr.getRoleName())||"贸管专员".equals(sr.getRoleName())){
					request.setAttribute("addupdate", true);
					break;
				}else{
					request.setAttribute("addupdate", false);
				}
			}
	        return new ModelAndView("xdss3/customerSupplierMaster/supplierMasterManager");
	 }
	 public ModelAndView supplierMasterDetail(HttpServletRequest request,
	            HttpServletResponse response) {
	        return new ModelAndView("xdss3/customerSupplierMaster/supplierMasterDetail");
	 }
	
	 public ModelAndView createSupplerMaster(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			return new ModelAndView("xdss3/customerSupplierMaster/supplierMasterDetail");
		}
	 public void updateSupplerMaster(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			String sucode = request.getParameter("lifnr");
//			sucode = sucode.substring(2, sucode.length());
			TSuppliers tSuppliers2= this.supplierMasterService.getTSuppliersByCode(sucode);
			JSONObject js = new JSONObject();
			if(null != tSuppliers2){
				SupplierMaster master=supplierMasterService.copyMaster(tSuppliers2);
				String id2 = this.supplierMasterService.isExist(sucode);

				if(StringUtils.isNotBlank(id2)){
		//				master.setCustomerId(id2); todo
						js.put("success", "false");
						js.put("msg", "该供应商的审批数据已经存在客户信息管理中！");
//						throw new BusinessException("该供应商的审批数据已经存在客户信息管理中！");
					}else{
						master.setSuppliersId(CodeGenerator.getUUID());
						this.supplierMasterService.saveOrUpdate(master);
						js.put("success", "true");				
						js.put("id", master.getSuppliersId());
					}
				}else{
					js.put("success", "false");
					js.put("msg", "没有该供应商的审批数据");
//					throw new BusinessException("没有该供应商的审批数据");
				}
			this.operateSuccessfullyWithString(response, js.toString());
		}
	 
	 /**
		 * 删除
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String id = request.getParameter("id");
			this.supplierMasterService.delete(id);
			JSONObject js = new JSONObject();
			js.put("ok", "删除成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			SupplierMaster info =  this.supplierMasterService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("xdss3/customerSupplierMaster/supplierMasterDetail");
		}
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			SupplierMaster info =  this.supplierMasterService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "view");
			return new ModelAndView("xdss3/customerSupplierMaster/supplierMasterDetail");
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
			
			
			sb.append("select max(SUPPLIERMASTER_ID) as SUPPLIERMASTER_ID,max(ACCOUNT_GROUP) as ACCOUNT_GROUP ,max(COMPANY_CODE) as COMPANY_CODE,max(PURCHASE_ORG) as PURCHASE_ORG,max(TITLE) as TITLE ,max(SUPPLIERS_NAME1) as SUPPLIERS_NAME1,max(SUPPLIERS_NAME2) as SUPPLIERS_NAME2,max(STREET) as STREET,max(REGION) as REGION,max(SEARCH_TERM) as SEARCH_TERM,max(CITY) as CITY,max(COUNTRY) as COUNTRY,max(AREA) as AREA,max(ZIP_CODE) as ZIP_CODE,max(PHONE) as PHONE,max(PHONE_EXT) as PHONE_EXT,max(FAX) as FAX,max(CONTACT_MAN) as CONTACT_MAN,max(EMAIL) as EMAIL,max(SAP_GUEST_CODE) as SAP_GUEST_CODE,max(SAP_GUEST_NAME) as SAP_GUEST_NAME,max(CURRENCY) as CURRENCY,max(SUBJECTS) as SUBJECTS,max(CASH_GROUP) as CASH_GROUP,max(PAY_WAY) as PAY_WAY,max(PAY_TERMS) as PAY_TERMS,max(CMD) as CMD, " +			
			"max(CREATOR) as CREATOR,max(CREATOR_TIME) as CREATOR_TIME,max(APPLY_TIME) as APPLY_TIME,max(APPROVED_TIME) as APPROVED_TIME,max(EXAMINE_STATE) as EXAMINE_STATE,max(EXAMINE_STATE) as EXAMINE_STATE_D_EXAMINE_STATE ,max( BUSINESS_NO ) as BUSINESS_NO,max(LICENSEPATH) as LICENSEPATH,max(ORGANIZATIONPATH) as ORGANIZATIONPATH,max(TAXATIONPATH) as TAXATIONPATH,max(SURVEYPATH) as SURVEYPATH,max(FINANCIALPATH) as FINANCIALPATH,max(CREDITPATH) as CREDITPATH,max(OTHERPATH) as OTHERPATH, " +
			"max(TYPE) as TYPE,max(CODE) as CODE,max(FORMED) as FORMED,max(CAPITAL) as CAPITAL,max(NATURE) as NATURE,max(COMPANYTYPE) as COMPANYTYPE,max(PERIODSTART) as PERIODSTART,max(PERIODEND) as PERIODEND,max(ANNUALINSPECTION) as ANNUALINSPECTION,max(OTHERINFO) as OTHERINFO,max(UPDATETIME) as UPDATETIME,max(dept_name) as CREATEDEPT,max(CREATEOR) as CREATEOR,max(CAPITALCURRENCY) as CAPITALCURRENCY, " );
			sb.append(" max(sm.SUPPLIERS_CODE) as SUPPLIERS_CODE,max(ts.DEPTID) as DEPT_ID ");
			sb.append(" from ysuppliermaster sm ");			
			sb.append(" left join ( select a.deptid,a.suppliercode,b.dept_name from ydeptbycusu a ,t_sys_dept b where a.deptid=b.dept_id and trim(suppliercode) is not null ) ts  on ts.suppliercode=sm.suppliers_code ");
			sb.append(" where 1 = 1 ");
			String guestCode = map.get("SUPPLIERS_CODE") == null ? "" : map.get("SUPPLIERS_CODE");
			if(guestCode != "")
			{
				sb.append("and SUPPLIERS_CODE = '" + guestCode + "'");
			}
			String guestName = map.get("SUPPLIERS_Name") == null ? "" : map.get("SUPPLIERS_Name");
			if(guestName != "")
			{
				sb.append("and SUPPLIERS_NAME1 like '%" + guestName + "%'");
			}
			String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
			if(SEARCHTERM != "")
			{
				sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
			}
			
			String nature = map.get("nature") == null ? "" : map.get("nature");
			if(nature != "")
			{
				sb.append("and nature = '" + nature + "'");
			}
			String companytype = map.get("companytype") == null ? "" : map.get("companytype");
			if(companytype != "")
			{
				sb.append("and companytype = '" + companytype + "'");
			}
			String formed = map.get("formed") == null ? "" : map.get("formed");
			if(formed != "")
			{
				sb.append("and formed = '" + formed + "'");
			}
			String capital = map.get("capital") == null ? "" : map.get("capital");
			if(capital != "")
			{
				sb.append("and capital = '" + capital + "'");
			}
			String periodStart = map.get("periodStart") == null ? "" : map.get("periodStart");
			String periodEnd = map.get("periodEnd") == null ? "" : map.get("periodEnd");
			if(periodStart != "" && periodEnd == "")
			{
				sb.append("and periodStart >= '" + periodStart + "'");
			}
			
			if(periodEnd != "" && periodStart == "")
			{
				sb.append("and periodEnd <= '" + periodEnd + "'");
			}
			
			if(periodEnd != "" && periodStart != "")
			{
				sb.append("and periodStart >= '" + periodStart + "'");
				sb.append("and periodEnd <= '" + periodEnd + "'");
			}
			String annualInspection = map.get("annualInspection") == null ? "" : map.get("annualInspection");
			if(annualInspection != "")
			{
				sb.append("and annualInspection = '" + annualInspection + "'");
			}
			String country = map.get("country") == null ? "" : map.get("country");
			if(country != "" && !"请选择...".equals(country)  )
			{
				sb.append("and country = '" + country + "'");
			}
			String area = map.get("area") == null ? "" : map.get("area");
			if(area != "" && !"请选择...".equals(area) )
			{
				sb.append("and area = '" + area + "'");
			}

			
			String grid_sql = sb.toString();
			String grid_columns = "SUPPLIERMASTER_ID,ACCOUNT_GROUP,COMPANY_CODE,PURCHASE_ORG,TITLE,SUPPLIERS_CODE,SUPPLIERS_NAME1,SUPPLIERS_NAME2,STREET,REGION,SEARCH_TERM,CITY,COUNTRY,AREA,BUSINESS_NO,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_GUEST_CODE,SAP_GUEST_NAME,CURRENCY,SUBJECTS,CASH_GROUP,PAY_WAY,PAY_TERMS,CMD,DEPT_ID,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,LICENSEPATH,ORGANIZATIONPATH,TAXATIONPATH,SURVEYPATH,FINANCIALPATH,CREDITPATH,OTHERPATH,TYPE,CODE,FORMED,CAPITAL,NATURE,COMPANYTYPE,PERIODSTART,PERIODEND,ANNUALINSPECTION,OTHERINFO,UPDATETIME,CREATEDEPT,CREATEOR,CAPITALCURRENCY";
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				grid_sql += " and ts.deptid in ( "+ userContext.getGrantedDepartmentsId() + ")";
			} else {
				grid_sql += " and ts.deptid = '"	+ userContext.getSysDept().getDeptid() + "'";
			}
			//grid_sql = grid_sql +" order by creator_time desc";
			grid_sql = grid_sql +" group by SUPPLIERMASTER_ID,creator_time";
			String grid_size = "10";
			req.setAttribute("grid_sql", grid_sql);
			String loginer = userContext.getSysUser().getUserId();
			req.setAttribute("loginer", loginer);
			req.setAttribute("grid_columns", grid_columns);
			req.setAttribute("grid_size", grid_size);
			servlet.processGrid(req, resp, true);
			
			
			
		}
		
//		导出
		public void exportToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		       String detailName = "suppliermaster";
		       String col = ReportUtil.getProperty(detailName);
		       
		       ExtComponentServlet servlet = new ExtComponentServlet();
				UserContext userContext = UserContextHolder.getLocalUserContext()
						.getUserContext();
				Map<String, String> map = extractFR(request);
				StringBuffer sb = new StringBuffer();
				
				
				sb.append("select max(SUPPLIERMASTER_ID) as SUPPLIERMASTER_ID,max(ACCOUNT_GROUP) as ACCOUNT_GROUP ,max(COMPANY_CODE) as COMPANY_CODE,max(PURCHASE_ORG) as PURCHASE_ORG,max(TITLE) as TITLE ,max(SUPPLIERS_NAME1) as SUPPLIERS_NAME1,max(SUPPLIERS_NAME2) as SUPPLIERS_NAME2,max(STREET) as STREET,max(REGION) as REGION,max(SEARCH_TERM) as SEARCH_TERM,max(CITY) as CITY,max(COUNTRY) as COUNTRY,max(AREA) as AREA,max(ZIP_CODE) as ZIP_CODE,max(PHONE) as PHONE,max(PHONE_EXT) as PHONE_EXT,max(FAX) as FAX,max(CONTACT_MAN) as CONTACT_MAN,max(EMAIL) as EMAIL,max(SAP_GUEST_CODE) as SAP_GUEST_CODE,max(SAP_GUEST_NAME) as SAP_GUEST_NAME,max(CURRENCY) as CURRENCY,max(SUBJECTS) as SUBJECTS,max(CASH_GROUP) as CASH_GROUP,max(PAY_WAY) as PAY_WAY,max(PAY_TERMS) as PAY_TERMS,max(CMD) as CMD, " +			
				"max(CREATOR) as CREATOR,max(CREATOR_TIME) as CREATOR_TIME,max(APPLY_TIME) as APPLY_TIME,max(APPROVED_TIME) as APPROVED_TIME,max(EXAMINE_STATE) as EXAMINE_STATE,max(EXAMINE_STATE) as EXAMINE_STATE_D_EXAMINE_STATE ,max( BUSINESS_NO ) as BUSINESS_NO, " +
				" CASE WHEN ( trim(max(sm.licensepath)) is null ) THEN '否'  ELSE '是' END licensepath, "+
				"CASE WHEN ( trim(max(sm.ORGANIZATIONPATH)) is null ) THEN '否'  ELSE '是' END ORGANIZATIONPATH, "+
				"CASE WHEN ( trim(max(sm.TAXATIONPATH)) is null ) THEN '否'  ELSE '是' END TAXATIONPATH, "+
				"CASE WHEN ( trim(max(sm.SURVEYPATH)) is null ) THEN '否'  ELSE '是' END SURVEYPATH, "+
				"CASE WHEN ( trim(max(sm.FINANCIALPATH)) is null ) THEN '否'  ELSE '是' END FINANCIALPATH, "+
				"CASE WHEN ( trim(max(sm.CREDITPATH)) is null ) THEN '否'  ELSE '是' END CREDITPATH, "+
				"CASE WHEN ( trim(max(sm.OTHERPATH)) is null ) THEN '否'  ELSE '是' END OTHERPATH, "+
				"max(TYPE) as TYPE,max(CODE) as CODE,max(FORMED) as FORMED,max(CAPITAL) as CAPITAL,max(NATURE) as NATURE,max(COMPANYTYPE) as COMPANYTYPE,max(PERIODSTART) as PERIODSTART,max(PERIODEND) as PERIODEND,max(ANNUALINSPECTION) as ANNUALINSPECTION,max(OTHERINFO) as OTHERINFO,max(UPDATETIME) as UPDATETIME,max(CREATEDEPT) as CREATEDEPT,max(CREATEOR) as CREATEOR,max(CAPITALCURRENCY) as CAPITALCURRENCY, " );
				sb.append(" max(sm.SUPPLIERS_CODE) as SUPPLIERS_CODE,max(ts.DEPTID) as DEPT_ID ");
				sb.append(" from ysuppliermaster sm ");			
				sb.append(" left join ( select deptid,suppliercode from ydeptbycusu where trim(suppliercode) is not null ) ts  on ts.suppliercode=sm.suppliers_code ");
				sb.append(" where 1 = 1 ");
				String guestCode = map.get("SUPPLIERS_CODE") == null ? "" : map.get("SUPPLIERS_CODE");
				if(guestCode != "")
				{
					sb.append("and SUPPLIERS_CODE = '" + guestCode + "'");
				}
				String guestName = map.get("SUPPLIERS_Name") == null ? "" : map.get("SUPPLIERS_Name");
				if(guestName != "")
				{
					sb.append("and SUPPLIERS_NAME1 like '%" + guestName + "%'");
				}
				String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
				if(SEARCHTERM != "")
				{
					sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
				}
				
				String nature = map.get("nature") == null ? "" : map.get("nature");
				if(nature != "")
				{
					sb.append("and nature = '" + nature + "'");
				}
				String companytype = map.get("companytype") == null ? "" : map.get("companytype");
				if(companytype != "")
				{
					sb.append("and companytype = '" + companytype + "'");
				}
				String formed = map.get("formed") == null ? "" : map.get("formed");
				if(formed != "")
				{
					sb.append("and formed = '" + formed + "'");
				}
				String capital = map.get("capital") == null ? "" : map.get("capital");
				if(capital != "")
				{
					sb.append("and capital = '" + capital + "'");
				}
				String periodStart = map.get("periodStart") == null ? "" : map.get("periodStart");
				String periodEnd = map.get("periodEnd") == null ? "" : map.get("periodEnd");
				if(periodStart != "" && periodEnd == "")
				{
					sb.append("and periodStart >= '" + periodStart + "'");
				}
				
				if(periodEnd != "" && periodStart == "")
				{
					sb.append("and periodEnd <= '" + periodEnd + "'");
				}
				
				if(periodEnd != "" && periodStart != "")
				{
					sb.append("and periodStart >= '" + periodStart + "'");
					sb.append("and periodEnd <= '" + periodEnd + "'");
				}
				String annualInspection = map.get("annualInspection") == null ? "" : map.get("annualInspection");
				if(annualInspection != "")
				{
					sb.append("and annualInspection = '" + annualInspection + "'");
				}
				String country = map.get("country") == null ? "" : map.get("country");
				if(country != "" && !"请选择...".equals(country)  )
				{
					sb.append("and country = '" + country + "'");
				}
				String area = map.get("area") == null ? "" : map.get("area");
				if(area != "" && !"请选择...".equals(area) )
				{
					sb.append("and area = '" + area + "'");
				}

				
				String grid_sql = sb.toString();
				
				if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
					grid_sql += " and ts.deptid in ( "+ userContext.getGrantedDepartmentsId() + ")";
				} else {
					grid_sql += " and ts.deptid = '"	+ userContext.getSysDept().getDeptid() + "'";
				}
				//grid_sql = grid_sql +" order by creator_time desc";
				grid_sql = grid_sql +" group by SUPPLIERMASTER_ID,creator_time";
		       
		      
		       
		       String[] cols = col.split(",");
		       String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
		       String[] colNames = colName.split(",");
		       InputStream is = this.reportService.createExcelFile(cols, colNames, grid_sql.toString(), null);
		       ReportUtil util = new ReportUtil();
		       util.download(is, "供应商信息.xls", response);
		       
		       is.close();
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
			SupplierMaster db = this.supplierMasterService.find(id);
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
			this.supplierMasterService.saveOrUpdate(db);
			JSONObject js = new JSONObject();
			js.put("success", true);
			js.put("msg", "删除成功");
			js.put("boname", boname);
			this.operateSuccessfullyWithString(response, js.toString());
		}
		
		/**
		 * 保存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		public void save(HttpServletRequest request, HttpServletResponse response,SupplierMaster info) throws IOException{
			
			UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
			if(StringUtils.isEmpty(info.getSuppliersId())){
				info.setSuppliersId(CodeGenerator.getUUID());
				info.setCreatorTime(DateUtils.getCurrTime(2));
				info.setCreator(context.getSysUser().getUserId());
//				info.setCreatorTime(DateUtils.getCurrTime(2));
				
				info.setCreateor(context.getSysUser().getUserName());
				info.setCreateDept(context.getSysDept().getDeptcode());
				info.setIsAvailable("1");
				info.setExamineState("3");
				info.setDeptId(context.getSysDept().getDeptid());
			}else{
				info.setUpdateTime(DateUtils.getCurrTime(2));
				SupplierMaster sm=this.supplierMasterService.find(info.getSuppliersId());
//				修改编号时，要同时修改部门权限表
				if(StringUtils.isNotBlank(info.getSuppliersCode()) && StringUtils.isNotBlank(sm.getSuppliersCode()) && !sm.getSuppliersCode().equals(info.getSuppliersCode())){
					this.supplierMasterService.updateDeptNo(info.getSuppliersCode(), sm.getSuppliersCode());
				}
			}
	
			this.supplierMasterService.saveOrUpdate(info);
			
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
