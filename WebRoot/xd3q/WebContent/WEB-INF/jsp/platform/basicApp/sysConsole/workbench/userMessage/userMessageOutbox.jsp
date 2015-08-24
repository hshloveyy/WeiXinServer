<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/commons.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户消息管理</title>
</head>
<body>
<form name="mainForm">
</form>
<div id="gridDiv"></div>
</body>
<script type="text/javascript" defer="defer">
function _send(userMessageId,receiver){
	if(receiver==null||receiver==''){
		_getMainFrame().showInfo('需要有收件人才能发送！');
		return false;
	}
	var param = '?action=_send&userMessageId=' + userMessageId;
	new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function _view(userMessageId){
	var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_view&type=${type}&userMessageId='
		+userMessageId+"&operationType=view";
	_getMainFrame().maintabs.addPanel('查看邮件',gridDiv_grid,url);
}

function _delete(userMessageId,addresseeId){
	if(userMessageId=="undefined")
		userMessageId = "";
	if(addresseeId=="undefined")
		addresseeId = "";
	if(userMessageId||addresseeId){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '是否确定要删除？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var param = '?action=_deletes&addresseeIds='+addresseeId+"&userMessageIds="+userMessageId;
					new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				}
			}
		});
	}
}

function _deletes(){
	var mygrid = gridDiv_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '是否确定要删除？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var addresseeIds = '';
		   				var userMessageIds = '';
		   				var tag = '';
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							addresseeIds += tag + records[i].json.addresseeId;
							userMessageIds += tag + records[i].json.userMessageId;
							tag = ',';
						}
						var param = '?action=_deletes&addresseeIds='+addresseeIds+"&userMessageIds="+userMessageIds;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的记录！');
	}
}

function _copyCreate(userMessageId){
	var mygrid = gridDiv_grid;
	if(mygrid.selModel==null || mygrid.selModel.getSelections()==''){
		_getMainFrame().showInfo('请选择要复制创建的记录！');
		return;
	}
	var records = mygrid.selModel.getSelections();
	if(records.length>1){
		_getMainFrame().showInfo('请选择要复制创建的记录！');
		return;
	}else{
		userMessageId = records[0].json.userMessageId;
		var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_copyCreate&type=${type}&userMessageId='+userMessageId;
		_getMainFrame().maintabs.addPanel('复制创建邮件',gridDiv_grid,url);
	}
}

function _edit(userMessageId){
	var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_create&type=${type}&userMessageId='+userMessageId;
	_getMainFrame().maintabs.addPanel('编辑邮件',gridDiv_grid,url);	
}

function _create(userMessageId){
	var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_create&type=${type}';
	_getMainFrame().maintabs.addPanel('创建邮件',gridDiv_grid,url);	
}

function _manager(value,metadata,record){
	//alert("value = "+value+" record = "+Ext.util.JSON.encode(record.data));
	var type = "${type}";
	if("unsend"==type){
		return	'<a href="#" onclick="_edit(\''+value
			+ '\');" style="color:green">编辑</a> '
			+ '<a href="#" onclick="_delete(\''+record.data.userMessageId
			+'\',\''+record.data.addresseeId+'\')" style="color:green">删除</a>';
	}
	else
		return	'<a href="#" onclick="_view(\''+value
			+ '\');" style="color:green">查看</a> '
			+ '<a href="#" onclick="_delete(\''+record.data.userMessageId
			+'\',\''+record.data.addresseeId+'\')" style="color:green">删除</a>';
}

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	reload_gridDiv_grid("");
}
</script>
</html>
<fisc:grid divId="gridDiv" tableName="${tableName}" whereSql="${whereSql}" needAutoLoad="true" 
 pageSize="20" needCheckBox="true" height="470" orderBySql="${orderBySql}" groupBySql="${groupBySql}"
 handlerClass="com.infolion.platform.basicApp.sysConsole.workbench.userMessage.web.grid.OutboxGrid" ></fisc:grid>