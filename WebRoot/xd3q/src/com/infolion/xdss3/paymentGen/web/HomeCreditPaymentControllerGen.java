/*
 * @(#)HomeCreditPaymentControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2013年11月19日 15点22分20秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Iterator;
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
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.xdss3.payment.domain.HomeCreditPayment;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.PlickListInfo;
import com.infolion.xdss3.payment.service.HomeCreditPaymentService;
import com.infolion.xdss3.payment.service.HomePaymentService;
import com.infolion.xdss3.payment.service.ImportPaymentService;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayItem;
import com.infolion.xdss3.payment.service.HomeCreditPayItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditPayCbill;
import com.infolion.xdss3.payment.service.HomeCreditPayCbillService;
          
import com.infolion.xdss3.payment.domain.HomeCreditBankItem;
import com.infolion.xdss3.payment.service.HomeCreditBankItemService;
          
import com.infolion.xdss3.payment.domain.HomeCreditDocuBank;
import com.infolion.xdss3.payment.service.HomeCreditDocuBankService;
          
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
          
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
          
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;
          
import com.infolion.xdss3.payment.domain.HomeCreditRebank;
import com.infolion.xdss3.payment.service.HomeCreditRebankService;
          
import com.infolion.xdss3.payment.domain.HomeCreditRelatPay;
import com.infolion.xdss3.payment.service.HomeCreditRelatPayService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 国内信用证(HomeCreditPayment)控制器类
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
public class HomeCreditPaymentControllerGen extends AbstractGenController
{
	private final String boName = "HomeCreditPayment";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected HomeCreditPaymentService homeCreditPaymentService;
	
	public void setHomeCreditPaymentService(HomeCreditPaymentService homeCreditPaymentService)
	{
		this.homeCreditPaymentService = homeCreditPaymentService;
	}
          
          
	@Autowired
	protected ImportPaymentService importPaymentService;

	public void setImportPaymentService(ImportPaymentService importPaymentService)
	{
		this.importPaymentService = importPaymentService;
	}     
          
          
          
          
	@Autowired
	protected AttachementService attachementService;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
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
	    HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
	    String workflowTaskId = request.getParameter("workflowTaskId");
	    String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("homeCreditPayment",homeCreditPayment);
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000370");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("xdss3/payment/homeCreditPaymentAdd");
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
		HomeCreditPayment homeCreditPayment = this.homeCreditPaymentService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("homeCreditPayment", homeCreditPayment);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000370");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/payment/homeCreditPaymentAdd");
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
		homeCreditPaymentService._delete(paymentid,getBusinessObject());
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
		String homeCreditPaymentIds = request.getParameter("homeCreditPaymentIds");
		homeCreditPaymentService._deletes(homeCreditPaymentIds,getBusinessObject());
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
		HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("paymentid");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
homeCreditPayment = this.homeCreditPaymentService._get(id);        }
        else
        {
           homeCreditPayment = this.homeCreditPaymentService._get(id);
        }
		// 押汇业务－－贸易方式:进口02，付款类型:押汇2(不进行是否纯代理判断)
		// 纯代理业务就不删除押汇银行
        boolean isP = "02".equals(homeCreditPayment.getTrade_type()) 
                && "2".equals(homeCreditPayment.getPay_type());
        
		if("押汇付款申请".equals(homeCreditPayment.getProcessstate())){
			if(!homeCreditPayment.getHomeCreditDocuBank().isEmpty()){
				BigDecimal docuAmount = new BigDecimal(0);
				for(Iterator it = homeCreditPayment.getHomeCreditDocuBank().iterator(); it.hasNext();){
					ImportDocuBankItem idi = (ImportDocuBankItem)it.next();
					docuAmount = docuAmount.add(idi.getDocuarypayamount());				// 计算总押汇金额
				}
				homeCreditPayment.setFactamount(docuAmount);					// 设置实际付款金额
			}
			String bankIndex = "";
			homeCreditPayment.setFactcurrency(homeCreditPayment.getCurrency());		// 将币别写入实际付款币别
			homeCreditPayment.getHomeCreditBankItem().clear();				// 清空"付款银行行项"
			if (  isP ) { // 押汇业务，保留押汇银行
                bankIndex = "2";
			} else { // 非押汇业务，删除押汇银行
				homeCreditPayment.getHomeCreditDocuBank().clear();              // 清空"押汇银行行项"
			}
			homeCreditPayment.setSettleSubject(null);						// 清空"付款结算科目"
			homeCreditPayment.setFundFlow(null);						// 清空"付款纯资金"
			homeCreditPayment.setVoucherdate(" ");
			homeCreditPayment.setAccountdate(" ");
			this.importPaymentService.deleteDocuInfo(homeCreditPayment.getPaymentid(), bankIndex);
			this.homeCreditPaymentService.updateHomeCreditPayment(homeCreditPayment);
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
		
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000370");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		
        if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
        
		request.setAttribute("homeCreditPayment", homeCreditPayment);  
		return new ModelAndView("xdss3/payment/homeCreditPaymentView");
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
		HomeCreditPayment homeCreditPayment = (HomeCreditPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeCreditPayment.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditPayItem> homeCreditPayItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
Set<HomeCreditPayItem> deletedHomeCreditPayItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
homeCreditPayment.setHomeCreditPayItem(homeCreditPayItemmodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditPayCbill> homeCreditPayCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
Set<HomeCreditPayCbill> deletedHomeCreditPayCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
homeCreditPayment.setHomeCreditPayCbill(homeCreditPayCbillmodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditBankItem> homeCreditBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
Set<HomeCreditBankItem> deletedHomeCreditBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
homeCreditPayment.setHomeCreditBankItem(homeCreditBankItemmodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditDocuBank> homeCreditDocuBankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
Set<HomeCreditDocuBank> deletedHomeCreditDocuBankSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
homeCreditPayment.setHomeCreditDocuBank(homeCreditDocuBankmodifyItems);
//绑定子对象(一对一关系)
SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
if(settleSubject!=null)
{
homeCreditPayment.setSettleSubject(settleSubject);
settleSubject.setHomeCreditPayment(homeCreditPayment);
}
//绑定子对象(一对一关系)
FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
if(fundFlow!=null)
{
homeCreditPayment.setFundFlow(fundFlow);
fundFlow.setHomeCreditPayment(homeCreditPayment);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditRebank> homeCreditRebankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
Set<HomeCreditRebank> deletedHomeCreditRebankSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
homeCreditPayment.setHomeCreditRebank(homeCreditRebankmodifyItems);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditRelatPay> homeCreditRelatPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
Set<HomeCreditRelatPay> deletedHomeCreditRelatPaySet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
homeCreditPayment.setHomeCreditRelatPay(homeCreditRelatPaymodifyItems);
//取得业务附件，业务ID
Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
if (!"view".equalsIgnoreCase(type))
{
this.homeCreditPaymentService._saveOrUpdate(homeCreditPayment
,deletedHomeCreditPayItemSet
,deletedHomeCreditPayCbillSet
,deletedHomeCreditBankItemSet
,deletedHomeCreditDocuBankSet
,deletedHomeCreditRebankSet
,deletedHomeCreditRelatPaySet
,attachements
,getBusinessObject());
}
//如果是押汇付款申请节点。业务只能修改还押汇金额，ZZH 20130927
if ("view".equalsIgnoreCase(type) && "押汇付款申请".equals(homeCreditPayment.getProcessstate()) ){
	HomeCreditPayment ip2 = this.homeCreditPaymentService._get(homeCreditPayment.getPaymentid());
	ip2.setRedocaryamount(homeCreditPayment.getRedocaryamount());
	this.homeCreditPaymentService._saveOrUpdate(ip2, null, null, null, null,null, null,null, getBusinessObject());
}
HomeCreditPayment hjj = this.homeCreditPaymentService._get(homeCreditPayment.getPaymentid());
String workflowBusinessNote = "";
String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
String payType1 = "";	// 付款方式
String payType2 = "";	// 付款类型
String pickList = "";   // 到单号
Set<HomeCreditPayItem> ipItems = homeCreditPayment.getHomeCreditPayItem();
if(ipItems.iterator().hasNext()){
	HomeCreditPayItem ipItem = ipItems.iterator().next();
    if(StringUtils.isNotBlank(ipItem.getPick_list_no())){
        PlickListInfo pli = this.importPaymentService.getPlickListInfoByno(ipItem.getPick_list_no());
        pickList = "-到单号" + pli.getPlickListNo();
    }
}
payType1 = this.importPaymentService.getDomainText("YDPAYTRADETYPE", homeCreditPayment.getPaymenttype());
payType2 = this.importPaymentService.getDomainText("YDPAYTYPE", homeCreditPayment.getPay_type());
workflowBusinessNote = homeCreditPayment.getPaymentno() + "|" + deptName + "|" + creator + 
                       pickList + "|" + payType2 + 
					   "|金额" + homeCreditPayment.getApplyamount() + "|付款日:" + homeCreditPayment.getPaydate();
homeCreditPayment.setWorkflowBusinessNote(workflowBusinessNote);

this.homeCreditPaymentService._submitProcess(homeCreditPayment
,deletedHomeCreditPayItemSet
,deletedHomeCreditPayCbillSet
,deletedHomeCreditBankItemSet
,deletedHomeCreditDocuBankSet
,deletedHomeCreditRebankSet
,deletedHomeCreditRelatPaySet		,getBusinessObject());
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
		return new ModelAndView("xdss3/payment/homeCreditPaymentManage");
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
		HomeCreditPayment homeCreditPayment  = new HomeCreditPayment();
		String paymentid = request.getParameter("paymentid");
		homeCreditPayment.setPaymentid(paymentid);
		LockService.unLockBOData(homeCreditPayment);
		this.operateSuccessfullyHiddenInfo(response);
	}
    
	/**
	 * 分配  
	 *   
	 * @param request
	 * @param response
	 */
	public void _assign (HttpServletRequest request, HttpServletResponse response)
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
		HomeCreditPayment homeCreditPayment = (HomeCreditPayment) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), HomeCreditPayment.class , true , request.getMethod(), true);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditPayItem> homeCreditPayItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
homeCreditPayment.setHomeCreditPayItem(homeCreditPayItemmodifyItems);
/***判断行项目立项信息是否有配置客户授信类型为代垫的，如有，则代垫到期日必须填写*/
for(HomeCreditPayItem item : homeCreditPayItemmodifyItems){
	if(homePaymentService.isValidCustCreditPro(item.getProject_no())&&StringUtils.isEmpty(homeCreditPayment.getReplacedate().trim()))
		throw new BusinessException("代垫到期日必须填写!");
}
Set<HomeCreditPayItem> homeCreditPayItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayItem.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditPayCbill> homeCreditPayCbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
homeCreditPayment.setHomeCreditPayCbill(homeCreditPayCbillmodifyItems);
Set<HomeCreditPayCbill> homeCreditPayCbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditPayCbill.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditBankItem> homeCreditBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
homeCreditPayment.setHomeCreditBankItem(homeCreditBankItemmodifyItems);
Set<HomeCreditBankItem> homeCreditBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditBankItem.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditDocuBank> homeCreditDocuBankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
homeCreditPayment.setHomeCreditDocuBank(homeCreditDocuBankmodifyItems);
Set<HomeCreditDocuBank> homeCreditDocuBankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditDocuBank.class, null);
//绑定子对象(一对一关系)
SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false , request.getMethod(), true);
if(settleSubject!=null)
{
homeCreditPayment.setSettleSubject(settleSubject);
settleSubject.setHomeCreditPayment(homeCreditPayment);
}
//绑定子对象(一对一关系)
FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false , request.getMethod(), true);
if(fundFlow!=null)
{
homeCreditPayment.setFundFlow(fundFlow);
fundFlow.setHomeCreditPayment(homeCreditPayment);
}
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditRebank> homeCreditRebankmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
homeCreditPayment.setHomeCreditRebank(homeCreditRebankmodifyItems);
Set<HomeCreditRebank> homeCreditRebankdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRebank.class, null);
// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
Set<HomeCreditRelatPay> homeCreditRelatPaymodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
homeCreditPayment.setHomeCreditRelatPay(homeCreditRelatPaymodifyItems);
Set<HomeCreditRelatPay> homeCreditRelatPaydeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { homeCreditPayment }, HomeCreditRelatPay.class, null);
//取得业务附件，业务ID
Set<Attachement> attachements = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, Attachement.class, null);
this.homeCreditPaymentService._saveOrUpdate(homeCreditPayment
,homeCreditPayItemdeleteItems
,homeCreditPayCbilldeleteItems
,homeCreditBankItemdeleteItems
,homeCreditDocuBankdeleteItems
,homeCreditRebankdeleteItems
,homeCreditRelatPaydeleteItems
,attachements,getBusinessObject());

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
				calActivity.setBusid(homeCreditPayment.getPaymentid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
	    	jo.put("paymentno", homeCreditPayment.getPaymentno());
		jo.put("paymentid", homeCreditPayment.getPaymentid());
jo.put("settlesubjectid", homeCreditPayment.getSettleSubject()!=null ? homeCreditPayment.getSettleSubject().getSettlesubjectid() : "");
jo.put("fundflowid", homeCreditPayment.getFundFlow()!=null ? homeCreditPayment.getFundFlow().getFundflowid() : "");String creator = homeCreditPayment.getCreator();
String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
jo.put("creator_text", creator_text);
jo.put("creator", creator);
jo.put("createtime", homeCreditPayment.getCreatetime());
String lastmodifyer = homeCreditPayment.getLastmodifyer();
String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
jo.put("lastmodifyer_text", lastmodifyer_text);
jo.put("lastmodifyer", lastmodifyer);
jo.put("lastmodifytime", homeCreditPayment.getLastmodifytime());		this.operateSuccessfullyWithString(response,jo.toString());
	   }
	}
    
	/**
	 * 附加空行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _appendLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 插入行  
	 *   
	 * @param request
	 * @param response
	 */
	public void _insertLine (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加同级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 增加下级节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _addSubNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 删除节点  
	 *   
	 * @param request
	 * @param response
	 */
	public void _deleteNode (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChart (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 图表明细查询  
	 *   
	 * @param request
	 * @param response
	 */
	public void _queryChartDetail (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 下载  
	 *   
	 * @param request
	 * @param response
	 */
	public void _download (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 上传  
	 *   
	 * @param request
	 * @param response
	 */
	public void _upload (HttpServletRequest request, HttpServletResponse response)
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
		HomeCreditPayment homeCreditPayment = new HomeCreditPayment();
		String id = request.getParameter("paymentid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
homeCreditPayment = this.homeCreditPaymentService._getForEdit(id);        }
        else
        {
           homeCreditPayment = this.homeCreditPaymentService._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000370");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);	
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("homeCreditPayment", homeCreditPayment);
		return new ModelAndView("xdss3/payment/homeCreditPaymentEdit");
	}
    
	/**
	 * 批量解锁  
	 *   
	 * @param request
	 * @param response
	 */
	public void _unlock (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 锁定  
	 *   
	 * @param request
	 * @param response
	 */
	public void _locked (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 业务编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public void _edityw (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 查看凭证  
	 *   
	 * @param request
	 * @param response
	 */
	public void _viewVoucher (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 查看流程状态  
	 *   
	 * @param request
	 * @param response
	 */
	public void _viewProcessState (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 模拟凭证  
	 *   
	 * @param request
	 * @param response
	 */
	public void _simulate (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 查看信用额度  
	 *   
	 * @param request
	 * @param response
	 */
	public void _viewCredit (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    
	/**
	 * 现金日记帐  
	 *   
	 * @param request
	 * @param response
	 */
	public void _bookofaccount (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
}