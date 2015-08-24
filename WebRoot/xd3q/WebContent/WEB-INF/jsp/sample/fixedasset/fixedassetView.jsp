<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月08日 08点18分10秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象固定资产(Fixedasset)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>固定资产查看页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetView.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetViewGen.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="Fixedasset"></fisc:relationship>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${fixedasset.anln1}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" > 公司代码：</td>
		<td width="30%">
			<div id="div_bukrs_sh"></div>
			<fisc:searchHelp divId="div_bukrs_sh" boName="Fixedasset" boProperty="bukrs"  value="${fixedasset.bukrs}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >资产次级编号：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Fixedasset.anln2" name="Fixedasset.anln2" value="${fixedasset.anln2}" <fisc:authentication sourceName="Fixedasset.anln2" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >资产分类：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Fixedasset.anlkl" name="Fixedasset.anlkl" value="${fixedasset.anlkl}" <fisc:authentication sourceName="Fixedasset.anlkl" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >技术资产号：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Fixedasset.gegst" name="Fixedasset.gegst" value="${fixedasset.gegst}" <fisc:authentication sourceName="Fixedasset.gegst" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >资产类型：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Fixedasset.anlar" name="Fixedasset.anlar" value="${fixedasset.anlar}" <fisc:authentication sourceName="Fixedasset.anlar" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" > 负责人：</td>
		<td  width="40%">
			<div id="div_assetcharge_sh"></div>
			<fisc:searchHelp divId="div_assetcharge_sh" boName="Fixedasset" boProperty="assetcharge"  value="${fixedasset.assetcharge}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" > 产品/物料：</td>
		<td width="30%">
			<div id="div_matnr_sh"></div>
			<fisc:searchHelp divId="div_matnr_sh" boName="Fixedasset" boProperty="matnr"  value="${fixedasset.matnr}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >创建人：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="Fixedasset" userId="${fixedasset.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >创建时间：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="Fixedasset.createtime" name="Fixedasset.createtime" value="${fixedasset.createtime}"  readonly="readonly">
		</td>
		<td width="15%" align="right" >最后修改者：</td>
		<td  width="40%">
			<fisc:user boProperty="lastmodifyer" boName="Fixedasset" userId="${fixedasset.lastmodifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >最后修改时间：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="Fixedasset.lastmodifytime" name="Fixedasset.lastmodifytime" value="${fixedasset.lastmodifytime}"  readonly="readonly">
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Fixedasset.client" name="Fixedasset.client" value="${fixedasset.client}">
	<input type="hidden" id="Fixedasset.anln1" name="Fixedasset.anln1" value="${fixedasset.anln1}">
	<input type="hidden" id="Fixedasset.processstate" name="Fixedasset.processstate" value="${fixedasset.processstate}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>


<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${fixedasset.processstate}';
//当前对象主键ID
var anln1 = '${fixedasset.anln1}';	

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
				            items:[
				            {
				            	id:'currentWorkflowTask',
					            title: '流程审批信息',
					            layout:'fit',
					            border:false,
			            		anchor: '-20',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'固定资产信息',
				            		layout:'fit',
				            		border:false,
				            		//height:235,
				            		autoScroll: true,
			            			anchor: '-20',
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								border:false,
								title:'流程跟踪信息',
			            		layout:'anchor',
				            	anchor: '-20',
				            	collapsible: true,
				            	collapsed:true,
				            	border:false,
				            	autoScroll: true,
								height:200,
								contentEl:'div_top_south'
							   }
							   ]
				   }]
				}
				,{
					region:'east',
					width:200,
					contentEl:'div_east',
					collapsible: true,
					collapsed:true,
					autoScroll: true,
					title:'相关操作'
				}]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_copyCreate',text:'复制创建',handler:_copyCreateFixedasset,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'创建',handler:_createFixedasset,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'取消',handler:_cancelFixedasset,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'提交',handler:_submitProcessFixedasset,iconCls:'task'},'-',
<%}%>
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();	}
	
//});
</script>
