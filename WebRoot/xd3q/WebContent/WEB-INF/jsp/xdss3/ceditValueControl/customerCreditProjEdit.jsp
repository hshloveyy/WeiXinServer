<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 11点05分03秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象客户信用额度下挂立项配置表(CustomerCreditProj)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditProjEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditProjEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
		<tr>
	      <td></td><td></td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.projectid}：</td>
		<td width="30%" >
			<div id="div_projectid_sh"></div>
				<fisc:searchHelp divId="div_projectid_sh" boName="CustomerCreditProj" boProperty="projectid" 
					value="${customerCreditProj.projectid}" defaultCondition=" DEPT_ID in (SELECT dept_id FROM t_sys_dept_user WHERE user_id = '${userId}' UNION SELECT DEPT_ID FROM T_SYS_USER_MANAGER_DEPT WHERE USER_ID = '${userId}') "></fisc:searchHelp>
		</td>
	</tr>
<input type="hidden" id="CustomerCreditProj.configprojectid" name="CustomerCreditProj.configprojectid" value="">
<input type="hidden" id="CustomerCreditProj.configid" name="CustomerCreditProj.configid" value="">
<input type="hidden" id="CustomerCreditProj.projectno" name="CustomerCreditProj.projectno" value="">
<input type="hidden" id="CustomerCreditProj.creator" name="CustomerCreditProj.creator" value="">
<input type="hidden" id="CustomerCreditProj.createtime" name="CustomerCreditProj.createtime" value="">
<input type="hidden" id="CustomerCreditProj.lastmodifyer" name="CustomerCreditProj.lastmodifyer" value="">
<input type="hidden" id="CustomerCreditProj.lastmodifytime" name="CustomerCreditProj.lastmodifytime" value="">
<input type="hidden" id="CustomerCreditProj.otherprepayvalue" name="CustomerCreditProj.otherprepayvalue" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//页面文本
var Txt={
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_save',text:'${vt.sOk}',handler:_save,iconCls:'icon-table-save'},'-',
					{id:'_cancel',text:'${vt.sCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
					' '
				   ],
			renderTo:'div_toolbar'
});
	
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
					region:'north',
					layout:'fit',
					height:26,
					border:false,
					contentEl:'div_toolbar'
	   		   },{
					region:'center',
					border:false,
					buttonAlign:'center',
					items:[{
							layout:'fit',
							region:'center',
							height:435.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
