/*
 * @(#)ExportIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.receiptLog.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.receiptLog.dao.PaymentMoneyHibernateDao;
import com.infolion.sapss.receiptLog.domain.TPaymentMoneyInfo;
@Service
public class PaymentMoneyService extends  BaseHibernateService{
	@Autowired
	private PaymentMoneyHibernateDao paymentMoneyHibernateDao;
	
	public void setPaymentMoneyHibernateDao(
			PaymentMoneyHibernateDao paymentMoneyHibernateDao) {
		this.paymentMoneyHibernateDao = paymentMoneyHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TPaymentMoneyInfo info) {
		this.paymentMoneyHibernateDao.saveOrUpdate(info);
		
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		TPaymentMoneyInfo info = this.paymentMoneyHibernateDao.get(id);
		info.setIsAvailable("0");
		this.paymentMoneyHibernateDao.update(info);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TPaymentMoneyInfo find(String id) {
		return this.paymentMoneyHibernateDao.get(id);
		
	}

	public String querySQL(HttpServletRequest request) {
		UserContext usercontext = UserContextHolder.getLocalUserContext().getUserContext();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_payment_money_info where is_available=1 ");
		if(StringUtils.isNotEmpty(request.getParameter("contractNo")))
			sb.append(" and contract_no='"+request.getParameter("contractNo")+"'");
		if(StringUtils.isNotEmpty(request.getParameter("payDate")))
			sb.append(" and pay_date='"+request.getParameter("payDate")+"'");
		if(StringUtils.isNotEmpty(request.getParameter("tradeType")))
			sb.append(" and trade_type='"+request.getParameter("tradeType")+"'");
		sb.append(" and dept_id='"+usercontext.getSysDept().getDeptid()+"'");
		sb.append(" order by create_time desc");
		return sb.toString();
	}
}
