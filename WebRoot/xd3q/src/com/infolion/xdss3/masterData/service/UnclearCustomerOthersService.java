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

import org.apache.commons.lang.StringUtils;
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

import com.infolion.xdss3.masterData.dao.CustomerTitleOthersHibernateDao;
import com.infolion.xdss3.masterData.dao.CustomerTitleOthersJdbcDao;

import com.infolion.xdss3.masterData.dao.UnclearDataOthersJdbcDao;
import com.infolion.xdss3.masterData.dao.UnclearTitleLogHibernateDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;

import com.infolion.xdss3.masterData.domain.CustomerTitleOthers;
import com.infolion.xdss3.masterData.domain.MasterDataRfcTable;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.masterData.domain.UnclearTitleLog;
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
public class UnclearCustomerOthersService extends BaseService
{

	protected static Log log = LogFactory.getFactory().getLog(UnclearCustomerOthersService.class);

	@Autowired
	protected CustomerTitleOthersHibernateDao customerTitleHibernateDao;

	/**
	 * @param customerTitleHibernateDao
	 *            the customerTitleHibernateDao to set
	 */
	public void setCustomerTitleHibernateDao(CustomerTitleOthersHibernateDao customerTitleHibernateDao)
	{
		this.customerTitleHibernateDao = customerTitleHibernateDao;
	}





	@Autowired
	private UnclearDataOthersJdbcDao unclearDataJdbcDao;

	/**
	 * @param unlearDataJdbcDao
	 *            the unclearDataJdbcDao to set
	 */
	public void setUnlearDataJdbcDao(UnclearDataOthersJdbcDao unclearDataJdbcDao)
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
	private CustomerTitleOthersJdbcDao customerTitleJdbcDao;

	/**
	 * @param customerTitleJdbcDao
	 *            the customerTitleJdbcDao to set
	 */
	public void setCustomerTitleJdbcDao(CustomerTitleOthersJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
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
	 * @param customer
	 * @return
	 */
	public MasterDataRfcTable _executeRfcGetMasterData(String syncDate, String zdate, String customer)
	{
		MasterDataRfcTable masterDataRfcTable = new MasterDataRfcTable();

		JCO.Table addTitleDatas = null;
		JCO.Table addItemDatas = null;
		JCO.Table outPutDatas = null;
		JCO.Table inputTable = null; // 用于输入凭证编号

		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.UNCLEARCUSTOMER_OTHERS_RFCFunctionName);
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(zdate, "BUDAT_S");
				input.setValue(syncDate, "BUDAT_E");
				if (customer != null && !customer.equals(""))
				{
					input.setValue(customer, "CUSTOMER");
					masterDataRfcTable.setRealtimeCall(true);
				}
				else
				{
					masterDataRfcTable.setRealtimeCall(false);
				}

				// 输入凭证号
				inputTable = function.getTableParameterList().getTable("I_BELNR");
				// 不是第一次同步数据,需要获取未清凭证编号
				if (!zdate.equals(""))
				{
					List<CertificateNo> certificateNoList = this.customerTitleHibernateDao.getUnclearCustomerCertificateNoByBukrs(customer);
					if (certificateNoList != null && certificateNoList.size() > 0)
					{
						// 输入凭证号
						inputTable = function.getTableParameterList().getTable("I_BELNR");
						for (int i = 0; i < certificateNoList.size(); i++)
						{
							inputTable.appendRow();
							inputTable.setRow(i);
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
				log.error("----------------取得未清客户主数据发生错误：目标系统上不存在RFC" + SyncMasterDataConstants.UNCLEARCUSTOMER_OTHERS_RFCFunctionName);
				masterDataRfcTable = null;
			}
		}
		catch (Exception ex)
		{
			log.error("----------------取得未清客户数据发生错误" + ex);
			String errMessage = ex.getMessage();
			// 记录主数据同步日志。
			SyncMasterLog syncMasterLog = new SyncMasterLog();
			syncMasterLog.setIssucceed("N");
			syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
			syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
			syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
			syncMasterLog.setErrMessage("取得未清客户数据发生错误:" + errMessage.substring(0, 220));
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
			syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
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
			String compareStr = "";
			if(masterDataRfcTable.isFirstTimeSyn()){
				StringBuffer sb = new StringBuffer();  
				for(Map<String,String> map : masterDataRfcTable.getAddDataList()){
					sb.append(map.get("BUKRS") + "," + map.get("BELNR") + "," + map.get("GJAHR") + "," + map.get("BUZEI") + "|");
				}
				compareStr = sb.toString();
			}

			// 未清客户抬头
			for (Map map : masterDataRfcTable.getAddDataList())
			{
				CustomerTitleOthers customerTitle = new CustomerTitleOthers();
				ExBeanUtils.setBeanValueFromMap(customerTitle, map);
				log.debug("第" + i + "笔新增抬头数据:");
				log.debug("BELNR:" + map.get("BELNR"));
				log.debug("BUKRS:" + map.get("BUKRS"));
				log.debug("customerTitle.getBktxt():" + customerTitle.getBktxt());
				log.debug("customerTitle.getBelnr():" + customerTitle.getBelnr());
				customerTitle.setBktxt(customerTitle.getBktxt().replaceAll("[\\#]", "＃"));
				customerTitle.setBktxt(customerTitle.getBktxt().replaceAll("[\\%]", "％"));
				customerTitle.setBudat(customerTitle.getBudat().replaceAll("-", ""));
				i = i + 1;
				// 判断该未清数据是否已清
				
				 customerTitle.setIscleared("0"); // 同步时默认设置为未清；
				
				// 若为日元，则DMBTR乘以100再保存
				if(customerTitle.getWaers().equals("JPY")){
				    BigDecimal dmbtr = new BigDecimal(customerTitle.getDmbtr().doubleValue());
				    customerTitle.setDmbtr(dmbtr.multiply(new BigDecimal(100)));
				}
				// 根据VGBEL(销售订单号)到T_CONTRACT_SALES_INFO表中取出CONTRACT_NO放入IHREZ字段中
				if(StringUtils.isNotBlank(customerTitle.getVgbel())){
				    String contractNo = this.getContractNoByVgbel(customerTitle.getVgbel());
				    if(StringUtils.isNotBlank(contractNo)){
				        customerTitle.setIhrez(contractNo);
				    }
				}
				boolean isCustomerTitleExist = this.unclearDataJdbcDao.isCustomerTitleExist(customerTitle.getBukrs(), customerTitle.getBelnr(), 
				                                                                            customerTitle.getGjahr(), customerTitle.getBuzei());
				/*
				 * 若为首次执行客户未清数据同步
				 */
				if(masterDataRfcTable.isFirstTimeSyn()){	// 全量同步
				    if(isCustomerTitleExist){		// 若该数据已经在数据库中存在，则只更新CONTRACT_NO和VGBEL
				        String existTitleId = this.getTitleIdIfExist(customerTitle.getBukrs(), customerTitle.getBelnr(), 
				                                                     customerTitle.getGjahr(), customerTitle.getBuzei());
				        CustomerTitleOthers ct = this.customerTitleHibernateDao.get(existTitleId);
				        if(StringUtils.isNotBlank(customerTitle.getVgbel())){
				            ct.setVgbel(customerTitle.getVgbel());	// 更新"销售订单号"
				        }
				        if(StringUtils.isNotBlank(customerTitle.getIhrez())){
				            ct.setIhrez(customerTitle.getIhrez());	// 更新"销售合同号"
				        }
				        /*// 若为日元，则DMBTR乘以100再保存
		                if(ct.getWaers().equals("JPY")){
		                    BigDecimal dmbtr = new BigDecimal(ct.getDmbtr().doubleValue());
		                    ct.setDmbtr(dmbtr.multiply(new BigDecimal(100)));
		                }*/
				        this.customerTitleHibernateDao.update(ct);
				    }else{
				        this.customerTitleHibernateDao.save(customerTitle);
				    }
				}else if(isCustomerTitleExist == false){	// 增量同步
				    this.customerTitleHibernateDao.save(customerTitle);
				}
				/*****************************************************************************/
			}
			this.customerTitleHibernateDao.flush();
//			if(masterDataRfcTable.isFirstTimeSyn()){ // 若为全量更新
//			    // 用YCUSTOMERTITLE表中的ID跟SAP数据集的ID进行比较，
//                // 若在SAP数据集中找不到，则将YCUSTOMERTITLE表中的本条数据的ISCLEARED字段更新成2
//			    List<CustomerTitleOthers> cts = this.customerTitleHibernateDao.find("from CustomerTitleOthers");
//			    for(int k=0;k<cts.size();k++){
//			        if(!this.isTitleExistInSAP(cts.get(k), compareStr)){
//			            cts.get(k).setIscleared("2");
//			        }
//			    }
//			    this.customerTitleHibernateDao.updateBatch(cts);
//			}
//			
//			i = 1;
//			// 未清客户行项目
////			for (Map map : masterDataRfcTable.getAddDetailDataList())
////			{
////				CustomerDetail customerDetail = new CustomerDetail();
////				ExBeanUtils.setBeanValueFromMap(customerDetail, map);
////				log.debug("第" + i + "笔新增行项目数据:");
////				log.debug("INVOICE:" + map.get("INVOICE"));
////				log.debug("BUZEI:" + map.get("BUZEI"));
////				log.debug("customerDetail.getBuzei():" + customerDetail.getBuzei());
////				log.debug("customerDetail.getMatnr():" + customerDetail.getMatnr());
////				i = i + 1;
////				customerDetail.setIscleared("0"); // 同步时默认设置为未清；
////
////				// 实时调用
////				if (masterDataRfcTable.isRealtimeCall())
////				{
////					this.customerDetailHibernateDao.save(customerDetail);
////				}
////				else
////				{
////					boolean ret = this.unclearDataJdbcDao.isCustomerDetailExist(customerDetail.getInvoice());
////					if (ret == false)
////					{
////						this.customerDetailHibernateDao.save(customerDetail);
////					}
////				}
////
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
//
//				// ExBeanUtils.setBeanValueFromMap(certificateNo, map);
//				certificateNoList.add(certificateNo);
//
//			}
//			if (certificateNoList.size() > 0)
//			{
//				this.unclearDataJdbcDao.updateUnclearFlagForCustomer(certificateNoList);
//			}

			if (masterDataRfcTable.getAddDataList().size() > 0 )
	//				+ masterDataRfcTable.getAddDetailDataList().size() > 0)
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
					syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
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
     * 同步更新外围客户未清抬头数据的ISCLEARED状态
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
            syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
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
             * 用YCUSTOMERTITLE表中的ID跟SAP数据集的ID进行比较，
             * 若在SAP数据集中找不到，则将YCUSTOMERTITLE表中的本条数据的ISCLEARED字段更新成2
             */
            List<CustomerTitleOthers> cts = this.customerTitleHibernateDao.find("from CustomerTitleOthers");
            List<CustomerTitleOthers> cts4Update = new ArrayList<CustomerTitleOthers>();
            log.debug("----------------从SAP获取客户未清数据共 " + masterDataRfcTable.getAddDataList().size() + " 笔！");
            log.debug("----------------从外围获取客户未清数据共 " + cts.size() + " 笔！");
            for(int k = 0; k < cts.size(); k++){
            	CustomerTitleOthers ct = cts.get(k);
                UnclearTitleLog unclearTitleLog = new UnclearTitleLog();
                unclearTitleLog.setUnclearTitleId(ct.getCustomertitleid());
                unclearTitleLog.setCustomer(ct.getKunnr());
                unclearTitleLog.setOldIscleared(ct.getIscleared());
                unclearTitleLog.setLogTime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                unclearTitleLog.setRemark("BUKRS='" + ct.getBukrs() + "',BELNR='" + ct.getBelnr() + "',GJAHR='" + ct.getGjahr() + "',BUZEI='" + ct.getBuzei() + "'");
                if(!this.isTitleExistInSAP(ct, compareStr)){
                    if(!ct.getIscleared().equals("2")){
                        log.debug("----------------将 BUKRS='" + ct.getBukrs() + "',BELNR='" + ct.getBelnr() + 
                                "',GJAHR='" + ct.getGjahr() + "',BUZEI='" + ct.getBuzei() + 
                        "' 的客户未清数据的ISCLEARED更新成'2'");
                        ct.setIscleared("2");
                        unclearTitleLog.setNewIscleared("2");
                        this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
                        cts4Update.add(ct);
                    }
                }else{
                    // 判断该未清数据是否已清
//                    if(this.customerTitleJdbcDao.isCustomerTitleClear(ct)){
//                        if(!ct.getIscleared().equals("1")){ // 判断是否需要更新
//                            log.debug("----------------将 BUKRS='" + ct.getBukrs() + "',BELNR='" + ct.getBelnr() + 
//                                    "',GJAHR='" + ct.getGjahr() + "',BUZEI='" + ct.getBuzei() + 
//                                    "' 的客户未清数据的ISCLEARED更新成'1'");
//                            ct.setIscleared("1");
//                            unclearTitleLog.setNewIscleared("1");
//                            this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
//                            cts4Update.add(ct);
//                        }
//                    }else{
                        if(!ct.getIscleared().equals("0")){ // 判断是否需要更新
                            log.debug("----------------将 BUKRS='" + ct.getBukrs() + "',BELNR='" + ct.getBelnr() + 
                                    "',GJAHR='" + ct.getGjahr() + "',BUZEI='" + ct.getBuzei() + 
                                    "' 的客户未清数据的ISCLEARED更新成'0'");
                            ct.setIscleared("0");
                            unclearTitleLog.setNewIscleared("0");
                            this.unclearTitleLogHibernateDao.saveOrUpdate(unclearTitleLog);
                            cts4Update.add(ct);
                        }
//                    }
                }
            }
            this.unclearTitleLogHibernateDao.flush();
            this.customerTitleHibernateDao.updateBatch(cts4Update);
            if(cts4Update.size() > 0){
                log.debug("----------------同步更新外围客户未清抬头数据的ISCLEARED状态成功，共 " + cts4Update.size() + " 笔！");
            }else{
                log.debug("----------------无需同步更新外围客户未清抬头数据的ISCLEARED状态！");
            }
            
            /*
             * 记录同步成功日志
            if(masterDataRfcTable.getAddDataList().size() > 0){
                SyncMasterLog syncMasterLog = new SyncMasterLog();
                syncMasterLog.setIssucceed("Y");
                syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                syncMasterLog.setSynctablename("YCUSTOMERTITLE");
                syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                syncMasterLog.setErrMessage(" ");
                syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            }*/
        }
    }

	/**
	 * 取得未清客户发票
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearCustomerInvoiceList(String customertitleids)
	{
		return this.customerTitleHibernateDao.getUnclearCustomerInvoiceList(customertitleids);
	}

	/**
	 * 根据供应商编号，取得供应商下未清发票数据集合
	 * 
	 * @param custom
	 *            客户
	 * @param subject
	 *            清帐科目
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<CustomerTitleOthers> getCustomerTitleListBySupplier(String custom, String subject, String shkzg)
	{
		return this.customerTitleJdbcDao.getCustomerTitleListByCustom(custom, subject, shkzg);
	}
	/**
	 * 取得供应商下未清发票数据集合
	 * @param custom
	 *  客户
	 * @param subject
	 * 清帐科目
	 * @param currencytext
	 * 货币文本
	 * @param companyno
	 * 公司代码	 
	 * @param shkzg
	 * * 业务范围
	 * @param depid
	 * 借方/贷方标识
	 * @return
	 */
	public List<CustomerTitleOthers> getCustomerTitleListBySupplier(String custom, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		return this.customerTitleJdbcDao.getCustomerTitleListByCustom(custom, subject,currencytext,companyno,depid, shkzg);
	}
	
	/**
	 * 通过合同取得未清客户发票（根据客户进行过滤）
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByContract(String kunnr, String contractnos)
	{
		return this.customerTitleHibernateDao.getUnclearInvoiceListByContract(kunnr, contractnos);
	}

	/**
	 * 通过立项取得未清客户发票（根据客户进行过滤）
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByProject(String kunnr, String projectnos)
	{
		return this.customerTitleHibernateDao.getUnclearInvoiceListByProject(kunnr, projectnos);
	}
	
	/**
	 * 通过合同取得未清客户发票
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByContract(String contractnos)
	{
		return this.customerTitleHibernateDao.getUnclearInvoiceListByContract(contractnos);
	}

	/**
	 * 通过立项取得未清客户发票
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByProject(String projectnos)
	{
		return this.customerTitleHibernateDao.getUnclearInvoiceListByProject(projectnos);
	}

	/**
	 * 通过立项取得未清客户发票
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceList(String contractnos, String projectnos)
	{
		return this.customerTitleHibernateDao.getUnclearInvoiceList(contractnos, projectnos);
	}

	/**
	 * 根据业务状态，取得开票已经审批完金额、在途金额。
	 * 
	 * @param invoice
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates)
	{
		return this.customerTitleJdbcDao.getSumClearAmount(invoice, businessstates);
	}
	/**
	 * 根据业务状态，取得开票已经审批完金额、在途金额。
	 * 
	 * @param invoice
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String invoice, String businessstates,String billclearid)
	{
		return this.customerTitleJdbcDao.getSumClearAmount(invoice, businessstates,billclearid);
	}
	
	/**
	 * 更新未清客户抬头数据上是否已结清标志
	 * 
	 * @param customertitleid
	 *            未清客户抬头ID
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateCustomerTitleIsCleared(String customertitleid, String isCleared)
	{
		this.customerTitleJdbcDao.updateCustomerTitleIsCleared(customertitleid, isCleared);
	}

	/**
	 * 更新未清客户抬头数据上是否已结清标志
	 * 
	 * @param invoice
	 *            发票号
	 * @param isCleared
	 *            是否结清标志。
	 */
	public void updateCustomerTitleIsClearedByInvoice(String invoice, String isCleared)
	{
		this.customerTitleJdbcDao.updateCustomerTitleIsClearedByInvoice(invoice, isCleared);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-16
	 * 判断YCUSTOMERTITLE表中所有数据的VGBEL字段是否都为空，若是则同步时需要全量同步
	 */
	public boolean isNeedFullSync(){
		return this.customerTitleJdbcDao.isNeedFullSync();
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-16
	 * 根据VGBEL(销售订单号)到T_CONTRACT_SALES_INFO表中取出CONTRACT_NO
	 */
	public String getContractNoByVgbel(String vgbel){
		List<Map<String,String>> list = this.customerTitleJdbcDao.getContractNoByVgbel(vgbel);
		if(list!=null && list.size()>0){
			return list.get(0).get("CONTRACT_NO");
		}else{
			return "";
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-17
	 * 根据公司代码、会计凭证号码、会计年度去查找客户未清数据ID
	 */
	public String getTitleIdIfExist(String bukrs, String belnr, String gjahr, String buzei){
		List<Map<String,String>> list = this.customerTitleJdbcDao.getTitleIdIfExist(bukrs, belnr, gjahr, buzei);
		if(list!=null && list.size()>0){
			return list.get(0).get("CUSTOMERTITLEID");
		}else{
			return "";
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-11-17
	 * 用当前数据库里的数据去遍历SAP数据集，若在SAP数据集中找不到，则将YCUSTOMERTITLE表中的本条数据的ISCLEARED字段更新成2
	 */
	public boolean isTitleExistInSAP(CustomerTitleOthers ct, String compareStr){
		String s = ct.getBukrs() + ct.getBelnr() + ct.getGjahr() + ct.getBuzei();
		if(compareStr.contains(s)){
			return true;
		}else{
			return false;
		}
	}
	


    
    /**
	 * 取得当天的冲销凭证
	 * @param burks 公司代码 必填
	 * @param kunnr 客户或供应商编号 可选
	 * @param shkzg 借贷标识 可选（由于客户冲销，不管是开票，还是收款，都会清账（生成冲销凭证并清账，清账凭证号就是冲销凭证号）所以如果是客户的话，SHKZG不用填
	 *  由于供应商冲销，只有付款会冲销并清账，发票检验不会清账，所以如果是供应商的话，SHKZG要填S）
	 * @param flag  客户或供应商标识，1为客户，2为供应商，必填
	 */
	public List<Map<String, String>>  _executeRfcGetStblg(String burks,String kunnr,String shkzg,String flag){
		 // 同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        syncDate = syncDate.substring(0, 8);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		JCO.Table outPutDatas = null;		
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();

		JCO.Client client = null;
		try
		{
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate("ZF_GETSTBLG");
			if (ftemplate != null)
			{
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();				
				input.setValue(syncDate, "ZDATE");
				input.setValue(burks, "I_BUKRS");
				if(StringUtils.isNotBlank(kunnr)){
					input.setValue(kunnr, "I_KUNNR");
				}
				if(StringUtils.isNotBlank(shkzg)){
					input.setValue(shkzg, "I_SHKZG");
				}
				input.setValue(flag, "I_FLAG");
				client.execute(function);
				// 输出凭证号
				outPutDatas = function.getTableParameterList().getTable("T_BELNR");
				list = ExtractSapData.getList(outPutDatas);
				
			}
		}catch(Exception ex){
			
		}finally
		{
			manager.cleanUp();
		}
		return list;
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
	public void updateCustomerTitleIsClearedByBelnr(String belnr,String burks,String gjahr,String stblg,String stjah, String isCleared)
	{
		this.customerTitleJdbcDao.updateCustomerTitleIsClearedByBelnr(belnr, burks, gjahr, stblg, stjah, isCleared);
	}
}