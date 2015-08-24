/*
 * @(#)MaterielService.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-6-24
 *  描　述：创建
 */

package com.infolion.sample.orderManage.purchaseOrder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.sample.orderManage.purchaseOrder.dao.MaterielJdbcDao;
import com.infolion.sample.orderManage.purchaseOrder.domain.Materiel;

/**
 * 
 * <pre>
 * 物料服务
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class MaterielService extends BaseService
{
	@Autowired
	private MaterielJdbcDao materielJdbcDao;

	/**
	 * @param materielJdbcDao
	 *            the materielJdbcDao to set
	 */
	public void setMaterielJdbcDao(MaterielJdbcDao materielJdbcDao)
	{
		this.materielJdbcDao = materielJdbcDao;
	}

	/**
	 * 取得物料数据
	 * 
	 * @param materielNo
	 * @return
	 */
	public Materiel _get(String materielNo)
	{
		return materielJdbcDao.getMateriel(materielNo);
	}
}
