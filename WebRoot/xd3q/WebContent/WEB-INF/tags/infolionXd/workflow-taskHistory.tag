<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="businessRecordId" required="true" rtexprvalue="true" description="业务记录id"%>
<%@ attribute name="divId" required="true" rtexprvalue="true"	description="显示DIV"%>
<%@ attribute name="width" required="true" rtexprvalue="true"	description="宽度"%>
<%@ attribute name="customeSQL" required="false" %>
<%@ attribute name="url" required="false" %>
<%@taglib prefix="fisc" tagdir="/WEB-INF/tags/infolionXd" %>
<%
	String sql="";
	if(url!=null && !"".equals(url)){
	
	}
	else if(customeSQL!=null && !"".equals(customeSQL))
		sql = customeSQL;
	else
		sql = "select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id='"+businessRecordId+"' order by TASK_END_TIME desc";
%>

<fisc:grid width="${width}" divId="${divId}" height="300"
	 headers="任务名称,办理部门,经办人,审批结果,审批意见,起始时间,结束时间"
	columns="TASK_NAME,EXAMINE_DEPT_NAME,EXAMINE_PERSON,EXAMINE_RESULT,EXAMINE,TASK_CREATE_TIME,TASK_END_TIME" pageSize="10" 
	sql="<%=sql%>" showMsgColumn="EXAMINE" url="<%=url%>">
	</fisc:grid>