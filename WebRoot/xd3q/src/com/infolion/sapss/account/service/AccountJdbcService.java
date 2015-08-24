package com.infolion.sapss.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.account.dao.AccountJdbcDao;
import com.infolion.sapss.account.domain.TAccountInfo;
import com.infolion.sapss.account.web.AccountFundVO;
import com.infolion.sapss.account.web.AccountGeneralVO;
import com.infolion.sapss.account.web.AccountMaintainVO;

@Service
public class AccountJdbcService extends BaseJdbcService {
	@Autowired
	private AccountJdbcDao accountJdbcDao;

	public void setAccountJdbcDao(AccountJdbcDao accountJdbcDao) {
		this.accountJdbcDao = accountJdbcDao;
	}

	public int updateAccountInfo(TAccountInfo info) {
		return this.accountJdbcDao.updateAccountInfo(info);
	}

	public int saveFundInfo(AccountFundVO info) {
		return this.accountJdbcDao.saveFundInfo(info);
	}

	public int saveGeneralInfo(AccountGeneralVO info) {
		return this.accountJdbcDao.saveGeneralInfo(info);
	}
	
	public int saveMaintainInfo(AccountMaintainVO info) {
		return this.accountJdbcDao.saveMaintainInfo(info);
	}
}
