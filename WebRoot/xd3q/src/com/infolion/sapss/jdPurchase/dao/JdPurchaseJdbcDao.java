/*
 * @(#)JdPurchaseJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.jdPurchase.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.jdPurchase.domain.TJindaPurchase;
@Repository
public class JdPurchaseJdbcDao extends BaseDao{
	/**
	 * 删除
	 * @param purchaseId
	 * @return
	 */
	public int delPurchase(String purchaseId) {
		String sql ="update t_jinda_purchase set is_available='0' where purchase_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{purchaseId});
	}
	/**
	 * 更新状态
	 * @param ts
	 * @param string
	 * @param b
	 */
	public void updateState(TJindaPurchase ts, String status, boolean b) {
		if(!b){
			String sql="update T_JINDA_PURCHASE set examine_status=? where purchase_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,ts.getPurchaseId()});
		}else{
			String sql="update T_JINDA_PURCHASE set examine_status=?,approved_time=? where purchase_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,DateUtils.getTimeStr(new Date(), 2),ts.getPurchaseId()});
		}
	}

}
