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
	<div id="gridDiv"></div>
</form>
</body>
<script type="text/javascript" defer="defer">

function _view(userMessageId){
	var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_show&type=${type}&userMessageId='
		+userMessageId+'&operationType=view';
	_getMainFrame().maintabs.addPanel('查看被删除的用户消息',gridDiv_grid,url);
}

function _delete(trashBoxId){
	 alert("trashBoxId:" + trashBoxId);
	if(trashBoxId=="undefined")
		_getMainFrame().showError("出错了，trashBoxId不能为空！");
	if(trashBoxId){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '是否确定要删除？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var param = '?action=_deletes&trashBoxIds='+trashBoxId;
					new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/trashBoxController.spr?', 
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
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/trashBoxController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的记录！');
	}
}

function _restore(trashBoxId){
	if(trashBoxId){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '是否确定还原这些消息？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var param = '?action=_restore&trashBoxIds='+trashBoxId;
					new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/trashBoxController.spr?', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				}
			}
		});
	}
}

function _restores(){
	var mygrid = gridDiv_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '是否确定还原这些消息？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';		
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							idList += tag + records[i].json.trashBoxId;
							tag = ',';
						}
						var param = '?action=_restore&trashBoxIds='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/trashBoxController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的记录！');
	}
}

function _manager(value,metadata,record){
	//alert("value = "+value+" record = "+Ext.util.JSON.encode(record.data));
	return	'<a href="#" onclick="_view(\''+record.data.trashBoxId+'\');" style="color:green">查看</a>'
		+' <a href="#" onclick="_restore(\''+value+'\');" style="color:green">还原</a>'
		+' <a href="#" onclick="_delete(\''+record.data.trashBoxId+'\');" style="color:green">删除</a>';
}

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	reload_gridDiv_grid();
}

</script>
</html>
<fisc:grid divId="gridDiv" tableName="${tableName}" whereSql="${whereSql}" needAutoLoad="true" 
 pageSize="20" needCheckBox="true" height="460" orderBySql=" deletedTime desc"
 handlerClass="com.infolion.platform.basicApp.sysConsole.workbench.userMessage.web.grid.TrashboxGrid" ></fisc:grid>