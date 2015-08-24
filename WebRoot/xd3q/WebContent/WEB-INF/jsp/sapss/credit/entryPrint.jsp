<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>

</head>
<body>
<TABLE align="center">
<TR><TD></TD><TD><DIV id="page1">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="2"><br><br><br></td></tr>
       <tr><td align="center" colspan="2"><font size="5">信用证主要条款审查登记表</font></td></tr>
       <tr><td colspan="2">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center">
			<tr>
				<td align="center" width="80" height="25">开证行</td>
				<td align="left" width="270" colspan="3">
					${main.createBank}
				</td>
				<td align="center" width="80" height="25">国别/地区</td>
				<td align="left" width="270" colspan="3">
					${main.country}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">开证申请人</td>
				<td align="left" width="270" colspan="3">
					${main.request}
				</td>
				<td align="center" width="80" height="25">付款方式</td>
				<td align="left" width="270" colspan="3">
					${main.paymentType}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">受益人</td>
				<td align="left" colspan="7">
					${main.benefit}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">金额</td>
				<td align="left" width="270" colspan="3">
					${main.amount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${main.currency}
				</td>
				<td align="center" width="80" height="25">合同号</td>
				<td align="left" width="270" colspan="3">
					${main.contractNo}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="50">品名及规格</td>
				<td align="left" colspan="7">
					&nbsp;&nbsp;&nbsp;&nbsp;${main.goods}&nbsp;&nbsp;${main.specification}
				</td>
			</tr>
		    <tr>
				<td align="center"height="25">装期</td>
				<td align="left">
					${main.loadingPeriod}
				</td>
				<td align="center"height="25">效期</td>
				<td align="left">
					${main.validDate}
				</td>
				<td align="center"height="25">可否分批</td>
				<td align="left">
					<c:if test="${main.canBatches=='Y'}">是</c:if>
					<c:if test="${main.canBatches=='N'}">否</c:if>
				</td>
				<td align="center"height="25">可否转运</td>
				<td align="left">
					<c:if test="${main.transShipment=='Y'}">是</c:if>
					<c:if test="${main.transShipment=='N'}">否</c:if>
				</td>
			</tr>
		    <tr>
				<td align="center" width="80" height="25">装运港</td>
				<td align="left" width="270" colspan="3">
					${main.portOfShipment}
				</td>
				<td align="center" width="80" height="25">目的港</td>
				<td align="left" width="270" colspan="3">
					${main.portOfDestination}
				</td>
			</tr>
			<tr>
				<td align="left" valign="top" height="170" colspan="8">
				   &nbsp;&nbsp;1、发票&nbsp;${main.invoice}<br>
				   &nbsp;&nbsp;2、提单&nbsp;${main.invoice}<br>
				   &nbsp;&nbsp;3、保险单&nbsp;${main.billOfInsurance}<br>
				   &nbsp;&nbsp;4、品质(分析证)&nbsp;${main.billOfQuality}<br>
				   &nbsp;&nbsp;5、受益人证明&nbsp;${main.benefitCertification}<br>
				   &nbsp;&nbsp;6、产地证&nbsp;${main.certificateOfOrigin}<br>
				   &nbsp;&nbsp;7、装箱单&nbsp;${main.packingSlip}<br>
				   &nbsp;&nbsp;8、起运电&nbsp;${main.dispatchElectric}<br>
				   &nbsp;&nbsp;9、其他单据&nbsp;${main.otherDocuments}
                </td>
			</tr>
			<tr>
				<td align="center" width="80" height="60">特别条款</td>
				<td align="left" colspan="7">
					&nbsp;&nbsp;&nbsp;&nbsp;${main.specialConditions}
				</td>
			</tr>
			<c:if test="${isHis==true}">
			<tr>
				<td align="center" width="80" height="60">应修改事项</td>
				<td align="left" colspan="7">
					&nbsp;&nbsp;&nbsp;&nbsp;${main.modifyInfo}
				</td>
			</tr>
			</c:if>
			<c:forEach items="${taskHis}" var="task">
		    <tr>
		        <td align="center" width="100" height="25" colspan="2">${task.taskName}</td>
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