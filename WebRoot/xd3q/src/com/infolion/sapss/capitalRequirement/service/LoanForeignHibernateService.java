package com.infolion.sapss.capitalRequirement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.capitalRequirement.dao.LoanForeignHibernateDao;
import com.infolion.sapss.capitalRequirement.dao.LoanForeignJdbcDao;
import com.infolion.sapss.capitalRequirement.dao.LoanForeignRepayHibernateDao;
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanForeignInfo;
import com.infolion.sapss.capitalRequirement.domain.TZJXQLoanForeignRepayInfo;

@Service
public class LoanForeignHibernateService {
	@Autowired
	private LoanForeignHibernateDao loanForeignHibernateDao;

	@Autowired
	private LoanForeignRepayHibernateDao loanForeignRepayHibernateDao;

	@Autowired
	private LoanForeignJdbcDao loanForeignJdbcDao;

	public void setLoanForeignHibernateDao(
			LoanForeignHibernateDao loanForeignHibernateDao) {
		this.loanForeignHibernateDao = loanForeignHibernateDao;
	}

	public void setLoanForeignRepayHibernateDao(
			LoanForeignRepayHibernateDao loanForeignRepayHibernateDao) {
		this.loanForeignRepayHibernateDao = loanForeignRepayHibernateDao;
	}

	public void setLoanForeignJdbcDao(LoanForeignJdbcDao loanForeignJdbcDao) {
		this.loanForeignJdbcDao = loanForeignJdbcDao;
	}

	public void saveLoanForeignInfo(TZJXQLoanForeignInfo info) {
		this.loanForeignHibernateDao.save(info);
	}

	public void updateLoanForeignInfo(TZJXQLoanForeignInfo info) {
		this.loanForeignHibernateDao.update(info);
	}

	public TZJXQLoanForeignInfo getLoanForeignInfo(String loanID) {
		return this.loanForeignHibernateDao.get(loanID);
	}

	public void saveLoanForeignRepay(TZJXQLoanForeignRepayInfo info) {
		this.loanForeignRepayHibernateDao.save(info);
	}

	public void updateLoanForeignRepay(TZJXQLoanForeignRepayInfo info) {
		this.loanForeignRepayHibernateDao.update(info);
	}

	public boolean deleteLoanInfo(String loanID) {
		TZJXQLoanForeignInfo info = this.getLoanForeignInfo(loanID);
		if (info.getIsAvailable().equals("1")) {
			info.setIsAvailable("0");
			this.updateLoanForeignInfo(info);//没有继续设置还款的删除标志
			return true;
		}
		return false;
	}

	public boolean deleteLoanForeignRepayInfo(String idList) {
		String[] idArr = idList.split("\\|");
		int len = idArr.length;
		for (int i = 0; i < len; i++) {
			TZJXQLoanForeignRepayInfo repay = this.loanForeignRepayHibernateDao
					.get(idArr[i]);
			if (repay.getIsAvailable().equals("1")) {
				repay.setIsAvailable("0");
				this.updateLoanForeignRepay(repay);
			}
		}
		return true;
	}

	public void calBalance(TZJXQLoanForeignInfo info) {
		info.setBalanceForeign(this.calBalanceForeign(info.getLoanID()));
		info.setBalanceCNY(this.calBalanceCNY(info.getLoanID()));
		if (info.getBalanceForeign() == null
				|| "".equals(info.getBalanceForeign())) {
			info.setIsPay("1");
		} else {
			info.setIsPay("0");
		}
		this.loanForeignHibernateDao.update(info);
	}

	private String calBalanceForeign(String loanID) {
		return this.loanForeignJdbcDao.calBalanceForeign(loanID);
	}

	private String calBalanceCNY(String loanID) {
		return this.loanForeignJdbcDao.calBalanceCNY(loanID);
	}
}
