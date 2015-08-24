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
       <tr><td align="center" colspan="2"><font size="6">信达外贸公司出口审单出单记录表</font></td></tr>
       <tr><td colspan="2">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="60" height="25">部门</td>
				<td align="center" width="80" height="25">接单日期</td>
				<td align="center" width="100" height="25">合同号(SC/NO.)</td>
				<td align="center" width="120" height="25" colspan="3">${main.billType } No.</td>
				<td align="center" width="200" height="25">发票号(INV NO.)</td>
				<td align="center" width="100" height="25">金额</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">${deptName }</td>
				<td align="center" width="80" height="25">${main.billDate }</td>
				<td align="center" width="100" height="25">${main.contractNo }</td>
				<td align="center" height="25" colspan="3">${main.lcdpdaNo }</td>
				<td align="center" width="200" height="25">${main.invNo }</td>
				<td align="center" width="100" height="25">${main.total }&nbsp;&nbsp;&nbsp;${main.currency }</td>
			</tr>
			<tr>
				<td align="center" colspan="2">核销单号</td>
				<td align="left" width="200" colspan="6"  height="25">
					&nbsp;&nbsp;${main.writeNo }
				</td>
			</tr>
			<tr>
				<td align="center" width="60" rowspan="3" height="400">审<br>单<br>记<br>录</td>
				<td align="left" colspan="7" height="330" valign="top">${main.examRecord }&nbsp;&nbsp;</td>
			</tr>
			<tr>
			    <td>审单</td>
				<td>&nbsp;&nbsp;${creator }</td>
		        <td colspan="2" height="25">经理复核</td>
				<td colspan="4">&nbsp;&nbsp;</td>
		    </tr>
			<tr>
			   <td colspan="7" height="50" valign="top">&nbsp;&nbsp;${main.mark }</td>
			</tr>
			<tr><td colspan="2">出单日期</td><td colspan="2">议付银行</td><td colspan="2">是否押汇</td><td colspan="2">银行签收</td></tr>
			<tr><td colspan="2">&nbsp;${main.examDate }&nbsp;</td><td colspan="2">&nbsp;${main.bank }&nbsp;</td><td colspan="2">&nbsp;${main.isNegotiat }&nbsp;</td><td colspan="2">&nbsp;&nbsp;</td></tr>
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