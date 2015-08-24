<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找客户页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var tempDS;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var customerForm=new Ext.form.FormPanel({
		frame:true,
		renderTo:document.body,
		baseCls:'x-plain',
		autoHeight:true,
		width:740,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'customer'
			},{
			contentEl:'gridDiv'
		}],
		buttons:[{
			text:'确定',
			handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
					}else{
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
			}
		},{
			text:'关闭',
			handler:function(){
				top.ExtModalWindowUtil.close();
			}
		}]
	});
 	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'pledgeReceiptsController.spr?action=rtnFindMaterial'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'MATNR'},
            		{name:'MAKTX'},
            		{name:'MEINS'},
            		{name:'MTART'},
            		{name:'WERKS'},
            		{name:'VKORG'},
            		{name:'MATKL'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
//{"VKORG":"2105","MTVER":"","BKLAS":"","VPRSV":"","PRCTR":"0021001005","MTART":"HAWA",
//	"EKGRP":"","MAKTX":"60g泉林双胶纸 889*1194","MEINS":"KG","MATNR":"000000000000000001","WERKS":"2105","BWTAR":""}	
//物料编号、物料描述、单位、物料类型、工厂、销售组织、采购组、物料组、评估类、估价类型、利润中心、价格类型
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           	  header: '物料编号',
              width: 100,
              sortable: true,
              dataIndex: 'MATNR'
           },{
           	  header: '物料描述',
              width: 100,
              sortable: false,
              dataIndex: 'MAKTX'
           },{
           	  header: '基本计量单位',
              width: 100,
              sortable: false,
              dataIndex: 'MEINS'
           },{
           	  header: '物料类型',
              width: 100,
              sortable: false,
              dataIndex: 'MTART'
           },{
           	   header: '工厂',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'WERKS'
           },{
           	   header: '销售组织',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'VKORG'
           },{
           	   header: '物料组',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'MATKL'
           }
    ]);
    cm.defaultSortable = true;
    
    var paging = new Ext.PagingToolbar({
        pageSize:10,
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
        height:280,
        width:715,
        bbar: paging,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'gridDiv',
        autowidth:true,
        layout:'fit'
    }); 
    grid.on('rowdblclick',function(grid,rowIndex){
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
     	
});//end of Ext.onReady    
function findMaterial(){
	var url = 'pledgeReceiptsController.spr?action=rtnFindMaterial&'+Form.serialize('materialForm');
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:10}});
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="materialForm">
	<table width="715"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
		<tr>
			<td width="8%" align="right">物料号</td>	
			<td width="20%"><input type="text" size="18" name="materialCd"/></td>	
			<td width="8%" align="right">物料描述</td>	
			<td width="20%"><input type="text" size="18" name="materialName"/></td>	
			<td width="8%" align="right">物料类型</td>	
			<td width="20%"><input type="text" size="18"  name="materialType"/></td>	
		</tr>
		<tr>	
			<td align="right">工厂</td>	
			<td><input type="text" size="18"  name="factory" value="${factory}"/></td>	
			<td align="right">销售组织</td>	
			<td><input type="text" size="18" name="saleOrg" value="${saleOrg}" /></td>	
			
		</tr>
		<tr align="center">
			<td colspan="6">
				<input type="button" value="查找" onclick="findMaterial()"/>   <input type="reset" value="清空"/>
			</td>
			
		</tr>
	</table>
</form>	
</div>
<div id="gridDiv" class="x-hide-display"></div>
<script type="text/javascript">
	document.getElementById('saleOrg').disabled=${isFunctionOrg};
	document.getElementById('factory').disabled=${isFunctionOrg};
</script>

</body>
</html>
