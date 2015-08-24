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
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.credit.dao.CreditArriveHibernateDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
import com.infolion.sapss.credit.dao.CreditRecHibernateDao;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.domain.TCreditRec;

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
public class CreditArriveHibernateService extends BaseHibernateService {
	private final Log log = LogFactory
			.getLog(CreditArriveHibernateService.class);
	@Autowired
	private CreditArriveHibernateDao creditArriveHibernateDao;
	@Autowired
	private CreditRecHibernateDao creditRecHibernateDao;
	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditArriveHibernate(
			CreditArriveHibernateDao creditArriveHibernateDao) {
		this.creditArriveHibernateDao = creditArriveHibernateDao;
	}

	public void setCreditRecHibernate(
			CreditRecHibernateDao creditRecHibernateDao) {
		this.creditRecHibernateDao = creditRecHibernateDao;
	}

	public void setCreditHisInfoHibernate(
			CreditHisInfoHibernateDao creditHisInfoHibernateDao) {
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private CreditArriveJdbcService creditArriveJdbcService;

	public void setCreditArriveJdbcService(
			CreditArriveJdbcService CreditArriveJdbcService) {
		this.creditArriveJdbcService = CreditArriveJdbcService;
	}

	/**
	 * 更新信用证主信息表
	 * 
	 * @param creditInfo
	 */
	public void updateCreditInfo(TCreditInfo creditInfo) {
		this.creditArriveHibernateDao.update(creditInfo);
	}

	/**
	 * 保存信用证开证信息
	 * 
	 * @param creditInfo
	 *            信用证表(主信息表)
	 * @return
	 */
	public String saveCreditInfo(TCreditInfo creditInfo, String contractSalesId) {
		// 信用证开证表
		TCreditRec creditRec;
		List list = new ArrayList<TCreditRec>();
		// 信用证ID
		String creditId = CodeGenerator.getUUID();
		creditInfo.setCreditId(creditId);
		creditInfo.setIsAvailable("1");

		// 信用证开证ID
		String creditRecId;
		String creator = creditInfo.getCreator();
		int n = 0;
		// 采购合同编号
		String[] contractSalesIdLst;
		contractSalesIdLst = contractSalesId.split(",");
		// 包含的采购合同个数
		n = contractSalesIdLst.length;

		// 保存信用证开证主信息表 creditInfo
		this.creditArriveHibernateDao.save(creditInfo);

		// 开始保存 信用证开证表 数据
		for (int i = 0; i <= n - 1; i++) {
			creditRec = new TCreditRec();
			creditRecId = CodeGenerator.getUUID();
			creditRec.setCreditId(creditId);
			creditRec.setContractSalesId(contractSalesIdLst[i]);
			creditRec.setCreditRecId(creditRecId);
			creditRec.setCreator(creator);
			creditRec.setCreatorTime(DateUtils.getCurrTime(2));

			// 保存信用证开证表 creditRec
			// this.creditRecHibernateDao.save(creditRec);
			list.add(creditRec);
		}
		this.creditRecHibernateDao.saveBatch(list);

		return creditId;
	}

	/**
	 * 修改信用证开证信息
	 * 
	 * @param projectInfo
	 * @return
	 */
	public void updateCreditInfo(TCreditInfo creditInfo, String contractSalesId) {
		// 信用证ID
		String creditId = creditInfo.getCreditId();
		// 信用证开证ID
		String creditRecId;
		String creator = creditInfo.getCreator();
		int n = 0;

		String[] contractSalesIdLst;
		// 是否要更新信用证开证表
		boolean isUpdate = false;

		if (contractSalesId != null && "".equals(contractSalesId) == false) {
			isUpdate = true;
		}

		contractSalesIdLst = contractSalesId.split(",");
		n = contractSalesIdLst.length;
		// 保存信用证开证主信息表 creditInfo
		this.creditArriveHibernateDao.update(creditInfo);

		if (isUpdate) {
			// 删除信用证收证表数据，重新插入数据
			updateCreditRec(creditId, creator, n, contractSalesIdLst);
		}

//		// LJX 20090429 Add 如果为审批页面修改信用证，则同步修改的内容到信阳证历史表数据
//		boolean hasHisInfo = false;
//		hasHisInfo = this.creditArriveJdbcService.getIsHasHisInfo(creditId);
//
//		if (hasHisInfo) {
//			// 开始修改最原始数据到 信用证开证历史表，版本号为0
//			TCreditHisInfo creditHisInfo = new TCreditHisInfo();
//			String CreditHisId = "";
//			;
//			BeanUtils.copyProperties(creditInfo, creditHisInfo);
//			CreditHisId = this.creditArriveJdbcService.getCreditHisId(creditId);
//
//			creditHisInfo.setCreditHisId(CreditHisId); // 信用证开证修改历史表ID
//			creditHisInfo.setVersionNo(0); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
//			creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
//			creditHisInfo.setCreditId(creditInfo.getCreditId()); // 重新设定信用证ID号。
//			creditHisInfo.setChangeTime(DateUtils.getCurrTime(2));
//			creditHisInfo.setApplyTime(""); // 申报时间
//			creditHisInfo.setApprovedTime(""); // 审批通过时间
//			creditHisInfo.setChangeState("0"); // 改证状态
//			// 1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
//			this.creditHisInfoHibernateDao.saveOrUpdate(creditHisInfo);
//		}

//		 信用证开证信息修改，则重新更新信用证开证历史信息表。
//		 updateCreditEntryHisInfo(creditHisInfo, creditId);

	}

	/**
	 * 删除信用证开证表数据，重新插入数据
	 * 
	 * @param creditRec
	 * @param creditId
	 * @param creator
	 * @param n
	 * @param contractPurchaseIdLst
	 */
	public void updateCreditRec(String creditId, String creator, int n,
			String[] contractSalesIdLst) {
		// 信用证开证表
		TCreditRec creditRec;
		String creditRecId;
		// 删除 信用证开证表数据 creditRec
		List<TCreditRec> tcreditRecList;
		tcreditRecList = this.creditRecHibernateDao
				.find("from TCreditRec where creditId='" + creditId + "'");

		Iterator it = tcreditRecList.iterator();
		while (it.hasNext()) {
			creditRec = new TCreditRec();
			creditRec = (TCreditRec) it.next();
			this.creditRecHibernateDao.remove(creditRec);
			log.debug("删除信用证开证表数据成功" + creditRec.getCreditRecId());
		}

		for (int i = 0; i <= n - 1; i++) {
			creditRec = new TCreditRec();
			creditRecId = CodeGenerator.getUUID();
			creditRec.setCreditId(creditId);
			creditRec.setContractSalesId(contractSalesIdLst[i]);
			creditRec.setCreditRecId(creditRecId);
			creditRec.setCreator(creator);
			creditRec.setCreatorTime(DateUtils.getCurrTime(2));

			// 重新保存信用证开证表 creditRec
			this.creditRecHibernateDao.save(creditRec);
		}
	}

	/**
	 * 根据ID取得信用证到证信息
	 * 
	 * @param id
	 * @return
	 */
	public TCreditInfo getTCreditInfo(String id) {
		return this.creditArriveHibernateDao.get(id);
	}

	/**
	 * 保存 信用证到证信息 t_credit_info,t_credit_rec
	 * 
	 * @param creditInfo
	 * @param creditId
	 * @param contractSalesId
	 * @return
	 */
	public JSONObject saveOrUpdateCreditInfo(TCreditInfo creditInfo,
			String creditId, String contractSalesId) {
		JSONObject jo = new JSONObject();
		if (creditId == null || "".equals(creditId)) {
			creditId = saveCreditInfo(creditInfo, contractSalesId);
			if (creditId == null || "".equals(creditId)) {
				jo.put("success", false);
				jo.put("creditid", creditId);
			} else {
				jo.put("success", true);
				jo.put("creditid", creditId);
			}
			// response.getWriter().print("{success:true,creditID:'" + creditId
			// + "'}");
		} else {
			updateCreditInfo(creditInfo, contractSalesId);
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
	public TCreditHisInfo convertCreditInfoToCreditHisInfo(
			TCreditInfo creditInfo, String creditHisId) {
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
		if (creditHisId == null || "".equals(creditHisId)) {
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
		
		TCreditHisInfo creditHisInfo = new TCreditHisInfo(creditId, tradeType,
				creditState, creditNo, projectNo, projectName, sapOrderNo,
				contractNo, createOrRec, customCreateDate, creditRecDate,
				createBank, createDate, country, request, benefit,
				benefitCertification, paymentType, amount, goods,
				specification, mark, invoice, billOfLading, billOfInsurance,
				billOfQuality, certificateOfOrigin, packingSlip, electricShip,
				dispatchElectric, otherDocuments, loadingPeriod, period, place,
				canBatches, transShipment, portOfShipment, portOfDestination,
				paymentDate, preSecurity, writeOffSingleNo, specialConditions,
				mattersShouldBeAmended, deptId, applyTime, approvedTime,
				isAvailable, creatorTime, creator, billConditions, currency,
				rate, creditHisId, versionNo, isExecuted, changeTime,
				changeExecTime, credit_Info, applyer, bailDate,contractId,ymatGroup);
		// BeanUtils.copyProperties(dest, orig);

		return creditHisInfo;
	}

	/**
	 * 审批时候，到各节点判断是否有必输字段还未输入，如果有则抛出异常提示
	 * 
	 * @param creditInfo
	 * @param info
	 * @param taskId
	 * @param taskName
	 * @param contractSalesId
	 * @return
	 */
	public String verifyFilds(TCreditInfo creditInfo, TCreditInfo info,
			String taskId, String taskName, String contractSalesId) 
	{
		String exceptionMsg = "";
		String createDate = "";
		createDate = creditInfo.getCreateDate();

		// 取得工作流节点名称
		if (taskId != null || "".equals(taskId) != false) 
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
	        taskName = taskInstanceContext.getTaskName();
			if("信用证复核".equals(taskName)||"信用证收证修改".equals(taskName))
			{
				if (creditInfo.getBenefit() == null || "".equals(creditInfo.getBenefit()))
				{
					exceptionMsg = "[受益人]为必填！";
				}
				else if(org.apache.commons.lang.StringUtils.isBlank(creditInfo.getCreditNo()))
				{
					exceptionMsg = "[信用证号]为必填！";	
				}
				else
				{
					if (org.apache.commons.lang.StringUtils.isBlank(contractSalesId))
					{
						updateCreditInfo(creditInfo);
					}
					else 
					{
						this.updateCreditInfo(creditInfo, contractSalesId);
					}
				}
			}
		
		}

		return exceptionMsg;
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
		if ("信用证复核".equals(taskName)||"信用证收证修改".equals(taskName))
		{
			taskType = "edit";
		}
		else 
		{
			taskType = "view";
		}

		return taskType;
	}
	
	/**
	 * @param taskName
	 * @return
	 */
	public TCreditInfo copyCreate(TCreditInfo oinfo){
		TCreditInfo info  = new TCreditInfo();		
		BeanUtils.copyProperties(oinfo, info, new String[]{"creditId","applyTime","approvedTime","creatorTime","creator","applyer","creditState"});		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		info.setCreditId(CodeGenerator.getUUID());
		// 审证人 默认为登陆人姓名
		info.setApplyer(userContext.getSysUser().getRealName());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setCreditState("2");
		this.creditArriveHibernateDao.save(info);
		
		//
		List<TCreditRec> tcreditRecList = this.creditRecHibernateDao
				.find("from TCreditRec where creditId='" + oinfo.getCreditId() + "'");
		Iterator it = tcreditRecList.iterator();
		while (it.hasNext()) {
			TCreditRec nRec = new TCreditRec();
			TCreditRec oRec = (TCreditRec) it.next();
			nRec.setCreditRecId(CodeGenerator.getUUID());
			nRec.setCreator(userContext.getSysUser().getUserId());
			nRec.setCreatorTime(DateUtils.getCurrTime(2));
			nRec.setCreditId(info.getCreditId());
			nRec.setContractSalesId(oRec.getContractSalesId());
			this.creditRecHibernateDao.save(nRec);
		}
		return info;
	}

}
