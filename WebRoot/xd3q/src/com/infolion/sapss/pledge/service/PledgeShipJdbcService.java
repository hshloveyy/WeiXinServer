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

import java.util.HashMap;
import java.util.Iterator;
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
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.pledge.dao.PledgeShipJdbcDao;
import com.infolion.sapss.pledge.domain.PledgeShipsInfo;
import com.infolion.sapss.pledge.domain.PledgeShipsItem;

/**
 * 
 * <pre></pre>
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
public class PledgeShipJdbcService extends BaseJdbcService {

	@Autowired
	PledgeShipJdbcDao  pledgeShipJdbcDao;	
	public void setPledgeShipJdbcDao(PledgeShipJdbcDao pledgeShipJdbcDao) {
		this.pledgeShipJdbcDao = pledgeShipJdbcDao;
	}
	public String getPledgeShipsInfoNo(String pledgeShipsInfoId) {
		String code = this.pledgeShipJdbcDao.queryDeptCode(pledgeShipsInfoId);
		return pledgeShipJdbcDao.getShipNo(code.substring(0, 2));
	}

	/**
	 * 获取MAP的对象名与值
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getFilter(Map map) {
		Map filter = new HashMap();
		if (map != null && !map.isEmpty()) {
			Iterator entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (java.util.Map.Entry) entries.next();
				String key = entry.getKey().toString();
				String[] keyValue = (String[]) entry.getValue();
				filter.put(key, keyValue[0]);
			}
		}
		return filter;
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
				+ ",T1.REAL_NAME,T2.DEPT_NAME" + " From T_PLEDGESHIPS_INFO T"
				+ " Left Join T_Sys_User T1 On T.Creator=T1.User_Id"
				+ " Left Join T_Sys_Dept T2 On T.Dept_ID=T2.Dept_ID"
				+ " left outer join T_PLEDGESHIPS_ITEM p on T.PLEDGESHIPS_INFO_ID=p.PLEDGESHIPS_ID"
				+ " Where 1=1 ");
		if ("0".equals(map.get("isAvailable")))
			sb.append(" and T.Is_Available='0'");
		else sb.append(" and T.Is_Available='1'");
		// 出仓单号
		if (StringUtils.isNotBlank(map.get("pledgeShipsInfoNo"))){
			sb.append(" and t.PLEDGESHIPS_INFO_NO like '%" + map.get("pledgeShipsInfoNo")+ "%'");
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

	public int deletePledgeShipsInfo(String pledgeShipsInfoId) {
		return this.pledgeShipJdbcDao.delete(pledgeShipsInfoId);
	}
	public void updateShipMateriel(String shipDetailId, String colName,
			String colValue) throws BusinessException {
		this.pledgeShipJdbcDao.updateShipMateriel(shipDetailId, colName, colValue);

	}
	public JSONObject submitWorkflow(PledgeShipsInfo pledgeShipsInfo,
			String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";

		pledgeShipsInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("pledge_export"));

		try {
			if (StringUtils.isNotBlank(taskId)) {
				TaskInstanceContext taskInstanceContext = WorkflowService
						.getTaskInstanceContext(taskId);
				String taskName = taskInstanceContext.getTaskName();

				
			}
			submitWorkflow(taskId, pledgeShipsInfo);
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
	private void submitWorkflow(String taskId, PledgeShipsInfo pledgeShipsInfo) {
		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(pledgeShipsInfo,
					pledgeShipsInfo.getPledgeShipsInfoId());
			this.pledgeShipJdbcDao.submitUpdateState(pledgeShipsInfo.getPledgeShipsInfoId());
		} else {
			WorkflowService.signalProcessInstance(pledgeShipsInfo, pledgeShipsInfo.getPledgeShipsInfoId(), null);
		}
		
	}
	public void update(String shipsInfoNo,String shipsInfoId) {
		this.pledgeShipJdbcDao.update(shipsInfoNo,shipsInfoId);
		
	}
	
	public List<PledgeShipsItem> queryShipMaterialList(String shipId){
		return pledgeShipJdbcDao.queryShipMaterialList(shipId);
	}
}
