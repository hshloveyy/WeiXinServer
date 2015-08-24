/*
 * @(#)BillApplyJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.ExcelObject;

/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class CreditHisQueryJdbcDao extends BaseDao
{	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[25];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("DEPT_NAME");
				values[2] = rs.getString("BUSINESS_TYPE");
				values[3] = rs.getString("PROJECT_NO");
				values[4] = rs.getString("CONTRACT_NO");
				values[5] = rs.getString("EKKO_UNSEZ");
				values[6] = rs.getString("MATERIAL_GROUP");
				values[7] = rs.getString("BENEFIT");
				values[8] = rs.getString("CUSTOMER_LINK_MAN");
				values[9] = rs.getString("CREDIT_INFO");
				values[10] = rs.getString("CREATE_DATE");				
				values[11] = rs.getString("CREATE_BANK");
				values[12] = rs.getString("CREDIT_NO");
				values[13] = rs.getString("AVAIL_DATE");
				values[14] = rs.getString("CURRENCY");
				values[15] = rs.getString("AMOUNT");
				values[16] = rs.getString("USDAMOUNT");
				values[17] = rs.getString("LOADING_PERIOD");
				values[18] = rs.getString("VALID_DATE");
				values[19] = rs.getString("CREDIT_STATE");
				values[20] = rs.getString("PICK_LIST_TOTAL");
				values[21] = rs.getString("AMOUNT_PICKTOTAL");
				values[22] = rs.getString("USDAMOUNT_PICKTOTAL");
				values[23] = rs.getString("AMOUNT_PAYMENTTOTAL");
				values[24] = rs.getString("USDAMOUNT_PAYMENTTOTAL");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	public void dealOutToExcelPay(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[23];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("dept_name"); 
				values[2] = rs.getString("create_date"); 
				values[3] = rs.getString("create_bank"); 
				values[4] = rs.getString("credit_no"); 
				values[5] = rs.getString("project_no"); 
				values[6] = rs.getString("amount"); 
				values[7] = rs.getString("currency"); 
				values[8] = rs.getString("esTitle"); 
				values[9] = rs.getString("pick_list_no"); 
				values[10] = rs.getString("acceptance_date"); 
				values[11] = rs.getString("pay_date"); 
				values[12] = rs.getString("paymentno"); 
				values[13] = rs.getString("ptTitle");
				values[14] = rs.getString("bsTitle");
				values[15] = rs.getString("ppaydate");
				values[16] = rs.getString("pcurrency");
				values[17] = rs.getString("documentarydate");
				values[18] = rs.getString("applyamount");
				values[19] = rs.getString("collectbankid");
				values[20] = rs.getString("documentarylimit");
				values[21] = rs.getString("doctaryinterest");
				values[22] = rs.getString("documentaryrate");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
}
