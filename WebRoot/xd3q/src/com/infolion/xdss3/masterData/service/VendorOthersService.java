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

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;

import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.dao.CertificateNoHibernateDao;

import com.infolion.xdss3.masterData.dao.UnclearDataOthersJdbcDao;
import com.infolion.xdss3.masterData.dao.UnclearTitleLogHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorDetailHibernateDao;

import com.infolion.xdss3.masterData.dao.VendorTitleOthersHibernateDao;
import com.infolion.xdss3.masterData.dao.VendorTitleOthersJdbcDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;

import com.infolion.xdss3.masterData.domain.MasterDataRfcTable;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.masterData.domain.UnclearTitleLog;
import com.infolion.xdss3.masterData.domain.VendorDetail;
import com.infolion.xdss3.masterData.domain.VendorTitleOthers;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 供应商未清数据(Vendor)服务类
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
public class VendorOthersService extends BaseService
{

	protected static Log log = LogFactory.getFactory().getLog(VendorOthersService.class);

	@Autowired
	protected VendorTitleOthersHibernateDao vendorTitleHibernateDao;

	/**
	 * @param vendorTitleHibernateDao
	 *            the vendorTitleHibernateDao to set
	 */
	public void setVendorTitleHibernateDao(VendorTitleOthersHibernateDao vendorTitleHibernateDao)
	{
		this.vendorTitleHibernateDao = vendorTitleHibernateDao;
	}

	@Autowired
	private VendorTitleOthersJdbcDao vendorTitleJdbcDao;

	/**
	 * @param vendorTitleJdbcDao
	 *            the vendorTitleJdbcDao to set
	 */
	public void setVendorTitleJdbcDao(VendorTitleOthersJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}

	@Autowired
	protected VendorDetailHibernateDao vendorDetailHibernateDao;

	/**
	 * @param vendorDetailHibernateDao
	 *            the vendorDetailHibernateDao to set
	 */
	public void setVendorDetailHibernateDao(VendorDetailHibernateDao vendorDetailHibernateDao)
	{
		this.vendorDetailHibernateDao = vendorDetailHibernateDao;
	}

	

	@Autowired
	private UnclearDataOthersJdbcDao unclearDataJdbcDao;

	/**
	 * @param unlearDataJDBCDao
	 *            the unlearDataJDBCDao to set
	 */
	public void setUnlearDataJDBCDao(UnclearDataOthersJdbcDao unclearDataJdbcDao)
	{
		this.unclearDataJdbcDao = unclearDataJdbcDao;
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
    private UnclearTitleLogHibernateDao unclearTitleLogHibernateDao;
    
    public void setUnclearTitleLogHibernateDao(UnclearTitleLogHibernateDao unclearTitleLogHibernateDao){
        this.unclearTitleLogHibernateDao = unclearTitleLogHibernateDao;
    }

	/**
	 * 执行RFC，取得需要同步是主数据。
	 * 
	 * @param syncDate
	 * @param zdate
	 * @return
	 */
	public MasterDataRfcTable _executeRfcGetMasterData(String syncDate, String zdate, String vendor)
	{
		MasterDataRfcTable masterDataRfcTable = new MasterDataRfcTable();

		JCO.Table addTitleDatas = null;
		JCO.Table addItemDatas = null;
		JCO.Table outPutDatas = null;
		JCO.Table inputTable = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();

		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.UNCLEARVENDOR_OTHERS_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(zdate, "BUDAT_S");
				input.setValue(syncDate, "BUDAT_E");
				if (vendor != null && !vendor.equals(""))
				{
					input.setValue(vendor, "VENDOR");
					masterDataRfcTable.setRealtimeCall(true);
				}
				else
				{
					masterDataRfcTable.setRealtimeCall(false);
				}

				// 不是第一次同步数据,需要获取未清凭证编号
				if (!zdate.equals(""))
				{
					List<CertificateNo> certificateNoList = this.vendorTitleHibernateDao.getUnclearCertificateNo(vendor);
					if (certificateNoList != null && certificateNoList.size() > 0)
					{
						// 输入凭证号
						inputTable = function.getTableParameterList().getTable("I_BELNR");
						inputTable.firstRow();
						for (int i = 0; i < certificateNoList.size(); i++)
						{
							inputTable.appendRow();
							inputTable.setValue(certificateNoList.get(i).getBelnr(), "BELNR");
							inputTable.setValue(certificateNoList.get(i).getBukrs(), "BUKRS");
							inputTable.setValue(certificateNoList.get(i).getGjahr(), "GJAHR");
						}
					}

					masterDataRfcTable.setFirstTimeSyn(false);
				}
				else
				{
					masterDataRfcTable.setFirstTimeSyn(true);
				}

				client.execute(function);
				// 新增的抬头数据集合
				addTitleDatas = function.getTableParameterList().getTable("ADD_HEADER");
				
				// 新增的行项目数据集合
				//addItemDatas = function.getTableParameterList().getTable("ADD_ITEM");
				
				// 输出凭证号
				outPutDatas = function.getTableParameterList().getTable("E_BELNR");

				List<Map<String, String>> addTitleList = ExtractSapData.getList(addTitleDatas);
				//List<Map<String, String>> addItemList = ExtractSapData.getList(addItemDatas);
				List<Map<String, String>> outputList = ExtractSapData.getList(outPutDatas);

				masterDataRfcTable.setAddDataList(addTitleList);
				//masterDataRfcTable.setAddDetailDataList(addItemList);
				masterDataRfcTable.setOutputTableDataList(outputList);
				masterDataRfcTable.setSyncDate(syncDate);
			}
			else
			{
				log.error("----------------取得未清供应商主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.UNCLEARVENDOR_OTHERS_RFCFunctionName);
				masterDataRfcTable = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得未清供应商数据发生错误" + ex);
			String errMessage = ex.getMessage();
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage("取得未清供应商数据发生错误:" + errMessage.substring(0, 220));
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
			syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage(errMessage.substring(0, 254));

			syncMasterLogService._saveSyncMasterLog(syncMasterLog);
			log.error("----------------执行RFC错误，错误信息为:" + errMessage);
		}
		else
		{
			log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (笔抬头数据和)+ " 
					//+ masterDataRfcTable.getAddDetailDataList().size() + "笔行项目数据需要同步！" 
					+ masterDataRfcTable.getOutputTableDataList().size() + "凭证号输出");

			int i = 1;

			// 未清供应商抬头
			for (Map map : masterDataRfcTable.getAddDataList())
			{
				VendorTitleOthers vendorTitle = new VendorTitleOthers();
				ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
				log.debug("第" + i + "笔新增抬头数据:");
				log.debug("BELNR:" + map.get("BELNR"));
				log.debug("BUKRS:" + map.get("BUKRS"));
				log.debug("vendorTitle.getBktxt():" + vendorTitle.getBktxt());
				log.debug("vendorTitle.getBelnr():" + vendorTitle.getBelnr());
				vendorTitle.setBktxt(vendorTitle.getBktxt().replaceAll("[\\#]", "＃"));
				vendorTitle.setBudat(vendorTitle.getBudat().replaceAll("-", ""));
				i = i + 1;
				
				// 判断该未清数据是否已清
//				if(this.vendorTitleJdbcDao.isVendorTitleClear(vendorTitle)){
//					vendorTitle.setIscleared("1");
//				}else{
					vendorTitle.setIscleared("0");
//				}
				// 若为日元，则DMBTR乘以100再保存
                if(vendorTitle.getWaers().equals("JPY")){
                    BigDecimal dmbtr = new BigDecimal(vendorTitle.getDmbtr().doubleValue());
                    vendorTitle.setDmbtr(dmbtr.multiply(new BigDecimal(100)));
                }
                
				boolean isVendorTitleExist = this.unclearDataJdbcDao.isVendorTitleExist(vendorTitle.getBukrs(), vendorTitle.getBelnr(), 
				                                                                        vendorTitle.getGjahr(), vendorTitle.getBuzei());
				if(isVendorTitleExist == false){
					this.vendorTitleHibernateDao.save(vendorTitle);
				}
			}

//			i = 1;
//			// 未清供应商行项目
////			for (Map map : masterDataRfcTable.getAddDetailDataList())
////			{
////				VendorDetail vendorDetail = new VendorDetail();
////				ExBeanUtils.setBeanValueFromMap(vendorDetail, map);
////				vendorDetail.setClient("800");
////				log.debug("第" + i + "笔新增行项目数据:");
////				log.debug("INVOICE:" + map.get("INVOICE"));
////				log.debug("BUZEI:" + map.get("BUZEI"));
////				log.debug("vendorDetail.getEbeln():" + vendorDetail.getEbeln());
////				log.debug("vendorDetail.getGjahr():" + vendorDetail.getGjahr());
////				i = i + 1;
////				vendorDetail.setIscleared("0");
////
////				if (masterDataRfcTable.isRealtimeCall())
////				{
////					this.vendorDetailHibernateDao.save(vendorDetail);
////				}
////				else
////				{
////					boolean ret = this.unclearDataJdbcDao.isSupplierDetailExist(vendorDetail.getInvoice(), vendorDetail.getGjahr());
////
////					if (ret == false)
////					{
////						this.vendorDetailHibernateDao.save(vendorDetail);
////					}
////				}
////			}
//
//			// 更新结清标志
//			List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
//			certificateNoList.clear();
//			for (Map map : masterDataRfcTable.getOutputTableDataList())
//			{
//				CertificateNo certificateNo = new CertificateNo();
//				certificateNo.setBelnr(map.get("BELNR").toString());
//				certificateNo.setBukrs(map.get("BUKRS").toString());
//				certificateNo.setGjahr(map.get("GJAHR").toString());
//				// ExBeanUtils.setBeanValueFromMap(certificateNo, map);
//				certificateNoList.add(certificateNo);
//			}
//
//			if (certificateNoList.size() > 0)
//			{
//				this.unclearDataJdbcDao.updateUnclearFlagForVendor(certificateNoList);
//			}

			if (masterDataRfcTable.getAddDataList().size() > 0 )
//					+ masterDataRfcTable.getAddDetailDataList().size() > 0)
			{
				// 记录主数据同步日志。

				/**
				 * 如果是实时调用，不保存同步log
				 */
				if (masterDataRfcTable.isRealtimeCall())
				{
					;
				}
				else
				{
					SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("Y");
					syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
					syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(" ");
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
				}
			}
		}
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 同步更新外围供应商未清抬头数据的ISCLEARED状态
     */
    public void _syncAllCustomerTitleState(MasterDataRfcTable masterDataRfcTable) throws Exception{

        if(masterDataRfcTable.getErrorMsgs() != null && masterDataRfcTable.getErrorMsgs().size() > 0){
            /*
             * 记录同步失败日志
             */
            String errMessage = masterDataRfcTable.getErrorMsgs().get(0).get(SyncMasterDataConstants.ERRMESSAGE);
            SyncMasterLog syncMasterLog = new SyncMasterLog();
            syncMasterLog.setIssucceed("N");
            syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
            syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
            syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
            syncMasterLog.setErrMessage(errMessage.substring(0, 254));
            syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            log.error("----------------执行RFC错误，错误信息为:" + errMessage);
        }else{
            /*
             * 将SAP传过来的每条数据指定的四个字段拼成复合主键，最后存放在比较的字符串中
             */
            String compareStr = "";
            StringBuffer sb = new StringBuffer();
            for(Map<String, String> map : masterDataRfcTable.getAddDataList()){
                sb.append(map.get("BUKRS") + map.get("BELNR") + map.get("GJAHR") + map.get("BUZEI") + "|");
            }
            compareStr = sb.toString();
            
            /*
             * 用YVENDORTITLE表中的ID跟SAP数据集的ID进行比较，
             * 若在SAP数据集中找不到，则将YVENDORTITLE表中的本条数据的ISCLEARED字段更新成2
             */
            List<VendorTitleOthers> vts = this.vendorTitleHibernateDao.find("from VendorTitleOthers");
            List<VendorTitleOthers> vts4Update = new ArrayList<VendorTitleOthers>();
            log.debug("----------------从SAP获取供应商未清数据共 " + masterDataRfcTable.getAddDataList().size() + " 笔！");
            log.debug("----------------从外围获取供应商未清数据共 " + vts.size() + " 笔！");
            for(int k = 0; k < vts.size(); k++){
            	VendorTitleOthers vt = vts.get(k);
                UnclearTitleLog unclearTitleLog = new UnclearTitleLog();
                unclearTitleLog.setUnclearTitleId(vt.getVendortitleid());
                unclearTitleLog.setSupplier(vt.getLifnr());
                unclearTitleLog.setOldIscleared(vt.getIscleared());
                unclearTitleLog.setLogTime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                unclearTitleLog.setRemark("BUKRS='" + vt.getBukrs() + "',BELNR='" + vt.getBelnr() + "',GJAHR='" + vt.getGjahr() + "',BUZEI='" + vt.getBuzei() + "'");
                if(!this.isTitleExistInSAP(vt, compareStr)){
                    if(!vt.getIscleared().equals("2")){
                        log.debug("----------------将 BUKRS='" + vt.getBukrs() + "',BELNR='" + vt.getBelnr() + 
                                "',GJAHR='" + vt.getGjahr() + "',BUZEI='" + vt.getBuzei() + 
                        "' 的供应商未清数据的ISCLEARED更新成'2'");
                        vt.setIscleared("2");
                        unclearTitleLog.setNewIscleared("2");
                        this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
                        vts4Update.add(vt);
                    }
                }else{
                    // 判断该未清数据是否已清
//                    if(this.vendorTitleJdbcDao.isVendorTitleClear(vt)){
//                        if(!vt.getIscleared().equals("1")){ // 判断是否需要更新
//                            log.debug("----------------将 BUKRS='" + vt.getBukrs() + "',BELNR='" + vt.getBelnr() + 
//                                    "',GJAHR='" + vt.getGjahr() + "',BUZEI='" + vt.getBuzei() + 
//                                    "' 的供应商未清数据的ISCLEARED更新成'1'");
//                            vt.setIscleared("1");
//                            unclearTitleLog.setNewIscleared("1");
//                            this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
//                            vts4Update.add(vt);
//                        }
//                    }else{
                        if(!vt.getIscleared().equals("0")){ // 判断是否需要更新
                            log.debug("----------------将 BUKRS='" + vt.getBukrs() + "',BELNR='" + vt.getBelnr() + 
                                    "',GJAHR='" + vt.getGjahr() + "',BUZEI='" + vt.getBuzei() + 
                                    "' 的供应商未清数据的ISCLEARED更新成'0'");
                            vt.setIscleared("0");
                            unclearTitleLog.setNewIscleared("0");
                            this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
                            vts4Update.add(vt);
                        }
//                    }
                }
            }
            this.vendorTitleHibernateDao.flush();
            this.vendorTitleHibernateDao.updateBatch(vts4Update);
            if(vts4Update.size() > 0){
                log.debug("----------------同步更新外围供应商未清抬头数据的ISCLEARED状态成功，共 " + vts4Update.size() + " 笔！");
            }else{
                log.debug("----------------无需同步更新外围供应商未清抬头数据的ISCLEARED状态！");
            }

            /*
             * 记录同步成功日志
            if(masterDataRfcTable.getAddDataList().size() > 0){
                SyncMasterLog syncMasterLog = new SyncMasterLog();
                syncMasterLog.setIssucceed("Y");
                syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                syncMasterLog.setSynctablename("YVENDORTITLE");
                syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                syncMasterLog.setErrMessage(" ");
                syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            }*/
        }

    }

	/**
	 * 根据供应商编号，取得供应商下未清发票数据集合
	 * 
	 * @param supplier
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<VendorTitleOthers> getVendorTitleListBySupplier(String supplier, String subject, String shkzg)
	{
		return this.vendorTitleJdbcDao.getVendorTitleListBySupplier(supplier, subject, shkzg);
	}

	/**
	 * 根据供应商编号，取得供应商下未清发票数据集合
	 * @param supplier
	 * 供应商编号
	 * @param subject
	 * 清帐科目
	 * @param currencytext
	 * 货币文本
	 * @param companyno
	 * 公司代码
	 * @param shkzg
	 * 业务范围
	 * @param depid
	 * 借方/贷方标识
	 * @return
	 */
	public List<VendorTitleOthers> getVendorTitleListBySupplier(String supplier, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		return this.vendorTitleJdbcDao.getVendorTitleListBySupplier(supplier, subject,currencytext,companyno,depid, shkzg);
	}
	/**
	 * 根据未清供应商数据抬头ID(可以多个逗号隔开),取得供应商下未清发票数据集合
	 * 
	 * @param user
	 * @return
	 */
	public List<VendorTitleOthers> getVendorTitleList(String vendortitleids)
	{
		return this.vendorTitleJdbcDao.getVendorTitleList(vendortitleids);
	}

	


	
	
	/**
	 * 更新未清供应商抬头数据上是否已结清标志
	 * 
	 * @param vendortitleid
	 *            未清供应商抬头ID
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateVendorTitleIsCleared(String vendortitleid, String isCleared)
	{
		this.vendorTitleJdbcDao.updateVendorTitleIsCleared(vendortitleid, isCleared);
	}


	


    
    /**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 用当前数据库里的数据去遍历SAP数据集，若在SAP数据集中找不到，则将YVENDORTITLE表中的本条数据的ISCLEARED字段更新成2
     */
    public boolean isTitleExistInSAP(VendorTitleOthers vt, String compareStr){
        String s = vt.getBukrs() + vt.getBelnr() + vt.getGjahr() + vt.getBuzei();
        if(compareStr.contains(s)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 更新冲销的状态
     * @param belnr
     * @param burks
     * @param gjahr
     * @param stblg
     * @param stjah
     * @param isCleared
     */
    public void updateVendorTitleIsClearedByBelnr(String belnr,String burks,String gjahr,String stblg,String stjah, String isCleared)
	{
    	this.vendorTitleJdbcDao.updateVendorTitleIsClearedByBelnr(belnr, burks, gjahr, stblg, stjah, isCleared);
	}
}