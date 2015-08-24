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
.close{
	background-image:url(images/fam/forward.gif) !important;
}
</style>
</head>
<body>
<div id="div_bomInfo"></div>
</body>
</html>
<script type="text/javascript">
var PurchaseRowsId ='${purchaserowsid}';
var bomds;
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	bomds.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;
   	
   	var bomPlant = Ext.data.Record.create([
   		{name:'BOM_ID'},
   		{name:'PURCHASE_ROWS_ID'},
   		{name:'SAP_ROW_NO'},   		
   		{name:'MATERIEL'},
   		{name:'MATERIEL_NAME'},
   		{name:'BOM_MATERIEL_CMD'},
   		{name:'ENTRY_QUANTITY'},
   		{name:'ENTRY_UOM_D_UNIT'},
   		{name:'PLANT_D_FACTORY'},
   		{name:'CMD'} 
	]);
	
	bomds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=queryBomInfo&purchaserowsid='+PurchaseRowsId}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},bomPlant)
    });
    
    var bomsm = new Ext.grid.CheckboxSelectionModel();
    
    var bomcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		bomsm,
			{header: 'BOMID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'BOM_ID',
           	hidden:true
			},
			{header: '采购合同行项ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'PURCHASE_ROWS_ID',
           	hidden:true
			},
           {header: '物料号',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL'
           },
           {header: '物料描述',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL_NAME'
           },
           {header: '条目单位',
           width: 150,
           sortable: true,
           dataIndex: 'ENTRY_UOM'
           },
           {header: '物料数量',
           width: 100,
           sortable: true,
           dataIndex: 'ENTRY_QUANTITY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })
           },
           {header: '工厂',
           width: 100,
           sortable: true,
           dataIndex: 'PLANT'
           },           
           {header: '备注',
           width: 200,
           sortable: true,
           dataIndex: 'CMD',
           editor: new fm.TextField({
               allowBlank: true
           })
           }
    ]);
    bomcm.defaultSortable = true;

    var bombbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:bomds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var bomgrid = new Ext.grid.EditorGridPanel({
    	id:'bomGrid',
        ds: bomds,
        cm: bomcm,
        sm: bomsm,
        bbar: bombbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_bomInfo',
        layout:"fit"
    });

   	bomds.load({params:{start:0, limit:10}});

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[bomgrid]
		}]
	});
});
</script>