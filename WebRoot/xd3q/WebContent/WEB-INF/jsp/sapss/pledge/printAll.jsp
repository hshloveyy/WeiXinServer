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
int pageSize = 12;
int pageCount = (size%pageSize>0)?(size/pageSize)+1:(size/pageSize);
double  qualityTotal = 0d;
double  total = 0d;
Integer meListCount = (Integer) request.getAttribute("meListCount");
int pagei = 1;
%>

</head>
<body>
<TABLE align="center">
<%for(int p=1;p<=pageCount;p++){ %>
<TR><TD></TD><TD><DIV id="page<%=pagei++ %>">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="4"><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4">信达股份有限公司货物<font size="5">进</font>仓单</font></td><td></td></tr>
       <tr><td>${deptName }:${creator }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;过账时间:${main.approvedTime }</td><td align="right">序号:${main.serialNo }&nbsp;&nbsp;进仓单号：${main.receiptNo }<%=pageCount<=1?"":"_"+p %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
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
				<td align="center" width="60" height="25">备注</td>
				<td align="center" colspan="7">
					${main.memo}
				</td>
			</tr>
			<tr>
			    <td align="center"  height="25">物料号</td>
				<td align="center">品名规格</td>
				<td align="center">批次</td>
				<td align="center">单位</td>
				<td align="center">单价</td>
				<td align="center">数量</td>
				<td align="center">金额</td>
				<td align="center">币别</td>
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
		        <td align="center" height="25" colspan="2"><%=me.getMaterialCode() %></td>
				<td align="left" colspan="2">
					&nbsp;&nbsp;<%=me.getMaterialName() %>
				</td>
				<td align="left" colspan="4">
					&nbsp;&nbsp;<%=me.getMaterialUnit() %>
				</td>
		    </tr>
			<%} else {%>
			<tr>
			    <td align="center" height="25"><%=me.getMaterialCode() %></td>
				<td align="center"><%=me.getMaterialName() %></td>
				<td align="center"><%=me.getBatchNo()%></td>
				<td align="center"><%=me.getMaterialUnit() %></td>
				<td align="center"><%=NumberUtils.round(me.getPrice(),2) %></td>
				<td align="center"><%=NumberUtils.round(me.getQuantity(),3) %></td>
				<td align="center"><%=NumberUtils.round(me.getTotal(),2) %></td>
				<td align="center"><%=me.getCurrency() %></td>
			</tr>
			<%if(meListCount==(i+1)){%>
			<tr>
				<td align="left"  height="25" colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总计</td>
				<td align="center"><%=NumberUtils.round(qualityTotal,3) %></td>
				<td align="center"><%=NumberUtils.round(total,2) %></td>
				<td align="center"></td>
			</tr>
			<%} %>
			<%} %>
			<%} %>
			

		</table>
		</td>
		<td>第<br>一<br>联<br><br>存<br>根</td>
		</tr>
		</table>
</DIV></TD></TR>
<%} %>

<%for(int p=1;p<=pageCount;p++){ %>
<TR><TD></TD><TD><DIV id="page<%=pagei++ %>">
      <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="4"><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4">信达股份有限公司货物<font size="5">进</font>仓单</font></td><td></td></tr>
       <tr><td>${deptName }:${creator }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;过账时间:${main.approvedTime }</td><td align="right">序号:${main.serialNo }&nbsp;&nbsp;进仓单号：${main.receiptNo }<%=pageCount<=1?"":"_"+p %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
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
				<td align="center" width="60" height="25">备注</td>
				<td align="center" colspan="7">
					${main.memo}
				</td>
			</tr>
			<tr>
			    <td align="center"  height="25">物料号</td>
				<td align="center">品名规格</td>
				<td align="center">批次</td>
				<td align="center">单位</td>
				<td align="center">单价</td>
				<td align="center">数量</td>
				<td align="center">金额</td>
				<td align="center">币别</td>
			</tr>
			<%boolean hisMark = false;
			  for (int i=(p*pageSize-pageSize);(i<meList.size()&&i<p*pageSize);i++){
			      TReceiptMaterial me = (TReceiptMaterial)meList.get(i);
			      //total +=me.getTotal();
			      //qualityTotal+=me.getQuantity();
			      String isValid = me.getIsAvailabel();
			%>
			
			
			<% if("fuck".equals(isValid)){ %>
			 <tr>
		        <td align="center" height="25" colspan="2"><%=me.getMaterialCode() %></td>
				<td align="left" colspan="2">
					&nbsp;&nbsp;<%=me.getMaterialName() %>
				</td>
				<td align="left" colspan="4">
					&nbsp;&nbsp;<%=me.getMaterialUnit() %>
				</td>
		    </tr>
			<%} else {%>
			<tr>
			    <td align="center" height="25"><%=me.getMaterialCode() %></td>
				<td align="center"><%=me.getMaterialName() %></td>
				<td align="center"><%=me.getBatchNo()%></td>
				<td align="center"><%=me.getMaterialUnit() %></td>
				<td align="center"><%=NumberUtils.round(me.getPrice(),2) %></td>
				<td align="center"><%=NumberUtils.round(me.getQuantity(),3) %></td>
				<td align="center"><%=NumberUtils.round(me.getTotal(),2) %></td>
				<td align="center"><%=me.getCurrency() %></td>
			</tr>
			<%if(meListCount==(i+1)){%>
			<tr>
				<td align="left"  height="25" colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总计</td>
				<td align="center"><%=NumberUtils.round(qualityTotal,3) %></td>
				<td align="center"><%=NumberUtils.round(total,2) %></td>
				<td align="center"></td>
			</tr>
			<%} %>
			<%} %>
			<%} %>
			

		</table>
		</td>
		<td>第<br>二<br>联<br><br>财<br>务</td>
		</tr>
		</table>
</DIV></TD></TR>
<%} %>
<%
pageCount = (meListCount%pageSize>0)?(meListCount/pageSize)+1:(meListCount/pageSize);

 %>
<%for(int p=1;p<=pageCount;p++){ %>
<TR><TD></TD><TD><DIV id="page<%=pagei++ %>">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="4"><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4">信达股份有限公司货物<font size="5">进</font>仓单</font></td><td></td></tr>
       <tr><td>${deptName }:${creator }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;过账时间:${main.approvedTime }</td><td align="right">序号:${main.serialNo }&nbsp;&nbsp;进仓单号：${main.receiptNo }<%=pageCount<=1?"":"_"+p %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
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
				<td align="center" width="60" height="25">备注</td>
				<td align="center" colspan="7">
					${main.memo}
				</td>
			</tr>
			<tr>
			    <td align="center"  height="25">物料号</td>
				<td align="center">品名规格</td>
				<td align="center">批次</td>
				<td align="center">单位</td>
				<td align="center">单价</td>
				<td align="center">数量</td>
				<td align="center">金额</td>
				<td align="center">币别</td>
			</tr>
			<%boolean hisMark = false;
			  for (int i=(p*pageSize-pageSize);(i<meList.size()&&i<p*pageSize);i++){
			      TReceiptMaterial me = (TReceiptMaterial)meList.get(i);
			      //total +=me.getTotal();
			      //qualityTotal+=me.getQuantity();
			      String isValid = me.getIsAvailabel();
			  if("fuck".equals(isValid)){ 
			     continue;
			  } else {%>
			<tr>
			    <td align="center" height="25"><%=me.getMaterialCode() %></td>
				<td align="center"><%=me.getMaterialName() %></td>
				<td align="center"><%=me.getBatchNo()%></td>
				<td align="center"><%=me.getMaterialUnit() %></td>
				<td align="center"><%=NumberUtils.round(me.getPrice(),2) %></td>
				<td align="center"><%=NumberUtils.round(me.getQuantity(),3) %></td>
				<td align="center"><%=NumberUtils.round(me.getTotal(),2) %></td>
				<td align="center"><%=me.getCurrency() %></td>
			</tr>
			<%if(meListCount==(i+1)){%>
			<tr>
				<td align="left"  height="25" colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总计</td>
				<td align="center"><%=NumberUtils.round(qualityTotal,3) %></td>
				<td align="center"><%=NumberUtils.round(total,2) %></td>
				<td align="center"></td>
			</tr>
			<%} %>
			<%} %>
			<%} %>
			

		</table>
		</td>
		<td>第<br>三<br>联<br><br>业<br>务</td>
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