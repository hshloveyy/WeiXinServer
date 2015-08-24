/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.credit.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.credit.dao.CreditCreateHibernateDao;
import com.infolion.sapss.credit.dao.CreditEntryHibernateDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
import com.infolion.sapss.credit.domain.TCreditCreate;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class CreditEntryHibernateService extends BaseHibernateService
{
	private final Log log = LogFactory.getLog(CreditEntryHibernateService.class);
	@Autowired
	private CreditEntryHibernateDao creditEntryHibernateDao;
	@Autowired
	private CreditCreateHibernateDao creditCreateHibernateDao;
	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditEntryHibernate(CreditEntryHibernateDao creditEntryHibernateDao)
	{
		this.creditEntryHibernateDao = creditEntryHibernateDao;
	}

	public void setCreditCreateHibernate(CreditCreateHibernateDao creditCreateHibernateDao)
	{
		this.creditCreateHibernateDao = creditCreateHibernateDao;
	}

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao;

	public void setPaymentImportsInfoJdbcDao(PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao)
	{
		paymentImportsInfoJdbcDao = paymentImportsInfoJdbcDao;
	}

	/**
	 * 更新信用证主信息表
	 * 
	 * @param creditInfo
	 */
	public void updateCreditInfo(TCreditInfo creditInfo)
	{
		this.creditEntryHibernateDao.update(creditInfo);
	}

	/**
	 * 保存信用证开证信息
	 * 
	 * @param creditInfo
	 *            信用证表(主信息表)
	 * @return
	 */
	public String saveCreditInfo(TCreditInfo creditInfo, String contractPurchaseId)
	{
		// 信用证开证表
		TCreditCreate creditCreate;
		List list = new ArrayList<TCreditCreate>();
		// 信用证ID
		String creditId = CodeGenerator.getUUID();
		creditInfo.setCreditId(creditId);
		creditInfo.setIsAvailable("1");
		// 信用证开证ID
		String creditCreateId;
		String creator = creditInfo.getCreator();
		int n = 0;
		// 采购合同编号
		String[] contractPurchaseIdLst;

		// contractPurchaseId =creditInfo.getContractNo();
		contractPurchaseIdLst = contractPurchaseId.split(",");

		// 包含的采购合同个数
		n = contractPurchaseIdLst.length;

		// 保存信用证开证主信息表 creditInfo
		this.creditEntryHibernateDao.save(creditInfo);

		// //LJX 20090326
		// // 新增信用证开证信息时候，同事保存一份基础数据到 信用证历史数据表。
		// creditHisInfo.setVersionNo(0); //原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
		// creditHisInfo.setIsExecuted("0"); //默认为 变更还未执行
		// creditHisInfo.setCreditId(creditId); //重新设定 信用证ID号。
		// creditHisInfo.setChangeTime(DateUtils.getCurrTime(2));
		// this.creditHisInfoHibernateDao.save(creditHisInfo);

		// 开始保存 信用证开证表 数据
		for (int i = 0; i <= n - 1; i++)
		{
			creditCreate = new TCreditCreate();
			creditCreateId = CodeGenerator.getUUID();
			creditCreate.setCreditId(creditId);
			creditCreate.setContractPurchaseId(contractPurchaseIdLst[i]);
			creditCreate.setCreditCreateId(creditCreateId);
			creditCreate.setCreator(creator);
			creditCreate.setCreatorTime(DateUtils.getCurrTime(2));

			// 保存信用证开证表 creditCreate
			// this.creditCreateHibernateDao.save(creditCreate);
			list.add(creditCreate);
		}

		this.creditCreateHibernateDao.saveBatch(list);

		return creditInfo.getCreditId();
	}

	/**
	 * 修改信用证开证主信息
	 * 
	 * @param projectInfo
	 * @return
	 */
	public void updateCreditInfo(TCreditInfo creditInfo, String contractPurchaseId)
	{
		// 信用证ID
		String creditId = creditInfo.getCreditId();
		// 信用证开证ID
		String creditCreateId;
		String creator = creditInfo.getCreator();
		int n = 0;

		String[] contractPurchaseIdLst;

		// 是否要更新信用证开证表
		boolean isUpdate = false;

		if (contractPurchaseId != null && "".equals(contractPurchaseId) == false)
		{
			isUpdate = true;
		}

		contractPurchaseIdLst = contractPurchaseId.split(",");
		n = contractPurchaseIdLst.length;
		// 保存信用证开证主信息表 creditInfo
		this.creditEntryHibernateDao.update(creditInfo);

		if (isUpdate)
		{
			// 删除信用证开证表数据，重新插入数据
			this.updateCreditCreate(creditId, creator, n, contractPurchaseIdLst);
		}
		// LJX 20090331 修改为审批通过时候才插入，信用证开证历史信息表
		// 信用证开证信息修改，则重新更新信用证开证历史信息表。
		// updateCreditEntryHisInfo(creditHisInfo, creditId);
	}

	/**
	 * 删除信用证开证表数据，重新插入数据
	 * 
	 * @param creditCreate
	 * @param creditId
	 * @param creator
	 * @param n
	 * @param contractPurchaseIdLst
	 */
	public void updateCreditCreate(String creditId, String creator, int n, String[] contractPurchaseIdLst)
	{
		// 信用证开证表
		TCreditCreate creditCreate;
		String creditCreateId;
		// 删除 信用证开证表数据 creditCreate
		List<TCreditCreate> tCreditCreateList;
		tCreditCreateList = this.creditCreateHibernateDao.find("from TCreditCreate where creditId='" + creditId + "'");

		Iterator it = tCreditCreateList.iterator();
		while (it.hasNext())
		{
			creditCreate = new TCreditCreate();
			creditCreate = (TCreditCreate) it.next();
			this.creditCreateHibernateDao.remove(creditCreate);
			log.debug("删除信用证开证表数据成功:" + creditCreate.getCreditCreateId());
		}

		for (int i = 0; i <= n - 1; i++)
		{
			creditCreate = new TCreditCreate();
			creditCreateId = CodeGenerator.getUUID();
			creditCreate.setCreditId(creditId);
			creditCreate.setContractPurchaseId(contractPurchaseIdLst[i]);
			creditCreate.setCreditCreateId(creditCreateId);
			creditCreate.setCreator(creator);
			creditCreate.setCreatorTime(DateUtils.getCurrTime(2));

			// 重新保存信用证开证表 creditCreate
			this.creditCreateHibernateDao.save(creditCreate);
		}
	}

	/**
	 * 信用证开证信息修改，则重新更新信用证开证历史信息表。
	 * 
	 * @param creditHisInfo
	 * @param creditId
	 */
	public void updateCreditEntryHisInfo(TCreditHisInfo creditHisInfo, String creditId)
	{
		// 信用证开证历史表
		TCreditHisInfo tcreditHisInfo = new TCreditHisInfo();
		// LJX 20090326 信用证开证修改时候，删除信用证开证历史中原始数据 版本号为0的记录，并重新插入新的历史原始数据。
		// 删除 信用证开证表数据 creditCreate
		List<TCreditHisInfo> tCreditHisInfoList;
		tCreditHisInfoList = this.creditHisInfoHibernateDao.find("from TCreditHisInfo where creditId='" + creditId + "' and versionNo=0");

		Iterator itHisInfo = tCreditHisInfoList.iterator();
		while (itHisInfo.hasNext())
		{
			tcreditHisInfo = (TCreditHisInfo) itHisInfo.next();
			this.creditHisInfoHibernateDao.remove(tcreditHisInfo);
			log.debug("删除信用证开证历史表数据成功:" + tcreditHisInfo.getCreditHisId());
		}

		creditHisInfo.setVersionNo(0); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
		creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
		creditHisInfo.setCreditId(creditId); // 重新设定 信用证ID号。
		creditHisInfo.setChangeTime(DateUtils.getCurrTime(2)); // 变更时间
		// 重新插入信用证历史表数据。
		this.creditHisInfoHibernateDao.save(creditHisInfo);
	}

	/**
	 * 根据ID取得信用证开证信息
	 * 
	 * @param id
	 * @return
	 */
	public TCreditInfo getTCreditInfo(String id)
	{
		return this.creditEntryHibernateDao.get(id);
	}

	/**
	 * 审批时候，初始化各参数，并根据汇率计算出金额,同时传流程参数，做金额判断。
	 * 
	 * @param creditInfo
	 * @param doWorkflow
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 * @return
	 */
	public void initSubmitWorkflow(TCreditInfo creditInfo, String doWorkflow, String workflowLeaveTransitionName, String workflowExamine)
	{
		creditInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		creditInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|金额:" + creditInfo.getAmount() + "|" + creditInfo.getProjectName());

		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			creditInfo.setWorkflowExamine(workflowExamine);
			creditInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		// 取得币别
		String currency = "";
		currency = creditInfo.getCurrency();
		// 取得汇率
		Double rate;
		if (creditInfo.getRate() == null || "".equals(creditInfo.getRate()))
		{
			rate = Double.valueOf(1);
		}
		else
		{
			rate = creditInfo.getRate().doubleValue();
		}

		String stramount;
		if (creditInfo.getAmount() == null || "".equals(creditInfo.getAmount()))
		{
			stramount = "0";
		}
		else
		{
			stramount = creditInfo.getAmount();
		}

		BigDecimal amount = new BigDecimal(stramount);

		if (!"USD".equals(currency) && !"CNY".equals(currency))
		{
			String cr = this.paymentImportsInfoJdbcDao.getCurrentRate(currency);
			BigDecimal rate1 = new BigDecimal(cr); // this.paymentImportsInfoJdbcDao.getCurrentRate(currency);
			if (rate1 == null)
			{
				rate1 = new BigDecimal(1);
			}
			amount = amount.multiply(rate1);
		}

		Map map = new HashMap();
		map.put("_workflow_total_value", amount.doubleValue() / 10000 + ""); // 金额
		map.put("_workflow_currency", currency); // 币别
		creditInfo.setWorkflowUserDefineTaskVariable(map);
	}

	/**
	 * 审批时候，到各节点判断是否有必输字段还未输入，如果有则抛出异常提示
	 * 
	 * @param creditInfo
	 * @param taskId
	 * @param taskName
	 * @return
	 */
	public String verifyFilds(TCreditInfo creditInfo, TCreditInfo info, String taskId, String taskName,String contractPurchaseId)
	{
		String exceptionMsg = "";
		String createDate = "";
		createDate = creditInfo.getCreateDate();
		if (taskId != null && !"".equals(taskId))
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
			
			if ("填写开证申请表及预保".equals(taskName))
			{
				//creditInfo.setValidDate(info.getValidDate()); // 有效期限
				creditInfo.setPaymentDate(info.getPaymentDate()); // 付款日
				//creditInfo.setTransShipment(info.getTransShipment()); // 可否转船
				//creditInfo.setCanBatches(info.getCanBatches()); // 可否分批
				//creditInfo.setPreSecurity(info.getPreSecurity()); // 是否预保
				creditInfo.setCurrency(info.getCurrency()); // 汇率

			}
			

			if ("财务人员审核".equals(taskName) && "填写开证申请表及预保".equals(taskName))
			{
				creditInfo.setBailDate(info.getBailDate());// 保证金日期
			}

			if (taskName.indexOf("填写开证行")!=-1)
			{
				if (StringUtils.isEmpty(creditInfo.getCreateBank()))
				{
					exceptionMsg = "请填写开证行！";
				}
				else
				{
					updateCreditInfo(creditInfo);
				}
			}
			else if (taskName.indexOf("填写开证申请表及预保")!=-1||taskName.indexOf("贸管审单员审核")!=-1||taskName.indexOf("贸管专员审核")!=-1)
			{
			    //updateCreditInfo(creditInfo);
				if(StringUtils.isBlank(contractPurchaseId))
				{
					updateCreditInfo(creditInfo);
				}
				else
				{
					this.updateCreditInfo(creditInfo, contractPurchaseId);
				}
			}
			else if ("财务人员审核".equals(taskName))
			{
				if (creditInfo.getCreditInfo() == null || "".equals(creditInfo.getCreditInfo()))
				{
					exceptionMsg = "请填写保证金情况！";
				}
				else
				{
					updateCreditInfo(creditInfo);
				}
			}
			else if (taskName.indexOf("填写核销单")!=-1)
			{
				if (creditInfo.getWriteOffSingleNo() == null || "".equals(creditInfo.getWriteOffSingleNo()))
				{
					exceptionMsg = "请填写核销单！";
				}
				else
				{
					// info.setWriteOffSingleNo(creditInfo.getWriteOffSingleNo());
					updateCreditInfo(creditInfo);
				}
			}
			else if (taskName.indexOf("回填信用证号和开证日期")!=-1)
			{

				if (StringUtils.isEmpty(creditInfo.getCreditNo()) || StringUtils.isEmpty(creditInfo.getCreateDate()))
				{
					exceptionMsg = "请回填信用证号和开证日期！";
				}
				else
				{   //校验信用证号重复
					List<TCreditInfo> tCreditInfoList  = this.creditEntryHibernateDao.find("from TCreditInfo where creditNo='" + creditInfo.getCreditNo() + "' and isAvailable=1 and creditState not in (7,9) and createOrRec=1");
					if(!tCreditInfoList.isEmpty()){
						exceptionMsg = "信用证号重复，请更改！";
						return exceptionMsg;
					}
					creditInfo.setCreateDate(createDate);
					updateCreditInfo(creditInfo);
				}
			}
		}

		return exceptionMsg;
	}
	
	/**
	 * 通过信用证ID,重新取得
	 * @param creditId
	 * @return
	 */
	public String getContractPurchaseId(String creditId)
	{
		String contractPurchaseId = "";
		
		
		return contractPurchaseId;
	}

	/**
	 * 根据taskName工作流节点名称产生一个与之相对应的标识码
	 * 
	 * @param taskName
	 * @return
	 */
	public String getTaskType(String taskName)
	{
		String taskType = "";
		if (taskName.indexOf("填写开证行")!=-1)
		{
			taskType = "1";
		}
		else if (taskName.indexOf("填写开证申请表及预保")!=-1||taskName.indexOf("贸管审单员审核")!=-1||taskName.indexOf("贸管专员审核")!=-1)
		{
			taskType = "5";
		}
		else if (taskName.indexOf("填写核销单")!=-1)
		{
			taskType = "2";
		}
		else if (taskName.indexOf("回填信用证号和开证日期")!=-1)
		{
			taskType = "3";
		}
		else if (taskName.indexOf("财务人员审核")!=-1)
		{
			taskType = "4";
		}

		return taskType;
	}

	/**
	 * 保存 信用证开证信息 t_credit_info,t_credit_create
	 * 
	 * @param creditInfo
	 * @param creditId
	 * @param contractPurchaseId
	 * @return
	 */
	public JSONObject saveOrUpdateCreditInfo(TCreditInfo creditInfo, String creditId, String contractPurchaseId)
	{
		JSONObject jo = new JSONObject();
		if (creditId == null || "".equals(creditId))
		{
			creditId = saveCreditInfo(creditInfo, contractPurchaseId);
			if (creditId == null || "".equals(creditId))
			{
				jo.put("success", false);
				jo.put("creditid", creditId);
			}
			else
			{
				jo.put("success", true);
				jo.put("creditid", creditId);
			}
			// response.getWriter().print("{success:true,creditID:'" + creditId
			// + "'}");
		}
		else
		{
			updateCreditInfo(creditInfo, contractPurchaseId);
			jo.put("success", true);
			jo.put("creditid", creditInfo.getCreditId());
		}

		return jo;
	}

	/**
	 * 转化TCreditInfo实体为TCreditHisInfo
	 * 
	 * @param creditInfo
	 * @return
	 */
	public TCreditHisInfo convertCreditInfoToCreditHisInfo(TCreditInfo creditInfo, String creditHisId)
	{
		// String creditHisId;
		Integer versionNo = 0;
		String isExecuted = "";
		String changeTime = "";
		String changeExecTime = "";

		String creditId;
		String tradeType;
		String creditState;
		String creditNo;
		String projectNo;
		String projectName;
		String sapOrderNo;
		String contractNo;
		String createOrRec;
		String customCreateDate;
		String creditRecDate;
		String createBank;
		String createDate;
		String country;
		String request;
		String benefit;
		String benefitCertification;
		String paymentType;
		String amount;
		String goods;
		String specification;
		String mark;
		String invoice;
		String billOfLading;
		String billOfInsurance;
		String billOfQuality;
		String certificateOfOrigin;
		String packingSlip;
		String electricShip;
		String dispatchElectric;
		String otherDocuments;
		String loadingPeriod;
		String period;
		String place;
		String canBatches;
		String transShipment;
		String portOfShipment;
		String portOfDestination;
		String paymentDate;
		String preSecurity;
		String writeOffSingleNo;
		String specialConditions;
		String mattersShouldBeAmended;
		String deptId;
		String applyTime;
		String approvedTime;
		String isAvailable;
		String creatorTime;
		String creator;
		String billConditions;
		String currency;
		BigDecimal rate;
		String validDate;
		String credit_Info;
		String applyer;
		String bailDate;
		String contractId;
		String ymatGroup;
		
		// 信用证历史ID
		if (creditHisId == null || "".equals(creditHisId))
		{
			creditHisId = CodeGenerator.getUUID();
		}

		creditId = creditInfo.getCreditId();
		tradeType = creditInfo.getTradeType();
		creditState = creditInfo.getCreditState();
		creditNo = creditInfo.getCreditNo();
		projectNo = creditInfo.getProjectNo();
		projectName = creditInfo.getProjectName();
		sapOrderNo = creditInfo.getSapOrderNo();
		contractNo = creditInfo.getContractNo();
		createOrRec = creditInfo.getCreateOrRec();
		customCreateDate = creditInfo.getCustomCreateDate();
		creditRecDate = creditInfo.getCreditRecDate();
		createBank = creditInfo.getCreateBank();
		createDate = creditInfo.getCreateDate();
		country = creditInfo.getCountry();
		request = creditInfo.getRequest();
		benefit = creditInfo.getBenefit();
		benefitCertification = creditInfo.getBenefitCertification();
		paymentType = creditInfo.getPaymentType();
		amount = creditInfo.getAmount();
		goods = creditInfo.getGoods();
		specification = creditInfo.getSpecification();
		mark = creditInfo.getMark();
		invoice = creditInfo.getInvoice();
		billOfLading = creditInfo.getBillOfLading();
		billOfInsurance = creditInfo.getBillOfInsurance();
		billOfQuality = creditInfo.getBillOfQuality();
		certificateOfOrigin = creditInfo.getCertificateOfOrigin();
		packingSlip = creditInfo.getPackingSlip();
		electricShip = creditInfo.getElectricShip();
		dispatchElectric = creditInfo.getDispatchElectric();
		otherDocuments = creditInfo.getOtherDocuments();
		loadingPeriod = creditInfo.getLoadingPeriod();
		period = creditInfo.getPeriod();
		place = creditInfo.getPlace();
		canBatches = creditInfo.getCanBatches();
		transShipment = creditInfo.getTransShipment();
		portOfShipment = creditInfo.getPortOfShipment();
		portOfDestination = creditInfo.getPortOfDestination();
		paymentDate = creditInfo.getPaymentDate();
		preSecurity = creditInfo.getPreSecurity();
		writeOffSingleNo = creditInfo.getWriteOffSingleNo();
		specialConditions = creditInfo.getSpecialConditions();
		mattersShouldBeAmended = creditInfo.getMattersShouldBeAmended();
		deptId = creditInfo.getDeptId();
		applyTime = creditInfo.getApplyTime();
		approvedTime = creditInfo.getApprovedTime();
		isAvailable = creditInfo.getIsAvailable();
		creatorTime = creditInfo.getCreatorTime();
		creator = creditInfo.getCreator();
		billConditions = creditInfo.getBillConditions();
		currency = creditInfo.getCurrency();
		rate = creditInfo.getRate();
		validDate = creditInfo.getValidDate();
		credit_Info = creditInfo.getCreditInfo();
		applyer = creditInfo.getApplyer();
		bailDate = creditInfo.getBailDate();
		contractId = creditInfo.getContractId();
		ymatGroup = creditInfo.getYmatGroup();
		TCreditHisInfo creditHisInfo = new TCreditHisInfo(creditId, tradeType, creditState, creditNo, projectNo, projectName, sapOrderNo, contractNo, createOrRec, customCreateDate, creditRecDate, createBank, createDate, country, request, benefit, benefitCertification, paymentType, amount, goods, specification, mark, invoice, billOfLading, billOfInsurance, billOfQuality, certificateOfOrigin, packingSlip, electricShip, dispatchElectric, otherDocuments, loadingPeriod, period, place, canBatches, transShipment, portOfShipment, portOfDestination, paymentDate, preSecurity, writeOffSingleNo, specialConditions, mattersShouldBeAmended, deptId, applyTime, approvedTime, isAvailable, creatorTime, creator, billConditions, currency, rate, creditHisId, versionNo, isExecuted, changeTime, changeExecTime, credit_Info, applyer, bailDate,contractId,ymatGroup);

		return creditHisInfo;
	}
	
	public TCreditInfo getCreditInfo(String creditNo){
		List list = this.creditEntryHibernateDao.find("from TCreditInfo a where a.creditNo = '"+creditNo+"' and a.createOrRec=1");
		if(!list.isEmpty()) return (TCreditInfo)list.get(0);
		return null;
		
	}
	
	public void saveOrUpdateCreditInfo(TCreditInfo info){
		creditEntryHibernateDao.saveOrUpdate(info);
	}
}
