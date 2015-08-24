/*
 * @(#)FundsRequireQueryDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-11-3
 *  描　述：创建
 */

package com.infolion.sapss.capitalRequirement.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class FundsRequireQueryDao extends BaseDao {

	public List<?> getPaymentInfo(Map<String, String> map) {
		Map m = this.time(map);
		String sql = "select time_value,sum(sum_value) sum_value from "
				+ "(select (case when substr(p.paydate,1,6)<'"
				+ m.get("currMonth")
				+ "' then '"
				+ m.get("currMonth")
				+ "01'"
				+ " else p.paydate end) time_value,sum(p.convertamount) sum_value "
				+ " from ypayment p where substr(p.paydate,1,6)<='"
				+ m.get("currMonth")
				+ "' and p.paymenttype in('03','04') and p.payer=' ' and p.pay_type!=2"
				+ " group by p.paydate) group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * type:1银行承兑汇票;2:国内信用证 06 国内证，07 承兑
	 * 
	 * @param map
	 * @param type
	 * @return
	 */
	public List getCreditInfo(Map<String, String> map, String type) {
		int typeInt = 1;
		if ("06".equals(type)) {
			typeInt = 2;
		}
		Map timeMap = this.time(map);
		// String sql = "select (case when substr(p.draftdate,1,6)<'"
		// + timeMap.get("currMonth")
		// + "' then '"
		// + timeMap.get("currMonth")
		// + "01'"
		// + " else p.draftdate end) time_value,sum(p.convertamount) sum_value "
		// + " from ypayment p where substr(p.draftdate,1,6)<='"
		// + timeMap.get("currMonth") + "' and p.paymenttype='" + type
		// + "' and p.payer=' ' group by p.draftdate";
		// 2010.11.15修改：银承和国内证改为两部分取数，一部分来自系统，一部分手工录。
		String sql = "select time_value,sum(sum_value) sum_value from "
				+ "(select (case when substr(p.draftdate,1,6)<'"
				+ timeMap.get("currMonth")
				+ "' then '"
				+ timeMap.get("currMonth")
				+ "01' else p.draftdate end) time_value,sum(p.convertamount) sum_value "
				+ "from ypayment p where substr(p.draftdate,1,6)<='"
				+ timeMap.get("currMonth")
				+ "' and p.paymenttype='"
				+ type
				+ "' and p.payer=' ' group by p.draftdate "
				+ "union all "
				+ "select (case when substr(t.end_date,1,4)||substr(t.end_date,6,2)<'"
				+ timeMap.get("currMonth")
				+ "' then '"
				+ timeMap.get("currMonth")
				+ "01' else replace(t.end_date,'-','') end) time_value,sum(t.amount) sum_value "
				+ "from t_zjxq_credit t where t.type='"
				+ typeInt
				+ "' and substr(t.end_date,1,4)||substr(t.end_date,6,2) <='"
				+ timeMap.get("currMonth")
				+ "' and t.is_available='1' and t.is_pay='0' group by t.end_date) group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 进口押汇
	 * 
	 * @param map
	 * @return
	 */
	public List getDraftInfo(Map<String, String> map) {
		Map timeMap = this.time(map);
		String sql = "select time_value,sum(sum_value) sum_value from "
				+ "(select (case when substr(p.documentarypaydt,1,6)<'"
				+ timeMap.get("currMonth")
				+ "' then '"
				+ timeMap.get("currMonth")
				+ "01'"
				+ " else p.documentarypaydt end) time_value,sum(p.convertamount) sum_value "
				+ " from ypayment p where substr(p.documentarypaydt,1,6)<='"
				+ timeMap.get("currMonth")
				+ "' and p.pay_type=2 and p.documentarypaydt!=' '"
				+ " and p.payrealdate=' '  group by p.documentarypaydt) group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 出口押汇
	 * 
	 * @param map
	 * @return
	 */
	public List getDraftOutInfo(Map<String, String> map) {
		Map timeMap = this.time(map);
		String sql = "select time_value,sum(sum_value) sum_value from "
				+ "(select (case when substr(y.maturity,1,6)<'"
				+ timeMap.get("currMonth")
				+ "' then '"
				+ timeMap.get("currMonth")
				+ "01'"
				+ " else y.maturity end) time_value,(T1.BMONEY2 - T2.RMONEY2) sum_value "
				+ " from YBILLPURCHASED Y,"
				+ "(SELECT SUM(B.REALMONEY) BMONEY,B.BILLPURID,SUM(B.REALMONEY1) BMONEY2 FROM YBILLPURBANKITEM B GROUP BY B.BILLPURID) T1,"
				+ "(SELECT SUM(R.REALMONEY) RMONEY,R.BILLPURID,SUM(R.REALMONEY2) RMONEY2 FROM YBILLPURREBANK R GROUP BY R.BILLPURID) T2 "
				+ "where Y.BILLPURID = T1.BILLPURID AND Y.BILLPURID = T2.BILLPURID AND "
				+ "T2.RMONEY2 - T1.BMONEY2 < 0 AND y.maturity <> ' ' AND substr(y.maturity,1,6)<='"
				+ timeMap.get("currMonth") + "') group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	private Map time(Map<String, String> map) {
		String currMonth = "";
		String nextMonth = "";

		if (map.get("year") != null && map.get("month") != null) {
			int month = Integer.valueOf(map.get("month")).intValue();
			month += 1;
			currMonth = map.get("year") + map.get("month");
			nextMonth = map.get("year") + month;
			if (Integer.valueOf(map.get("month")).intValue() < 10)
				currMonth = map.get("year") + "0" + map.get("month");
			if (month < 10)
				nextMonth = map.get("year") + "0" + month;
		}
		Map<String, String> m = new HashMap();
		m.put("currMonth", currMonth);
		m.put("nextMonth", nextMonth);
		return m;

	}

	/**
	 * type:1短期;2长期
	 * 
	 * @param map
	 * @param type
	 * @return
	 */
	public List getCNYLoanInfo(Map<String, String> map, int type) {
		Map timeMap = this.time(map);
		String sql = "select time_value,sum(sum_value) sum_value from "
				+ "(select (case when substr(replace(t.end_date,'-',''),1,6)<'"
				+ timeMap.get("currMonth") + "' then '"
				+ timeMap.get("currMonth") + "01'"
				+ " else replace(t.end_date,'-','') end) time_value,"
				+ "sum(t.balance) sum_value from t_zjxq_loan_cny t "
				+ " where t.type='" + type + "'";
		sql += " and substr(replace(t.end_date,'-',''),1,6) <'"
				+ timeMap.get("nextMonth") + "'";
		sql += " and t.is_available='1'";
		sql += " and t.is_pay='0'";
		sql += " group by t.end_date)";
		sql += " group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List getForeignInfo(Map<String, String> map) {
		Map timeMap = time(map);
		String sql = "select time_value,sum(sum_value) sum_value from (select "
				+ "(case when substr(replace(t.end_date,'-',''),1,6)<'"
				+ timeMap.get("currMonth") + "' then '"
				+ timeMap.get("currMonth") + "01'"
				+ " else replace(t.end_date,'-','') end) time_value,"
				+ "sum(t.balance_cny) sum_value from t_zjxq_loan_foreign t "
				+ " where 1=1";
		sql += " and substr(replace(t.end_date,'-',''),1,6) <'"
				+ timeMap.get("nextMonth") + "'";
		sql += " and t.is_available='1'";
		sql += " and t.is_pay='0'";
		sql += " group by t.end_date)";
		sql += " group by time_value";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public List getCnyShortOrLongInfo(String date, int i) {
		String sql = "select t.*,t.balance amt from t_zjxq_loan_cny t where t.is_pay='0' and t.is_available='1' and t.type='"
				+ i
				+ "' "
				+ "and replace(t.end_date,'-','')='"
				+ date
				+ "' order by t.end_date";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select t.*,t.balance amt from t_zjxq_loan_cny t where t.is_pay='0' and t.is_available='1' and t.type='"
					+ i
					+ "' "
					+ "and replace(t.end_date,'-','')<='"
					+ date
					+ "' order by t.end_date";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public List getForeignShortLoanInfo(String date) {
		String sql = "select * from t_zjxq_loan_foreign t where t.is_pay='0' and t.is_available='1' "
				+ "and replace(t.end_date,'-','')='"
				+ date
				+ "' order by t.end_date";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select * from t_zjxq_loan_foreign t where t.is_pay='0' and t.is_available='1' "
					+ "and replace(t.end_date,'-','')<='"
					+ date
					+ "' order by t.end_date";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public List getImportPaymentInfo(String date) {
		String sql = "select p.paymentid,p.paydate,p.paymentno,p.applyamount,p.currency from ypayment p "
				+ "where p.paydate=? and p.payer=' ' and p.pay_type!=2 and p.paymenttype in('03','04') "
				+ "order by p.paydate";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select p.paymentid,p.paydate,p.paymentno,p.applyamount,p.currency from ypayment p "
					+ "where p.paydate<=? and p.payer=' ' and p.pay_type!=2 and p.paymenttype in('03','04') "
					+ "order by p.paydate";
		}
		return this.getJdbcTemplate().queryForList(sql, new Object[] { date });
	}

	public List getDraftBillInfo(String date) {
		String sql = "select p.paymentid,p.documentarypaydt,p.paymentno,p.applyamount,p.currency2 from ypayment p "
				+ "where p.documentarypaydt='"
				+ date
				+ "' and p.payrealdate=' ' and p.pay_type=2 and p.documentarypaydt!=' ' "
				+ "order by p.documentarypaydt";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select p.paymentid,p.documentarypaydt,p.paymentno,p.applyamount,p.currency2 from ypayment p "
					+ "where p.documentarypaydt<='"
					+ date
					+ "' and p.payrealdate=' ' and p.pay_type=2 and p.documentarypaydt!=' ' "
					+ "order by p.documentarypaydt";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List getDraftOutBillInfo(String date) {
		String sql = "SELECT y.billpurid,y.billpur_no,y.maturity,t1.bmoney,"
				+ "y.currency,t2.rmoney,(T1.BMONEY - T2.RMONEY) sum_value "
				+ "FROM YBILLPURCHASED Y,(SELECT SUM(B.REALMONEY) BMONEY,B.BILLPURID,SUM(B.REALMONEY1) BMONEY2 "
				+ "FROM YBILLPURBANKITEM B GROUP BY B.BILLPURID) T1,"
				+ "(SELECT SUM(R.REALMONEY) RMONEY,R.BILLPURID,SUM(R.REALMONEY2) RMONEY2 FROM YBILLPURREBANK R GROUP BY R.BILLPURID) T2 "
				+ "WHERE Y.BILLPURID = T1.BILLPURID AND Y.BILLPURID = T2.BILLPURID AND T2.RMONEY - T1.BMONEY < 0 "
				+ "AND y.maturity = '" + date + "' order by y.maturity";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "SELECT y.billpurid,y.billpur_no,y.maturity,t1.bmoney,y.currency,t2.rmoney,(T1.BMONEY - T2.RMONEY) sum_value "
					+ "FROM YBILLPURCHASED Y,(SELECT SUM(B.REALMONEY) BMONEY,B.BILLPURID,SUM(B.REALMONEY1) BMONEY2 "
					+ "FROM YBILLPURBANKITEM B GROUP BY B.BILLPURID) T1,"
					+ "(SELECT SUM(R.REALMONEY) RMONEY,R.BILLPURID,SUM(R.REALMONEY2) RMONEY2 FROM YBILLPURREBANK R GROUP BY R.BILLPURID) T2 "
					+ "WHERE Y.BILLPURID = T1.BILLPURID AND Y.BILLPURID = T2.BILLPURID AND T2.RMONEY - T1.BMONEY < 0 "
					+ "AND y.maturity <= '" + date + "' order by y.maturity";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List getBankAcceptancInfo(String date) {
		String sql = "select p.paymentid,p.draftdate,p.paymentno,p.applyamount,p.currency from ypayment p "
				+ "where p.draftdate='"
				+ date
				+ "' and p.payer=' ' and p.paymenttype='07' "
				+ "union all "
				+ "select '',replace(t.end_date,'-','') draftdate,'',to_number(amount) applyamount,'' from "
				+ "t_zjxq_credit t where t.type='1' and t.is_available='1' and t.is_pay='0' "
				+ "and replace(t.end_date,'-','')='"
				+ date
				+ "' order by draftdate";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select p.paymentid,p.draftdate,p.paymentno,p.applyamount,p.currency from ypayment p "
					+ "where p.draftdate<='"
					+ date
					+ "' and p.payer=' ' and p.paymenttype='07' "
					+ "union all "
					+ "select '',replace(t.end_date,'-','') draftdate,'',to_number(amount) applyamount,'' from "
					+ "t_zjxq_credit t where t.type='1' and t.is_available='1' and t.is_pay='0' "
					+ "and replace(t.end_date,'-','')<='"
					+ date
					+ "' order by draftdate";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List getHomeCreditInfo(String date) {
		String sql = "select p.paymentid,p.draftdate,p.paymentno,p.applyamount,p.currency from ypayment p "
				+ "where p.draftdate='"
				+ date
				+ "' and p.payer=' ' and p.paymenttype='06' "
				+ "union all "
				+ "select '',replace(t.end_date,'-','') draftdate,'',to_number(amount) applyamount,'' from "
				+ "t_zjxq_credit t where t.type='2' and t.is_available='1' and t.is_pay='0' "
				+ "and replace(t.end_date,'-','')='"
				+ date
				+ "' order by draftdate";
		if (date.substring(date.length() - 2, date.length()).equals("01")) {
			sql = "select p.paymentid,p.draftdate,p.paymentno,p.applyamount,p.currency from ypayment p "
					+ "where p.draftdate<='"
					+ date
					+ "' and p.payer=' ' and p.paymenttype='06' "
					+ "union all "
					+ "select '',replace(t.end_date,'-','') draftdate,'',to_number(amount) applyamount,'' from "
					+ "t_zjxq_credit t where t.type='2' and t.is_available='1' and t.is_pay='0' "
					+ "and replace(t.end_date,'-','')<='"
					+ date
					+ "' order by draftdate";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}
}
