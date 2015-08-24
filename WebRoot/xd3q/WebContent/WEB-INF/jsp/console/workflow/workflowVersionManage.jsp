<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
</style>
</head>
<body>
<div id="div_center" style="width:100%;height:100%">
<iframe id="workflowframe" scrolling="auto" frameborder="0" width="100%" height="100%" src=""></iframe>
</div>

<div id="div_west_tree"></div>
<div id="div_history_west_tree"></div>
</body>
</html>
<script type="text/javascript">
function addworkflow(node,e){
	alert(node.id);
}

function deleteworkflow(node,e){
	alert(node.id);
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	//document.frames["workflowframe"].location.href="common/test1.jsp";
   	var useTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByLoginUser'
    });
      
    useTreeLoader.on("beforeload", function(treeLoader, node) {   
                useTreeLoader.baseParams.id = node.attributes.id;   
            }, useTreeLoader);
    
    var usetree = new Ext.tree.TreePanel({
    	id:'use_tree',
        el:'div_west_tree',
        layout:'fit',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: useTreeLoader
    });
    
    var useroot = new Ext.tree.AsyncTreeNode({
        text: '在用流程信息',
        draggable:false,
        id:'-1'
    });
    usetree.setRootNode(useroot);
    usetree.render();
    usetree.on('click',usetreeclick);
    useroot.expand();
    
    function usetreeclick(node,e){
    }
    
    usetree.on("contextmenu",function(node,e){
		var treeMenu = new Ext.menu.Menu
        ([
            {
            	xtype:"button",
            	text:"发布",
            	icon:"images/fam/add.gif",
            	pressed:true,
            	handler:function(){
            		addworkflow(node,e);
            	}
            },
            {
            	xtype:"button",
            	text:"注消",
            	icon:"images/fam/delete.gif",
            	pressed:true,
            	handler:function(){
            		deleteworkflow(node,e);
            	}
            }
        ]);
		treeMenu.showAt(e.getPoint());
   });
    
    
    var historyTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByLoginUser'
    });
      
    historyTreeLoader.on("beforeload", function(treeLoader, node) {   
                historyTreeLoader.baseParams.id = node.attributes.id;   
            }, historyTreeLoader);
    
    var historytree = new Ext.tree.TreePanel({
    	id:'history_tree',
        el:'div_history_west_tree',
        layout:'fit',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: historyTreeLoader
    }); 
    
    var historyroot = new Ext.tree.AsyncTreeNode({
        text: '历史流程信息',
        draggable:false,
        id:'-1'
    });
    historytree.setRootNode(historyroot);
    historytree.render();
    historytree.on('click',historyreeclick);
    historyroot.expand();
    
    historytree.on("contextmenu",function(node,e){
		var treeMenu = new Ext.menu.Menu
        ([
            {xtype:"button",text:"启用",icon:"images/fam/add.gif",pressed:true}
        ]);
		treeMenu.showAt(e.getPoint());
    });
    
    function historyreeclick(node,e){
    }
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			width:200,
			title:"流程信查询",
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
            	title:'在用流程查询',
				border:false,
				layout:'fit',
				contentEl: 'div_west_tree',
				tools:[{
					id:'refresh',   
                    qtip: '刷新角色树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('use_tree');
                        tree.body.mask('Loading', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function(){
                        	tree.body.unmask();
                        }, 1000);
                     }
				}],
				items:[usetree]
            },{
            	title:'历史流程查询',
				border:false,
				layout:'fit',
				contentEl: 'div_history_west_tree',
				tools:[{
					id:'refresh',   
                    qtip: '刷新角色树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('history_tree');
                        tree.body.mask('Loading', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function(){
                        	tree.body.unmask();
                        }, 1000);
                     }
				}],
				items:[historytree]
            }]
		},{
			region:"center",
			layout:'border',
			items:[{
					region:"center",
					title:"流程图",
					autoScroll:true, 
					layout:'fit',
					items:[{
						xtype:'panel',
						border:false,
						layout:'fit',
						contentEl:'div_center'
					}]
				}]
		}]
	});
});
</script>