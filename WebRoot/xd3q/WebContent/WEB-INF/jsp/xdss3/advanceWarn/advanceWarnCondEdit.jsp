<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月13日 18点01分21秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象预警对像条件(AdvanceWarnCond)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnCondEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnCondEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.condno}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="AdvanceWarnCond.condno" name="AdvanceWarnCond.condno" value="${advanceWarnCond.condno}" <fisc:authentication sourceName="AdvanceWarnCond.condno" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.leftparentheses}：</td>
		<td width="30%" >
			<div id="div_leftparentheses_dict"></div>
			<fisc:dictionary boName="AdvanceWarnCond" boProperty="leftparentheses" dictionaryName="YDLEFTPARENTHESIS" divId="div_leftparentheses_dict" isNeedAuth="false"  value="${advanceWarnCond.leftparentheses}"   ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.fieldcode}：</td>
		<td width="30%" >
			<div id="div_fieldcode_sh"></div>
			<fisc:searchHelp divId="div_fieldcode_sh" boName="AdvanceWarnCond" boProperty="fieldcode" value="${advanceWarnCond.fieldcode}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.condrole}：</td>
		<td width="30%" >
			<div id="div_condrole_dict"></div>
			<fisc:dictionary boName="AdvanceWarnCond" boProperty="condrole" dictionaryName="YDOPERATOR" divId="div_condrole_dict" isNeedAuth="false"  value="${advanceWarnCond.condrole}"   ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.condvalue}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="AdvanceWarnCond.condvalue" name="AdvanceWarnCond.condvalue" value="${advanceWarnCond.condvalue}" <fisc:authentication sourceName="AdvanceWarnCond.condvalue" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.rightparentheses}：</td>
		<td width="30%" >
			<div id="div_rightparentheses_dict"></div>
			<fisc:dictionary boName="AdvanceWarnCond" boProperty="rightparentheses" dictionaryName="YDRIGHTPARENTHESIS" divId="div_rightparentheses_dict" isNeedAuth="false"  value="${advanceWarnCond.rightparentheses}"   ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.connectcond}：</td>
		<td width="30%" >
			<div id="div_connectcond_dict"></div>
			<fisc:dictionary boName="AdvanceWarnCond" boProperty="connectcond" dictionaryName="YDJOINCONDTION" divId="div_connectcond_dict" isNeedAuth="false"  value="${advanceWarnCond.connectcond}"   ></fisc:dictionary>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="AdvanceWarnCond.conditionid" name="AdvanceWarnCond.conditionid" value="">
<input type="hidden" id="AdvanceWarnCond.warnid" name="AdvanceWarnCond.warnid" value="">
<input type="hidden" id="AdvanceWarnCond.creator" name="AdvanceWarnCond.creator" value="">
<input type="hidden" id="AdvanceWarnCond.createtime" name="AdvanceWarnCond.createtime" value="">
<input type="hidden" id="AdvanceWarnCond.lastmodifyer" name="AdvanceWarnCond.lastmodifyer" value="">
<input type="hidden" id="AdvanceWarnCond.lastmodifytime" name="AdvanceWarnCond.lastmodifytime" value="">
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
							height:647.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
