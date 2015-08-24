<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月28日 11点05分03秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象客户代垫额度和发货额度配置(CustomerCreditConf)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditConfView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ceditValueControl/customerCreditConfViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_customerCreditProj" boName="CustomerCreditProj" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YCUSTCREDPROJ.CONFIGID='${customerCreditConf.configid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${customerCreditConf.configid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.customerid" nodeId="${workflowNodeDefId}"/> >${vt.property.customerid}：</td>
		<td width="30%">
			<div id="div_customerid_sh"></div>
			<fisc:searchHelp divId="div_customerid_sh" boName="CustomerCreditConf" boProperty="customerid"  value="${customerCreditConf.customerid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.credittype" nodeId="${workflowNodeDefId}"/> >${vt.property.credittype}：</td>
		<td  width="40%">
			<div id="div_credittype_dict"></div>
			<fisc:dictionary boName="CustomerCreditConf" boProperty="credittype" dictionaryName="YDCUSTOMERCREDITTYPE" divId="div_credittype_dict" isNeedAuth="false"  value="${customerCreditConf.credittype}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.prepayvalue" nodeId="${workflowNodeDefId}"/> >${vt.property.prepayvalue}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="CustomerCreditConf.prepayvalue" name="CustomerCreditConf.prepayvalue" value="${customerCreditConf.prepayvalue}" <fisc:authentication sourceName="CustomerCreditConf.prepayvalue" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.sendvalue" nodeId="${workflowNodeDefId}"/> >${vt.property.sendvalue}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="CustomerCreditConf.sendvalue" name="CustomerCreditConf.sendvalue" value="${customerCreditConf.sendvalue}" <fisc:authentication sourceName="CustomerCreditConf.sendvalue" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.startingtime" nodeId="${workflowNodeDefId}"/> >${vt.property.startingtime}：</td>
		<td width="30%">
			<input type="text" id="CustomerCreditConf.startingtime" name="CustomerCreditConf.startingtime" value="">
				<fisc:calendar applyTo="CustomerCreditConf.startingtime"  divId="" fieldName="" defaultValue="${customerCreditConf.startingtime}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="CustomerCreditConf.endtime" nodeId="${workflowNodeDefId}"/> >${vt.property.endtime}：</td>
		<td  width="40%">
			<input type="text" id="CustomerCreditConf.endtime" name="CustomerCreditConf.endtime" value="">
				<fisc:calendar applyTo="CustomerCreditConf.endtime"  divId="" fieldName="" defaultValue="${customerCreditConf.endtime}"></fisc:calendar>
		</td>
	</tr>

	<input type="hidden" id="CustomerCreditConf.createtime" name="CustomerCreditConf.createtime" value="${customerCreditConf.createtime}">
	<input type="hidden" id="CustomerCreditConf.client" name="CustomerCreditConf.client" value="${customerCreditConf.client}">
	<input type="hidden" id="CustomerCreditConf.lastmodifytime" name="CustomerCreditConf.lastmodifytime" value="${customerCreditConf.lastmodifytime}">
	<input type="hidden" id="CustomerCreditConf.configid" name="CustomerCreditConf.configid" value="${customerCreditConf.configid}">
	<input type="hidden" id="CustomerCreditConf.lastmodifyer" name="CustomerCreditConf.lastmodifyer" value="${customerCreditConf.lastmodifyer}">
	<input type="hidden" id="CustomerCreditConf.creator" name="CustomerCreditConf.creator" value="${customerCreditConf.creator}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_customerCreditProj"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var configid = '${customerCreditConf.configid}';	

//页面文本
var Txt={
	//客户代垫额度和发货额度配置
	customerCreditConf:'${vt.boText}',
	          
	//客户信用额度下挂立项配置表
	customerCreditProj:'${customerCreditProjVt.boText}',
	//boText创建
	customerCreditProj_Create:'${customerCreditProjVt.boTextCreate}',
	//boText复制创建
	customerCreditProj_CopyCreate:'${customerCreditProjVt.boTextCopyCreate}',
	// 进行【客户信用额度下挂立项配置表复制创建】操作时，只允许选择一条记录！
	customerCreditProj_CopyCreate_AllowOnlyOneItemForOperation:'${customerCreditProjVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【客户信用额度下挂立项配置表复制创建】操作的记录！
	customerCreditProj_CopyCreate_SelectToOperation:'${customerCreditProjVt.copyCreate_SelectToOperate}',
	//boText复制创建
	customerCreditConf_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	customerCreditConf_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	customerCreditConf_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:122.5,
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
						            		title:'${customerCreditProjVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_customerCreditProj'
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
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelCustomerCreditConf,iconCls:'icon-undo'},'-',
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
