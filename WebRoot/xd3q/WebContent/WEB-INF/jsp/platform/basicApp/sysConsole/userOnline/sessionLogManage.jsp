<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年08月18日 11点30分33秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>用户会话记录表(SessionLog)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户会话记录表管理页面</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">用户名：${username}</td>
		<td width="30%" >
		<!-- UITYPE:01 -->
			<input class="inputText" type="text" id="username.fieldValue" name="username.fieldValue" value="">
			<input type="hidden" id="username.fieldName" name="username.fieldName" value="USERNAME"> 
			<input type="hidden" id="username.dataType" name="username.dataType" value="S">  
			<input type="hidden" id="username.option" name="username.option" value="like">
		</td>
    <td width="15%" ></td>
    <td width="40%" ></td>
	</tr>
	<tr>
		<td width="15%" align="right">登录时间 从：</td>
		<td width="30%" >
			<input type="hidden" id="logintime.fieldName" name="logintime.fieldName" value="LOGINTIME"> 
			<input type="hidden" id="logintime.dataType" name="logintime.dataType" value="D"> 
			<input type="hidden" id="logintime.isRangeValue" name="logintime.isRangeValue" value="true">
			<!-- UITYPE:05 -->
		   <input type="text" id="logintime_minValue" name="logintime.minValue">
		   <fisc:calendar applyTo="logintime_minValue" format="Y-m-d H:i:s" divId="" fieldName="" showTime="true"></fisc:calendar>
	   </td>
		<td width="15%" align="right">到：</td>
		<td width="40%" >
			<input type="text" id="logintime_maxValue" name="logintime.maxValue">
			<fisc:calendar applyTo="logintime_maxValue" format="Y-m-d H:i:s" divId="" fieldName="" showTime="true"></fisc:calendar>
		</td>
			

	</tr>
	<tr>
		<td width="15%" align="right">注销时间 从：</td>
		<td width="30%" >
			<input type="hidden" id="logouttime.fieldName" name="logouttime.fieldName" value="LOGOUTTIME"> 
			<input type="hidden" id="logouttime.dataType" name="logouttime.dataType" value="D"> 
			<input type="hidden" id="logouttime.isRangeValue" name="logouttime.isRangeValue" value="true">
			<!-- UITYPE:05 -->
		   <input type="text" id="logouttime_minValue" name="logouttime.minValue">
		   <fisc:calendar applyTo="logouttime_minValue" format="Y-m-d H:i:s" divId="" fieldName="" showTime="true"></fisc:calendar>
	   </td>
		<td width="15%" align="right">到：</td>
		<td width="40%" >
			<input type="text" id="logouttime_maxValue" name="logouttime.maxValue">
			<fisc:calendar applyTo="logouttime_maxValue" format="Y-m-d H:i:s" divId="" fieldName="" showTime="true"></fisc:calendar>
		</td>
			

	</tr>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_SessionLog_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_SessionLog_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除用户会话记录表
 */
function _deletes()
{
	if (SessionLog_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【用户会话记录表批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = SessionLog_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.sessionlogid + '|';
						}

						var param = '&sessionLogIds='+ids;
						new AjaxEngine('<%= request.getContextPath() %>/platform/basicApp/sysConsole/userOnline/sessionLogController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【用户会话记录表批量删除】操作的记录！');
	}
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:"center",
			border:false,
			items:[{
					region:"north",
					border:false,
					contentEl:'div_toolbar',
					height:25
				},{
					region:'center',
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"日志明细",
		            height:380,
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="SessionLog" editable="false" needOperationColumn="false" 
	needCheckBox="false" needToolbar="false" needAutoLoad="true" defaultCondition="LOGOUTYPE!='0'" orderBySql=" YSESSIONLOG.LOGINTIME DESC,YSESSIONLOG.LOGOUTTIME DESC"></fisc:grid>
