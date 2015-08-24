/*
 * @(#)TestTableJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-15
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
@Repository
public class TestTableJdbcDao extends BaseDao {
	public List queryTestTable(){
		String strSql = "select a.* from test_table a";
		
		return getJdbcTemplate().queryForList(strSql);
	}
}
