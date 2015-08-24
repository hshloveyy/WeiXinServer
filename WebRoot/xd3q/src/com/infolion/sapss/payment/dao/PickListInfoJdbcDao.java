/*
 * @(#)PickListInfoJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.core.dao.BaseHibernateDao;
import com.infolion.sapss.payment.domain.TPickListInfo;

@Repository
public class PickListInfoJdbcDao extends BaseDao
{
	/**
	 * 更新物料信息
	 * @param purchaseRowsId
	 * @param colName
	 * @param colValue
	 */
	public void updateMateriel(String purchaseRowsId, String colName,
			String colValue)
	{
		String sql = " update t_payment_imports_material " + "set " + colName
				+ "='" + colValue + "' where purchase_rows_id = ?";

		getJdbcTemplate().update(sql, new Object[] { purchaseRowsId });

	}
	
	public boolean isExistBillNo(String pickListId,String pickListNo){
		String sql = "select count(*) from t_pick_list_info where pick_list_id <>'"+pickListId+"' and pick_list_no='"+pickListNo+"' and is_available='1'";
		int i = getJdbcTemplate().queryForInt(sql);
		if(i>0) return true;
		else return false;
	}
}
