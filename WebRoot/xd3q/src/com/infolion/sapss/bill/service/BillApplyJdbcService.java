/*
 * @(#)BillApplyJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.bill.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.bill.dao.BillApplyJdbcDao;
import com.infolion.sapss.bill.dao.BillApplyMaterialHibernateDao;
import com.infolion.sapss.bill.domain.TBillApplyMaterial;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;

/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class BillApplyJdbcService extends BaseJdbcService {
	@Autowired
	private BillApplyJdbcDao BillApplyJdbcDao;
	@Autowired
	BillApplyMaterialHibernateDao BillApplyMaterialHibernateDao;
	
	/**
	 * 根据ID删除信息
	 * @param BillApplyId
	 * @return
	 */
	public int deleteBillApply(String BillApplyId){
		return this.BillApplyJdbcDao.delete(BillApplyId);
	}
	
	/**
	 *  取得最新的BillApplyNo单据流水号
	 * @param map
	 * @return
	 */
	public String getBillApplyNo()
	{
		return BillApplyJdbcDao.getBillApplyNo();
	}
	
	/**
	 *  取得 查询的SQL语句
	 * @param map
	 * @return
	 */
	public String getQuerySql(Map<String, String> map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Select t.*,t.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE" +
				",T1.REAL_NAME,T2.DEPT_NAME,T3.CONTRACT_NO" +
				" From T_BILL_APPLY T" +
				" Left Join T_Sys_User T1 On T.Creator=T1.User_Id" +
				" Left Join T_Sys_Dept T2 On T.Dept_ID=T2.Dept_ID" +
				" Left Join T_Contract_Sales_Info T3 On T.CONTRACT_SALES_ID=T3.CONTRACT_SALES_ID" +
				" Where T.Is_Available='1'");
		// 贸易类型
		if (StringUtils.isNotBlank(map.get("tradeType")))
			sb.append(" and t.TRADE_TYPE='" + map.get("tradeType") + "'");	
		if (StringUtils.isNotBlank(map.get("cardCodeUnit")))
			sb.append(" and t.BILL_TO_PARTY_NAME like '%" + map.get("cardCodeUnit") + "%'");
		if (StringUtils.isNotBlank(map.get("paperNo")))
			sb.append(" and t3.VBKD_IHREZ like '%" + map.get("paperNo") + "%'");
		// 票据类型
		if (StringUtils.isNotBlank(map.get("billType")))
			sb.append(" and t.BILL_TYPE='" + map.get("billType") + "'");		
		// 开票申请单号
		if (StringUtils.isNotBlank(map.get("billApplyNo")))
			sb.append(" and t.bill_Apply_No like '%" + map.get("billApplyNo") + "%'");
		// 部门名称
		if (StringUtils.isNotBlank(map.get("dept")))
			sb.append(" and t.DEPT_ID like '%" + map.get("dept") + "%'");
		// SAP订单号
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo") + "%'");
		if (StringUtils.isNotBlank(map.get("sapReturnNo")))
			sb.append(" and t.SAP_RETURN_NO like '%" + map.get("sapReturnNo") + "%'");
		
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and t.contract_sales_no like '%" + map.get("contractNo") + "%'");
		// 申请人
		if (StringUtils.isNotBlank(map.get("creator")))
			sb.append(" and T1.REAL_NAME like '%" + map.get("creator") + "%'");
		// 申报日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.APPLY_TIME >= '" + map.get("sDate") + "'");
		//申报日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.APPLY_TIME <= '" + map.get("eDate") + "'");
		if (StringUtils.isNotBlank(map.get("taxNo")))
			sb.append(" and t.tax_No like '%" + map.get("taxNo") + "%'");
		if (StringUtils.isNotBlank(map.get("sapDocNo")))
			sb.append(" and t.sap_bill_no like '%" + map.get("sapDocNo") + "%'");
		if (StringUtils.isNotBlank(map.get("billToPartyName")))
			sb.append(" and t.bill_To_Party_Name like '%" + map.get("billToPartyName") + "%'");
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		sb.append(" and t.dept_id in ("+ userContext.getGrantedDepartmentsId() + ",");
		sb.append("'" + userContext.getSysDept().getDeptid() + "')");

		sb.append(" Order By t.CREATOR_TIME Desc");
		//System.out.println("查询SQL语句:" + sb.toString());
		return sb.toString();
	}
	
	/**
	 *  取得 销售订单查询的SQL语句
	 * @param map
	 * @return
	 */
	public String getSalesQuerySql(Map<String, String> map)
	{			
		String contractGroupNo = map.get("contractGroupNo");
		String contractGroupName = map.get("contractGroupName");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String deptId = map.get("deptId");
		String orderState = map.get("orderState");
		String tradeType = map.get("tradeType");
		String hasQuantity = map.get("hasQuantity");
		String sql = "select DISTINCT c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_Sales_id,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.EKKO_UNSEZ,a.EKKO_LIFNR_NAME,a.ekko_inco1"
				+ " from v_sel_Sales_apply_mt v1, t_contract_Sales_info a, t_contract_group b, t_project_info c"
				+ " where v1.CONTRACT_Sales_ID=a.CONTRACT_Sales_ID and a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";
				
		if (StringUtils.isNotBlank(hasQuantity))
			sql = sql + " and nvl(v1.has_quantity,0) > 0";
		else
			sql = sql + " and nvl(v1.has_quantity,0) <= 0";

		if (StringUtils.isNotBlank(contractGroupNo))
		{
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName))
		{
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(deptId))
		{
			sql = sql + " and a.dept_id like '%" + deptId + "%'";
		}
		if (StringUtils.isNotBlank(orderState))
		{
			sql = sql + " and a.order_state like '%" + orderState + "%'";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and a.trade_type = " + tradeType;
		}
		return sql;
	}

	/**
	 * 更新开票申请单
	 * 
	 * @param billApplyDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateReceiptMateriel(String exportMateId, String colName,
			String colValue)
	{
		BillApplyJdbcDao.updateBillApplyMateriel(exportMateId, colName,
				colValue);
	}
	
	/**
	 * 查询销售订单物料
	 */
	public String getQuerySalesMaterielSql(Map<String, String> filter)
	{
		String sql = "";
		String tradeType = filter.get("tradeType");
		sql = "select t.*,1 LINES from t_contract_Sales_materiel t where 1 = 1";
		String contractSalesId = filter.get("contractSalesId");
		if (StringUtils.isNotBlank(contractSalesId))
		{
			sql += " and t.contract_Sales_id = '" + contractSalesId + "'";
		}
		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode))
		{
			sql += " and t.EKPO_MATNR like '%" + materialCode + "%'";
		}
		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName))
		{
			sql += " and t.EKPO_TXZ01 like '%" + materialName + "%'";
		}
		return sql;
	}
	
	/**
	 * 提交流程
	 * 
	 * @param creditInfo
	 * @return
	 */
	public void submitWorkflow(String taskId, TBillApply tBillApply)
	{
		tBillApply.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("import_invoice_v1"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("BILL_TYPE", tBillApply.getBillType());
		tBillApply.setWorkflowUserDefineProcessVariable(map);
		tBillApply.setWorkflowUserDefineTaskVariable(map);
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tBillApply, tBillApply.getBillApplyId());
			this.BillApplyJdbcDao.submitUpdateState(tBillApply.getBillApplyId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tBillApply, tBillApply.getBillApplyId(), null);
		}
	}
	
	/**
	 * 提交审批流程
	 * @param BillApply
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(TBillApply tBillApply,String taskId)
	{
		JSONObject jo = new JSONObject();
		String msg="";
		
		tBillApply.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("import_invoice_v1"));
		
		try
		{
			if(StringUtils.isNotBlank(taskId)){
				TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
				String taskName = taskInstanceContext.getTaskName();

				if(StringUtils.isNotBlank(taskName) && taskName.indexOf("综合一部仓管员")!=-1){
//					if(BillApply.getSapReturnNo()==null || "".equals(BillApply.getSapReturnNo()))
//						throw new BusinessException("SAP物料凭证号,须填写");
//					else
//						this.BillApplyJdbcDao.updateBillApply(BillApply.getReceiptId(), "SAP_RETURN_NO", BillApply.getSapReturnNo());
				}
			}
			submitWorkflow(taskId, tBillApply);
		}
		catch (Exception e)
		{
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:" + e.getMessage();
			e.printStackTrace();
		}
		
		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);
		
		
		return jo;
	}
	/**
	 * 
	 * @param billApplyId
	 * @param sapBillNo
	 * @param taxNo
	 */
	public void saveSapInfo(String billApplyId, String sapBillNo, String taxNo) {
		this.BillApplyJdbcDao.saveSapInfo(billApplyId,sapBillNo,taxNo);
	}
	public List<TBillApplyMaterial> getBillApplyMaterial(String billAppleId){
		return this.BillApplyJdbcDao.getBillApplyMaterial(billAppleId);
	}
	
	public String getQueryBillInfoSql(Map<String, String> filter){
		String sql = "select d.dept_name,p.project_no,p.project_name,m.material_name,m.material_unit,ROUND(m.quantity,3) as quantity," +
				"ROUND(m.price,2) as price ,ROUND(m.loan_money,2) as loan_money ,ROUND(m.tax,2) as tax,ROUND(m.total_money,2)" +
				" as total_money,b.apply_time,SUBSTR(b.approved_time,0,10) as approved_time,b.cmd from t_bill_apply_material m " +
				"left outer join t_bill_apply b on m.bill_apply_id=b.bill_apply_id " +
				"left outer join t_contract_sales_info s on s.contract_sales_id = b.contract_sales_id " +
				"left outer join t_project_info p on s.project_id=p.project_id " +
				"left outer join t_sys_dept d on b.dept_id=d.dept_id " +
				"where (p.trade_type='7' or p.trade_type='10'  or p.trade_type='8') and b.is_available='1' and b.examine_state='3'";
		
		
		if (filter != null && !filter.isEmpty()) {
			
			String materialName = filter.get("materialName");
			if (StringUtils.isNotBlank(materialName)) {
				sql += " and m.material_name like '%" + materialName + "%'";
			}
			String quantity = filter.get("quantity");
			if (StringUtils.isNotBlank(quantity)) {
				sql += " and m.quantity >='" + quantity + "'";
			}
			
			String quantity1 = filter.get("quantity1");
			if (StringUtils.isNotBlank(quantity1)) {
				sql += " and m.quantity <='" + quantity1 + "'";
			}
			
			String loanMoney = filter.get("loanMoney");
			if (StringUtils.isNotBlank(loanMoney)) {
				sql += " and m.loan_Money >='" + loanMoney + "'";
			}
			
			String loanMoney1 = filter.get("loanMoney1");
			if (StringUtils.isNotBlank(loanMoney1)) {
				sql += " and m.loan_Money <='" + loanMoney1 + "'";
			}
			
			String totalMoney = filter.get("totalMoney");
			if (StringUtils.isNotBlank(totalMoney)) {
				sql += " and m.total_Money >='" + totalMoney + "'";
			}
			
			String totalMoney1 = filter.get("totalMoney1");
			if (StringUtils.isNotBlank(totalMoney1)) {
				sql += " and m.total_Money <='" + totalMoney1 + "'";
			}
			
			String applyTime = filter.get("applyTime");
			if(StringUtils.isNotBlank(applyTime)){
				sql +=" and b.apply_Time >='" + applyTime +"'";
			}
			
			String applyTime1 = filter.get("applyTime1");
			if(StringUtils.isNotBlank(applyTime1)){
				sql +=" and b.apply_Time <='" + applyTime1 +"'";
			}
			
			String approvedTime = filter.get("approvedTime");
			if(StringUtils.isNotBlank(approvedTime)){
				sql +=" and b.bill_time >='" + approvedTime +"'";
			}
			
			String approvedTime1 = filter.get("approvedTime1");
			if(StringUtils.isNotBlank(approvedTime1)){
				sql +=" and b.bill_time <='" + approvedTime1 +"'";
			}
			// 部门的过滤
			UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
			String deptId = filter.get("deptId");
			String deptSql = "";
			// 部门条件判断
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				// 处理多选
				if (StringUtils.isNotBlank(deptId)) {
					String[] dp = deptId.split(",");
					deptId = "";
					for (int i = 0; i < dp.length; i++) {
						if (i == (dp.length - 1))
							deptId = deptId + "'" + dp[i] + "'";
						else
							deptId = deptId + "'" + dp[i] + "',";
					}
					deptSql = " and b.dept_id in (" + deptId + ")";
					sql += deptSql;
				}
				sql += " and b.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",";
				sql += "'" + userContext.getSysDept().getDeptid() + "')";

			} else {
				sql += " and b.dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'";
			}
		}

		return sql+" order by d.dept_name";
}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		BillApplyJdbcDao.dealOutToExcel(sql ,excel);
	}
	
	public void dealOutToExcel1(String sql ,ExcelObject excel){
		BillApplyJdbcDao.dealOutToExcel1(sql ,excel);
	}
	
	public int checkAgentQuantity(String contractSalesId){
		return BillApplyJdbcDao.checkAgentQuantity(contractSalesId);
	}
	
	public void markPrint(String id){
		BillApplyJdbcDao.markPrint(id);
	}
}
