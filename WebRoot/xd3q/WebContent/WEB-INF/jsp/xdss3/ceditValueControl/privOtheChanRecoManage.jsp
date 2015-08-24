<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 14点37分34秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象供应商代垫费用查询(PrivOtheChanReco)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/privOtheChanRecoManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/privOtheChanRecoManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" 
boName="PrivOtheChanReco"  
editable="false" 
needCheckBox="true" 
needToolbar="true" 
needAutoLoad="true" 
orderBySql=" YOTHECHANRECO.Createtime DESC "
defaultCondition = "YOTHECHANRECO.USERTYPE = '3' and EXISTS (SELECT 'x' FROM T_PROJECT_INFO PI WHERE PI.DEPT_ID  in (SELECT dept_id FROM t_sys_dept_user WHERE user_id = '${userId}' UNION SELECT DEPT_ID FROM T_SYS_USER_MANAGER_DEPT WHERE USER_ID = '${userId}')  AND PI.PROJECT_ID = PROJECTID)"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">供应商：</td>
		<td  width="30%" >
			<div id="div_userid_sh"></div>
			<input type="hidden" id="userid.fieldName" name="userid.fieldName" value="YOTHECHANRECO.USERID"> 
			<input type="hidden" id="userid.dataType" name="userid.dataType" value="S">  
			<input type="hidden" id="userid.option" name="userid.option" value="like">
			<fisc:searchHelp divId="div_userid_sh" boName="PrivOtheChanReco" boProperty="userid" searchType="field" hiddenName="userid.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.projectid}：</td>
		<td  width="40%" >
			<div id="div_projectid_sh"></div>
			<input type="hidden" id="projectid.fieldName" name="projectid.fieldName" value="YOTHECHANRECO.PROJECTID"> 
			<input type="hidden" id="projectid.dataType" name="projectid.dataType" value="S">  
			<input type="hidden" id="projectid.option" name="projectid.option" value="like">
			<fisc:searchHelp divId="div_projectid_sh" boName="PrivOtheChanReco" boProperty="projectid" searchType="field" hiddenName="projectid.fieldValue" valueField="PROJECT_ID" displayField="PROJECT_NAME"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">创建日期：</td>
		<td  width="30%" >
			<div id="div_createtime_sh"></div>
			<input type="text" id="createtime.fieldValue" name="createtime.fieldValue" value="">
			<input type="hidden" id="createtime.fieldName" name="createtime.fieldName" value="YOTHECHANRECO.CREATETIME"> 
			<input type="hidden" id="createtime.dataType" name="createtime.dataType" value="D">  
			<input type="hidden" id="createtime.option" name="createtime.option" value="like">
			<fisc:calendar applyTo="createtime.fieldValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%" align="right">备注：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="remark.fieldValue" name="remark.fieldValue" value="" size="30">
			<input type="hidden" id="remark.fieldName" name="remark.fieldName" value="YOTHECHANRECO.REMARK"> 
			<input type="hidden" id="remark.dataType" name="remark.dataType" value="S">  
			<input type="hidden" id="remark.option" name="remark.option" value="like">
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
	privOtheChanReco:'${vt.boText}',
	// 您选择了【供应商代垫费用查询删除】操作，是否确定继续该操作？
	privOtheChanReco_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
