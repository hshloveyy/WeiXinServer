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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.receiptLog.dao.ReceiptGoodsHibernateDao;
import com.infolion.sapss.receiptLog.dao.ReceiptLogJdbcDao;
import com.infolion.sapss.receiptLog.domain.TReceiptGoodsInfo;
@Service
public class ReceiptGoodsService extends  BaseHibernateService{
	@Autowired
	private ReceiptGoodsHibernateDao receiptGoodsHibernateDao;
	@Autowired
	private ReceiptLogJdbcDao receiptLogJdbcDao;
	
	public void setExportDrawbackHibernateDao(
			ReceiptGoodsHibernateDao receiptGoodsHibernateDao) {
		this.receiptGoodsHibernateDao = receiptGoodsHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TReceiptGoodsInfo info) {
		this.receiptGoodsHibernateDao.saveOrUpdate(info);
		
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		TReceiptGoodsInfo info = this.receiptGoodsHibernateDao.get(id);
		info.setIsAvailable("0");
		this.receiptGoodsHibernateDao.update(info);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TReceiptGoodsInfo find(String id) {
		return this.receiptGoodsHibernateDao.get(id);
		
	}

	public String querySQL(Map<String,String> request) {
		UserContext usercontext = UserContextHolder.getLocalUserContext().getUserContext();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_receipt_goods_info where is_available=1 ");
		if(StringUtils.isNotEmpty(request.get("receiptNo")))
			sb.append(" and receipt_no='"+request.get("receiptNo")+"'");
		if(StringUtils.isNotEmpty(request.get("importDate")))
			sb.append(" and import_date >='"+request.get("importDate")+"'");
		if(StringUtils.isNotEmpty(request.get("importDate1")))
			sb.append(" and import_date <='"+request.get("importDate1")+"'");
		if(StringUtils.isNotEmpty(request.get("customeNo")))
			sb.append(" and custome_no like '%"+request.get("customeNo")+"%'");
		if(StringUtils.isNotEmpty(request.get("writeNo")))
			sb.append(" and write_NO like '%"+request.get("writeNo")+"%'");
		if(StringUtils.isNotEmpty(request.get("tradeType")))
			sb.append(" and trade_type='"+request.get("tradeType")+"'");
		sb.append(" and dept_id='"+usercontext.getSysDept().getDeptid()+"'");
		sb.append(" order by creator_time desc");
		return sb.toString();
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		receiptLogJdbcDao.dealOutToExcel(sql ,excel);
	}

	public void setReceiptLogJdbcDao(ReceiptLogJdbcDao receiptLogJdbcDao) {
		this.receiptLogJdbcDao = receiptLogJdbcDao;
	}
}
