package test.infolion.platform.dpframework.uicomponent.tree.service;

import java.util.ArrayList;
import java.util.List;

//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.tree.domain.Tree;
import com.infolion.platform.dpframework.uicomponent.tree.domain.TreeNode;
import com.infolion.platform.dpframework.uicomponent.tree.service.TreeService;
public class TreeServiceTest extends JdbcServiceTest{

	@Autowired
	private TreeService treeService;
	
	public TreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

	public void testGetTreeData() {
/*		Tree tree = new Tree();
		tree.setTableName("T_SYS_DEPT");
		tree.setIdColumnName("DEPT_ID");
		tree.setParentColumnName("PDEPT_ID");
		tree.setTitleColumnName("DEPT_NAME");
		tree.setRootValue("-1");
		tree.setNode(new TreeNode());
		String treeDate = treeService.getTreeData(tree);
		log.info(treeDate);
*/	}

	public void testGetSubNodes() {
		String jo = "[{\"id\":\"usercompany\",\"hrefTarget\":\"dateView\",\"entityAttributes\":{\"User.creator\":\"administrator\"},\"text\":\"组织机构信息\",\"dbclick\":\"testb\",\"level\":\"\",\"cls\":\"\",\"click\":\"testa\",\"children\":[],\"leaf\":false,\"href\":\"page.jsp?dept_id=usercompany\"}]";
		jo = jo.replaceAll("\"children\":\\[\\]", "");
		log.debug(jo);
	}
	
	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList<String>();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}
}
