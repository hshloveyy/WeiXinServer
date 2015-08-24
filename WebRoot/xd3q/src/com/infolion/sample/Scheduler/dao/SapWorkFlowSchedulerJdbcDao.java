/*
 * @(#)MaterielHibernateDao.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-6-24
 *  描　述：创建
 */

package com.infolion.sample.Scheduler.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.infolion.platform.basicApp.authManage.domain.Menu;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sample.purchase.domain.PurchasingDoc;
import com.infolion.sample.sales.domain.SalesDoc;

/**
 * <pre>
 * 取得SAP采购、销售凭证，未审批部分的数据，JDBC操作类。
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
public class SapWorkFlowSchedulerJdbcDao extends BaseJdbcDao
{
	/**
	 * 取得未审批的SAP采购凭证列表。
	 * 
	 * @return
	 */
	public Set<PurchasingDoc> getPurchasingDocs()
	{
		Set<PurchasingDoc> purchasingDocs= new LinkedHashSet();
		String sql = "select EBELN,MANDT,BUKRS,STATU,ERNAM,ZTERM,WAERS,PROCESSSTATE,KUNNR,LASTAPPNAME,LASTAPPTIME,FRGZU from EKKO where AEDAT >='20091204' and FRGZU=' ' and processState=' '";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			PurchasingDoc purchasingDoc = new PurchasingDoc();
			ExBeanUtils.setBeanValueFromMap(purchasingDoc, map);
			purchasingDocs.add(purchasingDoc);
		}
		
		return purchasingDocs ;
	}

	/**
	 * 取得未审批的SAP销售凭证列表。
	 * 
	 * @return
	 */
	public Set<SalesDoc> getSalesDocs()
	{
		Set<SalesDoc> salesDocDocs=  new LinkedHashSet();
		String sql = "SELECT * FROM YVSOHEAD WHERE ERDAT>= '20091207'and PROCESSSTATE=' ' and ENDORSESTATE=' '";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			SalesDoc salesDoc = new SalesDoc();
			ExBeanUtils.setBeanValueFromMap(salesDoc, map);
			salesDocDocs.add(salesDoc);
		}
		
		return salesDocDocs ;
	}

	/**
	 * 更新流程状态(processState)
	 * 
	 * @param tableName
	 * @param processState
	 */
	public void updateProcessState(String tableName ,String processState,String idFiled,String id,String client)
	{
		Assert.notNull("参数tableName不能为空！");
		Assert.notNull("参数processState不能为空！");
	
		String strSql = "UPDATE " + tableName + " SET PROCESSSTATE='"+ processState+ "' WHERE " + idFiled +"='" +id+ "' AND MANDT='" + client+"'"  ;
		this.getJdbcTemplate().update(strSql);
	}

	/**
	 * 查询用的映射类，将查询出来的字段映射到类的属性
	 * 
	 * @author
	 * 
	 */
	protected class PurchasingDocMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Menu menu = new Menu();

			return menu;
		}
	}

}
