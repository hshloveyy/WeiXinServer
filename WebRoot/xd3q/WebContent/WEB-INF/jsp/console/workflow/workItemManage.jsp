<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id = "div_west_tree" style="overflow: auto; width: 100%; height: 100%"></div>
<div id = "div_center_center"></div>
</body>
</html>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var root=new Ext.tree.TreeNode({
		id:"root",
		text:"我的事务",
		leaf:false
	});
	
	var waitForItem=new Ext.tree.TreeNode({
		id:'waitForItem',
		text:'待办事务',
		leaf:true
	});

    var finishWaitForItem=new Ext.tree.TreeNode({
		id:'finishWaitForItem',
		text:'我参与的事务',
		leaf:true
	});

    var advanceWarnItem=new Ext.tree.TreeNode({
		id:'advanceWarnItem',
		text:'我的预警信息',
		leaf:true
	});
	
	root.appendChild(waitForItem);
    root.appendChild(finishWaitForItem);
    root.appendChild(advanceWarnItem);
    
    var tree=new Ext.tree.TreePanel({
    	el:'div_west_tree',
    	layout:'fit',
		root:root,
		animate:true,
		enableDD:false,
		collapsed:false,
		border:false,
		rootVisible:false,
		containerScroll: true
	});
	
	tree.on('click',treeclick);
	function treeclick(node,e){
		if (node.leaf == true){
			var currentTab = Ext.getCmp("selecttab").getActiveTab();
			var mailTab = Ext.getCmp("selecttab");
			if (currentTab.id != node.id){
				mailTab.remove(currentTab);
				
				var tabHtme = '';
				
				if (node.id == 'waitForItem'){
					tabHtme = '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="workflowController.spr?action=waitForTransactWorkView"></iframe>';
				}
				if (node.id == 'finishWaitForItem'){
					tabHtme = '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="workflowController.spr?action=finishWorkView"></iframe>';
				}
				if (node.id == 'advanceWarnItem'){
					tabHtme = '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="xdss3/advanceWarn/advanceWarnInfoController.spr?action=_manage"></iframe>';
				}
				
				var newTab = new Ext.Panel({
					title: node.text,
					border:false,
					id:node.id,
					html: tabHtme
				});
				
				mailTab.add(newTab).show();
			}
		}
	}
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			title:"待办信息查询",
			split:true,			 //可改变框体大小
			collapsible: true,   //可收缩
            width: 200,
            minSize: 175,
            maxSize: 400,
            margins:'0 0 0 5',
            layout:'accordion',
            layoutConfig:{
               animate:true
            },
            items:[{
            	title:'目录树查询',
				border:false,
				contentEl: 'div_west_tree',
				items:[tree]
            }]
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'tabpanel',
				id:'selecttab',
				name:'selecttab',
            	plain:true,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
	                title: '待办事务',
	                id:'waitForItem',
            		name:'waitForItem',
	                layout:'fit',
	                html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="workflowController.spr?action=waitForTransactWorkView"></iframe>'
            	}]
			}]
		}]
	});
});
</script>