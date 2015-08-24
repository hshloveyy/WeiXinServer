/*
 * @(#)AttachementJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-9
 *  描　述：创建
 */

package com.infolion.platform.component.fileProcess.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
@Repository
public class XdssAttachementJdbcDao extends BaseDao {
	/**
	 * 删除流程实例的附件信息(单条)
	 * @param in_accessoryIdList
	 */
	public int delAttachement(String fileName,String userId){
		String sql = "delete t_attachement t where t.attachement_id=? and t.creator= '"+userId+"'";
		return getJdbcTemplate().update(sql, new Object[]{fileName});
	}
}
