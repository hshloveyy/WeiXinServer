/*
 * @(#)SysWfCommonalityService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-5
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.service;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.ext.CommonProcessInstance;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dao.SysWfCommonalityJdbcDao;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.ReceiptShipConstants;

/**
 * <pre>
 * XDSS3工作流公共服务。
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
public class SysWfCommonalityService extends BaseJdbcService
{
	@Autowired
	private SysWfCommonalityJdbcDao sysWfCommonalityJdbcDao;

	public void setSysWfCommonalityJdbcDao(SysWfCommonalityJdbcDao sysWfCommonalityJdbcDao)
	{
		this.sysWfCommonalityJdbcDao = sysWfCommonalityJdbcDao;
	}

	public String getUserAllActorId()
	{
		return sysWfCommonalityJdbcDao.getUserAllActorId();
	}

	public int getResultCount(String in_strSql)
	{
		return sysWfCommonalityJdbcDao.getResultCount(in_strSql);
	}

	public String getUserProcessSql()
	{
		return sysWfCommonalityJdbcDao.getUserProcessSql();
	}

	public String getAuthProcessSql()
	{
		return sysWfCommonalityJdbcDao.getUserAuthProcessSql();
	}

	@Autowired
	private SysWfTaskActorService sysWfTaskActorService;

	public void setSysWfTaskActorService(SysWfTaskActorService sysWfTaskActorService)
	{
		this.sysWfTaskActorService = sysWfTaskActorService;
	}

	/**
	 *判断当前用户是否是管理人员，目前用角色名称判断
	 * 
	 * @param rolesList
	 * @return
	 */
	public boolean isManager()
	{
		List<SysRole> grantedRoles = UserContextHolder.getLocalUserContext().getUserContext().getGrantedRoles();
		for (Iterator iterator = grantedRoles.iterator(); iterator.hasNext();)
		{
			SysRole sysRole = (SysRole) iterator.next();
			if (sysRole.getRoleName().indexOf("经理") != -1)
				return true;
		}
		return false;
	}

	/**
	 * 取得我的待办事项Grid数据JSONOBjcct. 整合BDP平台待办事项。
	 * 
	 * @param start
	 * @param size
	 * @param modalName
	 * @param businessInfo
	 * @param taskName
	 * @return
	 */
	public JSONObject getMyProcessInstances(int start, int size, String modalName, String businessInfo, String taskName)
	{
		StringBuffer sb = new StringBuffer();
		if (modalName != null && !"".equals(modalName.trim()))
			sb.append(" and model_name like '%").append(modalName).append("%'");
		if (businessInfo != null && !"".equals(businessInfo.trim()))
			sb.append(" and business_note like '%").append(businessInfo).append("%'");
		if (taskName != null && !"".equals(taskName.trim()))
			sb.append(" and name_ like '%").append(taskName).append("%'");
		//20120710
		if(ReceiptShipConstants.ins().isShouldHide()){
			sb.append(" and name_ not like '%*'");
		}

		StringBuffer strSql = new StringBuffer();
		String actorId = this.getUserAllActorId();
		// 是否职能部门 1是 2否
		String isFuncDept = UserContextHolder.getLocalUserContext().getUserContext().getSysDept().getIsFuncDept();
		String appendSQL = " and 1=1";
		String strCountSql;
		// 统一查询条件
		StringBuffer whereSql = new StringBuffer();

		// LJX 20100429 Add BDP工作流待办SQL查询条件。
		// 备注：查询字段 t.ASSIGNLOGIC,t.COMMONPROCESSID,t.PARENTCOMMONPROCESSID
		// 暂时做冗余，如果确认不使用，可以去掉。
		// PROCESSTYPE='1' 为旧信达流程、PROCESSTYPE='2' 为平台流程。
		String bdpWorkTaskWhereSql = BdpTaskService.getWhereSql();
		String bdpDataAuthSql = "";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 非职能部门职能看到自己部门的数据
		if ("2".equals(isFuncDept))
		{
			String deptId = userContext.getSysDept().getDeptid();
			String userName = userContext.getSysUser().getUserName();
			String userId = userContext.getSysUser().getUserId();
			// 部门经理可以看到所属部门下的业务记录，其他业务人员不允许看到
			if (this.isManager())
			{
				appendSQL = " and Department_Id='" + deptId + "' and ((t.ACTOR_NAME not like '%业务员%' or t.CREATOR='" + userName + "') ";
				appendSQL += "or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";

				bdpDataAuthSql = " and Department_Id='" + deptId + "' and ((t.ACTOR_NAME not like '%业务员%' or t.CREATOR='" + userId + "') ";
				bdpDataAuthSql += "or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_id from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
			}
			else
			{
				appendSQL = " and((Department_Id='" + deptId + "' and CREATOR='" + userName + "') or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_name from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
				bdpDataAuthSql = " and((Department_Id='" + deptId + "' and CREATOR='" + userId + "') or " + "(Department_Id='" + deptId + "' and CREATOR not in (select u.user_id from t_sys_user u,t_sys_dept_user t where u.user_id=t.user_id and t.dept_id='" + deptId + "')))";
			}

			whereSql.append(" ((PROCESSTYPE='1' and ACTOR_ID in (" + actorId + ")" + appendSQL + ")");
			whereSql.append(" or (PROCESSTYPE='2' and " + bdpWorkTaskWhereSql + bdpDataAuthSql + "))");

			strSql.append("select * from (select rownum rnum,a.* ");
			strSql.append(" from ( select DISTINCT t.BOID,t.ASSIGNLOGIC,t.COMMONPROCESSID,t.EXTPROCESSID,t.PARENTCOMMONPROCESSID,t.ENDNODENAME,t.INSENDTIME,t.NODEID,t.PROCESSTYPE,t.TASKCREATETIME,t.PROCESS_ID,t.START_,t.MODEL_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_");
			strSql.append("  from V_SYS_WF_PROCESS_USER t ");
			strSql.append("  where 1=1 and " + whereSql.toString() + sb.toString());
			strSql.append("  order by ins_create_time desc) a )where rnum>" + start + " and rnum<" + (start + size + 1));

		}
		else
		{
			String processSql = this.getUserProcessSql();
			String authSql = this.getAuthProcessSql();
			log.debug("转移权限：" + authSql);
			whereSql.append(" ((processType = '1' and (" + processSql + authSql + ")) ");
			whereSql.append(" or (PROCESSTYPE='2' and ((" + bdpWorkTaskWhereSql + "  and Department_Id in (" + userContext.getGrantedDepartmentsId() + ")) " + authSql + ") ))");

			strSql.append("select * from (select rownum rnum,a.* ");
			strSql.append(" from ( select DISTINCT t.BOID,t.ASSIGNLOGIC,t.COMMONPROCESSID,t.EXTPROCESSID,t.PARENTCOMMONPROCESSID,t.ENDNODENAME,t.INSENDTIME,t.NODEID,t.PROCESSTYPE,t.TASKCREATETIME,t.PROCESS_ID,t.START_,t.MODEL_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_");
			strSql.append(" from V_SYS_WF_PROCESS_USER t " + "where 1=1  and " + whereSql.toString() + sb.toString());
			strSql.append(" order by ins_create_time desc) a )where rnum>" + start + " and rnum<" + (start + size + 1));
		}

		strCountSql = "select count(*) from (select DISTINCT t.BOID,t.ASSIGNLOGIC,t.COMMONPROCESSID,t.EXTPROCESSID,t.PARENTCOMMONPROCESSID,t.ENDNODENAME,t.INSENDTIME,t.NODEID,t.PROCESSTYPE,t.TASKCREATETIME,t.PROCESS_ID,t.TASK_ID,t.BUSINESS_RECORD_ID,t.BUSINESS_NOTE,t.PROCESS_URL,t.Ins_Create_Time,t.MODEL_NAME,t.NAME_" + " from V_SYS_WF_PROCESS_USER t " + " where 1=1 and " + whereSql.toString() + sb.toString() + ")";
		int iResultCount = this.getResultCount(strCountSql);

		log.debug("工作流待办SQL查询语句:" + strSql.toString());
		// log.debug("工作流待办SQL查询语句:" + strCountSql);

		List<CommonProcessInstance> instanceList = sysWfTaskActorService.getMyProcessInstances(strSql.toString());
		JSONArray ja = JSONArray.fromObject(instanceList);
		JSONObject jo = new JSONObject();
		jo.put("totalProperty", iResultCount);
		jo.put("root", ja);

		return jo;
	}
}
