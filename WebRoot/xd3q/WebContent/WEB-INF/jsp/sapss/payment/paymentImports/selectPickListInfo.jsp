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
<form action="" id="findForm" name="findForm">
<table width="100%">
<tr>
	<td align="center">到单号/发票号</td>
	<td align="center">到单日</td>
	<td align="center">合同编码</td>
	<td align="center">合同名称</td>
	<td align="center">信用证号</td>
	<td align="center">金额</td>
	<td align="center"></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="examineState" name="examineState" value="${examineState}"></input>
	<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>
	<input type="text" id="pickListNo" name="pickListNo" value="" size="12"></input>
	</td>
	<td>
	<input type="text" id="pickListrecDate" name="pickListrecDate" value="" size="12"></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value="" size="12"></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size="12"></input>
	</td>
	<td>
	<input type="text" id="lcNo" name="lcNo" value="" size="12"></input>
	</td>
	<td>
	<input type="text" id="totalValue" name="totalValue" value="" size="8"></input>-<input type="text" id="totalValue1" name="totalValue1" value="" size="8"></input>
	</td>
	<td>
	<input type="button" value="查找" onclick="find()"></input>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_pickListInfo"></div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;


function find(){
	var param = Form.serialize('findForm');
	var requestUrl  = 'paymentImportsInfoController.spr?action=queryPickListInfo&' + param;
	ds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	ds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'PICK_LIST_ID'},
            		{name:'PICK_LIST_NO'},
					{name:'CONTRACT_PURCHASE_ID'},
					{name:'CONTRACT_NO'},
            		{name:'CONTRACT_NAME'},
            		{name:'EKKO_WAERS'},
            		{name:'WRITE_LIST_NO1'},
            		{name:'PAY_DATE'},
            		{name:'PAY_VALUE'},
            		{name:'TOTALVALUE'},
            		{name:'CURRENCY_ID'},
            		{name:'LC_NO'},
            		{name:'PROVIDER'}
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
           },
		　 {header: '提货单ID',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'PICK_LIST_ID'
           },
		　 {header: '提货单',
           width: 100,
           sortable: false,
           dataIndex: 'PICK_LIST_NO'
           },           
		　 {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: '合同详情',
           	width: 80,
           	sortable: false,
           	dataIndex:'detail',
           	renderer:contractOper
           },
           {header: '核销单号',
           width: 80,
           sortable: false,
           dataIndex: 'WRITE_LIST_NO1'
           },
           {header: '付款日',
           width: 80,
           sortable: false,
           dataIndex: 'PAY_DATE'
           },
           {header: '信用证号',
           width: 80,
           sortable: false,
           dataIndex: 'LC_NO'
           },
           {header: '金额',
           width: 80,
           sortable: false,
           dataIndex: 'TOTALVALUE'
           }
    ]);
    cm.defaultSortable = true;
    function contractOper(value, p, record){
    	return String.format('<a href="#" onclick="openContractWin()">合同详情</a>',value);
    }
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_pickListInfo',
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
			contentEl: 'div_pickListInfo',
			buttonAlign:'center',
			items:[grid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (grid.selModel.hasSelection()){
						var records = grid.selModel.getSelections();
						
						if (records.length > 1){
							top.ExtModalWindowUtil.alert('提示','只能选择一个项目信息！');
						}else{
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}
					}
					else{
						top.ExtModalWindowUtil.alert('提示','请选择指定的项目信息！');
					}
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
function openContractWin(){
	var records = grid.selModel.getSelections();
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	
	top.ExtModalWindowUtil.show(contractName+'-'+contractNo,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
			'',
			'',
			{width:800,height:550});
}
</script>
