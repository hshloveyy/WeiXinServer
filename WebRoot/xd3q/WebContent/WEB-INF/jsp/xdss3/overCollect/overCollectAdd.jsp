<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月01日 06点24分20秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象多收款表(OverCollect)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/overCollect/overCollectAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/overCollect/overCollectAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.collectid}：</td>
		<td width="30%" >
		    <input type="text" class="inputText" id="OverCollect.collectid" name="OverCollect.collectid" value="${overCollect.collect.collectid}" <fisc:authentication sourceName="OverCollect.collectid" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.contract_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.contract_no" name="OverCollect.contract_no" value="${overCollect.contract_no}" <fisc:authentication sourceName="OverCollect.contract_no" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.project_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.project_no" name="OverCollect.project_no" value="${overCollect.project_no}" <fisc:authentication sourceName="OverCollect.project_no" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.overamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.overamount" name="OverCollect.overamount" value="${overCollect.overamount}" <fisc:authentication sourceName="OverCollect.overamount" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.prepaidamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.prepaidamount" name="OverCollect.prepaidamount" value="${overCollect.prepaidamount}" <fisc:authentication sourceName="OverCollect.prepaidamount" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.diffamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.diffamount" name="OverCollect.diffamount" value="${overCollect.diffamount}" <fisc:authentication sourceName="OverCollect.diffamount" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.cashflow}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.cashflow" name="OverCollect.cashflow" value="${overCollect.cashflow}" <fisc:authentication sourceName="OverCollect.cashflow" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.overtype}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.overtype" name="OverCollect.overtype" value="${overCollect.overtype}" <fisc:authentication sourceName="OverCollect.overtype" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.itemtext}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.itemtext" name="OverCollect.itemtext" value="${overCollect.itemtext}" <fisc:authentication sourceName="OverCollect.itemtext" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.exportisclear}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.exportisclear" name="OverCollect.exportisclear" value="${overCollect.exportisclear}" <fisc:authentication sourceName="OverCollect.exportisclear" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.shipisclear}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.shipisclear" name="OverCollect.shipisclear" value="${overCollect.shipisclear}" <fisc:authentication sourceName="OverCollect.shipisclear" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.billisclear}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.billisclear" name="OverCollect.billisclear" value="${overCollect.billisclear}" <fisc:authentication sourceName="OverCollect.billisclear" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.moneyisclear}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OverCollect.moneyisclear" name="OverCollect.moneyisclear" value="${overCollect.moneyisclear}" <fisc:authentication sourceName="OverCollect.moneyisclear" taskId=""/>>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="OverCollect.overcollectid" name="OverCollect.overcollectid" value="">
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
							height:690.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
