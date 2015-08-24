/*
 * @(#)SyncMasterDataService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月25日 14点51分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.domain.MasterDataRfcTable;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerify;
import com.infolion.xdss3.tradeMonitoring.domain.InvoiceVerifyItem;
import com.infolion.xdss3.tradeMonitoring.domain.Vbrk;
import com.infolion.xdss3.tradeMonitoring.domain.Vbrp;
import com.infolion.xdss3.tradeMonitoring.service.InvoiceVerifyService;
import com.infolion.xdss3.tradeMonitoring.service.VbrkService;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 主数据同步(SyncMasterData)服务类
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
public class SyncMasterDataService
{
	private static Log log = LogFactory.getFactory().getLog(SyncMasterDataService.class);

	@Autowired
	private SupplierService supplierService;

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	public void setSupplierService(SupplierService supplierService)
	{
		this.supplierService = supplierService;
	}

	@Autowired
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService)
	{
		this.customerService = customerService;
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
	private HkontService hkontService;

	/**
	 * @param hkontService
	 *            the hkontService to set
	 */
	public void setHkontService(HkontService hkontService)
	{
		this.hkontService = hkontService;
	}

	@Autowired
	private CashFlowItemService cashFlowItemService;

	/**
	 * @param cashFlowItem
	 *            the cashFlowItem to set
	 */
	public void setCashFlowItemService(CashFlowItemService cashFlowItemService)
	{
		this.cashFlowItemService = cashFlowItemService;
	}

	@Autowired
	private CostCenterService costCenterService;

	/**
	 * @param costCenterService
	 *            the costCenterService to set
	 */
	public void setCostCenterService(CostCenterService costCenterService)
	{
		this.costCenterService = costCenterService;
	}

	@Autowired
	private PrctrService prctrService;

	/**
	 * @param prctrService
	 *            the prctrService to set
	 */
	public void setPrctrService(PrctrService prctrService)
	{
		this.prctrService = prctrService;
	}

	@Autowired
	private VbrkService vbrkService;

	/**
	 * @param vbrkService
	 *            the vbrkService to set
	 */
	public void setVbrkService(VbrkService vbrkService)
	{
		this.vbrkService = vbrkService;
	}

	@Autowired
	private InvoiceVerifyService invoiceVerifyService;

	/**
	 * @param invoiceVerifyService
	 *            the invoiceVerifyService to set
	 */
	public void setInvoiceVerifyService(InvoiceVerifyService invoiceVerifyService)
	{
		this.invoiceVerifyService = invoiceVerifyService;
	}

	@Autowired
	private VendorService vendorService;

	/**
	 * @param vendorService
	 *            the vendorService to set
	 */
	public void setVendorService(VendorService vendorService)
	{
		this.vendorService = vendorService;
	}

	@Autowired
	private UnclearCustomerService unclearCustomerService;

	/**
	 * @param unclearCustomerService
	 *            the unclearCustomerService to set
	 */
	public void setUnclearCustomerService(UnclearCustomerService unclearCustomerService)
	{
		this.unclearCustomerService = unclearCustomerService;
	}

	@Autowired
	private CompanyService companyService;

	/**
	 * @param companyService
	 *            the companyService to set
	 */
	public void setCompanyService(CompanyService companyService)
	{
		this.companyService = companyService;
	}

	@Autowired
	private DeptService DeptService;

	/**
	 * @param deptService
	 *            the deptService to set
	 */
	public void setDeptService(DeptService deptService)
	{
		DeptService = deptService;
	}
	
	@Autowired
	private MatGroupService matGroupService;
	
	
	/**
	 * @param matGroupService the matGroupService to set
	 */
	public void setMatGroupService(MatGroupService matGroupService) {
		this.matGroupService = matGroupService;
	}
	
	@Autowired
	private TcurrService tcurrService;
	
	/**
	 * @param tcurrService the tcurrService to set
	 */
	public void setTcurrService(TcurrService tcurrService) {
		this.tcurrService = tcurrService;
	}
	
	@Autowired
    private BsegService bsegService;
	
    public void setBsegService(BsegService bsegService){
        this.bsegService = bsegService;
    }

    @Autowired
	private VendorOthersService vendorOthersService;

	/**
	 * @param vendorService
	 *            the vendorService to set
	 */
	public void setVendorOthersService(VendorOthersService vendorOthersService)
	{
		this.vendorOthersService = vendorOthersService;
	}

	@Autowired
	private UnclearCustomerOthersService unclearCustomerOthersService;

	/**
	 * @param unclearCustomerService
	 *            the unclearCustomerService to set
	 */
	public void setUnclearCustomerOthersService(UnclearCustomerOthersService unclearCustomerOthersService)
	{
		this.unclearCustomerOthersService = unclearCustomerOthersService;
	}
	
    /**
	 * 执行指定方法。
	 * 
	 * @param methodName
	 */
	public void invoke(String methodName)
	{
		try
		{
			this.getClass().getMethod(methodName).invoke(this);
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 同步供应商主数据。
	 */
	public void _synchronizeSupplier()
	{
		UserContext userContext = new UserContext();
		userContext.setClient("800");
		UserContextHolder.setCurrentContext(userContext);
		// 供应商主数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.Supplier_TableName);
		log.debug("----------------供应商主数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		MasterDataRfcTable masterDataRfcTable = this.supplierService._executeRfcGetMasterData(syncDate, zdate);
		if (null == masterDataRfcTable)
		{}
		else
		{
			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() > 0)
			{
				try
				{
					this.supplierService._ayncMasterData(masterDataRfcTable);
					log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (新增)+ " + masterDataRfcTable.getModifyDataList().size() + "(修改)供应商主数据同步成功！");
				}
				catch (Exception e)
				{
					String errMessage = e.getMessage() + e.getCause();
					// 记录主数据同步日志。
					SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("N");
					syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
					syncMasterLog.setSynctablename(SyncMasterDataConstants.Supplier_TableName);
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(errMessage.substring(0, 254));
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
					log.error("----------------执行供应商主数据同步错误，错误信息为:" + errMessage);
					// e.printStackTrace();
				}
			}
			else
				log.debug("----------------无需要同步的供应商主数据！");
		}

	}

	/**
	 * 同步客户主数据。
	 */
	public void _synchronizeCustomer()
	{
		// 客户主数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.Customer_TableName);
		log.debug("----------------客户主数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		MasterDataRfcTable masterDataRfcTable = this.customerService._executeRfcGetMasterData(syncDate, zdate);
		if (null == masterDataRfcTable)
		{}
		else
		{
			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() > 0)
			{
				try
				{
					this.customerService._ayncMasterData(masterDataRfcTable);
					log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (新增)+ " + masterDataRfcTable.getModifyDataList().size() + "(修改)客户主数据同步成功！");
				}
				catch (Exception e)
				{
					String errMessage = e.getMessage() + e.getCause();
					// 记录主数据同步日志。
					SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("N");
					syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
					syncMasterLog.setSynctablename(SyncMasterDataConstants.Customer_TableName);
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(errMessage.substring(0, 254));
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
					log.error("----------------执行客户主数据同步错误，错误信息为:" + errMessage);
					// e.printStackTrace();
				}
			}
			else
				log.debug("----------------无需要同步的客户主数据！");
		}

	}

	/**
	 * 同步会计科目数据。
	 */
	public void _synchronizeHkont()
	{
		// 会计科目数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.Hkont_TableName);
		log.debug("----------------会计科目数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		List<Map<String, String>> list = this.hkontService._executeRfcGetMasterData(syncDate, zdate);
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.hkontService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔会计科目数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行会计科目数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得笔会计科目数据！--------------------");
			}
		}

	}

	/**
	 * 同步现金流量数据。
	 */
	public void _synchronizeCashFlowItem()
	{
		// 现金流量数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.CashFlowItem_TableName);
		log.debug("----------------现金流量数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		List<Map<String, String>> list = this.cashFlowItemService._executeRfcGetMasterData(syncDate, zdate);
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.cashFlowItemService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔现金流量数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行现金流量数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得现金流量数据！--------------------");
			}
		}

	}

	/**
	 * 同步成本中心数据。
	 */
	public void _synchronizeCostCenter()
	{
		// 成本中心同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.CostCenter_TableName);
		log.debug("----------------成本中心数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		List<Map<String, String>> list = this.costCenterService._executeRfcGetMasterData(syncDate, zdate);
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.costCenterService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔成本中心数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行成本中心数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得成本中心数据！--------------------");
			}
		}

	}

	/**
	 * 同步利润中心数据。
	 */
	public void _synchronizePrctr()
	{
		// 利润中心同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.CostCenter_TableName);
		log.debug("----------------利润中心数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		List<Map<String, String>> list = this.prctrService._executeRfcGetMasterData(syncDate, zdate);
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.prctrService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔利润中心数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行利润中心数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得利润中心数据！--------------------");
			}
		}

	}

	/**
	 * 同步开票业务数据。
	 */
	public void _synchronizeVbrk()
	{
		UserContext userContext = new UserContext();
		userContext.setClient("800");
		UserContextHolder.setCurrentContext(userContext);
		// 开票业务数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.Vbrk_TableName);
		// 调整往早日期推一天
		Calendar cd = Calendar.getInstance();
		try {
            cd.setTime( DateUtils.getDate(zdate.substring(0, 8), "yyyyMMdd"));
            cd.add(cd.DAY_OF_YEAR, -1);
            String newStrDate = (new   SimpleDateFormat( "yyyyMMdd")).format(cd.getTime());
            zdate = newStrDate + zdate.substring(8);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
		
		log.debug("----------------开票业务数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
	//	MasterDataRfcTable masterDataRfcTable = this.vbrkService._executeRfcGetMasterData(syncDate, zdate);
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
		
		if (null == masterDataRfcTable)
		{}
		else
		{
			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() + masterDataRfcTable.getAddDetailDataList().size() > 0)
			{
				try
				{
					//this.vbrkService._ayncMasterData(masterDataRfcTable);
					_ayncMasterData(masterDataRfcTable);
					log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (新增)+ " + masterDataRfcTable.getModifyDataList().size() + "(修改)开票业务抬头数据同步成功！");
					log.debug("----------------共" + masterDataRfcTable.getAddDetailDataList().size() + " (新增)开票业务明细数据同步成功！");
				}
				catch (Exception e)
				{
					String errMessage = e.getMessage() + e.getCause();
					// 记录主数据同步日志。
					SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("N");
					syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
					syncMasterLog.setSynctablename(SyncMasterDataConstants.Vbrk_TableName);
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(errMessage.substring(0, 254));
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
					log.error("----------------执行开票业务数据同步错误，错误信息为:" + errMessage);
					// e.printStackTrace();
				}
			}
			else
				log.debug("----------------无需同步开票业务数据！");
		}

	}

	public void _ayncMasterData(MasterDataRfcTable masterDataRfcTable) throws Exception
	{
	    BusinessObject businessObject = BusinessObjectService.getBusinessObject("Vbrk");
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
			log.debug("--------准备--------共" + masterDataRfcTable.getAddDataList().size() + " 开票业务抬头(新增)+ " + masterDataRfcTable.getAddDetailDataList().size() + " 开票业务明细数据（新增） " + masterDataRfcTable.getModifyDataList().size() + " (修改)笔开票业务数据抬头需要同步！");
			int i = 1;
			Map<String,String> m = new HashMap<String,String>();
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
				//修改原来的冲销字段
				if(StringUtils.isNotBlank(vbrk.getSfakn())){
					String vbeln = StringUtils.leftPad(vbrk.getSfakn(), 10, '0');
					m.put(vbrk.getSfakn(),vbrk.getVbeln());
					if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From ygetvbrk where vbeln='"+vbeln+"'")) {
						this.vbrkService.getTradeMonitoringJdbcDao().updateSfakn(vbeln, vbrk.getVbeln());
					}
				}
                if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From ygetvbrk where vbeln='"+vbrk.getVbeln()+"'")) {
                    log.debug("此条数据已存在：" + vbrk.getVbeln());
                    continue;
                }                
				this.vbrkService.getVbrkHibernateDao().save(vbrk);
				
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
				if(StringUtils.isBlank(vbrk.getSfakn())){
					vbrk.setSfakn(m.get(vbrk.getVbeln()));
				}
				if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From ygetvbrk where vbeln='"+vbrk.getVbeln()+"'")) {
                    log.debug("此条数据已存在：" + vbrk.getVbeln());
//                    this.vbrkService._delete(vbrk.getVbeln(), businessObject);
                    //删除不要，原因修改抬头数据只有冲销会修改，原来数据可以不变，因为我在冲销的数据那边去修改了被冲销的数据
                    continue;
                }
				//修改原来的冲销字段
//				if(StringUtils.isNotBlank(vbrk.getSfakn())){
//					String vbeln = StringUtils.leftPad(vbrk.getSfakn(), 10, '0');
//					if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From ygetvbrk where vbeln='"+vbeln+"'")) {
//						this.vbrkService.getTradeMonitoringJdbcDao().updateSfakn(vbeln, vbrk.getVbeln());
//					}
//				}
				
				this.vbrkService.getVbrkHibernateDao().saveOrUpdate(vbrk);
			}

			i = 1;
			String sql  = "";
			for (Map map : masterDataRfcTable.getAddDetailDataList())
			{
				Vbrp vbrp = new Vbrp();
				Vbrk vbrk = new Vbrk();
				vbrk.setVbeln((String) map.get("VBELN"));
				vbrp.setVbrk(vbrk);
				String vbLen = (String) map.get("VBELN");
				if(map.get("VBELN") == null || "".equals(vbLen.trim())){
					log.debug("第" + i + "笔新增行项目数据的VBLEN为空!!!");
				}

				log.debug("第" + i + "笔新增行项目数据:");
				ExBeanUtils.setBeanValueFromMap(vbrp, map);
				log.debug("VBELN:" + map.get("VBELN"));
				log.debug("ARKTX:" + map.get("ARKTX"));
				log.debug("vbrp.getVrkme():" + vbrp.getVrkme());
				i = i + 1;
                sql = "select 'x' From ygetvbrp where matnr = '" + vbrp.getMatnr() + "' " + "and vbeln = '"
                        + vbrk.getVbeln() + "' " + "and fkimg = '" + vbrp.getFkimg() + "' " + "and vrkme = '"
                        + vbrp.getVrkme() + "' " + "and aubel = '" + vbrp.getAubel() + "' " + "and taxout = " + vbrp.getTaxout();
                if (this.vbrkService.getTradeMonitoringJdbcDao().isExists(sql)) {
                    log.debug("此条数据忽略：" + vbLen);
                    continue;
                }
				this.vbrkService.getVbrpHibernateDao().save(vbrp);
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
	
	/**
	 * 同步发票校验数据。
	 */
	@Deprecated
	public void _synchronizeRseg()
	{
		// UserContext userContext = new UserContext();
		// userContext.setClient("800");
		// UserContextHolder.setCurrentContext(userContext);
		// // 发票校验数据同步：
		// // 同步时间
		// String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// // 上次同步成功时间
		// String zdate =
		// this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.RSEG_TableName);
		// log.debug("----------------发票校验数据同步: syncDate:" + syncDate +
		// ",zdate:" + zdate);
		// MasterDataRfcTable masterDataRfcTable =
		// this.rsegService._executeRfcGetMasterData(syncDate, zdate);
		// if (null == masterDataRfcTable)
		// {}
		// else
		// {
		// if (masterDataRfcTable.getAddDataList().size() +
		// masterDataRfcTable.getModifyDataList().size() +
		// masterDataRfcTable.getAddDetailDataList().size() > 0)
		// {
		// try
		// {
		// this.rsegService._ayncMasterData(masterDataRfcTable);
		// log.debug("----------------共" +
		// masterDataRfcTable.getAddDataList().size() + " (新增)+ " +
		// masterDataRfcTable.getModifyDataList().size() + "(修改)发票校验抬头数据同步成功！");
		// log.debug("----------------共" +
		// masterDataRfcTable.getAddDetailDataList().size() +
		// " (新增)发票校验行项目数据同步成功！");
		// }
		// catch (Exception e)
		// {
		// String errMessage = e.getMessage() + e.getCause();
		// // 记录主数据同步日志。
		// SyncMasterLog syncMasterLog = new SyncMasterLog();
		// syncMasterLog.setIssucceed("N");
		// syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
		// syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEG_TableName);
		// syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
		// syncMasterLog.setErrMessage(errMessage.substring(0, 254));
		// syncMasterLogService._saveSyncMasterLog(syncMasterLog);
		// log.error("----------------执行发票校验数据同步错误，错误信息为:" + errMessage);
		// // e.printStackTrace();
		// }
		// }
		// else
		// log.debug("----------------无需同步发票校验数据！");
		// }

	}

	/**
	 * 同步发票校验数据。
	 */
	public void _synchronizeInvoiceVerify()
	{
	    BusinessObject businessObject = BusinessObjectService.getBusinessObject("InvoiceVerify");
		UserContext userContext = new UserContext();
		userContext.setClient("800");
		UserContextHolder.setCurrentContext(userContext);
		// 发票校验数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		// 上次同步成功时间
		String zdate = this.syncMasterLogService._getSyncDate(SyncMasterDataConstants.RSEG_TableName);
	      // 调整往早日期推一天
        Calendar cd = Calendar.getInstance();
        try {
            cd.setTime( DateUtils.getDate(zdate.substring(0, 8), "yyyyMMdd"));
            cd.add(cd.DAY_OF_YEAR, -1);
            String newStrDate = (new   SimpleDateFormat( "yyyyMMdd")).format(cd.getTime());
            zdate = newStrDate + zdate.substring(8);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
		log.debug("----------------发票校验数据同步: syncDate:" + syncDate + ",zdate:" + zdate);
		MasterDataRfcTable masterDataRfcTable = this.invoiceVerifyService._executeRfcGetMasterData(zdate, syncDate);
		if (null == masterDataRfcTable)
		{}
		else
		{
			if (masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getModifyDataList().size() + masterDataRfcTable.getAddDetailDataList().size() > 0)
			{
				try
				{
				//	this.invoiceVerifyService._ayncMasterData(masterDataRfcTable);
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
                            if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From YGETBELNR where maincode='"+invoiceVerify1.getMaincode()+"'")) {
                                log.debug("此条数据已存在：" + invoiceVerify1.getMaincode());
                                continue;
                            }
							this.invoiceVerifyService.getInvoiceVerifyHibernateDao().save(invoiceVerify1);
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
							if (this.vbrkService.getTradeMonitoringJdbcDao().isExists("select 'x' From YGETBELNR where maincode='"+invoiceVerify1.getMaincode()+"'")) {
							    this.invoiceVerifyService._delete(invoiceVerify2.getMaincode(), businessObject);
                                log.debug("此条数据已存在：" + invoiceVerify2.getMaincode());
                            }
							this.invoiceVerifyService.getInvoiceVerifyHibernateDao().saveOrUpdate(invoiceVerify2);
						}

						int ii = 1;
						String sql  = "";
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
                                    sql = "select 'x' From YGETITEM where MAINCODE = '" + invoiceVerifyT.getMaincode()
                                            + "' " + "and buzei = '" + invoiceVerifyItem.getBuzei() + "' "
                                            + "and menge = '" + invoiceVerifyItem.getMenge() + "' " + "and shuilv = '"
                                            + invoiceVerifyItem.getShuilv() + "' ";	
				                if (this.vbrkService.getTradeMonitoringJdbcDao().isExists(sql)) {
				                    log.debug("此条数据忽略：" + map.get("MAINCODE"));
				                    continue;
				                }
								this.invoiceVerifyService.getInvoiceVerifyItemHibernateDao().save(invoiceVerifyItem);
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
				
					log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (新增)+ " + masterDataRfcTable.getModifyDataList().size() + "(修改)发票校验抬头数据同步成功！");
					log.debug("----------------共" + masterDataRfcTable.getAddDetailDataList().size() + " (新增)发票校验行项目数据同步成功！");
				}
				catch (Exception e)
				{
					String errMessage = e.getMessage() + e.getCause();
					// 记录主数据同步日志。
					SyncMasterLog syncMasterLog = new SyncMasterLog();
					syncMasterLog.setIssucceed("N");
					syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
					syncMasterLog.setSynctablename(SyncMasterDataConstants.RSEG_TableName);
					syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
					syncMasterLog.setErrMessage(errMessage.substring(0, 254));
					syncMasterLogService._saveSyncMasterLog(syncMasterLog);
					log.error("----------------执行发票校验数据同步错误，错误信息为:" + errMessage);
					// e.printStackTrace();
				}
			}
			else
				log.debug("----------------无需同步发票校验数据！");
		}

	}

	/**
	 * 同步未清供应商数据。
	 */
    public void _synchronizeUnclearVendor(String vendor,boolean isUpdate){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 未清供应商数据同步：
        // 同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        // 上次同步成功时间
        String zdate = this.syncMasterLogService._getSyncDate("YVENDORTITLE").trim();
        log.debug("----------------同步未清供应商数据: syncDate:" + syncDate + ",zdate:" + zdate);
        MasterDataRfcTable masterDataRfcTable = this.vendorService._executeRfcGetMasterData(syncDate, zdate, vendor);
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size()
            // + masterDataRfcTable.getAddDetailDataList().size()
                    + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.vendorService._ayncMasterData(masterDataRfcTable);
                    log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (笔未清供应商抬头数据新增)");
                    // + masterDataRfcTable.getAddDetailDataList().size()
                    // + "笔未清供应商行项目数据新增数据同步成功！");
                    log.debug("----------------共" + masterDataRfcTable.getOutputTableDataList().size() + " 笔凭证编号数据输出成功！");
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    // 记录主数据同步日志。
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YVENDORTITLE");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行供应商未清数据同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步供应商未清数据！");
            }
        }
        if(isUpdate){
	        if(StringUtils.isNotBlank(vendor)){ // 若供应商编号不为空，则还需要执行更新客户未清抬头数据的存储过程
	            log.debug("----------------执行供应商(" + vendor + ")未清抬头状态更新的存储过程……");
	            this.vendorService.updateVendorTitle(vendor);
	            log.debug("----------------执行供应商(" + vendor + ")未清抬头状态更新的存储过程成功！");
	        }else{                                 // 否则执行更新全部供应商未清抬头数据的存储过程 
	            log.debug("----------------执行全部供应商未清抬头状态更新的存储过程……");
	            this.vendorService.updateVendorTitle();
	            log.debug("----------------执行全部供应商未清抬头状态更新的存储过程成功！");
	        }
        }
    }

	// 定时器调用
	public void _synchronizeUnclearVendor()
	{
		this._synchronizeUnclearVendor(null,true);
	}

	/**
	 * 同步未清客户数据。
	 */
    public void _synchronizeUnclearCustomer(String customer,boolean isUpdate){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 未清客户数据同步：
        // 同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        // 上次同步时间
        String zdate = this.syncMasterLogService._getSyncDate("YCUSTOMERTITLE").trim();
        // 先判断是否需要全量更新
        if(this.unclearCustomerService.isNeedFullSync()){
            customer = "";  // 将客户编号清空
            zdate = "";     // 将上次同步时间清空
        }
        log.debug("----------------同步未清客户数据: syncDate:" + syncDate + ",zdate:" + zdate);
        MasterDataRfcTable masterDataRfcTable = this.unclearCustomerService._executeRfcGetMasterData(syncDate, zdate, customer);
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size()
            // + masterDataRfcTable.getAddDetailDataList().size()
                    + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.unclearCustomerService._ayncMasterData(masterDataRfcTable);
                    log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (笔未清客户抬头数据新增)");
                    // + masterDataRfcTable.getAddDetailDataList().size()
                    // + "笔未清客户行项目数据新增数据同步成功！");
                    log.debug("----------------共" + masterDataRfcTable.getOutputTableDataList().size() + " 笔凭证编号数据输出成功！");
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    // 记录主数据同步日志。
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("ycustomertitle");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行客户未清数据同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步客户未清数据！");
            }
        }
        if(isUpdate){
	        if(StringUtils.isNotBlank(customer)){   // 若客户编号不为空，则执行更新单个客户未清抬头数据的存储过程
	            log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程……");
	            this.unclearCustomerService.updateCustomerTitle(customer);
	            log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程成功！");
	        }else{                                 // 否则执行更新全部客户未清抬头数据的存储过程 
	            log.debug("----------------执行全部客户未清抬头状态更新的存储过程……");
	            this.unclearCustomerService.updateCustomerTitle();
	            log.debug("----------------执行全部客户未清抬头状态更新的存储过程成功！");
	        }
        }
    }

	// 定时器调用
	public void _synchronizeUnclearCustomer()
	{
		this._synchronizeUnclearCustomer(null,true);
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 同步更新外围客户未清抬头数据的ISCLEARED状态(定时器调用)
     */
    public void _syncAllCustomerTitleState(){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 本次同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        log.debug("----------------同步更新客户未清抬头数据的ISCLEARED状态: syncDate:" + syncDate + ",zdate:" + "");
        MasterDataRfcTable masterDataRfcTable = this.unclearCustomerService._executeRfcGetMasterData(syncDate, "", "");
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.unclearCustomerService._syncAllCustomerTitleState(masterDataRfcTable);
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YCUSTOMERTITLE");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行客户未清抬头数据的ISCLEARED状态同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步更新客户未清抬头数据的ISCLEARED状态！");
            }
        }
/*        log.debug("----------------执行全部客户未清抬头状态更新的存储过程……");
        this.unclearCustomerService.updateCustomerTitle();
        log.debug("----------------执行全部客户未清抬头状态更新的存储过程成功！");*/
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 同步更新外围供应商未清抬头数据的ISCLEARED状态(定时器调用)
     */
    public void _syncAllSupplierTitleState(){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 本次同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        log.debug("----------------同步更新供应商未清抬头数据的ISCLEARED状态: syncDate:" + syncDate + ",zdate:" + "");
        MasterDataRfcTable masterDataRfcTable = this.vendorService._executeRfcGetMasterData(syncDate, "", "");
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.vendorService._syncAllCustomerTitleState(masterDataRfcTable);
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YVENDORTITLE");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行供应商未清抬头数据的ISCLEARED状态同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步更新供应商未清抬头数据的ISCLEARED状态！");
            }
        }
    }

	/**
	 * 同步公司数据。
	 */
	public void _synchronizeCompany()
	{
		// 公司数据同步：
		log.debug("----------------开始公司数据同步：");
		List<Map<String, String>> list = this.companyService._executeRfcGetMasterData();
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.companyService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔公司数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行公司数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得公司数据！--------------------");
			}
		}

	}

	/**
	 * 同步部门数据。
	 */
	public void _synchronizeDept()
	{
		// 部门数据同步：
		log.debug("----------------开始部门数据同步：");
		List<Map<String, String>> list = this.DeptService._executeRfcGetMasterData();
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.DeptService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔部门数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行部门数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得部门数据！--------------------");
			}
		}
	}
	
	/**
	 * 同步物料组。
	 */
	public void _synchronizeMatGroup()
	{
		// 物料组数据同步：
		// 同步时间
		String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
		log.debug("----------------开始同步物料组数据--------------------- ");
		List<Map<String, String>> list = this.matGroupService._executeRfcGetMasterData();
		if (null == list)
		{}
		else
		{
			if (list.size() > 0)
			{
				try
				{
					this.matGroupService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔物料组数据成功！");
				}
				catch (Exception e)
				{
					log.error("----------------执行物料组数据同步错误，错误信息为:" + e.getMessage());
				}
			}
			else
			{
				log.debug("----------------没有获得物料组数据！--------------------");
			}
		}

	}
	
	/**
	 * 同步汇率数据
	 */
	public void _synchronizeTcurr(){
		log.debug("----------------开始同步汇率数据--------------------- ");
		List<Map<String, String>> list = this.tcurrService._executeRfcGetMasterData();
		if(null == list){
		}else{
			if(list.size() > 0){
				try{
					this.tcurrService._ayncMasterData(list);
					log.debug("----------------共获得" + list.size() + "笔汇率数据成功！");
				}catch(Exception e){
					log.error("----------------执行汇率数据同步错误，错误信息为:" + e.getMessage());
				}
			}else{
				log.debug("----------------没有获得汇率数据！--------------------");
			}
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建时间：2010-12-16
	 * 同步凭证数据(有必要将定时器设定在当天晚上12点之前)
	 */
	public void _synchronizeBseg(){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);    // 当前同步时间
        String zdate = this.syncMasterLogService._getSyncDate("YGETBSEG");      // 上次同步时间
        log.debug("----------------同步凭证数据(BSEG): syncDate:" + syncDate + ",zdate:" + zdate + "……");
        
        try{
            this.bsegService._executeRfcGetMasterData(syncDate, zdate);
            log.debug("----------------成功同步凭证数据(BSEG): syncDate:" + syncDate + ",zdate:" + zdate + "！");
        }catch(Exception e){
            String errMessage = e.getMessage() + e.getCause();
            SyncMasterLog syncMasterLog = new SyncMasterLog();
            syncMasterLog.setIssucceed("N");
            syncMasterLog.setSyncdate(syncDate);
            syncMasterLog.setSynctablename("YGETBSEG");
            syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
            syncMasterLog.setErrMessage(errMessage);
            syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            log.error("----------------执行凭证数据(BSEG)同步错误，错误信息为:" + errMessage);
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
	public void _synchronizeWriteOff(String burks,String kunnr,String shkzg,String flag){
		List<Map<String, String>> list = unclearCustomerService._executeRfcGetStblg(burks, kunnr, shkzg, flag);
		for (Map map : list){
			String belnr = map.get("BELNR").toString();
			String gjahr = map.get("GJAHR").toString();
			String bukrs = map.get("BUKRS").toString();
			String stblg = map.get("STBLG").toString();
			String stjah = map.get("STJAH").toString();
			//跟其他的冲销凭证区别开。
			stblg = "Z" + stblg;
			//1为客户
			if("1".equals(flag)){
				this.unclearCustomerService.updateCustomerTitleIsClearedByBelnr(belnr, bukrs, gjahr, stblg, stjah, "2");
			}else{
				this.vendorService.updateVendorTitleIsClearedByBelnr(belnr, bukrs, gjahr, stblg, stjah, "2");
			}
		}
	}
	
	/**
	 * 同步未清供应商数据其他公司。
	 */
    public void _synchronizeUnclearVendorOthers(String vendor){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 未清供应商数据同步：
        // 同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        // 上次同步成功时间
        String zdate = this.syncMasterLogService._getSyncDate("YVENDORTITLEOTHERS").trim();
        log.debug("----------------同步未清供应商数据: syncDate:" + syncDate + ",zdate:" + zdate);
        MasterDataRfcTable masterDataRfcTable = this.vendorOthersService._executeRfcGetMasterData(syncDate, zdate, vendor);
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size()
            // + masterDataRfcTable.getAddDetailDataList().size()
                    + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.vendorOthersService._ayncMasterData(masterDataRfcTable);
                    log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (笔未清供应商抬头数据新增)");
                    // + masterDataRfcTable.getAddDetailDataList().size()
                    // + "笔未清供应商行项目数据新增数据同步成功！");
                    log.debug("----------------共" + masterDataRfcTable.getOutputTableDataList().size() + " 笔凭证编号数据输出成功！");
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    // 记录主数据同步日志。
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行供应商未清数据同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步供应商未清数据！");
            }
        }
//        if(StringUtils.isNotBlank(vendor)){ // 若供应商编号不为空，则还需要执行更新客户未清抬头数据的存储过程
//            log.debug("----------------执行供应商(" + vendor + ")未清抬头状态更新的存储过程……");
//            this.vendorOthersService.updateVendorTitle(vendor);
//            log.debug("----------------执行供应商(" + vendor + ")未清抬头状态更新的存储过程成功！");
//        }else{                                 // 否则执行更新全部供应商未清抬头数据的存储过程 
//            log.debug("----------------执行全部供应商未清抬头状态更新的存储过程……");
//            this.vendorOthersService.updateVendorTitle();
//            log.debug("----------------执行全部供应商未清抬头状态更新的存储过程成功！");
//        }
    }

	// 定时器调用
	public void _synchronizeUnclearVendorOthers()
	{
		this._synchronizeUnclearVendorOthers(null);
	}

	/**
	 * 同步未清客户数据。
	 */
    public void _synchronizeUnclearCustomerOthers(String customer){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 未清客户数据同步：
        // 同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        // 上次同步时间
        String zdate = this.syncMasterLogService._getSyncDate("YCUSTOMERTITLEOTHERS").trim();
        // 先判断是否需要全量更新
        if(this.unclearCustomerService.isNeedFullSync()){
            customer = "";  // 将客户编号清空
            zdate = "";     // 将上次同步时间清空
        }
        log.debug("----------------同步未清客户数据: syncDate:" + syncDate + ",zdate:" + zdate);
        MasterDataRfcTable masterDataRfcTable = this.unclearCustomerOthersService._executeRfcGetMasterData(syncDate, zdate, customer);
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size()
            // + masterDataRfcTable.getAddDetailDataList().size()
                    + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.unclearCustomerOthersService._ayncMasterData(masterDataRfcTable);
                    log.debug("----------------共" + masterDataRfcTable.getAddDataList().size() + " (笔未清客户抬头数据新增)");
                    // + masterDataRfcTable.getAddDetailDataList().size()
                    // + "笔未清客户行项目数据新增数据同步成功！");
                    log.debug("----------------共" + masterDataRfcTable.getOutputTableDataList().size() + " 笔凭证编号数据输出成功！");
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    // 记录主数据同步日志。
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行客户未清数据同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步客户未清数据！");
            }
        }
//        if(StringUtils.isNotBlank(customer)){   // 若客户编号不为空，则执行更新单个客户未清抬头数据的存储过程
//            log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程……");
//            this.unclearCustomerService.updateCustomerTitle(customer);
//            log.debug("----------------执行客户(" + customer + ")未清抬头状态更新的存储过程成功！");
//        }else{                                 // 否则执行更新全部客户未清抬头数据的存储过程 
//            log.debug("----------------执行全部客户未清抬头状态更新的存储过程……");
//            this.unclearCustomerService.updateCustomerTitle();
//            log.debug("----------------执行全部客户未清抬头状态更新的存储过程成功！");
//        }
    }

	// 定时器调用
	public void _synchronizeUnclearCustomerOthers()
	{
		this._synchronizeUnclearCustomerOthers(null);
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 同步更新外围客户未清抬头数据的ISCLEARED状态(定时器调用)
     */
    public void _syncAllCustomerOthersTitleState(){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 本次同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        log.debug("----------------同步更新客户未清抬头数据的ISCLEARED状态: syncDate:" + syncDate + ",zdate:" + "");
        MasterDataRfcTable masterDataRfcTable = this.unclearCustomerOthersService._executeRfcGetMasterData(syncDate, "", "");
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.unclearCustomerOthersService._syncAllCustomerTitleState(masterDataRfcTable);
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YCUSTOMERTITLEOTHERS");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行客户未清抬头数据的ISCLEARED状态同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步更新客户未清抬头数据的ISCLEARED状态！");
            }
        }
/*        log.debug("----------------执行全部客户未清抬头状态更新的存储过程……");
        this.unclearCustomerService.updateCustomerTitle();
        log.debug("----------------执行全部客户未清抬头状态更新的存储过程成功！");*/
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建时间：2010-12-06
     * 同步更新外围供应商未清抬头数据的ISCLEARED状态(定时器调用)
     */
    public void _syncAllSupplierOthersTitleState(){
        UserContext userContext = new UserContext();
        userContext.setClient("800");
        UserContextHolder.setCurrentContext(userContext);
        // 本次同步时间
        String syncDate = DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE);
        log.debug("----------------同步更新供应商未清抬头数据的ISCLEARED状态: syncDate:" + syncDate + ",zdate:" + "");
        MasterDataRfcTable masterDataRfcTable = this.vendorOthersService._executeRfcGetMasterData(syncDate, "", "");
        if(null != masterDataRfcTable){
            if(masterDataRfcTable.getAddDataList().size() + masterDataRfcTable.getOutputTableDataList().size() > 0){
                try{
                    this.vendorOthersService._syncAllCustomerTitleState(masterDataRfcTable);
                }catch(Exception e){
                    String errMessage = e.getMessage() + e.getCause();
                    SyncMasterLog syncMasterLog = new SyncMasterLog();
                    syncMasterLog.setIssucceed("N");
                    syncMasterLog.setSyncdate(masterDataRfcTable.getSyncDate());
                    syncMasterLog.setSynctablename("YVENDORTITLEOTHERS");
                    syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                    syncMasterLog.setErrMessage(errMessage);
                    syncMasterLogService._saveSyncMasterLog(syncMasterLog);
                    log.error("----------------执行供应商未清抬头数据的ISCLEARED状态同步错误，错误信息为:" + errMessage);
                }
            }else{
                log.debug("----------------无需同步更新供应商未清抬头数据的ISCLEARED状态！");
            }
        }
    }
}