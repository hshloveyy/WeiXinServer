/*
 * @(#)CustomerCreditConfHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分10秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.domain.OtherChangeRecord;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControlGen.dao.CustomerCreditConfHibernateDaoGen;


/**
 * <pre>
 * 客户代垫额度和发货额度配置(CustomerCreditConf),HibernateDao 操作类
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
public class CustomerCreditConfHibernateDao extends CustomerCreditConfHibernateDaoGen<CustomerCreditConf>
{
	/**
	 * 更新客户其他代垫或放货金额
	 * 
	 * @param customerid
	 * @param prepayment
	 * @return 0：更新成功；1:更新失败
	 */
	public int updateCredit(String customerid, String projectid, String valueType, double prepayValue, double sendValue, String remark)
	{
		String hql = "from CustomerCreditConf custCreditConf where customerid = ?";
		
		List<CustomerCreditConf> custCreditConf = this.find(hql,customerid);

		for (CustomerCreditConf conf : custCreditConf)
		{
			BigDecimal oldValue = BigDecimal.valueOf(0.0);
			BigDecimal newValue = BigDecimal.valueOf(0.0);
			BigDecimal oldValue2 = BigDecimal.valueOf(0.0);
			BigDecimal newValue2 = BigDecimal.valueOf(0.0);
			hql = " from CustomerCreditProj customerCreditProj where projectid='"
				+ projectid + "' and customerCreditConf.configid = '"
				+ conf.getConfigid() + "'";
			
			List<CustomerCreditProj> customerCreditProj = this.find(hql);
			if (customerCreditProj.size() < 1) {
			    continue;
			}
			
			if(valueType.equals("1")){	// 若为放货
				oldValue = ((CustomerCreditProj)customerCreditProj.get(0)).getOthersendvalue();			
				newValue = oldValue.add(BigDecimal.valueOf(sendValue));
				((CustomerCreditProj)customerCreditProj.get(0)).setOtherprepayvalue(BigDecimal.valueOf(0));
				((CustomerCreditProj)customerCreditProj.get(0)).setOthersendvalue(newValue);
			}else if(valueType.equals("2")){// 若为代垫
				// 取得旧值
				oldValue = ((CustomerCreditProj)customerCreditProj.get(0)).getOtherprepayvalue();			
				// 计算新值
				newValue = oldValue.add(BigDecimal.valueOf(prepayValue));
				((CustomerCreditProj)customerCreditProj.get(0)).setOtherprepayvalue(newValue);
				((CustomerCreditProj)customerCreditProj.get(0)).setOthersendvalue(BigDecimal.valueOf(0));
			}else{	// 若为代垫+放货
				// 先计算
				oldValue = ((CustomerCreditProj)customerCreditProj.get(0)).getOtherprepayvalue();			
				newValue = oldValue.add(BigDecimal.valueOf(prepayValue));
				((CustomerCreditProj)customerCreditProj.get(0)).setOtherprepayvalue(newValue);
				oldValue2 = ((CustomerCreditProj)customerCreditProj.get(0)).getOthersendvalue();			
				newValue2 = oldValue2.add(BigDecimal.valueOf(sendValue));
				((CustomerCreditProj)customerCreditProj.get(0)).setOthersendvalue(newValue2);
			}
			
			this.getHibernateTemplate().update(customerCreditProj.get(0));
			
			// 更新成功后，插入变更记录
			OtherChangeRecord otherChangeRecord = new OtherChangeRecord();
			if(valueType.equals("1") || valueType.equals("3")){		// 若为放货
				otherChangeRecord.setNewvalue(BigDecimal.valueOf(sendValue));
				// 设置授信类型
				otherChangeRecord.setUsertype("1");
			}else if(valueType.equals("2")){						// 若为代垫
				otherChangeRecord.setNewvalue(BigDecimal.valueOf(prepayValue));	
				// 设置授信类型
				otherChangeRecord.setUsertype("2");
			}
			otherChangeRecord.setOldvalue(oldValue);
			otherChangeRecord.setUserid(customerid);
					
			otherChangeRecord.setRemark(remark);
			otherChangeRecord.setProjectid(projectid);
			this.getHibernateTemplate().save(otherChangeRecord);
			
			// 若授信类型为“代垫+放货”，除了添加放货记录外，还要添加代垫记录
			if(valueType.equals("3")){
				OtherChangeRecord ocr = new OtherChangeRecord();
				ocr.setNewvalue(BigDecimal.valueOf(prepayValue));
				ocr.setOldvalue(oldValue2);
				ocr.setUserid(customerid);
				// 设置授信类型
				ocr.setUsertype("2");		
				ocr.setRemark(remark);
				ocr.setProjectid(projectid);
				this.getHibernateTemplate().save(ocr);
			}
			return 0;
		}
		
			return -1;
	}


	/**
	 * 插入变更记录
	 * @param customerid
	 * @param newValue
	 * @return
	 */
	/*
	public void insertChangeRecord(String customerid, double newValue)
	{
		String hql = "from CustomerCreditConf custCreditConf where customerid = ?";
		List custCreditConf = this.find(hql,customerid);
				
		if(custCreditConf.size() != 0 )
		{
			CustomerCreditConf customerCreditConf = (CustomerCreditConf)custCreditConf.get(0);
			OtherChangeRecord otherChangeRecord = new OtherChangeRecord();
			otherChangeRecord.setNewvalue(BigDecimal.valueOf(newValue));
			otherChangeRecord.setOldvalue(customerCreditConf.getOtherprepayvalue());
			otherChangeRecord.setUserid(customerid);
			otherChangeRecord.setUsertype("1");		// 客户类型为"1"
			this.getHibernateTemplate().save(otherChangeRecord);
		}		
	}*/
}	