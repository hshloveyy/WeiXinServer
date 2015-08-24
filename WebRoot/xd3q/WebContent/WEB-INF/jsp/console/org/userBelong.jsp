<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="div_center_center">
</div>
</body>
</html>
<script type="text/javascript">
var userId = '${userid}';

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'orgController.spr?action=queryUserByUserId&userid=' + userId}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'userId'},
					{name:'deptId'},
					{name:'deptName'},
					{name:'userName'}
          		])
    });
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  {
           header: '用户名',
           width: 100,
           sortable: true,
           dataIndex: 'userName'
           },
		　{header: '员工编号',
           width: 100,
           sortable: false,
           dataIndex: 'userId',
           hidden:true
           },
		  {header: '部门编号',
           width: 100,
           sortable: false,
           dataIndex: 'deptId',
           hidden:true
           },
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'deptName'
           }
    ]);
    cm.defaultSortable = true;
    
    var grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        layout:'fit'
    });
	
	ds.load({params:{start:0, limit:50},arg:[]});
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'panel',
				border:false,
            	defaults:{bodyStyle:'padding:0px'},
            	layout:'fit',
            	items:[grid]
			}]
		}]
	});
});
</script>