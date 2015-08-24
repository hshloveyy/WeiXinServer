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
<div id="div_north" class="search">
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >部门名称：</td>
		<td width="30%" >
			<div id="div_createdeptid_sh"></div>
			<fisc:searchHelp divId="div_createdeptid_sh" boName="AdvanceWarnRecv" boProperty="createdeptid" value="${advanceWarnRecv.createdeptid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >角色名称：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="roleName" name="roleName" value="" />
		</td>
	</tr>
	<tr align="center">
      <td align="center" colspan="4"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>
<div id="div_center_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;

function find(){
	var url = 'orgController.spr?action=receiveUserStaff&'+Form.serialize('mainForm');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   	var plant = Ext.data.Record.create([
		{name:'userId'},
		{name:'deptId'},
		{name:'deptName'},
		{name:'userName'},
		{name:'realName'}   	                                              	
	]);
   	
   	ds = new Ext.data.Store({
   				proxy : new Ext.data.HttpProxy({url:' '}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},plant)
    });

   	var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           header: '用户名',
           width: 100,
           sortable: true,
           dataIndex: 'userName'
           },{
           header: '用户姓名',
           width: 100,
           sortable: true,
           dataIndex: 'realName'
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
    
    grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        border:false,
        loadMask:true,
        autoScroll:true,
        autowidth:true,
        region:'center',
        el: 'div_center_center',
        layout:'fit'
    });

    grid.render();
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[
		{
			region:"north",
			contentEl:'div_north',
			border:false,
			height:60
		},
		{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'panel',
				border:false,
				buttonAlign:'center',
            	defaults:{bodyStyle:'padding:0px'},
            	layout:'fit',
            	items:[grid],
            	buttons: [{
       	              text: '确认',
       	              handler:function()
       	              {
            			if (grid.selModel.hasSelection()){
            				var records = grid.selModel.getSelections();

            				top.ExtModalWindowUtil.setReturnValue(records);
            				top.ExtModalWindowUtil.markUpdated();
    		                top.ExtModalWindowUtil.close();
            			}else{
            				top.ExtModalWindowUtil.alert('提示','请选择员工信息信息！');
            			}
       	             }   
            	}]
			}]
		}]
	});
});
</script>