/*
 * @(#)AttachementBusinessJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-14
 *  描　述：创建
 */

package com.infolion.platform.dpframework.uicomponent.attachement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.stereotype.Repository;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.uicomponent.attachement.domain.Attachement;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.LobHandlerFactory;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class AttachementJdbcDao extends BaseJdbcDao
{
	/**
	 * 根据ID列表删除业务附件
	 * 
	 * @param attachementIdList
	 * @return
	 */
	public int delete(String attachementIdList,String userId)
	{
		
		String sql = "DELETE FROM YATTACHEMENT WHERE ATTACHEMENTID in("+attachementIdList+") and creator='"+userId+"'";
		Integer vResult = this.getJdbcTemplate().update(sql);//.update(sql,null);

		return vResult;
	}

	/**
	 * 根据业务ID，删除业务附件
	 * 
	 * @param businessId
	 * @return
	 */
	public int deleteByBusinessId(String businessId)
	{
		String sql = "DELETE FROM YATTACHEMENT WHERE BUSINESSID =?";
		Integer vResult = this.getJdbcTemplate().update(sql, new Object[] { businessId });

		return vResult;
	}

	/**
	 * 根据业务ID，删除业务附件
	 * 
	 * @param businessId
	 * @return
	 */
	public int deleteByBusinessId(String businessId,String boname)
	{
		String sql = "DELETE FROM YATTACHEMENT WHERE BUSINESSID =? and boname = ? ";
		Integer vResult = this.getJdbcTemplate().update(sql, new Object[] { businessId , boname});

		return vResult;
	}
	
	/**
	 * 更新业务附件，业务ID
	 * 
	 * @param businessid
	 * @return
	 */
	public int update(String attachementId, String businessid)
	{
		String sql = "UPDATE YATTACHEMENT SET BUSINESSID=? WHERE  ATTACHEMENTID =?";
		Integer vResult = this.getJdbcTemplate().update(sql, new Object[] { businessid, attachementId });
		System.out.println("vResult: " + vResult.toString());
		return vResult;
	}

	/**
	 * 保存附件。
	 * 
	 * @param attachement
	 */
	public void updateAttachement(final Attachement attachement, final byte[] fileStore)
	{
		String sql = "INSERT INTO YATTACHEMENT(ATTACHEMENTID,BUSINESSID,BONAME,BOPROPERTY,FILENAME,DESCRIPTION,FILESTORE,MANDT,CREATOR,CREATETIME,LASTMODIFYOR,LASTMODIFYTIME)";
		sql += " values(?,?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(LobHandlerFactory.getSourceDbLobHandler())
		{
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException
			{
				ps.setString(1, attachement.getAttachementId());
				ps.setString(2, attachement.getBusinessId());
				ps.setString(3, attachement.getBoName());
				ps.setString(4, attachement.getBoProperty());
				ps.setString(5, attachement.getFileName());
				ps.setString(6, attachement.getDescription());
				lobCreator.setBlobAsBytes(ps, 7, fileStore);
				ps.setString(8, attachement.getClient());
				ps.setString(9, attachement.getCreator());
				ps.setString(10, attachement.getCreateTime());
				ps.setString(11, attachement.getLastModifyer());
				ps.setString(12, attachement.getLastModifyTime());
			}
		});
	}

	public void updateAttachement(final Attachement attachement)
	{
		String sql = "INSERT INTO YATTACHEMENT(ATTACHEMENTID,BUSINESSID,BONAME,BOPROPERTY,FILENAME,DESCRIPTION,MANDT,CREATOR,CREATETIME,LASTMODIFYOR,LASTMODIFYTIME)";
		sql += " values(?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().execute(sql, new AbstractLobCreatingPreparedStatementCallback(LobHandlerFactory.getSourceDbLobHandler())
		{
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException
			{
				ps.setString(1, attachement.getAttachementId());
				ps.setString(2, attachement.getBusinessId());
				ps.setString(3, attachement.getBoName());
				ps.setString(4, attachement.getBoProperty());
				ps.setString(5, attachement.getFileName());
				ps.setString(6, attachement.getDescription());
				ps.setString(7, attachement.getClient());
				ps.setString(8, attachement.getCreator());
				ps.setString(9, attachement.getCreateTime());
				ps.setString(10, attachement.getLastModifyer());
				ps.setString(11, attachement.getLastModifyTime());
			}
		});
	}
	
	/**
	 * 更新业务附件，业务ID
	 * 
	 * @param attachements
	 */
	public void update(Set<Attachement> attachements, String businessid)
	{
		String attachementIdList = "";

		Iterator<Attachement> it = attachements.iterator();

		while (it.hasNext())
		{
			Attachement attachement = it.next();
			String attachementId = "";
			attachementId = attachement.getAttachementId();
			attachementIdList = attachementIdList + "'" + attachementId + "',";
			this.update(attachementId, businessid);
		}
		// if (StringUtils.isNotBlank(attachementIdList))
		// {
		// attachementIdList = attachementIdList.substring(0,
		// attachementIdList.length() - 1);
		// }
	}
}
