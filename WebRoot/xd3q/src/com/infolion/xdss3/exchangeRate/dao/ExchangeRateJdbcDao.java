package com.infolion.xdss3.exchangeRate.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.exchangeRate.domain.ExchangeRate;
@Repository
public class ExchangeRateJdbcDao extends BaseJdbcDao {
	public ExchangeRate getExchangeRate(String date,String currency,String currency2)
	{
		String sql = "select * from yexchangerate e where e.rate_date='"+date+"' and e.currency='"+currency+"' and e.currency2='"+currency2+"'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		if(null != list && list.size() !=0){
			ExchangeRate rate = new ExchangeRate();
			ExBeanUtils.setBeanValueFromMap(rate, list.get(0));
			return rate;
		}else{
			return null;
		}
	}
	
	public List<ExchangeRate> getList(String sql){
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		List<ExchangeRate> li = new ArrayList<ExchangeRate>();
		if(null != list && list.size() !=0){
			for(int i=0 ;i<list.size();i++){
				ExchangeRate rate = new ExchangeRate();
				ExBeanUtils.setBeanValueFromMap(rate, list.get(i));
				li.add(rate);
			}
			
		}
		return li;
		
	}
}
