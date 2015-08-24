<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找供应商页面</title>
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
			contentEl:'supplierForm'
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
			text:'取消选择',
			handler:function(){
							top.ExtModalWindowUtil.setReturnValue('');
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
			}
		},{
			text:'关闭',
			handler:function(){
				top.ExtModalWindowUtil.close();
			}
		}]
	});
	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=findSupplier'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'LAND1'},
					{name:'REGIO'},
					{name:'INCO1'},
					{name:'INCO2'},
					{name:'ORT01'},
					{name:'STRAS'},
					{name:'TELBX'},
					{name:'LIFNR'},
					{name:'TELFX'},
					{name:'EKORG'},
					{name:'BUKRS'},
					{name:'NAME2'},
					{name:'NAME1'},
					{name:'ZWELS'},
					{name:'TELF1'},
					{name:'TELF2'},
					{name:'PSTLZ'},
					{name:'ZTERM'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
    //供应商编码、供应商名称、街道/门牌号、邮政编码/城市、国家、地区、电话、电话分机、传真号、EMAIL、联系人、付款条件、付款方式、货币、国际贸易条款1、国际贸易条款2
    //"LAND1":"CN","REGIO":"150","INCO1":"","INCO2":"","ORT01":"涵江",
   // "STRAS":"莆田市涵江区梧榶镇新丰村","TELBX":"","LIFNR":"0040000000","TELFX":"","EKORG":"2109","BUKRS":"2100","NAME2":"",
   // "NAME1":"莆田市涵江区章圣鞋业有限公司","ZWELS":"H","TELF1":"","TELF2":"","PSTLZ":"365002","ZTERM":"0003"}
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           	header: '供应商编码',
           	width: 100,
           	sortable: true,
           	dataIndex: 'LIFNR'
          },{
           	header: '供应商名称',
           	width: 100,
           	sortable: true,
           	dataIndex: 'NAME1'
		  },{
           	header: '街道/门牌号',
           	width: 100,
           	sortable: true,
           	dataIndex: 'STRAS'
		  },{
           	header: '采购组织',
           	width: 100,
           	sortable: true,
           	dataIndex: 'EKORG'
		  },{
           	header: '邮政编码/城市',
           	width: 100,
           	sortable: true,
           	dataIndex: 'PSTLZ'
		  },{
           	header: '国家',
           	width: 100,
           	sortable: true,
           	dataIndex: 'LAND1'
		  },{
           	header: '地区',
           	width: 100,
           	sortable: true,
           	dataIndex: 'REGIO'
		  },{
           	header: '电话',
           	width: 100,
           	sortable: true,
           	dataIndex: 'TELF1'
		  },{
           	header: '电话分机',
           	width: 100,
           	sortable: true,
           	dataIndex: 'TELF2'
		  },{
           	header: '传真号',
           	width: 100,
           	sortable: true,
           	dataIndex: 'TELFX'
		  },{
           	header: 'EMAIL',
           	width: 100,
           	sortable: true,
           	dataIndex: 'TELBX'
		  },{
           	header: '联系人',
           	width: 100,
           	sortable: true,
           	dataIndex: 'NAME2'
		  },{
           	header: '付款条件',
           	width: 100,
           	sortable: true,
           	dataIndex: 'ZTERM'
		  },{
           	header: '付款方式',
           	width: 100,
           	sortable: true,
           	dataIndex: 'ZWELS'
		  },{
           	header: '货币',
           	width: 100,
           	sortable: true,
           	dataIndex: 'BUKRS'
		  },{
           	header: '国际贸易条款1',
           	width: 100,
           	sortable: true,
           	dataIndex: 'INCO1'
		  },{
           	header: '国际贸易条款2',
           	width: 100,
           	sortable: true,
           	dataIndex: 'INCO2'
		  }          
    ]);
    cm.defaultSortable = true;
    
    var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
 //ds.load({params:{start:0, limit:10},arg:[]});
 var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        height:280,
        width:717,
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
function findSupply(){
	var url = 'masterQueryController.spr?action=findSupplier&'+Form.serialize('supplierForm');
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:10}});
}

</script>
</head>
<body>
<div id="gridDiv" class="x-hide-display"></div>
<div id="supplier" class="x-hide-display">
<form id="supplierForm">
	<table width="717">
		<tr align="center">
			<td width="26%">编码</td>	
			<td><input type="text" name="supplyCd"/></td>	
			<td width="27%">名称</td>	
			<td><input type="text" name="supplyName"/></td>	
			<td width="27%">采购组织</td>	
			<td><input type="text" name="purchaseOrg" value="${purchaseOrg}" /></td>	
			<td width="20%">
			<input type="button" value="查找" onclick="findSupply();"/><input type="reset" value="清空"/>
			</td>
		</tr>
	</table>
</form>
</div>
<script type="text/javascript">
	document.getElementById('purchaseOrg').disabled=${isFunctionOrg};
</script>

</body>
</html>
