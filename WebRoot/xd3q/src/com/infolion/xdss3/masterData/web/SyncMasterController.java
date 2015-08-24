/*
 * @(#)SyncMasterController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月04日 08点01分48秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.xdss3.masterData.service.SyncMasterDataService;

/**
 * <pre>
 * 主数据同步(SyncMaster)控制器类
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
public class SyncMasterController extends BaseMultiActionController
{

	@Autowired
	private SyncMasterDataService syncMasterDataService;

	/**
	 * @param syncMasterDataService
	 *            the syncMasterDataService to set
	 */
	public void setSyncMasterDataService(SyncMasterDataService syncMasterDataService)
	{
		this.syncMasterDataService = syncMasterDataService;
	}

	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("xdss3/masterData/SapDataSync");
	}

	/**
	 * 同步主数据
	 * 
	 * @param request
	 * @param response
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException 
	 */
	public void _ayncMasterData(HttpServletRequest request, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException
	{
		String methodName = request.getParameter("methodName");
		JSONObject jo = new JSONObject();
		String jsontext = "";
		
		try
		{
			/**
			 * 供应商未清数据
			 */
			if(methodName.equals("_synchronizeUnclearVendor"))
			{
				syncMasterDataService._synchronizeUnclearVendor(null,true);
			}
			/**
			 * 客户未清数据
			 */
			else if(methodName.equals("_synchronizeUnclearCustomer"))
			{
				syncMasterDataService._synchronizeUnclearCustomer(null,true);
			}
			/**
			 * 供应商未清数据其他公司
			 */
			if(methodName.equals("_synchronizeUnclearVendorOthers"))
			{
				syncMasterDataService._synchronizeUnclearVendorOthers(null);
			}
			/**
			 * 客户未清数据其他公司
			 */
			else if(methodName.equals("_synchronizeUnclearCustomerOthers"))
			{
				syncMasterDataService._synchronizeUnclearCustomerOthers(null);
			}
			else
			{
				syncMasterDataService.getClass().getMethod(methodName).invoke(syncMasterDataService);
			}			
			jo.put("success", true);
			jo.put("message", "同步成功!");
		}
		catch( Exception ex )
		{
			jo.put("success", false);
			jo.put("message", "同步失败!");
		}
		
		jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
	
	public void _syncAll(HttpServletRequest request, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException
	{
		JSONObject jo = new JSONObject();
		String jsontext = "";
		
		try
		{
			syncMasterDataService._synchronizeCashFlowItem();
			syncMasterDataService._synchronizeCompany();
			syncMasterDataService._synchronizeCostCenter();
			syncMasterDataService._synchronizeCustomer();
			syncMasterDataService._synchronizeDept();
			syncMasterDataService._synchronizeHkont();
			syncMasterDataService._synchronizeInvoiceVerify();
			syncMasterDataService._synchronizePrctr();
			syncMasterDataService._synchronizeSupplier();
			syncMasterDataService._synchronizeUnclearCustomer(null,true);
			syncMasterDataService._synchronizeUnclearVendor(null,true);
			syncMasterDataService._synchronizeUnclearVendorOthers(null);
			syncMasterDataService._synchronizeUnclearCustomerOthers(null);
			syncMasterDataService._synchronizeVbrk();
			syncMasterDataService._synchronizeMatGroup();
			syncMasterDataService._synchronizeTcurr(); 
			
			jo.put("success", true);
			jo.put("message", "同步成功!");
		}
		catch( Exception ex )
		{
			jo.put("success", false);
			jo.put("message", "同步失败!");
		}
		
		jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}
}