/*
 * @(#)JdPurchaseJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.investment.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.investment.domain.TInvestmentInfo;
@Repository
public class InvestmentJdbcDao extends BaseDao{
	/**
	 * 删除
	 * @param purchaseId
	 * @return
	 */
	public int delInfo(String id) {
		String sql ="update T_investment_info set is_available='0' where info_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{id});
	}
	/**
	 * 更新状态
	 * @param ts
	 * @param string
	 * @param b
	 */
	public void updateState(TInvestmentInfo ts, String status, boolean b) {
		if(!b){
			String sql="update T_investment_info set status=? where info_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,ts.getInfoId()});
		}else{
			String sql="update T_investment_info set status=?,approved_time=?,available_time=? where info_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,DateUtils.getTimeStr(new Date(), 2),DateUtils.getTimeStr(new Date(), 2),ts.getInfoId()});
		}
	}

}
