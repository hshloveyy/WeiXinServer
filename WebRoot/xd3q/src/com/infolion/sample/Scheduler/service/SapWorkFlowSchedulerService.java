/*
 * @(#)SalesDocItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月02日 15点38分57秒
 *  描　述：创建
 */
package com.infolion.sample.Scheduler.service;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.sample.Scheduler.dao.SapWorkFlowSchedulerJdbcDao;
import com.infolion.sample.purchase.domain.PurchasingDoc;
import com.infolion.sample.sales.domain.SalesDoc;

/**
 * <pre>
 * SAP销售凭证、采购凭证工作流审批状态扫描
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
@Service
public class SapWorkFlowSchedulerService extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private SapWorkFlowSchedulerJdbcDao sapWorkFlowSchedulerJdbcDao;

	/**
	 * @param sapWorkFlowSchedulerJdbcDao
	 *            the sapWorkFlowSchedulerJdbcDao to set
	 */
	public void setSapWorkFlowSchedulerJdbcDao(SapWorkFlowSchedulerJdbcDao sapWorkFlowSchedulerJdbcDao)
	{
		this.sapWorkFlowSchedulerJdbcDao = sapWorkFlowSchedulerJdbcDao;
	}

	/**
	 * 
	 */
	public void sapWorkFlowTiming()
	{
		Set<PurchasingDoc> purchasingDocs = sapWorkFlowSchedulerJdbcDao.getPurchasingDocs();
		log.debug("SAP采购凭证、销售凭证审批状态调度开始:"
				+ DateUtils.getCurrTimeStr(DateUtils.CN_DISPLAY_DATE));
		if (purchasingDocs != null && purchasingDocs.size() > 0)
		{
			Iterator<PurchasingDoc> itPurchasingDoc = purchasingDocs.iterator();
			log.debug("发现 " + purchasingDocs.size()
					+ " 笔采购凭证数据未审批，系统开始处理自动提交审批。");
			while (itPurchasingDoc.hasNext())
			{
				PurchasingDoc purchasingDoc = itPurchasingDoc.next();
				String id = purchasingDoc.getEbeln();
				// if(id != null && id.equalsIgnoreCase("4500017232"))
				// {
					WorkflowService.createAndSignalProcessInstance(purchasingDoc, id);
					log.debug("成功提交流程！业务ID:" + id);
					String processState = purchasingDoc.getProcessstate();
					String client = purchasingDoc.getClient();
					sapWorkFlowSchedulerJdbcDao.updateProcessState("EKKO" ,processState,"EBELN",id,client);
					log.debug("成功更新流程状态为:[" + processState + "],业务ID:" + id);
				// }
			}
		}

		
		Set<SalesDoc> salesDocs = sapWorkFlowSchedulerJdbcDao.getSalesDocs();
		if (salesDocs != null && salesDocs.size() > 0)
		{
			Iterator<SalesDoc> itSalesDoc = salesDocs.iterator();
			log.debug("发现 " + salesDocs.size() + " 笔销售凭证数据未审批，系统开始处理自动提交审批。");
			while (itSalesDoc.hasNext())
			{
				SalesDoc salesDoc = itSalesDoc.next();
				String id = salesDoc.getVbeln();
				// if(id != null && id.equalsIgnoreCase("0000012075"))
				// {
					WorkflowService.createAndSignalProcessInstance(salesDoc, id);
					log.debug("成功提交流程！业务ID:" + id);
					String processState = salesDoc.getProcessstate();
					String client = salesDoc.getClient();
					sapWorkFlowSchedulerJdbcDao.updateProcessState("VBAK" ,processState,"VBELN",id,client);
					log.debug("成功更新流程状态为:[" + processState + "],业务ID:" + id);
				// }
			}
		}
		
		log.debug("SAP采购凭证、销售凭证审批状态调度结束:"
				+ DateUtils.getCurrTimeStr(DateUtils.CN_DISPLAY_DATE));

	}
}