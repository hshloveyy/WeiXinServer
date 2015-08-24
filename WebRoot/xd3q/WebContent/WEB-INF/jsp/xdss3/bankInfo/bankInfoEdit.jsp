<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月29日 11点38分28秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象银行信息(BankInfo)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/bankInfo/bankInfoEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/bankInfo/bankInfoEditGen.js"></script>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${bankInfo.bank_id}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="BankInfo.bank_code" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.bank_code}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BankInfo.bank_code" name="BankInfo.bank_code" value="${bankInfo.bank_code}"   <fisc:authentication sourceName="BankInfo.bank_code" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="BankInfo.bank_name" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.bank_name}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BankInfo.bank_name" name="BankInfo.bank_name" value="${bankInfo.bank_name}"   <fisc:authentication sourceName="BankInfo.bank_name" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="BankInfo.bank_account" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.bank_account}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BankInfo.bank_account" name="BankInfo.bank_account" value="${bankInfo.bank_account}"   <fisc:authentication sourceName="BankInfo.bank_account" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="BankInfo.bank_hkont" nodeId="${workflowNodeDefId}"/> >${vt.property.bank_hkont}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BankInfo.bank_hkont" name="BankInfo.bank_hkont" value="${bankInfo.bank_hkont}"   <fisc:authentication sourceName="BankInfo.bank_hkont" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>

	<input type="hidden" id="BankInfo.client" name="BankInfo.client" value="${bankInfo.client}">
	<input type="hidden" id="BankInfo.bank_id" name="BankInfo.bank_id" value="${bankInfo.bank_id}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var bank_id = '${bankInfo.bank_id}';	

//页面文本
var Txt={
	//银行信息
	bankInfo:'${vt.boText}',
	//boText复制创建
	bankInfo_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	bankInfo_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	bankInfo_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
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
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:85,
				            		contentEl:'div_center'
						}
							   ]
				   }]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateBankInfo,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBankInfo,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

//});
</script>