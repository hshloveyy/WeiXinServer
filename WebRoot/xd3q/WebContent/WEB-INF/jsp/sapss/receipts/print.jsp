<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.infolion.sapss.receipts.domain.TReceiptMaterial"%>
<%@ page import="com.infolion.sapss.common.NumberUtils"%>
<%@ page import="com.infolion.platform.console.workflow.dto.TaskHisDto"%>
<jsp:directive.page import="java.math.BigDecimal"/>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进仓打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>
<%
List meList = (List)request.getAttribute("meList"); 
List taskHis = (List)request.getAttribute("taskHis"); 
int size = meList.size();
int pageSize = 10;
int pageCount = (size%pageSize>0)?(size/pageSize)+1:(size/pageSize);
if(pageCount==0) pageCount=1;
double  qualityTotal = 0d;
double  total = 0d;
Integer meListCount = (Integer) request.getAttribute("meListCount");
%>

</head>
<body>
<TABLE align="center">
<%for(int p=1;p<=pageCount;p++){ %>
<TR><TD></TD><TD><DIV id="page<%=p %>">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="4"><br><br><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4" color="black">${title }货物<font size="5" color="black">进</font>仓单
       <c:choose>
        <c:when test="${main.isAvailable=='0'}">
		 <font color="black">(作废)</font>
		</c:when>
		<c:when test="${main.isAvailable=='1'&&main.examineState=='4'}">
		 <font color="black">(审批不通过)</font>
		</c:when>
		<c:otherwise>        			
	   	</c:otherwise>
       </c:choose>
       </font></td><td></td></tr>
       <tr><td><font color="black">${deptName }:${creator }</font></td><td><font color="black">&nbsp;&nbsp;&nbsp;&nbsp;过账时间:${main.approvedTime }</font></td><td align="right"><font color="black">序号:${main.serialNo }&nbsp;&nbsp;进仓单号：${main.receiptNo }<%=pageCount<=1?"":"_"+p %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
			<tr>
				<td align="center" width="60" height="25"><font color="black">销货单位</font></td>
				<td align="center" width="150" colspan="2">
					<font color="black">${main.unitName}</font>
				</td>
				<td align="center" width="60"><font color="black">仓库名称及地址</font></td>
				<td align="center" width="100" colspan="2">
				    <font color="black">${main.warehouse}<br>
					${main.warehouseAdd}</font>
				</td>
				<td align="center" width="60"><font color="black">sap物料凭证号</font></td>
				<td align="center" width="100"><font color="black">${main.sapReturnNo }</font></td>
			</tr>
			<tr>
				<td align="center" width="60" height="25"><font color="black">纸质合同号</font></td>
				<td align="center" width="100" colspan="2">
					<font color="black">${main.contractPaperNo}</font>
				</td>
				<td align="center" width="60"><font color="black">采购合同号</font></td>
				<td align="center" width="100" colspan="2"><font color="black">${main.contractNo }</font></td>
				<td align="center" width="60"><font color="black">SAP订单号</font></td>
				<td align="center" width="100">
					<font color="black">${main.sapOrderNo}</font>
				</td>
			</tr>
			<tr>
				<td align="center" width="60" height="25"><font color="black">备注</font></td>
				<td align="center" colspan="7">
					<font color="black">${main.memo}</font>
				</td>
			</tr>
			<tr>
			    <td align="center"  height="25"><font color="black">物料号</font></td>
				<td align="center"><font color="black">品名规格</font></td>
				<td align="center"><font color="black">批次</font></td>
				<td align="center"><font color="black">单位</font></td>
				<td align="center"><font color="black">单价</font></td>
				<td align="center"><font color="black">数量</font></td>
				<td align="center"><font color="black">金额</font></td>
				<td align="center"><font color="black">币别</font></td>
			</tr>
			<%boolean hisMark = false;
			  for (int i=(p*pageSize-pageSize);(i<meList.size()&&i<p*pageSize);i++){
			      TReceiptMaterial me = (TReceiptMaterial)meList.get(i);
			     
			      total +=me.getTotal();
			      qualityTotal+=me.getQuantity();
			      String isValid = me.getIsAvailabel();
			%>
			
			
			<% if("fuck".equals(isValid)){ %>
			 <tr>
		        <td align="center"  height="25" colspan="2"><font color="black"><%=me.getMaterialCode() %></font></td>
				<td align="left"  colspan="2">
					&nbsp;&nbsp;<font color="black"><%=me.getMaterialName() %></font>
				</td>
				<td align="left" colspan="4">
					&nbsp;&nbsp;<font color="black"><%=me.getMaterialUnit() %></font>
				</td>
		    </tr>
			<%} else {%>
			<tr>
			    <td align="center" height="25"><font color="black"><%=me.getMaterialCode() %></font></td>
				<td align="center"><font color="black"><%=me.getMaterialName() %></font></td>
				<td align="center"><font color="black"><%=me.getBatchNo()%></font></td>
				<td align="center"><font color="black"><%=me.getMaterialUnit() %></td>
				<td align="center"><font color="black"><%=NumberUtils.round(me.getPrice(),2) %></font></td>
				<td align="center"><font color="black"><%=NumberUtils.round(me.getQuantity(),3) %></font></td>
				<td align="center"><font color="black"><%=NumberUtils.round(me.getTotal(),2) %></font></td>
				<td align="center"><font color="black"><%=me.getCurrency() %></font></td>
			</tr>
			<%if(meListCount==(i+1)){%>
			<tr>
				<td align="left"  height="25" colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="black">总计</font></td>
				<td align="center"><font color="black"><%=NumberUtils.round(qualityTotal,3) %></font></td>
				<td align="center"><font color="black"><%=NumberUtils.round(total,2) %></font></td>
				<td align="center"><font color="black"></td>
			</tr>
			<%} %>
			<%} %>
			<%} %>
			

		</table>
		</td>
		<c:if test="${printType=='1'}">
		<td><font color="black">第<br>一<br>联<br><br>存<br>根</font></td>
		</c:if>
		<c:if test="${printType=='2'}">
		<td><font color="black">第<br>二<br>联<br><br>财<br>务</font></td>
		</c:if>
		<c:if test="${printType=='3'}">
		<td><font color="black">第<br>三<br>联<br><br>业<br>务</font></td>
		</c:if>
		</tr>
		</table>
</DIV></TD></TR>
<%} %>

</TABLE>
</body>
<script>
doPrint('2','1480','2100');
</script>
</html>