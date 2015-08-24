<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保证金退回打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>

</head>
<body>
<TABLE align="center">
<TR><TD></TD><TD><DIV id="page1">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="2"><br><br><br><br><br><br></td></tr>
       <tr><td align="center" colspan="2"><font size="6">信达股份有限公司保证金退回审批单</font></td></tr>
       <tr><td>申请时间：&nbsp;&nbsp;${main.applyDate}</td><td align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="2">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="150" colspan="2" height="25">申请部门</td>
				<td align="left" width="200" colspan="2">
					${deptName}
				</td>
				<td align="center" width="150" colspan="2" >申请人</td>
				<td align="left" width="200" colspan="2" >
					${main.applyer}
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2" height="25">申请金额</td>
				<td align="left" colspan="6">
					${main.applySun} &nbsp;&nbsp;&nbsp;&nbsp;(${payBig })
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2" height="25">申请期货账户</td>
				<td align="left" colspan="6">
					${main.applyAccount}
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2" height="25">收款账户</td>
				<td align="left" colspan="6">
					${main.receiptAccount}
				</td>
			</tr>
			<tr>
				<td align="center" height="50" colspan="2">备注</td>
				<td colspan="6" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;${main.cmd}
				</td>
			</tr>
		<c:forEach items="${taskHis}" var="task">
		    <tr>
		        <td align="center" width="150" height="25" colspan="2">${task.taskName}</td>
				<td align="left" width="100" colspan="2">
					&nbsp;&nbsp;${task.examinePerson}
				</td>
				<td align="left" width="500" colspan="4">
					&nbsp;&nbsp;${task.examine}
				</td>
		    </tr>
		</c:forEach>
		
		</table>
		</td>
		</tr>
		</table>
</DIV></TD></TR></TABLE>
</body>
<script>
doPrint('1','2100','2970');
</script>
</html>