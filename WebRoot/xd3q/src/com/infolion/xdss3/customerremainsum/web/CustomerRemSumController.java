/*
 * @(#)CustomerRemSumController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月13日 09点53分56秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerremainsum.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.customerremainsumGen.web.CustomerRemSumControllerGen;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;

/**
 * <pre>
 * 客户项目余额(CustomerRemSum)控制器类
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
public class CustomerRemSumController extends CustomerRemSumControllerGen
{
    @Autowired
    private ReportService reportService;
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
	public void insertData(HttpServletRequest request, HttpServletResponse response){
		String customerid = request.getParameter("customerid");
		String projectid = request.getParameter("projectid");
		String creditType = request.getParameter("credittype");
//		if(StringUtils.isNullBlank(creditType))
//			creditType = "3";//默认为放货和代垫
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getDeptUserId();
		this.customerRemSumService.insertData(userid,projectid,customerid,creditType);
	}
	
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getDeptUserId();
		this.customerRemSumService.insertData(userid,"","","");
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("userid", userid);
		return new ModelAndView("xdss3/customerremainsum/customerRemSumManage");
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
       String detailName = "customerremsummanage";
       String col = ReportUtil.getProperty(detailName);
       
       UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
       String userid = userContext.getSysUser().getDeptUserId();
       
       StringBuffer sql = new StringBuffer();
	   sql.append(" SELECT MAX(t.DEPTNAME) AS DEPTNAME, ");
	   sql.append(" MAX(t.PROJECT) AS PROJECT, ");
	   sql.append(" MAX(t.CCREDIT) AS CCREDIT, ");
	   sql.append(" MAX(t.NAME1) AS NAME1, ");
	   sql.append(" 		MAX(t.PROJECT_NAME) AS PROJECT_NAME, ");
	   sql.append(" MAX(t.REMAININGSUM) AS REMAININGSUM, ");
	   sql.append(" MAX(t.PREPAYVALUE) AS PREPAYVALUE, ");
	   sql.append(" MAX(t.SENDVALUE) AS SENDVALUE, ");
	   sql.append(" MAX(t.ENDTIME) AS ENDTIME	 ");		
	   sql.append(" FROM (  ");
       sql.append(" SELECT S.DEPTNAME, S.PROJECT, CASE ");
       sql.append("          WHEN S.CREDITTYPE = '1' THEN ");
       sql.append("           '放货' ");
       sql.append("          WHEN S.CREDITTYPE = '2' THEN ");
       sql.append("           '代垫' ");
       sql.append("        END CCREDIT, ");
       sql.append("        V.NAME1, ");
       sql.append("        P.PROJECT_NAME, ");
       sql.append("        S.REMAININGSUM, ");
       sql.append("        S.PREPAYVALUE, ");
       sql.append("        S.SENDVALUE, ");
       sql.append("        S.ENDTIME, ");
       sql.append("        S.CUSTOMERNO, ");
       sql.append("        S.PROJECTNO ");	
       sql.append("   FROM YCUSTOMERREMSUM S ");
       sql.append("   LEFT JOIN YVCUSTCREDCONF V ");
       sql.append("     ON (S.CUSTOMERNO = V.KUNNR) ");
       sql.append("   LEFT JOIN T_PROJECT_INFO P ");
       sql.append("     ON (P.PROJECT_ID = S.PROJECTNO) ");
       sql.append("  WHERE 1 = 1 ");
       sql.append("    AND 1 = 1 ");
       sql.append("    AND S.USERID = '"+userid+"'  ");
       sql.append("    AND EXISTS ");
       sql.append("  (SELECT 'x' ");
       sql.append("           FROM T_PROJECT_INFO PI ");
       sql.append("          WHERE PI.DEPT_ID IN ");
       sql.append("                (SELECT DEPT_ID ");
       sql.append("                   FROM T_SYS_DEPT_USER ");
       sql.append("                  WHERE USER_ID = '"+userid+"'  ");
       sql.append("                 UNION ");
       sql.append("                 SELECT DEPT_ID ");
       sql.append("                   FROM T_SYS_USER_MANAGER_DEPT ");
       sql.append("                  WHERE USER_ID = '"+userid+"' ) ");
       sql.append("            AND PI.PROJECT_ID = PROJECTNO) ");
       sql.append("    AND S.MANDT = '800' ");
//       sql.append("  ORDER BY S.ENDTIME DESC, S.CREDITTYPE, S.CUSTOMERNO, S.PROJECTNO ");
       sql.append("    ) t		GROUP BY  t.CUSTOMERNO, t.PROJECTNO ");
       String[] cols = col.split(",");
       String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
       String[] colNames = colName.split(",");
       InputStream is = this.reportService.createExcelFile(cols, colNames, sql.toString(), null);
       ReportUtil util = new ReportUtil();
       util.download(is, "客户余额查询.xls", response);
       
       is.close();
   }
}