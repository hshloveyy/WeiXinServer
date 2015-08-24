/*
 * @(#)CustomerCreditConfController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分10秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.ceditValueControlGen.web.CustomerCreditConfControllerGen;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditConfService;
/**
 * <pre>
 * 客户代垫额度和发货额度配置(CustomerCreditConf)控制器类
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
public class CustomerCreditConfController extends CustomerCreditConfControllerGen
{
	@Autowired
	private CustomerCreditConfService customerCreditConfService;
	public void setCustomerCreditConfService(CustomerCreditConfService customerCreditConfService)
	{
		this.customerCreditConfService = customerCreditConfService;
	}
	
	
	public ModelAndView _prepaymentManage(HttpServletRequest req,HttpServletResponse resp)
	{		
        String userId = UserContextHolder.getLocalUserContext()
                .getUserContext().getSysUser().getDeptUserId();
        req.setAttribute("userId", userId);
		return new ModelAndView("xdss3/ceditValueControl/customerPrepaymentEdit");
	}
	/**
	 * 更新客户代垫或放货额度
	 * @param req
	 * @param resp
	 * @param tc
	 * @throws Exception
	 */
	public void updateCredit(HttpServletRequest req,HttpServletResponse resp) throws Exception
	{
		String customerid = req.getParameter("customerid");
		String projectid = req.getParameter("projectid");
		// 授信类型
		String valueType = req.getParameter("valueType");
		// 代垫额度
		double prepayValue = Double.parseDouble(req.getParameter("prepayValue"));
		// 放货额度
		double sendValue   = Double.parseDouble(req.getParameter("sendValue"));
		// 备注
		String remark = req.getParameter("remark");
		
		resp.setCharacterEncoding("GBK");

		int ret =
			this.customerCreditConfService.updateCredit(customerid, projectid, valueType, prepayValue, sendValue, remark);			
			
		this.operateSuccessfully(resp);
		
	}
	
    public void checkExists(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 绑定主对象值
        CustomerCreditConf customerCreditConf = (CustomerCreditConf) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), CustomerCreditConf.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<CustomerCreditProj> customerCreditProjectmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { customerCreditConf }, CustomerCreditProj.class, null);
        customerCreditConf.setCustomerCreditProject(customerCreditProjectmodifyItems);
        if (customerCreditConf.getCustomerCreditProject()==null) {
            this.operateSuccessfully(response);
            return;
        }
        for (CustomerCreditProj pro : customerCreditConf.getCustomerCreditProject()) {
            List<CustomerCreditConf> list = this.customerCreditConfService.checkExists(
                    customerCreditConf.getCredittype(), pro.getProjectno());
            if (list != null && list.size() > 0) {
                this.operateSuccessfully(response, "存在相同授信类型的失信记录\n项目号:" + pro.getProjectno());
                return;
            }
        }

        this.operateSuccessfully(response);
    }
}