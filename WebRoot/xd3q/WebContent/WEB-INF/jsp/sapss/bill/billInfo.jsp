<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸贸易明细</title>
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
<form action="" id="findBillInfoFrom" name="findBillInfoFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">物料名称：</td>
		<td><input type="text" id="materialName" name="materialName" value=""></input>
		</td>
		<td align="right">申请数量区间：</td>
		<td><input type="text" id="quantity"
			name="quantity" value="" size="10"></input>-<input type="text" id="quantity1" size="10"
			name="quantity1" value=""></input></td>
	</tr>
	<tr>
		<td align="right">申请时间从：</td>
		<td>
		<div id="sDateDiv"></div>
		</td>
		<td align="right">到：</td>
		<td>
		<div id="eDateDiv"></div>
		</td>
		<td align="right">货款金额区间：</td>
		<td><input type="text" id="loanMoney"
			name="loanMoney" value="" size="10"></input>-<input type="text" id="loanMoney1" size="10"
			name="loanMoney1" value=""></input></td>
	</tr>
	<tr>
		<td align="right">开票时间：</td>
		<td><div id="sGetSheetTimeDiv"></div></td>
		<td align="right">到：</td>
		<td><div id="eGetSheetTimeDiv"></div></td>
		<td align="right">价税合计区间：</td>
		<td><input type="text" id="totalMoney"
			name="totalMoney" value="" size="10"></input>-<input type="text" id="totalMoney1" size="10"
			name="totalMoney1" value=""></input></td>
	</tr>
	
	<tr>
		<td colspan="6" align="center"><input type="button" value="查找" onclick="findBillInfo()"><input type="reset" value="清空">&nbsp;&nbsp;&nbsp;&nbsp;
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
function findBillInfo(){
	var param = Form.serialize('findBillInfoFrom');
	var requestUrl = 'billApplyController.spr?action=queryBillInfo&' + param;
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
				var param = Form.serialize('findBillInfoFrom');
		        var requestUrl = 'billApplyController.spr?action=dealOutToExcel&' + param;
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
	var param = Form.serialize('findBillInfoFrom');
	var requestUrl = 'billApplyController.spr?action=dealOutToExcel&' + param;
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
		{name:'LOAN_MONEY'}, 
		{name:'TAX'}, 
		{name:'TOTAL_MONEY'}, 
		{name:'APPLY_TIME'}, 
		{name:'APPROVED_TIME'}, 
		{name:'CMD'}
	]);

	shipNotifyInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'billApplyController.spr?action=queryBillInfo'}),
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
           {header: '货款金额',
           width: 100,
           sortable: true,
           dataIndex: 'LOAN_MONEY'
           },
           {header: '税款金额',
           width: 100,
           sortable: true,
           dataIndex: 'TAX'
           },
           {header: '价税总计',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_MONEY'
           },
           {header: '申请时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },
           {header: '开票时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
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
			title: strshipNotifyTitle + "内贸贸易明细表",
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
	
	var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime',
		name:'applyTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sDateDiv',
		endDateField:'applyTime'
   	});
   	
   	var applyTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime1',
	    name:'applyTime1',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eDateDiv',
		startDateField:'applyTime1'
   	});

	var approvedTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'approvedTime',
		name:'approvedTime',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'sGetSheetTimeDiv',
		endDateField:'approvedTime'
   	});
   	
   	var approvedTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'approvedTime1',
	    name:'approvedTime1',
		width: 160,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eGetSheetTimeDiv',
		startDateField:'approvedTime1'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"
	isMutilSelect="true"></fiscxd:dept>