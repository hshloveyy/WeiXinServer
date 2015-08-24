/*
 * @(#)ReportDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-10-23
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;

import edu.emory.mathcs.backport.java.util.Collections;


@Repository
public class ReportDao extends BaseDao{
	/**
	 * 
	 * @param saleContractId
	 * @return
	 */
	public List<Map> getSaleContract(Map<?,?> map){
		String sql="select * from t_project_info a left join t_contract_group b on a.project_id=b.project_id and b.is_available='1' "+
			"left join t_contract_sales_info c on b.contract_group_id=c.contract_group_id and c.is_available='1' "+
			"left join t_contract_sales_materiel d on c.contract_sales_id=d.contract_sales_id ";
		sql+=" where 1=1 ";
		if(map.get("contract_no")!=null)
			sql+=" and c.contract_no='"+map.get("contract_no")+"'";
		if(map.get("contract_group_no")!=null)
			sql+=" and b.contract_group_no='"+map.get("contract_group_no")+"'";
		if(map.get("project_no")!=null)
			sql+=" and a.project_no='"+map.get("project_no")+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	/**
	 * 
	 * @param saleContractId
	 * @return
	 */
	public List getShip(Map<?,?> map){
		String sql="select a.*,b.*,c.vbap_zmeng sale_row_quantity,c.*" ;
		sql+=" from t_ship_info a left join t_ship_material b on a.ship_id=b.ship_id,t_contract_sales_materiel c";
		sql+=" where a.contract_sales_id=?";
		sql+=" and b.sales_rows_id=c.sales_rows_id";
		sql+=" order by b.material_code";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{""});
		return list;
		
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List getPayMoney(Map<String, String> map) {
		String sql=
		"select a.project_no,a.project_name,b.contract_group_no,c.contract_no,c.ekko_unsez old_contract_no,d.ekpo_txz01,c.ekko_lifnr_name,c.trade_type,c.contract_purchase_id"+
		" from t_project_info a left join t_contract_group b on a.project_id=b.project_id and b.is_available='1'"+
		" left join t_contract_purchase_info c on b.contract_group_id = c.contract_group_id and c.is_available='1'"+
		" left join t_contract_purchase_materiel d on c.contract_purchase_id=d.contract_purchase_id";
		sql+=" where  1=1 ";
		if(map.get("contract_no")!=null)
			sql+=" and c.contract_no='"+map.get("contract_no")+"'";
		if(map.get("contract_group_no")!=null)
			sql+=" and b.contract_group_no='"+map.get("contract_group_no")+"'";
		if(map.get("project_no")!=null)
			sql+=" and a.project_no='"+map.get("project_no")+"'";

		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public String getMaxDate(Object purchaseId){
		String sql="select max(a.creator_time) max_date from t_payment_contract a  where a.contract_purchase_id=?";
		Map<String,String> r = this.getJdbcTemplate().queryForMap(sql,new Object[]{purchaseId});
		return r.get("max_date");
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public Object getSumValue(Object purchaseId){
		String sql=" select sum(a.pay_value) sum_pay_value from t_payment_contract a where a.contract_purchase_id=?";
		Map<String,Object> r = this.getJdbcTemplate().queryForMap(sql,new Object[]{purchaseId});
		return r.get("sum_pay_value");
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List getExportShipGoods(Map map) {
		String sql ="select * from v_export_ship_goods where contract_sales_id=?";
		if(map.get("materialCd")!=null){
			sql+=" and VBAP_MATNR='"+map.get("materialCd")+"'";
		}
		return this.getJdbcTemplate().queryForList(sql,new Object[]{map.get("saleId")});
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List getCreditPicklist(Map map) {
		String sql ="select * from v_credit_picklist where contract_sales_id=?";
		if(map.get("materialCd")!=null){
			sql+=" and VBAP_MATNR='"+map.get("materialCd")+"'";
		}
		return this.getJdbcTemplate().queryForList(sql,new Object[]{map.get("saleId")});
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List getDrawback(Map map) {
		String sql ="select * from v_drawback where contract_sales_id=?";
		if(map.get("materialCd")!=null){
			sql+=" and VBAP_MATNR='"+map.get("materialCd")+"'";
		}
		return this.getJdbcTemplate().queryForList(sql,new Object[]{map.get("saleId")});
	}
	/**
	 * 
	 * @param object
	 * @return
	 */
	public List getExportData(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 
	 * @param map
	 * @param cols
	 */
	public List getReceiptGoods(Map map, String cols) {
		String sql="select "+cols+" from v_export_receiptGoods where 1=1 ";
		if(map.get("contract_no")!=null)
			sql+=" and contract_no='"+map.get("contract_no")+"'";
		if(map.get("contract_group_no")!=null)
			sql+=" and contract_group_no='"+map.get("contract_group_no")+"'";
		if(map.get("project_no")!=null)
			sql+=" and project_no='"+map.get("project_no")+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 取得明细数据
	 * @param detailName
	 * @param map
	 * @param cols
	 * @return
	 */
	public List<?> getDetailData(boolean isTable,String detailName,Map map,String cols){
		String sql="";
		if(isTable)
			sql="select "+cols+" from t_v_"+detailName+" where 1=1 ";
		else
			sql="select "+cols+" from v_"+detailName+" where 1=1 ";
		if(map.get("contract_no")!=null)
			sql+=" and contract_no='"+map.get("contract_no")+"'";
		if(map.get("contract_group_no")!=null)
			sql+=" and contract_group_no='"+map.get("contract_group_no")+"'";
		if(map.get("project_no")!=null)
			sql+=" and project_no='"+map.get("project_no")+"'";
		
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";
		
		if (cols.indexOf("dept_name") != -1 || cols.indexOf("DEPT_NAME") != -1){
			sql = sql + " order by dept_name";
			
			if (cols.indexOf("contract_no") != -1 || cols.indexOf("contract_no") != -1){
				sql = sql + ",contract_no";
			}
		}
		
		return this.getJdbcTemplate().queryForList(sql);
	}
}
