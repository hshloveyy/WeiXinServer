package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.CostCenter;
import com.infolion.xdss3.masterData.domain.Hkont;
import com.infolion.xdss3.masterData.domain.Prctr;

@Repository
public class PrctrJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除利润中心数据
	 */
	public void delete()
	{
		String sql = "delete from YPRCTR";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入成本中心
	 * @param hkont
	 */
	public void insert(Prctr prctr)
	{
		String uId = CodeGenerator.getUUID();
		String sql = "insert into YPRCTR(MANDT,PRCTRID,KOKRS,BUKRS,PRCTR,DATBI,DATAB,KHINR,KTEXT) values( '800', '"
			 + uId
			 + "', '" + prctr.getKokrs()
			 + "', '" + prctr.getBukrs() 
			 + "', '" + prctr.getPrctr() 
			 + "', '" + prctr.getDatbi() 
			 + "', '" + prctr.getDatab() 
			 + "', '" + prctr.getKhinr() 
			 + "', '" + prctr.getKtext() + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
