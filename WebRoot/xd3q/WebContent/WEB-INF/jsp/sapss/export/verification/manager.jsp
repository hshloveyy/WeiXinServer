<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口核销单申领表</title>
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
      <td width="12%" height="20" align="right">部门:</td>
      <td width="20%"><div id="dept"></div></td>
      <td width="8%" align="right">核销单号</td>
      <td width="30%" ><input name="writeNo" type="text" size="20" /></td>
      <td width="10%" align="right">五联单号:</td>
      <td width="20%"><input name="noticeNo" type="text" size="20" /></td>
    </tr>
     <tr >
      <td width="12%" height="20" align="right">发票号:</td>
      <td width="20%"><input name="taxNo" type="text" size="20"/></td>
      <td width="8%" align="right">领单日期</td>
      <td width="30%" ><table><tr><td><input name="receiptDate" type="text"/></td><td>-</td><td><input name="receiptDate1" type="text"/></td></tr></table></td>
      <td width="10%" align="right">回单日期:</td>
      <td width="20%"><table><tr><td><input name="backDate" type="text"/></td><td>-</td><td><input name="backDate1" type="text"/></td></tr></table></td>
    </tr>
    <tr >
      <td align="right"></td>
      <td ></td>
      <td colspan="4"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/><a onbeforeactivate="onbeforClickA()" href="shipNotifyController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></td>
    </tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
function onbeforClickA(){
	var param = Form.serialize('findCondictionFR');
	var requestUrl = 'verificationReceiptController.spr?action=dealOutToExcel&' + param+'&deptId=' + selectId_dept; 
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
			top.ExtModalWindowUtil.show('增加','verificationReceiptController.spr?action=createView','',find,{width:900,height:500});
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
 	 									var parm ='?action=delete&verificationReceiptId='+records[0].json.VERIFICATION_RECEIPT_ID; 
 										new AjaxEngine('verificationReceiptController.spr',
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
        		proxy : new Ext.data.HttpProxy({url:'verificationReceiptController.spr?action=find'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'VERIFICATION_RECEIPT_ID'},
            		{name:'EXPORT_APPLY_ID'},
					{name:'EXPORT_APPLY_NO'},
					{name:'RECEIPT_DEPT_NAME'},
					{name:'RECEIPT_DEPT_ID'},
					{name:'RECEIPT_MAN'},
					{name:'RECEIPT_DATE'},
					{name:'BACK_DATE'},
					{name:'WRITE_NO'},
					{name:'TAX_NO'},
					{name:'MARK'}
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
            dataIndex: 'VERIFICATION_RECEIPT_ID'
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
           header: '部门',
           sortable: false,
           dataIndex: 'RECEIPT_DEPT_NAME'
           },
           {header: '领单人',
           width: 100,
           sortable: true,
           dataIndex: 'RECEIPT_MAN'
           },{
           		header: '领单日期',
          		width: 100,
           		sortable: false,
           		dataIndex: 'RECEIPT_DATE'
           },
           {header: '回单日期',
           width: 100,
           sortable: false,
           dataIndex: 'BACK_DATE'
           },
           {header: '发票号',
           width: 100,
           sortable: false,
           dataIndex: 'TAX_NO'
           },{header: '核销单号',
           width: 100,
           sortable: false,
           dataIndex: 'WRITE_NO'
           },{
           		header: '备注',
           		width: 100,
           		sortable: false,
           		dataIndex: 'MARK'
           }
    ]);
    itemCm.defaultSortable = true;
    function renderHallName(value, meta, rec, rowIdx, colIdx, ds){
        return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
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
	   	
	   	var d3 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"receiptDate",
			name:"receiptDate",
			width: 90,
			applyTo:'receiptDate',
			vtype: 'daterange',
			startDateField: 'receiptDate'
	   	});
	   	var d4 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"receiptDate1",
			name:"receiptDate1",
			width: 90,
			applyTo:'receiptDate1',
			vtype: 'daterange',
			startDateField: 'receiptDate1'
	   	});
	   	var d5 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"backDate1",
			name:"backDate1",
			width: 90,
			applyTo:'backDate1',
			vtype: 'daterange',
			startDateField: 'backDate1'
	   	});
	   	var d6 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"backDate",
			name:"backDate",
			width: 90,
			applyTo:'backDate',
			vtype: 'daterange',
			startDateField: 'backDate'
	   	});
	   	
});
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if(callbackFlag=='del')
		find();
}

function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','verificationReceiptController.spr?action=updateView&verificationReceiptId='+record[0].json.VERIFICATION_RECEIPT_ID,'',find,{width:800,height:500});
}
function modifyCallback(){
	var url = 'verificationReceiptController.spr?action=find';
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'verificationReceiptController.spr?action=find&'+Form.serialize('findCondictionFR')+'&deptId=' + selectId_dept;
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
