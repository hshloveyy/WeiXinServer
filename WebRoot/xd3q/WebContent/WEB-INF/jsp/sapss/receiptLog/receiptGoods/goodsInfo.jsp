<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>收货详细页面</title>
</head>
<body>
<form name="detailForm">
<table width="585" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="765" height="100%" valign="top"><table width="765" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="189"><div align="right">到单号<font color="red">▲</font></div></td>
        <td>
       	<input type="text" name="receiptNo" id='receiptNo' value="${receiptGoods.receiptNo}" readonly="readonly"/>
       	<input type="button" value="..." onclick="selectPickListInfoWin()" />
       	<input type="hidden" name="receiptId" value="${receiptGoods.receiptId}" />
       	</td>
        <td><div align="right">海关商编:</div></td>
        <td><input type="text" name="hgsb" value="${receiptGoods.hgsb}"/></td>
        </tr>
      <tr>
        <td><div align="right">核销单号:</div></td>
        <td><input type="text" name="writeNo" value="${receiptGoods.writeNo}"/></td>
         <td><div align="right">成交币别:</div></td>
        <td><input type="text" name="cjbb" value="${receiptGoods.cjbb}"/></td>
        </tr>
      <tr>
        <td><div align="right">进口日期:</div></td>
        <td><input type="text" name="importDate" value="${receiptGoods.importDate}"/></td>
         <td><div align="right">报关数量:</div></td>
        <td><input type="text" name="bgsl" value="${receiptGoods.bgsl}"/></td>
        </tr>
      <tr>
        <td><div align="right">报关单号:</div></td>
        <td><input type="text" name="customeNo" value="${receiptGoods.customeNo}" /></td>
         <td><div align="right">报关单位:</div></td>
        <td><input type="text" name="bgdw" value="${receiptGoods.bgdw}"/></td>
        </tr>
      <tr>
        <td><div align="right">预录入号:</div></td>
        <td><input type="text" name="preWrCd" value="${receiptGoods.preWrCd}"/></td>
         <td><div align="right">成交方式:</div></td>
        <td><input type="text" name="cjfs" value="${receiptGoods.cjfs}"/></td>
        </tr>
      <tr>
        <td><div align="right">报关金额:</div></td>
        <td><input type="text" name="customeCash" value="${receiptGoods.customeCash}" /></td>
         <td><div align="right">核销日期:</div></td>
        <td><input type="text" name="hxrq" value="${receiptGoods.hxrq}"/></td>
        </tr>
      <tr>
        <td><div align="right">报关口岸:</div></td>
        <td><input type="text" name="customePort" value="${receiptGoods.customePort}" /></td>
         <td><div align="right">退汇金额:</div></td>
        <td><input type="text" name="thje" value="${receiptGoods.thje}"/></td>
        </tr>
      <tr>
        <td><div align="right">进口国别:</div></td>
        <td><input type="text" name="importCountry" value="${receiptGoods.importCountry}" /></td>
         <td><div align="right">退汇日期:</div></td>
        <td><input type="text" name="thrq" value="${receiptGoods.thrq}"/></td>
        </tr>
      <tr>
       <td><div align="right">核销金额:</div></td>
        <td><input type="text" name="hxje" value="${receiptGoods.hxje}"/></td>
         <td><div align="right">核销进度:</div></td>
        <td>
        <select name="hxjd" id="hxjd"><option value="已核销">已核销</option><option value="部分核销">部分核销</option><option value="未核销">未核销</option><option value="银行核销">银行核销</option></select>
        <script>
        Ext.getDom('hxjd').value='${receiptGoods.hxjd}';
        </script>
        </td>
        </tr>
      <tr>
       <td><div align="right">单价:</div></td>
        <td><input type="text" name="dj" value="${receiptGoods.dj}"/></td>
        <td></td>
        <td>
        </td>
        </tr>
      <tr>
        <td><div align="right">备注:</div></td>
        <td colspan="3"><textarea rows="3" cols="50" name="remark">${receiptGoods.remark}</textarea></td>
        </tr>
      <tr>
        <td colspan="4" align="center">
        	<input type="hidden" name="infoId" value="${receiptGoods.infoId}"/>
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/></td>
        </tr>
      
    </table></td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectPickListInfoWin(){
	top.ExtModalWindowUtil.show('查询进口到单信息',
	'paymentImportsInfoController.spr?action=selectPickListInfo&examineState=3',
	'',
	selectPickListInfoCallBack,
	{width:900,height:450});
}
var selectPickListInfoCallBack = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('receiptNo').value=returnvalue.PICK_LIST_NO;
	$('receiptId').value=returnvalue.PICK_LIST_ID;
	$('writeNo').value=returnvalue.WRITE_LIST_NO1;
	$('hxje').value=returnvalue.PAY_VALUE;
	$('cjbb').value=returnvalue.CURRENCY_ID;
}

function saveForm(){
	if(Ext.getDom('receiptNo').value==''){
	   top.ExtModalWindowUtil.alert('提示','到单号必须填写！');
	   return;
	}
	var parm="?action=save&"+Form.serialize('detailForm');
	new AjaxEngine('receiptGoodsController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	if($('infoId').value=='') $('infoId').value=customField.infoId;
	top.ExtModalWindowUtil.alert('提示',customField.ok);
}
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var time = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'importDate'
   	});
   	var thrq = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'thrq'
   	});
   	var hxrq = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'hxrq'
   	});
});
</script>
</html>
