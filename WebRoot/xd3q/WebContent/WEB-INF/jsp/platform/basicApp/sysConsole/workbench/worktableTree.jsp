<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
request.setAttribute("contextPath",request.getContextPath());%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="toolbarDiv"></div>
<div id="treeDiv" class="search"></div>
</body>
<script type="text/javascript" defer="defer">
/*var toolbars = new Ext.Toolbar({
	items:[
			'-',
			{id:'_search',text:'新建',handler:_create,iconCls:'icon-cls'},'-',
			{id:'_cls',text:'查找',handler:_search,iconCls:'icon-cls'},'-'
			],
	renderTo:"toolbarDiv"
});*/

var tabs = new Ext.Panel({
	id:'mainPanel',
    renderTo: document.body,
    autoWidth:false,
    loadMask:true,
    frame:true,
    //tbar:toolbars,
    baseCls:'scarch',
    //defaults:{autoHeight: true},
    width:212,
    items:[
       		//{contentEl:'toolbarDiv',id:'toolbarDiv1',width:212},
			{contentEl:'treeDiv',id:'treeDiv1',autoScroll:true}
          ]
});//linkUrl="${contextPath}/basicApp/worktable/worktableController.spr?action=_view&operationType=userMessage"

function _create(){
	
}

function _search(){
	
}
</script>
<fisc:tree style="search" rootValue="-1" target="dataView" treeTitle="${treeTitle}" treeName="workbench" divId="treeDiv" whereSql="${whereSql}" needAutoLoad="true"></fisc:tree>
</html>