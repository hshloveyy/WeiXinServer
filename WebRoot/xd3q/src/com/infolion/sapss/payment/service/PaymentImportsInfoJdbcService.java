/*
 * @(#)PaymentImportsInfoJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.domain.ProcessObject;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
import com.infolion.sapss.payment.dao.PickListInfoHibernateDao;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.payment.domain.TPickListInfo;
import com.infolion.sapss.payment.web.PaymentImportsInfoQueryVO;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;
@Service
public class PaymentImportsInfoJdbcService extends BaseJdbcService implements ProcessCallBack
{
	@Autowired
	private PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao;
	@Autowired
	private PickListInfoHibernateDao pickListInfoHibernateDao;
	public void setPaymentImportsInfoJdbcDao(
			PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao)
	{
		paymentImportsInfoJdbcDao = paymentImportsInfoJdbcDao;
	}
	public void setPickListInfoHibernateDao(
			PickListInfoHibernateDao pickListInfoHibernateDao) {
		this.pickListInfoHibernateDao = pickListInfoHibernateDao;
	}


	public String getPickListSql(Map<String, String> map)
	{
		UserContext text = UserContextHolder.getLocalUserContext().getUserContext();
		String pickListNo = map.get("pickListNo");
		String pickListrecDate = map.get("pickListrecDate");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String writeNo = map.get("writeNo");
		String tradeType = map.get("tradeType");
		String pickListType="";
		if("dp".equals(tradeType))
			pickListType="3";
		else if("da".equals(tradeType)){
			pickListType="4";
		}else if("tt".equals(tradeType)){
			pickListType="5";
		}else if("sight_lc".equals(tradeType)){
			pickListType="1";
		}else if("usance_lc".equals(tradeType)){
			pickListType="2";	
		}
		String examineState = map.get("examineState");
		String lcNo = map.get("lcNo");
		String totalValue = map.get("totalValue");
		String totalValue1 = map.get("totalValue1");
		String sql = "select t1.*,t2.contract_name,t2.ekko_waers,t2.ekko_Lifnr_Name,t3.write_list_no as write_list_no1,t1.totalvalue as pay_value from t_pick_list_info t1 inner join " +
				"t_contract_purchase_info t2 on t1.contract_purchase_id = t2.contract_purchase_id left outer join  t_payment_imports_info t3 on t1.pick_list_id=t3.pick_list_id where t1.examine_state='3' and t1.is_available='1' ";

		if (StringUtils.isNotBlank(pickListNo))
		{
			sql = sql + " and t1.pick_list_no like '%" + pickListNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(writeNo))
		{
			sql = sql + " and t3.write_list_no like '%" + writeNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(pickListrecDate))
		{
			sql = sql + " and t1.pick_list_rec_date = '" + pickListrecDate + "'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and t1.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and t2.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and t1.pick_list_type = '" + pickListType + "'";
		}
		if (StringUtils.isNotBlank(examineState))
		{
			sql = sql + " and t1.examine_state = " + examineState;
		}
		if (StringUtils.isNotBlank(lcNo))
		{
			sql = sql + " and t1.lc_no like '%" + lcNo +"%'";
		}
		if (StringUtils.isNotBlank(totalValue))
		{
			sql = sql + " and t1.totalValue >=" + totalValue+"";
		}
		if (StringUtils.isNotBlank(totalValue1))
		{
			sql = sql + " and t1.totalValue <=" + totalValue1+"";
		}
		if(text.getSysDept().getIsFuncDept().equals("1")){
			sql = sql + " and t1.dept_Id in ("+text.getGrantedDepartmentsId()+")";
		}else{
			sql = sql + " and t1.dept_Id = '"+ text.getSysDept().getDeptid() + "'";
		}
		return sql;
	}
	
	private int getTradeTypeByPickListId(String pickListId){
		return paymentImportsInfoJdbcDao.getTradeTypeByPickListId(pickListId);
	}
	/**
	 * 提交工作流
	 * @param taskIdR
	 * @param paymentImportsInfo
	 */
	public void submintWorkflow(String taskId,TPaymentImportsInfo paymentImportsInfo) {
		Map map = new HashMap();
		BigDecimal bd = new BigDecimal(paymentImportsInfo.getPayValue());//申请付款金额
		map.put("_workflow_currency",paymentImportsInfo.getCurrency());
		if(!"USD".equals(paymentImportsInfo.getCurrency()) && !"CNY".equals(paymentImportsInfo.getCurrency())){
			String cr = this.paymentImportsInfoJdbcDao.getCurrentRate(paymentImportsInfo.getCurrency());
			BigDecimal rate =new BigDecimal(cr);
			if(rate==null){
				rate = new BigDecimal(1);
			}
			bd = bd.multiply(rate);
		}
		map.put("_workflow_total_value",bd.doubleValue()/10000+"");
		String tradeType = paymentImportsInfo.getTradeType();
		/****/
		map.put("_dept_id", paymentImportsInfo.getDeptId());
		int businessType =getTradeTypeByPickListId(paymentImportsInfo.getPickListId());
		map.put("_business_Type", String.valueOf(businessType));
		/****/
		
		paymentImportsInfo.setWorkflowModelName(businessType(tradeType,paymentImportsInfo.getPayType()));
		if("tt".equals(tradeType)){
			map.put("_isTT", "TT");//是否TT付款
			if("2".equals(paymentImportsInfo.getPayType()))//付款类型为押汇
//				paymentImportsInfo.setWorkflowProcessName("foreign_trade_negotiating_v1");
				paymentImportsInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_negotiating_v1"));
			else{
//				paymentImportsInfo.setWorkflowProcessName("foreign_trade_pay_v1");
				paymentImportsInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_pay_v1"));
				
			}	
			
		}else{
			map.put("_current_issue",paymentImportsInfo.getTradeType());
			if("2".equals(paymentImportsInfo.getPayType())){//付款类型为押汇
				paymentImportsInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_negotiating_v1"));
				TPickListInfo pickListInfo = this.pickListInfoHibernateDao.get(paymentImportsInfo.getPickListId());
			    map.put("_has_rec_write", pickListInfo.getHasRecWrite());
			}
			else{
				TPickListInfo pickListInfo = this.pickListInfoHibernateDao.get(paymentImportsInfo.getPickListId());
				map.put("_has_rec_write", pickListInfo.getHasRecWrite());
				paymentImportsInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("foreign_trade_LC_DP_DA_pay"));
				if("押汇付款申请".equals(paymentImportsInfo.getWorkflowLeaveTransitionName())){
					if("".equals(paymentImportsInfo.getDocumentaryCurrency())||
							"".equals(paymentImportsInfo.getDocumentaryDate())||
							"".equals(paymentImportsInfo.getDocumentaryLimit())||
							"".equals(paymentImportsInfo.getPayDate())){
						throw new BusinessException("请填写押汇申请信息");
					}
				}
			}
		}
		map.put(Constants.WORKFLOW_USER_KEY_VALUE, paymentImportsInfo.getPaymentId());
		map.put("_negotiating_workflow_process_name", paymentImportsInfo.getWorkflowProcessName());
		paymentImportsInfo.setWorkflowUserDefineTaskVariable(map);
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(paymentImportsInfo, paymentImportsInfo.getPaymentId());
			this.paymentImportsInfoJdbcDao.updateState(paymentImportsInfo,"2");
		}else{
			WorkflowService.signalProcessInstance(paymentImportsInfo,paymentImportsInfo.getPaymentId(), null);
		}

	}
	/**
	 * 
	 * @param businessType
	 * @return
	 */
	private String businessType(String businessType,String padType){
		if("tt".equals(businessType)){
			if("2".equals(padType)) return "TT押汇及到期付款审批";
			return "TT付款审批";
		}else if("dp".equals(businessType)){
			if("2".equals(padType)) return "DP押汇及到期付款审批";
			return "DP付款审批";
		}else if("da".equals(businessType)){
			return "DA承兑及到期付款审批";
		}else if("sight_lc".equals(businessType)){
			if("2".equals(padType)) return "即期信用证押汇及到期付款审批";
			return "即期信用证付款审批";
		}else if("usance_lc".equals(businessType)){
			return "远期信用证承兑及到期付款审批";
		}
		return "";
	}
	/**
	 * 取得进口货款付款信息ID
	 * @param vo
	 * @return
	 */
	public String getPaymentIdList(PaymentImportsInfoQueryVO vo,String payType) {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();		
		StringBuffer sb = new StringBuffer();
		sb.append("select main.PAYMENT_ID,main.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,t1.PICK_LIST_NO," +
				"main.PAY_VALUE,main.PAY_TYPE PAY_TYPE_D_PAY_TYPE_DPDA,main.pay_time,main.pay_date,main.PAY_REAL_VALUE,main.PAY_REAL_TIME,main.APPLY_TIME,main.APPROVED_TIME," +
				"t1.CONTRACT_NO,main.WRITE_LIST_NO,t1.PICK_LIST_REC_DATE,t3.dept_name,main.creator,t1.pick_list_type PICK_LIST_TYPE_D_PICK_LTYPE,t1.issuing_date,t1.issuing_bank," +
				"t4.real_name,t1.COLLECTION_BANK,t1.lc_no credit_no,t5.title,main.currency from t_payment_imports_info main ");
		sb.append(" left outer join t_pick_list_info t1 on main.pick_list_id=t1.pick_list_id");
//		sb.append(" left outer join t_payment_imports_material t2 on main.pick_list_id=t2.pick_list_id");
		sb.append(" left outer join t_sys_dept t3 on main.dept_id=t3.dept_id");
		sb.append(" left outer join t_sys_user t4 on main.creator = t4.user_id ");
		sb.append(" left outer join bm_pick_list_type t5 on t1.pick_list_type=t5.id ");
		sb.append(" where main.is_available = '1'");
		sb.append("and main.dept_id in("+userContext.getGrantedDepartmentsId()+") ");
		if(StringUtils.isNotBlank(payType))
			sb.append(" and main.creator='"+userContext.getSysUser().getUserId()+"'");
		if(StringUtils.isNotBlank(vo.getContractNo())){
			sb.append(" and t1.contract_no like '%"+vo.getContractNo()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getTradeType())){
			sb.append(" and main.trade_type='"+vo.getTradeType().toLowerCase()+"'");
		}
		if(StringUtils.isNotBlank(vo.getPickLiskNo())){
			sb.append(" and t1.pick_list_no like'%"+vo.getPickLiskNo()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getPickListRecDate())){
			sb.append(" and t1.pick_list_rec_date='"+vo.getPickListRecDate()+"'");
		}
		if(StringUtils.isNotBlank(vo.getWriteListNo())){
			sb.append(" and (t1.write_list_no like '%"+vo.getWriteListNo()+"%' or main.write_list_no like '%"+vo.getWriteListNo()+"%')");
		}
		if(StringUtils.isNotBlank(vo.getApplyTime())){
			sb.append(" and main.apply_time='"+vo.getApplyTime()+"'");
		}
		if(StringUtils.isNotBlank(vo.getApprovedTime())){
			sb.append(" and main.approved_time>='"+vo.getApprovedTime()+"'");
		}
		if(StringUtils.isNotBlank(vo.getApprovedTime1())){
			sb.append(" and main.approved_time<='"+vo.getApprovedTime1()+"'");
		}
		if(StringUtils.isNotBlank(vo.getGoodsName())){
			sb.append(" and t1.goods like '%"+vo.getGoodsName()+"%'");
		}
		if(vo.getDict_dept()!=null && vo.getDict_dept().indexOf("请选择")==-1){
//			if(vo.getDict_dept().indexOf("请选择")!=-1){
//				sb.append(" and t3.dept_name='"+userContext.getSysDept().getDeptname()+"'");
//			}else{
				String temp = vo.getDict_dept();
				if(temp.indexOf(",")!=-1){
					String[] temps = temp.split(",");
					temp="";
					for (int i = 0; i < temps.length; i++) {
						temp=temp+"'"+temps[i]+"'";
						if(i!=temps.length-1)
							temp=temp+",";
					}
					sb.append(" and t3.dept_name in ("+temp+")");
				}else
					sb.append(" and t3.dept_name='"+vo.getDict_dept()+"'");
//			}
		}
		if(StringUtils.isNotBlank(vo.getPayRealTime())){
			sb.append(" and main.pay_real_time='"+vo.getPayRealTime()+"'");
		}
		if(StringUtils.isNotBlank(vo.getPayType())){
			sb.append(" and main.pay_type='"+vo.getPayType()+"'");
		}
		if(StringUtils.isNotBlank(vo.getPayTime())){
			sb.append(" and main.pay_time>='"+vo.getPayTime()+"'");
		}//
		if(StringUtils.isNotBlank(vo.getPayTime1())){
			sb.append(" and main.pay_time<='"+vo.getPayTime1()+"'");
		}
		if(StringUtils.isNotBlank(vo.getDpDaNo())){
			sb.append(" and t1.dp_da_no like '%"+vo.getDpDaNo()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getPickListType())){
			sb.append(" and t1.pick_list_type='"+vo.getPickListType()+"'");
		}
		if(StringUtils.isNotBlank(vo.getCollectionBank())){
			sb.append(" and t1.collection_bank like '%"+vo.getCollectionBank()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getCreditNO())){
			sb.append(" and t1.lc_no like '%"+vo.getCreditNO()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getIssuingDate())){
			sb.append(" and t1.issuing_date='"+vo.getIssuingDate()+"'");
		}
		if(StringUtils.isNotBlank(vo.getIssuingBank())){
			sb.append(" and t1.issuing_bank like '%"+vo.getIssuingBank()+"%'");
		}
		if(StringUtils.isNotBlank(vo.getEkpoTxz01())){
//			sb.append(" and t2.ekpo_txz01 like '%"+vo.getEkpoTxz01()+"%'");
		}
		return sb.toString();
	}
	/**
	 * 更新记录状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String flag) {
		TPaymentImportsInfo paymentImportsInfo = new TPaymentImportsInfo();
		paymentImportsInfo.setPaymentId(businessRecordId);
		String status="";
		if (flag==null || "false".equals(flag)) {
			status = "3";
			if (ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
				status = "4";
		}else{
			status = "5";
			if (ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
				status = "6";
			
		}
		this.paymentImportsInfoJdbcDao.updateState(paymentImportsInfo, status);
	}
	/**
	 * 更新记录状态
	 * @param bizId
	 * @param statusCode
	 */
	public void updateStatus(String bizId,String statusCode){
		TPaymentImportsInfo paymentImportsInfo = new TPaymentImportsInfo();
		paymentImportsInfo.setPaymentId(bizId);
		this.paymentImportsInfoJdbcDao.updateState(paymentImportsInfo, statusCode);
	}
	
	/**
	 * 首次提交审批
	 * @param obj
	 */
	public void firstSubmitParticularWorkflow(TParticularWorkflow obj) {
		obj.setWorkflowProcessUrl("paymentImportsInfoController.spr?action=linkSubmitParticularWorkflow");//设置待办链接的URL
		obj.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("special_approve_v1"));
		WorkflowService.createAndSignalProcessInstance(obj, obj.getParticularId());
		TPaymentImportsInfo info = new TPaymentImportsInfo();
		info.setPaymentId(obj.getOriginalBizId());
		this.paymentImportsInfoJdbcDao.updateState(info, "7");//特批审批中
	}
	/**
	 * 提交特批审批
	 * @param obj
	 */
	public void submitParticularWorkflow(TParticularWorkflow obj) {
		WorkflowService.signalProcessInstance(obj,obj.getParticularId(), null);
		
	}
	/**
	 * 
	 * @param paymentId
	 * @param string
	 * @param exchangeRate
	 * @return
	 */
	public int updateColumnData(String paymentId, String columnName,String columnValue) {
		return this.paymentImportsInfoJdbcDao.updateColumnData(paymentId,columnName,columnValue);
	}
	
	public int batchUpdate(Map<String,String> para,String paymentId){
		int i =0;
		for (Iterator<String> it = para.keySet().iterator();it.hasNext();){
			String key = it.next();
			i = this.paymentImportsInfoJdbcDao.updateColumnData(paymentId, key, para.get(key));
		}
		return i;
	}
	
	public String checkPayValue(TPaymentImportsInfo info){
		return paymentImportsInfoJdbcDao.checkPayValue(info);
	}
	public String queryParticularId(String paymentId){
		return paymentImportsInfoJdbcDao.queryParticularId(paymentId);
	}
	public void dealOutToExcel(String sql ,ExcelObject excel){
		paymentImportsInfoJdbcDao.dealOutToExcel(sql ,excel);
	}
}
