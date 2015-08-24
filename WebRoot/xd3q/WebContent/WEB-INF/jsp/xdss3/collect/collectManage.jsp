<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月20日 01点22分48秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象收款(Collect)管理页面
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
<!-- 邱杰烜  2010-09-08 加入数据权限控制 -->
<fisc:grid divId="div_southForm" 
		   boName="Collect"  
		   editable="false"
		   needCheckBox="true" 
		   needToolbar="true" 
		   needAutoLoad="true" 
		   needAuthentication="true"
		   orderBySql="YCOLLECT.LASTMODIFYTIME desc" ></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.collectno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="collectno.fieldValue" name="collectno.fieldValue" value="" <fisc:authentication sourceName="Collect.collectno" taskId=""/>>
			<input type="hidden" id="collectno.fieldName" name="collectno.fieldName" value="YCOLLECT.COLLECTNO"> 
			<input type="hidden" id="collectno.dataType" name="collectno.dataType" value="S">  
			<input type="hidden" id="collectno.option" name="collectno.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.customer}：</td>
		<td  width="40%" >
			<div id="div_customer_sh"></div>
			<input type="hidden" id="customer.fieldName" name="customer.fieldName" value="YCOLLECT.CUSTOMER"> 
			<input type="hidden" id="customer.dataType" name="customer.dataType" value="S">  
			<input type="hidden" id="customer.option" name="customer.option" value="like">
			<fisc:searchHelp divId="div_customer_sh" boName="Collect" boProperty="customer" searchType="field" hiddenName="customer.fieldValue" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	collect:'${vt.boText}',
	collect_Create:'${vt.boTextCreate}',
	// 您选择了【收款删除】操作，是否确定继续该操作？
	collect_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
