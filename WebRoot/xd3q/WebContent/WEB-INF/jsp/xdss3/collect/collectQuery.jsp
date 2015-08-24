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
		   needToolbar="false" 
		   needAutoLoad="true" 
		   needAuthentication="true"
		   orderBySql="YCOLLECT.LASTMODIFYTIME desc"></fisc:grid>
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
    <%--
	<tr>
		<td width="15%" align="right">立项号：</td>
		<td  width="30%" >
			<div id="div_project_no_sh"></div>
			<input type="hidden" id="project_no.fieldName" name="project_no.fieldName" value="YCOLLECTITEM.PROJECT_NO"> 
			<input type="hidden" id="project_no.dataType" name="project_no.dataType" value="S">  
			<input type="hidden" id="project_no.option" name="project_no.option" value="like">
			<fisc:searchHelp divId="div_project_no_sh" boName="CollectItem" boProperty="project_no" searchType="field" hiddenName="project_no.fieldValue" valueField="PROJECT_NO" displayField="PROJECT_NAME"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">合同编号：</td>
		<td  width="40%" >
			<div id="div_contract_no_sh"></div>
			<input type="hidden" id="contract_no.fieldName" name="contract_no.fieldName" value="YCOLLECTITEM.CONTRACT_NO"> 
			<input type="hidden" id="contract_no.dataType" name="contract_no.dataType" value="S">  
			<input type="hidden" id="contract_no.option" name="contract_no.option" value="like">
			<fisc:searchHelp divId="div_contract_no_sh" boName="CollectItem" boProperty="contract_no" searchType="field" hiddenName="contract_no.fieldValue" valueField="CONTRACT_NO" displayField="CONTRACT_NNAME"></fisc:searchHelp>
		</td>
	</tr>
	--%>
		<tr>
		<td width="15%" align="right">申请收款金额：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="applyamount.fieldValue" name="applyamount.fieldValue" value="" <fisc:authentication sourceName="Collect.applyamount" taskId=""/>>
			<input type="hidden" id="applyamount.fieldName" name="applyamount.fieldName" value="YCOLLECT.applyamount"> 
			<input type="hidden" id="applyamount.dataType" name="applyamount.dataType" value="S">  
			<input type="hidden" id="applyamount.option" name="applyamount.option" value="like">
		</td>
		<td width="15%" align="right">币别：</td>
		<td  width="30%" >
			<div id="div_currency_sh"></div>
			<input type="hidden" id="currency.fieldName" name="currency.fieldName" value="YCollect.CURRENCY"> 
			<input type="hidden" id="currency.dataType" name="currency.dataType" value="S">  
			<input type="hidden" id="currency.option" name="currency.option" value="like">
			<fisc:searchHelp divId="div_currency_sh" boName="Collect" boProperty="currency" searchType="field" hiddenName="currency.fieldValue" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
		</td>
    </tr>
		<tr>
		<td width="15%" align="right">收款方式：</td>
		<td  width="30%" >
			<div id="div_collecttype_dict"></div>
			<input type="hidden" id="collecttype.fieldName" name="collecttype.fieldName" value="YCOLLECT.collecttype"> 
			<input type="hidden" id="collecttype.dataType" name="collecttype.dataType" value="S">  
			<input type="hidden" id="collecttype.option" name="collecttype.option" value="like">
			<fisc:dictionary hiddenName="collecttype.fieldValue" dictionaryName="YDCOLLECTTYPE" divId="div_collecttype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">部门：</td>
		<td  width="40%" >
			<div id="div_dept_id_sh"></div>
			<input type="hidden" id="dept_id.fieldName" name="dept_id.fieldName" value="YCOLLECT.DEPT_ID"> 
			<input type="hidden" id="dept_id.dataType" name="dept_id.dataType" value="S">  
			<input type="hidden" id="dept_id.option" name="dept_id.option" value="like">
			<fisc:searchHelp divId="div_dept_id_sh" boName="Collect" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>		
	</tr>
		<tr>
		<td width="15%" align="right">${vt.property.businessstate}：</td>
		<td  width="30%" >
			<div id="div_businessstate_dict"></div>
			<input type="hidden" id="businessstate.fieldName" name="businessstate.fieldName" value="YCOLLECT.BUSINESSSTATE"> 
			<input type="hidden" id="businessstate.dataType" name="businessstate.dataType" value="S">  
			<input type="hidden" id="businessstate.option" name="businessstate.option" value="equal">
			<fisc:dictionary hiddenName="businessstate.fieldValue" dictionaryName="YDCOLLECTBUZSTATE" divId="div_businessstate_dict" isNeedAuth="false"  ></fisc:dictionary>
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
