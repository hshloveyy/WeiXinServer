<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理物料查询</title>
</head>
<body >
<div id="queryDiv">
<form name="queryForm">
	<table width="100%">
	<tr>
		<td width="100" align="right">物料：</td>	
		<td><input type="text" name="materialName" readonly="readonly"/>
			<input type="hidden" name="materialNo" id="materialNo"/>
			<input type="button" value="..." onclick="findMaterial()"/>
		</td>	
		<td align="right">工厂：</td>	
		<td><div id='div_factory'></div></td>	
		<td align="right">仓库：</td>	
		<td><div id='div_warehouse'></div></td>	
		
	</tr>
	<tr>
		<td align="right">合同号：</td>	
		<td><input type="text" name="contractNo"/></td>	
		<td align="right">(审批通过)时间从：</td>	
		<td><input type="text" name="startTime" /></td>	
		<td align="right">到：</td>	
		<td><input type="text" name="endTime" /></td>	
	</tr>
	<tr>	
	    <td align="right">批次：</td>	
		<td><input type="text" name="batchNo" id="batchNo"/></td>
		<td colspan="4" align="left"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/><a onbeforeactivate="onbeforClickA()" href="masterQueryController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv"></div>	
</body>
<script type="text/javascript">
 function findMaterial(){
		top.ExtModalWindowUtil.show('查找物料',
				'masterQueryController.spr?action=toFindMaterial',
				'',findMaterialCallback,{width:755,height:450});
	
	}
 function findMaterialCallback(){
		var cb = top.ExtModalWindowUtil.getReturnValue();
		Ext.getDom('materialName').value=cb.MAKTX;
		Ext.getDom('materialNo').value=cb.MATNR;
 }	
 
 function onbeforClickA(){
	var param = Form.serialize('queryForm');
	var requestUrl = 'outerReportController.spr?action=dealOutToExcel&' + param;
	//requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}
</script>
<script type="text/javascript">
var grid;
var store;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	var fields=[
	        	{name:'MATERIAL_CODE'},
	        	{name:'MATERIAL_NAME'},
	        	{name:'FACTORY_D_FACTORY'},
	        	{name:'BATCH_NO'},
	        	{name:'APPROVED_TIME'},
	        	{name:'QUANTITY'},
	        	{name:'OUT'},
	        	{name:'LEAVE'},
	        	{name:'IN_TOTAL'},
	        	{name:'OUT_TOTAL'},
	        	{name:'LEAVE1'},
	        	{name:'MATERIAL_UNIT'},
	        	{name:'CONTRACT_PAPER_NO'},
	        	{name:'CONTRACT_NAME'}
	    ];
    //material_code,material_name,Ekpo_Werks_Name,batch_no,operate_time,quantity,out,leave,material_unit,contract_paper_no,contract_name
	var columns=[new Ext.grid.RowNumberer(),
		{header:'物料编号',sortable:true,dataIndex:'MATERIAL_CODE'},
		{header:'物料描述',sortable:true,dataIndex:'MATERIAL_NAME'},
		{header:'工厂',sortable:true,dataIndex:'FACTORY_D_FACTORY'},
		{header:'批次',sortable:true,dataIndex:'BATCH_NO'},
		{header:'进仓时间',sortable:true,dataIndex:'APPROVED_TIME'},
		{header:'单位',sortable:true,dataIndex:'MATERIAL_UNIT'},
		{header:'进仓数量',sortable:true,dataIndex:'QUANTITY'},
		{header:'进仓金额',sortable:true,dataIndex:'IN_TOTAL'},
		{header:'出仓数量',sortable:true,dataIndex:'OUT'},
		{header:'出仓金额',sortable:true,dataIndex:'OUT_TOTAL'},
		{header:'当前库存',sortable:true,dataIndex:'LEAVE',hidden:true},
		{header:'差额',sortable:true,dataIndex:'LEAVE1'},
		{header:'外部纸质合同号',sortable:true,dataIndex:'CONTRACT_PAPER_NO'},
		{header:'合同名称',sortable:true,dataIndex:'CONTRACT_NAME'}
	];
	store = new Ext.data.Store({
    			proxy : new Ext.data.HttpProxy({url:''}),
    			reader: new Ext.data.JsonReader({
        					root: 'root',
        					totalProperty: 'totalProperty'
        				},
        				fields
				)
	});
	var cm = new Ext.grid.ColumnModel(
		columns
	);
	var pagingBar = new Ext.PagingToolbar({
         	displayInfo:true,
         	emptyMsg:'没有数据显示',
         	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
         	store:store,
         	pageSize:20
     	});
	
	grid = new Ext.grid.GridPanel({
  		applyTo:'gridDiv',
		ds:store,
		cm:cm,
		bbar:pagingBar,
		border:true,
		loadMask:true,
		autoScroll:true,
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
        height:'500',
		viewConfig : {  
               // forceFit : true,  
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
	//data range
	Ext.apply(Ext.form.VTypes, {
	  daterange: function(val, field) {
	    var date = field.parseDate(val);
	
	    var dispUpd = function(picker) {
	      var ad = picker.activeDate;
	      picker.activeDate = null;
	      picker.update(ad);
	    };
	    
	    if (field.startDateField) {
	      var sd = Ext.getCmp(field.startDateField);
	      sd.maxValue = date;
	      if (sd.menu && sd.menu.picker) {
	        sd.menu.picker.maxDate = date;
	        dispUpd(sd.menu.picker);
	      }
	    } else if (field.endDateField) {
	      var ed = Ext.getCmp(field.endDateField);
	      ed.minValue = date;
	      if (ed.menu && ed.menu.picker) {
	        ed.menu.picker.minDate = date;
	        dispUpd(ed.menu.picker);
	      }
	    }
	    return true;
	  }
	});
	var d1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"applyTime",
		name:"applyTime",
		width: 160,
		applyTo:'startTime',
		vtype: 'daterange',
		endDateField: 'endApplyTime'
   	});
   	var d2 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"endApplyTime",
		name:"endApplyTime",
		width: 160,
		applyTo:'endTime',
		vtype: 'daterange',
		startDateField: 'applyTime'
   	});
	var viewport = new Ext.Viewport({
	layout:"border",
	items:[{
		region:"north",
		title:'查询',
		collapsible: false,
		contentEl:'queryDiv'
	},
	{
		title:'代理物料库存',
		region:"center",
		layout:'fit',
		collapsible: false,
//		contentEl:'gridDiv'
		items:[grid]
	}]
});
});

function find(){
	var params = Form.serialize('queryForm');
	store.proxy=new Ext.data.HttpProxy({url:'outerReportController.spr?action=rtnAgentMaterialStorage&'+params});
	store.load({params:{start:0, limit:20}});
}    			
function fullScreen(){
	//<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id='webObject' name='webObject' width=0></OBJECT>
	$('webObject').execwb(7,1);
}
</script></html>
<fiscxd:dictionary divId="div_factory" fieldName="factory" dictionaryName="BM_FACTORY" width="150" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_warehouse" fieldName="warehouse" dictionaryName="BM_WAREHOUSE" width="150" ></fiscxd:dictionary>
