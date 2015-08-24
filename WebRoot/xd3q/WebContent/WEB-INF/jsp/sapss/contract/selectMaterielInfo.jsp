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
<table width="100%">
<tr>
	<td align="center">物料编号</td>
	<td align="center">物料描述</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="text" id="" name="" value=""></input>
	</td>
	<td>
	<input type="text" id="" name="" value=""></input>
	</td>
	<td>
	<input type="button" value="查询"></input>
	</td>
	<td>
	<input type="button" value="清空"></input>
	</td>
</tr>
</table>
</div>

<div id="div_materiel"></div>
</body>
</html>
<script type="text/javascript">
var PurchaseId = '${purchaseid}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var sumitMateriel = new Ext.Toolbar.Button({
   		text:'确定',
		handler:function(){
			top.ExtModalWindowUtil.setReturnValue('1234567');
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
			/*if (materielgrid.selModel.hasSelection()){
				var records = materielgrid.selModel.getSelections();
				
				if (records.length > 1){
					var text = '';
					text = text + records[0].json.contractInfoId + 
							'|' + records[0].json.contractInfoCode;
					
					
				}else{
					top.ExtModalWindowUtil.alert('提示','只能选择一种物料信息！');
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择指定的物料信息！');
			}*/
		}
   	});
   	
   	var closeMateriel = new Ext.Toolbar.Button({
   		text:'取消',
		handler:function(){
			top.ExtModalWindowUtil.close();
		}
   	});
    
    var materieltbar = new Ext.Toolbar({
		items:[sumitMateriel,'-',closeMateriel]
	});

	var materields = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'contractInfoId'},
					{name:'contractInfoCode'}
          		])
    });
    
    var materielsm = new Ext.grid.CheckboxSelectionModel();
    
    var materielcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		materielsm,
		   {header: '物料编号',
           width: 100,
           sortable: true,
           dataIndex: 'contractInfoId'
           },
		　 {header: '物料描述',
           width: 100,
           sortable: false,
           dataIndex: 'contractInfoCode'
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
    	id:'materielGrid',
        ds: materields,
        cm: materielcm,
        sm: materielsm,
        bbar: materielbbar,
        tbar: materieltbar,
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
			items:[materielgrid]
		}]
	});
});
</script>
