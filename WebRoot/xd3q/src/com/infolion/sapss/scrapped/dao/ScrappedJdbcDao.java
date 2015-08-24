/*
 * @(#)ScrappedJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.scrapped.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.scrapped.domain.TScrapped;
@Repository
public class ScrappedJdbcDao extends BaseDao {
	/**
	 * 更新物料字段
	 * @param scrappedMaterialId
	 * @param colName
	 * @param colValue
	 */
	public int updateMaterial(String scrappedMaterialId, String colName,
			String colValue) {
		String sql="update t_scrapped_material set "+colName+"=? where scrapped_material_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{colValue,scrappedMaterialId});
	}
	/**
	 * 删除报废记录
	 * @param scrappedId
	 */
	public int delScrapped(String scrappedId) {
		String sql="update t_scrapped set is_available='0' where scrapped_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{scrappedId});
	}
	/**
	 * 删除物料
	 * @param materialId
	 */
	public int delMaterial(String materialId) {
		String sql="delete t_scrapped_material where scrapped_material_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{materialId});
	}
	/**
	 * 更新状态
	 * @param ts
	 * @param string
	 */
	public int updateState(TScrapped ts, String statusCode,boolean updateTime) {
		if(!updateTime){
			String sql="update t_scrapped set examine_state=? where scrapped_id=?";
			return this.getJdbcTemplate().update(sql, new Object[]{statusCode,ts.getScrappedId()});
		}else{
			String sql="update t_scrapped set examine_state=?,approved_time=? where scrapped_id=?";
			return this.getJdbcTemplate().update(sql, new Object[]{statusCode,DateUtils.getTimeStr(new Date(), 2),ts.getScrappedId()});
		}
			
	}
	/**
	 * 统计每笔报废的金额
	 * @param scrappedId
	 * @return
	 */
	public int sumMoney(String scrappedId) {
		String sql="select sum(money) total from t_scrapped_material where scrapped_id=?";
		return this.getJdbcTemplate().queryForInt(sql, new Object[]{scrappedId});
	}

}
