<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银行申请管理页面</title>
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
<form id="findSubjectFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="12%" height="20" align="right">SAP科目代码:</td>
      <td width="20%"><input name="subjectNO_SAP" type="text" size="20"/></td>
      <td width="12%" align="right">科目短文:</td>
      <td width="20%" colspan="3"><input name="shortDescription" type="text" size="20"/></td>
      <td width="10%" align="right">审批状态:</td>
      <td width="20%"><div id="subjectStateDiv"></div></td>
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
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加会计科目','subjectController.spr?action=create','',find,{width:800,height:500});
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
												url: 'subjectController.spr?action=delete&subjectID='+records[0].json.SUBJECT_ID,
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
	//extComponentServlet?type=grid&grid_columns=SUBJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'subjectController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'SUBJECT_ID'},
            		{name:'SUBJECT_NO_SAP'},
					{name:'SHORT_DESCRIPTION'},
					{name:'ACCOUNT_GROUP'},
					{name:'CURRENCY'},
					{name:'COL_STATE_GROUP'},
					{name:'APPLY_TIME'},
					{name:'SUBJECT_STATE_D_SUBJECT_STATE'},
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
			header: '科目ID',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'SUBJECT_ID'
           },{
			header: 'SAP科目代码',
           	width: 100,
           	dataIndex: 'SUBJECT_NO_SAP',
           	sortable: true
           },{
           header: '科目短文本',
           width: 100,
           sortable: false,
           dataIndex: 'SHORT_DESCRIPTION',
           renderer:renderHallName
           },
           {header: '帐户组',
           width: 100,
           sortable: true,
           dataIndex: 'ACCOUNT_GROUP'
           },{
           		header: '科目货币',
          		width: 100,
           		sortable: false,
           		dataIndex: 'CURRENCY'
           	//	renderer:trade_typeRD
           },
           {header: '字段状态群组',
           width: 100,
           sortable: false,
           dataIndex: 'COL_STATE_GROUP'
           },{
           		header: '申报时间',
           		width: 100,
           		sortable: false,
           		dataIndex: 'APPLY_TIME'
           },{
           		header: '审批状态',
           		width: 100,
           		sortable: true,
           		dataIndex: 'SUBJECT_STATE_D_SUBJECT_STATE'
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
    	var state = record.data.SUBJECT_STATE_D_SUBJECT_STATE;
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
    	id:'subjectGrid',
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
			title:"会计科目申请列表",
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
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.SUBJECT_ID,
		'',	'',	{width:880,height:400});
}
function operaForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('修改会计科目','subjectController.spr?action=modify&from=modify&subjectID='+record[0].json.SUBJECT_ID,'',modifyCallback,{width:800,height:400});
		}
}
function submitWorkflowForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('提交会计科目申请','subjectController.spr?action=modify&from=workflow&subjectID='+record[0].json.SUBJECT_ID,'',modifyCallback,{width:800,height:400});
		}
}
function viewProjectForm(){
		if(tempGrid.selModel.hasSelection()){
			var record = tempGrid.selModel.getSelections();
			top.ExtModalWindowUtil.show('查看会计科目申请','subjectController.spr?action=modify&from=view&subjectID='+record[0].json.SUBJECT_ID,'',modifyCallback,{width:800,height:400});
		}
}
function modifyCallback(){
	var url = 'subjectController.spr?action=find';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'subjectController.spr?action=find&'+Form.serialize('findSubjectFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dictionary divId="subjectStateDiv" fieldName="state" dictionaryName="BM_SUBJECT_STATE" width="155"></fiscxd:dictionary>