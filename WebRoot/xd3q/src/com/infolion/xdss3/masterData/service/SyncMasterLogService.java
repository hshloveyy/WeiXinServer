/*
 * @(#)SyncMasterLogService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.xdss3.masterData.dao.SyncMasterLogHibernateDao;
import com.infolion.xdss3.masterData.dao.SyncMasterLogJdbcDao;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;

/**
 * <pre>
 * 主数据同步日志(SyncMasterLog)服务类
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
@Service
public class SyncMasterLogService extends BaseService
{
	@Autowired
	private SyncMasterLogJdbcDao syncMasterLogJdbcDao;

	@Autowired
	private SyncMasterLogHibernateDao syncMasterLogHibernateDao;

	/**
	 * @return the syncMasterLogJdbcDao
	 */
	public SyncMasterLogJdbcDao getSyncMasterLogJdbcDao()
	{
		return syncMasterLogJdbcDao;
	}

	/**
	 * @param syncMasterLogJdbcDao
	 *            the syncMasterLogJdbcDao to set
	 */
	public void setSyncMasterLogJdbcDao(SyncMasterLogJdbcDao syncMasterLogJdbcDao)
	{
		this.syncMasterLogJdbcDao = syncMasterLogJdbcDao;
	}

	/**
	 * @return the syncMasterLogHibernateDao
	 */
	public SyncMasterLogHibernateDao getSyncMasterLogHibernateDao()
	{
		return syncMasterLogHibernateDao;
	}

	/**
	 * @param syncMasterLogHibernateDao
	 *            the syncMasterLogHibernateDao to set
	 */
	public void setSyncMasterLogHibernateDao(SyncMasterLogHibernateDao syncMasterLogHibernateDao)
	{
		this.syncMasterLogHibernateDao = syncMasterLogHibernateDao;
	}

	/**
	 * 保存主数据同步日志。
	 * 
	 * @param syncMasterLog
	 */
	public void _saveSyncMasterLog(SyncMasterLog syncMasterLog)
	{
		this.syncMasterLogHibernateDao.save(syncMasterLog);
	}

	/**
	 * 根据主数据表名称取得主数据最后一次同步成功时间。
	 * 
	 * @param synctablename
	 * @return
	 */
	public String _getSyncDate(String synctablename)
	{
		return this.syncMasterLogJdbcDao.getSyncDate(synctablename);
	}

}