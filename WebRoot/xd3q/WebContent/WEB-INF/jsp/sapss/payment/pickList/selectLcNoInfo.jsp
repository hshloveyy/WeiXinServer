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
	<td align="center">信用证号</td>
	<td align="center">开证行</td>
	<td align="center">合同编码</td>
	<td align="center">合同名称</td>
	<td align="center">贸易方式</td> 
	<td align="center">部门</td> 
	
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="text" id="creditNo" name="creditNo" value="" size="13"></input>
	</td>
	<td>
	<input type="text" id="creditBank" name="creditBank" size="13"></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" size="13"></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" size="13"></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td>
	<div id="dept"></div>
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

<div id="div_purchase"></div>
</body>
</html>
<script type="text/javascript">
var purchaseds;



function findPurchaseInfo(){
	var param = Form.serialize('findPurchaseForm');
	var requestUrl  = 'pickListInfoController.spr?action=queryLcNoInfo&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim();
	requestUrl = requestUrl +'&department='+selectId_dept;
	purchaseds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	purchaseds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	purchaseds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'CONTRACT_PURCHASE_ID'},
					{name:'CREDIT_NO'},
					{name:'GOODS'},
					{name:'CREATE_BANK'},					
					{name:'CREATE_DATE'},					
					{name:'CONTRACT_NO'},
					{name:'SAP_ORDER_NO'},
            		{name:'CONTRACT_NAME'},
            		{name:'EKKO_WAERS'},
            		{name:'WRITE_OFF_SINGLE_NO'},
            		{name:'EKKO_LIFNR_NAME'},
            		{name:'AVAIL_DATE'},
					{name:'YMAT_GROUP'}
          		])
    });
    
    var purchasesm = new Ext.grid.CheckboxSelectionModel();
    
    var purchasecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		purchasesm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
           },
		　 {header: '信用证号',
           width: 100,
           sortable: false,
           dataIndex: 'CREDIT_NO'
           },
		　 {header: '开证行',
           width: 100,
           sortable: false,
           dataIndex: 'CREATE_BANK'
           },           
		　 {header: '开证日期',
           width: 100,
           sortable: false,
           hidden:true,
           dataIndex: 'CREATE_DATE'
           },           
		　 {header: '合同编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: 'SAP订单号',
           width: 150,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
           {header: '币种',
           width: 150,
           sortable: false,
           hidden: true,
           dataIndex: 'EKKO_WAERS'
           },
           {header: '核销单号',
           width: 150,
           sortable: false,
           hidden: true,
           dataIndex: 'WRITE_OFF_SINGLE_NO'
           },
           {header: '物料组编号',
           width: 100,
           sortable: false,
           hidden : true,
           dataIndex: 'YMAT_GROUP'
           }            
    ]);
    purchasecm.defaultSortable = true;
    
    var purchasebbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:purchaseds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var purchasegrid = new Ext.grid.GridPanel({
    	id:'purchasegrid',
        ds: purchaseds,
        cm: purchasecm,
        sm: purchasesm,
        bbar: purchasebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_purchase',
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
			contentEl: 'div_purchase',
			buttonAlign:'center',
			items:[purchasegrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (purchasegrid.selModel.hasSelection()){
						var records = purchasegrid.selModel.getSelections();
						
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
	
	
	purchasegrid.on('rowdblclick',function(grid,rowIndex){
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
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="130"></fiscxd:dictionary>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="160" isUseDiv="true" isMutilSelect="true"></fiscxd:dept>