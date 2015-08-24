<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找付款方式页面</title>
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
		width:160,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'gridDiv'
		}],
		buttons:[{
			text:'确定',
			handler:function(){
   				if (grid.selModel.hasSelection()){
					var records = grid.selModel.getSelections();
					var result = "";
					for (var i=0;i<records.length;i++)
					{
					    result = result + "," + records[i].json.ID;
					}
							top.ExtModalWindowUtil.setReturnValue(result);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
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
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=rtnFindPayTerm'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'ID'},
            		{name:'TITLE'},
            		{name:'CMD'}									
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel(); 
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
			header: '付款编码',
           width: 50,
           sortable: false,
           dataIndex: 'ID'
           },{
			header: '付款名称',
           width: 100,
           sortable: true,
           dataIndex: 'TITLE'
           },{
			header: '说明',
           	width: 200,
           	dataIndex: 'CMD',
           	sortable: true
           }        
    ]);
    cm.defaultSortable = true;
 ds.load();
 var grid = new Ext.grid.GridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        height:350,
        width:400,
   //    bbar: paging,
        border:false,
        loadMask:true,
        autoScroll:true,
        applyTo:'gridDiv',
        autowidth:true,
        layout:'fit'
    });  
});//end of Ext.onReady   
</script>
</head>
<body>
<div id="gridDiv" class="x-hide-display"></div>
</body>
</html>
