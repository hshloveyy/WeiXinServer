package com.infolion.sapss.interestBill.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
@Repository
public class InterestBillJdbcDao extends BaseDao{
	
	public String getBillApplyNo()
	{
		final List<String> returnFalg = new ArrayList<String>();
		String sql = "select lpad(nvl(count(a.interest_bill_no) + 1,1),10,'0') interest_bill_no from t_interest_bill a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("interest_bill_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	public double queryTotalValue(){
		String sql = "select sum(t.value) as totalValue from t_interest_bill t where t.exam_state in ('2','3') and t.is_available='1' and substr(ltrim(t.unit_No),3,1)!='1' and substr(t.apply_time,0,4)=to_char(SYSDATE,'YYYY')";
		final List<Double> returnFalg = new ArrayList<Double>();
		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getDouble("totalValue"));
			}
		});

		if (returnFalg.size() == 0)
			return 0d;
		else
			return returnFalg.get(0);
	}

}
