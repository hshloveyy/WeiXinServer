/*
 * @(#)BoProcessDefinitionTree.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-6-11
 *  描　述：创建
 */

package com.infolion.platform.workflow.manager.web;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.uicomponent.tree.TreeSource;
import com.infolion.platform.dpframework.uicomponent.tree.domain.Tree;
import com.infolion.platform.dpframework.uicomponent.tree.domain.TreeNode;
import com.infolion.platform.dpframework.uicomponent.tree.service.TreeBaseService;
import com.infolion.platform.dpframework.util.DBTypeUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class BoProcessDefinitionTree extends TreeBaseService implements TreeSource
{

	/**
	 * 
	 * 
	 * @param tree
	 * @return
	 */
	public Tree getTree(Tree tree)
	{
		TreeNode node = tree.getNode(0);
		String level = Integer.toString(Integer.parseInt(node.getLevel()) + 1);
		String idColumnName = tree.getIdColumnName();
		String parentColumnName = tree.getParentColumnName();
		String titleColumnName = tree.getTitleColumnName();
		String tableName = tree.getTableName();
		String condition = "";
		if ("1".equals(node.getLevel()))
		{
			idColumnName = "processDefinitionName";
			parentColumnName = "appModel";
			titleColumnName = "bpName";
			tableName = "WF_BOPROCESSDEF";
			condition = " and length(bpdefid)>20 and memo not like 'DELETE'";
		}
		if ("2".equals(node.getLevel()))
		{
			idColumnName = "extProcessId";
			parentColumnName = "processDefinitionName";
			if (DBTypeUtils.MSSQL.equals(DBTypeUtils.getDbType()))
				titleColumnName = "'第'+CONVERT(char,VERSION)+'版'";
			else if (DBTypeUtils.ORACLE.equals(DBTypeUtils.getDbType()))
				titleColumnName = "concat(concat('第',VERSION),'版')";
			else if (DBTypeUtils.DB2.equals(DBTypeUtils.getDbType()))
				titleColumnName = "concat(concat('第',CHAR(VERSION)),'版')";
			tableName = "WF_PROCESSDEF";
			condition = " order by VERSION";
		}
		List<TreeNode> treeNodes = getTreeJdbcDao().getTreeNodes(tree.getRootValue(), idColumnName, titleColumnName, parentColumnName, tableName, condition);
		if("1".equals(node.getLevel())) condition = "";
		if (treeNodes != null && treeNodes.size() > 0)
		{
			for (TreeNode treeNode : treeNodes)
			{
				treeNode.setName(idColumnName);
				treeNode.setLevel(level);
				treeNode.setText(treeNode.getText());
				String url = "";
				if ("3".equals(level))
				{
					url = tree.getLinkUrl();
					if (url.indexOf("?") < 0)
					{
						url += "?" + idColumnName + "=" + treeNode.getNodeId();
					}
					else
						url += "&" + idColumnName + "=" + treeNode.getNodeId();
				}
				treeNode.setOnclick(node.getOnclick());
				treeNode.setOndbclick(node.getOndbclick());
				treeNode.setTarget(node.getTarget());
				int childrenNumber = 0;
				if ("1".equals(level))
					childrenNumber = getTreeJdbcDao().getChildrenNumber(treeNode.getNodeId(), "appModel", "WF_BOPROCESSDEF", " and length(bpdefid)>20 and memo not like 'DELETE'");
				if ("2".equals(level))
					childrenNumber = getTreeJdbcDao().getChildrenNumber(treeNode.getNodeId(), "processDefinitionName", "WF_PROCESSDEF", condition);
				if (childrenNumber > 0)
					treeNode.setHasChildren(true);

			}
		}
		tree.removeNode(0);
		tree.setNodes(treeNodes);
		return tree;
	}
}
