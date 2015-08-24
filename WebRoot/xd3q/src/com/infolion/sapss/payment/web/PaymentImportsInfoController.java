/*
 * @(#)SampleController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.sapss.payment.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.grid.OracleGridDataSource;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.payment.domain.TPickListInfo;
import com.infolion.sapss.payment.service.PaymentImportsInfoHibernateService;
import com.infolion.sapss.payment.service.PaymentImportsInfoJdbcService;
import com.infolion.sapss.payment.service.PickListInfoHibernateService;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

/**
 * 
 * <pre>
 * 控制器进口货款付款物料表
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
public class PaymentImportsInfoController extends BaseMultiActionController
{
	@Autowired
	private PaymentImportsInfoHibernateService paymentImportsInfoHibernateService;
	@Autowired
	private PaymentImportsInfoJdbcService paymentImportsInfoJdbcService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private ParticularWorkflowService particularWorkflowService;
	@Autowired
	private PickListInfoHibernateService pickListInfoHibernateService;
	
	
	public void setPickListInfoHibernateService(
			PickListInfoHibernateService pickListInfoHibernateService) {
		this.pickListInfoHibernateService = pickListInfoHibernateService;
	}

	public void setPaymentImportsInfoHibernateService(
			PaymentImportsInfoHibernateService paymentImportsInfoHibernateService)
	{
		this.paymentImportsInfoHibernateService = paymentImportsInfoHibernateService;
	}
	
	public void setParticularWorkflowService(
			ParticularWorkflowService particularWorkflowService) {
		this.particularWorkflowService = particularWorkflowService;
	}

	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}
	public void setPaymentImportsInfoJdbcService(
			PaymentImportsInfoJdbcService paymentImportsInfoJdbcService) {
		this.paymentImportsInfoJdbcService = paymentImportsInfoJdbcService;
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
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
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
	 *入口方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public ModelAndView entry(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException
	{
		String tradeType = request.getParameter("payment_type");
		request.setAttribute("businessType", tradeType);//用作标题显示判断
		String tag = paymentImportsInfoHibernateService.getPaymentImportsTag(tradeType);
		request.setAttribute("tradeType", tradeType);//TT/DPDA/LC
		this.getDictType(tradeType, request);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("loginer", userContext.getSysUser().getUserId());
		return new ModelAndView("sapss/payment/paymentImports/paymentImportsManager" + tag);
	}
	/**
	 * 增加列表的方法
	 * @param request
	 * @param response
	 * @return 界面
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException{
		String tradeType = request.getParameter("tradeType");
		String tag = paymentImportsInfoHibernateService.getPaymentImportsTag(tradeType);
		request.setAttribute("tradeType",tradeType);
		request.setAttribute("path", tag.toLowerCase());
		TPaymentImportsInfo paymentImportsInfo = new TPaymentImportsInfo();
		paymentImportsInfo.setPaymentId(CodeGenerator.getUUID());
		request.setAttribute("paymentImportsInfo", paymentImportsInfo);
		request.setAttribute("isNew", true);
		
		this.getDictType(tradeType, request);
		return new ModelAndView("sapss/payment/paymentImports/createPaymentImports");
	}
	/**
	 * 取得字典表付款类型
	 * @param tradeType
	 * @param request
	 */
	private String getDictType(String tradeType,HttpServletRequest request){
		String dictType="";
		if(StringUtils.isNotBlank(tradeType)){
			tradeType = tradeType.toLowerCase();
			if("tt".equals(tradeType) || "dp".equals(tradeType) || "sight_lc".equals(tradeType)){
				dictType="BM_PAY_TYPE_TT";
			}else {
				dictType="BM_PAY_TYPE_LC";
			}
			Map<String, DictionaryRow> map = SysCachePoolUtils.getDictTableGroup(dictType);
			Collection<DictionaryRow> payTypes = map.values();
			request.setAttribute("payTypes", payTypes);
			request.setAttribute("dictPayType", dictType);
			dictType = "PAY_TYPE_D_PAY_TYPE_DPDA";
			request.setAttribute("colPayType", dictType);
		}
		return dictType;
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param info
	 * @throws IOException 
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,TPaymentImportsInfo info) throws IOException{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if(info.getTradeType().equals("tt")){
			if("".equals(info.getPayAccount())||info.getPayAccount()==null){
				throw new BusinessException("收款帐号不能为空");
			}
			if("".equals(info.getPayBank())||info.getPayBank()==null){
				throw new BusinessException("收款银行不能为空");
			}
			String msg = paymentImportsInfoJdbcService.checkPayValue(info);
		}
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrDate(2));
		info.setDeptId(userContext.getSysDept().getDeptid());
		if(info.getPaymentId()==null || "".equals(info.getPaymentId()))
			info.setPaymentId(CodeGenerator.getUUID());
		info.setIsAvailable("1");
		info.setApplyTime(DateUtils.getCurrDate(2));
		info.setExamineState("1");//新增
		this.paymentImportsInfoHibernateService.savePaymentImportsInfo(info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功");
		jo.put("paymentId", info.getPaymentId());
		
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String paymentId = request.getParameter("paymentId");
		int result = this.paymentImportsInfoHibernateService.delete(paymentId);
		String msg = "";
		if(result>0){
			msg ="删除成功";
		}else
			msg ="删除失败";
		this.operateSuccessfullyWithString(response, msg);
	}
	
	/**
	 * 进入查询到单界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectPickListInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String tradeType = request.getParameter("tradeType");
		String examineState = request.getParameter("examineState");
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("examineState", examineState);
		return new ModelAndView("sapss/payment/paymentImports/selectPickListInfo");
	}
	
	/**
	 * 列表取值方法
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryImportInfo(HttpServletRequest request,HttpServletResponse response,PaymentImportsInfoQueryVO vo) throws IOException, ServletException
	{
		Map map = extractFR(request);
		vo.setDict_dept((String)map.get("dict_dept"));
		vo.setEkpoTxz01((String)map.get("ekpoTxz01"));
		vo.setIssuingBank((String)map.get("issuingBank"));
		vo.setGoodsName((String)map.get("googsName"));
		String payType=this.getDictType(vo.getTradeType(), request);
		String sql = this.paymentImportsInfoJdbcService.getPaymentIdList(vo,payType);
		payType ="".equals(payType)?"":payType+",";
		String grid_columns = "EXAMINE_STATE_D_EXAMINE_STATE,PAYMENT_ID,PICK_LIST_NO,PAY_VALUE," +
				"PAY_TYPE_D_PAY_TYPE_DPDA, PAY_TIME,PAY_DATE,PAY_REAL_VALUE,PAY_REAL_TIME,APPLY_TIME,APPROVED_TIME," +
				"DEPT_NAME,REAL_NAME,DEPT_NAME,CONTRACT_NO,WRITE_LIST_NO,PICK_LIST_REC_DATE,CREATOR,"+
				"PICK_LIST_TYPE_D_PICK_LTYPE,ISSUING_DATE,ISSUING_BANK,COLLECTION_BANK,CREDIT_NO";
		String size =request.getParameter("limit");
		size = size==null||"".equals(size)?"10":size;
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}		
	/**
	 * 查询到单号(发票号)
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPickListInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		Map map = extractFR(request);
		String sql = this.paymentImportsInfoJdbcService.getPickListSql(map);
		String grid_columns="WRITE_LIST_NO1,PICK_LIST_ID,PICK_LIST_NO,CONTRACT_PURCHASE_ID,PAY_VALUE," +
				"CONTRACT_NO,CONTRACT_NAME,ekko_waers,PAY_DATE,EKKO_LIFNR_NAME PROVIDER,TOTALVALUE,CURRENCY_ID,LC_NO";
		String size =request.getParameter("limit");
		size = size==null||"".equals(size)?"10":size;
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	
	/**
	 * 查找付款信息,并返回到修改页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView findPaymentImportInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String paymentId = request.getParameter("paymentId");
		TPaymentImportsInfo paymentImportsInfo =  this.paymentImportsInfoHibernateService.findPaymentImportInfo(paymentId);
		if (paymentId != null && !"".equals(paymentId)) {
			request.setAttribute("purId", paymentImportsInfoJdbcService.queryParticularId(paymentId));
		}
		request.setAttribute("paymentImportsInfo", paymentImportsInfo);
		String tag = paymentImportsInfoHibernateService.getPaymentImportsTag(paymentImportsInfo.getTradeType());
		request.setAttribute("path", tag.toLowerCase());//
		this.getDictType(paymentImportsInfo.getTradeType(), request);
		request.setAttribute("tradeType", paymentImportsInfo.getTradeType());
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String from = request.getParameter("from");
		request.setAttribute("from", from);
		String status = request.getParameter("status");
		if(status!=null && !"".equals(status) && !"新增".equals(status)){
			UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
			if(uc.getSysDept().getDeptname().indexOf("资金部")!=-1)
				request.setAttribute("modify_YH_rate", true);
		}else
			request.setAttribute("modify_YH_rate",false);
		if(status!=null && !"".equals(status) && !"新增".equals(status)){
			UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
			if(uc.getSysDept().getDeptname().indexOf("综合二部")!=-1)
				request.setAttribute("modify_writeNo", true);
		}else
			request.setAttribute("modify_writeNo",false);
		return new ModelAndView("sapss/payment/paymentImports/createPaymentImports");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void saveExchangeRate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String exchangeRate = request.getParameter("exchangeRate");
		String paymentId = request.getParameter("paymentId");
		String documentaryLimit = request.getParameter("documentaryLimit");
		String documentaryDate = request.getParameter("documentaryDate");
		String payDate = request.getParameter("payDate");
		String documentaryCurrency = request.getParameter("documentaryCurrency");
		Map<String,String> para = new HashMap<String,String>();
		para.put("EXCHANGE_RATE", exchangeRate);
		para.put("documentary_Limit", documentaryLimit);
		para.put("documentary_Date", documentaryDate);
		para.put("pay_Date", payDate);
		para.put("documentary_Currency", documentaryCurrency);
		
		int result = this.paymentImportsInfoJdbcService.batchUpdate(para, paymentId);
		JSONObject jo = new JSONObject();
		if(result>0)
			jo.put("returnMessage", "更新押汇信息成功");
		else
			jo.put("returnMessage", "更新押汇信息失败");
		this.operateSuccessfullyWithString(response, jo.toString());

	}
	
	public void saveWriteNo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String writeListNo = request.getParameter("writeListNo");
		String paymentId = request.getParameter("paymentId");

		Map<String,String> para = new HashMap<String,String>();
		para.put("write_List_No", writeListNo);
		int result = this.paymentImportsInfoJdbcService.batchUpdate(para, paymentId);
		JSONObject jo = new JSONObject();
		if(result>0)
			jo.put("returnMessage", "更新核销单号成功");
		else
			jo.put("returnMessage", "更新核销单号成功");
		this.operateSuccessfullyWithString(response, jo.toString());

	}
	/**
	 * 打开审批页面(待办链接)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView workflowSubmit(HttpServletRequest req,HttpServletResponse response){
		//从代办中取得业务记录ID
		String paymentId = req.getParameter("businessRecordId");
		TPaymentImportsInfo paymentImportsInfo =  this.paymentImportsInfoHibernateService.findPaymentImportInfo(paymentId);
		//显示业务类型
		req.setAttribute("tradeType", "进口付款");
		
		String taskId = req.getParameter("taskId");
		//历史跟踪
		req.setAttribute("businessRecordId", paymentId);
		//
		String nodeName = WorkflowService.getTaskInstanceContext(paymentId, taskId).getTaskName();
		req.setAttribute("taskId", taskId);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String type="view";//如果不是创建者,则为只读
		if(userContext.getSysUser().getUserId().equals(paymentImportsInfo.getCreator()) && nodeName!=null && nodeName.indexOf("修改")!=-1)
			type="modify";//修改页面信息
		if("填写押汇信息".equals(nodeName))
			type="writeRealInfo";
		else if("填写核销单".equals(nodeName))
			type="writeListNo";
		else if("承兑付款申请".equals(nodeName))
			type="writeYHInfo";//填写押汇信息
		else if("出纳付款".equals(nodeName))
			type="payMoney";//可以修改备注
		else if("业务修改".equals(nodeName) || "押汇不通过".equals(nodeName)){
			if("modify".equals(type) && paymentImportsInfo.getDocumentaryCurrency()!=null && !"".equals(paymentImportsInfo.getDocumentaryCurrency()) 
			   && paymentImportsInfo.getDocumentaryDate()!=null && !"".equals(paymentImportsInfo.getDocumentaryDate())
			   && paymentImportsInfo.getDocumentaryLimit()!=null && !"".equals(paymentImportsInfo.getDocumentaryLimit())){
				type="writeYHInfo";//修改押汇信息
			}
		}
		//业务信息
		req.setAttribute("iframeSrc",//<iframe src="${iframeSrc}" width=100% height=350 id="content" ></iframe>
				"paymentImportsInfoController.spr?action=findPaymentImportInfo&paymentId="+ paymentId+"&type="+type+"&from=workflow");
		//提交动作
		req.setAttribute("submitUrl", "paymentImportsInfoController.spr");
		req.setAttribute("action", "submitWorkflow");
		//动作参数
		req.setAttribute("iframeForms","Form.serialize(document.frames['content']['mainForm'])+'&'+Form.serialize(document.frames['content']['headForm'])");
		String taskName = WorkflowService.getTaskInstanceContext(taskId).getTaskName();
		if(taskName.indexOf("出纳")>=0) req.setAttribute("isPrintView", "true");
		else req.setAttribute("isPrintView", "false");
		return new ModelAndView("sapss/payment/paymentImports/paymentWorkflowManager");
	}
	
	/**
	 * 
	 * @param paymentImportsInfo
	 * @param request
	 * @return
	 */
	private TPaymentImportsInfo setTPaymentImportsInfo(TPaymentImportsInfo paymentImportsInfo,HttpServletRequest request){
		paymentImportsInfo.setPickListNo(request.getParameter("pickListNo"));
		paymentImportsInfo.setPickListId(request.getParameter("pickListId"));
		paymentImportsInfo.setTradeType(request.getParameter("tradeType"));
		paymentImportsInfo.setPayType(request.getParameter("payType"));
		paymentImportsInfo.setPayValue(request.getParameter("payValue"));
		paymentImportsInfo.setPayBank(request.getParameter("payBank"));
		paymentImportsInfo.setPayAccount(request.getParameter("payAccount"));
		paymentImportsInfo.setPayTime(request.getParameter("payTime"));
		paymentImportsInfo.setPayer(request.getParameter("payer"));
		paymentImportsInfo.setExchangeRate(request.getParameter("exchangeRate"));
		paymentImportsInfo.setCurrency(request.getParameter("currency"));
		paymentImportsInfo.setPayUse(request.getParameter("payUse"));
		paymentImportsInfo.setCmd(request.getParameter("cmd"));
		return paymentImportsInfo;
	}
	/**
	 * 提交审批动作
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void submitWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg="";
		try {
			String taskId = request.getParameter("workflowTaskId");
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			String paymentId = request.getParameter("paymentId");
			String type = request.getParameter("type");
			TPaymentImportsInfo paymentImportsInfo = new TPaymentImportsInfo();
			
	//		if(type==null || "view".equals(type)){
				paymentId = paymentId==null?request.getParameter("businessId"):paymentId;
				paymentImportsInfo =  this.paymentImportsInfoHibernateService.findPaymentImportInfo(paymentId);
	//		}else
			if("modify".equals(type)){
				paymentImportsInfo.setPaymentId(paymentId);
				this.setTPaymentImportsInfo(paymentImportsInfo, request);
				this.paymentImportsInfoHibernateService.savePaymentImportsInfo(paymentImportsInfo);
			}else if("writeRealInfo".equals(type)){
				if(request.getParameter("payRealTime")==null || "".equals(request.getParameter("payRealTime"))){
					throw new BusinessException("实际付款日期(押汇)必填");
				}
				if(request.getParameter("payRealValue")==null || "".equals(request.getParameter("payRealValue"))){
					throw new BusinessException("实际付款金额(押汇)必填");
				}
				if(request.getParameter("documentaryRealLimit")==null || "".equals(request.getParameter("documentaryRealLimit"))){
					throw new BusinessException("实际押汇期限");
				}
				/*if(request.getParameter("documentaryRealRate")==null || "".equals(request.getParameter("documentaryRealRate"))){
					throw new BusinessException("实际押汇汇率");
				}*/
				paymentImportsInfo.setPayRealTime(request.getParameter("payRealTime"));
				paymentImportsInfo.setPayRealValue(request.getParameter("payRealValue"));
				paymentImportsInfo.setDocumentaryRealLimit(request.getParameter("documentaryRealLimit"));
				paymentImportsInfo.setDocumentaryRealRate(request.getParameter("documentaryRealRate"));
				paymentImportsInfo.setDocumentaryInterest(request.getParameter("documentaryInterest"));
				paymentImportsInfo.setCmd(request.getParameter("cmd"));
				this.paymentImportsInfoHibernateService.savePaymentImportsInfo(paymentImportsInfo);
			}else if("writeListNo".equals(type)){
				if(request.getParameter("writeListNo")==null || "".equals(request.getParameter("writeListNo"))||"null".equals(request.getParameter("writeListNo")) ){
					throw new BusinessException("核销单号必填");
				}
				paymentImportsInfo.setWriteListNo(request.getParameter("writeListNo"));
				this.paymentImportsInfoHibernateService.savePaymentImportsInfo(paymentImportsInfo);
			}else if("writeYHInfo".equals(type) ){
				paymentImportsInfo.setPaymentId(paymentId);
				//
				paymentImportsInfo.setDocumentaryCurrency(request.getParameter("documentaryCurrency"));
				paymentImportsInfo.setDocumentaryDate(request.getParameter("documentaryDate"));
				paymentImportsInfo.setDocumentaryLimit(request.getParameter("documentaryLimit"));
				paymentImportsInfo.setPayDate(request.getParameter("payDate"));
				paymentImportsInfo.setPayAccount(request.getParameter("payAccount"));
				paymentImportsInfo.setPayBank(request.getParameter("payBank"));
				paymentImportsInfo.setCmd(request.getParameter("cmd"));
				this.paymentImportsInfoHibernateService.savePaymentImportsInfo(paymentImportsInfo);
			//}else if("payMoney".equals(type)){
			}else{
				paymentImportsInfo.setCmd(request.getParameter("cmd"));
				
				this.paymentImportsInfoHibernateService.savePaymentImportsInfo(paymentImportsInfo);
			}
			
			paymentImportsInfo.setWorkflowTaskId(taskId);
			paymentImportsInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			paymentImportsInfo.setWorkflowExamine(workflowExamine);
	
			String dict = this.getDictType(paymentImportsInfo.getTradeType(), request);
			dict="BM_"+dict.substring(dict.lastIndexOf("_D_")+3);
			dict = SysCachePoolUtils.getDictDataValue(dict, paymentImportsInfo.getPayType());
			String payString = "|付款日:"+paymentImportsInfo.getPayTime();
			if(dict.equals("押汇")){
			   payString = "|押汇到期付款日:"+paymentImportsInfo.getPayDate();
			}
			UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();		
			paymentImportsInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname()
					+ "|"
					+ userContext.getSysUser().getRealName()
					+ "|"
					+ paymentImportsInfo.getPickListNo()
					+ "|"
					+ dict+payString+"|金额:"+paymentImportsInfo.getPayValue());
			paymentImportsInfo.setApprovedTime(DateUtils.getCurrTime(2));
			this.paymentImportsInfoJdbcService.submintWorkflow(taskId,paymentImportsInfo);
		} catch (Exception e) {
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", true);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	/**
	 * 综合查询入口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView compositeQuery(HttpServletRequest request,HttpServletResponse response){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		Map<String, DictionaryRow> map = SysCachePoolUtils.getDictTableGroup("BM_PAY_TYPE_DPDA");
		Collection<DictionaryRow> payTypes = map.values();
		request.setAttribute("payTypes", payTypes);
		return new ModelAndView("sapss/payment/paymentImports/paymentCompositeQuery");
	}
	/**
	 * 打开提交特批页面
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String bizId = request.getParameter("bizId");
		String particularId = request.getParameter("particularId");
		String title = "进口货物付款业务信息";
		request.setAttribute("title", title);
		request.setAttribute("particularId", particularId);
		request.setAttribute("iframeSrc", "paymentImportsInfoController.spr?action=findPaymentImportInfo&type=view&paymentId="+bizId);
		if(request.getParameter("from")==null)
			request.setAttribute("submitUrl", "paymentImportsInfoController.spr?action=firstSubmitParticularWorkflow&bizId="+bizId+"&title="+title);
		return new ModelAndView("sapss/workflow/particular/particularWorkflowPage");
	}
	/**
	 * 首次提交流程审批
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void firstSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg="";
		try {
			String bizId = request.getParameter("bizId");
			TParticularWorkflow obj = new TParticularWorkflow();
			obj.setOriginalBizId(bizId);
			obj.setParticularId(CodeGenerator.getUUID());
			obj.setCreatedTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			UserContext ctext = UserContextHolder.getLocalUserContext().getUserContext();
			obj.setCreator(ctext.getSysUser().getUserName());
			obj.setModuleName("进口货物付款特批流程");
			obj.setLinkUrl("");
			obj.setWorkflowBusinessNote("进口货物付款特批流程");
			this.particularWorkflowService.save(obj);

			this.paymentImportsInfoJdbcService.firstSubmitParticularWorkflow(obj);
		} catch (Exception e) {
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", true);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
		
	}
	/**
	 * 待办链接
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView linkSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//从代办中取得业务记录ID
		String particularId = request.getParameter("businessRecordId");
		String taskId = request.getParameter("taskId");
		//历史跟踪
		request.setAttribute("businessRecordId", particularId);
		
		String originalBizId = this.particularWorkflowService.find(particularId);
		//
		request.setAttribute("taskId", taskId);
		request.setAttribute("iframeSrc",
					"paymentImportsInfoController.spr?action=openSubmitParticularWorkflow&bizId="+ originalBizId+"&particularId="+particularId+"&from=workflow");
		//提交动作
		request.setAttribute("submitUrl", "paymentImportsInfoController.spr");
		request.setAttribute("action", "submitParticularWorkflow&pariticularId="+ particularId);
		//动作参数
		request.setAttribute("revisable", "false");// 
		//iframe表单域
		request.setAttribute("iframeForms", "'p=p'");
		String taskName = WorkflowService.getTaskInstanceContext(taskId).getTaskName();
		if(taskName.indexOf("出纳")>=0){
			request.setAttribute("isPrintView", "true");
			request.setAttribute("controller", "paymentImportsInfoController");
			request.setAttribute("bizId", originalBizId);
		}
		return new ModelAndView("sapss/project/workflowManager");
	}
	/**
	 * 提交特批流程审批
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void submitParticularWorkflow(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg="";
		try{
			String taskId = request.getParameter("workflowTaskId");
			String workflowLeaveTransitionName = request.getParameter("workflowLeaveTransitionName");
			String workflowExamine = request.getParameter("workflowExamine");
			String pariticularId = request.getParameter("pariticularId");
			TParticularWorkflow obj = new TParticularWorkflow();
			obj.setParticularId(pariticularId);
			obj.setWorkflowExamine(workflowExamine);
			obj.setWorkflowTaskId(taskId);
			obj.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			obj.setWorkflowProcessUrl("paymentImportsInfoController.spr?action=linkSubmitParticularWorkflow");//设置待办链接的URL
			obj.setWorkflowBusinessNote("进口货物付款特批流程");
			Map variable = new HashMap();
			variable.put("PARTICULAR_ID",pariticularId);
			obj.setWorkflowUserDefineTaskVariable(variable);
			this.paymentImportsInfoJdbcService.submitParticularWorkflow(obj);

		} catch (Exception e) {
			msg="异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", true);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
	}
	
	@Autowired
	private SysUserService sysUserService;
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}

	
	public ModelAndView dealPrint(HttpServletRequest req,HttpServletResponse response){
		
		String paymentId = req.getParameter("paymentId");
		TPaymentImportsInfo info =  this.paymentImportsInfoHibernateService.findPaymentImportInfo(paymentId);
		TPickListInfo pickInfo = this.pickListInfoHibernateService.getPickListInfo(info.getPickListId());
		req.setAttribute("deptName", sysDeptService.queryDeptById(info.getDeptId()).getDeptname());
		req.setAttribute("creator", ((SysUser)sysUserService.queryUserByUserId(info.getCreator()).get(0)).getRealName());
		req.setAttribute("main", info);
		req.setAttribute("pick", pickInfo);
		String payValue = new BigDecimal(info.getPayValue()).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		req.setAttribute("payValue", payValue);
		req.setAttribute("payValueBig", PaymentContants.changeToBig(Double.parseDouble(payValue)));
		req.setAttribute("taskHis", sysWfUtilsService.queryTaskHisList(paymentId));
		return new ModelAndView("sapss/payment/paymentImports/print");
	}
	
    public ModelAndView dealParticularPrint(HttpServletRequest req,HttpServletResponse response){
    	String particularId = req.getParameter("particularId");
		String paymentId = req.getParameter("paymentId");
		TPaymentImportsInfo info =  this.paymentImportsInfoHibernateService.findPaymentImportInfo(paymentId);
		TPickListInfo pickInfo = this.pickListInfoHibernateService.getPickListInfo(info.getPickListId());
		req.setAttribute("deptName", sysDeptService.queryDeptById(info.getDeptId()).getDeptname());
		req.setAttribute("creator", ((SysUser)sysUserService.queryUserByUserId(info.getCreator()).get(0)).getRealName());
		req.setAttribute("main", info);
		req.setAttribute("pick", pickInfo);
		String payValue = new BigDecimal(info.getPayValue()).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		req.setAttribute("payValue", payValue);
		req.setAttribute("payValueBig", PaymentContants.changeToBig(Double.parseDouble(payValue)));
		List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(paymentId);
		List<TaskHisDto> listPur = sysWfUtilsService.queryTaskHisList(particularId);
        listDto.addAll(listPur);
		req.setAttribute("taskHis",listDto);
		return new ModelAndView("sapss/payment/paymentImports/print");
	}
    
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response,PaymentImportsInfoQueryVO vo) throws Exception{
		String[] titles  = new String[]{"序号","部门","申请人","币别","金额","合同号","信用证号","到单类型","开证日期","开证行","（即期/承兑到期）付款日","押汇到期付款日","审批通过日期"};

		ExcelObject excel = new ExcelObject(titles);
		Map map = extractFR(request);
		vo.setDict_dept((String)map.get("dict_dept"));
		vo.setEkpoTxz01((String)map.get("ekpoTxz01"));
		vo.setIssuingBank((String)map.get("issuingBank"));
		String payType=this.getDictType(vo.getTradeType(), request);
		String sql = this.paymentImportsInfoJdbcService.getPaymentIdList(vo,payType);

		paymentImportsInfoJdbcService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("进口货物付款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
