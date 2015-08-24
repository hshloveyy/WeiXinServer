<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证历史查询</title>
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
		<td align="right">开/改证时间从：</td>
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
	    <td align="right">采购合同号：</td>
		<td>
		   <input type="text" name="contractNo" id="contractNo" size="15">
		</td>
		<td align="right">信用证号：</td>
		<td>
		    <input type="text" name="creditNo" id="creditNo" size="15">
		</td>
		<td align="right">开证行：</td>
		<td>
		   <input type="text" name="createBank" id="createBank" size="15">		
		</td>
		<td align="">
		   <input type="button" value="查找" onclick="findCreditInfo()"><input type="reset" value="清空">&nbsp;&nbsp;&nbsp;&nbsp;
		   <a onbeforeactivate="onbeforClickA()" href="creditHisQueryController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a>
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
	var requestUrl = 'creditHisQueryController.spr?action=queryCreditInfo&' + param;
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
		        var requestUrl = 'creditHisQueryController.spr?action=dealOutToExcel&' + param;
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
	var requestUrl = 'creditHisQueryController.spr?action=dealOutToExcel&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var creditInfoPlant = Ext.data.Record.create([
		{name:'DEPT_NAME'}, 
		{name:'BUSINESS_TYPE'}, 
		{name:'PROJECT_NO'}, 
		{name:'CONTRACT_NO'}, 
		{name:'EKKO_UNSEZ'}, 
		{name:'MATERIAL_GROUP'}, 
		{name:'BENEFIT'}, 
		{name:'CUSTOMER_LINK_MAN'}, 
		{name:'CREDIT_INFO'}, 
		{name:'CREATE_DATE'}, 
		{name:'APPROVED_TIME'}, 
		{name:'CREATE_BANK'}, 
		{name:'CREDIT_NO'},
		{name:'AVAIL_DATE'},
		{name:'CURRENCY'},
		{name:'AMOUNT'},
		{name:'USDAMOUNT'},
		{name:'LOADING_PERIOD'},
		{name:'VALID_DATE'},
		{name:'CREDIT_STATE'},
		{name:'PICK_LIST_TOTAL'},
		{name:'AMOUNT_PICKTOTAL'},
		{name:'USDAMOUNT_PICKTOTAL'},
		{name:'AMOUNT_PAYMENTTOTAL'},
		{name:'USDAMOUNT_PAYMENTTOTAL'}
	]);

	creditInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditHisQueryController.spr?action=queryCreditInfo'}),
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
           {header: '业务类型',
           width: 100,
           sortable: true,
           dataIndex: 'BUSINESS_TYPE'
           },
           {header: '项目编号',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '采购合同号',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '外部纸质合同号',
           width: 120,
           sortable: true,
           dataIndex: 'EKKO_UNSEZ'
           },
           {header: '物料组名称',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIAL_GROUP'
           },
           {header: '受益人',
           width: 100,
           sortable: true,
           dataIndex: 'BENEFIT'
           },
           {header: '国内委托方',
           width: 130,
           sortable: true,
           dataIndex: 'CUSTOMER_LINK_MAN'
           },
           {header: '保证金',
           width: 100,
           sortable: true,
           dataIndex: 'CREDIT_INFO'
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
           {header: '信用证期限',
           width: 100,
           sortable: true,
           dataIndex: 'AVAIL_DATE'
           },
           {header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {header: '信用证金额',
           width: 100,
           sortable: true,
           dataIndex: 'AMOUNT'
           },
           {header: '信用证美金金额',
           width: 100,
           sortable: true,
           dataIndex: 'USDAMOUNT'
           },
           {header: '装期',
           width: 100,
           sortable: true,
           dataIndex: 'LOADING_PERIOD'
           },
           {header: '效期',
           width: 100,
           sortable: true,
           dataIndex: 'VALID_DATE'
           },           
           {header: '信用证状态',
           width: 100,
           sortable: true,
           dataIndex: 'CREDIT_STATE'
           },           
           {header: '到单金额',
           width: 100,
           sortable: true,
           dataIndex: 'PICK_LIST_TOTAL'
           },
           {header: '信用证扣去到单余额',
           width: 140,
           sortable: true,
           dataIndex: 'AMOUNT_PICKTOTAL'
           },
           {header: '信用证扣去到单美金余额',
           width: 160,
           sortable: true,
           dataIndex: 'USDAMOUNT_PICKTOTAL'
           },
           {header: '信用证扣去实际付款余额',
           width: 160,
           sortable: true,
           dataIndex: 'AMOUNT_PAYMENTTOTAL'
           },
           {header: '信用证扣去实际付款美金余额',
           width: 180,
           sortable: true,
           dataIndex: 'USDAMOUNT_PAYMENTTOTAL'
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
			title:"信用证历史条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:100
		},{
			region:"center",
			layout:'fit',
			title:"信用证历史明细表",
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
		vtype: 'daterange',
		renderTo:'sDateDiv',
		endDateField:'applyTime'
   	});
   	
   	var applyTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime1',
	    name:'applyTime1',
		width: 88,
	    readOnly:true,
		vtype: 'daterange',
		renderTo:'eDateDiv',
		startDateField:'applyTime1'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"
	isMutilSelect="true"></fiscxd:dept>