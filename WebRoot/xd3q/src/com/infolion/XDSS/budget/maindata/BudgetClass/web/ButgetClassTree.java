package com.infolion.XDSS.budget.maindata.BudgetClass.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.XDSS.budget.maindata.BudgetClass.dao.BudgetClassHibernateDao;
import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.platform.dpframework.uicomponent.tree.TreeSource;
import com.infolion.platform.dpframework.uicomponent.tree.domain.Tree;
import com.infolion.platform.dpframework.uicomponent.tree.domain.TreeNode;
import com.infolion.platform.dpframework.uicomponent.tree.service.TreeBaseService;
import com.infolion.platform.dpframework.util.DBTypeUtils;

@Service
public class ButgetClassTree extends TreeBaseService implements TreeSource {
	
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BudgetClassHibernateDao budgetClassHibernateDao;
	
	public void setBudgetClassHibernateDao(BudgetClassHibernateDao budgetClassHibernateDao)
	{
		this.budgetClassHibernateDao = budgetClassHibernateDao;
	}

	public Tree getTree(Tree tree) {
		// 得到包含初始参数的TreeNode节点
		TreeNode node = tree.getNode(0);
		String idColumnName = tree.getIdColumnName();
		String parentColumnName = tree.getParentColumnName();
		String titleColumnName = tree.getTitleColumnName();
		String tableName = tree.getTableName();
		String condition = "";
		
		if (!"-1".equals(node.getNodeId())){
			BudgetClass budgetClass = this.budgetClassHibernateDao.get(node.getNodeId());
			
			if ("2".equals(budgetClass.getSourcetype())){
				idColumnName = "budclassid";
				parentColumnName = "budupclassid";
				if (DBTypeUtils.MSSQL.equals(DBTypeUtils.getDbType()))
					titleColumnName = "(case when version = -1 then '临时版' else '第'+CONVERT(char,version)+'版' end)";
				else if (DBTypeUtils.ORACLE.equals(DBTypeUtils.getDbType()))
					titleColumnName = "(case when version = -1 then '临时版' else concat(concat('第',version),'版') end)";
				else if (DBTypeUtils.DB2.equals(DBTypeUtils.getDbType()))
					titleColumnName = "(case when version = -1 then '临时版' else concat(concat('第',CHAR(version)),'版') end)";
				tableName = "YBUDCLASS";
				condition = " order by version";
			}else{
				idColumnName = "budclassid";
				parentColumnName = "budupclassid";
				titleColumnName = "budclassname";
				tableName = "YBUDCLASS";
				condition = "";
			}
		}else{
			idColumnName = "budclassid";
			parentColumnName = "budupclassid";
			titleColumnName = "budclassname";
			tableName = "YBUDCLASS";
			condition = "";
		}
		
		// 查询出子节点
		List<TreeNode> treeNodes = getTreeJdbcDao().getTreeNodes(tree.getRootValue(), idColumnName, titleColumnName, parentColumnName, tableName, condition);
		
		if (treeNodes != null && treeNodes.size() > 0)
		{
			for (TreeNode treeNode : treeNodes)
			{
				int childrenNumber = 0;
				childrenNumber = getTreeJdbcDao().getChildrenNumber(treeNode.getNodeId(), "budupclassid", "YBUDCLASS", condition);
				
				if (childrenNumber > 0)
					treeNode.setHasChildren(true);
			}
		}
		tree.removeNode(0);
		tree.setNodes(treeNodes);
		return tree;
	}

}
