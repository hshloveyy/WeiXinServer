/*
 * @(#)TSysDictService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-27
 *  描　述：创建
 */

package com.infolion.platform.console.dictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.dictionary.dao.SysDictJdbcDao;
import com.infolion.platform.console.dictionary.domain.SysDict;
import com.infolion.platform.core.service.BaseJdbcService;

@Service
public class SysDictService extends BaseJdbcService {
	@Autowired
	private SysDictJdbcDao tSysDictJdbcDao;

	public void setTSysDictJdbcDao(SysDictJdbcDao sysDictJdbcDao) {
		tSysDictJdbcDao = sysDictJdbcDao;
	}

	public void addDictionary(SysDict tSysDict) {
		tSysDictJdbcDao.addDictionary(tSysDict);
	}

	public void updateDictionary(SysDict tSysDict) {
		tSysDictJdbcDao.updateDictionary(tSysDict);
	}

	public void deleteDictionary(String in_strDict_List) {
		tSysDictJdbcDao.deleteDictionaryInfo(in_strDict_List);
	}

	public SysDict queryDictionaryById(String in_strDict_Id) {
		return tSysDictJdbcDao.queryDictionaryById(in_strDict_Id);
	}

	public List<SysDict> queryDictionaryInfo(SysDict tSysDict) {
		return tSysDictJdbcDao.queryDictionaryInfo(tSysDict);
	}

	/**
	 * 取得所有字典表配置信息
	 * 
	 * @return
	 */
	public List<SysDict> queryAllDictionaryInfo() {
		return tSysDictJdbcDao.queryAllDictionarys();
	}
	/**
	 * 取得单层数据字典
	 * @param sysDict
	 * @return
	 */
	public List querySignalDictionaryData(SysDict sysDict) {
		return this.tSysDictJdbcDao.querySignalDictionaryData(sysDict);
	}
	/**
	 * 取得多层数据字典
	 * @param sysDict
	 * @return
	 */
	public List queryMutilDictionaryData(SysDict sysDict) {
		return this.tSysDictJdbcDao.queryMutilDictionaryData(sysDict);
	}
}
