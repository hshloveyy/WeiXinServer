/*
 * @(#)CustomerTitleHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月07日 10点19分38秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.domain.CustomerTitle;

/**
 * <pre>
 * 客户未清数据抬头(CustomerTitle),HibernateDao 操作类
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
public class CustomerTitleHibernateDao extends BaseHibernateDao<CustomerTitle>
{	
	/**
	 * 获取指定客户未清数据凭证编号列表
	 * @param bukrs 
	 * 			null: 取所有数据
	 * @return
	 */
	public List<CertificateNo> getUnclearCustomerCertificateNoByBukrs(String kunnr)
	{
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		certificateNoList.clear();
		List<CustomerTitle> customerTitleList;
		if(kunnr == null || kunnr.equals(""))
		{
			String hql = "from CustomerTitle where iscleared = '0'";
			customerTitleList = this.find(hql);
		}
		else
		{
			String hql = "from CustomerTitle where iscleared = '0' and kunnr = ?";
			customerTitleList = this.find(hql,kunnr);
		}
		
		if( customerTitleList != null && customerTitleList.size() > 0 )
		{
			for( int i = 0; i < customerTitleList.size(); i++ )
			{
				CertificateNo certificateNo = new CertificateNo();
				certificateNo.setBelnr(customerTitleList.get(i).getBelnr());
				certificateNo.setGjahr(customerTitleList.get(i).getGjahr());
				certificateNo.setBukrs(customerTitleList.get(i).getBukrs());
				certificateNoList.add(certificateNo);
			}
		}
		return certificateNoList;
	}
	
	/**
	 * 取得未清客户发票
	 * @param id
	 * @return
	 */
	public List getUnclearCustomerInvoiceList(String customertitleids)
	{		
		String hql = "from CustomerTitle where customertitleid in (:ids) and iscleared = '0' ";
		Query query = getSession().createQuery(hql); 
		query.setParameterList("ids", customertitleids.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 通过合同取得未清客户发票
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByContract(String contractnos)
	{	
		String hql = "from CustomerTitle where ihrez in (:ids) and iscleared = '0' ";
		
		Query query = getSession().createQuery(hql); 
		query.setParameterList("ids", contractnos.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 通过立项取得未清客户发票
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByProject(String projectnos)
	{	
		String hql = "from CustomerTitle where bname in (:ids) and iscleared = '0' ";
		Query query = getSession().createQuery(hql); 
		query.setParameterList("ids", projectnos.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 通过合同取得未清客户发票（根据客户进行过滤）
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByContract(String kunnr, String contractnos)
	{	
		String hql = "from CustomerTitle where kunnr = (:kunnr) and ihrez in (:ids) and iscleared = '0' ";
		
		Query query = getSession().createQuery(hql); 
		query.setParameter("kunnr", kunnr);
		query.setParameterList("ids", contractnos.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 通过立项取得未清客户发票（根据客户进行过滤）
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceListByProject(String kunnr, String projectnos)
	{	
		String hql = "from CustomerTitle where kunnr = (:kunnr) and bname in (:ids) and iscleared = '0' ";
		Query query = getSession().createQuery(hql); 
		query.setParameter("kunnr", kunnr);
		query.setParameterList("ids", projectnos.split(",")); 
		return query.list(); 
	}
	
	/**
	 * 通过合同、立项取得未清客户发票
	 * @param id
	 * @return
	 */
	public List getUnclearInvoiceList(String contractnos, String projectnos)
	{	
		String hql = "from CustomerTitle where ((ihrez in (:cns)) or (bname in (:pns))) and iscleared = '0' ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("cns", contractnos.split(","));
		query.setParameterList("pns", projectnos.split(","));
		return query.list(); 
	}
}