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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
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
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.PropertiesUtil;
import com.infolion.sapss.Constants;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditEntryHibernateService;
import com.infolion.sapss.credit.service.CreditEntryHisHibernateService;
import com.infolion.sapss.credit.service.CreditEntryHisJdbcService;
import com.infolion.sapss.credit.service.CreditEntryJdbcService;

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
public class CreditEntryHisController extends BaseMultiActionController
{
	private final Log log = LogFactory.getLog(CreditEntryHisController.class);
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

	@Autowired
	private CreditEntryHisHibernateService creditEntryHisHibernateService;

	public void setCreditEntryHisHibernateService(CreditEntryHisHibernateService creditEntryHisHibernateService)
	{
		this.creditEntryHisHibernateService = creditEntryHisHibernateService;
	}

	@Autowired
	private CreditEntryHisJdbcService creditEntryHisJdbcService;

	public void setCreditEntryHisJdbcService(CreditEntryHisJdbcService creditEntryHisJdbcService)
	{
		this.creditEntryHisJdbcService = creditEntryHisJdbcService;
	}

	/**
	 * 信用证开证信息，修改历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView modifyCreditHisInfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
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

		TCreditHisInfo info = this.creditEntryHisHibernateService.getTCreditHisInfoBycreditId(creditId);
		if(info==null){
			info = new TCreditHisInfo();
			TCreditInfo creditInfo =creditEntryHibernateService.getTCreditInfo(creditId);
			try{
			PropertyUtils.copyProperties(info, creditInfo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("main", info);
		request.setAttribute("versionNoNew", info.getVersionNo());
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditEntryHisJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditEntryHisJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditEntryHisJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int deptIndex = userContext.getSysDept().getDeptShortCode().indexOf("MG");
        request.setAttribute("deptIndex", deptIndex);
        request.setAttribute("purTradeType", Constants.getPurTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditEntryHisInfo");
	}

	/**
	 * 新增一笔信用证改证数据(CreditEntryHisInfo)
	 * 
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws Exception
	 */
	public void saveCreditEntryHisInfo(HttpServletRequest request, HttpServletResponse response, TCreditHisInfo creditHisInfo) throws Exception
	{
		String creditId = request.getParameter("creditId");
		String versionNo = request.getParameter("versionNoOld");
		String creditHisId = request.getParameter("creditHisId");

		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");

		JSONObject jo = new JSONObject();
		boolean isPass = true;
		jo = this.creditEntryHisHibernateService.saveOrUpdateCreditHisInfo(creditHisInfo, versionNo, isPass);

		System.out.println("信用证修改历史版本号:" + versionNo + ",是否保存成功: " + isPass);
		System.out.println("信用证修改历史creditHisId:" + creditHisId);
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
		String creditHisId = request.getParameter("creditHisId");
		
		// 操作类型
		String operate = (String) request.getParameter("operate");
		request.setAttribute("operate", operate);

		TCreditHisInfo info = this.creditEntryHisHibernateService.getTCreditHisInfo(creditHisId);
		request.setAttribute("creditId", info.getCreditId());

		request.setAttribute("main", info);
		request.setAttribute("versionNoNew", info.getVersionNo());
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());
		// 工作流节点名称的标识码
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		
		String revisable = "false";
		//由此，控制那些节点可以上传附件。
		if("5".equals(taskType))
		{
			 revisable = "true";
		}
		request.setAttribute("revisable", revisable);// 提交审批不能上传附件
		request.setAttribute("purTradeType", Constants.getPurTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditEntryHisWorkFlow");
	}

	/**
	 * 信用证开证改证审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditEntryExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String creditHisId = request.getParameter("businessRecordId");
		//String creditId = "";
		//creditId = this.creditEntryHisJdbcService.getCreditId(creditHisId);

		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String taskType = this.creditEntryHisHibernateService.getTaskType(taskName,creditHisId);


		
		request.setAttribute("businessRecordId", creditHisId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("iframeSrc", "creditEntryHisController.spr?action=workflow&tradeType=" + "" + "&operate=iframe&creditHisId=" + creditHisId + "&taskType=" + taskType);
		request.setAttribute("submitUrl", "creditEntryHisController.spr?action=submitWorkflow");
		// request.setAttribute("action", "submitWorkflow");
		//request.setAttribute("revisable", revisable);// 提交审批不能上传附件

		//System.out.println("businessRecordId：" + creditHisId);

		return new ModelAndView("sapss/workflow/commonProcessPage");
	}

	/**
	 * 信用证开证改证提交流程审批
	 * 
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request, HttpServletResponse response, TCreditHisInfo creditHisInfo) throws IllegalAccessException, InvocationTargetException, IOException, Exception
	{
		String taskId = request.getParameter("workflowTaskId");
		String creditHisId = request.getParameter("creditHisId");

		String doWorkflow = request.getParameter("doWorkflow");
		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");

		creditHisInfo.setApprovedTime(DateUtils.getCurrTime(2));

		// 审批时候，初始化各参数，并根据汇率计算出金额,同时传流程参数，做金额判断。 同时保存修改。
		this.creditEntryHisHibernateService.initSubmitWorkflow(creditHisInfo, doWorkflow, workflowLeaveTransitionName, workflowExamine);
	
		//LJX 20090422 保存修改数据
		this.creditEntryHisHibernateService.saveModifyCreditHisInfo(creditHisInfo, taskId);

		//System.out.println("当前工作流节点名称:" + taskName);
		//System.out.println("提交审批信用证ID:" + creditHisInfo.getCreditId() + "改证历史ID:" + creditHisInfo.getCreditHisId() + " doWorkflow:" + doWorkflow);
		//System.out.println("WorkflowExamine:" + creditHisInfo.getWorkflowExamine());

		JSONObject jo = new JSONObject();
		jo = this.creditEntryHisJdbcService.submitWorkflow(creditHisInfo, taskId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 取得信用证修改历史记录表信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryCreditHisInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String creditId = request.getParameter("creditId");
		String grid_sql = this.creditEntryHisJdbcService.getqueryCreditHisInfoSql(creditId);

		// String grid_columns =
		// "CREDIT_HIS_ID,VERSION_NO,CHANGE_TIME,CHANGE_EXEC_TIME,IS_EXECUTED,CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";
		StringBuffer sb = new StringBuffer();
		sb.append("CREDIT_HIS_ID,CREDIT_ID,CHANGE_STATE,VERSION_NO,IS_EXECUTED,CHANGE_TIME,CHANGE_EXEC_TIME,TRADE_TYPE,CREDIT_STATE,");
		sb.append("CREDIT_NO,PROJECT_NO,PROJECT_NAME,SAP_ORDER_NO,CONTRACT_NO,CONTRACT_ID,CREATE_OR_REC,CUSTOM_CREATE_DATE,");
		sb.append("CREDIT_REC_DATE,CREATE_BANK,CREATE_DATE,VALID_DATE,COUNTRY,REQUEST,BENEFIT,BENEFIT_CERTIFICATION,");
		sb.append("PAYMENT_TYPE,AMOUNT,RATE,CURRENCY,GOODS,SPECIFICATION,MARK,INVOICE,BILL_OF_LADING,BILL_OF_INSURANCE,");
		sb.append("BILL_OF_QUALITY,CERTIFICATE_OF_ORIGIN,PACKING_SLIP,ELECTRIC_SHIP,DISPATCH_ELECTRIC,OTHER_DOCUMENTS,");
		sb.append("LOADING_PERIOD,PERIOD,PLACE,CAN_BATCHES,TRANS_SHIPMENT,PORT_OF_SHIPMENT,PORT_OF_DESTINATION,");
		sb.append("PAYMENT_DATE,PRE_SECURITY,WRITE_OFF_SINGLE_NO,SPECIAL_CONDITIONS,BILL_CONDITIONS,");
		sb.append("MATTERS_SHOULD_BE_AMENDED,CMD,DEPT_ID,APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR,DEPT_NAME,CREDIT_STATE_D_CREDIT_STATE,CREDIT_INFO,BAIL_DATE,AVAIL_DATE,MODIFY_INFO");
		String grid_columns = sb.toString();
		// CREDIT_HIS_ID,CREDIT_ID,VERSION_NO,IS_EXECUTED,CHANGE_TIME,CHANGE_EXEC_TIME,TRADE_TYPE,CREDIT_STATE,CREDIT_NO,PROJECT_NO,PROJECT_NAME,SAP_ORDER_NO,CONTRACT_NO,CREATE_OR_REC,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,CREATE_BANK,CREATE_DATE,VALID_DATE,COUNTRY,REQUEST,BENEFIT,BENEFIT_CERTIFICATION,PAYMENT_TYPE,AMOUNT,RATE,CURRENCY,GOODS,SPECIFICATION,MARK,INVOICE,BILL_OF_LADING,BILL_OF_INSURANCE,BILL_OF_QUALITY,CERTIFICATE_OF_ORIGIN,PACKING_SLIP,ELECTRIC_SHIP,DISPATCH_ELECTRIC,OTHER_DOCUMENTS,LOADING_PERIOD,PERIOD,PLACE,CAN_BATCHES,TRANS_SHIPMENT,PORT_OF_SHIPMENT,PORT_OF_DESTINATION,PAYMENT_DATE,PRE_SECURITY,WRITE_OFF_SINGLE_NO,SPECIAL_CONDITIONS,BILL_CONDITIONS,MATTERS_SHOULD_BE_AMENDED,CMD,DEPT_ID,APPLY_TIME,APPROVED_TIME,IS_AVAILABLE,CREATOR_TIME,CREATOR
		log.debug("取得信用证修改历史记录表信息SQL:" + grid_sql);

		String grid_size =request.getParameter("limit");
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 目前无使用。暂时保留垃圾。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public ModelAndView toCreditHisInfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, IOException
	{
		String creditId = request.getParameter("creditId");
		String creditHisId = request.getParameter("creditHisId");

		request.setAttribute("creditId", creditId);
		request.setAttribute("creditHisId", creditHisId);

		TCreditHisInfo info = this.creditEntryHisHibernateService.getTCreditHisInfo(creditHisId);
		request.setAttribute("main", info);
		request.setAttribute("purTradeType", Constants.getPurTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditEntryHisInfo");
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
	
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	public SysWfUtilsService getSysWfUtilsService() {
		return sysWfUtilsService;
	}

	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	
	public ModelAndView dealPrint(HttpServletRequest request,HttpServletResponse response){
		String creditHisId = request.getParameter("creditHisId");
		TCreditHisInfo info = creditEntryHisHibernateService.getTCreditHisInfo(creditHisId);
		if(info.getVersionNo()==0){
			creditHisId = info.getCreditId();
		}
		request.setAttribute("isHis", true);
		 request.setAttribute("main", info);
		 List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(creditHisId);
		 request.setAttribute("taskHis", listDto);
		return new ModelAndView("sapss/credit/entryPrint");
	}
}
