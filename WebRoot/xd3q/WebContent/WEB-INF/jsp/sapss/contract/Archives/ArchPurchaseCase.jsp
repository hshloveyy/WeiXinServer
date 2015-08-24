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
<div id="div_otherInfo"></div>
</body>
</html>
<script type="text/javascript">
var strPurchaseRowsId = '${purchaserowsid}';
var otherds;

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;

   	var otherPlant = Ext.data.Record.create([
   		{name:'MATERIEL_CASE_ID'},
   		{name:'PURCHASE_ROWS_ID'},
   		{name:'KONV_KSCHL_D_CONDITION_TYPE_PU'},
   		{name:'KONV_WEARS_D_CURRENCY'},
   		{name:'KONV_KBETR'},
   		{name:'KONV_LIFNR'},
   		{name:'KONV_LIFNR_NAME'},
   		{name:'CMD'}
	]);
	
	otherds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=queryPurchaseMaterielCaseInfo&purchaserowsid='+strPurchaseRowsId}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},otherPlant)
    });
    
    var othersm = new Ext.grid.CheckboxSelectionModel();
    
    var othercm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		othersm,
			{header: '其他费用ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'MATERIEL_CASE_ID',
           	hidden:true
			},
           {header: '采购合同行项ID',
           width: 100,
           sortable: true,
           dataIndex: 'PURCHASE_ROWS_ID',
           hidden:true
           },
           {header: '条件类型',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KSCHL_D_CONDITION_TYPE_PU'
           },
           {header: '币别名称',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_WEARS_D_CURRENCY'
           },
           {header: '价格( 条件金额或百分数 )',
           width: 200,
           sortable: true,
           dataIndex: 'KONV_KBETR'
           },
           {header: '供应商或债权人的帐号',
           width: 200,
           sortable: true,
           dataIndex: 'KONV_LIFNR'
           },
           {header: '供应商名称',
           width: 200,
           sortable: true,
           dataIndex: 'KONV_LIFNR_NAME'
           },
           {header: '备注',
           width: 200,
           sortable: true,
           dataIndex: 'CMD'
           }
    ]);
    othercm.defaultSortable = true;
    
    var otherbbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:otherds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var othergrid = new Ext.grid.EditorGridPanel({
    	id:'otherGrid',
        ds: otherds,
        cm: othercm,
        sm: othersm,
        bbar: otherbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_otherInfo',
        layout:"fit"
    });
    
    otherds.load({params:{start:0, limit:10}});
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[othergrid]
		}]
	});
});
</script>