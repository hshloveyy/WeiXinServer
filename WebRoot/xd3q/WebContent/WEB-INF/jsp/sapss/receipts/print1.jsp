<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进仓打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>

</head>
<body>
<TABLE align="center">
<TR><TD></TD><TD><DIV id="page1">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="3"><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4">信达股份有限公司货物<font size="5">进</font>仓单</font></td></tr>
       <tr><td>${deptName }:${creator }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;申报时间:${main.applyTime }</td><td align="right">进仓单号：${main.receiptNo }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="60" height="25">销货单位</td>
				<td align="center" width="150" colspan="2">
					${main.unitName}
				</td>
				<td align="center" width="60">仓库名称及地址</td>
				<td align="center" width="100" colspan="2">
				    ${main.warehouse}<br>
					${main.warehouseAdd}
				</td>
				<td align="center" width="60">sap物料凭证号</td>
				<td align="center" width="100">${main.sapReturnNo }</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25">纸质合同号</td>
				<td align="center" width="100" colspan="2">
					${main.contractPaperNo}
				</td>
				<td align="center" width="60">采购合同号</td>
				<td align="center" width="100" colspan="2">${main.contractNo }</td>
				<td align="center" width="60">SAP订单号</td>
				<td align="center" width="100">
					${main.sapOrderNo}
				</td>
			</tr>
			<tr>
			    <td align="center"  height="25">物料号</td>
				<td align="center">品名规格</td>
				<td align="center">批次</td>
				<td align="center">单位</td>
				<td align="center">数量</td>
				<td align="center">单价</td>
				<td align="center">金额</td>
				<td align="center">币别</td>
			</tr>
			<c:forEach items="${meList}" var="me">
			<tr>
			    <td align="center"  height="25">${me.materialCode}</td>
				<td align="center"  height="25">${me.materialName}</td>
				<td align="center">${me.batchNo }</td>
				<td align="center">${me.materialUnit }</td>
				<td align="center">${me.quantity }</td>
				<td align="center">${me.price }</td>
				<td align="center">${me.total }</td>
				<td align="center">${me.currency }</td>
			</tr>
			</c:forEach>
		</table>
		</td>
		</tr>
		</table>
</DIV></TD></TR></TABLE>
</body>
<script>
doPrint('1');
</script>
</html>