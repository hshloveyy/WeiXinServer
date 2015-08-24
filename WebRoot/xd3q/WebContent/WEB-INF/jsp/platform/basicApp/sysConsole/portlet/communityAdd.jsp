<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年11月16日 01点50分23秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>社区管理(Community)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社区管理增加页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/portlet/communityAdd.js"></script>
</head>
<body>
<fisc:grid divId="div_portlet" boName="Portlet" needCheckBox="true" editable="false" defaultCondition=" YPORTLET.COMMUNITYID='${community.communityid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:2;formRowNo:14 ;rowNo: 14;columnNo: 1;2X -->
		<td align="right"  width="15%" >社区定义名称：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Community.communityname" name="Community.communityname" value="${community.communityname}" <fisc:authentication sourceName="Community.communityname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:3;formRowNo:14 ;rowNo: 14;columnNo: 2;2X -->
		<td align="right"  width="15%" >社区描述：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Community.communitydesc" name="Community.communitydesc" value="${community.communitydesc}" <fisc:authentication sourceName="Community.communitydesc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:4;formRowNo:16 ;rowNo: 16;columnNo: 1;2X -->
		<td align="right"  width="15%" >社区主题ID：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Community.communitythemeid" name="Community.communitythemeid" value="${community.communitythemeid}" <fisc:authentication sourceName="Community.communitythemeid" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:5;formRowNo:16 ;rowNo: 16;columnNo: 2;2X -->
		<td align="right"  width="15%" >是否默认：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Community.isdefault" name="Community.isdefault" value="${community.isdefault}" <fisc:authentication sourceName="Community.isdefault" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:6;formRowNo:18 ;rowNo: 18;columnNo: 1;2X -->
		<td align="right"  width="15%" > 权限资源：</td>
		<td  width="30%" >
			<div id="div_authresourceid_sh"></div>
			<fisc:searchHelp divId="div_authresourceid_sh" boName="Community" boProperty="authresourceid" value="${community.authresourceid}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:7;formRowNo:18 ;rowNo: 18;columnNo: 2;2X -->
		<td align="right"  width="15%" >URL地址：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Community.url" name="Community.url" value="${community.url}" <fisc:authentication sourceName="Community.url" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:8;formRowNo:20 ;rowNo: 20;columnNo: 1;2X -->
		<td align="right"  width="15%" >是否可拖拽：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Community.draggable" name="Community.draggable" value="${community.draggable}" <fisc:authentication sourceName="Community.draggable" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:9;formRowNo:20 ;rowNo: 20;columnNo: 2;2X -->
		<td align="right"  width="15%" >创建人：</td>
		<td   width="40%" >
			<fisc:user boProperty="creator" boName="Community" userId="${community.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:10;formRowNo:22 ;rowNo: 22;columnNo: 1;2X -->
		<td align="right"  width="15%" >创建日期：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Community.createtime" name="Community.createtime" value="${community.createtime}"  readonly="readonly" <fisc:authentication sourceName="Community.createtime" taskId=""/>>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:11;formRowNo:22 ;rowNo: 22;columnNo: 2;2X -->
		<td align="right"  width="15%" >最后修改者：</td>
		<td   width="40%" >
			<fisc:user boProperty="lastmodifyer" boName="Community" userId="${community.lastmodifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:12;formRowNo:24 ;rowNo: 24;columnNo: 1;2X -->
		<td align="right"  width="15%" >最后修改日期：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="Community.lastmodifytime" name="Community.lastmodifytime" value="${community.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="Community.lastmodifytime" taskId=""/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Community.client" name="Community.client" value="${community.client}">
	<input type="hidden" id="Community.communityid" name="Community.communityid" value="${community.communityid}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_portlet" ></div>
                     
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '社区管理信息',
				            		layout:'fit',
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
						            		title: 'Portlet',
						            		layout:'fit',
						            		id:'portletTab',
						            		contentEl:'div_portlet'
						            	}
				          
						]
						}]
						}]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
					'-'],  
			renderTo:"div_toolbar"
	});
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
//});
</script>
