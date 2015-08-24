<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月11日 09点53分07秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象未明户收款(UnnamerCollect)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${unnamerCollect.unnamercollectid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="UnnamerCollect.unnamercollectno" nodeId="${workflowNodeDefId}"/> >${vt.property.unnamercollectno}：</td>
		<td width="30%">
			<input type="text" readonly="readonly" class="inputText" id="UnnamerCollect.unnamercollectno" name="UnnamerCollect.unnamercollectno" value="${unnamerCollect.unnamercollectno}" <fisc:authentication sourceName="UnnamerCollect.unnamercollectno" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right"  width="15%">
		</td>
		<td width="40%">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.applyamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.applyamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="UnnamerCollect.applyamount" name="UnnamerCollect.applyamount" value="${unnamerCollect.applyamount}"   <fisc:authentication sourceName="UnnamerCollect.applyamount" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.currency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currency}：</td>
		<td  width="40%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="UnnamerCollect" boProperty="currency"  value="${unnamerCollect.currency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.collcetbankacc" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collcetbankacc}：</td>
		<td  width="40%">
			<div id="div_collcetbankacc_sh"></div>
			<fisc:searchHelp divId="div_collcetbankacc_sh" boName="UnnamerCollect" boProperty="collcetbankacc"  value="${unnamerCollect.collcetbankacc}" defaultCondition="BANK_ACCOUNT<>' '"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.collectbankname" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collectbankname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="UnnamerCollect.collectbankname" name="UnnamerCollect.collectbankname" value="${unnamerCollect.collectbankname}"   <fisc:authentication sourceName="UnnamerCollect.collectbankname" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="UnnamerCollect.createtime" name="UnnamerCollect.createtime" value="${unnamerCollect.createtime}"  readonly="readonly">
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.deptid" nodeId="${workflowNodeDefId}"/> >${vt.property.deptid}：</td>
		<td  width="40%">
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" boName="UnnamerCollect" boProperty="deptid"  value="${unnamerCollect.deptid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="UnnamerCollect" userId="${unnamerCollect.creator}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="UnnamerCollect.voucherdate" name="UnnamerCollect.voucherdate" value="">
				<fisc:calendar applyTo="UnnamerCollect.voucherdate"  divId="" fieldName="" defaultValue="${unnamerCollect.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="UnnamerCollect.text" name="UnnamerCollect.text"  <fisc:authentication sourceName="UnnamerCollect.text" nodeId="${workflowNodeDefId}"/>>${unnamerCollect.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="UnnamerCollect.remark" name="UnnamerCollect.remark"  <fisc:authentication sourceName="UnnamerCollect.remark" nodeId="${workflowNodeDefId}"/>>${unnamerCollect.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.applyamount2" nodeId="${workflowNodeDefId}"/> >${vt.property.applyamount2}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="UnnamerCollect.applyamount2" name="UnnamerCollect.applyamount2" value="${unnamerCollect.applyamount2}"   <fisc:authentication sourceName="UnnamerCollect.applyamount2" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td  width="40%">
			<input type="text" id="UnnamerCollect.accountdate" name="UnnamerCollect.accountdate" value="">
				<fisc:calendar applyTo="UnnamerCollect.accountdate"  divId="" fieldName="" defaultValue="${unnamerCollect.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.collectrate" nodeId="${workflowNodeDefId}"/> >${vt.property.collectrate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="UnnamerCollect.collectrate" name="UnnamerCollect.collectrate" value="${unnamerCollect.collectrate}"   <fisc:authentication sourceName="UnnamerCollect.collectrate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="UnnamerCollect.cashflowitem" nodeId="${workflowNodeDefId}"/> >${vt.property.cashflowitem}：</td>
		<td  width="40%">
			<div id="div_cashflowitem_sh"></div>
			<fisc:searchHelp divId="div_cashflowitem_sh" boName="UnnamerCollect" boProperty="cashflowitem"  value="${unnamerCollect.cashflowitem}"></fisc:searchHelp>
		</td>
	</tr>

	<input type="hidden" id="UnnamerCollect.client" name="UnnamerCollect.client" value="${unnamerCollect.client}">
	<input type="hidden" id="UnnamerCollect.unnamercollectid" name="UnnamerCollect.unnamercollectid" value="${unnamerCollect.unnamercollectid}">
	<input type="hidden" id="UnnamerCollect.collectbanksbj" name="UnnamerCollect.collectbanksbj" value="${unnamerCollect.collectbanksbj}">
	<input type="hidden" id="UnnamerCollect.lastmodifytime" name="UnnamerCollect.lastmodifytime" value="${unnamerCollect.lastmodifytime}">
	<input type="hidden" id="UnnamerCollect.lastmodifyer" name="UnnamerCollect.lastmodifyer" value="${unnamerCollect.lastmodifyer}">
	<input type="hidden" id="UnnamerCollect.businessstate" name="UnnamerCollect.businessstate" value="${unnamerCollect.businessstate}">
	<input type="hidden" id="UnnamerCollect.processstate" name="UnnamerCollect.processstate" value="${unnamerCollect.processstate}">
	<input type="hidden" id="UnnamerCollect.isclaim" name="UnnamerCollect.isclaim" value="0">
	<input type="hidden" id="UnnamerCollect.collectbankid" name="UnnamerCollect.collectbankid" value="${unnamerCollect.collectbankid}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/unnameCollect/unnamerCollectEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/unnameCollect/unnamerCollectEditGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${unnamerCollect.processstate}';
//当前对象主键ID
var unnamercollectid = '${unnamerCollect.unnamercollectid}';	
var xjrj = '${xjrj}';
var username = '${username}';

//页面文本
var Txt={
	//未明户收款
	unnamerCollect:'${vt.boText}',
	//boText复制创建
	unnamerCollect_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	unnamerCollect_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	unnamerCollect_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            	id:'currentWorkflowTask',
					            title: '${vt.processApproveInfo}',
					            border:false,
					            layout:'fit',
					            contentEl:'div_top_north'
					          },
				              {
				              		id:'centercontent',
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:272.5,
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								title:'${vt.processTrackInfo}',
								border:false,
			            		layout:'anchor',
				            	collapsible: true,
				            	collapsed:true,
				            	autoScroll: true,
								height:200,
								contentEl:'div_top_south'
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
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateUnnamerCollect,iconCls:'icon-table-save'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessUnnamerCollect,iconCls:'task'},'-',
<%}%>
{id:'_previewVoucher',text:'凭证预览',handler:_previewVoucherUnnamerCollect,iconCls:'icon-view'},'-',
{id:'_cashJournal',text:'现金日记账',handler:_cashJournalUnnamerCollect,iconCls:'icon-view'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelUnnamerCollect,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		Ext.getCmp('_previewVoucher').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
Ext.getCmp('_saveOrUpdate').disable();
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_saveOrUpdate').disable();	}
//});
</script>

