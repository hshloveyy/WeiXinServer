/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.receipts.service;

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
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.dao.ReceiptsJdbcDao;
import com.infolion.sapss.receipts.dao.ReceiptsMaterialHibernateDao;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;

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
public class ReceiptsJdbcService extends BaseJdbcService {
	@Autowired
	private ReceiptsJdbcDao receiptsJdbcDao;
	@Autowired
	ReceiptsMaterialHibernateDao receiptsMaterialHibernateDao;

	/**
	 * 根据ID删除信用证开证信息
	 * 
	 * @param creditId
	 * @return
	 */
	public int deleteReceiptInfo(String receiptId) {
		return this.receiptsJdbcDao.delete(receiptId);
	}

	/**
	 * 取得最新的ReceiptNo单据流水号
	 * 
	 * @param map
	 * @return
	 */
	public String getReceiptNo(String code) {
		return receiptsJdbcDao.getReceiptNo(code);
	}
	
	public String getSerialNo() {
		return receiptsJdbcDao.getSerialNo();
	}

	/**
	 * 取得 查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQuerySql(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select distinct t.*,t.EXAMINE_STATE RSEXAM_STATE_D_RSEXAM_STATE"
				+ ",T1.REAL_NAME,T2.DEPT_NAME,p.EKKO_UNSEZ" + " From T_RECEIPT_INFO T"
				+ " Left Join t_receipt_material m On m.receipt_id=T.receipt_id " 
				+ " Left Join T_Sys_User T1 On T.Creator=T1.User_Id"
				+ " Left Join T_Sys_Dept T2 On T.Dept_ID=T2.Dept_ID " +
				  " left outer join t_contract_purchase_info p on T.contract_purchase_id=p.contract_purchase_id"
				+ " Where 1=1 ");
		if(ReceiptShipConstants.ins().isShouldHide()) sb.append(" and T.EXAMINE_STATE!='5'");
		
		if ("0".equals(map.get("isAvailable")))
			sb.append(" and T.Is_Available='0'");
		else sb.append(" and T.Is_Available='1'");
		// 贸易类型
		if (StringUtils.isNotBlank(map.get("tradeType")))
			sb.append(" and t.TRADE_TYPE='" + map.get("tradeType") + "'");
		if (StringUtils.isNotBlank(map.get("isReturn")))
			sb.append(" and t.isReturn='" + map.get("isReturn") + "'");
		// 进仓单号
		if (StringUtils.isNotBlank(map.get("receiptNo"))){
			sb.append(" and (t.RECEIPT_NO like '%" + map.get("receiptNo")+ "%'");
			sb.append(" or t.serialNo like '%" + map.get("receiptNo")+ "%')");
		}
		// 部门名称
		if (StringUtils.isNotBlank(map.get("dept")))
			sb.append(" and t.DEPT_ID like '%" + map.get("dept") + "%'");
		// 立项号
		if (StringUtils.isNotBlank(map.get("projectName")))
			sb.append(" and t.PROJECT_NO like '%" + map.get("projectName")
					+ "%'");
		// 合同编码
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and t.CONTRACT_NO like '%" + map.get("contractNo")
					+ "%'");
		// 合同组编码
		if (StringUtils.isNotBlank(map.get("batchNo")))
			sb.append(" and m.batch_no like '%"
					+ map.get("batchNo") + "%'");
		// SAP订单号
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo")
					+ "%'");
		// 申报日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.APPLY_TIME >= '" + map.get("sDate") + "'");
		// 申报日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.APPLY_TIME <= '" + map.get("eDate") + "'");
		
		// 结束日期
		if (StringUtils.isNotBlank(map.get("sDate1")))
			sb.append(" and t.APPROVED_TIME >= '" + map.get("sDate1") + "'");
		// 结束日期
		if (StringUtils.isNotBlank(map.get("eDate1")))
			sb.append(" and t.APPROVED_TIME <= '" + map.get("eDate1") + " 24:00:00'");
		
		
		if (StringUtils.isNotBlank(map.get("sapReturnNo")))
			sb.append(" and t.sap_return_no like '%" + map.get("sapReturnNo")
					+ "%'");
		if(StringUtils.isNotBlank(map.get("paperNo"))){
			sb.append(" and p.ekko_unsez like '%"+map.get("paperNo")+"%'");
		}
		if(StringUtils.isNotBlank(map.get("examState"))){
			sb.append(" and t.examine_state = '"+map.get("examState")+"'");
		}
		if (StringUtils.isNotBlank(map.get("wareHouse"))) {
			sb.append(" and t.wareHouse = '" + map.get("wareHouse") + "'");
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptId = map.get("deptId");
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
				deptSql = " and t.dept_id in (" + deptId + ")";
				sb.append(deptSql);
			}
			sb.append(" and t.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",");
			sb.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			sb.append(" and t.dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'");
		}
		sb.append(" Order By t.CREATOR_TIME Desc");
		return sb.toString();
	}

	/**
	 * 取得 采购订单查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getPurchaseQuerySql(Map<String, String> map) {
		String contractGroupNo = map.get("contractGroupNo");
		String contractGroupName = map.get("contractGroupName");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String deptId = map.get("deptId");
		String orderState = map.get("orderState");
		String tradeType = map.get("tradeType");
		String hasQuantity = map.get("hasQuantity");
		String ekkoUnsez = map.get("ekkoUnsez");
		String sql = "select DISTINCT c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_purchase_id,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.EKKO_UNSEZ,a.invoicing_party_name EKKO_LIFNR_NAME,a.ekko_inco1"
				+ " from v_sel_purchase_apply_mt v1, t_contract_Purchase_info a, t_contract_group b, t_project_info c"
				+ " where v1.CONTRACT_PURCHASE_ID=a.CONTRACT_PURCHASE_ID and a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";

		if (StringUtils.isNotBlank(hasQuantity))
			sql = sql + " and nvl(v1.has_quantity,0) > 0";
		else
			sql = sql + " and nvl(v1.has_quantity,0) <= 0";

		if (StringUtils.isNotBlank(contractGroupNo)) {
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName)) {
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo)) {
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName)) {
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(deptId)) {
			sql = sql + " and a.dept_id like '%" + deptId + "%'";
		}
		if (StringUtils.isNotBlank(orderState)) {
			sql = sql + " and a.order_state in (" + orderState + ")";
		}
		if (StringUtils.isNotBlank(tradeType)) {
			sql = sql + " and a.trade_type = " + tradeType;
		}
		if(StringUtils.isNotBlank(ekkoUnsez)){
			sql = sql + " and a.ekko_Unsez like '%"+ekkoUnsez+"%'";
		}

		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and a.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

		return sql;
	}

	/**
	 * 更新进仓单
	 * 
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public double updateReceiptMateriel(String receiptDetailId, String colName,
			String colValue) {
		BigDecimal has_quantity = BigDecimal.valueOf(0);

		// 判断控制输入数量
		if (colName.equals("QUANTITY")) {
			TReceiptMaterial tReceiptMaterial = receiptsMaterialHibernateDao
					.get(receiptDetailId);
			String purchaseRowsId = tReceiptMaterial.getPurchaseRowsId();
			// 销售物料
			if (StringUtils.isNotBlank(purchaseRowsId))
				has_quantity = receiptsJdbcDao.getPurchaseHasQuantity(
						purchaseRowsId, tReceiptMaterial.getReceiptId(),
						receiptDetailId);

			BigDecimal quantity = BigDecimal.valueOf(Double
					.parseDouble(colValue));
			has_quantity = has_quantity.subtract(quantity);

			System.out.println("has_quantity : " + has_quantity);

			// 如果小于0的话超过剩余数量
			// if (has_quantity.signum() == -1)
			// throw new BusinessException("申请数量超过合同物料数量: " + has_quantity);
		}
		receiptsJdbcDao.updateReceiptMateriel(receiptDetailId, colName,
				colValue);

		return has_quantity.doubleValue();
	}

	/**
	 * 查询采购订单物料
	 */
	public String getQueryPurchaseMaterielSql(Map<String, String> filter) {
		String sql = "";
		String tradeType = filter.get("tradeType");
		sql = "select t.*,1 LINES from t_contract_Purchase_materiel t where 1 = 1";
		String contractPurchaseId = filter.get("contractPurchaseId");
		if (StringUtils.isNotBlank(contractPurchaseId)) {
			sql += " and t.contract_Purchase_id = '" + contractPurchaseId + "'";
		}
		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode)) {
			sql += " and t.EKPO_MATNR like '%" + materialCode + "%'";
		}
		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName)) {
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
	public void submitWorkflow(String taskId, TReceiptInfo receiptInfo) {
//		receiptInfo.setWorkflowProcessName("进仓管理");
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(receiptInfo,
					receiptInfo.getReceiptId());
			this.receiptsJdbcDao.submitUpdateState(receiptInfo.getReceiptId());
		} else {
			WorkflowService.signalProcessInstance(receiptInfo, receiptInfo
					.getReceiptId(), null);
		}
	}

	/**
	 * 提交审批流程
	 * 
	 * @param receiptInfo
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(TReceiptInfo receiptInfo, String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";

		receiptInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("receipt_v1"));

		try {
			if (StringUtils.isNotBlank(taskId)) {
				TaskInstanceContext taskInstanceContext = WorkflowService
						.getTaskInstanceContext(taskId);
				String taskName = taskInstanceContext.getTaskName();

				if (StringUtils.isNotBlank(taskName)
						&& taskName.indexOf("仓管员") != -1 && taskName.indexOf("*") == -1) {
					if (StringUtils.isBlank(receiptInfo.getSapReturnNo())
							|| "".equals(receiptInfo.getSapReturnNo())){
						jo.put("returnMessage", "提交审批失败！SAP物料凭证号,须填写");
						return jo;
					}
					else
						this.receiptsJdbcDao.updateReceipts(receiptInfo
								.getReceiptId(), "SAP_RETURN_NO", receiptInfo
								.getSapReturnNo());
				}
			}
			submitWorkflow(taskId, receiptInfo);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"
					+ e.getMessage();
			e.printStackTrace();
		}

		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		return jo;
	}
	
	public List<TReceiptMaterial> queryReceiptMaterial(String receiptId){
		return receiptsJdbcDao.queryReceiptMaterial(receiptId);
	}
	/**
	 * 
	 * @param receiptId
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	public int updateReceipts(String receiptId, String columnName,	String columnValue) {
		return this.receiptsJdbcDao.updateReceipts(receiptId,columnName,columnValue);
	}
	
	public String queryDeptCode(String receiptId){
		return receiptsJdbcDao.queryDeptCode(receiptId);
	}
	
	public void updateWithTransition(String receiptId,String transistionName){
		if("二次结算".equals(transistionName)){
			receiptsJdbcDao.updateReceipts(receiptId, "seConfigTime", DateUtils.getCurrTime(2));
			receiptsJdbcDao.updateReceipts(receiptId, "EXAMINE_STATE", "5");
			receiptsJdbcDao.updateSeReceiptQua(receiptId,"1");
		}
		else if("确认".equals(transistionName)){
			receiptsJdbcDao.updateSeReceiptQua(receiptId,"2");
		}
		else if("退回财务".equals(transistionName)||"退回修改".equals(transistionName)){
			receiptsJdbcDao.updateReceipts(receiptId, "seConfigTime", "");
			receiptsJdbcDao.updateReceipts(receiptId, "EXAMINE_STATE", "2");
			receiptsJdbcDao.updateSeReceiptQua(receiptId,null);
		}
	}
	
	public String getStorageQuerySql(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		String endDateSql = "to_date('"+DateUtils.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE)+"','yyyy/mm/dd')";
		if (StringUtils.isNotBlank(map.get("sDate"))){
			endDateSql = "to_date('"+map.get("sDate")+"','yyyy/mm/dd')";
		}
		//列名
		sb.append("SELECT decode(GL.APPROVED_TIME,'1999-09-09 00:00:00','期初',substr(GL.APPROVED_TIME,0,10)) APPROVED_TIME," +
				  "GL.COMPANY_CODE,GL.WAREHOUSENAME,GL.DEPT_NAME,GL.TRADETYPENAME,GL.PROJECT_NO,GL.CONTRACT_NO,GL.CONTRACT_PAPER_NO,GL.SAP_ORDER_NO,GL.UNIT_NAME,GL.GROUPNAME," +
				  "GL.MATERIAL_CODE,GL.MATERIAL_NAME,GL.BATCH_NO," +
				  "decode(GL.APPROVED_TIME,'1999-09-09 00:00:00','期初',round("+endDateSql+"- to_date(substr(GL.APPROVED_TIME,0,10),'yyyy/mm/dd'))) KULIN,GL.MATERIAL_UNIT,F1.KUCU,GL.UNITPRICE,GL.EKPO_PEINH,GL.CURRENCY," +
				  "round(F1.KUCU*GL.UNITPRICE/GL.EKPO_PEINH,2) TOTAL,getcnyamount(substr(GL.APPROVED_TIME,0,10),GL.CURRENCY,F1.KUCU*GL.UNITPRICE/GL.EKPO_PEINH) CNYTOTAL,"+
				  "decode(sign(GL.YUQI),1 ,GL.YUQI,0) YUQI");
		
		sb.append(" FROM (");
		sb.append("select R.*,nvl(R.receiptTtotal,0)-nvl(S.shipTotal,0) kucu From (");
		sb.append("  select nvl(sum(a1.receiptTtotal),0) receiptTtotal,a1.material_code,a1.warehouse,a1.batch_no,a1.dept_id from (");
		//sb.append("     --进仓");
		sb.append("    select a.quantity receiptTtotal,a.material_code,b.warehouse,a.batch_no,b.dept_id from t_receipt_material a ");
		sb.append("    left outer join t_receipt_info b on a.receipt_id=b.receipt_id where b.is_available=1 ");
		sb.append("    and ((b.examine_state=3 and b.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b.examine_state=5)");
		//sb.append("     --条件");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append("and ((b.examine_state=3 and b.approved_time<='"+map.get("sDate")+" 24:00:00') or (b.examine_state=5 and b.seconfigtime<='"+map.get("sDate")+" 24:00:00'))");
		}
		//sb.append("     --条件");
		sb.append("    union all");
		sb.append("    select d.quantity receiptTtotal,d.material_code,d.warehouse,d.batch_no,d.dept_id From t_profitloss_origin d");
		//sb.append("     --条件//20150729新增库存报废");
	    sb.append("    union all");
		sb.append("    select -b.quantity receiptTtotal,b.material_code,substr(b.warehouse,0,4) warehouse,b.batch_no,a.dept_id from T_SCRAPPED a left outer join T_SCRAPPED_MATERIAL b on a.scrapped_id=b.scrapped_id where a.examine_state=3 and a.is_available=1 and a.approved_time>'"+ReceiptShipConstants.START_DATE+"'");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append(" and a.approved_time<='"+map.get("sDate")+" 24:00:00'");
		}
		sb.append("    union all");
		//sb.append("    ---销售退货");
		sb.append("    select sum(rm.quantity),rm.material_code,r.warehouse,rm.batch_no,r.dept_id ");
		sb.append("    from t_sales_return_material rm left outer join T_SALES_RETURN r on rm.return_id=r.return_id");
		sb.append("    where r.is_available=1 and r.examine_state=3 and r.approved_time>'"+ReceiptShipConstants.START_DATE+"'");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append(" and r.approved_time<='"+map.get("sDate")+" 24:00:00'");
		}
		sb.append("    group by rm.material_code,r.warehouse,rm.batch_no,r.dept_id ");
		//sb.append("    ---条件");
		sb.append("  ) a1 group by a1.material_code,a1.warehouse,a1.batch_no,a1.dept_id ");
		sb.append(") R ");
		sb.append("LEFT OUTER  join (");
		//sb.append("    ---出仓");
		sb.append("  select nvl(sum(a1.quantity),0) shipTotal,a1.material_code,b1.warehouse,a1.batch_no,b1.dept_id from t_ship_material a1 ");
		sb.append("  left outer join t_ship_info b1 on a1.ship_id=b1.ship_id where b1.is_available=1 ");
		sb.append("  and ((b1.examine_state = 3 and b1.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b1.examine_state=5)");
		//sb.append("  --条件");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append("and ((b1.examine_state=3 and b1.approved_time<='"+map.get("sDate")+" 24:00:00') or (b1.examine_state=5 and b1.seconfigtime<='"+map.get("sDate")+" 24:00:00'))");
		}
		//sb.append("  --条件");
		sb.append("  group by a1.material_code,b1.warehouse,a1.batch_no,b1.dept_id");
		sb.append(") S ON R.material_code=S.material_code AND R.warehouse=S.warehouse AND R.batch_no=S.batch_no AND R.dept_id=S.dept_id ");
		sb.append("WHERE nvl(R.receiptTtotal,0)-nvl(S.shipTotal,0)>0");
		sb.append(") F1  ");

		//sb.append("--关联");
		sb.append("inner join  ");

		sb.append("(");
		sb.append("select all1.*,row_number() over(partition by dept_id,warehouseName,material_code,batch_no order by approved_time desc) rn from (");
		sb.append("  select c.company_code,b.dept_id,c.dept_name,b.warehouse,g.title warehouseName,d.title tradeTypeName,b.trade_type,b.project_no,b.contract_no,b.contract_paper_no,");
		sb.append("  b.sap_order_no,b.unit_name,f.title groupName,e.ymat_group MATERIAL_GROUP,");
		sb.append("  a.material_name,a.material_code,a.quantity,");
		sb.append("  a.batch_no,a.material_unit,nvl(nvl(a.cost_price,a.price),0) unitprice,a.ekpo_peinh,a.currency,nvl(b.seconfigtime,b.approved_time) approved_time, '',");
		//--查询时间不为空//--查询时间为空，默认sysdate系统时间
		sb.append("  round("+endDateSql+"-to_date(b.sendgoodsdate,'yyyy/mm/dd')) YUQI");

		sb.append("  from t_receipt_material a");
		sb.append("  left outer join t_receipt_info b on a.receipt_id=b.receipt_id");
		sb.append("  left outer join t_contract_purchase_info e on b.contract_purchase_id=e.contract_purchase_id");
		sb.append("  left outer join t_sys_dept c on b.dept_id=c.dept_id");
		sb.append("  left outer join bm_business_type d on b.trade_type=d.id");
		sb.append("  left outer join bm_material_group f on e.ymat_group=f.id");
		sb.append("  left outer join bm_warehouse g on b.warehouse=g.id");
		sb.append("  where b.is_available=1 and ((b.examine_state=3 and b.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b.examine_state=5)");
		//sb.append("  --条件where ");
		sb.append("  union all");
		
		//--查询时间不为空//--查询时间为空，默认sysdate系统时间
		sb.append("  select o.*,round("+endDateSql+"-to_date(o.sendgoodsdate,'yyyy/mm/dd')) YUQI from t_profitloss_origin o ");
				
		sb.append("  union all");
		
		sb.append("  select d.company_code,r.dept_id,d.dept_name,r.warehouse,w.title warehouseName,b.title tradeTypeName,s.trade_type,s.contract_no,s.contract_no,s.vbkd_ihrez contract_paper_no,");
		sb.append("  r.sap_order_no,s.kuagv_kunnr_name,m.title,s.ymat_group,rm.material_name,rm.material_code,rm.quantity,rm.batch_no,rm.material_unit,");
		sb.append("  rm.price,rm.price_unit,rm.currency,r.approved_time,'',0");
		sb.append("  from t_sales_return_material rm left outer join T_SALES_RETURN r on rm.return_id=r.return_id");
		sb.append("  left outer join t_contract_sales_info s on r.contract_sales_id=s.contract_sales_id");
		sb.append("  left outer join t_sys_dept d on r.dept_id=d.dept_id");
		sb.append("  left outer join bm_warehouse w on r.warehouse=w.id");
		sb.append("  left outer join bm_business_type b on s.trade_type=b.id");
		sb.append("  left outer join bm_material_group m on s.ymat_group=m.id");
		sb.append("  where  r.is_available=1 and r.examine_state=3 and r.approved_time>'"+ReceiptShipConstants.START_DATE+"'");

		sb.append(") all1 ");
		sb.append(") GL ON F1.material_code=GL.material_code AND F1.warehouse=GL.warehouse AND F1.batch_no=GL.batch_no AND F1.dept_id=GL.dept_id  where rn=1");
		
		//是否二次结算
		if (StringUtils.isNotBlank(map.get("isSeConfig"))){
			if("1".equals(map.get("isSeConfig"))){
			   sb.append("and  exists (");
			}
			else sb.append("and not exists (");
			
	  		sb.append(" select '' from (");
	   		sb.append("  select distinct * from (");
	    		sb.append("   select a.material_code,b.warehouse,a.batch_no,b.dept_id from t_receipt_material a ");
	    		sb.append("   left outer join t_receipt_info b on a.receipt_id=b.receipt_id where b.is_available=1 and  b.examine_state=5");
	            //--条件
	    		if (StringUtils.isNotBlank(map.get("sDate"))){
	    		sb.append("   and b.seconfigtime<='"+map.get("sDate")+" 24:00:00'");
	    		}
	    		sb.append("   union all   ");
	     
	    		sb.append("   select a1.material_code,b1.warehouse,a1.batch_no,b1.dept_id from t_ship_material a1 ");
	    		sb.append("   left outer join t_ship_info b1 on a1.ship_id=b1.ship_id where b1.is_available=1 and b1.examine_state=5");
	            //--条件
	    		if (StringUtils.isNotBlank(map.get("sDate"))){
	    		sb.append("   and b1.seconfigtime<='"+map.get("sDate")+" 24:00:00'");
	    		}
	   		sb.append("  )");
	  		sb.append(" ) fuck where F1.material_code=fuck.material_code AND F1.warehouse=fuck.warehouse AND F1.batch_no=fuck.batch_no AND F1.dept_id=fuck.dept_id");
			sb.append(")");
		}

		if (StringUtils.isNotBlank(map.get("companyID")))
			sb.append(" and GL.COMPANY_CODE = '" + map.get("companyID")+"'");
		if (StringUtils.isNotBlank(map.get("tradeType")))
			sb.append(" and GL.TRADE_TYPE='" + map.get("tradeType") + "'");
		if (StringUtils.isNotBlank(map.get("projectNo")))
			sb.append(" and GL.project_no like '%" + map.get("projectNo") + "%'");
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and GL.contract_No like '%" + map.get("contractNo") + "%'");
		if (StringUtils.isNotBlank(map.get("contractPaperNo")))
			sb.append(" and GL.contract_Paper_No like '%" + map.get("contractPaperNo") + "%'");
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and GL.sap_order_no like '%" + map.get("sapOrderNo") + "%'");
		if (StringUtils.isNotBlank(map.get("materialGroup")))
			sb.append(" and GL.material_group = '" + map.get("materialGroup") + "'");
		if (StringUtils.isNotBlank(map.get("materialName")))
			sb.append(" and GL.material_name like '%" + map.get("materialName") + "%'");
		if (StringUtils.isNotBlank(map.get("providerName")))
			sb.append(" and GL.unit_name like '%" + map.get("providerName") + "%'");
		if (StringUtils.isNotBlank(map.get("wareHouse")))
			sb.append(" and F1.warehouse = '" + map.get("wareHouse") + "'");
		if (StringUtils.isNotBlank(map.get("batchNo")))
			sb.append(" and F1.batch_no like '%" + map.get("batchNo") + "%'");

		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptId = map.get("deptId");
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
				deptSql = " and F1.dept_id in (" + deptId + ")";
				sb.append(deptSql);
			}
			sb.append(" and F1.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",");
			sb.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			sb.append(" and F1.dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'");
		}	
		sb.append(" order by GL.APPROVED_TIME DESC,GL.company_code,GL.dept_name ");
		return sb.toString();
	}
	
	public void dealStorageToExcel(String sql ,ExcelObject excel){
		receiptsJdbcDao.dealStorageToExcel(sql ,excel);
	}
	
	public String getSeConfigQuerySql(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("select b.trade_type,ty.title tradeTypeName,d.dept_id,'进仓' mark,d.company_code, nvl(b.approved_time,b.seconfigtime) seconfigtime,d.dept_name,b.warehouse,b.warehouse_add,b.project_no,b.project_name,b.serialno,b.contract_no,b.contract_paper_no,b.sap_order_no,b.unit_name,m.id material_group,m.title mgTitle,a.material_code,a.material_name,a.MATERIAL_UNIT,a.batch_no,a.quantity,a.price,a.ekpo_peinh,");
		sb.append("a.total,a.currency,b.examine_state ");
		sb.append("from t_receipt_material a left outer join t_receipt_info b on a.receipt_id=b.receipt_id ");
		sb.append("left outer join t_contract_purchase_info p on b.contract_purchase_id=p.contract_purchase_id ");
		sb.append("left outer join bm_material_group m on p.ymat_group=m.id ");
		sb.append("left outer join t_sys_dept d on b.dept_id=d.dept_id ");
		sb.append("left outer join bm_business_type ty on ty.id=b.trade_type ");
		sb.append("where b.is_available=1 and (b.examine_state=5 or b.examine_state=3) ");
		sb.append("union all ");
		sb.append("select b1.trade_type,ty.title tradeTypeName,d.dept_id,'出仓' mark,d.company_code,nvl(b1.approved_time,b1.seconfigtime) seconfigtime,d.dept_name,b1.warehouse,b1.warehouse_add,b1.project_no,b1.project_name,b1.serialno,b1.sales_no,b1.contract_paper_no,b1.sap_order_no,b1.unit_name,m.id material_group,m.title mgTitle,a1.material_code,a1.material_name,a1.MATERIAL_UNIT,a1.batch_no,a1.quantity,a1.price,a1.ekpo_peinh,");
		sb.append("a1.total,a1.currency,b1.examine_state from t_ship_material a1 left outer join t_ship_info b1 on a1.ship_id=b1.ship_id ");
		sb.append("left outer join t_contract_sales_info s on b1.contract_sales_id=s.contract_sales_id ");
		sb.append("left outer join bm_material_group m on s.ymat_group=m.id ");
		sb.append("left outer join t_sys_dept d on b1.dept_id=d.dept_id ");
		sb.append("left outer join bm_business_type ty on ty.id=b1.trade_type ");
		sb.append("where b1.is_available=1 and (b1.examine_state=5 or b1.examine_state=3) ");
		sb.append(") F1 where 1=1");
		if (StringUtils.isNotBlank(map.get("companyID")))
			sb.append(" and F1.COMPANY_CODE = '" + map.get("companyID")+"'");
		if (StringUtils.isNotBlank(map.get("tradeType")))
			sb.append(" and F1.TRADE_TYPE='" + map.get("tradeType") + "'");
		if (StringUtils.isNotBlank(map.get("projectNo")))
			sb.append(" and F1.project_no like '%" + map.get("projectNo") + "%'");
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and F1.contract_No like '%" + map.get("contractNo") + "%'");
		if (StringUtils.isNotBlank(map.get("contractPaperNo")))
			sb.append(" and F1.contract_Paper_No like '%" + map.get("contractPaperNo") + "%'");
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and F1.sap_order_no like '%" + map.get("sapOrderNo") + "%'");
		if (StringUtils.isNotBlank(map.get("materialGroup")))
			sb.append(" and F1.material_group = '" + map.get("materialGroup") + "'");
		if (StringUtils.isNotBlank(map.get("materialName")))
			sb.append(" and F1.material_name like '%" + map.get("materialName") + "%'");
		if (StringUtils.isNotBlank(map.get("providerName")))
			sb.append(" and F1.unit_name like '%" + map.get("providerName") + "%'");
		if (StringUtils.isNotBlank(map.get("wareHouse")))
			sb.append(" and F1.warehouse = '" + map.get("wareHouse") + "'");
		if (StringUtils.isNotBlank(map.get("batchNo")))
			sb.append(" and F1.batch_no like '%" + map.get("batchNo") + "%'");
		if (StringUtils.isNotBlank(map.get("mark"))){
			sb.append(" and F1.mark = '" + map.get("mark") + "'");
		}
		if (StringUtils.isNotBlank(map.get("sDate"))){
			sb.append(" and F1.seconfigtime >= '" + map.get("sDate") + " 00:00:00'");
		}
		if (StringUtils.isNotBlank(map.get("sDate1"))){
			sb.append(" and F1.seconfigtime <= '" + map.get("sDate1") + " 24:00:00'");
		}
		if (StringUtils.isNotBlank(map.get("eState"))){
			sb.append(" and F1.examine_state = '" + map.get("eState") + "'");
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptId = map.get("deptId");
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
				deptSql = " and F1.dept_id in (" + deptId + ")";
				sb.append(deptSql);
			}
			sb.append(" and F1.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",");
			sb.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			sb.append(" and F1.dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'");
		}	
		sb.append(" order by F1.MARK, F1.seconfigtime DESC,F1.company_code,F1.dept_name ");

	    return sb.toString();
	}
	
	public void dealSeConfigToExcel(String sql ,ExcelObject excel){
		receiptsJdbcDao.dealSeConfigToExcel(sql ,excel);
	}
	
	public void updateDate(TReceiptInfo info ,String oldStr,String newStr,String userId){
		receiptsJdbcDao.updateDate(info ,oldStr,newStr,userId);
	}
	/**
	public List fuckDealQuery(){
		return receiptsJdbcDao.fuckDealQuery();
	}
	public int fuckCheck(String receiptid){
		return receiptsJdbcDao.fuckCheck(receiptid);
	}
	**/
}
