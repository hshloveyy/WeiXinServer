<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.sapss.account.domain.TInnerTransferInfo"%>
<%@ page import="com.infolion.sapss.account.domain.TInnerTransferDetail"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内部划拨打印页面</title>
<script language="javascript" src="/js/printer/printer.js"></script>
<%
TInnerTransferInfo main = (TInnerTransferInfo)request.getAttribute("main");
List details = (List)request.getAttribute("details");
int len = 0;
%>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<style type="text/css">
<!--
.style1 {
	font-size: medium;
	font-weight: bold;
	line-height: 150%;
}
-->
</style>
</head>
<body>
<table width="550" align="center" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF"><tr><td>
	<div id="page1">
	<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
	  <tr>
		<td height="44" align="center">&nbsp;</td>
	  </tr>
	  <tr>
		<td height="40" align="center" valign="middle" nowrap>
			<span class="style1">内部资金划拨单</span>
		</td>
	  </tr>
	  <tr>
		<td height="20">
			申请时间:<%=main.getApplyTime()%>
		</td>
	  </tr>
	  <tr><td>
	  	<table width="100%" border="1" cellpadding="1" cellspacing="1" bordercolor="#000000">
			  <tr height="35">
				<td width="15%" align="right"><b>申请部门</b></td>
				<td width="25%"><%=main.getOrgName()%></td>
				<td width="15%" align="right"><b>申请</b>人</td>
				<td width="25%"><%=main.getCreatorName()%></td>
			  </tr>
			  <tr height="35">
				<td align="right"><b>付款人</b></td>
				<td><%=main.getPayer()%></td>
				<td align="right"><b>收款人</b></td>
				<td><%=main.getReceiver()%></td>
			  </tr>
			  <tr height="35">
				<td align="right"><b>付款形式</b></td>
				<td colspan="3"><%=main.getPayType()%></td>
			  </tr>
			  <%
			  if(details!=null&&details.size()>0)
			  {
			  %>
			  <tr><td colspan="4"><table width="100%" border="1" cellpadding="0" cellspacing="0">
			  	<tr>
					<td height="25" align="center"><b>付款开户行</b></td>
					<td align="center"><b>付款帐户</b></td>
					<td align="center"><b>收款开户行</b></td>
					<td align="center"><b>收款帐户</b></td>
					<td align="center"><b>金额</b></td>
				</tr>
			  <%
			  	len = details.size();
			    for(int i=0;i<len;i++)
				{
					TInnerTransferDetail detail = (TInnerTransferDetail)details.get(i);
			  %>
			  <tr height="25">
				<td align="center"><%=detail.getPayBank()%></td>
				<td align="center"><%=detail.getPayAccount()%></td>
				<td align="center"><%=detail.getReceiveBank()%></td>
				<td align="center"><%=detail.getReceiveAccount()%></td>
				<td align="center"><%=detail.getSum()%></td>
			  </tr>
			  <%}%>
			  </table></td></tr>
			  <%}%>
			  <tr height="35">
				<td align="right"><b>资金部经理意见</b></td>
				<td colspan="3"><%=request.getAttribute("dept")%></td>
			  </tr>
			  <tr height="35">
				<td align="right"><b>股份分管副总意见</b></td>
				<td colspan="3"><%=request.getAttribute("general")%></td>
			  </tr>
		</table></td></tr>
	</table></div></td></tr>
</table>
<script>
doPrint('1');
</script>
</body>
</html>
