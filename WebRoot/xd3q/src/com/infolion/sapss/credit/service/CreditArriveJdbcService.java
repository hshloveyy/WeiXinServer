/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.credit.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.credit.dao.CreditArriveHibernateDao;
import com.infolion.sapss.credit.dao.CreditArriveJdbcDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

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
public class CreditArriveJdbcService extends BaseJdbcService implements
		ProcessCallBack
{

	@Autowired
	private CreditArriveJdbcDao creditArriveJdbcDao;

	public void setCreditArriveJdbcDao(CreditArriveJdbcDao creditArriveJdbcDao)
	{
		this.creditArriveJdbcDao = creditArriveJdbcDao;
	}

	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private CreditArriveHibernateDao creditArriveHibernateDao;

	public void setCreditArriveHibernate(CreditArriveHibernateDao creditArriveHibernateDao)
	{
		this.creditArriveHibernateDao = creditArriveHibernateDao;
	}

	/**
	 * 根据ID删除信用证表(T_CREDIT_INFO)、信用证到证表(T_CREDIT_CREATE)数据
	 * T_CREDIT_INFO表只做逻辑删除,T_CREDIT_CREATE表做物理删除
	 * 
	 * @param creditId
	 * @return
	 */
	public boolean deletecreditInfo(String creditId)
	{
		int i = this.creditArriveJdbcDao.delete(creditId);
		int j = this.creditArriveJdbcDao.deleteCreditRec(creditId);

		boolean result = false;
		if (i > 0 && j > 0)
		{
			result = true;
		}

		return result;
	}

	/**
	 * 取得 查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQuerySql(Map<String, String> map)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getGrantedDepartmentsId();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.CREDIT_ID,t.CREDIT_NO,t.CUSTOM_CREATE_DATE,t.CREDIT_REC_DATE,t.CREATE_DATE,");
		sb.append("t.DEPT_ID,t.PROJECT_NO,t.CONTRACT_NO,t.SAP_ORDER_NO,t.CREATE_BANK,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,");
		sb.append("t.CREATOR,b.DEPT_NAME  from T_CREDIT_INFO t , t_sys_dept b where  t.DEPT_ID= b.DEPT_ID and t.is_available='1' and t.trade_type='" + map.get("tradeType") + "' and t.CREATE_OR_REC=2 ");

		// 部门ID
		if (map.get("deptid") != null)
		{
			sb.append(" and t.DEPT_ID = '" + map.get("deptid") + "'");
		}
		else
		{
			// 部门ID权限控制
			if (StringUtils.isNotBlank(strSqlDeptId))
			{
				sb.append(" and t.DEPT_ID in (" + strSqlDeptId + ")");
			}
		}
		
		// 信用证号
		if (map.get("creditNo") != null)
			sb.append(" and t.CREDIT_NO like '%" + map.get("creditNo") + "%'");
		// 到证行
		if (map.get("createBank") != null)
			sb.append(" and t.CREATE_BANK like '%" + map.get("createBank") + "%'");
		// 合同编码
		if (map.get("contractNo") != null)
			sb.append(" and t.CONTRACT_NO like '%" + map.get("contractNo") + "%'");
		// SAP订单号
		if (map.get("sapOrderNo") != null)
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo") + "%'");
		// 到证起始日期
		if (map.get("sDate") != null)
			sb.append(" and t.CREATE_DATE >= '" + map.get("sDate") + "'");
		// 到证终止日期
		if (map.get("eDate") != null)
			sb.append(" and t.CREATE_DATE <= '" + map.get("eDate") + "'");

		sb.append(" order by t.CREATOR_TIME desc");
		System.out.println("信用证收证查询功能SQL语句:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 取得 综合查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQueryListSql(Map<String, String> map)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getGrantedDepartmentsId();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.CREDIT_ID,t.CREDIT_NO,t.CUSTOM_CREATE_DATE,t.CREDIT_REC_DATE,t.CREATE_DATE,");
		sb.append("t.DEPT_ID,t.PROJECT_NO,t.PROJECT_NAME,t.AMOUNT,t.REQUEST,t.CONTRACT_NO,t.SAP_ORDER_NO,t.CREATE_BANK,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,");
		sb.append("t.CREATOR,b.DEPT_NAME  from T_CREDIT_INFO t , t_sys_dept b where  t.DEPT_ID= b.DEPT_ID and t.is_available='1' and t.CREATE_OR_REC=2 ");

		// 信用证类型
		if (StringUtils.isNotBlank(map.get("tradeType")))
		{
			sb.append(" and t.trade_type = '" + map.get("tradeType") + "'");
		}

		// 信用证状态
		if (StringUtils.isNotBlank(map.get("creditState")))
		{
			sb.append(" and t.credit_state = '" + map.get("creditState") + "'");
		}

		// 查询。选择的部门
		String deptId = map.get("deptid");

		// 部门条件判断
		if ("1".equals(userContext.getSysDept().getIsFuncDept()))
		{
			// 处理多选
			if (StringUtils.isNotBlank(deptId))
			{
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++)
				{
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				sb.append(" and t.dept_id in (" + deptId + ")");
			}
			sb.append(" and t.dept_id in ( " + strSqlDeptId + ")");
		}
		else
		{
			sb.append(" and t.dept_id = '" + userContext.getSysDept().getDeptid() + "'");
		}

		// 信用证号
		if (StringUtils.isNotBlank(map.get("creditNo")))
			sb.append(" and t.CREDIT_NO like '%" + map.get("creditNo") + "%'");
		// 到证行
		if (StringUtils.isNotBlank(map.get("createBank")))
			sb.append(" and t.CREATE_BANK like '%" + map.get("createBank") + "%'");
		// 合同编码
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and t.CONTRACT_NO like '%" + map.get("contractNo") + "%'");
		// SAP订单号
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo") + "%'");
		// 到证起始日期
		if (StringUtils.isNotBlank(map.get("creditRecDates")))
			sb.append(" and t.CREDIT_REC_DATE >= '" + map.get("creditRecDates") + "'");
		// 到证终止日期
		if (StringUtils.isNotBlank(map.get("creditRecDatee")))
			sb.append(" and t.CREDIT_REC_DATE < '" + map.get("creditRecDatee") + "'");
		// 客户 开证起始日期
		if (StringUtils.isNotBlank(map.get("customerCreateDates")))
			sb.append(" and t.CUSTOM_CREATE_DATE >= '" + map.get("customerCreateDates") + "'");
		// 客户开证证终止日期
		if (StringUtils.isNotBlank(map.get("customerCreateDatee")))
			sb.append(" and t.CUSTOM_CREATE_DATE < '" + map.get("customerCreateDatee") + "'");
		//
		if (StringUtils.isNotBlank(map.get("projectName")))
			sb.append(" and t.project_name like '%" + map.get("projectName") + "%'");
		sb.append(" order by t.CREATOR_TIME desc");
		System.out.println("信用证收证综合查询功能SQL语句:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 取得查询信用证到证列表的SQL语句
	 * 
	 * @return
	 */
	public String getCreditArriveListSql(String tradeType)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getGrantedDepartmentsId();

		StringBuffer sb = new StringBuffer();
		// CREDIT_ID,CREDIT_NO,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,DEPT_ID,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR
		sb.append(" select t.*,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,b.DEPT_NAME");
		sb.append(" from T_CREDIT_INFO t , t_sys_dept b where  t.DEPT_ID= b.DEPT_ID ");
		sb.append("and t.is_available='1' and t.CREATE_OR_REC=2");
		sb.append(" and t.trade_type='" + tradeType + "' ");
		// 部门ID权限控制
		// if (strSqlDeptId != null || "".equals(strSqlDeptId) == false)
		// {
		// sb.append(" and t.DEPT_ID = '" + strSqlDeptId + "'");
		// }
		if (StringUtils.isNotBlank(strSqlDeptId))
		{
			sb.append(" and t.DEPT_ID in (" + strSqlDeptId + ")");
		}

		sb.append(" order by t.CREATOR_TIME desc");

		return sb.toString();
	}

	/**
	 * 取得查询销售合同信息的SQL语句
	 * 
	 * @param request
	 * @return
	 */
	public String getQuerySalesSql(HttpServletRequest request)
	{
		String contractGroupNo = extractFR(request, "contractGroupNo");
		String contractGroupName = request.getParameter("contractGroupName");
		String contractNo = extractFR(request, "contractNo");
		String contractName = request.getParameter("contractName");
		String depId = request.getParameter("depId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");

		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_sales_id," + "a.contract_no,a.contract_name,a.sap_order_no,a.YMAT_GROUP" + "  from t_contract_sales_info a, t_contract_group b, t_project_info c" + " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id and a.is_available='1'";

		if (StringUtils.isNotBlank(contractGroupNo))
		{
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo + "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName))
		{
			sql = sql + " and b.contract_group_name like '%" + contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(depId))
		{
			sql = sql + " and a.dept_id like '%" + depId + "%'";
		}

		sql = sql + " and a.order_state in('3','9','5','7')";
		// if (orderState != null && !"".equals(orderState))
		// {
		// sql = sql + " and a.order_state like '%" + orderState + "%'";
		// }

		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and a.trade_type = " + tradeType;
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
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm)
	{
		try
		{
			String wait = java.net.URLDecoder.decode(req.getQueryString(), "UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1)
			{
				wait = wait.substring(0, pos);
			}
			return wait;
		}
		catch (UnsupportedEncodingException e)
		{}
		return null;
	}

	/**
	 * 取得列出信用证相关联的采购合同信息的SQL
	 * 
	 * @param creditId
	 * @return
	 */
	public String getViewSalesListByCreditIdSql(String creditId)
	{
		// CONTRACT_PURCHASE_ID,CREDIT_ID,SAP_ORDER_NO,CONTRACT_NO,CONTRACT_NAME
		StringBuffer sb = new StringBuffer();
		sb.append("select a.CONTRACT_SALES_ID CONTRACT_ID,a.CREDIT_ID,b.SAP_ORDER_NO,b.CONTRACT_NO,b.CONTRACT_NAME ,b.YMAT_GROUP");
		sb.append(" from  T_CREDIT_REC a,t_contract_sales_info b");
		sb.append(" where  a.contract_Sales_id=b.contract_Sales_id");
		sb.append(" and a.CREDIT_ID='" + creditId + "'");
		return sb.toString();
	}

	/**
	 * 提交流程
	 * 
	 * @param creditInfo
	 * @return
	 */
	public void submitWorkflow(String taskId, TCreditInfo creditInfo)
	{
		//creditInfo.setWorkflowModelName("信用证到证申请");
		
//		creditInfo.setWorkflowProcessName("credit_receive_v1");
		creditInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("credit_receive_v1"));
		/**流程新增信用证类型 modify by cat 20131114*/
		String wfModelName = "信用证到证申请";
		if("3".equals(Constants.getSaleTradeType(creditInfo.getTradeType()))) wfModelName = "出口信用证到证申请";
		else if ("2".equals(Constants.getSaleTradeType(creditInfo.getTradeType()))) wfModelName = "国内信用证到证申请";
		creditInfo.setWorkflowModelName(wfModelName);
		/***modify by cat 20131114**/

		creditInfo.setWorkflowProcessUrl("creditArriveController.spr?action=creditArriveExamine");

		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(creditInfo, creditInfo.getCreditId(), creditInfo.getDeptId());
			// WorkflowService.createAndSignalProcessInstance(creditInfo,creditInfo.getCreditId());
			this.creditArriveJdbcDao.submitUpdateState(creditInfo.getCreditId());

			//LJX 20090430 modify 修改为当审批通过时候才新增历史数据
//			// 开始新增最原始数据到 信用证开证历史表，版本号为0
//			TCreditHisInfo creditHisInfo = new TCreditHisInfo();
//
//			BeanUtils.copyProperties(creditInfo, creditHisInfo);
//
//			creditHisInfo.setCreditHisId(CodeGenerator.getUUID()); // 信用证开证修改历史表ID
//			creditHisInfo.setVersionNo(0); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
//			creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
//			creditHisInfo.setCreditId(creditInfo.getCreditId()); // 重新设定信用证ID号。
//			creditHisInfo.setChangeTime(DateUtils.getCurrTime(2));
//			creditHisInfo.setApplyTime(""); // 申报时间
//			creditHisInfo.setApprovedTime(""); // 审批通过时间
//			creditHisInfo.setChangeState("0"); // 改证状态
//			// 1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
//			this.creditHisInfoHibernateDao.save(creditHisInfo);

		}
		else
		{
			WorkflowService.signalProcessInstance(creditInfo, creditInfo.getCreditId(), null);
		}
	}

	/**
	 * 提交审批流程
	 * 
	 * @param creditInfo
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(TCreditInfo creditInfo, String taskId)
	{
		JSONObject jo = new JSONObject();
		String msg = "";
		try
		{
			submitWorkflow(taskId, creditInfo);
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
	 * 回调更新信用证开证状态
	 */
	public void updateBusinessRecord(String businessRecordId, String examineResult, String creditState)
	{
		String result = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			result = creditState;
		}
		this.creditArriveJdbcDao.workflowUpdateState(businessRecordId, examineResult, result);
		
		//LJX 20090430 modify 信用证收证审批通过，同时保存一份历史版本数据
		// 开始新增最原始数据到 信用证开证历史表，版本号为0
		TCreditInfo creditInfo = this.creditArriveHibernateDao.get(businessRecordId);
		TCreditHisInfo creditHisInfo = new TCreditHisInfo();

		BeanUtils.copyProperties(creditInfo, creditHisInfo);

		creditHisInfo.setCreditHisId(CodeGenerator.getUUID()); // 信用证开证修改历史表ID
		creditHisInfo.setVersionNo(0); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
		creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
		creditHisInfo.setCreditId(creditInfo.getCreditId()); // 重新设定信用证ID号。
		//creditHisInfo.setChangeTime(DateUtils.getCurrTime(2));
		creditHisInfo.setApplyTime(""); // 申报时间
		creditHisInfo.setApprovedTime(""); // 审批通过时间
		creditHisInfo.setChangeState("0"); // 改证状态
		// 1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
		this.creditHisInfoHibernateDao.save(creditHisInfo);
		
	}

	/**
	 * 从信用证收证修改记录，更新数据到信用证主信息表
	 * 
	 * @param creditHisId
	 */
	public JSONObject saveChangeCreditInfo(TCreditHisInfo creditHisInfo, String versionNoOld)
	{
		String creditHisId = "";
		JSONObject jo = new JSONObject();
		boolean isSuccess = true;
		int vNo = 0;
		String versionNo = creditHisInfo.getVersionNo().toString();
		boolean isAdd = false;
		TCreditHisInfo creditHisInfoNew = new TCreditHisInfo();

		if ("0".equals(creditHisInfo.getChangeState()) || creditHisInfo.getChangeState() == null || "4".equals(creditHisInfo.getChangeState()))
		{
			isAdd = true;
		}

		try
		{
			if (isAdd)
			{
				// if ("0".equals(versionNo) == false)
				vNo = java.lang.Integer.parseInt(versionNo) + 1;
				// 新增一笔信用证修改历史数据
				creditHisInfoNew = saveCreditHisInfo(creditHisInfo, vNo);
				creditHisId = creditHisInfoNew.getCreditHisId();
			}
			else
			{
				// 修改
				vNo = Integer.parseInt(versionNo);
				creditHisInfo.setVersionNo(vNo);
				this.creditHisInfoHibernateDao.update(creditHisInfo);
				creditHisInfoNew = this.creditHisInfoHibernateDao.get(creditHisInfo.getCreditHisId());
			}

			// 开始执行变更信息。 从信用证改证历史表 更新到 信用证主表。
			TCreditInfo creditInfo = new TCreditInfo();// this.creditEntryHibernateDao.get(creditId)
			BeanUtils.copyProperties(creditHisInfoNew, creditInfo);
			creditInfo.setCreditState("3"); // 信用证状态 重新设置为 3 审批中
			this.creditArriveHibernateDao.update(creditInfo);

		}
		catch (Exception e)
		{
			isSuccess = false;
		}

		if (isSuccess)
		{
			jo.put("success", true);
			jo.put("creditHisId", creditHisId);
			jo.put("versionNo", creditHisInfo.getVersionNo().toString());
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

		return jo;
	}

	/**
	 * 新增一笔信用证修改历史数据
	 * 
	 * @param creditHisInfo
	 * @return
	 */
	public TCreditHisInfo saveCreditHisInfo(TCreditHisInfo creditHisInfo, int vNo)
	{
		boolean isSuccess = true;
		JSONObject jo = new JSONObject();
		String creditHisId = "";
		// 新增 信用证收证修改历史。
		creditHisId = CodeGenerator.getUUID();
		creditHisInfo.setCreditHisId(creditHisId);
		creditHisInfo.setIsExecuted("1"); // 默认为 变更还未执行
		creditHisInfo.setChangeTime(DateUtils.getCurrTime(2)); // 变更时间
		creditHisInfo.setChangeExecTime(DateUtils.getCurrTime(2));// 变更执行时间
		creditHisInfo.setChangeState("2");// 状态为，信用证改证申请。1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
		// 。注：如果状态为0则为还未有变更记录。

		if ("0".equals(vNo))
		{
			vNo = vNo + 1;
		}

		creditHisInfo.setVersionNo(vNo); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)

		this.creditHisInfoHibernateDao.save(creditHisInfo);

		return creditHisInfo;
	}

	/**
	 * 新增信用证收证时候，初始化基本信息
	 * 
	 * @param tradeType
	 * @return
	 */
	public TCreditInfo getInitEntity(String tradeType)
	{
		TCreditInfo info = new TCreditInfo();

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 信用证状态
		info.setCreditState("2"); // 1 信用证开证 2 信用证收证 3 执行 4 改证 5 撤销 6 结束
		// 开/收证标识CREATE_OR_REC
		info.setCreateOrRec("2"); // 1开证 2收证
		// 贸易类型
		info.setTradeType(tradeType);

		// 审证人 默认为登陆人姓名
		info.setApplyer(userContext.getSysUser().getRealName());

		// LJX 20090415 信用证收证是职能部门贸管部的功能，所以部门名称（表中的dept_id）不是贸管部，应该是同受益人一样的部门
		// info.setDeptId(userContext.getSysDept().getDeptid());
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreatorTime(DateUtils.getCurrTime(2));
		info.setIsAvailable("1");

		return info;
	}

	/**
	 * 取得页面保存按钮、提交按钮、上传附件权限
	 * 
	 * @param operate
	 * @param creator
	 * @return
	 */
	public Map<String, Boolean> getControlMap(String operate, String creator)
	{
		// 保存按钮是否 灰色
		boolean saveDisabled = true; // 默认 按钮灰
		// 提交按钮是否隐藏
		boolean submitHidden = true;
		boolean revisable = true;
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		if (operate != null)
		{
			if (operate.equals("modify") || operate.equals("iframe"))
			{
				submitHidden = true;
				if (userContext.getSysUser().getUserId().equals(creator))
				{
					saveDisabled = false;
					revisable = true;
				}
				else
				{
					saveDisabled = true;
					revisable = false;
				}
			}
			else if (operate.equals("workflow"))
			{
				saveDisabled = true;
				submitHidden = false;
				revisable = true;
			}
			else
			{
				saveDisabled = true;
				submitHidden = true;
				revisable = false;
			}
		}

		map.put("saveDisabled", saveDisabled);
		map.put("submitHidden", submitHidden);
		map.put("revisable", revisable);

		return map;
	}

	/**
	 * 提交工作流时候，初始化基本信息等
	 * 
	 * @param creditInfo
	 * @param doWorkflow
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 */
	public void initWorkflowInfo(TCreditInfo creditInfo, String doWorkflow, String workflowLeaveTransitionName, String workflowExamine, String creditState)
	{
		creditInfo.setApprovedTime(DateUtils.getCurrTime(2));
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		creditInfo.setWorkflowBusinessNote(userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|" + creditInfo.getProjectNo() + "|" + creditInfo.getProjectName());

		if (doWorkflow == null && !"mainForm".equals(doWorkflow))
		{
			creditInfo.setWorkflowExamine(workflowExamine);
			creditInfo.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		}

		Map workflowUserDefineTaskVariable = new HashMap();
		workflowUserDefineTaskVariable.put("_workflow_credit_state", creditState);
		creditInfo.setWorkflowUserDefineTaskVariable(workflowUserDefineTaskVariable);

		creditInfo.setWorkflowModelName("信用证到证申请");
		creditInfo.setWorkflowProcessName("credit_receive_v1");
		creditInfo.setWorkflowProcessUrl("creditArriveController.spr?action=creditArriveExamine");
	}

	/**
	 * 审批页面 进行保存动作，则 对信用证状态进行重新赋值
	 * 
	 * @param creditInfo
	 * @param operate
	 * @param creditState
	 */
	public void setCreditState(TCreditInfo creditInfo, String operate, String creditState)
	{
		// 如果 是从 审批页面 进行保存动作，则 对信用证状态进行重新赋值
		if ("iframe".equals(operate))
		{
			creditInfo.setCreditState(creditState);
		}
	}

	/**
	 * 取得审批过程中，是否有修改 信用证状态。
	 * 
	 * @param creditInfo
	 * @param taskId
	 * @param workflowLeaveTransitionName
	 * @return
	 */
	public boolean getIsModifyCreditState(String creditId, String creditState, String taskId, String workflowLeaveTransitionName)
	{
		boolean isPass = true;
		if (taskId != null && taskId != "")
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			String taskName = taskInstanceContext.getTaskName();
			if ("贸管人员更改信用证状态".equals(taskName))
			{
				if (("3".equals(creditState) || (creditState == null) || "".equals(creditState)) && "状态已更新".equals(workflowLeaveTransitionName))
				{
					isPass = false;
				}

				this.creditArriveJdbcDao.updateCreditHisInfoState(creditId);
			}
		}

		return isPass;
	}

	/**
	 * 根据信用证ID取得信用证修改历史记录表信息
	 * 
	 * @param creditId
	 * @return
	 */
	public String getqueryCreditHisInfoSql(String creditId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select t.*,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,b.DEPT_NAME from t_credit_his_info t , t_sys_dept b ");
		sb.append(" where  t.DEPT_ID= b.DEPT_ID and t.is_available='1' and t.credit_Id='" + creditId + "'");
		sb.append(" order by t.CREATOR_TIME desc");
		return sb.toString();
	}

	/**
	 * 查询立项信息列表SQL语句
	 * 
	 * @param strProjectName
	 * @param strProjectNo
	 * @param deptId
	 * @param tradeType
	 * @return
	 */
	public String getQueryProjectInfoSQL(String strProjectName, String strProjectNo, String deptId, String tradeType)
	{
		String strSql = "select a.project_id,a.project_name,a.project_no,a.ymat_group " + "from t_project_info a " + "where  a.is_available = '1'";
		if (strProjectName != null && !"".equals(strProjectName))
		{
			strSql = strSql + " and a.project_name like '%" + strProjectName + "%'";
		}

		if (strProjectNo != null && !"".equals(strProjectNo))
		{
			strSql = strSql + " and a.project_no like '%" + strProjectNo + "%'";
		}

		if (tradeType != null && !"".equals(tradeType))
		{
			strSql = strSql + " and a.trade_type = '" + tradeType + "'";
		}
		if(StringUtils.isNotBlank(deptId))
			strSql = strSql + " and a.dept_id = '" + deptId + "'";
		// 立项状态为生效,变更通过
		strSql = strSql + " and a.project_state in('3','8')";
		return strSql;
	}

	/**
	 * 取得部门查询SQL语句
	 * 
	 * @param strDeptName
	 * @param strDeptCode
	 * @return
	 */
	public String getQueryDeptInfoSQL(String strDeptName, String strDeptCode)
	{
		String strSql = "select a.* from t_sys_dept a where a.DELETED = '1'";
		if (strDeptName != null && !"".equals(strDeptName))
		{
			strSql = strSql + " and a.dept_name like '%" + strDeptName + "%'";
		}

		if (strDeptCode != null && !"".equals(strDeptCode))
		{
			strSql = strSql + " and a.dept_code like '%" + strDeptCode + "%'";
		}

		return strSql;
	}
	
	
	/**
	 * 根据信用证ID取得信用证收证修改历史记录(最高版本)，最新数据。
	 * 
	 * @param creditId
	 * @return credit_his_id,信用证历史信息ID
	 */
	public String getCreditHisId(String creditId)
	{
       String creditHisID = "";
       creditHisID = this.creditArriveJdbcDao.getCreditHisId(creditId);
       return creditHisID;  
	}
	
	/**
	 * 根据信用证ID判断该信用证是否有修改历史。注：信用证历史版本号大于0的就是有修改历史（信用证改证）
	 * @param creditId
	 * @return
	 */
	public boolean getIsHasHisInfo(String creditId)
	{
		boolean isHasHisInfo  = false;
		String versionNO = "";
		versionNO = this.creditArriveJdbcDao.getMaxVersionNo(creditId);
		if(org.apache.commons.lang.StringUtils.isBlank(versionNO))
		{
			 isHasHisInfo  = false;
		}
		else if("0".equals(versionNO))
		{
			 isHasHisInfo  = false;
		}
		else
		{
			 isHasHisInfo  = true;
		}
		
		System.out.print("信用证ID为: " + creditId + " ,最高版本号为: " + versionNO);
		
		return isHasHisInfo;
	}
	
	
	public String checkFilds(TCreditInfo creditInfo) 
	{
		String exceptionMsg = "";
		if (creditInfo.getBenefit() == null || "".equals(creditInfo.getBenefit()))
		{
			exceptionMsg = "[受益人]为必填！";
		}
		if(creditArriveJdbcDao.isExistCreditNo(creditInfo.getCreditId(), creditInfo.getCreditNo())){
			exceptionMsg = "[信用证号："+creditInfo.getCreditNo()+"]已存在，不允许重复！";
		}
		
//		String amount = creditInfo.getAmount();
//		String rate = creditInfo.getRate().toString();
//		
//		if(StringUtils.isNumeric(amount)== false && StringUtils.isBlank(amount))
//		{
//			exceptionMsg = "[外币金额]必须为数字！"; 	
//		}
//		if(StringUtils.isNumeric(rate)== false && StringUtils.isBlank(rate))
//		{
//			exceptionMsg = "[汇率]必须为数字！"; 	
//		}
		
		return exceptionMsg;
	}
	/**
	 * 
	 * @param creditId
	 * @param status
	 */
	public void saveCreditStatus(String creditId, String status) {
		String creditHisId = this.creditArriveJdbcDao.getCreditHisId(creditId);
		TCreditHisInfo info_o  = creditHisInfoHibernateDao.get(creditHisId);
		TCreditHisInfo info = new TCreditHisInfo();
		if(info_o==null){
			info = new TCreditHisInfo();
			TCreditInfo creditInfo = creditArriveHibernateDao.get(creditId);
			try{
				PropertyUtils.copyProperties(info, creditInfo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		try{
			PropertyUtils.copyProperties(info, info_o);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 新增一条有效信用证改正。
		creditHisId = CodeGenerator.getUUID();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		info.setCreator(userContext.getSysUser().getUserId());
		info.setCreditHisId(creditHisId);
		info.setIsExecuted("1"); // 默认为 变更还未执行
		info.setApplyTime(DateUtils.getCurrTime(2));
		info.setApprovedTime(DateUtils.getCurrTime(2));
		info.setChangeTime(DateUtils.getCurrTime(2)); // 变更时间
		info.setChangeExecTime(DateUtils.getCurrTime(2));
		info.setChangeState("4");// 状态为，信用证改证申请。1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
		info.setVersionNo(info.getVersionNo()+1); // 
        info.setCreditState(status);
		
		this.creditHisInfoHibernateDao.save(info);
		this.creditArriveJdbcDao.saveCreditStatus(creditId,status);
	}

}
