/*
 * @(#)DictTableCacheManager.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.console.dictionary.domain.SysDict;
import com.infolion.platform.console.dictionary.service.SysDictService;
import com.infolion.platform.sys.Constants;

/**
 * 
 * <pre>
 * 数据字典数据缓存管理器
 * </pre>
 * 
 * 字典表的存储逻辑： <br>
 * 1.cache中存储所有的字典表。以Element的形式存储 <br>
 * 2.每个字典表中有一个到多个的分组，没有定义分组的有一个系统默认的分组。以Map的形式存储在SysDictTable#
 * dictTableGroupRows中 <br>
 * 3.每个分组为一张详细数据的字典表 <br>
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
 @Component
public class DictTableCacheManagerXd extends CacheItemManagerAdapter
{
	@Autowired
	private SysDictService sysDictService;

	public void setSysDictService(SysDictService sysDictService)
	{
		this.sysDictService = sysDictService;
	}

	/**
	 * 用于缓存字典表的核心缓存名
	 */

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 初始化所有的字典表
	 */
	public void init()
	{
		log.debug("开始加载信达一期字典表...");
		log.debug("--------------------------------------------");
		List<SysDict> list = sysDictService.queryAllDictionaryInfo();
		for (SysDict dictTable : list)
		{
			this.loadDictTable(dictTable);
		}
		log.debug("--------------------------------------------");
		log.info("信达一期字典表加载完毕.");
	}

	/**
	 * 加载某个字典表,并缓存到核心缓存中
	 * 
	 * @param sysDict
	 */
	private void loadDictTable(SysDict sysDict)
	{
		String tabName = sysDict.getDicttable();

		// 该字典表的数据存储Map
		Map<String, Map<String, DictionaryRow>> groupRows = new HashMap();
		int dictType = Integer.parseInt(sysDict.getDicttype());
		switch (dictType)
		{
			// 单层数据字典
			case 1:

				log.debug("开始加载单层字典表：" + tabName + "...");
			// 查询的结果集
				List resultList = this.sysDictService.querySignalDictionaryData(sysDict);
				groupRows.put(Constants.DICT_TABLE_DEFAULT_GROUP_NAME, this.loadDictData(resultList, sysDict));
				break;
			// 多层数据字典
			case 2:

				log.debug("开始加载多层字典表：" + tabName + "...");
			// 查询的结果集
				List mutilDictResultList = this.sysDictService.queryMutilDictionaryData(sysDict);
				if (StringUtils.isEmpty(sysDict.getDictkeycolumn()) || StringUtils.isEmpty(sysDict.getDictparentcolumn()))
				{
					log.error("多层字典表[" + sysDict.getDicttable()
						+ "]没有填写“字典表关键字段名” 或者 “父编码字段名”");
					return;
				}
				groupRows.put(Constants.DICT_TABLE_DEFAULT_GROUP_NAME, this.loadDictData(mutilDictResultList, sysDict));
				break;
		}

		sysDict.setDictTableGroupRows(groupRows);
		String key = sysDict.getDicttable().toUpperCase();
		sysCachePoolXd.putCoreCacheElem(key, sysDict);
		log.debug("单层字典表[" + tabName + "]加载完成." + "一共加载了["
				+ groupRows.get(Constants.DICT_TABLE_DEFAULT_GROUP_NAME).size()
				+ "]行数据。");

	}

	/**
	 * 刷新所有字典表
	 */
	public void refreshAll()
	{
		List<SysDict> list = sysDictService.queryAllDictionaryInfo();
		for (SysDict dictTable : list)
		{
			refreshDictTable(dictTable);
		}
	}

	/**
	 * 刷新某个字典表
	 */
	public void refresh(List<String> dictIds)
	{
		for (String dictId : dictIds)
		{
			SysDict dictTable = sysDictService.queryDictionaryById(dictId);
			refreshDictTable(dictTable);
		}
	}

	/**
	 * 刷新某个字典表
	 * 
	 * @param dictTable
	 */
	private void refreshDictTable(SysDict dictTable)
	{
		this.sysCachePoolXd.removeCoreCacheElem(dictTable.getDicttable());
		loadDictTable(dictTable);
	}

	/**
	 * 加载一个字典表
	 * 
	 * @param resultList
	 * @param sysDict
	 * @return
	 */
	private Map<String, DictionaryRow> loadDictData(List resultList, SysDict sysDict)
	{
		Map<String, DictionaryRow> dictMap = new LinkedMap();
		for (int i = 0; i < resultList.size(); i++)
		{
			Map resultMap = (Map) resultList.get(i);
			String codeFieldValue = (String) resultMap.get(sysDict.getDictcodecolumn());
			// 加载数据
			DictionaryRow row = new DictionaryRow();
			row.setCode(codeFieldValue);
			row.setTitle((String) resultMap.get(sysDict.getDictnamecolumn().toUpperCase()));
			if (null != resultMap.get(sysDict.getDictparentcolumn()))
			{
				row.setParentCode((String) resultMap.get(sysDict.getDictparentcolumn().toUpperCase()));
			}
			dictMap.put(codeFieldValue, row);
		}

		return dictMap;
	}

}
