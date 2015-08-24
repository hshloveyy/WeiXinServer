<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月16日 11点33分53秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象人员(Personnel)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/personnelManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/personnelManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="Personnel"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" ></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.personnelname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="personnelname.fieldValue" name="personnelname.fieldValue" value="" <fisc:authentication sourceName="Personnel.personnelname" taskId=""/>>
			<input type="hidden" id="personnelname.fieldName" name="personnelname.fieldName" value="YPERSONNEL.PERSONNELNAME"> 
			<input type="hidden" id="personnelname.dataType" name="personnelname.dataType" value="S">  
			<input type="hidden" id="personnelname.option" name="personnelname.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.title}：</td>
		<td  width="40%" >
			<div id="div_title_dict"></div>
			<input type="hidden" id="title.fieldName" name="title.fieldName" value="YPERSONNEL.TITLE"> 
			<input type="hidden" id="title.dataType" name="title.dataType" value="S">  
			<input type="hidden" id="title.option" name="title.option" value="like">
			<fisc:dictionary hiddenName="title.fieldValue" dictionaryName="YDTITLE" divId="div_title_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.sex}：</td>
		<td  width="30%" >
			<div id="div_sex_dict"></div>
			<input type="hidden" id="sex.fieldName" name="sex.fieldName" value="YPERSONNEL.SEX"> 
			<input type="hidden" id="sex.dataType" name="sex.dataType" value="S">  
			<input type="hidden" id="sex.option" name="sex.option" value="like">
			<fisc:dictionary hiddenName="sex.fieldValue" dictionaryName="YDSEX" divId="div_sex_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.birthday}：</td>
		<td  width="40%" >
			<input type="text" id="birthday.fieldValue" name="birthday.fieldValue" value="">
			<input type="hidden" id="birthday.fieldName" name="birthday.fieldName" value="YPERSONNEL.BIRTHDAY"> 
			<input type="hidden" id="birthday.dataType" name="birthday.dataType" value="D">  
			<input type="hidden" id="birthday.option" name="birthday.option" value="like">
				<fisc:calendar applyTo="birthday.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.status}：</td>
		<td  width="30%" >
			<div id="div_status_dict"></div>
			<input type="hidden" id="status.fieldName" name="status.fieldName" value="YPERSONNEL.STATUS"> 
			<input type="hidden" id="status.dataType" name="status.dataType" value="S">  
			<input type="hidden" id="status.option" name="status.option" value="like">
			<fisc:dictionary hiddenName="status.fieldValue" dictionaryName="YDPERSONNELSTATUS" divId="div_status_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.postid}：</td>
		<td  width="40%" >
			<div id="div_postid_dict"></div>
			<input type="hidden" id="postid.fieldName" name="postid.fieldName" value="YPERSONNEL.POSTID"> 
			<input type="hidden" id="postid.dataType" name="postid.dataType" value="S">  
			<input type="hidden" id="postid.option" name="postid.option" value="like">
			<fisc:dictionary hiddenName="postid.fieldValue" dictionaryName="YPOST" divId="div_postid_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.positionid}：</td>
		<td  width="30%" >
			<div id="div_positionid_dict"></div>
			<input type="hidden" id="positionid.fieldName" name="positionid.fieldName" value="YPERSONNEL.POSITIONID"> 
			<input type="hidden" id="positionid.dataType" name="positionid.dataType" value="S">  
			<input type="hidden" id="positionid.option" name="positionid.option" value="like">
			<fisc:dictionary hiddenName="positionid.fieldValue" dictionaryName="YPOSITION" divId="div_positionid_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.companyid}：</td>
		<td  width="40%" >
			<div id="div_companyid_sh"></div>
			<input type="hidden" id="companyid.fieldName" name="companyid.fieldName" value="YPERSONNEL.COMPANYID"> 
			<input type="hidden" id="companyid.dataType" name="companyid.dataType" value="S">  
			<input type="hidden" id="companyid.option" name="companyid.option" value="like">
			<fisc:searchHelp divId="div_companyid_sh" boName="Personnel" boProperty="companyid" searchType="field" hiddenName="companyid.fieldValue" valueField="COMPANYCODE" displayField="COMPANYNAME"></fisc:searchHelp>
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
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	personnel:'${vt.boText}',
	personnel_Create:'${vt.boTextCreate}',
	// 复制创建
	personnel_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【人员复制创建】操作的记录！
	personnel_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【人员复制创建】操作时，只允许选择一条记录！
	personnel_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【人员批量删除】操作，是否确定继续该操作？
	personnel_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【人员批量删除】操作的记录！
	personnel_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【人员删除】操作，是否确定继续该操作？
	personnel_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
