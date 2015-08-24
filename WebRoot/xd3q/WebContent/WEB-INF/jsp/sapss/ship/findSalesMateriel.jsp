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
<form action="" id="findsalesForm" name="findsalesForm">
<table width="100%">
<tr>
	<td align="center">物料编码</td>
	<td align="center">物料描述</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td align="center">
		<input type="hidden" id="contractSalesId" name="contractSalesId" value="${contractSalesId}"></input>
		<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>
		<input type="hidden" id="shipType" name="shipType" value="${shipType}"></input>		
		<input type="text" id="materialCode" name="materialCode" value=""></input>
	</td>
	<td align="center">
	<input type="text" id="materialName" name="materialName" value=""></input>
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
	var param = Form.serialize('findsalesForm');
	var requestUrl  = 'shipNotifyController.spr?action=querySalesMateriel&' + param;
	salesds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	salesds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	salesds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'' }),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'SALES_ROWS_ID'},            	
            		{name:'MATERIAL_CODE'},
					{name:'MATERIAL_NAME'},
					{name:'MATERIAL_UNIT'},
            		{name:'TOTAL_QUANTITY'},
					{name:'RATE'},
					{name:'PRICE'},
            		{name:'CURRENCY'},
            		{name:'KPEIN'},
					{name:'TOTAL'},
					{name:'APPLY_QUANTITY'},
            		{name:'HAS_QUANTITY'}
          		])
    });
    
    var salessm = new Ext.grid.CheckboxSelectionModel();
    
    var salescm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		salessm,
		　 {header: '行项目ID',
           width: 100,
           sortable: false,
           dataIndex: 'SALES_ROWS_ID',
           hidden: true
           },		
		　 {header: '物料编码',
           width: 100,
           sortable: false,
           dataIndex: 'MATERIAL_CODE'
           },
           {header: '物料描述',
           width: 150,
           sortable: false,
           dataIndex: 'MATERIAL_NAME'
           },
		　 {header: '单位',
           width: 80,
           sortable: false,
           dataIndex: 'MATERIAL_UNIT'
           },
		　 {header: '单价',
           width: 80,
           sortable: false,
           dataIndex: 'PRICE'
           }, 
		　 {header: '条件订价单位',
           width: 80,
           sortable: false,
           dataIndex: 'KPEIN'
           },                       
           {header: '总数量',
           width: 100,
           sortable: false,
           dataIndex: 'TOTAL_QUANTITY'
           },
           {header: '已申请数量',
           width: 100,
           sortable: false,
           dataIndex: 'APPLY_QUANTITY'
           },
           {header: '剩余数量',
           width: 100,
           sortable: false,
           dataIndex: 'HAS_QUANTITY'
           }               
    ]);
    salescm.defaultSortable = true;
    
    var salesbbar = new Ext.PagingToolbar({
        pageSize: 200,
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
					if (salesgrid.selModel.hasSelection()){
						var records = salesgrid.selModel.getSelections();
						
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
	
	
	salesgrid.on('rowdblclick',function(grid,rowIndex){
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