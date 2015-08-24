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
<form action="" id="findExportApplyMtForm" name="findExportApplyMtForm">
<table width="100%">
<tr>
	<td align="center">物料编码</td>
	<td align="center">物料描述</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td align="center">
		<input type="hidden" id="exportApplyId" name="exportApplyId" value="${exportApplyId}"></input>
		<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>
		<input type="text" id="materialCode" name="materialCode" value=""></input>
	</td>
	<td align="center">
	<input type="text" id="materialName" name="materialName" value=""></input>
	</td>		
	<td>
	<input type="button" value="查找" onclick="findExportApplyMt()"></input>
	</td>
	<td>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_materiel"></div>
</body>
</html>
<script type="text/javascript">
var materields;



function findExportApplyMt(){
	var param = Form.serialize('findExportApplyMtForm');
	var requestUrl  = 'shipController.spr?action=queryExportApplyMateriel&' + param;
	materields.proxy= new Ext.data.HttpProxy({url: requestUrl});
	materields.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	materields = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'' }),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
					{name:'EXPORT_MATE_ID'},              
					{name:'EXPORT_APPLY_ID'},                 
					{name:'MATERIAL_CODE'},                
				    {name:'MATERIAL_NAME'},                 
					{name:'MATERIAL_UNIT'},                
            		{name:'TOTAL_QUANTITY'},
					{name:'RATE'},
					{name:'PRICE'},
            		{name:'CURRENCY'},
            		{name:'PEINH'},
					{name:'TOTAL'},
					{name:'APPLY_QUANTITY'},
            		{name:'HAS_QUANTITY'}
          		])
    });
    
    var materielsm = new Ext.grid.CheckboxSelectionModel();
    
    var materielcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		materielsm,
		　 {header: '行项目ID',
           width: 100,
           sortable: false,
           dataIndex: 'EXPORT_MATE_ID',
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
           dataIndex: 'PEINH'
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
           dataIndex: 'HAS_QUANTITY',
           hidden: true
           }                        
    ]);
    materielcm.defaultSortable = true;
    
    var materielbbar = new Ext.PagingToolbar({
        pageSize: 200,
        store:materields,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var materielgrid = new Ext.grid.GridPanel({
    	id:'materielgrid',
        ds: materields,
        cm: materielcm,
        sm: materielsm,
        bbar: materielbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_materiel',
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
			contentEl: 'div_materiel',
			buttonAlign:'center',
			items:[materielgrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (materielgrid.selModel.hasSelection()){
						var records = materielgrid.selModel.getSelections();
						
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
	
	
	materielgrid.on('rowdblclick',function(grid,rowIndex){
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