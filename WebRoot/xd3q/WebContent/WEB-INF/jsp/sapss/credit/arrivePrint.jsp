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
				<td align="center" width="80" height="25">证号</td>
				<td align="left" width="200" colspan="2">
					${main.creditNo}
				</td>
				<td align="center" width="80" height="25">开证日期</td>
				<td align="left">
					${main.customCreateDate}
				</td>
				<td align="center" width="80" height="25">到证日期</td>
				<td align="left" width="200" colspan="2">
					${main.creditRecDate}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">开证行</td>
				<td align="left" colspan="4">
					${main.createBank}
				</td>
				<td align="center" width="80" height="25">国别/地区</td>
				<td align="left" colspan="2">
					${main.country}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">申请人</td>
				<td align="left" colspan="7">
					${main.request}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">受益人</td>
				<td align="left" width="270" colspan="3">
					${main.benefit}
				</td>
				<td align="center" width="80" height="25">付款方式</td>
				<td align="left" width="270" colspan="3">
					${main.paymentType}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">金额</td>
				<td align="left" width="270" colspan="3">
					${main.amount}&nbsp;&nbsp;&nbsp;&nbsp;(${main.currency})
				</td>
				<td align="center" width="80" height="25">合同号</td>
				<td align="left" width="270" colspan="3">
					${main.contractNo}
				</td>
			</tr>
			<tr>
				<td align="left" height="50" colspan="6" valign="top">&nbsp;&nbsp;品名及规格:${main.goods}&nbsp;&nbsp;${main.specification}</td>
				<td align="left" valign="top" colspan="2">&nbsp;&nbsp;唛头：${main.mark}
				</td>
			</tr>
			<tr>
				<td align="center" height="25">发票</td>
				<td align="center" height="25">提单</td>
				<td align="center" height="25">保险单</td>
				<td align="center" height="25">品质(分析)证</td>
				<td align="center" height="25">收益人证明</td>
				<td align="center" height="25">产地证</td>
				<td align="center" height="25">装箱单</td>
				<td align="center" height="25">装船电</td>
			</tr>
			<tr>
				<td align="center" height="25">${main.invoice}</td>
				<td align="center" height="25">${main.billOfLading}</td>
				<td align="center" height="25">${main.billOfInsurance}</td>
				<td align="center" height="25">${main.billOfQuality}</td>
				<td align="center" height="25">${main.benefitCertification}</td>
				<td align="center" height="25">${main.certificateOfOrigin}</td>
				<td align="center" height="25">${main.packingSlip}</td>
				<td align="center" height="25">${main.electricShip}</td>
			</tr>
			<tr>
				<td align="center" width="80" height="25">其他单据</td>
				<td align="left" colspan="7">
					${main.otherDocuments}
				</td>
			</tr>
		    <tr>
				<td align="center"height="25">装期</td>
				<td align="left">
					${main.loadingPeriod}
				</td>
				<td align="center"height="25">效期及地点</td>
				<td align="left">
					${main.validDate}&nbsp;&nbsp;${main.place}
				</td>
				<td align="center"height="25">可否分批</td>
				<td align="left">
					<c:if test="${main.canBatches=='1'}">是</c:if>
					<c:if test="${main.canBatches=='0'}">否</c:if>
				</td>
				<td align="center"height="25">可否转运</td>
				<td align="left">
					<c:if test="${main.transShipment=='1'}">是</c:if>
					<c:if test="${main.transShipment=='0'}">否</c:if>
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
				<td align="center" width="80" height="60">特别条款</td>
				<td align="left" colspan="7" valign="top">
					${main.specialConditions}
				</td>
			</tr>
			<tr>
				<td align="center" width="80" height="60">应修改事项</td>
				<td align="left" colspan="7" valign="top">
					${main.billConditions}
				</td>
			</tr>
			<tr>
			   <td align="center" width="150" height="25" colspan="2">初审人</td>
				<td align="left" width="100" colspan="2">
					&nbsp;&nbsp;${main.applyer}
				</td>
				<td align="left" width="500" colspan="4">
					&nbsp;&nbsp;
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