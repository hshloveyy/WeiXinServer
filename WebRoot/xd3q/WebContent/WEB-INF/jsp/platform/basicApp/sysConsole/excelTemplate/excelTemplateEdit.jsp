<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年08月13日 17点15分10秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>excel模版(ExcelTemplate)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>excel模版编辑页面</title>
		<style type="text/css">
.x-grid3-header-offset {
	width: auto;
}
</style>
	</head>
	<body>
	<fisc:attachement businessId="${excelTemplate.tempId}"
	allowDelete="true" divId="div_attachement" boName="ExcelTemplate"
	gridPageSize="10" gridHeight="285"></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width=""
	businessRecordId="${excelTemplate.tempId}"></fisc:workflow-taskHistory>
	
		<div id="div_toolbar"></div>
<div id="div_center" class="search">
			<form id="mainForm" name="mainForm">
				<table width="100%" border="0" cellpadding="4" cellspacing="1">
					<tr>
						<td width="15%" align="right">
							模版描述：
						</td>
						<td width="30%" >
							<input  class="inputText"  type="text" id="ExcelTemplate.tempDesc" name="ExcelTemplate.tempDesc" value="${excelTemplate.tempDesc}">
						</td>
						<td width="15%" align="right">
							业务对象名称：
						</td>
						<td width="40%" >
							<input type="hidden" id="ExcelTemplate.boId"
								name="ExcelTemplate.boId" value="${excelTemplate.boId}">
							<input  class="inputText"  type="text" id="ExcelTemplate.boName"
								name="ExcelTemplate.boName" value="${excelTemplate.boName}" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							业务对象描述：
						</td>
						<td width="30%" >
							<input class="inputText" type="text" id="ExcelTemplate.boDesc"
								name="ExcelTemplate.boDesc" value="${excelTemplate.boDesc}" readonly="readonly">
						</td>
						<td width="15%" align="right">
							方法ID：
						</td>
						<td width="40%" >
							<div id="div_methodId_sh"></div>
							<fisc:searchHelp divId="div_methodId_sh" boName="ExcelTemplate"
								boProperty="methodId" searchType="form" value="${excelTemplate.methodId}" callBackHandler="boSerachHelpCallBack"></fisc:searchHelp>
						</td>
					</tr>
					<tr>
						<td width="15%" align="right">
							方法名：
						</td>
						<td width="30%" >
							<input class="inputText" type="text" id="ExcelTemplate.methodName"
								name="ExcelTemplate.methodName"
								value="${excelTemplate.methodName}" readonly="readonly">
						</td>
						<td width="15%" align="right">
							方法描述：
						</td>
						<td width="40%" >
							<input class="inputText" type="text" id="ExcelTemplate.methodDesc"
								name="ExcelTemplate.methodDesc"
								value="${excelTemplate.methodDesc}" readonly="readonly">
						</td>
					</tr>
					  	<tr>
			  		<td align="right" width="15%">创建人:</td>
				  	<td align="left" width="30%">
				  		<fisc:user boProperty="creator" boName="ExcelTemplate" userId="${excelTemplate.creator}"></fisc:user>
				  	</td>
				  	<td align="right" width="15%">创建时间:</td>
				  	<td align="left" width="40%">
						<input type="text" class="inputText" id="ExcelTemplate.createTime" name="ExcelTemplate.createTime" value="${excelTemplate.createTime}" readOnly="readonly">
					</td>
			  	</tr>
			  	<tr>
			  		<td align="right" width="15%">最后修改人:</td>
				  	<td align="left" width="30%">
				  		<fisc:user boProperty="lastModifyor" boName="ExcelTemplate" userId="${excelTemplate.lastModifyor}"></fisc:user>
				  	</td>
				  	<td align="right" width="15%">最后修改时间:</td>
				  	<td align="left" width="40%">
				  		<input type="text" class="inputText" id="ExcelTemplate.lastModifytime" name="ExcelTemplate.lastModifytime" value="${excelTemplate.lastModifytime}" readOnly="readonly">
				  	</td>
			  	</tr>
					<input type="hidden" id="ExcelTemplate.tempId"
						name="ExcelTemplate.tempId" value="${excelTemplate.tempId}">
					<input type="hidden" id="ExcelTemplate.boId"
						name="ExcelTemplate.boId" value="${excelTemplate.boId}">
				
				</table>
			</form>
		</div>


		<div id="div_attachement"></div>


		<div id="div_top_north" class="search">
			<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
		</div>
		<div id="div_top_south"></div>
		<div id="div_east"></div>
		<div id="div_west"></div>
	</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%=request.getContextPath()%>';
//是否已经提交流程
var isSubmited = '';


function boSerachHelpCallBack(sjson){
	$('ExcelTemplate.boId').value = sjson.BOID;
	$('ExcelTemplate.boName').value=sjson.BONAME;
	$('ExcelTemplate.boDesc').value=sjson.DESCRIPTION
	$('ExcelTemplate.methodName').value=sjson.METHODNAME;
	$('ExcelTemplate.methodDesc').value=sjson.METHODDESC;
}  

          
/**
 * 保存 
 */
function _saveOrUpdateExcelTemplate()
{
	var param = Form.serialize('mainForm');			        	         
	param = param + ""+ getAttachementGridData();
	new AjaxEngine(contextPath + '/excelTemplateController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callBack:customCallBackHandle});
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var id = result.tempId;
	document.getElementById("ExcelTemplate.tempId").value = id;	

	document.getElementById("ExcelTemplate.creator_text").value = result.creator_text;
	document.getElementById("ExcelTemplate.creator").value = result.creator;
	document.getElementById("ExcelTemplate.createTime").value = result.createtime;
	document.getElementById("ExcelTemplate.lastModifyor_text").value = result.lastmodifyer_text;
	document.getElementById("ExcelTemplate.lastModifyor").value = result.lastmodifyer;
	document.getElementById("ExcelTemplate.lastModifyTime").value = result.lastmodifytime;
	
}

/**
* 取消
*/
function _cancel()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
	
	var toolbars = new Ext.Toolbar({
		items:['-','->','-',
{id:'_saveOrUpdate',text:'保存',handler:_saveOrUpdateExcelTemplate,iconCls:'ICON-TABLE-SAVE'},'-',
{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
'-'],
renderTo:'div_toolbar'
});

	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
					region:'center',
					layout:'border',
					items:[{
							region:'north',
							layout:'fit',
							height:29,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            autoScroll:true,
				            items:[{
				            	id:'currentWorkflowTask',
					            title: '流程审批信息',
					            layout:'fit',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'excel模版信息',
				            		layout:'fit',
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	autoScroll: true,
									activeTab:0,
						           items:[
				          				{
						            		title:'业务附件',
						            		layout:'fit',
						            		contentEl:'div_attachement'
						            	}
				          
						    ]}
						   ]}
						,{
							id:'historyWorkflowTask',
							region:'south',
							title:'流程跟踪信息',
		            		layout:'anchor',
			            	collapsible: true,
			            	collapsed:true,
			            	autoScroll: true,
							height:200,
							contentEl:'div_top_south'
						}]
				}
                 ]
	});
	
	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
			Ext.getCmp('_submit').disable();
			Ext.getCmp('_deletes').disable();
		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_saveOrUpdate').disable();	}
	
});

</script>

