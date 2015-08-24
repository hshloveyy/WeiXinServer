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
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
</style>
</head>
<body>
<div id="main" class="x-hide-display">
<form id="findCondictionFR" method="post">
<table width="100%" border="1" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr>
      <td height="20">模块名称：<input name="modalName" type="text" /></td>
      <td>业务信息：<input name="businessInfo" type="text" /></td>
      <td><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
</table>
</form>
</div>

<div id="div_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;

function find(){
	var url = 'orgController.spr?action=getAllProcessInstancesHistory&toUserId=${toUserId}&startTime=${startTime}&endTime=${endTime}&toId=${toId}&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}

function openWin(){
	var record = grid.selModel.getSelections();
	var modelName = record[0].data.modelName;
	var businessNote = record[0].data.businessNote;
	var endNodeName = record[0].data.endNodeName;
	_getMainFrame().maintabs.addPanel('审批信息','','workflowController.spr?action=finishWorkDetailView&businessRecordId='+record[0].data.businessRecordId+'&taskId='+record[0].data.taskId);

}

function openBdpWorkFlowWin()
{
	var record = grid.selModel.getSelections();
	var modelName = record[0].data.modelName;
	var businessNote = record[0].data.businessNote;
	var endNodeName = record[0].data.endNodeName;
	_getMainFrame().maintabs.addPanel('审批信息','',
	'workflowController.spr?action=finishWorkDetailViewByBdp&businessRecordId='+record[0].data.businessRecordId+'&taskId='+record[0].data.taskId);
	
}
//查看业务记录信息
function viewBusinessInfo(){

var record = grid.selModel.getSelections();
	var modelName = record[0].data.modelName;
	var businessNote = record[0].data.businessNote;
	var endNodeName = record[0].data.endNodeName;
	var processType = record[0].data.processType;
	var boId = record[0].data.boId;

	var url = 'workflowController.spr?action=toViewDetail&boId='+ boId +'&businessRecordId='+record[0].data.businessRecordId+'&modelName='+encodeURI(modelName);
	//+'-'+endNodeName+'-'+businessNote
	_getMainFrame().maintabs.addPanel(modelName,null,url);
	
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'modelName'},
					{name:'businessNote'},
					{name:'endNodeName'},
					{name:'insCreateTime'},
					{name:'insEndTime'},
					{name:'detail'},
					{name:'processId'},
					{name:'taskId'},
					{name:'boId'},
					{name:'processType'},
					{name:'businessRecordId'}
          		])
    });

    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		　{header: '模块名称',
           width: 80,
           sortable: false,
           dataIndex: 'modelName'
           },
		  {header: '业务信息',
           width: 240,
           sortable: false,
           dataIndex: 'businessNote'
           },
           {header: '审批结果',
           width: 100,
           sortable: true,
           dataIndex: 'endNodeName'
           },
           {header: '开始时间',
               width: 100,
               sortable: true,
               dataIndex: 'insCreateTime'
           },
           {header: '结束时间',
            width: 100,
            sortable: true,
            dataIndex: 'insEndTime'
           },
           {header: '详情',
            width: 100,
            sortable: true,
            dataIndex: 'detail',
            renderer:renderDetail
           },
           {header: 'processId',
           width: 100,
           sortable: true,
           dataIndex: 'processId',
           hidden:true
           },
           {header: 'taskId',
           width: 100,
           sortable: true,
           dataIndex: 'taskId',
           hidden:true
           },
           {header: 'processType',
           width: 100,
           sortable: true,
           dataIndex: 'processType',
           hidden:true
           },
           {header: 'businessRecordId',
           width: 100,
           sortable: true,
           dataIndex: 'businessRecordId',
           hidden:true
           }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
		viewConfig : {  
                forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        },          
        layout:'fit'
    });
    
    //ds.load({params:{start:0, limit:10},arg:[]});
    
    function renderDetail(value, p, record){
    var processType =  record.data.processType;
        if(processType=='1')
        {
    		return '<a href="#" onclick="openWin()">审批信息</a> <a href="#" onclick="viewBusinessInfo()">业务信息</a>';
        }
   		else
   		{
    		return '<a href="#" onclick="openBdpWorkFlowWin()">审批信息</a>  <a href="#" onclick="viewBusinessInfo()">业务信息</a>';
   		}
    	//return '<a href="#" onclick="openWin()">审批信息</a> <a href="#" onclick="viewBusinessInfo()">业务信息</a>';
    }

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			height:70,
			items:[{contentEl:'main'}]
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