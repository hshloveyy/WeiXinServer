/*
 * @(#)MaterielHibernateDao.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-6-24
 *  描　述：创建
 */

package com.infolion.sample.orderManage.purchaseOrder.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.sample.orderManage.purchaseOrder.domain.Materiel;

/**
 * 
 * <pre>
 * 物料dao
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
public class MaterielJdbcDao extends BaseJdbcDao
{
	/**
	 * 取得物料信息
	 * 
	 * @param materielNo
	 * @return
	 */
	public Materiel getMateriel(final String materielNo)
	{
		final Materiel materiel = new Materiel();
		final String client = UserContextHolder.getLocalUserContext().getUserContext().getClient();
		String strSql = "select t.mandt,t.matnr,t.meins,t.kzeff,t.datab,t.maktx "
				+ "from YVMATERIEL t "
				+ "where t.mandt = '"
				+ client
				+ "' and t.matnr = '"
				+ materielNo
				+ "'";
		getJdbcTemplate().query(strSql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				materiel.setClient(client);
				materiel.setKzeff(rs.getString("KZEFF"));
				materiel.setMaterielNo(materielNo);
				materiel.setDesc(rs.getString("MAKTX"));
				materiel.setUnit(rs.getString("MEINS"));
				materiel.setValidDateStart(rs.getString("DATAB"));
			}
		});
		return materiel;
	}
}
