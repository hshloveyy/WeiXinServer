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
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.dao.CostCenterJDBCDao;
import com.infolion.xdss3.masterData.domain.CostCenter;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 现金流量项(CashFlowItem)服务类
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
public class CostCenterService extends BaseService
{

	protected static Log log = LogFactory.getFactory().getLog(CostCenterService.class);

	@Autowired
	protected CostCenterJDBCDao costCenterJDBCDao;

	/**
	 * @param costCenterJDBCDao
	 *            the costCenterJDBCDao to set
	 */
	public void setCostCenterJDBCDao(CostCenterJDBCDao costCenterJDBCDao)
	{
		this.costCenterJDBCDao = costCenterJDBCDao;
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
	public List<Map<String, String>> _executeRfcGetMasterData(String syncDate, String zdate)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		JCO.Table returnDatas = null;

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.CostCenter_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				client.execute(function);

				// 新增的数据集合
				returnDatas = function.getTableParameterList().getTable("OT_CSKS");

				list = ExtractSapData.getList(returnDatas);

			}
			else
			{
				log.error("----------------取得需要同步是主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.CostCenter_RFCFunctionName);
				list = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得成本中心数据发生错误" + ex);
			list = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return list;
	}

	/**
	 * 同步主数据。
	 * 
	 * @param syncDate
	 * @param addDataList
	 * @param modifyDataList
	 * @param errorMsgs
	 */
	public void _ayncMasterData(List<Map<String, String>> list) throws Exception
	{

		log.debug("----------------共取得" + list.size() + "笔成本中心数据！");
		int i = 1;

		if (list.size() > 0)
		{
			this.costCenterJDBCDao.delete();
		}

		for (Map map : list)
		{
			CostCenter costCenter = new CostCenter();
			ExBeanUtils.setBeanValueFromMap(costCenter, map);
			log.debug("第" + i + "笔数据:");
			log.debug("GSBER:" + map.get("GSBER"));
			log.debug("BUKRS:" + map.get("BUKRS"));
			log.debug("costCenter.getGsber():" + costCenter.getGsber());
			log.debug("costCenter.getBukrs():" + costCenter.getBukrs());
			log.debug("costCenter.getKostl():" + costCenter.getKostl());
			log.debug("costCenter.getKtext():" + costCenter.getKtext());
			i = i + 1;
			this.costCenterJDBCDao.insert(costCenter);
		}
	}
}