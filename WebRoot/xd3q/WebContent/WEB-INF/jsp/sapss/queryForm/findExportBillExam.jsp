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
<form action="" id="findExportBillExam" name="findExportBillExam">
<table width="100%">
<tr>
	<td align="center">出口货物通知单号</td>
	<td align="center">发票号</td>
	<td align="center">合同编码</td>
	<td align="center">核销单号</td>
	<td align="center">贸易方式</td> 
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="orderState" name="orderState" value="${orderState}"></input>
	<input type="text" id="exportApplyNo" name="exportApplyNo" value="" size="17"></input>
	</td>
	<td>
	<input type="text" id="invNo" name="invNo" value="" size="17"></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value="" size="18"></input>
	</td>		
	<td>
	<input type="text" id="writeNo" name="writeNo" value="" size="18"></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
    <td>
	<input type="button" value="查找" onclick="findPurchaseInfo()"></input>
	</td>
	<td>
	<!-- <input type="reset" value="清空"></input> -->
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
	var param = Form.serialize('findExportBillExam');
	var requestUrl  = 'exportQueryController.spr?action=queryExportBillExam&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim()

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
            		{name:'EXPORT_BILL_EXAM_ID'},
            		{name:'EXPORT_APPLY_ID'},
            		{name:'EXPORT_APPLY_NO'},
					{name:'INV_NO'},
					{name:'BILL_TYPE'},
            		{name:'DEPT_ID'},
					{name:'BILL_DATE'},
					{name:'CONTRACT_NO'},
            		{name:'LCDPDA_NO'},
					{name:'TOTAL'},
				    {name:'CURRENCY'},
					{name:'WRITE_NO'},
					{name:'EXAM_RECORD'},
					{name:'EXAM_DATE'},
					{name:'BANK'},
					{name:'IS_NEGOTIAT'},
					{name:'BILL_SHIP_DATE'},
					{name:'SHOULD_INCOME_DATE'},
					{name:'MARK'}
          		])
    });
    
    var purchasesm = new Ext.grid.CheckboxSelectionModel();
    
    var purchasecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		purchasesm,
		   {header: '出口出单审单ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_BILL_EXAM_ID',
           hidden:true
           },
		　 {header: '出口货物通知ID',
           width: 80,
           sortable: false,
           dataIndex: 'EXPORT_APPLY_ID',
           hidden:true
           },
           {header: '出口货物通知单号',
           width: 120,
           sortable: false,
           dataIndex: 'EXPORT_APPLY_NO'
           },
		　 {header: '发票号',
           width: 80,
           sortable: false,
           dataIndex: 'INV_NO'
           },
           {header: '单据类型',
           width: 150,
           sortable: false,
           dataIndex: 'BILL_TYPE'
           },
		　 {header: '合同编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '接单日期',
           width: 120,
           sortable: false,
           dataIndex: 'BILL_DATE'
           },
		　 {header: 'L/C,D/P,DA NO.',
           width: 100,
           sortable: false,
           dataIndex: 'LCDPDA_NO'
           },
           {header: '金额',
           width: 100,
           sortable: false,
           dataIndex: 'TOTAL'
           },
           {header: '币别',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'CURRENCY'
           },
		　 {header: '核销单号',
           width: 100,
           sortable: false,
           dataIndex: 'WRITE_NO'
           },
		　 {header: '出单日期',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'EXAM_DATE'
           },
		　 {header: '预付银行',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'BANK'
           },
		　 {header: '是否押汇',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'IS_NEGOTIAT'
           },
		　 {header: '提单装船日',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'BILL_SHIP_DATE'
           },
		　 {header: '应收汇日',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'SHOULD_INCOME_DATE'
           },
		　 {header: '备注',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'MARK'
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
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="153"></fiscxd:dictionary>