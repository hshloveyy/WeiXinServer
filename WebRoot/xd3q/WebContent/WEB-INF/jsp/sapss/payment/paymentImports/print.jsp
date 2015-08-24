<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进口货款打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>

</head>
<body>
<TABLE align="center">
<TR><TD></TD><TD><DIV id="page1">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="2"><br><br><br><br><br><br></td></tr>
       <tr><td align="center" colspan="2"><font size="6">信达股份有限公司进口付款审批单</font></td></tr>
       <tr><td colspan="2">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="60" height="25">申请部门</td>
				<td align="center" width="100">
					${deptName}
				</td>
				<td align="center" width="60">申请人</td>
				<td align="center" width="100">
					${creator}
				</td>
				<td align="center" width="60">立项或合同号</td>
				<td align="center" width="100">${pick.contractNo}</td>
				<td align="center" width="60">付款形式</td>
				<td align="center" width="100">
				     <c:if test="${main.tradeType=='sight_lc'}">
				          既期信用证
				         <c:if test="${main.payType=='1'}">
				           普通付款
				         </c:if>
				         <c:if test="${main.payType=='2'}">
				           押汇付款
				         </c:if>
				     </c:if>
				     <c:if test="${main.tradeType=='dp'}">
				        DP
				        <c:if test="${main.payType=='1'}">
				           普通付款
				         </c:if>
				         <c:if test="${main.payType=='2'}">
				           押汇付款
				         </c:if>
				     </c:if>
				     <c:if test="${main.tradeType=='tt'}">
				         TT
				         <c:if test="${main.payType=='1'}">
				           普通付款
				         </c:if>
				         <c:if test="${main.payType=='2'}">
				           押汇付款
				         </c:if>
				     </c:if>
				     <c:if test="${main.tradeType=='usance_lc'}">远期信用证承兑付款</c:if>
				     <c:if test="${main.tradeType=='da'}">DA承兑付款</c:if>
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="100">付<br>款<br>用<br>途</td>
				<td colspan="7" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;
					${main.payUse}<br>&nbsp;&nbsp;&nbsp;&nbsp;${main.cmd}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">信用证号</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${pick.lcNo}
				</td>
				<td align="center" width="60">付款日</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.payTime}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">到单号</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.pickListNo}
				</td>
				<td align="center" width="60">收款银行及帐号</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.payBank}<br>
					&nbsp;&nbsp;${main.payAccount}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">付款金额</td>
				<td align="left" colspan="7">
					&nbsp;&nbsp;${payValue}&nbsp;&nbsp;&nbsp;&nbsp;
				    ${main.currency}
				    &nbsp;&nbsp;&nbsp;&nbsp;
				    ( ${payValueBig } )
			</tr>
		<c:forEach items="${taskHis}" var="task">
		    <tr>
		        <td align="center" width="100" height="25" colspan="2">${task.taskName}</td>
				<td align="left" width="100" colspan="2">
					&nbsp;&nbsp;${task.examinePerson}
				</td>
				<td align="left" width="500" colspan="5">
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