package com.infolion.xdss3.masterData.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.xdss3.masterData.domain.Tcurr;
/**
 * <pre>
 * 汇率(Tcurr),JDBCDao 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 邱杰烜
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class TcurrJDBCDao extends BaseJdbcDao{
	
	/**
	 * 删除汇率数据
	 */
	public void delete()
	{
		String sql = "delete from YTCURR";
		this.getJdbcTemplate().execute(sql);	
	}
	
	/**
	 * 插入汇率数据
	 * @param tcurr
	 */
	public void insert(Tcurr tcurr){
		String uId = CodeGenerator.getUUID();
		tcurr.setTcurrid(uId);
		
		String sql = "insert into YTCURR(MANDT,TCURRID,TCURR,FCURR,GDATU,UKURS) values('300', '" 
					 + uId
					 + "', '" + tcurr.getTcurr()
					 + "', '" + tcurr.getFcurr()
					 + "', '" + tcurr.getGdatu()
					 + "', '" + tcurr.getUkurs()
					 + "')";
		this.getJdbcTemplate().execute(sql);
	}
}
