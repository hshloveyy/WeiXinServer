/*
 * @(#)CreditlogController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月17日 07点13分35秒
 *  描　述：创建
 */
package com.infolion.xdss3.creditlog.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.creditlogGen.web.CreditlogControllerGen;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.sapss.masterQuery.service.ReportService;
import com.infolion.sapss.masterQuery.web.ReportUtil;

/**
 * <pre>
 * 授信日志表(Creditlog)控制器类
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
public class CreditlogController extends CreditlogControllerGen
{    
    @Autowired
    private ReportService reportService;
    
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
	public void queryCreditData(HttpServletRequest request, HttpServletResponse response){
		String ytype = request.getParameter("ytype");
		String crditType = request.getParameter("crdittype");
		String customerid = request.getParameter("customerid");
		String providerid = request.getParameter("providerId");
		String prov_projectid = request.getParameter("prov_projectid");
		String cust_projectid = request.getParameter("cust_projectid");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getDeptUserId();
		
		if("1".equals(ytype)){//客户
			if("1".equals(crditType)){//客户放货
				this.creditlogService.callSaveCustomerSendCreditLog(userid, customerid, cust_projectid);
			}else if("2".equals(crditType)){//客户代垫
				this.creditlogService.callSaveCustomerCreditLog(userid, customerid, cust_projectid);
			}else{
				this.creditlogService.callSaveCustomerSendCreditLog(userid, customerid, cust_projectid);
				this.creditlogService.callSaveCustomerCreditLog(userid, customerid, cust_projectid);
			}
		}else{
			this.creditlogService.callSaveProviderCreditLog(userid, providerid, prov_projectid);
		}
	}
	
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response){
		this.creditlogService.clearData();
		request.setAttribute("vt", getBusinessObject().getViewText());
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userid = userContext.getSysUser().getDeptUserId();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("userid", userid);
		return new ModelAndView("xdss3/creditlog/creditlogManage");
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
        String detailName = "creditlog";
        String col = ReportUtil.getProperty(detailName);
        String sql = this.genSQLForExcel(request, col);
        String[] cols = col.split(",");
        String colName = new String(ReportUtil.getProperty(detailName + "_names").getBytes("ISO-8859-1"), "UTF-8");
        String[] colNames = colName.split(",");
        InputStream is = this.reportService.createExcelFile(cols, colNames, sql, null);
        ReportUtil util = new ReportUtil();
        util.download(is, "授信日志查询.xls", response);
        
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
        String sql="select * From (select CASE WHEN T.YTYPE = '2' THEN '供应商' ELSE '客户' END YTYPE," +
        		"CASE WHEN T.ACTION = '1' THEN '加' ELSE '减' END ACTION," +
        		"CASE WHEN T.CREDITTYPE = '2' AND T.YTYPE = '1' THEN '代垫' WHEN T.CREDITTYPE = '1' AND T.YTYPE = '1' THEN '放货' ELSE '供应商授信' END CREDITTYPE," +
        		"YDM.DDTEXT YMODULE," +
        		"busnum,amout,projectno," +
        		"TT.NAME1 lifnrname, TT2.NAME1  kunnrname, p.project_name, t.activedate " +
        		" from  YCREDITLOG t  " +
        		" LEFT JOIN (SELECT DISTINCT LIFNR, NAME1 FROM YVPROVCREDCONF) TT  ON (T.PROVIDERNO = TT.LIFNR)  " +
        		" LEFT JOIN (SELECT DISTINCT KUNNR, NAME1 FROM YVCUSTCREDCONF) TT2 ON (TT2.KUNNR = T.CUSTNO) " +
        		" LEFT JOIN (SELECT DDTEXT, DOMVALUE_L FROM DD07T WHERE DOMNAME = 'YDMODULE') YDM ON (YDM.DOMVALUE_L = T.YMODULE) " +
        		" LEFT JOIN t_project_info p ON (p.project_id=t.projectid) " +
        		" where t.MANDT = '800' ";
        if(StringUtils.isNotBlank(request.getParameter("ytype.fieldValue")))
            sql+=" and t.ytype='"+request.getParameter("ytype.fieldValue")+"'";
        if(StringUtils.isNotBlank(request.getParameter("credittype.fieldValue")))
            sql+=" and t.credittype='"+request.getParameter("credittype.fieldValue")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("providerno.fieldValue")))
            sql+=" and t.providerno='"+request.getParameter("providerno.fieldValue").replaceAll("-", "")+"'"; 
        if(StringUtils.isNotBlank(request.getParameter("custno.fieldValue")))
            sql+=" and t.custno='"+request.getParameter("custno.fieldValue")+"'"; 
   
        
        // 部门的过滤
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        sql += " and t.userid = '" + userContext.getSysUser().getDeptUserId()+ "' order by t.activedate desc)";
        

        return sql;
    }
}