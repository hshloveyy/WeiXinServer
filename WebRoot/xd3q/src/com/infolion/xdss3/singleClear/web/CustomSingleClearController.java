/*
 * @(#)CustomSingleClearController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月16日 11点02分05秒
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.uicomponent.grid.data.TableSql;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.queryCondition.QueryConditionService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;
import com.infolion.xdss3.singleClearGen.web.CustomSingleClearControllerGen;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 客户单清帐(CustomSingleClear)控制器类
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
public class CustomSingleClearController extends CustomSingleClearControllerGen
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
	
	@Autowired
	private CustomerClearAccountService customerClearAccountService;
	

	/**
	 * @param customerClearAccountService the customerClearAccountService to set
	 */
	public void setCustomerClearAccountService(
			CustomerClearAccountService customerClearAccountService) {
		this.customerClearAccountService = customerClearAccountService;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.CustomSingleClearControllerGen#
	 * _autoAssian(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _autoAssign(HttpServletRequest request, HttpServletResponse response)
	{
		String custom = request.getParameter("custom");
		String subject = request.getParameter("subject");
		String currencytext = request.getParameter("currencytext");
		String companyno = request.getParameter("companyno");
		String depid = request.getParameter("depid");
		JSONObject jo = this.customSingleClearService._autoAssign(custom, subject,currencytext,companyno,depid);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			log.debug("根据选择的未清客户编号，查询出未清客户下所有未清发票信息:" + jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}

	/**
	 * 作废
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void _blankOut(HttpServletRequest request, HttpServletResponse response)
	{

		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		this.customSingleClearService._blankOut(customSingleClear);
		JSONObject jo = new JSONObject();
		jo.put("businessstate", BusinessState.BLANKOUT);
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.CustomSingleClearControllerGen#
	 * _clearAssian(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _clearAssign(HttpServletRequest request, HttpServletResponse response)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.CustomSingleClearControllerGen#
	 * _delete(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String customsclearid = request.getParameter("customsclearid");
		customSingleClearService._delete(customsclearid, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.infolion.xdss3.singleClearGen.web.CustomSingleClearControllerGen#
	 * _queryUnClear(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void _queryUnClear(HttpServletRequest request, HttpServletResponse response)
	{
		//先保存数据再查询
		// 绑定主对象值
//		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
//		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
//		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
//		customSingleClear.setUnClearCustomBill(unClearCustomBills);
//		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
//		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
//		 Object[] { customSingleClear }, UnClearCustomBill.class, null);
//		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
//		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
//		customSingleClear.setUnClearCollect(unClearCollects);
//		 Set<UnClearCollect> unClearCollectdeleteItems =
//		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
//		 Object[] { customSingleClear }, UnClearCollect.class, null);
//		// 绑定子对象(一对一关系)
//		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
//		if (settleSubject != null)
//		{
//			customSingleClear.setSettleSubject(settleSubject);
//			settleSubject.setCustomSingleClear(customSingleClear);
//		}
//		// 绑定子对象(一对一关系)
//		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
//		if (fundFlow != null)
//		{
//			customSingleClear.setFundFlow(fundFlow);
//			fundFlow.setCustomSingleClear(customSingleClear);
//		}
//		
//		if(!StringUtils.isNullBlank(customSingleClear.getCustomsclearid()))
//		this.customSingleClearService._saveOrUpdate(customSingleClear, unClearCustomBilldeleteItems, unClearCollectdeleteItems, getBusinessObject());
		
		
		String customsclearid = request.getParameter("customsclearid");
		if(!StringUtils.isNullBlank(customsclearid)){
			CustomSingleClear customSingleClear = customSingleClearService._getDetached(customsclearid);
			customSingleClear.setUnClearCollect(null);
			customSingleClear.setUnClearCustomBill(null);
			customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
			
		}
		String custom = request.getParameter("custom");
		String subject = request.getParameter("subject");
		String currencytext = request.getParameter("currencytext");
		String companyno = request.getParameter("companyno");
		String depid = request.getParameter("depid");
		
		JSONObject jo = this.customSingleClearService._queryUnClear(custom, subject,currencytext,companyno,depid);
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jo);
			log.debug("根据选择的未清客户编号，查询出未清客户下所有未清发票信息:" + jo.toString());
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}

	}

	/**
	 * 保存
	 */
	@Override
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		 Set<UnClearCollect> unClearCollectdeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		
		this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
//		this.customSingleClearService._saveOrUpdate(customSingleClear, unClearCustomBilldeleteItems, unClearCollectdeleteItems, getBusinessObject());
		jo.put("customsclearid", customSingleClear.getCustomsclearid());
		jo.put("settlesubjectid", customSingleClear.getSettleSubject() != null ? customSingleClear.getSettleSubject().getSettlesubjectid() : "");
		jo.put("fundflowid", customSingleClear.getFundFlow() != null ? customSingleClear.getFundFlow().getFundflowid() : "");
		String creator = customSingleClear.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", customSingleClear.getCreatetime());
		String lastmodifyer = customSingleClear.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", customSingleClear.getLastmodifytime());
		jo.put("businessstate", customSingleClear.getBusinessstate());

		this.operateSuccessfullyWithString(response, jo.toString());

	}

	/**
	 * 确认清帐
	 */
	@Override
	public void _submitClear(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		 Set<UnClearCollect> unClearCollectdeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}

		// 是否需要生成清帐凭证。
		if (this.customSingleClearService.isHaveClearVoucher(customSingleClear))
		{
			String businessId = customSingleClear.getCustomsclearid();
			if (!StringUtils.isNullBlank(businessId) && !this.customSingleClearService.checkVoucher(businessId))
				throw new BusinessException("请先进行凭证预览！");
		}
		
		// 更新业务状态为清帐生效(审批通过)。
		customSingleClear.setBusinessstate(BusinessState.COLLECTSUBMITPASS);
		this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
//		this.customSingleClearService._saveOrUpdate(customSingleClear, unClearCustomBilldeleteItems, unClearCollectdeleteItems, getBusinessObject());
		// 客户未清抬头数据中的数据，判断是否已结清
		this.customSingleClearService.updateCustomerTitleIsCleared(customSingleClear);

		jo.put("customsclearid", customSingleClear.getCustomsclearid());
		jo.put("settlesubjectid", customSingleClear.getSettleSubject() != null ? customSingleClear.getSettleSubject().getSettlesubjectid() : "");
		jo.put("fundflowid", customSingleClear.getFundFlow() != null ? customSingleClear.getFundFlow().getFundflowid() : "");
		String creator = customSingleClear.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", customSingleClear.getCreatetime());
		String lastmodifyer = customSingleClear.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", customSingleClear.getLastmodifytime());
		jo.put("businessstate", customSingleClear.getBusinessstate());

		this.operateSuccessfullyWithString(response, jo.toString());

	}

	/**
	 * 模拟凭证
	 */
	@Override
	public void _voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		Set<UnClearCollect> unClearCollectdeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		//先保存
		this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
		
		String businessId = customSingleClear.getCustomsclearid();
		// 先删除凭证信息。
//		this.voucherService._deleteVoucherByBusinessId(businessId);
		this.voucherService.deleteVoucherByBusinessid(businessId);
		
		List<String> voucherIds = this.customSingleClearService._saveVoucher(customSingleClear, unClearCustomBills, fundFlow, settleSubject);
		for (int i = 0; i < voucherIds.size(); i++)
		{
			if("!null".equals(voucherIds.get(i))){
				throw new BusinessException("调整金额要等于借款金额减贷款金额！");
//				jo.put("exception", "exception");
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
			returnVoucherIds = customSingleClear.getCustomsclearid();
			jo.put("businessid", returnVoucherIds);
		}
		jo.put("customsclearid", businessId);
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());

	}
	
	/**
	 * 模拟凭证
	 */	
	public void _voucherPreview2(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		Set<UnClearCollect> unClearCollectdeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		//先保存
		this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
		
		String businessId = customSingleClear.getCustomsclearid();
		customSingleClear =  customSingleClearService._get(businessId);
		// 先删除凭证信息。
		this.voucherService.deleteVoucherByBusinessid(businessId);
		ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(customSingleClear);
		BigDecimal marginAmount = new BigDecimal("0");
		BigDecimal marginAmount_bwb = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			marginAmount_bwb =customerClearAccountService.getMarginByBwb(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		boolean isSave = false;
		if(!StringUtils.isNullBlank(businessId))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(customSingleClear, marginAmount,isSave);
//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = customerClearAccountService.isClearAccount(customSingleClear,marginAmount_bwb);
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
				boolean isp = customerClearAccountService.isProfitAndLoss(customSingleClear.getCurrencytext(), customSingleClear.getCompanyno());
//				可以全清出清账凭证
				if(infoVoucher.isClear()){
					Voucher clearVoucher =customerClearAccountService.saveClearVoucherByCustomer(parameterVoucher,infoVoucher,businessId,ClearConstant.SINGLE_TYPE,isp);
//					数据有错误
					if(null == clearVoucher){
						jo.put("isRight", false);
						jo.put("info", info.CODE_9);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
				}else{
//				部分清（有外币出汇损）,并且差额不为0
					if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
						Voucher plVoucher = customerClearAccountService.saveProfitAndLossVoucher(parameterVoucher);
					}
					
				}
//				保存本次全清的数据，用来更新isclear状态
				request.getSession().setAttribute(businessId, infoVoucher);
				//判断是否需要删除
				this.voucherService.judgeVoucherNeedDel(businessId);
				
				jo.put("businessid", businessId);
				jo.put("customsclearid", businessId);
				jo.put("isRight", true);
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}else{
//				数据错误不能保存
				jo.put("customsclearid", businessId);
				jo.put("isRight", infoVoucher.isRight());
				jo.put("info", infoVoucher.getInfo());
				this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
			}
		}else{
//			数据错误不能保存
			jo.put("customsclearid", businessId);
			jo.put("isRight", info.isRight());
			jo.put("info", info.getInfo());
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
		}

	}
	
	/**
	 * 确认清帐
	 */	
	public void _submitClear2(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		 Set<UnClearCollect> unClearCollectdeleteItems =
		 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
		 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		
		
		// 更新业务状态为清帐生效(审批通过)。
		customSingleClear.setBusinessstate(BusinessState.COLLECTSUBMITPASS);
		this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
		
		
		// 客户未清抬头数据中的数据，判断是否已结清
		String bussinessid = customSingleClear.getCustomsclearid();
		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//		是否全清
		if(infoVoucher.isClear()){
			customerClearAccountService.updateIsClear(bussinessid);
		}else{
			customerClearAccountService.updateIsClear(infoVoucher);
		}		
		request.getSession().removeAttribute(bussinessid);

		jo.put("customsclearid", customSingleClear.getCustomsclearid());
		jo.put("settlesubjectid", customSingleClear.getSettleSubject() != null ? customSingleClear.getSettleSubject().getSettlesubjectid() : "");
		jo.put("fundflowid", customSingleClear.getFundFlow() != null ? customSingleClear.getFundFlow().getFundflowid() : "");
		String creator = customSingleClear.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", customSingleClear.getCreatetime());
		String lastmodifyer = customSingleClear.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", customSingleClear.getLastmodifytime());
		jo.put("businessstate", customSingleClear.getBusinessstate());

		this.operateSuccessfullyWithString(response, jo.toString());

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
					whereSql = URLDecoder.decode(" and  businessstate  not in ('3','-1')", "UTF-8") + whereSql;
				}
				if("1".equals(businessstate)){
					whereSql = URLDecoder.decode(" and businessstate  in ('3','-1')", "UTF-8") + whereSql;
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
		String customertitleids = request.getParameter("customertitleids");
		String customclearid = request.getParameter("customerclearid");
		//先删除
		if(!StringUtils.isNullBlank(customclearid)){
			String [] ids=customertitleids.split(",");
			CustomSingleClear customSingleClear =this.customSingleClearService._get(customclearid);
			for(UnClearCollect unClearCollect :customSingleClear.getUnClearCollect()){
				for(String id :ids){
					if(unClearCollect.getCustomertitleid().equals(id)){
						customSingleClear.getUnClearCollect().remove(unClearCollect);
						break;
					}
				}
			}
			for(UnClearCustomBill unClearCustomBill :customSingleClear.getUnClearCustomBill()){
				for(String id :ids){
					if(unClearCustomBill.getCustomertitleid().equals(id)){
						customSingleClear.getUnClearCustomBill().remove(unClearCustomBill);
						break;
					}
				}
			}
			this.customSingleClearService._saveOrUpdate(customSingleClear, getBusinessObject());
		}
		
		JSONObject jsonList = this.customSingleClearService._queryUnClear(customertitleids);
		
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
		String businessId = request.getParameter("customsclearid");
		try
		{
			String voucherid = this.customSingleClearService.getVoucherId(businessId);
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
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBills = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBills);
		 Set<UnClearCustomBill> unClearCustomBilldeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollects = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollects);
		Set<UnClearCollect> unClearCollectdeleteItems =
			 ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new
			 Object[] { customSingleClear }, UnClearCollect.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			customSingleClear.setSettleSubject(settleSubject);
			settleSubject.setCustomSingleClear(customSingleClear);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			customSingleClear.setFundFlow(fundFlow);
			fundFlow.setCustomSingleClear(customSingleClear);
		}
		ParameterVoucherObject parameterVoucher =customerClearAccountService.setParameter(customSingleClear);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;		
		if (settleSubject != null || fundFlow != null){			
			settleSubjectVoucher = customerClearAccountService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountService.getMargin(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}	
		boolean isSave = false;
		if(!StringUtils.isNullBlank(customSingleClear.getCustomsclearid()))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(customSingleClear, marginAmount,isSave);
		jo.put("isRight", info.isRight());
		jo.put("info", info.getInfo());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}
}