/*
 * @(#)ReportUtil.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-10-28
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.JsonUtils;
import com.infolion.platform.util.PropertiesUtil;

public class ReportUtil {
	public void download(InputStream inStream, String filename,HttpServletResponse response) throws IOException{
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
		} catch (IOException e) {
		}finally{
//			String info = JsonUtils.generateMessageJson("info","导出结束");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter writer = response.getWriter();
//			writer.write(info);
			response.getOutputStream().close();
			response.flushBuffer();
			inStream.close();
		}
	}
	private static Properties p;
	public static String getProperty(String property){
		if(p==null)
			load();
		return p.getProperty(property);
		
	}
	private static void load(){
		p = new Properties();
		try {
			p.load(ReportUtil.class.getResourceAsStream("/com/infolion/sapss/masterQuery/report.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * excel查询语句
	 * @param detailName
	 * @param request
	 * @param fileName
	 * @param response
	 * @param dataList
	 * @return
	 * @throws IOException
	 */
	public static String queryForExcel(boolean isTable,String detailName,HttpServletRequest request,String col) throws IOException{
		String sql="select "+col+" from v_"+detailName+" where 1=1 ";
		if(isTable)
			sql="select "+col+" from t_v_"+detailName+" where 1=1 ";
		if(StringUtils.isNotBlank(request.getParameter("contract")))
			sql+=" and contract_no='"+request.getParameter("contract")+"'";
		else if(StringUtils.isNotBlank(request.getParameter("contract_no")))
			sql+=" and contract_no='"+request.getParameter("contract_no")+"'"; 
		if(StringUtils.isNotBlank(request.getParameter("contractGroup")))
			sql+=" and contract_group_no='"+request.getParameter("contractGroup")+"'";
		else if(StringUtils.isNotBlank(request.getParameter("contract_group_no")))
			sql+=" and contract_group_no='"+request.getParameter("contract_group_no")+"'";
		if(StringUtils.isNotBlank(request.getParameter("projectNo")))
			sql+=" and project_no='"+request.getParameter("projectNo")+"'";
		else if(StringUtils.isNotBlank(request.getParameter("project_no")))
			sql+=" and project_no='"+request.getParameter("project_no")+"'";
		if(StringUtils.isNotBlank(request.getParameter("deptId")))
			sql+=" and dept_id='"+request.getParameter("deptId")+"'";
		if(StringUtils.isNotBlank(request.getParameter("projectTimeStart"))){
			sql +=" and approved_time >='"+request.getParameter("projectTimeStart")+"000000'";
		if(StringUtils.isNotBlank(request.getParameter("projectTimeEnd")))
			sql +=" and approved_time <='"+request.getParameter("projectTimeEnd")+"240000'";
		}
		if(StringUtils.isNotBlank(request.getParameter("paperContract")))//纸质合同
			sql +=" and old_contract_no like '%"+request.getParameter("paperContract")+"%'";

		if(StringUtils.isBlank(request.getParameter("contract"))
				&&StringUtils.isBlank(request.getParameter("contractGroup"))
				&&StringUtils.isBlank(request.getParameter("projectNo"))
				&&StringUtils.isBlank(request.getParameter("projectTimeStart"))
				&&StringUtils.isBlank(request.getParameter("deptId"))//
				&&StringUtils.isBlank(request.getParameter("project_no"))//详细页面参数
				&&StringUtils.isBlank(request.getParameter("contract_group_no"))//详细页面参数
				&& StringUtils.isBlank(request.getParameter("paperContract"))
				&&StringUtils.isBlank(request.getParameter("contract_no"))//详细页面参数
				)
			sql +=" and approved_time like '"+DateUtils.getDateFormat(new Date(), "yyyy")+"%'";
		if("home_ship_goods".equals(detailName)&&StringUtils.isNotBlank(request.getParameter("relater"))){
			sql +=" and kuagv_kunnr_name like '%"+request.getParameter("relater")+"%'";
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";
		
		if (col.indexOf("dept_name") != -1 || col.indexOf("DEPT_NAME") != -1){
			sql = sql + " order by dept_name";
			
			if (col.indexOf("contract_no") != -1 || col.indexOf("contract_no") != -1){
				sql = sql + ",contract_no";
			}
		}

		return sql;
	}	
	/**
	 * 主信息查询条件
	 * @param request
	 * @param response
	 * @return
	 */
	public static String queryCondiction(HttpServletRequest request,HttpServletResponse response){
		Map map = getParameterMap(request);
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		String condiction="";
		if(StringUtils.isNotBlank(request.getParameter("projectNo")))
			condiction +=" and project_no='"+request.getParameter("projectNo")+"'";
		if(StringUtils.isNotBlank(request.getParameter("deptId"))){//部门
			if("1".equals(uc.getSysDept().getIsFuncDept()))
				condiction +=" and dept_id in('"+request.getParameter("deptId")+"')";
			else
				condiction +=" and dept_id='"+request.getParameter("deptId")+"'";
		}
		if(StringUtils.isNotBlank(request.getParameter("projectTimeStart")) 
				&& StringUtils.isNotBlank(request.getParameter("projectTimeEnd"))){//立项生效时间
			condiction +=" and approved_time between '"+request.getParameter("projectTimeStart")+"'";
			condiction +=" and '"+request.getParameter("projectTimeEnd")+"'";
		}else if(StringUtils.isNotBlank(request.getParameter("projectTimeStart"))){
			condiction +=" and approved_time like '%"+request.getParameter("projectTimeStart")+"%'";
		}else if(StringUtils.isBlank(request.getParameter("projectTimeStart")) //
				&& StringUtils.isBlank(request.getParameter("projectNo"))
				&& StringUtils.isBlank(request.getParameter("deptId"))
				&& StringUtils.isBlank(request.getParameter("projectTimeEnd"))
				&& StringUtils.isBlank(request.getParameter("relater"))
				&& StringUtils.isBlank(request.getParameter("contractGroup"))
				&& StringUtils.isBlank(request.getParameter("paperContract"))
				&& StringUtils.isBlank(request.getParameter("contract"))
				&& StringUtils.isBlank(request.getParameter("tradeType"))
				&& StringUtils.isBlank(request.getParameter("material"))
		){
			condiction +=" and approved_time like '"+DateUtils.getDateFormat(new Date(), "yyyy")+"%'";
		}
		if(StringUtils.isNotBlank(request.getParameter("relater")))//客户-供应商
			condiction +=" and relater like '%"+map.get("relater")+"%'";
		if(StringUtils.isNotBlank(request.getParameter("contractGroup")))//合同组
			condiction +=" and contract_group_no like '%"+request.getParameter("contractGroup")+"%'";
		if(StringUtils.isNotBlank(request.getParameter("paperContract")))//纸质合同
			condiction +=" and old_contract_no like '%"+request.getParameter("paperContract")+"%'";
		if(StringUtils.isNotBlank(request.getParameter("contract")))//合同
			condiction +=" and contract_no like '%"+request.getParameter("contract")+"%'";
		if(StringUtils.isNotBlank(request.getParameter("tradeType")))//贸易方式
			condiction +=" and trade_id='"+request.getParameter("tradeType")+"'";
		if(StringUtils.isNotBlank(request.getParameter("sapOrderNo")))//
			condiction +=" and sap_order_no like '%"+request.getParameter("sapOrderNo")+"%'";
		
		//如果立项状态没有选择就默认是“变更通过”，“生效”，“变更”
		if(StringUtils.isNotBlank(request.getParameter("projectState"))){
			condiction += " and project_state = '" + request.getParameter("projectState") + "'";
		}else{
			condiction += " and project_state in ('8','3','6')";
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		condiction += " and dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		condiction += "'" + userContext.getSysDept().getDeptid() + "')";

		return condiction;
	}	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, String> getParameterMap(HttpServletRequest request){
		Map<String, String> parameter = new HashMap<String,String>();
		Map<?, ?> parameterMap = request.getParameterMap();
		Iterator<?> keySet = parameterMap.keySet().iterator();
		for(;keySet.hasNext();){
			String key = keySet.next().toString();
			if(parameterMap.get(key)!=null){
				try{
					String value[]=(String[])parameterMap.get(key);
					if(value.length>0&&value[0].length()>0){
						value[0] = new String(value[0].getBytes("ISO-8859-1"),"UTF-8");
						parameter.put(key, value[0]);
					}
				}catch(Exception e){
				}
			}
		}
		return parameter;
	}
	
}
