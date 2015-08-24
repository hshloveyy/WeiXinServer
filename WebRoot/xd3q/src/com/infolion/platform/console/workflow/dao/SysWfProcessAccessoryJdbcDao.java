/*
 * @(#)SysWfProcessAccessoryJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-5
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.domain.JbpmProcessdefinition;
import com.infolion.platform.console.workflow.domain.SysWfProcessAccessory;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;

@Repository
public class SysWfProcessAccessoryJdbcDao extends BaseDao
{
	/**
	 * 通过processid查询该流程实例的附件列表
	 * 
	 * @param in_strProcessId
	 * @return
	 */
	public List<SysWfProcessAccessory> queryProcessAccessory(String in_strProcessId)
	{
		final List<SysWfProcessAccessory> accessoryList = new ArrayList();

		String strSql = "select a.*,d.dept_name,c.user_name " + "from t_sys_wf_process_accessory a, " + "t_sys_dept_user            b, " + "t_sys_user                 c, " + "t_sys_dept                 d " + "where a.process_id = ? " + "and a.creator = b.dept_user_id " + "and b.user_id = c.user_id " + "and b.dept_id = d.dept_id";

		getJdbcTemplate().query(strSql, new Object[] { in_strProcessId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				SysWfProcessAccessory sysWfProcessAccessory = new SysWfProcessAccessory();
				sysWfProcessAccessory.setAccessoryId(rs.getString("accessory_id"));
				sysWfProcessAccessory.setProcessId(rs.getString("process_id"));
				sysWfProcessAccessory.setFileDescription(rs.getString("file_description"));
				sysWfProcessAccessory.setFilePath(rs.getString("file_path"));
				sysWfProcessAccessory.setOriginalFileName(rs.getString("original_file_name"));
				sysWfProcessAccessory.setNewFileName(rs.getString("new_file_name"));
				sysWfProcessAccessory.setUpdateDate(rs.getString("update_date"));
				sysWfProcessAccessory.setCreator(rs.getString("user_name"));
				accessoryList.add(sysWfProcessAccessory);
			}
		});

		return accessoryList;
	}

	/**
	 * 增加流程实例的附件
	 * 
	 * @param in_sysWfProcessAccessory
	 */
	public void addProcessAccessory(SysWfProcessAccessory in_sysWfProcessAccessory)
	{
		String strSql = "insert into t_sys_wf_process_accessory " + "(accessory_Id, " + "process_Id, " + "file_Description, " + "file_Path, " + "original_File_Name, " + "new_File_Name, " + "update_Date, " + "creator) " + "values " + "(?,?,?,?,?,?,?,?)";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		Object[] insertparams = new Object[] { CodeGenerator.getUUID(), in_sysWfProcessAccessory.getProcessId(), in_sysWfProcessAccessory.getFileDescription(), in_sysWfProcessAccessory.getFilePath(), in_sysWfProcessAccessory.getOriginalFileName(), in_sysWfProcessAccessory.getNewFileName(), DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), userContext.getSysUser().getUserId() };

		getJdbcTemplate().update(strSql, insertparams);
	}

	/**
	 * 删除流程实例的附件信息
	 * 
	 * @param in_accessoryIdList
	 */
	public void deleteProcessAccessory(String in_accessoryIdList)
	{
		String strSql = "delete t_sys_wf_process_accessory where accessory_id =?";

		String[] accessoryList = in_accessoryIdList.split("\\|");

		for (int i = 0; i < accessoryList.length; i++)
		{
			getJdbcTemplate().update(strSql, new Object[] { accessoryList[i] });
		}
	}

	/**
	 * 删除流程实例的附件信息(单条)
	 * 
	 * @param in_accessoryIdList
	 */
	public int delProcessAccessory(String fileName)
	{
		String sql = "delete t_sys_wf_process_accessory t where t.new_file_name=?";
		return getJdbcTemplate().update(sql, new Object[] { fileName });
	}

	/**
	 * 流得流程版本信息
	 * 
	 * @return
	 */
	public List<JbpmProcessdefinition> queryProcessDefinition()
	{
		final List<JbpmProcessdefinition> processList = new ArrayList();

		String strSql = "select a.* from jbpm_processdefinition a";

		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				JbpmProcessdefinition jbpmProcessdefinition = new JbpmProcessdefinition();
				jbpmProcessdefinition.setId_(rs.getString("id_"));
				jbpmProcessdefinition.setName_(rs.getString("name_"));
				jbpmProcessdefinition.setDescription_(rs.getString("description_"));
				processList.add(jbpmProcessdefinition);
			}
		});

		return processList;
	}
}
