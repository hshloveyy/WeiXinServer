/*
 * @(#)PaymentImportsInfoHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.payment.dao.PaymentImportsInfoHibernateDao;
import com.infolion.sapss.payment.dao.PaymentImportsInfoJdbcDao;
import com.infolion.sapss.payment.dao.PickListInfoHibernateDao;
import com.infolion.sapss.payment.dao.PickListInfoJdbcDao;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
@Service
public class PaymentImportsInfoHibernateService extends BaseHibernateService
{
	@Autowired
	private PaymentImportsInfoHibernateDao paymentImportsInfoHibernateDao;
	@Autowired
	private PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao;
	@Autowired
	private PickListInfoHibernateDao pickListInfoHibernateDao;
	@Autowired
	private PickListInfoJdbcDao pickListInfoJdbcDao;

	public PaymentImportsInfoHibernateDao getPaymentImportsInfoHibernateDao()
	{
		return paymentImportsInfoHibernateDao;
	}

	public void setPaymentImportsInfoHibernateDao(
			PaymentImportsInfoHibernateDao paymentImportsInfoHibernateDao)
	{
		this.paymentImportsInfoHibernateDao = paymentImportsInfoHibernateDao;
	}

	public PaymentImportsInfoJdbcDao getPaymentImportsInfoJdbcDao()
	{
		return paymentImportsInfoJdbcDao;
	}

	public void setPaymentImportsInfoJdbcDao(
			PaymentImportsInfoJdbcDao paymentImportsInfoJdbcDao)
	{
		this.paymentImportsInfoJdbcDao = paymentImportsInfoJdbcDao;
	}

	public PickListInfoHibernateDao getPickListInfoHibernateDao()
	{
		return pickListInfoHibernateDao;
	}

	public void setPickListInfoHibernateDao(
			PickListInfoHibernateDao pickListInfoHibernateDao)
	{
		this.pickListInfoHibernateDao = pickListInfoHibernateDao;
	}

	public PickListInfoJdbcDao getPickListInfoJdbcDao()
	{
		return pickListInfoJdbcDao;
	}

	public void setPickListInfoJdbcDao(PickListInfoJdbcDao pickListInfoJdbcDao)
	{
		this.pickListInfoJdbcDao = pickListInfoJdbcDao;
	}

	/**
	 * 保存进口付款单
	 * @param TPaymentImportsInfo
	 */
	public void savePaymentImportsInfo(TPaymentImportsInfo TPaymentImportsInfo)
	{
		paymentImportsInfoHibernateDao.saveOrUpdate(TPaymentImportsInfo);

	}

	/**
	 * 取得页面跳转标签
	 * 
	 * @param paymentType
	 * @return
	 */
	public String getPaymentImportsTag(String paymentType)
	{
		String tag = "";
		if (paymentType.equals("tt") || paymentType.equals("dpda")
				|| paymentType.equals("lc"))
		{
			tag = paymentType;
		}
		else if (paymentType.equals("da") || paymentType.equals("dp"))
		{
			tag = "dpda";
		}
		else if (paymentType.indexOf("lc") > 0)
		{
			tag = "lc";
		}
		else if (paymentType.length() == 1)
		{
			int iPaymentTypee = 0;
			try
			{
				iPaymentTypee = Integer.parseInt(paymentType);
			}
			catch (Exception e)
			{

			}
			switch (iPaymentTypee)
			{
			case 1:
			case 2:
				tag = "lc";
				break;
			case 3:
			case 4:
				tag = "dpda";
			case 5:
				tag = "tt";
			}
		}
		return tag.toUpperCase();
	}
	/**
	 * 删除
	 * @param paymentId
	 */
	public int delete(String paymentId) {
		return this.paymentImportsInfoJdbcDao.delete(paymentId);
	}
	/**
	 * 查找付款信息
	 * @param paymentId
	 * @return
	 */
	public TPaymentImportsInfo findPaymentImportInfo(String paymentId) {
		return this.paymentImportsInfoHibernateDao.get(paymentId);
	}

}
