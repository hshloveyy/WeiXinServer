/*
 * @(#)SupplierRefundItemJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：邱荣发
 *  时　间：2010-6-22
 *  描　述：创建
 */

package com.infolion.xdss3.unnameCollect.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;

@Repository
public class UnnamerCollectJdbcDao extends BaseJdbcDao
{
	/**
	 * 根据供应商获得供应商名称
	 * @param vendor
	 * @return
	 */
	public String getVendorDescById(String vendor)
	{
		String sql = "select NAME1 vendorDesc from YGETLIFNR where LIFNR = '"
				+ vendor + "'";
		
		List<Map<String,String>> retList = this.getJdbcTemplate().queryForList(sql);
		
		if( retList != null )
		{
			return retList.get(0).get("vendorDesc");
		}
		else
		{
			return "";
		}
	}

	/**
	 * 获取银行ID & 银行科目
	 * @param bankname
	 * @param bankAccount
	 * @return
	 */
	public Map<String,String> getBankIdAndSubject( String bankname, String bankAccount)
	{
		Map<String,String> retMap = new HashMap<String, String>();
		
		String sql = "select BANK_ID bankid, BANK_HKONT bankSubject from YBANK_INFO where BANK_NAME = '"
			+ bankname + "' and BANK_ACCOUNT = '"
			+ bankAccount + "'";
		
		List<Map<String,String>> retList = this.getJdbcTemplate().queryForList(sql);
		
		if(retList != null)
		{
			retMap.put("bankid", retList.get(0).get("bankid"));
			retMap.put("bankSubject", retList.get(0).get("bankSubject"));
			return retMap;
		}
		else
		{
			return null;
		}
	}

	public double getExhangeRateByFcurr(String fCurr) {
		String sql = "select UKURS ukurs from YTCURR where FCURR = '" + fCurr + "' and TCURR = 'CNY' order by GDATU desc";
		List<Map<String,Number>> retList =  this.getJdbcTemplate().queryForList(sql);
		if(retList != null && retList.size() > 0){
			Number ukurs = retList.get(0).get("ukurs");
			return ukurs.doubleValue();
		}else
			return 0.0;
		
	}
}
