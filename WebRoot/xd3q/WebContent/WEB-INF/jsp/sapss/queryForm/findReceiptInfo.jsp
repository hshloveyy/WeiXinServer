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
	<td align="center">进仓单号</td>
	<td align="center">立项号</td>
	<td align="center">立项名称</td>
	<td align="center">合同号</td>
	<td align="center">合同名称</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="text" id="receiptNo" name="receiptNo" value="" size=15></input>
	</td>
	<td>
	<input type="text" id="projectNo" name="projectNo" value="" size=15></input>
	</td>
	<td>
	<input type="text" id="projectName" name="projectName" value="" size=15></input>
	</td>	
	<td>	
	<input type="text" id="contractNo" name="contractNo" value="" size=15></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size=15></input>
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
	var requestUrl  = 'receiptGoodsController.spr?action=queryReceiptInfo&' + param;

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
					{name:'RECEIPT_ID'}, 
					{name:'PROJECT_NO'},                    
					{name:'PROJECT_NAME'},                   
					{name:'CONTRACT_NO'},                   
					{name:'CONTRACT_NAME'},
					{name:'RECEIPT_NO'}
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		   {header: '进仓单ID',
           width: 100,
           sortable: true,
           dataIndex: 'RECEIPT_ID',
           hidden:true
           },
		　 {header: '进仓单号',
           width: 80,
           sortable: false,
           dataIndex: 'RECEIPT_NO'
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
           {header: '合同号',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
            width: 120,
            sortable: false,
            dataIndex: 'CONTRACT_NAME'
            }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 20,
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
					var records = grid.selModel.getSelections();
					if (records.length !=1 ){
						top.ExtModalWindowUtil.alert('提示','请选择一笔记录！');
					}else{
						top.ExtModalWindowUtil.setReturnValue(records[0].data);
						top.ExtModalWindowUtil.markUpdated();
						top.ExtModalWindowUtil.close();
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
		var records = grid.selModel.getSelections();
		if (records.length !=1 ){
			top.ExtModalWindowUtil.alert('提示','请选择一笔记录！');
		}else{
			top.ExtModalWindowUtil.setReturnValue(records[0].data);
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		}
    });
});
</script>
