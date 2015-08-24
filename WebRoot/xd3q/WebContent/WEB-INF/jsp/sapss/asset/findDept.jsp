<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找部门信息页面</title>
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
        		proxy : new Ext.data.HttpProxy({url:'assetController.spr?action=findDept&pdeptid='+'${pdeptid}'}),
        		reader: new Ext.data.JsonReader({
            				root: 'root',
            				totalProperty: 'totalProperty'
            			},[
            		{name:'DEPT_NAME'},
            		{name:'DEPT_ID'}
          		])
    });
    tempDS = ds;
    var sm = new Ext.grid.CheckboxSelectionModel();
    tempDS.load({params:{start:0, limit:100}});
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		 {
           	  header: '部门名称',
              width: 200,
              sortable: true,
              dataIndex: 'DEPT_NAME'
           },
           {
           	  header: '部门id',
              width: 100,
              sortable: true,
              hidden:true,
              dataIndex: 'DEPT_ID'
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
	var url = 'assetController.spr?action=findDept&pdeptid='+$('pdeptid').value;
	tempDS.proxy= new Ext.data.HttpProxy({url:url});
	tempDS.load({params:{start:0, limit:100}});
}
</script>
</head>
<body>

<div id="customer" class="x-hide-display">
<form id="materialForm">
	<table width="715"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
		<tr>
			<td width="12%" align="right">公司名称:</td>	
			<td width="50%">
				<select name="pdeptid" id="pdeptid">
						<c:forEach items="${depts}" var="row">
							<option value="${row.deptid}" ${row.deptid==pdeptid?"selected":""}>${row.deptname}</option>
						</c:forEach>
				</select>
            </td>	
			<td align="left" colspan="2">&nbsp;<input type="button" value="查找" onclick="findMaterial()"/>   <input type="reset" value="清空"/></td>	
		</tr>
	</table>
</form>	
</div>
<div id="gridDiv" class="x-hide-display"></div>
</body>
</html>
