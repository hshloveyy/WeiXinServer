<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月13日 18点01分18秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预警对像配置(AdvanceWarnObject)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnObjectEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/advanceWarn/advanceWarnObjectEditGen.js"></script>
</head>
<body>
<fisc:grid divId="div_advanceWarnRecv" boName="AdvanceWarnRecv" needCheckBox="true" editable="true" defaultCondition=" YADVAWARNRECE.WARNID='${advanceWarnObject.warnid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_advanceWarnCond" boName="AdvanceWarnCond" needCheckBox="true" editable="true" defaultCondition=" YADVAWARNCOND.WARNID='${advanceWarnObject.warnid}'" needAutoLoad="true" height="285" nameSapceSupport="true" orderBySql=" YADVAWARNCOND.CONDNO "></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${advanceWarnObject.warnid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.boid" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.boid}：</td>
		<td width="30%">
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="AdvanceWarnObject" boProperty="boid"  value="${advanceWarnObject.boid}" callBackHandler="boidCallBack"> ></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.fieldcode" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.fieldcode}：</td>
		<td  width="40%">
			<div id="div_fieldcode_sh"></div>
			<fisc:searchHelp divId="div_fieldcode_sh" boName="AdvanceWarnObject" boProperty="fieldcode"  value="${advanceWarnObject.fieldcode}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.warnrole" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.warnrole}：</td>
		<td width="30%">
			<div id="div_warnrole_dict"></div>
			<fisc:dictionary boName="AdvanceWarnObject" boProperty="warnrole" dictionaryName="YDWARNRULE" divId="div_warnrole_dict" isNeedAuth="false"  value="${advanceWarnObject.warnrole}"  ></fisc:dictionary>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.warnvalue" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.warnvalue}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="AdvanceWarnObject.warnvalue" name="AdvanceWarnObject.warnvalue" value="${advanceWarnObject.warnvalue}"   <fisc:authentication sourceName="AdvanceWarnObject.warnvalue" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.primarykey" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.primarykey}：</td>
		<td width="30%">
			<div id="div_primarykey_sh"></div>
			<fisc:searchHelp divId="div_primarykey_sh" boName="AdvanceWarnObject" boProperty="primarykey"  value="${advanceWarnObject.primarykey}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.viewurl" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.viewurl}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="AdvanceWarnObject.viewurl" name="AdvanceWarnObject.viewurl" value="${advanceWarnObject.viewurl}"   <fisc:authentication sourceName="AdvanceWarnObject.viewurl" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.dealfunc" nodeId="${workflowNodeDefId}"/> >${vt.property.dealfunc}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="AdvanceWarnObject.dealfunc" name="AdvanceWarnObject.dealfunc" value="${advanceWarnObject.dealfunc}"   <fisc:authentication sourceName="AdvanceWarnObject.dealfunc" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.state" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.state}：</td>
		<td  width="40%">
			<div id="div_state_dict"></div>
			<fisc:dictionary boName="AdvanceWarnObject" boProperty="state" dictionaryName="YDWARNSTATE" divId="div_state_dict" isNeedAuth="false"  value="${advanceWarnObject.state}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.cleartype" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.cleartype}：</td>
		<td width="30%">
			<div id="div_cleartype_dict"></div>
			<fisc:dictionary boName="AdvanceWarnObject" boProperty="cleartype" dictionaryName="YDCLEARTYPE" divId="div_cleartype_dict" isNeedAuth="false"  value="${advanceWarnObject.cleartype}"  ></fisc:dictionary>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.clearuser" nodeId="${workflowNodeDefId}"/> >${vt.property.clearuser}：</td>
		<td  width="40%">
			<div id="div_clearuser_sh"></div>
			<fisc:searchHelp divId="div_clearuser_sh" boName="AdvanceWarnObject" boProperty="clearuser"  value="${advanceWarnObject.clearuser}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="AdvanceWarnObject.warninfo" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.warninfo}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="AdvanceWarnObject.warninfo" name="AdvanceWarnObject.warninfo" value="${advanceWarnObject.warninfo}"   <fisc:authentication sourceName="AdvanceWarnObject.warninfo" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="AdvanceWarnObject.client" name="AdvanceWarnObject.client" value="${advanceWarnObject.client}">
	<input type="hidden" id="AdvanceWarnObject.warnid" name="AdvanceWarnObject.warnid" value="${advanceWarnObject.warnid}">
	<input type="hidden" id="AdvanceWarnObject.creator" name="AdvanceWarnObject.creator" value="${advanceWarnObject.creator}">
	<input type="hidden" id="AdvanceWarnObject.createtime" name="AdvanceWarnObject.createtime" value="${advanceWarnObject.createtime}">
	<input type="hidden" id="AdvanceWarnObject.lastmodifyer" name="AdvanceWarnObject.lastmodifyer" value="${advanceWarnObject.lastmodifyer}">
	<input type="hidden" id="AdvanceWarnObject.lastmodifytime" name="AdvanceWarnObject.lastmodifytime" value="${advanceWarnObject.lastmodifytime}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_advanceWarnRecv"></div>
<div id="div_advanceWarnCond"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var warnid = '${advanceWarnObject.warnid}';	

//页面文本
var Txt={
	//预警对像配置
	advanceWarnObject:'${vt.boText}',
	          
	//预警对像接收人
	advanceWarnRecv:'${advanceWarnRecvVt.boText}',
	//boText创建
	advanceWarnRecv_Create:'${advanceWarnRecvVt.boTextCreate}',
	//boText复制创建
	advanceWarnRecv_CopyCreate:'${advanceWarnRecvVt.boTextCopyCreate}',
	// 进行【预警对像接收人复制创建】操作时，只允许选择一条记录！
	advanceWarnRecv_CopyCreate_AllowOnlyOneItemForOperation:'${advanceWarnRecvVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预警对像接收人复制创建】操作的记录！
	advanceWarnRecv_CopyCreate_SelectToOperation:'${advanceWarnRecvVt.copyCreate_SelectToOperate}',
          
	//预警对像条件
	advanceWarnCond:'${advanceWarnCondVt.boText}',
	//boText创建
	advanceWarnCond_Create:'${advanceWarnCondVt.boTextCreate}',
	//boText复制创建
	advanceWarnCond_CopyCreate:'${advanceWarnCondVt.boTextCopyCreate}',
	// 进行【预警对像条件复制创建】操作时，只允许选择一条记录！
	advanceWarnCond_CopyCreate_AllowOnlyOneItemForOperation:'${advanceWarnCondVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预警对像条件复制创建】操作的记录！
	advanceWarnCond_CopyCreate_SelectToOperation:'${advanceWarnCondVt.copyCreate_SelectToOperate}',
	//boText复制创建
	advanceWarnObject_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	advanceWarnObject_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	advanceWarnObject_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:235,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						           items:[
			              						                {
						            		title:'${advanceWarnRecvVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_advanceWarnRecv'
						            	},
          						                {
						            		title:'${advanceWarnCondVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_advanceWarnCond'
						            	}
						    ]}
						   ]}
]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateAdvanceWarnObject,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelAdvanceWarnObject,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

//});
Ext.onReady(function(){
    var tabsSize = 2;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 2 ; i++){
		   tabs.setActiveTab(2-1-i);
		}
	}
 });
</script>