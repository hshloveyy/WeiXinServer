<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>立项管理页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
.x-grid-row-bgcolor-effect{
	color:red;
}
</style>
</head>
<body>

<div id="div_center"></div>

<div id="div_south"></div>
<div id="gridTagDiv"></div>
<div id="main" class="x-hide-display">
<form id="findCondictionFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
  <tr >
      <td width="12%" height="20" align="right">承办部门:</td>
      <td width="20%"><div id="deptDiv"></div></td>
      <td width="8%" align="right">承 办 人:</td>
      <td width="25%" colspan="3"><input name="underTaker" type="text" size="20"/></td>
      <td width="10%" align="right">项目编号:</td>
      <td width="24%"><input name="projectNo" type="text" size="20" /></td>
    </tr>
    <tr >
      <td align="right">项目名称:</td>
      <td ><input name="projectName" type="text" size="20" /></td>
      <td align="right">申报日期:</td>
      <td colspan="3"><table><tr><td><div id="applyTimeDiv"></div></td>
      <td >至</td>
      <td ><div id="endApplyTimeDiv"></div></td></tr></table>
      </td>
      <td align="right">项目状态:</td>
      <td><div id="projectStateDiv"></div></td>
    </tr>
    <tr align="center">
      <td width="12%" height="20" align="right">贸易类型:</td>
      <td width="20%" align="left"><div id="tradeTypeDiv"></div></td>
      <td align="right">品名:</td>
      <td colspan="3"><input name="className" type="text" size="20" /></td>
      <td width="10%" align="right">客户/供应商:</td>
      <td width="24%"><input name="unitName" type="text" size="20" /></td>
	</tr>
    <tr align="center">
      <td width="12%" height="20" align="right">查询时间:</td>
      <td colspan="3"><table><tr><td><div id="queryTimeDiv"></div></td>
      <td >至</td>
      <td ><div id="endQueryTimeDiv"></div></td></tr></table>
      </td>
      <td colspan="6" align="center"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>
</body>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){find();}}
var ds;
var tempGrid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

    var itemTbar = new Ext.Toolbar({
	});
   	
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'projectController.spr?action=find_bw&state=3'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'PROJECT_ID'},
            		{name:'PROJECT_NAME'},
					{name:'APPLY_TIME'},
					{name:'PROJECT_NO'},
					{name:'TRADE_TYPE_D_BUSINESS_TYPE'},
					{name:'NUDER_TAKER'},
					{name:'ORG_NAME'},
					{name:'APPROVED_TIME'},
					{name:'PROJECT_STATE_D_PROJECT_STATE'},
					{name:'opera'},
					{name:'contrctGroup'}
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '项目ID',
           width: 50,
           sortable: false,
           hidden:true,
           dataIndex: 'PROJECT_ID'
           },{
           		header: '操作',
           		width: 40,
           		sortable: false,
           		dataIndex:'opera',
           		renderer: operaRD
           },{
			header: '项目编号',
           	width: 50,
           	dataIndex: 'PROJECT_NO',
           	sortable: true
           },{
           header: '项目名称',
           width: 100,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },{
           		header: '项目状态',
           		width: 50,
           		sortable: false,
           		dataIndex: 'PROJECT_STATE_D_PROJECT_STATE',
           		renderer:function(value,metaData){
           			if(value=='生效')
           				metaData.css='x-grid-row-bgcolor-effect';
           			return value;
           		}
           },{header: '申报日期',
           		width: 100,
           		sortable: true,
           		dataIndex: 'APPLY_TIME'
           },{
           		header: '贸易类型',
          		width: 100,
           		sortable: false,
           		dataIndex: 'TRADE_TYPE_D_BUSINESS_TYPE'
           },
           {header: '承办人',
           width: 100,
           sortable: false,
           dataIndex: 'NUDER_TAKER'
           },
           {header: '承办部门',
           width: 100,
           sortable: false,
           dataIndex: 'ORG_NAME'
           },{
           		header: '审批通过时间',
           		width: 100,
           		sortable: true,
           		dataIndex: 'APPROVED_TIME',
           		renderer:approvedTimeRD
           },{
           		header: '合同组',
           		width: 100,
           		sortable: false,
           		dataIndex: 'contrctGroup',
           		renderer: contractGroupRD
           }
    ]);
    itemCm.defaultSortable = true;
    function approvedTimeRD(value,metadata,record){
    	var apptime = record.data.APPROVED_TIME;
    	if(apptime!=null)
    		return apptime.substring(0,4)+'-'+apptime.substring(4,6)+'-'+apptime.substring(6,8)+' '+apptime.substring(8,10)+':'+apptime.substring(10,12)+':'+apptime.substring(12,14);
    	else
    		return '';	
    }
    function operaRD(value,metadata,record){
    	var state = record.data.PROJECT_STATE_D_PROJECT_STATE;
    	if(state!='立项')
    		return '<a href="#" onclick="viewProjectForm()">查看</a>'; //<a href="#" onclick="viewHistory()">流程跟踪</a>
        else 
        	return '<a href="#" onclick="viewProjectForm()">查看</a>	';
    }
    function contractGroupRD(value,metadata,record){
        return '<a href="#" onclick="contractGroupForm();">查看</a>';
    }
    
     var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
    });
    //itemStore.load();进入页面时不加载
    var itemGrid = new Ext.grid.GridPanel({
    	id:'contractGrid',
        ds: itemStore,
        cm: itemCm,
        sm: itemSm,
        bbar: paging,
        tbar: itemTbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'south',
        el: 'div_south',
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
    
    tempGrid = itemGrid;
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			height:130,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:"立项列表",
			items:[itemGrid]
		}]
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
		width: 100,
		renderTo:'applyTimeDiv',
		vtype: 'daterange',
		endDateField: 'endApplyTime'
   	});
   	var d2 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"endApplyTime",
		name:"endApplyTime",
		width: 100,
		renderTo:'endApplyTimeDiv',
		vtype: 'daterange',
		startDateField: 'applyTime'
   	});
	var d3 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"queryTime",
		name:"queryTime",
		width: 100,
		renderTo:'queryTimeDiv',
		vtype: 'daterange',
		endDateField: 'endQueryTime'
   	});
   	var d4 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"endQueryTime",
		name:"endQueryTime",
		width: 100,
		renderTo:'endQueryTimeDiv',
		vtype: 'daterange',
		startDateField: 'queryTime'
   	});

});
function viewHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看'+records[0].json.TRADE_TYPE_D_BUSINESS_TYPE+records[0].json.PROJECT_STATE_D_PROJECT_STATE+'审批历史信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PROJECT_ID,
		'',	'',	{width:905,height:340});
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var queryTime = Ext.getCmp("queryTime");
			var endQueryTime = Ext.getCmp("endQueryTime");
			if (queryTime && (queryTime.value=='' || queryTime.value==undefined )) {
				_getMainFrame().showInfo("请输入查询开始时间");
				return;
			}
			if (endQueryTime && (endQueryTime.value=='' || endQueryTime.value==undefined )) {
				_getMainFrame().showInfo("请输入查询结束时间");
				return;
			}
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看'+record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+record[0].json.PROJECT_STATE_D_PROJECT_STATE,'projectController.spr?action=modify_bw&from=view&projectId='+record[0].json.PROJECT_ID+'&queryTime='+queryTime.value+'&endQueryTime='+endQueryTime.value,'',modifyCallback,{width:800,height:500});
		}
}
function contractGroupForm(){
		if(tempGrid.selModel.hasSelection()){
			var queryTime = Ext.getCmp("queryTime");
			var endQueryTime = Ext.getCmp("endQueryTime");
			if (queryTime && (queryTime.value=='' || queryTime.value==undefined )) {
				_getMainFrame().showInfo("请输入查询开始时间");
				return;
			}
			if (endQueryTime && (endQueryTime.value=='' || endQueryTime.value==undefined )) {
				_getMainFrame().showInfo("请输入查询结束时间");
				return;
			}
			var record = tempGrid.selModel.getSelections()
			top.ExtModalWindowUtil.show('查看合同组','projectController.spr?action=showContractGroup2&projectId='+record[0].json.PROJECT_ID,'',modifyCallback2,{width:800,height:500});
			
		}
}
function modifyCallback2(){
	var contractGroupid='';
	var contractGroupno='';
	var projectId='';
	var queryTime = Ext.getCmp("queryTime");
	var endQueryTime = Ext.getCmp("endQueryTime");
	//取一个值
	var cb = top.ExtModalWindowUtil.getReturnValue();
	var contractGroupid='&contractGroupid=' + cb.CONTRACT_GROUP_ID;
	var contractGroupno=cb.CONTRACT_GROUP_NO;
	var projectId=cb.PROJECT_ID;
	//取多个值先不用
	/**
	var res = top.ExtModalWindowUtil.getReturnValue();
	for(var i=0;i < res.length;i++){
		contractGroupid= contractGroupid + '&contractGroupid=' + res[i].data.CONTRACT_GROUP_ID;
		contractGroupno = contractGroupno + ',' + res[i].data.CONTRACT_GROUP_NO;
		projectId=res[i].data.PROJECT_ID;
	}
	**/	
	
	top.ExtModalWindowUtil.show('查看合同组' + contractGroupno,'projectController.spr?action=modify_bw&from=view&projectId='+ projectId +  contractGroupid +'&queryTime='+queryTime.value+'&endQueryTime='+endQueryTime.value,'',modifyCallback,{width:800,height:500});
	
}

function modifyCallback(){
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'projectController.spr?action=find_bw&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="deptDiv" rootTitle="承办单位" width="140" isMutilSelect="true" ></fiscxd:dept>
<fiscxd:dictionary divId="tradeTypeDiv" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" width="158"></fiscxd:dictionary>
<fiscxd:dictionary divId="projectStateDiv" fieldName="state" dictionaryName="BM_PROJECT_STATE" width="155"></fiscxd:dictionary>