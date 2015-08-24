/*
 * @(#)DictionaryTagHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-2
 *  描　述：创建
 */

package com.infolion.platform.component.dictionary;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.infolion.platform.sys.cache.SysCachePoolUtils;

/**
 * 
 * <pre>
 * 字典表组件tag处理类
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
public class DictionaryTagHandler {
	/**
	 * 根据字典表名称，返回字典表json串
	 * 
	 * @param dictName
	 * @return
	 */
	public static String getDictionaryJsonString(String dictName) {
		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup(dictName);
		JSONArray array = new JSONArray();
		for (Iterator iterator = sysDictMap.keySet().iterator(); iterator
				.hasNext();) {
			String code = (String) iterator.next();
			DictionaryRow dictionaryRow = sysDictMap.get(code);
			JSONObject object = new JSONObject();
			object.put("code", dictionaryRow.getCode());
			object.put("title", dictionaryRow.getTitle());
			array.add(object);
		}
		return array.toString();
	}

	/**
	 * 返回数组形式数据
	 * 
	 * @param dictName
	 * @return
	 */
	public static String getDictionaryArrayString(String dictName) {
		StringBuffer result = new StringBuffer();
		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup(dictName);
		String split="";
		result.append("[['','请选择'],");
		for (Iterator iterator = sysDictMap.keySet().iterator(); iterator
				.hasNext();) {
			String code = (String) iterator.next();
			DictionaryRow dictionaryRow = sysDictMap.get(code);
			result.append(split);
			result.append("['");
			result.append(dictionaryRow.getCode());
			result.append("','");
			result.append(dictionaryRow.getTitle());
			result.append("']");
			split=",";
		}
		result.append("]");
		return result.toString();
	}
	/**
	 * 返回带编码形式的字典表数据
	 * @param dictName
	 * @return
	 */
	public static String getDictionaryArrayStringWithCode(String dictName) {
		StringBuffer result = new StringBuffer();
		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup(dictName);
		String split="";
		result.append("[['','请选择'],");
		for (Iterator iterator = sysDictMap.keySet().iterator(); iterator
				.hasNext();) {
			String code = (String) iterator.next();
			DictionaryRow dictionaryRow = sysDictMap.get(code);
			result.append(split);
			result.append("['");
			result.append(dictionaryRow.getCode());
			result.append("','");
			result.append(dictionaryRow.getCode()+"("+dictionaryRow.getTitle()+")");
			result.append("']");
			split=",";
		}
		result.append("]");
		return result.toString();
	}
}
