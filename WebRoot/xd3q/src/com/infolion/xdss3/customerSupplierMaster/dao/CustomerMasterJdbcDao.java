package com.infolion.xdss3.customerSupplierMaster.dao;




import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.mainData.domain.TGuest;

@Repository
public class CustomerMasterJdbcDao  extends BaseDao{
	/**
	 * 删除物料主数据的关联信息
	 * @param paymentId
	 */
    public int delete(String customerMaster_ID)
    {
    	String sql = "update ycustomerMaster set is_available='0' where customerMaster_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{customerMaster_ID});
    }
    /**
     * 通过编号查找是否存在的数据
     * @param no
     * @return
     */
    public String getMasterByNo(String no){
    	String sql = "select cm.customermaster_id from ycustomermaster cm where cm.guest_code='"+no+"'";
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);    	
		for (Map map : rowList)
		{
			return map.get("customermaster_id").toString();
		}
		return "";
    }
    public String getTGuestBycode(String guestCode){
    	String sql = "select guest_id from (select  *  from t_guest g where g.is_available='1' and g.guest_code ='"+guestCode+"'  order by g.approved_time desc) where  rownum =1";
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);    	
		for (Map map : rowList)
		{
			return map.get("guest_id").toString();
		}
		return "";
	}
    public int updateDeptNo(String newno,String oldno)
    {
    	String sql = "update ydeptbycusu dc set dc.guestcode=? where dc.guestcode=?";
		return this.getJdbcTemplate().update(sql, new Object[]{newno,oldno});
    }
    
}