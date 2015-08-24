/*
 * @(#)ExpiredShippingControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月04日 11点00分13秒
 *  描　述：创建
 */
package com.infolion.sample.ShippingListGen.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.util.Set;
import java.io.IOException;
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
import com.infolion.sample.ShippingList.domain.ExpiredShipping;
import com.infolion.sample.ShippingList.service.ExpiredShippingService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;

/**
 * <pre>
 * 过期出货清单(ExpiredShipping)控制器类
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
public class ExpiredShippingControllerGen extends BaseMultiActionController
{
	@Autowired
	private ExpiredShippingService expiredShippingService;
	
	public void setExpiredShippingService(ExpiredShippingService expiredShippingService)
	{
		this.expiredShippingService = expiredShippingService;
	}
    
	public void _expExcel(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Content-Type","application/force-download");
		response.setHeader("Content-Type","application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename=export.xls");
		String exportContent = request.getParameter("exportContent");
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(exportContent); 
			System.out.println(exportContent);
		}
		catch (IOException e)
		{
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 管理  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
		return new ModelAndView("sample/ShippingList/expiredShippingManage");
	}
}