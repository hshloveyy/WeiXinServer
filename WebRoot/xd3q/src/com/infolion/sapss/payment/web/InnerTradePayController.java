/*
 * @(#)InnerTradePayController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-19
 *  描　述：创建
 */

package com.infolion.sapss.payment.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.ExceptionPostHandle;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.payment.domain.TPaymentInnerInfo;
import com.infolion.sapss.payment.service.PaymentInnerInfoHibernateService;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;

/**
 * 控制器
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class InnerTradePayController extends BaseMultiActionController {
	// 服务类注入
	@Autowired
	private PaymentInnerInfoJDBCService paymentInnerInfoJDBCService;

	public PaymentInnerInfoJDBCService getPaymentInnerInfoJDBCService() {
		return paymentInnerInfoJDBCService;
	}

	public void setPaymentInnerInfoJDBCService(
			PaymentInnerInfoJDBCService paymentInnerInfoJDBCService) {
		this.paymentInnerInfoJDBCService = paymentInnerInfoJDBCService;
	}

	@Autowired
	private PaymentInnerInfoHibernateService paymentInnerInfoHibernateService;

	public PaymentInnerInfoHibernateService getPaymentInnerInfoHibernateService() {
		return paymentInnerInfoHibernateService;
	}

	public void setPaymentInnerInfoHibernateService(
			PaymentInnerInfoHibernateService paymentInnerInfoHibernateService) {
		this.paymentInnerInfoHibernateService = paymentInnerInfoHibernateService;
	}

    @Autowired
    private CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;
    public void setCheckCustomerSendCreditSerice(CheckCustomerSendCreditSerice checkCustomerSendCreditSerice)
    {
        this.checkCustomerSendCreditSerice = checkCustomerSendCreditSerice;
    }
	
	//private final Log log = LogFactory.getLog(InnerTradePayController.class);

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
		String payMethod = request.getParameter("payMethod") == null ? ""
				: request.getParameter("payMethod");
		request.setAttribute("payMethod", payMethod);
		String payType = request.getParameter("payType") == null ? "" : request
				.getParameter("payType");
		request.setAttribute("payType", payType);
		setUserInfo(request);
		this.inputControlSelect(request);
		return new ModelAndView("sapss/payment/innerTradePay/innerTradePayManage");
	}
	
	public ModelAndView show(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return defaultMethod(request,response);
	}
	/**
	 * 设置用户信息到request中
	 * @param request
	 */
	private void setUserInfo(HttpServletRequest request) {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("realName", userContext.getSysUser().getRealName());
		request.setAttribute("userName", userContext.getSysUser().getUserName());
		request.setAttribute("userId", userContext.getSysUser().getUserId());
		request.setAttribute("deptId", userContext.getSysDept().getDeptid());
		request.setAttribute("deptName", userContext.getSysDept().getDeptname());
	}

	/**
	 * 设置用户信息到 paymentInnerInfo
	 * @param paymentInnerInfo
	 */
	private void setUserInfo(TPaymentInnerInfo paymentInnerInfo) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		paymentInnerInfo.setCreatorName(userContext.getSysUser().getRealName());
		paymentInnerInfo.setCreator(userContext.getSysUser().getUserId());
		paymentInnerInfo.setDeptId(userContext.getSysDept().getDeptid());
		paymentInnerInfo.setDeptName(userContext.getSysDept().getDeptname());
	}

	/**
	 * 内贸货款操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView process(HttpServletRequest request,
			HttpServletResponse response, String operate)
			throws IllegalAccessException, InvocationTargetException {
		String paymentId = request.getParameter("paymentId");
		TPaymentInnerInfo info;
		if (paymentId != null && !"".equals(paymentId)) {
			info = this.paymentInnerInfoHibernateService
					.getPaymentInnerInfo(paymentId);
			request.setAttribute("paymentId", paymentId);
			request.setAttribute("purId", paymentInnerInfoJDBCService.queryParticularId(paymentId));
		} else {
			info = new TPaymentInnerInfo();
			setUserInfo(info);
		}
		setUserInfo(request);
		// 保存按钮是否 灰色
		boolean saveDisabled;
		if ("view".equals(operate))
			saveDisabled = true;
		else
			saveDisabled = false;
		// 提交按钮是否隐藏
		boolean submitHidden;
		if ("modify".equals(operate))
			submitHidden = false;
		else
			submitHidden = true;
		request.setAttribute("submitHidden", submitHidden);
		request.setAttribute("saveDisabled", saveDisabled);
		request.setAttribute("saveFunction", "save");
		request.setAttribute("closeHidden", false);
		// 内贸付款类型
		String payMethod = request.getParameter("payMethod");
		String payType = request.getParameter("payType");
		// 金额
		String payValue = "";
		if(info.getPayValue()!=null){
			DecimalFormat df = new DecimalFormat("0.00");
			payValue = df.format(info.getPayValue());
		}
		/*if(payMethod!=null&&!"".equals(payMethod))
			info.setPayMethod(payMethod);
		else*/
			request.setAttribute("payMethod", info.getPayMethod());
		
		if(payType!=null&&!"".equals(payType))
			info.setPayType(payType);
		else 
			request.setAttribute("payType", info.getPayType());
		request.setAttribute("main", info);
		request.setAttribute("payValue", payValue);
		String styleControl = "";
		String checkTag = "";
		this.inputControlByCreateBank(request, info, checkTag, styleControl);
		this.inputControl(request);
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		//判断是否需要关联立项号
		request.setAttribute("isRelateProject",WorkflowUtils.isRelatedProject());
		return new ModelAndView("sapss/payment/innerTradePay/innerTradePayCreate");
	}

	/**
	 * 新增内贸货款
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
		return this.process(request, response, "create");
	}

	/**
	 * 查看内贸货款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return this.process(request, response, "view");
	}
	
	/**
	 * 查看内贸货款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView viewRecord(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String paymentId = request.getParameter("paymentId");
		TPaymentInnerInfo paymentInnerInfo = paymentInnerInfoHibernateService.getPaymentInnerInfo(paymentId);
		request.setAttribute("payType", paymentInnerInfo.getPayType());
		request.setAttribute("payMethod", paymentInnerInfo.getPayMethod());
		return this.process(request, response, "view");
	}
	/**
	 * 查看内贸付款流程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView workflow(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		String paymentId = request.getParameter("paymentId");
		TPaymentInnerInfo info = this.paymentInnerInfoHibernateService
				.getPaymentInnerInfo(paymentId);
		inputControl(request,info);
		String payMethod = request.getParameter("payMethod");
		String payType = request.getParameter("payType");
		request.setAttribute("payMethod", payMethod);
		request.setAttribute("payType", payType);
		request.setAttribute("main", info);
		DecimalFormat dt = new DecimalFormat("0.000");
		String payValue = dt.format(info.getPayValue());
		request.setAttribute("payValue", payValue);
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String currentUser = userContext.getSysUser().getUserId();
		//如果当前用户是提交该审批申请的用户,调用创建修改用的页面并屏蔽相关按钮
		if(currentUser.equals(info.getCreator())){
			ModelAndView modelAndView = this.process(request, response, "create");
			request.removeAttribute("closeHidden");
			request.setAttribute("closeHidden", true);
			request.setAttribute("saveFunction", "examineModify");
			return modelAndView;
		}else{//如果不是则调用审批界面
			request.removeAttribute("saveDisabled");
			request.setAttribute("saveDisabled", true);
			request.setAttribute("isRelateProject",WorkflowUtils.isRelatedProject());
			return new ModelAndView("sapss/payment/innerTradePay/innerTradePayExamine");
		}
	}
	
	/**
	 * 对审批页面的输入框进行控制
	 * @param request
	 */
	private void inputControl(HttpServletRequest request,TPaymentInnerInfo info){
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		String styleControl = " style=\"color: #909090;\" readonly=\"readonly\"";
		String checkTag = "<font color=red>▲</font>";
		String isCreateBank = "";
		String isNotCreateBank = "";
		//如果是出纳，可以对付款银行和付款帐号等多种信息进行处理
		if(taskName.indexOf("出纳") < 0){			
			request.setAttribute("isNotTeller", styleControl);
			request.setAttribute("payRealTimeStatus", "true");
		}else{
			request.setAttribute("isTeller", checkTag);
			request.setAttribute("payRealTimeStatus", "false");
			if(info.getPayRealTime()==null||"".equals(info.getPayRealTime()))
				info.setPayRealTime(DateUtils
						.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		}
		//如果是"资金部经理审核（填写开证行，是否需要保证金）"
		//或银行承兑汇票的"资金部经理审核"节点则需要填写相关信息
		if("资金部经理审核（填写开证行，是否需要保证金）".equals(taskName)
				||"7".equals(info.getPayMethod())&&"资金部经理审核".equals(taskName)){
			isCreateBank = checkTag;
			request.setAttribute("maturityDateStatus", "true");
		}else{
			//在国内信用付款中只有资金部人员确认并填写到期日才需要填写到期日期
			if("1".equals(info.getPayMethod())&& taskName.indexOf("确认并填写到期日")!=-1){
				request.setAttribute("maturityDateStatus", "false");
				request.setAttribute("maturityDateCheck", checkTag);
			}//在其它付款方式中如果是资金部则需要填写到期日期
			else if(taskName.indexOf("资金部") < 0){
				request.setAttribute("maturityDateStatus", "true");
			}else{
				request.setAttribute("maturityDateStatus", "false");
				request.setAttribute("maturityDateCheck", checkTag);			
			}
			isNotCreateBank = styleControl;
		}
		this.inputControlByCreateBank(request, info, isCreateBank, isNotCreateBank);
	}
	
	/**
	 * 对填写开证行，是否需要保证金输入框进行控制
	 * @param request
	 * @param info
	 * @param isCreateBank
	 * @param isNotCreateBank
	 */
	private void inputControlByCreateBank(HttpServletRequest request,TPaymentInnerInfo info,String isCreateBank,String isNotCreateBank){
		StringBuffer sbCreateBank = new StringBuffer();
		String createBank = info.getCreateBank()==null?"":info.getCreateBank();
		String preSecurity = info.getPreSecurity()==null?"":info.getPreSecurity();
		DecimalFormat dt = new DecimalFormat("0.000");
		Double depositValue = info.getDeposit();
		String deposit = depositValue==null?"":dt.format(depositValue);
		String radioStatus = "onclick=\"setDeposit();\"";
		if(isNotCreateBank.indexOf("readonly")>=0){
			radioStatus = "onclick=\"setPreSecurity();\"";
		}
		if("1".equals(info.getPayType())&&
				("1".equals(info.getPayMethod())||"7".equals(info.getPayMethod()))){
			sbCreateBank.append("\t\t\t<tr>\r\n");
			if("1".equals(info.getPayMethod())){
				sbCreateBank.append("\t\t\t\t<td width=\"11%\" align=\"right\">"+isCreateBank+"开证行：</td>\r\n");
				sbCreateBank.append("\t\t\t\t<td width=\"22%\" align=\"left\">\r\n");
				sbCreateBank.append("\t\t\t\t\t<input name=\"createBank\" id=\"createBank\" type=\"text\" size=\"20\" tabindex=\"1\" value=\""
						+createBank+"\" "+isNotCreateBank+" maxlength=\"100\"/>\r\n\t\t\t\t</td>\r\n");
			}
			sbCreateBank.append("\t\t\t\t<td width=\"11%\" align=\"right\">"+isCreateBank+"是否需要保证金：</td>\r\n");
			sbCreateBank.append("\t\t\t\t<td width=\"22%\" align=\"left\">\r\n");
			sbCreateBank.append("\t\t\t\t\t否<input name=\"preSecurity\" id=\"preSecurity1\" type=\"radio\" value=\"0\" "+radioStatus+"/>\r\n");
			sbCreateBank.append("\t\t\t\t\t是<input name=\"preSecurity\" id=\"preSecurity0\" type=\"radio\" value=\"1\" "
					+radioStatus+"/>\r\n");
			if("1".equals(info.getPayMethod())){
				sbCreateBank.append("\t\t\t\t\t<font id=\"amount\">金额:</font><input name=\"deposit\" id=\"deposit\" type=\"text\" size=\"12\""
						+" value=\""+deposit+"\" " + isNotCreateBank + " maxlength=\"20\"/>\r\n");				
			}
			sbCreateBank.append("\t\t\t\t</td>\r\n");
			if("7".equals(info.getPayMethod())){
				sbCreateBank.append("\t\t\t\t\t<td width=\"11%\" align=\"right\"><font id=\"amount\">保证金金额：</font></td>\r\n");
				sbCreateBank.append("\t\t\t\t<td width=\"22%\" align=\"left\">\r\n");
				sbCreateBank.append("\t\t\t\t\t<input name=\"deposit\" id=\"deposit\" type=\"text\" size=\"20\""
						+" value=\""+deposit+"\" " + isNotCreateBank + " maxlength=\"20\"/>\r\n");
				sbCreateBank.append("\t\t\t\t</td>\r\n");
			}
			sbCreateBank.append("\t\t\t</tr>\r\n");
			request.setAttribute("isCreateBank", sbCreateBank.toString());
		}else{
			sbCreateBank.append("\t\t\t<input type=\"hidden\" name=\"createBank\" value=\""+createBank+"\">\r\n");
			sbCreateBank.append("\t\t\t<input type=\"hidden\" name=\"preSecurity\" value=\""+preSecurity+"\">\r\n");
			sbCreateBank.append("\t\t\t<input type=\"hidden\" name=\"deposit\" value=\""+deposit+"\">\r\n");
			request.setAttribute("isNotCreateBank", sbCreateBank.toString());
		}
	}
	
	private void inputControlSelect(HttpServletRequest request){
		String payType = request.getParameter("payType");
		String payMethod = request.getParameter("payMethod");
		StringBuffer sb = new StringBuffer();
		if("1".equals(payType)){
			if("1".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"国内信用证\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"1\">");
			}else if("7".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"银行/商业承兑汇票\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"7\">");
			}else{
				sb.append("<select name=\"payMethod\" id=\"payMethod\">\r\n");
				sb.append("<option value=\"2,3,4,5,6,9\">请选择</option>\r\n");		
				sb.append("<option value=2>现金</option>\r\n");				
				sb.append("<option value=3>背书</option>\r\n");				
				sb.append("<option value=4>转账</option>\r\n");				
				sb.append("<option value=5>电汇</option>\r\n");				
				sb.append("<option value=6>网银</option>\r\n");	
				sb.append("<option value=9>银行即期汇票</option>\r\n");	
				sb.append("</select>\r\n");
			}
		}else if("2".equals(payType)){
			 if("2".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"期货保证金\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"2\">");
			 }		
			 else {
					sb.append("<select name=\"payMethod\" id=\"payMethod\">\r\n");
					sb.append("<option value=\"3,4,5,6,1\">请选择</option>\r\n");
					sb.append("<option value=3>非货款/背书</option>\r\n");				
					sb.append("<option value=4>非货款/转账</option>\r\n");				
					sb.append("<option value=5>非货款/电汇</option>\r\n");				
					sb.append("<option value=6>非货款/网银</option>\r\n");	
					sb.append("<option value=7>非货款/现金</option>\r\n");	
					sb.append("<option value=1></option>\r\n");
					sb.append("</select>\r\n");
				}
		}if("3".equals(payType)){
			sb.append("<input type=\"text\" value=\"进口预付款/TT\" style=\"color: #909090;\" readonly=\"readonly\">");
			sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"8\">");
			
		}
		request.setAttribute("payMethodControl", sb.toString());
	}
	
	private void inputControl(HttpServletRequest request){
		String payType = request.getParameter("payType");
		String payMethod = request.getParameter("payMethod");
		if(payMethod==null) 
			payMethod=request.getAttribute("payMethod")==null?null:(String)request.getAttribute("payMethod");
		if(payType==null)
			payType=request.getAttribute("payType")==null?null:(String)request.getAttribute("payType");
		StringBuffer sb = new StringBuffer();
		if("1".equals(payType)){
			if("1".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"国内信用证\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"1\">");
			}else if("7".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"银行/商业承兑汇票\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"7\">");
			}else{
				sb.append("<select name=\"payMethod\" id=\"payMethod\">\r\n");
				sb.append("<option value=>请选择</option>\r\n");		
				sb.append("<option value=2>现金</option>\r\n");				
				sb.append("<option value=3>背书</option>\r\n");				
				sb.append("<option value=4>转账</option>\r\n");				
				sb.append("<option value=5>电汇</option>\r\n");				
				sb.append("<option value=6>网银</option>\r\n");	
				sb.append("<option value=9>银行即期汇票</option>\r\n");	
				sb.append("</select>\r\n");
			}
		}else if("2".equals(payType)){
			 if("2".equals(payMethod)){
				sb.append("<input type=\"text\" value=\"期货保证金\" style=\"color: #909090;\" readonly=\"readonly\">");
				sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"2\">");
			 }		
			 else {
					sb.append("<select name=\"payMethod\" id=\"payMethod\">\r\n");
					sb.append("<option value=>请选择</option>\r\n");
					sb.append("<option value=3>非货款/背书</option>\r\n");				
					sb.append("<option value=4>非货款/转账</option>\r\n");				
					sb.append("<option value=5>非货款/电汇</option>\r\n");				
					sb.append("<option value=6>非货款/网银</option>\r\n");	
					sb.append("<option value=7>非货款/现金</option>\r\n");	
					sb.append("<option value=1></option>\r\n");
					sb.append("</select>\r\n");
				}
		}if("3".equals(payType)){
			sb.append("<input type=\"text\" value=\"进口预付款/TT\" style=\"color: #909090;\" readonly=\"readonly\">");
			sb.append("<input type=\"hidden\" name=\"payMethod\" id=\"payMethod\" value=\"8\">");
			
		}
		request.setAttribute("payMethodControl", sb.toString());
	}
	
	/**
	 * 修改内贸货款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {
		return this.process(request, response, "modify");
	}

	/**
	 * 保存内贸货款信息
	 * 
	 * @param request
	 * @param response
	 * @param creditInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,TPaymentInnerInfo paymentInnerInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException {
		String contractPurchaseIds[] = request
				.getParameterValues("contractPurchaseId");
		checkContract(contractPurchaseIds,paymentInnerInfo);
		//String meg = paymentInnerInfoJDBCService.checkPayValue(contractPurchaseIds,paymentInnerInfo);
		//if(meg!=null&&meg.length()>4) throw new BusinessException(meg);
		String paymentId = request.getParameter("paymentId");
		JSONObject jo = new JSONObject();

        
        String strResult = this.checkCustomerSendCreditSerice.checkCredit("a", paymentInnerInfo.getProjectNo(),
                new Float(paymentInnerInfo.getExchangeRate() * paymentInnerInfo.getPayValue()));
        String returnMessage = "";
        // 确定有授信且额度不足
        if (strResult != null && !strResult.split("&")[0].equals("false") && strResult.split("&")[1].equals("false")) {
            jo.put("success", "false");
            returnMessage = "授信额度不足，无法提交！";
        } else {
            jo = paymentInnerInfoHibernateService.saveOrUpdatePaymentInnerInfo(
                    paymentId, contractPurchaseIds, paymentInnerInfo);
        }
        
			


        if (jo.getBoolean("success") == true)
            jo.put("returnMessage", "保存成功！");
        else if (returnMessage != "") {
            jo.put("returnMessage", returnMessage);
        } else {
            jo.put("returnMessage", "保存失败！");
        }
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	private void checkContract(String[] strArray,TPaymentInnerInfo info){
		if(strArray==null) return ;
		Double v = 0d;
		List<String> list = new ArrayList<String>();
		for(int i=0;i<strArray.length;i++){
			String[] str = strArray[i].split("/");
			String key = str[0];
			String value =str[1];
			if(list.contains(key)) throw new BusinessException("同一合同只能选择一次！");
			list.add(key);
			v+=Double.parseDouble(value);
		}
		if(Double.parseDouble(NumberUtils.round(v, 2))!=info.getPayValue().doubleValue())
			throw new BusinessException("合同金额累加不等于付款金额，请更改");
	}
	
	/**
	 * 内贸货款 删除动作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean isSuccess = false;
		// 删除动作实现
		String paymentId = (String) request.getParameter("paymentId");
		isSuccess = this.paymentInnerInfoHibernateService
				.deletePaymentInnerInfo(paymentId);

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		if (isSuccess) {
			ExceptionPostHandle.generateInfoMessage(response, "删除成功");
		} else {
			ExceptionPostHandle.generateInfoMessage(response, "记录不能删除");
		}
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map<String, String> parameter = this.paymentInnerInfoJDBCService
				.getParameterMap(request);
		String grid_columns = "PAYMENT_ID,DEPT_NAME,CREATOR_NAME,CREATOR,PAY_METHOD,APPLY_TIME,APPROVED_TIME,PAY_REAL_TIME,PAY_VALUE,REC_BANK,OPEN_ACCOUNT_BANK,OPEN_ACCOUNT_NO,EXAMINE_STATE,EXAMINE_STATE_D_EXAMINE_STATE,pay_method_view";
		String grid_size = "10";
		String grid_sql = this.paymentInnerInfoJDBCService.getQuerySql(parameter);
		paymentInnerInfoJDBCService.queryProcess(request, response, grid_sql,
				grid_columns, grid_size);
	}

	/**
	 * 查询内贸付款合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryInnerPayContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> parameter = this.paymentInnerInfoJDBCService
				.getParameterMap(request);
		// 贸易类型
		String grid_sql = paymentInnerInfoJDBCService.getInnerPayContractQuerySql(parameter);
		String grid_columns = "PROJECT_NO,PROJECT_NAME,CONTRACT_GROUP_NO,CONTRACT_GROUP_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,CONTRACT_NAME,SAP_ORDER_NO,APPROVED_TIME,TOTAL,CMD,PAPER_NO,CONTRACT_TYPE,CONTRACT_INFO,ORDER_STATE_D_ORDER_STATE";
		String grid_size = "15";
		paymentInnerInfoJDBCService.queryProcess(request, response, grid_sql,
				grid_columns, grid_size);
	}

	/**
	 * 查询与某一内贸付款相关的合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPaymentContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Map<String, String> parameter = this.paymentInnerInfoJDBCService
				.getParameterMap(request);
		// 贸易类型
		String grid_sql = paymentInnerInfoJDBCService.getPaymentContractQuerySql(parameter);
		String grid_columns = "CONTRACT_TYPE,CONTRACT_NAME,CONTRACT_PURCHASE_ID,CONTRACT_NO,PAY_VALUE,SAP_ORDER_NO,APPROVED_TIME,TOTAL,CMD,ORDER_STATE_D_ORDER_STATE,CONTRACT_INFO,PAPER_NO";
		String grid_size = "100";
		paymentInnerInfoJDBCService.queryProcess(request, response, grid_sql,
				grid_columns, grid_size);
	}

	/**
	 * 调用内贸合同显示界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ModelAndView selectInnerPayContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		request.setAttribute("projectId", request.getParameter("projectId"));
		request.setAttribute("projectNo", request.getParameter("projectNo"));
		String payType = request.getParameter("payType");
		request.setAttribute("payType", payType);
		if("2".equals(payType)){
			String radioTitle = "<td align=\"center\">合同类型</td>";
			String radio = "<td>付款合同<input type=\"radio\" name=\"contractType\" value=1 checked />&nbsp;&nbsp;"
				+"&nbsp;&nbsp;销售合同<input type=\"radio\" name=\"contractType\" value=2 /></td>";
			request.setAttribute("radio", radio);
			request.setAttribute("radioTitle", radioTitle);			
		}
		setUserInfo(request);
		return new ModelAndView("sapss/payment/innerTradePay/selectInnerContract");
	}

	/**
	 * 提交与保存内贸付款信息,用于第一次提交审批及用户提交信息被退回修改时
	 * @param request
	 * @param response
	 * @param paymentInnerInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitAndSave(HttpServletRequest request,
			HttpServletResponse response,TPaymentInnerInfo paymentInnerInfo) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		if (taskId == null || taskId.length() <= 0)
			taskId = "CREATE_NEW_TASK";
		String contractPurchaseIds[] = request
				.getParameterValues("contractPurchaseId");
		String paymentId = request.getParameter("paymentId");
		JSONObject jo = new JSONObject();
		
        String strResult = this.checkCustomerSendCreditSerice.checkCredit("a", paymentInnerInfo.getProjectNo(),
                new Float(paymentInnerInfo.getExchangeRate() * paymentInnerInfo.getPayValue()));
        // 确定有授信且额度不足
        String returnMessage = "";
        if (strResult != null && !strResult.split("&")[0].equals("false") && strResult.split("&")[1].equals("false")) {
            jo.put("success", "false");
            returnMessage = "授信额度不足，无法提交！";
        } else {
            jo = paymentInnerInfoHibernateService.saveOrUpdatePaymentInnerInfo(paymentId, taskId, contractPurchaseIds,
                    paymentInnerInfo);
        }

        if (jo.getBoolean("success") == true)
            jo.put("returnMessage", "保存成功！");
        else if (returnMessage != "") {
            jo.put("returnMessage", returnMessage);
        } else {
            jo.put("returnMessage", "保存失败！");
        }
        
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 在审批及提交的内贸付款信息
	 * @param request
	 * @param response
	 * @param paymentInnerInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public void submitPaymentInnerInfo(HttpServletRequest request,
			HttpServletResponse response, TPaymentInnerInfo paymentInnerInfo)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		//TaskInstanceContext taskInstanceContext = WorkflowService
		//		.getTaskInstanceContext(taskId);
		//String taskName = taskInstanceContext.getTaskName();
		//如果是内贸付款中出纳及资金部相关节点,需要保存被修改的内贸付款信息
		//if(taskName.indexOf("出纳") >= 0 || taskName.indexOf("资金部") >= 0){
		//	paymentInnerInfoHibernateService.updatePaymentInnerInfo(paymentInnerInfo);
		//}
		this.paymentInnerInfoHibernateService.submitPaymentInnerInfo(taskId,
				paymentInnerInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 设置内贸付款审批界面相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws Exception
	 */
	public ModelAndView paymentInnerInfoExamine(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String paymentId = request.getParameter("businessRecordId");
		TPaymentInnerInfo paymentInnerInfo = this.paymentInnerInfoHibernateService
				.getPaymentInnerInfo(paymentId);
		String iframeSrc = "";
		String taskId = request.getParameter("taskId");
		TaskInstanceContext taskInstanceContext = WorkflowService
				.getTaskInstanceContext(taskId);
		String taskName = taskInstanceContext.getTaskName();
		//设置审批者要查看的内贸付款信息对应的开启链接
		iframeSrc = "innerTradePayController.spr?action=workflow&paymentId="
				+ paymentId + "&payMethod="+paymentInnerInfo.getPayMethod()
				+ "&payType=" + paymentInnerInfo.getPayType() + "&taskId=" + taskId;

		request.setAttribute("businessRecordId", paymentId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("iframeSrc", iframeSrc);
		//设置提交时的动作
		request.setAttribute("submitUrl",
				"innerTradePayController.spr?action=submitPaymentInnerInfo");

		if(taskName.indexOf("出纳")>=0||taskName.indexOf("上海信达诺财务付款")>=0) request.setAttribute("isPrintView", "true");
		else request.setAttribute("isPrintView", "false");
		return new ModelAndView("sapss/payment/innerTradePay/commonProcessPage");
	}

	/**
	 * 取得内贸货款信息列表数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getInnerTradePayList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		query(request, response);
	}
	/**
	 * 综合查询入口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView compositeQuery(HttpServletRequest request,HttpServletResponse response){
		String payType = request.getParameter("payType");	
		request.setAttribute("payType", payType);
		return new ModelAndView("sapss/payment/innerTradePay/innerTradePayCompositeQuery");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response){
		String payType = request.getParameter("payType");
		String payMethod = request.getParameter("payMethod");
		String paymentId = request.getParameter("paymentId");
		String particularId = request.getParameter("particularId");
		TPaymentInnerInfo info = paymentInnerInfoHibernateService.getPaymentInnerInfo(paymentId);
		payMethod = info.getPayMethod();
		payType = info.getPayType();
		String title="内贸付款特批审批";
		request.setAttribute("title", title);
		request.setAttribute("particularId",particularId);
		String parms = "payType,"+payType+";payMethod,"+payMethod+";paymentId,"+paymentId;
		request.setAttribute("iframeSrc", "innerTradePayController.spr?action=view&payType="+payType+"&payMethod="+payMethod+"&paymentId="+paymentId);
		//判断是否来自待办链接
		if(request.getParameter("fromParticular")==null){
			request.setAttribute("submitUrl", "particularWorkflowController.spr");
			request.setAttribute("parameters", "?action=firstSubmitParticularWorkflow&bizId="+paymentId+"&parms="+parms+
					"&controller=innerTradePayController&title="+title);
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
	public ModelAndView dealPrint(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String paymentId = request.getParameter("paymentId");
		TPaymentInnerInfo paymentInnerInfo = this.paymentInnerInfoHibernateService
				.getPaymentInnerInfo(paymentId);
		String coantractNoStr = this.paymentInnerInfoJDBCService.queryContractNoByPayMentId(paymentId);
        List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(paymentId);
        if(coantractNoStr==null) coantractNoStr=paymentInnerInfo.getProjectNo();
        request.setAttribute("contractNoStr", coantractNoStr);
        request.setAttribute("taskHis", listDto);
		request.setAttribute("main", paymentInnerInfo);
		String payValue = new BigDecimal(paymentInnerInfo.getPayValue()).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		request.setAttribute("payValue", payValue);
		request.setAttribute("payValueBig", PaymentContants.changeToBig(Double.parseDouble(payValue)));
		return new ModelAndView("sapss/payment/innerTradePay/print");
	}
	
	public ModelAndView dealParticularPrint(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String particularId = request.getParameter("particularId");
		String paymentId = request.getParameter("paymentId");
		if(particularId==null||"".equals(particularId)){
			particularId = paymentInnerInfoJDBCService.queryParticularId(paymentId);
		}
		TPaymentInnerInfo paymentInnerInfo = this.paymentInnerInfoHibernateService
				.getPaymentInnerInfo(paymentId);
		String coantractNoStr = this.paymentInnerInfoJDBCService.queryContractNoByPayMentId(paymentId);
        List<TaskHisDto> listDto = sysWfUtilsService.queryTaskHisList(paymentId);
        List<TaskHisDto> listPur = sysWfUtilsService.queryTaskHisList(particularId);
        listDto.addAll(listPur);
        if(coantractNoStr==null) coantractNoStr=paymentInnerInfo.getProjectNo();
        request.setAttribute("contractNoStr", coantractNoStr);
        request.setAttribute("taskHis", listDto);
		request.setAttribute("main", paymentInnerInfo);
		String payValue = new BigDecimal(paymentInnerInfo.getPayValue()).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		request.setAttribute("payValue", payValue);
		request.setAttribute("payValueBig", PaymentContants.changeToBig(Double.parseDouble(payValue)));
		return new ModelAndView("sapss/payment/innerTradePay/print");
	}
	
	
	public void queryBankAndAccount(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {
		String recBank = request.getParameter("recBank");
		Map map = paymentInnerInfoJDBCService.queryBankAndAccount(recBank);
		JSONObject jo = new JSONObject();
		
		jo.put("openAccountBank", map.get("openAccountBank"));
		jo.put("openAccountNo", map.get("openAccountNo"));
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}
	
	public void dealOutToExcel(HttpServletRequest request,
			HttpServletResponse response,PaymentImportsInfoQueryVO vo) throws Exception{
		String[] titles  = new String[]{"序号","部门","申请人","付款形式","币别","金额","付款时间"};

		ExcelObject excel = new ExcelObject(titles);
		Map<String, String> parameter = this.paymentInnerInfoJDBCService
		.getParameterMap(request);
        String sql = this.paymentInnerInfoJDBCService.getQuerySql(parameter);

        paymentInnerInfoJDBCService.dealOutToExcel(sql ,excel);
		
		try{
			//response.reset();
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("application/x-download;charset=utf-8");
	        response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(URLEncoder.encode("非货款表.xls", "utf-8")).toString());
			excel.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
