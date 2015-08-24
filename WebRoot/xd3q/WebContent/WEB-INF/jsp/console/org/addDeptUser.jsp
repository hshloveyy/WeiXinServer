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
<form action="" name="userform">
<table>
<tr>
<td>用户名:</td>
<td>
<input type="text" name="userName" id="userName" value="" size="15"></input>
</td>
</tr>

<tr>
<td>姓名:</td>
<td>
<input type="text" name="realName" id="realName" value="" size="15"></input>
</td>
</tr>

<tr>
<td>性别:</td>
<td><div id="sexDiv"></div>
</td>
</tr>

<tr>
<td>身份证:</td>
<td>
<input type="text" name="idCard" id="idCard" value="" size="15"></input>
</td>
</tr>

<tr>
<td colspan="2" align="right">
<input type="button" name="fimduser" id="fimduser" value="搜索" onclick="searchUser();"></input>
</td>
</tr>

</table>
</form>
</div>

<div id="div_center_center">
</div>
<form action="" name="deptuserform">
<input type="hidden" name="userlist" id="userlist" value=""></input>
<input type="hidden" name="deptid" id="deptid" value=""></input>
<input></input>
</form>
</body>
</html>
<script type="text/javascript">
var deptId = '${deptid}';
var tree;
var ds;

function searchUser(){
	var url = 'orgController.spr?action=queryUserByCondition&';
	url += Form.serialize('userform');
	
	ds.proxy= new Ext.data.HttpProxy({url: url});
	ds.load();
}

function customCallBackHandle(transport){
	top.ExtModalWindowUtil.markUpdated();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	function customCallBackHandle(transport){
	}
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByParentId'
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
		ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryUserByDeptIdToGrid&deptid='+node.id});
		ds.load();
    }

    var tbar = new Ext.Toolbar({
   		items:[{
   			text:'增加',
	    	iconCls:'add',
			handler:function(){
				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
			        var idList = '';            					
   					for(var i=0;i<records.length;i++){
   						idList = idList + records[i].json.userId + '|';
   					}
   					document.deptuserform.userlist.value =idList;
   					document.deptuserform.deptid.value = deptId;
   					
   					var param = Form.serialize('deptuserform');
					param += "&action=addDeptUser";
					new AjaxEngine('orgController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择要增加的员工信息！');
				}
			}
   		}]
   	});

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
					{name:'mobile'},
					{name:'deleted'}
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
           {header: '手机号码',
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
        autowidth:true
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
            },{
            	title:'条件查询',
				border:false,
				xtype:'panel',
				contentEl:'div_west_condition'
            }]
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'tabpanel',
            	plain:true,
            	height:300,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
	                title: '员工列表详情',
	                id:'userlist',
	                layout:'border',
	                items:[grid]
            	}]
			}]
		}]
	});
});
</script>
<fiscxd:dictionary divId="sexDiv" fieldName="sex" dictionaryName="BM_SYS_SEX" selectedValue=""></fiscxd:dictionary>