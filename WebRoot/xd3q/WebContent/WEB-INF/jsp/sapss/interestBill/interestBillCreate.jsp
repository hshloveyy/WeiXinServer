<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>开票申请单登记表(利息税)</title>
		
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
.copy{
	background-image:url(<%= request.getContextPath()%>/images/fam/application_go.png) !important;
}
</style>

<script type="text/javascript">
//var from = '<%=request.getAttribute("popfrom")%>';
var tabsTemp ;
var isChanged=false;
//贸易名称
var strbillApplyTitle ='';
var strOperType='';
var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';
/*--------------------------------------------------------*/
function selectProjectInfo(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'projectController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallBack,
	{width:800,height:400});
}
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.mainForm.projectId.value = returnvalue.PROJECT_ID + "";
	document.mainForm.projectNo.value = returnvalue.PROJECT_NO + "";
}
function viewProjectForm(){
    var projectId = Ext.getDom("projectId").value
    if(projectId=='') {
         showMsg('请选择立项信息！');
         return ;
   }
   top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId='+projectId,'','',{width:800,height:500});
}

function submitForm(){
   var param1 = Form.serialize('mainForm');
   var param = param1  + "&action=submitAndSaveBillApply";
   new AjaxEngine('interestBillController.spr', 
	 {method:"post", parameters: param, onComplete: customCallBackHandle});

}

function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}
function showFindCustomer(){
	top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});
}
function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('unitName').value='';
		Ext.getDom('unitNo').value='';
	}
	else {
		Ext.getDom('unitName').value=cb.NAME1;
		Ext.getDom('unitNo').value=cb.KUNNR;
		Ext.getDom('unitType').value=1;
	}
}
function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('unitName').value='';
		Ext.getDom('unitNo').value='';
	}
	else {
		Ext.getDom('unitName').value=cb.NAME1;
		Ext.getDom('unitNo').value=cb.LIFNR;
		Ext.getDom('unitType').value=2;
	}
}

function save(){
   if(checkValues()){
      var ajax = Ext.Ajax.request({
			url: 'interestBillController.spr?action=saveBillApply',
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
				 }else{
				   Ext.getDom('interestBillId').value=txt.interestBillId;
				   Ext.getDom('createTime').value=txt.createTime;
				   Ext.getDom('creator').value=txt.creator;
				   Ext.getDom('isAvailable').value=txt.isAvailable;
				   Ext.getDom('examState').value=txt.examState;
				   Ext.getDom('interestBillNo').value=txt.interestBillNo;
				   Ext.getDom('recordhdinputname').value=txt.interestBillId;
				   tabsTemp.getItem('fileEl').enable();
				   tabsTemp.getItem('historyinfo').enable();
				   Ext.Msg.alert('提示','保存成功');
				}
			},
			waitMsg:'处理中...',
			failure:function(resp,o){
				Ext.Msg.alert('Error',o.result.msg);
			}
		});
   }
}
function saveSapReturnNo(){
		var param='?action=saveSapInfo&interestBillId=${main.interestBillId}&sapBillNo='+$('sapBillNo').value+'&taxNo='+$('taxNo').value;
		new AjaxEngine('billApplyController.spr', 
				   {method:"post", parameters: param, onComplete: callBackHandle});
}
/*********************/

//

function saveCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	var interestBillId = customField.interestBillId;
	if(interestBillId!=''){
	   Ext.getDom('interestBillId')=customField.interestBillId;
	   Ext.getDom('createTime')=customField.createTime;
	   Ext.getDom('creator')=customField.creator;
	   Ext.getDom('isAvailable')=customField.isAvailable;
	   Ext.getDom('examState')=customField.examState;
	   Ext.getDom('interestBillNo')=customField.interestBillNo;
	   Ext.getDom('recordhdinputname').value=interestBillId;
	   tabsTemp.getItem('fileEl').enable();
	   tabsTemp.getItem('historyinfo').enable();
	}
	
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);
	//top.ExtModalWindowUtil.markUpdated();
    //top.ExtModalWindowUtil.close();
}

//新增保存删除提示
function customCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);
	top.ExtModalWindowUtil.markUpdated();
    top.ExtModalWindowUtil.close();
}



Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
	
	var taskType = '${taskType}';
	if (taskType=='1')
		Ext.getDom("sapBillNo").readOnly = false;  

    var fm = Ext.form;

    var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
                miniHeight:300,
            	title:'开票申请信息(利息税)',
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            },{contentEl:'rule',
               id:'fileEl', 
               title: '附件信息',
               autoScroll:'true',
               disabled:${!tabable},
               listeners:{activate:handlerActivate}
            }
            ,{
            	title:'审批历史记录',
            	contentEl: 'div_history',
            	id:'historyinfo',
            	disabled:${!tabable},
				name:'historyinfo',
				autoScroll:'true',
            	layout:'fit'
            }]
	});

   	//申报日期
   	var applyDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyDate',
	    name:'applyDate',
		width: 160,
	    readOnly:true,
		applyTo:'applyDate'
   	});   	
   	//开票日期
   	var makeDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'makeDate',
	    name:'makeDate',
		width: 160,
		applyTo:'makeDate'
   	});  	
   	var receiptDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'receiptDate',
	    name:'receiptDate',
		width: 160,
		applyTo:'receiptDate'
   	}); 
   	   	
 	//---------------------------------------
    tabsTemp = tabs;
 	 
});//end of Ext.onReady   




function checkValues()
{
     if (Ext.getDom('unitNo').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','请先选择开票单位！');
     	return false;
     }
     if (Ext.getDom('receiptDate').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','请先选择应收日期！');
     	return false;
     }
	 if (Ext.getDom('value').value=="")
     {
     	top.ExtModalWindowUtil.alert('提示','开票金额不能为空！');
     	return false;
     }
         
     return true;
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
   			
				new AjaxEngine('billApplyController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}

//关闭窗体
function closeForm(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
    top.ExtModalWindowUtil.markUpdated();
    top.ExtModalWindowUtil.close();
}

//清空窗口
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

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	updateTotal();
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
//	showMsg(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
</script>
	</head>
	<body>
		<div id='div_progressBar'></div>
		<div id="main" class="x-hide-display">
			<div id="div_main" class="x-hide-display">
				<form id="mainForm" action="" name="mainForm">
					<table width="100%" border="1" cellpadding="4" cellspacing="1"
						bordercolor="#6699FF" class="datatable">
						<tr>
							<td width="11%" align="right">
								立项号：
							</td>
							<td width="22%" align="left">
								<input id="projectNo" name="projectNo" readonly="readonly"
									type="text" size="12" tabindex="1" value="${main.projectNo}">
								<input id="selectProjectBtn" type="button" value="..." onclick="selectProjectInfo()"/>
					            <input type="hidden" value="${main.projectId }" name="projectId"/>
					            <a href="#" onclick="viewProjectForm()">查看</a>
							</td>
							<td width="11%" align="right">
								开票申请单号：
							</td>
							<td width="22%" align="left">
								<div id="div_billType">
									<input id="interestBillNo" name="interestBillNo" type="text"
										size="20" tabindex="1" value="${main.interestBillNo}" readonly />
								</div>
							</td>
							<td width="11%" align="right">
								部门：
							</td>
							<td width="22%" align="left">
							    <c:if test="${empty(main.deptId)}">
							    <input id="deptId" name="deptId" type="hidden" size="20"
									value="${deptId}" />
								<input id="deptName" type="text" name="deptName"
									readonly="readonly" size="20" value="${deptName}">
								</c:if>
								<c:if test="${!empty(main.deptId)}">
							    <input id="deptId" name="deptId" type="hidden" size="20"
									value="${main.deptId}" />
								<input id="deptName" type="text" name="deptName"
									readonly="readonly" size="20" value="${main.deptName}">
								</c:if>
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								开票单位编码<font color="red">▲</font>
							</td>
							<td width="22%" align="left">
							    <nobr>
								<input id="unitNo" name="unitNo"
									readonly="readonly" type="text" size="12" tabindex="1"
									value="${main.unitNo}" />
								<input id="showCustomer" type="button" value="客户" onclick="showFindCustomer();"/>
								<input type="button" id="showSupplier" value="供应商" onclick="showFindSupplier()">
							    </nobr>
							</td>
							<td width="11%" align="right">
								开票单位：
							</td>
							<td width="22%" align="left">
								<input id="unitName" name="unitName"
									readonly="readonly" type="text" size="20" tabindex="1"
									value="${main.unitName}">
							</td>
							<td width="11%" align="right">
								<nobr>客户税务登记号：</nobr>
							</td>
							<td width="22%" align="left">
								<input id="billPartyNo" type="text" id="billPartyNo" name="billPartyNo"
									size="20" value="${main.billPartyNo}">
							   
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								开票金额<font color="red">▲</font>
							</td>
							<td width="22%" align="left">
								<input id="value" name="value" type="text" size="20" tabindex="1"
									value="${main.value}" />
							</td>
							<td width="11%" align="right">
								申报日期：
							</td>
							<td width="22%" align="left">
								<input id="applyDate" name="applyDate" type="text"
									value="${main.applyDate}">
							</td>
							<td align="right">
								开票日期
							</td>
							<td align="left">
								<input id="makeDate" name="makeDate" type="text" size="20"
									tabindex="1" value="${main.makeDate}">
							</td>
						</tr>
						<tr>
							<td width="11%" align="right">
								<nobr>SAP开票凭证号：</nobr>
							</td>
							<td width="22%" align="left">
								<input id="sapReturnNo" name="sapReturnNo" type="text" size="20" tabindex="1"
									value="${main.sapReturnNo}">
							</td>
							<td align="right">
								发票号：
							</td>
							<td align="left">
								<input id="taxNo" name="taxNo" type="text" size="20"
									tabindex="1" value="${main.taxNo}">
							</td>
							<td align="right">
								纸质合同号：
							</td>
							<td align="left">
								<input id="paperNo" name="paperNo" type="text" size="20"
									tabindex="1" value="${main.paperNo}">
							</td>
						</tr>
						<tr>
							<td align="right">
								应收日<font color="red">▲</font>
							</td>
							<td align="left">
								<input id="receiptDate" name="receiptDate" type="text" size="20"
									tabindex="1" value="${main.receiptDate}">
							</td>
							<td align="right">
							备注：
						    </td>
						    <td colspan="3">
							   <textarea cols="30" rows="1" id="memo" name="memo" style="width:95%;overflow-y:visible;word-break:break-all;">${main.memo}</textarea>
						    </td>
						</tr>
						<tr>
						<td align="center" colspan="6">
						   <input id="isAvailable" name="isAvailable" type="hidden" value="${main.isAvailable}" />
						   <input id="createTime" name="creatorTime" type="hidden" value="${main.createTime}" />
						   <input name="creator" type="hidden" value="${main.creator}" />
						   <input type="hidden" name="examState" value="${main.examState}"/>
						   <input type="hidden" name="unitType" value="${main.unitType}"/>
						   <input type="hidden" name="applyTime" value="${main.applyTime}"/>
						   <input type="hidden" name="interestBillId" value="${main.interestBillId}"/>
						   <c:if test="${!save==true}">
						   <input type="button" value="保存" onclick="save();"/>
						   </c:if>
						   <c:if test="${!submit==true}">
						   <input type="button" value="提交" onclick="submitForm();"/>
						   </c:if>
						   <c:if test="${!close==true}">
						   <input type="button" value="关闭" onclick="closeForm();"/>
						   </c:if>
						</td>
						</tr>
						
					</table>

				</form>
			</div>

			<div id="div_center"></div>

		<div id="div_history" class="x-hide-display"></div>
        <div id="rule" class="x-hide-display" ></div>

	</body>
</html>
<fiscxd:workflow-taskHistory divId="div_history"
	businessRecordId="${main.interestBillId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="rule" recordId="${main.interestBillId}" erasable="${editable}"  increasable="${editable}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>