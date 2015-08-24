/*
 * @(#)PurchaseChangeJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.contract.domain.TPurchaseChange;
import com.infolion.sapss.workflow.ProcessCallBack;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class PurchaseChangeJdbcDao extends BaseDao{
	/**
	 * 删除变更
	 * @param changeId
	 */
	public int deleteChange(String changeId) {
		String sql = "update T_PURCHASE_CHANGE set is_available='0' where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{changeId});
	}
	/**
	 * 更新变更信息
	 * @param tc
	 * @return
	 */
	public int updateChange(TPurchaseChange tc) {
		String sql = "update T_PURCHASE_CHANGE set change_desc=?,currency=?,amount=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{tc.getChangeDesc(),tc.getCurrency(),tc.getAmount(),tc.getChangeId()});
	}
	/**
	 * 提交审批时更新记录状态
	 * @param changeId
	 */
	public int submitUpdateState(String changeId,String state) {
		String sql = "update T_PURCHASE_CHANGE set change_state=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{state,changeId});
		
	}
	/**
	 * 保存信息中心执行信息
	 * @param tpc
	 * @return
	 */
	public int saveChanged(TPurchaseChange tpc) {
		String sql ="update T_PURCHASE_CHANGE set change_operator=?,operate_time=?,is_changed=?,change_note=? where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{tpc.getChangeOperator(),
				tpc.getOperateTime(),tpc.getIsChanged(),tpc.getChangeNote(),tpc.getChangeId()});
	}
	/**
	 * 返回批次号
	 * @param projectId
	 * @return
	 */
	public int getChangeNo(String bizId,String changeState) {
		String sql = "select change_no from T_PURCHASE_CHANGE where contract_purchase_id=? and change_state=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[]{bizId,changeState});
		if(list!=null)
			return list.size();
		return 0;
	}
	/**
	 * 更新业务记录状态
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowChangeUpdateState(String businessRecordId,String examineResult) {
		String sql = "update T_PURCHASE_CHANGE set change_state=? , approved_time=? where change_id=?";
		String result="";
		if(!ProcessCallBack.EXAMINE_FAILE.equals(examineResult))
			result="4";//变更已执行
		else
			result="5";//变更作废
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
		
	}
	/**
	 * 变更状态更新
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowUpdateState(String businessRecordId,String examineResult) {//, approved_time=?取消更改原合同审批通过时间
		String sql = "update t_contract_purchase_info set order_state=?  where contract_purchase_id=?";
		String result="";
		if(ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
			result="7";//变更通过
		else if(ProcessCallBack.NO_EXAMINE.equals(examineResult))
			result="9";//未经审批写入SAP变更通过
		else
			result="3";//维持不变--审批通过
		return this.getJdbcTemplate().update(sql, new Object[]{result/**,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)**/,businessRecordId});
	}
	
	/**
	 * 取得合同id
	 * @param businessRecordId
	 */
	public String getContractId(String businessRecordId) {
		String sql ="select contract_purchase_id from T_PURCHASE_CHANGE where change_id=?";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{businessRecordId});
		if(list!=null && list.size()>0){
			return (String)((Map)list.get(0)).get("contract_purchase_id");
		}else
			return "";
		
	}
}
