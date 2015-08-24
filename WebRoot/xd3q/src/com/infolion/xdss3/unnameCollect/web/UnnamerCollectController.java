/*
 * @(#)UnnamerCollectController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月08日 10点19分31秒
 *  描　述：创建
 */
package com.infolion.xdss3.unnameCollect.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.unnameCollect.domain.UnnamerCollect;
import com.infolion.xdss3.unnameCollectGen.web.UnnamerCollectControllerGen;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 未明户收款(UnnamerCollect)控制器类
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
public class UnnamerCollectController extends UnnamerCollectControllerGen {
	
	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	public void setSdjd(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request,
			HttpServletResponse response) {
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		UnnamerCollect unnamerCollect = new UnnamerCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj))
		{
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("username", userContext.getSysUser().getUserName());
		request.setAttribute("unnamerCollect", unnamerCollect);
		
		/**
		 * 获取用户上下文ID
		 */
		String userId = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysUser().getUserId();
		request.setAttribute("userId", userId);

		String userDeptid = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptid();
		String userDeptName = UserContextHolder.getLocalUserContext()
				.getUserContext().getSysDept().getDeptname();
		request.setAttribute("userDeptid", "FEDEF1AE-F8DD-483D-8260-971A46CF0389");
		request.setAttribute("defaultCashItem", "101");
		request.setAttribute("userDeptName", userDeptName);

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000283");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/unnameCollect/unnamerCollectAdd");
	}

	/**
	 * 编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		UnnamerCollect unnamerCollect = new UnnamerCollect();
		String id = request.getParameter("unnamercollectid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
			unnamerCollect = this.unnamerCollectService._getForEdit(id);        }
        else
        {
           unnamerCollect = this.unnamerCollectService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000283");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);	
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
        
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Properties prop = this.getProperties("config/config.properties");
		String xjrj = prop.getProperty("xjrj");
		if (xjrj != null && !"".equals(xjrj))
		{
			request.setAttribute("xjrj", xjrj);
		}

		request.setAttribute("username", userContext.getSysUser().getUserName());
		request.setAttribute("unnamerCollect", unnamerCollect);
		return new ModelAndView("xdss3/unnameCollect/unnamerCollectEdit");
	}
	
	/**
	 * 
	 * @param absolutePath
	 */
	public Properties getProperties(String absolutePath)
	{
		Properties p = new Properties();
		try
		{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(absolutePath);
			p.load(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 * 凭证预览
	 * 
	 * @param request
	 * @param response
	 */
	public void _previewVoucher(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		
		// 绑定主对象值
		UnnamerCollect unnamerCollect = (UnnamerCollect) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						UnnamerCollect.class, true, request.getMethod(), true);
		String voucherid = this.unnamerCollectService.saveVoucher(unnamerCollect);
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(voucherid);
			System.out.println(voucherid);
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}		
	}
	
	public void cashJournal(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		UnnamerCollect unnamerCollect = (UnnamerCollect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), UnnamerCollect.class , true , request.getMethod(), true);
		this.unnamerCollectService._saveOrUpdate(unnamerCollect,getBusinessObject());
		this.operateSuccessfullyHiddenInfo(response);
	}
	
	
	@Override
	public ModelAndView _view(HttpServletRequest request,
			HttpServletResponse response) {
		UnnamerCollect unnamerCollect = new UnnamerCollect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("unnamercollectid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			unnamerCollect = this.unnamerCollectService._get(id);
		} else {
			unnamerCollect = this.unnamerCollectService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000283");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		/**
		 * @修改作者：邱杰烜
		 * @修改日期：2010-08-17
		 * ------------------------------ 修改记录 ------------------------------
		 * 邱杰烜 2010-08-17 根据部门ID获取公司代码
		 * 邱杰烜 2010-09-07 若当前节点为"财务会计审核"，则设置默认现金流量项为"101"
		 */
		/***********************************************************************/
		String creator = unnamerCollect.getCreator();
		String companyCode = this.sysDeptJdbcDao.getCompanyCodeByUserId(creator);
		System.out.println("---------------" + "获取公司代码： " + companyCode + "----------------");
		request.setAttribute("companyCode", companyCode);
		// 若当前节点为"财务会计审核"，则设置默认现金流量项为"101"
		if(null!=unnamerCollect.getProcessstate() && "财务会计审核".equals(unnamerCollect.getProcessstate())){
			unnamerCollect.setCashflowitem("101");
		}
		/***********************************************************************/
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}

		request.setAttribute("unnamerCollect", unnamerCollect);
		return new ModelAndView("xdss3/unnameCollect/unnamerCollectView");
	}
	
	public void calculateExchangeRate(HttpServletRequest request, HttpServletResponse response){
		String fCurr = request.getParameter("fCurr");
		try
		{
			double exchangeRate = this.unnamerCollectService.getUnnamerCollectJdbcDao().getExhangeRateByFcurr(fCurr);
			JSONObject jo = new JSONObject();
			jo.put("collectRate", exchangeRate);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			System.out.println(jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

}