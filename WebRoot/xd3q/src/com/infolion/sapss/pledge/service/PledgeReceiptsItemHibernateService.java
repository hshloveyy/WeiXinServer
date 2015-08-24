package com.infolion.sapss.pledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.pledge.dao.PledgeReceiptsItemHibernateDao;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;

@Service    
public class PledgeReceiptsItemHibernateService extends BaseHibernateService{

	@Autowired
	private PledgeReceiptsItemHibernateDao pledgeReceiptsItemHibernateDao;
	public void setPledgeReceiptsItemHibernateDao(
			PledgeReceiptsItemHibernateDao pledgeReceiptsItemHibernateDao) {
		this.pledgeReceiptsItemHibernateDao = pledgeReceiptsItemHibernateDao;
	}

	@Transactional(readOnly = true)
	public void saveMaterielInfo(PledgeReceiptsItem pledgeReceiptsItem){
		pledgeReceiptsItemHibernateDao.saveOrUpdate(pledgeReceiptsItem);
	}
	
	public PledgeReceiptsItem getPledgeReceiptsItem(String pledgeReceiptsItemId)
	{
		return this.pledgeReceiptsItemHibernateDao.get(pledgeReceiptsItemId);
	}
	
	/**
	 * 删除进仓单物料实体数据
	 * @param receiptDetailId
	 * @return
	 */
	public void deleteMaterial(String pledgeReceiptsItemId)
	{
		String[] ids = pledgeReceiptsItemId.split(",");
		for(String id:ids){
			if(id==null) continue;
			PledgeReceiptsItem pledgeReceiptsItem = pledgeReceiptsItemHibernateDao.get(id);
			pledgeReceiptsItemHibernateDao.remove(pledgeReceiptsItem);
		}
	}
}
