/*
 * @(#)CollectControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分22秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectGen.web;

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
import com.infolion.xdss3.CommonDataAuth;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;

/**
 * <pre>
 * 收款(Collect)控制器类
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
public class CollectControllerGen extends AbstractGenController
{
	private final String boName = "Collect";

	public String getBoName()
	{
		return this.boName;
	}

	@Autowired
	protected CollectService collectService;

	public void setCollectService(CollectService collectService)
	{
		this.collectService = collectService;
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
		Collect collect = new Collect();
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
		request.setAttribute("collect", collect);
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000258");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		return new ModelAndView("xdss3/collect/collectAdd");
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
		String id = request.getParameter("collectid");
		Collect collect = this.collectService._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
		request.setAttribute("collect", collect);
		request.setAttribute("vt", getBusinessObject().getViewText());
		if (null != getBusinessObject().getSubBusinessObject())
		{
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
			}
		}
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000258");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("xdss3/collect/collectAdd");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String collectid = request.getParameter("collectid");
		collectService._delete(collectid, getBusinessObject());
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
		String collectIds = request.getParameter("collectIds");
		collectService._deletes(collectIds, getBusinessObject());
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
		Collect collect = new Collect();
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("collectid");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			collect = this.collectService._get(id);
		}
		else
		{
			collect = this.collectService._get(id);
		}
		/**
		 * @修改作者：邱杰烜
		 * @修改日期：2010-10-08
		 * 若为"资金部确认票据"节点，将结算科目与纯资金项删除
		 */
		/***********************************************************************/ 
		if("资金部确认票据".equals(collect.getProcessstate())||"确认票据".equals(collect.getProcessstate())){
			collect.setSettleSubject(null);	// 清空"结算科目"
			collect.setFundFlow(null);		// 清空"纯资金"
			this.collectService.deleteSettleFund(collect.getCollectid());
		}
		/***********************************************************************/ 
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000258");
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
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		if(userContext.getSysUser().getDeptId().equals("E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D")){
			request.setAttribute("saveDate", true);
		}
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectView");
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
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Collect.class, true, request.getMethod(), true);
		// 类型标识是从那个页面提交，view 表示从view页面提交流程。
		String type = request.getParameter("type");
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectItem.class, null);
		Set<CollectItem> deletedCollectItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectCbill.class, null);
		Set<CollectCbill> deletedCollectCbillSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectRelated.class, null);
		Set<CollectRelated> deletedCollectRelatedSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
		    if(null==settleSubject.getFlag() || "".equals(settleSubject.getFlag().trim())){
                settleSubject.setFlag("0");
            }
		    collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
		    if(null==fundFlow.getFlag() || "".equals(fundFlow.getFlag().trim())){
                fundFlow.setFlag("0");
            }
		    collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectBankItem.class, null);
		Set<CollectBankItem> deletedCollectBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		if (!"view".equalsIgnoreCase(type))
		{
//			检查单据号码是否有重复，有重复不能提交
			if(StringUtils.isNotBlank(collect.getDraft())){
				Collect co =this.collectService.getCollectBydraft(collect.getDraft().trim(), collect.getCollectid());
				if(null != co){
					throw new BusinessException("单据号码已经存在！请重新填写，可在号码后加A来区别以前的号码！");
				}
			}
			
			if (collect.getProcessstate() != null)
			{
				if (("财务会计审核票据并做帐").equals(collect.getProcessstate()))
				{
					collect.setSettleSubject(null);
					collect.setFundFlow(null);
				}
			}

			// TODO LJX 20100901 DEBUG
			log.debug("collectbankitemmodifyItems:" + collectbankitemmodifyItems != null ? collectbankitemmodifyItems.size() : "0");
			log.debug("collectbankitemdeleteItems:" + deletedCollectBankItemSet != null ? deletedCollectBankItemSet.size() : "0");
			
			// 邱杰烜 2011-05-25 下一个节点要做贴现或议付凭证，为了防止“结算科目”和“纯资金往来”重复生成的问题，在些预先生成一条数据
			if(("资金部出纳确认").equals(collect.getProcessstate())){
			    if(null == collect.getSettleSubject()){
			        SettleSubject ss = new SettleSubject();
			        ss.setAmount1(BigDecimal.ZERO);
			        ss.setAmount2(BigDecimal.ZERO);
			        ss.setAmount3(BigDecimal.ZERO);
			        ss.setAmount4(BigDecimal.ZERO);
			        ss.setStandardamount1(BigDecimal.ZERO);
			        ss.setStandardamount2(BigDecimal.ZERO);
			        ss.setStandardamount3(BigDecimal.ZERO);
			        ss.setStandardamount4(BigDecimal.ZERO);
			        ss.setFlag("0");
			        collect.setSettleSubject(ss);
			        ss.setCollect(collect);
			    }
			    if(null == collect.getFundFlow()){
			        FundFlow ff = new FundFlow();
			        ff.setAmount1(BigDecimal.ZERO);
			        ff.setAmount2(BigDecimal.ZERO);
			        ff.setAmount3(BigDecimal.ZERO);
			        ff.setStandardamount1(BigDecimal.ZERO);
                    ff.setStandardamount2(BigDecimal.ZERO);
                    ff.setStandardamount3(BigDecimal.ZERO);
			        ff.setFlag("0");
			        collect.setFundFlow(ff);
			        ff.setCollect(collect);
			    }
			}
			
			String processState = collect.getProcessstate();
			String leaveTransct = collect.getWorkflowLeaveTransitionName();
			if(!("财务会计审核贴现或议付并做帐".equals(processState) && ("确认贴现或议付已做帐".equals(leaveTransct) || "部分议付返回资金部".equals(leaveTransct)))){
			    this.collectService._saveOrUpdate(collect, deletedCollectItemSet, deletedCollectCbillSet, deletedCollectRelatedSet, deletedCollectBankItemSet, getBusinessObject());
			}else{
			    this.collectService.updateDisNegVoucherState(collect.getCollectid());
			}

		}
		
		String workflowBusinessNote = "";
		String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
		String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
		// 邱杰烜 2010-10-11 添加银行信息进待办信息
		String bankAccount = "";
		String bankName = "";
		if(collect.getCollectbankitem().iterator().hasNext()){
			bankAccount = collect.getCollectbankitem().iterator().next().getCollectbankacc();
			bankName = this.collectService.getBankInfoByAccount(bankAccount);
			workflowBusinessNote = collect.getCollectno() + "|" + deptName + "|" + creator + "|"+collect.getCurrency()+"|金额" + collect.getApplyamount() + "|" + bankName;
		}else{
			workflowBusinessNote = collect.getCollectno() + "|" + deptName + "|" + creator + "|"+collect.getCurrency()+"|金额" + collect.getApplyamount();
		}
		if("12".equals(collect.getCollecttype())){
			workflowBusinessNote+="|单号:"+collect.getDraft();
		}
		collect.setWorkflowBusinessNote(workflowBusinessNote);
		this.collectService._submitProcess(collect, deletedCollectItemSet, deletedCollectCbillSet, deletedCollectRelatedSet, deletedCollectBankItemSet, getBusinessObject());
		this.operateSuccessfully(response);
	}

	/**
	 * 管理
	 * --------------------------------- 修改记录 ----------------------------------
	 * 邱杰烜 2010-09-08 返回管理页时加入Grid数据权限过滤 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("vt", getBusinessObject().getViewText());
		/***********************************************************************/
		String strDataAuthSql = CommonDataAuth.getDataAuthSql(this.getBusinessObject());
		request.setAttribute("dataAuthSql", strDataAuthSql);
		/***********************************************************************/
		return new ModelAndView("xdss3/collect/collectManage");
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
		Collect collect = new Collect();
		String collectid = request.getParameter("collectid");
		collect.setCollectid(collectid);
		LockService.unLockBOData(collect);
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
		Collect collect = (Collect) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Collect.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectItem> collectitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectItem.class, null);
		collect.setCollectitem(collectitemmodifyItems);
		Set<CollectItem> collectitemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectItem.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectCbill> collectcbillmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectCbill.class, null);
		collect.setCollectcbill(collectcbillmodifyItems);
		Set<CollectCbill> collectcbilldeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectCbill.class, null);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectRelated> collectrelatedmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectRelated.class, null);
		collect.setCollectrelated(collectrelatedmodifyItems);
		Set<CollectRelated> collectrelateddeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectRelated.class, null);
		// 绑定子对象(一对一关系)
		SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
		if (settleSubject != null)
		{
			collect.setSettleSubject(settleSubject);
			settleSubject.setCollect(collect);
		}
		// 绑定子对象(一对一关系)
		FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
		if (fundFlow != null)
		{
			collect.setFundFlow(fundFlow);
			fundFlow.setCollect(collect);
		}
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<CollectBankItem> collectbankitemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { collect }, CollectBankItem.class, null);
		collect.setCollectbankitem(collectbankitemmodifyItems);
		Set<CollectBankItem> collectbankitemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { collect }, CollectBankItem.class, null);
		if(StringUtils.isBlank(collect.getBusinessstate())){
		    collect.setBusinessstate("0");
		}
		this.collectService._saveOrUpdate(collect, collectitemdeleteItems, collectcbilldeleteItems, collectrelateddeleteItems, collectbankitemdeleteItems, getBusinessObject());

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
				calActivity.setBusid(collect.getCollectid());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
		else
		{
			jo.put("collectno", collect.getCollectno());
			jo.put("collectid", collect.getCollectid());
			jo.put("settlesubjectid", collect.getSettleSubject() != null ? collect.getSettleSubject().getSettlesubjectid() : "");
			jo.put("fundflowid", collect.getFundFlow() != null ? collect.getFundFlow().getFundflowid() : "");
			String creator = collect.getCreator();
			String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
			jo.put("creator_text", creator_text);
			jo.put("creator", creator);
			jo.put("createtime", collect.getCreatetime());
			String lastmodifyer = collect.getLastmodifyer();
			String lastmodifyer_text = SysCachePoolUtils.getDictDataValue("YUSER", lastmodifyer);
			jo.put("lastmodifyer_text", lastmodifyer_text);
			jo.put("lastmodifyer", lastmodifyer);
			jo.put("lastmodifytime", collect.getLastmodifytime());
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
		Collect collect = new Collect();
		String id = request.getParameter("collectid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");
		if (null == id)
			id = businessId;

		if (StringUtils.isNullBlank(id))
		{
			collect = this.collectService._getForEdit(id);
		}
		else
		{
			collect = this.collectService._getForEdit(id);
		}

		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000258");
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
		request.setAttribute("collect", collect);
		return new ModelAndView("xdss3/collect/collectEdit");
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
}