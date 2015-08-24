<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月25日 08点58分59秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象检验信息(Examine)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examine/examineManage.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examine/examineManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.outward3}：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="outward3.fieldValue" name="outward3.fieldValue" value="" <fisc:authentication sourceName="Examine.outward3" taskId=""/>>
			<input type="hidden" id="outward3.fieldName" name="outward3.fieldName" value="YEXAMINE.OUTWARD3"> 
			<input type="hidden" id="outward3.dataType" name="outward3.dataType" value="S">  
			<input type="hidden" id="outward3.option" name="outward3.option" value="like">
		</td>
    <td></td>
    <td></td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.vi} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="vi.fieldName" name="vi.fieldName" value="YEXAMINE.VI"> 
			<input type="hidden" id="vi.isRangeValue" name="vi.isRangeValue" value="true">
			<!-- UITYPE:13 -->
			<input type="text" class="inputText" id="vi_minValue" name="vi.minValue" <fisc:authentication sourceName="Examine.vi" taskId=""/>>
		</td>
		<td width="15%" align="right">${vt.sTo}：</td>
		<td width="40%">
			<input type="text" class="inputText" id="vi_maxValue" name="vi.maxValue" <fisc:authentication sourceName="Examine.vi" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.outward} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="outward.fieldName" name="outward.fieldName" value="YEXAMINE.OUTWARD"> 
			<input type="hidden" id="outward.isRangeValue" name="outward.isRangeValue" value="true">
			<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="outward_minValue" name="outward.minValue" <fisc:authentication sourceName="Examine.outward" taskId=""/>>
		</td>
		<td width="15%" align="right">${vt.sTo}：</td>
		<td width="40%">
			<input type="text" class="inputText" id="outward_maxValue" name="outward.maxValue" <fisc:authentication sourceName="Examine.outward" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.certificatedate} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="certificatedate.fieldName" name="certificatedate.fieldName" value="YEXAMINE.CERTIFICATEDATE"> 
			<input type="hidden" id="certificatedate.isRangeValue" name="certificatedate.isRangeValue" value="true">
			<!-- UITYPE:04 -->
    		<input type="text" id="certificatedate_minValue" name="certificatedate.minValue">
    		<input type="hidden" id="certificatedate.dataType" name="certificatedate.dataType" value="D"> 
			<fisc:calendar applyTo="certificatedate_minValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%"  align="right">${vt.sTo}：</td>
		<td width="40%">
			<input type="text" id="certificatedate_maxValue" name="certificatedate.maxValue">			
			<fisc:calendar applyTo="certificatedate_maxValue"  divId="" fieldName=""></fisc:calendar>
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
<script type="text/javascript">

//页面文本
var Txt={
	// 采购订单
	examine:'${vt.boText}',
	examine_Create:'${vt.boTextCreate}',
	// 复制创建
	examine_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【检验信息复制创建】操作的记录！
	examine_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【检验信息复制创建】操作时，只允许选择一条记录！
	examine_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【检验信息批量删除】操作，是否确定继续该操作？
	examine_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【检验信息批量删除】操作的记录！
	examine_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 凭证日期,结束日期要大于起始日期!
	certificatedate_EndDateShouldLargerStartDate:'${vt.endDateShouldLargerStartDate.certificatedate}',
	// 您选择了【检验信息删除】操作，是否确定继续该操作？
	examine_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
<fisc:grid divId="div_southForm" boName="Examine"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
