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
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
.roleuser{
background-image:url(images/fam/user_edit.png) !important;
}
</style>
</head>
<body>
<div id="div_west_tree" style="overflow: auto; width: 100%; height: 100%">
</div>

<!-- 下面是右边上部信息 -->
<div id="div_center_north" style="overflow: auto; width: 100%; height: 100%">
<div id="tool_bar">
</div>
<form action="" name="roleform">
<table width="100%">
<tr>
<td>角色名称：</td>
<td>
<input type="hidden" name="roleId" id="roleId" value=""></input>
<input type="text" name="roleName" id="roleName" value="" size="15"></input>
</td>
<td>角&nbsp&nbsp色&nbsp&nbsp&nbsp类&nbsp&nbsp&nbsp型：</td>
<td>
<div id="roleTypeDiv"></div>
</td>
</tr>

<tr>
<td>角色范围：</td>
<td>
<div id="roleScopeDiv"></div>
</td>
<td>上&nbsp一&nbsp级&nbsp色&nbsp角：</td>
<td>
<input type="hidden" name="parentRole" id="parentRole" value=""></input>
<input type="text" name="pRoleName" id="pRoleName" value="" size="15"></input>
</td>
</tr>

<tr>
<td>备&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp注：</td>
<td colspan="3">
<input type="text" name="roleCmd" id="roleCmd" value="" size="55"></input></td>
</tr>

<tr>
<td colspan="4" align="center">
<input type="button" disabled="true" name="submitrole" id="submitrole" value="提交" onclick="addRole();"></input>
</td>
</tr>
</table>
</form>
</div>

<div id="div_center_center">
</div>
</body>
</html>

<script type="text/javascript">
var tree;

function addRole(){
	var param = Form.serialize("roleform");
	param += "&action=addRole";
	new AjaxEngine('orgController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

function customCallBackHandle(transport)
{
	var treeNodeId = document.roleform.roleId.value;
	var treeNode = tree.getNodeById(treeNodeId);
	var parentNode = treeNode.parentNode;
	
	if (parentNode.childNodes.length == 1)
		tree.root.reload();
	else
		parentNode.reload();

	roletbr.setDisabled(false);
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryRoleByLoginUser'
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
    	id:'role_tree',
        el:'div_west_tree',
        layout:'fit',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '角色信息',
        draggable:false,
        id:'-1'
    });
    tree.setRootNode(root);
    tree.render();
    tree.on('click',treeclick);
    root.expand();
    
    function treeclick(node,e){
	   	if (node.id == 'new')
			return;
			
		//如果已经增加了一个新的节点没有提交，又转去点击别的节点，就先取消这个新增的结点
    	var treeNode = tree.getNodeById("new");
    	if (treeNode != null)
    	{
    		var parentNode = treeNode.parentNode;
    		treeNode.remove();
			if (parentNode.firstChild == null)
			{
				parentNode.leaf = true;
			}
    	}
		
		ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryResourceByRoleId&roleid='+node.id});
		ds.load({params:{start:0, limit:200},arg:[]});

		if (node.id == '-1'){
			document.roleform.reset();
			document.roleform.roleId.value = '-1';
			roletbr.setDisabled(true);
			tbar.setDisabled(true);
		}else{
			Ext.Ajax.request({
				url: 'orgController.spr?action=queryRoleByRoleId&roleid='+node.id,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);
					
					document.roleform.roleId.value = responseArray.roleId;
					document.roleform.roleName.value = responseArray.roleName;
					dict_roleTypeDiv.setComboValue(responseArray.roleType);
					dict_roleScopeDiv.setComboValue(responseArray.roleScope);
					document.roleform.parentRole.value = responseArray.parentRole;
					document.roleform.pRoleName.value  = responseArray.PRoleName;
					document.roleform.roleCmd.value = responseArray.roleCmd;
				}
			});
			
			//为了控制员工不能修改自己身角色的资源
			roletbr.setDisabled(false);
			if (node.parentNode.id == '-1'){
				tbar.setDisabled(true);
			}else{
				tbar.setDisabled(false);
			}
		}
    }

    var addrolebutton = new Ext.Toolbar.Button({
   		text:'增加下级角色',
   		iconCls:'add',
   		handler:function(){
   			if (document.roleform.roleId.value == '' ||
    			document.roleform.roleId.value == 'new')
    			return;
    				
   			var treeNodeId = document.roleform.roleId.value;
   			var treeNode = tree.getNodeById(treeNodeId);
   			treeNode.expand();
   			var chlidNode=new Ext.tree.TreeNode({
				id:'new',
				text:'请增加部门信息',
				leaf : true
			});
			treeNode.leaf = false;
			treeNode.appendChild(chlidNode);
			treeNode.expand();
			
			document.roleform.reset();
			document.roleform.roleId.value ='new';
			document.roleform.parentRole.value = treeNode.id;
			document.roleform.pRoleName.value = treeNode.text;
			roletbr.setDisabled(true);
			document.roleform.submitrole.disabled = false;
   		}
    });

    var updaterolebutton = new Ext.Toolbar.Button({
    		text:'修改当前角色',
    		iconCls:'update',
    		handler:function(){	
    			document.roleform.submitrole.disabled = false;
    		}
    });
    var deleterolebutton = new Ext.Toolbar.Button({
    		text:'删除当前角色',
    		iconCls:'delete',
    		handler:function(){
    			if (ds.getCount() > 0){
    				top.ExtModalWindowUtil.alert('提示','请先删除角色下的资源再删除本角色！');
    				return;
    			}
    			var nodeId = document.roleform.roleId.value;
    			var node = tree.getNodeById(nodeId);
    			var parentNode = node.parentNode;
    			if (node.leaf == false){
					top.ExtModalWindowUtil.alert('提示','请先删除下级子角色后再删除本角色！');
				}else{
					if (nodeId != 'new'){
						Ext.Ajax.request({
							url: 'orgController.spr?action=deleteRole&roleid='+nodeId,
							success: function(response, options){
								top.ExtModalWindowUtil.alert('提示','操作成功！');
								document.roleform.reset();
							},
							failure:function(response, options){
								top.ExtModalWindowUtil.alert('提示','操作失败！');
							}
						});
					}
					node.remove();
					if (parentNode.firstChild == null)
					{
						parentNode.leaf = true;
					}
				}
    		}
    });
    
   
    var roletbr = new Ext.Toolbar({
    	rendTo:'tool_bar',
   		items:[addrolebutton,'-',updaterolebutton,'-',deleterolebutton]
   	});
	roletbr.render('tool_bar');
	roletbr.setDisabled(true);
    
    
    var ds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:''}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},[
       		{name:'resourceid'},
       		{name:'typeid'},
       		{name:'typename'},
       		{name:'resourcename'},
       		{name:'resourcetitle'},
       		{name:'resourceurl'},
       		{name:'resourceicon'},
       		{name:'parentid'},
       		{name:'parenttitle'},
       		{name:'cmd'}
     		])
    });
    
    var cm = new Ext.grid.ColumnModel([
    								  new Ext.grid.RowNumberer(),
         							  {header: '资源编号',
                                        width: 100,
                                        sortable: false,
                                        dataIndex: 'resourceid',
                                        hidden:true
                                        },
                                        {header: '资源类型编号',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'typeid',
                                        hidden:true
                                        },
                                        {header: '资源标题',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourcetitle'
                                        },
                                        {header: '资源名称',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourcename'
                                        },
                                        {header: '资源地址',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourceurl'
                                        },
                                        {header: '上一级资源编号',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'parentid',
                                        hidden:true
                                        },
                                        {header: '上一级资源标题',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'parenttitle'
                                        },
                                        {
                                        header: '资源类型名称',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'typename'
                                        },
                                        {header: '资源图标',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourceicon'
                                        },
                                        {header: '备注',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'cmd'
                                        }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 50,
        store: ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    function callbackFunction(){
    	ds.load();
    }
    
    //resourceds.load({params:{start:0, limit:50},arg:[]});
    var addreaourcebutton = new Ext.Toolbar.Button({
   		text:'增加角色资源',
   		iconCls:'add',
   		handler:function(){
   			var treeNodeId = document.roleform.roleId.value;
			var treeNode = tree.getNodeById(treeNodeId);
			var parentNodeId = treeNode.parentNode.id;
			
   			top.ExtModalWindowUtil.show('增加'+treeNode.text+'下挂资源信息',
			'orgController.spr?action=addRoleResourceView&roleid='+treeNodeId+'&uproleid='+parentNodeId,
			'',
			callbackFunction,
			{width:350,height:500});
   		}
    });
	/*var deletereaourcebutton = new Ext.Toolbar.Button({
   		text:'删除角色资源',
   		iconCls:'delete',
   		handler:function(){	
   			if (grid.selModel.hasSelection())
   			{
				var records = grid.selModel.getSelections();
		        var idList = '';            					
  				for(var i=0;i<records.length;i++){
  					idList = idList + records[i].json.resourceid + '|';
  				}
  					
  				Ext.Ajax.request({
					url: 'orgController.spr?action=deleteRoleResource&resourcelist='+idList+'&roleid='+document.roleform.roleId.value,
					success: function(response, options){
						ds.reload();
					},
					failure:function(response, options){
					}
				});
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的资源信息！');
			}
   		}
    });*/
    
    var deleteallresourceButton = new Ext.Toolbar.Button({
    	text:'删除该角色所有资源',
   		iconCls:'delete',
   		handler:function(){
   			Ext.Ajax.request({
				url: 'orgController.spr?action=deleteAllRoleResource&roleid='+document.roleform.roleId.value,
				success: function(response, options){
					ds.reload();
				},
				failure:function(response, options){
				}
			});
   		}
    });
    
    var addUserRole = new Ext.Toolbar.Button({
    	text:'授权员工角色',
    	iconCls:'roleuser',
   		handler:function(){
   			var treeNodeId = document.roleform.roleId.value;
			var treeNode = tree.getNodeById(treeNodeId);
			var parentNodeId = treeNode.parentNode.id;
			
   			top.ExtModalWindowUtil.show('('+treeNode.text + ')角色授权界面',
				'orgController.spr?action=addRoleUserView&roleid='+treeNode.id,
				'',
				'',
				{width:700,height:580});
   		}
    });
    
    var showRoleUser = new Ext.Toolbar.Button({
    	text:'角色拥有员工信息',
    	iconCls:'find',
   		handler:function(){
   			var treeNodeId = document.roleform.roleId.value;
			var treeNode = tree.getNodeById(treeNodeId);
			var parentNodeId = treeNode.parentNode.id;
			
   			top.ExtModalWindowUtil.show('('+treeNode.text + ')角色拥有员工界面',
				'orgController.spr?action=roleUser&roleid='+treeNode.id,
				'',
				'',
				{width:250,height:400});
   		}
    });
    
   	var tbar = new Ext.Toolbar({
   		items:[addreaourcebutton,'-',deleteallresourceButton,'-',showRoleUser,'-',addUserRole]
   	});
   	tbar.setDisabled(true);
    
    var grid = new Ext.grid.GridPanel({
        ds: ds,
        cm: cm,
        bbar: bbar,
        tbar: tbar,
        border:false,
        loadMask: true,
        autoScroll:true,
        el: 'div_center_center',
        autowidth:true,
        layout:'fit'
    });
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			title:"角色信息查询",
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
				layout:'fit',
				contentEl: 'div_west_tree',
				tools:[{
					id:'refresh',   
                    qtip: '刷新角色树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('role_tree');
                        tree.body.mask('Loading', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function(){
                        	tree.body.unmask();
                        }, 1000);
                     }
				}],
				items:[tree]
            }]
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_north',
				title: '角色信息',
            	plain:true,
            	layout:'fit',
            	defaults:{bodyStyle:'padding:0px'}
			},{
				region:'south',
				contentEl:'div_center_center',
				title: '角色资源列表详情',
            	plain:true,
            	height:330,
				layout:'fit',
            	defaults:{bodyStyle:'padding:0px'},
	            items:[grid]
			}]
		}]
	});
});
</script>
<fiscxd:dictionary divId="roleTypeDiv" fieldName="roleType" dictionaryName="BM_SYS_ROLE_TYPE" width="126"></fiscxd:dictionary>

<fiscxd:dictionary divId="roleScopeDiv" fieldName="roleScope" dictionaryName="BM_SYS_ROLE_SCOPE" width="126"></fiscxd:dictionary>