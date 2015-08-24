package com.infolion.xdss3.ageAnalysis.web;

import java.io.IOException;
import java.net.URLDecoder;
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
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.xdss3.ageAnalysis.dao.AgeAnalysisJdbcDao;
import com.infolion.xdss3.ageAnalysis.domain.AgeAnalysis;
import com.infolion.xdss3.ageAnalysis.domain.UnclearDetailed;
import com.infolion.xdss3.ageAnalysis.service.AgeAnalysisService;
import com.infolion.xdss3.ageAnalysis.service.UnclearDetailedService;

public class AgeAnalysisController  extends BaseMultiActionController
{
	
	@Autowired
    private AgeAnalysisService ageAnalysisService;

	public void setAgeAnalysisService(AgeAnalysisService ageAnalysisService) {
		this.ageAnalysisService = ageAnalysisService;
	}
	
	@Autowired
    private UnclearDetailedService unclearDetailedService;

	public void setUnclearDetailedService(
			UnclearDetailedService unclearDetailedService) {
		this.unclearDetailedService = unclearDetailedService;
	}
	
	@Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    @Autowired
    private AgeAnalysisJdbcDao ageAnalysisJdbcDao;
    
	
	public void setAgeAnalysisJdbcDao(AgeAnalysisJdbcDao ageAnalysisJdbcDao) {
		this.ageAnalysisJdbcDao = ageAnalysisJdbcDao;
	}


	public void synAgeAnalysis(HttpServletRequest request, HttpServletResponse response) throws IOException{
		unclearDetailedService.updateClearItem();
		String customerNo=request.getParameter("customerNo");
		String subjectCode=request.getParameter("subjectCode");
		String date=request.getParameter("date");
		String companyNo=request.getParameter("companyNo");
		String depid=request.getParameter("depid");
		String businessState=request.getParameter("businessState");
		String isExceed2=request.getParameter("isExceed2");
		String customerType=request.getParameter("customerType");
		ageAnalysisService.delAllBycus(date, companyNo, depid, customerNo, subjectCode, businessState, customerType);
		ageAnalysisService.sysAgeAnalysis(date, companyNo, depid, customerNo, subjectCode, businessState, isExceed2, customerType);
	}
	
	
	public void synUnclear(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String customerNo=request.getParameter("customerNo");
		String subjectCode=request.getParameter("subjectCode");
		String date=request.getParameter("date");
		String companyNo=request.getParameter("companyNo");
		String depid=request.getParameter("depid");
		String businessState=request.getParameter("businessState");
		String isExceed2=request.getParameter("isExceed");
		String customerType=request.getParameter("customerType");
		unclearDetailedService.delByCus(date, companyNo, depid, customerNo, subjectCode);
		unclearDetailedService.sysUnclear(date, companyNo, depid, customerNo, subjectCode, businessState, isExceed2, customerType);
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建日期：2011-01-12 账龄分析综合查询
     */
    public ModelAndView ageAnalysisQuery(HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("xdss3/ageAnalysis/ageAnalysisQuery");
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2011-01-14 未清明细查询
     */
    public ModelAndView unclearDetailQuery(HttpServletRequest request,
            HttpServletResponse response) {
    	String customerType = request.getParameter("customerType");
        String customerNo = request.getParameter("customerNo");
        String subjectCode = request.getParameter("subjectCode");           
        String augdt = request.getParameter("augdt");       
        String bukrs = request.getParameter("bukrs");
        String gsber = request.getParameter("gsber");
        String vbeltype = request.getParameter("vbeltype");
        String isExceed = request.getParameter("isExceed");
        String subject2 = request.getParameter("subject2");    
        request.setAttribute("customerType", customerType);
        request.setAttribute("customerNo", customerNo);
        request.setAttribute("subjectCode", subjectCode);
        request.setAttribute("augdt", augdt);
        request.setAttribute("bukrs", bukrs);
        request.setAttribute("gsber", gsber);
        request.setAttribute("vbeltype", vbeltype);
        request.setAttribute("isExceed", isExceed);
        request.setAttribute("subject2", subject2);
        return new ModelAndView("xdss3/ageAnalysis/unclearDetailQuery");
    }
    
    public List<String> getList(String str){
    	String[] strs = str.split(",");
    	List<String> ls=new ArrayList<String>();
    	for(String s : strs){
    		ls.add(s);
    	}
    	if(strs.length==0)ls.add(str);
    	return ls;
    }
    public void queryAgeAnalysisGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws Exception{
        StringBuffer sql = new StringBuffer("SELECT A.*, '' total FROM YAGEANALYSIS A WHERE 1=1");
        // 获取SQL参数
        String customerType = request.getParameter("customerType");
        String customerNo = request.getParameter("customerNo");
        String subjectCode = request.getParameter("subjectCode");        
        customerNo =URLDecoder.decode(customerNo);
        subjectCode = subjectCode.replaceAll("\\*", "%");
        customerType = ageAnalysisService.getCustomerTypeBySubject(subjectCode);
        
        String augdt = request.getParameter("augdt");
        augdt = augdt.replaceAll("-", "");
        String bukrs = request.getParameter("bukrs");
        String gsber = request.getParameter("gsber");
        gsber =URLDecoder.decode(gsber);
        String vbeltype = request.getParameter("vbeltype");
        vbeltype =URLDecoder.decode(vbeltype);
        String isExceed = request.getParameter("isExceed");
        
//        ageAnalysisService.setAgeExport();
     
//    	unclearDetailedService.updateClearItem();    		
//		ageAnalysisService.delAllBycus(augdt, bukrs, gsber, customerNo, subjectCode, vbeltype, customerType);
//		ageAnalysisService.sysAgeAnalysis(augdt, bukrs, gsber, customerNo, subjectCode, vbeltype, isExceed, customerType);
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        if("1".equals(customerType)){
        	ageAnalysisService.updateClearItemByCustomer(userContext.getUser().getUserName(), augdt);
        }else{
        	ageAnalysisService.updateClearItemBySupplier(userContext.getUser().getUserName(), augdt);
        }
        String saknr = ageAnalysisService.getSaknrBySubject(subjectCode);
        List<Map<String, String>> list = ageAnalysisService._executeRfcGetMasterData(bukrs,gsber,augdt,saknr,vbeltype,customerNo);
//        ageAnalysisJdbcDao.delByUsername(userContext.getUser().getUserName());
        List<AgeAnalysis> ageAnalysisList = ageAnalysisService._ayncMasterData(list, bukrs, gsber, augdt, subjectCode,vbeltype);
//        if(StringUtils.isNotBlank(customerType)){
//            sql.append(" AND A.CUSTOMERTYPE = '" + customerType + "'");
//        }
//        
//        if(StringUtils.isNotBlank(customerNo)){
//            sql.append(" AND A.CUSTOMERNO IN (" + customerNo + ")");
//        }
//        
//        if(StringUtils.isNotBlank(subjectCode)){
//            sql.append(" AND A.SUBJECTCODE LIKE '" + subjectCode + "'");
//        }
//
//        if(StringUtils.isNotBlank(augdt)){
//            augdt = augdt.replaceAll("-", "");
//            sql.append(" AND A.AUGDT = '" + augdt + "'");
//        }
//        
//        if(StringUtils.isNotBlank(bukrs)){
//            sql.append(" AND A.BUKRS in (" + bukrs + ")");
//        }
//        
//        if(StringUtils.isNotBlank(gsber)){
//        	gsber = gsber.replaceAll("'", "");
//        	gsber = gsber.replaceAll(" ", "");
//        	gsber = ageAnalysisService.sortGsber(gsber);
//            sql.append(" AND A.GSBER = '" + gsber + "'");
//        }
//        
//        if(StringUtils.isNotBlank(isExceed)){
//            sql.append(" AND A.ISEXCEED = '" + isExceed + "'");
//        }
//        
//        if(StringUtils.isNotBlank(vbeltype)){
//        	vbeltype = vbeltype.replaceAll("'", "");		
//        	vbeltype = vbeltype.replaceAll(" ", "");
//        	vbeltype = ageAnalysisService.sortGsber(vbeltype);
//            sql.append(" AND A.VBELTYPE = ('" + vbeltype + "')");
//        }
//        
//        gridQueryCondition.setBoName("");
//        gridQueryCondition.setTableSql("");
//        gridQueryCondition.setWhereSql("");
//        gridQueryCondition.setOrderSql("");
//        gridQueryCondition.setGroupBySql("");
//        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
//        gridQueryCondition.setHandlerClass("com.infolion.xdss3.ageAnalysis.domain.AgeAnalysisGrid");
//        String editable = "false";
//        String needAuthentication = "false";
//        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
    	MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(ageAnalysisList.toArray());
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
    public void queryUnclearDetailGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws Exception{
        StringBuffer sql = new StringBuffer("SELECT A.*, '' total ,'' total2  FROM YUNCLEARDETAILED A WHERE 1=1");
        String customerType = request.getParameter("customerType");
        String customerNo = request.getParameter("customerNo");
        customerNo =URLDecoder.decode(customerNo);
        String subjectCode = request.getParameter("subjectCode");
        subjectCode = subjectCode.replaceAll("\\*", "%");
        String subject = request.getParameter("subject2");
        subject = subject.replaceAll("\\*", "%");
        customerType = ageAnalysisService.getCustomerTypeBySubject(subject);
//        subjectCode = subjectCode.replaceAll("\\*", "%");
//        String subject = request.getParameter("subject2");
       
        String augdt = request.getParameter("augdt");
        augdt = augdt.replaceAll("-", "");
        String bukrs = request.getParameter("bukrs");
        String gsber = request.getParameter("gsber");
        gsber = URLDecoder.decode(gsber);
        String vbeltype = request.getParameter("vbeltype");
        vbeltype =URLDecoder.decode(vbeltype);
        String isExceed = request.getParameter("isExceed");
        unclearDetailedService.delByCus(augdt, bukrs, gsber, customerNo, subject);
//		unclearDetailedService.sysUnclear(augdt, bukrs, gsber, customerNo, subjectCode, vbeltype, isExceed, customerType);
        String saknr = ageAnalysisService.getSaknrBySubject(subject);
         saknr = saknr +"_"+subjectCode;
         List<Map<String, String>> list = unclearDetailedService._executeRfcGetMasterData(bukrs, gsber, augdt, saknr, vbeltype, customerNo);
        List<UnclearDetailed> unclearDetailedList = unclearDetailedService._ayncMasterData(list, bukrs, gsber, augdt, subjectCode, vbeltype,customerType);
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
//        ----处理退款数据，收付款要减去退款的金额
        List<String> cus = new ArrayList<String>();
        cus.add(customerNo);
        unclearDetailedService.changeAmount(unclearDetailedList, bukrs, cus, userContext.getUser().getUserName(), customerType);
////		 if(StringUtils.isNotBlank(customerType)){
////	            sql.append(" AND A.CUSTOMERTYPE = '" + customerType + "'");
////	        }
////	        
////	        if(StringUtils.isNotBlank(customerNo)){
//	            sql.append(" AND A.CUSTOMERNO = '" + customerNo + "'");
////	        }
//	        
//	        if(StringUtils.isNotBlank(subjectCode)){
//	            sql.append(" AND A.SUBJECTCODE LIKE '" + subjectCode + "'");
//	        }
//
////	        if(StringUtils.isNotBlank(augdt)){
////	            augdt = augdt.replaceAll("-", "");
////	            sql.append(" AND A.AUGDT = '" + augdt + "'");
////	        }
//	        
//	        if(StringUtils.isNotBlank(bukrs)){
//	            sql.append(" AND A.COMPANYNO = '" + bukrs + "'");
//	        }
//	        
//	        if(StringUtils.isNotBlank(gsber)){
//	            sql.append(" AND A.deptId ='" + gsber + "'");
//	        }
//	        
//	        if(StringUtils.isNotBlank(isExceed)){
//	            sql.append(" AND A.ISEXCEED = '" + isExceed + "'");
//	        }
//	        
//	        if(StringUtils.isNotBlank(vbeltype)){
//	            sql.append(" AND A.VBELTYPE IN (" + vbeltype + ")");
//	        }
//	        
//        gridQueryCondition.setBoName("");
//        gridQueryCondition.setTableSql("");
//        gridQueryCondition.setWhereSql("");
//        gridQueryCondition.setOrderSql("");
//        gridQueryCondition.setGroupBySql("");
//        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
//        gridQueryCondition.setHandlerClass("com.infolion.xdss3.ageAnalysis.domain.UnclearDetailGrid");
//        String editable = "false";
//        String needAuthentication = "false";
//        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unclearDetailedList.toArray());
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
	
}
