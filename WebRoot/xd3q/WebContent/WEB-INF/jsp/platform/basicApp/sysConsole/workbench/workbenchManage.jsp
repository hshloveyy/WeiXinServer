<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作台</title>
</head>
<body>
<fisc:tree style="" rootValue="-1" target="" treeTitle="${treeTitle}"
 onclick="treeClick" treeName="workbench" divId="div_tree" whereSql="${whereSql}" needAutoLoad="true">
 </fisc:tree>

<div id="div_tree"></div>
<div id="div_north" ></div>

</body>
<script type="text/javascript" defer="defer">
var tab ;
//树的单击事件
function treeClick(node){
	if(node.leaf)
	{
		if(node.attributes.url)
		{
			var tab = Ext.getCmp("tab");
			tab.setTitle(node.attributes.text);
			tab.setSrc(node.attributes.url);
		}
	}
}

Ext.onReady(function(){
   tab = new Ext.ux.ManagedIframePanel({
	    id:'tab',
	    title:'待办事项',
	    name:'tab',
		defaultSrc:"<%=request.getContextPath()%>/basicApp/workbench/task/taskController.spr?action=_view",
        height:500
	   });
   
	//布局
   var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'center', 
			border:false,
			layout:'border', 
			items:[{
				region:'north',
				height:0,
	            border:false,
				contentEl:'div_north'
			},{
	            region:"west",
	            title:"",
	            split:true,          //可改变框体大小
	            collapsible: true,   //可收缩
	            width: 200,
	            minSize: 175,
	            maxSize: 400,
	            margins:'0 0 0 5',
	            layout:'fit',
	            layoutConfig:{
	               animate:true
	            },
	            items:[{
	                title:'工作台',
                    border:false,
                    layout:'fit',
	                tools:[{
	                    id:'refresh',   
	                    qtip: '刷新',
	                     handler: function(event, toolEl, panel) {
	                		div_tree_tree.body.mask('刷新中...');
	                		reload_div_tree_tree();
	                        div_tree_tree.root.collapse(true, false);
	                        setTimeout(function(){
	                        	div_tree_tree.body.unmask();
	                        }, 1000);
	                     }
	                },{
	                id:'maximize',
	                qtip:'展开树',
	                handler:function(event, toolEl, panel) {
	                	div_tree_tree.expandAll();
	                }
	                },{
	                id:'minimize',
	                qtip:'收起树',
	                handler:function(event, toolEl, panel) {
	                	div_tree_tree.collapseAll();
	                }}],
	                items:[{autoScroll:true,contentEl:'div_tree'}]
	            }]
			},{
				region:'center',
	    		autoScroll: false,
	    		autoWidth: false ,
	    		autoHeight: false ,
	            border:false,
	            items:[tab]
			}]
		   	}]
		});
});
</script>
</html>