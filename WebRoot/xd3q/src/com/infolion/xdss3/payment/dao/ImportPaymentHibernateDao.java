/*
 * @(#)ImportPaymentHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 11点58分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.paymentGen.dao.ImportPaymentHibernateDaoGen;


/**
 * <pre>
 * 进口付款(ImportPayment),HibernateDao 操作类
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
public class ImportPaymentHibernateDao extends ImportPaymentHibernateDaoGen<ImportPayment>{
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25
	 * 根据付款单号取得付款信息
	 */
	public ImportPayment getPaymentByNo(String paymentNo){
		String hql = "from HomePayment h where paymentno = '" + paymentNo +"'";
		List<ImportPayment> list = this.getHibernateTemplate().find(hql);
		return list.get(0);
	}
}