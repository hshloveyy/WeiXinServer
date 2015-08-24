package com.infolion.platform.console.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.dao.BaseDao;
@Repository
public class SysWfUtilsJdbcDao extends BaseDao{
	
	public List<TaskHisDto> queryTaskHistList(String businessRecorderId){
		final List<TaskHisDto> list = new ArrayList<TaskHisDto>();
		String strSql = "select TASK_NAME,EXAMINE_DEPT_NAME,EXAMINE_PERSON,EXAMINE_RESULT,EXAMINE,TASK_CREATE_TIME,TASK_END_TIME" +
                        " from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id=? order by TASK_CREATE_TIME asc";
		
		getJdbcTemplate().query(strSql,new Object[]{businessRecorderId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TaskHisDto dto = new TaskHisDto();
			    dto.setTaskName(rs.getString("TASK_NAME"));
			    dto.setExamine(rs.getString("EXAMINE"));
			    dto.setExamineDeptName(rs.getString("EXAMINE_DEPT_NAME"));
			    dto.setExaminePerson(rs.getString("EXAMINE_PERSON"));
			    dto.setExamineResult(rs.getString("EXAMINE_RESULT"));
			    dto.setTaskCreateTime(rs.getString("TASK_CREATE_TIME"));
			    dto.setTaskEndTime(rs.getString("TASK_END_TIME"));
				list.add(dto);
			}});

		return list;
	}
	
	public String queryTransitionName(String businessid,long nodeid){
		String strSql = "select transitionname,logicsql from t_sys_wf_transition where nodeid=?";
		final String bid = businessid ;
		final List<String> returnList = new ArrayList<String>();
		getJdbcTemplate().query(strSql,new Object[]{nodeid}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String logicSQL = rs.getString("LOGICSQL");
				String transitionname = rs.getString("transitionname");				
				if(getJdbcTemplate().queryForInt(logicSQL, new Object[]{bid})>0) 
					returnList.add(transitionname);
			}});
		return returnList.size()>0?returnList.get(0):"";
	}
	
	public void dealActionHandler(String businessid,long nodeid){
		String strSql = "select actionsql,actiontype from t_sys_wf_sqlaction where nodeid=?";
		final String bid = businessid ;
		getJdbcTemplate().query(strSql,new Object[]{nodeid}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String actionsql = rs.getString("actionsql");
				String actiontype = rs.getString("actiontype");				
				if("1".equals(actiontype)){
					for(String sql : actionsql.split(";")){
						if(!"".equals(sql)&&null!=sql) getJdbcTemplate().update(sql, new Object[]{bid});
					}
				}else if("2".equals(actiontype)){
					String procedure = "{call "+actionsql+"('"+bid+"')}";  
		            getJdbcTemplate().execute(procedure);
				}
			}});
	}

}
