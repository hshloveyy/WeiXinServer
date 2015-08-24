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
<form action="" id="findBillApplyForm" name="findExportApplyForm">
<table width="100%">
<tr>
	<td align="center">开票申请单号号</td>
	<td align="center">开票金额</td>
	<td align="center">开票凭证号</td>
	<td align="center">合同号</td>
	<td align="center">合同名称</td>
	<td align="center">纸质合同号</td>
	<td align="center">贸易方式</td> 
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="examineState" name="examineState" value="${examineState}"></input>
	<input type="text" id="billApplyNo" name="billApplyNo" value="" size=10></input>
	</td>
	<td>
	<input type="text" id="totalValue" name="totalValue" value="" size=7></input>-
	<input type="text" id="totalValue1" name="totalValue1" value="" size=7></input>
	</td>
	<td>
	<input type="text" id="sapBillNo" name="sapBillNo" value="" size=10></input>
	</td>	
	<td>	
	<input type="text" id="contractNo" name="contractNo" value="" size=10></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size=10></input>
	</td>
	<td>
	<input type="text" id="paperNo" name="paperNo" value="" size=10></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td>
	<input type="button" value="查找" onclick="findBillApply()"></input>
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
var BillApplyds;



function findBillApply(){
	var param = Form.serialize('findBillApplyForm');
	var requestUrl  = 'shipController.spr?action=queryBillApply&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim()

	BillApplyds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	BillApplyds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	BillApplyds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
					{name:'BILL_APPLY_ID'}, 
					{name:'BILL_APPLY_NO'},       
					{name:'SAP_BILL_NO'},               
					{name:'PRICE_TOTAL'},                          
					{name:'CONTRACT_SALES_NO'},     
					{name:'CONTRACT_NAME'},                       
					{name:'SAP_ORDER_NO'},
					{name:'VBKD_IHREZ'}               
          		])
    });
    
    var BillApplysm = new Ext.grid.CheckboxSelectionModel();
    
    var BillApplycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		BillApplysm,
		   {header: 'ID',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_APPLY_ID',
           hidden:true
           },
		   {header: '开票申请单号',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_APPLY_NO'
           },  
           {header: '开票凭证号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_BILL_NO'
           },
		　 {header: '开票金额',
           width: 80,
           sortable: false,
           dataIndex: 'PRICE_TOTAL'
           },                    
		　 {header: '销售合同号',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_SALES_NO'
           },
           {header: '销售合同名称',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
		　 {header: '销售订单号',
           width: 100,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },           
		　 {header: '纸制合同号',
           width: 100,
           sortable: false,
           dataIndex: 'VBKD_IHREZ'
           }                                
    ]);
    BillApplycm.defaultSortable = true;
    
    var BillApplybbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:BillApplyds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var BillApplygrid = new Ext.grid.GridPanel({
    	id:'BillApplygrid',
        ds: BillApplyds,
        cm: BillApplycm,
        sm: BillApplysm,
        bbar: BillApplybbar,
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
			items:[BillApplygrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (BillApplygrid.selModel.hasSelection()){
						var records = BillApplygrid.selModel.getSelections();
						
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
	
	
	BillApplygrid.on('rowdblclick',function(grid,rowIndex){
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
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="153"></fiscxd:dictionary>