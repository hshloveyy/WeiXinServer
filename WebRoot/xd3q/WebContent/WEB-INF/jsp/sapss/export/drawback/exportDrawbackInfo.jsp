<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>收汇详细页面</title>
</head>
<body>
<form name="detailForm">
<table width="800" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="800" height="100%" valign="top"><table width="800" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="189">
        <div align="right">出口货物通知单号<font color="red">▲</font></div></td>
        <td>
        	<input type="text" name="exportApplyNo" value="${exportDrawbackInfo.exportApplyNo}" readonly="readonly"/>
        	<input type="hidden" name="exportApplyId" value="${exportDrawbackInfo.exportApplyId}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectExportApplyInfo()" />
        </td>
        <td align="right">核销单号：</td>
        <td><input type="text" name="writeNo" value="${exportDrawbackInfo.writeNo}" /></td>
      </tr>
      
      
      <tr>
        <td><div align="right">合同号:</div></td>
        <td><input type="text" name="hdrq" value="${exportDrawbackInfo.hdrq}" /></td>
        <td><div align="right">预录入号:</div></td>
        <td><input type="text" name="ylrh" value="${exportDrawbackInfo.ylrh}" /></td>
       
        
      </tr>
      <tr>
        <td><div align="right">出口日期 :</div></td>
        <td><input type="text" name="ckrq" value="${exportDrawbackInfo.ckrq}" /></td>
        <td><div align="right">报关单号:</div></td>
        <td><input type="text" name="bgdh" value="${exportDrawbackInfo.bgdh}" /></td>
        
      </tr>
      
      <tr>
        <td><div align="right">核销日期:</div></td>
        <td><input type="text" name="writeDate" value="${exportDrawbackInfo.writeDate}"/></td>
        <td><div align="right">退税率:</div></td>
        <td><input type="text" name="drawbackRate" value="${exportDrawbackInfo.drawbackRate}" /></td>
      </tr>
      
      <tr>
        <td><div align="right">成交币别:</div></td>
        <td><input type="text" name="cjbb" value="${exportDrawbackInfo.cjbb}" /></td>
        <script language="javascript">
        if($('cjbb').value=='') $('cjbb').value='USD';
        </script>
        <td><div align="right">退税申报日:</div></td>
        <td><input type="text" name="drawbackDate" value="${exportDrawbackInfo.drawbackDate}"/></td>
      </tr>
      <tr>
        
        <td><div align="right">报关数量:</div></td>
        <td><input type="text" name="bgsl" value="${exportDrawbackInfo.bgsl}" /></td>
        <td><div align="right">退税申报额:</div></td>
        <td><input type="text" name="drawbackValue" value="${exportDrawbackInfo.drawbackValue}" /></td>
      </tr>
      <tr>
         <td><div align="right">报关金额:</div></td>
        <td><input type="text" name="bgje" value="${exportDrawbackInfo.bgje}" /></td>
        <td><div align="right">实际退税额:</div></td>
        <td><input type="text" name="drawbackReal" value="${exportDrawbackInfo.drawbackReal}" /></td>
      </tr>
      
      
      <tr>
        <td><div align="right">保税区业务:</div></td>
        <td><input type="text" name="bgdw" value="${exportDrawbackInfo.bgdw}" /></td>
        <td><div align="right">税款到帐日:</div></td>
        <td><input type="text" name="skdzr" value="${exportDrawbackInfo.skdzr}" /></td>
        
      </tr>
      
      
      <tr>
        <td><div align="right">核销金额:</div></td>
        <td><input type="text" name="hxje" value="${exportDrawbackInfo.hxje}" /></td>
        <td><div align="right">增值税发票金额:</div></td>
        <td><input type="text" name="zzsfpje" value="${exportDrawbackInfo.zzsfpje}" /></td>
      </tr>
      
      <tr>
        <td><div align="right">海运费:</div></td>
        <td><input type="text" name="shipingValue" value="${exportDrawbackInfo.shipingValue}" /></td>
        <td><div align="right">换汇比:</div></td>
        <td><input type="text" name="hhb" value="${exportDrawbackInfo.hhb}" /></td>
      </tr>
      <tr>
        <td><div align="right">海关商编:</div></td>
        <td><input type="text" name="hgsb" value="${exportDrawbackInfo.hgsb}" /></td>
        <td><div align="right">出口口岸:</div></td>
        <td><input type="text" name="ckka" value="${exportDrawbackInfo.ckka}" /></td>
      </tr>
      <tr>
        <td><div align="right">运抵国:</div></td>
        <td colspan="3"><input type="text" name="mdg" value="${exportDrawbackInfo.mdg}" /></td>
      </tr>
      <tr>
        <td><div align="right">变更记录及附注:</div></td>
        <td colspan="3">
        	<textarea rows="3" cols="60" name="mark">${exportDrawbackInfo.mark}</textarea>
        </td>
     </tr>
      <tr>
        <td colspan="4" align="center">
        	<input type="hidden" name="exportDrawbackId" value="${exportDrawbackInfo.exportDrawbackId }"/>
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/>
        </td>
     </tr>
      
    </table></td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectExportApplyInfo(){
	top.ExtModalWindowUtil.show('查询出口货物通知单信息',
	'exportIncomeController.spr?action=selectExportApplyInfo',
	'',
	selectExportApplyInfoCallback,
	{width:900,height:450});
}
var selectExportApplyInfoCallback = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('exportApplyNo').value=returnvalue.NOTICE_NO;
	$('exportApplyId').value=returnvalue.EXPORT_APPLY_ID;
	$('writeNo').value=returnvalue.WRITE_NO;
	//$('ckka').value=returnvalue.EXPORT_PORT;
	//$('mdg').value=returnvalue.DESTINATIONS;
	$('bgsl').value=returnvalue.TOTAL_QUANTITY;
	$('bgje').value=returnvalue.TOTAL_MONEY;
	$('hdrq').value=returnvalue.CONTRACT_PAPER_NO;
	
}

function saveForm(){
 if(Ext.getDom('exportApplyNo').value==''){
	   top.ExtModalWindowUtil.alert('提示','出口货物通知单号必须填写！');
	   return;
	}
	var parm="?action=save&"+Form.serialize('detailForm');
	new AjaxEngine('exportDrawbackController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	$('exportDrawbackId').value=customField.id;
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
		applyTo:'writeDate'
   	});
	var time1 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'drawbackDate'
   	});
   	var time2 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'ckrq'
   	});
   	var time2 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'skdzr'
   	});
   	
});
</script>
</html>
