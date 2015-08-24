<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月26日 11点17分54秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(OporDoc)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="OporDoc"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"  orderBySql=" YOPORDOC.DOCDATE ,YOPORDOC.DOCTOTAL "></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.cardCode}：</td>
		<td  width="30%" >
			<div id="div_cardCode_sh"></div>
			<input type="hidden" id="cardCode.fieldName" name="cardCode.fieldName" value="YOPORDOC.CARDCODE"> 
			<input type="hidden" id="cardCode.dataType" name="cardCode.dataType" value="S">  
			<input type="hidden" id="cardCode.option" name="cardCode.option" value="like">
			<fisc:searchHelp divId="div_cardCode_sh" boName="OporDoc" boProperty="cardCode" searchType="field" hiddenName="cardCode.fieldValue" valueField="CARDCODE" displayField="CARDCODE"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.taxDate}：</td>
		<td  width="40%" >
			<input type="text" id="taxDate.fieldValue" name="taxDate.fieldValue" value="">
			<input type="hidden" id="taxDate.fieldName" name="taxDate.fieldName" value="YOPORDOC.TAXDATE"> 
			<input type="hidden" id="taxDate.dataType" name="taxDate.dataType" value="D">  
			<input type="hidden" id="taxDate.option" name="taxDate.option" value="like">
				<fisc:calendar applyTo="taxDate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.docDate}：</td>
		<td  width="30%" >
			<input type="text" id="docDate.fieldValue" name="docDate.fieldValue" value="">
			<input type="hidden" id="docDate.fieldName" name="docDate.fieldName" value="YOPORDOC.DOCDATE"> 
			<input type="hidden" id="docDate.dataType" name="docDate.dataType" value="D">  
			<input type="hidden" id="docDate.option" name="docDate.option" value="like">
				<fisc:calendar applyTo="docDate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
    <td></td>
    <td></td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.comments} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="comments.fieldName" name="comments.fieldName" value="YOPORDOC.COMMENTS"> 
			<input type="hidden" id="comments.isRangeValue" name="comments.isRangeValue" value="true">
	    	<input type="hidden" id="comments.dataType" name="comments.dataType" value="S"> 
			<div id="div_comments_minValuedict"></div>
			<fisc:dictionary hiddenName="comments.minValue" dictionaryName="YDOVTG" divId="div_comments_minValuedict" isNeedAuth="false"   groupValue="I" ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.sTo}：</td>
		<td width="40%">
			<div id="div_comments_maxValuedict"></div>
			<fisc:dictionary hiddenName="comments.maxValue" dictionaryName="YDOVTG" divId="div_comments_maxValuedict" isNeedAuth="false"   groupValue="I" ></fisc:dictionary>
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
	oporDoc:'${vt.boText}',
	oporDoc_Create:'${vt.boTextCreate}',
	// 复制创建
	oporDoc_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【采购订单复制创建】操作的记录！
	oporDoc_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【采购订单复制创建】操作时，只允许选择一条记录！
	oporDoc_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【采购订单批量删除】操作，是否确定继续该操作？
	oporDoc_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【采购订单批量删除】操作的记录！
	oporDoc_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【采购订单删除】操作，是否确定继续该操作？
	oporDoc_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
