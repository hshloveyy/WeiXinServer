package com.infolion.xdss3.foreignExchange.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;




@Repository
public class ForeignExchangeJdbcDao extends BaseJdbcDao {
	public static String CW = "0200";//财务
	public static String GL = "0400";//管理
	public static String XS = "0300";//销售
	/**
	 * 取得成本中心
	 * @param gsber 业务范围
	 * @param area 
	 * @return
	 */
	public String getcostcenter(String gsber,String area){
		String sql="select c.kostl from YCOSTCENTER c where c.gsber='"+gsber+"' and c.func_area='"+area+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if (null != list && list.size() > 0)
			return (String) ((Map) list.get(0)).get("kostl");
		else
			return "";
	}	

	/**
	 * 取得记账号
	 * @param agkoa 客户供应商的单号去掉0的头一位数
	 * @param skont 统驭科目
	 * @param debitcredit 借代标识 S H
	 * @param agkon 客户供应商标识，客户D，供应商K 
	 * @return
	 */
	public Map<String,String> getCheckCode(String agkoa,String skont,String debitcredit,String agkon){
		String sql="select c.checkcode,c.glflag from ycheckcode c where c.agkoa='"+agkoa+"' and c.subject='"+skont+"' and c.debitcredit='"+debitcredit+"' and c.agkon='"+agkon+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if (null != list && list.size() > 0)
			return  (Map) list.get(0);
		else
			return new HashMap<String,String> ();
	}
}
