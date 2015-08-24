<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找成本中心页面</title>
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
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
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
        		proxy : new Ext.data.HttpProxy({url:'assetController.spr?action=rtnFindCostCenter&comCode='+$('comCode').value}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'BUKRS'},
            		{name:'KOSTL'},
            		{name:'KTEXT'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
    tempDS.load({params:{start:0, limit:100}});
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		 {
           	  header: '公司代码',
              width: 70,
              sortable: true,
              dataIndex: 'BUKRS'
           },{
           	  header: '成本中心',
              width: 120,
              sortable: false,
              dataIndex: 'KOSTL'
           },{
           	  header: '成本中心描述',
              width: 150,
              sortable: false,
              dataIndex: 'KTEXT'
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
    if($('comCode').value==''){
       top.ExtModalWindowUtil.alert('提示','请录入公司代码！');
       return;
    }
	var url = 'assetController.spr?action=rtnFindCostCenter&comCode='+$('comCode').value;
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:100}});
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="materialForm" action="">
	<table width="715"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
		<tr>
			<td width="8%" align="right">公司代码</td>	
			<td width="20%"><input type="text" size="18" name="comCode" value="" <c:if test="${type=='1'}"></c:if>/></td>	
			<td align="left" colspan="3">&nbsp;<input type="button" value="查找" onclick="findMaterial()"/>   <input type="reset" value="清空"/></td>	
		</tr>
	</table>
</form>	
</div>
<div id="gridDiv" class="x-hide-display"></div>
</body>
</html>
