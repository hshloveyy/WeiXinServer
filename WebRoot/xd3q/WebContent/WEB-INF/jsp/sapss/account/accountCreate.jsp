<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会计科目申请页面</title>
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
<script type="text/javascript">
var from = '<%=request.getAttribute("popfrom")%>';
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
            {contentEl:'main', title: '银行主数据信息'},
			{contentEl:'fund',id:'fundEl',title:'资金部补充',disabled:true},
			{contentEl:'general',id:'generalEl',title:'股份财务部补充',disabled:true},
			{contentEl:'maintain',id:'maintainEl',title:'信息中心维护',disabled:true}
        ]
    });
    tempTab = tabs;
    //提交按键默认不能用
    //Ext.getDom('submitfr').disabled=true;
	//alert(from);
    //如果是修改窗口
    if(from=='modify'){
    	//Ext.getDom('accountID').value='<%=request.getAttribute("accountID")%>';
    }
    //如果是提交审批窗口
    else if(from=='workflow'){
		Ext.get('submitfr').setStyle("display","block");
    	//Ext.getDom('submitfr').disabled=false;
    	//Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('accountID').value='<%=request.getAttribute("accountID")%>';
    }
    //如果是查看窗口
    else if(from=='view'){
		Ext.getDom('saveFundfr').disabled=true;
		Ext.getDom('clearFundfr').disabled=true;
		Ext.getDom('saveGeneralfr').disabled=true;
		Ext.getDom('clearGeneralfr').disabled=true;
		Ext.getDom('saveMaintainfr').disabled=true;
		Ext.getDom('clearMaintainfr').disabled=true;
		tabs.getItem('fundEl').enable();
		tabs.getItem('generalEl').enable();
		tabs.getItem('maintainEl').enable();
    	Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('accountID').value='<%=request.getAttribute("accountID")%>';
    }
    //流程中可修改窗口
    else if(from=='iframe'){
	 	var taskName = parent.document.getElementById("workflowCurrentTaskName").value;
     	if(taskName=="资金部审批")
      	{
			tabs.getItem('fundEl').enable();
		}
		else if(taskName=="股份财务部信息补全"){
			tabs.getItem('fundEl').enable();
			tabs.getItem('generalEl').enable();
		}
		else{
		    tabs.getItem('fundEl').enable();
			tabs.getItem('generalEl').enable();
			tabs.getItem('maintainEl').enable();
		}
		//Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('accountID').value='<%=request.getAttribute("accountID")%>';
    	Ext.getDom('closefr').disabled=true;
    }
    
});//end of Ext.onReady    

var saveFlag = false;
function saveMainForm(btn){
	btn.disabled=true;
	var ajax = Ext.Ajax.request({
		url: 'accountController.spr?action=save&pid='+Ext.getDom('accountID').value,
		params:Form.serialize('mainForm'),
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			var json =txt['infolion-json'];
			if(json!=null && json.message!=null){
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
				saveFlag = true;
				Ext.getDom('accountID').value=txt.accountID;
				Ext.Msg.alert('提示','保存成功');
				btn.disabled=false;
			}
		},
		waitMsg:'处理中...',
		failure:function(resp,o){
			Ext.Msg.alert('Error',o.result.msg);
		}
	});
}

function saveFundForm(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'accountController.spr?action=saveFundInfo&accountID='+Ext.getDom('accountID').value,
		params:Form.serialize('fundForm'),
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
				showMsg('保存成功');
				btn.disabled=false;
			}
		}
	});
}

function saveGeneralForm(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'accountController.spr?action=saveGeneralInfo&accountID='+Ext.getDom('accountID').value,
		params:Form.serialize('generalForm'),
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
				showMsg('保存成功');
				btn.disabled=false;
			}
		}
	});
}

function saveMaintainForm(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'accountController.spr?action=saveMaintainInfo&accountID='+Ext.getDom('accountID').value,
		params:Form.serialize('maintainForm'),
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
				showMsg('保存成功');
				btn.disabled=false;
			}
		}
	});
}

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

function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
 				_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
function submitWorkflow(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '确定提交流程审批',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
				new AjaxEngine('accountController.spr?action=submitAccountInfo',
				 {method:"post", parameters: Form.serialize('mainForm'), onComplete: customCallBackHandle});		
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function submitMainForm(){
	setTimeout(function(){
	Ext.Ajax.request({
		url: 'accountController.spr?action=submitProjectInfo&doWorkflow=mainForm',
		params:Form.serialize('mainForm'),
		success: function(response, options){
					var txt = Ext.util.JSON.decode(response.responseText);
					if(txt.success){
						Ext.Msg.alert('提示',txt.msg);
						setTimeout(function(){
							_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}, 1000);
					}else{
						if(txt['infolion-json']!=null)
							Ext.Msg.alert('提示',txt['infolion-json']['message']);	
						else	
							Ext.Msg.alert('提示',txt.msg);	
					}	
				}
	});
	},500);
}

function check1() 
{ 
     var accountTitle=document.getElementById("accountTitle");
     if(accountTitle.checked==true)
     accountTitle.value="1";
     if(accountTitle.checked==false)
     accountTitle.value="";
}
function check2() 
{ 
     var balanceMaintain=document.getElementById("balanceMaintain");
     if(balanceMaintain.checked==true)
     balanceMaintain.value="1";
     if(balanceMaintain.checked==false)
     balanceMaintain.value="";
}
function check3() 
{ 
     var cashierAccount=document.getElementById("cashierAccount");
     if(cashierAccount.checked==true)
     cashierAccount.value="1";
     if(cashierAccount.checked==false)
     cashierAccount.value="";
}
</script>
</head>
<body>
<div id='progressBar'></div>
<div id="main" class="x-hide-display">
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="20%" align="right">申请单位</td>
        <td width="30%" align="left"><input name="orgName" type="text" size="25" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
        <td width="20%" colspan="2" align="right">申请人</td>
        <td width="30%">
        	<input name="creatorName" type="text" size="25" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">电话<font color="red">▲</font></td>
        <td>
        	<input name="tel" type="text" size="25" tabindex="2" value="${main.tel}" maxlength="20" />
        </td>
        <td align="right" colspan="2">EMAIL<font color="red">▲</font></td>
        <td>
        	<input name="email" type="text" size="25" tabindex="2" value="${main.email}" maxlength="50"/>
        </td>
      </tr>
	  <tr>
        <td align="right">公司代码<font color="red">▲</font></td>
        <td>
        	<input name="companyCode" type="text" size="25" tabindex="2" value="${main.companyCode}"/>
        </td>
        <td align="right" colspan="2">银行名称<font color="red">▲</font></td>
        <td>
        	<input name="headOfficeName" type="text" size="25" tabindex="2" value="${main.headOfficeName}" maxlength="25"/>
        </td>
      </tr>
	  <tr>
        <td align="right">国家<font color="red">▲</font></td>
        <td>
        	<input name="country" type="text" size="25" value="${main.country}" maxlength="2"/>
        </td>
        <td align="right" colspan="2">地区/省份<font color="red">▲</font></td>
        <td>
        	<input name="province" type="text" size="25" value="${main.province}" maxlength="3"/>
        </td>
      </tr>
	  <tr>
        <td align="right">城市<font color="red">▲</font></td>
        <td>
        	<input name="city" type="text" size="25" value="${main.city}"/>
        </td>
        <td align="right" colspan="2">街道</td>
        <td>
        	<input name="street" type="text" size="25" value="${main.street}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">分行(开户行名称)<font color="red">▲</font></td>
        <td>
        	<input name="bankName" type="text" size="25" value="${main.bankName}"/>
        </td>
        <td align="right" colspan="2">帐号<font color="red">▲</font></td>
        <td>
        	<input name="accountCode" type="text" size="25" value="${main.accountCode}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">币别<font color="red">▲</font></td>
        <td>
        	<div id="div_currency" name="div_currency"></div>
        </td>
        <td align="right" colspan="2">是否待核查帐户<font color="red">▲</font></td>
        <td>
        	<select name="isPVA">
				<option value="">请选择</option>
				<option value="1" <c:if test="${main.isPVA=='1'}"> selected </c:if> >是</option>
				<option value="0" <c:if test="${main.isPVA=='0'}"> selected </c:if> >否</option>
			</select>
        </td>
      </tr>
	  <tr>
        <td align="right">期初会计余额<font color="red">▲</font></td>
        <td>
        	<input name="balance" type="text" size="25" value="${main.balance}"/>
        </td>
        <td align="right" colspan="2">用 途</td>
        <td>
            <select name="purpose">
                <option value="">请选择</option>
        	    <option value="1" <c:if test="${main.purpose=='1'}"> selected </c:if> >人民币存款（一般存款）</option>
        		<option value="2" <c:if test="${main.purpose=='2'}"> selected </c:if> >人民币存款（人民币借款）</option>
        		<option value="3" <c:if test="${main.purpose=='3'}"> selected </c:if> >外币存款（一般存款）</option>
        		<option value="4" <c:if test="${main.purpose=='4'}"> selected </c:if> >外币存款（外币借款）</option>
        		<option value="5" <c:if test="${main.purpose=='5'}"> selected </c:if> >其它货币资金-外埠</option>
        		<option value="6" <c:if test="${main.purpose=='6'}"> selected </c:if> >其它货币资金-承保金</option>
        		<option value="7" <c:if test="${main.purpose=='7'}"> selected </c:if> >其它货币资金-LC保证金</option>
        		<option value="8" <c:if test="${main.purpose=='8'}"> selected </c:if> >其它货币资金-待核</option>
        		<option value="9" <c:if test="${main.purpose=='9'}"> selected </c:if> >其它货币资金-远期保证金</option>
        		<option value="10" <c:if test="${main.purpose=='10'}"> selected </c:if> >其它货币资金-定存</option>
        		<option value="11" <c:if test="${main.purpose=='11'}"> selected </c:if> >其它货币资金-其它（收到投资款等）</option>
        		<option value="12" <c:if test="${main.purpose=='12'}"> selected </c:if> >其它</option>
        	</select>
        </td>
      </tr>
      <tr>
			<td align="right">备注</td>
			<td colspan=4>
	      		<textarea rows="3" cols="80" id="memo" name="memo">${main.memo}</textarea>
			</td>			
		</tr>	
  		<tr>
  			<td align="right" colspan="2">
  			    <input type="button" value="保 存" onClick="saveMainForm(this)" class="btn" id="savefr" /><input type="hidden" name="accountID" value="${main.accountID}" />&nbsp;&nbsp;
  			</td>
  			 <td align="left" width="1%"><input type="button" value="提 交" onClick="submitWorkflow()" class="btn" id="submitfr" style="display:none"/></td>
			 <td colspan="2" width="49%">
    			<input type="button" value="关闭窗口" onClick="closeForm()" class="btn" id="closefr"/>
  			</td>
  		</tr>
</table>
<input type="hidden" name="taskId" value="${taskId}">
</form>
</div>
<div id="fund" class="x-hide-display">
<form id="fundForm" name="fundForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="35%" align="right">银行编号<font color="red">▲</font></td>
    <td width="65%">
		<input type="text" id="headOfficeNO" name="headOfficeNO" value="${main.headOfficeNO}">
    </td>
  </tr>
  <tr>
    <td align="right">开户行代码<font color="red">▲</font></td>
    <td>
		<input type="text" name="bankCode" value="${main.bankCode}">
    </td>
  </tr>
  <tr>
    <td align="right">银行代码<font color="red">▲</font></td>
    <td>
		<input type="text" name="headOfficeCode" value="${main.headOfficeCode}">
    </td>
  </tr>
  <tr>
    <td align="right">帐户标识：</td>
    <td>
		<input type="text" name="accountFlag" value="${main.accountFlag}">
    </td>
  </tr>
  <tr>
  	<td align="center" colspan="2">
		<input type="button" value="保 存" onClick="saveFundForm(this)" class="btn" id="saveFundfr" />
		<input type="button" value="清 空" onClick="resetForm(this.form)" id="clearFundfr"/>
	</td>
  </tr>
</table>
</form>
</div>
<div id="general" class="x-hide-display">
<form name="generalForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="35%" align="right">总账科目编号<font color="red">▲</font></td>
    <td width="65%">
		<input type="text" name="subjectNO" value="${main.subjectNO}">
    </td>
  </tr>
  <tr>
    <td align="right">描 述(短文本)<font color="red">▲</font></td>
    <td>
		<input type="text" size="50" name="shortDescription" value="${main.shortDescription}">
    </td>
  </tr>
  <tr>
    <td align="right">长文本<font color="red">▲</font></td>
    <td>
		<input type="text" size="50" name="longDescription" value="${main.longDescription}">
    </td>
  </tr>
  <tr>
  	<td align="center" colspan="2">
		<input type="button" value="保 存" onClick="saveGeneralForm(this)" class="btn" id="saveGeneralfr" />
		<input type="button" value="清 空" onClick="resetForm(this.form)" id="clearGeneralfr"/>
	</td>
  </tr>
</table>
</form>
</div>
<div id="maintain" class="x-hide-display">
<form id="maintainForm" name="maintainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="35%" align="right">会计科目<font color="red">▲</font></td>
    <td width="65%">
		<input id="accountTitle" name="accountTitle" type="checkbox" value="${main.accountTitle}" ${main.accountTitle==1?"checked":""} onclick="check1()"/>
    </td>
  </tr>
  <tr>
    <td align="right">银行科目期初余额维护<font color="red">▲</font></td>
    <td>
		<input id="balanceMaintain" name="balanceMaintain" type="checkbox" value="${main.balanceMaintain}" ${main.balanceMaintain==1?"checked":""} onclick="check2()"/>
    </td>
  </tr>
  <tr>
    <td align="right">出纳日记账<font color="red">▲</font></td>
    <td>
		<input id="cashierAccount" name="cashierAccount" type="checkbox" value="${main.cashierAccount}" ${main.cashierAccount==1?"checked":""} onclick="check3()"/>
    </td>
  </tr>
  <tr>
  	<td align="center" colspan="2">
		<input type="button" value="保 存" onClick="saveMaintainForm(this)" class="btn" id="saveMaintainfr" />
		<input type="button" value="清 空" onClick="resetForm(this.form)" id="clearMaintainfr"/>
	</td>
  </tr>
</table>
</form>
</div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" width="200" selectedValue="${main.currency}" disable="false" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${accountID}" width="750"></fiscxd:workflow-taskHistory>
