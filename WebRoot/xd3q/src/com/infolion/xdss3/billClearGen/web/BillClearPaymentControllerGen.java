/*
 * @(#)BillClearPaymentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点42分28秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClearGen.web;

import java.math.BigDecimal;
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
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.domain.BillInPayment;
import com.infolion.xdss3.billClear.service.BillClearPaymentService;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;

/**
 * <pre>
 * 票清付款(BillClearPayment)控制器类
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
public class BillClearPaymentControllerGen extends AbstractGenController
{
	private final String boName = "BillClearPayment";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected BillClearPaymentService billClearPaymentService;

	public void setBillClearPaymentService(BillClearPaymentService billClearPaymentService)
	{
		this.billClearPaymentService = billClearPaymentService;
	}

	/**
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _voucherPreview(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 自动分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _autoAssign(HttpServletRequest request, HttpServletResponse response)
	{

	}

	/**
	 * 清除分配
	 * 
	 * @param request
	 * @param response
	 */
	public void _clearAssign(HttpServletRequest request, HttpServletResponse response)
	{

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
		BillClearPayment billClearPayment = new BillClearPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		billClearPayment.setBusinessstate("0");
		// 开始接收从_manage方法传递过来的参数。
		String cleartypeManager = request.getParameter("cleartype");
		billClearPayment.setCleartype(cleartypeManager);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		request.setAttribute("billClearPayment", billClearPayment);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000297");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/billClear/billClearPaymentAdd");
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
		String id = request.getParameter("billclearid");
		BillClearPayment billClearPayment = this.billClearPaymentService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("billClearPayment", billClearPayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000297");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/billClear/billClearPaymentAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String billclearid = request.getParameter("billclearid");
		billClearPaymentService._delete(billclearid, getBusinessObject());
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
		String billClearPaymentIds = request.getParameter("billClearPaymentIds");
		billClearPaymentService._deletes(billClearPaymentIds, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		BillClearPayment billClearPayment = new BillClearPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("billclearid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			billClearPayment = this.billClearPaymentService._get(id);
		}
		else
		{
			billClearPayment = this.billClearPaymentService._get(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000297");
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

		request.setAttribute("billClearPayment", billClearPayment);
		return new ModelAndView("xdss3/billClear/billClearPaymentView");
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
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		Set<BillClearItemPay> deletedBillClearItemPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		Set<BillInPayment> deletedBillInPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		if (!StringUtils.isNullBlank(type) && type.equalsIgnoreCase("view"))
		{
			this.billClearPaymentService._saveOrUpdate(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
		}
		this.billClearPaymentService._submitProcess(billClearPayment, deletedBillClearItemPaySet, deletedBillInPaymentSet, getBusinessObject());
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
		StringBuilder sbSqlWhere = new StringBuilder();
		String cleartype = request.getParameter("cleartype");
		request.setAttribute("cleartype", cleartype);
		if (cleartype != null && StringUtils.isNotBlank(cleartype))
		{
			sbSqlWhere.append(" YBILLCLEAR.CLEARTYPE='" + cleartype + "'");
		}
		request.setAttribute("sqlWhere", sbSqlWhere.toString());
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/billClear/billClearPaymentManage");
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
		BillClearPayment billClearPayment = new BillClearPayment();
		String billclearid = request.getParameter("billclearid");
		billClearPayment.setBillclearid(billclearid);
		LockService.unLockBOData(billClearPayment);
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
		BillClearPayment billClearPayment = (BillClearPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), BillClearPayment.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillClearItemPay> billClearItemPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		billClearPayment.setBillClearItemPay(billClearItemPaymodifyItems);
		Set<BillClearItemPay> billClearItemPaydeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillClearItemPay.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillInPayment> billInPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		billClearPayment.setBillInPayment(billInPaymentmodifyItems);
		Set<BillInPayment> billInPaymentdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { billClearPayment }, BillInPayment.class, null);
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			billClearPayment.setFundFlow(fundFlow);
			fundFlow.setBillClearPayment(billClearPayment);
		}
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			billClearPayment.setSettleSubject(settleSubject);
			settleSubject.setBillClearPayment(billClearPayment);
		}
		this.billClearPaymentService._saveOrUpdate(billClearPayment, billClearItemPaydeleteItems, billInPaymentdeleteItems, getBusinessObject());

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
				calActivity.setBusid(billClearPayment.getBillclearid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("billclearno", billClearPayment.getBillclearno());
			jo.put("billclearid", billClearPayment.getBillclearid());
			jo.put("fundflowid", billClearPayment.getFundFlow() != null ? billClearPayment.getFundFlow().getFundflowid() : "");
			jo.put("settlesubjectid", billClearPayment.getSettleSubject() != null ? billClearPayment.getSettleSubject().getSettlesubjectid() : "");
			String creator = billClearPayment.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", billClearPayment.getCreatetime());
			String lastmodifyer = billClearPayment.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", billClearPayment.getLastmodifytime());
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
		BillClearPayment billClearPayment = new BillClearPayment();
		String id = request.getParameter("billclearid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
		}
		else
		{
			billClearPayment = this.billClearPaymentService._getForEdit(id);
		}
		BigDecimal sumclearamount = new BigDecimal(0);
		BigDecimal sumadjustamount = new BigDecimal(0);
		BigDecimal sumnowclearamount = new BigDecimal(0);
		for(BillInPayment billInPayment : billClearPayment.getBillInPayment()){
			if(null !=billInPayment.getNowclearamount())
			sumnowclearamount = sumnowclearamount.add(billInPayment.getNowclearamount());
		
		}
		for(BillClearItemPay billClearItemPay : billClearPayment.getBillClearItemPay()){
			if(null != billClearItemPay.getClearbillamount())
			sumclearamount = sumclearamount.add(billClearItemPay.getClearbillamount());
			if(null != billClearItemPay.getAdjustamount())
			sumadjustamount = sumadjustamount.add(billClearItemPay.getAdjustamount());
		}
		request.setAttribute("sumclearamount", sumclearamount.toString());
		request.setAttribute("sumadjustamount", sumadjustamount.toString());
		request.setAttribute("sumnowclearamount", sumnowclearamount.toString());
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000297");
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
		request.setAttribute("billClearPayment", billClearPayment);
		return new ModelAndView("xdss3/billClear/billClearPaymentEdit");
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
}