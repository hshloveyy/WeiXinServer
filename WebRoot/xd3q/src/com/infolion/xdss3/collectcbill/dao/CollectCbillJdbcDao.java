package com.infolion.xdss3.collectcbill.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
/**
 * @创建作者：邱杰烜
 * @创建日期：2010-08-25
 *
 */
@Repository
public class CollectCbillJdbcDao extends BaseDao {
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-24
	 * 获取原合同号
	 */
	public String getOldContractNo(String contract_no){
		String sql = "select old_contract_no from t_contract_sales_info where  contract_no = '" + contract_no + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			return (String)list.get(0).get("old_contract_no");
		}else{
			return "";
		}
	}
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-24
	 * 获取SAP订单号
	 */
	public String getSapOrderNo(String contract_no){
		String sql = "select sap_order_no from t_contract_sales_info where  contract_no = '" + contract_no + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			return (String)list.get(0).get("sap_order_no");
		}else{
			return "";
		}
	}
}
