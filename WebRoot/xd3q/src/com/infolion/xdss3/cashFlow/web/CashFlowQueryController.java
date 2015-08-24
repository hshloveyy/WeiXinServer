/**
 * 
 */
package com.infolion.xdss3.cashFlow.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.grid.data.TableSql;
import com.infolion.platform.dpframework.uicomponent.queryCondition.QueryConditionService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.sapss.masterQuery.web.ReportUtil;
import com.infolion.xdss3.cashFlow.domain.CashFlow;
import com.infolion.xdss3.cashFlow.service.CashFlowQueryService;
import com.infolion.xdss3.cashFlow.service.ExportExel;

/**
 * @author 钟志华
 *
 */
public class CashFlowQueryController extends BaseMultiActionController{

	@Autowired
	private GridService gridService;
	public void setGridService(GridService gridService) {
		this.gridService = gridService;
	}
	
	@Autowired
    private CashFlowQueryService cashFlowQueryService;

	public void setCashFlowQueryService(CashFlowQueryService cashFlowQueryService) {
		this.cashFlowQueryService = cashFlowQueryService;
	}
	public ModelAndView cashFlowQuery(HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("xdss3/cashFlow/cashFlowQuery");
    }
	public void queryCashFlowGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws Exception{
		String bukrs = request.getParameter("bukrs");//公司代码
		String gsber = request.getParameter("gsber");//业务范围
        gsber =URLDecoder.decode(gsber);
        String customerNo = request.getParameter("customerNo");//客户，供应商号
        String hkont = request.getParameter("subject");//科目
        customerNo =URLDecoder.decode(customerNo);
        String customerType = request.getParameter("customerType");//客户类型1为客户2为供应商
        String rstgr = request.getParameter("rstgr");//原因代码
        String projectno = request.getParameter("projectno");//立项号
        String augdt_to = request.getParameter("augdt_to");//过账起始日期
        String augdt_from = request.getParameter("augdt_from");//过账终止日期
        augdt_to = augdt_to.replaceAll("-", "");
        augdt_from = augdt_from.replaceAll("-", "");
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
       
        list = cashFlowQueryService._executeRfcGetCashFlow(bukrs, augdt_to, augdt_from, gsber, rstgr, hkont, projectno, customerNo,customerType, null);
      
        List<CashFlow> cashFlows =cashFlowQueryService.getCshFlow(list, bukrs, gsber,augdt_to, augdt_from, hkont);
       
	    List<CashFlow> onroadCashFlows =cashFlowQueryService.getOnroadCshFlow(bukrs, augdt_to, augdt_from, gsber, rstgr, hkont, projectno, customerNo,customerType, null);
	    cashFlows.addAll(onroadCashFlows);
//	    //先删除同一用户的数据
	    UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username=userContext.getUser().getUserName();
	    cashFlowQueryService.delCashFlow(username);
//	    //批量插入
	    cashFlowQueryService.saveOrUpdateAll(cashFlows);
	   
	    List<CashFlow> cashFlows2=new ArrayList<CashFlow>();
	    List<CashFlow> cashFlows3 = cashFlowQueryService.getCashFlow(username,"0", "100");
	   
	    	//只取100条数据
	    	for(CashFlow cashFlow: cashFlows3){	    		
	    		cashFlowQueryService.getCashFlow(cashFlow);				
	    		cashFlows2.add(cashFlow);	    		
	    	}
        
//        MultyGridData gridJsonData = new MultyGridData();
	    GridData gridJsonData = new GridData();
		gridJsonData.setData(cashFlows2.toArray());
		gridJsonData.setTotalSize(cashFlows.size());
//		gridJsonData.setTotalSize(1000);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
	}
	
	public void queryGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws SQLException
	{
		String defaultCondition = getDefaultCondition(request);
		String userDefineWhereSql = StringUtils.isNullBlank(gridQueryCondition.getWhereSql()) || "null".equals(gridQueryCondition.getWhereSql()) ? "" : gridQueryCondition.getWhereSql();
		String whereSql = QueryConditionService.getQueryExpression(request.getParameterMap());
		String tableName = gridQueryCondition.getTableName();
		String strTableSql = gridQueryCondition.getTableSql();
		String groupBySql = gridQueryCondition.getGroupBySql();
		String orderSql = gridQueryCondition.getOrderSql();
		try
		{
		if (!StringUtils.isNullBlank(defaultCondition))
		defaultCondition = URLDecoder.decode(defaultCondition, "UTF-8");
		if (!StringUtils.isNullBlank(userDefineWhereSql))
		whereSql = URLDecoder.decode(userDefineWhereSql, "UTF-8") + whereSql;
		if (!StringUtils.isNullBlank(tableName))
		tableName = URLDecoder.decode(tableName, "UTF-8");
		if (!StringUtils.isNullBlank(groupBySql))
		groupBySql = URLDecoder.decode(groupBySql, "UTF-8");
		if (!StringUtils.isNullBlank(orderSql))
		orderSql = URLDecoder.decode(orderSql, "UTF-8");
		if (!StringUtils.isNullBlank(strTableSql))
		strTableSql = URLDecoder.decode(strTableSql, "UTF-8");
		}
		catch (UnsupportedEncodingException e1)
		{
		e1.printStackTrace();
		}
		// 如果指定了table sql实现类
		if (!StringUtils.isNullBlank(gridQueryCondition.getTableSqlClass()))
		{
		TableSql tableSql = (TableSql) EasyApplicationContextUtils.getBeanByName(gridQueryCondition.getTableSqlClass());
		strTableSql = tableSql.getTableSql();
		}
		gridQueryCondition.setTableSql(strTableSql);
		gridQueryCondition.setTableName(tableName);		
		gridQueryCondition.setWhereSql(whereSql);
		gridQueryCondition.setOrderSql(orderSql);
		gridQueryCondition.setGroupBySql(groupBySql);
		String editable = request.getParameter("editable");
		String needAuthentication = request.getParameter("needAuthentication");
//		GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
		 UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
			String username=userContext.getUser().getUserName();
		String end ;
		int s =Integer.parseInt(gridQueryCondition.getStart());
		int lim=Integer.parseInt(gridQueryCondition.getLimit());
		end=String.valueOf(s+lim);
		List<CashFlow> cashFlows3 = cashFlowQueryService.getCashFlow(username,gridQueryCondition.getStart(), end);
//		List<CashFlow> cashFlows3 = cashFlowQueryService.getCashFlowByExport(username,gridQueryCondition.getStart(), end);
		 List<CashFlow> cashFlows2=new ArrayList<CashFlow>();
		for(CashFlow cashFlow: cashFlows3){	    		
    		cashFlowQueryService.getCashFlow(cashFlow);				
    		cashFlows2.add(cashFlow);	    		
    	}
		
		GridData gridJsonData = new GridData();
		gridJsonData.setData(cashFlows3.toArray());
		gridJsonData.setTotalSize(Integer.parseInt(cashFlowQueryService.getCount(username)));
		
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try
		{
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonList);
		}
		catch (IOException e)
		{
		logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 取得默认查询条件，如果有多个值传入，以最后的一个查询条件为准
	 * 
	 * @param request
	 * @return
	 */
	private String getDefaultCondition(HttpServletRequest request)
	{
	String[] defaultConditions = request.getParameterValues("defaultCondition");
	if (null == defaultConditions || defaultConditions.length < 1)
	return "";
	return defaultConditions[defaultConditions.length - 1];
	}
	
	/**
	 * 分页导出
	 * @param request
	 * @param response
	 */
	public void getCashFlowByExportToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username=userContext.getUser().getUserName();
		try{
//			String start="0";
//			　大批量数据导出的时候，需要注意这样的一个问题，Excel2003格式一个sheet只支持65536行，excel 2007 就比较多，是1048576
//			String end="1048576";
			List<CashFlow> cashFlows3 = cashFlowQueryService.getCashFlowByExport(username,null, null);
			String col = ReportUtil.getProperty("cashflow");
			String[] cols = col.split(",");
			String colName = new String(ReportUtil.getProperty("cashflow_names").getBytes("ISO-8859-1"), "UTF-8");
		    String[] colNames = colName.split(",");
		    ExportExel exportExel =new ExportExel<CashFlow>();
		    InputStream is = exportExel.createExcelFile(cols, colNames, cashFlows3, null);
		    ReportUtil util = new ReportUtil();
	        util.download(is, "现金流项量表_"+(DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE))+".xls", response);
	        
	        is.close();
			
//			response.setContentType("text/html;charset=UTF-8");
//			response.getWriter().print("");
//			System.out.println("");
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	
}
