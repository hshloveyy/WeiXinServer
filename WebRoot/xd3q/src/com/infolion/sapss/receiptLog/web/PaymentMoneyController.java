/*
 * @(#)ExportIncomeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.receiptLog.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.receiptLog.domain.TPaymentMoneyInfo;
import com.infolion.sapss.receiptLog.domain.TReceiptMoneyInfo;
import com.infolion.sapss.receiptLog.service.PaymentMoneyService;
import com.infolion.sapss.receiptLog.service.ReceiptMoneyService;

public class PaymentMoneyController extends BaseMultiActionController{
	@Autowired
	private PaymentMoneyService paymentMoneyService;
	public void setPaymentMoneyService(PaymentMoneyService paymentMoneyService) {
		this.paymentMoneyService = paymentMoneyService;
	}

	/**
	 * 系统菜单列表链接
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toManger(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receiptLog/paymentMoney/moneyManager");
	}
	/**
	 * 弹出收汇详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toInfo(HttpServletRequest request, HttpServletResponse response){
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/receiptLog/paymentMoney/moneyInfo");
	}
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String querySQL = this.paymentMoneyService.querySQL(request);
		String grid_column="info_id,contract_id,contract_no,pay_date,real_pay_date,pay_total,real_pay_total," +
				"pay_BALANCE,PURCHASE_CONTRACT_STAUS,CREATOR,CREATE_TIME";
		String grid_size=request.getParameter("limit");
		grid_size = grid_size==null?"10":grid_size;
		request.setAttribute("grid_sql", querySQL);
		request.setAttribute("grid_columns", grid_column);
		request.setAttribute("grid_size", grid_size);
		
		ExtComponentServlet servlet = new ExtComponentServlet();
		servlet.processGrid(request, response, true);
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void save(HttpServletRequest request, HttpServletResponse response,TPaymentMoneyInfo info) throws IOException{
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if(StringUtils.isEmpty(info.getInfoId())){
			info.setInfoId(CodeGenerator.getUUID());
		}
		info.setCreator(context.getSysUser().getRealName());
		info.setDeptId(context.getSysDept().getDeptid());
		info.setCreateTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");
		this.paymentMoneyService.saveOrUpdate(info);
		JSONObject js = new JSONObject();
		js.put("ok", "保存成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("infoId");
		this.paymentMoneyService.delete(id);
		JSONObject js = new JSONObject();
		js.put("ok", "删除成功");
		this.operateSuccessfullyWithString(response, js.toString());
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forModify(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("infoId");
		TPaymentMoneyInfo info =  this.paymentMoneyService.find(id);
		request.setAttribute("receiptMoney", info);
		return new ModelAndView("sapss/receiptLog/paymentMoney/moneyInfo");
	}
	
}
