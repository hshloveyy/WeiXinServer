<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投资管理</title>
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
	<tr>
		<td>投资类型</td>
		<td>
			 <select name="investmentType">
        		<option value="">请选择</option>
        		<option value="A">A 类</option>
        		<option value="B">B 类</option>
        	</select>
			
		</td>
		<td>审批类型</td>
		<td><div id="childTypeDiv"></div></td>
		<td>审批部门</td>
		<td><div id="deptDiv"></div></td>
		<td>子流程</td>
		<td><div id="subFlowDiv"></div></td>
	</tr>
	<tr>
		<td>开始日期</td>
		<td><input type="text" name="startDate"/></td>
		<td>结束日期</td>
		<td><input type="text" name="endDate"/></td>
		<td>状态</td>
		<td><input type="text" name=""/></td>
		<td colspan="2"></td>
	</tr>
    <tr align="center">
      <td align="center" colspan="8"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
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
var callbackFlag;
var childType,subFlow;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加','investmentController.spr?action=toInfo&TYPE=${TYPE}','',find,{width:800,height:500});
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
					}else if(records[0].data.STATUS_D_EXAMINE_STATE=='新增'){
							top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag = 'del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delInfo&infoId='+records[0].json.INFO_ID; 
 										new AjaxEngine('investmentController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					}else{
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的记录');
						}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});

    var itemTbar = new Ext.Toolbar({
		items:[add,'-',del]
	});
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=find&TYPE=${TYPE}'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'INFO_ID'},
            		{name:'INVESTMENT_TYPE'},
					{name:'STATUS_D_EXAMINE_STATE'},
					{name:'EXAMINE_TYPE'},
					{name:'DEPT_NAME'},
					{name:'CREATE_TIME'},
					{name:'CREATOR'},
					{name:'SUB_FLOW'},
					
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,
		{
			header: 'ID',
            sortable: false,
            hidden:true,
            dataIndex: 'INFO_ID'
        },{
       		header: '操作',
       		width: 100,
       		sortable: false,
       		dataIndex: 'oper',
       		renderer:operaRD
        },{
           header: '状态',
           width: 100,
           sortable: true,
           dataIndex: 'STATUS_D_EXAMINE_STATE'
        },{
			header: '审批类型',
           	dataIndex: 'EXAMINE_TYPE',
           	width:130,
           	renderer:renderExamineType,
           	sortable: true
        },{
			header: '投资类型',
           	width: 50,
           	dataIndex: 'INVESTMENT_TYPE',
           	sortable: true
           },{
           header: '子流程',
           sortable: false,
           width:130,
           dataIndex: 'SUB_FLOW'
           },
           {header: '部门',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '申请日期',
           width: 100,
           sortable: true,
           dataIndex: 'CREATE_TIME'
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
    //
    function renderExamineType(value,metadata,record){
		if(value=='1')
			return '投资项目规划与计划审批';
		if(value=='2')
			return '投资项目决策审批';
		if(value=='3')
			return '投资项目执行审批';
		if(value=='4')
			return '投资项目日常监控与专项评估';
		if(value=='5')
			return '投资项目处置';
    }
    function operaRD(value,metadata,record){
        if(record.data.CREATOR==''){}
        if(record.data.STATUS_D_EXAMINE_STATE=='新增'){
        	return "<a href='#' onclick='modify()'>修改</a> <a href='#' onclick='modify()'>提交</a>";
        }else
        	return "<a href='#' onclick='view()'>查看</a> <a href='#' onclick='viewHistory()'>流程查看</a>";
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
			title:"列表",
			items:[itemGrid]
		}]
	});
   	//
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
			width: 150,
			applyTo:'startDate',
			vtype: 'daterange',
			endDateField: 'endApplyTime'
	   	});
	   	var d2 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"endApplyTime",
			name:"endApplyTime",
			width: 150,
			applyTo:'endDate',
			vtype: 'daterange',
			startDateField: 'applyTime'
	   	});

	   	var childType_ds = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=rtnChildTypeDS&module=${TYPE}'}),
			reader: new Ext.data.JsonReader({
	    		root: 'root',
	    		totalProperty: 'totalProperty'
	    	},[
	    		{name:'CHILD_TYPE'},
	    		{name:'CHILD_TYPE_NAME'}
	  		])
		});
		var subFlow_ds = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url:'investmentController.spr?action=rtnSubFlowDS&module=${TYPE}'}),
			reader: new Ext.data.JsonReader({
	    		root: 'root',
	    		totalProperty: 'totalProperty'
	    	},[
	    		{name:'SUB_FLOW'},
	    		{name:'SUB_FLOW_NAME'}
	  		])
		});
		childType_ds.load();
		subFlow_ds.load();

	   childType = new Ext.form.ComboBox({ 
	        store: childType_ds, 
	        displayField:'CHILD_TYPE_NAME', 
	        valueField: 'CHILD_TYPE_NAME' , 
	        typeAhead: true, 
	        mode: 'local', 
	        triggerAction: 'query', 
	        emptyText:'请选择...', 
	        renderTo:'childTypeDiv',
	        selectOnFocus:true 
	    }); 
	   childType.setValue('${info.childType}');
	  subFlow = new Ext.form.ComboBox({ 
	        store: subFlow_ds, 
	        displayField:'SUB_FLOW_NAME', 
	        valueField: 'SUB_FLOW_NAME' , 
	        typeAhead: true, 
	        mode: 'local', 
	        triggerAction: 'query',
	        emptyText:'请选择...', 
	        renderTo:'subFlowDiv',
	        selectOnFocus:true 
	        });
	   subFlow.setValue('${info.subFlow}');
	   childType.on('select', function() { 
		   subFlow.reset();
		   subFlow_ds.proxy= new Ext.data.HttpProxy({url:encodeURI('investmentController.spr?action=rtnSubFlowDS&module=${TYPE}&childType=' + childType.getValue())});
		   subFlow_ds.load(); 
	    });


	   	
});
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if(callbackFlag=='del')
		find();
}
function viewHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.INFO_ID,
		'',	'',	{width:880,height:400});
}
function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','investmentController.spr?action=modify&infoId='+record[0].json.INFO_ID,'',find,{width:800,height:500});
}

function view(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看','investmentController.spr?action=view&infoId='+record[0].json.INFO_ID,'',find,{width:800,height:500});
}

function find(){
	var url = 'investmentController.spr?action=find&investmentType='+$('investmentType').value;
	url = url+'&childType='+childType.getValue()+'&subFlow='+subFlow.getValue();
	url	= url+'&startDate='+$('startDate').value+'&endDate='+$('endDate').value;
	url = url+'&deptId='+selectId_deptDiv+'&TYPE=${TYPE}';
	ds.proxy= new Ext.data.HttpProxy({url:encodeURI(url)});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="deptDiv" rootTitle="部门信息" width="155"></fiscxd:dept>