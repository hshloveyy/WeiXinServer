/*
 * @(#)CustomSingleClearControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 11点44分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.web;


import java.math.BigDecimal;
import java.util.Set;

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
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClear.service.CustomSingleClearService;

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
public class CustomSingleClearControllerGen extends AbstractGenController
{
	private final String boName = "CustomSingleClear";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected CustomSingleClearService customSingleClearService;

	public void setCustomSingleClearService(CustomSingleClearService customSingleClearService)
	{
		this.customSingleClearService = customSingleClearService;
	}

	/**
	 * 清除分配
	 * 
	 * @param request
	 * @param response
	 */
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

	}

	/**
	 * 作废
	 * 
	 * @param request
	 * @param response
	 */
	public void _blankOut(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryUnClear(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _autoAssign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		String id = request.getParameter("customsclearid");
		CustomSingleClear customSingleClear = this.customSingleClearService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("customSingleClear", customSingleClear);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000330");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/singleClear/customSingleClearAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String customsclearid = request.getParameter("customsclearid");
		customSingleClearService._delete(customsclearid, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request, HttpServletResponse response)
	{
		String customSingleClearIds = request.getParameter("customSingleClearIds");
		customSingleClearService._deletes(customSingleClearIds, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		CustomSingleClear customSingleClear = new CustomSingleClear();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("customsclearid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			customSingleClear = this.customSingleClearService._get(id);
		}
		else
		{
			customSingleClear = this.customSingleClearService._get(id);
		}
		
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000330");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}

		request.setAttribute("customSingleClear", customSingleClear);
		return new ModelAndView("xdss3/singleClear/customSingleClearView");
	}

	/**
	 * 提交
	 * 
	 * @param request
	 * @param response
	 */

	public void _submitProcess(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		Set<UnClearCustomBill> deletedUnClearCustomBillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		Set<UnClearCollect> deletedUnClearCollectSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollectmodifyItems);
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
		if (!StringUtils.isNullBlank(type) && type.equalsIgnoreCase("view"))
		{
			this.customSingleClearService._saveOrUpdate(customSingleClear, deletedUnClearCustomBillSet, deletedUnClearCollectSet, getBusinessObject());
		}
		this.customSingleClearService._submitProcess(customSingleClear, deletedUnClearCustomBillSet, deletedUnClearCollectSet, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/singleClear/customSingleClearManage");
	}

	/**
	 * 取消后的解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _cancel(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		CustomSingleClear customSingleClear = new CustomSingleClear();
		String customsclearid = request.getParameter("customsclearid");
		customSingleClear.setCustomsclearid(customsclearid);
		LockService.unLockBOData(customSingleClear);
		this.operateSuccessfullyHiddenInfo(response);
	}

	/**
	 * 分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _assign(HttpServletRequest request, HttpServletResponse response)
	{

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
		CustomSingleClear customSingleClear = (CustomSingleClear) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), CustomSingleClear.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCustomBill> unClearCustomBillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		customSingleClear.setUnClearCustomBill(unClearCustomBillmodifyItems);
		Set<UnClearCustomBill> unClearCustomBilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCustomBill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<UnClearCollect> unClearCollectmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
		customSingleClear.setUnClearCollect(unClearCollectmodifyItems);
		Set<UnClearCollect> unClearCollectdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { customSingleClear }, UnClearCollect.class, null);
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
		this.customSingleClearService._saveOrUpdate(customSingleClear, unClearCustomBilldeleteItems, unClearCollectdeleteItems, getBusinessObject());

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
				calActivity.setBusid(customSingleClear.getCustomsclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
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
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}

	/**
	 * 附加空行
	 * 
	 * @param request
	 * @param response
	 */
	public void _appendLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 插入行
	 * 
	 * @param request
	 * @param response
	 */
	public void _insertLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加同级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加下级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addSubNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 删除节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _deleteNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChart(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表明细查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 */
	public void _download(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 */
	public void _upload(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		CustomSingleClear customSingleClear = new CustomSingleClear();
		String id = request.getParameter("customsclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			customSingleClear = this.customSingleClearService._getForEdit(id);
		}
		else
		{
			customSingleClear = this.customSingleClearService._getForEdit(id);
		}
		
		BigDecimal sumclearamount = new BigDecimal(0);
		BigDecimal sumadjustamount = new BigDecimal(0);
		BigDecimal sumnowclearamount = new BigDecimal(0);
		for(UnClearCollect unClearCollect : customSingleClear.getUnClearCollect()){
			if(null !=unClearCollect.getNowclearamount())
			sumnowclearamount = sumnowclearamount.add(unClearCollect.getNowclearamount());
		
		}
		for(UnClearCustomBill unClearCustomBill : customSingleClear.getUnClearCustomBill()){
			if(null != unClearCustomBill.getClearamount())
			sumclearamount = sumclearamount.add(unClearCustomBill.getClearamount());
			if(null != unClearCustomBill.getAdjustamount())
			sumadjustamount = sumadjustamount.add(unClearCustomBill.getAdjustamount());
		}
		request.setAttribute("sumclearamount", sumclearamount.toString());
		request.setAttribute("sumadjustamount", sumadjustamount.toString());
		request.setAttribute("sumnowclearamount", sumnowclearamount.toString());
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000330");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("customSingleClear", customSingleClear);
		return new ModelAndView("xdss3/singleClear/customSingleClearEdit");
	}

	/**
	 * 批量解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _unlock(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 锁定
	 * 
	 * @param request
	 * @param response
	 */
	public void _locked(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		CustomSingleClear customSingleClear = new CustomSingleClear();
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
			customSingleClear.setDeptid(xdssUserContext.getSysUser().getDeptId());

		request.setAttribute("customSingleClear", customSingleClear);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000330");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/singleClear/customSingleClearAdd");
	}	
}