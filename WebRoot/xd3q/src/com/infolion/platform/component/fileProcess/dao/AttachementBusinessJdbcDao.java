/*
 * @(#)AttachementBusinessJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-9
 *  描　述：创建
 */

package com.infolion.platform.component.fileProcess.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.component.fileProcess.domain.TAttachementBusiness;
import com.infolion.platform.component.fileProcess.domain.TAttachementLog;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
@Repository
public class AttachementBusinessJdbcDao extends BaseDao {
	
	/**
	 * 增加流程实例的附件
	 * @param in_sysWfProcessAccessory
	 */
	public String saveTAttachementBusiness(TAttachementBusiness ab){
		String strSql ="insert into T_ATTACHEMENT_BUSINESS "+
						  "(ATTACHEMENT_BUSINESS_ID, " +
						   "RESOURCE_ID, " +
						   "RESOURCE_NAME, " +
						   "RECORD_ID, " +
						   "CREATOR_TIME, " +
						   "CREATOR) " +
						"values " +
						  "(?,?,?,?,?,?)";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String key = CodeGenerator.getUUID();
		Object[] insertparams = new Object[]{
				key,
				ab.getResourceId(),
				ab.getResourceName(),
				ab.getRecordId(),
				ab.getCreatorTime(),
				ab.getCreator()
			};
			
		getJdbcTemplate().update(strSql,insertparams);
		return key;
	}
	/**
	 * 判断是否存在
	 * @param ab
	 * @return
	 */
	public boolean isExist(TAttachementBusiness ab){
		String sql = "select count(*) from T_ATTACHEMENT_BUSINESS where RESOURCE_ID=? and RESOURCE_NAME=? and RECORD_ID=?";
		int i = this.getJdbcTemplate().queryForInt(sql, new Object[]{ab.getResourceId(),ab.getResourceName(),ab.getRecordId()});
		return i!=0;
	}
	
	public int fileUploadCheck(String filename,String userId,int type){
		filename = (type==2 ? filename.substring(0, filename.lastIndexOf(".")) : filename);
		String sql = "select count(*) From t_attachement  where old_name=? and creator=? and creator_time >?";
		String sql1 = "select count(*) From yattachement  where filename like '%"+filename+"%' and creator=? and createtime >?";
		if(1==type){
			int i = this.getJdbcTemplate().queryForInt(sql, new Object[]{filename,userId,
					DateUtils.add(DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), DateUtils.DB_STORE_DATE, -5, Calendar.SECOND)});
			return i;
		}else {
			int i = this.getJdbcTemplate().queryForInt(sql1, new Object[]{userId,
					DateUtils.add(DateUtils.getCurrTime(DateUtils.DB_STORE_DATE), DateUtils.DB_STORE_DATE, -5, Calendar.SECOND)});
			return i;
		}
	}
	
	public int fileUploadModifyCheck(String recordId){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String username = userContext.getSysUser().getUserName();
		if("libq".equals(username)||"E3C96C9E-DFF8-4DD2-BB6A-E59162ADA65D".equals(userContext.getSysDept().getDeptid())) return 1;
		else{
			String sql = "select usernames from t_attachement_modcf where recordid=?";
			List list = this.getJdbcTemplate().queryForList(sql, new Object[]{recordId});
			if(list.isEmpty()||list.size()==0) return 0;
			if(((Map)list.get(0)).get("USERNAMES") ==null) return 0;
			String usernames = (String)((Map)list.get(0)).get("USERNAMES");
			if((usernames).indexOf(","+username+",")>=0) return 1;
			return 0;
		}
	}
	/**
	 * 获取key
	 * @param ab
	 * @return
	 */
	public String getKey(TAttachementBusiness ab){
		String sql = "select ATTACHEMENT_BUSINESS_ID from T_ATTACHEMENT_BUSINESS where RESOURCE_ID=? and RESOURCE_NAME=? and RECORD_ID=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[]{ab.getResourceId(),ab.getResourceName(),ab.getRecordId()});
		return (String)((Map)list.get(0)).get("ATTACHEMENT_BUSINESS_ID");
	}

	/**
	 * 删除流程实例的附件信息(单条)
	 * @param in_accessoryIdList
	 */
	public int delAttachement(String fileName){
		String sql = "delete t_attachement t where t.read_name=?";
		return getJdbcTemplate().update(sql, new Object[]{fileName});
	}
	
	public int attachementLogRecord(TAttachementLog log){
		String strSql ="insert into t_attachement_log "+
		  "(ATTACHEMENT_LOG_ID, " +
		  "ATTACHEMENT_ID, " +
		  "OLD_NAME, " +
		  "READ_NAME, " +
		  "ATTACHEMENT_CMD, " +
		  "NEW_OLD_NAME, " +
		  "NEW_READ_NAME, " +
		  "NEW_ATTACHEMENT_CMD, " +
		  "OPERATE_TIME, " +
		  "OPERATER, " +
		  "MACK, " +
		  "ATTACHEMENT_BUSINESS_ID, " +
		  "UPLOAD_TIME, " +
		  "CREATOR_TIME, " +
		  "CMD, " +
		  "CREATOR) " +
		"values " +
		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String key = CodeGenerator.getUUID();
		Object[] insertparams = new Object[]{
				key,
				log.getAttachementId(),
				log.getOldName(),
				log.getReadName(),
				log.getAttachementCmd(),
				log.getNewOldName(),
				log.getNewReadName(),
				log.getNewAttachementCmd(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
				log.getOperater(),
				log.getMack(),
				log.getAttachementBusinessId(),
				log.getUploadTime(),
				log.getCreatorTime(),
				log.getCmd(),
				log.getCreator()
		};

		return getJdbcTemplate().update(strSql,insertparams);
	}
}
