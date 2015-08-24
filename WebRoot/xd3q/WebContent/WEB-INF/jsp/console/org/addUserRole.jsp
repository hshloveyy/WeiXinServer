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
<div id="div_west_tree" style="overflow: auto; width: 100%; height: 100%">
</div>
<div id="div_center_center">
<div id="div_center_grid"></div>
<div id="div_center_tool">
<table align="center">
<tr>
	<td>
		<input type="image" src="<%= request.getContextPath() %>/images/fam/arrow-down.gif" 
		onclick="addRole();"></input>
	</td>
	<td width="20"></td>
	<td width="20"></td>
	<td>
		<input type="image" src="<%= request.getContextPath() %>/images/fam/arrow-up.gif" 
		onclick="deleteRole();"></input>
	</td>
</tr>
</table>
</div>
</div>

<div id="div_center_south">
</div>
</body>
</html>
<script type="text/javascript">
var deptUserId = '${deptuserid}';

var tree;
var ds;
var byds;
var grid;
var bygrid;

function addRole()
{
	if (grid.selModel.hasSelection()){
		var records = grid.selModel.getSelections();
	       var idList = '';            					
		for(var i=0;i<records.length;i++){
			idList = idList + records[i].json.roleId + '|';
		}
					
		Ext.Ajax.request({
			url: 'orgController.spr?action=addUserRole&roleidlist='+idList+'&deptuserid='+deptUserId,
			success: function(response, options){
				top.ExtModalWindowUtil.alert('提示','操作成功！');
				byds.reload();
			},
			failure:function(response, options){
			}
		});
	}else{
		top.ExtModalWindowUtil.alert('提示','请选择要增加的角色信息！');
	}
}

function deleteRole()
{
	if (bygrid.selModel.hasSelection()){
		var records = bygrid.selModel.getSelections();
        var idList = '';            					
		for(var i=0;i<records.length;i++){
			idList = idList + records[i].json.roleId + '|';
		}
					
		Ext.Ajax.request({
			url: 'orgController.spr?action=deleteUserRole&roleidlist='+idList+'&deptuserid='+deptUserId,
			success: function(response, options){
				top.ExtModalWindowUtil.alert('提示','操作成功！');
				byds.reload();
			},
			failure:function(response, options){
			}
		});
	}else{
		top.ExtModalWindowUtil.alert('提示','请选择要删除的角色信息！');
	}
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
        el:'div_west_tree',
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
		ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryRoleByParentIdToGrid&id='+node.id});
		ds.load();
    }
    
    ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'roleId'},
					{name:'roleType'},
					{name:'roleName'},
					{name:'roleCmd'},
					{name:'roleScope'},
					{name:'proleName'}
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           header: '角色名称',
           width: 100,
           sortable: true,
           dataIndex: 'roleName'
           },
		　{header: '角色编号',
           width: 100,
           sortable: false,
           dataIndex: 'roleId',
           hidden:true
           },
		  {header: '角色类型',
           width: 100,
           sortable: false,
           dataIndex: 'roleType'
           },
           {header: '角色范围',
           width: 100,
           sortable: true,
           dataIndex: 'roleScope'
           },
           {header: '上一级角色',
           width: 100,
           sortable: true,
           dataIndex: 'proleName'
           },
           {header: '备注',
           width: 120,
           sortable: true,
           dataIndex: 'roleCmd'
           }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        sm:sm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        height:190,
        layout:'fit'
    });
    grid.render('div_center_grid');
    
    byds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'orgController.spr?action=queryRoleByDeptUserId&deptuserid='+deptUserId}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'roleId'},
					{name:'roleType'},
					{name:'roleName'},
					{name:'roleCmd'},
					{name:'roleScope'},
					{name:'proleName'}
          		])
    });
    byds.reload();
    
    var bysm = new Ext.grid.CheckboxSelectionModel();
    
    var bycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		bysm,
		  {
           header: '角色名称',
           width: 100,
           sortable: true,
           dataIndex: 'roleName'
           },
		　{header: '角色编号',
           width: 100,
           sortable: false,
           dataIndex: 'roleId',
           hidden:true
           },
		  {header: '角色类型',
           width: 100,
           sortable: false,
           dataIndex: 'roleType'
           },
           {header: '角色范围',
           width: 100,
           sortable: true,
           dataIndex: 'roleScope'
           },
           {header: '上一级角色',
           width: 100,
           sortable: true,
           dataIndex: 'proleName'
           },
           {header: '备注',
           width: 120,
           sortable: true,
           dataIndex: 'roleCmd'
           }
    ]);
    bycm.defaultSortable = true;
    
    var bybbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:byds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    bygrid = new Ext.grid.GridPanel({
        ds: byds,
        cm: bycm,
        sm:bysm,
        bbar: bybbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center_south',
        autowidth:true,
        layout:'fit'
    });
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			title:"部门信息",
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
				xtype:'panel',
				title:'授权者角色信息列表',
				layout:'fit'
			},{
				region:'south',
				contentEl:'div_center_south',
				xtype:'panel',
				title:'被授权者角色信息列表',
				layout:'fit',
            	height:300,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[bygrid]
			}]
		}]
	});
});
</script>