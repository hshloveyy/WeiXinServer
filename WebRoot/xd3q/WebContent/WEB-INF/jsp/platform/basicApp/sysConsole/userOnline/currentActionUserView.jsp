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
			items:[		
				{
					region:"center",
					layout:'fit',
					autoScroll: true,
					border:false,
					height:450,
					contentEl:'div_southForm'
				}]
			}]
	});
	
});
</script>
<fisc:grid divId="div_southForm" boName="SessionLog" editable="false" pageSize="100" height="450"
	needCheckBox="ture" needToolbar="true" needAutoLoad="true" defaultCondition="${defaultCondition}"></fisc:grid>
