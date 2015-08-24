/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.sapss.project.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.SaleContractJdbcService;
import com.infolion.sapss.project.ProjectConstants;
import com.infolion.sapss.project.domain.TApplyCustomerCredit;
import com.infolion.sapss.project.domain.TApplyProviderCredit;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.dto.ProjectPrintDto;
import com.infolion.sapss.project.service.ProjectHibernateService;
import com.infolion.sapss.project.service.ProjectJdbcService;

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
public class ProjectController extends BaseMultiActionController {
	private final Log log = LogFactory.getLog(ProjectController.class);
	// 服务类注入
	@Autowired
	private ProjectHibernateService projectHibernateService;

	public void setProjectService(ProjectHibernateService projectService) {
		this.projectHibernateService = projectService;
	}

	@Autowired
	private ProjectJdbcService projectJdbcService;

	public void setProjectJdbcService(ProjectJdbcService projectJdbcService) {
		this.projectJdbcService = projectJdbcService;
	}

	/**
	 * 默认方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public ModelAndView defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return new ModelAndView();
	}
	
	
	
	/**
	 * 保存客户信用额度
	 * @param req
	 * @param resp
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void saveOrUpdateCustCredit(HttpServletRequest req, HttpServletResponse resp,
			TApplyCustomerCredit vo) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String id = "";
		
		if (vo.getApplyId() == null || "".equals(vo.getApplyId())){
			id = CodeGenerator.getUUID();
			vo.setApplyId(id);
		}else{
			id= vo.getApplyId();
		}
		this.projectHibernateService.saveOrUpdateCustCredit(vo);
		
		resp.getWriter().print(	"{success:true,applyId:'" + id +"'}");
	}
	
	/**
	 * 保存供应商信用额度
	 * @param req
	 * @param resp
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void saveOrUpdateProvidCredit(HttpServletRequest req, HttpServletResponse resp,
			TApplyProviderCredit vo) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String id = "";
		
		if (vo.getProviderApplyId() == null || "".equals(vo.getProviderApplyId())){
			id = CodeGenerator.getUUID();
			vo.setProviderApplyId(id);
		}else{
			id = vo.getProviderApplyId();
		}
		
		this.projectHibernateService.saveOrUpdateProvidCredit(vo);
		
		resp.getWriter().print(	"{success:true,applyId:'" + id +"'}");
	}
	
	/**
	 * 进入客户信用额度界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView applyCustomerCreditView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String strProjectId = request.getParameter("projectId");
		String strForm = request.getParameter("from");
		request.setAttribute("from", strForm);
		
		//得到客户信用额度
		TApplyCustomerCredit cust = this.projectJdbcService.getCustomerCredit(strProjectId);
		cust.setProjectId(strProjectId);
		request.setAttribute("cust", cust);
		
		request.setAttribute("projectId", strProjectId);
		
		return new ModelAndView("sapss/project/applyCustomerCredit");
	}
	
	/**
	 * 进入供应商信用额度界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView applyProviderCreditView(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String strProjectId = request.getParameter("projectId");
		String strForm = request.getParameter("from");
		request.setAttribute("from", strForm);
		
		//得到供应商的信用额度
		TApplyProviderCredit provider = this.projectJdbcService.getProviderCredit(strProjectId);
		provider.setProjectId(strProjectId);
		request.setAttribute("provider", provider);
		
		request.setAttribute("projectId", strProjectId);
		
		return new ModelAndView("sapss/project/applyProviderCredit");
	}

	/**
	 * 创建立项
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView create(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		TProjectInfo info = new TProjectInfo();
		info.setOrgName(userContext.getSysDept().getDeptname());
		if("信达安业务部".equals(info.getOrgName())) info.setIsCredit("Y");
		info.setNuderTaker(userContext.getSysUser().getRealName());
		info.setDeptId(userContext.getSysDept().getDeptid());
		info.setUnderTakerId(userContext.getSysDept().getDeptid());
		info.setApplyTime(DateUtils.getCurrTimeStr(2));
		//info.setProjectNo(generateIndex());
		String tt = request.getParameter("tradeType");
		info.setTradeType(Integer.valueOf(tt).intValue());
		info.setProjectState("1");
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("currency", Currency);

		request.setAttribute("main", info);
		request.setAttribute("revisable", "true");
		request.setAttribute("save_button_enable", false);
		request.setAttribute("popfrom", "add");
		return new ModelAndView("sapss/project/create");

	}

	/**
	 * 
	 * @param title
	 * @param pageResource
	 * @return
	 */
	private Boolean processora(String title, String pageResource) {
		List list = UserContextHolder.getLocalUserContext().getUserContext()
				.getGrantedResources();
		try {
			for (int i = 0; i < list.size(); i++) {

				SysResource item = (SysResource) list.get(i);
				if (title.compareTo(item.getResourcetitle()) == 0
						&& pageResource.compareTo(item.getResourcename()) == 0)
					return Boolean.FALSE;
			}// 没有权限控制:显亮;资源在上下文中:显亮;
			return Boolean.TRUE;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}

	/**
	 * 转到修改立项信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView modify(HttpServletRequest req, HttpServletResponse resp)
			throws IllegalAccessException, InvocationTargetException {
		String projectId = req.getParameter("projectId");
		String isCreditView = req.getParameter("isCreditView");
		if(projectId==null) {
			String projectNo = req.getParameter("projectNo");
			projectId = projectJdbcService.queryProjectIdByNo(projectNo);
		}
		req.setAttribute("projectId", projectId);
		req.setAttribute("isCreditView", isCreditView);
		String from = (String) req.getParameter("from");
		req.setAttribute("popfrom", from);
		TProjectInfo info = this.projectHibernateService.getTProjectInfo(projectId);

		Map<String, TProjectAccounting> map = this.projectJdbcService.getProjectAccounting(projectId);
		TProjectAccounting ta;
		for (int i = 1; i < map.size() + 1; i++) {
			ta = (TProjectAccounting) map.get(i + "");
			req.setAttribute(ta.getCurrency().toLowerCase()
					+ ta.getAccountingItemId(), ta.getAccountingValue());
		}
		
		//原来的币别
		String cr = (String)req.getAttribute("other5");
		if(info.getCurrency()==null && req.getAttribute("other5")!=null){
			if("1".equals(cr))
				info.setCurrency("CNY");
			else if ("2".equals(cr))
				info.setCurrency("USD");
		}
		//原来的备注
		if(info.getMask()==null && req.getAttribute("other6")!=null){
			info.setMask((String)req.getAttribute("other6"));
		}
		
		req.setAttribute("main", info);
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		req.setAttribute("currency", Currency);

//		Boolean enable = processora("立项流程审批内容修改", info.getTradeType()+ "_save_buttom");
		boolean enable = true;
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (userContext.getSysUser().getUserId().equals(info.getCreator())) {
			enable = false;
		}
		// 财务部人员允许修改立项申请单
		/*if ("WMCW".equals(userContext.getSysDept().getDeptShortCode())) {
			enable = false;
		}*/
		if (from != null) {
			//立项生效后,显示变更链接,显示变更tab页
			if("change".equals(from)){
				req.setAttribute("from","change");//
				req.setAttribute("disabledChangeTab",false);//
				return new ModelAndView("sapss/project/createInfoRChange");//转到立项只读页面+变更
			}
			if("changeW".equals(from)){
				req.setAttribute("from","changeW");//
				req.setAttribute("disabledChangeTab",false);//
				
				return new ModelAndView("sapss/project/createInfoRChange");//转到立项只读页面+变更
			}
			
			if ("iframe".equals(from)) {
				if (!enable) {//转到立项可写页面
					return new ModelAndView("sapss/project/create");
				} else {//转到立项只读页面
					req.setAttribute("save_button_enable", enable);
					req.setAttribute("revisable", !enable);					
					return new ModelAndView("sapss/project/workflowCreateInfo");
				}
			} else if ("view".equals(from) || "workflow".equals(from)) {
				req.setAttribute("revisable", "false");
			} else {
				req.setAttribute("revisable", "true");
			}
		}
		/** modify by cat**/
		//String user_dep_name = userContext.getSysDept().getDeptname();
		//req.setAttribute("user_dep_name", user_dep_name);
		/***/
		return new ModelAndView("sapss/project/create");
	}
	
	/**
	 * 转到修改立项信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView modify_bw(HttpServletRequest req, HttpServletResponse resp)
	throws IllegalAccessException, InvocationTargetException {
		
	    String projectId = req.getParameter("projectId");
	    String isCreditView = req.getParameter("isCreditView");
	    if(projectId==null) {
	        String projectNo = req.getParameter("projectNo");
	        projectId = projectJdbcService.queryProjectIdByNo(projectNo);
	    }
	    String contractGroupId = req.getParameter("contractGroupid");
	    
        String queryTime = req.getParameter("queryTime");
        String endQueryTime = req.getParameter("endQueryTime");	   
	    req.setAttribute("isCreditView", isCreditView);
	    String from = (String) req.getParameter("from");
	    req.setAttribute("popfrom", from);
	   
	   
	    
	    // 设置返回SAP数据
	    Map<String, String> maps = this.projectJdbcService.fetchSAPAccountingItem(projectId,contractGroupId, queryTime.replace("-", "").trim(), endQueryTime.replace("-", "").trim());
	    for (String key:maps.keySet()) {
	        req.setAttribute(key, maps.get(key));
	    }
	       
        
	    TProjectAccounting ta;
	    
	    //根据立项查
        if(StringUtils.isNotBlank(projectId) && StringUtils.isBlank(contractGroupId) ){
        	 Map<String, TProjectAccounting> map = this.projectJdbcService.getProjectAccounting(projectId);
      	   
     	    for (int i = 1; i < map.size() + 1; i++) {
     	        ta = (TProjectAccounting) map.get(i + "");
     	        req.setAttribute(ta.getCurrency().toLowerCase()
     	                + ta.getAccountingItemId(), ta.getAccountingValue());
     	    }
     	    
     	// 设置返回取得外围进仓信息
            Map<String, String> mapsWW = this.projectJdbcService.fetchReceItem(projectId);
            for (String key:mapsWW.keySet()) {
                req.setAttribute(key, maps.get(key));
            }
            if (!mapsWW.isEmpty()) {
                String total = mapsWW.get("rmbp3");
                if (total != null) {
                    mapsWW.put( "rmbs23_p", String.valueOf(Double.valueOf(total) * 0.0003) );
                }
            }
        }
        //根据合同组查
        if(StringUtils.isNotBlank(contractGroupId) && StringUtils.isNotBlank(projectId) ){
        	 Map<String, TProjectAccounting> map = this.projectJdbcService.getContractGroupAccounting(contractGroupId);
        	   
      	    for (int i = 1; i < map.size() + 1; i++) {
      	        ta = (TProjectAccounting) map.get(i + "");      	        
      	        req.setAttribute(ta.getCurrency().toLowerCase()
      	                + ta.getAccountingItemId(), ta.getAccountingValue());
      	    }
      	    
      	// 设置返回取得外围进仓信息
             Map<String, String> mapsWW = this.projectJdbcService.fetchReceItemByContractGroupid(contractGroupId);
             for (String key:mapsWW.keySet()) {
                 req.setAttribute(key, maps.get(key));
             }
             if (!mapsWW.isEmpty()) {
                 String total = mapsWW.get("rmbp3");
                 if (total != null) {
                     mapsWW.put( "rmbs23_p", String.valueOf(Double.valueOf(total) * 0.0003) );
                 }
             }
        }
        req.setAttribute("projectId", projectId);
	    req.setAttribute("contractGroupId", contractGroupId);
        TProjectInfo info = this.projectHibernateService.getTProjectInfo(projectId);
//	       for (int i=0;i<70;i++) {
//	            req.setAttribute("uss"+i, "u"+i);
//	            req.setAttribute("rmbs"+i, ""+i);
//	        }
	    
	    //原来的币别
	    String cr = (String)req.getAttribute("other5");
	    if(info.getCurrency()==null && req.getAttribute("other5")!=null){
	        if("1".equals(cr))
	            info.setCurrency("CNY");
	        else if ("2".equals(cr))
	            info.setCurrency("USD");
	    }
	    //原来的备注
	    if(info.getMask()==null && req.getAttribute("other6")!=null){
	        info.setMask((String)req.getAttribute("other6"));
	    }
	    
	    req.setAttribute("main", info);
	    Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
	    Collection<DictionaryRow> Currency = CurrencyMap.values();
	    req.setAttribute("currency", Currency);
	    
	    req.setAttribute("revisable", "false");
	    return new ModelAndView("sapss/project/create_bw");
	}

	/**
	 * 立项审批界面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView projectExamine(HttpServletRequest req,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String projectId = req.getParameter("businessRecordId");
		TProjectInfo tp =  this.projectHibernateService.getTProjectInfo(projectId);
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tp.getTradeType().toString());
		req.setAttribute("tradeType", tradeType);
		String taskId = req.getParameter("taskId");
		String isCreditView ="0";
		String taskName = WorkflowService.getTaskInstanceContext(taskId).getTaskName();
		
		if(taskName.indexOf("风险管理部审核")>=0) isCreditView = "1";
		else isCreditView = "0" ;
		
		req.setAttribute("businessRecordId", projectId);
		req.setAttribute("taskId", taskId);
		req.setAttribute("iframeSrc","projectController.spr?action=modify&from=iframe&projectId="+ projectId + "&isCreditView=" + isCreditView);
		req.setAttribute("submitUrl","projectController.spr");
		req.setAttribute("action","submitProjectInfo");
		req.setAttribute("revisable", "false");// 提交审批不能上传附件
		req.setAttribute("iframeForms","Form.serialize(window.frames['content']['mainForm'])");
		return new ModelAndView("sapss/project/workflowManager");
	}

	/**
	 * 生成立项编码
	 */
	private String generateIndex() {
		UserContext cxt = UserContextHolder.getLocalUserContext()
				.getUserContext();

		Map map = this.projectJdbcService.generateProjectNo(cxt.getSysDept()
				.getDeptid());
		String deptShortCd = cxt.getSysDept().getDeptShortCode();
		Object ct = map.get("CREATOR_TIME");
		Object pno = map.get("PROJECT_NO");
		String year = "";
		String no = "0";
		int index = 0;
		if (ct == null || pno == null) {
			year = DateUtils.getCurrDate(1).substring(2, 4);
		} else {
			year = (String) ct;
			no = (String) pno;
			year = year.substring(2, 4);// 2009
			if (Integer.valueOf(year).compareTo(
					Integer.valueOf(DateUtils.getCurrDate(1).substring(2, 4))) == 0) {
				// 相等,表示同一年
//				no = no.substring(3, no.length());// 09T001
				no = no.substring(no.length()-3, no.length());
				
			} else {// 新一年
				year = DateUtils.getCurrDate(1).substring(2, 4);
				no = "0";
			}
		}

		index = Integer.valueOf(no).intValue();
		index = index + 1;// 递增
		int len = String.valueOf(index).length();
		String temp = "";
		for (int j = len; j < 3; j++) {
			temp = temp + "0";
		}
		return year + deptShortCd + temp + index;
	}

	/**
	 * 保存立项信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void saveProjectInfo(HttpServletRequest request,
			HttpServletResponse response, TProjectInfo projectInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
//		this.saveMain(request, response);
		String projectId = request.getParameter("pid");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		projectInfo.setCreator(userContext.getSysUser().getUserId());
		projectInfo.setCreatorTime(DateUtils.getCurrTime(2));
		projectInfo.setIsAvailable("1");
		if (projectId == null || "".equals(projectId)) {
			String projectNo = generateIndex();
			projectInfo.setProjectNo(projectNo);
			
			projectId = this.projectHibernateService.saveProjectInfo(projectInfo);
			response.getWriter().print(	"{success:true,projectId:'" + projectId + "',projectNo:'"+projectNo+"'}");
			
		} else {
			projectInfo.setProjectId(projectId);
			this.projectJdbcService.updateProjectMainInfo(projectInfo);
			response.getWriter().print(	"{success:true,projectId:'" + projectId + "'}");
		}
	}
	/**
	 * 保存授信信息
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void updateProjectCreditDes(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String msg = "";
		try{
			String projectId = request.getParameter("projectId");
			String creditDes = request.getParameter("creditDes");
			creditDes=URLDecoder.decode(creditDes,"utf-8");
			TProjectInfo projectInfo = projectHibernateService.getProjectInfo(projectId);
			projectInfo.setCreditDescription(creditDes);		
			projectHibernateService.saveOrUpdateProjectInfo(projectInfo);
		}catch(Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();		
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交成功！");
		else
			jo.put("returnMessage", msg);
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	/**
	 * 流程提交审批
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitProjectInfo(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String msg = "";
		try {
		String projectId = request.getParameter("projectId");
		String taskId = request.getParameter("workflowTaskId");
		TProjectInfo projectInfo = projectHibernateService.getTProjectInfo(projectId);
		projectInfo.setWorkflowTaskId(taskId);
		String workflowExamine = request.getParameter("workflowExamine");
		projectInfo.setWorkflowExamine(workflowExamine);
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		projectInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		//取得货币码
		String currency = this.projectJdbcService.getProjectAccountValue(projectId,"OTHER","5");
		//如果是人民币(兼容早期)
		if(currency!=null && !"".equals(currency)){
			if("1".equals(currency)){
		  // projectInfo.setSum(String.valueOf(Double.valueOf(projectInfo.getSum())
			//	* Double.valueOf(Constants.properties.getProperty("exchange_usd_to_cny"))));
				projectInfo.setCurrency("CNY");
			}else
				projectInfo.setCurrency("USD");
		}
		projectInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		projectInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ projectInfo.getProjectNo()
				+ "|"
				+ projectInfo.getProjectName());
		projectInfo.setProjectId(projectId);
		String doWorkflow = request.getParameter("doWorkflow");
		//如果总金额为空,显性提示
		if(projectInfo.getSum()==null || "".equals(projectInfo.getSum()))
			throw new BusinessException("总金额为空!");
		this.projectJdbcService.submitWorkflow(taskId, projectInfo);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交成功！");
		else
			jo.put("returnMessage", msg);
		this.operateSuccessfullyWithString(response, jo.toString());

	}
	
	private void accountAssemble(String itemName,int i,Map<String,String> validate,
			HttpServletRequest req,String projectId,List<TProjectAccounting> list){
		String parameter = itemName + i;
		String cash = req.getParameter(parameter);
		if(validate.keySet().contains(parameter)&&(cash==null || "".equals(cash)))
			throw new BusinessException(validate.get(parameter)+",不能为空,请检查!");
		TProjectAccounting ta = new TProjectAccounting();
		ta.setAccountingItem(i + "");
		ta.setAccountingItemId(i);
		ta.setCurrency(itemName.toUpperCase());
		ta.setAccountingValue(cash == null || "".equals(cash) ? "0" : cash);
		ta.setProjectId(projectId);
		list.add(ta);
	}
	private void dealAssemble(List<TProjectAccounting> list,HttpServletRequest req){
		String tradeType = req.getParameter("tradeType");
		String projectId = req.getParameter("projectId");
		Map<String,String> validate ;
		//内贸
		if(tradeType.equals("7")||tradeType.equals("10")||tradeType.equals("8")){
		   validate  = ProjectConstants.VALIDATE_HOMETRADE;
		   accountAssemble("other", 4, validate, req, projectId, list);
		   for(Integer i:ProjectConstants.EV_D_TT_HOMETRADE){
			   accountAssemble("rmb", i, validate, req, projectId, list);
		   }
		}
		//进口，进料加工（内销，外销）,转口
		else if(tradeType.equals("1")||tradeType.equals("3")||tradeType.equals("9")||tradeType.equals("11")||tradeType.equals("12")){
			validate  = ProjectConstants.VALIDATE_IMPORT;
			accountAssemble("other", 4, validate, req, projectId, list);
			for(Integer i:ProjectConstants.EV_D_TT_IMPORT){
				   accountAssemble("rmb", i, validate, req, projectId, list);
				   accountAssemble("us", i, validate, req, projectId, list);
		    }
		}
		//出口
		else if(tradeType.equals("2")||tradeType.equals("4")){
			validate = ProjectConstants.VALIDATE_EXPORT;
			accountAssemble("other", 4, validate, req, projectId, list);
			for(Integer i:ProjectConstants.EV_D_TT_EXPORT){
				   accountAssemble("rmb", i, validate, req, projectId, list);
				   accountAssemble("us", i, validate, req, projectId, list);
		    }
			accountAssemble("other", 3, validate, req, projectId, list);
		}
		//代理
		else if(tradeType.equals("5")||tradeType.equals("6")){
			validate  = ProjectConstants.VALIDATE_PROXY;
			accountAssemble("other", 4, validate, req, projectId, list);
			for(Integer i:ProjectConstants.EV_D_TT_PROXY){
				   accountAssemble("rmb", i, validate, req, projectId, list);
				   accountAssemble("us", i, validate, req, projectId, list);
		    }
		}
		else {
			validate = ProjectConstants.PROJECT_VALIDATE.get(tradeType);
			for (int i = 1; i < 32; i++) {
				accountAssemble("rmb", i, validate, req, projectId, list);
			}
			for (int i = 1; i < 32; i++) {
				accountAssemble("us", i, validate, req, projectId, list);
			}
			for (int i = 1; i < 5; i++) {
				accountAssemble("other", i, validate, req, projectId, list);
			}
		}
			
	}

	/**
	 * 保存核算页信息
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void saveDetail(HttpServletRequest req, HttpServletResponse resp,
			ProjectDetailVO vo) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {

		vo.setClassName(vo.getClassName() == null ? " " : vo.getClassName());
		vo.setDestinationPort(vo.getDestinationPort() == null ? " " : vo
				.getDestinationPort());
		vo.setNo(vo.getNo() == null ? Double.valueOf("0") : vo.getNo());
		vo.setShipmentDate(vo.getShipmentDate() == null ? " " : vo
				.getShipmentDate());
		vo.setShipmentPort(vo.getShipmentPort() == null ? " " : vo
				.getShipmentPort());
		vo.setSpec(vo.getSpec() == null ? " " : vo.getSpec());
		if(vo.getExchangeRate()==null||Double.parseDouble(vo.getExchangeRate())<=0)
			throw new BusinessException("请填写正确的汇率!");
//		if ("2".equals(req.getParameter("other5")))
//			vo.setSum(vo.getSum() == null ? "0" : (Double.valueOf(vo.getSum())
//					* Double.valueOf(Constants.properties.getProperty("exchange_usd_to_cny")))
//					+ "");
//		else
//			vo.setSum(vo.getSum() == null ? "0" : vo.getSum());
		
		this.projectJdbcService.saveDetailInfo(vo);
		List<TProjectAccounting> list = new ArrayList<TProjectAccounting>();
		
		/****/
		dealAssemble(list,req);
        /****/
		int records = this.projectJdbcService
				.saveOrUpdateProjectAccounting(list);

		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		this.operateSuccessfullyWithString(resp, jo.toString());

	}


	/**
	 * 测试用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveMain(HttpServletRequest request,
			HttpServletResponse response) {
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String elem = (String) en.nextElement();
			System.out.println(elem + ": " + request.getParameter(elem));
		}
	}

	/**
	 * 转到立项管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toManager(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 从配置页面取得立项类型
		String tradeType = request.getParameter("tradeType");
		String typeName = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tradeType);
		request.setAttribute("typeName", typeName);
		request.setAttribute("tradeType", tradeType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("loginer", userContext.getSysUser()
				.getUserId());

		return new ModelAndView("sapss/project/manager");
	}

	/**
	 * 立项管理页面grid数据源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rtnInitiationInfo(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		String grid_sql = "select * from t_project_info where is_available='1' order by apply_time desc";
		String grid_columns = "project_id,project_name,apply_time,project_no,trade_type,nuder_taker,org_name,project_state";
		String grid_size = "10";
		req.setAttribute("grid_sql", grid_sql);
		req.setAttribute("grid_columns", grid_columns);
		req.setAttribute("grid_size", grid_size);
		servlet.processGrid(req, resp, true);
	}

	/**
	 * 转到立项页面 在流程中嵌入立项页面里加iframe用行判断
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toCreate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String from = (String) request.getParameter("from");
		request.setAttribute("popfrom", from);
		request.setAttribute("revisable", "true");
		return new ModelAndView("sapss/project/create");
	}

	/**
	 * 删除一条记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String projectId = (String) req.getParameter("projectId");
		int i = this.projectJdbcService.deleteProjectInfo(projectId);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (i > 0)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}
	}

	/**
	 * 查找
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	// action=find&dict_deptDiv=请选择...&underTaker=&projectNo=&projectName=&applyTime=&state=
	public void find(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ExtComponentServlet servlet = new ExtComponentServlet();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Map<String, String> map = extractFR(req);
		StringBuffer sb = new StringBuffer();
		sb.append("select tpinfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime, TRADE_TYPE TRADE_TYPE_D_BUSINESS_TYPE,PROJECT_STATE PROJECT_STATE_D_PROJECT_STATE from t_project_info tpinfo where is_available='1' ");
		String deptId = map.get("deptId");
		if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
			// 处理多选
			if (StringUtils.isNotBlank(deptId)) {
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				sb.append(" and dept_id in (" + deptId + ")");				
			}
			sb.append(" and dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",");
			sb.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			sb.append(" and dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'");
		}
		
		if (map.get("underTaker") != null)
			sb.append(" and nuder_taker like '%" + map.get("underTaker")+ "%'");
		if (map.get("projectNo") != null)
			sb.append(" and project_no like '%" + map.get("projectNo") + "%'");
		if (map.get("className") != null)
			sb.append(" and class_Name like '%" + map.get("className") + "%'");
		if (map.get("unitName") != null)
			sb.append(" and (customer_Link_Man like '%" + map.get("unitName") + "%' or "+" provider_Link_Man like '%" + map.get("unitName") + "%')");
		if (map.get("unitNameN") != null)
			sb.append(" and  customer_Link_Man not like '%" + map.get("unitNameN") + "%' and "+" provider_Link_Man not like '%" + map.get("unitNameN") + "%'");

		if (map.get("projectName") != null)
			sb.append(" and project_name like '%" + map.get("projectName")
					+ "%'");
		if (map.get("tradeType") != null)// 立项管理页面
			sb.append(" and trade_type = '" + map.get("tradeType") + "'");
		else if (map.get("tradeTypeSingle") != null)// 立项新建页面
			sb.append(" and trade_type = '" + map.get("tradeTypeSingle") + "'");
		if (map.get("state") != null)
			sb.append(" and project_state = '" + map.get("state") + "'");
		
		if (map.get("applyTime") != null)
			sb.append(" and apply_Time >= '" + map.get("applyTime") + " 00:00:00'");
		
		if (map.get("endApplyTime") != null)
			sb.append(" and apply_Time <= '" + map.get("endApplyTime") + " 24:00:00'");
		
		
		sb.append(" order by apply_time desc");

		StringBuffer sb1 = new StringBuffer();
		sb1.append("select main.* from (");
		sb1.append(sb);
		sb1.append(")main ");
		if (map.get("applyTime") == null && map.get("endApplyTime") == null){
			/*sb1.append("where main.applyTime between to_date('");
			sb1.append(map.get("applyTime"));
			sb1.append("','yyyy-mm-dd hh24:mi:ss') and to_date('");
			sb1.append(map.get("endApplyTime"));
			sb1.append("','yyyy-mm-dd hh24:mi:ss')");
		}else if (map.get("applyTime") != null)
			sb1.append("where main.apply_time like '%" + map.get("applyTime") + "%'");
		else{//默认为本日前7天
*/			/*sb1.append("where main.applyTime between to_date('");
			Calendar cd = Calendar.getInstance();
			cd.add(Calendar.DAY_OF_YEAR, -365);//向前365天
			String fd = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cd.getTime());
			sb1.append(fd);
			sb1.append("','yyyy-mm-dd hh24:mi:ss') and to_date('");
			Calendar cd1 = Calendar.getInstance();
			cd1.add(Calendar.DAY_OF_YEAR, 1);//向后一天
			String fd1 = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cd1.getTime());
			sb1.append(fd1);
			sb1.append("','yyyy-mm-dd hh24:mi:ss')");*/
		}
		String grid_sql =sb1.toString();
		String grid_columns = "project_id,project_name,apply_time,project_no,PROJECT_STATE_D_PROJECT_STATE,KEEP_FLAG,TRADE_TYPE_D_BUSINESS_TYPE ,nuder_taker,org_name,creator,approved_time";
		String grid_size = "10";
		req.setAttribute("grid_sql", grid_sql);
		req.setAttribute("grid_columns", grid_columns);
		req.setAttribute("grid_size", grid_size);
		servlet.processGrid(req, resp, true);
	}
	
	/**
	 * 查找不控制时间
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void find_bw(HttpServletRequest req, HttpServletResponse resp)
	throws Exception {
	    ExtComponentServlet servlet = new ExtComponentServlet();
	    UserContext userContext = UserContextHolder.getLocalUserContext()
	    .getUserContext();
	    Map<String, String> map = extractFR(req);
	    StringBuffer sb = new StringBuffer();
	    sb.append("select tpinfo.*,to_date(apply_time,'yyyy-mm-dd hh24:mi:ss') applyTime, TRADE_TYPE TRADE_TYPE_D_BUSINESS_TYPE,PROJECT_STATE PROJECT_STATE_D_PROJECT_STATE from t_project_info tpinfo where is_available='1' ");
	    String deptName = map.get("dict_deptDiv") == null ? userContext.getSysDept().getDeptname() : map.get("dict_deptDiv");
//	    if("1".equals(userContext.getSysDept().getIsFuncDept())){
//	        if(!"".equals(deptName) && deptName.indexOf(",")!=-1){
//	            String[] dp = deptName.split(",");
//	            deptName="";
//	            for (int i = 0; i < dp.length; i++) {
//	                if(i==(dp.length-1))
//	                    deptName = deptName+"'"+dp[i]+"'";
//	                else
//	                    deptName = deptName+"'"+dp[i]+"',";
//	            }
//	        }else
//	            deptName = "'"+deptName+"'";
//	        sb.append(deptName.indexOf("请选择") != -1 ? "" : "and org_name in("+deptName+")");
//	        sb.append("and dept_id in ("+userContext.getGrantedDepartmentsId()+") ");
//	    }else{
//	        sb.append("and org_name='");
//	        sb.append((deptName.indexOf("请选择") != -1 ? userContext.getSysDept().getDeptname() : deptName)	+ "'");
//	    }	
	    
	    if (map.get("underTaker") != null)
	        sb.append(" and nuder_taker like '%" + map.get("underTaker")+ "%'");
	    if (map.get("projectNo") != null)
	        sb.append(" and project_no like '%" + map.get("projectNo") + "%'");
	    if (map.get("className") != null)
	        sb.append(" and class_Name like '%" + map.get("className") + "%'");
	    if (map.get("unitName") != null)
	        sb.append(" and (customer_Link_Man like '%" + map.get("unitName") + "%' or "+" provider_Link_Man like '%" + map.get("unitName") + "%')");
	    if (map.get("projectName") != null)
	        sb.append(" and project_name like '%" + map.get("projectName")
	                + "%'");
	    if (map.get("tradeType") != null)// 立项管理页面
	        sb.append(" and trade_type = '" + map.get("tradeType") + "'");
	    else if (map.get("tradeTypeSingle") != null)// 立项新建页面
	        sb.append(" and trade_type = '" + map.get("tradeTypeSingle") + "'");
	    if (map.get("state") != null)
	        sb.append(" and project_state = '" + map.get("state") + "'");
	    
	    if (map.get("applyTime") != null)
	        sb.append(" and apply_Time >= '" + map.get("applyTime") + " 00:00:00'");
	    
	    if (map.get("endApplyTime") != null)
	        sb.append(" and apply_Time <= '" + map.get("endApplyTime") + " 24:00:00'");
	    
	    
	    sb.append(" order by apply_time desc");
	    
	    String grid_sql =sb.toString();
	    String grid_columns = "project_id,project_name,apply_time,project_no,PROJECT_STATE_D_PROJECT_STATE,TRADE_TYPE_D_BUSINESS_TYPE ,nuder_taker,org_name,creator,approved_time";
	    String grid_size = "10";
	    req.setAttribute("grid_sql", grid_sql);
	    req.setAttribute("grid_columns", grid_columns);
	    req.setAttribute("grid_size", grid_size);
	    servlet.processGrid(req, resp, true);
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++) {
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		} catch (UnsupportedEncodingException e) {
		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 显示合同组列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showContractGroup(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		req.setAttribute("projectId", req.getParameter("projectId"));
		return new ModelAndView("sapss/queryForm/findContractGroup");
	}
	
	/**
	 * 显示合同组列表2
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showContractGroup2(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		req.setAttribute("projectId", req.getParameter("projectId"));
		return new ModelAndView("sapss/queryForm/findContractGroup2");
	}

	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView projectManager(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		if(txt.getSysDept().getDeptid().equals(Constants.FILE_DEPT_NAME_ID)){
			req.setAttribute("fileDisable", "false");
		}
		if(txt.getSysDept().getDeptid().equals(Constants.CLOSE_DEPT_NAME_ID)){
			req.setAttribute("closeDisable", "false");
		}
		return new ModelAndView("sapss/project/projectManager");
	}
	
	public void closeProject(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		projectJdbcService.closeProject(strIdList);
		JSONObject jo = new JSONObject();		
		jo.put("returnMessage", "操作成功！");
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	public void fileProject(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		projectJdbcService.fileProject(strIdList);
		JSONObject jo = new JSONObject();		
		jo.put("returnMessage", "操作成功！");
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView projectManager_bw(HttpServletRequest req,
	        HttpServletResponse resp) throws Exception {
	    return new ModelAndView("sapss/project/projectManager_bw");
	}
	/**
	 * 直接保存立项信息为有效状态
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void saveToPass(HttpServletRequest req, HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String projectId = req.getParameter("projectId");
		if(projectId==null||"".equals(projectId))
			throw new BusinessException("项目号为空！");
		projectJdbcService.updateProjectToPass(projectId);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		this.operateSuccessfullyWithString(resp, jo.toString());

	}
	
	public ModelAndView selectProjrctInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		return new ModelAndView("sapss/project/selectProjectInfo");
	}
	
	public void queryProjectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String strProjectName = extractFR(request, "projectname"); // request.getParameter("projectname");
		String strProjectNo = request.getParameter("projectno");
		String deptId = request.getParameter("deptId");
		String tradeType = request.getParameter("tradeType");

		String strSql = getQueryProjectInfoSQL(strProjectName, strProjectNo, deptId, tradeType);

		String grid_columns = "PROJECT_ID,PROJECT_NAME,PROJECT_NO,ORG_NAME";

		String grid_size = "20";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);


		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	public String getQueryProjectInfoSQL(String strProjectName, String strProjectNo, String deptId, String tradeType)
	{
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();
		String strSql = "select a.project_id,a.project_name,a.project_no ,a.org_name" + " from t_project_info a " + "where  a.is_available = '1'";
		if (strProjectName != null && !"".equals(strProjectName))
		{
			strSql = strSql + " and a.project_name like '%" + strProjectName + "%'";
		}

		if (strProjectNo != null && !"".equals(strProjectNo))
		{
			strSql = strSql + " and a.project_no like '%" + strProjectNo + "%'";
		}

		if (tradeType != null && !"".equals(tradeType))
		{
			strSql = strSql + " and a.trade_type = '" + tradeType + "'";
		}
		if(StringUtils.isNotBlank(deptId))//有选部门
			strSql = strSql + " and a.dept_id = '" + deptId + "'";
		else if(txt.getSysDept().getIsFuncDept()=="1")//是职能部门
			strSql+=" and a.dept_id in("+txt.getGrantedDepartmentsId()+")";
		else //自己所在的部门 
			strSql+=" and a.dept_id in('"+txt.getSysDept().getDeptid()+"')";
		// 立项状态为生效,变更通过
		strSql = strSql + " and a.project_state in('3','8')";
		
		return strSql;
	}
	

	public ModelAndView dealPrintV1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] projectIds = request.getParameterValues("projectId");
		List<ProjectPrintDto> list = projectJdbcService.queryProjects4Print(projectIds);
		request.setAttribute("list", list);
		return new ModelAndView("sapss/project/printV1");
    }
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public ModelAndView dealPrintV2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String projectId = request.getParameter("projectId");
		TProjectInfo info = this.projectHibernateService.getTProjectInfo(projectId);

		Map<String, TProjectAccounting> map = this.projectJdbcService.getProjectAccounting(projectId);
		
		List<CommonChangeDto> changeList = projectJdbcService.queryChangeDtos(projectId);
		request.setAttribute("changeList", changeList);
		
		List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(projectId);
		request.setAttribute("taskHis", list);
		TProjectAccounting ta;
		for (int i = 1; i < map.size() + 1; i++) {
			ta = (TProjectAccounting) map.get(i + "");
			request.setAttribute(ta.getCurrency().toLowerCase()
					+ ta.getAccountingItemId(), ta.getAccountingValue());
		}
		request.setAttribute("main", info);
		return new ModelAndView("sapss/project/printV2");
    }
	
	public ModelAndView seniorExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String projectId = request.getParameter("businessRecordId");

		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", projectId);

		TProjectInfo project = projectHibernateService.getTProjectInfo(projectId);
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE",  project.getTradeType().toString());
		request.setAttribute("tradeType", tradeType);


		request.setAttribute("iframeSrc","projectController.spr?action=modify&from=view&projectId="+ projectId);

        request.setAttribute("person", request.getParameter("person"));
		return new ModelAndView("sapss/workflow/particular/projectWorkFlow");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@Autowired
	private SaleContractJdbcService saleContractJdbcService;
	public void setSaleContractJdbcService(
			SaleContractJdbcService saleContractJdbcService) {
		this.saleContractJdbcService = saleContractJdbcService;
	}
	public void seniorSubmitSalesInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String businessRecordId = request.getParameter("businessRecordId");
		String workflowExamine = request.getParameter("workflowExamine");
		String person = request.getParameter("person");
		
		if(person!=null && "1".equals(person))
			person = "股份公司总经理审批";
		else
			person = "股份公司董事长审批";
		
		this.saleContractJdbcService.saveToWorkflow(businessRecordId,workflowExamine,person);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		this.operateSuccessfullyWithString(response, jo.toString());

	}
}
