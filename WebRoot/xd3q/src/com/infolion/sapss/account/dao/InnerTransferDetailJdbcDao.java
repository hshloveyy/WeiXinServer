package com.infolion.sapss.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.account.domain.TInnerTransferDetail;

@Repository
public class InnerTransferDetailJdbcDao extends BaseDao {
	public int updateTInnerTransferDetail(String detailID, String colName,
			String colValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_inner_transfer_detail set ");
		sb.append(colName);
		sb.append("=?");
		sb.append(" where detail_id=?");
		return this.getJdbcTemplate().update(sb.toString(),
				new Object[] { colValue, detailID });
	}

	public List<TInnerTransferDetail> getDetailList(String transferID) {
		final List<TInnerTransferDetail> details = new ArrayList<TInnerTransferDetail>();
		String strSql = "select * from t_inner_transfer_detail where is_available='1' and transfer_id=?";

		getJdbcTemplate().query(strSql, new Object[] { transferID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						TInnerTransferDetail detail = new TInnerTransferDetail();
						detail.setPayBank(rs.getString("pay_bank"));
						detail.setPayAccount(rs.getString("pay_account"));
						detail.setReceiveBank(rs.getString("receive_bank"));
						detail.setReceiveAccount(rs
								.getString("receive_account"));
						detail.setSum(rs.getString("sum"));
						details.add(detail);
					}
				});

		return details;
	}
}
