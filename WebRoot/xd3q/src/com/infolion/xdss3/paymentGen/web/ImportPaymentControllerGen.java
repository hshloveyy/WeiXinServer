/*
 * @(#)ImportPaymentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分17秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.web;

import java.math.BigDecimal;
import java.util.Iterator;
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
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.ImportRelatPayment;
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.payment.domain.PlickListInfo;
import com.infolion.xdss3.payment.service.ImportPaymentService;

/**
 * <pre>
 * 进口付款(ImportPayment)控制器类
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
public class ImportPaymentControllerGen extends AbstractGenController
{
	private final String boName = "ImportPayment";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected ImportPaymentService importPaymentService;

	@Autowired
	private CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;

	public void setCheckCustomerSendCreditSerice(CheckCustomerSendCreditSerice checkCustomerSendCreditSerice)
	{
		this.checkCustomerSendCreditSerice = checkCustomerSendCreditSerice;
	}
	
	public void setImportPaymentService(ImportPaymentService importPaymentService)
	{
		this.importPaymentService = importPaymentService;
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
		ImportPayment importPayment = new ImportPayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			importPayment = this.importPaymentService._getForEdit(id);
		}
		else
		{
			importPayment = this.importPaymentService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
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
		request.setAttribute("importPayment", importPayment);
		return new ModelAndView("xdss3/payment/importPaymentEdit");
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
	 * 模拟凭证
	 * 
	 * @param request
	 * @param response
	 */
	public void _simulate(HttpServletRequest request, HttpServletResponse response)
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
		return new ModelAndView("xdss3/payment/importPaymentAdd");
	}

	/**
	 * 重分配查看
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _viewForReassign(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("xdss3/payment/importPaymentEdit");
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
		ImportPayment importPayment = new ImportPayment();
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
		request.setAttribute("importPayment", importPayment);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/payment/importPaymentAdd");
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
		ImportPayment importPayment = this.importPaymentService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("importPayment", importPayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/payment/importPaymentAdd");
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
		importPaymentService._delete(paymentid, getBusinessObject());
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
		String importPaymentIds = request.getParameter("importPaymentIds");
		importPaymentService._deletes(importPaymentIds, getBusinessObject());
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
		ImportPayment importPayment = new ImportPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("paymentid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			importPayment = this.importPaymentService._get(id);
		}
		else
		{
			importPayment = this.importPaymentService._get(id);
		}
		/**
		 * @修改作者：邱杰烜
		 * @修改日期：2010-10-07
		 * 若为"押汇付款申请"节点，要将银行信息与押汇银行信息都删除，然后将押汇总金额和币别带到实际付款金额与实际付款币别。
		 */
		/***********************************************************************/
		// 押汇业务－－贸易方式:进口02，付款类型:押汇2(不进行是否纯代理判断)
		// 纯代理业务就不删除押汇银行
        boolean isP = "02".equals(importPayment.getTrade_type()) 
                && "2".equals(importPayment.getPay_type());
        
		if("押汇付款申请".equals(importPayment.getProcessstate())){
			if(!importPayment.getImportDocuBankItem().isEmpty()){
				BigDecimal docuAmount = new BigDecimal(0);
				for(Iterator it = importPayment.getImportDocuBankItem().iterator(); it.hasNext();){
					ImportDocuBankItem idi = (ImportDocuBankItem)it.next();
					docuAmount = docuAmount.add(idi.getDocuarypayamount());				// 计算总押汇金额
				}
				importPayment.setFactamount(docuAmount);					// 设置实际付款金额
			}
			String bankIndex = "";
			importPayment.setFactcurrency(importPayment.getCurrency());		// 将币别写入实际付款币别
			importPayment.getImportPayBankItem().clear();				// 清空"付款银行行项"
			if (  isP ) { // 押汇业务，保留押汇银行
                bankIndex = "2";
			} else { // 非押汇业务，删除押汇银行
                importPayment.getImportDocuBankItem().clear();              // 清空"押汇银行行项"
			}
			importPayment.setImportSettSubj(null);						// 清空"付款结算科目"
			importPayment.setImportFundFlow(null);						// 清空"付款纯资金"
			importPayment.setVoucherdate(" ");
			importPayment.setAccountdate(" ");
			this.importPaymentService.deleteDocuInfo(importPayment.getPaymentid(), bankIndex);
			this.importPaymentService.updateImportPayment(importPayment);
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

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000261");
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
		request.setAttribute("importPayment", importPayment);
		return new ModelAndView("xdss3/payment/importPaymentView");
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
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		Set<ImportPaymentItem> deletedImportPaymentItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		Set<ImportPaymentCbill> deletedImportPaymentCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		Set<ImportPayBankItem> deletedImportPayBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		Set<ImportDocuBankItem> deletedImportDocuBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillPayReBankItem> billPayReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		Set<BillPayReBankItem> deletedBillPayReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		importPayment.setBillPayReBankItem(billPayReBankItemmodifyItems);
		
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		Set<ImportRelatPayment> deletedImportRelatPaymentSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);
		if (!"view".equalsIgnoreCase(type))
		{
			this.importPaymentService._saveOrUpdate(importPayment, deletedImportPaymentItemSet, deletedImportPaymentCbillSet, deletedImportPayBankItemSet, deletedImportDocuBankItemSet,deletedBillPayReBankItemSet, deletedImportRelatPaymentSet, getBusinessObject());
		}
//		如果是押汇付款申请节点。业务只能修改还押汇金额，ZZH 20130927
		if ("view".equalsIgnoreCase(type) && "押汇付款申请".equals(importPayment.getProcessstate()) ){
			ImportPayment ip2 = this.importPaymentService.getImportPaymentById(importPayment.getPaymentid());
			ip2.setRedocaryamount(importPayment.getRedocaryamount());
			this.importPaymentService._saveOrUpdate(ip2, null, null, null, null,null, null, getBusinessObject());
		}
		String businesssate =importPayment.getBusinessstate();
		boolean flag =true;
		String res ="";
		//保存提交时，检查授信
		if("0".equals(businesssate) || StringUtils.isNullBlank(businesssate)){
			String strProviderId = importPayment.getSupplier();
			String strProjectNo="";
			for(ImportPaymentItem pi : importPayment.getImportPaymentItem()){
				strProjectNo = pi.getProject_no();
				break;
			}
			BigDecimal value = importPayment.getApplyamount().multiply(importPayment.getCloseexchangerat());
			String strResult = this.checkCustomerSendCreditSerice.checkCredit(strProviderId, strProjectNo, value.floatValue());
			String andFlag[] = strResult.split("&");
			
			if ("true".equals(andFlag[1])){
				flag= true;
			}else{ 
				if ("false".equals(andFlag[0]) && "false".equals(andFlag[1])){
					flag= true;
				}else{
					res="额度超出不能提交!"+andFlag[0];
					flag= false;
				}
			}
		}
		if(flag){
			ImportPayment hjj = this.importPaymentService.getImportPaymentById(importPayment.getPaymentid());
			String workflowBusinessNote = "";
			String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
			String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
			String payType1 = "";	// 付款方式
			String payType2 = "";	// 付款类型
			String pickList = "";   // 到单号
			Set<ImportPaymentItem> ipItems = importPayment.getImportPaymentItem();
			if(ipItems.iterator().hasNext()){
			    ImportPaymentItem ipItem = ipItems.iterator().next();
			    if(StringUtils.isNotBlank(ipItem.getPick_list_no())){
			        PlickListInfo pli = this.importPaymentService.getPlickListInfoByno(ipItem.getPick_list_no());
			        pickList = "-到单号" + pli.getPlickListNo();
			    }
			}
			payType1 = this.importPaymentService.getDomainText("YDPAYTRADETYPE", importPayment.getPaymenttype());
			payType2 = this.importPaymentService.getDomainText("YDPAYTYPE", importPayment.getPay_type());
			workflowBusinessNote = importPayment.getPaymentno() + "|" + deptName + "|" + creator + 
			                       pickList + "|" + payType2 + 
								   "|金额" + importPayment.getApplyamount() + "|付款日:" + importPayment.getPaydate();
			importPayment.setWorkflowBusinessNote(workflowBusinessNote);
			this.importPaymentService._submitProcess(importPayment, deletedImportPaymentItemSet, deletedImportPaymentCbillSet, deletedImportPayBankItemSet, deletedImportDocuBankItemSet,deletedBillPayReBankItemSet, deletedImportRelatPaymentSet, getBusinessObject());
			this.operateSuccessfully(response);
		}else{
			JSONObject jo = new JSONObject();
			jo.put("info", res);
			this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());

		}
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
		return new ModelAndView("xdss3/payment/importPaymentManage");
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
		ImportPayment importPayment = new ImportPayment();
		String paymentid = request.getParameter("paymentid");
		importPayment.setPaymentid(paymentid);
		LockService.unLockBOData(importPayment);
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
		ImportPayment importPayment = (ImportPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportPayment.class, true, request.getMethod(), true);

		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentItem> importPaymentItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		importPayment.setImportPaymentItem(importPaymentItemmodifyItems);
		/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
		for(ImportPaymentItem item : importPaymentItemmodifyItems){
			if(importPaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(importPayment.getReplacedate().trim()))
				throw new BusinessException("代垫到期日必须填写!");
		}
		Set<ImportPaymentItem> importPaymentItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPaymentCbill> importPaymentCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		importPayment.setImportPaymentCbill(importPaymentCbillmodifyItems);
		Set<ImportPaymentCbill> importPaymentCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPaymentCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportPayBankItem> importPayBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		importPayment.setImportPayBankItem(importPayBankItemmodifyItems);
		Set<ImportPayBankItem> importPayBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportPayBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportDocuBankItem> importDocuBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		importPayment.setImportDocuBankItem(importDocuBankItemmodifyItems);
		Set<ImportDocuBankItem> importDocuBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportDocuBankItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<BillPayReBankItem> billPayReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		Set<BillPayReBankItem> deletedBillPayReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, BillPayReBankItem.class, null);
		importPayment.setBillPayReBankItem(billPayReBankItemmodifyItems);
		// 绑定子对象(一对一关系)
		ImportSettSubj importSettSubj = (ImportSettSubj) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportSettSubj.class, false, request.getMethod(), true);
		if (importSettSubj != null)
		{
			importPayment.setImportSettSubj(importSettSubj);
			importSettSubj.setImportPayment(importPayment);
		}
		// 绑定子对象(一对一关系)
		ImportFundFlow importFundFlow = (ImportFundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ImportFundFlow.class, false, request.getMethod(), true);
		if (importFundFlow != null)
		{
			importPayment.setImportFundFlow(importFundFlow);
			importFundFlow.setImportPayment(importPayment);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<ImportRelatPayment> importRelatPaymentmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		importPayment.setImportRelatPayment(importRelatPaymentmodifyItems);
		Set<ImportRelatPayment> importRelatPaymentdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { importPayment }, ImportRelatPayment.class, null);
		this.importPaymentService._saveOrUpdate(importPayment, importPaymentItemdeleteItems, importPaymentCbilldeleteItems, importPayBankItemdeleteItems, importDocuBankItemdeleteItems,deletedBillPayReBankItemSet, importRelatPaymentdeleteItems, getBusinessObject());

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
				calActivity.setBusid(importPayment.getPaymentid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("paymentno", importPayment.getPaymentno());
			jo.put("paymentid", importPayment.getPaymentid());
			jo.put("settlesubjectid", importPayment.getImportSettSubj() != null ? importPayment.getImportSettSubj().getSettlesubjectid() : "");
			jo.put("fundflowid", importPayment.getImportFundFlow() != null ? importPayment.getImportFundFlow().getFundflowid() : "");
			String creator = importPayment.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", importPayment.getCreatetime());
			String lastmodifyer = importPayment.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", importPayment.getLastmodifytime());
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
}