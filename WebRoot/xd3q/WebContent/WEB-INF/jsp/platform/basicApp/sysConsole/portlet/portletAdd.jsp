<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月16日 09点40分31秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象Portlet(Portlet)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Portlet增加页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/portlet/portletAdd.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >PORTLET名称：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.portletname" name="Portlet.portletname" value="${portlet.portletname}" <fisc:authentication sourceName="Portlet.portletname" taskId=""/>>
		</td>
	      <td></td><td></td>
		</tr>
	<tr>
		<td width="20%" align="right" valign="top">PORTLET描述：</td>
		<td width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Portlet.portletdesc" name="Portlet.portletdesc"  <fisc:authentication sourceName="Portlet.portletdesc" taskId=""/>>${portlet.portletdesc}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" > 权限资源：</td>
		<td width="30%" >
			<div id="div_authresourceid_sh"></div>
			<fisc:searchHelp divId="div_authresourceid_sh" boName="Portlet" boProperty="authresourceid" value="${portlet.authresourceid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >URL地址：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.url" name="Portlet.url" value="${portlet.url}" <fisc:authentication sourceName="Portlet.url" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" > 社区主题：</td>
		<td width="30%" >
			<div id="div_communitythemeid_sh"></div>
			<fisc:searchHelp divId="div_communitythemeid_sh" boName="Portlet" boProperty="communitythemeid" value="${portlet.communitythemeid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >主题位置：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.regionid" name="Portlet.regionid" value="${portlet.regionid}" <fisc:authentication sourceName="Portlet.regionid" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >排序编号：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.orderno" name="Portlet.orderno" value="${portlet.orderno}" <fisc:authentication sourceName="Portlet.orderno" taskId=""/>>
		</td>
		<td width="20%" align="right" >是否可关闭：</td>
		<td width="30%" >
			<div id="div_closable_dict"></div>
			<fisc:dictionary boName="Portlet" boProperty="closable" dictionaryName="YDYESORNO" divId="div_closable_dict" isNeedAuth="false"  value="${portlet.closable}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >能否最大化：</td>
		<td width="30%" >
			<div id="div_maximizable_dict"></div>
			<fisc:dictionary boName="Portlet" boProperty="maximizable" dictionaryName="YDYESORNO" divId="div_maximizable_dict" isNeedAuth="false"  value="${portlet.maximizable}"></fisc:dictionary>
		</td>
		<td width="20%" align="right" >能否最小化：</td>
		<td width="30%" >
			<div id="div_minimizable_dict"></div>
			<fisc:dictionary boName="Portlet" boProperty="minimizable" dictionaryName="YDYESORNO" divId="div_minimizable_dict" isNeedAuth="false"  value="${portlet.minimizable}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >创建人：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.creator" name="Portlet.creator" value="${portlet.creator}" <fisc:authentication sourceName="Portlet.creator" taskId=""/>>
		</td>
		<td width="20%" align="right" >创建日期：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.createtime" name="Portlet.createtime" value="${portlet.createtime}" <fisc:authentication sourceName="Portlet.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >最后修改者：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.lastmodifyer" name="Portlet.lastmodifyer" value="${portlet.lastmodifyer}" <fisc:authentication sourceName="Portlet.lastmodifyer" taskId=""/>>
		</td>
		<td width="20%" align="right" >最后修改日期：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Portlet.lastmodifytime" name="Portlet.lastmodifytime" value="${portlet.lastmodifytime}" <fisc:authentication sourceName="Portlet.lastmodifytime" taskId=""/>>
		</td>
	</tr>
<input type="hidden" id="Portlet.portletid" name="Portlet.portletid" value="">
</table>
</form>
</div>

<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			border:false,
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:775,
					border:false,
					bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_center'
				}],
			buttons:[{
				        	text:'确定',
				        	name:'btn_save',
				        	minWidth: 40,
				        	handler:_save
				     },
				     {
				        	text:'取消',
				        	minWidth: 40,
				        	name:'btn_cancel',
				        	handler:_cancel
				}]
		}]
	});
//});
</script>
