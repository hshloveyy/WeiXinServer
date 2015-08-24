<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 11点05分03秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象客户代垫额度和发货额度配置(CustomerCreditConf)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditConfManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditConfManageGen.js"></script>
</head>
<body>
		<fisc:grid divId="div_southForm" boName="CustomerCreditConf"
			editable="false" needCheckBox="true" needToolbar="true"
			needAutoLoad="true"
			orderBySql=" endtime desc" ></fisc:grid>
		<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.customerid}：</td>
		<td  width="30%" >
			<div id="div_customerid_sh"></div>
			<input type="hidden" id="customerid.fieldName" name="customerid.fieldName" value="YCUSTCREDCONF.CUSTOMERID"> 
			<input type="hidden" id="customerid.dataType" name="customerid.dataType" value="S">  
			<input type="hidden" id="customerid.option" name="customerid.option" value="like">
			<fisc:searchHelp divId="div_customerid_sh" boName="CustomerCreditConf" boProperty="customerid" searchType="field" hiddenName="customerid.fieldValue" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
		</td>
    	<td width="15%" align="right">${vt.property.credittype}：</td>
		<td  width="40%">
			<div id="div_credittype_dict"></div>
			<input type="hidden" id="credittype.fieldName" name="credittype.fieldName" value="YCUSTCREDCONF.CREDITTYPE"> 
			<input type="hidden" id="credittype.dataType" name="credittype.dataType" value="S">  
			<input type="hidden" id="credittype.option" name="credittype.option" value="like">
			<fisc:dictionary hiddenName="credittype.fieldValue" dictionaryName="YDCUSTOMERCREDITTYPE" divId="div_credittype_dict" isNeedAuth="false"  ></fisc:dictionary>
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
	customerCreditConf:'${vt.boText}',
	customerCreditConf_Create:'${vt.boTextCreate}',
	// 复制创建
	customerCreditConf_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【客户代垫额度和发货额度配置复制创建】操作的记录！
	customerCreditConf_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【客户代垫额度和发货额度配置复制创建】操作时，只允许选择一条记录！
	customerCreditConf_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【客户代垫额度和发货额度配置批量删除】操作，是否确定继续该操作？
	customerCreditConf_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【客户代垫额度和发货额度配置批量删除】操作的记录！
	customerCreditConf_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【客户代垫额度和发货额度配置删除】操作，是否确定继续该操作？
	customerCreditConf_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
