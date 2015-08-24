/*
 * @(#)HomePaymentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomePaymentRelat;
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.service.HomePaymentService;

/**
 * <pre>
 * 内贸付款(HomePayment)控制器类
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
public class HomePaymentControllerGen extends AbstractGenController
{
	private final String boName = "HomePayment";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected HomePaymentService homePaymentService;

	public void setHomePaymentService(HomePaymentService homePaymentService)
	{
		this.homePaymentService = homePaymentService;
	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		HomePayment homePayment = new HomePayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("homePayment", homePayment);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000315");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/payment/homePaymentAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		String id = request.getParameter("paymentid");
		HomePayment homePayment = this.homePaymentService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("homePayment", homePayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000315");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/payment/homePaymentAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String paymentid = request.getParameter("paymentid");
		homePaymentService._delete(paymentid, getBusinessObject());
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
		String homePaymentIds = request.getParameter("homePaymentIds");
		homePaymentService._deletes(homePaymentIds, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 查看
	 * ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-13 在点击查看时，返回当前办理人员的职称（用于控制打印按钮，若为业务人员则不能显示打印按钮）
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		HomePayment homePayment = new HomePayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("paymentid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			homePayment = this.homePaymentService._get(id);
		}
		else
		{
			homePayment = this.homePaymentService._get(id);
		}
		
		/***********************************************************************/ 
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strRoleType = "";
		for (int i = 0; i < userContext.getGrantedRoles().size(); i++)
		{
			SysRole sysRole = (SysRole) userContext.getGrantedRoles().get(i);

			if ("业务员".indexOf(sysRole.getRoleName()) != -1)
			{
				strRoleType = "1";
			}
		}
		request.setAttribute("roletype", strRoleType);
		/***********************************************************************/ 
		
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000315");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
		request.setAttribute("homePayment", homePayment);
		return new ModelAndView("xdss3/payment/homePaymentView");
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
		HomePayment homePayment = (HomePayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomePayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentItem.class, null);
		Set<HomePaymentItem> deletedHomePaymentItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentItem.class, null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentCbill.class, null);
		Set<HomePaymentCbill> deletedHomePaymentCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentCbill.class, null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePayBankItem.class, null);
		Set<HomePayBankItem> deletedHomePayBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePayBankItem.class, null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomeDocuBankItem.class, null);
		Set<HomeDocuBankItem> deletedHomeDocuBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomeDocuBankItem.class, null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null)
		{
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null)
		{
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentRelat.class, null);
		Set<HomePaymentRelat> deletedHomePaymentRelatSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentRelat.class, null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		if (!"view".equalsIgnoreCase(type))
		{
			this.homePaymentService._saveOrUpdate(homePayment, deletedHomePaymentItemSet, deletedHomePaymentCbillSet, deletedHomePayBankItemSet, deletedHomeDocuBankItemSet, deletedHomePaymentRelatSet, getBusinessObject());
		}
		
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		workflowBusinessNote = "付款单号:" + homePayment.getPaymentno() + "|部门" + deptName + "|申请人:" + creator + "|付款方式" + homePayment.getPaymenttype()
		 + "|申请金额" + homePayment.getApplyamount() ;
		homePayment.setWorkflowBusinessNote(workflowBusinessNote);
		this.homePaymentService._submitProcess(homePayment, deletedHomePaymentItemSet, deletedHomePaymentCbillSet, deletedHomePayBankItemSet, deletedHomeDocuBankItemSet, deletedHomePaymentRelatSet, getBusinessObject());
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
		return new ModelAndView("xdss3/payment/homePaymentManage");
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
		HomePayment homePayment = new HomePayment();
		String paymentid = request.getParameter("paymentid");
		homePayment.setPaymentid(paymentid);
		LockService.unLockBOData(homePayment);
		this.operateSuccessfullyHiddenInfo(response);
	}

	/**
	 * 分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _assign(HttpServletRequest request, HttpServletResponse response)
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
		HomePayment homePayment = (HomePayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomePayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentItem> homePaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentItem.class, null);
		homePayment.setHomePaymentItem(homePaymentItemmodifyItems);
		/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
		for(HomePaymentItem item : homePaymentItemmodifyItems){
			if(homePaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(homePayment.getReplacedate().trim()))
				throw new BusinessException("代垫到期日必须填写!");
		}
		Set<HomePaymentItem> homePaymentItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentCbill> homePaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentCbill.class, null);
		homePayment.setHomePaymentCbill(homePaymentCbillmodifyItems);
		Set<HomePaymentCbill> homePaymentCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePayBankItem> homePayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePayBankItem.class, null);
		homePayment.setHomePayBankItem(homePayBankItemmodifyItems);
		Set<HomePayBankItem> homePayBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePayBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomeDocuBankItem> homeDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomeDocuBankItem.class, null);
		homePayment.setHomeDocuBankItem(homeDocuBankItemmodifyItems);
		Set<HomeDocuBankItem> homeDocuBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomeDocuBankItem.class, null);
		// 绑定子对象(一对一关系)
		HomeSettSubj homeSettSubj = (HomeSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeSettSubj.class, false, request.getMethod(), true);
		if (homeSettSubj != null)
		{
			homePayment.setHomeSettSubj(homeSettSubj);
			homeSettSubj.setHomePayment(homePayment);
		}
		// 绑定子对象(一对一关系)
		HomeFundFlow homeFundFlow = (HomeFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeFundFlow.class, false, request.getMethod(), true);
		if (homeFundFlow != null)
		{
			homePayment.setHomeFundFlow(homeFundFlow);
			homeFundFlow.setHomePayment(homePayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<HomePaymentRelat> homePaymentRelatmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentRelat.class, null);
		homePayment.setHomePaymentRelat(homePaymentRelatmodifyItems);
		Set<HomePaymentRelat> homePaymentRelatdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homePayment }, HomePaymentRelat.class, null);
		this.homePaymentService._saveOrUpdate(homePayment, homePaymentItemdeleteItems, homePaymentCbilldeleteItems, homePayBankItemdeleteItems, homeDocuBankItemdeleteItems, homePaymentRelatdeleteItems, getBusinessObject());

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
		{
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null)
			{
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(homePayment.getPaymentid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("paymentno", homePayment.getPaymentno());
			jo.put("paymentid", homePayment.getPaymentid());
			jo.put("settlesubjectid", homePayment.getHomeSettSubj() != null ? homePayment.getHomeSettSubj().getSettlesubjectid() : "");
			jo.put("fundflowid", homePayment.getHomeFundFlow() != null ? homePayment.getHomeFundFlow().getFundflowid() : "");
			String creator = homePayment.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", homePayment.getCreatetime());
			String lastmodifyer = homePayment.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", homePayment.getLastmodifytime());
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}

	/**
	 * 附加空行
	 * 
	 * @param request
	 * @param response
	 */
	public void _appendLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 插入行
	 * 
	 * @param request
	 * @param response
	 */
	public void _insertLine(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加同级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 增加下级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addSubNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 删除节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _deleteNode(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChart(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 图表明细查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 */
	public void _download(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 */
	public void _upload(HttpServletRequest request, HttpServletResponse response)
	{

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
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		HomePayment homePayment = new HomePayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			homePayment = this.homePaymentService._getForEdit(id);
		}
		else
		{
			homePayment = this.homePaymentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000315");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("homePayment", homePayment);
		return new ModelAndView("xdss3/payment/homePaymentEdit");
	}

	/**
	 * 批量解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _unlock(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 锁定
	 * 
	 * @param request
	 * @param response
	 */
	public void _locked(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 重分配复制
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyForReassign(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("xdss3/payment/homePaymentAdd");
	}

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("xdss3/payment/homePaymentEdit");
	}

	/**
	 * 重分配提交
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitForReassign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _simulate(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _autoassign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 清除分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _clearassign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 现金日记帐
	 * 
	 * @param request
	 * @param response
	 */
	public void _bookofaccount(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 查看信用额度
	 * 
	 * @param request
	 * @param response
	 */
	public void _viewCredit(HttpServletRequest request, HttpServletResponse response)
	{

	}
}