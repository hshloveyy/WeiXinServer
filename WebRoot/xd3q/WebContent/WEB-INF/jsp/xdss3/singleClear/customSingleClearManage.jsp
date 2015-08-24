<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月17日 11点12分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象客户单清帐(CustomSingleClear)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClear/customSingleClearManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClear/customSingleClearManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" needAuthentication="true"  boName="CustomSingleClear" url="/xdss3/singleClear/customSingleClearController.spr?action=queryGrid"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" defaultCondition="YCUSTOMSICLEAR.BUSINESSSTATE!='3' and YCUSTOMSICLEAR.BUSINESSSTATE!='-1'"  orderBySql=" YCUSTOMSICLEAR.LASTMODIFYTIME desc"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.custom}：</td>
		<td  width="30%" >
			<div id="div_custom_sh"></div>
			<input type="hidden" id="custom.fieldName" name="custom.fieldName" value="YCUSTOMSICLEAR.CUSTOM"> 
			<input type="hidden" id="custom.dataType" name="custom.dataType" value="S">  
			<input type="hidden" id="custom.option" name="custom.option" value="like">
			<fisc:searchHelp divId="div_custom_sh" boName="CustomSingleClear" boProperty="custom" searchType="field" hiddenName="custom.fieldValue" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.accountdate}：</td>
		<td  width="40%" >
			<input type="text" id="accountdate.fieldValue" name="accountdate.fieldValue" value="">
			<input type="hidden" id="accountdate.fieldName" name="accountdate.fieldName" value="YCUSTOMSICLEAR.ACCOUNTDATE"> 
			<input type="hidden" id="accountdate.dataType" name="accountdate.dataType" value="D">  
			<input type="hidden" id="accountdate.option" name="accountdate.option" value="like">
				<fisc:calendar applyTo="accountdate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.voucherdate}：</td>
		<td  width="30%" >
			<input type="text" id="voucherdate.fieldValue" name="voucherdate.fieldValue" value="">
			<input type="hidden" id="voucherdate.fieldName" name="voucherdate.fieldName" value="YCUSTOMSICLEAR.VOUCHERDATE"> 
			<input type="hidden" id="voucherdate.dataType" name="voucherdate.dataType" value="D">  
			<input type="hidden" id="voucherdate.option" name="voucherdate.option" value="like">
				<fisc:calendar applyTo="voucherdate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
		<td width="15%" align="right">${vt.property.subject}：</td>
		<td  width="40%" >
			<div id="div_subject_sh"></div>
			<input type="hidden" id="subject.fieldName" name="subject.fieldName" value="YCUSTOMSICLEAR.SUBJECT"> 
			<input type="hidden" id="subject.dataType" name="subject.dataType" value="S">  
			<input type="hidden" id="subject.option" name="subject.option" value="like">
			<fisc:searchHelp divId="div_subject_sh" boName="CustomSingleClear" boProperty="subject" searchType="field" hiddenName="subject.fieldValue" valueField="SAKNR" displayField="TXT20"></fisc:searchHelp>
		</td>
</tr>
<tr>
		<td width="15%" align="right">是否已清：</td>
		<td  width="30%" >			
			<select id="businessstate" name="businessstate">
               <option value="0" selected="true">否</option>
               <option value="1" >是</option>
            </select>
		</td>
		<td width="15%" align="right"></td>
		<td  width="40%" >			
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
	customSingleClear:'${vt.boText}',
	customSingleClear_Create:'${vt.boTextCreate}',
	// 您选择了【客户单清帐删除】操作，是否确定继续该操作？
	customSingleClear_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
