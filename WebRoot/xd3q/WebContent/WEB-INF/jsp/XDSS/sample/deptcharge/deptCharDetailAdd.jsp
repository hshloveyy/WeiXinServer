<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月11日 09点45分51秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象费用明细(DeptCharDetail)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptcharge/deptCharDetailAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptcharge/deptCharDetailAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.budtemid}：</td>
		<td width="30%" >
			<div id="div_budtemid_sh"></div>
			<fisc:searchHelp divId="div_budtemid_sh" boName="DeptCharDetail" boProperty="budtemid" value="${deptCharDetail.budtemid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >${vt.property.budtemitemid}：</td>
		<td width="30%" >
			<div id="div_budtemitemid_sh"></div>
			<fisc:searchHelp divId="div_budtemitemid_sh" boName="DeptCharDetail" boProperty="budtemitemid" value="${deptCharDetail.budtemitemid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.buditemname}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.buditemname" name="DeptCharDetail.buditemname" value="${deptCharDetail.buditemname}" <fisc:authentication sourceName="DeptCharDetail.buditemname" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.last9month}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.last9month" name="DeptCharDetail.last9month" value="${deptCharDetail.last9month}" <fisc:authentication sourceName="DeptCharDetail.last9month" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.last3month}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.last3month" name="DeptCharDetail.last3month" value="${deptCharDetail.last3month}" <fisc:authentication sourceName="DeptCharDetail.last3month" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.lastpredict}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.lastpredict" name="DeptCharDetail.lastpredict" value="${deptCharDetail.lastpredict}" <fisc:authentication sourceName="DeptCharDetail.lastpredict" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.crntbudget}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.crntbudget" name="DeptCharDetail.crntbudget" value="${deptCharDetail.crntbudget}" <fisc:authentication sourceName="DeptCharDetail.crntbudget" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth1}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth1" name="DeptCharDetail.budmonth1" value="${deptCharDetail.budmonth1}" <fisc:authentication sourceName="DeptCharDetail.budmonth1" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth2}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth2" name="DeptCharDetail.budmonth2" value="${deptCharDetail.budmonth2}" <fisc:authentication sourceName="DeptCharDetail.budmonth2" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth3}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth3" name="DeptCharDetail.budmonth3" value="${deptCharDetail.budmonth3}" <fisc:authentication sourceName="DeptCharDetail.budmonth3" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth4}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth4" name="DeptCharDetail.budmonth4" value="${deptCharDetail.budmonth4}" <fisc:authentication sourceName="DeptCharDetail.budmonth4" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth5}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth5" name="DeptCharDetail.budmonth5" value="${deptCharDetail.budmonth5}" <fisc:authentication sourceName="DeptCharDetail.budmonth5" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth6}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth6" name="DeptCharDetail.budmonth6" value="${deptCharDetail.budmonth6}" <fisc:authentication sourceName="DeptCharDetail.budmonth6" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth7}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth7" name="DeptCharDetail.budmonth7" value="${deptCharDetail.budmonth7}" <fisc:authentication sourceName="DeptCharDetail.budmonth7" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth8}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth8" name="DeptCharDetail.budmonth8" value="${deptCharDetail.budmonth8}" <fisc:authentication sourceName="DeptCharDetail.budmonth8" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth9}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth9" name="DeptCharDetail.budmonth9" value="${deptCharDetail.budmonth9}" <fisc:authentication sourceName="DeptCharDetail.budmonth9" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth10}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth10" name="DeptCharDetail.budmonth10" value="${deptCharDetail.budmonth10}" <fisc:authentication sourceName="DeptCharDetail.budmonth10" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budmonth11}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth11" name="DeptCharDetail.budmonth11" value="${deptCharDetail.budmonth11}" <fisc:authentication sourceName="DeptCharDetail.budmonth11" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budmonth12}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budmonth12" name="DeptCharDetail.budmonth12" value="${deptCharDetail.budmonth12}" <fisc:authentication sourceName="DeptCharDetail.budmonth12" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.budorgacc}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.budorgacc" name="DeptCharDetail.budorgacc" value="${deptCharDetail.budorgacc}" <fisc:authentication sourceName="DeptCharDetail.budorgacc" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.prelimit}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.prelimit" name="DeptCharDetail.prelimit" value="${deptCharDetail.prelimit}" <fisc:authentication sourceName="DeptCharDetail.prelimit" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.actamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="DeptCharDetail.actamount" name="DeptCharDetail.actamount" value="${deptCharDetail.actamount}" <fisc:authentication sourceName="DeptCharDetail.actamount" taskId=""/>>
		</td>
	</tr>
<input type="hidden" id="DeptCharDetail.deptchardetailid" name="DeptCharDetail.deptchardetailid" value="">
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
							height:1072.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
