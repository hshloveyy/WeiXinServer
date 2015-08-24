<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权-菜单</title>
</head>
<body>
<div id="div_menu"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';
//操作类别，菜单(menu)、角色(role)、方法(method)、流程(workflow)
var operType='menu' ;
var authMenuColumnTree ;

/**
* 回调函数 -权限转移给 
*/
function authRemoveCallBack()
{   
	authMenuColumnTree.body.mask();
	authMenuColumnTree.root.reload();
	authMenuColumnTree.root.collapse(true, false);
   	authMenuColumnTree.expandAll();
    setTimeout(function(){
    	authMenuColumnTree.body.unmask();
    }, 1000); 

	window.parent.alreadyRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
	window.parent.alreadyRemoveColumnTree.root.reload();
	window.parent.alreadyRemoveColumnTree.root.collapse(true, false);
     setTimeout(function(){
    	 window.parent.alreadyRemoveColumnTree.body.unmask();
     }, 1000); 
	//window.parent.reload_div_alreadyRemove_grid();
}

/**
* 方法-权限转移给
*/       
function _menuAuthRemove() 
{
	var menuIdLst="";
	var authresourceIdLst ="";
	var menuNodeIdLst = "";
	var uuIds ="";
    var strmsg ="";
	var checkedNodes = authMenuColumnTree.getChecked();
	if(checkedNodes.length <= 0)
	{
		_getMainFrame().showInfo("请选择要转移的菜单信息！");
		return ;
	}
	
	for(var i=0;i<checkedNodes.length;i++){
		var node = checkedNodes[i];
		//alert(node.attributes.leaf + " : " + node.attributes.alerdyRemove + " : " +  node.attributes.menuId + " : " + node.attributes.authresourceId + " : " + node.attributes.nodeDesc);
        if(node.attributes.leaf)
        {
            if(node.attributes.alerdyRemove =='N' )
            {
            	authresourceIdLst = authresourceIdLst + node.attributes.authresourceId + ",";
            	menuIdLst = menuIdLst + node.attributes.menuId + ",";
            	menuNodeIdLst = menuNodeIdLst  + node.attributes.nodeId + ",";
            }
            else if(node.attributes.alerdyRemove =='Y')
            {
            	var checkbox = node.getUI().checkbox;
                if(checkbox.checked)
                {
                    //去除选中，刷新节点数据
                	checkbox.checked = false;
                	node.attributes.checked = false;
                }
				strmsg = strmsg + node.attributes.nodeDesc + ",";	
            }
        }
		//node.getUI().checkbox.checked = false;
		//node.attributes.checked = false;
	}

	//alert(authresourceIdLst + "<br>" + menuIdLst  + "<br>"   + menuNodeIdLst);

	if(strmsg != "")
	{
		_getMainFrame().showInfo('菜单名:' + strmsg + " 已经转移过不能再次转移!系统自动会去除选中，请重新操作!");
		return;	
	}
	
	if(!authresourceIdLst)
	{
		for(var i=0;i<checkedNodes.length;i++){
			var node = checkedNodes[i];
			 if(!node.attributes.leaf){
				 var checkbox = node.getUI().checkbox;
	                if(checkbox.checked)
	                {
	                    //去除选中，刷新节点数据
	                	checkbox.checked = false;
	                	node.attributes.checked = false;
	                } 
			 }
			 else if(node.attributes.alerdyRemove =='Y'){
				 var checkbox = node.getUI().checkbox;
	                if(checkbox.checked)
	                {
	                    //去除选中，刷新节点数据
	                	checkbox.checked = false;
	                	node.attributes.checked = false;
	                } 
			 }
				 
		}
		_getMainFrame().showInfo("请选择要转移的菜单叶子节点！");
		return ;
	}

   uuIds = authresourceIdLst ;
   _getMainFrame().ExtModalWindowUtil.show('权限转移给',
			    context + '/index/indexMainController.spr?action=authRemove&operType=menu&uuIds='+uuIds +'&menuIdLst='+ menuIdLst  + '&bpdefids=' + menuNodeIdLst,
				'',
				authRemoveCallBack,
				{width:480,height:330});
}


Ext.onReady(function(){

    var toolbars = new Ext.Toolbar({
   	 		items:[
   	 				'-','->',
   	 				{id:'_menuAuthRemove',text:'权限转移给',handler:_menuAuthRemove, iconCls:'icon-add'},'-',
   	 				{text:' 展开树',handler:function(){authMenuColumnTree.expandAll();}},
   	 				{text:' 收起树',handler:function(){authMenuColumnTree.collapseAll();}},
	   	 			{text:' 刷新树',handler:function(){
   	 				    authMenuColumnTree.body.mask('Loading', 'x-mask-loading');
   	 			        authMenuColumnTree.root.reload();
   	 			        authMenuColumnTree.root.collapse(true, false);
	   			             setTimeout(function(){
	   			            	authMenuColumnTree.body.unmask();
	   			             }, 1000); 
	   		 				}},
   	 				'-'
   	 				]
   	 });

   	 var authMenuTreeLoader = new Ext.tree.TreeLoader({
       	 url:context+'/index/indexMainController.spr?action=getAuthMenuColumnTree',
         baseAttrs:{uiProvider:Ext.ux.ColumnTreeCheckNodeUI}//如果不需要checkbox ,则需要使用 Ext.tree.ColumnTreeNodeUI  
       	//baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider 属性 
       });   
         
   	 authMenuTreeLoader.on("beforeload", function(treeLoader, node) {   
                   //alert(node.attributes.menuId + ":" + node.attributes.authresourceId + ":" + node.attributes.nodeDesc);
                   authMenuTreeLoader.baseParams = {'id': node.attributes.id,'nodeId': node.attributes.nodeId,'menuId': node.attributes.menuId,'authresourceId': node.attributes.authresourceId};
               }, authMenuTreeLoader);


   	 authMenuColumnTree = new Ext.tree.ColumnTree({   
   				id:'authMenuColumnTree',
   		        region:'center',
   		        split:true,
   		        el:'div_menu',
   		        height:390,
   		        //collapsible: true,
   		        cmargins:'0 0 0 0',
   		        animCollapse:false,
   		        animate: false,
   		        collapseMode:'mini',
   			    border: false,   
   			    lines: true,  
   			    tbar:[toolbars], 
   			    rootVisible: false,   
   			    autoScroll:true,   
   			    checkModel:'cascade',//级联多选，如果不需要checkbox,该属性去掉   
   			    onlyLeafCheckable: false,//所有结点可选，如果不需要checkbox,该属性去掉   
   			    loader:authMenuTreeLoader,   
   			    root: new Ext.tree.AsyncTreeNode({ id:'-1'}),   
   			    columns:[ 
   			        //{ header:'', width:80, dataIndex:''},  ,align :'center'
   			    	//{ header:'节点ID', width:180, dataIndex:'authresourceId' },
   			        { header:'节点名称', width:180, dataIndex:'nodeDesc' },
   			        { header:'是否转移', width:100, dataIndex:'alerdyRemove',align :'center',headeralign :'center'}
   			    ],  
   	            listeners: {
   	                beforecheck : function(n) {
   	                	if (n.attributes.checked) return true;
   						//if( !n.expanded && !n.childrenRendered ) n.expand();
   						if (n.parentNode.id == '-1'){
   							var checkedNodes = authMenuColumnTree.getChecked();
   							for(var i=0;i<checkedNodes.length;i++){
   								var node = checkedNodes[i];
   								//if(node.id != n.id){
   									node.getUI().checkbox.checked = false;
   									node.attributes.checked = false;
   									authMenuColumnTree.fireEvent('check', node, false);
   								//}
   							}
   						}
   	                	return true;
   	                },
   	                
   	                afterrenderer : function(n) {
   	                	var checkbox = n.getUI().checkbox;
   	                	//if((typeof checkbox != 'undefined') && (n.parentNode != n.getOwnerTree().getRootNode())) 
   	   	                	//checkbox.disabled = false ;
   	                }
   	            }
   			}); 

   	//authMenuColumnTree.render(); 
   	//authMenuColumnTree.expand(); 
   	//authMenuColumnTree.expandAll();树全部展开

    var viewport = new Ext.Viewport({
        layout:'border',
        items:authMenuColumnTree
    });
    viewport.doLayout();   
});

</script> 
