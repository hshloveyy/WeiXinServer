package com.infolion.sapss.capitalRequirement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class LoanCNYJdbcDao extends BaseDao {
	public int updateLoanCNYRepayInfo(String repayID, String colName,
			String colValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("update T_ZJXQ_LOAN_CNY_REPAY set ");
		sb.append(colName);
		sb.append("=?");
		sb.append(" where repay_id=?");
		return this.getJdbcTemplate().update(sb.toString(),
				new Object[] { colValue, repayID });
	}

	public String calBalance(String loanID) {
		final List<String> list = new ArrayList<String>();
		getJdbcTemplate()
				.query(
						"select amount-(select nvl(sum(amount),0) from T_ZJXQ_LOAN_CNY_REPAY where is_available='1' and loan_id=?) as amount from T_ZJXQ_LOAN_CNY where is_available='1' and loan_id=?",
						new Object[] { loanID, loanID },
						new RowCallbackHandler() {
							public void processRow(ResultSet rs)
									throws SQLException {
								list.add(rs.getString("amount"));
							}
						});
		if (list.isEmpty())
			return "";
		return list.get(0);
	}
}
