/*
 * @(#)CustomSingleOtherController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点48分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOther.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClearOther.domain.CustomSingleOther;
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.singleClearOther.service.CustomSingleOtherService;
import com.infolion.xdss3.singleClearOther.service.CustomerClearAccountOtherService;
import com.infolion.xdss3.singleClearOtherGen.web.CustomSingleOtherControllerGen;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
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

/**
 * <pre>
 * 其他公司客户单清帐(CustomSingleOther)控制器类
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
public class CustomSingleOtherController extends CustomSingleOtherControllerGen
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
	private  CustomerClearAccountOtherService customerClearAccountOtherService;
	
	/**
	 * @param customerClearAccountOtherService the customerClearAccountOtherService to set
	 */
	public void setCustomerClearAccountOtherService(
			CustomerClearAccountOtherService customerClearAccountOtherService) {
		this.customerClearAccountOtherService = customerClearAccountOtherService;
	}
	
	@Autowired
	private CustomSingleOtherService customSingleOtherService;
	
	
	/**
	 * @param customSingleOtherService the customSingleOtherService to set
	 */
	public void setCustomSingleOtherService(
			CustomSingleOtherService customSingleOtherService) {
		this.customSingleOtherService = customSingleOtherService;
	}

	@Override
	public void _queryUnClear(HttpServletRequest request, HttpServletResponse response)
	{
		
		
		String customsclearid = request.getParameter("customsclearid");
		if(!StringUtils.isNullBlank(customsclearid)){
			CustomSingleOther customSingleOther = customSingleOtherService._getDetached(customsclearid);
			customSingleOther.setUnCleaCollectOther(null);
			customSingleOther.setUnCustomBillOther(null);
//			customSingleOtherService._saveOrUpdate(customSingleOther,null,null, getBusinessObject());
			this._saveOrUpdate(customSingleOther);
		}
		String custom = request.getParameter("custom");
		String subject = request.getParameter("subject");
		String currencytext = request.getParameter("currencytext");
		String companyno = request.getParameter("companyno");
		String depid = request.getParameter("depid");
		
		JSONObject jo = this.customSingleOtherService._queryUnClear(custom, subject,currencytext,companyno,depid);
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
	 * 取得已生成凭证的ID
	 * @param businessId
	 * @return
	 */
	public void getVoucherId(HttpServletRequest request, HttpServletResponse response){
		String businessId = request.getParameter("customsclearid");
		try
		{
			String voucherid = this.customSingleOtherService.getVoucherId(businessId);
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
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
		//绑定子对象(一对一关系)
		SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
		if(SettleSubjectOther!=null)
		{
		customSingleOther.setSettleSubjectOther(SettleSubjectOther);
		SettleSubjectOther.setCustomSingleOther(customSingleOther);
		}
		//绑定子对象(一对一关系)
		FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
		if(FundFlowOther!=null)
		{
		customSingleOther.setFundFlowOther(FundFlowOther);
		FundFlowOther.setCustomSingleOther(customSingleOther);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
		Set<UnCleaCollectOther> UnCleaCollectOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
		Set<UnCustomBillOther> UnCustomBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		
		
		ParameterVoucherObject parameterVoucher =customerClearAccountOtherService.setParameter(customSingleOther);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;		
		if (SettleSubjectOther != null || FundFlowOther != null){			
			settleSubjectVoucher = customerClearAccountOtherService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountOtherService.getMargin(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}	
		boolean isSave = false;
		if(!StringUtils.isNullBlank(customSingleOther.getCustomsclearid()))isSave=true;
		IInfo info =customerClearAccountOtherService.checkClearData(customSingleOther, marginAmount,isSave);
		jo.put("isRight", info.isRight());
		jo.put("info", info.getInfo());
		this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
	}
	
	/**
	 * 确认清帐
	 */	
	public void _submitClear(HttpServletRequest request, HttpServletResponse response){
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
		//绑定子对象(一对一关系)
		SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
		if(SettleSubjectOther!=null)
		{
		customSingleOther.setSettleSubjectOther(SettleSubjectOther);
		SettleSubjectOther.setCustomSingleOther(customSingleOther);
		}
		//绑定子对象(一对一关系)
		FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
		if(FundFlowOther!=null)
		{
		customSingleOther.setFundFlowOther(FundFlowOther);
		FundFlowOther.setCustomSingleOther(customSingleOther);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
		Set<UnCleaCollectOther> UnCleaCollectOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
		Set<UnCustomBillOther> UnCustomBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		
		
		// 更新业务状态为清帐生效(审批通过)。
		customSingleOther.setBusinessstate(BusinessState.COLLECTSUBMITPASS);
		this._saveOrUpdate(customSingleOther);
//		this.customSingleOtherService._saveOrUpdate(customSingleOther,UnCleaCollectOtherdeleteItems,UnCustomBillOtherdeleteItems, getBusinessObject());
		
		
		// 客户未清抬头数据中的数据，判断是否已结清
		String bussinessid = customSingleOther.getCustomsclearid();
		IInfoVoucher infoVoucher = (IInfoVoucher)request.getSession().getAttribute(bussinessid);
//		是否全清
		if(infoVoucher.isClear()){
			customerClearAccountOtherService.updateIsClear(bussinessid);
		}else{
			customerClearAccountOtherService.updateIsClear(infoVoucher);
		}		
		request.getSession().removeAttribute(bussinessid);
		
		jo.put("customclearno", customSingleOther.getCustomclearno());		jo.put("customsclearid", customSingleOther.getCustomsclearid());
		jo.put("settlesubjectid", customSingleOther.getSettleSubjectOther()!=null ? customSingleOther.getSettleSubjectOther().getSettlesubjectid() : "");
		jo.put("fundflowid", customSingleOther.getFundFlowOther()!=null ? customSingleOther.getFundFlowOther().getFundflowid() : "");String creator = customSingleOther.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", customSingleOther.getCreatetime());
		String lastmodifyer = customSingleOther.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", customSingleOther.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
		
	}
	
	/**
	 * 模拟凭证  
	 *   
	 * @param request
	 * @param response
	 */
	public void _voucherPreview (HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
		//绑定子对象(一对一关系)
		SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
		if(SettleSubjectOther!=null)
		{
		customSingleOther.setSettleSubjectOther(SettleSubjectOther);
		SettleSubjectOther.setCustomSingleOther(customSingleOther);
		}
		//绑定子对象(一对一关系)
		FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
		if(FundFlowOther!=null)
		{
		customSingleOther.setFundFlowOther(FundFlowOther);
		FundFlowOther.setCustomSingleOther(customSingleOther);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
		Set<UnCleaCollectOther> UnCleaCollectOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
		Set<UnCustomBillOther> UnCustomBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
				
//		this.customSingleOtherService._saveOrUpdate(customSingleOther,UnCleaCollectOtherdeleteItems,UnCustomBillOtherdeleteItems, getBusinessObject());
		this._saveOrUpdate(customSingleOther);
		String businessId = customSingleOther.getCustomsclearid();
		customSingleOther =  customSingleOtherService._get(businessId);
		// 先删除凭证信息。
		this.voucherService.deleteVoucherByBusinessid(businessId);
		ParameterVoucherObject parameterVoucher =customerClearAccountOtherService.setParameter(customSingleOther);
		BigDecimal marginAmount = new BigDecimal("0");
		Voucher settleSubjectVoucher = null;
		
		if (SettleSubjectOther != null || FundFlowOther != null){			
			settleSubjectVoucher = customerClearAccountOtherService.saveSettleSubjectVoucher(parameterVoucher);				
			marginAmount = customerClearAccountOtherService.getMargin(settleSubjectVoucher.getVoucherItem());
			parameterVoucher.setVoucherid(settleSubjectVoucher.getVoucherid());
		}
		//判断是否需要删除
		this.voucherService.judgeVoucherNeedDel(businessId);
		boolean isSave = false;
		if(!StringUtils.isNullBlank(businessId))isSave=true;
		IInfo info =customerClearAccountOtherService.checkClearData(customSingleOther, marginAmount,isSave);
//		判断本次数据是否正确
		if(info.isRight()){
			IInfoVoucher infoVoucher = customerClearAccountOtherService.isClearAccount(customSingleOther);
			parameterVoucher.setSubtractVlaue(infoVoucher.getSubtractVlaue());
			parameterVoucher.setSumAdjustamount(infoVoucher.getSumAdjustamount());
			parameterVoucher.setTaxCode(infoVoucher.getTaxCode());
//			判断以前的业务数据是否正确
			if(infoVoucher.isRight()){
//				是否出汇损
				boolean isp = customerClearAccountOtherService.isProfitAndLoss(customSingleOther.getCurrencytext(), customSingleOther.getCompanyno());
//				可以全清出清账凭证
				if(infoVoucher.isClear()){
					Voucher clearVoucher =customerClearAccountOtherService.saveClearVoucher(parameterVoucher,infoVoucher,businessId,ClearConstant.SINGLE_TYPE,isp);
//					数据有错误
					if(null == clearVoucher){
						jo.put("isRight", false);
						jo.put("info", info.CODE_9);
						this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
					}
				}else{
//				部分清（有外币出汇损）,并且差额不为0
					if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){									
						Voucher plVoucher = customerClearAccountOtherService.saveProfitAndLossVoucher(parameterVoucher);
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
				jo.put("isRight", info.isRight());
				jo.put("info", info.getInfo());
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
	 * 保存
	 * @param customSingleOther
	 */
	public void _saveOrUpdate(CustomSingleOther customSingleOther){		
		this.customSingleOtherService.deleteCustomSingleClearUnderOneToManySubObject(customSingleOther.getCustomsclearid());	
		this.customSingleOtherService._saveOrUpdate(customSingleOther);
	}
	
	/**
	 * 保存  
	 *   
	 * @param request
	 * @param response
	 */
	@Override
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomSingleOther customSingleOther = (CustomSingleOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleOther.class , true , request.getMethod(), true);
		//绑定子对象(一对一关系)
		SettleSubjectOther SettleSubjectOther = (SettleSubjectOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubjectOther.class, false , request.getMethod(), true);
		if(SettleSubjectOther!=null)
		{
		customSingleOther.setSettleSubjectOther(SettleSubjectOther);
		SettleSubjectOther.setCustomSingleOther(customSingleOther);
		}
		//绑定子对象(一对一关系)
		FundFlowOther FundFlowOther = (FundFlowOther) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlowOther.class, false , request.getMethod(), true);
		if(FundFlowOther!=null)
		{
		customSingleOther.setFundFlowOther(FundFlowOther);
		FundFlowOther.setCustomSingleOther(customSingleOther);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCleaCollectOther> UnCleaCollectOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		customSingleOther.setUnCleaCollectOther(UnCleaCollectOthermodifyItems);
		Set<UnCleaCollectOther> UnCleaCollectOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCleaCollectOther.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnCustomBillOther> UnCustomBillOthermodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		customSingleOther.setUnCustomBillOther(UnCustomBillOthermodifyItems);
		Set<UnCustomBillOther> UnCustomBillOtherdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleOther }, UnCustomBillOther.class, null);
		
		this._saveOrUpdate(customSingleOther);

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
				calActivity.setBusid(customSingleOther.getCustomsclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
		jo.put("customclearno", customSingleOther.getCustomclearno());		jo.put("customsclearid", customSingleOther.getCustomsclearid());
		jo.put("settlesubjectid", customSingleOther.getSettleSubjectOther()!=null ? customSingleOther.getSettleSubjectOther().getSettlesubjectid() : "");
		jo.put("fundflowid", customSingleOther.getFundFlowOther()!=null ? customSingleOther.getFundFlowOther().getFundflowid() : "");String creator = customSingleOther.getCreator();
		String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
		jo.put("creator_text", creator_text);
		jo.put("creator", creator);
		jo.put("createtime", customSingleOther.getCreatetime());
		String lastmodifyer = customSingleOther.getLastmodifyer();
		String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
		jo.put("lastmodifyer_text", lastmodifyer_text);
		jo.put("lastmodifyer", lastmodifyer);
		jo.put("lastmodifytime", customSingleOther.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
	   }
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
	
}