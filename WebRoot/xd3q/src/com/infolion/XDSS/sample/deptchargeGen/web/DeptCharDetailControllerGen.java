/*
 * @(#)DeptCharDetailControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分51秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptchargeGen.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.util.Set;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.JsonUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;
import com.infolion.XDSS.sample.deptcharge.service.DeptCharDetailService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 费用明细(DeptCharDetail)控制器类
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
public class DeptCharDetailControllerGen extends AbstractGenController
{
	private final String boName = "DeptCharDetail";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected DeptCharDetailService deptCharDetailService;
	
	public void setDeptCharDetailService(DeptCharDetailService deptCharDetailService)
	{
		this.deptCharDetailService = deptCharDetailService;
	}
	
	/**
	 * 删除  
	 *   
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String deptchardetailid = request.getParameter("deptchardetailid");
		deptCharDetailService._delete(deptchardetailid,getBusinessObject());
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
		String deptCharDetailIds = request.getParameter("deptCharDetailIds");
		deptCharDetailService._deletes(deptCharDetailIds,getBusinessObject());
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
		DeptCharDetail deptCharDetail = new DeptCharDetail();
	    deptCharDetail = (DeptCharDetail) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharDetail.class , false , request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		
		request.setAttribute("deptCharDetail", deptCharDetail);  
		return new ModelAndView("XDSS/sample/deptcharge/deptCharDetailView");
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
		DeptCharDetail deptCharDetail = (DeptCharDetail) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharDetail.class , true , request.getMethod(), true);
this.deptCharDetailService._submitProcess(deptCharDetail		,getBusinessObject());
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
		return new ModelAndView("XDSS/sample/deptcharge/deptCharDetailManage");
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
		DeptCharDetail deptCharDetail  = new DeptCharDetail();
		String deptchardetailid = request.getParameter("deptchardetailid");
		deptCharDetail.setDeptchardetailid(deptchardetailid);
		LockService.unLockBOData(deptCharDetail);
		this.operateSuccessfullyHiddenInfo(response);
	}
    
	/**
	 * 分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _assign (HttpServletRequest request, HttpServletResponse response)
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
		DeptCharDetail deptCharDetail = (DeptCharDetail) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharDetail.class , true , request.getMethod(), true);
this.deptCharDetailService._saveOrUpdate(deptCharDetail,getBusinessObject());
		jo.put("deptchardetailid", deptCharDetail.getDeptchardetailid());
		this.operateSuccessfullyWithString(response,jo.toString());
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
	 * 附加空行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _appendLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 插入行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _insertLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加同级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加下级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addSubNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 删除节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _deleteNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChart (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表明细查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 下载  
	 *   
	 * @param request
	 * @param response
	 */
	public void _download (HttpServletRequest request, HttpServletResponse response)
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
		DeptCharDetail deptCharDetail = new DeptCharDetail();
	    deptCharDetail = (DeptCharDetail) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharDetail.class , false, request.getMethod(), false);
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("deptCharDetail", deptCharDetail);
		return new ModelAndView("XDSS/sample/deptcharge/deptCharDetailEdit");
	}
	
	/**
	 * 创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
	    DeptCharDetail deptCharDetail = new DeptCharDetail();
		request.setAttribute("vt", getBusinessObject().getViewText());
		request.setAttribute("deptCharDetail",deptCharDetail);

	    return new ModelAndView("XDSS/sample/deptcharge/deptCharDetailAdd");
	}
	
	
	/**
	 * 复制创建  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		DeptCharDetail deptCharDetail = new DeptCharDetail ();
	    deptCharDetail = (DeptCharDetail) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), DeptCharDetail.class , false , request.getMethod(), false);
		request.setAttribute("deptCharDetail", deptCharDetail);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("XDSS/sample/deptcharge/deptCharDetailAdd");
	}

}