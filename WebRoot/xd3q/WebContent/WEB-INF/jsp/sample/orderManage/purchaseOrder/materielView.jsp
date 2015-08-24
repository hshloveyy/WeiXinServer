<%-- 
  - Author(s):张崇镇
  - Date: 2009-6-5
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:
  - <功能一>物料查看页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料查看页面</title>
</head>
<body>
<div id="div_south"></div>
<div id="div_center" class="search">
<form id="mainForm" name="mainForm">
<table width="600" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right">物料号：</td>
		<td><input type="text" id="materielNo" name="materielNo" value="${materiel.materielNo }"></td>
		<td align="right">物料描述：</td>
		<td><input type="text" id="desc" name="desc" value="${materiel.desc }"></td></td>
	</tr>
	<tr>
		<td align="right">基本计量单位 ：</td>
		<td><input type="text" id="unit" name="unit" value="${materiel.unit }"></td>
		<td align="right">有效期起：</td>
		<td><input type="text" id="validDateStart" name="validDateStart" value="${materiel.validDateStart }"></td></td>
	</tr>
	<tr>
		<td align="right">销售单位不可变 ：</td>
		<td colspan="3"><input type="text" id="kzeff" name="kzeff" value="${materiel.kzeff }"></td>
	</tr>	
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">

/**
 * 取消
 */
function _cancel()
{
	top.ExtModalWindowUtil.close();
	//top.maintabs.remove(top.maintabs.getActiveTab());
}
/**
 * EXT 布局
 */
//Ext.onReady(function(){	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:300,
					contentEl:'div_center'
				}],
			buttons:[{
				        	text:'取消',
				        	minWidth: 40,
				        	name:'btn_cancel',
				        	handler:_cancel
				}]
		}]
	});
//});
</script>