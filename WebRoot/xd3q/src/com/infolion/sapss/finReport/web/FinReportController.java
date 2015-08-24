package com.infolion.sapss.finReport.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.menu.domain.AsyncTreeCheckNode;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.EnhanceMultiActionController;
import com.infolion.sapss.bapi.FinReportUtils;
import com.infolion.sapss.finReport.FinReportConstans;
import com.infolion.sapss.finReport.service.FinReportService;

public class FinReportController extends EnhanceMultiActionController{


	@Autowired
	private FinReportService finReportService;

	public FinReportService getFinReportService() {
		return finReportService;
	}

	public void setFinReportService(FinReportService finReportService) {
		this.finReportService = finReportService;
	}

	public ModelAndView genneralView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/finReport/genneralView");
	}

	public void queryDeptInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		List<AsyncTreeCheckNode> treeNodelist = new ArrayList();
		List<SysDept> tSysDeptList = finReportService.queryDeptByParentId(strParentId);
		SysDept tSysDept = new SysDept();
		for (int i = 0; i < tSysDeptList.size(); i++) {
			tSysDept = tSysDeptList.get(i);
			AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
			asynTreeNode.setId(tSysDept.getDeptid());
			asynTreeNode.setText(tSysDept.getDeptname());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (tSysDept.getChildcount() == 0)
				asynTreeNode.setLeaf(true);
			else
				asynTreeNode.setLeaf(false);
			asynTreeNode.setChecked(false);
			treeNodelist.add(asynTreeNode);
		}
		JSONArray ja = JSONArray.fromObject(treeNodelist);
		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	public void queryGenneral(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		Map<String, String> filter = extractFR(request);
		String comCodes = filter.get("comCode");
		String busCodes = filter.get("busCode");
		String year = filter.get("year");
		String month = filter.get("month");
		String lastYear = String.valueOf((Integer.parseInt(year)-1));		
		Map<String,String> currenMap = FinReportUtils.queryGenneral(year, month, comCodes, busCodes);
		Map<String,String> lastYearMap = FinReportUtils.queryGenneral(lastYear, month, comCodes, busCodes);
		Map<String,BigDecimal> budgetMap = finReportService.queryGenneralBudget(year, month, comCodes, busCodes);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		BigDecimal twelve = new BigDecimal(12);
		for(String key : FinReportConstans.FIELD_GENNERAL.keySet()){
			Map<String,Object> map = new HashMap<String,Object>();
			BigDecimal fact = toBigD(currenMap.get(key));
			BigDecimal ybudget = (BigDecimal)(budgetMap.get(key));
			BigDecimal mbudget = ybudget.divide(twelve);
			BigDecimal difference = fact.subtract(mbudget);
			BigDecimal yearTotal = toBigD(currenMap.get(key+"_BNLJS")); //本年实际累计数
			BigDecimal yearBudget = mbudget.multiply(toBigD(month));//本年累计预算数
		    BigDecimal yearDiff = yearTotal.subtract(yearBudget);//本年累计预算偏差额
		    BigDecimal lastYearTotal = toBigD(lastYearMap.get(key+"_BNLJS"));
		    BigDecimal lastYearDiff = yearTotal.subtract(lastYearTotal);
			map.put("TITLE", FinReportConstans.FIELD_GENNERAL.get(key));//项目
			map.put("YBUDGET", ybudget);//查询当年年预算数
			map.put("MBUDGET",mbudget);//本月预算数
			map.put("FACT", fact);//本月实际数
			map.put("DIFFERENCE", difference);//本月预算偏差额
			map.put("DIFRANGE", mbudget.doubleValue()==0d?"0":difference.divide(mbudget,4));//本月预算偏差幅度
			map.put("DIFREASON", "");//本月差异原因分析
			map.put("YEARTOTAL", yearTotal);//本年累计实际数
			map.put("YEARDIFF", yearDiff);// 本年累计预算偏差额
			map.put("YEARDIFRANGE", yearBudget.doubleValue()==0d?"0":yearDiff.divide(yearBudget,4));//本年累计预算偏差幅度
			map.put("YEARDIFFREASON", "");//本年差异原因分析
			map.put("LASTYEARTOTAL", lastYearTotal);//上年同期累计数
			map.put("LASTYEARDIFF", lastYearDiff);//累计同比增减额
			map.put("LASTYEARRANGE", lastYearTotal.doubleValue()==0?"0":lastYearDiff.divide(lastYearTotal,4).multiply(new BigDecimal(100)));//累计同比增减%
			map.put("LASTYEARREASON", "");//差异原因分析
			list.add(map);
		}		
		String grid_columns = "TITLE,YBUDGET,MBUDGET,FACT,DIFFERENCE,DIFRANGE,YEARTOTAL,YEARTOTAL,YEARDIFF,YEARDIFRANGE,YEARDIFFREASON,LASTYEARTOTAL,LASTYEARDIFF,LASTYEARRANGE,LASTYEARREASON";
		String grid_size = "30";
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		JSONArray ja = JSONArray.fromObject(list);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty",list.size());
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}
	
	private BigDecimal toBigD(String value){
		return new BigDecimal(value);
	}
	
	public void dealDeptBs(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String deptId = request.getParameter("deptId");
		JSONObject jo = new JSONObject();
		Map<String,String> map = finReportService.dealDeptBs(deptId);
		jo.put("isSuccess", map.get("isSuccess"));
        jo.put("returnMessage", map.get("returnMessage"));
        jo.put("busCode", map.get("busCode"));
        jo.put("comCode", map.get("comCode"));
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
		
	}

}
