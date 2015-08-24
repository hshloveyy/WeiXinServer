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
<form action="" id="findPurchaseForm" name="findPurchaseForm">
<table width="100%">
<tr>
	<td align="center">物料编码</td>
	<td align="center">物料描述</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td align="center">
		<input type="hidden" id="contractPurchaseId" name="contractPurchaseId" value="${contractPurchaseId}"></input>
		<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>
		<input type="text" id="materialCode" name="materialCode" value=""></input>
	</td>
	<td align="center">
	<input type="text" id="materialName" name="materialName" value=""></input>
	</td>		
	<td>
	<input type="button" value="查找" onclick="findPurchaseInfo()"></input>
	</td>
	<td>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_Purchase"></div>
</body>
</html>
<script type="text/javascript">
var Purchaseds;



function findPurchaseInfo(){
	var param = Form.serialize('findPurchaseForm');
	var requestUrl  = 'receiptsController.spr?action=queryPurchaseMateriel&' + param;
	Purchaseds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	Purchaseds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	Purchaseds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'' }),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'PURCHASE_ROWS_ID'},            	
            		{name:'EKPO_MATNR'},
					{name:'EKPO_TXZ01'},
					{name:'EKPO_MEINS'},
            		{name:'EKPO_MENGE'},
					{name:'FLOW_NO'},
					{name:'TAXES'},
            		{name:'TOTAL_TAXES'}
          		])
    });
    
    var Purchasesm = new Ext.grid.CheckboxSelectionModel();
    
    var Purchasecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		Purchasesm,
		　 {header: '行项目ID',
           width: 100,
           sortable: false,
           dataIndex: 'PURCHASE_ROWS_ID',
           hidden: true
           },		
		　 {header: '物料编码',
           width: 100,
           sortable: false,
           dataIndex: 'EKPO_MATNR'
           },
           {header: '物料描述',
           width: 150,
           sortable: false,
           dataIndex: 'EKPO_TXZ01'
           },
		　 {header: '单位',
           width: 100,
           sortable: false,
           dataIndex: 'EKPO_MEINS'
           },
           {header: '数量',
           width: 100,
           sortable: false,
           dataIndex: 'EKPO_MENGE'
           },
		　 {header: '批次号',
           width: 100,
           sortable: false,
           dataIndex: 'FLOW_NO'
           },
           {header: '销项税金',
           width: 100,
           sortable: false,
           dataIndex: 'TAXES'
           },
		　 {header: '总额',
           width: 100,
           sortable: false,
           dataIndex: 'TOTAL_TAXES'
           }                     
    ]);
    Purchasecm.defaultSortable = true;
    
    var Purchasebbar = new Ext.PagingToolbar({
        pageSize: 200,
        store:Purchaseds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var Purchasegrid = new Ext.grid.GridPanel({
    	id:'Purchasegrid',
        ds: Purchaseds,
        cm: Purchasecm,
        sm: Purchasesm,
        bbar: Purchasebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_Purchase',
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
			contentEl: 'div_Purchase',
			buttonAlign:'center',
			items:[Purchasegrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (Purchasegrid.selModel.hasSelection()){
						var records = Purchasegrid.selModel.getSelections();
						
						if (records.length > 1){
							top.ExtModalWindowUtil.alert('提示','只能选择一个物料信息！');
						}else{
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}
					}
					else{
						top.ExtModalWindowUtil.alert('提示','请选择指定的物料信息！');
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
	
	
	Purchasegrid.on('rowdblclick',function(grid,rowIndex){
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