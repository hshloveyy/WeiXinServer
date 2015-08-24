/*
 * @(#)AttachementService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：
 *  时　间：2009-1-5
 *  描　述：创建
 */

package com.infolion.platform.component.fileProcess.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.fileProcess.dao.AttachementBusinessJdbcDao;
import com.infolion.platform.component.fileProcess.domain.TAttachementBusiness;
import com.infolion.platform.component.fileProcess.domain.TAttachementLog;
import com.infolion.platform.core.service.BaseService;
@Service
public class AttachementBusinessJdbcService extends BaseService {
	@Autowired
	private AttachementBusinessJdbcDao attachementBusinessJdbcDao;
	/**
	 * 增加一条附件记录
	 * @param attachement
	 */
	public String saveAttachementBusiness(TAttachementBusiness attachement){
		if(!attachementBusinessJdbcDao.isExist(attachement))
			return attachementBusinessJdbcDao.saveTAttachementBusiness(attachement);
		else
			return attachementBusinessJdbcDao.getKey(attachement);
	}
	/**
	 * 删除附件记录(单条)
	 * @param fileName
	 * @return
	 */
	public int delAttachement(String fileName){
		return attachementBusinessJdbcDao.delAttachement(fileName);
	}
	public AttachementBusinessJdbcDao getAttachementBusinessJdbcDao() {
		return attachementBusinessJdbcDao;
	}
	public void setAttachementBusinessJdbcDao(
			AttachementBusinessJdbcDao attachementBusinessJdbcDao) {
		this.attachementBusinessJdbcDao = attachementBusinessJdbcDao;
	}
	public int fileUploadCheck(String filename,String userId,int type){
		return attachementBusinessJdbcDao.fileUploadCheck(filename,userId,type);
	}
	
	public int fileUploadModifyCheck(String recordId){
		return attachementBusinessJdbcDao.fileUploadModifyCheck(recordId);
	}
	public int attachementLogRecord(TAttachementLog log){
		return attachementBusinessJdbcDao.attachementLogRecord(log);
	}
}
