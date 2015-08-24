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
function _reply(userMessageId,sender,senderName,addresseeId){
	_readed(addresseeId);
	if(typeof(userMessageId)=='object'){
		var mygrid = gridDiv_grid;
		if (mygrid.selModel.hasSelection() > 0){
			if (mygrid.selModel.hasSelection() == 1){
				var records = mygrid.selModel.getSelections();
				var id = records[0].data.userMessageId;
				var _sender = records[0].data.senderId;
				//var _senderName = escape(escape(records[0].data.sender));
				var _senderName = records[0].data.sender;
				var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_create&type=${type}&userMessageId='
					+id+"&addressee="+_sender+"&addresseeName="+_senderName+"&operationType=reply";
				//alert(url);
				_getMainFrame().maintabs.addPanel('回复消息',gridDiv_grid,url);
			}else{
				_getMainFrame().showInfo('只能选择一个要回复的消息！');
			}
		}else{
			_getMainFrame().showInfo('请选择要回复的消息！');
		}
	}else{
		var _senderName = escape(escape(senderName));
		var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_create&type=${type}&userMessageId='
			+userMessageId+"&addressee="+sender+"&addresseeName="+_senderName+"&operationType=reply";
		//alert(url);
		_getMainFrame().maintabs.addPanel('回复消息',gridDiv_grid,url);
	}
}

function _create(userMessageId){
	var url = '/xmdp/basicApp/workbench/userMessage/userMessageController.spr?action=_create&type=unsend';
	_getMainFrame().maintabs.addPanel('创建邮件',gridDiv_grid,url);	
}

function _view(userMessageId,addresseeId){
	_readed(addresseeId);
	var url = '<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?action=_view&type=${type}&userMessageId='
		+userMessageId+'&operationType=view';
	_getMainFrame().maintabs.addPanel('查看消息',gridDiv_grid,url);
}

function _delete(userMessageId,addresseeId){
	if(typeof(userMessageId)=="undefined")
		userMessageId = "";
	if(typeof(addresseeId)=="undefined")
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

function _manager(value,metadata,record){
	//alert("value = "+value+" record = "+Ext.util.JSON.encode(record.data));
	return	'<a href="#" onclick="_view(\''+record.data.userMessageId+'\',\''+record.data.addresseeId+
	'\');" style="color:green">查看</a> <a href="#" onclick="_reply(\''+record.data.userMessageId+'\',\''+record.data.senderId+
	'\',\''+record.data.sender+'\',\''+record.data.addresseeId+
	'\');" style="color:green">回复</a> <a href="#" onclick="_delete(\''+record.data.userMessageId+'\',\''+record.data.addresseeId+'\')" style="color:green">删除</a>';

}

function _isReaded(value,metadata,record){
	if(value=='Y'||(record.data.rUserMessageId!=''&&typeof(record.data.rUserMessageId)!=undefined))
		return '是';
	else
		return '<font color="blue">否</font>';
}

function _readed(addresseeId){
	if("readed"=="${type}")
		return false;
	var xmlHttp;
	try{
		// Firefox, Opera 8.0+, Safari
		xmlHttp=new XMLHttpRequest();
	}catch (e){
		// Internet Explorer
		try{
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e){
			try{
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}catch (e){
				_getMainFrame().showInfo("您的浏览器不支持AJAX！");
				return false;
			}
		}
	}
	
	var DataToSend = 'action=_readed&addresseeId='+addresseeId;
	//var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlHttp.open("POST","<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttp.send(DataToSend);
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	reload_gridDiv_grid("");
}
</script>
</html>
<fisc:grid divId="gridDiv" tableName="${tableName}" whereSql="${whereSql}" needAutoLoad="true" 
 pageSize="20" needCheckBox="true" height="460" orderBySql=" sendDate DESC"
 handlerClass="com.infolion.platform.basicApp.sysConsole.workbench.userMessage.web.grid.InboxGrid" ></fisc:grid>