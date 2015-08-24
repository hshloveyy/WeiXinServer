<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="javascript">
<%String message = (String) request.getAttribute("message");
String messageLevel = (String) request.getAttribute("messageLevel");
String icon ;
request.setAttribute("message", message);
			/**if (SysMsgConstants.INFO.equals(messageLevel)) {
				icon = "INFO";
			} else if (SysMsgConstants.WARN.equals(messageLevel)) {
				icon = "WARN";
			} else {

				icon = "ERROR";
			}**/
%>

var message = '${message}';
    message = message.replace(/(\r\n|\n)/g,"<br>");
	//showError(message);
Ext.onReady(function(){
	var title;
	var icon;
	var messageLevel = '<%=messageLevel%>';
	//alert(messageLevel + message);
	if (messageLevel=='I'){
		title = "系统消息";
	    if(sys)
	    {
	        title = sys.info;
	    }
		icon = Ext.MessageBox.INFO;
	} else if (messageLevel=='W') {
		title = "系统警告";
	    if(sys)
	    {
	        title = sys.warn;
	    }
		icon = Ext.MessageBox.WARNING;
	} else if(messageLevel=='E'){
		title = "系统错误";
	    if(sys)
	    {
	        title = sys.error;
	    }
		icon = Ext.MessageBox.ERROR;
	}
	else {
		title = "系统错误";
	    if(sys)
	    {
	        title = sys.error;
	    }
		icon = Ext.MessageBox.ERROR;
	}

	_getMainFrame().Ext.MessageBox.show({
			title:title,
			msg: message,
			buttons: {yes:'<%=okText%>'},
			icon: icon,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				}
			}
		});
});

</script>
</head>
<body>
</body>
</html>
