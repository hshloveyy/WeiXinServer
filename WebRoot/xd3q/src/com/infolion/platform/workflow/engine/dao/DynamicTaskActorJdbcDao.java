/*
 * @(#)DynamicTaskActorJdbcDao.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-10-20
 *  描　述：创建
 */

package com.infolion.platform.workflow.engine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class DynamicTaskActorJdbcDao extends BaseJdbcDao
{

	public Set getDyncActor(String dyncQuerySql)
	{
		final Set actors = new HashSet();
		this.getJdbcTemplate().query(dyncQuerySql, new RowMapper()
		{
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String actorId = rs.getString(1);
				actors.add(actorId);
				return null;
			}
		});
		return actors;
	}

	public void deleteDynamicTaskActorByTaskId(long taskinsId)
	{
		String sql = "delete from WF_DYNATASKACTOR  where  taskinsId = '" + taskinsId + "' ";
		this.getJdbcTemplate().update(sql);
	}
	
	public void deleteDynamicTaskActorByBusinessId(String businessid)
	{
		String sql = "delete from WF_DYNATASKACTOR  where  businessid = '" + businessid + "' ";
		this.getJdbcTemplate().update(sql);
	}
}
