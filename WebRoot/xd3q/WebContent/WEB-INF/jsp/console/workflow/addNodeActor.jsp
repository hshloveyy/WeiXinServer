<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/ext-2.0/TreeCheckNodeUI.js"></script>
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
	background-image:url(images/fam/user_comment.png) !important;
}
.selectuser{
background-image:url(images/fam/user_add.gif) !important;
}
.roleuser{
background-image:url(images/fam/user_edit.png) !important;
}
</style>
</head>
<body>
<div id="div_center"></div>
<div id="div_middle" style="overflow: auto; width: 100%; height: 100%">
<div id = "div_west_tree" style="overflow: auto; width: 100%; height: 50%"></div>
<div id = "div_west_role_grid" style="overflow: auto; width: 100%; height: 50%"></div>
</div>

<div id="div_west" style="overflow: auto; width: 100%; height: 100%">
<div id ="div_west_dept_tree" style="overflow: auto; width: 100%; height: 50%"></div>
<div id ="div_west_user_grid" style="overflow: auto; width: 100%; height: 50%"></div>
</div>

</body>
</html>
<script type="text/javascript">
var processid = '${processid}';
var taskid = '${taskid}';
var ds;
var userds;

function customCallBackHandle(transport){
	ds.reload();
	var maintab = Ext.getCmp("selecttab");
	maintab.setActiveTab("already");
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var deleteactor = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
		        var idList = '';            					
  				for(var i=0;i<records.length;i++){
  					idList = idList + records[i].json.taskActorId + '|';
  				}
  				
  				var param = '?taskactoridlist=' + idList;
				param = param + "&action=deleteTaskActor";

				new AjaxEngine('workflowController.spr', 
					{method:"post", parameters: param, onComplete: callBackHandle});
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的员工信息！');
			}
		}
   	});
   	
   	var tbar = new Ext.Toolbar({
		items:[deleteactor,'-']
	});
   	
   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'workflowController.spr?action=queryTaskActorForGrid&processid='+processid+'&taskid='+taskid}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'actorType'},
					{name:'actorId'},
					{name:'actorName'},
					{name:'creator'},
					{name:'createTime'},
					{name:'taskActorId'}
          		])
    });
    var sm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  sm,
		　{header: '参与者类型',
           width: 100,
           sortable: false,
           dataIndex: 'actorType'
           },
		  {header: '参与者编号',
           width: 100,
           sortable: false,
           dataIndex: 'actorId',
           hidden:true
           },
           {header: '参与者名称',
           width: 100,
           sortable: true,
           dataIndex: 'actorName'
           },
           {header: '创造者',
           width: 100,
           sortable: true,
           dataIndex: 'creator',
           hidden:true
           },
           {header: '创造时间',
           width: 120,
           sortable: true,
           dataIndex: 'createTime'
           },
           {header: '参与者骗号',
           width: 120,
           sortable: true,
           dataIndex: 'taskActorId',
           hidden:true
           }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 500,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        sm:sm,
        tbar:tbar,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    ds.load();
   	
   	var roleTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryRoleByLoginUser'
    });
      
    roleTreeLoader.on("beforeload", function(treeLoader, node) {   
                roleTreeLoader.baseParams.id = node.attributes.id;   
            }, roleTreeLoader);
    
    var roletree = new Ext.tree.TreePanel({
        el:'div_west_tree',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: roleTreeLoader
    }); 
    
    var roleroot = new Ext.tree.AsyncTreeNode({
        text: '角色信息',
        draggable:false,
        id:'-1'
    });
    roletree.setRootNode(roleroot);
    roleroot.expand();
    roletree.render('div_west_tree');
    roletree.on("click",function(node,e)
	{       
	    roleds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryRoleByParentIdToGrid&id='+node.id});
		roleds.load();
	});
	
	var roleds = new Ext.data.Store({
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
    
    var rolesm = new Ext.grid.CheckboxSelectionModel();
    
    var rolecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		rolesm,
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
    rolecm.defaultSortable = true;
    
    var rolebbar = new Ext.PagingToolbar({
        pageSize: 500,
        store:roleds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var rolegrid = new Ext.grid.GridPanel({
        ds: roleds,
        cm: rolecm,
        sm: rolesm,
        bbar: rolebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        el: 'div_west_role_grid',
        autowidth:true,
        layout:'fit'
    });
    rolegrid.render('div_west_role_grid');
    
    var deptTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByLoginUser'
    });
      
    deptTreeLoader.on("beforeload", function(treeLoader, node) {   
                deptTreeLoader.baseParams.id = node.attributes.id;   
            }, deptTreeLoader);
    
    var depttree = new Ext.tree.TreePanel({
        el:'div_west_dept_tree',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: deptTreeLoader
    }); 
    
    var deptroot = new Ext.tree.AsyncTreeNode({
        text: '部门信息',
        draggable:false,
        id:'-1'
    });
    depttree.setRootNode(deptroot);
    deptroot.expand();
    depttree.render('div_west_dept_tree');
    depttree.on("click",function(node,e)
	{       
	    userds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryUserByDeptIdToGrid&deptid='+node.id});
		userds.load();
	});
    
    userds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'userId'},
					{name:'deptId'},
					{name:'deptName'},
					{name:'userName'},
					{name:'realName'},
					{name:'deptUserId'}
          		])
    });
    
    var usersm = new Ext.grid.CheckboxSelectionModel();
    
    var usercm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		usersm,
		  {
           header: '用户名',
           width: 100,
           sortable: true,
           dataIndex: 'userName'
           },
		　{header: '员工编号',
           width: 100,
           sortable: false,
           dataIndex: 'userId',
           hidden:true
           },
		  {header: '部门编号',
           width: 100,
           sortable: false,
           dataIndex: 'deptId',
           hidden:true
           },
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'deptName'
           },
           {header: '姓名',
           width: 100,
           sortable: true,
           dataIndex: 'realName'
           },
           {header: '员工部门编号',
           width: 100,
           sortable: true,
           dataIndex: 'deptUserId',
           hidden:true
           }
    ]);
    usercm.defaultSortable = true;
    
    var userbbar = new Ext.PagingToolbar({
        pageSize: 500,
        store:userds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var usergrid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: userds,
        cm: usercm,
        sm: usersm,
        bbar: userbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_west_user_grid',
        autowidth:true,
        layout:'fit'
    });
    usergrid.render('div_west_user_grid');
    
    var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			layout:'border',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'selecttab',
				name:'selecttab',
            	plain:true,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
            		title:'已有的参与信息',
            		id:'already',
            		name:'already',
            		layout:'fit',
                	items:[grid]
            	},{
            		title:'角色信息',
            		layout:'fit',
            		id:'role',
            		name:'role',
            		contentEl:'div_middle'
            	},{
            		title:'部门人员信息',
            		id:'user',
            		name:'user',
            		contentEl:'div_west'
            	}],
            	buttons:[{
            		text:'确认',
            		handler:function(){
            			if (Ext.getCmp("selecttab").getActiveTab().id != 'already'){
	            			var actorType = '';
	            			var idList = '';
	            			var nameList = '';
	            			if (Ext.getCmp("selecttab").getActiveTab().id == 'role'){
	            				var records = rolegrid.selModel.getSelections();       					
								for(var i=0;i<records.length;i++){
									idList = idList + records[i].json.roleId + '|';
									nameList = nameList + records[i].json.roleName + '|';
								}
								actorType = '1';
	            			}
	            			
	            			if (Ext.getCmp("selecttab").getActiveTab().id == 'user'){
	            				var records = usergrid.selModel.getSelections();
								for(var i=0;i<records.length;i++){
									idList = idList + records[i].json.deptUserId + '|';
									nameList = nameList + records[i].json.realName + '|';
								}
								actorType = '2';
	            			}
            				
            				var param = '?processDefId=' + processid;
            				param = param + '&taskDefId=' + taskid;
            				param = param + '&actorType=' + actorType;
            				param = param + '&actorId=' + idList;
            				param = param + '&actorName=' + nameList;
							param = param + "&action=addTaskActor";

							new AjaxEngine('workflowController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
            			}else{
            				top.ExtModalWindowUtil.markUpdated();
            				top.ExtModalWindowUtil.close();
            			}
            		}
            	},{
            		text:'关闭',
            		handler:function(){
            			top.ExtModalWindowUtil.markUpdated();
            			top.ExtModalWindowUtil.close();
            		}
            	}]
			}]
		}]
	});
});
</script>