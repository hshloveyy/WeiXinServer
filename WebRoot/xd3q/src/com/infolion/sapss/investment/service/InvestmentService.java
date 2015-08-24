/*
 * @(#)JdPurchaseService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.investment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.investment.dao.InvestmentHibernate;
import com.infolion.sapss.investment.dao.InvestmentJdbcDao;
import com.infolion.sapss.investment.dao.InvestmentPaymentHibernate;
import com.infolion.sapss.investment.dao.InvestmentProjectHibernate;
import com.infolion.sapss.investment.domain.TInvestmentInfo;
import com.infolion.sapss.investment.domain.TInvestmentPaymentInfo;
import com.infolion.sapss.investment.domain.TInvestmentProjectInfo;
import com.infolion.sapss.jdPurchase.domain.TJindaPurchase;
@Service
public class InvestmentService extends BaseJdbcService{
	@Autowired
	private InvestmentJdbcDao investmentJdbcDao;
	@Autowired
	private InvestmentHibernate investmentHibernate;
	@Autowired
	private InvestmentProjectHibernate investmentProjectHibernate;
	@Autowired
	private InvestmentPaymentHibernate investmentPaymentHibernate;
	
	public void setInvestmentProjectHibernate(
			InvestmentProjectHibernate investmentProjectHibernate) {
		this.investmentProjectHibernate = investmentProjectHibernate;
	}
	public void setInvestmentPaymentHibernate(
			InvestmentPaymentHibernate investmentPaymentHibernate) {
		this.investmentPaymentHibernate = investmentPaymentHibernate;
	}
	public void setInvestmentJdbcDao(InvestmentJdbcDao investmentJdbcDao) {
		this.investmentJdbcDao = investmentJdbcDao;
	}
	public void setInvestmentHibernate(InvestmentHibernate investmentHibernate) {
		this.investmentHibernate = investmentHibernate;
	}
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
	}
	/**
	 * 保存
	 * @param s
	 */
	public void saveInfo(TInvestmentInfo s) {
		this.investmentHibernate.saveOrUpdate(s);
	}
	/**
	 * 查找
	 * @param scrappedId
	 */
	public TInvestmentInfo findInfo(String id) {
		List<TInvestmentInfo> list= this.investmentHibernate.find("from TInvestmentInfo t where t.infoId=?", new String[]{id});
		if(list!=null && list.size()>0)
			return list.get(0);
		return new TInvestmentInfo();
	}
	/**
	 * 删除
	 * @param scrappedId
	 */
	public int delInfo(String id) {
		return this.investmentJdbcDao.delInfo(id);
		
	}
	/**
	 * 提交流程审批
	 * @param taskId
	 * @param ts
	 */
	public void submit(String taskId, TInvestmentInfo ts) {
		Map map = new HashMap();
		map.put("deptId", ts.getDeptId());
		map.put("infoId", ts.getInfoId());
		ts.setWorkflowUserDefineTaskVariable(map);
		ts = chooseWorkflow(ts);
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(ts, ts.getInfoId());
			this.investmentJdbcDao.updateState(ts,"2",false);
		}else{
			WorkflowService.signalProcessInstance(ts,ts.getInfoId(), null);
		}
		
	}
	/**
	 * 
	 * @param ts
	 * @param status
	 * @param b
	 */
	public void updateState(TInvestmentInfo ts, String status, boolean b){
		this.investmentJdbcDao.updateState(ts, status, b);
	}
	/**
	 * 
	 * @param ts
	 * @return
	 */
	private  TInvestmentInfo chooseWorkflow(TInvestmentInfo ts){
		if(ts.getSubFlow().equals("资产评估和审计等流程")){
			ts.setWorkflowProcessName("invest_common1_v1");
		}else if(ts.getSubFlow().indexOf("付款")!=-1){
			ts.setWorkflowProcessName("invest_payment_v1");
		}else 
			ts.setWorkflowProcessName("invest_conmon_v1");
		return ts;
//		
//		投资规划流程,投资项目决策,合资谈判审批流程,投资项目日常监控与专项评估
//		
//		投资付款审批流程,
//		
//		//投资收款,
//		
//		资产评估和审计等流程
	}
	/**
	 * 
	 * @param info
	 */
	public void saveProject(TInvestmentProjectInfo info) {
		this.investmentProjectHibernate.saveOrUpdate(info);
	}
	/**
	 * 
	 * @param info
	 */
	public void savePayment(TInvestmentPaymentInfo info) {
		this.investmentPaymentHibernate.saveOrUpdate(info);
	}
	/**
	 * 
	 * @param infoId
	 * @return
	 */
	public TInvestmentPaymentInfo findPayment(String infoId) {
		List<TInvestmentPaymentInfo> list =  this.investmentPaymentHibernate.find("from TInvestmentPaymentInfo ts where ts.infoId=?",new String[]{infoId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
	/**
	 * 
	 * @param pid
	 */
	public void delProject(String pid) {
		TInvestmentProjectInfo info = new TInvestmentProjectInfo();
		info.setPid(pid);
		this.investmentProjectHibernate.remove(info);
	}
	
}
