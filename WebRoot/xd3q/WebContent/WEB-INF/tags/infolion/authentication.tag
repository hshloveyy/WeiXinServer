
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%@tag import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgHelper"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%-- 
  - Author(s):黄登辉
  - Date: 2008-12-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:输入框权限控制组件，可根据相应的授权控制text、checkbox、radio、textarea的属性
  -
--%><%@ tag pageEncoding="utf-8"%><%@
 tag import="org.apache.commons.lang.StringUtils"%><%@
 tag import="com.infolion.platform.basicApp.authManage.service.AuthenticationService"%><%@
 tag import="com.infolion.platform.basicApp.authManage.domain.OperationType"%><%@
 attribute name="sourceName" required="true" description="业务对象资源名，格式为boName.fieldName"%><%@
 attribute name="type" required="false" description="组件的类型，需要对checkbox或radio或textarea进行控制的需要输入相应的类型"%><%@
 attribute name="isNumber" required="false" description="是否是只能输入数字，true为是，integer为只能是整数，其它为否"%><%@
 attribute name="isExt" required="false" description="是否是在EXT代码中加控制，true为是，其它为否" %>
 <%@attribute name="taskId" required="false" description="工作流taskId" %>
 <%@attribute name="nodeId" required="false" description="工作流nodeId" %>
 <%
 String parameter[] = sourceName.split("\\.");
 String boName = parameter[0];
 String fieldName = parameter[1];
String operationType = AuthenticationService.isAttributePermission(boName+"."+fieldName,nodeId).getOperationType();
String onChange = "";//onchange事件输出
String onblur = "";//onblur事件输出
String readonly = "";//记录readonly状态
String visible = "true";//记录是否可见状态
String style = "";//记录用来控制是否可见的style属性
StringBuffer output = new StringBuffer(128);//通用输出
//文本
//不为空
String txtNotBeNull=LanguageService.textConcat("",SysMsgHelper.getDPMessage(SysMsgConstants.NotBeNull));
//不是合法数字
String txtIllegalNumber=LanguageService.textConcat("",SysMsgHelper.getDPMessage(SysMsgConstants.IllegalNumber));
//不是整数
String txtNotBeInteger=LanguageService.textConcat("",SysMsgHelper.getDPMessage(SysMsgConstants.NotBeInteger));
//该字段只读
String txtFieldReadOnly=SysMsgHelper.getDPMessage(SysMsgConstants.FieldReadOnly);
if(StringUtils.isBlank(type))
	type="";
if(OperationType.READONLY.equals(operationType)){
	readonly = "readonly";
}else if(OperationType.UNVISIABLE.equals(operationType)){
	style = "visibility: hidden;";
	visible = "false";
}else if(OperationType.REQUIREDINPUT.equals(operationType)){
	if(type.toLowerCase().equals("checkbox")||type.toLowerCase().equals("radio"))
		output.append(" onclick=\"function getValue(name){var myvalue = '';var obj = document.getElementsByName(name);if(obj.length==null){myvalue = obj.value;}for(i=0;i<obj.length;i++){if(obj[i].checked){myvalue = obj[i].value;break;}}if(Utils.isEmpty(myvalue)){alert(name+'").append(txtNotBeNull).append("');return false;}}; return getValue(this.name);\" ");	
	else
		onChange = "javascript:if(Utils.isEmpty(this.value)){alert(this.name+'"+txtNotBeNull+"');return false;} ";
	onblur = onChange;
}else if(OperationType.EDIEABLE.equals(operationType)){
	readonly = "";
}
if(StringUtils.isNotBlank(isExt)&&"true".equals(isExt.toLowerCase())){
	if(readonly.equals("readonly"))
		output.append("readonly:true,\r\n");
	if(visible.equals("false"))
		output.append("hidden:true,\r\n");
	if(StringUtils.isNotBlank(onChange))
		output.append("change:Utils.isEmpty,\r\n");
}else{
	//output += "style=\""+style+"\" onChange=\"javascript:if(Utils.isEmpty(this.value)){alert(this.name+'不为空');return false;}\"";
	if(StringUtils.isNotBlank(isNumber)&&"true".equals(isNumber.toLowerCase())){
		onChange += "if(!(Utils.isInteger(this.value)||Utils.isFloatValue(this.value))){alert(this.value+'"+txtIllegalNumber+"');this.focus();return false;} ";
	}
	if(StringUtils.isNotBlank(isNumber)&&"integer".equals(isNumber.toLowerCase())){
		onChange += "if(!Utils.isInteger(this.value)){alert(this.value+'"+txtNotBeInteger+"');this.focus();return false;} ";
	}
	output .append("style=\"").append(style).append("\"");
	if(StringUtils.isNotBlank(readonly)){
		if(type.toLowerCase().equals("checkbox"))
			output.append(" onclick =\"this.checked=!this.checked;\"");
		else if(type.toLowerCase().equals("radio"))
			output.append(" onkeydown =\"return false;\" onmousedown=\"alert('").append(txtFieldReadOnly).append("');return false;\"");
		else
			output.append(" readonly=\"").append(readonly).append("\"");
	}
	output.append(" onChange=\"").append(onChange).append("\"");
	output.append(" onblur=\"").append(onblur).append("\"");
}
%><%=output%>