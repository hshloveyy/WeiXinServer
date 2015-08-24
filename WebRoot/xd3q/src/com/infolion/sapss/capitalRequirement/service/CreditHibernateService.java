package com.infolion.sapss.capitalRequirement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.capitalRequirement.dao.CreditHibernateDao;
import com.infolion.sapss.capitalRequirement.domain.TZJXQCreditInfo;

@Service
public class CreditHibernateService extends BaseHibernateService {
	@Autowired
	private CreditHibernateDao creditHibernateDao;

	public void setCreditHibernateDao(CreditHibernateDao creditHibernateDao) {
		this.creditHibernateDao = creditHibernateDao;
	}

	public String saveCreditInfo(TZJXQCreditInfo info) {
		String id = CodeGenerator.getUUID();
		info.setCreditID(id);
		this.creditHibernateDao.save(info);
		return id;
	}

	public void updateCreditInfo(TZJXQCreditInfo info) {
		this.creditHibernateDao.update(info);
	}

	public TZJXQCreditInfo getCreditInfo(String creditID) {
		return this.creditHibernateDao.get(creditID);
	}

	public boolean deleteCreditInfo(String creditID) {
		TZJXQCreditInfo info = this.getCreditInfo(creditID);
		if (info.getIsAvailable().equals("1")) {
			info.setIsAvailable("0");
			this.updateCreditInfo(info);
			return true;
		}
		return false;
	}
}
