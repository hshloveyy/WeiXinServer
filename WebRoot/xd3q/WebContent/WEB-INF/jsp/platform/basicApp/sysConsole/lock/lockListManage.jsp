<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年08月11日 14点46分21秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>被锁定记录表(LockList)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>被锁定记录表管理页面</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"> 用户名：${userName}</td>
		<td width="30%">
		<!-- UITYPE:01 -->
			<input class="inputText" type="text" id="userName.fieldValue" name="userName.fieldValue" value="">
			<input type="hidden" id="userName.fieldName" name="userName.fieldName" value="USERNAME"> 
			<input type="hidden" id="userName.dataType" name="userName.dataType" value="S">  
			<input type="hidden" id="userName.option" name="userName.option" value="like">
		</td>
		<td width="15%" align="right">锁表名：${tabName}</td>
		<td width="40%">
		<!-- UITYPE:01 -->
			<input class="inputText" type="text" id="tabName.fieldValue" name="tabName.fieldValue" value="">
			<input type="hidden" id="tabName.fieldName" name="tabName.fieldName" value="TABLENAME"> 
			<input type="hidden" id="tabName.dataType" name="tabName.dataType" value="S">  
			<input type="hidden" id="tabName.option" name="tabName.option" value="like">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">时间 从：</td>
		<td width="30%">
			<input type="hidden" id="lockTime.fieldName" name="lockTime.fieldName" value="LOCKTIME"> 
			<input type="hidden" id="lockTime.dataType" name="lockTime.dataType" value="D"> 
			<input type="hidden" id="lockTime.isRangeValue" name="lockTime.isRangeValue" value="true">
			<!-- UITYPE:04 -->
    		<input class="inputText" type="text" id="lockTime_minValue" name="lockTime.minValue">
			<fisc:calendar applyTo="lockTime_minValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%" align="right">到：</td>
		<td width="40%">
			<input class="inputText" type="text" id="lockTime_maxValue" name="lockTime.maxValue">
			<fisc:calendar applyTo="lockTime_maxValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
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
	reload_LockList_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_LockList_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}

/**
 * grid 上的 删除按钮调用方法，批量解锁
 * 批量删除被锁定记录表
 */
function _unLocks()
{
	if (LockList_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【被锁定记录表批量解锁】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = LockList_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.businessId + '|';
						}

						var param = '&businessIds='+ids;
						new AjaxEngine('<%= request.getContextPath() %>/platform/dpframework/uicomponent/lock/lockListController.spr?action=_unLocks', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo('请选择需要进行【被锁定记录表批量解锁】操作的记录！');
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
					contentEl:'div_toolbar',
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					border:false,
					layout:'fit',
					autoScroll: true,
		            title:"订单明细",
		            height:380,
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="LockList" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
