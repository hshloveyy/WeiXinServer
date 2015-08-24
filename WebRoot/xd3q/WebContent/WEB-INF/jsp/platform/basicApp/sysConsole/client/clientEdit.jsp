<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年07月21日 19点32分55秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>客户端信息(Client)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户端信息增加页面</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<input type="hidden" id="Client.clientId" name="Client.clientId" value="${client.clientId}">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">客户端编号：</td>
		<td width="30%" >
			<input class="inputText"  type="text" id="Client.clientNo" name="Client.clientNo" value="${client.clientNo}">
		</td>
		<td width="15%" align="right">客户端描述：</td>
		<td width="40%" >
			<input class="inputText" type="text" id="Client.clientDesc" name="Client.clientDesc" value="${client.clientDesc}">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">城市：</td>
		<td width="30%" >
			<input class="inputText" type="text" id="Client.city" name="Client.city" value="${client.city}">
		</td>
		<td width="15%" align="right">客户端类型：</td>
		<td width="40%" >
			<div id="div_clientType"></div>
		</td>
	</tr>

</table>
</form>
</div>

<div id="div_east"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
	//应用的上下文路径，作为全局变量供js使用
	var contextPath = '<%=request.getContextPath()%>';

	/**
	 * 保存 
	 */
	function _save() {
		var param = Form.serialize('mainForm');
		new AjaxEngine(
				contextPath + '/basicApp/client/clientController.spr?action=_saveOrUpdate',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callBack : customCallBackHandle
				});
	}

	/**
	 * 操作成功后的回调动作
	 */
	function customCallBackHandle(transport) {
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var id = responseUtil.getCustomField('coustom');
		document.getElementById("Client.clientId").value = id;
	}

	/**
	 * 取消
	 */
	function _cancel() {
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}

	/**
	 * EXT 布局
	 */
	//Ext.onReady(function(){
	var viewport = new Ext.Viewport( {
		layout : "border",
		border:false,
		items : [ {
			region : 'center',
			layout : 'border',
			border:false,
			border:false,
			items : [ {
				region : 'north',
				layout : 'fit',
				height : 25,
				border:false,
				contentEl : 'div_toolbar'
			}, {
				region : 'center',
				layout : 'anchor',
				//height : 400,
				//autoScroll : true,
				items : [ {
					title : '客户端信息',
					layout : 'fit',
					border:false,
					//height : 400,
					anchor : '10',
					border:false,
					contentEl : 'div_center'
				} ]
			} ]
		} ]
	});
	var toolbars = new Ext.Toolbar( {
		items : [ '-', '->', '-',
		{
			id : '_save',
			text : '保存',
			handler : _save,
			iconCls : 'icon-table-save',
			disabled : ${buttonDisabled}
		}, '-',
		{
			id : '_cancel',
			text : '取消',
			handler : _cancel,
			iconCls : 'icon-undo',
			disabled : ${buttonDisabled}
		}, '-', {
			text : ' '
		} ],
		renderTo : "div_toolbar"
	});

	//});
</script>
<fisc:dictionary boName="Client" boProperty="clientType" dictionaryName="YDCLIENTTYPE" divId="div_clientType" value="${client.clientType}" isNeedAuth="false" ></fisc:dictionary>
