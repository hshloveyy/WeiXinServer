<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口货物通知单管理</title>
<style type="text/css">
.add {
	background-image: url(images/fam/add.gif) !important;
}

.delete {
	background-image: url(images/fam/delete.gif) !important;
}

.reset {
	background-image: url(images/fam/refresh.gif) !important;
}

.find {
	background-image: url(images/fam/find.png) !important;
}
</style>
</head>
<body>

<div id="div_northForm">
<form action="" id="findshipNotifyFrom" name="findshipNotifyFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">物料名称：</td>
		<td><input type="text" id="material" name="material" value=""></input>
		</td>
		<td align="right">币别：</td>
		<td><input type="text" id="currency"
			name="currency" value=""></input></td>
	</tr>
	<tr>
		<td align="right">申报日期从：</td>
		<td>
		<div id="sDateDiv"></div>
		</td>
		<td align="right">到：</td>
		<td>
		<div id="eDateDiv"></div>
		</td>
		<td align="right"> 核销单号：</td>
		<td> <input type="text" id="writeNo" name="writeNo" value=""></td>
	</tr>
	<tr>
		<td align="right">领单时间：</td>
		<td><div id="sGetSheetTimeDiv"></div></td>
		<td align="right">到：</td>
		<td><div id="eGetSheetTimeDiv"></div></td>
		<td align="right"> 立项号：</td>
		<td> <input type="text" id="projectNo" name="projectNo" value=""></td>
   </tr>
   <tr>
		<td colspan="6" align="center"><input type="button" value="查找" onclick="findShipNotifyInfo()"><input type="reset" value="清空">&nbsp;&nbsp;&nbsp;&nbsp;
		<!-- input type="button" value="下载" onclick="downloadNotifyInfo()"--><a onbeforeactivate="onbeforClickA()" href="shipNotifyController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</td>
		
	</tr>
	
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
//贸易类型
var tradeType = '${tradeType}';

var strshipNotifyTitle ='';
var strOperType = '0';

var purchaserrecord;
var shipNotifyRecord;
var purchaserfieldName;
strshipNotifyTitle = Utils.getTradeTypeTitle(tradeType);


var shipNotifyInfogrid;		//出仓单信息列表
var shipNotifyInfods;

//shipNotify 查找按钮的单击事件
function findShipNotifyInfo(){
	var param = Form.serialize('findshipNotifyFrom');
	var requestUrl = 'shipNotifyController.spr?action=queryNotifyInfo&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	//alert(requestUrl);
	
	shipNotifyInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	shipNotifyInfods.load({params:{start:0, limit:10},arg:[]});
}

function downloadNotifyInfo(){
	top.Ext.Msg.show({
		title:'提示',
		msg: '是否确定下载记录',
		buttons: {yes:'是', no:'否'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
				var param = Form.serialize('findshipNotifyFrom');
		        var requestUrl = 'shipNotifyController.spr?action=dealOutToExcel&' + param;
		            requestUrl = requestUrl + '&deptId=' + selectId_dept;
		            $('aaa').href=requestUrl;
		            $('aaa').click();
		           // window.location.href=requestUrl;
			}
		},
		icon: Ext.MessageBox.QUESTION
	});
}
function onbeforClickA(){
	var param = Form.serialize('findshipNotifyFrom');
	var requestUrl = 'shipNotifyController.spr?action=dealOutToExcel&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	var shipNotifyInfoPlant = Ext.data.Record.create([
		{name:'DEPT_NAME'}, 
		{name:'PROJECT_NO'}, 
		{name:'PROJECT_NAME'}, 
		{name:'MATERIAL_NAME'}, 
		{name:'MATERIAL_UNIT'}, 
		{name:'QUANTITY'}, 
		{name:'PRICE'}, 
		{name:'TOTAL'}, 
		{name:'CURRENCY'}, 
		{name:'WRITE_NO'}, 
		{name:'GET_SHEET_TIME'}, 
		{name:'CREATOR_TIME'}, 
		{name:'EXPORT_DATE'}, 
		{name:'EXCHANGE_TYPE'}, 
		{name:'TRADE_TYPE'}, 
		{name:'EXPORT_PORT'}, 
		{name:'DESTINATIONS'}, 
		{name:'CMD'}
	]);

	shipNotifyInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipNotifyController.spr?action=queryNotifyInfo&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},shipNotifyInfoPlant)
    });
    
    var shipNotifyInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var shipNotifyInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		shipNotifyInfosm,
		   {header: '部门',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '立项号',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '立项名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {header: '物料名称',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_NAME'
           },
           {header: '物料单位',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_UNIT'
           },
           {header: '申请数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUANTITY'
           },
           {header: '单价',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE'
           },
           {header: '总价',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL'
           },
           {header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {header: '核销单号',
           width: 100,
           sortable: true,
           dataIndex: 'WRITE_NO'
           },
           {header: '领单时间',
           width: 100,
           sortable: true,
           dataIndex: 'GET_SHEET_TIME'
           },
           {header: '申报时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR_TIME'
           },
           {header: '出口时间',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_DATE'
           },
           {header: '收汇方式',
           width: 100,
           sortable: true,
           dataIndex: 'EXCHANGE_TYPE'
           },
           {header: '贸易方式',
           width: 100,
           sortable: true,
           dataIndex: 'TRADE_TYPE'
           },
           {header: '出口口岸',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_PORT'
           },
           {header: '目的地',
           width: 100,
           sortable: true,
           dataIndex: 'DESTINATIONS'
           },
           {header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'CMD'
           }
		　 
    ]);
    shipNotifyInfocm.defaultSortable = true;
    
    var shipNotifyInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:shipNotifyInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    shipNotifyInfogrid = new Ext.grid.EditorGridPanel({
    	id:'shipNotifyInfoGrid',
        ds: shipNotifyInfods,
        cm: shipNotifyInfocm,
        sm: shipNotifyInfosm,
        bbar: shipNotifyInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    shipNotifyInfods.load({params:{start:0, limit:10},arg:[]});
    
   shipNotifyInfogrid.addListener('rowclick', shipNotifyInfogridrowclick);

    function shipNotifyInfogridrowclick(grid, rowIndex, e){
    	shipNotifyRecord = grid.getStore().getAt(rowIndex);
    }
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strshipNotifyTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:151
		},{
			region:"center",
			layout:'fit',
			title: strshipNotifyTitle + "出口贸易明细表",
			items:[shipNotifyInfogrid]
		}]
	});
	
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
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sDateDiv',
		endDateField:'eDate'
   	});
   	
   	var eDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eDateDiv',
		startDateField:'sDate'
   	});

	var sGetSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sGetSheetTime',
		name:'sGetSheetTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sGetSheetTimeDiv',
		endDateField:'eGetSheetTime'
   	});
   	
   	var eGetSheetTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eGetSheetTime',
	    name:'eGetSheetTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eGetSheetTimeDiv',
		startDateField:'sGetSheetTime'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"
	isMutilSelect="true"></fiscxd:dept>