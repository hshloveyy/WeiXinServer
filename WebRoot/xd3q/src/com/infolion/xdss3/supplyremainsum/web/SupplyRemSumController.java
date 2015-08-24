/*
 * @(#)SupplyRemSumController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月10日 02点06分05秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplyremainsum.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.supplyremainsumGen.web.SupplyRemSumControllerGen;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;

/**
 * <pre>
 * 供应商项目余额查询(SupplyRemSum)控制器类
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
public class SupplyRemSumController extends SupplyRemSumControllerGen
{
    @Autowired
    private ReportService reportService;
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
	public void insertData(HttpServletRequest request, HttpServletResponse response){
		String providerid = request.getParameter("providerid");
		String projectid = request.getParameter("projectid");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getUserId();
		this.supplyRemSumService.insertData(userid,projectid,providerid);
	}
	
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getUserId();
		this.supplyRemSumService.insertData(userid,"","");
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("userid", userid);
		return new ModelAndView("xdss3/supplyremainsum/supplyRemSumManage");
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
       String detailName = "supplyremsummanage";
       String col = ReportUtil.getProperty(detailName);
       
       UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
       String userid = userContext.getSysUser().getUserId();
       
       StringBuffer sql = new StringBuffer();
       sql.append(" SELECT S.DEPTNAME, S.PROJECT, PRO.NAME1, ");
       sql.append("        S.SUPPLIERNO, ");
       sql.append("        P.PROJECT_NAME, ");
       sql.append("        S.REMAININGSUM, ");
       sql.append("        S.TOTALVALUE, ");
       sql.append("        S.ENDTIME ");
       sql.append("   FROM YSUPPLIREMAINSUM S ");
       sql.append("   LEFT JOIN YVPROVCREDCONF PRO ");
       sql.append("     ON (PRO.LIFNR = S.SUPPLIERNO) ");
       sql.append("   LEFT JOIN T_PROJECT_INFO P ");
       sql.append("     ON (P.PROJECT_ID = S.PROJECTNO) ");
       sql.append("  WHERE 1 = 1 ");
       sql.append("    AND 1 = 1 ");
       sql.append("    AND S.USERID = '"+userid+"' ");
       sql.append("    AND EXISTS ");
       sql.append("  (SELECT 'x' ");
       sql.append("           FROM T_PROJECT_INFO PI ");
       sql.append("          WHERE PI.DEPT_ID IN ");
       sql.append("                (SELECT DEPT_ID ");
       sql.append("                   FROM T_SYS_DEPT_USER ");
       sql.append("                  WHERE USER_ID = '"+userid+"' ");
       sql.append("                 UNION ");
       sql.append("                 SELECT DEPT_ID ");
       sql.append("                   FROM T_SYS_USER_MANAGER_DEPT ");
       sql.append("                  WHERE USER_ID = '"+userid+"') ");
       sql.append("            AND PI.PROJECT_ID = S.PROJECTNO) ");
       sql.append("    AND S.MANDT = '800' ");
       sql.append(" GROUP BY S.DEPTNAME, ");
       sql.append("           S.PROJECT, ");
       sql.append("           PRO.NAME1, ");
       sql.append("           S.SUPPLIERNO, ");
       sql.append("           P.PROJECT_NAME, ");
       sql.append("           S.REMAININGSUM, ");
       sql.append("           S.TOTALVALUE, ");
       sql.append("           S.ENDTIME ");
       sql.append("  ORDER BY S.ENDTIME DESC, S.SUPPLIERNO, S.PROJECT ");
       
       String[] cols = col.split(",");
       String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
       String[] colNames = colName.split(",");
       InputStream is = this.reportService.createExcelFile(cols, colNames, sql.toString(), null);
       ReportUtil util = new ReportUtil();
       util.download(is, "供应商余额查询.xls", response);
       
       is.close();
   }
	
}