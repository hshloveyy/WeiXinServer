﻿<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 09点10分42秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象供应商授限立项配置(ProviderCreditProj)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商授限立项配置查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/providerCreditProjView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/providerCreditProjViewGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.projectid}：</td>
		<td width="30%" >
			<div id="div_projectid_sh"></div>
			<fisc:searchHelp divId="div_projectid_sh" boName="ProviderCreditProj" boProperty="projectid" value="${providerCreditProj.projectid}"></fisc:searchHelp>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="ProviderCreditProj.configprojectid" name="ProviderCreditProj.configprojectid" value="">
<input type="hidden" id="ProviderCreditProj.configid" name="ProviderCreditProj.configid" value="">
<input type="hidden" id="ProviderCreditProj.creator" name="ProviderCreditProj.creator" value="">
<input type="hidden" id="ProviderCreditProj.createtime" name="ProviderCreditProj.createtime" value="">
<input type="hidden" id="ProviderCreditProj.lastmodifyer" name="ProviderCreditProj.lastmodifyer" value="">
<input type="hidden" id="ProviderCreditProj.lastmodifytime" name="ProviderCreditProj.lastmodifytime" value="">
<input type="hidden" id="ProviderCreditProj.otherprepayvalue" name="ProviderCreditProj.otherprepayvalue" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

//页面文本
var Txt={
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_cancel',text:'${vt.sCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
					' '
				   ],
			renderTo:'div_toolbar'
});
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
					region:'north',
					layout:'fit',
					height:26,
					border:false,
					contentEl:'div_toolbar'
	   		   },{
					region:'center',
					border:false,
					buttonAlign:'center',
					items:[{
							layout:'fit',
							region:'center',
							height:435.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
