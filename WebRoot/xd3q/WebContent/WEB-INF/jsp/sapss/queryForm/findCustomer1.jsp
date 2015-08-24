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
//						customerCd:Ext.getDom('customerCd').value,
//						customerName:Ext.getDom('customerName').value,
//						customerIndex:Ext.getDom('customerIndex').value,
//						salerOrg:Ext.getDom('salerOrg').value,
//						saleChannel:Ext.getDom('saleChannel').value,
//						productOrg:Ext.getDom('productOrg').value
	
 	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=findCustomer'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'INCO1'},
					{name:'WAERS'},
					{name:'INCO2'},
					{name:'KUNNR'},
					{name:'ORT01'},
					{name:'BZIRK'},
					{name:'STRAS'},
					{name:'SORTL'},
					{name:'TELFX'},
					{name:'TELF1'},
					{name:'PSTLZ'},
					{name:'TELF2'},
					{name:'PSTLZ2'},
					{name:'ZTERM'},
					{name:'REGIO'},
					{name:'LAND1'},
					{name:'KUNN4'},
					{name:'KUNN3'},
					{name:'KUNN2'},
					{name:'TELBX'},
					{name:'NAME2'},
					{name:'ZWELS'},
					{name:'NAME1'},
					{name:'ZSABE'},
					{name:'ADRNR'},
					{name:'VKORG'},
					{name:'VTWEG'},
					{name:'SPART'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
//{"INCO1":"","WAERS":"CNY","INCO2":"","KUNNR":"0040000005","ORT01":"厦门",
//"BZIRK":"000002","STRAS":"吕岭路16号","SORTL":"","TELFX":"","TELF1":"","PSTLZ":"361000",
//"TELF2":"","PSTLZ2":"","ZTERM":"","REGIO":"","LAND1":"CN","KUNN4":"0040000005","KUNN3":"",
//"KUNN2":"0040000005","TELBX":"","NAME2":"","ZWELS":"","NAME1":"厦门鼎立纸业有限公司","ZSABE":"","ADRNR":"0000022717"}    
//客户编码、客户名称、街道/门牌号、邮政编码/城市、国家、地区、电话、电话分机、传真号、
//EMAIL、联系人、付款条件、付款方式、销售地区、货币、国际贸易条款1、国际贸易条款2、售达方、售达方名称/地址/邮编、开票方、付款方、销售组织、分销渠道、产品组
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           	  header: '客户编码',
              width: 100,
              sortable: true,
              dataIndex: 'KUNNR'
           },{
           	  header: '客户名称',
              width: 100,
              sortable: false,
              dataIndex: 'NAME1'
           },{
           	   header: '国家',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'LAND1'
           },{
           	   header: '地区',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'ORT01'
           },{
           	  header: '邮政编码/城市',
              width: 100,
              sortable: false,
              dataIndex: 'PSTLZ'
           },{
           	  header: '街道/门牌号',
              width: 100,
              sortable: false,
              dataIndex: 'STRAS'
           },{
           	   header: '电话',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'TELF1'
           },{
           	   header: '电话分机',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'TELF2'
           },{
           	   header: '传真号',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'TELFX'
           },{
           	   header: 'EMAIL',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'TELBX'
           },{
           	   header: '联系人',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'ZSABE'
           },{
           	   header: '付款条件',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'ZTERM'
           },{
           	   header: '付款方式',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'ZWELS'
           },{
           	   header: '销售地区',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'BZIRK'
           },{
           	   header: '货币',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'WAERS'
           },{
           	   header: '国际贸易条款1',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'INCO1'
           },{
           	   header: '国际贸易条款2',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'INCO2'
           },{
           	   header: '付款方',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'KUNN4'
           },{
           	   header: '销售组织',
           	   width: 100,
           	   sortable: false,
           	   dataIndex: 'VKORG'
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
function findCustomer(){
	var url = 'masterQueryController.spr?action=findCustomer&'+Form.serialize('customerForm');
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:10}});
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="customerForm">
	<table width="717">
		<tr align="center">
			<td width="25%">客户编码</td>	
			<td><input type="text" size="14" name="customerCd"/></td>	
			<td width="25%">客户名称</td>	
			<td><input type="text" size="14" name="customerName"/></td>	
			<td width="25%">销售组织</td>	
			<td><input type="text" size="14" id="salerOrg"  name="salerOrg" value="${salerOrg}"/></td>	
			<td width="25%">	
			<input type="button" value="查找" onclick="findCustomer();"/><input type="reset" value="清空"/>		
			</td>
		</tr>
	</table>
</form>	
</div>
<div id="gridDiv" class="x-hide-display"></div>
<script type="text/javascript">
	document.getElementById('salerOrg').disabled=${isFunctionOrg};
</script>
</body>
</html>
