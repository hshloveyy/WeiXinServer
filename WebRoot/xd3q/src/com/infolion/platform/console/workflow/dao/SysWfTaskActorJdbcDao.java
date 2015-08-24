/*
 * @(#)SysWfTaskActorJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-24
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.component.workflow.ext.CommonTaskInstance;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.console.workflow.domain.JbpmTask;
import com.infolion.platform.console.workflow.domain.SysWfTaskActor;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;

@Repository
public class SysWfTaskActorJdbcDao extends BaseDao
{
	/**
	 * 取的流程的ID,Name给前台的ComboBox
	 * 
	 * @return
	 */
	public List<ComboBoxFormat> queryProcessForComboBox()
	{
		final List<ComboBoxFormat> comboBoxFormatList = new ArrayList();
		final List<String> flowName = new ArrayList();

		String strSql = "select a.name_ from jbpm_processdefinition a group by a.name_";

		String strSqlVesion = "select a.id_, a.name_ " + "from jbpm_processdefinition a " + "where a.name_ = ? " + "and a.version_ = (select max(a.version_) " + "from jbpm_processdefinition a " + "where a.name_ = ?)";

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				flowName.add(rs.getString("name_"));
			}
		});

		for (int i = 0; i < flowName.size(); i++)
		{
			getJdbcTemplate().query(strSqlVesion, new Object[] { flowName.get(i), flowName.get(i) }, new RowCallbackHandler()
			{
				public void processRow(ResultSet rs) throws SQLException
				{
					ComboBoxFormat comboBoxFormat = new ComboBoxFormat();
					comboBoxFormat.setId(rs.getString("id_"));
					comboBoxFormat.setText(rs.getString("name_"));
					comboBoxFormat.setNick("");

					comboBoxFormatList.add(comboBoxFormat);
				}
			});
		}

		return comboBoxFormatList;
	}

	/**
	 * 取的流程节点的ID,Name给前台的ComboBox
	 * 
	 * @param in_strProcessId
	 * @return
	 */
	public List<ComboBoxFormat> queryProcessNodeForComboBox(String in_strProcessId)
	{
		final List<ComboBoxFormat> comboBoxFormatList = new ArrayList();
		String strSql = "select a.* " + "from jbpm_task a where processdefinition_ = ?";

		getJdbcTemplate().query(strSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ComboBoxFormat comboBoxFormat = new ComboBoxFormat();
				comboBoxFormat.setId(rs.getString("id_"));
				comboBoxFormat.setText(rs.getString("name_"));
				comboBoxFormat.setNick("");

				comboBoxFormatList.add(comboBoxFormat);
			}
		});

		return comboBoxFormatList;
	}

	/**
	 * 取的流程版本给前台的ComboBox
	 * 
	 * @param in_strProcessId
	 * @return
	 */
	public List<ComboBoxFormat> queryProcessVerForComboBox(String in_strProcessId)
	{
		final List<ComboBoxFormat> comboBoxFormatList = new ArrayList();
		String strSql = "select id_,version_ from jbpm_processdefinition where name_ = (select name_ from jbpm_processdefinition where id_=?) order by version_";

		getJdbcTemplate().query(strSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				ComboBoxFormat comboBoxFormat = new ComboBoxFormat();
				comboBoxFormat.setId(rs.getString("id_"));
				comboBoxFormat.setText(rs.getString("version_"));
				comboBoxFormat.setNick("");

				comboBoxFormatList.add(comboBoxFormat);
			}
		});

		return comboBoxFormatList;
	}
	/**
	 * 取的流程的节点给前台的grid
	 * 
	 * @param in_strProcessId
	 * @return
	 */
	public List<JbpmTask> queryProcessNodeForGrid(String in_strProcessId, String in_strTaskId)
	{
		final List<JbpmTask> jbpmTaskList = new ArrayList();
		String strSql = "select a.id_, " + "a.name_ task_name, " + "a.description_, " + "a.processdefinition_, " + "b.name_ process_name, " + "(select count(z.task_actor_id) from t_sys_wf_task_actor z where z.process_def_id = b.id_ and z.task_def_id = a.id_) childcount " + "from jbpm_task a,jbpm_processdefinition b " + "where a.processdefinition_ = b.id_ " + "and b.id_ = ?";

		if (in_strTaskId != null && !"".equals(in_strTaskId))
		{
			strSql = strSql + " and a.id_=" + in_strTaskId;
		}

		getJdbcTemplate().query(strSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				JbpmTask jbpmTask = new JbpmTask();
				jbpmTask.setId(rs.getString("id_"));
				jbpmTask.setName(rs.getString("task_name"));
				jbpmTask.setDescription(rs.getString("description_"));
				jbpmTask.setProcessdefitionId(rs.getString("processdefinition_"));
				jbpmTask.setProcessdefitionName(rs.getString("process_name"));
				jbpmTask.setChildcount(rs.getInt("childcount"));
				jbpmTask.setShowText("配置");

				jbpmTaskList.add(jbpmTask);
			}
		});
		
		//判断节点配置信息		
		String decisionSql = "SELECT N.ID_,N.NAME_,N.DESCRIPTION_,'' process_name,N.PROCESSDEFINITION_ ,(select count(t.transitionid) from t_sys_wf_transition t  where t.nodeid=n.id_) childcount fROM JBPM_NODE N WHERE N.CLASS_='D' and N.PROCESSDEFINITION_ = ? and exists (select '' from jbpm_delegation where id_=N.DECISIONDELEGATION AND CLASSNAME_='com.infolion.sapss.workflow.XDSSCommonDecisionHandler')";
		getJdbcTemplate().query(decisionSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				JbpmTask jbpmTask = new JbpmTask();
				jbpmTask.setId(rs.getString("id_"));
				jbpmTask.setName(rs.getString("NAME_"));
				jbpmTask.setDescription(rs.getString("description_"));
				jbpmTask.setProcessdefitionId(rs.getString("processdefinition_"));
				jbpmTask.setProcessdefitionName("判断");
				jbpmTask.setChildcount(rs.getInt("childcount"));
				jbpmTask.setShowText("判断配置");

				jbpmTaskList.add(jbpmTask);
			}
		});
		//ACTION节点配置信息		
		String actionSql = "SELECT N.ID_,N.NAME_,N.DESCRIPTION_,'' process_name,N.PROCESSDEFINITION_ ,(select count(t.actionID) from t_sys_wf_sqlaction t  where t.nodeid=n.id_) childcount fROM JBPM_NODE N WHERE N.PROCESSDEFINITION_ = ? and exists (select '' from jbpm_delegation d,jbpm_action a,jbpm_event e where d.id_=a.actiondelegation_ and a.event_=e.id_ and e.node_=n.id_ and d.classname_='com.infolion.sapss.workflow.XDSSCommonActionHandler')";
		getJdbcTemplate().query(actionSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				JbpmTask jbpmTask = new JbpmTask();
				jbpmTask.setId(rs.getString("id_"));
				jbpmTask.setName(rs.getString("NAME_"));
				jbpmTask.setDescription(rs.getString("description_"));
				jbpmTask.setProcessdefitionId(rs.getString("processdefinition_"));
				jbpmTask.setProcessdefitionName("动作");
				jbpmTask.setChildcount(rs.getInt("childcount"));
				jbpmTask.setShowText("动作配置");

				jbpmTaskList.add(jbpmTask);
			}
		});
		return jbpmTaskList;
	}

	/**
	 * 增加节点参与者信息
	 * 
	 * @param in_sysWfTaskActor
	 */
	public void addTaskActor(SysWfTaskActor in_sysWfTaskActor)
	{
		String strSelectSql = "select count(a.task_actor_id) from t_sys_wf_task_actor a " + "where process_def_id = ? " + "and a.task_def_id = ? " + "and a.actor_type = ? " + "and a.actor_id = ?";
		String strInsertSql = "insert into t_sys_wf_task_actor( " + "task_actor_id, " + "process_def_id, " + "task_def_id, " + "actor_type, " + "actor_id, " + "actor_name, " + "creator, " + "create_time " + ") " + "values( " + "?,?,?,?,?,?,?,?)";
		String[] IdList = in_sysWfTaskActor.getActorId().split("\\|");
		String[] NameList = in_sysWfTaskActor.getActorName().split("\\|");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		for (int i = 0; i < IdList.length; i++)
		{
			Object[] selectparams = new Object[] { in_sysWfTaskActor.getProcessDefId(), in_sysWfTaskActor.getTaskDefId(), in_sysWfTaskActor.getActorType(), IdList[i] };

			int recordCount = getJdbcTemplate().queryForInt(strSelectSql, selectparams);

			if (recordCount <= 0)
			{
				Object[] insertparams = new Object[] { CodeGenerator.getUUID(), in_sysWfTaskActor.getProcessDefId(), in_sysWfTaskActor.getTaskDefId(), in_sysWfTaskActor.getActorType(), IdList[i], NameList[i], userContext.getSysUser().getUserId(), DateUtils.getCurrTime(DateUtils.DB_STORE_DATE) };

				getJdbcTemplate().update(strInsertSql, insertparams);
			}
		}
	}

	/**
	 * 根椐流程编号和节点编号查询参与人信息
	 * 
	 * @param in_strProcessId
	 * @param in_strTackId
	 * @return
	 */
	public List<SysWfTaskActor> queryTaskActorForGrid(String in_strProcessId, String in_strTackId)
	{
		final List<SysWfTaskActor> sysWfTaskActorList = new ArrayList();

		String strSql = "select a.task_actor_id, " + "a.actor_type, " + "a.actor_id, " + "a.actor_name, " + "a.creator, " + "a.create_time " + "from t_sys_wf_task_actor a " + "where a.process_def_id = ? " + "and a.task_def_id  = ?";

		Object[] selectparams = new Object[] { in_strProcessId, in_strTackId };

		getJdbcTemplate().query(strSql, selectparams, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysWfTaskActor sysWfTaskActor = new SysWfTaskActor();
				if (rs.getString("actor_type").equals("1"))
					sysWfTaskActor.setActorType("角色");
				if (rs.getString("actor_type").equals("2"))
					sysWfTaskActor.setActorType("人员");
				sysWfTaskActor.setActorId(rs.getString("actor_id"));
				sysWfTaskActor.setActorName(rs.getString("actor_name"));
				sysWfTaskActor.setCreateTime(rs.getString("create_time"));
				sysWfTaskActor.setTaskActorId(rs.getString("task_actor_id"));

				sysWfTaskActorList.add(sysWfTaskActor);
			}
		});

		return sysWfTaskActorList;
	}

	/**
	 * 删除流程参与者信息
	 * 
	 * @param in_strTaskActorIdList
	 */
	public void deleteTaskActor(String in_strTaskActorIdList)
	{
		String strSql = "delete t_sys_wf_task_actor where task_actor_id = ?";

		String[] IdList = in_strTaskActorIdList.split("\\|");

		for (int i = 0; i < IdList.length; i++)
		{
			getJdbcTemplate().update(strSql, new Object[] { IdList[i] });
		}
	}

	/**
	 * 取得当前未结束流程实例(待办事项)
	 * 
	 * @return
	 */
	public List<CommonProcessInstance> getMyProcessInstances(String in_strSql)
	{
		final List<CommonProcessInstance> result = new ArrayList<CommonProcessInstance>();

		getJdbcTemplate().query(in_strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				commonProcessInstance.setBusinessRecordId(rs.getString("BUSINESS_RECORD_ID"));
				commonProcessInstance.setProcessUrl(rs.getString("PROCESS_URL"));
				commonProcessInstance.setModelId(rs.getString("MODEL_ID"));
				commonProcessInstance.setModelName(rs.getString("MODEL_NAME"));
				commonProcessInstance.setProcessId(rs.getLong("PROCESS_ID"));
				commonProcessInstance.setStartTime(rs.getDate("START_"));
				commonProcessInstance.setTaskId(rs.getLong("TASK_ID"));
				commonProcessInstance.setTaskName(rs.getString("NAME_"));
				commonProcessInstance.setInsCreateTime(rs.getString("INS_CREATE_TIME"));
				commonProcessInstance.setBusinessNote(rs.getString("BUSINESS_NOTE"));
				commonProcessInstance.setExamineAndApprove("审批");
				commonProcessInstance.setWorkFlowState("流程状态");

				// TODO LJX20100430 XDSS3、BDP系统整合。
				commonProcessInstance.setBoId(rs.getString("BOID"));
				commonProcessInstance.setAssignLogic(rs.getString("ASSIGNLOGIC"));
				commonProcessInstance.setCommonProcessId(rs.getString("COMMONPROCESSID"));
				commonProcessInstance.setEndNodeName(rs.getString("ENDNODENAME"));
				commonProcessInstance.setInsEndTime(rs.getString("INSENDTIME"));
				commonProcessInstance.setNodeId(Long.parseLong(rs.getString("NODEID")));
				commonProcessInstance.setProcessType(rs.getString("PROCESSTYPE"));
				commonProcessInstance.setTaskCreateTime(rs.getString("TASKCREATETIME"));
				commonProcessInstance.setParentCommonProcessId(rs.getString("PARENTCOMMONPROCESSID"));
				commonProcessInstance.setExtProcessId(rs.getString("EXTPROCESSID"));

				result.add(commonProcessInstance);
			}
		});
		return result;
	}

	/**
	 * 取得历史流程记录
	 * 
	 * @return
	 */
	public List<CommonProcessInstance> getMyProcessInstancesHistory(String in_strSql)
	{
		final List<CommonProcessInstance> result = new ArrayList<CommonProcessInstance>();

		getJdbcTemplate().query(in_strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				CommonProcessInstance commonProcessInstance = new CommonProcessInstance();
				commonProcessInstance.setBusinessRecordId(rs.getString("BUSINESS_RECORD_ID"));
				commonProcessInstance.setModelId(rs.getString("MODEL_ID"));
				commonProcessInstance.setModelName(rs.getString("MODEL_NAME"));
				commonProcessInstance.setProcessId(rs.getLong("PROCESS_ID"));
				// commonProcessInstance.setStartTime(rs.getDate("START_"));
				// commonProcessInstance.setTaskId(rs.getLong("TASK_ID"));
				commonProcessInstance.setTaskName(rs.getString("NAME_"));
				commonProcessInstance.setBoId(rs.getString("BOID"));
				commonProcessInstance.setProcessType(rs.getString("PROCESSTYPE"));
				commonProcessInstance.setEndNodeName(rs.getString("END_NODE_NAME"));
				commonProcessInstance.setInsEndTime(rs.getString("INS_END_TIME"));
				commonProcessInstance.setInsCreateTime(rs.getString("INS_CREATE_TIME"));
				commonProcessInstance.setBusinessNote(rs.getString("BUSINESS_NOTE"));
				result.add(commonProcessInstance);
			}
		});

		return result;
	}
	
	public List getLeadTaskIns(String in_strSql){
		
		return getJdbcTemplate().queryForList(in_strSql);
	}

	/**
	 * 查找流程图的taskId
	 * 
	 * @param businessRecordId
	 * @return
	 */
	public String findWorkflowPictureTaskId(String businessRecordId)
	{
		String sql = "select t.id_ taskId from jbpm_taskinstance t,t_sys_wf_process_instance t1 where t.procinst_ = t1.process_id and t1.business_record_id=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { businessRecordId });
		if (list != null && list.size() > 0)
		{
			return ((BigDecimal) ((Map) list.get(0)).get("taskId")).toString();
		}
		else
			return "";
	}
	
	public void copyProcessConfig(String poid,String copoid){
		//操作者拷贝
		String strSql = "select a.id_,a.name_ from jbpm_task a where processdefinition_ = ?";
		final String sql1 = "select b.actor_type,b.actor_id,b.actor_name from jbpm_task a , t_sys_wf_task_actor b where a.processdefinition_=b.process_def_id and a.id_=b.task_def_id and a.processdefinition_= ? and a.name_= ?";
		final String fcopoid = copoid;
		final String fpoid = poid;		
		getJdbcTemplate().query(strSql, new Object[] { poid }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{   String id_ = rs.getString("id_");
			    String name_ = rs.getString("name_");
                List list = getJdbcTemplate().queryForList(sql1,new Object[] {fcopoid, name_});
                if (list != null && list.size() > 0){
                	getJdbcTemplate().update("delete from t_sys_wf_task_actor where PROCESS_DEF_ID=? and TASK_DEF_ID=?",new Object[] {fpoid,id_});
                	for(int i = 0 ;i<list.size();i++){
                		Map map = (Map) list.get(i);
                		String exesql = "insert into t_sys_wf_task_actor (TASK_ACTOR_ID,PROCESS_DEF_ID,TASK_DEF_ID,ACTOR_TYPE,ACTOR_ID,ACTOR_NAME,CREATOR,CREATE_TIME) values (?,?,?,?,?,?,?,?)";
                		getJdbcTemplate().update(exesql,new Object[] {CodeGenerator.getUUID(), fpoid,id_,map.get("ACTOR_TYPE"),map.get("ACTOR_ID"),map.get("ACTOR_NAME"),"system",DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)});
                	}
                }
			}
		});
		//判断和动作拷贝
		String insertActionSQL = "INSERT INTO t_sys_wf_sqlaction select SYS_GUID(), n1.id_,sa.actionsql,n1.processdefinition_,sa.actiontype From t_sys_wf_sqlaction sa "
				+ " left outer join jbpm_node n  on sa.nodeid=n.id_ left outer join jbpm_node n1 on n1.name_=n.name_ where sa.processdefid=? and n1.processdefinition_=?";
		getJdbcTemplate().update(insertActionSQL,new Object[] {copoid,poid});
		String insertDesitionSQL = "INSERT INTO t_sys_wf_transition  select SYS_GUID(),n1.id_,jt.name_,st.logicsql,n1.processdefinition_ from jbpm_transition jt "
				+ "left outer join jbpm_node n1 on  jt.from_=n1.id_ "
				+ "left outer join jbpm_node n on n1.name_=n.name_ "
				+ "left outer join t_sys_wf_transition st on st.nodeid=n.id_ "
				+ "where jt.name_=st.transitionname  and  st.processdefid=? and  n1.processdefinition_=?";
		getJdbcTemplate().update(insertDesitionSQL,new Object[] {copoid,poid});
	}
	public void reloadTransition(String nodeid){
		getJdbcTemplate().update("delete from t_sys_wf_transition where nodeid=? ",new Object[] {nodeid});
		getJdbcTemplate().update("INSERT INTO t_sys_wf_transition SELECT SYS_GUID(),FROM_,NAME_,'',PROCESSDEFINITION_ FROM jbpm_transition WHERE from_=?",new Object[] {nodeid});
	}
	
	public void deleteTransition(String tid){
		String delCaseSql = "delete t_sys_wf_transition a where a.TRANSITIONID = ?";		
		getJdbcTemplate().update(delCaseSql,new Object[]{tid});
	}
	public void updateTransition(String transitionId,String strColName,String strColValue){
		String strSql =" update t_sys_wf_transition  set " + strColName + "='" + strColValue + "' where TRANSITIONID = ?";		
		getJdbcTemplate().update(strSql,new Object[]{transitionId});
	}
	public List<Map> queryForAction(String nodeid){
		String strSql ="select actionid,actionsql,actiontype from t_sys_wf_sqlaction where nodeid='"+nodeid+"'";		
		return getJdbcTemplate().queryForList(strSql);
	}
	public void saveOrUpdateAction(String processid,String nodeid,String actionid,String actionSQL, String actionType){
		if(null!=actionid&&!"".equals(actionid)){
			String strSql ="update t_sys_wf_sqlaction set actionType=? , actionSQL=? where actionid=?";
			getJdbcTemplate().update(strSql, new Object[] {actionType,actionSQL,actionid});
		}else {
			String strSql ="insert into t_sys_wf_sqlaction(actionid,NODEID,ACTIONSQL,PROCESSDEFID,ACTIONTYPE) values(?,?,?,?,?)";
			getJdbcTemplate().update(strSql, new Object[] {CodeGenerator.getUUID(),nodeid,actionSQL,processid,actionType});
		}
	}
}
