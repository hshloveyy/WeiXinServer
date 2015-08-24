<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/commons.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>树状菜单</title>
</head>
<body>
<input type="button" value="reload" onclick ="reload_div_tree('and deleted = 1');" >
<input type="button" value="delete" onclick ="delete_div_TreeNode();" >
<input type="button" value="reloadNode" onclick ="reload_div_treeNode();" >
<div id="div"></div>
</body>
<fisc:tree tableName="T_SYS_DEPT" onclick="testa" ondbclick="testb" style="" width="180" entityName = "com.infolion.platform.basicApp.authManage.domain.User"
 rootValue="-1" idColumnName="dept_id" parentColumnName="pdept_id" titleColumnName="dept_name" whereSql=" and 1=1"
linkUrl="page.jsp" target="dateView" treeTitle="测试树" divId="div" height="500" needAutoLoad="true" orderBySql="display_no"></fisc:tree>
<script type="text/javascript">
  function testa(node,e){
	  //e.stopEvent();
	  var info = "nodeId="+node.id+"\r\nnodeText="+node.text+"\r\nnodeHref="+node.attributes.href;
	  info += "\r\nentityAttributes="+Ext.util.JSON.encode(node.attributes.entityAttributes);
	  alert(info);
  }
  function testb(node,e){
	  alert("bbbbbbbb\r\nnodeId="+node.id+"\r\nnodeText="+node.text+"\r\nnodeHref="+node.attributes.href);
  }
</script>
</html>