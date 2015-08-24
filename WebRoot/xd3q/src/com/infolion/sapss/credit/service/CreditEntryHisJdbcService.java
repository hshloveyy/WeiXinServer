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

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.credit.dao.CreditEntryHibernateDao;
import com.infolion.sapss.credit.dao.CreditEntryHisJdbcDao;
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
public class CreditEntryHisJdbcService extends BaseJdbcService implements
		ProcessCallBack
{

	@Autowired
	private CreditEntryHisJdbcDao creditEntryHisJdbcDao;

	public void setCreditEntryHisJdbcDao(CreditEntryHisJdbcDao creditHisEntryJdbcDao)
	{
		this.creditEntryHisJdbcDao = creditHisEntryJdbcDao;
	}

	@Autowired
	private CreditHisInfoHibernateDao creditHisInfoHibernateDao;

	public void setCreditHisInfoHibernate(CreditHisInfoHibernateDao creditHisInfoHibernateDao)
	{
		this.creditHisInfoHibernateDao = creditHisInfoHibernateDao;
	}

	@Autowired
	private CreditEntryHibernateDao creditEntryHibernateDao;

	public void setCreditEntryHibernateDao(CreditEntryHibernateDao creditEntryHibernateDao)
	{
		this.creditEntryHibernateDao = creditEntryHibernateDao;
	}
	
	@Autowired
	private CreditEntryHibernateService creditEntryHibernateService;

	public void setCreditEntryHibernateService(CreditEntryHibernateService creditEntryService)
	{
		this.creditEntryHibernateService = creditEntryService;
	}



	/**
	 * 提交流程
	 * 
	 * @param creditInfo
	 * @return
	 */
	public void submitWorkflow(String taskId, TCreditHisInfo creditHisInfo)
	{
		/**流程新增信用证类型 modify by cat 20131114*/
		String wfModelName = "信用证开证改证申请";
		if("1".equals(Constants.getPurTradeType(creditHisInfo.getTradeType()))) wfModelName = "进口信用证开证改证申请";
		else if ("2".equals(Constants.getPurTradeType(creditHisInfo.getTradeType()))) wfModelName = "国内信用证开证改证申请";
		creditHisInfo.setWorkflowModelName(wfModelName);
		/***modify by cat 20131114**/
		
		creditHisInfo.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("credit_application_modify_v1"));
		
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(creditHisInfo, creditHisInfo.getCreditHisId());
			this.creditEntryHisJdbcDao.submitUpdateState(creditHisInfo.getCreditId(), creditHisInfo.getCreditHisId());
		}
		else
		{
			WorkflowService.signalProcessInstance(creditHisInfo, creditHisInfo.getCreditHisId(), null);
		}
	}

	/**
	 * 提交审批流程
	 * 
	 * @param creditInfo
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitWorkflow(TCreditHisInfo creditHisInfo, String taskId)
	{
		JSONObject jo = new JSONObject();
		String msg = "";
		try
		{
			submitWorkflow(taskId, creditHisInfo);
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
	 * 回调更新信用证开证改证 状态 状态:1、信用证开证 2、信用证收证 3、审批中
	 * 4、生效5、备用6、过期7、撤销8、关闭9、作废10、改证11、改证通过 12、改正不通过
	 */
	public void updateBusinessRecord(String businessRecordId, String examineResult, String sapOrderNo)
	{
		String result = "";
		String creditId = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			result = "11";
		}
		else if (ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
		{
			result = "5";
		}
		this.creditEntryHisJdbcDao.workflowUpdateState(businessRecordId, examineResult, result, creditId);

		// 如果改正通过则更新改证数据到信用证主信息表。
		if ("11".equals(result))
		{
			// LJX 20090416 Modify 开始执行变更信息。 从信用证改证历史表 更新到 信用证主表。
			String oldContractId = "";
			String contractId = "";
			boolean isUpadate = false;
			
			TCreditInfo creditInfo = new TCreditInfo();// this.creditEntryHibernateDao.get(creditId)
			TCreditHisInfo creditHisInfo = this.creditHisInfoHibernateDao.get(businessRecordId);
			
			oldContractId = this.creditEntryHisJdbcDao.getoldContractId(creditHisInfo.getCreditId());
			contractId = creditHisInfo.getContractId();
			//不更新信用证开证时间
			//String createDate = creditInfo.getCreateDate();
			BeanUtils.copyProperties(creditHisInfo, creditInfo);
			//creditInfo.setCreateDate(createDate);
			this.creditEntryHibernateDao.update(creditInfo);
						
			if(contractId.equals(oldContractId)==false)
			{
				isUpadate = true;
			}
			
			if(isUpadate) //是否要更新 信用证合同关联表
			{
				int n = 0;
				String[] contractIdLst;
				String creator = creditInfo.getCreator();
				contractIdLst = contractId.split(",");
				n = contractIdLst.length;
				creditId = creditInfo.getCreditId();
							
				//信用证开证改证。
				// 删除信用证开证表数据，重新插入数据
				this.creditEntryHibernateService.updateCreditCreate(creditId, creator, n, contractIdLst);
				System.out.println("信用证开证改证，审批通过后，重新更新信用证开证表数据成功！");

			}
   
		}
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
			if (operate.equals("change"))
			{
				if (userContext.getSysUser().getUserId().equals(creator))
				{
					submitHidden = false;
					saveDisabled = false;
					revisable = true;
				}
				else
				{
					submitHidden = true;
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
		sb.append(" order by t.VERSION_NO desc");
		return sb.toString();
	}

	/**
	 * 根据信用证开证改证ID,取得信用证ID号
	 * 
	 * @param creditHisId
	 * @return 信用证ID号
	 */
	public String getCreditId(String creditHisId)
	{
		String creditId = "";
		creditId = this.creditEntryHisJdbcDao.getCreditId(creditHisId);
		return creditId;
	}
}
