/*
 * @(#)ChangeProjectHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.project.dao.ChangeProjectHibernate;
import com.infolion.sapss.project.domain.TProjectChange;
@Service
public class ChangeProjectHibernateService extends BaseHibernateService{
	@Autowired
	private ChangeProjectHibernate changeProjectHibernate;
	public void setChangeProjectHibernate(
			ChangeProjectHibernate changeProjectHibernate) {
		this.changeProjectHibernate = changeProjectHibernate;
	}
	/**
	 * 保存变更信息
	 * @param tc
	 */
	public String saveChage(TProjectChange tc) {
		String id = CodeGenerator.getUUID();
		tc.setChangeId(id);
		this.changeProjectHibernate.save(tc);
		return id;
	}
	/**
	 * 查找变更记录
	 * @param changeId
	 * @return
	 */
	public TProjectChange findChange(String changeId) {
		return this.changeProjectHibernate.get(changeId);
	}
	/**
	 * 查找立项变更
	 * @param projectId
	 * @return
	 */
	public List<TProjectChange> findChangeByProjectId(String projectId,String changeState) {
		String hql = "from TProjectChange where projectId='"+projectId+"'";
		if(changeState!=null && !"".equals(changeState))
			hql = hql + " and changeState='"+changeState+"'";
		return this.changeProjectHibernate.find(hql);
	}
}
