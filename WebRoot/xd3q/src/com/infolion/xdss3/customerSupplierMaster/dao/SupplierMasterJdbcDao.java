package com.infolion.xdss3.customerSupplierMaster.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class SupplierMasterJdbcDao  extends BaseDao{
	/**
	 * 删除供应商主数据的关联信息
	 * @param paymentId
	 */
    public int delete(String supplierMaster_ID)
    {
    	String sql = "update ysupplierMaster set is_available='0' where supplierMaster_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{supplierMaster_ID});
    }
    /**
     * 通过编号查找是否存在的数据
     * @param no
     * @return
     */
    public String getMasterByNo(String no){
    	String sql = "select sm.suppliermaster_id from ysuppliermaster sm where sm.suppliers_code ='"+no+"'";
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);    	
    	for (Map map : rowList)
		{
			return map.get("suppliermaster_id").toString();
		}
		return "";
    }
    
    /**
     * 通过编号查找是否存在的数据
     * @param no
     * @return
     */
    public String getTSuppliersByCode(String code){
    	String sql = "select suppliers_id from (select  *  from t_suppliers g where g.is_available='1' and g.suppliers_code ='"+code+"'  order by g.approved_time desc)  where  rownum =1";
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);    	
    	for (Map map : rowList)
		{
			return map.get("suppliers_id").toString();
		}
		return "";
    }
    public int updateDeptNo(String newno,String oldno)
    {
    	String sql = "update ydeptbycusu dc set dc.suppliercode=? where dc.suppliercode=?";
		return this.getJdbcTemplate().update(sql, new Object[]{newno,oldno});
    }
}
