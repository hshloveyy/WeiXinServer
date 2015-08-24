<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月25日 08点58分59秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象检查信息明细(ExamineItem)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examineItem/examineItemEdit.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examineItem/examineItemEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
		<tr>
		 <td width="20%" align="right" >${vt.property.memo}：</td>
		 <td width="30%" >
			<!--  aa : MEMO bb: VI-->
			<input type="text" class="inputText" id="ExamineItem.memo" name="ExamineItem.memo" value="${examineItem.memo}" <fisc:authentication sourceName="ExamineItem.memo" taskId=""/>>
			</td>
		 <td width="20%" align="right" >${vt.property.certificatedate}：</td>
		 <td width="30%" >
				<input type="text" id="ExamineItem.certificatedate" name="ExamineItem.certificatedate" value="">
					<fisc:calendar applyTo="ExamineItem.certificatedate" format="Ymd" divId="" fieldName=""  defaultValue="${examineItem.certificatedate}" ></fisc:calendar>
			</td>
			</tr>
		<tr>
		 <td width="20%" align="right" >${vt.property.outward4}：</td>
		 <td width="30%" >
			<!--  aa : OUTWARD4 bb: VI-->
			<input type="text" class="inputText" id="ExamineItem.outward4" name="ExamineItem.outward4" value="${examineItem.outward4}" <fisc:authentication sourceName="ExamineItem.outward4" taskId=""/>>
			</td>
		      <td></td><td></td>
			</tr>
<input type="hidden" id="ExamineItem.viitem" name="ExamineItem.viitem" value="">
<input type="hidden" id="ExamineItem.vi" name="ExamineItem.vi" value="">
</table>
</form>
</div>

<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
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

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			border:false,
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:265,
					border:false,
					bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_center'
				}],
			buttons:[{
				        	text:Txt.ok,
				        	name:'btn_save',
				        	minWidth: 40,
				        	handler:_save
				     },
				     {
				        	text:Txt.cancel,
				        	minWidth: 40,
				        	name:'btn_cancel',
				        	handler:_cancel
				}]
		}]
	});
//});
</script>
