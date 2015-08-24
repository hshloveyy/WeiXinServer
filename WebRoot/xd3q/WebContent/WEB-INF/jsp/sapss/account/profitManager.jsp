<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>利润中心申请管理页面</title>
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
<form id="findProfitFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="12%" height="20" align="right">公司代码:</td>
      <td width="20%"><input name="companyCode" type="text" size="20"/></td>
      <td width="12%" align="right">利润中心名称:</td>
      <td width="20%" colspan="3"><input name="profitCenterName" type="text" size="20"/></td>
	  <td width="10%" align="right">利润中心组名称:</td>
      <td width="20%"><input name="profitGroupName" type="text" size="20"/></td>
	</tr>
	<tr>
	  <td width="12%" height="20" align="right">上层利润中心组:</td>
      <td width="20%"><input name="upProfitGroup" type="text" size="20"/></td>
      <td width="12%" align="right">是否改变标准层次:</td>
      <td width="20%" colspan="3">
	    <select name="isChangeStandard">
			<option value="">不限</option>
			<option value="1">是</option>
			<option value="0">否</option>
		</select></td>
      <td width="10%" align="right">审批状态:</td>
      <td width="20%"><div id="profitStateDiv"></div></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="8"><input type="button" value="查找" onClick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var tempGrid;
Ext.onReady(function(){
	//Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加利润中心','profitCenterController.spr?action=create','',find,{width:800,height:650});
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
												url: 'profitCenterController.spr?action=delete&profitID='+records[0].json.PROFIT_ID,
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

    var itemTbar = new Ext.Toolbar({
		items:[add,'-',del]
	});
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROFIT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'profitCenterController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'PROFIT_ID'},
            		{name:'COMPANY_CODE'},
					{name:'PROFIT_CENTER_NAME'},
					{name:'PROFIT_CENTER_NO'},
					{name:'UP_PROFIT_GROUP'},
					{name:'PROFIT_GROUP_NAME'},
					{name:'PROFIT_GROUP_NO'},
					{name:'APPLY_TIME'},
					{name:'PROFIT_STATE_D_PROFIT_STATE'},
					{name:'APPROVED_TIME'},
					{name:'CREATOR_NAME'},
					{name:'CREATOR_ID'},
					{name:'opera'}
          		])
    });
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    ////project_name,apply_time,project_no,trade_type,nuder_taker,org_name
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '利润中心ID',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'PROFIT_ID'
           },{
			header: '公司代码',
           	width: 100,
           	dataIndex: 'COMPANY_CODE',
           	sortable: true
           },{
           header: '利润中心名称',
           width: 100,
           sortable: false,
           dataIndex: 'PROFIT_CENTER_NAME',
           renderer:renderHallName
           },
           {header: '利润中心编号',
           width: 100,
           sortable: true,
           dataIndex: 'PROFIT_CENTER_NO'
           },{
           		header: '上层利润中心组',
          		width: 100,
           		sortable: false,
           		dataIndex: 'UP_PROFIT_GROUP'
           	//	renderer:trade_typeRD
           },
           {header: '利润中心组名称',
           width: 100,
           sortable: false,
           dataIndex: 'PROFIT_GROUP_NAME'
           },
           {header: '利润中心组编号',
           width: 100,
           sortable: false,
           dataIndex: 'PROFIT_GROUP_NO'
           },{
           		header: '申报时间',
           		width: 100,
           		sortable: false,
           		dataIndex: 'APPLY_TIME'
           },{
           		header: '审批状态',
           		width: 100,
           		sortable: true,
           		dataIndex: 'PROFIT_STATE_D_PROFIT_STATE'
           },{
           		header: '审批通过时间',
           		width: 100,
           		sortable: true,
           		dataIndex: 'APPROVED_TIME'
           },{
           		header: '创建者',
           		width: 100,
           		sortable: false,
           		dataIndex: 'CREATOR_NAME'
           },{
           		header: '创建者ID',
           		width: 100,
           		sortable: false,
				hidden: true,
           		dataIndex: 'CREATOR_ID'
           },{
           		header: '操作',
           		width: 100,
           		sortable: false,
           		dataIndex:'opera',
           		renderer: operaRD
           }
    ]);
    itemCm.defaultSortable = true;
    function renderHallName(value, meta, rec, rowIdx, colIdx, ds){
        return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
    }    
    function operaRD(value,metadata,record){
    	var state = record.data.PROFIT_STATE_D_PROFIT_STATE;
    	if('${loginer}'== record.data.CREATOR_ID){
    		if (state=='新增'){    		
        		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitWorkflowForm()">提交</a>';
       	 	}else{
        		return '<a href="#" onclick="viewProjectForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
            }		
    	}else{
        	return '<a href="#" onclick="viewProjectForm()">查看</a>';
    	}
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
    	id:'profitGrid',
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
			title:"利润中心申请列表",
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
function viewHistory(){
	var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.PROFIT_ID,
		'',	'',	{width:880,height:400});
}
function operaForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('修改利润中心','profitCenterController.spr?action=modify&from=modify&profitID='+record[0].json.PROFIT_ID,'',modifyCallback,{width:800,height:650});
		}
}
function submitWorkflowForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('提交利润中心申请','profitCenterController.spr?action=modify&from=workflow&profitID='+record[0].json.PROFIT_ID,'',modifyCallback,{width:800,height:650});
		}
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看利润中心申请','profitCenterController.spr?action=modify&from=view&profitID='+record[0].json.PROFIT_ID,'',modifyCallback,{width:800,height:650});
		}
}
function modifyCallback(){
	var url = 'profitCenterController.spr?action=find';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'profitCenterController.spr?action=find&'+Form.serialize('findProfitFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dictionary divId="profitStateDiv" fieldName="state" dictionaryName="BM_PROFIT_STATE" width="155"></fiscxd:dictionary>