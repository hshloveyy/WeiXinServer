package com.infolion.sapss.pledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.pledge.dao.PledgeReceiptsItemHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeShipItemHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeShipJdbcDao;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.pledge.domain.PledgeShipsItem;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;

@Service    
public class PledgeShipItemHibernateService extends BaseHibernateService{

	@Autowired
	private PledgeShipItemHibernateDao pledgeShipItemHibernateDao;
	public void setPledgeShipItemHibernateDao(
			PledgeShipItemHibernateDao pledgeShipItemHibernateDao) {
		this.pledgeShipItemHibernateDao = pledgeShipItemHibernateDao;
	}

	@Transactional(readOnly = true)
	public void saveMaterielInfo(PledgeShipsItem pledgeShipsItem){
		pledgeShipItemHibernateDao.saveOrUpdate(pledgeShipsItem);
	}


	/**
	 * 删除进仓单物料实体数据
	 * @param receiptDetailId
	 * @return
	 */
	public void deleteMaterial(String pledgeShipsItemId)
	{
		String[] ids = pledgeShipsItemId.split(",");
		for(String id:ids){
			if(id==null) continue;
			PledgeShipsItem pledgeShipsItem = pledgeShipItemHibernateDao.get(id);
			pledgeShipItemHibernateDao.remove(pledgeShipsItem);
		}
	}
	public PledgeShipsItem getPledgeShipsItem(String pledgeShipsItemId)
	{
		return this.pledgeShipItemHibernateDao.get(pledgeShipsItemId);
	}
	
	
}
