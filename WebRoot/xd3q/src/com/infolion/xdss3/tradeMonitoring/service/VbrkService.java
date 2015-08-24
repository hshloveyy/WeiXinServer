/*
 * @(#)VbrkService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月06日 13点53分46秒
 *  描　述：创建
 */
package com.infolion.xdss3.tradeMonitoring.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.domain.MasterDataRfcTable;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.masterData.service.SyncMasterLogService;
import com.infolion.xdss3.tradeMonitoring.dao.TradeMonitoringJdbcDao;
import com.infolion.xdss3.tradeMonitoring.dao.VbrkHibernateDao;
import com.infolion.xdss3.tradeMonitoring.dao.VbrpHibernateDao;
import com.infolion.xdss3.tradeMonitoring.domain.Vbrk;
import com.infolion.xdss3.tradeMonitoring.domain.Vbrp;
import com.infolion.xdss3.tradeMonitoringGen.service.VbrkServiceGen;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 开票数据抬头(Vbrk)服务类
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
public class VbrkService extends VbrkServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VbrkHibernateDao vbrkHibernateDao;

	public VbrkHibernateDao getVbrkHibernateDao() {
		return vbrkHibernateDao;
	}

    @Autowired
    protected TradeMonitoringJdbcDao tradeMonitoringJdbcDao;
    
    
	public TradeMonitoringJdbcDao getTradeMonitoringJdbcDao() {
        return tradeMonitoringJdbcDao;
    }

    public VbrpHibernateDao getVbrpHibernateDao() {
		return vbrpHibernateDao;
	}

	public SyncMasterLogService getSyncMasterLogService() {
		return syncMasterLogService;
	}

	/**
	 * @param vbrkHibernateDao
	 *            the vbrkHibernateDao to set
	 */
	public void setVbrkHibernateDao(VbrkHibernateDao vbrkHibernateDao)
	{
		this.vbrkHibernateDao = vbrkHibernateDao;
	}

	@Autowired
	protected VbrpHibernateDao vbrpHibernateDao;

	/**
	 * @param vbrpHibernateDao
	 *            the vbrpHibernateDao to set
	 */
	public void setVbrpHibernateDao(VbrpHibernateDao vbrpHibernateDao)
	{
		this.vbrpHibernateDao = vbrpHibernateDao;
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
	public MasterDataRfcTable _executeRfcGetMasterData(String syncDate, String zdate)
	{
		MasterDataRfcTable masterDataRfcTable = new MasterDataRfcTable();

		JCO.Table addDatas = null;
		JCO.Table modifyDatas = null;
		JCO.Table addDetailDatas = null;
		JCO.Table errorTable = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.Vbrk_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(syncDate, SyncMasterDataConstants.SYNCDATE);
				input.setValue(zdate, SyncMasterDataConstants.ZDATE);

				client.execute(function);
				// 新增的数据集合
				addDatas = function.getTableParameterList().getTable("OT_VBRK_ADD");

				// 修改的数据集合
				modifyDatas = function.getTableParameterList().getTable("OT_VBRK_MOD");

				// 增加的明细信息
				addDetailDatas = function.getTableParameterList().getTable("OT_VBRP_ADD");

				// 错误信息
				errorTable = function.getTableParameterList().getTable("ERRORTABLE");

				List<Map<String, String>> addDataList = ExtractSapData.getList(addDatas);
				List<Map<String, String>> modifyDataList = ExtractSapData.getList(modifyDatas);
				List<Map<String, String>> addDetailDataList = ExtractSapData.getList(addDetailDatas);
				List<Map<String, String>> errorMsgs = ExtractSapData.getList(errorTable);

				masterDataRfcTable.setAddDataList(addDataList);
				masterDataRfcTable.setErrorMsgs(errorMsgs);
				masterDataRfcTable.setModifyDataList(modifyDataList);
				masterDataRfcTable.setAddDetailDataList(addDetailDataList);
				masterDataRfcTable.setSyncDate(syncDate);
			}
			else
			{
				log.error("----------------取得需要同步是主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.Vbrk_RFCFunctionName);
				masterDataRfcTable = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得开票业务数据发生错误" + ex);
			String errMessage = ex.getMessage();
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename(SyncMasterDataConstants.Vbrk_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 254));
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
			syncMasterLog.setSynctablename(SyncMasterDataConstants.Vbrk_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 254));

			syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			log.error("----------------执行RFC错误，错误信息为:" + errMessage);
		}
		else
		{
			log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " 开票业务抬头(新增)+ " + masterDataRfcTable.getAddDetailDataList().size() + " 开票业务明细数据（新增） " + masterDataRfcTable.getModifyDataList().size() + " (修改)笔开票业务数据抬头需要同步！");
			int i = 1;
			for (Map map : masterDataRfcTable.getAddDataList())
			{
				Vbrk vbrk = new Vbrk();
				ExBeanUtils.setBeanValueFromMap(vbrk, map);
				log.debug("第" + i + "笔新增抬头数据:");
				log.debug("VBELN:" + map.get("VBELN"));
				log.debug("FKART:" + map.get("FKART"));
				log.debug("vbrk.getVtext():" + vbrk.getVtext());
				log.debug("vbrk.getName1():" + vbrk.getName1());
				log.debug("vbrk.getVtext():" + vbrk.getVtext());
				i = i + 1;
				vbrkHibernateDao.save(vbrk);
			}

			i = 1;
			for (Map map : masterDataRfcTable.getModifyDataList())
			{
				Vbrk vbrk = new Vbrk();
				log.debug("第" + i + "笔修改抬头数据:");
				ExBeanUtils.setBeanValueFromMap(vbrk, map);
				vbrk.setClient("800");
				log.debug("VBELN:" + map.get("VBELN"));
				log.debug("FKART:" + map.get("FKART"));
				log.debug("vbrk.getVtext():" + vbrk.getVtext());
				log.debug("vbrk.getName1():" + vbrk.getName1());
				log.debug("vbrk.getVtext():" + vbrk.getVtext());
				i = i + 1;
				vbrkHibernateDao.saveOrUpdate(vbrk);
			}

			i = 1;
			for (Map map : masterDataRfcTable.getAddDetailDataList())
			{
				Vbrp vbrp = new Vbrp();
				Vbrk vbrk = new Vbrk();
				vbrk.setVbeln((String) map.get("VBELN"));
				vbrp.setVbrk(vbrk);

				log.debug("第" + i + "笔新增行项目数据:");
				ExBeanUtils.setBeanValueFromMap(vbrp, map);
				log.debug("VBELN:" + map.get("VBELN"));
				log.debug("ARKTX:" + map.get("ARKTX"));
				log.debug("vbrp.getVrkme():" + vbrp.getVrkme());
				i = i + 1;
				this.vbrpHibernateDao.save(vbrp);
			}

			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() + masterDataRfcTable.getAddDetailDataList().size() > 0)
			{
				// 记录主数据同步日志。
				SyncMasterLog syncMasterLog = new SyncMasterLog();
				syncMasterLog.setIssucceed("Y");
				syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
				syncMasterLog.setSynctablename(SyncMasterDataConstants.Vbrk_TableName);
				syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				syncMasterLog.setErrMessage(" ");
				syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
		}
	}

}