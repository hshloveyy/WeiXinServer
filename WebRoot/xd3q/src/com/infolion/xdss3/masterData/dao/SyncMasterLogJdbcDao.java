/*
 * @(#)SyncMasterLogJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

/**
 * <pre>
 * 主数据同步日志(SyncMasterLog),JdbcDao 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class SyncMasterLogJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据主数据表名称取得主数据最后一次同步成功时间。
	 * 
	 * @param synctablename
	 * @return
	 */
	public String getSyncDate(String synctablename)
	{
		String sql = "select SYNCDATE from (select YSYNCMASTERLOG.SYNCDATE from YSYNCMASTERLOG where YSYNCMASTERLOG.SYNCTABLENAME='" + synctablename + "' and YSYNCMASTERLOG.ISSUCCEED='Y' order by YSYNCMASTERLOG.CREATETIME DESC) WHERE ROWNUM=1 ";
		List<String> userIdList = getJdbcTemplate().queryForList(sql, String.class);
		if (userIdList != null && userIdList.size() > 0)
		{
			return userIdList.get(0);
		}
		else
		{
			return "";
		}
	}

}