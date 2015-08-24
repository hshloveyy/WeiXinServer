package com.infolion.sapss.bapi.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.bapi.UpdataSapData;
import com.infolion.sapss.bapi.dao.BapiHibernateDao;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.bapi.domain.TBapiLogDetail;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.contract.service.PurchaseContractJdbcService;
import com.infolion.sapss.contract.service.SaleContractJdbcService;

@Service
public class BapiHibernateService extends BaseHibernateService{
	
	@Autowired
	private BapiHibernateDao bapiHibernateDao;
	
	public void setBapiHibernateDao(BapiHibernateDao bapiHibernateDao) {
		this.bapiHibernateDao = bapiHibernateDao;
	}
	@Autowired
	private ContractHibernateService contractHibernateService;
	
	
	public void setContractHibernateService(
			ContractHibernateService contractHibernateService) {
		this.contractHibernateService = contractHibernateService;
	}
	@Autowired
	private SaleContractJdbcService saleContractJdbcService;
	public void setSaleContractJdbcService(
			SaleContractJdbcService saleContractJdbcService) {
		this.saleContractJdbcService = saleContractJdbcService;
	}
	@Autowired
	private PurchaseContractJdbcService purchaseContractJdbcService;

	public void setPurchaseContractJdbcService(
			PurchaseContractJdbcService purchaseContractJdbcService) {
		this.purchaseContractJdbcService = purchaseContractJdbcService;
	}

	public TContractPurchaseInfo getTContractPurcharseInfoById(String id){
		return contractHibernateService.queryPurchase(id);
	}
	
	public TContractSalesInfo getTContractSalesInfoById(String id){
		return contractHibernateService.querySales(id);
	}
	
	public TBapiLog insertSalesOrder(TContractSalesInfo order){
		TBapiLog log =  UpdataSapData.insertSalesOrder(order);
		bapiHibernateDao.save(log);
		if(log.getSapOrderNo()!=null && !"".equals(log.getSapOrderNo()))
			this.saleContractJdbcService.saveSAPOrderNo(log.getSapOrderNo(),order.getContractSalesId());
		return log;
	}
	
	public TBapiLog insertPurcharseOrder(TContractPurchaseInfo order){
		TBapiLog log =  UpdataSapData.insertPurcharseOrder(order);
		bapiHibernateDao.save(log);
		if(log.getSapOrderNo()!=null && !"".equals(log.getSapOrderNo()))
			this.purchaseContractJdbcService.saveSAPOrderNo(log.getSapOrderNo(),order.getContractPurchaseId());
		return log;
	}
	/**
	 * 取得日志内容
	 * @param bapiLog
	 * @return
	 */
	public String getBapiLogText(TBapiLog bapiLog)
	{
		StringBuffer sb = new StringBuffer();
		Set<TBapiLogDetail> set = bapiLog.getTBapiLogDetails();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			TBapiLogDetail bapiLogDetail = (TBapiLogDetail) iterator.next();
			sb.append("<br>错误类型："+bapiLogDetail.getLogType()+",错误内容："+bapiLogDetail.getLogContent());
		}
		return sb.toString();
	}
	

}
