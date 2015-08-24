<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<jsp:directive.page import="java.math.BigDecimal"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>立项申请页面</title>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
tr{
	/*background-color:expression('white,#ffffcc'.split(',')[rowIndex%2]);*/
}
</style>
<%
BigDecimal INTEREST_RATE = new BigDecimal(7); 
request.setAttribute("INTEREST_RATE",INTEREST_RATE);
%>
<script type="text/javascript">
var tempTab={};
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {contentEl:'main', title: '项目名称及客户信息'},
            {contentEl:'detail',id:'detailEl',title: '立项测算表'},
            {contentEl:'rule',id:'fileEl', title: '立项附件',listeners:{activate:handlerActivate}}
        ]
    });
    tempTab = tabs;
    Ext.getDom('projectId').value='<%=request.getAttribute("projectId")%>';
    Ext.getDom('recordhdinputname').value='<%=request.getAttribute("projectId")%>';
    
});//end of Ext.onReady    
function handlerActivate(){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}

</script>
</head>
<body>
<div id='progressBar'></div>
<div id="main" class="x-hide-display">
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
  <tr >
    <td width="755" height="196">
    <table width="755" align="center" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td colspan="2"><div align="center"  >承 办 单 位</div></td>
        <td width="403" align="left"><input name="orgName" type="text" size="55" tabindex="1" value="${main.orgName}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >承   办   人</div></td>
        <td>
        	<input name="nuderTaker" type="text" size="55" tabindex="2" value="${main.nuderTaker}" readonly="readonly"/>
        	<input type="hidden" name="deptId" value="${main.deptId}"/>
        </td>
        
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >项 目 编 号</div></td>
        <td><input name="projectNo" type="text" size="55" tabindex="3" readonly="readonly" value="${main.projectNo}"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  ><c:if test="${main.tradeType=='1'||main.tradeType=='6'}">代理协议号</c:if><c:if test="${main.tradeType!='1'&&main.tradeType!='6'}">项 目 编 号</c:if></div></td>
        <td><input name="oldProjectNo" type="text" size="55" tabindex="3" value="${main.oldProjectNo}"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >项 目 名 称</div></td>
        <td><input name="projectName" type="text" size="55" tabindex="4" value="${main.projectName}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >申 报 日 期</div></td>
        <td><input name="applyTime" type="text" size="55" tabindex="5" value="${main.applyTime}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >终 止 时 间</div></td>
        <td><input name="availableDataEnd" type="text" size="55" tabindex="5" value="${main.availableDataEnd}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >是 否 授 信</div></td>
        <td>
        	<select id="isCredit" name="isCredit" tabindex="27">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.isCredit=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.isCredit=='N'}"> selected </c:if>>否</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JS<font color="red">*</font>
			
			<select id="cmd" name="cmd" tabindex="27">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.cmd=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.cmd=='N'}"> selected </c:if>>否</option>
			</select>
        </td>
      </tr>
       <tr>
        <td colspan="2"><div align="center"  >授 信 描 述</div></td>
        <td><textarea name="creditDescription" cols="57" rows="2" tabindex="3" style="overflow-y:visible;word-break:break-all;"  >${main.creditDescription}</textarea></td>
      </tr>
      <tr>
        <td width="13" rowspan="4" align="center" valign="middle"  >买 方 / 委托方</td>
        <td width="166" height="25"><div align="center"  >客 户 名 称</div></td>
        <td><input name="customerLinkMan" value="${main.customerLinkMan}" type="text" size="55" readonly="readonly" tabindex="6"/></td>
      </tr>
      <tr>
        <td><div align="center"  >付 款 方 式</div></td>
        <td><input name="customerPayType" type="text" size="55" tabindex="7" value="${main.customerPayType}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td><div align="center"  >结 算 方 式</div></td>
        <td><input name="customerBalanceType" type="text" size="55" tabindex="8" value="${main.customerBalanceType}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td><div align="center"  >说       明</div></td>
        <td><textarea name="customerExplain" cols="57" rows="2" tabindex="9" readonly="readonly">${main.customerExplain}</textarea></td>
      </tr>
      <tr>
        <td width="13" rowspan="4"  >卖 方 / 委托方</td>
        <td width="166" height="25"><div align="center"  >供应商 名称</div></td>
        <td><input name="providerLinkMan" value="${main.providerLinkMan}" type="text" size="55" readonly="readonly" tabindex="10"/></td>
      </tr>
      <tr>
        <td><div align="center"  >付 款 方 式</div></td>
        <td><input name="providerPayType" type="text" size="55" tabindex="11" value="${main.providerPayType}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td><div align="center"  >结 算 方 式</div></td>
        <td><input name="providerBalanceType" type="text" size="55" tabindex="12" value="${main.providerBalanceType}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td><div align="center"  >说       明</div></td>
        <td><textarea name="providerExplain" cols="57" rows="2" tabindex="12" readonly="readonly">${main.providerExplain}</textarea></td>
      </tr>
      
    </table>
    </td>
  </tr>
  <tr >
  <td  width="755" align="center">
  	<table width="750">
  		<tr>
  			<td width="49%" align="right">
  			</td>
  			<td width="1%" >
  				<input type="hidden" name="projectId" />
  				<input type="hidden" name="tradeType" value="${main.tradeType}"/>
  				<input type="hidden" name="projectState" value="${main.projectState}"/>
  				<input type="hidden" name="providerId" value="${main.providerId}"/>
  				<input type="hidden" name="providerAddress" value="${main.providerAddress}"/>
  				<input type="hidden" name="providerTel" value="${main.providerTel}"/>
  				<input type="hidden" name="proiderCreditGrade" value="${main.proiderCreditGrade}"/>
  				<input type="hidden" name="customerAddress" value="${main.customerAddress}"/>
  				<input type="hidden" name="customerTel" value="${main.customerTel}"/>
  				<input type="hidden" name="customerCreditGrade" value="${main.customerCreditGrade}"/>
  				<input type="hidden" name="customerId" value="${main.customerId}"/>
  				<input type="hidden" name="underTakerId" value="${main.underTakerId}"/>
  				<input type="hidden" name="sum" value="${main.sum}"/>
  				<input type="hidden" name="other5" value="${other5}"/>
  				
  			</td>
  			 <td  width="50%" align="left">
  			</td>
  		</tr>
  	</table>
 </td>
	</tr>
</table>
<input type="hidden" name="taskId" value="${taskId}">
</form>
</div>
<div id="detail" class="x-hide-display">
<form id="detailForm">
<c:if test="${main.tradeType=='7'||main.tradeType=='10'}">
<c:if test="${main.deptId=='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="costEvaluation/hKhomeTrade.jsp"%>
</c:if>
<c:if test="${main.deptId!='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="costEvaluation/homeTrade.jsp"%>
</c:if>
</c:if>
<c:if test="${main.tradeType=='1'||main.tradeType=='3'||main.tradeType=='8'||main.tradeType=='9'}">
<%@ include file="costEvaluation/import.jsp"%>
</c:if>
<c:if test="${main.tradeType=='2'||main.tradeType=='4'}">
<%@ include file="costEvaluation/export.jsp"%>
</c:if>
<c:if test="${main.tradeType=='5'||main.tradeType=='6'}">
<%@ include file="costEvaluation/proxy.jsp"%>
</c:if>
</form>
</div>
<div id="rule" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" erasable="true"  increasable="true"
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
