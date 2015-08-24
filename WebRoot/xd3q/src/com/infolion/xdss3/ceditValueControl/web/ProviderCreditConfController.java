/*
 * @(#)ProviderCreditConfController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点07分33秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditConfService;
import com.infolion.xdss3.ceditValueControlGen.web.ProviderCreditConfControllerGen;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.ExBeanUtils;

import java.lang.Exception;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 供应商信用额度配置(ProviderCreditConf)控制器类
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
public class ProviderCreditConfController extends ProviderCreditConfControllerGen
{
	@Autowired
	private ProviderCreditConfService providerCreditConfService;
	public void setProviderCreditConfService(ProviderCreditConfService providerCreditConfService)
	{
		this.providerCreditConfService = providerCreditConfService;
	}
	
    public ModelAndView _prepaymentManage(HttpServletRequest req, HttpServletResponse resp) {
        String userId = UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId();
        req.setAttribute("userId", userId);
        return new ModelAndView("xdss3/ceditValueControl/providerPrepaymentEdit");
    }
	
	public void updatePrepayment(HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		String providerid = req.getParameter("providerid");
		String projectid = req.getParameter("projectid");
		String strPrepay = req.getParameter("prepayment");
		String strRemark = req.getParameter("remark");
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");

		double prepayment = Double.parseDouble( req.getParameter("prepayment"));			
		
		int ret =
			this.providerCreditConfService.updatePrepayment(providerid, projectid,prepayment,strRemark);		
		
		this.operateSuccessfully(resp);
	}
	
	
	   
    public void checkExists(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 绑定主对象值
        ProviderCreditConf providerCreditConf = (ProviderCreditConf) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), ProviderCreditConf.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<ProviderCreditProj> providerCreditProjectmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { providerCreditConf }, ProviderCreditProj.class, null);
        providerCreditConf.setProviderCreditProject(providerCreditProjectmodifyItems);

        if (providerCreditConf.getProviderCreditProject()==null) {
            this.operateSuccessfully(response);
            return;
        }
        for (ProviderCreditProj pro : providerCreditConf.getProviderCreditProject()) {
            List<ProviderCreditConf> list = this.providerCreditConfService.checkExists(pro.getProjectno());
            if (list != null && list.size() > 0) {
                this.operateSuccessfully(response, "存在相同授信类型的失信记录\n项目号:" + pro.getProjectno());
                return;
            }
        }

        this.operateSuccessfully(response);
    }
    
    
}