/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.sapss.payment.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.payment.dao.PaymentContractHibernateDao;
import com.infolion.sapss.payment.dao.PaymentContractJdbcDao;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
import com.infolion.sapss.payment.dao.PaymentInnerInfoHibernateDao;
import com.infolion.sapss.payment.domain.TPaymentContract;
import com.infolion.sapss.payment.domain.TPaymentInnerInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 内贸付款处理服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class PaymentInnerInfoHibernateService extends BaseHibernateService implements ProcessCallBack{
	@Autowired
	private PaymentInnerInfoHibernateDao paymentInnerInfoHibernateDao;

	public PaymentInnerInfoHibernateDao getPaymentInnerInfoHibernateDao() {
		return paymentInnerInfoHibernateDao;
	}

	public void setPaymentInnerInfoHibernateDao(
			PaymentInnerInfoHibernateDao paymentInnerInfoHibernateDao) {
		this.paymentInnerInfoHibernateDao = paymentInnerInfoHibernateDao;
	}

	@Autowired
	private ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;

	public ContractPurchaseInfoHibernateDao getContractPurchaseInfoHibernateDao() {
		return contractPurchaseInfoHibernateDao;
	}

	public void setContractPurchaseInfoHibernateDao(
			ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao) {
		this.contractPurchaseInfoHibernateDao = contractPurchaseInfoHibernateDao;
	}

	@Autowired
	private PaymentContractHibernateDao paymentContractHibernateDao;

	public PaymentContractHibernateDao getPaymentContractHibernateDao() {
		return paymentContractHibernateDao;
	}

	public void setPaymentContractHibernateDao(
			PaymentContractHibernateDao paymentContractHibernateDao) {
		this.paymentContractHibernateDao = paymentContractHibernateDao;
	}

	@Autowired
	private PaymentContractJdbcDao paymentContractJdbcDao;

	public PaymentContractJdbcDao getPaymentContractJdbcDao() {
		return paymentContractJdbcDao;
	}

	public void setPaymentContractJdbcDao(
			PaymentContractJdbcDao paymentContractJdbcDao) {
		this.paymentContractJdbcDao = paymentContractJdbcDao;
	}
	@Autowired
	private PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao;
	
	public void setPaymentImportsInfoJdbcDao(
			PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao) {
		this.paymentImportsInfoJdbcDao = paymentImportsInfoJdbcDao;
	}

	/**
	 * 保存内贸付款信息
	 * @param paymentInnerInfo
	 * @return
	 */
	public String savePaymentInnerInfo(TPaymentInnerInfo paymentInnerInfo) {
		String id = paymentInnerInfo.getPaymentId();
		//如果ID为空
		if ("".equals(id) || null == id) {
			//获取GUID
			id = CodeGenerator.getUUID();
			paymentInnerInfo.setPaymentId(id);
			this.paymentInnerInfoHibernateDao.saveOrUpdate(paymentInnerInfo);
		} else {
			this.paymentInnerInfoHibernateDao.update(paymentInnerInfo);
		}
		return id;
	}
	
	public JSONObject saveOrUpdatePaymentInnerInfo(String paymentId,
			String[] contractPurchaseIds, TPaymentInnerInfo paymentInnerInfo) {
		return saveOrUpdatePaymentInnerInfo(paymentId, null, contractPurchaseIds, paymentInnerInfo);
	}
	
	/**
	 * 保存和提交内贸付款信息
	 * @param paymentId
	 * @param taskId
	 * @param contractPurchaseIds
	 * @param paymentInnerInfo
	 * @return
	 */
	public JSONObject saveOrUpdatePaymentInnerInfo(String paymentId,String taskId,
			String[] contractPurchaseIds, TPaymentInnerInfo paymentInnerInfo) {
		JSONObject jo = new JSONObject();
		//如果ID为空则为创建
		if (paymentId == null || "".equals(paymentId)) {
			//设置审批状态为新增
			paymentInnerInfo.setExamineState("1");
			paymentId = savePaymentInnerInfo(paymentInnerInfo);
			if (paymentId == null || "".equals(paymentId)) {
				jo.put("success", false);
				jo.put("paymentId", paymentId);
			} else {
				//保存内贸付款与付款合同的关联信息
				savePaymentContract(contractPurchaseIds, paymentInnerInfo, jo);
			}
		} else {
			updatePaymentInnerInfo(paymentInnerInfo);
			//删除旧有的内贸付款与付款合同关联信息
			deleteOldPaymentContract(paymentId);
			//重新保存
			savePaymentContract(contractPurchaseIds, paymentInnerInfo, jo);
			if("CREATE_NEW_TASK".equals(taskId)||taskId!=null&&taskId.length()>0){
				submitAndSave(taskId,paymentInnerInfo);
			}
		}
		return jo;
	}

	/**
	 * 保存内贸付款与付款合同的关联信息
	 * @param contractPurchaseIds 内贸付款相关合同ID数组
	 * @param paymentInnerInfo 内贸付款信息
	 * @param jo
	 */
	private void savePaymentContract(String[] contractPurchaseIds,
			TPaymentInnerInfo paymentInnerInfo, JSONObject jo) {
		//填充内贸付款合同的关联信息实体数组
		TPaymentContract[] paymentContracts = getNewPaymentContracts(
				contractPurchaseIds, paymentInnerInfo);
		/*if (paymentContracts == null || paymentContracts.length <= 0) {
			jo.put("success", false);
			jo.put("paymentId", paymentInnerInfo.getPaymentId());
		} else {*/
		if (paymentContracts != null && paymentContracts.length > 0) {
			for (int i = 0; i < paymentContracts.length; i++) {
				//调用DAO进行保存
				paymentContractHibernateDao.save(paymentContracts[i]);
			}
		}
			jo.put("success", true);
			jo.put("paymentId", paymentInnerInfo.getPaymentId());
		//}
	}

	/**
	 * 删除旧有的内贸付款与付款合同关联信息
	 * @param paymentId
	 */
	private void deleteOldPaymentContract(String paymentId) {
		paymentContractJdbcDao.deleteByPaymentId(paymentId);
	}

	/**
	 * 删除内贸付款信息
	 * @param paymentId
	 * @return
	 */
	public boolean deletePaymentInnerInfo(String paymentId){
		TPaymentInnerInfo paymentInnerInfo = this.paymentInnerInfoHibernateDao.get(paymentId);
		String applyTime = paymentInnerInfo.getApplyTime();
		//如果提交时间不为空,用户已提交审批,该记录不能删除
		if(applyTime!=null&&applyTime.length()>0){
			return false;
		}else{
			//设置有效标志为"0"
			paymentInnerInfo.setIsAvailable("0");
			//更新,进行逻辑删除
			paymentInnerInfoHibernateDao.update(paymentInnerInfo);
			//逻辑删除内贸付款合同关联信息
			this.paymentContractJdbcDao.deleteByPaymentId(paymentId, false);
			return true;
		}
	}
	
	/*public String save(Map<String, String> parameter) {
		TPaymentInnerInfo paymentInnerInfo = this
				.getNewPaymentInnerInfo(parameter);
		return savePaymentInnerInfo(paymentInnerInfo);
	}*/
	
	/**
	 * 内贸付款信息提交处理
	 * @param paymentInnerInfo
	 */
	public void submitPaymentInnerInfo(TPaymentInnerInfo paymentInnerInfo) {
		submitPaymentInnerInfo(null,paymentInnerInfo);
	}
	
	/**
	 * 内贸付款信息提交处理
	 * @param taskId
	 * @param paymentInnerInfo
	 */
	public void submitPaymentInnerInfo(String taskId ,TPaymentInnerInfo paymentInnerInfo) {
		//得到流程中的taskId
		if(taskId == null||"".equals(taskId))
			taskId = paymentInnerInfo.getWorkflowTaskId();
		String id = paymentInnerInfo.getPaymentId();
		//如果付款类型为内贸付款
		if("1".equals(paymentInnerInfo.getPayType())){
			//如果付款方式为信用证付款
			if("1".equals(paymentInnerInfo.getPayMethod()))
				//信息证付款流程
				paymentInnerInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("home_credit_v1"));
			else
				//非信用证付款走外贸付款流程
				paymentInnerInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_pay_v1"));
		}else if("2".equals(paymentInnerInfo.getPayType())){//如果付款类型为非货款支付
				paymentInnerInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_non_goods_pay"));
		}else if("3".equals(paymentInnerInfo.getPayType())){//进口预付款
			paymentInnerInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_pay_v1"));
		}
		//设置流程变量参数MAP
		Map<String,Object> parameters = new HashMap<String,Object>();
		BigDecimal bd = new BigDecimal(paymentInnerInfo.getPayValue());
		/****/
		parameters.put("_dept_id", paymentInnerInfo.getDeptId());
		/****/
		parameters.put("payBank", paymentInnerInfo.getPayBank());
		parameters.put("payAccount", paymentInnerInfo.getPayAccount());
		parameters.put("payRealTime", paymentInnerInfo.getPayRealTime());
		parameters.put("payer", paymentInnerInfo.getPayer());
		parameters.put("openAccountNo", paymentInnerInfo.getOpenAccountNo());
		parameters.put("openAccountBank", paymentInnerInfo.getOpenAccountBank());
		parameters.put("recBank", paymentInnerInfo.getRecBank());
		parameters.put("maturityDate", paymentInnerInfo.getMaturityDate());
		parameters.put("createBank", paymentInnerInfo.getCreateBank());
		parameters.put("preSecurity", paymentInnerInfo.getPreSecurity());
		parameters.put("deposit", paymentInnerInfo.getDeposit());
		//币别
		parameters.put("_workflow_currency",paymentInnerInfo.getCurrency());
		//根据币别换算
		if(!"USD".equals(paymentInnerInfo.getCurrency()) && !"CNY".equals(paymentInnerInfo.getCurrency())){
			String cur = paymentInnerInfo.getCurrency();
			if(cur==null||"".equals(cur))
				cur = "CNY";
			String cr = this.paymentImportsInfoJdbcDao.getCurrentRate(cur);
			BigDecimal rate =new BigDecimal(cr);
			if(rate==null){
				rate = new BigDecimal(1);
			}
			bd = bd.multiply(rate);
		}
		parameters.put("_workflow_total_value",bd.doubleValue()/10000+"");
		parameters.put("is_net_pay", paymentInnerInfo.getIsNetPay());
		//parameters.put("unit_no", paymentInnerInfo.getUnitNo());
		parameters.put("unit_name", paymentInnerInfo.getRecBank());
		
		//
		//将以上变量保存为流程节点变量
		paymentInnerInfo.setWorkflowUserDefineTaskVariable(parameters);
		//如果taskId(任务ID)不存在则创建新流程
		if ("CREATE_NEW_TASK".equals(taskId) || null == taskId || "".equals(taskId)){
			parameters.put("payMethod", paymentInnerInfo.getPayMethod());
			parameters.put("paymentInnerInfo", "1");
			parameters.put("payType", paymentInnerInfo.getPayType());
			parameters.put("currency", paymentInnerInfo.getCurrency());
			//将以上变量保存为流程全局变量
			paymentInnerInfo.setWorkflowUserDefineProcessVariable(parameters);
			//创建一新流程
			WorkflowService
					.createAndSignalProcessInstance(paymentInnerInfo, id);
		}
		else //如果任务ID不为空则流程向下走一步
			WorkflowService.signalProcessInstance(paymentInnerInfo, id, null);
		// operateSuccessfully(response);
	}

	/**
	 * 提交与保存
	 * @param taskId
	 * @param paymentInnerInfo
	 */
	public void submitAndSave(String taskId,
			TPaymentInnerInfo paymentInnerInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String subNote="内贸付款";
		String sql = "select title from bm_inner_pay_method where id=?";
		if("1".equals(paymentInnerInfo.getPayType())&&paymentInnerInfo.getProjectId()!=null&&!"".equals(paymentInnerInfo.getProjectId())){
			subNote = paymentContractJdbcDao.getTradeTypeByProjectId(paymentInnerInfo.getProjectId());
		}
		if("2".equals(paymentInnerInfo.getPayType())){
			paymentInnerInfo.setWorkflowModelName("非货款申请付款");
			subNote = "非货款支付";
			sql = "select title from bm_inner_pay_method_un where id=?";
		}
		else if("3".equals(paymentInnerInfo.getPayType())){
			paymentInnerInfo.setWorkflowModelName("进口预付款申请");
			subNote = "进口预付款";
			sql = "select title from bm_inner_pay_method_import where id=?";
		}
		String payMethodTitle = this.paymentContractJdbcDao.getSimpleJdbcTemplate()
				.queryForObject(sql,String.class, paymentInnerInfo.getPayMethod());
		//设置审批状态为"审批"
		paymentInnerInfo.setExamineState("2");
		//设置审批时间
		paymentInnerInfo.setApplyTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		//设置note提示信息
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + subNote + "|"
				+ payMethodTitle+"|金额:"+paymentInnerInfo.getPayValue();// + "|付款用途：" + paymentInnerInfo.getPayUse();
		paymentInnerInfo.setWorkflowBusinessNote(workflowBusinessNote);
		if("CREATE_NEW_TASK".equals(taskId))
			taskId = null;
		paymentInnerInfo.setWorkflowTaskId(taskId);
		//保存内贸付款信息
		this.savePaymentInnerInfo(paymentInnerInfo);
		//流程提交
		this.submitPaymentInnerInfo(paymentInnerInfo);
	}
	
	/**
	 * 流程回调更新内贸付款状态
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult,String sapOrderNo)
	{
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)){
			examineState = "3";
		}
		else if (ProcessCallBack.EXAMINE_FAILE.equals(examineResult)){
			examineState = "4";
		}
		else{
			examineState = "2";
		}
		TPaymentInnerInfo paymentInnerInfo = this.getPaymentInnerInfo(businessRecordId);
		paymentInnerInfo.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		paymentInnerInfo.setExamineState(examineState);
		this.savePaymentInnerInfo(paymentInnerInfo);
	}
	
	/**
	 * 用于信用证付款资金部审批通过后流程处理
	 * @param businessRecordId
	 * @param examineResult
	 * @param maturityDate
	 */
	public void updateSuccessfulRecord(String businessRecordId,
			String examineResult, String maturityDate){
		String examineState = "3";
		TPaymentInnerInfo paymentInnerInfo = this.getPaymentInnerInfo(businessRecordId);
		paymentInnerInfo.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		paymentInnerInfo.setExamineState(examineState);
		paymentInnerInfo.setMaturityDate(maturityDate);
		this.savePaymentInnerInfo(paymentInnerInfo);
	}	
	
	/**
	 * 用于出纳审核后记录更改的付款银行及付款银行帐号
	 * @param businessRecordId
	 * @param payBank
	 * @param payAccount
	 */
	public void updateWithTellerInfo(String businessRecordId,
			String payBank, String payAccount, String payer,String payRealTime){
		TPaymentInnerInfo paymentInnerInfo = this.getPaymentInnerInfo(businessRecordId);
		paymentInnerInfo.setPayBank(payBank);
		paymentInnerInfo.setPayAccount(payAccount);
		paymentInnerInfo.setPayer(payer);
		paymentInnerInfo.setPayRealTime(payRealTime);
		this.savePaymentInnerInfo(paymentInnerInfo);
	}
	
	/**
	 * 用于资金部经理审核（填写开证行，是否需要保证金）
	 * 后记录更改的开证行，是否需要保证金信息
	 * @param businessRecordId
	 * @param payBank
	 * @param payAccount
	 */
	public void updateWithCreateBank(String businessRecordId,
			String createBank, String preSecurity,Double deposit){
		TPaymentInnerInfo paymentInnerInfo = this.getPaymentInnerInfo(businessRecordId);
		if(createBank!=null&&createBank.length()>0)
			paymentInnerInfo.setCreateBank(createBank);
		paymentInnerInfo.setPreSecurity(preSecurity);
		if(deposit!=null&&!deposit.isNaN())
			paymentInnerInfo.setDeposit(deposit);
		this.savePaymentInnerInfo(paymentInnerInfo);
	}
	
	/**
	 * 根据ID查找相关内贸付款信息
	 * @param id
	 * @return
	 */
	public TPaymentInnerInfo getPaymentInnerInfo(String id) {
		return this.paymentInnerInfoHibernateDao.get(id);
	}

	/**
	 * 获取内贸付款与付款合同关联信息并填充到TPaymentContract实体中
	 * @param contractPurchaseIds 付款合同ID数组
	 * @param paymentInnerInfo 内贸付款信息
	 * @return 内贸付款与付款合同关联信息
	 */
	public TPaymentContract[] getNewPaymentContracts(
			final String[] contractPurchaseIds,
			TPaymentInnerInfo paymentInnerInfo) {
		if(contractPurchaseIds==null) return null;
		final Map<String,String> map = getContractDate(contractPurchaseIds);
 		TContractPurchaseInfo contractPurchaseInfo = new TContractPurchaseInfo();
		Iterator<?> iContractPurchaseInfo=(new ArrayList()).iterator();
		//根据付款合同ID数组查找相应的付款合同
		final String sql = "from TContractPurchaseInfo where contractPurchaseId in (:ids)";
		List<?> contractPurchaseInfos = (List<?>) this.contractPurchaseInfoHibernateDao
				.getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(sql);
						q.setParameterList("ids", new ArrayList<String>(map.keySet()));
						return q.list();
					}
				});

		if (contractPurchaseInfos != null && contractPurchaseInfos.size() > 0)
			iContractPurchaseInfo = contractPurchaseInfos.iterator();
	
		TPaymentContract paymentContracts[] = new TPaymentContract[contractPurchaseInfos
				.size()];
		//填充TPaymentContract实体数组
		for (int i = 0; iContractPurchaseInfo.hasNext(); i++) {
			String paymentContractId = CodeGenerator.getUUID();
			TPaymentContract paymentContract = new TPaymentContract();
			contractPurchaseInfo = (TContractPurchaseInfo) iContractPurchaseInfo
					.next();
			paymentContract.setCmd(contractPurchaseInfo.getMask());//备注
			paymentContract.setContractNo(contractPurchaseInfo.getContractNo());//合同编号
			paymentContract.setContractPurchaseId(contractPurchaseInfo
					.getContractPurchaseId());//付款合同ID
			paymentContract.setContractValue(contractPurchaseInfo.getTotal());//付款额
			paymentContract.setCreator(paymentInnerInfo.getCreator());//合同创建者
			paymentContract.setCreatorTime(paymentInnerInfo.getCreatorTime());//合同创建时间
			paymentContract.setDeptId(paymentInnerInfo.getDeptId());//部门ID
			paymentContract.setIsAvailable("1");//有效标志
			paymentContract.setPaymentContractId(paymentContractId);//内贸付款合同关联ID
			paymentContract.setPaymentId(paymentInnerInfo.getPaymentId());//内贸付款ID
			paymentContract.setSapOrderNo(contractPurchaseInfo.getSapOrderNo());//SAP业务ID
			paymentContract.setPayValue(map.get(contractPurchaseInfo.getContractPurchaseId()));
			paymentContract.setPaperNo(contractPurchaseInfo.getEkkoUnsez());
			paymentContracts[i] = paymentContract;
		}
		if("2".equals(paymentInnerInfo.getPayType())){
			TPaymentContract paymentContracts1[] = getNewPaymentContract(contractPurchaseIds,paymentInnerInfo);
			if(paymentContracts1!=null){
				TPaymentContract paymentContractTotal[] = new TPaymentContract[paymentContracts1.length+paymentContracts.length];
				System.arraycopy(paymentContracts, 0, paymentContractTotal, 0, paymentContracts.length);
				System.arraycopy(paymentContracts1, 0, paymentContractTotal, paymentContracts.length, paymentContracts1.length);
				return paymentContractTotal;
			}
		}
		return paymentContracts;
	}
	
	/**
	 * 获取内贸付款与销售合同关联信息并填充到TPaymentContract实体中
	 * @param contractSalesIds 销售合同ID数组
	 * @param paymentInnerInfo 内贸付款信息
	 * @return 内贸付款与销售合同关联信息
	 */
	private TPaymentContract[] getNewPaymentContract(
			final String[] contractSalesIds,
			TPaymentInnerInfo paymentInnerInfo) {
		final Map<String,String> map = getContractDate(contractSalesIds);
		TContractSalesInfo contractSalesInfo = new TContractSalesInfo();
		Iterator<?> iContractPurchaseInfo;
		//根据付款合同ID数组查找相应的付款合同
		final String sql = "from TContractSalesInfo where contractSalesId in (:ids)";
		List<?> contractSalesInfos = (List<?>) this.contractPurchaseInfoHibernateDao
				.getHibernateTemplate().execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(sql);
						q.setParameterList("ids", map.keySet());
						return q.list();
					}
				});

		if (contractSalesInfos != null && contractSalesInfos.size() > 0)
			iContractPurchaseInfo = contractSalesInfos.iterator();
		else
			return null;
		TPaymentContract paymentContracts[] = new TPaymentContract[contractSalesInfos
				.size()];
		//填充TPaymentContract实体数组
		for (int i = 0; iContractPurchaseInfo.hasNext(); i++) {
			String paymentContractId = CodeGenerator.getUUID();
			TPaymentContract paymentContract = new TPaymentContract();
			contractSalesInfo = (TContractSalesInfo) iContractPurchaseInfo
					.next();
			paymentContract.setCmd(contractSalesInfo.getMask());//备注
			paymentContract.setContractNo(contractSalesInfo.getContractNo());//合同编号
			paymentContract.setContractPurchaseId(contractSalesInfo
					.getContractSalesId());//付款合同ID
			paymentContract.setContractValue(contractSalesInfo.getOrderTotal());//付款额
			paymentContract.setCreator(paymentInnerInfo.getCreator());//合同创建者
			paymentContract.setCreatorTime(paymentInnerInfo.getCreatorTime());//合同创建时间
			paymentContract.setDeptId(paymentInnerInfo.getDeptId());//部门ID
			paymentContract.setIsAvailable("1");//有效标志
			paymentContract.setPaymentContractId(paymentContractId);//内贸付款合同关联ID
			paymentContract.setPaymentId(paymentInnerInfo.getPaymentId());//内贸付款ID
			paymentContract.setSapOrderNo(contractSalesInfo.getSapOrderNo());//SAP业务ID
			paymentContract.setPayValue(map.get(contractSalesInfo.getContractSalesId()));
			paymentContract.setPaperNo(contractSalesInfo.getVbkdIhrez());
			paymentContracts[i] = paymentContract;
		}
		return paymentContracts;
	}
	
	
	public Map<String,String> getContractDate(String[] contractIds){
		Map<String,String> map = new HashMap<String, String>();
		
		for(int i=0;i<contractIds.length;i++){
			String[] strs = contractIds[i].split("/");
			String key = strs[0];
			String value = strs[1];
			map.put(key,value);
		}
		return map;
	}


	/**
	 * 更新内贸付款信息
	 * @param paymentInnerInfo
	 */
	public void updatePaymentInnerInfo(TPaymentInnerInfo paymentInnerInfo) {
		paymentInnerInfoHibernateDao.update(paymentInnerInfo);
	}
}
