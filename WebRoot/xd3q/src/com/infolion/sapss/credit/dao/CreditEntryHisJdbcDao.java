/*
 * @(#)CreaditEntryJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-7
 *  描　述：创建
 */

package com.infolion.sapss.credit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.credit.domain.TCreditHisInfo;
import com.infolion.sapss.credit.domain.TCreditInfo;

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
@Repository
public class CreditEntryHisJdbcDao extends BaseDao
{
	/**
	 * 提交信用证改证流程审批后信用证主信息、信用证改证信息更新状态为改证 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、备用 6、过期
	 * 7、撤销 8、关闭 9、作废 10、改证 11、改证通过 12、改正不通过
	 * 
	 * @param creditId
	 * @param creditHisId
	 */
	public void submitUpdateState(String creditId, String creditHisId)
	{
		int i = 0;
		int j = 0;
		i = submitUpdateTCreditInfoState(creditId);
		j = submitUpdateTCreditHisInfoState(creditHisId);
	}

	/**
	 * 提交信用证改证流程审批后信用证主信息更新状态为改证 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、备用 6、过期 7、撤销
	 * 8、关闭 9、作废 10、改证 11、改证通过 12，改正不通过
	 * 
	 * @param creditId
	 * @return
	 */
	public int submitUpdateTCreditInfoState(String creditId)
	{
		String sql = "update T_CREDIT_INFO set credit_state=? where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[] { "10", creditId });
	}

	/**
	 * 提交信用证改证流程审批后信用证主信息更新状态为改证 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、备用 6、过期 7、撤销
	 * 8、关闭 9、作废 10、改证 11、改证通过 变更状态:1、变更申请2、变更审批中3、变更待执行4、变更已执行5、变更作废
	 * 
	 * @param creditId
	 * @return
	 */
	public int submitUpdateTCreditHisInfoState(String creditHisId)
	{
		String sql = "update T_CREDIT_HIS_INFO set credit_state=?,change_state=?,apply_time=? where CREDIT_HIS_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[] { "10", "2", DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), creditHisId });
	}

	/**
	 * 流程审批后更新信用证状态 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、备用 6、过期 7、撤销 8、关闭 9、作废 10、改证
	 * 11、改证通过
	 * 
	 * @param projectInfo
	 * @return creditId
	 */
	public void workflowUpdateState(String businessRecordId, String examineResult, String result, String creditId)
	{
		String sql = "";
		creditId = getCreditId(businessRecordId);
		String changeState = "";

		if ("11".equals(result))
		{
			changeState = "4";
		}
		else
		{
			changeState = "5";
		}

		// 更新信用证改证历史表 状态
		sql = "update T_CREDIT_HIS_INFO set credit_state=?,change_state=?,CHANGE_EXEC_TIME=?,approved_time=?,IS_EXECUTED=? where CREDIT_ID=? and CREDIT_HIS_ID=?";
		this.getJdbcTemplate().update(sql, new Object[] { result, changeState, DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), "1", creditId, businessRecordId }); // 4，变更已经执行

		// 更新信用证主表 状态
		sql = "update T_CREDIT_INFO set credit_state=? where CREDIT_ID=?";
		this.getJdbcTemplate().update(sql, new Object[] { result, creditId });

	}

	/**
	 * 根据信用证开证改证ID,取得信用证ID号
	 * 
	 * @param creditHisId
	 * @return 信用证ID号
	 */
	public String getCreditId(String creditHisId)
	{
		String sql = "select CREDIT_ID from t_credit_his_info t where t.credit_his_id=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditHisId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("CREDIT_ID");
		}
		else
			return "";
	}

	/**
	 * 根据信用证ID取得信用证修改历史记录(最高版本)，最新的，有效数据。
	 * 
	 * @param creditId
	 * @return credit_his_id,信用证历史信息ID
	 */
	public String getCreditHisId(String creditId)
	{
		//String sql = "select t.credit_his_id from t_credit_his_info t where t.CREDIT_ID = ? and t.VERSION_NO = (select  max(b.VERSION_NO) aa  from t_credit_his_info b where b.CREDIT_ID=?) and t.is_available=1 and t.change_state in (0,4)";
		String sql = "select t.credit_his_id from t_credit_his_info t where t.CREDIT_ID = ? and t.VERSION_NO = (select  max(b.VERSION_NO) aa  from t_credit_his_info b where b.CREDIT_ID=?)";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditId, creditId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("credit_his_id");
		}
		else
			return "";
	}

	/**
	 * 根据信用证ID取得信用证开证日期
	 * 
	 * @param creditId
	 * @return
	 */
	public String getCreateDate(String creditId)
	{
		String sql = "select t.CREATE_DATE from t_credit_info t where t.CREDIT_ID = ?";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("CREATE_DATE");
		}
		else
			return "";
	}
	
	
	/**
	 * 根据信用证ID取得信用证开证日期
	 * 
	 * @param creditId
	 * @return
	 */
	public String getCreateDateByHisId(String creditHisId)
	{
		String sql = "select t.CREATE_DATE from t_credit_His_info t where t.CREDIT_HIS_ID = ?";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditHisId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("CREATE_DATE");
		}
		else
			return "";
	}
	
	/**
	 * 根据信用证历史ID取得信用证创建者
	 * 
	 * @param creditId
	 * @return
	 */
	public String getCreator(String creditHisId)
	{
		String sql = "select t.CREATOR from t_credit_His_info t where t.CREDIT_His_ID = ?";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditHisId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("CREATOR");
		}
		else
			return "";
	}
	
	/**
	 * 通过creditID取得信用证表CONTRACT_ID 字段。
	 * @param creditId
	 * @return
	 */
	public String getoldContractId(String creditId)
	{
		String sql = "SELECT t.CONTRACT_ID FROM T_CREDIT_INFO t WHERE t.CREDIT_ID = ?";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("CONTRACT_ID");
		}
		else
			return "";
	}
	
}
