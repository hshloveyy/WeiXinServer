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

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.dao.SupplierHibernateDao;
import com.infolion.xdss3.masterData.dao.SupplierJdbcDao;
import com.infolion.xdss3.masterData.domain.MasterDataRfcTable;
import com.infolion.xdss3.masterData.domain.Supplier;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 供应商(Supplier)服务类
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
public class SupplierService extends BaseService
{

	protected static Log log = LogFactory.getFactory().getLog(SupplierService.class);

	@Autowired
	protected SupplierHibernateDao supplierHibernateDao;

	public void setSupplierHibernateDao(SupplierHibernateDao supplierHibernateDao)
	{
		this.supplierHibernateDao = supplierHibernateDao;
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
	
	@Autowired
	protected SupplierJdbcDao supplierJdbcDao;
	
	public void setSupplierJdbcDao(SupplierJdbcDao supplierJdbcDao) {
		this.supplierJdbcDao = supplierJdbcDao;
	}

	/**
	 * 执行RFC，取得需要同步是主数据。
	 * 
	 * @param syncDate
	 * @param zdate
	 * @return
	 */
	public MasterDataRfcTable _executeRfcGetMasterData(String syncDate, String zdate)
	{
		MasterDataRfcTable masterDataRfcTable = new MasterDataRfcTable();

		JCO.Table addDatas = null;
		JCO.Table modifyDatas = null;
		JCO.Table errorTable = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.Supplier_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(syncDate, SyncMasterDataConstants.SYNCDATE);
				input.setValue(zdate, SyncMasterDataConstants.ZDATE);

				client.execute(function);
				// 新增的数据集合
				addDatas = function.getTableParameterList().getTable(SyncMasterDataConstants.ADDDATA_TABLE);
				// 修改的数据集合
				modifyDatas = function.getTableParameterList().getTable(SyncMasterDataConstants.MODIFYDATA_TABLE);
				// 错误信息
				errorTable = function.getTableParameterList().getTable(SyncMasterDataConstants.ERRORTABLE_TABLE);

				List<Map<String, String>> addDataList = ExtractSapData.getList(addDatas);
				List<Map<String, String>> modifyDataList = ExtractSapData.getList(modifyDatas);
				List<Map<String, String>> errorMsgs = ExtractSapData.getList(errorTable);

				masterDataRfcTable.setAddDataList(addDataList);
				masterDataRfcTable.setErrorMsgs(errorMsgs);
				masterDataRfcTable.setModifyDataList(modifyDataList);
				masterDataRfcTable.setSyncDate(syncDate);
			}
			else
			{
				log.error("----------------取得供应商主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.Supplier_RFCFunctionName);
				masterDataRfcTable = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得供应商主数据发生错误" + ex);
			String errMessage = ex.getMessage();
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename(SyncMasterDataConstants.Supplier_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage("取得供应商主数据发生错误:" + errMessage.substring(0, 220));
			syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			masterDataRfcTable = null;
		}
		finally
		{
			manager.cleanUp();
		}

		return masterDataRfcTable;
	}

	/**
	 * 同步主数据。
	 * 
	 * @param syncDate
	 * @param addDataList
	 * @param modifyDataList
	 * @param errorMsgs
	 */
	public void _ayncMasterData(MasterDataRfcTable masterDataRfcTable) throws Exception
	{
		if (masterDataRfcTable.getErrorMsgs() != null && masterDataRfcTable.getErrorMsgs().size() > 0)
		{
			String errMessage = masterDataRfcTable.getErrorMsgs().get(0).get(SyncMasterDataConstants.ERRMESSAGE);
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename(SyncMasterDataConstants.Supplier_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 254));

			syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			log.error("----------------执行RFC错误，错误信息为:" + errMessage);
		}
		else
		{
			log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (新增)+ " + masterDataRfcTable.getModifyDataList().size() + "(修改)笔供应商主数据需要同步！");
			int i = 1;
			for (Map map : masterDataRfcTable.getAddDataList())
			{
				Supplier supplier = new Supplier();
				ExBeanUtils.setBeanValueFromMap(supplier, map);
				log.debug("第" + i + "笔新增数据:");
				log.debug("LIFNR:" + map.get("LIFNR"));
				log.debug("BUKRS:" + map.get("BUKRS"));
				log.debug("supplier.getName1():" + supplier.getName1());
				log.debug("supplier.getSortl():" + supplier.getSortl());
				log.debug("supplier.getLoevm():" + supplier.getLoevm());
				log.debug("supplier.getLand1():" + supplier.getLand1());
				log.debug("supplier.getAkont():" + supplier.getAkont());
				i = i + 1;
				supplierHibernateDao.save(supplier);
			}

			for (Map map : masterDataRfcTable.getModifyDataList())
			{
				Supplier supplier = new Supplier();
				ExBeanUtils.setBeanValueFromMap(supplier, map);
				supplier.setClient("800");
				log.debug("第" + i + "笔修改数据:");
				log.debug("LIFNR:" + map.get("LIFNR"));
				log.debug("BUKRS:" + map.get("BUKRS"));
				log.debug("supplier.getName1():" + supplier.getName1());
				log.debug("supplier.getSortl():" + supplier.getSortl());
				log.debug("supplier.getLoevm():" + supplier.getLoevm());
				log.debug("supplier.getLand1():" + supplier.getLand1());
				log.debug("supplier.getAkont():" + supplier.getAkont());
				i = i + 1;
				supplierHibernateDao.saveOrUpdate(supplier);
			}

			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() > 0)
			{
				// 记录主数据同步日志。
				SyncMasterLog syncMasterLog = new SyncMasterLog();
				syncMasterLog.setIssucceed("Y");
				syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
				syncMasterLog.setSynctablename(SyncMasterDataConstants.Supplier_TableName);
				syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				syncMasterLog.setErrMessage(" ");
				syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
		}
	}
	
	public String getSupplierName(String supplier, String bukrs)
	{
		return this.supplierJdbcDao.getSupplierName(supplier,bukrs);
	}
	
	public String getSupplierName(String supplier)
	{
		return this.supplierJdbcDao.getSupplierName(supplier);
	}
}