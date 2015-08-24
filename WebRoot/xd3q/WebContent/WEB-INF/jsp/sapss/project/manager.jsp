<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
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
      <td width="30%" colspan="3"><input name="underTaker" type="text" size="20"/></td>
      <td width="10%" align="right">项目编号:</td>
      <td width="20%"><input name="projectNo" type="text" size="20" /></td>
    </tr>
    <tr >
      <td align="right">项目名称:</td>
      <td ><input name="projectName" type="text" size="20" /></td>
      <td align="right">申报日期:</td>
      <td ><div id="applyTimeDiv"></div></td>
      <td >至</td>
      <td ><div id="endApplyTimeDiv"></div></td>
      <td align="right">项目状态:</td>
      <td><div id="projectStateDiv"></div></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="8"><input type="button" onkeydown="keyd()" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
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

    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加${typeName}立项','projectController.spr?action=create&tradeType=${tradeType}','',find,{width:800,height:500});
		}
   	});
   	
   	var del = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
 									if(buttonId=='yes'){
 											Ext.Ajax.request({
												url: 'projectController.spr?action=delete&projectId='+records[0].json.PROJECT_ID,
												success: function(response, options){
												   var json = Ext.util.JSON.decode(response.responseText);
													if(json.success){
														Ext.Msg.alert('提示',json.msg);
														find();
													}else{
														Ext.Msg.alert('提示',json.msg);
													}
												}
											});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});
   	
   	
   	var print = new Ext.Toolbar.Button({
   		text:'打印',
	    iconCls:'find',
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					    window.open('projectController.spr?action=dealPrintV2&projectId='+records[0].json.PROJECT_ID,'_blank','');
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});

    var itemTbar = new Ext.Toolbar({
		items:[add,'-',del,'-',print]
	});
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'projectController.spr?action=find&state=3&tradeTypeSingle=${tradeType}'}),
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
					{name:'contrctGroup'},
					{name:'CREATOR'}
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    ////project_name,apply_time,project_no,trade_type,nuder_taker,org_name
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '项目ID',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'PROJECT_ID'
           },{
           		header: '操作',
           		width: 130,
           		sortable: false,
           		dataIndex:'opera',
           		renderer: operaRD
           },{
           		header: '项目状态',
           		width: 100,
           		sortable: false,
           		dataIndex: 'PROJECT_STATE_D_PROJECT_STATE'
           },{
			header: '项目编号',
           	width: 100,
           	dataIndex: 'PROJECT_NO',
           	sortable: true
           },{
           header: '项目名称',
           width: 100,
           sortable: false,
           dataIndex: 'PROJECT_NAME',
           renderer:renderHallName
           },
           {header: '申报日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },{
           		header: '贸易类型',
          		width: 100,
           		sortable: false,
           		dataIndex: 'TRADE_TYPE_D_BUSINESS_TYPE'
           	//	renderer:trade_typeRD
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
           },{
           		header: '创建者',
           		width: 100,
           		sortable: false,
           		hidden:true,
           		dataIndex: 'CREATOR'
           }
    ]);
    itemCm.defaultSortable = true;
    function renderHallName(value, meta, rec, rowIdx, colIdx, ds){
        return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
    }    
    //审批通过时间渲染
    function approvedTimeRD(value,metadata,record){
    	var apptime = record.data.APPROVED_TIME;
    	if(apptime!=null)
    		return apptime.substring(0,4)+'-'+apptime.substring(4,6)+'-'+apptime.substring(6,8)+' '+apptime.substring(8,10)+':'+apptime.substring(10,12)+':'+apptime.substring(12,14);
    	else
    		return '';	
    }
    function operaRD(value,metadata,record){
    	var state = record.data.PROJECT_STATE_D_PROJECT_STATE;
    	if('${loginer}'== record.data.CREATOR){
    		if (state=='立项'){    		
        		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitWorkflowForm()">提交</a>';
       	 	}else if(state=='生效'){
        		return '<a href="#" onclick="viewProjectForm()">查看</a> <a href="#" onclick="toChangeForm()">变更</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	}else if(state=='变更'){
        		return '<a href="#" onclick="viewChangeForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	}else if(state=='变更通过'){
            		return '<a href="#" onclick="viewProjectForm()">查看</a> <a href="#" onclick="toChangeForm()">变更</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';	
            }else{
        		return '<a href="#" onclick="viewProjectForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
            }		
    	}else{
        	if(state.indexOf('变更')!=-1)
    			return '<a href="#" onclick="viewChangeForm()">查看立项</a>';
    		else
    			return '<a href="#" onclick="viewProjectForm()">查看立项</a>';
    	}
    }
    function contractGroupRD(value,metadata,record){
        return '<a href="#" onclick="contractGroupForm();">查看合同组</a>';
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
			height:105,
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

});
function viewChangeHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(records[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'变更详情信息','changeProjectController.spr?action=toWorkflowPictureViw&projectId='+records[0].json.PROJECT_ID,
		'',	'',	{width:880,height:400});
}
function viewChangePassHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(records[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'变更详情信息','changeProjectController.spr?action=toWorkflowPictureViw&state=finish&projectId='+records[0].json.PROJECT_ID,
		'',	'',	{width:880,height:400});
}
function viewHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(records[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.PROJECT_ID,
		'',	'',	{width:880,height:400});
}
function toChangeForm(){
	if(tempGrid.selModel.hasSelection()){
		var record = tempGrid.selModel.getSelections();
		_getMainFrame().maintabs.addPanel(record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'立项变更','', 'projectController.spr?action=modify&from=changeW&projectId='+record[0].json.PROJECT_ID);
	}
}
function operaForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('修改'+record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'立项','projectController.spr?action=modify&from=modify&projectId='+record[0].json.PROJECT_ID,'',modifyCallback,{width:800,height:500});
		}
}
function submitWorkflowForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('提交'+record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'立项申请','projectController.spr?action=modify&from=workflow&projectId='+record[0].json.PROJECT_ID,'',modifyCallback,{width:800,height:500});
		}
}
function viewChangeForm(){
	if(tempGrid.selModel.hasSelection()){
		var record = tempGrid.selModel.getSelections();
		_getMainFrame().maintabs.addPanel(record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'立项变更','', 'projectController.spr?action=modify&from=change&projectId='+record[0].json.PROJECT_ID);
	}
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看'+record[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'立项申请','projectController.spr?action=modify&from=view&projectId='+record[0].json.PROJECT_ID,'',modifyCallback,{width:800,height:500});
		}
}
function contractGroupForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看合同组','projectController.spr?action=showContractGroup&projectId='+record[0].json.PROJECT_ID,'',modifyCallback,{width:605,height:320});
		}
}
function modifyCallback(){
	var url = 'projectController.spr?action=find&tradeTypeSingle=${tradeType}';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'projectController.spr?action=find&tradeTypeSingle=${tradeType}&deptId=' + selectId_deptDiv+'&'+Form.serialize('findCondictionFR');
	
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="deptDiv" rootTitle="承办单位" width="140" ></fiscxd:dept>
<fiscxd:dictionary divId="projectStateDiv" fieldName="state" dictionaryName="BM_PROJECT_STATE" width="155"></fiscxd:dictionary>