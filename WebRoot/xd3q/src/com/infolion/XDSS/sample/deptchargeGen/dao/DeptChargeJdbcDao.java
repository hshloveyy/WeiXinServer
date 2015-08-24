/*
 * @(#)DeptChargeJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-3-16
 *  描　述：创建
 */

package com.infolion.XDSS.sample.deptchargeGen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.AssertUtil;

@Repository
public class DeptChargeJdbcDao extends BaseJdbcDao
{
	@SuppressWarnings("unchecked")
	public List<String[]> getVersionList(String budclassid, String budOrgId)
	{
		AssertUtil.notNullBlank(budclassid, "budSortId");
		AssertUtil.notNullBlank(budOrgId, "budOrgName");
		String sql = "select DEPTCHARGEID, VERSION from YDEPTCHARGE where BUDSORTID=? and BUDORGID=?";
		List resultList = this.getJdbcTemplate().queryForList(sql, new Object[] { budclassid, budOrgId });
		List<String[]> list = new ArrayList<String[]>();
		for (Object obj : resultList)
		{
			Map map = (Map) obj;
			String[] s = new String[2];
			s[1] = map.get("VERSION").toString();
			s[0] = map.get("DEPTCHARGEID").toString();
			list.add(s);
		}
		return list;
	}

}
