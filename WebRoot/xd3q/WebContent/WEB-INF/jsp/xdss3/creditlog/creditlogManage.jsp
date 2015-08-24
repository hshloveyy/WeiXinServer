<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年09月21日 07点09分30秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象授信日志表(Creditlog)管理页面
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
<fisc:grid divId="div_southForm" boName="Creditlog"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"  
defaultCondition="YCREDITLOG.USERID='${userid}'"  orderBySql=" YCREDITLOG.ACTIVEDATE desc"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.ytype}：</td>
		<td  width="30%" >
			<div id="div_ytype_dict"></div>
			<input type="hidden" id="ytype.fieldName" name="ytype.fieldName" value="YCREDITLOG.YTYPE"> 
			<input type="hidden" id="ytype.dataType" name="ytype.dataType" value="S">  
			<input type="hidden" id="ytype.option" name="ytype.option" value="like">
			<fisc:dictionary hiddenName="ytype.fieldValue" dictionaryName="YDCUSTPROTYPE" divId="div_ytype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.credittype}：</td>
		<td  width="40%" >
			<div id="div_credittype_dict"></div>
			<input type="hidden" id="credittype.fieldName" name="credittype.fieldName" value="YCREDITLOG.CREDITTYPE"> 
			<input type="hidden" id="credittype.dataType" name="credittype.dataType" value="S">  
			<input type="hidden" id="credittype.option" name="credittype.option" value="like">
			<fisc:dictionary hiddenName="credittype.fieldValue" dictionaryName="YDCUSTOMERCREDITTYPE" divId="div_credittype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">供应商：</td>
		<td  width="30%" >
			<div id="div_providerno_sh"></div>
			<input type="hidden" id="providerno.fieldName" name="providerno.fieldName" value="YCREDITLOG.PROVIDERNO"> 
			<input type="hidden" id="providerno.dataType" name="providerno.dataType" value="S">  
			<input type="hidden" id="providerno.option" name="providerno.option" value="like">
			<fisc:searchHelp divId="div_providerno_sh" boName="Creditlog" boProperty="providerno" searchType="field" hiddenName="providerno.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">客户：</td>
		<td  width="40%" >
			<div id="div_custno_sh"></div>
			<input type="hidden" id="custno.fieldName" name="custno.fieldName" value="YCREDITLOG.CUSTNO"> 
			<input type="hidden" id="custno.dataType" name="custno.dataType" value="S">  
			<input type="hidden" id="custno.option" name="custno.option" value="like">
			<fisc:searchHelp divId="div_custno_sh" boName="Creditlog" boProperty="custno" searchType="field" hiddenName="custno.fieldValue" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">供应商项目：</td>
		<td  width="30%" >
			<div id="div_prov_projectno_sh"></div>
<!--			<input type="hidden" id="prov_projectno.fieldName" name="prov_projectno.fieldName" value="YCREDITLOG.PROJECTNO"> -->
<!--			<input type="hidden" id="prov_projectno.dataType" name="prov_projectno.dataType" value="S">  -->
<!--			<input type="hidden" id="prov_projectno.option" name="prov_projectno.option" value="like">-->
			<fisc:searchHelp divId="div_prov_projectno_sh" boName="SupplyRemSum" boProperty="projectno" searchType="field" hiddenName="prov_projectno" valueField="PROJECT_ID" displayField="PROJECT_NAME"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">客户项目：</td>
		<td  width="40%" >
			<div id="div_cust_projectno_sh"></div>
<!--			<input type="hidden" id="projectno.fieldName" name="projectno.fieldName" value="YCREDITLOG.PROJECTNO"> -->
<!--			<input type="hidden" id="projectno.dataType" name="projectno.dataType" value="S">  -->
<!--			<input type="hidden" id="projectno.option" name="projectno.option" value="like">-->
			<fisc:searchHelp divId="div_cust_projectno_sh" boName="CustomerRemSum" boProperty="projectno" searchType="field" hiddenName="cust_projectno" valueField="PROJECT_ID" displayField="PROJECT_NAME"></fisc:searchHelp>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/creditlog/creditlogManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/creditlog/creditlogManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	creditlog:'${vt.boText}',
	// 您选择了【授信日志表删除】操作，是否确定继续该操作？
	creditlog_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-',
				{id:'_exp',text:'导出',handler:_expExcel},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
