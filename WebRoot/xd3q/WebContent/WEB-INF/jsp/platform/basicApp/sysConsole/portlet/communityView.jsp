<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年11月16日 01点50分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>社区管理(Community)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社区管理查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/portlet/communityView.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:grid divId="div_portlet" boName="Portlet" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPORTLET.COMMUNITYID='${community.communityid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${community.communityid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >社区定义名称：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Community.communityname" name="Community.communityname" value="${community.communityname}" <fisc:authentication sourceName="Community.communityname" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >社区描述：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Community.communitydesc" name="Community.communitydesc" value="${community.communitydesc}" <fisc:authentication sourceName="Community.communitydesc" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >社区主题ID：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Community.communitythemeid" name="Community.communitythemeid" value="${community.communitythemeid}" <fisc:authentication sourceName="Community.communitythemeid" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >是否默认：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Community.isdefault" name="Community.isdefault" value="${community.isdefault}" <fisc:authentication sourceName="Community.isdefault" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" > 权限资源：</td>
		<td width="30%">
			<div id="div_authresourceid_sh"></div>
			<fisc:searchHelp divId="div_authresourceid_sh" boName="Community" boProperty="authresourceid"  value="${community.authresourceid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >URL地址：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Community.url" name="Community.url" value="${community.url}" <fisc:authentication sourceName="Community.url" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >是否可拖拽：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Community.draggable" name="Community.draggable" value="${community.draggable}" <fisc:authentication sourceName="Community.draggable" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >创建人：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="Community" userId="${community.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >创建日期：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="Community.createtime" name="Community.createtime" value="${community.createtime}"  readonly="readonly">
		</td>
		<td width="15%" align="right" >最后修改者：</td>
		<td  width="40%">
			<fisc:user boProperty="lastmodifyer" boName="Community" userId="${community.lastmodifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >最后修改日期：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="Community.lastmodifytime" name="Community.lastmodifytime" value="${community.lastmodifytime}"  readonly="readonly">
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Community.client" name="Community.client" value="${community.client}">
	<input type="hidden" id="Community.communityid" name="Community.communityid" value="${community.communityid}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_portlet"></div>
                     

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var communityid = '${community.communityid}';	


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
				            		title:'社区管理信息',
				            		layout:'fit',
				            		border:false,
				            		//height:235,
				            		autoScroll: true,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						           items:[
				          						                {
						            		title:'Portlet',
						            		layout:'fit',
						            		contentEl:'div_portlet'
						            	}
				          
						    ]}
						   ]}
						]
				}
				 ]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_create',text:'创建',handler:_createCommunity,iconCls:'icon-add'},'-',
{id:'_copyCreate',text:'复制创建',handler:_copyCreateCommunity,iconCls:'icon-copyCreate'},'-',
'->','-',
{id:'_cancel',text:'取消',handler:_cancelCommunity,iconCls:'icon-undo'},
'-'
],
			renderTo:'div_toolbar'
	});

	
//});
Ext.onReady(function(){
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
 });
</script>
