<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银行承兑汇票添加页面</title>
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
</style>
</head>
<body>
<div id="main" class="x-hide-display">
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="20%" align="right">添加单位</td>
        <td width="30%" align="left"><input name="orgName" type="text" size="25" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
        <td colspan="2" width="20%" align="right">添加人</td>
        <td width="30%">
        	<input name="creatorName" type="text" size="25" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">公司代码<font color="red">▲</font></td>
        <td>
        	<input name="companyCode" type="text" size="25" tabindex="2" value="${main.companyCode}"/>
        </td>
        <td colspan="2" align="right">出票行<font color="red">▲</font></td>
        <td>
        	<input name="bank" type="text" size="25" tabindex="2" value="${main.bank}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">出票日<font color="red">▲</font></td>
        <td>
        	<input name="beginDate" type="text" size="25" tabindex="2" value="${main.beginDate}" readonly="readonly"/>
        </td>
        <td colspan="2" align="right">到期日<font color="red">▲</font></td>
        <td>
        	<input name="endDate" type="text" size="25" tabindex="2" value="${main.endDate}" maxlength="20" readonly="readonly"/>
        </td>
      </tr>
	  <tr>
        <td align="right">收款人名称<font color="red">▲</font></td>
        <td>
        	<input name="receiver" type="text" size="25" tabindex="2" value="${main.receiver}"/>
        </td>
        <td colspan="2" align="right">单据号</td>
        <td>
        	<input name="billNO" type="text" size="25" tabindex="2" value="${main.creditID}" readonly="readonly"/>
        </td>
      </tr>
	  <tr>
		<td align="right">金额<font color="red">▲</font></td>
        <td>
        	<input name="amount" type="text" size="25" tabindex="2" value="${main.amount}"/>
        </td>
        <td colspan="2" align="right">是否已还款<font color="red">▲</font></td>
        <td>
        	<select name="isPay">
				<option value="">请选择</option>
				<option value="1" <c:if test="${main.isPay=='1'}"> selected </c:if> >是</option>
				<option value="0" <c:if test="${main.isPay=='0'}"> selected </c:if> >否</option>
			</select>
        </td>
	  </tr>
	  <tr>
        <td align="right">保证金比例</td>
        <td>
        	<input name="marginsRate" type="text" size="25" tabindex="5" value="${main.marginsRate}">
        </td>
        <td colspan="2" align="right">保证金金额</td>
        <td>
        	<input name="marginsAmount" type="text" size="25" tabindex="5" value="${main.marginsAmount}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">手续费</td>
        <td>
        	<input name="handlingCharge" type="text" size="25" tabindex="5" value="${main.handlingCharge}"/>
        </td>
        <td colspan="2" align="right">承诺等其他费用</td>
        <td>
        	<input name="otherFee" type="text" size="25" tabindex="5" value="${main.otherFee}"/>
        </td>
      </tr>
</table>
<input type="hidden" name="creditID" value="${main.creditID}">
<input type="hidden" name="createTime" value="${main.createTime}" />
<input type="hidden" name="isAvailable" value="${main.isAvailable}" />
<input type="hidden" name="type" value="${main.type}" />
<input type="hidden" name="from" value="${popfrom}">
</form>
</div>

</body>
</html>
<script type="text/javascript">
var from = '<%=request.getAttribute("popfrom")%>';
Ext.onReady(function(){
    
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var beginDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'beginDate'
   	});
	var endDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'endDate'
   	});
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
				region:"center",
				collapsible: true,
				height:210,
				items:[{contentEl:'main'}]
			},{
				region:"south",
				layout:'fit',
				buttonAlign:'center',
				buttons:[{
					text:'保存',
					hidden: ${!save_button_enable},
					handler:function(){
						var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=save";
						new AjaxEngine('creditController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}
				},{
					text:'关闭',
					handler:function(){
						top.ExtModalWindowUtil.markUpdated();
						top.ExtModalWindowUtil.close();
					}
				}]
		}]
	});
	
});//end of Ext.onReady    

var saveFlag = false;
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

</script>