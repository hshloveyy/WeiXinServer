package com.infolion.xdss3.payment.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

@Repository
public class PaymentCurrencyJdbcDao extends BaseJdbcDao{
	
	/**
	 * 获取币别代码
	 */
	public List<String> getAllCurrencyCode(){
	
		String strSql = "select WEARS from YTCURT";
	
		List<String> allCurrencyCode = this.getJdbcTemplate().queryForList(strSql);
		return allCurrencyCode;
	}
}
