package com.infolion.sapss.capitalRequirement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.capitalRequirement.dao.LoanForeignJdbcDao;

@Service
public class LoanForeignJdbcService {

	@Autowired
	private LoanForeignJdbcDao loanForeignJdbcDao;

	public void setLoanForeignJdbcDao(LoanForeignJdbcDao loanForeignJdbcDao) {
		this.loanForeignJdbcDao = loanForeignJdbcDao;
	}

	public int updateLoanForeignRepayInfo(String repayID, String colName,
			String colValue) {
		return this.loanForeignJdbcDao.updateLoanForeignRepayInfo(repayID,
				colName, colValue);
	}

}
