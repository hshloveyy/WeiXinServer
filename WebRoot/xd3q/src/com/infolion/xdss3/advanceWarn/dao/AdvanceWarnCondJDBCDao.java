package com.infolion.xdss3.advanceWarn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

@Repository
public class AdvanceWarnCondJDBCDao extends BaseJdbcDao{


	//private  String rpSql = new String();
	
	/**
	 * 判断是否需要预警，需要预警则返回对象列表
	 * @param dealFunction: 处理函数
	 * @param keyField：		关键属性
	 * @param sql：			查询SQL
	 * @return 需要预警对象（关键属性的值）
	 * @throws SQLException 
	 */
	public List<Map<String,String>> getAlarmList(String dealFunction, String keyField, String sql) throws SQLException
	{		
		List<Map<String,String>> lstRet = this.getJdbcTemplate().queryForList(sql);
		List<Map<String,String>> alarmList = new ArrayList<Map<String,String>>();		
		alarmList.clear();
		
		// 没有配置处理函数
		if( dealFunction == null || dealFunction.equals("") )
		{
			for(Map map : lstRet)
			{
				alarmList.add(map);
			}
			return alarmList;
		}

		//遍历查询结果集，然后通过处理函数判断
		for(Map map : lstRet)
		{
			// 调用处理函数
			String functionSql = "select " + dealFunction + "('" + map.get("primarykey") + "') from dual" ;
			int iRet = this.getJdbcTemplate().queryForInt(functionSql);
				
			// 满足条件
			if(iRet == 1)
			{
				alarmList.add(map);
			}
		}		
		return alarmList;
	}
	
	/**
	 * 根据预警内容替换
	 * @param warnInfo
	 * @return
	 */
	public String getAlarmInfo(String warnInfo, String tableName, String primaryKeyField, String primaryKeyValue)
	{
		String sql = "select '''' || replace(replace("+ "'" + warnInfo +"'" +",'{','''||'),'}','||''') || '''' replace_text from dual";
		
		List<Map<String,String>> infoList = this.getJdbcTemplate().queryForList(sql);
		String rpSql = infoList.get(0).get("replace_text");
		
		String strPrimaryKey = primaryKeyField.substring(primaryKeyField.indexOf("[")+1,
				primaryKeyField.indexOf("]") );
		
		String sql2 = "select "+  rpSql + " warninfo from "+ tableName + " a where " + strPrimaryKey + "= '" + primaryKeyValue + "' ";
		
		List<Map<String,String>> infoList0 = this.getJdbcTemplate().queryForList(sql2);
		
		rpSql = infoList0.get(0).get("warninfo");		
		return rpSql;
	}
	
	/**
	 * 判断预警接收人的部门是否正确
	 * @param deptid
	 * @param receiverid
	 * @return
	 */
	public boolean checkUserDepid(String deptid, String receiverid,String warnid )
	{
		String sql = "select count(*) from YADVAWARNRECE where RECEIVEUSERID = '" 
			+ receiverid + "' and CREATEDEPTID = '" + deptid + "' and WARNID = '" + warnid + "'";
		int iCount = this.getJdbcTemplate().queryForInt(sql);
		return iCount > 0 ? true : false;
	}
	
	/**
	 * 获得需要再次预警的预警信息对象列表
	 * @return
	 */
	public List<Map<String, String>> getNeedAgainWarnInfo()
	{
		String sql = "select * from YADVAWARNINFO where (AGAINWARNTIME <> 0) and ( replace(CLOSETIME,' ', '') is not null ) and sysdate >= (to_date(trim(CLOSETIME), 'yyyymmdd') + AGAINWARNTIME)";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
