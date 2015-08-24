<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2011年11月03日 17点08分06秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象资金占用调整(FundAdjustment)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${fundAdjustment.fundid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.fundno" nodeId="${workflowNodeDefId}"/> >${vt.property.fundno}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="FundAdjustment.fundno" name="FundAdjustment.fundno" value="${fundAdjustment.fundno}" <fisc:authentication sourceName="FundAdjustment.fundno" nodeId="${workflowNodeDefId}"/>   readonly="readonly">
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.customer" nodeId="${workflowNodeDefId}"/> >${vt.property.customer}：</td>
		<td  width="40%">
			<div id="div_customer_sh"></div>
			<fisc:searchHelp divId="div_customer_sh" boName="FundAdjustment" boProperty="customer"  value="${fundAdjustment.customer}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.supplier" nodeId="${workflowNodeDefId}"/> >${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="FundAdjustment" boProperty="supplier"  value="${fundAdjustment.supplier}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.deptid" nodeId="${workflowNodeDefId}"/> >${vt.property.deptid}：</td>
		<td  width="40%">
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" boName="FundAdjustment" boProperty="deptid"  value="${fundAdjustment.deptid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.projectid" nodeId="${workflowNodeDefId}"/> >${vt.property.projectid}：</td>
		<td width="30%">
			<div id="div_projectid_sh"></div>
			<fisc:searchHelp divId="div_projectid_sh" boName="FundAdjustment" boProperty="projectid"  value="${fundAdjustment.projectid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.trade_type" nodeId="${workflowNodeDefId}"/> >${vt.property.trade_type}：</td>
		<td  width="40%">
			<div id="div_trade_type_dict"></div>
			<fisc:dictionary boName="FundAdjustment" boProperty="trade_type" dictionaryName="YDTRADETYPE" divId="div_trade_type_dict" isNeedAuth="false"  value="${fundAdjustment.trade_type}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.mat_group" nodeId="${workflowNodeDefId}"/> >${vt.property.mat_group}：</td>
		<td width="30%">
			<div id="div_mat_group_sh"></div>
			<fisc:searchHelp divId="div_mat_group_sh" boName="FundAdjustment" boProperty="mat_group"  value="${fundAdjustment.mat_group}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.locktime" nodeId="${workflowNodeDefId}"/> >${vt.property.locktime}：</td>
		<td  width="40%">
			<input type="text" id="FundAdjustment.locktime" name="FundAdjustment.locktime" value="">
				<fisc:calendar applyTo="FundAdjustment.locktime"  divId="" fieldName="" defaultValue="${fundAdjustment.locktime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.amount" nodeId="${workflowNodeDefId}"/> >${vt.property.amount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="FundAdjustment.amount" name="FundAdjustment.amount" value="${fundAdjustment.amount}" <fisc:authentication sourceName="FundAdjustment.amount" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.currency" nodeId="${workflowNodeDefId}"/> >${vt.property.currency}：</td>
		<td  width="40%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="FundAdjustment" boProperty="currency"  value="${fundAdjustment.currency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.amount2" nodeId="${workflowNodeDefId}"/> >${vt.property.amount2}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="FundAdjustment.amount2" name="FundAdjustment.amount2" value="${fundAdjustment.amount2}" <fisc:authentication sourceName="FundAdjustment.amount2" nodeId="${workflowNodeDefId}"/>  >
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.cmd" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.cmd}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="FundAdjustment.cmd" name="FundAdjustment.cmd" <fisc:authentication sourceName="FundAdjustment.cmd" nodeId="${workflowNodeDefId}"/>>${fundAdjustment.cmd}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="FundAdjustment" userId="${fundAdjustment.creator}"></fisc:user>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="FundAdjustment.createtime" name="FundAdjustment.createtime" value="${fundAdjustment.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.lastmodifytime" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifytime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="FundAdjustment.lastmodifytime" name="FundAdjustment.lastmodifytime" value="${fundAdjustment.lastmodifytime}"  readonly="readonly">
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="FundAdjustment.lastmodifyer" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifyer}：</td>
		<td  width="40%">
			<fisc:user boProperty="lastmodifyer" boName="FundAdjustment" userId="${fundAdjustment.lastmodifyer}"></fisc:user>
		</td>
	</tr>

	<input type="hidden" id="FundAdjustment.client" name="FundAdjustment.client" value="${fundAdjustment.client}">
	<input type="hidden" id="FundAdjustment.fundid" name="FundAdjustment.fundid" value="${fundAdjustment.fundid}">
	<input type="hidden" id="FundAdjustment.fundflag" name="FundAdjustment.fundflag" value="${fundAdjustment.fundflag}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/fundadjustment/fundAdjustmentView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/fundadjustment/fundAdjustmentViewGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var fundid = '${fundAdjustment.fundid}';	

//页面文本
var Txt={
	//资金占用调整
	fundAdjustment:'${vt.boText}',
	//boText复制创建
	fundAdjustment_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	fundAdjustment_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	fundAdjustment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		border:false,
				            		//height:310,
				            		autoScroll: true,
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
{id:'_create',text:'${vt.mCreate}',handler:_createFundAdjustment,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelFundAdjustment,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
//});
</script>