package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class HkontJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除会计科目数据
	 */
	public void delete()
	{
		String sql = "delete from YSKAT";
		this.getJdbcTemplate().execute(sql);	
	}
	
	public void insert(Hkont hkont)
	{
		String sql = "insert into YSKAT(MANDT,BUKRS,SAKNR,KTOPL,TXT20) values( '800', '"
			 + hkont.getBukrs() + "', '" + hkont.getSaknr() + "', '" + hkont.getKtopl() 
			 + "', '"+ hkont.getTxt20() + "')";
		
		this.getJdbcTemplate().execute(sql);
	}
}
