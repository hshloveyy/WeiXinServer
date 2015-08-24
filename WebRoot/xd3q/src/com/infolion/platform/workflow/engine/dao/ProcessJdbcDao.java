/*
 * @(#)ProcessJdbcDao.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-7-23
 *  描　述：创建
 */

package com.infolion.platform.workflow.engine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.workflow.engine.domain.ExtendProcessInstance;
import com.infolion.platform.workflow.engine.domain.ExtendTaskInstance;


@Repository
public class ProcessJdbcDao extends BaseJdbcDao
{
	  public boolean taskNodeIsSignal(String businessId, long jbpmTaskInsId)
	  {
	    StringBuffer sb = new StringBuffer();
	    sb.append("select count(*) from  (select * from WF_TASKINS union select * From WF_TASKINS_O ) ");
	    sb.append(" where BUSINESSID=? and TASKID=?");
	    
	    int i = getJdbcTemplate().queryForInt(sb.toString(), new Object[] { businessId, Long.valueOf(jbpmTaskInsId) });
	    if (i > 0) {
	      return true;
	    }
	    return false;
	  }
	  
	  public boolean processIsSignal(String businessId)
	  {
	    StringBuffer sb = new StringBuffer();
	    sb.append("select count(*) from  WF_PROCESSINS ");
	    sb.append(" where BUSINESSID=?");
	    
	    int i = getJdbcTemplate().queryForInt(sb.toString(), new Object[] { businessId });
	    if (i > 0) {
	      return true;
	    }
	    return false;
	  }

	public void updateProcessInstanceEndState(long processId, String endNodeName)
	{
	    String strSql = "update WF_PROCESSINS set INSENDTIME='" + DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE) + "',ENDNODENAME=" + this.sqlSyntax.getQueryString() + "'" + endNodeName + "' where PROCESSID=" + String.valueOf(processId);
		getJdbcTemplate().update(strSql);
	}


	public void updateExtendProcessInstanceSubBusinessId(long processId, String primaryPropertyName, String subBusinessId)
	{    
		String strSql = "update WF_PROCESSINS set PROCESSURL=PROCESSURL||'&" + primaryPropertyName + "=" + subBusinessId + "' where PROCESSID=" + processId;
		getJdbcTemplate().update(strSql);
	}


	public void updateBusinessTableState(String tableName, String keyColumnName, String keyValue, String endNodeName)
	{   String strSql = "update " + tableName.toUpperCase() + " set PROCESSSTATE=" + this.sqlSyntax.getQueryString() + "'" + endNodeName + "' where " + keyColumnName.toUpperCase() + "='" + keyValue + "'";
     	getJdbcTemplate().update(strSql);
	}


	public BoInstance getBoInstance(long processId)
	{   
		return (BoInstance) getJdbcTemplate().queryForObject("select BUSINESSID,BOID from WF_PROCESSINS where PROCESSID=" + String.valueOf(processId), new RowMapper()
		{
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				BoInstance boInstance = new BoInstance();
				boInstance.setBusinessId(rs.getString("BUSINESSID"));
				boInstance.setBoId(rs.getString("BOID"));
				return boInstance;
			}
		});
	}


	@SuppressWarnings("unchecked")
	public String getBoIdByProcessId(long processId)
	{
		
		String result = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select b.BOID from  WF_PROCESSDEF a,WF_BOPROCESSDEF b ");
		sb.append(" where a.processdefinitionname=b.processdefinitionname ");
		sb.append("AND a.PROCESSID=? AND a.ACTIVE='Y'");

		List<String> list = this.getJdbcTemplate().queryForList(sb.toString(), new Long[] { processId }, String.class);

		if (list != null && list.size() == 1)
		{
			result = list.get(0);
		}
		else
		{
			throw new BusinessException("出错了，【根据流程processId，取得与之绑定的业务对象ID】查询出现问题，可能存在脏数据或者数据不存在。");
		}

		return result;
	}


	@Deprecated
	@SuppressWarnings("unchecked")
	public String getBoIdByProcessId_Deprecated(long processId, String processInsId)
	{   
		String result = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select b.BOID from  WF_PROCESSDEF a,WF_BOPROCESSDEF b ");
		sb.append(" where a.PROCESSDEFINITIONNAME=b.PROCESSDEFINITIONNAME ");
		sb.append("AND a.PROCESSID=? AND a.ACTIVE='Y'");

		List<String> list = this.getJdbcTemplate().queryForList(sb.toString(), new Long[] { processId }, String.class);

		if (list != null && list.size() == 1)
		{
			result = list.get(0);
		}
		else
		{
			sb = new StringBuffer();
			sb.append("select a.BOID from WF_PROCESSINS a ");
			sb.append(" where a.PROCESSINSID=?");
			list = this.getJdbcTemplate().queryForList(sb.toString(), new String[] { processInsId }, String.class);
			if (list != null && list.size() == 1)
			{
				result = list.get(0);
			}
			else
			{
				throw new BusinessException("出错了，【根据流程processId，取得与之绑定的业务对象ID】查询出现问题，可能存在脏数据或者数据不存在。");
			}
		}

		return result;
	}


	public List<ExtendTaskInstance> getExtTaskInstances(String businessRecordId)
	{
		List<ExtendTaskInstance> list = new ArrayList<ExtendTaskInstance>();
	    String sql = "select * from (select * from WF_TASKINS union select * From WF_TASKINS_O ) T where T.BUSINESSID='" + businessRecordId + "' or T.PARENTBUSINESSID='" + businessRecordId + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			ExtendTaskInstance extendTaskInstance = new ExtendTaskInstance();
			ExBeanUtils.setBeanValueFromMap(extendTaskInstance, map);
			list.add(extendTaskInstance);
		}

		return list;
	}
	
	  public List<ExtendTaskInstance> getExtTaskInstances2(String businessRecordId)
	  {
	    List<ExtendTaskInstance> list = new ArrayList();
	    String sql = "select T.TASKINSID,T.TASKID,T.PROCESSID,T.BUSINESSID,T.TASKNAME,T.TASKDESCRIPTION,T.TASKCREATETIME,T.TASKENDTIME,T.EXAMINE,U.REAL_NAME EXAMINEPERSON,T.EXAMINERESULT,T.WITHTIME,T.PARENTBUSINESSID,T.PARENTPROCESSID from (select * from WF_TASKINS union select * From WF_TASKINS_O ) T LEFT OUTER JOIN T_SYS_USER U ON T.EXAMINEPERSON = U.USER_NAME where T.BUSINESSID='" + businessRecordId + "' or T.PARENTBUSINESSID='" + businessRecordId + "'";
	    List<Map> rowList = getJdbcTemplate().queryForList(sql);
	    for (Map map : rowList)
	    {
	      ExtendTaskInstance extendTaskInstance = new ExtendTaskInstance();
	      ExBeanUtils.setBeanValueFromMap(extendTaskInstance, map);
	      list.add(extendTaskInstance);
	    }
	    return list;
	  }


	public List<ExtendProcessInstance> getProcessInstance(String businessId, long taskId)
	{
		String sql = "select * from WF_TASKACTOR T where T.NODEID = ?";
		final List<ExtendProcessInstance> result = new ArrayList<ExtendProcessInstance>();
		this.getJdbcTemplate().query(sql, new Object[] { businessId, taskId }, new RowCallbackHandler()
		{

			public void processRow(ResultSet rs) throws SQLException
			{
				ExtendProcessInstance extProcessIns = new ExtendProcessInstance();
				extProcessIns.setBusinessId(rs.getString("BUSINESSID"));
				extProcessIns.setProcessUrl(rs.getString("PROCESSURL"));
				extProcessIns.setBoId(rs.getString("BOID"));
				extProcessIns.setProcessId(rs.getLong("PROCESSID"));
				extProcessIns.setInsCreateTime(rs.getString("INSCREATETIME"));
				extProcessIns.setTaskId(rs.getLong("TASK_ID"));
				extProcessIns.setTaskName(rs.getString("NAME_"));
				result.add(extProcessIns);

			}
		});
		return result;
	}

}
