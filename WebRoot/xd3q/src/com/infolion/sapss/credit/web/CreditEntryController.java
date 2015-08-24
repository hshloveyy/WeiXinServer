/*
 * @(#)CreditEntryController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.credit.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditEntryHibernateService;
import com.infolion.sapss.credit.service.CreditEntryJdbcService;
import com.infolion.sapss.payment.web.PaymentImportsInfoQueryVO;
import com.infolion.sapss.project.domain.TProjectInfo;

/**
 * 
 * <pre>
 * 控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CreditEntryController extends BaseMultiActionController
{
	private final Log log = LogFactory.getLog(CreditEntryController.class);
	// 服务类注入
	@Autowired
	private CreditEntryHibernateService creditEntryHibernateService;

	public void setCreditEntryHibernateService(CreditEntryHibernateService creditEntryService)
	{
		this.creditEntryHibernateService = creditEntryService;
	}

	@Autowired
	private CreditEntryJdbcService creditEntryJdbcService;

	public void setCreditEntryJdbcService(CreditEntryJdbcService creditEntryJdbcService)
	{
		this.creditEntryJdbcService = creditEntryJdbcService;
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
	public ModelAndView defaultMethod(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		return new ModelAndView();
	}

	/**
	 * 新增信用证开证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("purTradeType", Constants.getPurTradeType(tradeType));

		TCreditInfo info = this.creditEntryJdbcService.getInitEntity(tradeType);

		// 保存按钮是否 灰色
		boolean saveDisabled = false;
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		// 贸易类型
		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", "true");
		request.setAttribute("main", info);
		request.setAttribute("CHANGE_CREDIT_STATUS", false);
		return new ModelAndView("sapss/credit/creditEntryCreate");

	}

	/**
	 * 修改信用证开证信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 保存按钮是否 灰色
		boolean saveDisabled = true;
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		boolean revisable = true;

		// 信用证ID
		String creditId = request.getParameter("creditId");
		request.setAttribute("creditId", creditId);
		// 操作类型
		String operate = (String) request.getParameter("operate");
		request.setAttribute("operate", operate);

		TCreditInfo info = this.creditEntryHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditEntryJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditEntryJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditEntryJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		String deptName = context.getSysDept().getDeptname();
		String status = info.getCreditState();
		if("5".equals(status)||"7".equals(status)||"8".equals(status)||"11".equals(status)){
			if(deptName!=null && deptName.indexOf("贸管")!=-1){
				request.setAttribute("CHANGE_CREDIT_STATUS", true);
				request.setAttribute("revisable", "true");
			}else
				request.setAttribute("CHANGE_CREDIT_STATUS", false);
		}else
			request.setAttribute("CHANGE_CREDIT_STATUS", false);
		log.debug("修改状态creditId:" + creditId);
		// log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate +
		// " 上传:" + revisable);
		request.setAttribute("purTradeType", Constants.getPurTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditEntryCreate");
	}
	/**
	 * 保存信用证状态
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveCreditStatus(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String creditId = request.getParameter("creditId");
		String status = request.getParameter("creditStatus");
		JSONObject jo = new JSONObject();
		this.creditEntryJdbcService.saveCreditStatus(creditId,status);
		jo.put("success",true);
		jo.put("creditid",creditId);
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 工作流审批信用证信息详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView workflow(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 信用证ID
		String creditId = request.getParameter("creditId");
		request.setAttribute("creditId", creditId);
		// 操作类型
		String operate = (String) request.getParameter("operate");
		request.setAttribute("operate", operate);

		TCreditInfo info = this.creditEntryHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		// 工作流节点名称的标识码
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		request.setAttribute("revisable", "false");// 提交审批不能上传附件

		String revisable = "false";
		//由此，控制那些节点可以上传附件。
		if("5".equals(taskType))
		{
			 revisable = "true";
		}
		request.setAttribute("revisable", revisable);// 提交审批不能上传附件
		request.setAttribute("purTradeType", Constants.getPurTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditEntryWorkFlow");
	}

	/**
	 * 保存信用证开证信息
	 * 
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response, TCreditInfo creditInfo) throws IllegalAccessException, InvocationTargetException, IOException
	{
		String creditId = request.getParameter("creditId");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");

		JSONObject jo = new JSONObject();
		// 采购合同编码
		String contractPurchaseId = request.getParameter("contractPurchaseId");

		if (creditInfo.getContractNo() == null || "".equals(creditInfo.getContractNo()))
		{
			throw new BusinessException("[合同号]为必填！");
		}

		jo = this.creditEntryHibernateService.saveOrUpdateCreditInfo(creditInfo, creditId, contractPurchaseId);
		String creditId1 = jo.get("creditid").toString();
		request.setAttribute("creditId", creditId1);
		// log.debug("保存creditId:" + creditId1 + "::" + creditId);
		log.debug("信用证开征保存：contractPurchaseId：" + contractPurchaseId);
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 信用证开证信息 删除动作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean isSuccess = false;
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 删除动作实现
		String creditId = (String) request.getParameter("creditId");

		isSuccess = this.creditEntryJdbcService.deletecreditInfo(creditId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (isSuccess)
		{
			// this.operateSuccessfully(response);
			ExceptionPostHandle.generateInfoMessage(response, "删除成功");
		}
		else
		{
			ExceptionPostHandle.generateInfoMessage(response, "记录不能删除");
		}
	}

	/**
	 * 转到信用证开证管理页面 xds
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditEntryManageView(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		// log.debug("贸易类型:" + tradeType);
		return new ModelAndView("sapss/credit/creditEntryManage");
	}

	/**
	 * 综合查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditEntryManageQuery(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		int deptIndex = userContext.getSysDept().getDeptShortCode().indexOf("MG");
        request.setAttribute("deptIndex", deptIndex);
		return new ModelAndView("sapss/credit/creditEntryManageQuery");
	}

	/**
	 * 取得信用证开证信息列表数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCreditEntryList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		ExtComponentServlet servlet = new ExtComponentServlet();
		String grid_sql = this.creditEntryJdbcService.getCreditEntryListSql(tradeType);
		String grid_columns = "CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";
		String grid_size = request.getParameter("limit");
		log.debug("取得信用证开证信息列表数据:" + grid_sql);
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		servlet.processGrid(request, response, true);
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		Map<String, String> map = extractFR(request);

		String grid_sql = creditEntryJdbcService.getQuerySql(map);

		String grid_columns = "CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,EKKO_UNSEZ,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		// log.debug("信用证开证查询:" + grid_sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 综合查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Map<String, String> map = extractFR(request);

		String grid_sql = creditEntryJdbcService.getQueryListSql(map);

		String grid_columns = "CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,AMOUNT,CREDIT_STATE_D_CREDIT_STATE,CREATOR";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		log.debug("信用证开证综合查询:" + grid_sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 进入合同选择窗体,查询登陆用户所在部门的合同信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView selectContractInfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 贸易类型,
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 合同单据状态， 3 为审核通过。
		String orderState = request.getParameter("orderState");
		request.setAttribute("orderState", orderState);
		// 合同号
		String contractNo = request.getParameter("contractNo");
		request.setAttribute("contractNo", contractNo);
		// 合同类型
		String contractType = request.getParameter("contractType");
		request.setAttribute("contractType", contractType);

		// log.debug("贸易类型:" + tradeType + "合同号:" + contractNo);
		return new ModelAndView("sapss/credit/selectPurchaseInfo");
	}

	/**
	 * 查看信用证相关联的合同信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView viewContractInfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 信用证ID
		String creditId = request.getParameter("creditId");
		request.setAttribute("creditId", creditId);
		// 贸易类型,
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		// 合同类型 contractType=P 采购 S 销售
		String contractType = request.getParameter("contractType");
		request.setAttribute("contractType", contractType);
		// // 合同单据状态， 3 为审核通过。
		// String orderState = request.getParameter("orderState");
		// request.setAttribute("orderState", orderState);
		log.debug("查看相关合同信息,信用证ID:" + creditId);

		return new ModelAndView("sapss/credit/viewContractInfo");
	}

	/**
	 * 列出信用证相关联的采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void viewPurchaseListByCreditId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String creditId = request.getParameter("creditId");

		String sql = this.creditEntryJdbcService.getViewPurchaseListByCreditIdSql(creditId);
		String grid_columns = "CONTRACT_ID,CREDIT_ID,SAP_ORDER_NO,CONTRACT_NO,CONTRACT_NAME,YMAT_GROUP";
		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
	    log.debug("列出信用证相关联的采购合同信息SQL：" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 查询采购合同相关信息 LJX 20090310
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String sql = this.creditEntryJdbcService.getQueryPurchaseSql(request);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,EKKO_UNSEZ,CONTRACT_NAME,SAP_ORDER_NO,YMAT_GROUP";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		// log.debug("选择采购合同SQL：" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 查看信用证开证单据所关联的采购合同列表信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void viewPurchaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String sql = this.creditEntryJdbcService.getQueryPurchaseSql(request);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRA" + "CT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,YMAT_GROUP";

		String grid_size =request.getParameter("limit");
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		// log.debug("查看采购合同SQL：" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 信用证开证审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditEntryExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String creditId = request.getParameter("businessRecordId");
		String iframeSrc = "";
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);

		String taskName = taskInstanceContext.getTaskName();
		String taskType = this.creditEntryHibernateService.getTaskType(taskName);
		iframeSrc = "creditEntryController.spr?action=workflow&tradeType=" + "" + "&operate=iframe&creditId=" + creditId + "&taskType=" + taskType;

		
		request.setAttribute("businessRecordId", creditId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("iframeSrc", iframeSrc);
		request.setAttribute("submitUrl", "creditEntryController.spr?action=submitWorkflow");
		// request.setAttribute("action", "submitWorkflow");
		//request.setAttribute("revisable", "false");// 提交审批不能上传附件
	    log.debug("businessRecordId：" + creditId + " taskName:" + taskName);

		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 提交流程审批
	 * 
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, TCreditInfo creditInfo) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskName = "";
		String taskId = request.getParameter("workflowTaskId");
		String creditId = request.getParameter("creditId");

		String doWorkflow = request.getParameter("doWorkflow");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		// 采购合同编码
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		
		TCreditInfo info = this.creditEntryHibernateService.getTCreditInfo(creditId);

		// 审批时候，初始化各参数，并根据汇率计算出金额,同时传流程参数，做金额判断。
		this.creditEntryHibernateService.initSubmitWorkflow(creditInfo, doWorkflow, workflowLeaveTransitionName, workflowExamine);
		String exceptionMsg = "";
		// 审批时候，到各节点判断是否有必输字段还未输入，如果有则抛出异常提示
		exceptionMsg = this.creditEntryHibernateService.verifyFilds(creditInfo, info, taskId, taskName,contractPurchaseId);

		if ("".equals(exceptionMsg) == false)
		{
			throw new BusinessException(exceptionMsg);
		}

		log.debug("当前工作流节点名称:" + taskName);
	    log.debug("提交审批信用证ID:" + creditInfo.getCreditId() +  " doWorkflow:" + doWorkflow);
		log.debug("WorkflowExamine:" + creditInfo.getWorkflowExamine());
		log.debug("contractPurchaseId:" + contractPurchaseId);

		JSONObject jo = new JSONObject();
		jo = this.creditEntryJdbcService.submitWorkflow(creditInfo, taskId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(), "UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1)
			{
				wait = wait.substring(0, pos);
			}
			return wait;
		}
		catch (UnsupportedEncodingException e)
		{}
		return null;
	}

	/**
	 * 萃取url
	 * 
	 * @param req
	 * @return 参数名,参数值的map
	 */
	private Map<String, String> extractFR(HttpServletRequest req)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(), "UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++)
			{
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		}
		catch (UnsupportedEncodingException e)
		{}
		return Collections.EMPTY_MAP;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response){
		//'creditEntryController.spr?action=modify&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID
		String operate = request.getParameter("operate");
		String tradeType = request.getParameter("tradeType");
		String creditId = request.getParameter("creditId");
		String particularId = request.getParameter("particularId");
		String title="信用证开证特批审批";
		request.setAttribute("title", title);
		request.setAttribute("particularId",particularId);
		String parms = "operate,"+operate+";tradeType,"+tradeType+";creditId,"+creditId;
		request.setAttribute("iframeSrc", "creditEntryController.spr?action=modify&operate="+operate+"&tradeType="+tradeType+"&creditId="+creditId);
		//判断是否来自待办链接
		if(request.getParameter("fromParticular")==null){
			request.setAttribute("submitUrl", "particularWorkflowController.spr");
			request.setAttribute("parameters", "?action=firstSubmitParticularWorkflow&bizId="+creditId+"&parms="+parms+
					"&controller=creditEntryController&title="+title);
		}
		return new ModelAndView("sapss/workflow/particular/particularWorkflowPage");

	}
	
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	public SysWfUtilsService getSysWfUtilsService() {
		return sysWfUtilsService;
	}

	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	
	public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String creditId = request.getParameter("creditId");
		TCreditInfo info = creditEntryHibernateService.getTCreditInfo(creditId);
		 request.setAttribute("main", info);
		 List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(creditId);
		 request.setAttribute("taskHis", listDto);
		return new ModelAndView("sapss/credit/entryPrint");
	}
	
	public ModelAndView addReferenceView(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("tradeType", request.getParameter("tradeType"));
		return new ModelAndView("sapss/credit/referenceView");
	}
	
	public void addReferenceCredit(HttpServletRequest request,
			HttpServletResponse response, TContractGroup tContractGroup)
			throws IOException {
		String creditNo = request.getParameter("creditNo");
		
		TCreditInfo info = creditEntryHibernateService.getCreditInfo(creditNo);
		if(info==null) throw new BusinessException("请录入正确的信用证号！");

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		info.setCreditId(CodeGenerator.getUUID());

		// 部门编号
		info.setDeptId(userContext.getSysDept().getDeptid());
		// 是否有效
		info.setIsAvailable("1");
		// 创建时间
		info.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		info.setCreator(userContext.getSysUser().getUserId());
		// 状态
		info.setCreditState("1");
		info.setApplyTime("");
		info.setApprovedTime("");
		info.setAmount("0");
		info.setContractId("");
		info.setContractNo("");
		info.setSapOrderNo("");
		info.setCreditNo("");
		info.setRequest(userContext.getSysUser().getRealName());
		info.setTradeType(request.getParameter("tradeType"));
	
		creditEntryHibernateService.saveOrUpdateCreditInfo(info);
		
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("returnMessage", "创建成功!");
		jsonObject.put("creditId", info.getCreditId());
		jsonObject.put("contractType","2");	
		String jsontext = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response,PaymentImportsInfoQueryVO vo) throws Exception{
		String[] titles  = new String[]{"序号","合同号","立项号","立项名称","信用证号","开证行","开证日期","付款方式","受益人","币别","金额","有效期","装运期","状态"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> map = extractFR(request);

		String sql = creditEntryJdbcService.getQueryListSql(map);

		creditEntryJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("信用证开证表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
