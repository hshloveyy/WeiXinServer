<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月10日 15点43分12秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>数据传输记录表(DataTransfer)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据传输记录表增加页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/dpframework/dataTransfers/dataTransferAdd.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>数据库名：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="DataTransfer.systemname" name="DataTransfer.systemname" value="${dataTransfer.systemname}"  >
			注:PL/SQL数据库名
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>数据库类型：</td>
		<td  width="40%">
			<select name="DataTransfer.dbtype" id="DataTransfer.dbtype" onblur="setDburl()">
				<option value="oracle">Oracle</option>
				<option value="sql server 2000">SQL server 2000</option>
				<option value="sql server 2005">SQL server 2005</option>
				<option value="mysql">MySql</option>
				<option value="db2">DB2</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>数据库连接串：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" size="50" id="DataTransfer.dburl" name="DataTransfer.dburl" value="${dataTransfer.dburl}"  >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>用户名：</td>
		<td   width="40%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="DataTransfer.username" name="DataTransfer.username" value="${dataTransfer.username}"  >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>用户口令：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="DataTransfer.password" name="DataTransfer.password" value="${dataTransfer.password}"  >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >模块选项：</td>
		<td   width="40%" >
			CRM:<input type="checkbox" id="modular1" value="YCRM" />
			信达项目三期:<input type="checkbox" id="modular2" value="YXD3Q" />
			信达项目三期:<input type="checkbox" id="modular3" value="YXDSS" />
			拙雅项目:<input type="checkbox" id="modular4" value="YJYPM" />
		</td>
	</tr>
	<input type="hidden" id="DataTransfer.datatransferid" name="DataTransfer.datatransferid" value="${DataTransfer.datatransferid}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>


<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

/**
 * 保存 
 */
function _save()
{
	if(isCreateCopy){
		document.getElementById("DataTransfer.datatransferid").value = "";
	}
	var selModuled="";
	if($('modular1').checked )
	{
		selModuled = selModuled + $('modular1').value  + "," ;
	}
	
	if($('modular2').checked )
	{
		selModuled = selModuled+ $('modular2').value  + "," ;
	}
	if($('modular3').checked )
	{
		selModuled = selModuled+ $('modular3').value  + "," ;
	}
	if($('modular4').checked )
	{
		selModuled = selModuled+ $('modular4').value  + "," ;
	}
	if(selModuled)
	{
		selModuled = selModuled.substring(0,selModuled.length-1);
	}
	var param = Form.serialize('mainForm')+'&DataTransfer.modular='+selModuled;	
	//var param = Form.serialize('mainForm');	
    new AjaxEngine(contextPath + '/platform/dpframework/dataTransfers/dataTransferController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callBack:customCallBackHandle});
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var id=result.id;
	document.getElementById("DataTransfer.datatransferid").value = id;
	isCreateCopy = false;	

}
          

/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}


/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '数据传输记录表信息',
				            		layout:'fit',
				            		autoScroll: true,
				            		contentEl:'div_center'
						}

				  ]
				 }]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
					{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
					'-'],  
			renderTo:"div_toolbar"
	});
	function setDburl(){
		var sl = $('DataTransfer.dbtype').value;
		if(sl=='oracle')
			$('DataTransfer.dburl').value='jdbc:oracle:thin:@';
		else if (sl=='sql server 2000')
			$('DataTransfer.dburl').value='jdbc:microsoft:sqlserver://';
		else if (sl=='sql server 2005')
			$('DataTransfer.dburl').value='jdbc:sqlserver://';
		else if (sl=='mysql')
			$('DataTransfer.dburl').value='jdbc:mysql://';
	}
//});
</script>
