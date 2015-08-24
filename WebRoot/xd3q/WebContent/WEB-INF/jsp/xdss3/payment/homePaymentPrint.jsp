<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.infolion.platform.workflow.engine.domain.ExtendTaskInstance"%>
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
       <tr><td>申请时间：&nbsp;&nbsp;${main.createtime}</td><td align="right">付款单号：${main.paymentno}&nbsp;&nbsp;</td></tr>
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
				<td align="center" width="100"><font color="red">
					<%--start yanghancai 2010-09-25 收款形式转换--%>
				     <c:if test="${main.paymenttype=='01'}">TT</c:if>
				     <c:if test="${main.paymenttype=='02'}">DP</c:if>
				     <c:if test="${main.paymenttype=='03'}">即期信用证</c:if>
				     <c:if test="${main.paymenttype=='04'}">远期信用证</c:if>
				     <c:if test="${main.paymenttype=='05'}">DA</c:if>
				     <c:if test="${main.paymenttype=='0'}">国内信用证</c:if>
				     <c:if test="${main.paymenttype=='07'}">银行/商业承兑汇票</c:if> 				     
				     <c:if test="${main.paymenttype=='08'}">现金</c:if>
				     <c:if test="${main.paymenttype=='09'}">背书</c:if>
				     <c:if test="${main.paymenttype=='10'}">转账</c:if>
				     <c:if test="${main.paymenttype=='11'}">电汇</c:if>
				     <c:if test="${main.paymenttype=='12'}">网银</c:if>
				     <c:if test="${main.paymenttype=='13'}"><font color="red">银行汇票</font></c:if>
				     <%--end yanghancai 2010-09-25 收款形式转换--%>
				     </font>
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="100">付<br>款<br>用<br>途</td>
				<td colspan="7" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;
					${main.text}<br>&nbsp;&nbsp;&nbsp;&nbsp;${main.remark}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">收款单位及名称</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${supplierName}
				</td>
				<td align="center" width="60">开户银行及帐号</td>
				<td align="left" width="200" colspan="3">
					&nbsp;&nbsp;${main.collectbankid}<br>  <%-- yanghancai 2010-09-25收款银行--%>
					&nbsp;&nbsp;${main.collectbankacc}
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">付款金额</td>
				<td align="left" colspan="7">
					&nbsp;&nbsp;${main.factamount}&nbsp;&nbsp;&nbsp;&nbsp;
				    ${main.currency}
				    &nbsp;&nbsp;&nbsp;&nbsp;
				    ( ${payValueBig} )
			</tr>
			
			<%
			   List<ExtendTaskInstance> listTaskIns = (List)request.getAttribute("taskHis");
			   Object printType =  request.getAttribute("printType");
			   for(ExtendTaskInstance ins : listTaskIns){
			   if("1".equals(printType)&&(ins.getTaskName().indexOf("出纳")>-1||ins.getTaskName().indexOf("上海信达诺财务付款")>-1)) break;
			 %>
		    <tr>
		        <td align="center" width="100" height="25" colspan="2"><%=ins.getTaskName()%></td>
				<td align="left" width="100" colspan="2">
					&nbsp;&nbsp;<%=ins.getExaminePerson()%>
				</td>
				<td align="left" width="500" colspan="5">
					&nbsp;&nbsp;<%=ins.getExamine()%>
				</td>
		    </tr>
	        <%} %>
		</table>
		</td>
		</tr>
		</table>
		<%-- yanghancai 2010-09-25 取消财务编号--%><%-- <tr><td>&nbsp;&nbsp;</td><td align="right">财务编号：${main.finumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>--%>
		
</DIV></TD></TR></TABLE>
</body>
<script>
doPrint('1','2100','2970');
<c:if test="${main.paymenttype=='07'}">alert('银行/商业承兑汇票，请确认！');</c:if>
<c:if test="${main.paymenttype=='13'}">alert('银行即期汇票，请确认！');</c:if>
</script>
</html>