package com.infolion.xdss3.customerSupplierMaster.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.fileProcess.domain.TAttachement;
import com.infolion.platform.component.fileProcess.domain.TAttachementBusiness;
import com.infolion.platform.component.fileProcess.services.AttachementBusinessJdbcService;
import com.infolion.platform.component.fileProcess.services.AttachementHibernateService;
import com.infolion.platform.component.fileProcess.services.AttachementJdbcService;
import com.infolion.platform.component.processor.FileRenamePolicyImpl;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;

import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.Constants;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;

import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.domain.TSuppliers;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;


import com.infolion.xdss3.cashFlow.service.ExportExel;
import com.infolion.xdss3.customerSupplierMaster.domain.CustomerMaster;
import com.infolion.xdss3.customerSupplierMaster.service.CustomerMasterService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;


public class CustomerMasterController  extends BaseMultiActionController{
	
	private static String propertiesPah ="\\config\\config.properties";
	private static Properties p = null;
	void init() {
		p = new Properties();
		try{
			p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
		}
		catch (Exception e) {
			LogFactory.getLog(this.getClass()).error("加载配置文件失败:"+e.getMessage());
		}
	}
	
	  @Autowired
	   private CustomerMasterService customerMasterService;
	   
		public void setCustomerMasterService(
				CustomerMasterService customerMasterService) {
		this.customerMasterService = customerMasterService;
	}
		 @Autowired
		    private ReportService reportService;
		    public void setReportService(ReportService reportService) {
		        this.reportService = reportService;
		    }
	 public ModelAndView customerMasterManage(HttpServletRequest request,
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
	        return new ModelAndView("xdss3/customerSupplierMaster/customerMasterManager");
	    }
	 public ModelAndView customerMasterDetail(HttpServletRequest request,
	            HttpServletResponse response) {
	        return new ModelAndView("xdss3/customerSupplierMaster/customerMasterDetail");
	    }
	 public ModelAndView createCustomerMaster(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			
			
			 return new ModelAndView("xdss3/customerSupplierMaster/customerMasterDetail");
		}
	 public void updateCustomerMaster(HttpServletRequest request,
				HttpServletResponse response) throws IllegalAccessException,
				InvocationTargetException {
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			request.setAttribute("loginer", userContext.getSysUser().getUserId());
			String guestcode = request.getParameter("kunnr");
//			guestcode = guestcode.substring(2, guestcode.length());
			TGuest tGuest2 = this.customerMasterService.getTGuestBycode(guestcode);
			JSONObject js = new JSONObject();
			if(null != tGuest2){
				CustomerMaster master=customerMasterService.copyMaster(tGuest2);
				String id2 = this.customerMasterService.isExist(guestcode);
				if(StringUtils.isNotBlank(id2)){
	//				master.setCustomerId(id2); todo
					js.put("success", "false");
					js.put("msg", "该客户的审批数据已经存在客户信息管理中！");
//					throw new BusinessException("该客户的审批数据已经存在客户信息管理中！");
				}else{
					master.setCustomerId(CodeGenerator.getUUID());
					this.customerMasterService.saveOrUpdate(master);
					js.put("success", "true");				
					js.put("id", master.getCustomerId());
				}
			}else{
				js.put("success", "false");
				js.put("msg", "没有该客户的审批数据");
//				throw new BusinessException("没有该客户的审批数据");
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
			this.customerMasterService.delete(id);
			JSONObject js = new JSONObject();
			js.put("ok", "删除成功");
			this.operateSuccessfullyWithString(response, js.toString());
		}
		public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			CustomerMaster info =  this.customerMasterService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "Modify");
			
			request.setAttribute("modify", request.getParameter("from"));
			return new ModelAndView("xdss3/customerSupplierMaster/customerMasterDetail");
		}
		public ModelAndView forView(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			CustomerMaster info =  this.customerMasterService.find(id);
			request.setAttribute("main", info);
			request.setAttribute("Type", "view");
			return new ModelAndView("xdss3/customerSupplierMaster/customerMasterDetail");
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
			sb.append("select max(CUSTOMERMASTER_ID) as CUSTOMERMASTER_ID,max(ACCOUNT_GROUP) as ACCOUNT_GROUP ,max(COMPANY_CODE) as COMPANY_CODE,max(SALES_ORG) as SALES_ORG,max(TITLE) as TITLE ,max(GUEST_NAME) as GUEST_NAME,max(ADDRESS) as ADDRESS,max(SEARCH_TERM) as SEARCH_TERM,max(CITY) as CITY,max(COUNTRY) as COUNTRY,max(AREA) as AREA,max(VAT_NUM) as VAT_NUM,max(BUSINESS_NO) as BUSINESS_NO,max(ZIP_CODE) as ZIP_CODE,max(PHONE) as PHONE,max(PHONE_EXT) as PHONE_EXT,max(FAX) as FAX,max(CONTACT_MAN) as CONTACT_MAN,max(EMAIL) as EMAIL,max(SAP_SUPPLIERS_CODE) as SAP_SUPPLIERS_CODE,max(SAP_SUPPLIERS_NAME) as SAP_SUPPLIERS_NAME,max(CURRENCY) as CURRENCY,max(TAX_SORT) as TAX_SORT,max(ACCOUNT_ALLOT) as ACCOUNT_ALLOT,max(SUBJECTS) as SUBJECTS,max(CASH_GROUP) as CASH_GROUP,max(PAY_WAY) as PAY_WAY,max(PAY_TERMS) as PAY_TERMS,max(CMD) as CMD, " +
					"max(CREATOR) as CREATOR,max(CREATOR_TIME) as CREATOR_TIME,max(APPLY_TIME) as APPLY_TIME,max(APPROVED_TIME) as APPROVED_TIME,max(EXAMINE_STATE) as EXAMINE_STATE,max(EXAMINE_STATE) as EXAMINE_STATE_D_EXAMINE_STATE ,max(LICENSEPATH) as LICENSEPATH,max(ORGANIZATIONPATH) as ORGANIZATIONPATH,max(TAXATIONPATH) as TAXATIONPATH,max(SURVEYPATH) as SURVEYPATH,max(FINANCIALPATH) as FINANCIALPATH,max(CREDITPATH) as CREDITPATH,max(OTHERPATH) as OTHERPATH, " +
			"max(TYPE) as TYPE,max(CODE) as CODE,max(FORMED) as FORMED,max(CAPITAL) as CAPITAL,max(NATURE) as NATURE,max(COMPANYTYPE) as COMPANYTYPE,max(PERIODSTART) as PERIODSTART,max(PERIODEND) as PERIODEND,max(ANNUALINSPECTION) as ANNUALINSPECTION,max(OTHERINFO) as OTHERINFO,max(UPDATETIME) as UPDATETIME,max(ts.dept_name) as CREATEDEPT,max(GUESTID) as GUESTID,max(CREATEOR) as CREATEOR,max(CAPITALCURRENCY) as CAPITALCURRENCY, " );
			sb.append(" max(sm.GUEST_CODE) as GUEST_CODE,max(ts.DEPTID) as DEPT_ID ");
			sb.append(" from ycustomermaster sm ");			
			sb.append(" left join ( select a.deptid,a.GUESTCODE,b.dept_name from ydeptbycusu a ,t_sys_dept b where a.deptid=b.dept_id and trim(GUESTCODE) is not null ) ts  on ts.GUESTCODE=sm.GUEST_CODE ");
			
			sb.append(" where 1 = 1 ");							
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
//			String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
//			if(SEARCHTERM != "")
//			{
//				sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
//			}
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
			String grid_columns = "CUSTOMERMASTER_ID,ACCOUNT_GROUP,COMPANY_CODE,SALES_ORG,TITLE,GUEST_CODE,GUEST_NAME,ADDRESS,SEARCH_TERM,CITY,COUNTRY,AREA,VAT_NUM,BUSINESS_NO,ZIP_CODE,PHONE,PHONE_EXT,FAX,CONTACT_MAN,EMAIL,SAP_SUPPLIERS_CODE,SAP_SUPPLIERS_NAME,CURRENCY,TAX_SORT,ACCOUNT_ALLOT,SUBJECTS,CASH_GROUP,PAY_TERMS,PAY_WAY,CMD,DEPT_ID,CREATOR,CREATOR_TIME,APPLY_TIME,APPROVED_TIME,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,LICENSEPATH,ORGANIZATIONPATH,TAXATIONPATH,SURVEYPATH,FINANCIALPATH,CREDITPATH,OTHERPATH,TYPE,CODE,FORMED,CAPITAL,NATURE,COMPANYTYPE,PERIODSTART,PERIODEND,ANNUALINSPECTION,OTHERINFO,UPDATETIME,GUESTID,CREATEDEPT,CREATEOR,CAPITALCURRENCY";
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				grid_sql += " and ts.deptid in ( "+ userContext.getGrantedDepartmentsId() + ")";
			} else {
				grid_sql += " and ts.deptid = '"	+ userContext.getSysDept().getDeptid() + "'";
			}
//			grid_sql = grid_sql +" order by creator_time desc";
			grid_sql = grid_sql +" group by CUSTOMERMASTER_ID,creator_time";
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
		       String detailName = "customermaster";
		       String col = ReportUtil.getProperty(detailName);
		       
		       ExtComponentServlet servlet = new ExtComponentServlet();
				UserContext userContext = UserContextHolder.getLocalUserContext()
						.getUserContext();
				Map<String, String> map = extractFR(request);
				StringBuffer sb = new StringBuffer();
				sb.append("select max(CUSTOMERMASTER_ID) as CUSTOMERMASTER_ID,max(ACCOUNT_GROUP) as ACCOUNT_GROUP ,max(COMPANY_CODE) as COMPANY_CODE,max(SALES_ORG) as SALES_ORG,max(TITLE) as TITLE ,max(GUEST_NAME) as GUEST_NAME,max(ADDRESS) as ADDRESS,max(SEARCH_TERM) as SEARCH_TERM,max(CITY) as CITY,max(COUNTRY) as COUNTRY,max(AREA) as AREA,max(VAT_NUM) as VAT_NUM,max(BUSINESS_NO) as BUSINESS_NO,max(ZIP_CODE) as ZIP_CODE,max(PHONE) as PHONE,max(PHONE_EXT) as PHONE_EXT,max(FAX) as FAX,max(CONTACT_MAN) as CONTACT_MAN,max(EMAIL) as EMAIL,max(SAP_SUPPLIERS_CODE) as SAP_SUPPLIERS_CODE,max(SAP_SUPPLIERS_NAME) as SAP_SUPPLIERS_NAME,max(CURRENCY) as CURRENCY,max(TAX_SORT) as TAX_SORT,max(ACCOUNT_ALLOT) as ACCOUNT_ALLOT,max(SUBJECTS) as SUBJECTS,max(CASH_GROUP) as CASH_GROUP,max(PAY_WAY) as PAY_WAY,max(PAY_TERMS) as PAY_TERMS,max(CMD) as CMD, " +
						"max(CREATOR) as CREATOR,max(CREATOR_TIME) as CREATOR_TIME,max(APPLY_TIME) as APPLY_TIME,max(APPROVED_TIME) as APPROVED_TIME,max(EXAMINE_STATE) as EXAMINE_STATE,max(EXAMINE_STATE) as EXAMINE_STATE_D_EXAMINE_STATE , " +
						" CASE WHEN ( trim(max(sm.licensepath)) is null ) THEN '否'  ELSE '是' END licensepath, "+
						"CASE WHEN ( trim(max(sm.ORGANIZATIONPATH)) is null ) THEN '否'  ELSE '是' END ORGANIZATIONPATH, "+
						"CASE WHEN ( trim(max(sm.TAXATIONPATH)) is null ) THEN '否'  ELSE '是' END TAXATIONPATH, "+
						"CASE WHEN ( trim(max(sm.SURVEYPATH)) is null ) THEN '否'  ELSE '是' END SURVEYPATH, "+
						"CASE WHEN ( trim(max(sm.FINANCIALPATH)) is null ) THEN '否'  ELSE '是' END FINANCIALPATH, "+
						"CASE WHEN ( trim(max(sm.CREDITPATH)) is null ) THEN '否'  ELSE '是' END CREDITPATH, "+
						"CASE WHEN ( trim(max(sm.OTHERPATH)) is null ) THEN '否'  ELSE '是' END OTHERPATH, "+
				"max(TYPE) as TYPE,max(CODE) as CODE,max(FORMED) as FORMED,max(CAPITAL) as CAPITAL,max(NATURE) as NATURE,max(COMPANYTYPE) as COMPANYTYPE,max(PERIODSTART) as PERIODSTART,max(PERIODEND) as PERIODEND,max(ANNUALINSPECTION) as ANNUALINSPECTION,max(OTHERINFO) as OTHERINFO,max(UPDATETIME) as UPDATETIME,max(CREATEDEPT) as CREATEDEPT,max(GUESTID) as GUESTID,max(CREATEOR) as CREATEOR,max(CAPITALCURRENCY) as CAPITALCURRENCY, " );
				sb.append(" max(sm.GUEST_CODE) as GUEST_CODE,max(ts.DEPTID) as DEPT_ID ");
				sb.append(" from ycustomermaster sm ");			
				sb.append(" left join ( select deptid,GUESTCODE from ydeptbycusu where trim(GUESTCODE) is not null ) ts  on ts.GUESTCODE=sm.GUEST_CODE ");
				sb.append(" where 1 = 1 ");							
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
//				String SEARCHTERM = map.get("SEARCH_TERM") == null ? "" : map.get("SEARCH_TERM");
//				if(SEARCHTERM != "")
//				{
//					sb.append("and SEARCH_TERM = '" + SEARCHTERM + "'");
//				}
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
//				grid_sql = grid_sql +" order by creator_time desc";
				grid_sql = grid_sql +" group by CUSTOMERMASTER_ID,creator_time";
		       
		      
				
		       String[] cols = col.split(",");
		       String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
		       String[] colNames = colName.split(",");
		       InputStream is = this.reportService.createExcelFile(cols, colNames, grid_sql.toString(), null);
		       ReportUtil util = new ReportUtil();
		       util.download(is, "客户信息.xls", response);
		       
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
			CustomerMaster db = this.customerMasterService.find(id);
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
			this.customerMasterService.saveOrUpdate(db);
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
		public void save(HttpServletRequest request, HttpServletResponse response,CustomerMaster info) throws IOException{
			
			UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
			if(StringUtils.isEmpty(info.getCustomerId())){
				info.setCustomerId(CodeGenerator.getUUID());
				info.setCreatorTime(DateUtils.getCurrTime(2));
				info.setCreator(context.getSysUser().getUserId());		
				
				info.setCreateor(context.getSysUser().getUserName());
				info.setCreateDept(context.getSysDept().getDeptcode());
				info.setIsAvailable("1");
				info.setExamineState("3");
				info.setDeptId(context.getSysDept().getDeptid());
			}else{
				info.setUpdateTime(DateUtils.getCurrTime(2));
				CustomerMaster cu=this.customerMasterService.find(info.getCustomerId());
//				修改编号时，要同时修改部门权限表
				if(StringUtils.isNotBlank(info.getGuestCode()) && StringUtils.isNotBlank(cu.getGuestCode()) && !cu.getGuestCode().equals(info.getGuestCode())){
					this.customerMasterService.updateDeptNo(info.getGuestCode(), cu.getGuestCode());
				}
			}
			
			this.customerMasterService.saveOrUpdate(info);
			
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
		
		
		/**
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control","no-cache");
			String id = request.getParameter("id");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 取得上传的文件流对象
			MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
			String fileName = "";
			String newName="";
			
			// 判断request是不是multipart请求:
			if (request instanceof MultipartHttpServletRequest)
			{}
			else
			{
				// 业务附件上传失敗，当前request不是multipart请求！
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, Constants.UpLoadFailure);
			}
				
			if (multipartFile != null && !multipartFile.isEmpty()){
				p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
				fileName = multipartFile.getOriginalFilename();
				fileName = CodeGenerator.getUUID() + "_" + fileName;
				newName = multipartFile.getOriginalFilename();
				long fileSize = multipartFile.getSize();
				String file_size = (String)p.get("file_size");
				if(fileSize>Long.parseLong(file_size)*1024)
					throw new BusinessException("文件不能超过"+file_size+"KB");
				try {
					//String typeStr = "File";
					String currentPath=p.getProperty("file_path");//"/UserFiles/"+typeStr;
//					String sonDir = DateUtils.getYearMonth(DateUtils.HYPHEN_DISPLAY_DATE);
					String sonDir =id;
					File file = new File(currentPath+File.separator+sonDir);
				    if (!file.exists()&&!file.isDirectory()) 
			             file.mkdirs();
					//String currentDirPath=getServletContext().getRealPath(currentPath);
					//currentPath=request.getContextPath()+currentPath;
					
					String nameWithoutExt=getNameWithoutExtension(fileName);
					String ext=getExtension(fileName);
					File pathToSave=new File(currentPath+File.separator+sonDir,fileName);
					String fileUrl=currentPath+File.separator+sonDir+"/"+fileName;
					
					int counter=1;
					while(pathToSave.exists()){
						newName=nameWithoutExt+"("+counter+")"+"."+ext;
						fileUrl=currentPath+"/"+newName;
						pathToSave=new File(currentPath+File.separator+sonDir,newName);
						counter++;
					}
					multipartFile.transferTo(pathToSave);
					newName=sonDir+File.separator+newName;
				
				}catch (Exception ex) {
					throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, ex.toString());
					
				}
			}
			


			JSONObject js = new JSONObject();
			js.put("success", "true");
			js.put("msg", "上传成功！");
			js.put("path", fileName);
			js.put("id", id);
//			this.operateSuccessfullyWithString(response, js.toString());
			response.getWriter().write(js.toString());			
		}
		
		/*
		 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
		 */
	  	private static String getNameWithoutExtension(String fileName) {
	    		return fileName.substring(0, fileName.lastIndexOf("."));
	    	}
	    	
		/*
		 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF bug #991489
		 */
		private String getExtension(String fileName) {
			return fileName.substring(fileName.lastIndexOf(".")+1);
		}
		
		/**
		 * 
		 * @param req
		 * @param resp
		 * @throws ServletException
		 * @throws IOException
		 */
		public void proceeFileDownload(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
			String fileName = java.net.URLDecoder.decode(req.getParameter("fileName"),"UTF-8");
			String path = java.net.URLDecoder.decode(req.getParameter("path"),"UTF-8");
			fileName = path +"/" + fileName;
			try {
//				PropertiesUtil p = new PropertiesUtil(path);
				p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
				String dir = p.getProperty("file_path");
				File file = new File(dir);
				String fileServerPath = dir;
				if(!file.isAbsolute()){
					fileServerPath = "http://" + req.getServerName() + ":"+ req.getServerPort() + req.getContextPath() + fileServerPath+"/"+fileName;
				}
				requestFile(fileServerPath, resp, fileName);
			}catch(FileNotFoundException e){
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write("<script>window.alert('文件"+fileName+"不存在,请联系系统管理员')</script>");
			}
		}
		
		/**
		 * 获取请求文件
		 * @param url
		 * @param resp
		 * @param fileName
		 * @throws IOException
		 */
		public void requestFile(String url,HttpServletResponse resp,String fileName) throws IOException{
			File file = new File(url);
			if(!file.isDirectory()){
				URL httpUrl = new URL(url);
				URLConnection conn = httpUrl.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				InputStream	inStream = conn.getInputStream();
				download(inStream, fileName, resp);
			}else{
				InputStream in = new FileInputStream(url+"/"+fileName);
				download(in, fileName, resp);
			}
		}
		
		/**
		 * 下载主程序
		 * @param inStream
		 * @param filename
		 * @param response
		 * @throws IOException
		 */
		private void download(InputStream inStream, String filename,HttpServletResponse response) throws IOException{
			byte[] b = new byte[10 * 1024];
			int len = 0;
			try {
				// 设置输出的格式
				response.reset();
				response.setCharacterEncoding("utf-8");
				// 开始解析
				response.setContentType("application/x-download;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
				// 循环取出流中的数据
				while ((len = inStream.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
				response.getOutputStream().close();
				inStream.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}finally{
				response.flushBuffer();
			}
		}
		
		
		/**
		 * 删除文件
		 * @param req
		 * @param resp
		 * @throws ServletException
		 * @throws IOException
		 */
		public void proceeFileDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
			String callBack = req.getParameter("callback");
//			PropertiesUtil p = new PropertiesUtil(path);
			p = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
			String dir =p.getProperty("file_path");
			String fileName = req.getParameter("fileName");
			String path = java.net.URLDecoder.decode(req.getParameter("path"),"UTF-8");
			fileName = path +"/" + fileName;
			String creator = req.getParameter("creator");
			String name = fileName;
			File file = new File(dir);
			fileName = dir+"/"+name;
			if(!file.isAbsolute()){
				fileName = req.getRealPath(dir+"/"+name);
			}
			file = new File(fileName);
			resp.setContentType("text/html; charset=UTF-8");
			JSONObject js = new JSONObject();
			if(file.exists()){
				UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
				String userid = userContext.getSysUser().getUserId();
				if(userid.equals(creator)){
					file.delete();
//					resp.getWriter().write(callBack + "('删除成功')");					
					js.put("success", true);
					js.put("msg", "删除成功！");
					this.operateSuccessfullyWithString(resp, js.toString());
				}else{
//					resp.getWriter().write(callBack + "('您不是文件创建者，不允许删除该文件！')");
					js.put("success", false);
					js.put("msg", "您不是文件创建者，不允许删除该文件！！");
					this.operateSuccessfullyWithString(resp, js.toString());
				}
			}else 
				js.put("success", false);
				js.put("msg", "该文件不存在！！");
//				resp.getWriter().write(js.toString());
				this.operateSuccessfullyWithString(resp, js.toString());
//			    resp.getWriter().write(callBack + "('"+name+"不存在!')");
		}
		
}
