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
<div id="div_west_tree" style="overflow: auto; width: 100%; height: 100%">
</div>
<div id="div_west_condition">
</div>

<div id="div_center_center">
</div>
<form action="" name="deptform">
<input type="hidden" name="deptid" id="deptid" value=""></input>
<input type="hidden" name="deptname" id="deptname" value=""></input>
</form>
</body>
</html>
<script type="text/javascript">
var tree;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByLoginUser'
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
    	id:'dept_tree',
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
        text: '部门信息',
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
		
		ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryUserByDeptIdToGrid&deptid='+node.id});
		ds.load();
		
		//如果点的是根目录就不用再去查询部门信息
		if (node.id == '-1'){
			tbar.setDisabled(true);
		}
		else{
			tbar.setDisabled(false);
			document.deptform.deptid.value = node.id;
			document.deptform.deptname.value = node.text;
		}
    }
    
    function callbackFunction(){
    	ds.load();
    }

   	var addUser = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加员工信息',
			'orgController.spr?action=addUserView&deptid='+document.deptform.deptid.value,
			'',
			callbackFunction,
			{width:450,height:350});
		}
   	});
   	var updateUser = new Ext.Toolbar.Button({
   		text:'修改',
    	iconCls:'update',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
				}else{
					top.ExtModalWindowUtil.show('修改员工信息',
					'orgController.spr?action=modiUserInfoView&deptid='+records[0].json.deptId+'&userid='+records[0].json.userId,
					'',
					callbackFunction,
					{width:450,height:350});
				}
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要修改的行！');
			}
		}
   	});
   	var deleteUser = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
		        var idList = '';            					
  					for(var i=0;i<records.length;i++){
  						idList = idList + records[i].json.userId + '|';
  					}
  					
  					Ext.Ajax.request({
					url: 'orgController.spr?action=deleteUser&userlist='+idList+'&deptid='+records[0].json.deptId,
					success: function(response, options){
						ds.reload();
					},
					failure:function(response, options){
						
					}
				});
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的员工信息！');
			}
		}
   	});
   	var userBelong = new Ext.Toolbar.Button({
   		text:'查询员工所属情况',
   		iconCls:'find',
   		handler:function(){
   			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
				}else{
					top.ExtModalWindowUtil.show('员工所属部门情况',
						'orgController.spr?action=userBelong&userid='+ records[0].json.userId,
						'',
						'',
						{width:250,height:300});
				}
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要查看的员工！');
			}
   		}
   	});
   	var selectUser = new Ext.Toolbar.Button({
   		text:'选译员工',
   		iconCls:'selectuser',
		handler:function(){
			top.ExtModalWindowUtil.show('选择员工',
			'orgController.spr?action=addDeptUserView&deptid='+ document.deptform.deptid.value,
			'',
			callbackFunction,
			{width:700,height:500});
		}
   	});
   	
   	var addUserRole = new Ext.Toolbar.Button({
   		text:'授权员工角色',
   		iconCls:'roleuser',
   		handler:function(){
   			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行授权！');
				}else{
					//alert(top.document.userform.deptUserId.value);
					if (top.document.userform.deptUserId.value == records[0].json.deptUserId)
					{
						top.ExtModalWindowUtil.alert('提示','不能自己给自己授权！');
					}else{
						top.ExtModalWindowUtil.show('员工角色授权界面',
							'orgController.spr?action=addUserRoleView&deptuserid='+ records[0].json.deptUserId,
							'',
							'',
							{width:700,height:580});
					}
				}
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要授权的员工！');
			}
   		}
   	});
   	
   		var addUserPopedom = new Ext.Toolbar.Button({
   		text:'分配数据权限',
   		iconCls:'roleuser',
   		handler:function(){
   			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行授权！');
				}else{
					if (top.document.userform.deptUserId.value == records[0].json.deptUserId)
					{
						top.ExtModalWindowUtil.alert('提示','不能自己给自己授权！');
					}else{
						top.ExtModalWindowUtil.show('员工数据权限授权界面',
							'orgController.spr?action=addUserPopedomView&deptuserid='+ records[0].json.deptUserId,
							'',
							'',
							{width:450,height:580});
					}
				}
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要授权的员工！');
			}
   		}
   	});
   	
	var tbar = new Ext.Toolbar({
		items:[addUser,'-',updateUser,'-',deleteUser,'-',userBelong,'-',selectUser,'-',addUserRole,'-',addUserPopedom]
	});
   	tbar.setDisabled(true);
   	ds = new Ext.data.Store({
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
					{name:'sex'},
					{name:'address'},
					{name:'idCard'},
					{name:'employeeNo'},
					{name:'password'},
					{name:'EMail'},
					{name:'creator'},
					{name:'createTime'},
					{name:'deleted'},
					{name:'deptUserId'},
					{name:'mobile'}
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
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
           {header: '身份证号',
           width: 120,
           sortable: true,
           dataIndex: 'idCard'
           },
           {header: '工号',
           width: 120,
           sortable: true,
           dataIndex: 'employeeNo'
           },
           {header: '性别',
           width: 100,
           sortable: true,
           dataIndex: 'sex'
           },
           {header: '地址',
           width: 100,
           sortable: true,
           dataIndex: 'address'
           },
           {header: '邮箱',
           width: 100,
           sortable: true,
           dataIndex: 'EMail'
           },
           {header: '记录创建者',
           width: 100,
           sortable: true,
           dataIndex: 'creator',
           hidden:true
           },
           {header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'createTime',
           hidden:true
           },
           {
           header: '员工部门编号',
           width: 100,
           sortable: true,
           dataIndex: 'deptUserId',
           hidden:true
           },
           {
           header: '手机号码',
           width: 100,
           sortable: true,
           dataIndex: 'mobile'
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
    
    var grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        sm:sm,
        bbar: bbar,
        tbar: tbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center_center',
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
				layout:'fit',
				contentEl: 'div_west_tree',
				tools:[{
					id:'refresh',   
                    qtip: '刷新部门树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('dept_tree');
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
				contentEl:'div_center_center',
				title: '员工列表详情',
            	plain:true,
				layout:'fit',
            	defaults:{bodyStyle:'padding:0px'},
	            items:[grid]
			}]
		}]
	});
});
</script>