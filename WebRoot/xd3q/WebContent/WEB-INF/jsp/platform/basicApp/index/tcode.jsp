<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>讯盟业务平台控制台</title>
</head>
<body >
<div id="div_southForm" style="overflow: auto; width: 100%; height: 100%"></div>
<div id="div_toolbar" ></div>
<div id="div_center" ></div>
 
<fisc:grid divId="div_southForm" pageSize="10" tableName="(select t.AUTHRESOURCEID,t.AUTHRESOURCEDESC,t.TCODE,t.URL,t.DEGREE  from YAUTHRESOURCE t where t.TCODE like '%${strTcode}%' or t.AUTHRESOURCEDESC like '%${strTcode}%' and t.AUTHRESOURCETYPE!='1' ) TT " defaultCondition=""
 handlerClass="com.infolion.platform.basicApp.index.web.grid.QueryTcodeGrid"
 whereSql="" orderBySql="" needCheckBox="false" title="" needAutoLoad="true" editable="false" height="300"></fisc:grid>
</body>
</html>

<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';

Ext.onReady(function(){	
  div_southForm_grid.on("rowdblclick",function gridrowdblclick(grid, rowindex, e){
		  var record =  grid.getSelectionModel().getSelected(); 
		  var tcode = record.data.tcode;  
		  var url = record.data.url;
		  var nodeText = record.data.authresourcedesc;
		  var nodeId = record.data.authresourceid;

		  if(!tcode)
		  {
			  Ext.Ajax.request({
					waitTitle:'系统提示',
			  		method: 'POST',
					url: context + '/index/indexMainController.spr?action=executeTcode',
					params:'&tcode='+ tcode ,
					success: function(response, options){
					     var node = Ext.util.JSON.decode(response.responseText);
						 if(node.success==true){
							 if(node.isExist==true){
		                    	 _getMainFrame().addTab(node.text,node.id,node.hrefTarget,node.iconCls);
		                    	 _getMainFrame().tcodeWin.close();
		                    	 _getMainFrame().tcodeWin = null;
							 }
						 }
						 else
					     {
								Ext.Msg.show({
									title : "操作失败！",
									msg : node.msg,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR
								});
						 }
					},
					faliue: function(response, options){
						showError('与服务器交互失败，请查看具体提示原因！');
					}
	
				}); 
		  }
		  else
		  {
			  _getMainFrame().addTab(nodeText,nodeId,url,'');
		  } 
	});

var viewport = new Ext.Viewport({
	layout:"border",
	items:[{
		region:"center",
		items:[{
				region:"north",
				contentEl:'div_toolbar',
				border:false,
				height:0
			},{
				region:'center',
				border:false,
				contentEl:'div_center'
			},		
			{
				region:"south",
				layout:'fit',
				border:false,
				autoScroll: true,
	            title:"事务搜索明细:(双击行记录，执行事务)",
	            height:500,
	            bodyStyle:"background-color:#DFE8F6",
				contentEl:'div_southForm'
			}]
		}]
});
/*
var toolbars = new Ext.Toolbar({
	items:[
			'-',
			{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
			{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
			],
	renderTo:"div_toolbar"
});
*/
//div_southForm_grid.doLayout();


});

</script>