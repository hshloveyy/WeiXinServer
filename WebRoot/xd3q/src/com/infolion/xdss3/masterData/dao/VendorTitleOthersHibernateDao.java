/*
 * @(#)VendorTitleHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月07日 10点18分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.domain.VendorTitleOthers;


/**
 * <pre>
 * 未清供应商数据抬头(VendorTitle),HibernateDao 操作类
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
public class VendorTitleOthersHibernateDao extends BaseHibernateDao<VendorTitleOthers>
{
	/**
	 * 获取未清供应商数据凭证编号列表
	 * @return
	 */
	public List<CertificateNo> getUnclearCertificateNo( String vendor)
	{
		List<CertificateNo> certificateNoList = new ArrayList<CertificateNo>();
		certificateNoList.clear();
		List<VendorTitleOthers> vendorTitleList;
		if( vendor == null || vendor.equals(""))
		{
			String hql = "from VendorTitleOthers where iscleared = '0'";
			vendorTitleList = this.find(hql);
		}
		else
		{
			String hql = "from VendorTitleOthers where iscleared = '0' and lifnr = ?";
			vendorTitleList = this.find(hql, vendor);
		}
		
		if( vendorTitleList != null && vendorTitleList.size() > 0 )
		{
			for( int i = 0; i < vendorTitleList.size(); i++ )
			{
				CertificateNo certificateNo = new CertificateNo();
				certificateNo.setBelnr(vendorTitleList.get(i).getBelnr());
				certificateNo.setGjahr(vendorTitleList.get(i).getGjahr());
				certificateNo.setBukrs(vendorTitleList.get(i).getBukrs());
				certificateNoList.add(certificateNo);
			}
		}
		return certificateNoList;
	}
}