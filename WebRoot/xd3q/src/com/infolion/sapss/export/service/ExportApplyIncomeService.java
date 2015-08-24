/*
 * @(#)ExportApplyIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-27
 *  描　述：创建
 */

package com.infolion.sapss.export.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.export.dao.ExportApplyIncomeHibernateDao;
import com.infolion.sapss.export.domain.TExportApplyIncome;
@Service
public class ExportApplyIncomeService extends  BaseHibernateService{
	@Autowired
	private ExportApplyIncomeHibernateDao exportApplyIncomeHibernateDao;

	public void setExportApplyIncomeHibernateDao(
			ExportApplyIncomeHibernateDao exportApplyIncomeHibernateDao) {
		this.exportApplyIncomeHibernateDao = exportApplyIncomeHibernateDao;
	}
	
	public void saveOrUpdate(TExportApplyIncome ai){
		this.exportApplyIncomeHibernateDao.saveOrUpdate(ai);
	}

	public TExportApplyIncome find(String exportIncomeInfoId) {
		String hql = "from TExportApplyIncome t where t.exportIncomeInfoId='"+exportIncomeInfoId+"'";
		List<TExportApplyIncome> list =this.exportApplyIncomeHibernateDao.find(hql);
		if(list.size()>0)
			return list.get(0);
		return new TExportApplyIncome();
	}
}
