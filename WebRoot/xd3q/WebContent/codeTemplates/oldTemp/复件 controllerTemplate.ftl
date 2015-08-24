/*
 * @(#)${entityAttribute.beanName}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${entityAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package com.infolion.${entityAttribute.packageName}.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.util.ExBeanUtils;

import com.infolion.${entityAttribute.packageName}.domain.${entityAttribute.boName};
import com.infolion.${entityAttribute.packageName}.service.${entityAttribute.boName}Service;

import com.infolion.platform.dpframework.core.web.BaseMultiActionController;

/**
 * <pre>
 * ${entityAttribute.beanName}控制器类
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
public class ${entityAttribute.beanName} extends BaseMultiActionController
{
	@Autowired
	private ${entityAttribute.boName}Service ${entityAttribute.boInstanceName}Service;
	
	public void set${entityAttribute.boName}Service(${entityAttribute.boName}Service ${entityAttribute.boInstanceName}Service)
	{
		this.${entityAttribute.boInstanceName}Service = ${entityAttribute.boInstanceName}Service;
	}
	
	//fields
	<#list entityTemp as modelPro>
	<#-- 控制器方法 -->
    <#if modelPro.primaryKey=="_manage">
    
	/**
	 * ${modelPro.methodDesc}:${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _manage(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderManage");
	}
	
	<#elseif modelPro.primaryKey=="_view">
	
	/**
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("{entityAttribute.beanInstanceName}Id");
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = this.${entityAttribute.boInstanceName}Service._get(id);
		request.setAttribute("${entityAttribute.beanInstanceName}", ${entityAttribute.beanInstanceName});
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderView");
	}
	
	<#elseif modelPro.primaryKey=="_edit">
	
	/**
	 * 编辑链接进入方法
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("{entityAttribute.beanInstanceName}Id");
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = this.${entityAttribute.boInstanceName}Service._get(id);
		request.setAttribute("${entityAttribute.beanInstanceName}", ${entityAttribute.beanInstanceName});
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderEdit");
	}
	
	<#elseif modelPro.primaryKey=="_create">
	
	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderAdd");
	}
	
	<#elseif modelPro.primaryKey=="_copyCreate">
	
	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("{entityAttribute.beanInstanceName}Id");
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = this.${entityAttribute.boInstanceName}Service._get(id);
		request.setAttribute("${entityAttribute.beanInstanceName}", ${entityAttribute.beanInstanceName});
		return new ModelAndView("sample/orderManage/purchaseOrder/purchaseOrderAdd");
	}
	
	<#elseif modelPro.primaryKey=="_update">
	
	/**
	 * 采购订单主对象保存，修改，以及订单行项目保存，修改和删除动作
	 * 
	 * @param request
	 * @param response
	 */
	public void _update(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = (${entityAttribute.beanName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${entityAttribute.beanName}.class);
		this.${entityAttribute.boInstanceName}Service._update(${entityAttribute.beanInstanceName});
		this.operateSuccessfully(response);
	}
	
	<#elseif modelPro.primaryKey=="_save">
	
	/**
	 * 采购订单主对象保存，以及订单行项目保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _save(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = (${entityAttribute.beanName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${entityAttribute.beanName}.class);
		this.${entityAttribute.boInstanceName}Service._save(${entityAttribute.beanInstanceName});
		this.operateSuccessfully(response);
	}
	
	<#elseif modelPro.primaryKey=="_delete">
	
	/**
	 * 单个删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String ${entityAttribute.beanInstanceName}Id = request.getParameter("${entityAttribute.beanInstanceName}Id");
		${entityAttribute.boInstanceName}Service._delete(${entityAttribute.beanInstanceName}Id);
		this.operateSuccessfully(response);
	}

	<#elseif modelPro.primaryKey=="_deletes">
	
	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request, HttpServletResponse response)
	{
		String ${entityAttribute.beanInstanceName}Ids = request.getParameter("${entityAttribute.beanInstanceName}Ids");
		${entityAttribute.boInstanceName}Service._deletes(${entityAttribute.beanInstanceName}Ids);
		this.operateSuccessfully(response);
	}

	<#elseif modelPro.primaryKey=="_query">
	
	/**
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response)
	{

	}
	
	<#elseif modelPro.primaryKey=="_submitProcess">
	
	/**
	 * 提交工作流
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitProcess(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${entityAttribute.beanName} ${entityAttribute.beanInstanceName} = (${entityAttribute.beanName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${entityAttribute.beanName}.class);
		this.${entityAttribute.boInstanceName}Service._submitProcess(${entityAttribute.beanInstanceName});
	}

	<#elseif modelPro.primaryKey=="_entryProcess">
	
	/**
	 * 工作流待办入口
	 * 
	 * @param request
	 * @param response
	 */
	public void _entryProcess(HttpServletRequest request, HttpServletResponse response)
	{

	}
	
	
	<#else>
    
	/**
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response)
	{

	}
	
    </#if>
}
