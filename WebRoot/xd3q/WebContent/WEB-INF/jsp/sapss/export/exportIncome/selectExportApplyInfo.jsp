<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="queryDiv">
<form action="" id="findForm" name="findForm">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
    <td align="center">核销单号</td>
	<td align="center">立项号</td>
	<td align="center">销售合同号</td>
	<td align="center">采购合同号</td>
	<td align="center">SAP订单号</td>
	<td align="center">业务类型</td>
	<td></td>
	<td></td>
</tr>
<tr>
    <td>
		<input type="text" id="writeNo" name="writeNo" value="" size="12"></input>
	</td>
	<td>
		<input type="text" id="projectNo" name="projectNo" value="" size="12"></input>
	</td>
	<td>
		<input type="text" id="salesNo" name="salesNo" value="" size="12"></input>
	</td>
	<td>
		<input type="text" id="purchaseNo" name="purchaseNo" value="" size="12"></input>
	</td>		
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value="" size="12"></input>
	</td>
	<td>
		<div id="div_tradeType"></div>
	</td>
	<td>
		<input type="button" value="查找" onclick="find()"></input>
	</td>
	<td>
		<input type="button" id ="btnClear" value="清空" onclick="reclear();"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_Sales"></div>
<div id="gridDiv"></div>
</body>
</html>
<script type="text/javascript">
var gridPanel;
var ds;
var sm;


function reclear(){
	$('findForm').reset();
}

function find(){
	var param = Form.serialize('findForm');
	var requestUrl  = 'exportIncomeController.spr?action=queryExportApply&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim();
	ds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	ds.load({params:{start:0, limit:10}});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var form=new Ext.form.FormPanel({
		frame:true,
		renderTo:document.body,
		baseCls:'x-plain',
		height:450,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'queryDiv'
			},{
			contentEl:'gridDiv'
		}],
		buttons:[{
			text:'确定',
			handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
					}else{  
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
			}
		},{
			text:'关闭',
			handler:function(){
				top.ExtModalWindowUtil.close();
			}
		}]
	});
	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'EXPORT_APPLY_ID'},
            		{name:'TRADE_TYPE'},
            		{name:'PROJECT_NO'},
					{name:'PROJECT_NAME'},
					{name:'CONTRACT_GROUP_NO'},
            		{name:'SALES_NO'},
					{name:'PURCHASE_NO'},
					{name:'SAP_ORDER_NO'},
					{name:'WRITE_NO'},
            		{name:'NOTICE_NO'},
            		{name:'TOTAL_QUANTITY'},
            		{name:'TOTAL_MONEY'},
            		{name:'EXPORT_PORT'},
            		{name:'DESTINATIONS'},
            		{name:'CONTRACT_PAPER_NO'}
          		])
    });
    
    sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		   {header: '通知单ID',
           width: 100,
           sortable: true,
           hidden:true,
           dataIndex: 'EXPORT_APPLY_ID'
           },
		   {header: '业务类型',
           width: 40,
           sortable: true,
           dataIndex: 'TRADE_TYPE'
           },
           {header: '核销单号',
           width: 80,
           sortable: true,
           dataIndex: 'WRITE_NO'
           },
		　 {header: '项目编码',
           width: 80,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 150,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
		　 {header: '合同组编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
           {header: '销售合同号',
           width: 80,
           sortable: false,
           dataIndex: 'SALES_NO'
           },
		　 {header: '采购合同号',
           width: 80,
           sortable: false,
           dataIndex: 'PURCHASE_NO'
           },
           {header: 'SAP订单号',
           width: 80,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
		　 {header: '通知号',
           width: 100,
           sortable: false,
           dataIndex: 'NOTICE_NO'
           }                     
    ]);
    cm.defaultSortable = true;
    
    var pagingBar = new Ext.PagingToolbar({
        pageSize:10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   gridPanel = new Ext.grid.GridPanel({
        id:'exportApplygrid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: pagingBar,
        height:280,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        layout:'fit',
        applyTo:'gridDiv'
    });
   gridPanel.on('rowdblclick',function(grid,rowIndex){
  		if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
			if (records.length > 1){
				top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
			}else{
				top.ExtModalWindowUtil.setReturnValue(records[0].data);
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
		}
    });
   
});
</script>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" width="153"></fiscxd:dictionary>
