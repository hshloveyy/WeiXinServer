package com.infolion.sapss.capitalRequirement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.capitalRequirement.dao.LoanCNYHibernateDao;
import com.infolion.sapss.capitalRequirement.dao.LoanCNYJdbcDao;
import com.infolion.sapss.capitalRequirement.dao.LoanCNYRepayHibernateDao;
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanCNYInfo;
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanCNYRepayInfo;

@Service
public class LoanCNYHibernateService {
	@Autowired
	private LoanCNYHibernateDao loanCNYHibernateDao;

	@Autowired
	private LoanCNYRepayHibernateDao loanCNYRepayHibernateDao;

	@Autowired
	private LoanCNYJdbcDao loanCNYJdbcDao;

	public void setLoanCNYHibernateDao(LoanCNYHibernateDao loanCNYHibernateDao) {
		this.loanCNYHibernateDao = loanCNYHibernateDao;
	}

	public void setLoanCNYRepayHibernateDao(
			LoanCNYRepayHibernateDao loanCNYRepayHibernateDao) {
		this.loanCNYRepayHibernateDao = loanCNYRepayHibernateDao;
	}

	public void setLoanCNYJdbcDao(LoanCNYJdbcDao loanCNYJdbcDao) {
		this.loanCNYJdbcDao = loanCNYJdbcDao;
	}

	public void saveLoanCNYInfo(TZJXQLoanCNYInfo info) {
		this.loanCNYHibernateDao.save(info);
	}

	public void updateLoanCNYInfo(TZJXQLoanCNYInfo info) {
		this.loanCNYHibernateDao.update(info);
	}

	public TZJXQLoanCNYInfo getLoanCNYInfo(String loanID) {
		return this.loanCNYHibernateDao.get(loanID);
	}

	public void saveLoanCNYRepay(TZJXQLoanCNYRepayInfo info) {
		this.loanCNYRepayHibernateDao.save(info);
	}

	public void updateLoanCNYRepay(TZJXQLoanCNYRepayInfo info) {
		this.loanCNYRepayHibernateDao.update(info);
	}

	public boolean deleteLoanInfo(String loanID) {
		TZJXQLoanCNYInfo info = this.getLoanCNYInfo(loanID);
		if (info.getIsAvailable().equals("1")) {
			info.setIsAvailable("0");
			this.updateLoanCNYInfo(info);// 没有继续设置还款的删除标志
			return true;
		}
		return false;
	}

	public boolean deleteLoanCNYRepayInfo(String idList, String loanID) {
		String[] idArr = idList.split("\\|");
		int len = idArr.length;
		for (int i = 0; i < len; i++) {
			TZJXQLoanCNYRepayInfo repay = this.loanCNYRepayHibernateDao
					.get(idArr[i]);
			if (repay.getIsAvailable().equals("1")) {
				repay.setIsAvailable("0");
				this.updateLoanCNYRepay(repay);
			}
		}
		return true;
	}

	public void calBalance(TZJXQLoanCNYInfo info) {
		info.setBalance(this.calBalance(info.getLoanID()));
		if (info.getBalance() == null || "".equals(info.getBalance())) {
			info.setIsPay("1");
		} else {
			info.setIsPay("0");
		}
		this.loanCNYHibernateDao.update(info);
	}

	private String calBalance(String loanID) {
		return this.loanCNYJdbcDao.calBalance(loanID);
	}
}
