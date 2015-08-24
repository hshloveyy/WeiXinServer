package com.infolion.sapss.receiptLog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.ExcelObject;

@Repository
public class ReceiptLogJdbcDao extends BaseDao{
	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[23];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("RECEIPT_NO");
				values[2] = rs.getString("WRITE_NO");
				values[3] = rs.getString("IMPORT_DATE");
				values[4] = rs.getString("CUSTOME_NO");
				values[5] = rs.getString("PRE_WR_CD");
				values[6] = rs.getString("CUSTOME_PRICE");
				values[7] = rs.getString("CUSTOME_CASH");
				values[8] = rs.getString("CUSTOME_PORT");
				values[9] = rs.getString("IMPORT_COUNTRY");
				values[10] = rs.getString("CUSTOME_TOTAL");
				values[11] = rs.getString("hgsb");
				values[12] = rs.getString("cjbb");
				values[13] = rs.getString("bgsl");
				values[14] = rs.getString("dj");
				values[15] = rs.getString("bgdw");
				values[16] = rs.getString("cjfs");
				values[17] = rs.getString("hxje");
				values[18] = rs.getString("hxrq");
				values[19] = rs.getString("thje");
				values[20] = rs.getString("thrq");
				values[21] = rs.getString("hxjd");
				values[22] = rs.getString("REMARK");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
}
