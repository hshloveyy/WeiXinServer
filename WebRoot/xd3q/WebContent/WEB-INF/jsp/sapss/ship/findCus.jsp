<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找外客户页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var tempDS;
var ds;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var customerForm=new Ext.form.FormPanel({
		frame:true,
		renderTo:document.body,
		baseCls:'x-plain',
		autoHeight:true,
		width:600,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'div_north'
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
	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'shipNotifyController.spr?action=rtnFindCus'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
					{name:'ID'},
					{name:'TITLE'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           	header: 'ID',
           	width: 50,
           	sortable: true,
           	dataIndex: 'ID'
		  },
		  {
           	header: '外客户名称',
           	width: 300,
           	sortable: true,
           	dataIndex: 'TITLE'
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
 ds.load();
 var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar:paging,
        height:310,
        width:450,
   //    bbar: paging,
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
function findCrditInfo(){
	var param = Form.serialize('findLCForm');
	var requestUrl  = 'shipNotifyController.spr?action=rtnFindCus&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;

	ds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	ds.load({params:{start:0, limit:10},arg:[]});
} 
</script>
</head>
<body>
<div id="div_north" style="width:100%;height:100%">
<form action="" id="findLCForm" name="findLCForm">
<table width="70%">
<tr>
    <td>&nbsp;</td>
	<td >部门</td>
	<td >外客户名称</td>
	<td></td>
	<td></td>
</tr>
<tr>
    <td>&nbsp;</td>
	<td>
	<div id="dept"></div>
	</td>
	<td>
	<input type="text" id="title" name="title" value=""></input>
	</td>
	<td>
	<input type="button" value="查找" onclick="findCrditInfo()"></input>
	</td>
	<td>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>
<div id="gridDiv" class="x-hide-display"></div>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
</body>
</html>
