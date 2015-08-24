/*
 * @(#)ProfitLossController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点48分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLoss.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.infolion.xdss3.profitLoss.domain.ProfitLoss;
import com.infolion.xdss3.profitLossGen.web.ProfitLossControllerGen;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;

/**
 * <pre>
 * 合同信息及市场单价维护(ProfitLoss)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@BDPController(parent = "baseMultiActionController")
public class ProfitLossController extends ProfitLossControllerGen
{
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
    @Autowired
    private ReportService reportService;
    
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
	/**
	 * 保存  
	 *   
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		String strProfitlossidList = request.getParameter("profitlossidlist");
		String strMarketunitpriceList = request.getParameter("marketunitpricelist");
		String strMaxlosscommentList = request.getParameter("maxlosscommentList");
		String strPositionvalueList = request.getParameter("positionvaluelist");
		this.profitLossService.saveChange(strProfitlossidList, strMarketunitpriceList, strMaxlosscommentList, strPositionvalueList);
		//Set<ProfitLoss> profitLossSet = ExBeanUtils.bindModifyBoData(request.getParameterMap(), ProfitLoss.class);
		//this.profitLossService._saveOrUpdate(profitLossSet, getBusinessObject());
		this.operateSuccessfullyWithString(response,jo.toString());
	}	
	
	/**
	 * 重新读取  
	 *   
	 * @param request
	 * @param response
	 */
	public void _reload (HttpServletRequest request, HttpServletResponse response)
	{
		String companyId = request.getParameter("companyid.fieldValue");
		String dateValue = request.getParameter("datevalue.fieldValue");
		String deptId = request.getParameter("deptid.fieldValue");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> tbMap = new HashMap<String,Object>();
		map.put("BUKRS", companyId);
		map.put("BUDAT", dateValue);
		tbMap.put("WERKS", deptId);
		profitLossService._reload(map,tbMap);
		this.operateSuccessfully(response);      
	}
	
	/**
     * 导出Excel
     * 
     * @param request
     * @param response
     */
    public void _expExcel(HttpServletRequest request, HttpServletResponse response)
    {
        response.setHeader("Content-Type","application/force-download");
        response.setHeader("Content-Type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=export.xls");
        String exportContent = request.getParameter("exportContent");
        try
        {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(exportContent); 
            System.out.println(exportContent);
        }
        catch (IOException e)
        {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
    /**
     * POI导出Excel
     * @param detailName
     * @param request
     * @param fileName
     * @param response
     * @throws IOException
     */
    public void detailToExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String detailName = request.getParameter("detailName");
        String col = ReportUtil.getProperty(detailName);
        String sql = this.genSQLForExcel(request, col);
        String[] cols = col.split(",");
        String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
        String[] colNames = colName.split(",");
        InputStream is = this.reportService.createExcelFile(cols, colNames, sql, null);
        ReportUtil util = new ReportUtil();
        if ("profitlossmng".equals(detailName)) {
            util.download(is, "存货浮动盈亏表(贸管).xls", response);
        } else if ("profitloss".equals(detailName)) {
            util.download(is, "存货浮动盈亏表(业务).xls", response);
        } else {
            util.download(is, "book1.xls", response);
        }
        
        is.close();
    }
    
    /**
     * excel查询语句
     * @param request
     * @param fileName
     * @param response
     * @param dataList
     * @return
     * @throws IOException
     */
    private String genSQLForExcel(HttpServletRequest request,String col) throws IOException{
        String sql="select "+col+", o.organizationname as organizationname from  yprofitloss t   left join YCOMPANYINFO c  on (t.companyid = c.companyid)  left join YORGANIZATION o   on (t.deptid = o.organizationid)  left join YMATGROUP m    on (t.material_group = m.matkl) where 1=1 ";
        if(StringUtils.isNotBlank(request.getParameter("companyid.fieldValue")))
            sql+=" and t.companyid='"+request.getParameter("companyid.fieldValue")+"'";
        if(StringUtils.isNotBlank(request.getParameter("deptid.fieldValue")))
            sql+=" and t.deptid='"+request.getParameter("deptid.fieldValue")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("datevalue.fieldValue")))
            sql+=" and t.datevalue='"+request.getParameter("datevalue.fieldValue").replaceAll("-", "")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("project_no.fieldValue")))
            sql+=" and t.project_no='"+request.getParameter("project_no.fieldValue")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("type.fieldValue")))
            sql+=" and t.type='"+URLDecoder.decode(request.getParameter("type.fieldValue"), "UTF-8")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("material_group.fieldValue")))
            sql+=" and t.material_group='"+request.getParameter("material_group.fieldValue")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("BATCH_NO.fieldValue")))
            sql+=" and t.batch_no='"+request.getParameter("BATCH_NO.fieldValue")+"'"; 
        
        // 部门的过滤
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        sql += " and t.deptid in (" + userContext.getGrantedDepartmentsId()
                + ",";
        sql += "'" + userContext.getSysDept().getDeptid() + "') order by t.Datevalue, t.Deptid, t.type desc,  t.project_no, t.contractno, t.profitlossid ";
        

        return sql;
    }
    
    /**
     * POI导出Excel
     * @param detailName
     * @param request
     * @param fileName
     * @param response
     * @throws IOException
     */
    public void detailToExcel_matnr(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String detailName = request.getParameter("detailName");
        String col = ReportUtil.getProperty(detailName);
        String sql = this.genSQLForExcel_matnr(request);
        String[] cols = col.split(",");
        String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
        String[] colNames = colName.split(",");
        InputStream is = this.reportService.createExcelFile(cols, colNames, sql, null);
        ReportUtil util = new ReportUtil();
        util.download(is, "SAP库存业务一览表.xls", response);
        
        is.close();
    }
    
    /**
     * excel查询语句
     * @param request
     * @param fileName
     * @param response
     * @param dataList
     * @return
     * @throws IOException
     */
    private String genSQLForExcel_matnr(HttpServletRequest request) throws IOException{
        
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("ProfitLoss");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YPROFITLOSS", "A");
        } catch (Exception ex) {
        }
        
        // 获取SQL参数
        String deptId = request.getParameter("dept_id"); // 部门ID
        String type = request.getParameter("type"); // 业务类型
        String project_state = request.getParameter("project_state"); // 立项状态
        String ekko_ihrez = request.getParameter("ekko_ihrez"); // 采购合同号
        String ekpo_txz01 = request.getParameter("ekpo_txz01"); // 物料名称
        
        String contract_no = request.getParameter("contract_no"); // 外部约质合同号
        String material_group = request.getParameter("material_group.fieldValue"); // 物料组
        String projectno = request.getParameter("projectno"); // 立项号
        String maturity1 = request.getParameter("maturity1"); // 立项审批通过日期
        String maturity2 = request.getParameter("maturity2"); // 立项审批通过日期2
        
        String approved_time1 = request.getParameter("approved_time1"); // 合同审批通过日期
        String approved_time2 = request.getParameter("approved_time2"); // 合同审批通过日期2
        String companyId = request.getParameter("companyid.fieldValue");
        String dateValue = request.getParameter("datevalue.fieldValue");
        String supplier = request.getParameter("supplier");
        String orderid = request.getParameter("orderid");

        StringBuffer sql = new StringBuffer(
                "SELECT distinct A.*, o.organizationname  as dept_id_text, "
                        + " yc.comanyname as companyid_text, gr.wgbez  as material_group_text  "
                        + "  FROM T_TEMP_PROFITLOSS A left join YORGANIZATION o on ( a.deptid=o.organizationid ) "
                        + "                                                  left join YCOMPANYINFO yc on (a.companyid=yc.companyid)" 
                        + "                                                  left join YMATGROUP gr on (gr.matkl = a.material_group) ");
        if (StringUtils.isNotBlank(project_state)
                || StringUtils.isNotBlank(maturity1)
                || StringUtils.isNotBlank(maturity2) || StringUtils.isNotBlank(approved_time1)
                || StringUtils.isNotBlank(approved_time2)) {
                      sql.append(",  "
                        + "       T_CONTRACT_PURCHASE_INFO     B,"
                        + "       T_CONTRACT_PURCHASE_MATERIEL C,"
                        + "       T_PROJECT_INFO               D "
                        + " WHERE B.CONTRACT_NO = A.CONTRACTNO"
                        + "   AND B.CONTRACT_PURCHASE_ID = C.CONTRACT_PURCHASE_ID"
                        + "   AND D.PROJECT_ID = B.PROJECT_ID ");
        } else {
            sql.append(" where  1=1 ");
        }
        sql.append(strAuthSql);

        
//        // 限制查询条件
//        if  (StringUtils.isNotBlank(deptId) 
//                && StringUtils.isNotBlank(type)
//                && StringUtils.isNotBlank(project_state)
//                && StringUtils.isNotBlank(ekko_ihrez)
//                && StringUtils.isNotBlank(ekpo_txz01)
//                
//                && StringUtils.isNotBlank(contract_no)
////                && StringUtils.isNotBlank(material_group)
//                && StringUtils.isNotBlank(projectno)
//                && StringUtils.isNotBlank(maturity1)
//                && StringUtils.isNotBlank(maturity2)
//                
//                && StringUtils.isNotBlank(approved_time1)
//                && StringUtils.isNotBlank(approved_time2)
//                && StringUtils.isNotBlank(companyId)
//                && StringUtils.isNotBlank(dateValue)
//                ) {
//            sql.append(" and 1=2 ");
//        }
        
        // 设置部门ID条件
        if (StringUtils.isNotBlank(deptId)) {
            sql.append(" AND a.deptid = '" + deptId + "'");
        }
        if (StringUtils.isNotBlank(type)) {
            type = URLDecoder.decode(type, "UTF-8");
            sql.append(" AND a.type LIKE '" + type + "'");
        }
        if (StringUtils.isNotBlank(project_state)) {
            sql.append(" AND d.project_state LIKE '%" + project_state + "%'");
        }
        if (StringUtils.isNotBlank(ekko_ihrez)) {
            sql.append(" AND a.ekko_unsez LIKE '%" + ekko_ihrez + "%'");
        }
        if (StringUtils.isNotBlank(ekpo_txz01)) {
            ekpo_txz01 = URLDecoder.decode(ekpo_txz01, "UTF-8");
            sql.append(" AND a.meterial_name LIKE '%" + ekpo_txz01 + "%'");
        }
        if (StringUtils.isNotBlank(contract_no)) {
            contract_no = URLDecoder.decode(contract_no, "UTF-8");
            sql.append(" AND a.contractno LIKE '%" + contract_no + "%'");
        }
        if (StringUtils.isNotBlank(material_group)) {
            sql.append(" AND a.material_group LIKE '%" + material_group + "%'");
        }
        if (StringUtils.isNotBlank(projectno)) {
            sql.append(" AND a.project_no = '" + projectno + "'");
        }
        if (StringUtils.isNotBlank(companyId)) {
            sql.append(" AND a.companyId = '" + companyId + "'");
        }
        if (StringUtils.isNotBlank(dateValue)) {
            sql.append(" AND a.datevalue = '" + dateValue.replaceAll("-", "") + "'");
        }
        if (StringUtils.isNotBlank(supplier)) {
            sql.append(" AND a.providerid = '" + supplier + "'");
        }
        if (StringUtils.isNotBlank(orderid)) {
            sql.append(" AND a.orderid like '%" + orderid + "%'");
        }
        
        // 设置合同审批通过时间条件
        if (StringUtils.isNotBlank(maturity1) && StringUtils.isNotBlank(maturity2)) {
            sql.append("AND d.approved_time BETWEEN '" + maturity1 + "' AND '" + maturity2 + "'");
        } else if (StringUtils.isNotBlank(maturity1)) {
            sql.append("AND d.approved_time > '" + maturity1 + "'");
        } else if (StringUtils.isNotBlank(maturity2)) {
            sql.append("AND d.approved_time < '" + maturity2 + "'");
        }

        // 设置审批通过时间条件
        if (StringUtils.isNotBlank(approved_time1) && StringUtils.isNotBlank(approved_time2)) {
            sql.append("AND b.approved_time BETWEEN '" + approved_time1 + "' AND '" + approved_time2 + "'");
        } else if (StringUtils.isNotBlank(approved_time1)) {
            sql.append("AND b.approved_time > '" + approved_time1 + "'");
        } else if (StringUtils.isNotBlank(approved_time2)) {
            sql.append("AND b.approved_time < '" + approved_time2 + "'");
        }
        
        sql.append(" order by a.datevalue, a.deptid, a.type desc,  a.project_no, a.contractno, a.profitlossid ");
        
        return sql.toString();
    }
    
    /**
     * 中转综合查询页面
     */
    public ModelAndView _conditionQueryByMatnr(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("xdss3/profitLoss/profitLossQueryByMatnr");
    }
    
    /**
     * 物料多条件查询
     */
    public void queryMatnrGrid(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
 throws Exception {
        gridQueryCondition.setLimit("10");
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("ProfitLoss");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YPROFITLOSS", "T");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        // 获取SQL参数
        String deptId = request.getParameter("dept_id"); // 部门ID
        String type = request.getParameter("type"); // 业务类型
        String project_state = request.getParameter("project_state"); // 立项状态
        String ekko_ihrez = request.getParameter("ekko_ihrez"); // 采购合同号
        String ekpo_txz01 = request.getParameter("ekpo_txz01"); // 物料名称
        String contract_no = request.getParameter("contract_no"); // 外部约质合同号
        String material_group = request.getParameter("material_group.fieldValue"); // 物料组
        String projectno = request.getParameter("projectno"); // 立项号
        String maturity1 = request.getParameter("maturity1"); // 立项审批通过日期
        String maturity2 = request.getParameter("maturity2"); // 立项审批通过日期2
        String approved_time1 = request.getParameter("approved_time1"); // 合同审批通过日期
        String approved_time2 = request.getParameter("approved_time2"); // 合同审批通过日期2
        String companyId = request.getParameter("companyid.fieldValue");
        String dateValue = request.getParameter("datevalue.fieldValue");
        String supplier = request.getParameter("supplier");
        String orderid = request.getParameter("orderid");

        StringBuffer sql = new StringBuffer(
                "SELECT DISTINCT A.*, o.organizationname  as dept_id_text, "
                        + " yc.comanyname as companyid_text, gr.wgbez  as material_group_text "
                        + "  FROM T_TEMP_PROFITLOSS A left join YORGANIZATION o on ( a.deptid=o.organizationid ) "
                        + "                                                  left join YCOMPANYINFO yc on (a.companyid=yc.companyid)"
                        + "                                                  left join YMATGROUP gr on (gr.matkl = a.material_group and gr.mandt='800') ");
        if (StringUtils.isNotBlank(project_state)
                ||  StringUtils.isNotBlank(maturity1)
                || StringUtils.isNotBlank(maturity2) || StringUtils.isNotBlank(approved_time1)
                || StringUtils.isNotBlank(approved_time2)) {
                      sql.append(",  "
                        + "       T_CONTRACT_PURCHASE_INFO     B,"
                        + "       T_CONTRACT_PURCHASE_MATERIEL C,"
                        + "       T_PROJECT_INFO               D "
                        + " WHERE B.CONTRACT_NO = A.CONTRACTNO"
                        + "   AND B.CONTRACT_PURCHASE_ID = C.CONTRACT_PURCHASE_ID"
                        + "   AND D.PROJECT_ID = B.PROJECT_ID ");
        } else {
            sql.append(" where  1=1 ");
        }

        // 设置部门ID条件
        if (StringUtils.isNotBlank(deptId)) {
            sql.append(" AND a.deptid = '" + deptId + "'");
        }
        if (StringUtils.isNotBlank(type)) {
            type = URLDecoder.decode(type, "UTF-8");
            sql.append(" AND a.type LIKE '" + type + "'");
        }
        if (StringUtils.isNotBlank(project_state)) {
            sql.append(" AND d.project_state LIKE '%" + project_state + "%'");
        }
        if (StringUtils.isNotBlank(ekko_ihrez)) {
            sql.append(" AND a.ekko_unsez LIKE '%" + ekko_ihrez + "%'");
        }
        if (StringUtils.isNotBlank(ekpo_txz01)) {
            ekpo_txz01 = URLDecoder.decode(ekpo_txz01, "UTF-8");
            sql.append(" AND a.meterial_name LIKE '%" + ekpo_txz01 + "%'");
        }
        if (StringUtils.isNotBlank(contract_no)) {
            contract_no = URLDecoder.decode(contract_no, "UTF-8");
            sql.append(" AND a.contractno LIKE '%" + contract_no + "%'");
        }
        if (StringUtils.isNotBlank(material_group)) {
            sql.append(" AND a.material_group LIKE '%" + material_group + "%'");
        }
        if (StringUtils.isNotBlank(projectno)) {
            sql.append(" AND a.project_no = '" + projectno + "'");
        }
        if (StringUtils.isNotBlank(companyId)) {
            sql.append(" AND a.companyId = '" + companyId + "'");
        }
        if (StringUtils.isNotBlank(dateValue)) {
            sql.append(" AND a.datevalue = '" + dateValue.replaceAll("-", "") + "'");
        }
        if (StringUtils.isNotBlank(supplier)) {
            sql.append(" AND a.providerid = '" + supplier + "'");
        }
        if (StringUtils.isNotBlank(orderid)) {
            sql.append(" AND a.orderid like '%" + orderid + "%'");
        }
        // 设置合同审批通过时间条件
        if (StringUtils.isNotBlank(maturity1) && StringUtils.isNotBlank(maturity2)) {
            sql.append("AND d.approved_time BETWEEN '" + maturity1 + "' AND '" + maturity2 + "'");
        } else if (StringUtils.isNotBlank(maturity1)) {
            sql.append("AND d.approved_time > '" + maturity1 + "'");
        } else if (StringUtils.isNotBlank(maturity2)) {
            sql.append("AND d.approved_time < '" + maturity2 + "'");
        }

        // 设置审批通过时间条件
        if (StringUtils.isNotBlank(approved_time1) && StringUtils.isNotBlank(approved_time2)) {
            sql.append("AND b.approved_time BETWEEN '" + approved_time1 + "' AND '" + approved_time2 + "'");
        } else if (StringUtils.isNotBlank(approved_time1)) {
            sql.append("AND b.approved_time > '" + approved_time1 + "'");
        } else if (StringUtils.isNotBlank(approved_time2)) {
            sql.append("AND b.approved_time < '" + approved_time2 + "'");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> tbMap = new HashMap<String, Object>();
        map.put("BUKRS", companyId);
        map.put("BUDAT", dateValue);
        tbMap.put("WERKS", deptId);
        if ("0".equals(gridQueryCondition.getStart())) {
            profitLossService._reload2T_TEMP_PROFITLOSS(map, tbMap);
        }

        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql(" datevalue, deptid, type desc, project_no, contractno, profitlossid ");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.profitLoss.web.ProfitLossMatnrGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable,
                needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
}