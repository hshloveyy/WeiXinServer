<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.infolion.platform.console.workflow.dto.TaskHisDto"%>
<%@ page import="com.infolion.sapss.common.dto.CommonChangeDto"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售订单信息打印</title>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script></head>
<body>
<%

%>
<TABLE align="center">

<TR><TD></TD><TD><DIV id="page1">
     <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
           <br></br>
           <tr>
             <td colspan="6" height="30"><div align="center"  ><font size="4">销售订单信息</font></div></td>
           </tr>
<tr>
	<td height="30"  align="right" width="13%">项目名称</td>
	<td width="20%">${sales.projectName}
	</td>
	<td align="right"width="13%" >信达项目</td>
	<td width="20%">
	<nobr>${sales.vbakBname}</nobr>
	</td>
	<td align="right" width="13%" >合同组名称</td>
	<td width="20%">${contractGroup.contractGroupName}
	</td>
</tr>
<tr>
	<td height="30"  align="right">原合同编码</td>
	<td>${sales.oldContractNo}
	</td>
	<td align="right">合同编码</td>
	<td>${sales.contractNo}</td>
	<td align="right">合同名称</td>
	<td>${sales.contractName}
	</td>	
</tr>
<tr>
	<td height="30"  align="right">手册号</td>
	<td>${sales.vbkdBstkdE}</td>
	<td align="right">
		外部纸质合同号
	</td>
	<td>${sales.vbkdIhrez}
	</td>
	<td align="right">
		单据日期
	</td>
	<td>${sales.vbakAudat}</td>		
</tr>
<tr>
	<td height="30"  align="right">付款条件</td>
	<td>
	${sales.vbkdZtermName }
	</td>
	<td align="right">付款方式</td>
	<td>${sales.vbkdZlschName }
	</td>
	<td align="right">销售地区</td>
	<td>${sales.vbkdBzirkName }
	</td>	
</tr>
<tr>
	<td height="30"  align="right">货币码</td>
	<td>${sales.vbakWaerk }
	</td>
	<td align="right">会计汇率</td>
	<td>${sales.vbkdKurrf}
	</td>
	<td align="right">装运期</td>
	<td>${sales.shipmentDate}
	</td>	
</tr>
<tr>
	<td height="30"  align="right">国际贸易条件1</td>
	<td>
	${sales.vbkdInco1Name}
	</td>
	<td align="right">国际贸易条件2</td>
	<td>${sales.vbkdInco2}
	</td>
	<td align="right">装运港</td>
	<td>${sales.shipmentPort}
	</td>
</tr>
<tr>
	<td  height="30" align="right">售达方名称</td>
	<td colspan="3">${sales.kuagvKunnrName}
	</td>
	<td align="right">目的港</td>
	<td>${sales.destinationPort}
	</td>		
</tr>
<tr>
	<td height="30"  align="right">送达方名称</td>
	<td colspan="3">${sales.kuwevKunnrName}
	</td>
	<td align="right">净金额</td>
	<td>${sales.orderNet}
	</td>
</tr>
<tr>
	<td  height="30" align="right">付费方名称</td>
	<td colspan="3">${sales.payerName}
	</td>
	<td align="right">销项税金</td>
	<td>${sales.orderTaxes}
	</td>	
</tr>
<tr>
	<td height="30"   align="right">收票方名称</td>
	<td colspan="3">${sales.bllToPartyName}
	</td>
	<td align="right">总金额</td>
	<td>${sales.orderTotal}
	</td>	
</tr>
<tr>
             <td colspan="6" height="25"><div align="center"><strong><font size="4">物料信息</font></strong></div></td>
           </tr>
           <tr>
               <td height="25"><div align="center">物料编码</div></td>
               <td>物料名称</td>
               <td>数量</td>
               <td>单位</td>
               <td>单价</td>
               <td>价格单位</td>
               
           </tr>
           <c:forEach items="${sales.contractSalesMateriels}" var="me">
             <tr>
              <td height="25"><div align="center">${me.vbapMatnr }</div></td>
              <td>&nbsp;${me.vbapArktx }</td>
              <td>&nbsp;${me.vbapZmeng }</td>
              <td>&nbsp;${me.vbapVrkme }</td>
              <td>&nbsp;${me.konvKbetr }</td>
              <td>&nbsp;${me.vbapKpein }</td>
             </tr>
           </c:forEach>
           <tr>
             <td colspan="6" height="25"><div align="center"  >审核意见</div></td>
           </tr>
           <%List<TaskHisDto> his = (List<TaskHisDto>)request.getAttribute("taskHis");
             for(int k=0;k<his.size();k++){
             TaskHisDto task =his.get(k);
            %>
             <tr>
              <td height="25"><div align="center"><%=task.getExaminePerson() %></div></td>
              <td   colspan="5" >&nbsp;<%=task.getExamine() %></td>
             </tr>
           <%} %>
         </table>
       </DIV>
</TD><td></td></TR>

<tr><td><br><br></td></tr>
<%
List<CommonChangeDto> changeList = (List<CommonChangeDto>)request.getAttribute("changeList");
int pageSize = 3;
int pageIndex = 1;
if(!changeList.isEmpty()){
for(int i=0;i<changeList.size();i++){ 
      CommonChangeDto dto = changeList.get(i);
      if(i%pageSize==0) pageIndex++;
%>
<%if(i%(pageSize)==0){%>
<TR><TD></TD><TD><DIV id="page<%=pageIndex %>">
    <br><br>
    <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
    <tr><td colspan="10" align="center" height="30"><font size="4">订单变更信息</font></td></tr>
<%} %>
    <tr><td height="30" width="100" align="center">变更时间</td><td colspan="4"><%=dto.getChangeTime() %></td><td>变更批次</td><td colspan="4"><%=dto.getChangeNo() %></td></tr>
    <tr><td height="30" align="center">变更描述</td><td colspan="9"><%=dto.getChangeDesc() %></td></tr>
    <tr><td height="30" align="center" colspan="10">变更审核意见</td></tr>
    <%List<TaskHisDto> changeHis = dto.getTasklist();
             for(int k=0;k<changeHis.size();k++){
             TaskHisDto task =changeHis.get(k);
             if("李标强".equals(task.getExaminePerson())) continue;
            %>
             <tr>
              <td height="25"><div align="center"><%=task.getExaminePerson() %></div></td>
              <td colspan="9">&nbsp;<%=task.getExamine() %></td>
             </tr>
           <%} %>
<%if((i+1)%pageSize==0||(i+1)==changeList.size()){%>
    </table>
</DIV></TD></TR>
<%} %>
<%}
} %>

<tr><td><br><br></td></tr>
</TABLE>
</body>
<script>
doPrint('1','2100','2970');
</script>
</html>