<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="java.util.List"%>
<%@ tag	import="com.infolion.platform.workflow.engine.WorkflowService"%>
<%@ tag	import="com.infolion.platform.workflow.engine.domain.TaskInstanceContext"%>
<%@ tag	import="com.infolion.platform.dpframework.util.DateUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="taskInstanceId" required="true" rtexprvalue="true"	description="任务实例id"%>
<%
	TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskInstanceId);
	List transitions = taskInstanceContext.getTransitions();
	jspContext.setAttribute("transitions", transitions);
	jspContext.setAttribute("taskInstanceContext", taskInstanceContext);
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("currentTime", DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
%>

<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%><form name="workflowForm" id="workflowForm">
<script type="text/javascript">
	function replaceSpecialChar(e){
		Ext.getDom('workflowExamine').value = Utils.replaceSpecialCharacter(e.value);
	}
</script>
<table width="100%" border="0" cellpadding="4" cellspacing="1">
		<tr>
				<td width="15%" align="right"><%=LanguageService.getText(LangConstants.SYS_CURRENT_STATE) %>：
				</td>
				<td width="30%" align="left"><input type="text" class="inputText" readonly="readonly" value="${taskInstanceContext.taskName}"></td>
				<td width="15%" align="right"><%=LanguageService.getText(LangConstants.SYS_CURRENT_HANDING) %>：</td>
				<td width="40%" align="left"><input type="text" id="taskInstanceContext.currentActor" class="inputText" readonly="readonly" value="${taskInstanceContext.currentActor}"></td>
		</tr>
		<tr>
				<td align="right" width="15%"><%=LanguageService.getText(LangConstants.SYS_NEXT_OPERATE) %>：</td>
				<td align="left" width="30%"><select id="workflowLeaveTransitionName" name="workflowLeaveTransitionName" style="width: 203px">
							<c:forEach items="${transitions}" var="transition">
								<option value="${transition.transitionName }">${transition.transitionName }</option>							
							</c:forEach>
							</select></td>
				<td align="right" width="15%"><%=LanguageService.getText(LangConstants.SYS_PROCESS_TIME) %>：</td>
				<td align="left" width="40%"><input type="text"  class="inputText" readonly="readonly" value="${currentTime }"></td>
		</tr>
		<tr>
				<td align="right" width="15%"><%=LanguageService.getText(LangConstants.SYS_APPROVE_VIEW) %>：</td>
				<td align="left" colspan="3" width="85%"><textarea id="workflowExamine" name="workflowExamine" onchange="replaceSpecialChar(this)">同意</textarea></td>
		</tr>
		<input name="workflowTaskId" value="${taskInstanceId}" type="hidden" />
		<input id="workflowCurrentTaskName" type="hidden" name="workflowCurrentTaskName" value="${taskInstanceContext.taskName}">
</table>
</form>