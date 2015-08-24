<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月29日 09点52分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象凭证预览(Voucher)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/voucher/voucherManageSearch.js"></script>

</head>
<body>
<fisc:grid divId="div_southForm" boName="Voucher"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" orderBySql="voucherno,voucherclass" defaultCondition="companycode = '${companycode}' and trim(voucherno) is not null"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
	<tr>
	<td width="15%" align="right">凭证号：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="voucherno.fieldValue" name="voucherno.fieldValue" value="">
			<input type="hidden" id="voucherno.fieldName" name="voucherno.fieldName" value="YVoucher.voucherno"> 
			<input type="hidden" id="voucherno.dataType" name="voucherno.dataType" value="S">  
			<input type="hidden" id="voucherno.option" name="voucherno.option" value="like">
		</td>
		<td width="15%" align="right">业务ID：</td>
		<td  width="40%" >
			<input type="text" id="businessId.fieldValue" name="businessId.fieldValue" value="">
			<input type="hidden" id="businessId.fieldName" name="businessId.fieldName" value="YVoucher.businessId"> 
			<input type="hidden" id="businessId.dataType" name="businessId.dataType" value="S">  
			<input type="hidden" id="businessId.option" name="businessId.option" value="like">				
		</td>
		</tr>
		
	<tr>
		<td width="15%" align="right">创建时间从：</td>
		<td  width="30%" >
			<input type="text" id="importdate.fieldValue" name="importdate.fieldValue" value="">
			<input type="hidden" id="importdate.fieldName" name="importdate.fieldName" value="YVoucher.importdate"> 
			<input type="hidden" id="importdate.dataType" name="importdate.dataType" value="D">  
			<input type="hidden" id="importdate.option" name="importdate.option" value="like">
				<fisc:calendar applyTo="importdate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
		<td width="15%" align="right">到：</td>
		<td  width="30%" >
			<input type="text" id="importdate.fieldValue_from" name="importdate.fieldValue_from" value="">
			<input type="hidden" id="importdate.fieldName" name="importdate.fieldName" value="YVoucher.importdate"> 
			<input type="hidden" id="importdate.dataType" name="importdate.dataType" value="D">  
			<input type="hidden" id="importdate.option" name="importdate.option" value="like">
				<fisc:calendar applyTo="importdate.fieldValue_from"  divId="" fieldName="" ></fisc:calendar>
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
	voucher:'${vt.boText}',
	// 您选择了【凭证预览删除】操作，是否确定继续该操作？
	voucher_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				{id:'_search',text:'重新生成凭证',handler:genvoucher2,iconCls:'icon-search'},'-'
				],
		renderTo:"div_toolbar"
	});
	
	var tb =Voucher_grid.getTopToolbar();
	tb.items.get('Voucher._genVoucher').hide();		// 隐藏"生成凭证"按钮
	
	
});
</script>
