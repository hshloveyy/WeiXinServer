/*
 * @(#)PurchaseChangeController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TPurchaseChange;
import com.infolion.sapss.contract.domain.TSalesChange;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.contract.service.ContractService;
import com.infolion.sapss.contract.service.PurchaseChangeHibernateService;
import com.infolion.sapss.contract.service.PurchaseChangeJdbcService;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class PurchaseChangeController extends BaseMultiActionController {
	@Autowired
	private PurchaseChangeHibernateService purchaseChangeHibernateService;
	@Autowired
	private PurchaseChangeJdbcService purchaseChangeJdbcService;
	@Autowired
	private ContractHibernateService  contractHibernateService;
	@Autowired
	private ContractService contractService;
	
	public void setPurchaseChangeHibernateService(
			PurchaseChangeHibernateService purchaseChangeHibernateService) {
		this.purchaseChangeHibernateService = purchaseChangeHibernateService;
	}

	public void setPurchaseChangeJdbcService(
			PurchaseChangeJdbcService purchaseChangeJdbcService) {
		this.purchaseChangeJdbcService = purchaseChangeJdbcService;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public void setContractHibernateService(
			ContractHibernateService contractHibernateService) {
		this.contractHibernateService = contractHibernateService;
	}

	/**
	 * 保存变更信息
	 * @param req
	 * @param resp
	 * @param tc
	 * @throws Exception
	 */
	public void saveChange(HttpServletRequest req,HttpServletResponse resp,TPurchaseChange tc) throws Exception{
		String contractId = req.getParameter("contractId");
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		tc.setContractPurchaseId(contractId);
		tc.setCreator(ut.getSysUser().getUserId());
		tc.setCreateTime(DateUtils.getCurrTimeStr(2));
		tc.setApplyTime(DateUtils.getCurrTimeStr(2));
		tc.setIsAvailable("1");
		tc.setChangeState("1");
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");

		if(tc.getChangeId()==null || "".equals(tc.getChangeId().trim())){
			int no=this.purchaseChangeJdbcService.getChangeNo(contractId,"5");
			int passNo=this.purchaseChangeJdbcService.getChangeNo(contractId,"4");
			no = no + passNo;
			no++;
			tc.setChangeNo(no+"");
			String changeId = purchaseChangeHibernateService.saveChage(tc);
			resp.getWriter().print("{success:true,msg:'保存成功!',changeId:'"+changeId+"',changeNo:'"+no+"'}");
		}	
		else{
			int rst = purchaseChangeJdbcService.updateChange(tc);
			if(rst>0)
				resp.getWriter().print("{success:true,msg:'更新成功!'}");
			else
				resp.getWriter().print("{success:true,msg:'更新失败!'}");
		}	
	}
	/**
	 * 返回变更列表
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void findChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String contractId= req.getParameter("contractId");
		String sql = "select tpc.*, change_state CHANGE_STATE_D_CHANGE_STATE,contract_purchase_id CONTRACT_ID from" +
				" t_purchase_change tpc where is_available='1' and contract_purchase_id='"+contractId+"'";
		String columns="change_desc,change_time,changer,apply_time,approved_time,CONTRACT_ID,change_id,CHANGE_STATE_D_CHANGE_STATE,change_no,currency,amount";
		String size= req.getParameter("limit");
		ExtComponentServlet servlet = new ExtComponentServlet();
		req.setAttribute("grid_sql", sql);
		req.setAttribute("grid_columns", columns);
		req.setAttribute("grid_size", size);
		servlet.processGrid(req, resp, true);
	}
	/**
	 * 删除变更
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void deleteChange(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("changeId");
		int rst = purchaseChangeJdbcService.deleteChange(changeId);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		if (rst > 0)
			resp.getWriter().print("{success:true,msg:'删除成功'}");
		else {
			resp.getWriter().print("{success:false,msg:'记录不能删除'}");
		}

	}
	/**
	 * 转到变更列表页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toChangeTab(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		String contractId = req.getParameter("contractId");
		req.setAttribute("contractId", contractId);
		//查找变更审批中状态为审批的记录,用于反显
		UserContext uc = UserContextHolder.getLocalUserContext().getUserContext();
		List<TPurchaseChange> list =  purchaseChangeHibernateService.findChangeByPurchaseId(contractId, "2");		
		TPurchaseChange bo = new TPurchaseChange();
		if(list!=null && list.size()>0){
			bo =list.get(0);
		} else {
			bo.setChanger(ut.getSysUser().getRealName());
			bo.setContractPurchaseId(contractId);
			bo.setChangeTime(DateUtils.getCurrTimeStr(2));
			bo.setChangeNo("0");
			bo.setCreator(uc.getSysUser().getUserId());
		}
		//传递 不经审批写入SAP 状态
		String st = req.getParameter("shortSAP");
		if(st!=null && "true".equals(st))
			req.setAttribute("shortSAP","true");
		
		//判断是不是信息中心
		if("XXZX".equals(ut.getSysDept().getDeptShortCode())){
			req.setAttribute("closeChange", false);
			bo.setChangeOperator(ut.getSysUser().getRealName());
		}else
			req.setAttribute("closeChange", true);
		//流程中:当退回给业务人员修改时,判断是不是创建人
		String from = req.getParameter("from");
		req.setAttribute("isChanged", bo.getIsChanged());
		req.setAttribute("bo", bo);
		if(from!=null ){
			req.setAttribute("controller", "purchaseChangeController.spr");
			if ("change".equals(from)) {
				if (bo.getCreator().equals(uc.getSysUser().getUserId())) {
					req.setAttribute("wfCanModify", true);
					return new ModelAndView("sapss/contract/contractChangeTabW");
				} else {
					req.setAttribute("wfCanModify", false);
					return new ModelAndView("sapss/contract/Archives/contractChangeTabR");
				}
			} if ("changeW".equals(from)){
				req.setAttribute("wfCanModify", true);
				return new ModelAndView("sapss/contract/contractChangeTabW");
			}else if ("changeR".equals(from)){
				if(uc.getSysDept().getDeptShortCode().equals("MG")){
					req.setAttribute("fileEdit", "true");
				}
				req.setAttribute("wfCanModify", false);
				return new ModelAndView("sapss/contract/Archives/contractChangeTabR");
			}
		}
		return new ModelAndView("sapss/contract/contractChangeTabW");
	}
	/**
	 * 提交审批(提交动作)
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void submitWorkflow(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String changeId = request.getParameter("changeId");
		//查找变更内容
		TPurchaseChange change =  this.purchaseChangeHibernateService.findChange(changeId);
		
		TContractPurchaseInfo info = this.contractHibernateService.getContractPurchaseInfo(change.getContractPurchaseId());
		String taskId = request.getParameter("workflowTaskId");
		change.setWorkflowTaskId(taskId);
		change.setApprovedTime(DateUtils.getCurrTime(2));
		change.setWorkflowExamine(request.getParameter("workflowExamine"));
		change.setWorkflowLeaveTransitionName(request.getParameter("workflowLeaveTransitionName"));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		change.setWorkflowBusinessNote(userContext.getSysDept().getDeptname()
				+ "|"
				+ userContext.getSysUser().getRealName()
				+ "|"
				+ info.getContractNo()
				+ "|"
				+ info.getContractName());
		
		String msg = "";
		try {
			/**取消控制20130729 cat
			UserContext uct = UserContextHolder.getLocalUserContext().getUserContext();
			if("XXZX".equals(uct.getSysDept().getDeptShortCode())){
				if(!"1".equals(change.getIsChanged()))
					throw new BusinessException("请确认SAP是否已冻结");
			}**/
			String taskName = request.getParameter("workflowCurrentTaskName");
			String transitionName = request.getParameter("workflowLeaveTransitionName");
			change.setWorkflowLeaveTransitionName(transitionName);
			String workflowExamine = request.getParameter("workflowExamine");
			change.setWorkflowExamine(workflowExamine);
			String group = info.getEkkoEkgrp();
			String deptCode = userContext.getSysDept().getDeptcode();
			//状态 不是未经审批变更,
			//if(taskId != null && !"8".equals(info.getOrderState()) && "部门经理确认".equals(taskName) && "直接修改SAP订单".equals(transitionName)){2014.10.20
			if(taskId != null && "部门经理确认".equals(taskName) && "直接修改SAP订单".equals(transitionName)){
				//throw new BusinessException("正常流程不是'未经审批写入SAP',变更不能走此流程 ,请检查!");//2014.10.20
				throw new BusinessException("变更不能走此流程 ,请检查!");
			}
			//
			if (null != taskId && !contractService.checkAllowPurcharseSubmit(taskName, transitionName,
							group, deptCode, info.getTradeType(),""))
				throw new BusinessException("除了五部内贸纸业务之外的业务活动不允许直接写入SAP订单,请检查!");
			//传递 不经审批写入SAP 状态
			String st = request.getParameter("shortSAP");
			boolean isDirectWriteToSAP = false;
			if(st!=null && "true".equals(st))
				isDirectWriteToSAP = true;
			this.purchaseChangeJdbcService.submitWorkflow(taskId, change,info,isDirectWriteToSAP);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg)){
			jo.put("returnMessage", "提交成功!");
			jo.put("success", true);
		}	
		else{
			jo.put("returnMessage", msg);
			jo.put("success", false);
		}	
		this.operateSuccessfullyWithString(response, jo.toString());
		//response.getWriter().print(jo.toString());
	}
	/**
	 * 代办打开审批(代办链接)
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public ModelAndView workflowSubmit(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		//从代办中取得业务记录ID
		String changeId = req.getParameter("businessRecordId");
		String taskId = req.getParameter("taskId");
		//历史跟踪
		req.setAttribute("businessRecordId", changeId);
		TPurchaseChange tpc =  this.purchaseChangeHibernateService.findChange(changeId);
		TContractPurchaseInfo tcpi = contractHibernateService.queryContractPurchaseInfoById(tpc.getContractPurchaseId());
		//显示业务类型
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tcpi.getTradeType().toString());
		req.setAttribute("tradeType", tradeType);
		
		//
		req.setAttribute("taskId", taskId);
		UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
		//判断是不是信息中心
		if("XXZX".equals(ut.getSysDept().getDeptShortCode())){
			req.setAttribute("iframeSrc",
					"purchaseChangeController.spr?action=findPurchaseInfo&from=contractW&changeId="+ changeId);
		}else
			req.setAttribute("iframeSrc",
					"purchaseChangeController.spr?action=findPurchaseInfo&from=contractR&changeId="+ changeId);
		//提交动作
		req.setAttribute("submitUrl", "purchaseChangeController.spr");
		req.setAttribute("action", "submitWorkflow&changeId="+ changeId);
		//动作参数
		req.setAttribute("revisable", "false");// 
		//iframe表单域
		req.setAttribute("iframeForms", "Form.serialize(window.frames['content']['baseForm'])+'&'+Form.serialize(window.frames['content']['contractForm'])");
		return new ModelAndView("sapss/project/workflowManager");
		
	}
	/**
	 * 查找信息,用于流程审批中信息显示
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public ModelAndView findPurchaseInfo(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("changeId");
		//查找变更内容
		TPurchaseChange bo =  this.purchaseChangeHibernateService.findChange(changeId);
		
		//通过变更记录找合同记录
		TContractPurchaseInfo info = new TContractPurchaseInfo();
		info = contractHibernateService.queryContractPurchaseInfoById(bo.getContractPurchaseId());
		String strContractGroupName = contractService.getContractGroupName(info.getContractGroupId()).getContractGroupName();

		req.setAttribute("purchase", info);
		req.setAttribute("contractGroupName", strContractGroupName);
		String from = req.getParameter("from");
		if(from!=null){
			req.setAttribute("writeSap",false);
			if("contractR".equals(from)){
				UserContext ut = UserContextHolder.getLocalUserContext().getUserContext();
				if(ut.getSysUser().getUserId().equals(bo.getCreator()))
					req.setAttribute("from", "changeW");
				else
					req.setAttribute("from", "changeR");
				info = selectToInputShow(info);
				req.setAttribute("purchase", info);
				return new ModelAndView("sapss/contract/Archives/purchaseContractRChange");
			}else if("contractW".equals(from)){
				req.setAttribute("from", "changeR");
				return new ModelAndView("sapss/contract/Apply/purchaseContractWChange");
			}
		}
		info = selectToInputShow(info);
		req.setAttribute("purchase", info);
		return new ModelAndView("sapss/contract/Archives/purchaseContractRChange");
	}
	/**
	 * 原下拉框改为文本显示
	 * @param info
	 * @return
	 */
	private TContractPurchaseInfo selectToInputShow(TContractPurchaseInfo info){
		//付款条件
		String ekkoZterm = SysCachePoolUtils.getDictDataValue("BM_PAYMENT_TYPE", info.getEkkoZterm());
		info.setEkkoZterm(ekkoZterm);
		//国际贸易条件1
		String ekkoInco1 = SysCachePoolUtils.getDictDataValue("BM_INCOTERM_TYPE", info.getEkkoInco1());
		info.setEkkoInco1(ekkoInco1);
		//采购组
		String ekkoEkgrp = SysCachePoolUtils.getDictDataValue("BM_INCOTERM_TYPE", info.getEkkoEkgrp());
		info.setEkkoEkgrp(ekkoEkgrp);
		return info;
	}
	/**
	 * 保存信息中心执行信息
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void saveChanged(HttpServletRequest req,HttpServletResponse resp,TPurchaseChange tpc) throws Exception{
		String changeId =req.getParameter("changeId");
		tpc.setChangeId(changeId);
		tpc.setOperateTime(DateUtils.getCurrTimeStr(2));
		int rtn = purchaseChangeJdbcService.saveChanged(tpc);
		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if (rtn>0){
			jo.put("returnMessage", "保存成功!");
		}	
		else{
			jo.put("returnMessage","保存失败!");
		}	
		resp.getWriter().print(jo.toString());
		//this.operateSuccessfullyWithString(resp, jo.toString());
			
	}
	/**
	 * 转到工作流程图
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public void toWorkflowPictureViw(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String contractId = req.getParameter("businessRecordId");
		String state = req.getParameter("state");
		String cd="2";
		if(state!=null && "finish".equals(state)){
			cd="4";
		}
		List<TPurchaseChange> list= purchaseChangeHibernateService.findChangeByPurchaseId(contractId,cd);
		String changeId="";
		if(list!=null && list.size()>0){
			changeId = list.get(0).getChangeId();
		}
		req.getRequestDispatcher("workflowController.spr?action=finishWorkDetailView&businessRecordId="+changeId).forward(req, resp);
	}
	/**
	 * 转到工作流程图
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public void toChagneWorkflowPictureViw(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		String changeId = req.getParameter("projectId");
		req.getRequestDispatcher("workflowController.spr?action=finishWorkDetailView&businessRecordId="+changeId).forward(req, resp);
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
}
