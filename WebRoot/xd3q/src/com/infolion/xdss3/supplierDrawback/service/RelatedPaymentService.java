/*
 * @(#)CustomerDbBankItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月30日 10点48分01秒
 *  描　述：创建
 */
package com.infolion.xdss3.supplierDrawback.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemJdbcDao;


/**
 * <pre>
 * 客户退款银行(CustomerDbBankItem)服务类
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
@Service
public class RelatedPaymentService extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private SupplierRefundItemJdbcDao supplierRefundItemJdbcDao;	
	
	
	/**
	 * @param supplierRefundItemJdbcDao the supplierRefundItemJdbcDao to set
	 */
	public void setSupplierRefundItemJdbcDao(
			SupplierRefundItemJdbcDao supplierRefundItemJdbcDao) {
		this.supplierRefundItemJdbcDao = supplierRefundItemJdbcDao;
	}


	/**
	 * 保存客户退款关联单据数据
	 * @param refundmentId
	 */
	public void saveRelatedCollect(String refundmentId)
	{
		/**
		 * 保存供应商退款关联单据数据
		 */
		this.supplierRefundItemJdbcDao.saveRelatedPaymentByRefundmentid(refundmentId);
		
		/**
		 * 退款凭证产生后，更新付款Item表
		 */
		this.supplierRefundItemJdbcDao.updatePaymentItem(refundmentId);
	}
}