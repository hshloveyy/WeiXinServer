package com.infolion.sapss.ship.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.StoredProcedure;

import com.infolion.platform.core.dao.BaseDao;

/**
 * 
 * @author user
 * 
 */
public class CheckCustomerSendCreditStoreProcedure extends StoredProcedure {
//	private static final String SQL = "check_customer_send_credit";
	public static final String OUT_RETURN = "rt";
	private String in_customer_id;
	private String in_project_id;
	private float in_value;

	public CheckCustomerSendCreditStoreProcedure(DataSource ds,
			String in_customer_id, String in_project_id, float in_value,String sql) {
		this.in_customer_id = in_customer_id;
		this.in_project_id = in_project_id;
		this.in_value = in_value;
		setDataSource(ds);
		setFunction(true);
		setSql(sql);
		// 一定要注意顺序，将OUt放在前面，否则，你的返回值永远为null
		declareParameter(new SqlOutParameter(OUT_RETURN, Types.FLOAT));
		declareParameter(new SqlParameter("in_customer_id", Types.VARCHAR));
		declareParameter(new SqlParameter("in_project_id", Types.VARCHAR));
		declareParameter(new SqlParameter("in_value", Types.FLOAT));
		compile();
	}

	public Map execute() {
		Map m = new HashMap();
		m.put("in_customer_id", in_customer_id);
		m.put("in_project_id", in_project_id);
		m.put("in_value", in_value);
		return execute(m);
	}

	public void printMap(Map results) {
		for (Iterator it = results.entrySet().iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

}
