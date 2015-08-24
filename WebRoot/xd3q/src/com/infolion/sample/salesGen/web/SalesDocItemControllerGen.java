/*
 * @(#)SalesDocItemControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 17点06分54秒
 *  描　述：创建
 */
package com.infolion.sample.salesGen.web;


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
import com.infolion.sample.sales.domain.SalesDocItem;
import com.infolion.sample.sales.service.SalesDocItemService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;

/**
 * <pre>
 * SAP销售凭证行项目(SalesDocItem)控制器类
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
public class SalesDocItemControllerGen extends BaseMultiActionController
{
	@Autowired
	private SalesDocItemService salesDocItemService;
	
	public void setSalesDocItemService(SalesDocItemService salesDocItemService)
	{
		this.salesDocItemService = salesDocItemService;
	}

	/**
	 * 查看  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		SalesDocItem salesDocItem = new SalesDocItem();
	    salesDocItem = (SalesDocItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SalesDocItem.class , false , request.getMethod(), false);
		request.setAttribute("salesDocItem", salesDocItem);  
		return new ModelAndView("sample/sales/salesDocItemView");
	}
    
	/**
	 * 管理  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
        
		return new ModelAndView("sample/sales/salesDocItemManage");
	}
}