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

   	var custstarttime = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'startingTime'
   	});

    var custendtime = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'endTime'
   	});

    var cust_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'isCreditCust',
        allowBlank:false,
        width:100,
        blankText:'...',
        forceSelection:true
     });

    cust_combo.setValue('${cust.isCreditCust}'); 

    var credit_type_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'creditType',
        allowBlank:false,
        width:100,
        blankText:'...',
        forceSelection:true
     });

    credit_type_combo.setValue('${cust.creditType}');


    if (strForm == 'view'){//如果是查看窗口
    	Ext.getDom('savecustcredit').disabled=true;
    	Ext.getDom('clearcustcredit').disabled=true;
    }else if(strForm=='modify'){//如果是修改窗口
    	Ext.getDom('clearcustcredit').disabled=true;
    }else if(strForm=='workflow'){//如果是提交审批窗口
    	Ext.getDom('savecustcredit').disabled=true;
    	Ext.getDom('clearcustcredit').disabled=true;
    }else if(strForm=='iframe'){//流程中可修改窗口
    	Ext.getDom('clearcustcredit').disabled=true;
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

function saveCustFORM(btn){
	btn.disabled=true;

	var isRequest = 'Y';

	if (isRequest == 'Y'){
		Ext.Ajax.request({
			url: 'projectController.spr?action=saveOrUpdateCustCredit',
			params:Form.serialize('custcreditForm'),
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
					Ext.getDom('applyId').value=txt.applyId;
					showMsg('保存成功');
					btn.disabled=false;
				}
			}
		});
	}
}
</script>
</head>
<body>
<form id="custcreditForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
<tr>
	<td >是否授信客户:</td>
	<td>
	<div>
   	 	<select name="isCreditCust" id="isCreditCust">
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
			<option value="1">放货</option>
			<option value="2">代垫</option>
			<option value="3">放货和代垫</option>
		</select>
	 </div>
	</td>
</tr>

<tr>
	<td>客户代垫额度：</td>
	<td>
		<input name="prepayVlaue" value="${cust.prepayVlaue}" type="text" class="text" size="25" />
	</td>
	
	<td>客户发货额度：</td>
	<td>
		<input name="sendValue" value="${cust.sendValue}" type="text" class="text" size="25" />
	</td>
</tr>

<tr>
	<td>客户额度有效起始时间：</td>
	<td>
		<input name="startingTime" value="${cust.startingTime}" type="text" class="text" size="25" readonly="readonly"/>
	</td>
	
	<td>客户额度有效结束时间：</td>
	<td>
		<input name="endTime" value="${cust.endTime}" type="text" class="text" size="25" readonly="readonly"/>
		<input type="hidden" name="applyId" value="${cust.applyId}"/>
		<input type="hidden" name="projectId" value="${cust.projectId}">
	</td>
</tr>
</table>
<table width="756">
<tr>
	<td><div align="center">
	  <input type="button" value="保存" onclick="saveCustFORM(this)" id="savecustcredit"/>
	  <input type="button" value="清空" onclick="resetForm(this.form)" id="clearcustcredit"/>
    </div></td>
</tr>
</table>
</form>
</body>
</html>