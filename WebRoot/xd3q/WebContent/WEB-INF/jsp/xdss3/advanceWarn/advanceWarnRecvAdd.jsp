<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月13日 18点01分20秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象预警对像接收人(AdvanceWarnRecv)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnRecvAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnRecvAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.createdeptid}：</td>
		<td width="30%" >
			<div id="div_createdeptid_sh"></div>
			<fisc:searchHelp divId="div_createdeptid_sh" boName="AdvanceWarnRecv" boProperty="createdeptid" value="${advanceWarnRecv.createdeptid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.receiveuserid}：</td>
		<td width="30%" >
			<div id="div_receiveuserid_sh"></div>
			<fisc:searchHelp divId="div_receiveuserid_sh" boName="AdvanceWarnRecv" boProperty="receiveuserid" value="${advanceWarnRecv.receiveuserid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.receivetype}：</td>
		<td width="30%" >
			<div id="div_receivetype_dict"></div>
			<fisc:dictionary boName="AdvanceWarnRecv" boProperty="receivetype" dictionaryName="YDRECEIVETYPE" divId="div_receivetype_dict" isNeedAuth="false"  value="${advanceWarnRecv.receivetype}"   ></fisc:dictionary>
		</td>
		<td width="20%" align="right" >${vt.property.receiveaddr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="AdvanceWarnRecv.receiveaddr" name="AdvanceWarnRecv.receiveaddr" value="${advanceWarnRecv.receiveaddr}" <fisc:authentication sourceName="AdvanceWarnRecv.receiveaddr" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right"><input type="button" value="增加接收员工" onclick="listClick();"></td>
		<td width="30%"></td>
		<td width="20%" align="right">员工列表:</td>
		<td width="30%"><input typr="text" name="userNameList" id="userNameList" size="28" readonly="true">
		<input type="hidden" id="userIdList" name="userIdList" value="">
		</td>
	</tr>
<input type="hidden" id="AdvanceWarnRecv.receiveid" name="AdvanceWarnRecv.receiveid" value="">
<input type="hidden" id="AdvanceWarnRecv.warnid" name="AdvanceWarnRecv.warnid" value="">
<input type="hidden" id="AdvanceWarnRecv.creator" name="AdvanceWarnRecv.creator" value="">
<input type="hidden" id="AdvanceWarnRecv.createtime" name="AdvanceWarnRecv.createtime" value="">
<input type="hidden" id="AdvanceWarnRecv.lastmodifyer" name="AdvanceWarnRecv.lastmodifyer" value="">
<input type="hidden" id="AdvanceWarnRecv.lastmodifytime" name="AdvanceWarnRecv.lastmodifytime" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

//多选员工回调函数
function selectUserCallBack(jsonArrayData)
{
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();

	var nameList = document.getElementById('userNameList').value;
	var idList = document.getElementById('userIdList').value; 
	for(var i=0;i<returnvalue.length;i++){
		if (i ==0){
			idList = idList + returnvalue[i].json.userId;
			nameList = nameList + returnvalue[i].json.userName;
		}else{
			idList = idList + '|' + returnvalue[i].json.userId;
			nameList = nameList + ',' + returnvalue[i].json.userName;
		}
	}

	document.getElementById('userNameList').value = nameList;
	document.getElementById('userIdList').value =idList;
}

function listClick(){
	top.ExtModalWindowUtil.show('选择接收用户信息',
			'orgController.spr?action=receiveUser',
			'',
			selectUserCallBack,
			{width:600,height:400});
}


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
							height:477.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
