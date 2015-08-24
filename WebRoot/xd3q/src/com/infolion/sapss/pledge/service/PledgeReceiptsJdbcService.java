/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.pledge.service;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.pledge.dao.PledgeReceiptsJdbcDao;
import com.infolion.sapss.pledge.domain.PledgeReceiptsInfo;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
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
public class PledgeReceiptsJdbcService extends BaseJdbcService {
	@Autowired
	private PledgeReceiptsJdbcDao pledgeReceiptsJdbcDao;
	public void setPledgeReceiptsJdbcDao(PledgeReceiptsJdbcDao pledgeReceiptsJdbcDao) {
		this.pledgeReceiptsJdbcDao = pledgeReceiptsJdbcDao;
	}
	public String getPledgeReceiptsInfoNo(String pledgeReceiptsInfoId) {
		String code = this.pledgeReceiptsJdbcDao.queryDeptCode(pledgeReceiptsInfoId);
		return pledgeReceiptsJdbcDao.getReceiptNo(code.substring(0, 2));
	}

	/**
	 * 根据ID删除进仓单信息
	 * 
	 * @param creditId
	 * @return
	 */
	public int deletePledgeReceiptsInfo(String pledgeReceiptsInfoId) {
		return this.pledgeReceiptsJdbcDao.delete(pledgeReceiptsInfoId);
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
				+ ",T1.REAL_NAME,T2.DEPT_NAME" + " From T_PLEDGERECEIPTS_INFO T"
				+ " Left Join T_Sys_User T1 On T.Creator=T1.User_Id"
				+ " Left Join T_Sys_Dept T2 On T.Dept_ID=T2.Dept_ID"
				+ " left outer join T_PLEDGERECEIPTS_ITEM p on T.PLEDGERECEIPTS_INFO_ID=p.PLEDGERECEIPTS_ID"
				+ " Where 1=1 ");
		if ("0".equals(map.get("isAvailable")))
			sb.append(" and T.Is_Available='0'");
		else sb.append(" and T.Is_Available='1'");
		// 进仓单号
		if (StringUtils.isNotBlank(map.get("pledgeReceiptsInfoNo"))){
			sb.append(" and t.PLEDGERECEIPTS_INFO_NO like '%" + map.get("pledgeReceiptsInfoNo")+ "%'");
		}
		// 部门名称
		if (StringUtils.isNotBlank(map.get("dept")))
			sb.append(" and t.DEPT_ID like '%" + map.get("dept") + "%'");
		// 立项号
		if (StringUtils.isNotBlank(map.get("projectName")))
			sb.append(" and t.PROJECT_NO like '%" + map.get("projectName")
					+ "%'");
		// 物料号
		if (StringUtils.isNotBlank(map.get("materialCode")))
			sb.append(" and p.MATERIAL_CODE like '%" + map.get("materialCode")
					+ "%'");
		// 物料名称
		if (StringUtils.isNotBlank(map.get("materialName")))
			sb.append(" and p.MATERIAL_NAME like '%" + map.get("materialName")
					+ "%'");
		// 销货单位
		if (StringUtils.isNotBlank(map.get("unitName")))
			sb.append(" and t.UNIT_NAME like '%" + map.get("unitName")
					+ "%'");
		// 批次号
		if (StringUtils.isNotBlank(map.get("batchNo")))
			sb.append(" and p.BATCH_NO like '%" + map.get("batchNo")
					+ "%'");
		// 申报日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.APPLY_TIME >= '" + map.get("sDate") + "'");
		// 申报日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.APPLY_TIME <= '" + map.get("eDate") + "'");
		//审批状态
		if(StringUtils.isNotBlank(map.get("examState"))){
			sb.append(" and t.examine_state = '"+map.get("examState")+"'");
		}
		// 审批结束日期
		if (StringUtils.isNotBlank(map.get("startDate")))
			sb.append(" and t.APPROVED_TIME >= '" + map.get("startDate") + "'");
		// 审批结束日期
		if (StringUtils.isNotBlank(map.get("endDate")))
			sb.append(" and t.APPROVED_TIME <= '" + map.get("endDate") + "'");
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
	 * 更新进仓单
	 * 
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateReceiptMateriel(String pledgeReceiptsItemId, String colName,
			String colValue) {
		pledgeReceiptsJdbcDao.updateReceiptMateriel(pledgeReceiptsItemId, colName,
				colValue);

	}
	/**
	 * 提交审批流程
	 * 
	 * @param receiptInfo
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(PledgeReceiptsInfo pledgeReceiptsInfo, String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";

		pledgeReceiptsInfo.setWorkflowProcessName("pledge_receipt");

		try {
			if (StringUtils.isNotBlank(taskId)) {
				TaskInstanceContext taskInstanceContext = WorkflowService
						.getTaskInstanceContext(taskId);
				String taskName = taskInstanceContext.getTaskName();

				
			}
			submitWorkflow(taskId, pledgeReceiptsInfo);
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
	
	/**
	 * 提交流程
	 * 
	 * @param creditInfo
	 * @return
	 */
	public void submitWorkflow(String taskId, PledgeReceiptsInfo pledgeReceiptsInfo) {
//		receiptInfo.setWorkflowProcessName("进仓管理");
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(pledgeReceiptsInfo,
					pledgeReceiptsInfo.getPledgeReceiptsInfoId());
			this.pledgeReceiptsJdbcDao.submitUpdateState(pledgeReceiptsInfo.getPledgeReceiptsInfoId());
		} else {
			WorkflowService.signalProcessInstance(pledgeReceiptsInfo, pledgeReceiptsInfo.getPledgeReceiptsInfoId(), null);
		}
	}
	public List<PledgeReceiptsItem> queryReceiptMaterial(String receiptId){
		return pledgeReceiptsJdbcDao.queryReceiptMaterial(receiptId);
	}
	public void update(String receiptsInfoNo, String pledgeReceiptsInfoId) {
		this.pledgeReceiptsJdbcDao.update(receiptsInfoNo, pledgeReceiptsInfoId);
		
	}
	public String getStorageQuerySql(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		String endDateSql = "sysdate";
		if (StringUtils.isNotBlank(map.get("sDate"))){
			endDateSql = "to_date('"+map.get("sDate")+"','yyyy/mm/dd')";
		}
		//列名
		sb.append("SELECT substr(GL.APPROVED_TIME,0,10) APPROVED_TIME," +
				  "GL.COMPANY_CODE,GL.WAREHOUSENAME,GL.DEPT_NAME,GL.PROJECT_NO," +
				  "GL.MATERIAL_CODE,GL.MATERIAL_NAME,GL.BATCH_NO," +
				  "round("+endDateSql+"- to_date(GL.APPROVED_TIME,'yyyy/mm/dd hh24-mi-ss')) KULIN,GL.MATERIAL_UNIT,F1.KUCU,GL.UNITPRICE,GL.EKPO_PEINH,GL.CURRENCY," +
				  "round(F1.KUCU*GL.UNITPRICE/GL.EKPO_PEINH,2) TOTAL,getcnyamount(substr(GL.APPROVED_TIME,0,10),GL.CURRENCY,F1.KUCU*GL.UNITPRICE/GL.EKPO_PEINH) CNYTOTAL,"+
				  "decode(sign(GL.YUQI),1 ,GL.YUQI,0) YUQI");
		
		sb.append(" FROM (");
		sb.append("select R.*,nvl(R.receiptTtotal,0)-nvl(S.shipTotal,0) kucu From (");
		sb.append("  select nvl(sum(a1.receiptTtotal),0) receiptTtotal,a1.material_code,a1.warehouse,a1.batch_no,a1.dept_id from (");
		//sb.append("     --进仓");
		sb.append("    select a.quantity receiptTtotal,a.material_code,b.warehouse,a.batch_no,b.dept_id from t_pledgereceipts_item a ");
		sb.append("    left outer join t_pledgereceipts_info b on a.pledgereceipts_id=b.pledgereceipts_info_id where b.is_available=1 ");
		sb.append("    and b.examine_state=3");
		//sb.append("     --条件");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append("and b.approved_time<='"+map.get("sDate")+" 24:00:00'");
		}
		//sb.append("     --条件");
		sb.append("  ) a1 group by a1.material_code,a1.warehouse,a1.batch_no,a1.dept_id ");
		sb.append(") R ");
		sb.append("LEFT OUTER  join (");
		//sb.append("    ---出仓");
		sb.append("  select nvl(sum(a1.quantity),0) shipTotal,a1.material_code,b1.warehouse,a1.batch_no,b1.dept_id from t_pledgeships_item a1 ");
		sb.append("  left outer join t_pledgeships_info b1 on a1.pledgeships_id=b1.pledgeships_info_id where b1.is_available=1 ");
		sb.append("  and b1.examine_state = 3");
		//sb.append("  --条件");
		if (StringUtils.isNotBlank(map.get("sDate"))){
		    sb.append("and b1.approved_time<='"+map.get("sDate")+" 24:00:00'");
		}
		//sb.append("  --条件");
		sb.append("  group by a1.material_code,b1.warehouse,a1.batch_no,b1.dept_id");
		sb.append(") S ON R.material_code=S.material_code AND R.warehouse=S.warehouse AND R.batch_no=S.batch_no AND R.dept_id=S.dept_id ");
		sb.append("WHERE nvl(R.receiptTtotal,0)-nvl(S.shipTotal,0)>0");
		sb.append(") F1  ");

		//sb.append("--关联");
		sb.append("inner join  ");

		sb.append("(");
		sb.append("select all1.*,row_number() over(partition by dept_id,warehouseName,material_code,batch_no order by approved_time) rn from (");
		sb.append("  select c.company_code,b.dept_id,c.dept_name,b.warehouse,g.title warehouseName,b.project_no,");
		sb.append("  a.material_name,a.material_code,a.quantity,");
		sb.append("  a.batch_no,a.material_unit,nvl(a.price,0) unitprice,a.ekpo_peinh,a.currency,b.approved_time, ");
		//--查询时间不为空//--查询时间为空，默认sysdate系统时间
		if (StringUtils.isNotBlank(map.get("sDate"))){
			sb.append("  round(to_date('"+map.get("sDate")+"','yyyy/mm/dd')-to_date(b.approved_time,'yyyy/mm/dd hh24-mi-ss'))-1 YUQI");
		}
		else sb.append("  round(sysdate-to_date(b.approved_time,'yyyy/mm/dd hh24-mi-ss'))-1 YUQI");

		sb.append("  from t_pledgereceipts_item a");
		sb.append("  left outer join t_pledgereceipts_info b on a.pledgereceipts_id=b.pledgereceipts_info_id");		
		sb.append("  left outer join t_sys_dept c on b.dept_id=c.dept_id");			
		sb.append("  left outer join bm_warehouse g on b.warehouse=g.id");
		sb.append("  where b.is_available=1 and b.examine_state=3");

		sb.append(") all1 ");
		sb.append(") GL ON F1.material_code=GL.material_code AND F1.warehouse=GL.warehouse AND F1.batch_no=GL.batch_no AND F1.dept_id=GL.dept_id  where rn=1");

		if (StringUtils.isNotBlank(map.get("companyID")))
			sb.append(" and GL.COMPANY_CODE = '" + map.get("companyID")+"'");
		if (StringUtils.isNotBlank(map.get("projectNo")))
			sb.append(" and GL.project_no like '%" + map.get("projectNo") + "%'");
		if (StringUtils.isNotBlank(map.get("materialName")))
			sb.append(" and GL.material_name like '%" + map.get("materialName") + "%'");
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
		//System.out.println(sb);
		return sb.toString();
	}
	public void dealStorageToExcel(String sql, ExcelObject excel) {
		pledgeReceiptsJdbcDao.dealStorageToExcel(sql ,excel);
		
	}
}
