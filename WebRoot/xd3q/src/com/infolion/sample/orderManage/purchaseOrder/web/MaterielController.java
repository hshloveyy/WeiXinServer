/*
 * @(#)MaterielController.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-6-24
 *  描　述：创建
 */

package com.infolion.sample.orderManage.purchaseOrder.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.sample.orderManage.purchaseOrder.domain.Materiel;
import com.infolion.sample.orderManage.purchaseOrder.service.MaterielService;

/**
 * 
 * <pre>
 * 物料显示控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@BDPController(parent = "baseMultiActionController")
public class MaterielController extends BaseMultiActionController
{
	@Autowired
	private MaterielService materielService;

	/**
	 * 
	 * @param materielService
	 *            the materielService to set
	 */
	public void setMaterielService(MaterielService materielService)
	{
		this.materielService = materielService;
	}

	/**
	 * 查看物料，显示物料信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		String materielNo = request.getParameter("materielNo");
		Materiel materiel = materielService._get(materielNo);
		request.setAttribute("materiel", materiel);
		return new ModelAndView("sample/orderManage/purchaseOrder/materielView");
	}
}
