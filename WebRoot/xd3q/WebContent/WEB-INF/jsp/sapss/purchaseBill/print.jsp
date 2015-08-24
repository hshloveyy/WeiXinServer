<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.infolion.sapss.purchaseBill.domain.TPurchaseBillMaterial"%>
<%@ page import="com.infolion.platform.console.workflow.dto.TaskHisDto"%>
<%@ page import="com.infolion.sapss.common.NumberUtils"%>
<%@ page import="com.infolion.sapss.payment.PaymentContants"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购开票打印</title>

<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script>
<%
List meList = (List)request.getAttribute("meList");
int size = meList.size();
int pageSize = 4;
int pageCount = (size%pageSize>0)?(size/pageSize)+1:(size/pageSize);

double  quantityTotal = 0d;
double  loanMoneyTotal = 0d;
double  taxTotal = 0d;
double  total = 0d;

Integer meListCount = (Integer) request.getAttribute("meListCount");
%>

</head>
<script>

</script>
<body>
<TABLE align="center">
<tr><TD align="center" colspan="5"></TD></tr>
<%for(int p=1;p<=pageCount;p++){ %>
<TR><TD></TD><TD><DIV id="page<%=p %>">
     <table border="0" cellpadding="4" cellspacing="1" class="datatable" align="center">
       <tr><td align="center" colspan="4"><br><br></td></tr>
       <tr><td align="center" colspan="3"><font size="4">信达股份有限公司采购发票审批表</font></td><td></td></tr>
       <tr><td>${deptName }:${creator }</td><td>&nbsp;&nbsp;&nbsp;&nbsp;申报时间:${main.applyTime }</td><td align="right">NO ：${main.purchaseBillNo }<%=pageCount<=1?"":"_"+p %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
       <tr><td colspan="3">
		<table width="700" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
			<tr>
				<td align="center" width="80" height="25">购货单位</td>
				<td align="center" width="320" colspan="3">
					${main.billToPartyName}
				</td>
				<td align="center" width="80">身份证号</td>
				<td align="center" width="100">
				    ${main.cardNo}
				</td>
				<td align="center" width="80">合同号</td>
				<td align="center" colspan="2">
				    ${main.paperContractNo}
				</td>
			</tr>
			<tr>
				<td align="center" height="25">发票类型</td>
				<td align="left" colspan="3">
				采购发票
				</td>
				<td align="center">发票号码</td>
				<td align="left">${main.taxNo }</td>
                <td align="center" width="80">SAP订单号</td>
				<td align="center" width="100" colspan="2">
				    ${main.sapOrderNo}
				</td>
			</tr>
			<tr>
				<td align="center" height="25">备注</td>
				<td align="center" colspan="8">
					${main.cmd}
				</td>
			</tr>
			<tr>
			    <td align="center" height="25">物料号</td>
				<td align="center" width="200" colspan="2">品名规格</td>
				<td align="center">单位</td>
				<td align="center" width="70">数量</td>
				<td align="center" width="70" colspan="2">单价</td>
				<td align="center" width="70" colspan="2">开票金额</td>
			</tr>
			<%boolean hisMark = false;
			  for (int i=(p*pageSize-pageSize);(i<meList.size()&&i<p*pageSize);i++){
			      TPurchaseBillMaterial me = (TPurchaseBillMaterial)meList.get(i);
			      quantityTotal +=me.getQuantity();
			      total +=me.getTotalMoney();
			      String mark = me.getPurchaseBillId();
			%>
			
			
			<% if("fuck".equals(mark)){ %>
			 <tr>
		        <td align="center"  height="25" colspan="4"><%=me.getMaterialCode() %></td>
				<td align="center"  colspan="1">
					&nbsp;&nbsp;<%=me.getMaterialName() %>
				</td>
				<td align="left" colspan="4">
					&nbsp;&nbsp;<%=me.getMaterialUnit() %>
				</td>
		    </tr>
			<%} else {%>
			<tr>
			    <td align="center" height="25"><%=me.getMaterialCode() %></td>
				<td align="center" colspan="2"><%=me.getMaterialName() %></td>
				<td align="center"><%=me.getMaterialUnit() %></td>
				<td align="center"><%=NumberUtils.round(me.getQuantity(),3)%></td>
				<td align="center" colspan="2"><%=NumberUtils.round(me.getPrice(),4)%></td>
				<td align="center" colspan="2"><%=NumberUtils.round(me.getTotalMoney(),2) %></td>
			</tr>
			<%if(meListCount==(i+1)){%>
			<tr>
				<td align="left"  height="25" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;总计(<%= PaymentContants.changeToBig(total)%>)</td>
				<td align="center"><%=NumberUtils.round(quantityTotal,3) %></td>
				<td colspan="2"></td>
				<td align="center"><%=NumberUtils.round(total,2) %></td>
			</tr>
			<%} %>
			<%} %>
			<%} %>
			

		</table>
		</td>
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