<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="java.util.List"%>
<%@ tag	import="com.infolion.platform.component.workflow.WorkflowService"%>
<%@ tag	import="com.infolion.platform.component.workflow.ext.TaskInstanceContext"%>
<%@ tag	import="com.infolion.platform.util.DateUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="taskInstanceId" required="true" rtexprvalue="true"	description="任务实例id"%>
<%
	TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskInstanceId);
	List transitions = taskInstanceContext.getTransitions();
	jspContext.setAttribute("transitions", transitions);
	jspContext.setAttribute("taskInstanceContext", taskInstanceContext);
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("currentTime", DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
%>
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
		<!-- tr>
				<td colspan="4" align="left"><h3>流程办理</h3></td>
		</tr-->
		<tr>
				<td align="right">当前状态：</td>
				<td align="left">${taskInstanceContext.taskName}</td>
				<td align="right">当前办理人：</td>
				<td align="left">${taskInstanceContext.currentActor}</td>
		</tr>
		<tr>
				<td align="right">下一步操作:</td>
				<td align="left"><select id="workflowLeaveTransitionName" name="workflowLeaveTransitionName" onmousewheel="javascript:event.returnValue=false;">
							<c:forEach items="${transitions}" var="transition">
								<option value="${transition.transitionName }">${transition.transitionName }</option>							
							</c:forEach>
							</select></td>
				<td align="right">处理时间：</td>
				<td align="left">${currentTime }</td>
		</tr>
		<tr>				
				<td align="right" width="70">审批意见：</td>
				<td align="left" colspan="3"><textarea rows="2" id="workflowExamine" cols="80" name="workflowExamine"  style="width:90%;overflow-y:visible;word-break:break-all;">${fn:indexOf(taskInstanceContext.taskName,"报备")>=0?"确认":"同意"}</textarea></td>
		</tr>
		<input id="workflowCurrentTaskName" type="hidden" name="workflowCurrentTaskName" value="${taskInstanceContext.taskName}">
</table>