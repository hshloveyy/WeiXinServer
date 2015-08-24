package com.infolion.xdss3.kpi.dao;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.kpi.domain.BankAcceptance;




@Repository
public class KpiJdbcDao extends BaseJdbcDao {
	public BigDecimal getSumAmount(String sql)
	{
		log.debug("执行SQL，取得金额合计值:" + sql);
		BigDecimal sumAmount = (BigDecimal) this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		if (null == sumAmount)
			return new BigDecimal(0.00);
		else
			return sumAmount;
	}
	
	
	public List<BankAcceptance> getBankAcceptance(String sql){
		List<BankAcceptance> list = new ArrayList<BankAcceptance>();
		  List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		  BigDecimal amount = new BigDecimal("0");
		  for(Map map: rowList){
			  BankAcceptance bat = new BankAcceptance();			 
			  bat.setCheckdate(map.get("checkdate").toString());
			  bat.setAmount(new BigDecimal(map.get("amount").toString()));
			  bat.setAmount2(new BigDecimal(map.get("amount2").toString()));
			  bat.setAmount3(new BigDecimal(map.get("amount3").toString()));
			  amount = amount.add(bat.getAmount3());
			  bat.setAmount4(amount);
//			  ExBeanUtils.setBeanValueFromMap(bat, map);
			  list.add(bat);
		  }
		  return list;
	}
}
