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
<div id="div_north" style="width:100%;height:100%">
<form action="" id="findsalesForm" name="findSalesForm">
<table width="100%">
<tr>
	<td align="center">合同组编码</td>
	<td align="center">合同组名称</td>
	<td align="center">合同编码</td>
	<td align="center">合同名称</td>
	<td align="center">贸易方式</td> 
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="customer" name="customer" value="${customer}"></input>
	<input type="hidden" id="orderState" name="orderState" value="${orderState}"></input>
	<input type="text" id="contractGroupNo" name="contractGroupNo" value=""></input>
	</td>
	<td>
	<input type="text" id="contractGroupName" name="contractGroupName" value=""></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value=""></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td>
	<input type="button" value="查找" onclick="findSalesInfo()"></input>
	</td>
	<td>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_sales"></div>
</body>
</html>
<script type="text/javascript">
var salesds;

function findSalesInfo(){
	var param = Form.serialize('findSalesForm');
	var requestUrl  = 'contractQueryController.spr?action=querySalesInfo&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim()

	salesds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	salesds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	salesds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'PROJECT_NO'},
					{name:'PROJECT_NAME'},
					{name:'CONTRACT_GROUP_NO'},
            		{name:'CONTRACT_GROUP_NAME'},
					{name:'CONTRACT_SALES_ID'},
					{name:'CONTRACT_NO'},
            		{name:'CONTRACT_NAME'},
					{name:'SAP_ORDER_NO'},
					{name:'VBKD_IHREZ'},//vbkdIhrez
					{name:'PAYER'},
					{name:'PAYER_NAME'},
					{name:'KUAGV_KUNNR'},
					{name:'KUAGV_KUNNR_NAME'},
					{name:'KUWEV_KUNNR'},		
					{name:'KUWEV_KUNNR_NAME'},	
					{name:'BILL_TO_PARTY'},		
					{name:'BILL_TO_PARTY_NAME'},	
					{name:'VBKD_INCO1'},
					{name:'VBKD_INCO2'}
          		])
    });
    
    var salessm = new Ext.grid.CheckboxSelectionModel();
    
    var salescm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		salessm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_SALES_ID',
           hidden:true
           },
		　 {header: '合同编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
		　 {header: '项目编码',
           width: 80,
           sortable: false,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 150,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
           {header: '付款方',
           width: 100,
           sortable: false,
           dataIndex: 'PAYER'
           },
           {header: '付款方名称',
           width: 100,
           sortable: false,
           dataIndex: 'PAYER_NAME'
           },            
		　 {header: '合同组编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
           {header: '合同组名称',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
		　 {header: 'SAP订单号',
           width: 80,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
           {header: '纸质合同号',
           width: 80,
           sortable: false,
           dataIndex: 'VBKD_IHREZ'
           },
           {header: '购货单位',
           width: 100,
           sortable: false,
           dataIndex: 'KUWEV_KUNNR_NAME',
           hidden:true
           },
		　 {header: '国际贸易条款',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'VBKD_INCO1'
           }, 
		　 {header: '国际贸易条款2',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'VBKD_INCO2'
           },
		   {header: '售达方',
           width: 100,
           sortable: false,
           dataIndex: 'KUAGV_KUNNR'
           },
           {header: '售达方名称',
           width: 100,
           sortable: false,
           dataIndex: 'KUAGV_KUNNR_NAME'
           },
      	   {header: '送达方',
           width: 100,
           sortable: false,
           dataIndex: 'KUWEV_KUNNR'
           },
           {header: '送达方名称',
           width: 100,
           sortable: false,
           dataIndex: 'KUWEV_KUNNR_NAME'
           },                                             
      	   {header: '收票方',
           width: 100,
           sortable: false,
           dataIndex: 'BILL_TO_PARTY'
           },
           {header: '收票方名称',
           width: 100,
           sortable: false,
           dataIndex: 'BILL_TO_PARTY_NAME'
           }           
    ]);
    salescm.defaultSortable = true;
    
    var salesbbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:salesds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var salesgrid = new Ext.grid.GridPanel({
    	id:'salesgrid',
        ds: salesds,
        cm: salescm,
        sm: salessm,
        bbar: salesbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_sales',
        autowidth:true,
        layout:'fit'
    });
   		
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:40,
			contentEl:'div_north'
		},{
			region:"center",
			layout:'border',
			contentEl: 'div_sales',
			buttonAlign:'center',
			items:[salesgrid],
			buttons:[{
				text:'确定',
				handler:function(){
					checkSelect(salesgrid,0);
				}
			},{
				text:'关闭',
				handler:function(){
					top.ExtModalWindowUtil.close();
				}
			}]
		}]
	});

	salesgrid.on('rowdblclick',function(grid,rowIndex){
  		checkSelect(grid,rowIndex);
    });    
    
    function checkSelect(grid,rowIndex)
    {
    	if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
	
			if (records.length < 1){
				top.ExtModalWindowUtil.alert('提示','请至少选择一张销售合同！');
			}else{
				
				var i, num=records.length;
				var payer = records[0].data.PAYER;
				for (i=1; i<num ;i++)
				{
					if(payer != records[i].data.PAYER)
					{
						top.ExtModalWindowUtil.alert('提示','只能选择同一家客户的销售合同！');
						return;
					} 
				}
				
				top.ExtModalWindowUtil.setReturnValue(records);
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
		}
		else{
			top.ExtModalWindowUtil.alert('提示','请选择指定的项目信息！');
		}
	}
});
</script>
<fiscxd:dictionary divId="div_tradeType" disable="true" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="153"></fiscxd:dictionary>