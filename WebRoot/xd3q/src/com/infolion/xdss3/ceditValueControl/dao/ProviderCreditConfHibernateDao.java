/*
 * @(#)ProviderCreditConfHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点07分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.xdss3.ceditValueControl.domain.OtherChangeRecord;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControlGen.dao.ProviderCreditConfHibernateDaoGen;


/**
 * <pre>
 * 供应商信用额度配置(ProviderCreditConf),HibernateDao 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class ProviderCreditConfHibernateDao extends ProviderCreditConfHibernateDaoGen<ProviderCreditConf>
{
	public int updatePrepayment(String providerid,String projectid, double prepayment, String remark)
	{
		String hql = "from ProviderCreditConf providerCreditConf where providerid = ?";
		
		List<ProviderCreditConf> providerCreditConf = this.find(hql,providerid);
		for(  ProviderCreditConf conf : providerCreditConf )
		{
			BigDecimal oldValue = BigDecimal.valueOf(0.0);
			
			hql = "from ProviderCreditProj providerCreditProj where projectid = '"
				  + projectid + 
				  "' and providerCreditConf.configid= '" + conf.getConfigid() + "'";
			
			List<ProviderCreditProj> providerCreditProj = this.find(hql);
			if (providerCreditProj.size() < 1) {
			    continue;
			}
			
			oldValue = ((ProviderCreditProj)providerCreditProj.get(0)).getOtherprepayvalue();
			
			BigDecimal newValue = oldValue.add(BigDecimal.valueOf(prepayment));
			
			((ProviderCreditProj)providerCreditProj.get(0)).setOtherprepayvalue(newValue);
			this.getHibernateTemplate().update(providerCreditProj.get(0));
			
			// 更新成功后，插入变更记录
			OtherChangeRecord otherChangeRecord = new OtherChangeRecord();
			otherChangeRecord.setNewvalue(new BigDecimal(prepayment)); //记录发生额
			otherChangeRecord.setOldvalue(oldValue);
			otherChangeRecord.setUserid(providerid);
			otherChangeRecord.setUsertype("3"); // 供应商类型为"3"
			otherChangeRecord.setRemark(remark);
			otherChangeRecord.setProjectid(projectid);
			this.getHibernateTemplate().save(otherChangeRecord);
				
			return 0;
		}
		return -1;
	}
	
	/*
	public void insertChangeRecord(String providerid, double newValue)
	{
		String hql = "from ProviderCreditConf providerCreditConf where providerid = ?";
		
		List providerCreditConf = this.find(hql,providerid);
		if(providerCreditConf.size() != 0 )
		{
			ProviderCreditConf providerCredititem = (ProviderCreditConf)providerCreditConf.get(0);
			OtherChangeRecord otherChangeRecord = new OtherChangeRecord();
			otherChangeRecord.setNewvalue(BigDecimal.valueOf(newValue));
			otherChangeRecord.setOldvalue(providerCredititem.getOtherprepayvalue());
			otherChangeRecord.setUserid(providerid);
			otherChangeRecord.setUsertype("2"); // 供应商类型为"2"
			this.getHibernateTemplate().save(otherChangeRecord);
			
		}
	}
	*/
}