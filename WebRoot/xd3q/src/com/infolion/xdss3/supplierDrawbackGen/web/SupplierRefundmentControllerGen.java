/*
 * @(#)SupplierRefundmentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月20日 09点59分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawbackGen.web;

import javax.annotation.Resource;
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
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundmentService;

import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierDbBankItemService;

import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.service.SupplierRefundItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 供应商退款(SupplierRefundment)控制器类
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
public class SupplierRefundmentControllerGen extends AbstractGenController {
	private final String boName = "SupplierRefundment";

	public String getBoName() {
		return this.boName;
	}

	@Autowired
	protected SupplierRefundmentService supplierRefundmentService;

	public void setSupplierRefundmentService(
			SupplierRefundmentService supplierRefundmentService) {
		this.supplierRefundmentService = supplierRefundmentService;
	}
	
	@Resource
	private SupplierClearAccountService supplierClearAccountService;
	
	
	/**
	 * @param supplierClearAccountService the supplierClearAccountService to set
	 */
	public void setSupplierClearAccountService(
			SupplierClearAccountService supplierClearAccountService) {
		this.supplierClearAccountService = supplierClearAccountService;
	}

	/**
	 * 凭证预览
	 * 
	 * @param request
	 * @param response
	 */
	public void _preview(HttpServletRequest request,
			HttpServletResponse response) {

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

	/**
	 * 重分配提交
	 * 
	 * @param request
	 * @param response
	 */
	public void _submitForReassign(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request,
			HttpServletResponse response) {
		String supplierRefundmentIds = request
				.getParameter("supplierRefundmentIds");
		supplierRefundmentService._deletes(supplierRefundmentIds,
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
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("refundmentid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			supplierRefundment = this.supplierRefundmentService._get(id);
		} else {
			supplierRefundment = this.supplierRefundmentService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
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

		request.setAttribute("supplierRefundment", supplierRefundment);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentView");
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
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SupplierRefundment.class, true, request.getMethod(),
						true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		Set<SupplierDbBankItem> deletedSupplierDbBankItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		Set<SupplierRefundItem> deletedSupplierRefundItemSet = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);

		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			supplierRefundment.setSettleSubject(settleSubject);
			settleSubject.setSupplierRefundment(supplierRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			supplierRefundment.setFundFlow(fundFlow);
			fundFlow.setSupplierRefundment(supplierRefundment);
		}
		if (!"view".equalsIgnoreCase(type)) {
			this.supplierRefundmentService._saveOrUpdate(supplierRefundment,
					deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
					getBusinessObject());
		}
		this.supplierRefundmentService._submitProcess(supplierRefundment,
				deletedSupplierDbBankItemSet, deletedSupplierRefundItemSet,
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
				"xdss3/supplierDrawback/supplierRefundmentManage");
	}

	/**
	 * 取消后的解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _cancel(HttpServletRequest request, HttpServletResponse response) {
		// 绑定主对象值
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String refundmentid = request.getParameter("refundmentid");
		supplierRefundment.setRefundmentid(refundmentid);
		LockService.unLockBOData(supplierRefundment);
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
		SupplierRefundment supplierRefundment = (SupplierRefundment) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SupplierRefundment.class, true, request.getMethod(),
						true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierDbBankItem> supplierDbBankItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		supplierRefundment.setSupplierDbBankItem(supplierDbBankItemmodifyItems);
		Set<SupplierDbBankItem> supplierDbBankItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierDbBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<SupplierRefundItem> supplierRefundItemmodifyItems = ExBeanUtils
				.bindModifySubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);
		supplierRefundment.setSupplierRefundItem(supplierRefundItemmodifyItems);
		Set<SupplierRefundItem> supplierRefundItemdeleteItems = ExBeanUtils
				.bindDeleteSubBoData(request.getParameterMap(),
						new Object[] { supplierRefundment },
						SupplierRefundItem.class, null);

		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils
				.bindBusinessObjectData(request.getParameterMap(),
						SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null) {
			supplierRefundment.setSettleSubject(settleSubject);
			settleSubject.setSupplierRefundment(supplierRefundment);
		}
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
				request.getParameterMap(), FundFlow.class, false, request
						.getMethod(), true);
		if (fundFlow != null) {
			supplierRefundment.setFundFlow(fundFlow);
			fundFlow.setSupplierRefundment(supplierRefundment);
		}
		
		BigDecimal marginAmount = new BigDecimal("0");
		boolean isSave = false;
		if(!StringUtils.isNullBlank(supplierRefundment.getRefundmentid()))isSave=true;
		IInfo info =supplierClearAccountService.checkClearData(supplierRefundment, marginAmount,isSave);
		//判断本次数据是否正确,不正确返回
		if(!info.isRight()){
			throw new BusinessException(info.getInfo());
		}
		
		this.supplierRefundmentService._saveOrUpdate(supplierRefundment,
				supplierDbBankItemdeleteItems, supplierRefundItemdeleteItems,
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
				calActivity.setBusid(supplierRefundment.getRefundmentid());
				calActivityService._update2(calActivity, BusinessObjectService
						.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		} else {
			jo.put("refundmentno", supplierRefundment.getRefundmentno());
			jo.put("refundmentid", supplierRefundment.getRefundmentid());
			String creator = supplierRefundment.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",
					creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo
					.put("settlesubjectid", supplierRefundment
							.getSettleSubject() != null ? supplierRefundment
							.getSettleSubject().getSettlesubjectid() : "");
			jo
					.put(
							"fundflowid",
							supplierRefundment.getFundFlow() != null ? supplierRefundment
									.getFundFlow().getFundflowid()
									: "");
			jo.put("createtime", supplierRefundment.getCreatetime());
			jo.put("lastmodifytime", supplierRefundment.getLastmodifytime());
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
		SupplierRefundment supplierRefundment = new SupplierRefundment();
		String id = request.getParameter("refundmentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id)) {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		} else {
			supplierRefundment = this.supplierRefundmentService._getForEdit(id);
		}
		String userId = UserContextHolder.getLocalUserContext()
		.getUserContext().getSysUser().getUserId();
        request.setAttribute("userId", userId);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
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
		request.setAttribute("supplierRefundment", supplierRefundment);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentEdit");
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
		SupplierRefundment supplierRefundment = new SupplierRefundment();
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
		request.setAttribute("supplierRefundment", supplierRefundment);
		boolean haveBindWorkFlow = WorkflowService
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentAdd");
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
		SupplierRefundment supplierRefundment = this.supplierRefundmentService
				._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("supplierRefundment", supplierRefundment);
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
				.getHaveBindWorkFlow("000000000295");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/supplierDrawback/supplierRefundmentAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response) {
		String refundmentid = request.getParameter("refundmentid");
		supplierRefundmentService._delete(refundmentid, getBusinessObject());
		this.operateSuccessfully(response);
	}
}