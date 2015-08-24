<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>树状菜单</title>
</head>
<body>
<div id="tree_div" style="overflow: auto; width: 100%; height: 100%"></div>
</body>

</html>
<script >
var menus;
//全局路径
var context = '<%= request.getContextPath() %>';

Ext.onReady(function(){	

    // 标准菜单树
    var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:context+'/authManage/userLoginController.spr?action=getSystemMenu',
    	baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider 属性 
    });   
      
   systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                //systemTreeLoader.baseParams.id = node.attributes.id; 
                alert(node.attributes.checked);
                systemTreeLoader.baseParams = {'id': node.attributes.id,'menuId': node.attributes.menuId};
            }, systemTreeLoader);

    tree = new Ext.tree.TreePanel({
    	id:'main_tree',
        el:'tree_div',
        useArrows:true,
        autoScroll:true,
        collapsed :false,
        rootVisible:true,
        containerScroll: true,
        border:false,
        animate:false,
        checkModel:'cascade',    //对树的级联多选 
        onlyLeafCheckable: false,//对树所有结点都可选       
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '系统菜单',
        draggable:false,
        id:'-1'
    });

    tree.setRootNode(root); 
	// 添加右键菜单
	//tree.on("contextmenu", showMainTree_Menu);
    //tree.on('click',treeClick);
    tree.on("check",function(node,checked){alert(node.text+" = "+checked)}); //注册"check"事件 
    tree.render(); 
    tree.expand(); 
});

</script>