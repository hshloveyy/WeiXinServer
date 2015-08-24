/*
 * @(#)SupplierService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.dao.DeptJdbcDao;
import com.infolion.xdss3.masterData.domain.Dept;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 公司信息(Company)服务类
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
public class DeptService extends BaseService
{

	protected static Log log = LogFactory.getFactory().getLog(DeptService.class);

	@Autowired
	protected DeptJdbcDao deptJdbcDao;

	/**
	 * @param deptJdbcDao
	 *            the deptJdbcDao to set
	 */
	public void setDeptJdbcDao(DeptJdbcDao deptJdbcDao)
	{
		this.deptJdbcDao = deptJdbcDao;
	}

	@Autowired
	private SyncMasterLogService syncMasterLogService;

	/**
	 * @param syncMasterLogService
	 *            the syncMasterLogService to set
	 */
	public void setSyncMasterLogService(SyncMasterLogService syncMasterLogService)
	{
		this.syncMasterLogService = syncMasterLogService;
	}

	/**
	 * 执行RFC，取得需要同步是主数据。
	 * 
	 * @param syncDate
	 * @param zdate
	 * @return
	 */
	public List<Map<String, String>> _executeRfcGetMasterData()
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.DEPT_RFCFuctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("IT_GSBER");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得部门主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.CostCenter_RFCFunctionName);
				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得部门数据发生错误" + ex);
			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return list;
	}

	/**
	 * 同步主数据（公司数据）。
	 */
	public void _ayncMasterData(List<Map<String, String>> list) throws Exception
	{

		log.debug("----------------共取得" + list.size() + "笔部门数据！");
		int i = 1;

		if (list.size() > 0)
		{
			this.deptJdbcDao.delete();
		}

		for (Map map : list)
		{
			Dept dept = new Dept();
			// ExBeanUtils.setBeanValueFromMap(company, map);
			dept.setCompanyid(" ");
			dept.setDeptid(map.get("GSBER").toString());
			dept.setDeptName(map.get("GTEXT").toString());

			log.debug("第" + i + "笔数据:");
			log.debug("GSBER:" + map.get("GSBER"));
			log.debug("GTEXT:" + map.get("GTEXT"));
			i = i + 1;
			this.deptJdbcDao.insert(dept);
		}
	}
}