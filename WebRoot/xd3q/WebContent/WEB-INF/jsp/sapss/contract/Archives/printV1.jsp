<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.infolion.platform.console.workflow.dto.TaskHisDto"%>
<%@ page import="com.infolion.sapss.common.dto.CommonChangeDto"%>
<%@ page import="com.infolion.sapss.contract.domain.TContractPurchaseInfo"%>
<%@ page import="com.infolion.sapss.contract.domain.TContractPurchaseMateriel"%>
<jsp:directive.page import="com.infolion.sapss.common.NumberUtils"/>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购订单信息打印</title>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script></head>
<body>
<%

%>
<TABLE align="center">

<TR><TD></TD><TD><DIV id="page1">
     <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
           <br></br>
           <tr>
             <td colspan="6" height="30"><div align="center"  ><font size="4">采购订单信息</font></div></td>
           </tr>
<tr>	
	<td height="30" align="right"><nobr>项目名称</nobr></td>
	<td><nobr>${purchase.projectName}</nobr>
	</td>
	<td align="right"><nobr>合同组编号</nobr></td>
	<td>
	</td>
	<td align="right"><nobr>合同编码</nobr></td>
	<td>${purchase.contractNo}
	</td>
</tr>
<tr>
	<td height="30" align="right">合同名称</td>
	<td colspan="3">${purchase.contractName}
	</td>
	<td align="right">原合同编码</td>
	<td>${purchase.oldContractNo}
	</td>
</tr>
<tr>
	<td height="30" align="right"><nobr>采购凭证日期</nobr></td>
	<td>${purchase.ekkoBedat}
	</td>
	<td align="right">货币码:</td>
	<td>${purchase.ekkoWaers}
	</td>
	<td align="right">外部纸质合同号</td>
	<td>${purchase.ekkoUnsez}
	</td>
	
</tr>
<tr>
	
	<td height="30" align="right">SAP订单号</td>
	<td>${purchase.sapOrderNo}
	</td>
	<td align="right">付款方式</td>
	<td>${purchase.payType}
	</td>
	<td align="right">付款条件</td>
	<td>${purchase.ekkoZtermName}
	</td>
</tr>
<tr>
	<td height="30" align="right">供应商名称</td>
	<td colspan="5">${purchase.ekkoLifnrName}
	</td>
	
</tr>
<tr>
	<td height="30" align="right">开票方名称</td>
	<td colspan="5">${purchase.invoicingPartyName}
	</td>
</tr>
<tr>
	<td height="30" align="right">收款方名称</td>
	<td colspan="5">${purchase.payerName}
	</td>
</tr>
<tr>
	<td height="30" align="right">国际贸易条件1</td>
	<td>${purchase.ekkoInco1}
	</td>
	<td align="right">国际贸易条件2</td>
	<td>${purchase.ekkoInco2}
	</td>
	<td align="right">汇率</td>
	<td>${purchase.ekkoWkurs}
	</td>
</tr>
<tr>
	<td height="30" align="right">订单币别不含税总计</td>
	<td>${purchase.totalAmount}
	</td>
	<td align="right">订单币别税费总计</td>
	<td>${purchase.totalTaxes}
	</td>
	<td align="right">订单币别总计</td>
	<td>${purchase.total}
	</td>
</tr>
<tr>
	<td height="30" align="right">装运港</td>
	<td>${purchase.shipmentPort}
	</td>
	<td align="right">目的港</td>
	<td>${purchase.destinationPort}
	</td>
	<td align="right">装运期</td>
	<td>${purchase.shipmentDate}
	</td>
</tr>
<%TContractPurchaseInfo info = (TContractPurchaseInfo)request.getAttribute("purchase"); 
List<TContractPurchaseMateriel> meList = info.getContractPurchaseMateriels();

%>
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
           <%for(int i=0;i<meList.size();i++){ 
            TContractPurchaseMateriel me = meList.get(i);
           %>
             <tr>
              <td height="25"><div align="center"><%=me.getEkpoMatnr() %></div></td>
              <td>&nbsp;<%=me.getEkpoTxz01() %></td>
              <td>&nbsp;<%=me.getEkpoMenge() %></td>
              <td>&nbsp;<%=me.getEkpoMeins() %></td>
              <td>&nbsp;<%=NumberUtils.round(me.getEkpoNetpr(),2) %></td>
              <td>&nbsp;<%=me.getEkpoPeinh() %></td>
              
             </tr>
          <%} %>

           <tr>
             <td colspan="6" height="25"><div align="center"  ><strong><font size="4">审核意见</font></strong></div></td>
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