/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-9-11
 *  描　述：创建
 */

package com.infolion.sapss.project.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.project.dao.ApplyCustomerCreditHibernate;
import com.infolion.sapss.project.dao.ApplyProviderCreditHibernate;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TApplyCustomerCredit;
import com.infolion.sapss.project.domain.TApplyProviderCredit;
import com.infolion.sapss.project.domain.TProjectInfo;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ProjectHibernateService extends BaseHibernateService {
	@Autowired
	private ProjectInfoHibernate projectInfoHibernate;

	public void setProjectInfoHibernate(ProjectInfoHibernate sampleDao) {
		this.projectInfoHibernate = sampleDao;
	}
	
	@Autowired
	private ApplyCustomerCreditHibernate applyCustomerCreditHibernate;
	public void setApplyCustomerCreditHibernate(ApplyCustomerCreditHibernate applyCustomerCreditHibernate)
	{
		this.applyCustomerCreditHibernate = applyCustomerCreditHibernate;
	}
	
	@Autowired
	private ApplyProviderCreditHibernate applyProviderCreditHibernate;
	public void setApplyProviderCreditHibernate(ApplyProviderCreditHibernate applyProviderCreditHibernate)
	{
		this.applyProviderCreditHibernate = applyProviderCreditHibernate;
	}

	/**
	 * 保存立项主信息
	 * @param projectInfo
	 * @return
	 */
	@Transactional(readOnly = true)
	public String saveProjectInfo(TProjectInfo projectInfo) {
		String id = CodeGenerator.getUUID();
		projectInfo.setProjectId(id);
		this.projectInfoHibernate.save(projectInfo);
		return id;
	}
	/**
	 * 修改立项主信息
	 * @param projectInfo
	 * @return
	 */
	@Transactional
	public void saveOrUpdateProjectInfo(TProjectInfo projectInfo) {
		this.projectInfoHibernate.saveOrUpdate(projectInfo);
	}
	
	public TProjectInfo getProjectInfo(String id) {
		TProjectInfo o = this.projectInfoHibernate.get(id);		
		return o;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TProjectInfo getTProjectInfo(String id) {
		TProjectInfo o = this.projectInfoHibernate.get(id);
		this.projectInfoHibernate.evict(o);
		return o;
	}
	
	/**
	 * 保存客户信用额度
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = true)
	public void saveOrUpdateCustCredit(TApplyCustomerCredit vo){	
		this.applyCustomerCreditHibernate.saveOrUpdate(vo);
	}
	
	/**
	 * 保存客户信用额度
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = true)
	public void saveOrUpdateProvidCredit(TApplyProviderCredit vo){
		this.applyProviderCreditHibernate.saveOrUpdate(vo);
	}
}
