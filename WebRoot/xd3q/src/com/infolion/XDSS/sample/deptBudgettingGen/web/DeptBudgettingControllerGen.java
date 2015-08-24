/*
 * @(#)DeptBudgettingControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月12日 11点34分35秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptBudgettingGen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Set;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.JsonUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.XDSS.sample.deptBudgetting.service.DeptBudgettingService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 部门预算编制(DeptBudgetting)控制器类
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
public class DeptBudgettingControllerGen extends AbstractGenController
{
	private final String boName = "DeptBudgetting";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected DeptBudgettingService deptBudgettingService;

	public void setDeptBudgettingService(DeptBudgettingService deptBudgettingService)
	{
		this.deptBudgettingService = deptBudgettingService;
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
		DeptBudgetting deptBudgetting = new DeptBudgetting();
		String id = request.getParameter("deptbudgettingid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;
		deptBudgetting = this.deptBudgettingService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000228");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
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
		request.setAttribute("deptBudgetting", deptBudgetting);
		return new ModelAndView("XDSS/sample/deptBudgetting/deptBudgettingEdit");
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		// TODO ADD
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);

		DeptBudgetting deptBudgetting = new DeptBudgetting();
		String workflowTaskId = request.getParameter("workflowTaskId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("deptBudgetting", deptBudgetting);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000228");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		return new ModelAndView("XDSS/sample/deptBudgetting/deptBudgettingAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("deptbudgettingid");
		DeptBudgetting deptBudgetting = this.deptBudgettingService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("deptBudgetting", deptBudgetting);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		return new ModelAndView("XDSS/sample/deptBudgetting/deptBudgettingAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String deptbudgettingid = request.getParameter("deptbudgettingid");
		deptBudgettingService._delete(deptbudgettingid, getBusinessObject());
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
		String deptBudgettingIds = request.getParameter("deptBudgettingIds");
		deptBudgettingService._deletes(deptBudgettingIds, getBusinessObject());
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
		DeptBudgetting deptBudgetting = new DeptBudgetting();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("deptbudgettingid");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(businessId))
		{
			String budorgid = request.getParameter("budorgid");
			String ayear = request.getParameter("ayear");
			deptBudgetting = this.deptBudgettingService._view(budorgid, ayear);
		}
		else
		{
			deptBudgetting = this.deptBudgettingService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000228");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}

		request.setAttribute("deptBudgetting", deptBudgetting);
		return new ModelAndView("XDSS/sample/deptBudgetting/deptBudgettingView");
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
		DeptBudgetting deptBudgetting = (DeptBudgetting) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptBudgetting.class, true, request.getMethod(), true);
		this.deptBudgettingService._submitProcess(deptBudgetting, getBusinessObject());
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
		return new ModelAndView("XDSS/sample/deptBudgetting/deptBudgettingManage");
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
		DeptBudgetting deptBudgetting = new DeptBudgetting();
		String deptbudgettingid = request.getParameter("deptbudgettingid");
		deptBudgetting.setDeptbudgettingid(deptbudgettingid);
		LockService.unLockBOData(deptBudgetting);
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
		String calActivityId = request.getParameter("calActivityId");
		// 绑定主对象值
		DeptBudgetting deptBudgetting = (DeptBudgetting) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptBudgetting.class, true, request.getMethod(), true);
		this.deptBudgettingService._saveOrUpdate(deptBudgetting, getBusinessObject());
		// TODO ADD
		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中，并关闭当前页。
		if (StringUtils.isNotBlank(calActivityId))
		{
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null)
			{
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(deptBudgetting.getDeptbudgettingid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			JSONObject jo = new JSONObject();
			jo.put("deptbudgettingid", deptBudgetting.getDeptbudgettingid());
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

}