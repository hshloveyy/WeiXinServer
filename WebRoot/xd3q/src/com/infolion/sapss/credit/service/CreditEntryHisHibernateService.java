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
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.credit.dao.CreditEntryHisJdbcDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
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
public class CreditEntryHisHibernateService extends BaseHibernateService
{
	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private CreditEntryHisJdbcDao creditEntryHisJdbcDao;

	public void setCreditEntryHisJdbcDao(CreditEntryHisJdbcDao creditEntryHisJdbcDao)
	{
		this.creditEntryHisJdbcDao = creditEntryHisJdbcDao;
	}

	@Autowired
	private PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao;

	public void setPaymentImportsInfoJdbcDao(PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao)
	{
		paymentImportsInfoJdbcDao = paymentImportsInfoJdbcDao;
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
		contractId= creditInfo.getContractId();
		ymatGroup = creditInfo.getYmatGroup();
		TCreditHisInfo creditHisInfo = new TCreditHisInfo(creditId, tradeType, creditState, creditNo, projectNo, projectName, sapOrderNo, contractNo, createOrRec, customCreateDate, creditRecDate, createBank, createDate, country, request, benefit, benefitCertification, paymentType, amount, goods, specification, mark, invoice, billOfLading, billOfInsurance, billOfQuality, certificateOfOrigin, packingSlip, electricShip, dispatchElectric, otherDocuments, loadingPeriod, period, place, canBatches, transShipment, portOfShipment, portOfDestination, paymentDate, preSecurity, writeOffSingleNo, specialConditions, mattersShouldBeAmended, deptId, applyTime, approvedTime, isAvailable, creatorTime, creator, billConditions, currency, rate, creditHisId, versionNo, isExecuted, changeTime, changeExecTime, credit_Info, applyer, bailDate,contractId,ymatGroup);
		// BeanUtils.copyProperties(dest, orig);

		return creditHisInfo;
	}

	/**
	 * 根据creditHisId取得信用证修改历史数据。
	 * 
	 * @param creditHisId
	 * @return
	 */
	public TCreditHisInfo getTCreditHisInfo(String creditHisId)
	{
		return this.creditHisInfoHibernateDao.get(creditHisId);
	}

	/**
	 * 根据信用证ID取得信用证最新版本修改历史数据
	 * 
	 * @param creditId
	 * @return
	 */
	public TCreditHisInfo getTCreditHisInfoBycreditId(String creditId)
	{
		String creditHisId = this.creditEntryHisJdbcDao.getCreditHisId(creditId);
		return this.creditHisInfoHibernateDao.get(creditHisId);
	}

	/**
	 * 保存新增信用证改证历史数据
	 * 
	 * @param creditHisInfo
	 * @param versionNo
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveOrUpdateCreditHisInfo(TCreditHisInfo creditHisInfo, String versionNo, boolean isPass) throws Exception
	{
		boolean isSuccess = true;
		JSONObject jo = new JSONObject();
		String creditHisId = "";
		int vNo = 0;

		// LJX 20090415 信用证改证时候，信用证开证日期不允许修改。
		String createDate = this.creditEntryHisJdbcDao.getCreateDate(creditHisInfo.getCreditId());
		creditHisInfo.setCreateDate(createDate);

		boolean isAdd = false;
		if ("0".equals(creditHisInfo.getChangeState()) || creditHisInfo.getChangeState() == null || "4".equals(creditHisInfo.getChangeState()) || "5".equals(creditHisInfo.getChangeState()))
		{
			isAdd = true;
		}
		System.out.println("变更状态:" + creditHisInfo.getChangeState());
		try
		{
			if (isAdd)
			{
				// 新增 信用证改正。
				creditHisId = CodeGenerator.getUUID();
				// UserContext userContext =
				// UserContextHolder.getLocalUserContext().getUserContext();
				// creditHisInfo.setCreator(userContext.getSysUser().getUserId());
				creditHisInfo.setCreditHisId(creditHisId);
				creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
				creditHisInfo.setChangeTime(DateUtils.getCurrTime(2)); // 变更时间
				creditHisInfo.setChangeExecTime("");
				creditHisInfo.setChangeState("1");// 状态为，信用证改证申请。1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
				// 。注：如果状态为0则为还未有变更记录。

				if (versionNo != null)
				{
					vNo = java.lang.Integer.parseInt(versionNo);
					vNo = vNo + 1;
				}

				creditHisInfo.setVersionNo(vNo); // 原始版本为0
				// ,如果有修改则新增一笔数据版本号为1(递增)

				this.creditHisInfoHibernateDao.save(creditHisInfo);
			}
			else
			{
				// 对还未提交的 信用证改证历史数据版本号不递增，只对当前还未提交的版本进行修改处理。
				vNo = Integer.parseInt(versionNo);
				creditHisInfo.setChangeTime(DateUtils.getCurrTime(2)); // 变更时间
				creditHisInfo.setChangeExecTime("");
				creditHisInfo.setVersionNo(vNo);
				this.creditHisInfoHibernateDao.update(creditHisInfo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			isSuccess = false;
		}

		if (isSuccess)
		{
			jo.put("success", true);
			jo.put("creditHisId", creditHisId);
			jo.put("versionNo", vNo);
			jo.put("changeState", "1");
		}
		else
		{
			creditHisId = "";
			jo.put("success", false);
			jo.put("creditHisId", creditHisId);
			jo.put("versionNo", versionNo);
			jo.put("changeState", "1");
		}
		isPass = isSuccess;
		return jo;
	}

	// /**
	// * 审批时候，到各节点判断是否有必输字段还未输入，如果有则抛出异常提示
	// *
	// * @param creditInfo
	// * @param taskId
	// * @param taskName
	// * @return
	// */
	// public String verifyFilds(TCreditHisInfo creditHisInfo, String taskId,
	// String taskName)
	// {
	// String exceptionMsg = "";
	// // 取得工作流节点名称
	// if (taskId != null && taskId != "")
	// {
	// TaskInstanceContext taskInstanceContext =
	// WorkflowService.getTaskInstanceContext(taskId);
	// taskName = taskInstanceContext.getTaskName();
	// if ("资金部填写开征行".equals(taskName))
	// {
	// if (creditHisInfo.getCreateBank() == null ||
	// "".equals(creditHisInfo.getCreateBank()) == true)
	// {
	// exceptionMsg = "请资金部填写开征行！";
	// }
	// else
	// {
	// updateCreditHisInfo(creditHisInfo);
	// }
	// }
	// else if ("财务人员审核".equals(taskName))
	// {
	// if (creditHisInfo.getCreditInfo() == null ||
	// "".equals(creditHisInfo.getCreditInfo()) == true)
	// {}
	// else
	// {
	// updateCreditHisInfo(creditHisInfo);
	// }
	// }
	// else if ("综合二部填写核销单".equals(taskName))
	// {
	// if (creditHisInfo.getWriteOffSingleNo() == null ||
	// "".equals(creditHisInfo.getWriteOffSingleNo()) == true)
	// {
	// exceptionMsg = "请综合二部填写核销单！";
	// }
	// else
	// {
	// updateCreditHisInfo(creditHisInfo);
	// }
	// }
	// else if ("外贸回填信用证号和开证日期".equals(taskName))
	// {
	//
	// if ((creditHisInfo.getCreditNo() == null ||
	// "".equals(creditHisInfo.getCreditNo()) == true) ||
	// (creditHisInfo.getCreateDate() == null ||
	// "".equals(creditHisInfo.getCreateDate()) == true))
	// {
	// exceptionMsg = "请外贸回填信用证号和开证日期！";
	// }
	// else
	// {
	// updateCreditHisInfo(creditHisInfo);
	// }
	// }
	// }
	//
	// return exceptionMsg;
	// }
	
	/**
	 * 审批时候，初始化各参数，并根据汇率计算出金额,同时传流程参数，做金额判断。
	 * 
	 * @param creditInfo
	 * @param doWorkflow
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 * @return
	 */
	public void initSubmitWorkflow(TCreditHisInfo creditHisInfo, String doWorkflow, String workflowLeaveTransitionName, String workflowExamine)
	{
		creditHisInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 业务描述信息
		creditHisInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + creditHisInfo.getProjectNo() + "|" + creditHisInfo.getProjectName());

		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			creditHisInfo.setWorkflowExamine(workflowExamine);
			creditHisInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}
		// 取得币别
		String currency = "";
		currency = creditHisInfo.getCurrency();
		// 取得汇率
		Double rate;
		if (creditHisInfo.getRate() == null || "".equals(creditHisInfo.getRate()))
		{
			rate = Double.valueOf(1);
		}
		else
		{
			rate = creditHisInfo.getRate().doubleValue();
		}

		String stramount;
		if (creditHisInfo.getAmount() == null || "".equals(creditHisInfo.getAmount()))
		{
			stramount = "0";
		}
		else
		{
			stramount = creditHisInfo.getAmount();
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
		creditHisInfo.setWorkflowUserDefineTaskVariable(map);
	
	}

	/**
	 * 审批流程如果退回修改则，保存修改的内容,收证和开证都用该方法。。乱。。TNND。
	 * @param creditHisInfo
	 * @param taskName
	 */
	public void saveModifyCreditHisInfo(TCreditHisInfo creditHisInfo,String taskId)
	{
		if(StringUtils.isBlank(taskId) == false)
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			String taskName = taskInstanceContext.getTaskName();
			
			if(StringUtils.isBlank(taskName)== false && ("信用证收证修改".equals(taskName)||"信用证改证复核".equals(taskName) || taskName.indexOf("填写开证申请表及预保")!=-1||"贸管审单员审核".equals(taskName)))
			{
				//保存修改。
				//String creditHisId = creditHisInfo.getCreditHisId();
				//String createDate = this.creditEntryHisJdbcDao.getCreateDateByHisId(creditHisId); 
			
//		        creditHisInfo.setCreditNo(info.getCreditNo());          //信用证号
//		        creditHisInfo.setCreateBank(info.getCreateBank());      //开证行
//				creditHisInfo.setCreateDate(createDate);      //开证日期
				
				this.updateCreditHisInfo(creditHisInfo);
			}
		}
	}
	
	
	/**
	 * 更新信用证开证历史表数据
	 * 
	 * @param creditInfo
	 */
	public void updateCreditHisInfo(TCreditHisInfo creditHisInfo)
	{
		this.creditHisInfoHibernateDao.update(creditHisInfo);
	}
	
	
	/**
	 * 根据taskName工作流节点名称产生一个与之相对应的标识码
	 * 
	 * @param taskName
	 * @return
	 */
	public String getTaskType(String taskName,String creditHisId)
	{
		String taskType = "";
		String creator = "";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		creator = this.creditEntryHisJdbcDao.getCreator(creditHisId);
		
        if ((taskName.indexOf("填写开证申请表及预保")!=-1 &&  creator.equals(userContext.getSysUser().getUserId()))||(taskName.indexOf("贸管审单员审核")!=-1))
		{
			taskType = "5";
		}
        
        if ("信用证收证修改".equals(taskName)||"信用证改证复核".equals(taskName))
		{
			taskType = "6";
		}
        
        //Log.debug("登陆者ID:" + userContext.getSysUser().getUserId() + "    单据建立者"+ creator);
        
		return taskType;
	}


}
