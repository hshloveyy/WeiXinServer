/*
 * @(#)TreeDictionaryTagController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-6
 *  描　述：创建
 */

package com.infolion.platform.component.dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.infolion.platform.console.menu.domain.AsyncTreeCheckNode;
import com.infolion.platform.console.menu.domain.AsyncTreeNode;
import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;

public class TreeDictionaryTagController extends BaseMultiActionController {
	public void defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strParentId = request.getParameter("id");
		String strDictName = request.getParameter("dictName");
		String strSelectValue = request.getParameter("selectvalue");

		Map<String, DictionaryRow> sysDictMap = SysCachePoolUtils
				.getDictTableGroup(strDictName);

		List<AsyncTreeCheckNode> treeNodelist = new ArrayList();

		for (Iterator iterator = sysDictMap.keySet().iterator(); iterator
				.hasNext();) {
			String code = (String) iterator.next();
			DictionaryRow dictionaryRow = sysDictMap.get(code);
			if (dictionaryRow.getParentCode()==null || !strParentId.equals(dictionaryRow.getParentCode()))
				continue;
			AsyncTreeCheckNode asynTreeNode = new AsyncTreeCheckNode();
			asynTreeNode.setId(dictionaryRow.getCode());
			asynTreeNode.setText(dictionaryRow.getTitle());
			asynTreeNode.setCls("folder");
			asynTreeNode.setHrefTarget("");
			// 如果孩子结点数为０则为叶结点
			if (hasSubNode(sysDictMap,dictionaryRow.getCode()))
				asynTreeNode.setLeaf(false);
			else
				asynTreeNode.setLeaf(true);

			//判断该节点是否选中
			if (hasNodeSelect(dictionaryRow.getCode(),strSelectValue))
				asynTreeNode.setChecked(true);
			else
				asynTreeNode.setChecked(false);

			treeNodelist.add(asynTreeNode);
		}

		JSONArray ja = JSONArray.fromObject(treeNodelist);

		String jsontext = ja.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	private boolean hasSubNode(Map<String, DictionaryRow> sysDictMap,String parentCode)
	{
		for (Iterator iterator = sysDictMap.keySet().iterator(); iterator
		.hasNext();) {
			String code = (String) iterator.next();
			DictionaryRow dictionaryRow = sysDictMap.get(code);
			if (parentCode.equals(dictionaryRow.getParentCode()))
				return true;
		}
		return false;
	}
	
	private boolean hasNodeSelect(String nodeCode,String SelectList){
		String[] codeList = SelectList.split("\\|");
		
		for (int i=0;i<codeList.length;i++){
			if (nodeCode.equals(codeList[i]))
				return true;
		}
		
		return false;
	}
}
