<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月07日 14点27分38秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象开票数据抬头(Vbrk)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/vbrkView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/vbrkViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_vbrp" boName="Vbrp" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YGETVBRP.VBELN='${vbrk.vbeln}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${vbrk.vbeln}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.vbeln" nodeId="${workflowNodeDefId}"/> >${vt.property.vbeln}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.vbeln" name="Vbrk.vbeln" value="${vbrk.vbeln}" <fisc:authentication sourceName="Vbrk.vbeln" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.fkart" nodeId="${workflowNodeDefId}"/> >${vt.property.fkart}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.fkart" name="Vbrk.fkart" value="${vbrk.fkart}" <fisc:authentication sourceName="Vbrk.fkart" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.waerk" nodeId="${workflowNodeDefId}"/> >${vt.property.waerk}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.waerk" name="Vbrk.waerk" value="${vbrk.waerk}" <fisc:authentication sourceName="Vbrk.waerk" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.fkdat" nodeId="${workflowNodeDefId}"/> >${vt.property.fkdat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.fkdat" name="Vbrk.fkdat" value="${vbrk.fkdat}" <fisc:authentication sourceName="Vbrk.fkdat" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.budat" nodeId="${workflowNodeDefId}"/> >${vt.property.budat}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.budat" name="Vbrk.budat" value="${vbrk.budat}" <fisc:authentication sourceName="Vbrk.budat" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.name1" nodeId="${workflowNodeDefId}"/> >${vt.property.name1}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.name1" name="Vbrk.name1" value="${vbrk.name1}" <fisc:authentication sourceName="Vbrk.name1" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.kurrf" nodeId="${workflowNodeDefId}"/> >${vt.property.kurrf}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.kurrf" name="Vbrk.kurrf" value="${vbrk.kurrf}" <fisc:authentication sourceName="Vbrk.kurrf" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.vtext" nodeId="${workflowNodeDefId}"/> >${vt.property.vtext}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.vtext" name="Vbrk.vtext" value="${vbrk.vtext}" <fisc:authentication sourceName="Vbrk.vtext" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.taxout" nodeId="${workflowNodeDefId}"/> >${vt.property.taxout}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.taxout" name="Vbrk.taxout" value="${vbrk.taxout}" <fisc:authentication sourceName="Vbrk.taxout" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.tax" nodeId="${workflowNodeDefId}"/> >${vt.property.tax}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.tax" name="Vbrk.tax" value="${vbrk.tax}" <fisc:authentication sourceName="Vbrk.tax" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Vbrk.taxin" nodeId="${workflowNodeDefId}"/> >${vt.property.taxin}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Vbrk.taxin" name="Vbrk.taxin" value="${vbrk.taxin}" <fisc:authentication sourceName="Vbrk.taxin" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  >${vt.property.sfakn}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Vbrk.sfakn" name="Vbrk.sfakn" value="${vbrk.sfakn}" >
		</td>
	</tr>

	<input type="hidden" id="Vbrk.client" name="Vbrk.client" value="${vbrk.client}">
	<input type="hidden" id="Vbrk.kunrg" name="Vbrk.kunrg" value="${vbrk.kunrg}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_vbrp"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var vbeln = '${vbrk.vbeln}';	

//页面文本
var Txt={
	//开票数据抬头
	vbrk:'${vt.boText}',
	          
	//开票明细
	vbrp:'${vbrpVt.boText}',
	//boText复制创建
	vbrk_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	vbrk_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	vbrk_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:235,
				            		autoScroll: true,
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
						            		title:'${vbrpVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_vbrp'
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
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelVbrk,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
//});
Ext.onReady(function(){
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
 });
</script>
