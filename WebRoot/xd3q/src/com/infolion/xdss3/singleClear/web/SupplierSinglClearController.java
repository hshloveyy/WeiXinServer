/*
 * @(#)SupplierSinglClearController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月08日 14点42分18秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.grid.data.TableSql;
import com.infolion.platform.dpframework.uicomponent.queryCondition.QueryConditionService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.singleClearGen.web.SupplierSinglClearControllerGen;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 供应商单清帐(SupplierSinglClear)控制器类
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
public class SupplierSinglClearController extends SupplierSinglClearControllerGen
{

	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService)
	{
		this.voucherService = voucherService;
	}
	@Resource
	private SupplierClearAccountService supplierClearAccountService;
	
	
	/**
	 * @param supplierClearAccountService the supplierClearAccountService to set
	 */
	public void setSupplierClearAccountService(
			SupplierClearAccountService supplierClearAccountService) {
		this.supplierClearAccountService = supplierClearAccountService;
	}

	/**
	 * 作废
	 * 
	 * @param request
	 * @param response
	 */
	public void _blankOut(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);

		this.supplierSinglClearService._blankOut(supplierSinglClear);

		JSONObject jo = new JSONObject();
		jo.put("businessstate", BusinessState.BLANKOUT);
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		supplierSinglClear.setBusinessstate("0");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
		.getLocalUserContext().getUserContext();
		if (null != xdssUserContext)
			supplierSinglClear.setDeptid(xdssUserContext.getSysUser().getDeptId());

		request.setAttribute("supplierSinglClear", supplierSinglClear);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000325");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/singleClear/supplierSinglClearAdd");
	}

	/**
	 * 根据选择的未清供应商编号，查询出未清供应商下所有未清发票信息。
	 */
	@Override
	public void _queryUnClear(HttpServletRequest request, HttpServletResponse response)
	{
		String supplier = request.getParameter("supplier");
		String subject = request.getParameter("subject");
		String currencytext = request.getParameter("currencytext");
		String companyno = request.getParameter("companyno");
		String depid = request.getParameter("depid");
		String supplierclearid = request.getParameter("supplierclearid");
		if(!StringUtils.isNullBlank(supplierclearid)){
			SupplierSinglClear supplierSinglClear = this.supplierSinglClearService._get(supplierclearid);
			supplierSinglClear.setUnClearPayment(null);
			supplierSinglClear.setUnClearSupplieBill(null);
			this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());

		}
		JSONObject jo = this.supplierSinglClearService._queryUnClear(supplier, subject,currencytext,companyno,depid);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			log.debug("根据选择的未清供应商编号，查询出未清供应商下所有未清发票信息:" + jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.SupplierSinglClearControllerGen
	 * #_autoAssign(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _autoAssign(HttpServletRequest request, HttpServletResponse response)
	{
		String supplier = request.getParameter("supplier");
		String subject = request.getParameter("subject");
		String currencytext = request.getParameter("currencytext");
		String companyno = request.getParameter("companyno");
		String depid = request.getParameter("depid");
		JSONObject jo = this.supplierSinglClearService._autoAssign(supplier, subject,currencytext,companyno,depid);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			log.debug("根据选择的未清供应商编号，查询出未清供应商下所有未清发票信息:" + jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.SupplierSinglClearControllerGen
	 * #_clearAssign(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _clearAssign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 确认清帐
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitClear(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		// 是否需要生成清帐凭证。
		if (this.supplierSinglClearService.isHaveClearVoucher(supplierSinglClear))
		{
			String businessId = supplierSinglClear.getSuppliersclearid();
			if (!StringUtils.isNullBlank(businessId) && !this.supplierSinglClearService.checkVoucher(businessId))
				throw new BusinessException("请先进行凭证预览！");
		}		
		
		
		// 更新业务状态为清帐生效(审批通过)。
		supplierSinglClear.setBusinessstate(BusinessState.SUBMITPASS);

		this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());

		// 开始处理供应商未清抬头数据中借贷为S的数据，判断是否已结清。
		this.supplierSinglClearService.updateVendorTitleIsCleared(supplierSinglClear);

		jo.put("suppliersclearid", supplierSinglClear.getSuppliersclearid());
		jo.put("fundflowid", supplierSinglClear.getFundFlow() != null ? supplierSinglClear.getFundFlow().getFundflowid() : "");
		jo.put("settlesubjectid", supplierSinglClear.getSettleSubject() != null ? supplierSinglClear.getSettleSubject().getSettlesubjectid() : "");
		String creator = supplierSinglClear.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", supplierSinglClear.getCreatetime());
		String lastmodifyer = supplierSinglClear.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", supplierSinglClear.getLastmodifytime());
		jo.put("businessstate", supplierSinglClear.getBusinessstate());

		this.operateSuccessfullyWithString(response, jo.toString());

	}

	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void _voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		String deptid1_htext = request.getParameter("SettleSubjectPay.deptid1_htext");
		log.debug("deptid1_htext: " + deptid1_htext);
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		//先保存
		this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());
		String businessId = supplierSinglClear.getSuppliersclearid();
		// 先删除凭证信息。
//		this.voucherService._deleteVoucherByBusinessId(businessId);
		this.voucherService.deleteVoucherByBusinessid(businessId);
		
		List<String> voucherIds = this.supplierSinglClearService._saveVoucher(supplierSinglClear, unClearSupplieBillItems, fundFlow, settleSubject);
		for (int i = 0; i < voucherIds.size(); i++)
		{
			if("!null".equals(voucherIds.get(i))){
				throw new BusinessException("调整金额要等于借款金额减贷款金额！");
			}
		}
		String returnVoucherIds = "";
//		for (int i = 0; i < voucherIds.size(); i++)
//		{
//			returnVoucherIds += voucherIds.get(i);
//			if (i + 1 < voucherIds.size())
//			{
//				returnVoucherIds += "&";
//			}
//		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		
		if(null != voucherIds && voucherIds.size()!=0){
			returnVoucherIds = supplierSinglClear.getSuppliersclearid();
			jo.put("businessid", returnVoucherIds);
		}
		jo.put("suppliersclearid", businessId);
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}

	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	
	public void _voucherPreview2(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		String deptid1_htext = request.getParameter("SettleSubjectPay.deptid1_htext");
		log.debug("deptid1_htext: " + deptid1_htext);
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		//先保存
		this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());
		String businessId = supplierSinglClear.getSuppliersclearid();
		supplierSinglClear = this.supplierSinglClearService._get(businessId);
		
		// 先删除凭证信息。
		this.voucherService.deleteVoucherByBusinessid(businessId);
		ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(supplierSinglClear);
		BigDecimal marginAmount = new BigDecimal("0");
		BigDecimal marginAmount_bwb = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			marginAmount_bwb =supplierClearAccountService.getMarginByBwb(settleSubjectVoucher.getVoucherItem());
//			客户跟供应商借贷方相反
			marginAmount = new BigDecimal("0").subtract(marginAmount);
			marginAmount_bwb = new BigDecimal("0").subtract(marginAmount_bwb);
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		boolean isSave = false;
		if(!StringUtils.isNullBlank(businessId))isSave=true;
		IInfo info =supplierClearAccountService.checkClearData(supplierSinglClear, marginAmount,isSave);
//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = supplierClearAccountService.isClearAccount(supplierSinglClear, marginAmount_bwb);
			if(null != infoVoucher.getSubtractVlaue()){
				parameterVoucher.setSubtractVlaue(infoVoucher.getSubtractVlaue().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if(null != infoVoucher.getSumAdjustamount()){
				parameterVoucher.setSumAdjustamount(infoVoucher.getSumAdjustamount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			parameterVoucher.setTaxCode(infoVoucher.getTaxCode());
//			判断以前的业务数据是否正确
			if(infoVoucher.isRight()){
//				是否出汇损
				boolean isp = supplierClearAccountService.isProfitAndLoss(supplierSinglClear.getCurrencytext(), supplierSinglClear.getCompanyno());
//				可以全清出清账凭证
				if(infoVoucher.isClear()){
					Voucher clearVoucher =supplierClearAccountService.saveClearVoucherBySupplier(parameterVoucher,infoVoucher,businessId,ClearConstant.SINGLE_TYPE_L,isp);
//					数据有错误
					if(null == clearVoucher){
						jo.put("isRight", false);
						jo.put("info", info.CODE_9);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
				}else{
//				部分清（有外币出汇损）,并且差额不为0
					if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
						Voucher plVoucher = supplierClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
					}
					
				}
//				保存本次全清的数据，用来更新isclear状态
				request.getSession().setAttribute(businessId, infoVoucher);
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(businessId);
				
				jo.put("businessid", businessId);
				jo.put("suppliersclearid", businessId);
				jo.put("isRight", true);
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}else{
//				数据错误不能保存
				jo.put("suppliersclearid", businessId);
				jo.put("isRight", infoVoucher.isRight());
				jo.put("info", infoVoucher.getInfo());
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}
		}else{
//			数据错误不能保存
			jo.put("suppliersclearid", businessId);
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}
	}
	
	/**
	 * 确认清帐
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitClear2(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		
		
		// 更新业务状态为清帐生效(审批通过)。
		supplierSinglClear.setBusinessstate(BusinessState.SUBMITPASS);

		this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());

		// 供应商未清抬头数据中的数据，判断是否已结清
		String bussinessid = supplierSinglClear.getSuppliersclearid();
		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//		是否全清
		if(infoVoucher.isClear()){
			supplierClearAccountService.updateIsClear(bussinessid);
		}else{
			supplierClearAccountService.updateIsClear(infoVoucher);
		}		
		request.getSession().removeAttribute(bussinessid);

		jo.put("suppliersclearid", supplierSinglClear.getSuppliersclearid());
		jo.put("fundflowid", supplierSinglClear.getFundFlow() != null ? supplierSinglClear.getFundFlow().getFundflowid() : "");
		jo.put("settlesubjectid", supplierSinglClear.getSettleSubject() != null ? supplierSinglClear.getSettleSubject().getSettlesubjectid() : "");
		String creator = supplierSinglClear.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", supplierSinglClear.getCreatetime());
		String lastmodifyer = supplierSinglClear.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", supplierSinglClear.getLastmodifytime());
		jo.put("businessstate", supplierSinglClear.getBusinessstate());

		this.operateSuccessfullyWithString(response, jo.toString());

	}

	
	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
		{
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null)
			{
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(supplierSinglClear.getSuppliersclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("suppliersclearid", supplierSinglClear.getSuppliersclearid());
			jo.put("fundflowid", supplierSinglClear.getFundFlow() != null ? supplierSinglClear.getFundFlow().getFundflowid() : "");
			jo.put("settlesubjectid", supplierSinglClear.getSettleSubject() != null ? supplierSinglClear.getSettleSubject().getSettlesubjectid() : "");
			String creator = supplierSinglClear.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", supplierSinglClear.getCreatetime());
			String lastmodifyer = supplierSinglClear.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", supplierSinglClear.getLastmodifytime());
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.SupplierSinglClearControllerGen
	 * #_delete(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String suppliersclearid = request.getParameter("suppliersclearid");
		supplierSinglClearService._delete(suppliersclearid, getBusinessObject());
		this.operateSuccessfully(response);
	}
	
	
	@Autowired
	private GridService gridService;
	
	public void setGridService(GridService gridService) {
		this.gridService = gridService;
	}

	public void queryGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws SQLException
	{
		String defaultCondition = getDefaultCondition(request);
		String userDefineWhereSql = StringUtils.isNullBlank(gridQueryCondition.getWhereSql()) || "null".equals(gridQueryCondition.getWhereSql()) ? "" : gridQueryCondition.getWhereSql();
		String whereSql = QueryConditionService.getQueryExpression(request.getParameterMap());
		String tableName = gridQueryCondition.getTableName();
		String strTableSql = gridQueryCondition.getTableSql();
		String groupBySql = gridQueryCondition.getGroupBySql();
		String orderSql = gridQueryCondition.getOrderSql();
		try
		{
		if (!StringUtils.isNullBlank(defaultCondition))
		defaultCondition = URLDecoder.decode(defaultCondition, "UTF-8");
		if (!StringUtils.isNullBlank(userDefineWhereSql))
		whereSql = URLDecoder.decode(userDefineWhereSql, "UTF-8") + whereSql;
		if (!StringUtils.isNullBlank(tableName))
		tableName = URLDecoder.decode(tableName, "UTF-8");
		if (!StringUtils.isNullBlank(groupBySql))
		groupBySql = URLDecoder.decode(groupBySql, "UTF-8");
		if (!StringUtils.isNullBlank(orderSql))
		orderSql = URLDecoder.decode(orderSql, "UTF-8");
		if (!StringUtils.isNullBlank(strTableSql))
		strTableSql = URLDecoder.decode(strTableSql, "UTF-8");
		}
		catch (UnsupportedEncodingException e1)
		{
		e1.printStackTrace();
		}
		// 如果指定了table sql实现类
		if (!StringUtils.isNullBlank(gridQueryCondition.getTableSqlClass()))
		{
		TableSql tableSql = (TableSql) EasyApplicationContextUtils.getBeanByName(gridQueryCondition.getTableSqlClass());
		strTableSql = tableSql.getTableSql();
		}
		gridQueryCondition.setTableSql(strTableSql);
		gridQueryCondition.setTableName(tableName);
		String businessstate = request.getParameter("businessstate");
		if(StringUtils.isNullBlank(businessstate)){
			gridQueryCondition.setDefaultCondition(defaultCondition);
		}else{
			gridQueryCondition.setDefaultCondition("");
			try
			{
				if("0".equals(businessstate)){
					whereSql = URLDecoder.decode(" and  businessstate  not in ('4','-1')", "UTF-8") + whereSql;
				}
				if("1".equals(businessstate)){
					whereSql = URLDecoder.decode(" and businessstate  in ('4','-1')", "UTF-8") + whereSql;
				}
			}catch (UnsupportedEncodingException e1)
			{
			e1.printStackTrace();
			}
		}
		gridQueryCondition.setWhereSql(whereSql);
		gridQueryCondition.setOrderSql(orderSql);
		gridQueryCondition.setGroupBySql(groupBySql);
		String editable = request.getParameter("editable");
		String needAuthentication = request.getParameter("needAuthentication");
		GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);
		try
		{
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonList);
		}
		catch (IOException e)
		{
		logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 取得默认查询条件，如果有多个值传入，以最后的一个查询条件为准
	 * 
	 * @param request
	 * @return
	 */
	private String getDefaultCondition(HttpServletRequest request)
	{
	String[] defaultConditions = request.getParameterValues("defaultCondition");
	if (null == defaultConditions || defaultConditions.length < 1)
	return "";
	return defaultConditions[defaultConditions.length - 1];
	}
	
	public void getUnSingleClearData(HttpServletRequest request, HttpServletResponse response){
		String vendortitleids = request.getParameter("vendortitleids");
		String suppliersclearid = request.getParameter("suppliersclearid");
		//先删除
		if(!StringUtils.isNullBlank(suppliersclearid)){
			String [] ids=vendortitleids.split(",");
			SupplierSinglClear supplierSinglClear =this.supplierSinglClearService._get(suppliersclearid);
			for(UnClearPayment unClearPayment :supplierSinglClear.getUnClearPayment()){
				for(String id :ids){
					if(unClearPayment.getVendortitleid().equals(id)){
						supplierSinglClear.getUnClearPayment().remove(unClearPayment);
						break;
					}
				}
			}
			for(UnClearSupplieBill unClearSupplieBill :supplierSinglClear.getUnClearSupplieBill()){
				for(String id :ids){
					if(unClearSupplieBill.getVendortitleid().equals(id)){
						supplierSinglClear.getUnClearSupplieBill().remove(unClearSupplieBill);
						break;
					}
				}
			}
			this.supplierSinglClearService._saveOrUpdate(supplierSinglClear, getBusinessObject());
		}
		JSONObject jsonList = this.supplierSinglClearService._queryUnClear(vendortitleids);
		
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonList);
			System.out.println(jsonList.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	
	
	
	/**
	 * 取得已生成凭证的ID
	 * @param businessId
	 * @return
	 */
	public void getVoucherId(HttpServletRequest request, HttpServletResponse response){
		String businessId = request.getParameter("supplierclearid");
		try
		{
			String voucherid = this.supplierSinglClearService.getVoucherId(businessId);
			if(null == voucherid)voucherid="0";
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(voucherid);
			System.out.println(voucherid);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
		
	}
	
	/**
	 * 检查数据的正确性
	 * @param request
	 * @param response
	 */
	public void checkClearData(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		String deptid1_htext = request.getParameter("SettleSubjectPay.deptid1_htext");
		log.debug("deptid1_htext: " + deptid1_htext);
		// 绑定主对象值
		SupplierSinglClear supplierSinglClear = (SupplierSinglClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SupplierSinglClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearSupplieBill> unClearSupplieBillItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearSupplieBill.class, null);
		supplierSinglClear.setUnClearSupplieBill(unClearSupplieBillItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearPayment> unClearPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { supplierSinglClear }, UnClearPayment.class, null);
		supplierSinglClear.setUnClearPayment(unClearPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			supplierSinglClear.setFundFlow(fundFlow);
			fundFlow.setSupplierSinglClear(supplierSinglClear);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			supplierSinglClear.setSettleSubject(settleSubject);
			settleSubject.setSupplierSinglClear(supplierSinglClear);
		}
		
		ParameterVoucherObject parameterVoucher =supplierClearAccountService.setParameter(supplierSinglClear);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = supplierClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = supplierClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
//			客户跟供应商借贷方相反
			marginAmount = new BigDecimal("0").subtract(marginAmount);
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}	
		boolean isSave = false;
		if(!StringUtils.isNullBlank(supplierSinglClear.getSuppliersclearid()))isSave=true;
		IInfo info =supplierClearAccountService.checkClearData(supplierSinglClear, marginAmount,isSave);
		jo.put("isRight", info.isRight());
		jo.put("info", info.getInfo());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		
	}
	
}