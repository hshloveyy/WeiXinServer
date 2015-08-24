/*
 * @(#)JdPurchaseJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.storageProtocol.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.project.domain.TProjectChange;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocol;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocolChange;
@Repository
public class StorageProtocolJdbcDao extends BaseDao{
	/**
	 * 删除
	 * @param purchaseId
	 * @return
	 */
	public int delInfo(String id) {
		String sql ="update t_storage_protocol set is_available='0' where info_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{id});
	}
	/**
	 * 更新状态
	 * @param ts
	 * @param string
	 * @param b
	 */
	public void updateState(TStorageProtocol ts, String status, boolean b) {
		if(!b){
			String sql="update t_storage_protocol set status=? where info_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,ts.getInfoId()});
		}else{
			String sql="update t_storage_protocol set status=?,approved_time=? where info_id=?";
			this.getJdbcTemplate().update(sql, new Object[]{status,DateUtils.getTimeStr(new Date(), 2),ts.getInfoId()});
		}
	}
	
	public void deleteContract(String storageProtocolId){
		this.getJdbcTemplate().update("delete from T_STORAGE_PROTOCOL_C where STORAGE_PROTOCOL_ID='"+storageProtocolId+"'");
	}
	
	public int getChangeNo(String infoId) {
		String sql = "select max(change_no) from T_STORAGEPROTOCOL_CHANGE where info_id=?";
		return this.getJdbcTemplate().queryForInt(sql, new Object[]{infoId});
	}
	
	public int updateChange(TStorageProtocolChange tc) {
		String sql = "update T_STORAGEPROTOCOL_CHANGE set change_desc=?  where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{tc.getChangeDesc(),tc.getChangeId()});
	}
	
	public int deleteChange(String changeId) {
		String sql = "update T_STORAGEPROTOCOL_CHANGE set is_available='0' where change_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{changeId});
	}
	
	public int submitUpdateChangeState(String changeId,String state) {
		String sql = "update T_STORAGEPROTOCOL_CHANGE set change_state=? where change_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{state,changeId});
		
	}
	
	public int submitUpdateInfoState(String projectId,String state){
		String sql = "update T_STORAGE_PROTOCOL set status=? where info_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{state,projectId});
	}
	
	
	public int workflowChangeUpdateState(String businessRecordId,String examineResult) {
		String sql = "update T_STORAGEPROTOCOL_CHANGE set change_state=? , approved_time=? where change_id=?";
		String result="";
		if("pass".equals(examineResult))
			result="4";
		else
			result="5";
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
		
	}
	
	public int workflowChangeUpdatePState(String businessRecordId,String examineResult) {
		String sql = "update T_STORAGE_PROTOCOL set status=?  where info_id=?";
		String result="";
		if("pass".equals(examineResult))
			result="8";
		else
			result="3";
		return this.getJdbcTemplate().update(sql, new Object[]{result,businessRecordId});
	}
	
	public void fileProtocol(String infoId){
		String sql = "update T_STORAGE_PROTOCOL set KEEP_FLAG=1  where info_id=?";
		this.getJdbcTemplate().update(sql, new Object[]{infoId});		
	}

}
