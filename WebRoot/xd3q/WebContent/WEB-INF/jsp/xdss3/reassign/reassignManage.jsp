<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月16日 12点02分38秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象重分配(Reassign)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
</head>
<body>
<fisc:grid divId="div_southForm" 
		   boName="Reassign"  
		   editable="false" 
		   needCheckBox="true" 
		   needToolbar="true" 
		   needAutoLoad="false" 
		   needAuthentication="false" 
		   orderBySql="CREATETIME DESC"
		   defaultCondition=" 1=1">
</fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.reassignbono}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="reassignbono.fieldValue" name="reassignbono.fieldValue" value="" <fisc:authentication sourceName="ImportPaymentQuery.reassignbono" taskId=""/>>
			<input type="hidden" id="reassignbono.fieldName" name="reassignbono.fieldName" value="YREASSIGN.REASSIGNBONO"> 
			<input type="hidden" id="reassignbono.dataType" name="reassignbono.dataType" value="S">  
			<input type="hidden" id="reassignbono.option" name="reassignbono.option" value="like">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.reassigntype}：</td>
		<td  width="30%" >
			<div id="div_reassigntype_dict"></div>
			<input type="hidden" id="reassigntype.fieldName" name="reassigntype.fieldName" value="YREASSIGN.REASSIGNTYPE"> 
			<input type="hidden" id="reassigntype.dataType" name="reassigntype.dataType" value="S">  
			<input type="hidden" id="reassigntype.option" name="reassigntype.option" value="equal">
			<fisc:dictionary hiddenName="reassigntype.fieldValue" dictionaryName="YDREASSIGNTYPE" divId="div_reassigntype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.reassigntmethod}：</td>
		<td  width="40%" >
			<div id="div_reassigntmethod_dict"></div>
			<input type="hidden" id="reassigntmethod.fieldName" name="reassigntmethod.fieldName" value="YREASSIGN.REASSIGNTMETHOD"> 
			<input type="hidden" id="reassigntmethod.dataType" name="reassigntmethod.dataType" value="S">  
			<input type="hidden" id="reassigntmethod.option" name="reassigntmethod.option" value="equal">
			<fisc:dictionary hiddenName="reassigntmethod.fieldValue" dictionaryName="YDREASSIGNMETHOD" divId="div_reassigntmethod_dict" isNeedAuth="false"  ></fisc:dictionary>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/reassign/reassignManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/reassign/reassignManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	reassign:'${vt.boText}',
	reassign_Create:'${vt.boTextCreate}',
	// 复制创建
	reassign_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【重分配复制创建】操作的记录！
	reassign_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【重分配复制创建】操作时，只允许选择一条记录！
	reassign_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【重分配批量删除】操作，是否确定继续该操作？
	reassign_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【重分配批量删除】操作的记录！
	reassign_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【重分配删除】操作，是否确定继续该操作？
	reassign_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}',
	// 创建
	mCreate:'${vt.mCreate}'
};

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'${vt.boTextDetail}',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
