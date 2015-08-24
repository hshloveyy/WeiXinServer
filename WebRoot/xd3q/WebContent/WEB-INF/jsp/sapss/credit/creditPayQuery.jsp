<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证付款查询</title>
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
<form action="" id="findCreditInfoFrom" name="findCreditInfoFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">开证时间从：</td>
		<td>
		   <table><tr><td><div id="sDateDiv"></div></td><td>-</td><td><div id="eDateDiv"></div></td></tr></table>
		
		</td>
		<td align="right">项目编号：</td>
		<td>
		   <input type="text" name="projectNo" id="projectNo" size="15">
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
	    
		<td align="right">信用证号：</td>
		<td>
		    <input type="text" name="creditNo" id="creditNo" size="15">
		</td>
		<td align="right">开证行：</td>
		<td>
		   <input type="text" name="createBank" id="createBank" size="15">		
		</td>
		<td align="right">付款日：</td>
		<td>
		   <table><tr><td><div id="sPayDateDiv"></div></td><td>-</td><td><div id="ePayDateDiv"></div></td></tr></table>
		</td>
		<td align="">
		   <input type="button" value="查找" onclick="findCreditInfo()"><input type="reset" value="清空">&nbsp;&nbsp;&nbsp;&nbsp;
		   <a onbeforeactivate="onbeforClickA()" href="creditHisQueryController.spr?action=dealOutToExcelPay" id="aaa" target="_self">下载</a>
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_center"></div>
<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
var creditInfoRecord;
var creditInfogrid;		//信用证信息列表
var creditInfods;

//creditInfo 查找按钮的单击事件
function findCreditInfo(){
	var param = Form.serialize('findCreditInfoFrom');
	var requestUrl = 'creditHisQueryController.spr?action=queryCreditInfoPay&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	//alert(requestUrl);	
	creditInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	creditInfods.load({params:{start:0, limit:10},arg:[]});
}

function downloadCreditHisInfo(){
	top.Ext.Msg.show({
		title:'提示',
		msg: '是否确定下载记录',
		buttons: {yes:'是', no:'否'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
				var param = Form.serialize('findCreditInfoFrom');
		        var requestUrl = 'creditHisQueryController.spr?action=dealOutToExcelPay&' + param;
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
	var param = Form.serialize('findCreditInfoFrom');
	var requestUrl = 'creditHisQueryController.spr?action=dealOutToExcelPay&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var creditInfoPlant = Ext.data.Record.create([
		{name:'DEPT_NAME'}, 
		{name:'CREATE_DATE'}, 
		{name:'CREATE_BANK'}, 
		{name:'CREDIT_NO'}, 
		{name:'PROJECT_NO'}, 
		{name:'AMOUNT'}, 
		{name:'CURRENCY'}, 
		{name:'ESTITLE'}, 
		{name:'PICK_LIST_NO'}, 
		{name:'ACCEPTANCE_DATE'}, 
		{name:'PAY_DATE'}, 
		{name:'PAYMENTNO'}, 
		{name:'PTTITLE'},
		{name:'BSTITLE'},
		{name:'PPAYDATE'},
		{name:'PCURRENCY'},
		{name:'DOCUMENTARYDATE'},
		{name:'APPLYAMOUNT'},
		{name:'COLLECTBANKID'},
		{name:'DOCUMENTARYLIMIT'},
		{name:'DOCTARYINTEREST'},
		{name:'DOCUMENTARYRATE'}
	]);

	creditInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditHisQueryController.spr?action=queryCreditInfoPay'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},creditInfoPlant)
    });
    
    var creditInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var creditInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		creditInfosm,
		   {header: '部门',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '开证日期',
           width: 100,
           sortable: true,
           dataIndex: 'CREATE_DATE'
           },
           {header: '开证行',
           width: 100,
           sortable: true,
           dataIndex: 'CREATE_BANK'
           },
           {header: '信用证号',
           width: 100,
           sortable: true,
           dataIndex: 'CREDIT_NO'
           },
           {header: '立项号',
           width: 120,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '开证金额',
           width: 100,
           sortable: true,
           dataIndex: 'AMOUNT'
           },
           {header: '开证币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {header: '开证状态',
           width: 130,
           sortable: true,
           dataIndex: 'ESTITLE'
           },
           {header: '到单号',
           width: 100,
           sortable: true,
           dataIndex: 'PICK_LIST_NO'
           },
           {header: '承兑日',
           width: 100,
           sortable: true,
           dataIndex: 'ACCEPTANCE_DATE'
           },
           {header: '付款日',
           width: 100,
           sortable: true,
           dataIndex: 'PAY_DATE'
           },
           {header: '付款单号',
           width: 100,
           sortable: true,
           dataIndex: 'PAYMENTNO'
           },
           {header: '付款类型',
           width: 100,
           sortable: true,
           dataIndex: 'PTTITLE'
           },
           {header: '付款状态',
           width: 100,
           sortable: true,
           dataIndex: 'BSTITLE'
           },
           {header: '银行到期付款日',
           width: 100,
           sortable: true,
           dataIndex: 'PPAYDATE'
           },
           {header: '付款币别',
           width: 100,
           sortable: true,
           dataIndex: 'PCURRENCY'
           },
           {header: '押汇日期',
           width: 100,
           sortable: true,
           dataIndex: 'DOCUMENTARYDATE'
           },
           {header: '押汇/付款金额',
           width: 100,
           sortable: true,
           dataIndex: 'APPLYAMOUNT'
           },           
           {header: '押汇/付款银行',
           width: 100,
           sortable: true,
           dataIndex: 'COLLECTBANKID'
           },           
           {header: '押汇期限',
           width: 100,
           sortable: true,
           dataIndex: 'DOCUMENTARYLIMIT'
           },
           {header: '押汇利率',
           width: 140,
           sortable: true,
           dataIndex: 'DOCTARYINTEREST'
           },
           {header: '押汇汇率',
           width: 160,
           sortable: true,
           dataIndex: 'DOCUMENTARYRATE'
           }		　 
    ]);
    creditInfocm.defaultSortable = true;
    
    var creditInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:creditInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    creditInfogrid = new Ext.grid.EditorGridPanel({
    	id:'creditInfoGrid',
        ds: creditInfods,
        cm: creditInfocm,
        sm: creditInfosm,
        bbar: creditInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    creditInfods.load({params:{start:0, limit:10},arg:[]});
    
   creditInfogrid.addListener('rowclick', creditInfogridrowclick);

    function creditInfogridrowclick(grid, rowIndex, e){
    	creditInfoRecord = grid.getStore().getAt(rowIndex);
    }
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"信用证付款查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:100
		},{
			region:"center",
			layout:'fit',
			title:"信用证付款明细表",
			collapsible: true,
			items:[creditInfogrid]
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
		width: 88,
	    readOnly:true,
		//vtype: 'daterange',
		renderTo:'sDateDiv',
		endDateField:'applyTime'
   	});
   	
   	var applyTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime1',
	    name:'applyTime1',
		width: 88,
	    readOnly:true,
		//vtype: 'daterange',
		renderTo:'eDateDiv',
		startDateField:'applyTime1'
   	});
   	
   var payTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'payTime',
		name:'payTime',
		width: 88,
	    readOnly:true,
		//vtype: 'daterange',
		renderTo:'sPayDateDiv',
		endDateField:'payTime'
   	});
   	
   	var payTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'payTime1',
	    name:'payTime1',
		width: 88,
	    readOnly:true,
		//vtype: 'daterange',
		renderTo:'ePayDateDiv',
		startDateField:'payTime1'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>