/*
 * @(#)AdvanceWarnInfoController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点17分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infolion.xdss3.advanceWarnGen.web.AdvanceWarnInfoControllerGen;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.thoughtworks.xstream.converters.basic.StringBuilderConverter;

/**
 * <pre>
 * 业务预警信息(AdvanceWarnInfo)控制器类
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
public class AdvanceWarnInfoController extends AdvanceWarnInfoControllerGen
{
	public void processWarnInfo(HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		//获取输入参数
		String warnInfoIds = req.getParameter("warnInfoId");
		String againWarnDay = req.getParameter("againWarnDay");
		String warnIds = req.getParameter("warnId");
		String primaryKeys = req.getParameter("primarykey");
		
		if(warnInfoIds == null || warnInfoIds.equals("") )
		{
			this.operateFailly(resp);
		}
		String[] warnInfoIdList = StringUtils.splitString(warnInfoIds, ",");
		String[] warnIdList = StringUtils.splitString(warnIds, ",");
		String[] primarykeyList =  StringUtils.splitString(primaryKeys, ",");
		this.advanceWarnInfoService.modifyWarnState(warnInfoIdList, warnIdList, primarykeyList,againWarnDay);
		
		this.operateSuccessfully(resp);
	}
}