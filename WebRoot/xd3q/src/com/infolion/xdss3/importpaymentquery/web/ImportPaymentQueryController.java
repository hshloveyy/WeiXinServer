/*
 * @(#)ImportPaymentQueryController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年10月01日 04点51分07秒
 *  描　述：创建
 */
package com.infolion.xdss3.importpaymentquery.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.importpaymentqueryGen.web.ImportPaymentQueryControllerGen;
import com.infolion.platform.dpframework.core.annotation.BDPController;

/**
 * <pre>
 * 进口付款查询(ImportPaymentQuery)控制器类
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
public class ImportPaymentQueryController extends ImportPaymentQueryControllerGen
{
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/importpaymentquery/importPaymentQueryManage");
		
	}
}