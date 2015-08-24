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

import com.infolion.platform.component.fileProcess.dao.AttachementHibernate;
import com.infolion.platform.component.fileProcess.domain.TAttachement;
import com.infolion.platform.core.service.BaseHibernateService;
@Service
public class AttachementHibernateService extends BaseHibernateService {
	@Autowired
	private AttachementHibernate attachementHibernate;
	/**
	 * 增加一条附件记录
	 * @param attachement
	 */
	public void saveAttachement(TAttachement attachement){
		attachementHibernate.save(attachement);
	}
	public void saveOrUpdateAttachement(TAttachement attachement){
		attachementHibernate.saveOrUpdate(attachement);
	}
	/**
	 * 删除附件记录(单条)
	 * @param fileName
	 * @return
	 */
	public void delAttachement(TAttachement attachement){
		attachementHibernate.remove(attachement);
	}
	
	public TAttachement getAttachement(String id){
		return attachementHibernate.get(id);
	}
	
	public AttachementHibernate getAttachementHibernate() {
		return attachementHibernate;
	}
	public void setAttachementHibernate(AttachementHibernate attachementHibernate) {
		this.attachementHibernate = attachementHibernate;
	}
}
