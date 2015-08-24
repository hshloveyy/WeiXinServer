<%-- 
保证金转货款
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保证金转货款</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectDepositManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectDepositManageGen.js"></script>
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
		   defaultCondition="trim(OLDCOLLECTITEMID) is not null "
		   orderBySql="createtime DESC "></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
		<td width="15%" align="right">收款单号：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="collectno.fieldValue" name="collectno.fieldValue" value="" <fisc:authentication sourceName="Collect.collectno" taskId=""/>>
			<input type="hidden" id="collectno.fieldName" name="collectno.fieldName" value="YCOLLECT.COLLECTNO"> 
			<input type="hidden" id="collectno.dataType" name="collectno.dataType" value="S">  
			<input type="hidden" id="collectno.option" name="collectno.option" value="like">
		</td>
		<td width="15%" align="right">客户：</td>
		<td  width="40%" >
			<div id="div_customer_sh"></div>
			<input type="hidden" id="customer.fieldName" name="customer.fieldName" value="YCOLLECT.CUSTOMER"> 
			<input type="hidden" id="customer.dataType" name="customer.dataType" value="S">  
			<input type="hidden" id="customer.option" name="customer.option" value="like">
			<fisc:searchHelp divId="div_customer_sh" boName="Collect" boProperty="customer" searchType="field" hiddenName="customer.fieldValue" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
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
	collect:'保证金转货款',
	collect_Create:'保证金转货款新增',
	// 复制创建
	collect_CopyCreate:'复制创建',
	// 复制创建
	mCopyCreate:'复制创建',
	// 请选择需要进行【收款复制创建】操作的记录！
	collect_CopyCreate_SelectToOperation:'请选择需要进行【收款复制创建】操作的记录！',
	// 进行【收款复制创建】操作时，只允许选择一条记录！
	collect_CopyCreate_AllowOnlyOneItemForOperation:'进行【收款复制创建】操作时，只允许选择一条记录！',
	// 您选择了【收款批量删除】操作，是否确定继续该操作？
	collect_Deletes_ConfirmOperation:'您选择了【收款批量删除】操作，是否确定继续该操作？',
	// 请选择需要进行【收款批量删除】操作的记录！
	collect_Deletes_SelectToOperation:'请选择需要进行【收款批量删除】操作的记录！',
	// 您选择了【收款删除】操作，是否确定继续该操作？
	collect_Delete_ConfirmOperation:'您选择了【收款删除】操作，是否确定继续该操作？',
	// 提示
	cue:'提示',
	// 确定
	ok:'确定',
	// 取消
	cancel:'取消',
	// 创建
	mCreate:'创建'
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
		            title:'保证金转货款明细',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
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
