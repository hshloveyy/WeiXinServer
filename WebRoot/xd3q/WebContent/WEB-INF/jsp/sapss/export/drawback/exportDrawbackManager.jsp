<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报废管理</title>
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
      <td width="12%" height="20" align="right">合同号:</td>
      <td width="20%"><input name="hdrq" type="text" size="20"/></td>
      <td width="8%" align="right">核销单号</td>
      <td width="30%" ><input name="writeNo" type="text" size="20" /></td>
      <td width="10%" align="right">出口货物通知单:</td>
      <td width="20%"><input name="noticeNo" type="text" size="20" /></td>
    </tr>
     <tr >
      <td width="12%" height="20" align="right">部门:</td>
      <td width="20%"><div id="dept"></div></td>
      <td width="8%" align="right">核销日期</td>
      <td width="30%" ><table><tr><td><input name="hexrq" type="text"/></td><td>-</td><td><input name="hexrq1" type="text"/></td></tr></table></td>
      <td width="10%" align="right">税款到帐日:</td>
      <td width="20%"><table><tr><td><input name="skhkrq" type="text"/></td><td>-</td><td><input name="skhkrq1" type="text"/></td></tr></table></td>
    </tr>
     <tr >
      <td align="right">退税申报日:</td>
      <td ><table><tr><td><input type="text" name="startDate"/></td><td>-</td><td><input type="text" name="endDate"/></td></tr></table></td>
      <td align="right">出口日期:</td>
      <td ><table><tr><td><input type="text" name="ckDate"/></td><td>-</td><td><input type="text" name="ckDate1"/></td></tr></table></td>
      <td colspan="2"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/><a onbeforeactivate="onbeforClickA()" href="shipNotifyController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></td>
    </tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
function onbeforClickA(){
	var param = Form.serialize('findCondictionFR');
	var requestUrl = 'exportDrawbackController.spr?action=dealOutToExcel&' + param+'&deptId=' + selectId_dept; 
	$('aaa').href=requestUrl;
}
document.onkeydown = function(){if (event.keyCode == 13){find();}}
var ds;
var tempGrid;
var callbackFlag;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var add = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加','exportDrawbackController.spr?action=toExportDrawbackInfo&tradeType=${tradeType}','',find,{width:900,height:500});
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
   									callbackFlag = 'del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&id='+records[0].json.EXPORT_DRAWBACK_ID; 
 										new AjaxEngine('exportDrawbackController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
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
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'exportDrawbackController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'EXPORT_DRAWBACK_ID'},
            		{name:'EXPORT_APPLY_ID'},
					{name:'EXPORT_APPLY_NO'},
					{name:'WRITE_DATE'},
					{name:'DRAWBACK_DATE'},
					{name:'DRAWBACK_RATE'},
					{name:'DRAWBACK_VALUE'},
					{name:'DRAWBACK_REAL'},
					{name:'SHIPING_VALUE'},
					{name:'RATE'},
					{name:'IMPOSE_RATE'},
					{name:'CREATOR'},
					{name:'CREATOR_TIME'},
					{name:'CREATOR_DEPT'}
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
            dataIndex: 'EXPORT_DRAWBACK_ID'
        },{
       		header: '操作',
       		width: 100,
       		sortable: false,
       		dataIndex: 'oper',
       		renderer:operaRD
       },{
			header: '出口货物通知单ID',
            sortable: false,
            hidden:true,
            dataIndex: 'EXPORT_APPLY_ID'
        },{
			header: '出口货物通知单号',
           	width: 100,
           	dataIndex: 'EXPORT_APPLY_NO',
           	sortable: true
           },{
           header: '核销日期',
           hidden:true,
           sortable: false,
           dataIndex: 'WRITE_DATE'
           },
           {header: '退税申报日',
           width: 100,
           sortable: true,
           dataIndex: 'DRAWBACK_DATE'
           },{
           		header: '退税率',
          		width: 100,
           		sortable: false,
           		dataIndex: 'DRAWBACK_RATE'
           },
           {header: '退税申报额',
           width: 100,
           sortable: false,
           dataIndex: 'DRAWBACK_VALUE'
           },
           {header: '实际退税额',
           width: 100,
           sortable: false,
           dataIndex: 'DRAWBACK_REAL'
           },{
           		header: '海运费',
           		width: 100,
           		sortable: false,
           		dataIndex: 'SHIPING_VALUE'
           },{
           		header: '税率',
           		width: 100,
           		sortable: true,
           		dataIndex: 'RATE'
           }
           ,{
           		header: '创建时间',
           		width: 100,
           		sortable: false,
           		dataIndex: 'CREATOR_TIME'
           },{
           		header: '创建者所在部门',
           		width: 100,
           		sortable: false,
           		dataIndex: 'CREATOR_DEPT',
           		hidden:true
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
        return "<a href='#' onclick='modify()'>修改</a>";
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
			width: 90,
			applyTo:'startDate',
			vtype: 'daterange',
			endDateField: 'endApplyTime'
	   	});
	   	var d2 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"endApplyTime",
			name:"endApplyTime",
			width: 90,
			applyTo:'endDate',
			vtype: 'daterange',
			startDateField: 'applyTime'
	   	});
	   	
	   	var d3 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"hexrq",
			name:"hexrq",
			width: 90,
			applyTo:'hexrq',
			vtype: 'daterange',
			startDateField: 'hexrq'
	   	});
	   	var d4 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"hexrq1",
			name:"hexrq1",
			width: 90,
			applyTo:'hexrq1',
			vtype: 'daterange',
			startDateField: 'hexrq1'
	   	});
	   	var d5 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"skhkrq1",
			name:"skhkrq1",
			width: 90,
			applyTo:'skhkrq1',
			vtype: 'daterange',
			startDateField: 'skhkrq1'
	   	});
	   	var d6 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"skhkrq",
			name:"skhkrq",
			width: 90,
			applyTo:'skhkrq',
			vtype: 'daterange',
			startDateField: 'skhkrq'
	   	});
	   	var d7 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"ckDate",
			name:"ckDate",
			width: 90,
			applyTo:'ckDate',
			vtype: 'daterange',
			startDateField: 'ckDate'
	   	});
	   	var d8 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"ckDate1",
			name:"ckDate1",
			width: 90,
			applyTo:'ckDate1',
			vtype: 'daterange',
			startDateField: 'ckDate1'
	   	});
	   	
});
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.ok);
	if(callbackFlag=='del')
		find();
}

function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','exportDrawbackController.spr?action=forModify&id='+record[0].json.EXPORT_DRAWBACK_ID,'',find,{width:800,height:500});
}
function modifyCallback(){
	var url = 'exportDrawbackController.spr?action=find&tradeTypeSingle=${tradeType}';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'exportDrawbackController.spr?action=find&'+Form.serialize('findCondictionFR')+'&deptId=' + selectId_dept;
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
