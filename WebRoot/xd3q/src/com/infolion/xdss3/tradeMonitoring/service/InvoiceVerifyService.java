/*
 * @(#)InvoiceVerifyService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月04日 15点54分15秒
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
import com.infolion.xdss3.tradeMonitoring.dao.InvoiceVerifyItemHibernateDao;
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerify;
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerifyItem;
import com.infolion.xdss3.tradeMonitoringGen.service.InvoiceVerifyServiceGen;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 发票校验(InvoiceVerify)服务类
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
public class InvoiceVerifyService extends InvoiceVerifyServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

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

	public InvoiceVerifyItemHibernateDao getInvoiceVerifyItemHibernateDao() {
		return invoiceVerifyItemHibernateDao;
	}

	@Autowired
	private InvoiceVerifyItemHibernateDao invoiceVerifyItemHibernateDao;

	/**
	 * @param invoiceVerifyItemHibernateDao
	 *            the invoiceVerifyItemHibernateDao to set
	 */
	public void setInvoiceVerifyItemHibernateDao(InvoiceVerifyItemHibernateDao invoiceVerifyItemHibernateDao)
	{
		this.invoiceVerifyItemHibernateDao = invoiceVerifyItemHibernateDao;
	}

	/**
	 * 执行RFC，取得需要同步是主数据。
	 * 
	 * @param syncDate
	 * @param zdate
	 * @return
	 */
	public MasterDataRfcTable _executeRfcGetMasterData(String startDate, String enddate)
	{
		MasterDataRfcTable masterDataRfcTable = new MasterDataRfcTable();

		JCO.Table addDatas = null;
		JCO.Table modifyDatas = null;
		JCO.Table addDetailDatas = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.RSEG_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(startDate, "BUDAT_S");
				input.setValue(enddate, "BUDAT_E");

				client.execute(function);
				// 新增的数据集合
				addDatas = function.getTableParameterList().getTable("ADD_BELNR");

				// 修改的数据集合
				modifyDatas = function.getTableParameterList().getTable("MODIFBELNR");

				// 增加的明细信息
				addDetailDatas = function.getTableParameterList().getTable("ADD_ITEM");

				List<Map<String, String>> addDataList = ExtractSapData.getList(addDatas);
				List<Map<String, String>> modifyDataList = ExtractSapData.getList(modifyDatas);
				List<Map<String, String>> addDetailDataList = ExtractSapData.getList(addDetailDatas);

				masterDataRfcTable.setAddDataList(addDataList);
				masterDataRfcTable.setModifyDataList(modifyDataList);
				masterDataRfcTable.setAddDetailDataList(addDetailDataList);
				masterDataRfcTable.setSyncDate(enddate);

			}
			else
			{
				log.error("----------------取得发票校验数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.RSEG_RFCFunctionName);
				masterDataRfcTable = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得发票校验数据发生错误" + ex);
			String errMessage = ex.getMessage();
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEG_TableName);
			syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEGItem_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 225));
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
			syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEG_TableName);
			syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEGItem_TableName);
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 225));

			syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			log.error("----------------执行RFC错误，错误信息为:" + errMessage);
		}
		else
		{
			log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " 发票校验抬头(新增)+ " + masterDataRfcTable.getAddDetailDataList().size() + " 发票校验行项目数据（新增） " + masterDataRfcTable.getModifyDataList().size() + " (修改)笔发票校验数据抬头需要同步！");
			InvoiceVerify invoiceVerify1 = null;
			InvoiceVerify invoiceVerify2 = null; 
			InvoiceVerifyItem invoiceVerifyItem = null;
			try{
			int i = 1;
			for (Map map : masterDataRfcTable.getAddDataList()){
				invoiceVerify1 = new InvoiceVerify();
				ExBeanUtils.setBeanValueFromMap(invoiceVerify1, map);
				log.debug("第" + i + "笔新增数据:");
				log.debug("GJAHR:" + map.get("GJAHR"));
				log.debug("BELNR:" + map.get("BELNR"));
				log.debug("rsegHeader.getBktxt():" + invoiceVerify1.getBktxt());
				i = i + 1;
				this.invoiceVerifyHibernateDao.save(invoiceVerify1);
			}

			for (Map map : masterDataRfcTable.getModifyDataList())
			{
				invoiceVerify2 = new InvoiceVerify();
				ExBeanUtils.setBeanValueFromMap(invoiceVerify2, map);
				log.debug("第" + i + "笔修改数据:");
				log.debug("GJAHR:" + map.get("GJAHR"));
				log.debug("BELNR:" + map.get("BELNR"));
				log.debug("rsegHeader.getBktxt():" + invoiceVerify2.getBktxt());
				i = i + 1;
				this.invoiceVerifyHibernateDao.saveOrUpdate(invoiceVerify2);
			}

			int ii = 1;
			for (Map map : masterDataRfcTable.getAddDetailDataList())
			{
				invoiceVerifyItem = new InvoiceVerifyItem();
				try{
					InvoiceVerify invoiceVerifyT = new InvoiceVerify();
					invoiceVerifyT.setMaincode((String) map.get("MAINCODE"));
					invoiceVerifyItem.setInvoiceVerify(invoiceVerifyT);
					ExBeanUtils.setBeanValueFromMap(invoiceVerifyItem, map);
					if(map.get("MAINCODE") == null || "".equals(map.get("MAINCODE"))){
						throw new Exception("maincode为空!!!");
					}
					log.debug("第" + ii + "笔新增行项目数据:");
					log.debug("BUZEI:" + map.get("BUZEI"));
					log.debug("MATNR:" + map.get("MATNR"));
					log.debug("MAINCODE:" + map.get("MAINCODE"));
					log.debug("rsegItem.getBstme():" + invoiceVerifyItem.getBstme());
					ii = ii + 1;
					this.invoiceVerifyItemHibernateDao.save(invoiceVerifyItem);
				}catch(Throwable e){
					System.out.println("第" + ii + "笔新增行项目数据:" + invoiceVerifyItem.getBstme() + "\t" + invoiceVerifyItem.getCode());
					e.printStackTrace();
				}
				
			}
			}catch(Throwable e){
				System.out.println("出错数据:" + invoiceVerifyItem.getBstme() + "\t" + invoiceVerifyItem.getCode());
				e.printStackTrace();
			}
			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() > 0)
			{
				// 记录主数据同步日志。
				SyncMasterLog syncMasterLog = new SyncMasterLog();
				syncMasterLog.setIssucceed("Y");
				syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
				syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEG_TableName);
				syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
				syncMasterLog.setErrMessage(" ");
				syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			}
		}
	}
}