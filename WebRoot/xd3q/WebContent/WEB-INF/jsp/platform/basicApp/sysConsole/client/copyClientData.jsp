<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/commons.jsp"%>
<title>客户端信息复制页面</title>
</head>
<body>
<div id="div_center" class="search">
<form id="mainForm" name="mainForm">
<table width="600" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">数据来源客户端：</td>
		<td>
			<input type="text" id="fromClientDesc" name="fromClientDesc" value="${client.clientDesc}" readonly="readonly">
			<input type="hidden" id="fromClientNo" name="fromClientNo" value="${client.clientNo}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td align="right">请选择数据复制方案：</td>
		<td>
			<div id="div_clientCopyType"></div>
		</td>
	</tr>
	<tr>
		<td align="right">请选择目标客户端：</td>
		<td>
			<div id="div_client"></div>
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_toolbar"></div>
</body>
<script type="text/javascript" defer="defer">
/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
	if (div_client_sh.getValue()){
		var param = "&clientNo=${client.clientNo}&toClientNo="+div_client_sh.getValue();
		//alert(param);
		new AjaxEngine('<%= request.getContextPath() %>/basicApp/client/clientController.spr?action=_copyClientData', 
				{method:"post", parameters: param, onComplete: callBackHandle});
	}else{
		_getMainFrame().showInfo('请选择需要进行【客户端信息复制创建】的客户端！');
	}	
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					height:28
				},{
					region:'center',
					contentEl:'div_center'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-','->','-',
				{id:'_search',text:'复制',handler:_copyCreate,iconCls:'icon-table-save'},'-',
				{id:'_cls',text:'取消',handler:_cancel,iconCls:'icon-undo'},
				'-',{text:' '}
				],
		renderTo:"div_toolbar"
	});
});

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
</script>
<fisc:dictionary hiddenName="clientCopyType" dictionaryName="YDCLIENTCOPYTYPE" divId="div_clientCopyType" isNeedAuth="false"></fisc:dictionary>
<fisc:searchHelp divId="div_client" boName="Client" boProperty="clientNo"></fisc:searchHelp>
</html>