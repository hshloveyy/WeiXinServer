package com.infolion.sapss.sqlReport.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.EnhanceMultiActionController;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.web.PaymentImportsInfoQueryVO;
import com.infolion.sapss.sqlReport.domain.TSqlElement;
import com.infolion.sapss.sqlReport.domain.TSqlFieldDf;
import com.infolion.sapss.sqlReport.service.SqlReportService;

public class SqlReportController extends EnhanceMultiActionController{
	
	private final Log log = LogFactory.getLog(SqlReportController.class);
	
	
	@Autowired
	private SqlReportService sqlReportService;
	
	
	public SqlReportService getSqlReportService() {
		return sqlReportService;
	}

	public void setSqlReportService(SqlReportService sqlReportService) {
		this.sqlReportService = sqlReportService;
	}

	public ModelAndView manageView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException{
		return new ModelAndView("sapss/sqlReport/sqlReportManage");
	}
	
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		Map<String, String> filter = extractFR(request);
		String sql = sqlReportService.getQuerySqlSql(filter);
		String grid_columns = "SQLELEMENTID,NAME,CMD,CREATERTIME";
		String grid_size = "10";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public ModelAndView addSqlInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TSqlElement info =  sqlReportService.addSqlElementInfo();
		request.setAttribute("info", info);
		return new ModelAndView("sapss/sqlReport/sqlReportInfo");
	}
	
	
	public ModelAndView viewSqlInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sqlElementId = request.getParameter("sqlElementId");
		TSqlElement info =  sqlReportService.getSqlElementInfo(sqlElementId);
		request.setAttribute("info", info);
		return new ModelAndView("sapss/sqlReport/sqlReportInfo");
	}
	
	public void queryField(HttpServletRequest request,
			HttpServletResponse response) throws Exception	{
		String elementId = request.getParameter("elementId");
		String grid_sql = "select t.* from t_sql_fielddf t where t.SQLELEMENTID ='"+elementId+"'order by t.orderNo";
		String grid_columns = "SQLFIELDDFID,SQLELEMENTID,FILEDNAME,FILEDTYPE,DICNAME,ISSHOW,ISQUERY,ORDERNO,FILEDSHOWNAME,WIDTH";
		String grid_size = "10";		
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);	
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public void updateShip(HttpServletRequest request,
			HttpServletResponse response, TSqlElement info) throws IOException	{
		sqlReportService.updateSqlElementInfo(info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void analySqlElement(HttpServletRequest request,
			HttpServletResponse response, TSqlElement info) throws IOException	{
		sqlReportService.analySqlElementInfo(info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void updateSqlField(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String sqlFieldId = request.getParameter("sqlFieldId");
		String colName = request.getParameter("colName");
		String colValue = extractFR(request, "colValue");
		JSONObject jsonObject = new JSONObject();
		sqlReportService.updateSqlField(sqlFieldId, colName, colValue);

		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	
	public ModelAndView viewSqlReport(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sqlElementId = request.getParameter("sqlElementId");
		TSqlElement info =  sqlReportService.getSqlElementInfo(sqlElementId);
		request.setAttribute("info", info);
		List<TSqlFieldDf> list =  sqlReportService.getSqlFieldDfList(sqlElementId);
		request.setAttribute("list", list);
		return new ModelAndView("sapss/sqlReport/sqlReportIns");
	}
	
	public void queryIns(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		String sqlElementId = request.getParameter("sqlElementId");
		TSqlElement info =  sqlReportService.getSqlElementInfo(sqlElementId);
		List<TSqlFieldDf> list =  sqlReportService.getSqlFieldDfList(sqlElementId);
		String grid_columns = "";
		Map<String, String> filter = extractFR(request);
		
		for(TSqlFieldDf fdf : list){				
			String filedName = fdf.getFiledName();
			grid_columns += filedName+"," ;
		}
		grid_columns = grid_columns.substring(0,grid_columns.length()-1);
		String grid_size = "10";
		request.setAttribute("grid_sql", getQueryInsSql(filter, info, list));
		request.setAttribute("grid_columns", grid_columns.toUpperCase());
		request.setAttribute("grid_size", grid_size);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public void dealOutToExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sqlElementId = request.getParameter("sqlElementId");
		TSqlElement info =  sqlReportService.getSqlElementInfo(sqlElementId);
		List<TSqlFieldDf> list =  sqlReportService.getSqlFieldDfList(sqlElementId);
		Map<String, String> filter = extractFR(request);
		List<String> showFields = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		for(TSqlFieldDf fdf : list){				
			if("1".equals(fdf.getIsShow())){
				showFields.add(fdf.getFiledShowName());
				fields.add(fdf.getFiledName());
			}
		}
		String[] titles  =  (String[])showFields.toArray(new String[0]);

		ExcelObject excel = new ExcelObject(titles);
        String sql = getQueryInsSql(filter, info, list);
        sqlReportService.dealOutToExcel(sql ,excel,fields);		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode(info.getName()+".xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getQueryInsSql(Map<String, String> filter,TSqlElement info,List<TSqlFieldDf> list){
		StringBuffer sqlBf = new StringBuffer("");
		sqlBf.append("select * from (").append(info.getSql()).append(") ins where 1=1 ");
		for(TSqlFieldDf fdf : list){
			if("1".equals(fdf.getIsQuery())){
				
				String filedType = fdf.getFiledType();
				String filedName = fdf.getFiledName();
				//grid_columns += filedName+"," ;
				//字段类型 0字符、1数值、2日期、3部门、4数据字典、5搜索帮助
				if("0".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and ins."+filedName+" like '%"+filter.get(fdf.getFiledName()) +"%'");
					}
				}else if("1".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and ins."+filedName+" >= "+filter.get(fdf.getFiledName()) +"");
					}
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()+"1"))) {
						sqlBf.append(" and ins."+filedName+" <= "+filter.get(fdf.getFiledName()+"1") +"");
					}
				}else if("2".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and replace(ins."+filedName+",'-','') >= '"+filter.get(fdf.getFiledName()).replaceAll("-", "") +"'");
					}
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()+"1"))) {
						sqlBf.append(" and replace(ins."+filedName+",'-','') <= '"+filter.get(fdf.getFiledName()+"1").replaceAll("-", "") +"'");
					}
				}else if("3".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and ins."+filedName+" in ('"+filter.get(fdf.getFiledName()).replaceAll(",", "','") +"')");
					}
				}else if("4".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and ins."+filedName+" = '"+filter.get(fdf.getFiledName()) +"'");
					}
				}else if("5".equals(filedType)){
					if (StringUtils.isNotBlank(filter.get(fdf.getFiledName()))) {
						sqlBf.append(" and ins."+filedName+" = '"+filter.get(fdf.getFiledName()) +"'");
					}
				}
			}
		}
		if("1".equals(info.getIsDeptAuthority())){
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			sqlBf.append(" and ins.dept_id in ( " + userContext.getGrantedDepartmentsId() + ",");
			sqlBf.append("'" + userContext.getSysDept().getDeptid() + "')");
		}
		return sqlBf.toString();
	}

}
