/*
 * @(#)TestProjectControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年04月21日 15点06分40秒
 *  描　述：创建
 */
package com.infolion.TEMP.TempGen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.TEMP.Temp.domain.TestProject;
import com.infolion.TEMP.Temp.service.TestProjectService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 项目（测试）(TestProject)控制器类
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
public class TestProjectControllerGen extends AbstractGenController
{
	private final String boName = "TestProject";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected TestProjectService testProjectService;

	public void setTestProjectService(TestProjectService testProjectService)
	{
		this.testProjectService = testProjectService;
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
		TestProject testProject = new TestProject();
		String id = request.getParameter("projectid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;
		testProject = this.testProjectService._getForEdit(id);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000214");
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
		request.setAttribute("testProject", testProject);
		return new ModelAndView("TEMP/Temp/testProjectEdit");
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
		TestProject testProject = new TestProject();
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
		request.setAttribute("testProject", testProject);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000214");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		return new ModelAndView("TEMP/Temp/testProjectAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("projectid");
		TestProject testProject = this.testProjectService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("testProject", testProject);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		return new ModelAndView("TEMP/Temp/testProjectAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String projectid = request.getParameter("projectid");
		testProjectService._delete(projectid, getBusinessObject());
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
		String testProjectIds = request.getParameter("testProjectIds");
		testProjectService._deletes(testProjectIds, getBusinessObject());
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
		TestProject testProject = new TestProject();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String id = request.getParameter("projectid");
		String businessId = request.getParameter("businessId");

		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			if (StringUtils.isNullBlank(businessId))
			{
				String projectid = request.getParameter("projectid");
				testProject = this.testProjectService._view(projectid);
			}
		}
		else
		{
			testProject = this.testProjectService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000214");
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

		request.setAttribute("testProject", testProject);
		return new ModelAndView("TEMP/Temp/testProjectView");
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
		TestProject testProject = (TestProject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), TestProject.class, true, request.getMethod(), true);
		this.testProjectService._submitProcess(testProject, getBusinessObject());
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
		return new ModelAndView("TEMP/Temp/testProjectManage");
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
		TestProject testProject = new TestProject();
		String projectid = request.getParameter("projectid");
		testProject.setProjectid(projectid);
		LockService.unLockBOData(testProject);
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
		TestProject testProject = (TestProject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), TestProject.class, true, request.getMethod(), true);
		this.testProjectService._saveOrUpdate(testProject, getBusinessObject());

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
				calActivity.setBusid(testProject.getProjectid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("projectid", testProject.getProjectid());
			String creator = testProject.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", testProject.getCreatetime());
			String lastmodifyer = testProject.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", testProject.getLastmodifytime());
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