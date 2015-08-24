<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>权限转移给</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_mainFrom" name="div_mainFrom" class="search" >
<form id="mainFrom" action="" name="mainFrom">
<table width="99%" height="320" align="center" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="20%" ></td>
		<td colspan="1" >
		<input type="hidden" name="userId" value="" /> 
		<input type="hidden" name="userName" value="" /> 
		<input type="hidden" name="uuIds" value="${uuIds}" /> 
		<input type="hidden" name="menuIdLst" value="${menuIdLst}" /> 
		<input type="hidden" name="bpdefids" value="${bpdefids}" />
		<BR></td>
	</tr>
	<tr>
		<td align="right" width="20%" >用户名:</td>
		<td width="30%" >
			<div id="divUserSerachHelp" ></div>
	    </td>
	</tr>
	<tr>
		<td align="right" width="20%" >授权开始:</td>
		<td width="30%" >
		   <div id="div_authstarttime"></div>
	    </td>
	</tr>
	<tr>
		<td align="right" width="20%" >授权结束:</td>
		<td width="30%" >
			<div id="div_authendtime"></div>
	    </td>
	</tr>
	<tr>
		<td align="right" width="20%" ></td>
		<td width="30%" >
			<input type="hidden" name="authtype" value="" disabled="disabled"/>
	    </td>
	</tr>
	<tr>
		<td align="right" width="20%" ></td>
		<td colspan="1" ><BR><BR><BR></td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="170">
		</td>
	</tr>	
</table>
</form>
</div>
<div id="div_east"></div>
<div id="div_west"></div>
</body>
</html>
<fisc:searchHelp boName="" searchType="field"  searchHelpName="YHUSER"  boProperty="" hiddenName="userId"  divId="divUserSerachHelp" displayField="USERNAME" valueField="USERID"  callBackHandler="userSerachHelpCallBack"></fisc:searchHelp>
<fisc:calendar applyTo=""  divId="div_authstarttime" fieldName="authstarttime" defaultValue=""></fisc:calendar>
<fisc:calendar applyTo="" divId="div_authendtime" fieldName="authendtime" defaultValue=""></fisc:calendar>

<script type="text/javascript" defer="defer">
//操作类别，菜单(menu)、角色(role)、方法(method)、流程(workflow) 审批授权类型:
var operType = '${operType}';
if(operType == 'workflow')
{
    Ext.getDom('authtype').disabled = false;
}

/**
 * 用户搜索帮助回调函数
 */
function userSerachHelpCallBack(sjson){
	Ext.getDom("userId").value = sjson.USERID;
	Ext.getDom("userName").value = sjson.USERNAME;
}

function removeDash(v,m){
	var s = v.split(m);
	var t='';
	for(var i=0;i<s.size();i++){
		t = t+s[i];
	}
	return t;
}

Ext.onReady(function(){

   	//var nodeType = '${favorites.nodeType}';
   	
    /**
    * 确认转移权限
    */
	function _saveAuthRemove(){
		var userId = Ext.getDom("userId").value;
		if (userId == '' || userId == null){
			_getMainFrame().showInfo('[用户名]为必填项！');
			return;				
		}
		var authstarttime = Ext.getDom("authstarttime").value;
		if (authstarttime == '' || authstarttime == null){
			_getMainFrame().showInfo('[授权开始]为必填项！');
			return;				
		}
		var authendtime = Ext.getDom("authendtime").value;
		if (authendtime == '' || authendtime == null){
			_getMainFrame().showInfo('[授权结束]为必填项！');
			return;				
		}
		var result = parseInt(removeDash(authstarttime,'-'))-parseInt(removeDash(authendtime,'-'));
		if(result>0){
			_getMainFrame().showInfo('[授权结束]要大于[授权开始]！');
			return;
		}
		var param = Form.serialize('mainFrom') ;
		
		//alert(param + operType);
		param += "&action=_saveAuthRemove&operType=" + operType;
		new AjaxEngine('<%=request.getContextPath()%>/index/indexMainController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
		   
 	 }
	  

	//整体布局
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'center',
			layout:'border',
			buttonAlign:'center',
			border:false,
			items:[{
					region:'north',
					height:0,
					border:false,
					contentEl:'div_toolbar'
				},{
					region:'center',
		            layout:'fit',
		            border:false,
		            height:160,
		            contentEl:'div_mainFrom'
				}],
		    buttons:[{
		        	text:'确定',
		        	id:'btn_Save',
		        	name:'btn_Save',
		        	minWidth: 20,
		        	handler:_saveAuthRemove
		        },
		        {
		        	text:'取消',
		        	id:'btn_cancel',
		        	minWidth: 20,
		        	name:'btn_cancel',
		        	handler:_close
		        }]
		},{
			region:'east',
			width:0,
			border:false,
			contentEl:'div_east'
		},{
			region:'west',
			width:0,
			border:false,
			contentEl:'div_west'				
		}]
	});
});

//关闭窗体
function _close(){
	Ext.Msg.show({
   		title:'系统提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				_getMainFrame().ExtModalWindowUtil.markUpdated();
				_getMainFrame().ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

/**
 * 确认转移权限回调函数
 */
function customCallBackHandle(transport)
{
  //alert(transport.responseText);
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var msg = responseUtil.getMessage();
  var msgType = responseUtil.getMessageType();
  if(msgType!="error")
  {   
	  _getMainFrame().showInfo(msg);	
	  _getMainFrame().ExtModalWindowUtil.markUpdated();
	  _getMainFrame().ExtModalWindowUtil.close();
   }   
}
</script>

