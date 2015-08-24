package com.infolion.platform.component.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infolion.platform.util.EasyApplicationContextUtils;
/**
 * 
 * @author 陈喜平
 *
 */
public class TreeDataSource {
	public TreeDataSource(){
		if(jdbc==null)
			jdbc = (JdbcTemplate)EasyApplicationContextUtils.getBeanByName("jdbcTemplate");
	}
	private JdbcTemplate jdbc;
	/**
	 * 
	 * @param sql
	 * @param parentId
	 * @param nodeId
	 * @return
	 */
	public List getRecords(String sql,String parentId,String nodeId){
		parentId = parentId.toUpperCase();
		String sql1 = "select * from ("+sql+")t where t."+parentId+"='"+nodeId+"' order by id";
		sql1 = sql1.toUpperCase();
		System.out.println(sql1);
		String hasLeafSql = "select "+parentId+" from ("+sql+")t group by t."+parentId +" order by t."+parentId;
		hasLeafSql = hasLeafSql.toUpperCase();
		List main = jdbc.queryForList(sql1);
		List parent = jdbc.queryForList(hasLeafSql);
		Iterator it = main.iterator();
		boolean flag = false;
		List list = new ArrayList();
		int pSize = parent.size();
		while (it.hasNext()) {
			Map map = (Map) it.next();
			Object id = map.get("ID");
			if (parent != null && pSize > 0) {
				for(int i=0;i<pSize;i++){
					Map p = (Map)parent.get(i);
					if(p.get(parentId)==id || id.equals(p.get(parentId))){
						map.put("cls", "folder");
						map.put("leaf", false);
						map.put("expandable", true);
						flag = true;
						break;
					}
				}
			}
			if(!flag){
				map.put("cls", "file");
				map.put("leaf", true);
				map.put("expandable", false);
			}
			flag = false;
			list.add(map);
		}
		return list;
	}
}
