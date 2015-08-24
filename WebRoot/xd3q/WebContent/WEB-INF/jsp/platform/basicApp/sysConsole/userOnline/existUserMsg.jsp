<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年08月18日 11点30分33秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>用户会话记录表(SessionLog)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.infolion.platform.dpframework.core.dao.SqlSyntaxHeterogeneous"%>
<%@ page import="com.infolion.platform.dpframework.util.EasyApplicationContextUtils"%>
<%@ page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@ page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@ page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@ page import="com.infolion.platform.basicApp.MsgConstants"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户会话</title>
</head>
<body>
<%
SqlSyntaxHeterogeneous sqlSyntax = (SqlSyntaxHeterogeneous) EasyApplicationContextUtils.getBeanByName("sqlSyntaxHeterogeneous");
request.setAttribute("notEqual" ,sqlSyntax.getNotEqual());

//系统文本
String strOk = LanguageService.getText(LangConstants.SYS_OK);
String strCancel = LanguageService.getText(LangConstants.SYS_CANCEL);
String loginSelect1=LanguageService.getText(LangConstants.Login_Select1);
String loginSelect2=LanguageService.getText(LangConstants.Login_Select2);
String loginSelect3=LanguageService.getText(LangConstants.Login_Select3);

String userName = (String) request.getAttribute("userName");
String client = (String) request.getAttribute("client");

//用户&1已经在客户端&2登录，请选择您需要的操作
String userLoginSelect = SysMsgHelper.getDPMessage(MsgConstants.UserLoginSelect,userName,client);
%>
<div id="div_form"></div>
<div id="div_northForm"></div>
<div id="div_title" class="search"><table>
	<tr>
		<td style="font-weight: bold;"><%=userLoginSelect %>：</td>
	</tr>
</table></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="550" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type="radio" name="action" value="1" id="rad1"/><label for="rad1"> <%=loginSelect1 %>；</label> </td>
		<td></td>
	</tr>
	<tr>
		<td><input type="radio" name="action" value="2" id="rad2" checked="checked"/><label for="rad2"> <%=loginSelect2 %>；</label></td>
		<td></td>
		
	</tr>
	<tr>
		<td><input type="radio" name="action" value="3" id="rad3"/><label for="rad3"> <%=loginSelect3 %></label></td>
		<td></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="button" value="<%=strOk%>" onclick="_confirm()"/> 
		<input type="button" value="<%=strCancel%>" onclick="_logout()"/> 
	</td>
	</tr>
</table>
</form>
</div>

</body>
</html>
<script type="text/javascript" defer="defer">
function _confirm(){
	var   rad   =   document.forms['mainForm'].action;   
	var   len   =   rad.length;   
	var   type;
	for(var   i   =   0;i<len;i++){   
	    if(rad[i].checked == true){   
	    	type=rad[i].value;
	        break;
	    }   
	}
	if(type=='1'){
		cancelAllOnlineUser();
	}else if (type=='2'){
		_goMain();
	}else if (type=='3'){
		_logout();
	}
}
/*
 * 
 */
function cancelAllOnlineUser(){
	var sessionIds='';
	for (var i = 0; i <SessionLog_grid.getStore().getCount(); i++) {
		var record = SessionLog_grid.getStore().getAt(i);  
		sessionIds = sessionIds+record.data.sessionid+',';
	}
	Ext.Ajax.request({
		url: '<%= request.getContextPath() %>/platform/basicApp/sysConsole/userOnline/onlineUserController.spr?action=removeUsersSession&sessionIds='+sessionIds,
		success: function(response, options){
			     var resp = Ext.util.JSON.decode(response.responseText);
				//window.close();
				if(resp.removeSuccessAndDirectToMain)
					parent.window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
					//'<%=request.getContextPath()%>/cognos.jsp';
		}	
	});
	
}
function _goMain(){
	parent.window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
	//'<%=request.getContextPath()%>/cognos.jsp';
}
/*
 * 
 */
function _logout(){
	parent.window.location.href="<%=request.getContextPath()%>/authManage/userLoginController.spr?action=logout";
}
/**
 * 
 */
function _closeForm(){
	window.close();
}
/**
 * EXT 布局
 */
Ext.onReady(function(){
	var form=new Ext.form.FormPanel({
		renderTo:'div_form',
		frame:true,
		baseCls:'x-plain',
		autoHeight:true,
		height:400,
		width:585,
		defaults:{
			msgTarget:'side'
		},
		items:[{
			contentEl:'div_northForm'
		},{
			contentEl:'div_title'
		},{
			contentEl:'div_center'
		}],
        keys:[{
			key:Ext.EventObject.ENTER,  
			fn:_confirm
        },{
			key:Ext.EventObject.SPACE,  
			fn:_confirm
        }]
	});
	form.focus();
	$('rad2').focus();
});
</script>
<fisc:grid divId="div_northForm" boName="SessionLog" editable="false" needOperationColumn="false" height="200"
	needCheckBox="false" needToolbar="false" needAutoLoad="true" defaultCondition="USERNAME='${userName}' and LOGOUTYPE='0' and SESSIONID${notEqual}'${sessionId}'"></fisc:grid>

