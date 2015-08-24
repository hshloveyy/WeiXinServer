/*
 * @(#)ChangeProjectHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.contract.dao.PurchaseChangeHibernate;
import com.infolion.sapss.contract.domain.TPurchaseChange;
@Service
public class PurchaseChangeHibernateService extends BaseHibernateService{
	@Autowired
	private PurchaseChangeHibernate purchaseChangeHibernate;
	public void setPurchaseChangeHibernate(
			PurchaseChangeHibernate purchaseChangeHibernate) {
		this.purchaseChangeHibernate = purchaseChangeHibernate;
	}
	/**
	 * 保存变更信息
	 * @param tc
	 */
	public String saveChage(TPurchaseChange tc) {
		String id = CodeGenerator.getUUID();
		tc.setChangeId(id);
		this.purchaseChangeHibernate.save(tc);
		return id;
	}
	/**
	 * 查找变更记录
	 * @param changeId
	 * @return
	 */
	public TPurchaseChange findChange(String changeId) {
		String hql="from TPurchaseChange t where t.changeId=?";
		List<TPurchaseChange> list = this.purchaseChangeHibernate.find(hql,new String[]{changeId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return null; 
	}
	/**
	 * 查找合同变更
	 * @param projectId
	 * @return
	 */
	public List<TPurchaseChange> findChangeByPurchaseId(String purchaseId,String changeState) {
		String hql = "from TPurchaseChange where contractPurchaseId='"+purchaseId+"'";
		if(changeState!=null && !"".equals(changeState))
			hql = hql + " and changeState='"+changeState+"'";
		return this.purchaseChangeHibernate.find(hql);
	}
}
