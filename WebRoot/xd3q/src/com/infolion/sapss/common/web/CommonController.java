/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.sapss.common.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.waitForTransactWork.dao.WaitForTransactWorkJdbcDao;

/**
 * 
 * <pre>
 * 控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CommonController extends BaseMultiActionController {
	private static String work_follow_img_path="/images/workflow_img/";
	
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WaitForTransactWorkJdbcDao waitForTransactWorkJdbcDao;

	/**
	 * 显示流程图
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView showWorkfolowImg(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String path = request.getRealPath("");
		File file = new File(path+work_follow_img_path);

		List<String> imgs = Arrays.asList(file.list());
		request.setAttribute("path", work_follow_img_path);
		request.setAttribute("imgs", imgs);
		return new ModelAndView("showWorkFlowImg");

	}
	
	/***
	 * 工资查询
	 */
	public ModelAndView showPayView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {		
		return new ModelAndView("pay");

	}
	
	public void rtnFindPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String userId = userContext.getSysUser().getUserId();
		String employeeNo = sysUserService.queryEmployeeNo(userId);
		Map map = new HashMap<String, String>();
		String month = request.getParameter("month");
		String month1 = request.getParameter("month1");
		String year = request.getParameter("year");
		map.put("PERNR", employeeNo);
		map.put("PABRJ", year);
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> sumMap = new HashMap<String, String>();
		try{
			int start = Integer.parseInt(month);
			int end = Integer.parseInt(month1);
			String date = DateUtils.getCurrDateStr(DateUtils.DB_STORE_DATE);
			String cuYear = date.substring(0, 4);
			String cuMonth = date.substring(4, 6);
			String cuDate = date.substring(6, 8);
			for(int i=start;i<=end;i++){
				if(Integer.parseInt(cuYear)<=Integer.parseInt(year)){
					if((Integer.parseInt(cuMonth)-i<1)||(Integer.parseInt(cuMonth)-i==1&&Integer.parseInt(cuDate)<7)){
						continue;
					}
				}
				map.put("PABRP", String.valueOf(i));
				ExtractSapData.assembleJcoTable(ExtractSapData.getDicData("ZM_GETPAYRESULT", "IT_ALVOUT", map),list, sumMap);
			}
			sumMap.put("SNAME", "");
			sumMap.put("ORGTX", "总计");
			list.add(sumMap);
			//Map<String,String> map1 = list.get(0);
			//String name = map1.get("SNAME");
			//if(!name.equals(userContext.getSysUser().getRealName()))
			//	throw new BusinessException("外围系统名字和SAP系统不匹配");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray ja = JSONArray.fromObject(list);
		JSONObject jo = new JSONObject();
		jo.put("root", ja);
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(jsontext);

	}
	
	public void checkSpecil(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		//try{
		String type = request.getParameter("type");
		String businessId = request.getParameter("businessId");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
        if("oyz".equals(userContext.getSysUser().getUserName())){
        	jo.put("returnMessage", String.valueOf(waitForTransactWorkJdbcDao.isOYZAutoSubmit(null, businessId, type)));
		}			
		else
			jo.put("returnMessage", "false");
		this.operateSuccessfullyWithString(response, jo.toString());

	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public WaitForTransactWorkJdbcDao getWaitForTransactWorkJdbcDao() {
		return waitForTransactWorkJdbcDao;
	}

	public void setWaitForTransactWorkJdbcDao(
			WaitForTransactWorkJdbcDao waitForTransactWorkJdbcDao) {
		this.waitForTransactWorkJdbcDao = waitForTransactWorkJdbcDao;
	}
}
