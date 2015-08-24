package com.infolion.sapss.capitalRequirement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.sapss.capitalRequirement.dao.LoanCNYJdbcDao;

@Service
public class LoanCNYJdbcService {

	@Autowired
	private LoanCNYJdbcDao loanCNYJdbcDao;


	public void setLoanCNYJdbcDao(LoanCNYJdbcDao loanCNYJdbcDao) {
		this.loanCNYJdbcDao = loanCNYJdbcDao;
	}

	public int updateLoanCNYRepayInfo(String repayID, String colName,
			String colValue) {
		return this.loanCNYJdbcDao.updateLoanCNYRepayInfo(repayID, colName,
				colValue);
	}

}
