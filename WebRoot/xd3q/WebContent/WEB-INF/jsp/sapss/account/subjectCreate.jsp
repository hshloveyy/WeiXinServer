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
            {contentEl:'main', title: '会计科目信息'},
			{contentEl:'additional',id:'additionalEl',title:'股份财务部补充',disabled:true},
			{contentEl:'final',id:'finalEl',title:'信息中心补充',disabled:true}
        ]
    });
    tempTab = tabs;
    //提交按键默认不能用
    //Ext.getDom('submitfr').disabled=true;
	//alert(from);
    //如果是修改窗口
    if(from=='modify'){
    	//Ext.getDom('subjectID').value='<%=request.getAttribute("subjectID")%>';
    }
    //如果是提交审批窗口
    else if(from=='workflow'){
		Ext.get('submitfr').setStyle("display","block");
    	//Ext.getDom('submitfr').disabled=false;
    	//Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('subjectID').value='<%=request.getAttribute("subjectID")%>';
    }
    //如果是查看窗口
    else if(from=='view'){
		Ext.getDom('saveAdditionalfr').disabled=true;
		Ext.getDom('clearAdditionalfr').disabled=true;
		Ext.getDom('saveFinalfr').disabled=true;
		Ext.getDom('clearFinalfr').disabled=true;
		tabs.getItem('additionalEl').enable();
		tabs.getItem('finalEl').enable();
    	Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('subjectID').value='<%=request.getAttribute("subjectID")%>';
    }
    //流程中可修改窗口
    else if(from=='iframe'){
		var taskName = parent.document.getElementById("workflowCurrentTaskName").value;
		if(taskName=="股份财务部审批")
      	{
			tabs.getItem('additionalEl').enable();
		}
		else{
			tabs.getItem('additionalEl').enable();
			tabs.getItem('finalEl').enable();
		}
		//Ext.getDom('savefr').disabled=true;
    	//Ext.getDom('subjectID').value='<%=request.getAttribute("subjectID")%>';
    	Ext.getDom('closefr').disabled=true;
    }
    
});//end of Ext.onReady    

var saveFlag = false;
function saveMainForm(btn){
	btn.disabled=true;
	var ajax = Ext.Ajax.request({
		url: 'subjectController.spr?action=save&pid='+Ext.getDom('subjectID').value,
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
				Ext.getDom('subjectID').value=txt.subjectID;
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

function saveAdditionalForm(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'subjectController.spr?action=saveAdditionalInfo&subjectID='+Ext.getDom('subjectID').value,
		params:Form.serialize('additionalForm'),
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

function saveFinalForm(btn){
	btn.disabled=true;
	Ext.Ajax.request({
		url: 'subjectController.spr?action=saveFinalInfo&subjectID='+Ext.getDom('subjectID').value,
		params:Form.serialize('finalForm'),
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
				new AjaxEngine('subjectController.spr?action=submitSubjectInfo',
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
		url: 'subjectController.spr?action=submitProjectInfo&doWorkflow=mainForm',
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
</script>
</head>
<body>
<div id='progressBar'></div>
<div id="main" class="x-hide-display">
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td colspan="2" align="right">申 请 单 位</td>
        <td width="403" align="left"><input name="orgName" type="text" size="55" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
      </tr>
      <tr>
        <td colspan="2" align="right">申 请 人</td>
        <td>
        	<input name="creatorName" type="text" size="55" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">电 话<font color="red">▲</font></td>
        <td>
        	<input name="tel" type="text" size="55" tabindex="2" value="${main.tel}" maxlength="20" />
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">EMAIL<font color="red">▲</font></td>
        <td>
        	<input name="email" type="text" size="55" tabindex="2" value="${main.email}" maxlength="50"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">公 司 代 码<font color="red">▲</font></td>
        <td>
        	<input name="companyCode" type="text" size="55" tabindex="2" value="${main.companyCode}"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">SAP 科 目 代 码<font color="red">▲</font></td>
        <td>
        	<input name="subjectNO_SAP" type="text" size="55" tabindex="2" value="${main.subjectNO_SAP}" maxlength="10"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">帐 户 组<font color="red">▲</font></td>
        <td>
        	<div id="div_accountGroup" name="div_accountGroup"></div>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">科 目 短 文 本<font color="red">▲</font></td>
        <td>
        	<input name="shortDescription" type="text" size="55" tabindex="2" value="${main.shortDescription}" maxlength="20"/>
        </td>
      </tr>
	  <tr>
        <td colspan="2" align="right">科 目 长 文 本</td>
        <td>
        	<input name="longDescription" type="text" size="55" tabindex="2" value="${main.longDescription}"/>
        </td>
      </tr>
  		<tr>
  			<td width="49%" align="right">
  			    <input type="button" value="保 存" onClick="saveMainForm(this)" class="btn" id="savefr" />
  			</td>
  			<td width="1%" >
  				<input type="hidden" name="subjectID" value="${main.subjectID}" /><input type="button" value="提 交" onClick="submitWorkflow()" class="btn" id="submitfr" style="display:none">
  				<!--input type="hidden" name="approveState" value="${main.approveState}"/>
				<input type="hidden" name="applyTime" value="${main.applyTime}"/-->
  			</td>
  			 <td  width="50%" align="left">
  			    
    			<input type="button" value="关闭窗口" onClick="closeForm()" class="btn" id="closefr"/>
  			</td>
  		</tr>
</table>
<input type="hidden" name="taskId" value="${taskId}">
</form>
</div>
<div id="additional" class="x-hide-display">
<form name="additionalForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="15%" align="right">BS</td>
    <td width="35%">
		<select name="isBS">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isBS=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isBS=='0'}"> selected </c:if> >否</option>
		</select>
    </td>
    <td width="18%" align="right">IS</td>
    <td width="32%">
		<select name="isIS">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isIS=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isIS=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
  </tr>
  <tr>
    <td align="right">科目货币<font color="red">▲</font></td>
    <td>
		<div id="div_currency" name="div_currency"></div>
    </td>
    <td align="right">是否仅以本位币显示余额<font color="red">▲</font></td>
    <td>
		<select name="isOnlyCNY">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isOnlyCNY=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isOnlyCNY=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
  </tr>
  <tr>
    <td align="right">税类别</td>
    <td>
		<input type="text" name="taxType" value="${main.taxType}">
    </td>
    <td align="right">允许无税款过帐<font color="red">▲</font></td>
    <td>
		<select name="isAllowNoTax">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isAllowNoTax=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isAllowNoTax=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
  </tr>
  <tr>
  	<td align="right">统驭科目类型</td>
    <td>
		<select name="controlType">
			<option value="">请选择</option>
			<option value="A" <c:if test="${main.controlType=='A'}"> selected </c:if> >资产</option>
			<option value="K" <c:if test="${main.controlType=='K'}"> selected </c:if> >供应商</option>
			<option value="D" <c:if test="${main.controlType=='D'}"> selected </c:if> >客户</option>
		</select>
    </td>
    <td align="right">未清项管理<font color="red">▲</font></td>
    <td>
		<select name="isNotClear">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isNotClear=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isNotClear=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
  </tr>
  <tr>
  	<td align="right">行项目显示<font color="red">▲</font></td>
    <td>
		<select name="isShowRows">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isShowRows=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isShowRows=='0'}"> selected </c:if> >否</option>
		</select>
    </td>
    <td align="right">排序码<font color="red">▲</font></td>
    <td>
		<input type="text" name="orderNO" value="${main.orderNO}">
	</td>
  </tr>
  <tr>
    <td align="right">只允许自动过帐<font color="red">▲</font></td>
    <td>
		<select name="isOnlyAuto">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isOnlyAuto=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isOnlyAuto=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
    <td align="right">与现金流动有关<font color="red">▲</font></td>
    <td>
		<select name="isCashRelated">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isCashRelated=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isCashRelated=='0'}"> selected </c:if> >否</option>
		</select>
	</td>
  </tr>
  <tr>
  	<td align="right">是否新增<font color="red">▲</font></td>
    <td colspan="3">
		<select name="isNew">
			<option value="">请选择</option>
			<option value="1" <c:if test="${main.isCashRelated=='1'}"> selected </c:if> >是</option>
			<option value="0" <c:if test="${main.isCashRelated=='0'}"> selected </c:if> >否</option>
		</select>
    </td>
  </tr>
  <tr>
    <td align="right">备注</td>
    <td colspan="3">
		<textarea cols="60" rows="3" name="remark" style="overflow-y:visible;word-break:break-all;">${main.remark}</textarea>
	</td>
  </tr>
  <tr>
  	<td colspan="4" align="center">
		<input type="button" value="保 存" onClick="saveAdditionalForm(this)" class="btn" id="saveAdditionalfr" />
		<input type="button" value="清 空" onClick="resetForm(this.form)" id="clearAdditionalfr"/>
	</td>
  </tr>
</table>
</form>
</div>
<div id="final" class="x-hide-display">
<form name="finalForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="35%" align="right">字段状态群组<font color="red">▲</font></td>
    <td width="65%">
		<div id="div_colStateGroup" name="div_colStateGroup"></div>
    </td>
  </tr>
  <tr>
    <td width="35%" align="right">计划层次<font color="red">▲</font></td>
    <td width="65%">
		<div id="div_planLayer" name="div_planLayer"></div>
	</td>
  </tr>
  <tr>
  	<td colspan="4" align="center">
		<input type="button" value="保 存" onClick="saveFinalForm(this)" class="btn" id="saveFinalfr" />
		<input type="button" value="清 空" onClick="resetForm(this.form)" id="clearFinalfr"/>
	</td>
  </tr>
 </table>
</form>
</div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" width="200" selectedValue="${main.currency}" disable="false" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_colStateGroup" fieldName="colStateGroup" dictionaryName="bm_col_state_group" width="250" selectedValue="${main.colStateGroup}" disable="false" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_planLayer" fieldName="planLayer" dictionaryName="bm_plan_layer" width="200" selectedValue="${main.planLayer}" disable="false" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_accountGroup" fieldName="accountGroup" dictionaryName="bm_account_group_subject" width="200" selectedValue="${main.accountGroup}" disable="false" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${subjectID}" width="750"></fiscxd:workflow-taskHistory>
