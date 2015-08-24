/*
 * @(#)CreditArriveController.java
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

import org.apache.commons.lang.StringUtils;
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
import com.infolion.sapss.Constants;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.service.CreditArriveHibernateService;
import com.infolion.sapss.credit.service.CreditArriveJdbcService;
import com.infolion.sapss.credit.service.CreditEntryHisHibernateService;

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
public class CreditArriveController extends BaseMultiActionController
{
	private final Log log = LogFactory.getLog(CreditArriveController.class);
	// 服务类注入
	@Autowired
	private CreditArriveHibernateService creditArriveHibernateService;

	public void setCreditArriveHibernateService(CreditArriveHibernateService creditArriveService)
	{
		this.creditArriveHibernateService = creditArriveService;
	}

	@Autowired
	private CreditArriveJdbcService creditArriveJdbcService;

	public void setCreditArriveJdbcService(CreditArriveJdbcService CreditArriveJdbcService)
	{
		this.creditArriveJdbcService = CreditArriveJdbcService;
	}

	@Autowired
	private CreditEntryHisHibernateService creditEntryHisHibernateService;

	public void setCreditEntryHisHibernateService(CreditEntryHisHibernateService creditEntryHisHibernateService)
	{
		this.creditEntryHisHibernateService = creditEntryHisHibernateService;
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
	 * 新增信用证到证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 保存按钮是否 灰色
		boolean saveDisabled = false;
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("saleTradeType", "2");//Constants.getSaleTradeType(tradeType)
		request.setAttribute("tradeType", tradeType);

		TCreditInfo info = this.creditArriveJdbcService.getInitEntity(tradeType);

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", "true");
		request.setAttribute("main", info);
		request.setAttribute("saleTradeType", Constants.getSaleTradeType(tradeType));
		return new ModelAndView("sapss/credit/creditArriveCreate");

	}

	/**
	 * 修改信用证到证信息
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

		TCreditInfo info = this.creditArriveHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);
		log.debug("部门ID:" + info.getDeptId());
	    log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate);
	    request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveCreate");
	}
	
	
	public ModelAndView copyCreate(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 保存按钮是否 灰色
		boolean saveDisabled = true;
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		boolean revisable = true;
		// 信用证ID
		String creditId = request.getParameter("creditId");
		
		// 操作类型
		String operate = "modify";
		request.setAttribute("operate", operate);

		TCreditInfo oinfo = this.creditArriveHibernateService.getTCreditInfo(creditId);
		
		TCreditInfo info = this.creditArriveHibernateService.copyCreate(oinfo);
		request.setAttribute("main", info);
		
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);
		log.debug("部门ID:" + info.getDeptId());
	    log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate);
	    request.setAttribute("creditId", info.getCreditId());
	    request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveCreate");
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
		this.creditArriveJdbcService.saveCreditStatus(creditId,status);
		jo.put("success",true);
		jo.put("creditid",creditId);
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	/**
	 * 修改信用证到证信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 保存按钮是否 灰色
		boolean saveDisabled = true;
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		boolean revisable = false;
		// 信用证ID
		String creditId = request.getParameter("creditId");
		request.setAttribute("creditId", creditId);
		// 操作类型
		String operate = (String) request.getParameter("operate");
		request.setAttribute("operate", operate);

		TCreditHisInfo info = this.creditEntryHisHibernateService.getTCreditHisInfoBycreditId(creditId);

		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());
		
		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		String deptName = context.getSysDept().getDeptname();
		String status = info.getCreditState();
		if("5".equals(status)||"7".equals(status)||"8".equals(status)||"15".equals(status)||"16".equals(status)){
			if(deptName!=null && deptName.indexOf("贸管")!=-1){
				request.setAttribute("CHANGE_CREDIT_STATUS", true);
			}else
				request.setAttribute("CHANGE_CREDIT_STATUS", false);
		}else
			request.setAttribute("CHANGE_CREDIT_STATUS", false);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		int deptIndex = userContext.getSysDept().getDeptShortCode().indexOf("MG");
        request.setAttribute("deptIndex", deptIndex);
        request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveHisInfo");
	}

	public ModelAndView change(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
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

		// TCreditInfo creditInfo =
		// this.creditArriveHibernateService.getTCreditInfo(creditId);
		TCreditHisInfo info = this.creditEntryHisHibernateService.getTCreditHisInfoBycreditId(creditId);

		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);

	    log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate);
	    request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveHisInfo");
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
		String contractSalesId = request.getParameter("contractSalesId");
		// String operate = request.getParameter("operate");
		String exceptionMsg = this.creditArriveJdbcService.checkFilds(creditInfo);
		if ("".equals(exceptionMsg) == false)
		{
			throw new BusinessException(exceptionMsg);
		}
		
		jo = this.creditArriveHibernateService.saveOrUpdateCreditInfo(creditInfo, creditId, contractSalesId);
		// log.debug("部门ID:" + creditInfo.getDeptId());

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 保存变更修改信息（无用）
	 * 20090429 信用证收证的变更 需求已经改变，现修改为 和信用证开证 改证一样
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void saveChange(HttpServletRequest request, HttpServletResponse response, TCreditHisInfo creditHisInfo) throws IllegalAccessException, InvocationTargetException, IOException
	{
		String creditId = request.getParameter("creditId");
		String versionNoOld = request.getParameter("versionNoOld");
		response.setCharacterEncoding("GBK");
		response.setContentType("text ml; charset=GBK");

		JSONObject jo = new JSONObject();

		jo = this.creditArriveJdbcService.saveChangeCreditInfo(creditHisInfo, versionNoOld);

		// jo =
		// this.creditArriveHibernateService.saveOrUpdateCreditInfo(creditInfo,creditHisInfo,
		// creditId, contractSalesId);
		// log.debug("取修改历史:" + creditHisInfo.getCreditId());

		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 信用证到证信息 删除动作
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

		isSuccess = this.creditArriveJdbcService.deletecreditInfo(creditId);

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
	 * 转到信用证到证管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditArriveManageView(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		// log.debug("贸易类型:" + tradeType);
		return new ModelAndView("sapss/credit/creditArriveManage");
	}

	/**
	 * 转到信用证到证综合查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditArriveManageQuery(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		int deptIndex = userContext.getSysDept().getDeptShortCode().indexOf("MG");
        request.setAttribute("deptIndex", deptIndex);
		return new ModelAndView("sapss/credit/creditArriveManageQuery");
	}

	/**
	 * 取得信用证到证信息列表数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getcreditArriveList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 贸易类型
		String tradeType = request.getParameter("tradeType");
		ExtComponentServlet servlet = new ExtComponentServlet();
		String grid_sql = this.creditArriveJdbcService.getCreditArriveListSql(tradeType);
		String grid_columns = "CREDIT_ID,CREDIT_NO,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";
		String grid_size = "10";

		// log.debug("信用证到证信息列表数据:" + grid_sql);

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
		// String deptid = request.getParameter("deptid");
		// log.debug("部门ID:" + deptid);
		String grid_sql = creditArriveJdbcService.getQuerySql(map);

		String grid_columns = "CREDIT_ID,CREDIT_NO,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";
		String grid_size = "10";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

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
		String grid_sql = creditArriveJdbcService.getQueryListSql(map);
		String grid_columns = "credit_rec_date,custom_create_date,CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,PROJECT_NAME,AMOUNT,REQUEST,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR";

		String grid_size = "10";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

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

		// log.debug("贸易类型:" + tradeType);
		return new ModelAndView("sapss/credit/selectSalesInfo");
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
		// log.debug("信用证ID:" + creditId);

		return new ModelAndView("sapss/credit/viewContractInfo");
	}

	/**
	 * 列出信用证相关联的销售合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void viewSalesListByCreditId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String creditId = request.getParameter("creditId");

		String sql = this.creditArriveJdbcService.getViewSalesListByCreditIdSql(creditId);
		String grid_columns = "CONTRACT_ID,CREDIT_ID,SAP_ORDER_NO,CONTRACT_NO,CONTRACT_NAME,YMAT_GROUP";
		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
	    log.debug("列出信用证相关联的销售合同信息SQL：" + sql);
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
	public void querySalesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String sql = this.creditArriveJdbcService.getQuerySalesSql(request);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_Sales_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,YMAT_GROUP";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		log.debug("选择销售合同SQL：" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 查看信用证相关联的合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void viewSalesInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String sql = this.creditArriveJdbcService.getQuerySalesSql(request);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRA" + "CT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_Sales_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,YMAT_GROUP";

		String grid_size = "20";
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);
		log.debug("查看采购合同SQL：" + sql);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 信用证到证审批界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView creditArriveExamine(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String creditId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		String iframeSrc = "";
		TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();

//		if ("信用证收证重新发起".equals(taskName))
//		{
//			iframeSrc = "creditArriveController.spr?action=change&tradeType=" + "" + "&operate=iframe&creditId=" + creditId;
//		}
//		else if("信用证收证修改".equals(taskName))
//		{
//		      boolean hasHisInfo = false;
//		      hasHisInfo =this.creditArriveJdbcService.getIsHasHisInfo(creditId);
//
//		      if(hasHisInfo)
//		      {
//					iframeSrc = "creditArriveController.spr?action=change&tradeType=" + "" + "&operate=iframe&creditId=" + creditId;
//		      }
//		      else
//		      {
//					iframeSrc = "creditArriveController.spr?action=modify&tradeType=" + "" + "&operate=modify&creditId=" + creditId;
//		      }
//		}
//		else
//		{
//			iframeSrc = "creditArriveController.spr?action=modify&tradeType=" + "" + "&operate=iframe&creditId=" + creditId;
//		}

		String taskType = this.creditArriveHibernateService.getTaskType(taskName);
		iframeSrc = "creditArriveController.spr?action=workflow&tradeType=" + "" + "&operate=iframe&creditId=" + creditId + "&taskType=" + taskType;
		
		request.setAttribute("businessRecordId", creditId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("iframeSrc", iframeSrc);
		request.setAttribute("submitUrl", "creditArriveController.spr?action=submitWorkflow");
		request.setAttribute("revisable", "false");// 提交审批不能上传附件
		log.debug("businessRecordId：" + creditId + "; iframeSrc:" + iframeSrc);

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
		String doWorkflow = request.getParameter("doWorkflow");

		String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
		String workflowExamine = request.getParameter("workflowExamine");
		String creditState = request.getParameter("CREDIT_STATE");
		String creditId = creditInfo.getCreditId();

		this.creditArriveJdbcService.initWorkflowInfo(creditInfo, doWorkflow, workflowLeaveTransitionName, workflowExamine, creditState);

		// 销售合同编码
		String contractSalesId = request.getParameter("contractSalesId");
		
		TCreditInfo info = this.creditArriveHibernateService.getTCreditInfo(creditId);
		
		String exceptionMsg = "";
		// 审批时候，到各节点判断是否有必输字段还未输入,如果有则抛出异常提示。如果有需要保存数据的则保存数据，
		exceptionMsg = this.creditArriveHibernateService.verifyFilds(creditInfo, info, taskId, taskName,contractSalesId);

		if ("".equals(exceptionMsg) == false)
		{
			throw new BusinessException(exceptionMsg);
		}
		
		boolean isPass = this.creditArriveJdbcService.getIsModifyCreditState(creditId, creditState, taskId, workflowLeaveTransitionName);

		if (isPass == false)
		{
			throw new BusinessException("请贸管人员更改信用证状态！");
		}

		 log.debug("信用证状态:" + creditState);
		 log.debug("提交审批信用证ID:" + creditInfo.getCreditId()+ " doWorkflow:" + doWorkflow);
		 log.debug("WorkflowExamine:" + creditInfo.getWorkflowExamine());
		 log.debug("WorkflowModelName:" + creditInfo.getWorkflowModelName());
		 log.debug("WorkflowProcessName:" +creditInfo.getWorkflowProcessName());

		JSONObject jo = new JSONObject();
		jo = this.creditArriveJdbcService.submitWorkflow(creditInfo, taskId);

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

		TCreditInfo info = this.creditArriveHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		// 工作流节点名称的标识码
		String taskType = request.getParameter("taskType");
		request.setAttribute("taskType", taskType);
		
		String revisable = "false";
		//由此，控制那些节点可以上传附件。
		if("edit".equals(taskType))
		{
			 revisable = "true";
		}
		request.setAttribute("revisable", revisable);// 提交审批不能上传附件
		request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveWorkFlow");
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

		TCreditInfo info = this.creditArriveHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);

		log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate + " 上传:" + revisable);
		request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveHisInfo");
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
		String grid_sql = this.creditArriveJdbcService.getqueryCreditHisInfoSql(creditId);

		String grid_columns = "CREDIT_HIS_ID,VERSION_NO,CHANGE_TIME,CHANGE_EXEC_TIME,IS_EXECUTED,CREDIT_ID,CREDIT_NO,CREATE_DATE,DEPT_NAME,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR,YMAT_GROUP";
		log.debug("取得信用证修改历史记录表信息SQL:" + grid_sql);

		String grid_size = "10";
		request.setAttribute("grid_sql", grid_sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	public ModelAndView reloadCreditHisInfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
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

		TCreditInfo info = this.creditArriveHibernateService.getTCreditInfo(creditId);
		request.setAttribute("main", info);
		// 贸易类型
		request.setAttribute("tradeType", info.getTradeType());

		submitHidden = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("submitHidden");
		saveDisabled = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("saveDisabled");
		revisable = this.creditArriveJdbcService.getControlMap(operate, info.getCreator()).get("revisable");

		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("revisable", revisable);

		log.debug("提交是否隐藏:" + submitHidden + " 操作:" + operate + " 上传:" + revisable);
		request.setAttribute("saleTradeType", Constants.getSaleTradeType(info.getTradeType()));
		return new ModelAndView("sapss/credit/creditArriveHisInfo");
	}

	/**
	 * 进入项目信息查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectProjrctInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strDeptId = request.getParameter("deptid");
		String projectState = request.getParameter("projectState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptid", strDeptId);
		request.setAttribute("projectState", projectState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/credit/selectProjectInfo");
	}

	/**
	 * 查找立项
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryProjectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String strProjectName = extractFR(request, "projectname"); // request.getParameter("projectname");
		String strProjectNo = request.getParameter("projectno");
		String deptId = request.getParameter("deptId");
		String tradeType = request.getParameter("tradeType");

		String strSql = this.creditArriveJdbcService.getQueryProjectInfoSQL(strProjectName, strProjectNo, deptId, tradeType);

		String grid_columns = "PROJECT_ID,PROJECT_NAME,PROJECT_NO,YMAT_GROUP";

		String grid_size = "20";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		log.debug("信用证收证立项选择窗体SQL语句:" + strSql);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 选择部门(受益人)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectDeptInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String strDeptId = request.getParameter("deptid");
		String projectState = request.getParameter("projectState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptid", strDeptId);
		request.setAttribute("projectState", projectState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/credit/selectDeptInfo");
	}

	/**
	 * 查找部门
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryDept(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String strDeptName = extractFR(request, "deptname"); // request.getParameter("projectname");
		String strDeptCode = request.getParameter("deptcode");

		String strSql = this.creditArriveJdbcService.getQueryDeptInfoSQL(strDeptName, strDeptCode);

		String grid_columns = "DEPT_ID,DEPT_CODE,DEPT_NAME";

		String grid_size =request.getParameter("limit");
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		log.debug("信用证收证立项选择窗体SQL语句:" + strSql);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 目前无用。（测试代码）
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getHasHis(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String creditId =request.getParameter("creditId"); 
		JSONObject jo = new JSONObject();
		String creditHisId = this.creditArriveJdbcService.getCreditHisId(creditId);
		
		if(StringUtils.isBlank(creditHisId))
		{
			jo.put("HASHIS", "0");
		}
		else
		{
			jo.put("HASHIS", "1");
		}
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
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
		String creditId = request.getParameter("creditId");
		TCreditInfo info = creditArriveHibernateService.getTCreditInfo(creditId);
		if(info.getSpecialConditions()!=null)
		    info.setSpecialConditions(info.getSpecialConditions().replaceAll("\n","<br>"));  
		if(info.getBillConditions()!=null)
		    info.setBillConditions(info.getBillConditions().replaceAll("\n","<br>"));
		request.setAttribute("main", info);
		 List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(creditId);
		 request.setAttribute("taskHis", listDto);
		return new ModelAndView("sapss/credit/arrivePrint");
	}

}
