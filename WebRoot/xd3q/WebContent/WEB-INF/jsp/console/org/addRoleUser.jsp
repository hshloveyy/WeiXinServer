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
var strRoleId = '${roleid}';
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
        el:'div_west_tree',
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
		
		if (node.parentNode.id == '-1'){
			tbar.setDisabled(true);
		}else{
			tbar.setDisabled(false);
		}
    }
    
    function callbackFunction(){
    	ds.load();
    }

   	var addRole = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
		        var idList = '';            					
				for(var i=0;i<records.length;i++){
					idList = idList + records[i].json.deptUserId + '|';
				}

				Ext.Ajax.request({
					url: 'orgController.spr?action=addRoleUser&roleid='+strRoleId+'&deptuserlist='+idList,
					success: function(response, options){
						top.ExtModalWindowUtil.alert('提示','操作成功！');
					},
					failure:function(response, options){
						top.ExtModalWindowUtil.alert('提示','操作失败！');
					}
				});
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要授权的员工！');
			}
		}
   	});

	var tbar = new Ext.Toolbar({
		items:[addRole]
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
					{name:'password'},
					{name:'EMail'},
					{name:'creator'},
					{name:'createTime'},
					{name:'deleted'},
					{name:'deptUserId'}
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
            	plain:true,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
	                title: '员工列表详情',
	                id:'userlist',
	                layout:'fit',
	                items:[grid]
            	}]
			}]
		}]
	});
});
</script>