package com.infolion.sapss.masterQuery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.ExcelObject;
@Repository
public class MasterQueryJdbcDao extends BaseDao{
	
	public void dealOutToExcel(String sql , final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[12];
				values[0] = rs.getString("material_code");
				values[1] = rs.getString("material_name");
				values[2] = rs.getString("FACTORY_D_FACTORY");
				values[3] = rs.getString("batch_no");
				values[4] = rs.getString("approved_time");
				values[5] = rs.getString("material_unit");
				values[6] = rs.getString("quantity");
				values[7] = rs.getString("in_total");
				values[8] = rs.getString("out");
				values[9] = rs.getString("out_total");
				values[10] = rs.getString("leave");
				values[11] = rs.getString("leave1");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}

	public List<Map<String, String>> getCustomerMaster(String customerCd,String customerName,String salerOrg){
		String sql = "select cm.guest_code kunnr,cm.guest_name name1,cm.country LAND1,cm.area ORT01,cm.vat_num PSTLZ, "+
			" cm.address STRAS,cm.phone TELF1,cm.phone_ext TELF2,cm.fax TELFX,cm.email TELBX,cm.contact_man ZSABE, "+
			" cm.pay_terms ZTERM,cm.pay_way ZWELS,' ' BZIRK,cm.currency WAERS,' ' INCO1,' ' INCO2,cm.sap_suppliers_code KUNN4, "+
			" cm.sales_org VKORG from ycustomermaster cm ,ydeptbycusu dcs where dcs.guestcode = cm.guest_code and 1=1 " ;
			if(StringUtils.isNotBlank(customerCd)){
				sql = sql + "and	cm.guest_code like '%"+customerCd+"%'";
			}
			if(StringUtils.isNotBlank(customerName)){
				sql = sql + "and	cm.guest_name like '%"+customerName+"%'";
			}
			if(StringUtils.isNotBlank(salerOrg)){
				sql = sql + "and	dcs.gsber='"+salerOrg+"'";
			}
			List list = this.getJdbcTemplate().queryForList(sql);
			return list;
	}
	
	public List<Map<String, String>> getSupplierMaster(String supplierCd,String supplierName,String purchase_org){
		String sql = "select cm.suppliers_code LIFNR,cm.suppliers_name1 name1,cm.country LAND1,cm.area ORT01,cm.vat_num PSTLZ, " +
					"cm.street STRAS,cm.phone TELF1,cm.phone_ext TELF2,cm.fax TELFX,cm.email TELBX,cm.contact_man NAME2, " +
					"cm.pay_terms ZTERM,cm.pay_way ZWELS,' ' BZIRK,cm.currency WAERS,' ' INCO1,' ' INCO2,cm.sap_guest_code KUNN4, " +
					"cm.purchase_org EKORG from ysuppliermaster cm ,ydeptbycusu dcs where dcs.suppliercode = cm.suppliers_code and 1=1 " ;
			if(StringUtils.isNotBlank(supplierCd)){
				sql = sql + "and	cm.suppliers_code like '%"+supplierCd+"%'";
			}
			if(StringUtils.isNotBlank(supplierName)){
				sql = sql + "and	cm.suppliers_name1 like '%"+supplierName+"%'";
			}
			if(StringUtils.isNotBlank(purchase_org)){
				sql = sql + "and	dcs.gsber='"+purchase_org+"'";
			}
			List list = this.getJdbcTemplate().queryForList(sql);
			return list;
	}
	
}
