/*
 * @(#)SpreadSheetDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-3-4
 *  描　述：创建
 */

package com.infolion.XDSS.budget.sheet.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.AssertUtil;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.LobHandlerFactory;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.spreadsheet.SpreadsheetException;
import com.infolion.platform.spreadsheet.flex.ViewDef;

@Repository
public class BudgetSheetDao extends BaseJdbcDao
{
	/**
	 * 根据不同的数据库取得LobHandler
	 * 
	 * @return
	 */
	public LobHandler getLobHandler()
	{
		return LobHandlerFactory.getSourceDbLobHandler();
	}

	/**
	 * 根据预算分类ID取得Flex界面定义的二进制流并反序列化成ViewDef
	 * 
	 * @param budclassid
	 * @return
	 */
	public ViewDef getViewDef(String budclassid, String boId, String langIso)
	{
		String sql = "select FLEXSTORE from YBUDFLEXTEM where BUDSORTID=? and LANGUAGE=? and BOID=?";
		List<?> list = getJdbcTemplate().query(sql, new Object[] { budclassid, langIso, boId }, new RowMapper()
		{
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				byte[] bs = getLobHandler().getBlobAsBytes(rs, 1);
				try
				{
					ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
					return ois.readObject();
				}
				catch (IOException e)
				{
					throw new SpreadsheetException(e);
				}
				catch (ClassNotFoundException e)
				{
					throw new SpreadsheetException(e);
				}
			}
		});
		if (list.size() == 1)
		{
			return (ViewDef) list.get(0);
		}
		else if (list.size() > 1)
		{
			throw new SpreadsheetException("FLEXSTORE表出现脏数据：BUDSORTID=" + budclassid + ",LANGUAGE=" + langIso);
		}
		else
		{
			return null;
		}
	}

	/**
	 * 
	 * @param budsortid
	 * @param langIso
	 * @deprecated ISCHANGE字段废弃不用
	 * @return
	 */
	public String getChangeVersion(String budsortid, String langIso)
	{
		String sql = "select ISCHANGE from YBUDFLEXTEM where BUDSORTID=? and LANGUAGE=?";
		// String str = (String) this.getJdbcTemplate().queryForObject(sql, new
		// Object[] { budsortid, langIso }, String.class);
		List<String> list = this.getJdbcTemplate().queryForList(sql, new Object[] { budsortid, langIso }, String.class);
		if (list.size() <= 0)
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}

	/**
	 * 取得指定的Flex定义数据上次修改时间，如果找不到返回-1
	 * 
	 * @param budclassid
	 * @param langIso
	 * @return
	 */
	public long getLastModify(String budclassid, String boId, String langIso)
	{
		String sql = "select LASTMODIFY from YBUDFLEXTEM where BUDSORTID=? and LANGUAGE=? and BOID= ?";
		List<String> list = this.getJdbcTemplate().queryForList(sql, new Object[] { budclassid, langIso, boId }, String.class);
		long lastModify = -1;
		if (list.size() > 0)
		{
			String str = list.get(0);
			if (StringUtils.isNotBlank(str))
			{
				lastModify = Long.parseLong(str);
			}
		}
		return lastModify;
	}

	public void saveOrUpdateViewDef(final String budclassid, final String boId, ViewDef viewDef, final String langIso, final long lastModify)
	{
		AssertUtil.notNullBlank(budclassid, "budsortid");
		AssertUtil.notNullBlank(langIso, "langIso");

		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		if (viewDef != null)
		{
			try
			{
				ObjectOutputStream oos = new ObjectOutputStream(bao);
				oos.writeObject(viewDef);
			}
			catch (IOException e)
			{
				throw new SpreadsheetException("序列化Flex界面定义对象失败！", e);
			}
		}
		final byte[] binaryData = bao.toByteArray();

		String sql = "select ISCHANGE from YBUDFLEXTEM where BUDSORTID=? and LANGUAGE=? and BOID= ?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { budclassid, langIso, boId });
		if (list.size() > 0)
		{
			sql = "update YBUDFLEXTEM set FLEXSTORE= ?,LASTMODIFY= ? where BUDSORTID=? and LANGUAGE=? and BOID=?";
			getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(this.getLobHandler())
			{
				@Override
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException
				{
					lobCreator.setBlobAsBytes(ps, 1, binaryData);
					ps.setString(2, Long.toString(lastModify));
					ps.setString(3, budclassid);
					ps.setString(4, langIso);
					ps.setString(5, boId);
				}
			});
		}
		else
		{
			sql = "insert into YBUDFLEXTEM (BUDSORTID,LANGUAGE,LASTMODIFY,BOID,FLEXSTORE) values(?,?,?,?,?)";
			getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(this.getLobHandler())
			{
				@Override
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException
				{
					ps.setString(1, budclassid);
					ps.setString(2, langIso);
					ps.setString(3, Long.toString(lastModify));
					ps.setString(4, boId);
					lobCreator.setBlobAsBytes(ps, 5, binaryData);
				}
			});
		}
	}

	/**
	 * 汇总指定模板预算项
	 * 
	 * @param foreignKeyColumnName
	 *            子对象关系主对象的外键
	 * @param boInstIds
	 *            所有要汇总的业务实例ID组成的数组
	 * @param budtemitemid
	 *            模板预算项ID
	 * @param budTempItemColumnName
	 *            关联模板预算项的字段名
	 * @param sumProperties
	 *            要进行汇总的属性
	 * @param tableName
	 *            模板预算关联子对象的表名
	 * @return 返回属性名（非字段名）和汇总值的Map
	 */
	public Map<String, Object> summary(String foreignKeyColumnName, String[] boInstIds, String budtemitemid, String budTempItemColumnName, final Property[] sumProperties, String tableName)
	{
		StringBuffer sqlSb = new StringBuffer();
		// select SUM(t.要汇总的属性字段)... from tableName t where
		// t.budTempItemColumnName='budtemitemid' and foreignKeyColumnName
		// in(业务实例ID...)
		sqlSb.append("select ");
		for (Property sumProperty : sumProperties)
		{
			sqlSb.append("SUM(t.").append(StringUtils.upperCase(sumProperty.getColumnName())).append("),");
		}
		// 移除最后一个豆号
		sqlSb.setLength(sqlSb.length() - 1);
		sqlSb.append(" from ").append(StringUtils.upperCase(tableName)).append(" t");
		sqlSb.append(" where t.").append(StringUtils.upperCase(budTempItemColumnName)).append("='").append(budtemitemid).append("'");
		sqlSb.append(" and t.").append(StringUtils.upperCase(foreignKeyColumnName)).append(" in(");
		for (String boInstId : boInstIds)
		{
			sqlSb.append("'").append(boInstId).append("',");
		}
		// 移除最后一个豆号
		sqlSb.setLength(sqlSb.length() - 1);
		sqlSb.append(")");
		String sql = sqlSb.toString();
		log.debug("汇总SQL语句：" + sql);
		List list = this.getJdbcTemplate().query(sql, new RowMapper()
		{

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < sumProperties.length; i++)
				{
					Property sumProperty = sumProperties[i];
					map.put(sumProperty.getPropertyName(), rs.getObject(i + 1));
				}
				return map;
			}
		});
		if (list != null && list.size() > 0)
		{
			return (Map<String, Object>) list.get(0);
		}
		else
			return null;
	}

	/**
	 * 从预算组织取得所有主对象ID组成的数组
	 * 
	 * @param tableName
	 *            主对象表名
	 * @param idColumnName
	 *            主对象ID字段名
	 * @param budOrgColumnName
	 *            关联预算组织字段名
	 * @param budOrgIds
	 *            要查询的预算组织ID组成的数组
	 * @param budClassColumnName
	 *            关联预算分类字段名
	 * @param budClassId
	 *            预算分类ID
	 * @param whereSql
	 *            其它查询条件，可以为空
	 * @return 查询得到的主对象ID组成的数组
	 */
	public String[] getBusIdsByOrdId(String tableName, String idColumnName, String budOrgColumnName, String[] budOrgIds, String budClassColumnName, String budClassId, String whereSql)
	{
		StringBuffer sqlSb = new StringBuffer();
		idColumnName = StringUtils.upperCase(idColumnName);
		sqlSb.append("select ").append(idColumnName).append(" from ").append(StringUtils.upperCase(tableName));
		sqlSb.append(" where ").append(StringUtils.upperCase(budOrgColumnName)).append(" in (");
		for (String budOrgId : budOrgIds)
		{
			sqlSb.append("'").append(budOrgId).append("',");
		}
		sqlSb.setLength(sqlSb.length() - 1);
		sqlSb.append(") and ");
		sqlSb.append(StringUtils.upperCase(budClassColumnName)).append(" = '").append(budClassId).append("'");
		if (StringUtils.isNotBlank(whereSql))
		{
			sqlSb.append(" and ").append(whereSql);
		}
		String sql = sqlSb.toString();
		log.debug("从预算组织取得主对象ID SQL语句：" + sql);
		List list = this.getJdbcTemplate().queryForList(sql);
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			Map map = (Map) list.get(i);
			result[i] = (String) map.get(idColumnName);
		}
		return result;
	}

	/**
	 * 取得流程审批历史记录
	 * 
	 * @param boInstId
	 * @param processColumn
	 */
	public List<Map<String, Object>> getTaskHistory(String boInstId, String[] processColumn)
	{
		AssertUtil.notNullBlank(boInstId, "业务ID");
		if (processColumn == null || processColumn.length == 0)
		{
			throw new SpreadsheetException("processColumn不能为空");
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		for (String column : processColumn)
		{
			sqlBuffer.append(column).append(",");
		}
		sqlBuffer.setLength(sqlBuffer.length() - 1);
		sqlBuffer.append(" from WF_TASKINS where BUSINESSID=?");
		return this.getJdbcTemplate().queryForList(sqlBuffer.toString(), new Object[] { boInstId });
	}
}
