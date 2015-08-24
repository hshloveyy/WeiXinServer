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
</style>
</head>
<body>
<div id="div_north" style="width:100%;height:100%">
<table border="0" width="100%">
<tr>
	<td>字 典 表 表 名:</td>
	<td>
		<input id="dicttable" name="dicttable" type="text" value="" size="15"></input>
	</td>
	<td>编 码 字 段 名:</td>
	<td>
		<input id="dictcodecolumn" name="dictcodecolumn" type="text" value="" size="15"></input>
	</td>
	<td>名 称 字 段 名:</td>
	<td>
		<input id="dictnamecolumn" name="dictnamecolumn" type="text" value="" size="15"></input>
	</td>
</tr>
<tr>
	<td>字 典 表 类 型:</td>
	<td>
		<!--  <select name="dicttype" id="dicttype" size="1"
			width="125" style="width: 125px">
			<option value="" selected="selected">请选择</option>
			<option value="1">单表字典表</option>
			<option value="2">主从字典表</option>
		</select>-->
		<div id="dicttypeDiv"></div>
	</td>
	<td>字典关键字段名:</td>
	<td>
		<input id="dictkeycolumn" name="dictkeycolumn" type="text" value="" size="15"></input>
	</td>
	<td>父 编 码 字 段:</td>
	<td>
		<input id="dictparentcolumn" name="dictparentcolumn" type="text" value="" size="15"></input>
	</td>
</tr>
<tr>
<td colspan="6" align="right">
	<input type="button" name="dictfind" value="搜索" onclick="findDict();"></input>
</td>
</tr>
</table>
</div>
<div id="div_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;

function findDict(){
	var findCondition = 'dictController.spr?action=queryDictionaryInfo';
	var findCount = 0;
	findCondition = findCondition + '&dicttable=' + document.all.dicttable.value.trim();
	if (document.all.dicttable.value.trim() != '') 
		findCount = findCount + 1;
		
	findCondition = findCondition + '&dictcodecolumn=' + document.all.dictcodecolumn.value.trim();
	if (document.all.dictcodecolumn.value.trim() != '') 
		findCount = findCount + 1;
		
	findCondition = findCondition + '&dictnamecolumn=' + document.all.dictnamecolumn.value.trim();
	if (document.all.dictnamecolumn.value.trim() != '') 
		findCount = findCount + 1;
		
	findCondition = findCondition + '&dicttype=' + dict_dicttypeDiv.getSelectedValue().trim();
	if (dict_dicttypeDiv.getSelectedValue().trim() != '') 
		findCount = findCount + 1;
		
	findCondition = findCondition + '&dictkeycolumn=' + document.all.dictkeycolumn.value.trim();
	if (document.all.dictkeycolumn.value.trim() != '') 
		findCount = findCount + 1;
		
	findCondition = findCondition + '&dictparentcolumn=' + document.all.dictparentcolumn.value.trim();
	if (document.all.dictparentcolumn.value.trim() != '') 
		findCount = findCount + 1;
		
	if (findCount == 0 )
		top.ExtModalWindowUtil.alert('提示','请输入或选择需要查询的条件！');
	else{
		ds.proxy= new Ext.data.HttpProxy({url:findCondition});
		ds.load({params:{start:0, limit:50},arg:[]});
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';	
   	
   	var tbar = new Ext.Toolbar({
   		items:[{
   			text:'增加',
	    	iconCls:'add',
			handler:function(){
				top.ExtModalWindowUtil.show('增加字典表配置',
				'dictController.spr?action=addDictionacyView',
				'',
				'',
				{width:600,height:180});
			}
   		},{
   			text:'修改',
	    	iconCls:'update',
			handler:function(){
				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
					}else{
						top.ExtModalWindowUtil.show('修改字典表配置',
						'dictController.spr?action=addDictionacyView&dictid='+records[0].json.dictid,
						'',
						'',
						{width:600,height:180});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择要修改的行！');
				}
			}
   		},{
   			text:'删除',
	    	iconCls:'delete',
			handler:function(){
				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
			        var idList = '';            					
   					for(var i=0;i<records.length;i++){
   						idList = idList + records[i].json.dictid + '|';
   					}
   					
   					Ext.Ajax.request({
						url: 'dictController.spr?action=deleteDictionary&dictid='+idList,
						success: function(response, options){
							ds.reload();
						},
						failure:function(response, options){
							
						}
					});
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择要删除的行！');
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
            		{name:'dictid'},
            		{name:'dicttable'},
            		{name:'dictcodecolumn'},
            		{name:'dictnamecolumn'},
            		{name:'dicttype'},
            		{name:'dicttypename'},
            		{name:'dictkeycolumn'},
            		{name:'dictparentcolumn'},
            		{name:'cmd'},
            		{name:'creator'},
            		{name:'createtime'}
          		])
    });
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {header: '字典编号',
           width: 100,
           sortable: false,
           dataIndex: 'dictid',
           hidden:true
           },
           {header: '字典表名',
           width: 100,
           sortable: true,
           dataIndex: 'dicttable'
           },
           {
           header: '编码字段名',
           width: 100,
           sortable: true,
           dataIndex: 'dictcodecolumn'
           },
           {header: '名称字段名',
           width: 100,
           sortable: true,
           dataIndex: 'dictnamecolumn'
           },
           {header: '字典表类型编号',
           width: 100,
           sortable: true,
           dataIndex: 'dicttype',
           hidden:true
           },
           {header: '字典表类型',
           width: 100,
           sortable: true,
           dataIndex: 'dicttypename'
           },
           {header: '字典表关键字段名',
           width: 120,
           sortable: true,
           dataIndex: 'dictkeycolumn'
           },
           {header: '父编码字段名',
           width: 100,
           sortable: true,
           dataIndex: 'dictparentcolumn'
           },
           {header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'cmd'
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
           dataIndex: 'createtime',
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
        //autoExpandColumn:"memory",  //自动扩展宽度的列
        autoScroll:true,
        region:'center',
        el: 'div_center',
        layout:"fit"
    });
    
    //ds.load({params:{start:0, limit:50},arg:[]});

    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"条件查询",
			collapsible: true,
			height:100,
			items:[{
				xtype:'panel',
				border:false,
				contentEl:'div_north'
			}]
		},{
			region:"center",
			layout:'border',
			title:"数据显示",
			contentEl: 'div_center',
			items:[grid]
		}]
	});
});
</script>
<fiscxd:dictionary divId="dicttypeDiv" fieldName="dicttype" dictionaryName="BM_SYS_DICT_TYPE" width="126"></fiscxd:dictionary>
