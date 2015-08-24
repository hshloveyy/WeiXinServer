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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.credit.dao.CreditEntryHibernateDao;
import com.infolion.sapss.credit.dao.CreditEntryHisJdbcDao;
import com.infolion.sapss.credit.dao.CreditEntryJdbcDao;
import com.infolion.sapss.credit.dao.CreditHisInfoHibernateDao;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;
import com.infolion.sapss.credit.web.CreditEntryController;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
import com.infolion.sapss.payment.dao.PickListInfoHibernateDao;
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
public class CreditEntryJdbcService extends BaseJdbcService implements
		ProcessCallBack
{
	private final Log log = LogFactory.getLog(CreditEntryJdbcService.class);
	@Autowired
	private CreditEntryJdbcDao creditEntryJdbcDao;

	public void setCreditEntryJdbcDao(CreditEntryJdbcDao creditEntryJdbcDao)
	{
		this.creditEntryJdbcDao = creditEntryJdbcDao;
	}

	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private CreditEntryHibernateDao creditEntryHibernateDao;

	public void setCreditEntryHibernate(CreditEntryHibernateDao creditEntryHibernateDao)
	{
		this.creditEntryHibernateDao = creditEntryHibernateDao;
	}
	
	@Autowired
	private CreditEntryHisJdbcDao creditEntryHisJdbcDao;

	public void setCreditEntryHisJdbcDao(CreditEntryHisJdbcDao creditEntryHisJdbcDao)
	{
		this.creditEntryHisJdbcDao = creditEntryHisJdbcDao;
	}

	/**
	 * 根据ID删除信用证表(T_CREDIT_INFO)、信用证开证表(T_CREDIT_CREATE)数据
	 * T_CREDIT_INFO表只做逻辑删除,T_CREDIT_CREATE表做物理删除
	 * 
	 * @param creditId
	 * @return
	 */
	public boolean deletecreditInfo(String creditId)
	{
		int i = this.creditEntryJdbcDao.delete(creditId);
		int j = this.creditEntryJdbcDao.deleteCreditCreate(creditId);

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
		StringBuffer sb = new StringBuffer();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getSysDept().getDeptid();

		sb.append("select t.CREDIT_ID,t.CREDIT_NO,t.CREATE_DATE,t.DEPT_ID,t.PROJECT_NO,t.CONTRACT_NO,t.SAP_ORDER_NO,P.EKKO_UNSEZ,");
		sb.append("t.CREATE_BANK,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,t.CREATOR,b.DEPT_NAME ");
		sb.append("from T_CREDIT_INFO t left outer join t_sys_dept b  on t.DEPT_ID= b.DEPT_ID  left outer join t_contract_purchase_info p on t.contract_id=p.contract_purchase_id" + "  where t.is_available='1' and t.CREATE_OR_REC=1 and t.trade_type='" + map.get("tradeType") + "' ");

		// 部门ID
		if (StringUtils.isNotBlank(map.get("deptid")))
		{
			sb.append(" and t.DEPT_ID = '" + map.get("deptid") + "'");
		}
		else
		{
			// 部门ID权限控制
			if (strSqlDeptId != null || "".equals(strSqlDeptId) == false)
			{
				sb.append(" and t.DEPT_ID = '" + strSqlDeptId + "'");
			}
		}

		// 信用证号
		if (StringUtils.isNotBlank(map.get("creditNo")))
			sb.append(" and t.CREDIT_NO like '%" + map.get("creditNo") + "%'");
		// 开证行
		if (StringUtils.isNotBlank(map.get("createBank")))
			sb.append(" and t.CREATE_BANK like '%" + map.get("createBank") + "%'");
		// 合同编码
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and t.CONTRACT_NO like '%" + map.get("contractNo") + "%'");
		if (StringUtils.isNotBlank(map.get("contractPaperNo")))
			sb.append(" and p.ekko_unsez like '%" + map.get("contractPaperNo") + "%'");
		// SAP订单号
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo") + "%'");
		// 开证起始日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.CREATE_DATE >= '" + map.get("sDate") + "'");
		// 开证终止日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.CREATE_DATE <= '" + map.get("eDate") + "'");

		sb.append("  order by t.CREATOR_TIME desc");
		// log.debug("信用证查询功能SQL语句:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 取得 信用证综合查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQueryListSql(Map<String, String> map)
	{
		StringBuffer sb = new StringBuffer();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getGrantedDepartmentsId();

		sb.append("select t.CREDIT_ID,t.CREDIT_NO,t.CREATE_DATE,t.DEPT_ID,t.PROJECT_NO,t.project_name,t.CONTRACT_NO,t.SAP_ORDER_NO,");
		sb.append("t.CREATE_BANK,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,t.CREATOR,b.DEPT_NAME,t.PAYMENT_TYPE,t.BENEFIT,T.CURRENCY,T.AMOUNT,t.valid_Date,t.loading_Period,c.title  ");
		sb.append("from T_CREDIT_INFO t left outer join t_sys_dept b  on t.DEPT_ID= b.DEPT_ID left outer join bm_credit_state c on t.credit_state=c.id" + "  where t.is_available='1' and t.CREATE_OR_REC=1 ");

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

		// 开证行
		if (StringUtils.isNotBlank(map.get("createBank")))
			sb.append(" and t.CREATE_BANK like '%" + map.get("createBank") + "%'");
		// 合同编码
		if (StringUtils.isNotBlank(map.get("contractNo")))
			sb.append(" and t.CONTRACT_NO like '%" + map.get("contractNo") + "%'");
		// SAP订单号
		if (StringUtils.isNotBlank(map.get("sapOrderNo")))
			sb.append(" and t.SAP_ORDER_NO like '%" + map.get("sapOrderNo") + "%'");
		// 开证起始日期
		if (StringUtils.isNotBlank(map.get("sDate")))
			sb.append(" and t.CREATE_DATE >= '" + map.get("sDate") + "'");
		// 开证终止日期
		if (StringUtils.isNotBlank(map.get("eDate")))
			sb.append(" and t.CREATE_DATE <= '" + map.get("eDate") + "'");
		if (StringUtils.isNotBlank(map.get("projectName")))
			sb.append(" and t.project_name like '%" + map.get("projectName") + "%'");

		sb.append("  order by t.CREATOR_TIME desc");
		log.debug("信用证综合查询功能SQL语句:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 取得查询信用证开证列表的SQL语句
	 * 
	 * @return
	 */
	public String getCreditEntryListSql(String tradeType)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String strSqlDeptId = userContext.getSysDept().getDeptid();

		StringBuffer sb = new StringBuffer();
		sb.append(" select t.*,t.CREDIT_STATE CREDIT_STATE_D_CREDIT_STATE,b.DEPT_NAME ");
		sb.append(" from T_CREDIT_INFO t left outer join  t_sys_dept b on  t.DEPT_ID= b.DEPT_ID ");
		// sb.append(" left outer join T_CREDIT_HIS_INFO a on a.CREDIT_ID=t.CREDIT_ID and a.CHANGE_STATE in('2','4')");
		sb.append(" where  t.is_available='1' and t.CREATE_OR_REC=1  and t.trade_type='" + tradeType + "' ");
		// 部门ID权限控制
		if (StringUtils.isNotBlank(strSqlDeptId))
		{
			sb.append(" and t.DEPT_ID = '" + strSqlDeptId + "'");
		}
		sb.append(" order by t.CREATOR_TIME desc");

		return sb.toString();
	}

	/**
	 * 取得查询采购合同信息的SQL语句
	 * 
	 * @param request
	 * @return
	 */
	public String getQueryPurchaseSql(HttpServletRequest request)
	{
		String contractGroupNo = extractFR(request, "contractGroupNo");
		String contractGroupName = request.getParameter("contractGroupName");
		String contractNo = extractFR(request, "contractNo");
		String contractName = request.getParameter("contractName");
		String depId = request.getParameter("depId");
		String orderState = request.getParameter("orderState");
		String tradeType = request.getParameter("tradeType");
		String ekkoUnsez = request.getParameter("ekkoUnsez");

		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_purchase_id,a.ekko_Unsez," + "a.contract_no,a.contract_name,a.sap_order_no,a.ymat_group" + "  from t_contract_purchase_info a, t_contract_group b, t_project_info c" + " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id and a.is_available='1'";
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and a.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";

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
		if (StringUtils.isNotBlank(orderState))
		{
			sql = sql + " and a.order_state in (" + orderState + ")";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and a.trade_type = " + tradeType;
		}
		if(StringUtils.isNotBlank(ekkoUnsez)){
			sql = sql + " and a.ekko_Unsez like '%"+ekkoUnsez+"%'";
		}

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
	public String getViewPurchaseListByCreditIdSql(String creditId)
	{
		// CONTRACT_PURCHASE_ID,CREDIT_ID,SAP_ORDER_NO,CONTRACT_NO,CONTRACT_NAME
		StringBuffer sb = new StringBuffer();
		sb.append("select a.CONTRACT_PURCHASE_ID CONTRACT_ID,a.CREDIT_ID,b.SAP_ORDER_NO,b.CONTRACT_NO,b.CONTRACT_NAME ,b.YMAT_GROUP");
		sb.append(" from  T_CREDIT_CREATE a,t_contract_purchase_info b");
		sb.append(" where  a.contract_purchase_id=b.contract_purchase_id");
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
		// Map map = new HashMap();
		// map.put("_workflow_total_value", totalValue);
		// creditInfo.setWorkflowUserDefineTaskVariable(map);
		
		creditInfo.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("credit_application_v1"));
		/**流程新增信用证类型 modify by cat 20131114*/
		String wfModelName = "信用证开证申请";
		if("1".equals(Constants.getPurTradeType(creditInfo.getTradeType()))) wfModelName = "进口信用证开证申请";
		else if ("2".equals(Constants.getPurTradeType(creditInfo.getTradeType()))) wfModelName = "国内信用证开证申请";
		creditInfo.setWorkflowModelName(wfModelName);
		/***modify by cat 20131114**/
		
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(creditInfo, creditInfo.getCreditId());
			this.creditEntryJdbcDao.submitUpdateState(creditInfo.getCreditId());
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
	 * 回调更新信用证开证状态 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效5、备用6、过期7、撤销8、关闭9、作废10、改证
	 */
	public void updateBusinessRecord(String businessRecordId, String examineResult, String sapOrderNo)
	{
		String result = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			result = "5";
		}
		else if (ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
		{
			result = "9";
		}
		this.creditEntryJdbcDao.workflowUpdateState(businessRecordId, examineResult, result);

		if ("5".equals(result))
		{
			// 开始新增最原始数据到 信用证开证历史表，版本号为0
			TCreditInfo creditInfo = this.creditEntryHibernateDao.get(businessRecordId);
			TCreditHisInfo creditHisInfo = new TCreditHisInfo();

			BeanUtils.copyProperties(creditInfo, creditHisInfo);

			creditHisInfo.setCreditHisId(CodeGenerator.getUUID()); // 信用证开证修改历史表ID
			creditHisInfo.setVersionNo(0); // 原始版本为0 ,如果有修改则新增一笔数据版本号为1(递增)
			creditHisInfo.setIsExecuted("0"); // 默认为 变更还未执行
			creditHisInfo.setCreditId(creditInfo.getCreditId()); // 重新设定 信用证ID号。
			//creditHisInfo.setChangeTime(DateUtils.getCurrTime(2));
			creditHisInfo.setApplyTime("");// 申报时间
			creditHisInfo.setApprovedTime("");// 审批通过时间
			creditHisInfo.setChangeState("0");

			this.creditHisInfoHibernateDao.save(creditHisInfo);

		}
	}

	/**
	 * 新增开证时候，初始化基本信息
	 * 
	 * @param tradeType
	 * @return
	 */
	public TCreditInfo getInitEntity(String tradeType)
	{
		TCreditInfo info = new TCreditInfo();

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		// 信用证状态
		info.setCreditState("1"); // 1 信用证开证 2 信用证收证 3 执行 4 改证 5 撤销 6 结束
		// 开/收证标识CREATE_OR_REC
		info.setCreateOrRec("1"); // 1开证 2收证
		// 贸易类型
		info.setTradeType(tradeType);
		// 开证日期 默认为当天。
		info.setCreateDate(DateUtils.getCurrDate(2));
		// 申请人 默认为登陆人姓名
		info.setRequest(userContext.getSysUser().getRealName());

		info.setDeptId(userContext.getSysDept().getDeptid());
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
			if (operate.equals("modify"))
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
			else if (operate.equals("iframe"))
			{
				submitHidden = true;
				saveDisabled = true;
				revisable = false;
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
 * 
 * @param creditId
 * @param status
 */
	public void saveCreditStatus(String creditId, String status) {
		String creditHisId = this.creditEntryHisJdbcDao.getCreditHisId(creditId);
		TCreditHisInfo info_o  = creditHisInfoHibernateDao.get(creditHisId);
		TCreditHisInfo info = new TCreditHisInfo();
		if(info_o==null){
			info = new TCreditHisInfo();
			TCreditInfo creditInfo = creditEntryHibernateDao.get(creditId);
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

		this.creditEntryJdbcDao.saveCreditStatus(creditId,status);
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		creditEntryJdbcDao.dealOutToExcel(sql ,excel);
	}
}
