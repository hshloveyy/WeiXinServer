package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.Hkont;
import com.infolion.xdss3.masterData.domain.MatGroup;

@Repository
public class MatGroupJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除物料组数据
	 */
	public void delete()
	{
		String sql = "delete from YMATGROUP";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入物料组数据
	 * @param hkont
	 */
	public void insert(MatGroup matGroup)
	{
		String sql = "insert into YMATGROUP(MANDT,MATKL,WGBEZ) values( '800', '"
			 + matGroup.getMatkl() + "', '" + matGroup.getWgbez() +  "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
