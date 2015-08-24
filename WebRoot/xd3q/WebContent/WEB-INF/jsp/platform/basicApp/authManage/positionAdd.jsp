<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月17日 10点34分51秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象职位(Position)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职位增加页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/positionAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/positionAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:2;formRowNo:1 ;rowNo: 1;columnNo: 1;1X -->
		<td align="right"  width="15%" > 部门组织：</td>
		<td  width="30%" >
			<div id="div_organizationid_sh"></div>
			<fisc:searchHelp divId="div_organizationid_sh" boName="Position" boProperty="organizationid" value="${position.organizationid}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:3;formRowNo:1 ;rowNo: 1;columnNo: 2;1X -->
		<td align="right"  width="15%" >职位编号：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Position.positionno" name="Position.positionno" value="${position.positionno}" <fisc:authentication sourceName="Position.positionno" taskId=""/>  readonly="readonly">
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:4;formRowNo:2 ;rowNo: 2;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>描述：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Position.desc" name="Position.desc" value="${position.desc}" <fisc:authentication sourceName="Position.desc" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:5;formRowNo:2 ;rowNo: 2;columnNo: 2;1X -->
		<td align="right"  width="15%" >是否是领导：</td>
		<td   width="40%" >
			<div id="div_isleader_dict"></div>
			<fisc:dictionary boName="Position" boProperty="isleader" dictionaryName="YDYESORNO" divId="div_isleader_dict" isNeedAuth="false"  value="${position.isleader}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:6;formRowNo:3 ;rowNo: 3;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>职位类型：</td>
		<td  width="30%" >
			<div id="div_positiontype_dict"></div>
			<fisc:dictionary boName="Position" boProperty="positiontype" dictionaryName="YDPOSITIONTYPE" divId="div_positiontype_dict" isNeedAuth="false"  value="${position.positiontype}"></fisc:dictionary>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 9(调试用)序号:8;formRowNo:4 ;rowNo: 4;columnNo: 1;1X -->
		<td align="right"  width="15%" valign="top">备注：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Position.memo" name="Position.memo" <fisc:authentication sourceName="Position.memo" taskId=""/>>${position.memo}</textarea>
		</td>
	</tr>

	<input type="hidden" id="Position.client" name="Position.client" value="${position.client}">
	<input type="hidden" id="Position.positionid" name="Position.positionid" value="${position.positionid}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>

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
				            		title: '职位信息',
				            		layout:'fit',
				            		autoScroll: true,
				            		contentEl:'div_center'
						}

				  ]
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
//});
</script>
