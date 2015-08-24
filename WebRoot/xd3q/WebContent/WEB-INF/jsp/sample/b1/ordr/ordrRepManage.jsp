<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月23日 15点52分47秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象OrdrRep(OrdrRep)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/ordr/ordrRepManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/ordr/ordrRepManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="OrdrRep"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.comments}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="comments.fieldValue" name="comments.fieldValue" value="" <fisc:authentication sourceName="OrdrRep.comments" taskId=""/>>
			<input type="hidden" id="comments.fieldName" name="comments.fieldName" value="YVORDRVIEW.COMMENTS"> 
			<input type="hidden" id="comments.dataType" name="comments.dataType" value="S">  
			<input type="hidden" id="comments.option" name="comments.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.cardcode}：</td>
		<td  width="40%" >
			<div id="div_cardcode_sh"></div>
			<input type="hidden" id="cardcode.fieldName" name="cardcode.fieldName" value="YVORDRVIEW.CARDCODE"> 
			<input type="hidden" id="cardcode.dataType" name="cardcode.dataType" value="S">  
			<input type="hidden" id="cardcode.option" name="cardcode.option" value="like">
			<fisc:searchHelp divId="div_cardcode_sh" boName="OrdrRep" boProperty="cardcode" searchType="field" hiddenName="cardcode.fieldValue" valueField="CARDCODE" displayField="CARDCODE"></fisc:searchHelp>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.cardname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="cardname.fieldValue" name="cardname.fieldValue" value="" <fisc:authentication sourceName="OrdrRep.cardname" taskId=""/>>
			<input type="hidden" id="cardname.fieldName" name="cardname.fieldName" value="YVORDRVIEW.CARDNAME"> 
			<input type="hidden" id="cardname.dataType" name="cardname.dataType" value="S">  
			<input type="hidden" id="cardname.option" name="cardname.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.numatcard}：</td>
		<td  width="40%" >
			<input type="text" class="inputText" id="numatcard.fieldValue" name="numatcard.fieldValue" value="" <fisc:authentication sourceName="OrdrRep.numatcard" taskId=""/>>
			<input type="hidden" id="numatcard.fieldName" name="numatcard.fieldName" value="YVORDRVIEW.NUMATCARD"> 
			<input type="hidden" id="numatcard.dataType" name="numatcard.dataType" value="S">  
			<input type="hidden" id="numatcard.option" name="numatcard.option" value="like">
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.slpname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="slpname.fieldValue" name="slpname.fieldValue" value="" <fisc:authentication sourceName="OrdrRep.slpname" taskId=""/>>
			<input type="hidden" id="slpname.fieldName" name="slpname.fieldName" value="YVORDRVIEW.SLPNAME"> 
			<input type="hidden" id="slpname.dataType" name="slpname.dataType" value="S">  
			<input type="hidden" id="slpname.option" name="slpname.option" value="like">
		</td>
    <td></td>
    <td></td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.docdate} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="docdate.fieldName" name="docdate.fieldName" value="YVORDRVIEW.DOCDATE"> 
			<input type="hidden" id="docdate.isRangeValue" name="docdate.isRangeValue" value="true">
    		<input type="text" id="docdate_minValue" name="docdate.minValue">
    		<input type="hidden" id="docdate.dataType" name="docdate.dataType" value="D"> 
			<fisc:calendar applyTo="docdate_minValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%"  align="right">${vt.sTo}：</td>
		<td width="40%">
			<input type="text" id="docdate_maxValue" name="docdate.maxValue">			
			<fisc:calendar applyTo="docdate_maxValue"  divId="" fieldName=""></fisc:calendar>
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
	ordrRep:'${vt.boText}',
	// 过帐日期,结束日期要大于起始日期!
	docdate_EndDateShouldLargerStartDate:'${vt.endDateShouldLargerStartDate.docdate}',
	// 您选择了【OrdrRep删除】操作，是否确定继续该操作？
	ordrRep_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
