/*
 * @(#)CustomerDbBankItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月30日 10点48分01秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;


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
public class RelatedCollectService extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}
	
	/**
	 * 保存客户退款关联单据数据
	 * @param refundmentId
	 */
	public void saveRelatedCollect(String refundmentId)
	{
		this.customerRefundItemJdbcDao.saveRelatedCollectByRefundmentid(refundmentId);
		
		this.customerRefundItemJdbcDao.updateCollectItem(refundmentId);
	}
}