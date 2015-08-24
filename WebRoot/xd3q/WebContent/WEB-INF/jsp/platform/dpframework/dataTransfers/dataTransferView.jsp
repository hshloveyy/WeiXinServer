<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月10日 14点59分08秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>数据传输记录表(DataTransfer)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import ="com.infolion.platform.dpframework.dataTransfers.domain.DataTransfer"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据传输记录表查看页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/platform/dpframework/dataTransfers/dataTransferView.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${DataTransfer.datatransferid}"></fisc:workflow-taskHistory>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >系统名称：</td>
		<td width="30%">
			<input type="text" readonly="readonly" class="inputText" id="DataTransfer.systemname" name="DataTransfer.systemname" value="${dataTransfer.systemname}" <fisc:authentication sourceName="DataTransfer.systemname" taskId="${workflowTaskId}"/>  >
			注:PL/SQL数据库名(Oracle，tnsnames.ora文件中定义的连接串 ) 
		</td>
	</tr>
	<tr>	
		<td width="15%" align="right" >数据库类型：</td>
		<td  width="30%">
			<input type="text" readonly="readonly" class="inputText" id="DataTransfer.dbtype" name="DataTransfer.dbtype" value="${dataTransfer.dbtype}" <fisc:authentication sourceName="DataTransfer.dbtype" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >数据库连接串：</td>
		<td width="30%">
			<input type="text" size="50" readonly="readonly" id="DataTransfer.dburl" name="DataTransfer.dburl" value="${dataTransfer.dburl}" <fisc:authentication sourceName="DataTransfer.dburl" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>	
		<td width="15%" align="right" >用户名：</td>
		<td  width="30%">
			<input type="text" readonly="readonly" class="inputText" id="DataTransfer.username" name="DataTransfer.username" value="${dataTransfer.username}" <fisc:authentication sourceName="DataTransfer.username" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >用户口令：</td>
		<td width="30%">
			<input type="text" readonly="readonly" class="inputText" id="DataTransfer.password" name="DataTransfer.password" value="${dataTransfer.password}" <fisc:authentication sourceName="DataTransfer.password" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>	
		<td width="15%" align="right" >模块选项：</td>
		<td  width="30%" >
					CRM:<input type="checkbox" id="modular1" value="YCRM"
			<%
			DataTransfer dataTransfer = (DataTransfer) request.getAttribute("dataTransfer");
			String modular = dataTransfer.getModular();
			System.out.println("modular:" + modular);
			if (null!= modular && modular.indexOf("YCRM") >= 0)
			{
				%> checked="checked"<%
			}
			%>
			/>
			信达项目三期:<input type="checkbox" id="modular2" value="YXD3Q"
			<%
			if (null!= modular && modular.indexOf("YXD3Q") >= 0)
			{
				%> checked="checked"<%
			}
			%>
			/>
			信达项目三期:<input type="checkbox" id="modular3" value="YXDSS"
			<%
			if (null!= modular && modular.indexOf("YXDSS") >= 0)
			{
				%> checked="checked"<%
			}
			%>
			/>
			拙雅项目:<input type="checkbox" id="modular4" value="YJYPM" 
			<%
			if (null!= modular && modular.indexOf("YJYPM") >= 0)
			{
				%> checked="checked"<%
			}
			%>
			/>
			<input type="hidden" value="${dataTransfer.modular}" name="DataTransfer.modular"/> 
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >传输方案：</td>
		<td>
			<select name="plan" id="plan">
				<option value="">请选择....</option>
				<!--<option value="1">初始化传输(SAP)</option>
				<option value="4">完整数据传输(SAP)</option>  -->
				<option value="2">业务对象配置传输</option>
				<!-- <option value="3">初始化传输(非SAP)旧版本(已废弃)</option>
				<option value="5">初始化(全部业务数据)传输(非SAP)</option>
				<option value="6">初始化(初始化数据)传输(非SAP)</option> -->
				<option value="7">SAP元数据传输(非SAP)(传输所有开发类下SAP元数据,先删再插)</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >是否写入文件：</td>
		<td>
			<input type="radio" name="writable" id="writable1" value="true"  ${writable1}>是
			<input type="radio" name="writable" id="writable2" value="false" ${writable2}>否
		</td>
	
	</tr>
	<input type="hidden" id="DataTransfer.datatransferid" name="DataTransfer.datatransferid" value="${DataTransfer.datatransferid}">
</table>
</form>
</div>
	
<div id="div_adding" class="x-hide-display"> 
<form id="syncForm" name="syncForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >增量更新业务对象(新版)：</td>
		<td width="30%" valign="middle">
			<textarea rows="2" cols="40" name="boNames" id="boNames"></textarea>
			<input type="button" value="更新" onclick="_checkSAPServer('boNames','incrementSync')"/>
			<input type="button" value="<-" onclick="_clearBO('businessObject')"/>注:多个用","隔开
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >增量同步流程模型(同步激活状态模型)：</td>
		<td width="30%" valign="middle">
			<input type="button" value="增量同步流程模型(同步激活状态模型)" disabled="disabled" onclick="incrementSyncWorkFlow();"/>
		</td>
	</tr>
</table>
</form>
</div>

<form id="addingForm" name="addingForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >增量更新业务对象(旧版)：</td>
		<td width="30%" valign="middle">
			<textarea rows="2" cols="40" name="businessObject" id="businessObject"></textarea>
			<input type="button" value="更新" onclick="_checkSAPServer('businessObject','updateBusinessObject')"/>
			<input type="button" value="<-" onclick="_clearBO('businessObject')"/>注:多个用","隔开
		</td>
	</tr>
</table>
</form>

	
<div id="div_advanceSetting" class="x-hide-display"> 
<form id="advanceForm" name="advanceForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >是否插入数据：</td>
		<td>
			<input type="checkbox" id="insertData" value="true"/>
		</td>
	</tr>

	<tr>
		<td width="15%" align="right" >增加表：</td>
		<td width="30%" valign="middle">
			<textarea rows="2" cols="40" name="otherTable" id="otherTable"></textarea>
			<input type="button" value="增加" onclick="_checkSAPServer('otherTable','addOtherTable')"/>
			<input type="button" value="<-" onclick="_clearBO('otherTable')"/>注:多个用","隔开
		</td>
	</tr>

	<tr>
		<td width="15%" align="right" >增加视图：</td>
		<td width="30%" valign="middle">
			<textarea rows="2" cols="40" name="otherView" id="otherView"></textarea>
			<input type="button" value="增加" onclick="_checkSAPServer('otherView','addOtherView')"/>
			<input type="button" value="<-" onclick="_clearBO('otherView')"/>注:多个用","隔开
		</td>
	</tr>

	
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>


<div id="div_top_north" class="search">
</div>
<div id="div_top_south" class="x-hide-display"></div>
<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var id = '${DataTransfer.datatransferid}';	


/**
 * 保存 
 */
function _saveOrUpdateDataTransfer()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("DataTransfer.datatransferid").value = id;	
}
          

/**
 * 取消
 */
function _cancelDataTransfer()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	//_getMainFrame().ExtModalWindowUtil.close();
	//_getMainFrame().ExtModalWindowUtil.markUpdated();
}
/**
 * 
 * @return
 */
function _executeDataTransfer(){
/*	var para = Form.serialize('mainForm');
	var param = '?action=execute&'+para;
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
*/	
	var plan = $('plan').value;
	if(plan!='')
		_checkSAPServer('','execute');
	else
		showInfo('请选择传输方案!');
}
function showMsg(transport){
	//{"infolion-json":{"type":"info","coustom":"只有运行在SAP服务器上才可能执行此操作","message":"操作成功。"}}
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msg = responseUtil.getCustomField('coustom');
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if(msg!=null){
		showError(msg);
	}else{
		showInfo(responseUtil.getCustomField('message'));
	}
}
/**
 * 
 * @return
 */
function _testConnection(){
	var para = Form.serialize('mainForm');
	var param = '?action=testConnection&'+para;
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
}
function _updateBO(){
	if($('businessObject').value.trim()=='')
		return;
/*
	var isConnectToSAP = $('isConnectToSAP').checked;
	var para;
	if(isConnectToSAP)
		para = Form.serialize('mainForm')+'&businessObject='+$('businessObject').value+'&isConnectToSAP=true';
	else
		para = Form.serialize('mainForm')+'&businessObject='+$('businessObject').value;
	var param = '?action=updateBusinessObject&'+para;
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
	*/
}
function _addOtherTable(){
	if($('otherTable').value.trim()=='')
		return;
	var para = Form.serialize('mainForm')+'&otherTable='+$('otherTable').value;
	if($('insertData').checked==true)
		para+='&insertData=true';
	var param = '?action=addOtherTable&'+para;
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
}

function _addOtherView(){
	if($('otherView').value.trim()=='')
		return;
	var para = Form.serialize('mainForm')+'&otherView='+$('otherView').value;
	var param = '?action=addOtherView&'+para;
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
	
}
var clickType;
var addPara='';
function _checkSAPServer(typeid,type){
	clickType = type;
	if($(typeid)!=null && $(typeid).value.trim()=='')
	{
		_getMainFrame().showInfo('请输入【增量更新业务对象】！');
		return;
	}
	if($(typeid)!=null && $(typeid).value.trim()!=''){
		addPara = '&'+typeid+'='+$(typeid).value;
		if($('insertData').checked==true)
			addPara+='&insertData=true';
	}

	var param = '?action=checkSAPServer&';
	new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: doFunction});	
}

function doFunction(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msg = responseUtil.getCustomField('coustom');
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	var connMsg='';
	var acttionFlag=0;
	if(msg!=null && msg=='true'){
		connMsg = '<h>SAP数据库-->开发数据库,要继续?</h>';
		acttionFlag=1;
	}else{
		acttionFlag=2;
		var para = Form.serialize('mainForm');
		var param = '?action=checkCurrentSetting&'+para;
		new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
				{method:"post", parameters: param, onComplete: doFunctionReverse});	
	}
	if(acttionFlag==1)
		Ext.Msg.show({
	   		title:'系统提示',
	   		msg: connMsg,
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
	 				var para = Form.serialize('mainForm');
	 				var param = '?action='+clickType+'&'+para+addPara;
	 				new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
	 						{method:"post", parameters: param, onComplete: callBackHandle});	
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.WARNING
		});
}


function incrementSyncWorkFlow()
{
	if($('boNames')!=null && $('boNames').value.trim()=='')
	{
		_getMainFrame().showInfo('请输入【要增量更新流程模型的业务对象】！');
		return;
	}
	
	Ext.Msg.show({
   		title:'系统提示',
   		msg: "是否确认增量同步流程模型(同步激活状态模型)？",
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
 				var para = Form.serialize('mainForm') + "&boNames=" + $('boNames').value.trim() ;
 	 			var param = "?action=incrementSyncWorkFlow&" + para
 				new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
 						{method:"post", parameters: param, onComplete: callBackHandle});	
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.WARNING
	});
}


function doFunctionReverse(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msg = responseUtil.getCustomField('coustom');
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	var connMsg='';
	if(msg!=null && msg=='true'){
		connMsg = '<h>开发数据库<--SAP数据库,要继续?</h>';
		Ext.Msg.show({
	   		title:'系统提示',
	   		msg: connMsg,
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
	 				var para = Form.serialize('mainForm');
	 				var param = '?action='+clickType+'&'+para+addPara;
	 				new AjaxEngine(contextPath+'/platform/dpframework/dataTransfers/dataTransferController.spr', 
	 						{method:"post", parameters: param, onComplete: callBackHandle});	
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.WARNING
		});	
	}else{
		connMsg = '<h>本页的数据库配置不是当前运行的环境,不能进行数据传输!</h>';
		showError(connMsg);
	}
	
	
}
function _clearBO(id){
	$(id).value='';
}

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[{
				            	title:'系统传输',
				            	layout:'fit',
				            	border:false,
				            	//height:122.5,
				            	autoScroll: true,
				            	contentEl:'div_center'
							},{
			            		title:'增量数据传输',
			            		layout:'fit',
			            		border:false,
			            		//height:122.5,
			            		autoScroll: true,
			            		contentEl:'div_adding'
							},{
			            		title:'高级配置',
			            		layout:'fit',
			            		border:false,
			            		collapsible: true,
			            		collapsed:true,
			            		autoScroll: true,
			            		contentEl:'div_advanceSetting'
							}]
				   }]
				}
				 ]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',{id:'_cancel',text:'测试连接',handler:_testConnection,iconCls:'connect'},'-',
					{id:'_cancel',text:'系统数据传输',handler:_executeDataTransfer,iconCls:'icon-window'},'-',
					'->','-',
					{id:'_cancel',text:'取消',handler:_cancelDataTransfer,iconCls:'icon-undo'},
					'-'
			],
			renderTo:'div_toolbar'
	});

	
//});

</script>
