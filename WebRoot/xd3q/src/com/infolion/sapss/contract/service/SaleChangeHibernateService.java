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
import com.infolion.sapss.contract.dao.SaleChangeHibernate;
import com.infolion.sapss.contract.domain.TPurchaseChange;
import com.infolion.sapss.contract.domain.TSalesChange;
@Service
public class SaleChangeHibernateService extends BaseHibernateService{
	@Autowired
	private SaleChangeHibernate saleChangeHibernate;
	public void setChangeProjectHibernate(
			SaleChangeHibernate saleChangeHibernate) {
		this.saleChangeHibernate = saleChangeHibernate;
	}
	/**
	 * 保存变更信息
	 * @param tc
	 */
	public String saveChage(TSalesChange tc) {
		String id = CodeGenerator.getUUID();
		tc.setChangeId(id);
		this.saleChangeHibernate.save(tc);
		return id;
	}
	/**
	 * 查找变更记录
	 * @param changeId
	 * @return
	 */
	public TSalesChange findChange(String changeId) {
		String hql="from TSalesChange t where t.changeId=?";
		List<TSalesChange> list = this.saleChangeHibernate.find(hql,new String[]{changeId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return null; 
		
	}
	/**
	 * 查找合同变更
	 * @param projectId
	 * @return
	 */
	public List<TSalesChange> findChangeBySaleId(String saleId,String changeState) {
		String hql = "from TSalesChange where contractSalesId='"+saleId+"'";
		if(changeState!=null && !"".equals(changeState))
			hql = hql + " and changeState='"+changeState+"'";
		return this.saleChangeHibernate.find(hql);
	}
}
