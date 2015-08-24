<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进口货物付款页面</title>
<style type="text/css">
.add {
	background-image: url(<%=request.getContextPath()%>/images/fam/add.gif ) !important;
}
.delete {
	background-image: url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update {
	background-image: url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find {
	background-image: url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<style type="text/css" media=print>
	.noprint{display : none }
</style>
</head>
<body>
<div id="topDiv" class="x-hide-display">
<div id="div_pur_history" class="x-hide-display"></div>
<form id="headForm" name="headForm">
<input type="hidden" name="tradeType" id="tradeType" value="${tradeType}"/>
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" id="paymentId" name="paymentId" value="${paymentImportsInfo.paymentId}"/>
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right" >到单号<font color="red">▲</font></td>
		<td align="left">
			<input name="pickListId" id="pickListId" type="hidden" value="${paymentImportsInfo.pickListId}" />
			<input name="pickListNo" id="pickListNo" type="text" value="${paymentImportsInfo.pickListNo}"/>
			<input type="button" id="btn1" value="..." onclick="selectPickListInfoWin()" title="选择进口到单">
			<a href="#" onclick="showContract()">查看合同</a>
		</td>
		<td align="right">付款类型<font color="red">▲</font></td>
		<td align="left">
		<select name="payType1" id="payType1" onchange="changeYH(this)">
			<option value=''>请选择</option>
			<c:forEach items="${payTypes}" var="row">
				<option value="${row.code}">${row.title}</option>
			</c:forEach>
		</select>
		<input type="hidden" id="payType" name="payType" value="${paymentImportsInfo.payType}"/>
		</td>
	</tr>
</table>
</form>
</div>
<div id="bottomDiv" >
<form id="mainForm" name="mainForm">
<table width="100%" border="1" cellpadding="4" cellspacing="1"	bordercolor="#6699FF" class="datatable">
	<tr>
		<td  align="right">申请付款金额<font color="red">▲</font></td>
		<td  align="left">
			<input name="payValue" type="text"size="20" value="${paymentImportsInfo.payValue}" onchange="judgeFloat(this,'申请付款金额')"/>
		</td>
		<td  align="right">收款银行<c:if test="${tradeType=='tt'}"><font color="red">▲</font></c:if></td>
		<td  align="left">
			<input name="payBank" type="text" size="20" value="${paymentImportsInfo.payBank}" />
		</td>
		<td  align="right"  >收款帐号<c:if test="${tradeType=='tt'}"><font color="red">▲</font></c:if></td>
		<td  align="left">
			<input name="payAccount" type="text" size="20" value="${paymentImportsInfo.payAccount}" />
		</td>
	</tr>
	<tr>
		<td  align="right">(即期/承兑到期)付款日：</td>
		<td  align="left">
			<input name="payTime" id="payTime" type="text" value="${paymentImportsInfo.payTime}" readonly="readonly"/>		
		</td>
		<td align="right"  >收款单位：</td>
		<td><input type="text" name="payUnit" id="payUnit" value="${paymentImportsInfo.payUnit}"/></td>
		
		<td  align="right">付款人：</td>
		<td  align="left">
			<input name="payer" type="text"	size="20" value="${paymentImportsInfo.payer}" />
		</td>
		
	</tr>
	<tr>
		<td  align="right"  >币别<font color="red">▲</font></td>
		<td  align="left" >
			<input name="currency" id="currency" type="text" size="20" value="${paymentImportsInfo.currency}" readonly="readonly"/>
		</td>
		<td align="right"  >押汇币别：</td>
		<td><input type="text" name="documentaryCurrency" id="documentaryCurrency" value="${paymentImportsInfo.documentaryCurrency}"/></td>
		
		<td align="right"  >押汇日：</td>
		<td><input type="text" name="documentaryDate" id="documentaryDate" value="${paymentImportsInfo.documentaryDate}" readonly="readonly"/></td>
	</tr>
	<tr>
		<td align="right"  >押汇到期付款日：</td>
		<td><input type="text" name="payDate" id="payDate" value="${paymentImportsInfo.payDate}" readonly="readonly"/></td>
		<td align="right"  >押汇期限：</td>
		<td><input type="text" name="documentaryLimit" id="documentaryLimit" value="${paymentImportsInfo.documentaryLimit}"/></td>
		<td  align="right" >押汇利率：</td>
		<td>
			<input name="exchangeRate" type="text"	size="20" value="${paymentImportsInfo.exchangeRate}" onchange="judgeFloat(this,'押汇利率')"/>
		</td>
	</tr>
	<tr>
		<td align="right"  >实际付款日期(押汇)：</td>
		<td  align="left">
			<input name="payRealTime" id="payRealTime" readonly="readonly" type="text" size="20" value="${paymentImportsInfo.payRealTime}"/>
		</td>
		<td  align="right"  >实际付款金额(押汇)：</td>
		<td  align="left">
			<input name="payRealValue" id="payRealValue" readonly="readonly" type="text" size="20" value="${paymentImportsInfo.payRealValue}" onchange="judgeFloat(this,'实际付款金额(押汇)')"/>
		</td>
		<td align="right"  >实际押汇期限：</td>
		<td><input type="text" name="documentaryRealLimit" readonly="readonly" id="documentaryRealLimit" value="${paymentImportsInfo.documentaryRealLimit}"/></td>
	</tr>
		
	<tr>
		<td align="right"  >实际押汇汇率：</td>
		<td><input type="text" name="documentaryRealRate" readonly="readonly" id="documentaryRealRate" value="${paymentImportsInfo.documentaryRealRate}" onchange="judgeFloat(this,'实际押汇汇率')"/></td>
		<td  align="right"  >押汇利息：</td>
		<td  align="left">
			<input type="text" name="documentaryInterest" readonly="readonly" id="documentaryInterest" value="${paymentImportsInfo.documentaryInterest}" onchange="judgeFloat(this,'押汇利息')"/>
		</td>
	    <td align="right"  >核销单号：</td>
		<td><input type="text" name="writeListNo" id="writeListNo" value="${paymentImportsInfo.writeListNo}" readonly="readonly"/></td>
	</tr>
	<tr>
		<td  align="right"  >付款用途：</td>
		<td  align="left" colspan="5">
			<textarea  cols="50"	rows="2" id="payUse" name="payUse" style="width:90%;overflow-y:visible;word-break:break-all;">${paymentImportsInfo.payUse}</textarea>
		</td>
	</tr>
	<tr>
		<td  align="right"  >备注：</td>
		<td  align="left" colspan="5">
			<textarea cols="50"	rows="2"  id="cmd" name="cmd" style="width:90%;overflow-y:visible;word-break:break-all;">${paymentImportsInfo.cmd}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="center">
		<div id="btnGroup">
			<input type="button" onclick="save()" value="保存"/>
			<input type="button" id="submitBtn" onclick="submitWorkflow()" value="提交"/>
			<input type="button" onclick="closeForm()" value="关闭"/>
		</div>
		<div id="modify_exchangeRate">
			<input type="button" onclick="saveExchangeRate()" value="保存押汇信息"/>
		</div>
		<div id="modify_writeNo">
			<input type="button" onclick="saveWriteNo()" value="保存核销单号"/>
		</div>
		</td>
	</tr>
</table>
</form>
</div>
<div id="ruleFile" class="x-hide-display" ></div>
<div id="history" class="x-hide-display" ></div>
</body>
<fiscxd:fileUpload divId="ruleFile" erasable="true"  increasable="true" recordIdHiddenInputName="444"
	 resourceId="2222" resourceName="33333" recordId="${paymentImportsInfo.paymentId}"></fiscxd:fileUpload>

<script type="text/javascript">
var ifInWorkflow=true;
var paymentImportsInfogrid; 
var actionType;
var workforkHeight='${from}';
var payDate;
var documentaryDate;
if(workforkHeight==null || workforkHeight=='')
	workforkHeight=305;
else
	workforkHeight=475;
var src='pickListInfoController.spr?action=viewPickListInfo&pickListType=${path}&pickListId=${paymentImportsInfo.pickListId}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	//如果非空,表示流程页面
   	if(''!='${type}'){
		$('btnGroup').innerHTML='';
		ifInWorkflow=false;
		$('payType1').disabled=true;
		$('btn1').disabled=true;
		$('pickListNo').readOnly=true;
   	}

	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
          {
            title: '详细信息',
            layout:'form',
            autoHeight:true,
      		items:[{
    			contentEl:'topDiv',
    			miniHeight:30,
    			autoHeight:true,
    			collapsed:false,
    			collapsible: true
    		},{
    			height:workforkHeight,
    			title:'到单信息',
    			html:'<iframe src='+src+' width="100%" height="450" id="iframe"/>',
    			id:'iframeItem',
    			autoScroll:true,
    			collapsible:true,
    			collapsed:false
    		},{
    			contentEl:'bottomDiv',
    			height:300,
    			autoHeight:true,
    			collapsible:true,
    			collapsed:false
    		}]
           },{
               contentEl:'ruleFile',
               id:'fileEl',
               title: '附件信息'
           },{
               disabled:ifInWorkflow,
               contentEl:'history',
               title: '付款审批历史记录'
              }
              <c:if test="${paymentImportsInfo.examineState=='7'||paymentImportsInfo.examineState=='8'||paymentImportsInfo.examineState=='9'}">
         	 ,{
	           	title:'特批审批历史记录',
	           	contentEl: 'div_pur_history',
	           	id:'purhistoryinfo',
				name:'purhistoryinfo',
				autoScroll:true,
	           	layout:'fit'
      		  }
      		 </c:if>
           ]
    });
	
	var payTime = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'payTime'
   	});
	var payRealTime = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		disabled:true,
		applyTo:'payRealTime'
   	});
	 payDate = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'payDate'
   	});
	 documentaryDate = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'documentaryDate'
   	});
   	//非修改页面
   	if('${type}'!='' && 'modify'!='${type}'){
   	 	var arr= Ext.query('input[type=text]');
   	 	for(var i=0;i<arr.length;i++){
   	 		arr[i].readOnly=true;
   	 	}
   	 	payTime.disable();
   	 	$('payType1').disabled=true;
   	 	$('btn1').disabled=true;
   	 	$('payUse').disabled=true;
   	 	//$('cmd').disabled=true;
   	}
   	//填写押汇信息
   	if('writeRealInfo'=='${type}'){
   		Ext.getDom('payRealTime').readOnly = false;
   		Ext.getDom('payRealValue').readOnly = false;
   		Ext.getDom('documentaryRealLimit').readOnly = false;
   		Ext.getDom('documentaryRealRate').readOnly = false;
   		Ext.getDom('documentaryInterest').readOnly = false;
   		payRealTime.enable();
 	}
   	//填写核销单号
   	if('writeListNo'=='${type}'){
   		Ext.getDom('writeListNo').readOnly = false;
 	}
	//如果为空
 	if(('${type}'=='') && ('${isNew}'!='')){
		$('submitBtn').disabled=true;
   	}
   	$('payType1').value='${paymentImportsInfo.payType}';
	if($('payType1').value!='2'){
		$('documentaryLimit').readOnly=true;
		documentaryDate.disable();
		payDate.disable();
		$('documentaryCurrency').readOnly=true;
	}else if('${type}'!=''){
		$('documentaryLimit').readOnly=true;
		documentaryDate.disable();
		payDate.disable();
		$('documentaryCurrency').readOnly=true;
	}
	if('${type}'=='writeYHInfo'){
		$('documentaryLimit').readOnly=false;
		documentaryDate.enable();
		payDate.enable();
		$('documentaryCurrency').readOnly=false;
	    $('payBank').readOnly=false;
   	 	$('payAccount').readOnly=false;
	}
	if('${type}'=='payMoney'){
		$('cmd').disabled=false;
	}
	var md = '${modify_YH_rate}';
	if(md=='true'){
		$('exchangeRate').readOnly=false;
		$('documentaryLimit').readOnly=false;
		documentaryDate.enable();
		payDate.enable();
		$('documentaryCurrency').readOnly=false;
		
	}else
	   	$('modify_exchangeRate').innerHTML='';
	   	
	   	
	var modify_writeNo = '${modify_writeNo}';
	if(modify_writeNo=='true'){
		$('writeListNo').readOnly=false;		
	}else
	   	$('modify_writeNo').innerHTML='';

	ruleFile_ns_ds.load({params:{start:0, limit:10,recordId:'${paymentImportsInfo.paymentId}'}});
//---------------------------------------
	new Ext.ToolTip({
        target:'payBank',
        width:200,
        html: $('payBank').value==''?'':$('payBank').value,
        trackMouse:true
    });
	new Ext.ToolTip({
        target:'payUnit',
        width:200,
        html: $('payUnit').value==''?'':$('payUnit').value,
        trackMouse:true
    });

	
});//end of Ext.onReady   
function judgeFloat(obj,name){
	var v = obj.value;
	if(!Utils.isFloatValue(v)){
		top.Ext.Msg.show({title:'提示',msg:'<font color="red">'+name+'</font>含有非数字字符',buttons:Ext.Msg.CANCEL,fn:function(btn,text){
				obj.focus();
				return false;
			}});
	}
}

// 保存
function save(){
	var paras = '?action=save&'+Form.serialize('headForm')+'&'+Form.serialize('mainForm');
	new AjaxEngine('paymentImportsInfoController.spr',{method:"post", parameters: paras, onComplete: callBackHandle});
}
//AjaxEngine回调函数
var customCallBackHandle=function(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if(actionType=='submit'){
		top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
	if(customField!=null && '保存成功'==customField.returnMessage){
		$('paymentId').value=customField.paymentId;
		top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	}else{
		top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	}
		
}

//提示窗口
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

//提交工作流审批
function submitWorkflow(){

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				actionType='submit';
   	   			var parm='?action=submitWorkflow&tradeType=${tradeType}&businessId='+$('paymentId').value+'&cmd='+$('cmd').value;
				new AjaxEngine('paymentImportsInfoController.spr',{method:'post', parameters: parm, onComplete: callBackHandle});
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}	

//关闭窗体
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

function selectPickListInfoWin(){
	top.ExtModalWindowUtil.show('查询进口提货单信息',
	'paymentImportsInfoController.spr?action=selectPickListInfo&examineState=3&tradeType=${tradeType}',
	'',
	selectPickListInfoCallBack,
	{width:900,height:450});
}
function selectPickListInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('pickListId').value=returnvalue.PICK_LIST_ID;
	Ext.getDom('pickListNo').value=returnvalue.PICK_LIST_NO;
	Ext.getDom('currency').value=returnvalue.EKKO_WAERS;
	Ext.getDom('writeListNo').value=returnvalue.WRITE_LIST_NO;
    Ext.getDom('payTime').value=returnvalue.PAY_DATE;
    Ext.getDom('payUnit').value=returnvalue.PROVIDER;
    

	window.frames('iframe').location.replace('pickListInfoController.spr?action=viewPickListInfo&pickListType=${path}&pickListId='+returnvalue.PICK_LIST_ID);
}
function changeYH(obj){
    $('payType').value=obj.value;
	//选择-押汇
	if(obj.value=='2' || '${type}'=='writeYHInfo'){
		$('documentaryLimit').readOnly=false;
		documentaryDate.enable();
		payDate.enable();
		$('documentaryCurrency').readOnly=false;
	}else{
		$('documentaryLimit').readOnly=true;
		documentaryDate.disable();
		payDate.disable();
		$('documentaryCurrency').readOnly=true;

		$('documentaryLimit').value='';
		documentaryDate.setValue('');
		payDate.setValue('');
		$('documentaryCurrency').value='';
	}
}
function showContract(){
	//'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
	top.ExtModalWindowUtil.show('查看采购合同',
			'pickListInfoController.spr?action=showContract&businessRecordId='+$('pickListId').value,
			'',
			'',
			{width:800,height:550});
	
}
//保存押汇汇率
function saveExchangeRate(){
	var paras = '?action=saveExchangeRate&exchangeRate='+$('exchangeRate').value+'&paymentId=${paymentImportsInfo.paymentId}';
	    paras +='&documentaryLimit='+$('documentaryLimit').value;
	    paras +='&documentaryDate='+$('documentaryDate').value;
	    paras +='&payDate='+$('payDate').value;
	    paras +='&documentaryCurrency='+$('documentaryCurrency').value;
	new AjaxEngine('paymentImportsInfoController.spr',{method:"post", parameters: paras, onComplete: callBackHandle});
	
}
//保存核销单号
function saveWriteNo(){
	var paras = '?action=saveWriteNo&writeListNo='+$('writeListNo').value+'&paymentId=${paymentImportsInfo.paymentId}';
	new AjaxEngine('paymentImportsInfoController.spr',{method:"post", parameters: paras, onComplete: callBackHandle});
	
}
</script>
</html>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${paymentImportsInfo.paymentId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:workflow-taskHistory divId="div_pur_history" businessRecordId="${purId}" width="750"></fiscxd:workflow-taskHistory>
