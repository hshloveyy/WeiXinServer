package com.infolion.sapss.asset.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

@Repository
public class AssetJdbcDao extends BaseDao{
	
	
	public void updateUserHis(String assetUserHisId,
			String strColName, String strColValue){
		String strSql =" update T_ASSET_USERHIS " +
			"set " + strColName + "='" + strColValue + 
			"' where ASSETUSERHISID = ?";		
		getJdbcTemplate().update(strSql,new Object[]{assetUserHisId});
	}
	
	public void updateMaintain(String assetMaintainId,
			String strColName, String strColValue){
		String strSql =" update t_asset_maintain " +
			"set " + strColName + "='" + strColValue + 
			"' where ASSETMAINTAINID = ?";		
		getJdbcTemplate().update(strSql,new Object[]{assetMaintainId});
	}
	
	
	public void deleteUserHis(String assetUserHisId){
		String delSql = "delete T_ASSET_USERHIS a where a.ASSETUSERHISID = ?";
		getJdbcTemplate().update(delSql,new Object[]{assetUserHisId});
	}
	
	public void deleteMaintain(String assetMaintainId){
		String delSql = "delete t_asset_maintain a where a.ASSETMAINTAINID = ?";
		getJdbcTemplate().update(delSql,new Object[]{assetMaintainId});
	}
	
	public List queryDepts(String pdeptid){
		String sql = "select DEPT_ID,DEPT_NAME FROM T_SYS_DEPT WHERE PDEPT_ID ='"+pdeptid+"' OR DEPT_ID='"+pdeptid+"' ORDER BY CREATE_TIME ";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public String queryUserMan(String assetInfoId){
		final List<String> returnFalg = new ArrayList();
		String sql = "select * from (select userman from t_asset_userhis where assetInfoid=? order by startdate desc) where rownum=1";
		getJdbcTemplate().query(sql, new Object[]{assetInfoId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add(rs.getString("userman"));
			}
		});
		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}

}
