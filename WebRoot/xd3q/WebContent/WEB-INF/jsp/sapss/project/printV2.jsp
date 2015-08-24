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
<title>立项信息打印</title>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/printer/printer.js"></script></head>
<body>
<%

%>
<TABLE align="center">

<TR><TD></TD><TD><DIV id="page1">
     <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
           <br></br>
           <tr>
             <td colspan="3" height="30"><div align="center"  ><font size="4">立项信息</font></div></td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center"  >承 办 单 位</div></td>
             <td width="403" align="left">&nbsp;${main.orgName}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center"  >承   办   人</div></td>
             <td>&nbsp;${main.nuderTaker}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center"  >项 目 编 号</div></td>
             <td>&nbsp;${main.projectNo}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center"  >原 项 目 编 号</div></td>
             <td>&nbsp;${main.oldProjectNo}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center" ><font color="red">*</font>项 目 名 称</div></td>
             <td>&nbsp;${main.projectName}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center">申 报 日 期</div></td>
             <td>&nbsp;${main.applyTime}</td>
           </tr>
           <tr>
             <td colspan="2" height="25"><div align="center">终 止 时 间</div></td>
             <td>&nbsp;${main.availableDataEnd}</td>
           </tr>
           <tr>
             <td width="13" rowspan="3" align="center" valign="middle"  >买 方 / 委托方</td>
             <td width="166" height="25"><div align="center"  >客 户 名 称</div></td>
             <td height="25">&nbsp;${main.customerLinkMan}</td>
           </tr>
           <tr>
             <td height="25"><div align="center"  >付 款 方 式</div></td>
             <td>&nbsp;${main.customerPayType}</td>
           </tr>
           <tr>
             <td height="25"><div align="center"  >结 算 方 式</div></td>
             <td>&nbsp;${main.customerBalanceType}</td>
           </tr>
           <tr>
             <td width="13" rowspan="3"  >卖 方 / 委托方</td>
             <td width="166" height="25"><div align="center"  >供 应 商 名称</div></td>
             <td height="25">&nbsp;${main.providerLinkMan}</td>
           </tr>
           <tr>
             <td height="25"><div align="center"  >付 款 方 式</div></td>
             <td>&nbsp;${main.providerPayType}</td>
           </tr>
           <tr>
             <td height="25"><div align="center"  >结 算 方 式</div></td>
             <td>&nbsp;${main.providerBalanceType}</td>
           </tr>
           <tr>
             <td colspan="3" height="20"><div align="center"  >审核意见</div></td>
           </tr>
           <%List<TaskHisDto> his = (List<TaskHisDto>)request.getAttribute("taskHis");
             for(int k=0;k<his.size();k++){
             TaskHisDto task =his.get(k);
            %>
             <tr>
              <td  colspan="2" height="20"><div align="center"><%=task.getExaminePerson() %></div></td>
              <td>&nbsp;<%=task.getExamine() %></td>
             </tr>
           <%} %>
         </table>
       </DIV>
</TD><td></td></TR>

<tr><td><br><br></td></tr>

<TR><TD></TD><TD><DIV id="page2">
    <br><br>
    <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
    <tr><td colspan="10" align="center" height="30"><font size="4">立项成本核算信息</font></td></tr>
  <tr>
    <td width="50" height="20">品名</td>
    <td width="150" colspan="3">&nbsp;${main.className}</td>
    <td width="43"><div align="right">规格</div></td>
    <td width="90">&nbsp;${main.spec}</td>
    <td width="37"><div align="center">装运期</div></td>
    <td width="121">&nbsp;${main.shipmentDate}</td>
    <td width="48"><div align="center"><div align="center">装运港</div></td>
    <td width="98">&nbsp;${main.shipmentPort}</td>
  </tr>
  <tr>
    <td height="20">金额</td>
    <td width="0" colspan="3">&nbsp;${main.sum}&nbsp;万${main.currency}
    </td>
    <td><div align="right">数量</div></td>
    <td >&nbsp;${main.no}</td>
    <td><div align="center">单位</div></td>
    <td>&nbsp;${other4}</td>
    <td><div align="center">目的港</div></td>
    <td>&nbsp;${main.destinationPort}</td>
  </tr>
  <tr>
    <td rowspan="2" height="20" valign="middle"><div align="center">类别</div></td>
    <td width="14" rowspan="2" valign="middle"><div align="center">序号</div></td>
    <td colspan="4" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
    <td colspan="4" height="20" align="center">立项成本核算(汇率${main.exchangeRate}&nbsp;)</td>
  </tr>
  <tr>
    <td colspan="2" height="20"><div align="center">RMB￥</div>
    <td colspan="2"><div align="center" id="bibei"></div></td>
  </tr>
  <tr>
    <td rowspan="6" height="20">进货成本</br>与价格</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2" height="20"><div  align="center">数量</div></td>
    <td colspan="2" align="center">${rmb1}&nbsp;</td>
    <td colspan="2" align="center">&nbsp;${us1}&nbsp;</td>
  </tr>
  <tr>
    <td height="20">1</td>
    <td colspan="2"><nobr>商品购进成本（FOB）</nobr></td>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		&nbsp;${rmb2}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us2}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">金额</div></td>
    <td colspan="2" align="center">
		&nbsp;${rmb3}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us3}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">2</td>
    <td colspan="4">国外运费(F)</td>
    <td colspan="2" align="center">
		&nbsp;${rmb4}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us4}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">3</td>
    <td colspan="4">国外保险费(I)</td>
    <td colspan="2" align="center">
		&nbsp;${rmb5}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us5}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">4</td>
    <td colspan="4">小计（C&amp;F.CIF.进货总额）</td>
    <td colspan="2" align="center">
		&nbsp;${rmb6}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us6}&nbsp;</div></td>
  </tr>
  <tr>
    <td rowspan="9" height="20">经营费用</td>
    <td>5</td>
    <td colspan="4" height="20">直接流通费合计:</td>
    <td colspan="2" align="center">
		&nbsp;${rmb7}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us7}&nbsp;</td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="4">(1)短途运杂、装卸旨</td>
    <td colspan="2" align="center">
		&nbsp;${rmb8}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us8}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		&nbsp;${rmb9}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us9}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		&nbsp;${rmb10}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us10}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		&nbsp;${rmb11}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us11}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">6</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		&nbsp;${rmb12}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us12}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">7</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		&nbsp;${rmb13}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us13}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">8</td>
    <td colspan="4">效益工资提成</td>
    <td colspan="2" align="center">
		&nbsp;${rmb14}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us14}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">9</td>
    <td colspan="4">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center">
		&nbsp;${rmb15}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us15}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">管理费用</td>
    <td>10</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		&nbsp;${rmb16}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us16}&nbsp;</div></td>
  </tr>
  <tr>
    <td rowspan="3" height="20">财务费用</td>
    <td>11</td>
    <td colspan="4">货款利息</td>
    <td colspan="2" align="center">
		&nbsp;${rmb17}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us17}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">12</td>
    <td colspan="4">退税款占用利息</td>
    <td colspan="2" align="center">
		&nbsp;${rmb18}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us18}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">13</td>
    <td colspan="4">银行结算费</td>
    <td colspan="2" align="center">
		&nbsp;${rmb19}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us19}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20" rowspan="4">各种税费</td>
    <td>14</td>
    <td colspan="4">海关关税
    &nbsp;${other1}&nbsp;
    %,税则号:
    &nbsp;${other2}&nbsp;</td>
    <td colspan="2" align="center">
		&nbsp;${rmb20}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us20}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">15</td>
    <td colspan="4">增值税及附加费（利润×18.87%、14.43%）</td>
    <td colspan="2" align="center">
		&nbsp;${rmb21}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us21}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">16</td>
    <td colspan="4">营业税及附加费（5.55%）</td>
    <td colspan="2" align="center">
		&nbsp;${rmb22}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us22}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">17</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center">
		&nbsp;${rmb23}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us23}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">退税</td>
    <td>18</td>
    <td colspan="4">退税率
    &nbsp;${other3}&nbsp;
    %</td>
    <td colspan="2" align="center">
		&nbsp;${rmb24}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us24}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">手续费</td>
    <td>19</td>
    <td colspan="4">预计利润率（百分比或单位利润）</td>
    <td colspan="2" align="center">
		&nbsp;${rmb25}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us25}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20" rowspan="4">成本与销</br>售收入</td>
    <td>20</td>
    <td colspan="4" height="20">单位成本合计</td>
    <td colspan="2" align="center">
		&nbsp;${rmb26}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us26}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="2" >&nbsp;</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		&nbsp;${rmb27}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us27}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">21</td>
    <td colspan="2">商品销售价</td>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		&nbsp;${rmb28}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us28}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">金额</div></td>
    <td colspan="2" align="center">
		&nbsp;${rmb29}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us29}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20" rowspan="2">换汇成本</br>及盈亏</td>
    <td>22</td>
    <td colspan="4" height="20">换汇成本</td>
    <td colspan="2" align="center">
		&nbsp;${rmb30}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us30}&nbsp;</div></td>
  </tr>
  <tr>
    <td height="20">23</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		&nbsp;${rmb31}&nbsp;
    </td>
    <td colspan="2"><div align="center">&nbsp;${us31}&nbsp;</div></td>
  </tr>
    
    </table>
  </DIV>
</TD><td></td></TR>
<tr><td><br><br></td></tr>
<%
List<CommonChangeDto> changeList = (List<CommonChangeDto>)request.getAttribute("changeList");
int pageSize = 3;
int pageIndex = 2;
if(!changeList.isEmpty()){
for(int i=0;i<changeList.size();i++){ 
      CommonChangeDto dto = changeList.get(i);
      if(i%pageSize==0) pageIndex++;
%>
<%if(i%(pageSize)==0){%>
<TR><TD></TD><TD><DIV id="page<%=pageIndex %>">
    <br><br>
    <table width="740" border="1" cellpadding="4" cellspacing="1" bordercolor="black" class="datatable" align="center">
    <tr><td colspan="10" align="center" height="30"><font size="4">立项变更信息</font></td></tr>
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