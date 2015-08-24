/*
 * @(#)CustomerRefundmentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月14日 15点34分35秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawbackGen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
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
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;

import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.service.CustomerDbBankItemService;

import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.service.CustomerClearAccountService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 客户退款(CustomerRefundment)控制器类
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
public class CustomerRefundmentControllerGen extends AbstractGenController {
	private final String boName = "CustomerRefundment";

	public String getBoName() {
		return this.boName;
	}

	@Autowired
	protected CustomerRefundmentService customerRefundmentService;

	public void setCustomerRefundmentService(
			CustomerRefundmentService customerRefundmentService) {
		this.customerRefundmentService = customerRefundmentService;
	}

	@Autowired
	protected AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}
	
	@Autowired
	protected ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}
	
	@Autowired
	private CustomerClearAccountService customerClearAccountService;
	

	/**
	 * @param customerClearAccountService the customerClearAccountService to set
	 */
	public void setCustomerClearAccountService(
			CustomerClearAccountService customerClearAccountService) {
		this.customerClearAccountService = customerClearAccountService;
	}

	/**
	 * 预览凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _preview(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request,
			HttpServletResponse response) {
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("customerRefundment", customerRefundment);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000291");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentAdd");
	}

	/**
	 * 复制创建
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request,
			HttpServletResponse response) {
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
		String id = request.getParameter("refundmentid");
		CustomerRefundment customerRefundment = this.customerRefundmentService
				._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("customerRefundment", customerRefundment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000291");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response) {
		String refundmentid = request.getParameter("refundmentid");
		customerRefundmentService._delete(refundmentid, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request,
			HttpServletResponse response) {
		String customerRefundmentIds = request
				.getParameter("customerRefundmentIds");
		customerRefundmentService._deletes(customerRefundmentIds,
				getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request,
			HttpServletResponse response) {
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("refundmentid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			customerRefundment = this.customerRefundmentService._get(id);
		} else {
			customerRefundment = this.customerRefundmentService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000291");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}

		request.setAttribute("customerRefundment", customerRefundment);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentView");
	}

	/**
	 * 提交
	 * 
	 * @param request
	 * @param response
	 */

	public void _submitProcess(HttpServletRequest request,
			HttpServletResponse response) {
		// 绑定主对象值
		CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						CustomerRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		Set<CustomerDbBankItem> deletedCustomerDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		Set<CustomerRefundItem> deletedCustomerRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			customerRefundment.setSettleSubject(settleSubject);
			settleSubject.setCustomerRefundment(customerRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			customerRefundment.setFundFlow(fundFlow);
			fundFlow.setCustomerRefundment(customerRefundment);
		}
//取得业务附件，业务ID
Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		if (!"view".equalsIgnoreCase(type)) {
			this.customerRefundmentService._saveOrUpdate(customerRefundment,
					deletedCustomerDbBankItemSet, deletedCustomerRefundItemSet,attachements,
					getBusinessObject());
		}
		this.customerRefundmentService._submitProcess(customerRefundment,
				deletedCustomerDbBankItemSet, deletedCustomerRefundItemSet,
				getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("vt", getBusinessObject().getViewText());
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this
				.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		return new ModelAndView(
				"xdss3/customerDrawback/customerRefundmentManage");
	}

	/**
	 * 取消后的解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _cancel(HttpServletRequest request, HttpServletResponse response) {
		// 绑定主对象值
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String refundmentid = request.getParameter("refundmentid");
		customerRefundment.setRefundmentid(refundmentid);
		LockService.unLockBOData(customerRefundment);
		this.operateSuccessfullyHiddenInfo(response);
	}

	/**
	 * 分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _assign(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		CustomerRefundment customerRefundment = (CustomerRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						CustomerRefundment.class, true, request.getMethod(),
						true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerDbBankItem> customerDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		customerRefundment.setCustomerDbBankItem(customerDbBankItemmodifyItems);
		Set<CustomerDbBankItem> customerDbBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerDbBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CustomerRefundItem> customerRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		customerRefundment.setCustomerRefundItem(customerRefundItemmodifyItems);
		Set<CustomerRefundItem> customerRefundItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { customerRefundment },
						CustomerRefundItem.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			customerRefundment.setSettleSubject(settleSubject);
			settleSubject.setCustomerRefundment(customerRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			customerRefundment.setFundFlow(fundFlow);
			fundFlow.setCustomerRefundment(customerRefundment);
		}
//取得业务附件，业务ID
Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
		BigDecimal marginAmount = new BigDecimal("0");
		boolean isSave = false;
		if(!StringUtils.isNullBlank(customerRefundment.getRefundmentid()))isSave=true;
		IInfo info =customerClearAccountService.checkClearData(customerRefundment, marginAmount,isSave);
		//判断本次数据是否正确,不正确返回
		if(!info.isRight()){
			throw new BusinessException(info.getInfo());
		}
	this.customerRefundmentService._saveOrUpdate(customerRefundment,
				customerDbBankItemdeleteItems, customerRefundItemdeleteItems,attachements,
				getBusinessObject());

		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId)) {
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils
					.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null) {
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(customerRefundment.getRefundmentid());
				calActivityService._update2(calActivity, BusinessObjectService
						.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		} else {
			jo.put("refundmentno", customerRefundment.getRefundmentno());
			jo.put("refundmentid", customerRefundment.getRefundmentid());
			String creator = customerRefundment.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
					creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo
					.put("settlesubjectid", customerRefundment
							.getSettleSubject() != null ? customerRefundment
							.getSettleSubject().getSettlesubjectid() : "");
			jo
					.put(
							"fundflowid",
							customerRefundment.getFundFlow() != null ? customerRefundment
									.getFundFlow().getFundflowid()
									: "");
			jo.put("createtime", customerRefundment.getCreatetime());
			jo.put("lastmodifytime", customerRefundment.getLastmodifytime());
			this.operateSuccessfullyWithString(response, jo.toString());
		}
	}

	/**
	 * 附加空行
	 * 
	 * @param request
	 * @param response
	 */
	public void _appendLine(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 插入行
	 * 
	 * @param request
	 * @param response
	 */
	public void _insertLine(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 增加同级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addNode(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 增加下级节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _addSubNode(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 删除节点
	 * 
	 * @param request
	 * @param response
	 */
	public void _deleteNode(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 图表查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChart(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 图表明细查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 */
	public void _download(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 上传
	 * 
	 * @param request
	 * @param response
	 */
	public void _upload(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request,
			HttpServletResponse response) {
		CustomerRefundment customerRefundment = new CustomerRefundment();
		String id = request.getParameter("refundmentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		} else {
			customerRefundment = this.customerRefundmentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000291");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject()) {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
				request.setAttribute(bo.getBeanAttribute()
						.getBeanInstanceName()
						+ "Vt", bo.getViewText());
			}
		}
		request.setAttribute("customerRefundment", customerRefundment);
		return new ModelAndView("xdss3/customerDrawback/customerRefundmentEdit");
	}

	/**
	 * 批量解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _unlock(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 锁定
	 * 
	 * @param request
	 * @param response
	 */
	public void _locked(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 重分配复制
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}
}