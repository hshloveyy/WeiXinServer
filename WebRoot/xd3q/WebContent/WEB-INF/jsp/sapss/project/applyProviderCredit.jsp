<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var strForm = '${from}';

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   	var providstarttime = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'providerStartingTime'
   	});

    var providendtime = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'providerEndTime'
   	});

    var provider_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'isCreditProvider',
        allowBlank:false,
        width:100,
        blankText:'...',
        forceSelection:true
     });

    provider_combo.setValue('${provider.isCreditProvider}'); 

    var credit_type_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'creditType',
        allowBlank:false,
        width:100,
        blankText:'...',
        forceSelection:true
     });

    credit_type_combo.setValue('${provider.creditType}');

    if (strForm == 'view'){
    	Ext.getDom('saveprovidercredit').disabled=true;
    	Ext.getDom('clearprovidercredit').disabled=true;
    }else if(strForm=='modify'){
    	Ext.getDom('clearprovidercredit').disabled=true;
    }else if(strForm=='workflow'){
    	Ext.getDom('saveprovidercredit').disabled=true;
    	Ext.getDom('clearprovidercredit').disabled=true;
    }else if(strForm=='iframe'){
    	Ext.getDom('clearprovidercredit').disabled=true;
    }
});

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

function saveProvidFORM(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'projectController.spr?action=saveOrUpdateProvidCredit',
		params:Form.serialize('providercreditForm'),
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			var json = txt['infolion-json'];
			if(json!=null&&json.message!=null&&json.message!='操作成功'){
					top.Ext.Msg.show({
   						title:'警告',
   						closable:false,
   						msg:json.message,
   						buttons:{yes:'关闭'},
   						fn:Ext.emptyFn,
   						icon:Ext.MessageBox.WARNING
					});
					btn.disabled=false;
			}else{
				Ext.getDom('providerApplyId').value=txt.applyId;
				showMsg('保存成功');
				btn.disabled=false;
			}
		}
	});
}
</script>
</head>
<body>
<form id="providercreditForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
<tr>
	
	<td >是否授信供应商:</td>
	<td>
	<div>
   	 	<select name="isCreditProvider" id="isCreditProvider">
			<option value="">请选择</option>
			<option value="1">有</option>
			<option value="2">没有</option>
		</select>
	 </div>
	</td>

	<td >授信类型:</td>
	<td>
	<div>
   	 	<select name="creditType" id="creditType">
			<option value="">请选择</option>
			<option value="1">供应商自营出口*和部分关联下家的内贸</option>
			<option value="2">供应商自营业务类型</option>
		</select>
	 </div>
	</td>
</tr>

<tr>
	<td>供应商信用总额度：</td>
	<td>
		<input name="providerTotalValue" value="${provider.providerTotalValue}" type="text" class="text" size="25" />
	</td>
	<td></td>
	<td></td>
</tr>

<tr>
	<td>供应商额度有效起始时间：</td>
	<td>
		<input name="providerStartingTime" value="${provider.providerStartingTime}" type="text" class="text" size="25" readonly="readonly"/>
	</td>
	
	<td>供应商额度有效结束时间：</td>
	<td>
		<input name="providerEndTime" value="${provider.providerEndTime}" type="text" class="text" size="25" readonly="readonly"/>
		<input type="hidden" name="providerApplyId" value="${provider.providerApplyId}"/>
		<input type="hidden" name="projectId" value="${provider.projectId}">
	</td>
</tr>
</table>
<table width="756">
<tr>
	<td><div align="center">
	  <input type="button" value="保存" onclick="saveProvidFORM(this)" id="saveprovidercredit"/>
	  <input type="button" value="清空" onclick="resetForm(this.form)" id="clearprovidercredit"/>
    </div></td>
</tr>
</table>
</form>
</body>
</html>