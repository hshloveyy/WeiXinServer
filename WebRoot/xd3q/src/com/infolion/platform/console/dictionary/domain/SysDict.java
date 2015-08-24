/*
 * @(#)TSysDict.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-11-26
 *  描　述：创建
 */

package com.infolion.platform.console.dictionary.domain;

import java.util.HashMap;
import java.util.Map;

import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class SysDict extends BaseObject {
	// 单层字典表
	public final static int SINGLE_TABLE = 1;
	// 多层字典表
	public final static int MUTIL_TABLE = 2;
	/**
	 * 以group分组的map，
	 */
	private Map<String, Map<String, DictionaryRow>> dictTableGroupRows = new HashMap<String, Map<String, DictionaryRow>>();

	private String dictid;
	@ValidateRule(dataType = DataType.STRING, label = "字典表名", maxLength = 100, required = true)
	private String dicttable;
	@ValidateRule(dataType = DataType.STRING, label = "编码字段名", maxLength = 100, required = true)
	private String dictcodecolumn;
	@ValidateRule(dataType = DataType.STRING, label = "名称字段名", maxLength = 100, required = true)
	private String dictnamecolumn;
	@ValidateRule(dataType = DataType.STRING, label = "字典表类型", maxLength = 1, required = true)
	private String dicttype;
	private String dicttypename;
	@ValidateRule(dataType = DataType.STRING, label = "字典关键字段名", maxLength = 100)
	private String dictkeycolumn;
	@ValidateRule(dataType = DataType.STRING, label = "父编码字段", maxLength = 100)
	private String dictparentcolumn;
	@ValidateRule(dataType = DataType.STRING, label = "备注", maxLength = 200)
	private String cmd;
	private String creator;
	private String createtime;

	public SysDict() {
		super();
	}

	public SysDict(String dictid, String dicttable, String dictcode_column,
			String dictname_column, String dicttype, String dictkey_column,
			String dictparent_column, String cmd, String creator,
			String createtime) {
		super();
		this.dictid = dictid;
		this.dicttable = dicttable;
		this.dictcodecolumn = dictcode_column;
		this.dictnamecolumn = dictname_column;
		this.dicttype = dicttype;
		this.dictkeycolumn = dictkey_column;
		this.dictparentcolumn = dictparent_column;
		this.cmd = cmd;
		this.creator = creator;
		this.createtime = createtime;
	}

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
	}

	public String getDicttable() {
		return dicttable;
	}

	public void setDicttable(String dicttable) {
		this.dicttable = dicttable;
	}

	public String getDictcodecolumn() {
		return dictcodecolumn;
	}

	public void setDictcodecolumn(String dictcodecolumn) {
		this.dictcodecolumn = dictcodecolumn;
	}

	public String getDictnamecolumn() {
		return dictnamecolumn;
	}

	public void setDictnamecolumn(String dictnamecolumn) {
		this.dictnamecolumn = dictnamecolumn;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getDictkeycolumn() {
		return dictkeycolumn;
	}

	public void setDictkeycolumn(String dictkeycolumn) {
		this.dictkeycolumn = dictkeycolumn;
	}

	public String getDictparentcolumn() {
		return dictparentcolumn;
	}

	public void setDictparentcolumn(String dictparentcolumn) {
		this.dictparentcolumn = dictparentcolumn;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getDicttypename() {
		return dicttypename;
	}

	public void setDicttypename(String dicttypename) {
		this.dicttypename = dicttypename;
	}

	public Map<String, Map<String, DictionaryRow>> getDictTableGroupRows() {
		return dictTableGroupRows;
	}

	public void setDictTableGroupRows(
			Map<String, Map<String, DictionaryRow>> dictTableGroupRows) {
		this.dictTableGroupRows = dictTableGroupRows;
	}

}
