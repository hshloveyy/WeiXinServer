/*
 * @(#)CreaditEntryJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-13
 *  描　述：创建
 */

package com.infolion.sapss.credit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;

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
public class CreditArriveJdbcDao extends BaseDao
{

	/**
	 * 根据ID删除信用证表数据(T_CREDIT_INFO)
	 * 
	 * @param creditId
	 * @return
	 */
	public int delete(String creditId)
	{
		String sql = "update T_CREDIT_INFO set is_available='0' where credit_id=? and credit_state in('2')";

		return this.getJdbcTemplate().update(sql, new Object[] { creditId });
	}

	/**
	 * 根据ID信用证开证表数据(T_CREDIT_CREATE)
	 * 
	 * @param creditId
	 * @return
	 */
	public int deleteCreditRec(String creditId)
	{
		String sql = "delete T_CREDIT_REC where credit_id=?";

		return this.getJdbcTemplate().update(sql, new Object[] { creditId });
	}

	/**
	 * 提交流程审批后更新状态 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、改证 6、撤销 7、作废
	 * 
	 * @param creditId
	 */
	public int submitUpdateState(String creditId)
	{
		String sql = "update T_CREDIT_INFO set credit_state=?,apply_time=? where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[] { "3", DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), creditId });
	}

	/**
	 * 流程审批后更新信用证状态 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、改证 6、撤销 7、作废
	 * 
	 * @param projectInfo
	 * @return
	 */
	public int workflowUpdateState(String businessRecordId, String examineResult, String result)
	{
		String sql = "update T_CREDIT_INFO set credit_state=? , approved_time=? where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[] { result, DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), businessRecordId });
	}

	/**
	 * 信用证改证状态更新
	 * 
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowChangeUpdateState(String businessRecordId, String examineResult)
	{
		String sql = "update T_CREDIT_INFO set credit_state=? , approved_time=? where CREDIT_ID=?";
		String result = "";
		if ("pass".equals(examineResult))
			result = "8"; // 改证通过
		else
			result = "4"; // 生效
		return this.getJdbcTemplate().update(sql, new Object[] { result, DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), businessRecordId });
	}
	
	/**
	 * 当修改历史超过2笔数据时候，更新原来的数据的变更状态为4
	 * @param creditId
	 */
	public void updateCreditHisInfoState(String creditId)
	{
		   String sql = "update t_credit_his_info set CHANGE_STATE='4' where CREDIT_ID=? and VERSION_NO in(SELECT MAX(VERSION_NO) from  t_credit_his_info where CREDIT_ID=?)";
	       this.getJdbcTemplate().update(sql, new Object[] { creditId, creditId });
	}
	

	/**
	 * 根据信用证ID取得信用证收证修改历史记录(最高版本)，最新数据。
	 * 
	 * @param creditId
	 * @return credit_his_id,信用证历史信息ID
	 */
	public String getCreditHisId(String creditId)
	{
		//String sql = "select t.credit_his_id from t_credit_his_info t where t.CREDIT_ID = ? and t.VERSION_NO = (select  max(b.VERSION_NO) aa  from t_credit_his_info b where b.CREDIT_ID=?) and t.is_available=1 and t.change_state in (0,4)";
		String sql = "select t.credit_his_id from t_credit_his_info t where t.CREDIT_ID = ? and t.VERSION_NO = (select  max(b.VERSION_NO) aa  from t_credit_his_info b where b.CREDIT_ID=? and b.VERSION_NO>0)";

		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditId, creditId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("credit_his_id");
		}
		else
			return "";
	}
	
	/**
	 * 根据信用证ID,取得该信用证的最高历史版本号
	 * @param creditId
	 * @return
	 */
	public String getMaxVersionNo(String creditId)
	{
		String sql = "select  max(b.VERSION_NO) VERSION_NO  from t_credit_his_info b where b.CREDIT_ID=?";
        String vresult ="" ; 
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { creditId });
		if (list != null && list.size() > 0)
		{
			Object aa =((Map) list.get(0)).get("VERSION_NO") + "";

			if(org.apache.commons.lang.StringUtils.isBlank(aa.toString()))
			{
				vresult =  aa.toString() ;
			}
			else
			{
				vresult =  "0";
			}
		}
		else
		{
			vresult =  "0";
		}
		
		return vresult;
	}
	/**
	 * 
	 * @param creditId
	 * @param status
	 */
	public void saveCreditStatus(String creditId, String status) {
		String sql="update T_CREDIT_INFO set credit_state=? where credit_id=?";
		//String sql1="update t_credit_his_info set credit_state=? where credit_id=? and version_no=(select max(version_no) from t_credit_his_info where credit_id=?)";
		this.getJdbcTemplate().update(sql, new Object[]{status,creditId});
		//this.getJdbcTemplate().update(sql1, new Object[]{status,creditId,creditId});
	}
	
	
	public boolean isExistCreditNo(String creditId,String creditNo){
		String sql;
		if(StringUtils.isNullBlank(creditId)){
			sql = "select count(*) from t_credit_info where credit_no='"+creditNo+"' and is_available='1'";
		}
		else sql = "select count(*) from t_credit_info where credit_id <>'"+creditId+"' and credit_no='"+creditNo+"' and is_available='1'";
		int i = getJdbcTemplate().queryForInt(sql);
		if(i>0) return true;
		else return false;
	}

}
