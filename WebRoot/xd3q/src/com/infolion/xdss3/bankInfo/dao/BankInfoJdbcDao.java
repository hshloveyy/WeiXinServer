/*
 * @(#)BankInfoJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-7-20
 *  描　述：创建
 */

package com.infolion.xdss3.bankInfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

@Repository
public class BankInfoJdbcDao extends BaseJdbcDao
{
	public String getDaBankHkonrName(String strBankId,String strDAaBankHkont){
		String sql = "select a.dabank_hkonrname from ybank_info a where a.bank_id = '"+strBankId+
		"' and a.dabank_hkont ='"+strDAaBankHkont+"'";

		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String strRet = "";
		if( list!= null && list.size() > 0)
		{
			strRet = list.get(0).get("dabank_hkonrname");
		}else{
			strRet = "";
		}
		return strRet;
	}
	
	public String getBankInfo(String BANK_ACCOUNT){
		String sql = "select BANK_HKONT from ybank_info where BANK_ACCOUNT = '"+BANK_ACCOUNT+"'";

		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String strRet = "";
		if( list!= null && list.size() > 0)
		{
			strRet = list.get(0).get("BANK_HKONT");
		}else{
			strRet = "";
		}
		return strRet;
	}
}
