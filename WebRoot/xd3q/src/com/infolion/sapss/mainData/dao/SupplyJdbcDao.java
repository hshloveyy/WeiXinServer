/*
 * @(#)PaymentContractJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张小军
 *  时　间：JUN 9, 2009
 *  描　述：创建
 */
package com.infolion.sapss.mainData.dao;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.mainData.domain.TMaterial;

public class SupplyJdbcDao  extends BaseDao{
	/**
	 * 删除供应商主数据的关联信息
	 * @param paymentId
	 */
    public int delete(String guestID)
    {
    	String sql = "update T_SUPPLIERS set is_available='0' where SUPPLIERS_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{guestID});
    }
}
