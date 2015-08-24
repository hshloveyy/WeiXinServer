/*
 * @(#)SysCachePoolUtils.java
 * 版权声明    福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */
package com.infolion.platform.sys.cache;

import java.util.Collection;
import java.util.Map;

import net.sf.ehcache.Element;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.console.dictionary.domain.SysDict;
import com.infolion.platform.sys.Constants;
import com.infolion.platform.sys.cache.core.SysCachePoolXd;

/**
 * 
 * <pre>
 * 缓存池工具类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SysCachePoolUtils {
	private static SysCachePoolXd sysCachePoolXd;

	/**
	 * 设置系统缓存池
	 * 
	 * @param sysCachePoolXd
	 */
	public static void setSysCachePool(SysCachePoolXd sysCachePoolXd)
	{
		SysCachePoolUtils.sysCachePoolXd = sysCachePoolXd;
	}

	/**
	 * 获得一张字典表
	 * 
	 * @param dictCode
	 * @return
	 */
	public static SysDict getDictTable(String dictCode) {
		Assert.notNull(sysCachePoolXd, "缓存池没有初始化,请先启动Spring容器进行初始化。");
		dictCode = dictCode.toUpperCase();
		Element elem = sysCachePoolXd.getCoreCache().get(dictCode);
		if (elem != null)
			return (SysDict) elem.getValue();
		else
			return null;
	}

	public static Collection<String> getDictTableGroupNames(String dictCode) {
		SysDict table = getDictTable(dictCode);
		return table.getDictTableGroupRows().keySet();
	}

	/**
	 * 返回字典表数据。使用键值对应方式
	 * 
	 * @param dictCode
	 * @return
	 */
	public static Map<String, String> getDictDataMap(String dictCode) {
		return getDictDataMap(dictCode,
				Constants.DICT_TABLE_DEFAULT_GROUP_NAME);
	}

	/**
	 * 返回字典表数据。使用键值对应方式
	 * 
	 * @param dictCode
	 * @param groupValue
	 * @return
	 */
	public static Map<String, String> getDictDataMap(String dictCode,
			String groupValue) {
		if (StringUtils.isBlank(groupValue))
			groupValue = Constants.DICT_TABLE_DEFAULT_GROUP_NAME;
		Map<String, DictionaryRow> rows = getDictTableGroup(dictCode,
				groupValue);
		Map<String, String> values = new LinkedMap();
		if (rows != null)
			for (DictionaryRow row : rows.values()) {
				values.put(row.getCode(), row.getTitle());
			}
		return values;
	}

	/**
	 * 根据字典表的键返回对应的值
	 * 
	 * @param dictCode
	 * @param key
	 * @return
	 */
	public static String getDictDataValue(String dictCode, String key) {
		return getDictDataValue(dictCode,
				Constants.DICT_TABLE_DEFAULT_GROUP_NAME, key);
	}

	/**
	 * 根据字典表的键返回对应的值
	 * 
	 * @param dictCode
	 * @param groupValue
	 *            分组
	 * @param key
	 * @return
	 */
	public static String getDictDataValue(String dictCode, String groupValue,
			String key) {
		if (StringUtils.isBlank(groupValue))
			groupValue = Constants.DICT_TABLE_DEFAULT_GROUP_NAME;
		Map<String, String> map = getDictDataMap(dictCode, groupValue);
		if (map != null)
			return map.get(key);
		else
			return "";
	}

	/**
	 * 返回默认的分组字典表
	 * 
	 * @param dictCode
	 * @return
	 * @see DictTableRow
	 * @see SysDictTable
	 */
	public static Map<String, DictionaryRow> getDictTableGroup(String dictCode) {
		return getDictTableGroup(dictCode,
				Constants.DICT_TABLE_DEFAULT_GROUP_NAME);
	}

	/**
	 * 返回某个分组字典表
	 * 
	 * @param dictCode
	 * @param groupValue
	 * @return
	 * @see DictTableRow
	 * @see SysDictTable
	 */
	public static Map<String, DictionaryRow> getDictTableGroup(String dictCode,
			String groupValue) {
		if (StringUtils.isBlank(groupValue))
			groupValue = Constants.DICT_TABLE_DEFAULT_GROUP_NAME;
		Map<String, DictionaryRow> groupValues = null;
		SysDict dictTable = getDictTable(dictCode);
		if (dictTable != null)
			groupValues = dictTable.getDictTableGroupRows().get(
					groupValue.toUpperCase());
		return groupValues;
	}

	/**
	 * 根据key返回自定义值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getCustomValue(String key) {
		Assert.notNull(sysCachePoolXd, "缓存池没有初始化,请先启动Spring容器进行初始化。");
		Element elem = sysCachePoolXd.getCustomCache().get(key);
		if (elem != null)
			return elem.getValue();
		else
			return null;
	}
}
