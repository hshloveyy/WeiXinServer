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
<form action="" id="findShipInfoForm" name="findShipInfoForm">
<table width="100%">
<tr>
	<td align="center">出仓单号</td>
	<td align="center">立项号</td>
	<td align="center">立项名称</td>
	<td align="center">合同号</td>
	<td align="center">合同名称</td>
	<td align="center">开票方名称</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="customer" name="customer" value="${customer}"></input>
	<input type="hidden" id="examineState" name="examineState" value="${examineState}"></input>
	<input type="hidden" id="contractSalesId" name="contractSalesId" value="${contractSalesId}"></input>
	<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>		
	<input type="text" id="shipNo" name="shipNo" value="" size=10></input>
	</td>
	<td>
	<input type="text" id="projectNo" name="projectNo" value="" size=10></input>
	</td>
	<td>
	<input type="text" id="projectName" name="projectName" value="" size=12></input>
	</td>	
	<td>	
	<input type="text" id="contractNo" name="contractNo" value="${contractNo}" size=12></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size=12></input>
	</td>
	<td>
	<input type="text" id="unitName" name="unitName" value="" size=12></input>
	</td>
	<td>
	<input type="button" value="查找" onclick="findShipInfo()"></input>
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
var ds;

function findShipInfo(){
	var param = Form.serialize('findShipInfoForm');
	var requestUrl  = 'shipQueryController.spr?action=queryShipInfo&' + param;

	ds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	ds.load({params:{start:0, limit:60},arg:[]});
}

function rowDblClick(){
	var param = Form.serialize('findShipInfoForm');
	var requestUrl  = 'shipQueryController.spr?action=queryShipInfo&' + param;

	ds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	ds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	if ('${contractSalesId}'!='')
		document.getElementById('contractNo').disabled = true;
	
	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
					{name:'SHIP_ID'}, 
					{name:'CONTRACT_SALES_ID'},
					{name:'VBKD_IHREZ'}, 
					{name:'PAYER'},
					{name:'PAYER_NAME'},
					{name:'KUAGV_KUNNR'},
					{name:'KUAGV_KUNNR_NAME'},
					{name:'KUWEV_KUNNR'},		
					{name:'KUWEV_KUNNR_NAME'},	
					{name:'BILL_TO_PARTY'},		
					{name:'BILL_TO_PARTY_NAME'},	
					{name:'BILL_STATE'},                     
					{name:'SHIP_NO'},
					{name:'QUANTITY'},
					{name:'PROJECT_NO'},                    
					{name:'PROJECT_NAME'},                   
				    {name:'CONTRACT_GROUP_NO'},               
					{name:'SALES_NO'},                       
					{name:'SAP_ORDER_NO'},
					{name:'CONTRACT_NAME'},
					{name:'SAP_RETURN_NO'},
					{name:'WAREHOUSE'},
					{name:'WAREHOUSE_ADD'}	
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		   {header: '出仓单ID',
           width: 100,
           sortable: true,
           dataIndex: 'SHIP_ID',
           hidden:true
           },
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_SALES_ID',
           hidden:true
           },  
		　 {header: '出仓单号',
           width: 80,
           sortable: false,
           dataIndex: 'SHIP_NO'
           },  
		　 {header: '总数量',
           width: 80,
           sortable: false,
           dataIndex: 'QUANTITY'
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
		　 {header: '项目号',
           width: 80,
           sortable: false,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 120,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },           
		　 {header: '合同编码',
           width: 100,
           sortable: false,
           dataIndex: 'SALES_NO'
           },
           {header: '合同名称',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           } ,
           {header: 'SAP订单号',
           width: 150,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
            {header: 'SAP交货号',
           width: 150,
           sortable: false,
           dataIndex: 'SAP_RETURN_NO'
           },
            {header: '仓库地址',
           width: 150,
           sortable: false,
           dataIndex: 'WAREHOUSE_ADD'
           },
           {header: '仓库地址',
               width: 150,
               sortable: false,
               dataIndex: 'WAREHOUSE',
               hidden:true
           },
           {header: '纸质合同号',
           width: 80,
           sortable: false,
           dataIndex: 'VBKD_IHREZ'
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
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 60,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: bbar,
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
			items:[grid],
			buttons:[{
				text:'确定',
				handler:function(){
					checkSelect(grid,0);
				}
			},{
				text:'关闭',
				handler:function(){
					top.ExtModalWindowUtil.close();
				}
			}]
		}]
	});
	
	grid.on('rowdblclick',function(grid,rowIndex){
  		checkSelect(grid,rowIndex);
    });
    
    function checkSelect(grid,rowIndex)
    {
    	if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
	
			if (records.length < 1){
				top.ExtModalWindowUtil.alert('提示','请至少选择一张出仓单！');
			}else{
				
				var i, num=records.length;
				var payer = records[0].data.PAYER;
				for (i=1; i<num ;i++)
				{
					if(payer != records[i].data.PAYER)
					{
						top.ExtModalWindowUtil.alert('提示','只能选择同一家客户的单据！');
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
