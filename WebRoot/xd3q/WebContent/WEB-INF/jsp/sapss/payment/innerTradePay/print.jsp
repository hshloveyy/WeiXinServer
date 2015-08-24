<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸货款打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>

</head>
<body>
<TABLE align="center">
<TR><TD></TD><TD><DIV id="page1">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="2"><br><br><br><br><br><br></td></tr>
       <tr><td align="center" colspan="2"><font size="6">信达股份有限公司付款审批单</font></td></tr>
       <tr><td>申请时间：&nbsp;&nbsp;${main.applyTime}</td><td align="right">财务编号：${main.financeNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="2">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="60" height="25">申请部门</td>
				<td align="center" width="100">
					${main.deptName}
				</td>
				<td align="center" width="60">申请人</td>
				<td align="center" width="100">
					${main.creatorName}
				</td>
				<td align="center" width="60">立项或合同号</td>
				<td align="center" width="100">${contractNoStr}</td>
				<td align="center" width="60">付款形式</td>
				<td align="center" width="100">
				<c:if test="${main.payType=='1'}">
				     <c:if test="${main.payMethod=='1'}">国内信用证</c:if>
				     <c:if test="${main.payMethod=='2'}">现金</c:if>
				     <c:if test="${main.payMethod=='3'}">背书</c:if>
				     <c:if test="${main.payMethod=='4'}">转账</c:if>
				     <c:if test="${main.payMethod=='5'}">电汇</c:if>
				     <c:if test="${main.payMethod=='6'}">网银</c:if>
				     <c:if test="${main.payMethod=='7'}"><font color="red">银行/商业承兑汇票</font></c:if>
				     <c:if test="${main.payMethod=='9'}"><font color="red">银行即期汇票</font></c:if>
				</c:if>
				<c:if test="${main.payType=='2'}">
				     <c:if test="${main.payMethod=='1'}">普非货款</c:if>
				     <c:if test="${main.payMethod=='2'}">期货保证金</c:if>
				     <c:if test="${main.payMethod=='3'}">非货款/背书</c:if>
				     <c:if test="${main.payMethod=='4'}">非货款/转账</c:if>
				     <c:if test="${main.payMethod=='5'}">非货款/电汇</c:if>
				     <c:if test="${main.payMethod=='6'}">非货款/网银</c:if>
				     <c:if test="${main.payMethod=='7'}">非货款/现金</c:if>
				</c:if>
				<c:if test="${main.payType=='3'}">
				     进口预付款TT
				</c:if>
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="100">付<br>款<br>用<br>途</td>
				<td colspan="7" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;
					${main.payUse}<br>&nbsp;&nbsp;&nbsp;&nbsp;${main.cmd}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">收款单位及名称</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.recBank}
				</td>
				<td align="center" width="60">开户银行及帐号</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.openAccountBank}<br>
					&nbsp;&nbsp;${main.openAccountNo}
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
<c:if test="${main.payType=='1'}">
<c:if test="${main.payMethod=='7'}">alert('银行/商业承兑汇票，请确认！');</c:if>
<c:if test="${main.payMethod=='9'}">alert('银行即期汇票，请确认！');</c:if>
</c:if>
</script>
</html>