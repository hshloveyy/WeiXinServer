/*
 * @(#)AttachementJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-12
 *  描　述：创建
 */

package com.infolion.platform.component.fileProcess.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.fileProcess.dao.XdssAttachementJdbcDao;
import com.infolion.platform.core.service.BaseJdbcService;
@Service
public class AttachementJdbcService extends BaseJdbcService{
	@Autowired
	private XdssAttachementJdbcDao xdssAttachementJdbcDao;
	/**
	 * 删除附件
	 * @param attachementId
	 * @return
	 */
	public int delAttachement(String attachementId,String userId){
		return this.xdssAttachementJdbcDao.delAttachement(attachementId,userId);
	}
	
	public XdssAttachementJdbcDao getAttachementJdbcDao() {
		return xdssAttachementJdbcDao;
	}

	public void setAttachementJdbcDao(XdssAttachementJdbcDao xdssAttachementJdbcDao) {
		this.xdssAttachementJdbcDao = xdssAttachementJdbcDao;
	}
}
