/*
 * @(#)JdPurchaseService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.jdPurchase.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.jdPurchase.dao.JdPurchaseHibernate;
import com.infolion.sapss.jdPurchase.dao.JdPurchaseJdbcDao;
import com.infolion.sapss.jdPurchase.domain.TJindaPurchase;
import com.infolion.sapss.scrapped.domain.TScrapped;
@Service
public class JdPurchaseService extends BaseJdbcService{
	@Autowired
	private JdPurchaseJdbcDao jdPurchaseJdbcDao;
	@Autowired
	private JdPurchaseHibernate jdPurchaseHibernate;

	public void setJdPurchaseJdbcDao(JdPurchaseJdbcDao jdPurchaseJdbcDao) {
		this.jdPurchaseJdbcDao = jdPurchaseJdbcDao;
	}
	public void setJdPurchaseHibernate(JdPurchaseHibernate jdPurchaseHibernate) {
		this.jdPurchaseHibernate = jdPurchaseHibernate;
	}
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
	}
	/**
	 * 保存
	 * @param s
	 */
	public void savePurchase(TJindaPurchase s) {
		this.jdPurchaseHibernate.saveOrUpdate(s);
	}
	/**
	 * 查找
	 * @param scrappedId
	 */
	public TJindaPurchase findPurchase(String purchaseId) {
		List<TJindaPurchase> list= this.jdPurchaseHibernate.find("from TJindaPurchase t where t.purchaseId=?", new String[]{purchaseId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return new TJindaPurchase();
	}
	/**
	 * 删除
	 * @param scrappedId
	 */
	public int delPurchase(String purchaseId) {
		return this.jdPurchaseJdbcDao.delPurchase(purchaseId);
		
	}
	/**
	 * 提交流程审批
	 * @param taskId
	 * @param ts
	 */
	public void submit(String taskId, TJindaPurchase ts) {
		Map map = new HashMap();
		map.put("PURCHASE_ID", ts.getPurchaseId());
		String account = ts.getApplyAccount();
		double acc=0;
		if(account!=null && !"".equals(account))
			acc = Double.valueOf(account).doubleValue()/10000;
		map.put("_workflow_total_value", acc+"");
		map.put("_workflow_currency","CNY");
		ts.setWorkflowUserDefineTaskVariable(map);
		ts.setWorkflowProcessName("contract_purcharse_v4");
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(ts, ts.getPurchaseId());
			this.jdPurchaseJdbcDao.updateState(ts,"2",false);
		}else{
			WorkflowService.signalProcessInstance(ts,ts.getPurchaseId(), null);
		}
		
	}

}
