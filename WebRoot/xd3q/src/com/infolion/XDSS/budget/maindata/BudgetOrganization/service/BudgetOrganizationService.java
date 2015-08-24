/*
 * @(#)BudgetOrganizationService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分05秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrganization.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetOrganizationGen.service.BudgetOrganizationServiceGen;
import com.infolion.platform.dpframework.uicomponent.tree.domain.Tree;
import com.infolion.platform.dpframework.uicomponent.tree.domain.TreeNode;
import com.infolion.platform.dpframework.uicomponent.tree.service.TreeService;
import com.infolion.platform.dpframework.util.StringUtils;

/**
 * <pre>
 * 预算组织(BudgetOrganization)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class BudgetOrganizationService extends BudgetOrganizationServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private TreeService treeService;

	/**
	 * 查找以指定预算组织为根节点的树中名称为subBudOrgName的子节点<br>
	 * 遍历树节点采用前序遍历，当找到第一个名字匹配的节点时，立即返回
	 * 
	 * @param rootBudOrgId
	 *            要节点预算组织ID
	 * @param subBudOrgName
	 *            要查找的子节点名
	 * @return 要查找的子节点实例，如果找不到返回null
	 */
	public BudgetOrganization getSubOrgByName(String rootBudOrgId, String subBudOrgName)
	{
		Tree budorgTree = createBudOrgTree();
		TreeNode node = treeService.getTreeNode(budorgTree, rootBudOrgId);
		BudgetOrganization budgetOrganization = null;
		String nodeId = search(budorgTree, node, subBudOrgName);
		if (nodeId != null)
		{
			budgetOrganization = this._get(nodeId);
		}
		return budgetOrganization;
	}

	private String search(Tree tree, TreeNode node, String subBudOrgName)
	{
		if (StringUtils.equals(node.getText(), subBudOrgName))
		{
			return node.getNodeId();
		}
		List<TreeNode> list = treeService.getTreeNodes(tree, node);
		for (TreeNode treeNode : list)
		{
			String nodeId = search(tree, treeNode, subBudOrgName);
			if (nodeId != null)
			{
				return nodeId;
			}
		}
		return null;

	}

	/**
	 * 构造一个预算组织树，未构造任何节点
	 * 
	 * @return
	 */
	public static Tree createBudOrgTree()
	{
		Tree _budorgTree = new Tree();
		_budorgTree.setIdColumnName("BUDORGID");
		_budorgTree.setParentColumnName("BUDUPORGID");
		_budorgTree.setTableName("YBUDORG");
		_budorgTree.setTitleColumnName("BUDORGNAME");
		return _budorgTree;
	}

}