<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权</title>
</head>

<body>
  	<div id="div_otherUserRemove"></div>
</body>
</html>

 
<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';
//被授权ColumnTree
var otherUserRemoveColumnTree ;

Ext.onReady(function(){

    var toolbars = new Ext.Toolbar({
   	  items:[
   				'-','->',
   				{text:' 展开树',handler:function(){otherUserRemoveColumnTree.expandAll();}},
   				{text:' 收起树',handler:function(){otherUserRemoveColumnTree.collapseAll();}},
   				{text:' 刷新树',handler:function(){
   					otherUserRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
   					otherUserRemoveColumnTree.root.reload();
   					otherUserRemoveColumnTree.root.collapse(true, false);
  		             setTimeout(function(){
  		            	otherUserRemoveColumnTree.body.unmask();
  		             }, 1000); 
  	 				}},
   				'-'
   				]
   	});
   	
	 var otherUserRemoveTreeLoader = new Ext.tree.TreeLoader({
    	url:context+'/index/indexMainController.spr?action=getOtherUserRemoveColumnTreeGroupByUser',
        baseAttrs:{uiProvider:Ext.ux.ColumnTreeCheckNodeUI}//如果不需要checkbox ,则需要使用 Ext.tree.ColumnTreeNodeUI  
    });   

	 otherUserRemoveTreeLoader.on("beforeload", function(treeLoader, node) {   
                otherUserRemoveTreeLoader.baseParams = {'resourceType': node.attributes.resourceType,
                                                          'authType': node.attributes.authType,
                                                    'userAuthMoveId': node.attributes.userAuthMoveId,
                                                        'toUserName': node.attributes.toUserName,
                                                        'resourceId': node.attributes.resourceId,
                                                        'name': node.attributes.name
                                                     };
            }, otherUserRemoveTreeLoader);

	 otherUserRemoveColumnTree = new Ext.tree.ColumnTree({   
				id:'otherUserRemoveColumnTree',
		        region:'center',
		        split:true,
		        el:'div_otherUserRemove',
		        tbar:[toolbars],
		        cmargins:'0 0 0 0',
		        animCollapse:false,
		        animate: false,
		        collapseMode:'mini',
			    border: false,   
			    lines: true,   
			    rootVisible: false,   
			    autoScroll:true,   
			    //checkModel:'cascade',//级联多选，如果不需要checkbox,该属性去掉   
			    //onlyLeafCheckable: false,//所有结点可选，如果不需要checkbox,该属性去掉   
			    loader:otherUserRemoveTreeLoader,   
			    root: new Ext.tree.AsyncTreeNode({ id:'-1'}),   
			    columns:[   
			        //{ header:'权限转移ID', width:140, dataIndex:'userAuthMoveId' },
			        //{ header:'权限转移类别名称', width:140, dataIndex:'typeName'},
			        { header:'名称', width:240, dataIndex:'name'},
			        { header:'名称描述', width:300, dataIndex:'nameDesc'},
			        { header:'权限转给用户', width:120, dataIndex:'toUserName',align :'center',headeralign :'center'},
			        { header:'授权开始时间', width:120, dataIndex:'authStartTime',align :'center',headeralign :'center'},
			        { header:'授权结束时间', width:120, dataIndex:'authEndTime',align :'center',headeralign :'center'},
			        { header:'是否有效', width:120, dataIndex:'isEffect',align :'center',headeralign :'center'}
			        //{ header:'menuId', width:140, dataIndex:'menuId'},
			        //{ header:'resourceId', width:140, dataIndex:'resourceId'},
			        //{ header:'roleId', width:140, dataIndex:'roleId'}

			    ],  
   	            listeners: {
	            beforecheck : function(n) {
	            	if (n.attributes.checked) return true;
					//if( !n.expanded && !n.childrenRendered ) n.expand();
					if (n.parentNode.id == '-1'){
						var checkedNodes = otherUserRemoveColumnTree.getChecked();
						for(var i=0;i<checkedNodes.length;i++){
							var node = checkedNodes[i];
							//if(node.id != n.id){
								node.getUI().checkbox.checked = false;
								node.attributes.checked = false;
								otherUserRemoveColumnTree.fireEvent('check', node, false);
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

	 //otherUserRemoveColumnTree.render(); 
	 //otherUserRemoveColumnTree.expand(); 
	 //otherUserRemoveColumnTree.expandAll();
    var viewport = new Ext.Viewport({
        layout:'border',
        items:otherUserRemoveColumnTree
    });

    viewport.doLayout();   
});

</script>

