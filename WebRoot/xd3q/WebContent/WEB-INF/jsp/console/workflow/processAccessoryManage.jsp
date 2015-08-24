<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
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
<div id="div_center_center">
</div>

<ffcs:fileUpload workFlowId="56"></ffcs:fileUpload>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;

function customCallBackHandle(transport){
	ds.reload();
}

function addAccessory(){
	openUpload();
}

function deleteAccessory(){
	if (grid.selModel.hasSelection()){
		var records = grid.selModel.getSelections();
		var idList = '';            					
		for(var i=0;i<records.length;i++){
			idList = idList + records[i].json.accessoryId + '|';
		}
		
		var param = '?processidlist=' + idList;
		param = param + "&action=deleteProcessAccessory";

		new AjaxEngine('workflowController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});
	}else{
		top.ExtModalWindowUtil.alert('提示','请选择要删除的附件！');
	}
}
</script>
<ffcs:nestToolbar modelName="" name="toolbar" pageResourcesName="" iconCls="add;delete" handler="addAccessory;deleteAccessory" text="增加;删除" isNeedAuth="false;false"></ffcs:nestToolbar>
<script type="text/javascript">
var processid = '${processid}';


Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'workflowController.spr?action=queryProcessAccessory&processid='+processid}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'accessoryId'},
					{name:'processId'},
					{name:'fileDescription'},
					{name:'filePath'},
					{name:'originalFileName'},
					{name:'newFileName'},
					{name:'updateDate'},
					{name:'creator'}
          		])
    });
    
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  {
           header: '编号',
           width: 100,
           sortable: true,
           dataIndex: 'accessoryId',
           hidden:true
           },
		　{header: '流程实例编号',
           width: 100,
           sortable: false,
           dataIndex: 'processId',
           hidden:true
           },
           {header: '原始文件名',
           width: 300,
           sortable: true,
           dataIndex: 'originalFileName',
           renderer:renderOper
           },
		   {header: '文件描述',
           width: 300,
           sortable: false,
           dataIndex: 'fileDescription'
           },
           {header: '文件路径',
           width: 300,
           sortable: true,
           dataIndex: 'filePath',
           hidden:true
           },
           {header: '新文件名',
           width: 100,
           sortable: true,
           dataIndex: 'newFileName',
           hidden:true
           },
           {header: '上传时间',
           width: 100,
           sortable: true,
           dataIndex: 'updateDate'
           },
           {header: '上传者',
           width: 100,
           sortable: true,
           dataIndex: 'creator'
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
        ds: ds,
        cm: cm,
        bbar:bbar,
        tbar:toolbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        layout:'fit'
    });
    
     function renderOper(value, p, record){
    	return String.format('<a href="{0}">{1}</a>',record.data.filePath,value);
    }
	
	ds.load({params:{start:0, limit:50},arg:[]});
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'panel',
				border:false,
            	defaults:{bodyStyle:'padding:0px'},
            	layout:'fit',
            	items:[grid]
			}]
		}]
	});
});
</script>
