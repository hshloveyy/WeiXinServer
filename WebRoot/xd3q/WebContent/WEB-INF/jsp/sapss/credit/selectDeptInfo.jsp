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
<form action="" id="findProjectForm" name="findProjectForm">
<table width="100%">
<tr>
	<td align="center">部门编号</td>
	<td align="center">部门名称</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
		<input type="text" id="deptcode" name="deptcode" value=""></input>
	</td>
	<td>
		<input type="text" id="deptname" name="deptname" value=""></input>
	</td>
	<td>
	</td>
	<td>
		<input type="button" value="查找" onclick="findProjectInfo()"></input>
	</td>
	<td>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_project"></div>
</body>
</html>
<script type="text/javascript">
var projectds;

function findProjectInfo(){
	var param = Form.serialize('findProjectForm');
	var requestUrl  = 'creditArriveController.spr?action=queryDept&' + param;
	
	projectds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	projectds.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	projectds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'DEPT_ID'},
					{name:'DEPT_CODE'},
					{name:'DEPT_NAME'}
          		])
    });
    
    var projectsm = new Ext.grid.CheckboxSelectionModel();
    
    var projectcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		projectsm,
		   {header: '部门ID',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_ID',
           hidden:true
           },
		   {header: '部门编号',
           width: 100,
           sortable: false,
           dataIndex: 'DEPT_CODE'
           },
	       {header: '部门名称',
           width: 210,
           sortable: false,
           dataIndex: 'DEPT_NAME'
           }
    ]);
    projectcm.defaultSortable = true;
    
    var projectbbar = new Ext.PagingToolbar({
        pageSize:10,
        store:projectds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var projectgrid = new Ext.grid.GridPanel({
    	id:'projectGrid',
        ds: projectds,
        cm: projectcm,
        sm: projectsm,
        bbar: projectbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_project',
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
			contentEl: 'div_project',
			buttonAlign:'center',
			items:[projectgrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (projectgrid.selModel.hasSelection()){
						var records = projectgrid.selModel.getSelections();
						
						if (records.length > 1){
							top.ExtModalWindowUtil.alert('提示','只能选择一个项目信息！');
						}else{
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}
					}
					else{
						top.ExtModalWindowUtil.alert('提示','请选择指定的项目信息！');
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
	
	
	projectgrid.on('rowdblclick',function(grid,rowIndex){
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
