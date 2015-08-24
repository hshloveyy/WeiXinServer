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
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findContractFrom" name="findContractFrom">
<table width="100%">

<tr>
	<td align="right">合同编码：</td>
	<td>
	<input type="text" id="contractgroupno" name="contractgroupno" value=""></input>
	<input type="hidden" id="projectId" name="projectId" value="${projectId}"></input>	
	</td>	
	<td>
	<input type="button" value="查找" onclick="findContractInfo()"></input>
	<input type="reset" value="清空"></input>
	<input type="button" value="确定" onclick="getContractGroup(this)"></input>
	</td>
</tr>

</table>
</form>
</div>

<div id="div_center"></div>

</body>
</html>
<script type="text/javascript">

var contractInfogrid;		//合同期本信息列表
var contractInfods;
//查找按钮的单击事件
function findContractInfo(){
	var requestUrl = 'contractController.spr?action=findContractGroup&';
	requestUrl += Form.serialize('findContractFrom');
	requestUrl = requestUrl ;
	contractInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	contractInfods.load({params:{start:0, limit:10},arg:[]});
	
	
}

function getContractGroup(btn){
	btn.disabled=true;
	if (contractInfogrid.selModel.hasSelection()){
		var records = contractInfogrid.selModel.getSelections();
		if (records.length > 1){
			top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
		}else{
				top.ExtModalWindowUtil.setReturnValue(records[0].data);
				//取多个值先不用
				//top.ExtModalWindowUtil.setReturnValue(records);
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
		}
	}else{
		top.ExtModalWindowUtil.alert('提示','请选择一条记录');
	}
    	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var contractInfoPlant = Ext.data.Record.create([
		{name:'CONTRACT_GROUP_ID'},
		{name:'CONTRACT_GROUP_NAME'},
		{name:'CONTRACT_GROUP_NO'},
		{name:'CMD'},
		{name:'PROJECT_ID'},
		{name:'PROJECT_NAME'},
		{name:'CREATE_TIME'},
		{name:'REAL_NAME'}
	]);

	contractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractInfoPlant)
    });
    
    var contractInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var contractInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		contractInfosm,
		   {header: '合同组ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_ID',
           hidden:true
           },
		　 {header: '合同组名称',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
		　 {header: '备注',
           width: 100,
           sortable: false,
           dataIndex: 'CMD'
           },
           {header: '项目ID',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_ID',
           hidden:true
           },
           {header: '项目名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {
           header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATE_TIME'
           },
           {
           header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           }
    ]);
    contractInfocm.defaultSortable = true;
    
    var contractInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:contractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });

	contractInfogrid = new Ext.grid.EditorGridPanel({
    	id:'contractInfoGrid',
        ds: contractInfods,
        cm: contractInfocm,
        sm: contractInfosm,
        bbar: contractInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:20
		},{
			region:"center",
			layout:'fit',
			title: "合同组列表",
			items:[contractInfogrid]
		}]
	});
	
	findContractInfo();
});


</script>
