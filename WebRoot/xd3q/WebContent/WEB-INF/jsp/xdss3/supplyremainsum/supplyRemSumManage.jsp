<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年09月14日 02点24分06秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象供应商项目余额查询(SupplyRemSum)管理页面
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
			boName="SupplyRemSum"
			editable="false" 
			needCheckBox="true" 
			needToolbar="true"
			needAutoLoad="true"
			defaultCondition=" YSUPPLIREMAINSUM.USERID='${userid}'  and EXISTS (SELECT 'x' FROM T_PROJECT_INFO PI WHERE PI.DEPT_ID  in (SELECT dept_id FROM t_sys_dept_user WHERE user_id = '${userid}' UNION SELECT DEPT_ID FROM T_SYS_USER_MANAGER_DEPT WHERE USER_ID = '${userid}')  AND PI.PROJECT_ID = PROJECTNO) "
			pageSize="10000" 
			orderBySql=" DEPTNAME,  endtime DESC, SUPPLIERNO, PROJECTNO "></fisc:grid>
		<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.supplierno}：</td>
		<td  width="30%" >
			<div id="div_supplierno_sh"></div>
			<input type="hidden" id="supplierno.fieldName" name="supplierno.fieldName" value="YSUPPLIREMAINSUM.SUPPLIERNO"> 
			<input type="hidden" id="supplierno.dataType" name="supplierno.dataType" value="S">  
			<input type="hidden" id="supplierno.option" name="supplierno.option" value="like">
			<fisc:searchHelp divId="div_supplierno_sh" boName="SupplyRemSum" boProperty="supplierno" searchType="field" hiddenName="supplierno.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.projectno}：</td>
		<td  width="40%" >
			<div id="div_projectno_sh"></div>
			<input type="hidden" id="projectno.fieldName" name="projectno.fieldName" value="YSUPPLIREMAINSUM.PROJECTNO"> 
			<input type="hidden" id="projectno.dataType" name="projectno.dataType" value="S">  
			<input type="hidden" id="projectno.option" name="projectno.option" value="like">
			<fisc:searchHelp divId="div_projectno_sh" boName="SupplyRemSum" boProperty="projectno" searchType="field" hiddenName="projectno.fieldValue" valueField="PROJECT_ID" displayField="PROJECT_NAME"></fisc:searchHelp>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/supplyremainsum/supplyRemSumManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/supplyremainsum/supplyRemSumManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	supplyRemSum:'${vt.boText}',
	// 您选择了【供应商项目余额查询删除】操作，是否确定继续该操作？
	supplyRemSum_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
	
		
	div_projectno_sh_sh.on('beforeclick',function(){
		if (div_supplierno_sh_sh.getValue() == '') {
			_getMainFrame().showInfo('请先输入客户');
		}
	});
	
});
</script>
