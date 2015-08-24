<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.infolion.sapss.project.dto.ProjectPrintDto"%>
<%@ page import="com.infolion.platform.console.workflow.dto.TaskHisDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>立项信息打印</title>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script></head>
<body>
<%
List list = (List)request.getAttribute("list");
%>
<TABLE align="center" border="0">
<%
 for (int i = 0 ;i<list.size();i++){
 ProjectPrintDto dto = (ProjectPrintDto)list.get(i);

 %>
<TR><TD><DIV id="page<%=i+1 %>">
     <table width="755" align="center" border="1" cellpadding="4" cellspacing="0" bordercolor="#000000">
           <tr>
             <td colspan="4"><div align="center"  >立项信息</div></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center"  >承 办 单 位</div></td>
             <td width="403" align="left">&nbsp;<%=dto.getOrgName() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center"  >承   办   人</div></td>
             <td>&nbsp;<%=dto.getNuderTaker() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center"  >项 目 编 号</div></td>
             <td>&nbsp;<%=dto.getProjectNo()==null?"":dto.getProjectNo() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center"  >原 项 目 编 号</div></td>
             <td>&nbsp;<%=dto.getOldProjectNo() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center" ><font color="red">*</font>项 目 名 称</div></td>
             <td>&nbsp;<%=dto.getProjectName() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center">申 报 日 期</div></td>
             <td>&nbsp;<%=dto.getApplyTime() %></td>
           </tr>
           <tr>
             <td colspan="2"><div align="center">终 止 时 间</div></td>
             <td>&nbsp;<%=dto.getAvailableDataEnd() %></td>
           </tr>
           <tr>
             <td width="13" rowspan="3" align="center" valign="middle"  >买 方 / 委托方</td>
             <td width="166" height="25"><div align="center"  >客 户 名 称</div></td>
             <td>&nbsp;<%=dto.getCustomerLinkMan()==null?"":dto.getCustomerLinkMan() %></td>
           </tr>
           <tr>
             <td><div align="center"  >付 款 方 式</div></td>
             <td>&nbsp;<%=dto.getCustomerPayType()==null?"":dto.getCustomerLinkMan() %></td>
           </tr>
           <tr>
             <td><div align="center"  >结 算 方 式</div></td>
             <td>&nbsp;<%=dto.getCustomerBalanceType()==null?"":dto.getCustomerBalanceType() %></td>
           </tr>
           <tr>
             <td width="13" rowspan="3"  >卖 方 / 委托方</td>
             <td width="166" height="25"><div align="center"  >供 应 商 名称</div></td>
             <td>&nbsp;<%=dto.getProviderLinkMan()==null?"":dto.getProviderLinkMan() %></td>
           </tr>
           <tr>
             <td><div align="center"  >付 款 方 式</div></td>
             <td>&nbsp;<%=dto.getProviderPayType()==null?"":dto.getProviderPayType() %></td>
           </tr>
           <tr>
             <td><div align="center"  >结 算 方 式</div></td>
             <td>&nbsp;<%=dto.getProviderBalanceType()==null?"":dto.getProviderBalanceType() %></td>
           </tr>
           <tr>
             <td colspan="3"><div align="center"  >审核意见</div></td>
           </tr>
           <%List<TaskHisDto> his = dto.getTasks();
             for(int k=0;k<his.size();k++){
             TaskHisDto task =his.get(k);
            %>
             <tr>
              <td  colspan="2"><div align="center"><%=task.getExaminePerson() %></div></td>
              <td>&nbsp;<%=task.getExamine() %></td>
             </tr>
           <%} %>
         </table>
         <br>
         <br>
         <br>
       </DIV>
</TD></TR>
<%} %>
</TABLE>
</body>
<script>
doPrint('1','2100','2970');
</script>
</html>