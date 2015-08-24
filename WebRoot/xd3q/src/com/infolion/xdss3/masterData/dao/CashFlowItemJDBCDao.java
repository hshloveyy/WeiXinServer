package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class CashFlowItemJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除现金流量数据
	 */
	public void delete()
	{
		String sql = "delete from YT053S";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入现金流量项
	 * @param hkont
	 */
	public void insert(CashFlowItem cashFlowItem)
	{
		String sql = "insert into YT053S(MANDT,BUKRS,RSTGR,SPRAS,TXT40,TXT20) values( '800', '"
			 + cashFlowItem.getBukrs() + "', '" + cashFlowItem.getRstgr() + "', '" + cashFlowItem.getSpras() 
			 + "', '" + cashFlowItem.getTxt40() 
			 + "', '"+ cashFlowItem.getTxt20() + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
