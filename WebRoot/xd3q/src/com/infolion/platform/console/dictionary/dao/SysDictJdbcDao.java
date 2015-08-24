/*
 * @(#)TSysDictJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-27
 *  描　述：创建
 */

package com.infolion.platform.console.dictionary.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.dictionary.domain.SysDict;
import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.SqlGenerator;

@Repository
public class SysDictJdbcDao extends BaseDao {

	public void addDictionary(SysDict tSysDict) {
		String strSql = "insert into t_sys_dict " + "(dict_id, "
				+ "dict_table, " + "dict_code_column, " + "dict_name_column, "
				+ "dict_type, " + "dict_key_column, " + "dict_parent_column, "
				+ "cmd, " + "creator, " + "create_time) " + "values "
				+ "(?,?,?,?,?,?,?,?,?,?)";

		Object[] params = new Object[] { tSysDict.getDictid(),
				tSysDict.getDicttable(), tSysDict.getDictcodecolumn(),
				tSysDict.getDictnamecolumn(), tSysDict.getDicttype(),
				tSysDict.getDictkeycolumn(), tSysDict.getDictparentcolumn(),
				tSysDict.getCmd(), tSysDict.getCreator(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE) };

		getJdbcTemplate().update(strSql, params);
	}

	public void updateDictionary(SysDict tSysDict) {
		String strSql = "update t_sys_dict " + "set dict_table         = ?, "
				+ "dict_code_column   = ?, " + "dict_name_column   = ?, "
				+ "dict_type          = ?, " + "dict_key_column    = ?, "
				+ "dict_parent_column = ?, " + "cmd                = ? "
				+ "where dict_id = ?";
		Object[] params = new Object[] { tSysDict.getDicttable(),
				tSysDict.getDictcodecolumn(), tSysDict.getDictnamecolumn(),
				tSysDict.getDicttype(), tSysDict.getDictkeycolumn(),
				tSysDict.getDictparentcolumn(), tSysDict.getCmd(),
				tSysDict.getDictid() };

		getJdbcTemplate().update(strSql, params);
	}

	public void deleteDictionaryInfo(String in_strDict_List) {
		String strSql = "delete t_sys_dict where dict_id = ?";

		String[] IdList = in_strDict_List.split("\\|");

		for (int i = 0; i < IdList.length; i++) {
			Object[] params = new Object[] { IdList[i] };

			getJdbcTemplate().update(strSql, params);
		}
	}

	/**
	 * 取得所有数据字典配置
	 * 
	 * @return
	 */
	public List<SysDict> queryAllDictionarys() {
		final List<SysDict> dictList = new ArrayList();
		String strSql = "select a.* from t_sys_dict a";
		getJdbcTemplate().query(strSql,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						SysDict tSysDict = new SysDict();
						tSysDict.setDictid(rs.getString("dict_id"));
						tSysDict.setDicttable(rs.getString("dict_table"));
						tSysDict.setDictcodecolumn(rs
								.getString("dict_code_column"));
						tSysDict.setDictnamecolumn(rs
								.getString("dict_name_column"));
						tSysDict.setDicttype(rs.getString("dict_type"));
						if (rs.getString("dict_type").equals("1"))
							tSysDict.setDicttypename("单表字典表");

						if (rs.getString("dict_type").equals("2"))
							tSysDict.setDicttypename("主从字典表");
						//tSysDict.setDicttypename(SysCachePoolUtils.getDictDataValue("BM_SYS_DICT_TYPE", rs.getString("dict_type")));
						tSysDict.setDictkeycolumn(rs
								.getString("dict_key_column"));
						tSysDict.setDictparentcolumn(rs
								.getString("dict_parent_column"));
						tSysDict.setCmd(rs.getString("cmd"));
						tSysDict.setCreator(rs.getString("creator"));
						tSysDict.setCreatetime(rs.getString("create_time"));
						dictList.add(tSysDict);
					}
				});
		return dictList;
	}

	/**
	 * 取得单层数据字典数据
	 * 
	 * @param sysDict
	 * @return
	 */
	public List querySignalDictionaryData(SysDict sysDict) {
		String sql = "select " + sysDict.getDictcodecolumn() + ","
				+ sysDict.getDictnamecolumn() + " from "
				+ sysDict.getDicttable()
				+ " order by " + sysDict.getDictcodecolumn();
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 取得多层数据字典数据
	 * 
	 * @param sysDict
	 * @return
	 */
	public List queryMutilDictionaryData(SysDict sysDict) {
		String sql = "select " + sysDict.getDictcodecolumn() + ","
				+ sysDict.getDictnamecolumn() + ","
				+ sysDict.getDictparentcolumn() + " from "
				+ sysDict.getDicttable()
				+ " order by " + sysDict.getDictcodecolumn();
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<SysDict> queryDictionaryInfo(SysDict in_tSysDict) {
		final List<SysDict> dictList = new ArrayList();
		String strSql = "select a.* from t_sys_dict a where 1=1";

		// 字典表名不为空
		if (in_tSysDict.getDicttable() != null
				&& !"".equals(in_tSysDict.getDicttable())) {
			strSql = strSql + " and a.dict_table = '"
					+ in_tSysDict.getDicttable() + "'";
		}

		// 编码字段名不为空
		if (in_tSysDict.getDictcodecolumn() != null
				&& !"".equals(in_tSysDict.getDictcodecolumn())) {
			strSql = strSql + " and a.dict_code_column = '"
					+ in_tSysDict.getDictcodecolumn() + "'";
		}

		// 名称字段名不为空
		if (in_tSysDict.getDictnamecolumn() != null
				&& !"".equals(in_tSysDict.getDictnamecolumn())) {
			strSql = strSql + " and a.dict_name_column = '"
					+ in_tSysDict.getDictnamecolumn() + "'";
		}

		// 字典表类型名不为空
		if (in_tSysDict.getDicttype() != null
				&& !"".equals(in_tSysDict.getDicttype())) {
			strSql = strSql + " and a.dict_type = '"
					+ in_tSysDict.getDicttype() + "'";
		}

		// 字典表关键字段名不为空
		if (in_tSysDict.getDictkeycolumn() != null
				&& !"".equals(in_tSysDict.getDictkeycolumn())) {
			strSql = strSql + " and a.dict_key_column = '"
					+ in_tSysDict.getDictkeycolumn() + "'";
		}

		// 父编码字段名不为空
		if (in_tSysDict.getDictparentcolumn() != null
				&& !"".equals(in_tSysDict.getDictparentcolumn())) {
			strSql = strSql + " and a.dict_parent_column = '"
					+ in_tSysDict.getDictparentcolumn() + "'";
		}

		getJdbcTemplate().query(strSql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						SysDict tSysDict = new SysDict();
						tSysDict.setDictid(rs.getString("dict_id"));
						tSysDict.setDicttable(rs.getString("dict_table"));
						tSysDict.setDictcodecolumn(rs
								.getString("dict_code_column"));
						tSysDict.setDictnamecolumn(rs
								.getString("dict_name_column"));
						tSysDict.setDicttype(rs.getString("dict_type"));
						if (rs.getString("dict_type").equals("1"))
							tSysDict.setDicttypename("单表字典表");

						if (rs.getString("dict_type").equals("2"))
							tSysDict.setDicttypename("主从字典表");
						//tSysDict.setDicttypename(SysCachePoolUtils.getDictDataValue("BM_SYS_DICT_TYPE", rs.getString("dict_type")));
						tSysDict.setDictkeycolumn(rs
								.getString("dict_key_column"));
						tSysDict.setDictparentcolumn(rs
								.getString("dict_parent_column"));
						tSysDict.setCmd(rs.getString("cmd"));
						tSysDict.setCreator(rs.getString("creator"));
						tSysDict.setCreatetime(rs.getString("create_time"));
						dictList.add(tSysDict);
					}
				});

		return dictList;
	}

	public SysDict queryDictionaryById(String in_strDict_Id) {
		final SysDict tSysDict = new SysDict();
		String strSql = "select a.* from t_sys_dict a where a.dict_id = ?";
		getJdbcTemplate().query(strSql, new Object[] { in_strDict_Id },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						tSysDict.setDictid(rs.getString("dict_id"));
						tSysDict.setDicttable(rs.getString("dict_table"));
						tSysDict.setDictcodecolumn(rs
								.getString("dict_code_column"));
						tSysDict.setDictnamecolumn(rs
								.getString("dict_name_column"));
						tSysDict.setDicttype(rs.getString("dict_type"));
						tSysDict.setDictkeycolumn(rs
								.getString("dict_key_column"));
						tSysDict.setDictparentcolumn(rs
								.getString("dict_parent_column"));
						tSysDict.setCmd(rs.getString("cmd"));
						tSysDict.setCreator(rs.getString("creator"));
						tSysDict.setCreatetime(rs.getString("create_time"));
					}
				});

		return tSysDict;
	}

	// public List<TSysDict> getDictionaryInfo(Map in_tSysDict){
	// final List<TSysDict> dictList = new ArrayList();

	// String strSql ="select a.* from t_sys_dict a where 1=1 ";
	// SqlGenerator sqlGenerator = new SqlGenerator();

	// strSql += sqlGenerator.getSQLWhere(in_tSysDict);
	// int conditionCount = 0;
	// //字典表名
	// if (in_tSysDict.getDicttable() != null
	// && !"".equals(in_tSysDict.getDicttable())){
	// if (conditionCount == 0){
	//				
	// }else{
	//				
	// }
	// }
	// //编码字段名
	// if (in_tSysDict.getDictcodecolumn() != null
	// && !"".equals(in_tSysDict.getDictcodecolumn())){
	// if (conditionCount == 0){
	// }else{
	//				
	// }
	// }
	// //名称字段名
	// if (in_tSysDict.getDictnamecolumn() != null
	// && !"".equals(in_tSysDict.getDictnamecolumn())){
	// if (conditionCount == 0){
	// }else{
	//				
	// }
	// }
	// //字典表类型
	// if (in_tSysDict.getDicttype() != null
	// && !"".equals(in_tSysDict.getDicttype())){
	// if (conditionCount == 0){
	// }else{
	//				
	// }
	// }
	// //字典表关键字段名
	// if (in_tSysDict.getDictkeycolumn() != null
	// && !"".equals(in_tSysDict.getDictkeycolumn())){
	// if (conditionCount == 0){
	// }else{
	//				
	// }
	// }
	// //父编码字段名
	// if (in_tSysDict.getDictparentcolumn() != null
	// && !"".equals(in_tSysDict.getDictparentcolumn())){
	// if (conditionCount == 0){
	// }else{
	//				
	// }
	// }

	// return dictList;
	// }
}
